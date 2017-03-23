package com.naportec.aisv.logica;

import com.naportec.aisv.entidades.Contacto;
import com.naportec.aisv.entidades.Naviera;
import com.naportec.aisv.entidades.Solicitud;
import com.naportec.seguridad.entidades.SUser;
import com.naportec.seguridad.entidades.SUserrole;
import com.naportec.seguridad.entidades.SUserrolePK;
import com.naportec.utilidades.enumeraciones.Estado;
import com.naportec.utilidades.logica.AbstractFacade;
import com.naportec.utilidades.mail.UtilHtml;
import com.naportec.utilidades.mail.UtilMail;
import com.naportec.utilidades.otros.Aes;
import com.naportec.utilidades.otros.GeneradorClaves;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.LinkedList;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Logica de Negocios de la entidad Solicitud
 *
 * @author Fernando
 */
@Stateless
public class SolicitudFacade extends AbstractFacade<Solicitud> implements Serializable {

    private static final Logger LOG = Logger.getLogger(SolicitudFacade.class.getName());
    public static final int ERROR_APROBAR = 0;
    public static final int APROBAR_OK = 1;
    private final String correosSolicitud = "elizabeth.aguayo@dole.com;michel.zambrano@dole.com;julio.delpozo@dole.com;jonathan.davila@dole.com;brenda.lemarie@dole.com";

    public SolicitudFacade() {
        super(Solicitud.class);
        this.email = new UtilMail();
    }

    /**
     * Inicialización de nuestro Recurso de Datos
     */
    @PersistenceContext(unitName = "com.naportec_Aisv")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Método para guardar una nueva solictud de contacto y enviar un correo de
     * notificaciónb al cliente
     *
     * @param s
     * @throws EJBException
     */
    @Override
    public void guardar(Solicitud s) throws EJBException {
        s.setUsuarioModificacionSoli(this.getCurrentloggeduser());
        s.setFechaModificacionSoli(new Date());
        super.guardar(s);
        String plantilla = this.obtenerArchivos("/aisv/correo/plantillas/nuevaSolicitud.html");
        email = new UtilMail();
        email.setTo(s.getCorreoSoli());
        email.setSubject("NOTIFICACIÓN: << Naportec - " + s.getNombreSoli() + " - Nueva Solicitud>>");
        try {
            email.setContent(UtilHtml.importarDatosNuevaSolicitud(s, UtilMail.loadHTMLFile(plantilla)));
        } catch (IOException ex) {
            throw new EJBException("Ha sucedido un error al tratar de cargar la plantilla del correo");
        }
        try {
            email.send();
        } catch (MessagingException ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    /**
     * Método para guardar datos de contactos de la solicitud
     *
     * @param entidad
     * @throws EJBException
     */
    public void guardarSolicitud(Solicitud entidad) throws EJBException {
        List<Contacto> lista = entidad.getContactoList();
        entidad.setContactoList(new LinkedList<Contacto>());
        this.getEntityManager().persist(entidad);
        this.getEntityManager().flush();
        entidad = this.buscarPorIdentificacion(entidad.getIdentificacionSoli());
        for (Contacto x : lista) {
            x.setCodigoSoli(entidad);
            this.getEntityManager().persist(x);
        }
    }

    /**
     * Método para buscar una solicitud de contacto desde su numero de
     * identificación
     *
     * @param identificacion
     * @return
     */
    public Solicitud buscarPorIdentificacion(String identificacion) {
        Query q = getEntityManager().createQuery("SELECT x from Solicitud x where x.identificacionSoli=:ide and (x.estado =:estuno OR x.estado=:estdos)");
        q.setParameter("ide", identificacion);
        q.setParameter("estuno", Estado.PenAuto.name());
        q.setParameter("estdos", Estado.Pendiente.name());
        if (q.getResultList().size() > 0) {
            return (Solicitud) q.getResultList().get(0);
        } else {
            return null;
        }
    }

    /**
     * Método para saber si la solicitud existe
     *
     * @param s
     * @return
     */
    public boolean existeSolicitud(Solicitud s) {
        Query q = getEntityManager().createNamedQuery("Solicitud.existeSolicitud");
        q.setParameter("iden", s.getIdentificacionSoli());
        q.setParameter("estado", Estado.Anulado.name());
        if (q.getResultList().size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Método par saber si existe el nickname colocado en la solicitud
     *
     * @param s
     * @return
     */
    public boolean existeNick(Solicitud s) {
        Query q = getEntityManager().createNamedQuery("Solicitud.findByUsuarioSoli");
        q.setParameter("usuarioSoli", s.getUsuarioSoli());
        if (q.getResultList().size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Método para aprobar una solicitud de contacto
     *
     * @param s
     * @param usuario
     * @throws EJBException
     */
    public void aprobarSolicitud(Solicitud s, String usuario) throws EJBException {
        s.setUsuarioModificacionSoli(usuario);
        s.setFechaModificacionSoli(new Date());
        if (!s.getEstado().equals(Estado.Anulado.name())) {
            if (!s.getEstado().equals(Estado.Desaprobado.name()) || s.getsUserList() == null) {
                if (s.getsUserList() == null) {
                    s.setsUserList(new LinkedList<SUser>());
                }
                s.setEstado(Estado.Aprobado.name());
                Naviera nav = new Naviera();
                if (s.getTipoContactoNaviera() == true) {
                    nav.setCodigoCaeNavi(s.getDescripcionSoli());
                    nav.setCodigoOceNavi(s.getOceSoli());
                    nav.setNombreNavi(s.getNombreSoli());
                    nav.setEstado(Estado.Activo.name());
                    nav.setCorreoNotifNavi(s.getCorreoSoli());
                }
                if (s.getContactoList() != null) {
                    int cont = 1;
                    for (Contacto x : s.getContactoList()) {
                        SUser user = new SUser();
                        user.setEstado(Estado.Activo.name());
                        user.setUsrEnabled(true);
                        user.setUsrLoginname(s.getUsuarioSoli() + cont);
                        user.setUsrFirstname(x.getNombreCont());
                        user.setUsrToken("");
                        user.setUsrPassword(Aes.encryptData(GeneradorClaves.getPassword(GeneradorClaves.MAYUSCULAS, 2) + GeneradorClaves.getPassword(GeneradorClaves.MINUSCULAS, 2) + GeneradorClaves.getPassword(GeneradorClaves.NUMEROS, 2)));
                        user.setUsrEmail(x.getEmailCont());
                        user.setVersion(0);
                        user.setHoraingresoAu(new Date());
                        user.setUsuarioingresoAu(usuario);
                        user.setUsrTipoUsuario("E");
                        user.setUsrDescripcion(s.getNombreSoli());
                        user.setUsrTelefono(x.getTelefonoCont());
                        user.setUsrCelular(x.getCelularCont());
                        user.setCodigoSoli(s);
                        if (s.getTipoContactoNaviera() == true) {
                            user.setCodigoNavi(nav);
                        }
                        this.getEntityManager().persist(user);
                        s.getsUserList().add(user);
                        cont++;
                    }

                }
                this.modificar(s);
                for (int i = 0; i < s.getsUserList().size(); i++) {
                    //Agregacion de Rol al USUA
                    if (s.getsUserList().get(i).getEstado().equals(Estado.Activo.name())) {
                        SUserrole entidad = new SUserrole();
                        entidad.setVersion(0);
                        entidad.setHoraingresoAu(new Date());
                        entidad.setUsuarioingresoAu(usuario);
                        if (s.getTipoContactoSoli().equals("NA")) {
                            long usr = this.codigoUsuario(s.getsUserList().get(i));
                            SUserrolePK pk = new SUserrolePK(usr, 3);
                            entidad.setSUserrolePK(pk);
                        }
                        if (s.getTipoContactoSoli().equals("IE") || s.getTipoContactoSoli().equals("AA")) {
                            long usr = this.codigoUsuario(s.getsUserList().get(i));
                            SUserrolePK pk = new SUserrolePK(usr, 2);
                            entidad.setSUserrolePK(pk);
                        }
                        this.getEntityManager().persist(entidad);
                    }
                }
                for (SUser suser : s.getsUserList()) {
                    if (suser.getEstado().equals(Estado.Activo.name())) {
                        String plantilla = this.obtenerArchivos("/aisv/correo/plantillas/aprobacionAisv.html");
                        email = new UtilMail();
                        email.setTo(suser.getUsrEmail());
                        email.setSubject("NOTIFICACIÓN: << Naportec - " + suser.getCodigoSoli().getNombreSoli() + " - " + suser.getUsrFirstname() + " - Aprobación de Solicitud>>");
                        try {
                            email.setContent(UtilHtml.importarDatosAprobacion(suser, UtilMail.loadHTMLFile(plantilla)));
                        } catch (IOException ex) {
                            throw new EJBException("No hemos podido cargar la plantilla del Correo");
                        }
                        try {
                            email.send();
                        } catch (MessagingException ex) {
                            throw new EJBException(ex.getMessage());
                        }
                    }
                }
                email = new UtilMail();
                email.setTo(correosSolicitud);
                email.setSubject("NOTIFICACIÓN: << Naportec - " + s.getNombreSoli() + " - Aprobación de Solicitud>>");
                email.setContent("<h2>Se ha Aprobado la Solicitud de: " + s.getNombreSoli() + "</h2>");
                try {
                    email.send();
                } catch (MessagingException ex) {
                    throw new EJBException(ex.getMessage());
                }
            } else {
                s.setEstado(Estado.Aprobado.name());
                for (SUser u : s.getsUserList()) {
                    u.setEstado(Estado.Activo.name());
                    u.setUsrEnabled(true);
                    this.getEntityManager().merge(u);
                    String plantilla = this.obtenerArchivos("/aisv/correo/plantillas/aprobacionAisv.html");
                    email = new UtilMail();
                    email.setTo(u.getUsrEmail());
                    email.setSubject("NOTIFICACIÓN: << Naportec - " + u.getCodigoSoli().getNombreSoli() + " - " + u.getUsrFirstname() + " - Aprobación de Solicitud>>");
                    try {
                        email.setContent(UtilHtml.importarDatosAprobacion(u, UtilMail.loadHTMLFile(plantilla)));
                    } catch (IOException ex) {
                        throw new EJBException("No hemso podido cargar la plantilla del Correo");
                    }
                    try {
                        email.send();
                    } catch (MessagingException ex) {
                        throw new EJBException(ex.getMessage());
                    }
                }
                email = new UtilMail();
                email.setTo(correosSolicitud);
                email.setSubject("NOTIFICACIÓN: << Naportec - " + s.getNombreSoli() + " - Reaprobación de Solicitud>>");
                email.setContent("<h2>Se ha reaprobado la Solicitud de: " + s.getNombreSoli() + "</h2>");
                try {
                    email.send();
                } catch (MessagingException ex) {
                    throw new EJBException(ex.getMessage());
                }

                this.getEntityManager().merge(s);
            }
        }
    }

    /**
     * Método para obtener elcodigo del usuario de acuerdo a su nombre de
     * usaurio
     *
     * @param su
     * @return
     */
    private long codigoUsuario(SUser su) {
        Query q = getEntityManager().createNamedQuery("SUser.findByUsrLoginname");
        q.setParameter("usrLoginname", su.getUsrLoginname());
        List<SUser> lista = q.getResultList();
        if (lista.size() > 0) {
            return ((SUser) lista.get(0)).getUsrId();
        } else {
            return 0l;
        }
    }

    /**
     * Método para desaprobar la solcitud de contacto
     *
     * @param s
     * @param usuario
     * @param motivo
     * @throws MessagingException
     * @throws Exception
     */
    public void desaprobarSolicitud(Solicitud s, String usuario, String motivo) throws MessagingException, Exception {
        s.setUsuarioModificacionSoli(usuario);
        s.setFechaModificacionSoli(new Date());
        s.setMotivoSoli(motivo);
        s.setEstado(Estado.Desaprobado.name());
        if (s.getsUserList() != null) {
            for (int i = 0; i < s.getsUserList().size(); i++) {
                s.getsUserList().get(i).setUsrEnabled(false);
                s.getsUserList().get(i).setEstado(Estado.Inactivo.name());

            }
        }
        String plantilla = this.obtenerArchivos("/aisv/correo/plantillas/desaprobacionAisv.html");
        email = new UtilMail();
        email.setTo(s.getCorreoSoli() + ";" + correosSolicitud);
        email.setSubject("NOTIFICACIÓN: << Naportec - " + s.getNombreSoli() + " - Desaprobación de Solicitud>>");
        email.setContent(UtilHtml.importarDatosDesaprobacion(s, UtilMail.loadHTMLFile(plantilla), motivo));
        email.send();
        this.modificar(s);
    }

    /**
     * Método para anular la Solicitud de contacto
     *
     * @param s
     * @param usuario
     * @param motivo
     * @throws MessagingException
     * @throws Exception
     */
    public void anularSolicitud(Solicitud s, String usuario, String motivo) throws MessagingException, Exception {
        s.setUsuarioModificacionSoli(usuario);
        s.setFechaModificacionSoli(new Date());
        s.setMotivoSoli(motivo);
        s.setEstado(Estado.Anulado.name());
        if (s.getsUserList() != null) {
            for (int i = 0; i < s.getsUserList().size(); i++) {
                s.getsUserList().get(i).setUsrEnabled(false);
                s.getsUserList().get(i).setEstado(Estado.Inactivo.name());
            }
        }
        email = new UtilMail();
        email.setTo(correosSolicitud);
        email.setSubject("NOTIFICACIÓN: << Naportec - " + s.getNombreSoli() + " - Anulación de Solicitud>>");
        email.setContent("<h2>Se ha Anulado la Solicitud de: " + s.getNombreSoli() + "</h2>"
                + "<br/> <p><b>Motivo: </b>  " + motivo + "</p>");

        try {
            email.send();
        } catch (MessagingException ex) {
            throw new EJBException(ex.getMessage());
        }
        this.modificar(s);
    }

}
