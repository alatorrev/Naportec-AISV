package com.naportec.aisv.logica;

import com.naportec.aisv.entidades.Movimiento;
import com.naportec.aisv.entidades.Pagos;
import com.naportec.aisv.entidades.Precarga;
import com.naportec.aisv.entidades.Puerto;
import com.naportec.aisv.entidades.Transaccion;
import com.naportec.seguridad.entidades.SUser;
import com.naportec.utilidades.enumeraciones.Estado;
import com.naportec.utilidades.logica.AbstractFacade;
import com.naportec.utilidades.logica.Conexion;
import com.naportec.utilidades.mail.UtilHtml;
import com.naportec.utilidades.mail.UtilMail;
import java.io.IOException;
import java.io.Serializable;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import org.primefaces.context.RequestContext;

/**
 * Logica de Negocios de la entidad Transaccion
 *
 * @author Fernando
 */
@Stateless
public class TransaccionFacade extends AbstractFacade<Transaccion> implements Serializable {

    private String ipTransaccion;
    private SUser usuario;
    private static final Logger LOG = Logger.getLogger(TransaccionFacade.class.getName());

    /**
     * Inicialización de nuestro Recurso de Datos
     */
    @PersistenceContext(unitName = "com.naportec_Aisv")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TransaccionFacade() {
        super(Transaccion.class);
    }

    public void ingresoRochoTrans(Transaccion entity, String usua) {
        entity.setIngresoRochoTrans(new Date()); //used to be boolean true
        entity.setSalidaRochoTrans(null);       //used to be boolean false
        super.modificar(entity);
        this.getEntityManager().persist(llenadoDatos(entity, Estado.IngresoRocho.name().toUpperCase(), usua));
    }

    /**
     * Método para obtener una entidad Puerto a partir de un nombre y que este
     * activo
     *
     * @param nombre
     * @return
     */
    public Puerto ObtenerPuerto(String nombre) {
        Query q = getEntityManager().createNamedQuery("Puerto.findByNombrePuer");
        q.setParameter("estado", Estado.Activo.name());
        q.setParameter("nombrePuer", nombre.toUpperCase());
        if (q.getResultList().size() > 0) {
            return (Puerto) q.getResultList().get(0);
        } else {
            return null;
        }
    }

    /**
     * Método para guardar una Transaccion
     *
     * @param entity
     */
    @Override
    public void guardar(Transaccion entity) {
        super.guardar(entity);
        this.getEntityManager().persist(llenadoDatos(entity, Estado.Creacion.getName().toUpperCase(), this.getCurrentloggeduser()));
    }

    /**
     * Método para guardar una Transacción y llenar un movimiento de la
     * transaccion
     *
     * @param entity
     * @param prec
     */
    public void guardarCustom(Transaccion entity, Precarga prec) {
        Pagos pagos = this.getEntityManager().find(entity.getCodigo_pago().getClass(), entity.getCodigo_pago().getNumeroPago());
        if (pagos == null) {
            entity.getCodigo_pago().setFechaPago(null);
            entity.getCodigo_pago().setEstado("Pendiente");
            this.getEntityManager().persist(entity.getCodigo_pago());
        } else {
            entity.setCodigo_pago(pagos);
        }
        this.getEntityManager().persist(entity);
        this.getEntityManager().persist(llenadoDatos(entity, Estado.Creacion.getName().toUpperCase(), this.getUsuario().getUsrLoginname()));
        prec.setEspaciosDisponiblesPrec(prec.getEspaciosDisponiblesPrec() - 1);
        this.getEntityManager().merge(prec);
    }

    /**
     * Método para ANULAR una transaccion lo que significa que se cambiara al
     * estado Anulado
     *
     * @param entity
     */
    @Override
    public void eliminar(Transaccion entity) {
        this.getEntityManager().merge(llenadoDatos(entity, Estado.Anulado.name().toUpperCase(), this.getCurrentloggeduser()));
        entity.getCodigoPrec().setEspaciosDisponiblesPrec(entity.getCodigoPrec().getEspaciosDisponiblesPrec() + 1);
        this.getEntityManager().merge(entity.getCodigoPrec());
        this.getEntityManager().merge(entity);
    }

    @Deprecated
    public void modificarDatoNo(Transaccion entity, int u, int y, int x) throws Exception {
        super.modificar(entity);
    }

    /**
     * Método para modificar datos de una Transacción y grabar un movimiento de
     * la Transaccion
     *
     * @param entity
     */
    @Override
    public void modificar(Transaccion entity) {
        super.modificar(entity);
        this.getEntityManager().persist(llenadoDatos(entity, Estado.edicion.name().toUpperCase(), this.getCurrentloggeduser()));
    }

    /**
     * Método para modificar una Transaccion y cambiar su estado
     *
     * @param entity
     * @param estado
     * @throws Exception
     */
    public void modificar(Transaccion entity, Estado estado) throws Exception {
        entity.setEstado(estado.name());
        this.getEntityManager().merge(entity);
        this.getEntityManager().persist(llenadoDatos(entity, estado.name().toUpperCase(), this.getCurrentloggeduser()));
    }

    /**
     * Método para realizar aprobación definitiva de la Transaccion y comprobar
     * si esta transacción esta bloqueada por PAN(Policía Antinarcótico) enviar
     * notificación por correo electrónico.
     *
     * @param entity
     * @throws Exception
     */
    public void aprobacionDefinitiva(Transaccion entity) throws Exception {
        entity.setEstado(Estado.Aprobado.name());
        this.getEntityManager().merge(entity);
        this.getEntityManager().persist(llenadoDatos(entity, Estado.Aprobado.name().toUpperCase(), this.getCurrentloggeduser()));

        if (entity.getEstado().equals(Estado.Aprobado.name())) {
            if (entity.getEstadoPan() != null) {
                if (entity.getEstadoPan().equals(Estado.bloqueoPan.name())) {
                    String plantilla = this.obtenerArchivos("/aisv/correo/plantillas/bloqueoPan.html");
                    String datoSubject = entity.getContenedorTrans() != null ? entity.getContenedorTrans() : entity.getCodigoPrec().getBookingPrec();
                    StringBuilder sbcorreos = new StringBuilder();
                    if (entity.getUsrId() != null) {
                        if (entity.getUsrId().getCodigoSoli() != null) {
                            sbcorreos.append(entity.getUsrId().getCodigoSoli().getCorreoSoli());
                            sbcorreos.append(";");
                            sbcorreos.append(entity.getCodigoPrec().getIdLineaPrec().getCorreoNotifNavi());
                            sbcorreos.append(";");
                        }
                    }
                    sbcorreos.append("cip@dole.com;");
                    sbcorreos.append("naportecembarque@dole.com;");
                    sbcorreos.append("tecnicospct@dole.com;");
                    sbcorreos.append("coordinadoresdepatio@dole.com;");
                    sbcorreos.append("sipa_naportec@outlook.com;");
                    sbcorreos.append("wisser.escalante@dole.com;");
                    sbcorreos.append("raul.orrala@dole.com;");
                    sbcorreos.append("jorge.aviles@dole.com;");
                    sbcorreos.append("anden1@dole.com;");
                    sbcorreos.append("anden2@dole.com;");
                    sbcorreos.append("michel.zambrano@dole.com;");
                    sbcorreos.append("julio.delpozo@dole.com;");
//                    sbcorreos.append("perchoburgos@gmail.com;");
                    sbcorreos.append("Jaime.Loor@dole.com;");
                    sbcorreos.append("Eddy.F.Zamora@dole.com;");
                    sbcorreos.append("Henry.Morales.A@dole.com;");
                    sbcorreos.append("Richard.Ube@dole.com;");
                    sbcorreos.append("Coordinadores.Logisticos@dole.com;");
                    email = new UtilMail();
                    email.setTo(sbcorreos.toString());
//                    email.setCc(direccionCorreoClientes(entity));
                    try {
                        email.setSubject("NOTIFICACIÓN: << Naportec - " + datoSubject + " - " + entity.getCodigoPrec().getImportadorPrec() + " - Bloqueo PAN >>");
                    } catch (Exception ex) {
                        throw new Exception("No se ha podido colocar el Subject ");
                    }
                    try {
                        email.setContent(UtilHtml.importarDatosBloqueoPan(entity, UtilMail.loadHTMLFile(plantilla), sbcorreos.toString()));
                    } catch (IOException | ParseException ex) {
                        throw new Exception("No se ha podido cargar la plantilla");
                    }
                    email.send();
                }
            }
        }
    }

    /**
     * Método para realizar la aprobación documental y seenvían notificaciones a
     * las personas indicadas
     *
     * @param entity
     * @throws Exception
     */
    public void aprobacionDocumental(Transaccion entity, String userName) throws Exception {
        entity.setEstado(Estado.Documental.name());
        this.getEntityManager().merge(entity);
        this.getEntityManager().persist(llenadoDatos(entity, Estado.Documental.name().toUpperCase(), userName));
        String plantilla = this.obtenerArchivos("/aisv/correo/plantillas/aprobacionDocumental.html");
        email = new UtilMail();
        StringBuilder sb = new StringBuilder();
//        if (entity.getCodigoPrec().getCondicionContenedorPrec().trim().equals("LCL/LCL")) {
//            sb.append("kathy.pilligua@dole.com;");
//            sb.append("jose.mateus@dole.com;");
//            sb.append("william.cabanilla@dole.com;");
//            sb.append("percy.rojas@dole.com;");
//            sb.append("felix.segura@dole.com;");
//            sb.append("julio.aguirre@dole.com;");
//            sb.append("henry.izurieta@dole.com;");
//            sb.append("alfredo.rolando.cordova@dole.com;");
//        } else if (entity.getCodigoPrec().getCondicionContenedorPrec().trim().equals("FCL/FCL")) {
//            sb.append("despachadores.BNP@dole.com;");
//            sb.append("Basculataller@dole.com;");
//            sb.append("Basculamuelle@dole.com;");
//            sb.append("Raul.Orrala@dole.com;");
//            sb.append("Jorge.Aviles@dole.com;");
//            sb.append("Coordinadoresdepatio@dole.com;");
//        }
        //PARA TODOS********************************************************
        sb.append("michel.zambrano@dole.com;");
        sb.append("julio.delpozo@dole.com;");
//        sb.append("Evelyn.Rivera@dole.com;");
//        sb.append("despachos@dole.com;");
//        sb.append("naportecgarita@dole.com;");
        //******************************************************************
        email.setTo(sb.toString());
//        email.setCc(direccionCorreoClientes(entity));
        String referencia = "";
        if (entity.getCodigoPrec().getCondicionContenedorPrec().equals("FCL/FCL")) {
            referencia = entity.getContenedorTrans();
        } else if (entity.getCodigoPrec().getTipoPrec().equals("I")) {
            referencia = entity.getCodigoPrec().getBlhijoPrec();
        } else {
            referencia = entity.getCodigoPrec().getBookingPrec();
        }
        try {
            email.setSubject("NOTIFICACIÓN PRUEBA: << Naportec - " + entity.getPlacaTrasnportistaTrans() + " - " + referencia + " - "
                    + "Aprobacion Documental (" + entity.getCodigoPrec().getTipoPrec() + ")>>");
        } catch (Exception ex) {
            throw new Exception("No se ha podido colocar el Subject ");
        }
        try {
            email.setContent(UtilHtml.importarDatosAprobacionDocumental(entity, UtilMail.loadHTMLFile(plantilla), sb.toString()));
        } catch (IOException | ParseException ex) {
            throw new Exception("No se ha podido cargar la plantilla");
        }
        email.send();
    }

    /**
     * Método para realizar el bloqueo PAN(Policía Antinarcótico) de uan
     * Transaccion se realzar la verificación si la transacción ya ha sido
     * aprobada definitivamente se envía la notificación.
     *
     * @param entity
     * @param estado
     * @param userName
     */
    public void bloqueoPan(Transaccion entity, Estado estado, String userName) throws EJBException {
        entity.setEstadoPan(estado.name());
        this.getEntityManager().merge(entity);
        this.getEntityManager().persist(llenadoDatos(entity, estado.name().toUpperCase(), userName));
    }

    /**
     * Esta operacion debe realizarse cuando se graba el bloqueo PAN pero la
     * notificacion no se envia entonces el AISV debe regresar a su esta
     * original antes de ser bloqueado pero debe agregarse unaincidencia de un
     * error ocurrido al tratar de enviar la notifcacion
     *
     * @param entity
     * @param estado
     * @param userName
     * @throws EJBException
     */
    public void problemaBloqueoPan(Transaccion entity, String userName) throws EJBException {
        entity.setEstadoPan("");
        this.getEntityManager().merge(entity);
        this.getEntityManager().persist(llenadoDatos(entity, "FALLO NOT. BLOQ. PAN", userName));
    }

    public void notificacionBloqueoPan(Transaccion entity) throws MessagingException, IOException, ParseException {
        if (entity.getEstado().equals(Estado.Aprobado.name())) {
            String plantilla = this.obtenerArchivos("/aisv/correo/plantillas/bloqueoPan.html");
            String datoSubject = entity.getContenedorTrans() != null ? entity.getContenedorTrans() : entity.getCodigoPrec().getBookingPrec();
            StringBuilder sbcorreosCCO = new StringBuilder();
            StringBuilder sbcorreosTo = new StringBuilder();
            if (entity.getUsrId() != null) {
                if (entity.getUsrId().getCodigoSoli() != null) {
                    sbcorreosTo.append(entity.getUsrId().getCodigoSoli().getCorreoSoli());
                    sbcorreosTo.append(";");
                    sbcorreosTo.append(entity.getCodigoPrec().getIdLineaPrec().getCorreoNotifNavi());
//                    sbcorreosTo.append("michel.zambrano@dole.com;");
                }
            }
            //*******************************************************************
            sbcorreosCCO.append("cip@dole.com;");
            sbcorreosCCO.append("naportecembarque@dole.com;");
            sbcorreosCCO.append("tecnicospct@dole.com;");
            sbcorreosCCO.append("coordinadoresdepatio@dole.com;");
            sbcorreosCCO.append("sipa_naportec@outlook.com;");
            sbcorreosCCO.append("wisser.escalante@dole.com;");
            sbcorreosCCO.append("raul.orrala@dole.com;");
            sbcorreosCCO.append("jorge.aviles@dole.com;");
            sbcorreosCCO.append("anden1@dole.com;");
            sbcorreosCCO.append("anden2@dole.com;");
            sbcorreosCCO.append("michel.zambrano@dole.com;");
            sbcorreosCCO.append("julio.delpozo@dole.com;");
//            sbcorreosCCO.append("perchoburgos@gmail.com;");
//            sbcorreosCCO.append("Jaime.Loor@dole.com;");
//            sbcorreosCCO.append("Eddy.F.Zamora@dole.com;");
//            sbcorreosCCO.append("Henry.Morales.A@dole.com;");
//            sbcorreosCCO.append("Richard.Ube@dole.com;");
            sbcorreosCCO.append("Coordinadores.Logisticos@dole.com;");
            sbcorreosCCO.append("Reynaldo.Vera@dole.com;");
            email = new UtilMail();
            email.setTo(sbcorreosTo.toString());
            email.setCco(sbcorreosCCO.toString());
            try {
                email.setSubject("NOTIFICACIÓN: << Naportec - " + datoSubject + " - " + entity.getCodigoPrec().getImportadorPrec() + " - Bloqueo PAN >>");
            } catch (EJBException ex) {
                throw new EJBException("No se ha podido colocar el Subject ");
            }
            try {
                email.setContent(UtilHtml.importarDatosBloqueoPan(entity, UtilMail.loadHTMLFile(plantilla), sbcorreosTo.toString()));
            } catch (IOException ex) {
                throw new IOException("No se ha podido cargar la plantilla" + ex.getMessage());
            } catch (ParseException ex) {
                throw new ParseException("No se ha podido cargar la plantilla" + ex.getMessage(), 0);
            }
            email.send();
        }
    }

    /**
     * Método para realizar Bloqueo Preembarque si la transaccion está aprobada
     * se envía la notificación
     *
     * @param entity
     * @param estado
     * @param correo
     * @throws Exception
     */
    public void bloqueoPreembarque(Transaccion entity, Estado estado, String userName) throws Exception {
        entity.setEstadoPan(estado.name());
        entity.setPreembarqueTrans(true);
        this.getEntityManager().merge(entity);
        this.getEntityManager().persist(llenadoDatos(entity, estado.name().toUpperCase(), userName));
        if (entity.getEstado().equals(Estado.Aprobado.name())) {
            String plantilla = this.obtenerArchivos("/aisv/correo/plantillas/bloqueoPreembarque.html");
            String datoSubject = entity.getContenedorTrans() != null ? entity.getContenedorTrans() : entity.getCodigoPrec().getBookingPrec();
            email = new UtilMail();
            StringBuilder sbcorreos = new StringBuilder();
            sbcorreos.append("michel.zambrano@dole.com;");
            sbcorreos.append("julio.delpozo@dole.com;");
//            sbcorreos.append("perchoburgos@gmail.com;");
            sbcorreos.append(entity.getCorreos());
            email.setTo(sbcorreos.toString());
            email.setSubject("NOTIFICACIÓN: << Naportec - " + datoSubject + " - " + entity.getCodigoPrec().getImportadorPrec() + " - Bloqueo Preembarque >>");
            email.setContent(UtilHtml.importarDatosBloqueoPreembarque(entity, UtilMail.loadHTMLFile(plantilla)));
            email.send();
        }
    }

    /**
     * Método para realizar el desbloqueo pan luego se verifica si la
     * transaccion está aprobada se envía la notificación por correo
     *
     * @param entity
     * @param estado
     * @param correo
     * @throws Exception
     */
    public void desBloqueoPan(Transaccion entity, Estado estado, String userName) throws Exception {
        entity.setEstadoPan(estado.name());
        entity.setFechaInspeccionMTrans(entity.getFechaInspeccionTrans());
        this.getEntityManager().merge(entity);
        this.getEntityManager().persist(llenadoDatos(entity, estado.name().toUpperCase(), userName));
        if (entity.getEstado().equals(Estado.Aprobado.name())) {
            String plantilla = this.obtenerArchivos("/aisv/correo/plantillas/desbloqueoPan.html");
            String datoSubject = entity.getContenedorTrans() != null ? entity.getContenedorTrans() : entity.getCodigoPrec().getBookingPrec();
            email = new UtilMail();
            StringBuilder sbcorreos = new StringBuilder();
//            sbcorreos.append("perchoburgos@gmail.com;");
            sbcorreos.append(entity.getCorreos());
            StringBuilder sbcorreosCC = new StringBuilder();
            sbcorreosCC.append("michel.zambrano@dole.com;");
            sbcorreosCC.append("julio.delpozo@dole.com;");
            email.setTo(sbcorreos.toString());
            email.setCco(sbcorreosCC.toString());
            email.setSubject("NOTIFICACIÓN: << Naportec - " + datoSubject + " - " + entity.getCodigoPrec().getImportadorPrec() + " - Desbloqueo PAN>>");
            email.setContent(UtilHtml.importarDatosDesbloqueo(entity, UtilMail.loadHTMLFile(plantilla)));
            email.send();
        }
    }

    /**
     * Método para realizar el desbloqueo preembarque, se genera una
     * notificación
     *
     * @param entity
     * @param estado
     * @param correo
     * @throws Exception
     */
    public void desBloqueoPreembarque(Transaccion entity, Estado estado, String userName) throws Exception {
        if (estado.equals(Estado.DesbloqueoPreembarque.name())) {
            entity.setPreembarqueTrans(false);
            entity.setEstadoPan(estado.name());
            entity.setFechaInspeccionMTrans(entity.getFechaInspeccionTrans());
            this.getEntityManager().merge(entity);
            this.getEntityManager().persist(llenadoDatos(entity, estado.name().toUpperCase(), userName));
            String plantilla = this.obtenerArchivos("/aisv/correo/plantillas/desbloqueoPreembarque.html");
            String datoSubject = entity.getContenedorTrans() != null ? entity.getContenedorTrans() : entity.getCodigoPrec().getBookingPrec();
            email = new UtilMail();
            StringBuilder sbcorreos = new StringBuilder();
            sbcorreos.append("michel.zambrano@dole.com;");
            sbcorreos.append("julio.delpozo@dole.com;");
            sbcorreos.append("perchoburgos@gmail.com;");
            sbcorreos.append(entity.getCorreos());
            email.setTo(sbcorreos.toString());
            email.setSubject("NOTIFICACIÓN: << Naportec - " + datoSubject + " - " + entity.getCodigoPrec().getImportadorPrec() + " - Desbloqueo Preembarque >>");
            email.setContent(UtilHtml.importarDatosDesbloqueoPreembarque(entity, UtilMail.loadHTMLFile(plantilla)));
            email.send();
        }
    }

    /**
     * Método que crea una entidad de Movimiento con la ip y los datos de
     * usaurio que generaron el mismo
     *
     * @param t
     * @param tipo
     * @param usuario
     * @return
     */
    private Movimiento llenadoDatos(Transaccion t, String tipo, String usuario) {
        Movimiento m = new Movimiento();
        m.setCodigoTrans(t);
        m.setFechaMovi(new Date());
        m.setTipoMovi(tipo);
        m.setIpMovi(getIpTransaccion());
        m.setDescripcionMovi(usuario);
        return m;
    }

    /**
     * Método para verificar si la persona que retira la carga se encuentra en
     * un estado correcto para poderlo hacer y no esta bloqueado
     *
     * @param cedula
     * @return
     * @throws Exception
     */
    public String estadoPersonaRetira(String cedula) throws Exception {
        Conexion conexion = new Conexion();
        conexion.conexion_Only_Persona();
        String sql = "SELECT P_CEDULA, P_NOMBRES, P_APELLIDOS, P_TIPO, P_MAIL, P_EMPRESA, P_OBS, P_PROHIBIDO"
                + "  FROM dbo.R_PERSONA"
                + "  WHERE (P_CEDULA = ?)";
        ResultSet rs = conexion.ejecutarSQLSelect(sql, cedula.trim());
        String estadoTrans = "";
        int cont = 0;
        while (rs.next()) {
            estadoTrans = rs.getString(8);
            System.err.println("ESTADO DE LA PERSONA QUE RETIRA::" + estadoTrans);
            cont++;
        }
        if (cont > 0) {
            rs.close();
            if (estadoTrans.toUpperCase().equals("0")) {
                //EL TRANSPORTISTA ESTA HABILITADO
                return "HABILITADO";
            } else {
                return "PROHIBIDO";
            }
        } else {
            rs.close();
            return "HABILITADO";
        }
    }

    /**
     * Método para verificar si eltransportista que llevara la carga no este
     * bloqueado o no registrado en el sistema.
     *
     * @param cedula
     * @return
     * @throws Exception
     */
    public String estadoTransportista(String cedula) throws Exception {
        Conexion conexion = new Conexion();
        conexion.conexion_Only_Control_Vehicular();
        String sql = "SELECT NOMINA_ESTADO FROM AC_NOMINA WHERE NOMINA_CED = ?";
        ResultSet rs = conexion.ejecutarSQLSelect(sql, cedula.trim());
        String estadoTrans = "";
        int cont = 0;
        while (rs.next()) {
            estadoTrans = rs.getString(1);
            System.err.println("ESTADO DE TRASNPORTISTA::" + estadoTrans);
            cont++;
        }
        if (cont > 0) {
            rs.close();
            return estadoTrans;
        } else {
            rs.close();
            return "NOREGISTRADO";
        }
    }

    public String nombreTransportista(String cedula) throws Exception {
        Conexion conexion = new Conexion();
        conexion.conexion_Only_Control_Vehicular();
        String sql = "SELECT NOMINA_NOM,NOMINA_APE FROM AC_NOMINA WHERE NOMINA_CED = ?";
        ResultSet rs = conexion.ejecutarSQLSelect(sql, cedula.trim());
        String nombreTrans = "";
        int cont = 0;
        while (rs.next()) {
            nombreTrans = rs.getString(1) + " " + rs.getString(2);
            System.err.println("ESTADO DE TRASNPORTISTA::" + nombreTrans);
            cont++;
        }
        rs.close();

        if (cont > 0) {
            return nombreTrans;
        } else {
            rs.close();
            return "NO ENCONTRADO";
        }
    }

    public String facturaValida(String factura) {
        String numeroFactura = null;
        Conexion conexion = new Conexion();
        conexion.conexion_Cobros_enLinea();
        try {
            String sql = "SELECT FacturaNum FROM [FacturaAduana].[dbo].[VIE_FACTURA_PAGO] where FacturaNum =?";
            ResultSet rs = conexion.ejecutarSQLSelect(sql, factura.trim());
            rs.last();
            int rowsCount = rs.getRow();
            rs.beforeFirst();
            if (rowsCount > 0) {
                while (rs.next()) {
                    numeroFactura = rs.getString(1);
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            conexion.cerrarConexion();
        }
        return numeroFactura;
    }

    public String direccionCorreoClientes(Transaccion entity) {
        StoredProcedureQuery storedProcedure = this.getEntityManager().createStoredProcedureQuery("sp_aisv_correos");
        storedProcedure.registerStoredProcedureParameter("NumAISV", Long.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("CorreoR", String.class, ParameterMode.OUT);
        storedProcedure.setParameter("NumAISV", entity.getCodigoTrans());
        storedProcedure.execute();
        String emailClientAddresses = storedProcedure.getOutputParameterValue("CorreoR").toString();
        return emailClientAddresses;
    }
    /**
     * @return the ipTransaccion
     */
    public String getIpTransaccion() {
        return ipTransaccion;
    }

    /**
     * @param ipTransaccion the ipTransaccion to set
     */
    public void setIpTransaccion(String ipTransaccion) {
        this.ipTransaccion = ipTransaccion;
    }

    /**
     * @return the usuario
     */
    public SUser getUsuario() {
        if (usuario == null) {
            usuario = new SUser();
        }
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(SUser usuario) {
        this.usuario = usuario;
    }

}
