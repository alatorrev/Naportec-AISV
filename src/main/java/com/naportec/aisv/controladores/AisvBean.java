package com.naportec.aisv.controladores;

import com.naportec.aisv.entidades.Transaccion;
import com.naportec.aisv.websocket.DespachoNotificationEndPoint;
import com.naportec.aisv.websocket.WebSocketUtil;
import com.naportec.utilidades.controladores.LazyDataModelAdvance;
import com.naportec.utilidades.controladores.Mensaje;
import com.naportec.utilidades.controladores.UtilAisvController;
import com.naportec.utilidades.enumeraciones.Estado;
import com.naportec.utilidades.logica.Filtro;
import com.naportec.utilidades.otros.Fechas;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.event.SelectEvent;

/**
 * Clase Controlador que nos permitirá manejar las Aprobaciones de ASIV
 *
 * @author Fernando
 */
public class AisvBean extends UtilAisvController implements Serializable {

    /**
     * Objeto que permite saber el tipo de carga si es I=Importación o
     * E=Exportaci+on
     */
    private String tipoCarga;
    private String ruta = "/aisv/reportes/reporteAisvImportCont.jasper";
    private int queueSize;

    public AisvBean() {
        super();
    }

    @PostConstruct
    @Override
    public void inicializar() {
        super.inicializarAprobacion();
        queueSize = DespachoNotificationEndPoint.getListaValores().size();
    }

    public void recibirNotificacion() {
        queueSize = DespachoNotificationEndPoint.getListaValores().size();
    }

    public void obtenerAisvfromQueue() {
        DespachoNotificationEndPoint.getListaValores().clear();
        queueSize = DespachoNotificationEndPoint.getListaValores().size();
    }

    public void filtrarPorPendiente() {
        this.getListaAisv().filtroEqual("ingresoRochoTrans", true);
        this.getListaAisv().filtroEqual("salidaRochoTrans", false);
    }

    public void verTodos() {
        super.inicializarAprobacion();
    }

    /**
     * Método para inicializar listado con carga liviana.
     */
    public void inicializarReportes() {
        this.setListaAisvAux(new LazyDataModelAdvance<Transaccion>(this.logicaTransaccion));
        this.getListaAisvAux().filtroEqual("codigoPrec.tipoPrec", "x");
        this.getListaAisvAux().filtroOrderBy("codigoTrans", "desc");
    }

    /**
     * Método para inicializar exportación de los Reportes.
     */
    public void prepExportar() {
        try {
            this.inicializarReporteDatos(null, this.getListaAisvAux().loadTotalList(), ruta);
        } catch (JRException ex) {
            Logger.getLogger(PanBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AisvBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * gestión documental buttons*
     */
    boolean aprobar = false, desaprobar = false;

    public void rowSelect(SelectEvent evt) throws ParseException {
        Transaccion t = (Transaccion) evt.getObject();
        switch (t.getEstado()) {
            case "Activo":
                if (t.getCodigo_pago().getEstado().equals("Cancelado")
                        && Fechas.FechaFormat(this.getTransaccion().getIngresoRochoTrans(), "yyyy-MM-dd").getTime() == Fechas.FechaFormat(new Date(), "yyyy-MM-dd").getTime()
                        && Fechas.FechaFormat(this.getTransaccion().getFechaRetiroTrans(), "yyyy-MM-dd").getTime() == Fechas.FechaFormat(new Date(), "yyyy-MM-dd").getTime()) {
                    this.aprobar = true;
                    this.desaprobar = false;
                } else {
                    this.aprobar = false;
                    this.desaprobar = false;
                }
                break;
            case "Aprobado":
                this.aprobar = false;
                this.desaprobar = false;
                break;
            case "Anulado":
                this.aprobar = false;
                this.desaprobar = false;
                break;
            case "Documental":
                this.aprobar = false;
                this.desaprobar = true;
                break;
        }
    }
    
    public String customStyle(Transaccion s){
        if((s.getEstado().equals("Activo")||s.getEstado().equals("noAprobado")) && s.getIngresoRochoTrans()!=null) return "rowStyleNaranja";
        if(s.getEstado().equals("Documental") && s.getSalidaRochoTrans()==null) return "rowStyleVerde";
        if(s.getEstado().equals("Documental") && s.getSalidaRochoTrans()!=null) return "rowStyleCeleste";
        if(s.getEstado().equals("Aprobado")) return "rowStyleAmarillo";
        return null;
    }

    public void rowunSelect() {
        aprobar = false;
        desaprobar = false;
    }

    public boolean isAprobar() {
        return aprobar;
    }

    public void setAprobar(boolean aprobar) {
        this.aprobar = aprobar;
    }

    public boolean isDesaprobar() {
        return desaprobar;
    }

    public void setDesaprobar(boolean desaprobar) {
        this.desaprobar = desaprobar;
    }

//    /**
//     * Este métod permite inicializar la exportación de un reporteque luego será
//     * exportado a aun formato PDF o EXCEL
//     */
//    public void prepExportar() {
//        try {
//            Conexion conexion = new Conexion();
//            Map parametros = new HashMap();
//            String sql = this.getListaAisvAux().getInfoLazyDataModel()[2].toString();
//            if (sql.contains("WHERE")) {
//                if (sql.contains("ORDER BY")) {
//                    parametros.put("where", sql.substring(sql.indexOf("WHERE"), sql.indexOf("ORDER BY")));
//                    parametros.put("orderby", sql.substring(sql.indexOf("ORDER BY"), sql.length()));
//                }else{
//                    parametros.put("where", sql.substring(sql.indexOf("WHERE"), sql.length()));
//                }
//            }else{
//                 parametros.put("where", sql);
//            }
//            this.inicializarReporte(parametros, null,this.ruta, conexion.conexion_Aisv());
//        } catch (JRException ex) {
//            Logger.getLogger(PanBean.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (Exception ex) {
//            Logger.getLogger(PanBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public void actualizarDatosExterno() {
        this.setListaAisvAux(new LazyDataModelAdvance<Transaccion>(this.logicaTransaccion));
        this.getListaAisvAux().setFiltros(new LinkedList<Filtro>());
        this.getListaAisvAux().filtroEqual("usrId", this.usuario);
        if (this.getTipoTrasaccion().equals("I")) {
            this.getListaAisvAux().filtroEqual("codigoPrec.tipoPrec", "I");
            this.getListaAisvAux().filtroOrderBy("codigoTrans", "desc");
            if (this.tipoCarga.equals("FCL/FCL")) {
                ruta = "/aisv/reportes/reporteAisvImportCont.jasper";
                this.getListaAisvAux().filtroEqual("codigoPrec.condicionContenedorPrec", this.tipoCarga);
            } else {
                ruta = "/aisv/reportes/reporteAisvImportSuel.jasper";
                if (this.tipoCarga.equals("LCL/LCL")) {
                    this.getListaAisvAux().filtroEqual("codigoPrec.condicionContenedorPrec", this.tipoCarga);
                } else {
                    this.getListaAisvAux().filtroSub("codigoPrec.condicionContenedorPrec", "FCL/LCL", "codigoPrec.condicionContenedorPrec", "LCL/FCL");
                }
            }
        } else {
            this.getListaAisvAux().filtroEqual("codigoPrec.tipoPrec", "E");
            this.getListaAisvAux().filtroOrderBy("codigoTrans", "desc");
            if (this.tipoCarga.equals("FCL/FCL")) {
                ruta = "/aisv/reportes/reporteAisvExportCont.jasper";
                this.getListaAisvAux().filtroEqual("codigoPrec.condicionContenedorPrec", this.tipoCarga);
            } else {
                ruta = "/aisv/reportes/reporteAisvExportSuel.jasper";
                if (this.tipoCarga.equals("LCL/LCL")) {
                    this.getListaAisvAux().filtroEqual("codigoPrec.condicionContenedorPrec", this.tipoCarga);
                } else {
                    this.getListaAisvAux().filtroSub("codigoPrec.condicionContenedorPrec", "FCL/LCL", "codigoPrec.condicionContenedorPrec", "LCL/FCL");
                }
            }
        }
    }

    public void actualizarDatos() {
        this.logicaTransaccion.setContexto(FacesContext.getCurrentInstance());
        this.setListaAisvAux(new LazyDataModelAdvance<Transaccion>(this.logicaTransaccion));
        this.getListaAisvAux().setFiltros(new LinkedList<Filtro>());
        if (this.getTipoTrasaccion().equals("I")) {
            this.getListaAisvAux().filtroEqual("codigoPrec.tipoPrec", "I");
            this.getListaAisvAux().filtroOrderBy("codigoTrans", "desc");
            if (this.tipoCarga == null) {
                this.tipoCarga = "";
                this.getListaAisvAux().filtroEqual("codigoPrec.condicionContenedorPrec", this.tipoCarga);
            } else if (this.tipoCarga.equals("FCL/FCL")) {
                ruta = "/aisv/reportes/reporteAisvImportCont.jasper";
                this.getListaAisvAux().filtroEqual("codigoPrec.condicionContenedorPrec", this.tipoCarga);
            } else {
                ruta = "/aisv/reportes/reporteAisvImportSuel.jasper";
                if (this.tipoCarga.equals("LCL/LCL")) {
                    this.getListaAisvAux().filtroEqual("codigoPrec.condicionContenedorPrec", this.tipoCarga);
                } else {
                    this.getListaAisvAux().filtroSub("codigoPrec.condicionContenedorPrec", "FCL/LCL", "codigoPrec.condicionContenedorPrec", "LCL/FCL");
                }
            }
        } else {
            this.getListaAisvAux().filtroEqual("codigoPrec.tipoPrec", "E");
            this.getListaAisvAux().filtroOrderBy("codigoTrans", "desc");
            if (this.tipoCarga == null) {
                this.tipoCarga = "";
                this.getListaAisvAux().filtroEqual("codigoPrec.condicionContenedorPrec", this.tipoCarga);
            } else if (this.tipoCarga.equals("FCL/FCL")) {
                ruta = "/aisv/reportes/reporteAisvExportCont.jasper";
                this.getListaAisvAux().filtroEqual("codigoPrec.condicionContenedorPrec", this.tipoCarga);
            } else {
                ruta = "/aisv/reportes/reporteAisvExportSuel.jasper";
                if (this.tipoCarga.equals("LCL/LCL")) {
                    this.getListaAisvAux().filtroEqual("codigoPrec.condicionContenedorPrec", this.tipoCarga);
                } else {
                    this.getListaAisvAux().filtroSub("codigoPrec.condicionContenedorPrec", "FCL/LCL", "codigoPrec.condicionContenedorPrec", "LCL/FCL");
                }
            }
        }

    }

    /**
     * Mñetodo para exportar los reportes a PDF
     *
     * @param evt
     */
    public void exportar(ActionEvent evt) {
        try {
            Map parametros = new HashMap();
            String rutaReporte = "";
            parametros.put("imagen", this.getClass().getResourceAsStream("/com/naportec/utilidades/img/nap.png"));
            if (getTransaccion().getCodigoPrec().getTipoPrec().equals("I")) {
                parametros.put("tipoTransaccion", "Importador");
                parametros.put("bookingBl", "BL");
                rutaReporte = "/aisv/reportes/reporteAisvImportacion.jasper";
            } else {
                parametros.put("tipoTransaccion", "Exportador");
                parametros.put("bookingBl", "Booking");

                rutaReporte = "/aisv/reportes/reporteAisvExportacion.jasper";
            }
            //COLOCAR AQUI CODIGO DE BARRA
            //---------------------------------------------------------------
            this.inicializarReporte(parametros, rutaReporte);
        } catch (JRException ex) {
            Logger.getLogger(ContenerizadoBean.class.getName()).log(Level.SEVERE, null, ex);
            Mensaje.ERROR_DIALOG("Reporte AISV", "Ha sucedido un Error" + ex.getMessage());
        }
    }

    /**
     * Método que Permite la aprobación Documental del AISV
     */
    public void aprobacionDocumental() {
        try {
            this.logicaTransaccion.setContexto(FacesContext.getCurrentInstance());
            this.logicaTransaccion.aprobacionDocumental(this.getTransaccion(), this.getCurrentloggeduser());
            Logger.getLogger(PanBean.class.getName()).log(Level.INFO, "PASO LA APROBACION DOCUMENTAL" + this.getTransaccion());
            WebSocketUtil.sendNotificationDocumental("aprobado");
            Mensaje.SUCESO_DIALOG("Aprobacion Documental", "Se ha aprobado Documentalmente");
        } catch (Exception ex) {
            Mensaje.ERROR_DIALOG("Aprobacion Documental", "Operacion Imposible " + ex.getMessage());
        }
    }

    /**
     * Método que permite la aprobación definitiva del AISV
     */
    public void aprobacionDefinitiva() {
        try {
            this.logicaTransaccion.setContexto(FacesContext.getCurrentInstance());
            this.logicaTransaccion.aprobacionDefinitiva(this.getTransaccion());
            exportar(null);
//            Mensaje.SUCESO_DIALOG("Aprobacion Definitiva", "Se ha aprobado Definitivamente");
        } catch (Exception ex) {
            Mensaje.ERROR_DIALOG("Aprobacion Definitiva", "Operacion Imposible");
        }
    }

    /**
     * Método que permite la modificación de los datos de EIR de un AISV
     */
    public void modificacionAisv() {
        try {
            this.logicaTransaccion.setContexto(FacesContext.getCurrentInstance());
            this.logicaTransaccion.modificar(this.getTransaccion(), Estado.edicion);
        } catch (Exception ex) {
            Mensaje.ERROR_DIALOG("Modirficar AISV", "Modificación de AISV");
        }
    }

    /**
     * Métodoque permite la desaprobación de un AISV
     */
    public void desaprobacion() {
        try {
            this.logicaTransaccion.setContexto(FacesContext.getCurrentInstance());
            this.logicaTransaccion.modificar(this.getTransaccion(), Estado.Activo);
            WebSocketUtil.sendNotificationDocumental("desaprobado");
            Mensaje.SUCESO_DIALOG("Desaprobación", "Se ha desaprobado este AISV");
        } catch (Exception ex) {
            Mensaje.ERROR_DIALOG("Desaprobación", "Operacion Imposible");
        }
    }

    /**
     * @return the tipoCarga
     */
    public String getTipoCarga() {
        return tipoCarga;
    }

    /**
     * @param tipoCarga the tipoCarga to set
     */
    public void setTipoCarga(String tipoCarga) {
        this.tipoCarga = tipoCarga;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }

}
