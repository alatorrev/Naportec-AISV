package com.naportec.aisv.controladores;

import com.naportec.aisv.entidades.Transaccion;
import com.naportec.aisv.logica.TransaccionFacade;
import com.naportec.utilidades.controladores.Mensaje;
import com.naportec.utilidades.controladores.UtilController;
import com.naportec.utilidades.enumeraciones.Estado;
import com.naportec.utilidades.logica.Conexion;
import com.naportec.utilidades.logica.Filtro;
import com.naportec.utilidades.otros.Aes;
import com.naportec.utilidades.otros.Fechas;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import net.sf.jasperreports.engine.JRException;

/**
 * Clase controlador que contiene las acciones PAN(Policía Antinarcótico)
 *
 * @author Fernando
 */
public class PanBean extends UtilController<Transaccion> implements Serializable {

    /**
     * Bean que contiene la lógica de negocios de Transaccion
     */
    @EJB
    private TransaccionFacade logicaTrans;
    /**
     * Fecha de Inicio usada para el filtrado por fechas en la vista.
     */
    private Date fechaInicio;
    /**
     * Fecha Final usada para el filtrado por fechas en la vista.
     */
    private Date fechaFinal;
    /**
     * Fecha de Creación o Aprobación usada para el filtrado por fechas en la
     * vista.
     */
    private String fechaAproCrea;

    public PanBean() {
        super(Transaccion.class);
    }

    /**
     * Inicializar información necesaria
     */
    @PostConstruct
    public void inicializar() {
        logicaTrans.setIpTransaccion((((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr()));
        this.setEntidadLogica(logicaTrans);
        this.fechaAproCrea = "AP";
        this.getListadoEntidad().filtroEqual("codigoPrec.tipoPrec", "E");
        this.getListadoEntidad().filtroEqual("codigoPrec.condicionContenedorPrec", "FCL/FCL");
        this.getListadoEntidad().filtroOrderBy("codigoTrans", "desc");
    }

    /**
     * Este método permite agregar filtros de búsqueda desde la vista al elegir
     * los colores en la vista que representan estados del AISV
     *
     * @throws Exception
     */
    public void agregarFiltro() throws Exception {
        this.getListadoEntidad().setFiltros(new LinkedList<Filtro>());
        this.getListadoEntidad().filtroEqual("codigoPrec.tipoPrec", "E");
        this.getListadoEntidad().filtroEqual("codigoPrec.condicionContenedorPrec", "FCL/FCL");
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String fil = String.valueOf(params.get("fil"));
        if (fil.split(":")[0].equals("estadopan")) {
            this.getListadoEntidad().filtroEqual("estadoPan", fil.split(":")[1]);
            agregarFiltroFechas();
        } else if (fil.split(":")[1].toString().toUpperCase().trim().equals("TODOS")) {
            this.getListadoEntidad().setFiltros(new LinkedList<Filtro>());
            this.getListadoEntidad().filtroEqual("codigoPrec.tipoPrec", "E");
            this.getListadoEntidad().filtroEqual("codigoPrec.condicionContenedorPrec", "FCL/FCL");
        } else {
            this.getListadoEntidad().filtroEqual("estado", fil.split(":")[1]);
            agregarFiltroFechas();
        }
        this.getListadoEntidad().filtroOrderBy("codigoTrans", "desc");
    }

    /**
     * Este método permite agregar el filtrado de fechas desde la vista
     */
    public void agregarFiltroFechas() {
        this.getListadoEntidad().filtroEqual("codigoPrec.tipoPrec", "E");
        this.getListadoEntidad().filtroEqual("codigoPrec.condicionContenedorPrec", "FCL/FCL");
        this.getListadoEntidad().removeFiltro("fechaEirTrans");
        this.getListadoEntidad().removeFiltro("fechaCreacinTrans");
        if (this.fechaAproCrea.trim().equals("AP")) {
            this.fechaFinal = Fechas.sumarDiasFecha(fechaFinal, 1);
            this.getListadoEntidad().filtroBetweenFecha("fechaEirTrans", fechaInicio, "fechaFinal", fechaFinal);
        }
        if (this.fechaAproCrea.trim().equals("CR")) {
            this.fechaFinal = Fechas.sumarDiasFecha(fechaFinal, 1);
            this.getListadoEntidad().filtroBetweenFecha("fechaCreacinTrans", fechaInicio, "fechaFinal", fechaFinal);
        }
    }

    /**
     * Este métod permite inicializar la exportación de un reporteque luego será
     * exportado a aun formato PDF o EXCEL
     */
    public void prepExportar() {
        try {
            Conexion conexion = new Conexion();
            Map parametros = new HashMap();
            String sql = this.getListadoEntidad().getInfoLazyDataModel()[2].toString();
            if(sql.contains("aisv_itinerario t2")){
                sql=sql.replace("aisv_itinerario t2", "aisv_itinerario t3");
                sql=sql.replace("t2", "t3");
            }
          
            if (sql.contains("WHERE")) {
                if (sql.contains("ORDER BY")) {
                    parametros.put("where", sql.substring(sql.indexOf("WHERE"), sql.indexOf("ORDER BY")));
                    parametros.put("orderby", sql.substring(sql.indexOf("ORDER BY"), sql.length()));
                }else{
                    parametros.put("where", sql.substring(sql.indexOf("WHERE"), sql.length()));
                }
            }else{
                 parametros.put("where", sql);
            }
            this.inicializarReporte(parametros, null, "/aisv/reportes/reportePanNuevo.jasper", conexion.conexion_Aisv());
            conexion.cerrarConexion();
        } catch (JRException ex) {
            Logger.getLogger(PanBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PanBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void liberaReporte(){
        this.setJasper(null);
    }

    /**
     * Este método permite realizar el bloqueo PAN de un AISV
     */
    public void bloqueoAntinarcoticos() {
        int error = 0;
        String errorMessage = "";
        try {
            this.logicaTrans.setContexto(FacesContext.getCurrentInstance());
            this.logicaTrans.bloqueoPan(this.getEntidad(), Estado.bloqueoPan, this.getCurrentloggeduser());
        } catch (EJBException ex) {
            error = 1;
            errorMessage = ex.getMessage();
        }
        if (error == 0) {
            this.logicaTrans.setContexto(FacesContext.getCurrentInstance());
            try {
                this.logicaTrans.notificacionBloqueoPan(this.getEntidad());
                Mensaje.SUCESO_DIALOG("Bloqueo PAN", "Se ha bloqueado por PAN");
            } catch (MessagingException ex) {
                this.logicaTrans.problemaBloqueoPan(this.getEntidad(), this.getCurrentloggeduser());
                Mensaje.ERROR_DIALOG("Bloqueo PAN", "No se ha podido enviar la Notificación:" + ex.getMessage());
            } catch (IOException ex) {
                Mensaje.ERROR_DIALOG("Bloqueo PAN", "No se ha encontrado la plantilla de Correo:" + ex.getMessage());
            } catch (ParseException ex) {
                Mensaje.ERROR_DIALOG("Bloqueo PAN", "No se ha podido convertir un Resultado");
            }
        } else {
            Mensaje.ERROR_DIALOG("Bloqueo PAN", "Problema de Comunicación con Base de Datos:" + errorMessage);
        }
    }

    /**
     * Este método permite el bloqueo Preembarque de un AISV
     */
    public void bloqueoPrembarque() {
        try {
            this.logicaTrans.setContexto(FacesContext.getCurrentInstance());
            this.logicaTrans.bloqueoPreembarque(this.getEntidad(), Estado.bloqueoPreembarque, this.getCurrentloggeduser());
            Mensaje.SUCESO_DIALOG("Bloqueo Preembarque", "Se ha bloqueado por PAN Preembarque");
        } catch (Exception ex) {
            Mensaje.ERROR_DIALOG("Bloqueo Preembarque", "Operacion Imposible" + ex.getMessage());
        }
    }

    /**
     * Este método permite el Desbloqueo Preembarque de un AISV
     */
    public void desBloqueoPrembarque() {
        try {
            this.logicaTrans.setContexto(FacesContext.getCurrentInstance());
            this.logicaTrans.desBloqueoPreembarque(this.getEntidad(), Estado.DesbloqueoPreembarque, this.getCurrentloggeduser());
            Mensaje.SUCESO_DIALOG("Desbloqueo Preembarque", "Se ha Desbloqueado correctamente por Preembarque");
        } catch (Exception ex) {
            Mensaje.ERROR_DIALOG("Desbloqueo Preembarque", "Operacion Imposible " + ex.getMessage());
        }
    }

    /**
     * Este método permite el Desbloqueo de un AISV bloqueado
     */
    public void desBloqueoAntinarcoticos() {
        try {
            this.logicaTrans.setContexto(FacesContext.getCurrentInstance());
            this.logicaTrans.desBloqueoPan(this.getEntidad(), Estado.DesbloqueoPan, this.getCurrentloggeduser());
            Mensaje.SUCESO_DIALOG("Desbloqueo PAN", "Se ha desbloqueado por PAN");
        } catch (Exception ex) {
            Mensaje.ERROR_DIALOG("Desbloqueo PAN", "Operacion Imposible " + ex.getMessage());
        }
    }

    /**
     * Método para exportar un AISV a PDFy mostrarlo en la Vista
     *
     * @param evt
     */
    public void exportar(ActionEvent evt) {
        try {
            Map parametros = new HashMap();
            String rutaReporte = "";
            parametros.put("tipoTransaccion", "Exportador");
            parametros.put("bookingBl", "Booking");
            StringTokenizer tk = new StringTokenizer(getEntidad().getDaesTrans(), "/");
            parametros.put("numeroDae", "(" + (tk.countTokens() - 1) + ")");
            if (getEntidad().getCodigoPrec().getCondicionContenedorPrec().equals("FCL/FCL")) {
                rutaReporte = "/aisv/reportes/reporteAisvExportacion.jasper";
            } else {
                rutaReporte = "/aisv/reportes/reporteAisvExportacionSueltaConsol.jasper";
            }
            String datoBarra = Aes.encryptData(this.getEntidad().toString());
            String datoBarraDos = Aes.encryptData(this.getEntidad().getCodigoTrans() + "");
            System.out.println("" + Aes.encryptData(this.getEntidad().toString()));
            System.out.println("" + Aes.encryptData(this.getEntidad().getCodigoTrans() + ""));
            parametros.put("codigoBarraInf", datoBarra.substring(0, datoBarra.length() - 2));
            parametros.put("imagen", this.getClass().getResourceAsStream("/com/naportec/utilidades/img/nap.png"));
            parametros.put("primercodigo", datoBarraDos.substring(0, datoBarraDos.length() - 2));
            //COLOCAR AQUI CODIGO DE BARRA
            //---------------------------------------------------------------
            List<Transaccion> listadoObjeto = new LinkedList<>();
            listadoObjeto.add(getEntidad());
            this.inicializarReporte(parametros, listadoObjeto, rutaReporte, 0);
        } catch (JRException ex) {
            Logger.getLogger(ContenerizadoBean.class.getName()).log(Level.SEVERE, null, ex);
            Mensaje.ERROR_DIALOG("Reporte AISV", "Ha sucedido un Error" + ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(ContenerizadoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        if (fechaInicio == null) {
            fechaInicio = new Date();
        }
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFinal
     */
    public Date getFechaFinal() {
        if (fechaFinal == null) {
            fechaFinal = new Date();
        }
        return fechaFinal;
    }

    /**
     * @param fechaFinal the fechaFinal to set
     */
    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    /**
     * @return the fechaAproCrea
     */
    public String getFechaAproCrea() {
        return fechaAproCrea;
    }

    /**
     * @param fechaAproCrea the fechaAproCrea to set
     */
    public void setFechaAproCrea(String fechaAproCrea) {
        this.fechaAproCrea = fechaAproCrea;
    }
}
