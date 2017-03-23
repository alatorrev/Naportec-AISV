package com.naportec.utilidades.controladores;

import com.naportec.aisv.entidades.Dae;
import com.naportec.aisv.entidades.Precarga;
import com.naportec.aisv.entidades.Puerto;
import com.naportec.aisv.entidades.Transaccion;
import com.naportec.aisv.logica.ConsolidadoraFacade;
import com.naportec.aisv.logica.PrecargaFacade;
import com.naportec.aisv.logica.PuertoFacade;
import com.naportec.aisv.logica.TransaccionFacade;
import com.naportec.seguridad.entidades.SUser;
import com.naportec.seguridad.logica.SUserFacade;
import com.naportec.utilidades.enumeraciones.Estado;
import com.naportec.utilidades.logica.Filtro;
import com.naportec.utilidades.otros.Fechas;
import com.naportec.utilidades.validaciones.Validaciones;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Clase abstracta que nos permite manejar varios aspectos similares para varios
 * controladores como lo son: ConsolidadBean, ContenerizadoBean y SueltaBean
 *
 * @author Fernando
 */
public abstract class UtilAisvController implements Serializable {

    private String tipoTrasaccion;
    //--------------------------------------------------------
    private Puerto origen;
    private Puerto trasbordo;
    private Puerto destino;
    private Puerto destinoFinal;
    //--------------------------------------------------------
    protected LazyDataModelAdvance<Transaccion> listaAisv;
    private LazyDataModelAdvance<Transaccion> listaAisvAux;
    protected LazyDataModelAdvance<Precarga> listaImportacion;
    protected LazyDModelPrecargaExportacion listaExportacion;
    private Precarga seleccionadoEksat;
    //-------------------------------------------------
    @EJB
    protected PrecargaFacade logTran;
    @EJB
    protected TransaccionFacade logicaTransaccion;
    @EJB
    protected SUserFacade userLogica;
    @EJB
    protected PuertoFacade puertoLogica;
    @EJB
    protected ConsolidadoraFacade consolidadoraLogica;

    protected Transaccion transaccion;
    protected List<Dae> daesListado;
    private Dae dae;
    private JasperPrint jasper;
    protected String rutaReporteServidor;
    //-------------------------------------------------
    protected String completado;
    protected SUser usuario;
    private String exportador, rucExportador;

    private String currentloggeduser;

    public UtilAisvController() {
        //get logged in username
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        this.setCurrentloggeduser(auth.getName());
    }

    /**
     * Método para obtener una ruta completa desdeuna ruta de archivo relativa
     *
     * @param archivo
     * @return
     */
    public String obtenerRutas(String archivo) {
        String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(archivo);
        return reportPath;
    }

    /**
     * Iniializacion del reporte PDF cuando se crea un nuevo ASIV
     *
     * @param parametros
     * @param ruta
     * @throws JRException
     */
    protected void inicializarReporte(Map parametros, String ruta) throws JRException {
        List<Transaccion> listadoObjetos = new LinkedList<>();
        listadoObjetos.add(transaccion);
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listadoObjetos);
        String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(ruta);
        String serverPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/temporal/tmp.txt");
        jasper = JasperFillManager.fillReport(reportPath, parametros, beanCollectionDataSource);
        this.rutaReporteServidor = "/temporal/tmp.txt" + transaccion.getCodigoTrans() + ".pdf";
        JasperExportManager.exportReportToPdfFile(jasper, serverPath + transaccion.getCodigoTrans() + ".pdf");
        //agregado
        jasper = null;
        beanCollectionDataSource = null;
        listadoObjetos = null;
    }

    /**
     * Iniializacion del reporte PDF cuando se crea un nuevo ASIV desde un
     * objeto Transaccion
     *
     * @param parametros
     * @param ruta
     * @param t
     * @throws JRException
     */
    protected void inicializarReporteAisv(Map parametros, String ruta, Transaccion t) throws JRException {
        List<Transaccion> listadoObjetos = new LinkedList<>();
        listadoObjetos.add(t);
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listadoObjetos);
        String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(ruta);
        String serverPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/temporal/tmp.txt");
        jasper = JasperFillManager.fillReport(reportPath, parametros, beanCollectionDataSource);
        this.rutaReporteServidor = "/temporal/tmp.txt" + t.getCodigoTrans() + ".pdf";
        JasperExportManager.exportReportToPdfFile(jasper, serverPath + t.getCodigoTrans() + ".pdf");
        jasper = null;
        beanCollectionDataSource = null;
        listadoObjetos = null;
    }

    /**
     * Iniializacion del reportes de listado de Datos para que luego puedan ser
     * exportados a EXCEL o PDF
     *
     * @param parametros
     * @param listadoObjetos
     * @param ruta
     * @throws JRException
     */
    protected void inicializarReporteDatos(Map parametros, List<Transaccion> listadoObjetos, String ruta) throws JRException {
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listadoObjetos);
        String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(ruta);
        jasper = JasperFillManager.fillReport(reportPath, parametros, beanCollectionDataSource);
    }

    protected void inicializarReporte(Map parametros, List<Transaccion> listadoObjetos, String ruta, Connection con) throws JRException {
//        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listadoObjetos);
        String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(ruta);
//        jasper = JasperFillManager.fillReport(reportPath, parametros, beanCollectionDataSource);

        jasper = JasperFillManager.fillReport(reportPath, parametros, con);
    }

    /**
     * Método para exportar a PDF
     *
     * @param actionEvent
     * @throws JRException
     * @throws IOException
     */
    public void reportePDF(ActionEvent actionEvent) throws JRException, IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.pdf");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasper, servletOutputStream);
        FacesContext.getCurrentInstance().responseComplete();
    }

    /**
     * Método para exportar a EXCEL
     *
     * @param actionEvent
     * @throws JRException
     * @throws IOException
     */
    public void reporteEXCEL(ActionEvent actionEvent) throws JRException, IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.xlsx");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JRXlsxExporter docxExporter = new JRXlsxExporter();
        docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasper);
        docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
        docxExporter.exportReport();
        FacesContext.getCurrentInstance().responseComplete();
    }

    /**
     * Método para borrar archivos temporales
     */
    public void borrarArchivo() {
        File f = new File(this.rutaReporteServidor);
        if (f.exists()) {
            f.delete();
        } else {

        }
        Mensaje.ERROR("que pasa" + f.getAbsolutePath());
    }

    /**
     * Método para inicializar datos y listados para mostrar en las vistas
     */
    public void inicializar() {
        logicaTransaccion.setIpTransaccion((((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr()));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        usuario = userLogica.buscarUsuario(auth.getName());
        listaImportacion = new LazyDataModelAdvance<>(logTran);
        listaImportacion.filtroEqual("tipoPrec", "I");
        listaImportacion.filtroOrderBy("codigoPrec", "desc");
        listaImportacion.filtroEqual("habilitado", true);
        if (usuario.getCodigoSoli().getTipoContactoSoli().equals("IE")) {
            listaImportacion.filtroEqual("impExpIdPrec", usuario.getCodigoSoli().getIdentificacionSoli());
        }
        listaExportacion = new LazyDModelPrecargaExportacion(logTran);
        listaExportacion.setTipoPerfil(usuario.getCodigoSoli().getTipoContactoSoli());
        listaExportacion.setRucExportador(usuario.getCodigoSoli().getIdentificacionSoli());
//        listaExportacion.filtroEqual("tipoPrec", "E");
//        listaExportacion.filtroOrderBy("codigoPrec", "desc");
//        if (usuario.getCodigoSoli().getTipoContactoSoli().equals("IE")) {
//            listaExportacion.filtroEqual("idLineaPrec.perfilamientoNavi", true);
//            listaExportacion.filtroSub("impExpIdPrec", usuario.getCodigoSoli().getIdentificacionSoli(),
//                    "impExpIdPrec",consolidadoraLogica.getRucConsolidadora(usuario.getCodigoSoli().getIdentificacionSoli()));
////            listaExportacion.filtroUnion();
////            listaExportacion.filtroEqualUnion("idLineaPrec.perfilamientoNavi", false);
//        }
//        listaExportacion.filtroMayorEqual("idItinerarioPrec.fechaZarpeItin", new java.sql.Date(new Date().getTime()));

        //---------------------------------------------------------------------------------------------------
        listaAisv = new LazyDataModelAdvance<>(logicaTransaccion);
        if (usuario.getCodigoSoli().getTipoContactoSoli().equals("IE")) {
            listaAisv.filtroEqual("usrId.codigoSoli", usuario.getCodigoSoli());
        } else if (usuario.getCodigoSoli().getTipoContactoSoli().equals("AA")) {
            listaAisv.filtroEqual("usrId", usuario);
        }
        listaAisv.filtroEqual("codigoPrec.tipoPrec", this.getTipoTrasaccion());
        listaAisv.filtroOrderBy("codigoTrans", "desc");
    }

    /**
     * Método para inicializar datos y listados en al vista de aprobación
     */
    public void inicializarAprobacion() {
        logicaTransaccion.setIpTransaccion((((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr()));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        usuario = userLogica.buscarUsuario(auth.getName());
        listaAisv = new LazyDataModelAdvance<>(logicaTransaccion);
        listaAisv.filtroEqual("codigoPrec.tipoPrec", this.getTipoTrasaccion());
//        listaAisv.filtroEqual("estado", Estado.Activo.name());
        listaAisv.filtroOrderBy("ingresoRochoTrans", "desc");
        listaAisv.filtroOrderBy("codigoTrans", "desc");
        listaAisvAux = new LazyDataModelAdvance<>(logicaTransaccion);
        mostrarDatosTipoAux();
        listaAisvAux.filtroOrderBy("ingresoRochoTrans", "desc");
        listaAisvAux.filtroOrderBy("codigoTrans", "desc");
        
    }

    /**
     * Método para inicializar datos y listados en modificacion de datos de AISV
     * No se esta usando esa vista
     */
    public void inicializarModificacion() {
        logicaTransaccion.setIpTransaccion((((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr()));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        usuario = userLogica.buscarUsuario(auth.getName());
        listaAisv = new LazyDataModelAdvance<>(logicaTransaccion);
        listaAisv.filtroEqual("codigoPrec.tipoPrec", this.getTipoTrasaccion());
        listaAisv.filtroEqual("estado", Estado.Aprobado.name());
        listaAisv.filtroOrderBy("codigoTrans", "desc");
        listaAisvAux = new LazyDataModelAdvance<>(logicaTransaccion);
        mostrarDatosTipoAux();
        listaAisvAux.filtroOrderBy("codigoTrans", "desc");
    }

    /**
     * Método para incializar la vista el listado de Bookings
     */
    public void inicializarListadoBookings() {
        logicaTransaccion.setIpTransaccion((((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr()));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        usuario = userLogica.buscarUsuario(auth.getName());
        listaAisvAux = new LazyDataModelAdvance<>(logicaTransaccion);
        listaAisvAux.filtroEqual("codigoPrec.tipoPrec", "E");
        listaAisvAux.filtroOrderBy("codigoTrans", "desc");
    }

    /**
     * Mostrar Datos Auxiliares
     */
    public void mostrarDatosTipoAux() {
        List<Filtro> filtros = listaAisvAux.getFiltros();
        for (int i = 0; i < filtros.size(); i++) {
            if (filtros.get(i).getKey1().equals("codigoPrec.tipoPrec")) {
                listaAisvAux.getFiltros().remove(i);
            }
            if (filtros.get(i).getKey1().equals("estado")) {
                listaAisvAux.getFiltros().remove(i);
            }
        }
        listaAisvAux.filtroEqual("codigoPrec.tipoPrec", this.getTipoTrasaccion());

        if (this.getTipoTrasaccion().equals("E")) {
            listaAisvAux.filtroSub("estado", Estado.Activo.name(), "estado", Estado.Aprobado.name());

        } else {
            listaAisvAux.filtroSub("estado", Estado.Documental.name(), "estado", Estado.Aprobado.name());
        }
    }

    public void mostrarDatosTipo() {
        List<Filtro> filtros = listaAisv.getFiltros();
        for (int i = 0; i < filtros.size(); i++) {
            if (filtros.get(i).getKey1().equals("codigoPrec.tipoPrec")) {
                listaAisv.getFiltros().remove(i);
            }
        }
        listaAisv.filtroEqual("codigoPrec.tipoPrec", this.getTipoTrasaccion());
        listaAisv.setRowIndex(0);
    }

    /**
     * Método que inicializa datos necesarios para poder crear un nuevo AISV
     *
     * @param evt
     */
    public void prepararAisv(ActionEvent evt) {
        this.transaccion = new Transaccion();
        this.transaccion.setCodigoPrec(seleccionadoEksat);
        this.transaccion.setContenedorTrans(seleccionadoEksat.getContenedorPrec());
        this.transaccion.setSelloUnoTrans(seleccionadoEksat.getSello1Prec());
        this.transaccion.setSelloDosTrans(seleccionadoEksat.getSello2Prec());
        this.transaccion.setSelloTresTrans(seleccionadoEksat.getSello3Prec());
        this.transaccion.setPuertoDestinoTrans(seleccionadoEksat.getPtoDestinoPrec());
        this.transaccion.setPuertoOrigenTrans(seleccionadoEksat.getPtoOrigenPrec());
        this.transaccion.setPuertoTrasbordoTrans(seleccionadoEksat.getPtoTransbordoPrec());
        this.setExportador(seleccionadoEksat.getImportadorPrec());
        this.setRucExportador(seleccionadoEksat.getImpExpIdPrec());

        this.setOrigen(puertoLogica.buscarPorNombre(seleccionadoEksat.getPtoOrigenPrec()));
        this.setDestino(puertoLogica.buscarPorNombre(seleccionadoEksat.getPtoDestinoPrec()));
        this.setTrasbordo(puertoLogica.buscarPorNombre(seleccionadoEksat.getPtoTransbordoPrec()));
        this.destinoFinal = new Puerto();
        this.daesListado = new LinkedList<Dae>();
    }

    /**
     * Método que sirve para la validción de datos de un AISV
     *
     * @return
     */
    protected String validacionDatos() throws ParseException {
        //String mensajes = "";
        StringBuilder sb = new StringBuilder();
        if (this.getTransaccion().getCodigoPrec().getCondicionContenedorPrec().equals("FCL/FCL")) {
            if (this.getTransaccion().getContenedorTrans() == null) {
                this.getTransaccion().setContenedorTrans("");
            } else {
                String conty = this.getTransaccion().getContenedorTrans();
                String dat[] = conty.split("-");
                this.getTransaccion().setContenedorTrans(dat[0].trim());
            }
            if (this.getTransaccion().getContenedorTrans().length() == 0) {
                sb.append("El contenedor es necesario<br/>");
                //mensajes += "El contenedor es necesario<br/>";
            }
        }
        if (this.getTransaccion().getNombreTrasnportistaTrans() != null) {
            if (this.getTransaccion().getNombreTrasnportistaTrans().length() == 0) {
                sb.append("El Nombre del Transportista es Necesario<br/>");
                //mensajes += "El Nombre del Transportista es Necesario<br/>";
            }
        }
        if (this.getTransaccion().getCedulaTrasnportistaTrans().length() > 0) {
            this.getTransaccion().setCedulaTrasnportistaTrans(this.getTransaccion().getCedulaTrasnportistaTrans().trim());
            if (this.getTransaccion().getDocumentoIdentificador().equals("Cédula")) {
                if (Validaciones.validarCedula(this.getTransaccion().getCedulaTrasnportistaTrans()) != Validaciones.IDENTIFICACION_OK) {
                    sb.append("La cédula del Transportista es incorrecta<br/>");
                    //mensajes += "La cédula del Transportista es incorrecta<br/>";
                }
            }
            String bloqueo = "";
            try {
                bloqueo = logicaTransaccion.estadoTransportista(this.getTransaccion().getCedulaTrasnportistaTrans());
            } catch (Exception ex) {
                sb.append("Ha sucedido un error al tratar de verificar al Transportista: ").append(ex.getMessage()).append(".<br/>");
                //mensajes += "Ha sucedido un error al tratar de verificar al Transportista" + ex.getMessage() + ".<br/>";
            }
            if (bloqueo.trim().toUpperCase().equals("SUSPENDIDO") || bloqueo.trim().toUpperCase().equals("BLOQUEADO")) {
                sb.append("El transportista está ").append(bloqueo).append(". Por favor acérquese al área de Seguridad<br/>");
                //mensajes += "El transportista está " + bloqueo + ". Por favor acérquese al área de Seguridad<br/>";
            } else if (bloqueo.trim().equals("NOREGISTRADO")) {
                sb.append("El transportista no está registrado. Por favor acérquese al área de Seguridad<br/>");
                //mensajes += "El transportista no está registrado. Por favor acérquese al área de Seguridad<br/>";
            }
        } else {
            sb.append("Por Favor Coloque la cédula del Transportista<br/>");
            //mensajes += "Por Favor Coloque la cédula del Transportista<br/>";
        }
        if (this.getTransaccion().getPlacaTrasnportistaTrans() != null) {
            if (this.getTransaccion().getPlacaTrasnportistaTrans().trim().length() == 0) {
                sb.append("La Placa del Vehículo es necesaria<br/>");
                //mensajes += "La Placa del Vehículo es necesaria<br/>";
            }
            for (char dato : this.getTransaccion().getPlacaTrasnportistaTrans().trim().toCharArray()) {
                if (!Character.isLetterOrDigit(dato)) {
                    sb.append("La Placa del Vehículo no debe tener (- , /, espacios en blanco) <br/>");
                    break;
                }
            }
            if (this.getTransaccion().getPlacaTrasnportistaTrans().trim().length() == 0) {
                sb.append("La Placa del Vehículo es necesaria<br/>");
                //mensajes += "La Placa del Vehículo es necesaria<br/>";
            }
        }
        if (this.getTransaccion().getCodigoPrec().getTipoPrec().equals("E")) {
            if (this.getTransaccion().getCodigoPrec().getCondicionContenedorPrec().equals("FCL/FCL")) {
                if (this.getTransaccion().getSelloUnoTrans() == null) {
                    this.getTransaccion().setSelloUnoTrans("");
                }
                if (this.getTransaccion().getSelloUnoTrans().length() == 0) {
                    sb.append("El Sello de Naviera es necesario<br/>");
                    //mensajes += "El Sello de Naviera es necesario<br/>";
                }
                if (this.getTransaccion().getFechaSalidaTrans() == null) {
                    sb.append("La Fecha de Salida de Planta es obligatoria<br/>");
                    //mensajes += "La Fecha de Salida de Planta es obligatoria<br/>";
                }
                if (this.getTransaccion().getCiudadPlantaTrans() == null || this.getTransaccion().getCiudadPlantaTrans().trim().isEmpty()) {
                    sb.append("La Ciudad de Planta es Obligatoria<br/>");
                    //mensajes += "La Ciudad de Planta es Obligatoria<br/>";
                }
                if (this.getTransaccion().getDireccionPlantaTrans() == null || this.getTransaccion().getDireccionPlantaTrans().trim().isEmpty()) {
                    sb.append("La Dirección de Planta es Obligatoria<br/>");
                    //mensajes += "La Dirección de Planta es Obligatoria<br/>";
                }
            }

            if (this.getTransaccion().getCupoCargaTrans() == true) {
                if (this.getTransaccion().getNumeroCajasTrans() == null) {
                    sb.append("El Número de Cajas es Necesario<br/>");
                    //mensajes += "El Número de Cajas es Necesario<br/>";
                } else if (this.getTransaccion().getNumeroCajasTrans() <= 0) {
                    sb.append("El Número de Cajas es Necesario<br/>");
                    //mensajes += "El Número de Cajas es Necesario<br/>";
                }
            }
            if (this.getDaesListado().size() <= 0) {
                sb.append("Por favor Ingrese los datos DAE<br/>");
                //mensajes += "Por favor Ingrese los datos DAE<br/>";
            }
            if (this.getTransaccion().getCodigoPrec().getTipoPrec().equals("E")) {
                if (this.getTransaccion().getGuiaRemisionTrans().trim().length() <= 0) {
                    sb.append("La guía de Remisión es Obligatoria<br/>");
                    //mensajes += "La guía de Remisión es Obligatoria<br/>";
                }
            }

            if (this.getTransaccion().getPuertoOrigenTrans() == null) {
                sb.append("El Puerto de Orígen es obligatorio<br/>");
                //mensajes += "El Puerto de Orígen es obligatorio<br/>";
            }
            if (this.getTransaccion().getPuertoDestinoTrans() == null) {
                sb.append("El Puerto de destino es Obligatorio<br/>");
                //mensajes += "El Puerto de destino es Obligatorio<br/>";
            }

        } else {
            if (this.getTransaccion().getNombreRetiraTrans() != null) {
                if (this.getTransaccion().getNombreRetiraTrans().length() == 0) {
                    sb.append("El Nombre del que Retira es Necesario<br/>");
                    //mensajes += "El Nombre del que Retira es Necesario<br/>";
                }
            }
            if (this.getTransaccion().getCedulaRetiraTrans().trim().length() > 0) {
                if (Validaciones.validarCedula(this.getTransaccion().getCedulaRetiraTrans()) != Validaciones.IDENTIFICACION_OK) {
                    sb.append("La cédula de quién Retira no está correcta<br/>");
                    //mensajes += "La cédula de quién Retira no está correcta<br/>";
                }
                try {
                    String prohibido = logicaTransaccion.estadoPersonaRetira(this.getTransaccion().getCedulaRetiraTrans().trim());
                    if (prohibido.trim().toUpperCase().equals("PROHIBIDO")) {
                        sb.append("La persona delegada al retiro esta bloqueada. Por favor acérquese a seguridad física<br/>");
                        //mensajes += "La persona delegada al retiro esta bloqueada. Por favor acérquese a seguridad física<br/>";
                    }
                } catch (Exception ex) {
                    sb.append("Ha sucedido un error al tratar de comprobar el estado de la persona que retira: ").append(ex.getMessage()).append("<br/>");
                    //mensajes += "Ha sucedido un error al tratar de comprobar el estado de la persona que retira"+ex.getMessage()+"<br/>";
                }
            } else {
                sb.append("Por Favor Coloque la cédula del que Retira<br/>");
                //mensajes += "Por Favor Coloque la cédula del que Retira<br/>";
            }
        }
        if (this.getTransaccion().getCodigoPrec().getCondicionContenedorPrec().equals("FCL/LCL") || this.getTransaccion().getCodigoPrec().getCondicionContenedorPrec().equals("LCL/FCL")) {
            if (this.getTransaccion().getNumeroCajasTrans() == null) {
                sb.append("El Número de Cajas es Necesario<br/>");
                //mensajes += "El Número de Cajas es Necesario<br/>";
            } else if (this.getTransaccion().getNumeroCajasTrans() <= 0) {
                sb.append("El Número de Cajas es Necesario<br/>");
                //mensajes += "El Número de Cajas es Necesario<br/>";
            }
        }

        if (this.getTransaccion().getCodigo_pago().getNumeroPago().length() < 1) {
            sb.append("El número de factura es obligatorio <br/>");
        }

        if (Fechas.FechaFormat(this.getTransaccion().getFechaRetiroTrans(),"yyyy-MM-dd").getTime() < 
                Fechas.FechaFormat(new Date(),"yyyy-MM-dd").getTime()) {
            sb.append("La fecha de retiro no puede ser anterior a la actual <br/>");
        }
        
        if(logicaTransaccion.facturaValida(this.getTransaccion().getCodigo_pago().getNumeroPago())==null){
            sb.append("La factura no es válida <br/>");
        }
        
        
        if (sb.length() > 1) {
            return sb.toString();
        } else {
            return null;
        }
    }

    /**
     * Validacion de Despacho
     *
     * @return
     */
//    protected String validacionDespacho() {
//        StringBuilder sb = new StringBuilder();
//        if (this.getTransaccion().getCodigoPrec().getTipoPrec().equals("I")) {
//            if (this.getTransaccion().getFechaRetiroTrans() != null) {
//                Date fechaActual = new Date();
//                if (this.getTransaccion().getFechaRetiroTrans().getTime() < fechaActual.getTime()) {
//                    sb.append("La Fecha de retiro debe ser mayor o igual a la Fecha Actual<br/>");
//                }
//            } else {
//                sb.append("La Fecha de retiro es Requerida<br/>");
//            }
//            if (this.getTransaccion().getNumFacturaTrans() != null) {
//                if (pagosLogica.existePago(this.getTransaccion().getNumFacturaTrans()) == false) {
//                    sb.append("El número de Factura no existe o no está registrada en el Sistema<br/>");
//                }
//            } else {
//                 sb.append("El número de Factura es Requerido<br/>");
//            }
//        }
//        return sb.toString();
//    }
    /**
     * Método para anular un AISV
     */
    public void anularAisv() {
        try {
            if (this.getTransaccion().getCodigoPrec().getTipoPrec().equals("I")) {
                transaccion.setHabilitadoTrans(true);
                transaccion.getCodigoPrec().setHabilitado(true);
            }
            this.transaccion.setEstado(Estado.Anulado.name());
            this.logicaTransaccion.eliminar(transaccion);
            Mensaje.SUCESO_DIALOG("Anulacion AISV", "Se ha Anulado correctamente el AISV");
            transaccion = new Transaccion();
        } catch (Exception ex) {
            Mensaje.ERROR_DIALOG("Anulacion AISV", "Ha sucedido un error en la Anulacion del AISV" + ex.getMessage() + transaccion.getNombreTrasnportistaTrans());
        }
    }

    /**
     * Método para guardar uun AISV
     *
     * @throws Exception
     */
    public void guardarAISV() throws Exception {
        try {
            transaccion.setEstado(Estado.Activo.name());
            transaccion.setUsrId(usuario);
            transaccion.setFechaCreacinTrans(new Date());
            if (transaccion.getPlacaTrasnportistaTrans() != null) {
                transaccion.setPlacaTrasnportistaTrans(transaccion.getPlacaTrasnportistaTrans().replaceAll("\\s", ""));
            }
            if (this.getTransaccion().getCodigoPrec().getTipoPrec().equals("E")) {
                transaccion.setDaesTrans(tomarDatoDae());
            } else if (this.getTransaccion().getCodigoPrec().getCondicionContenedorPrec().equals("FCL/FCL")) {
                transaccion.setHabilitadoTrans(false);
                this.getSeleccionadoEksat().setHabilitado(false);
            }
            this.logicaTransaccion.guardarCustom(transaccion, this.getSeleccionadoEksat());
        } catch (Exception ex) {
            Logger.getLogger(UtilAisvController.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex);
        }
    }

    /**
     * Método para agregar DAES
     *
     * @param evt
     */
    public void agregarDAE(ActionEvent evt) {
        try {
            if (dae.getPrimerDato() == null || dae.getSegundoDato() == null || dae.getTercerDato() == null) {
                Mensaje.ADVERTENCIA_DIALOG("Agregacion de DAE", "Por favor llene <br/>todos los Campos DAE");
            } else if (dae.getPrimerDato().length() == 3 && dae.getSegundoDato().length() == 4 && dae.getTercerDato().length() == 2 && dae.getCuartoDato().length() == 8) {
                if (this.getDaesListado().isEmpty()) {
                    dae.setCodigo(1);
                } else {
                    dae.setCodigo(this.getDaesListado().get(this.getDaesListado().size() - 1).getCodigo() + 1);
                }
                this.getDaesListado().add(dae);
                dae = new Dae();
            } else {
                String mensaje = "Por favor verifique los datos.<br/>";
                if (dae.getPrimerDato().length() != 3) {
                    mensaje = "El primer valor debe tener 3 digitos<br/>";
                }
                if (dae.getSegundoDato().length() != 4) {
                    mensaje = "El segundo valor debe tener 4 digitos<br/>";
                }
                if (dae.getTercerDato().length() != 2) {
                    mensaje = "El tercer valor debe tener 2 digitos<br/>";
                }
                if (dae.getCuartoDato().length() != 8) {
                    mensaje = "El cuarto valor debe tener 8 digitos<br/>";
                }

                Mensaje.ADVERTENCIA_DIALOG("Agregacion de DAE", mensaje);
            }
            dae = new Dae();
        } catch (Exception ex) {
            Mensaje.ERROR_DIALOG("Agregacion de DAE", "Error con DAE");
        }
    }

    public void cargarNombreTransportista() {
        if (this.getTransaccion().getCedulaTrasnportistaTrans().length() > 0) {
            try {
                String nombreTransportista = logicaTransaccion.nombreTransportista(this.getTransaccion().getCedulaTrasnportistaTrans());
                this.getTransaccion().setNombreTrasnportistaTrans(nombreTransportista);
            } catch (Exception ex) {
                Logger.getLogger(UtilAisvController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            this.getTransaccion().setNombreTrasnportistaTrans(null);
        }
    }

    /**
     * Métodos para quitar DAE
     */
    public void quitarDAE() {
        try {
            if (dae != null) {
                this.getDaesListado().remove(dae);
            }
        } catch (Exception ex) {
            Mensaje.ERROR_DIALOG("Agregacion de DAE", "Error con DAE");
        }
    }

    /**
     * Método para convertir lso datos del listado de DAES a una cadena de texto
     * para poderlo guardar luego
     *
     * @return
     */
    protected String tomarDatoDae() {
        //String dato = "";
        StringBuilder sb = new StringBuilder();
        for (Dae dx : this.getDaesListado()) {
            sb.append(dx.getNumeroCompleto()).append("  /  ");
            //dato += dx.getNumeroCompleto() + "  /  ";
        }
        return sb.toString();
    }

    /**
     * Método para convertir lso datos del listado de DAES a una cadena de texto
     * para poderlo guardar luego
     *
     * @return
     */
    protected String tomarDatoDae(List<Dae> lista) {
        //String dato = "";
        StringBuilder sb = new StringBuilder();
        for (Dae dx : lista) {
            sb.append(dx.getNumeroCompleto()).append("  /  ");
            //dato += dx.getNumeroCompleto() + "  /  ";
        }
        return sb.toString();
    }

    /**
     * @return the listaImportacion
     */
    public LazyDataModelAdvance<Precarga> getListaImportacion() {
        return listaImportacion;
    }

    /**
     * @param listaImportacion the listaImportacion to set
     */
    public void setListaImportacion(LazyDataModelAdvance<Precarga> listaImportacion) {
        this.listaImportacion = listaImportacion;
    }

    /**
     * @return the listaExportacion
     */
    public LazyDModelPrecargaExportacion getListaExportacion() {
        return listaExportacion;
    }

    /**
     * @param listaExportacion the listaExportacion to set
     */
    public void setListaExportacion(LazyDModelPrecargaExportacion listaExportacion) {
        this.listaExportacion = listaExportacion;
    }

    /**
     * @return the seleccionadoEksat
     */
    public Precarga getSeleccionadoEksat() {
        return seleccionadoEksat;
    }

    /**
     * @param seleccionadoEksat the seleccionadoEksat to set
     */
    public void setSeleccionadoEksat(Precarga seleccionadoEksat) {
        this.seleccionadoEksat = seleccionadoEksat;
    }

    /**
     * @return the transaccion
     */
    public Transaccion getTransaccion() {
        if (transaccion == null) {
            transaccion = new Transaccion();
        }
        return transaccion;
    }

    /**
     * @param transaccion the transaccion to set
     */
    public void setTransaccion(Transaccion transaccion) {
        this.transaccion = transaccion;
    }

    /**
     * @return the daesListado
     */
    public List<Dae> getDaesListado() {
        if (daesListado == null) {
            daesListado = new LinkedList<Dae>();
        }
        return daesListado;
    }

    /**
     * @param daesListado the daesListado to set
     */
    public void setDaesListado(List<Dae> daesListado) {
        this.daesListado = daesListado;
    }

    /**
     * @return the dae
     */
    public Dae getDae() {
        if (dae == null) {
            dae = new Dae();
        }
        return dae;
    }

    /**
     * @param dae the dae to set
     */
    public void setDae(Dae dae) {
        this.dae = dae;
    }

    /**
     * @return the origen
     */
    public Puerto getOrigen() {
        if (origen == null) {
            origen = new Puerto();
        }
        return origen;
    }

    /**
     * @param origen the origen to set
     */
    public void setOrigen(Puerto origen) {
        this.origen = origen;
    }

    /**
     * @return the trasbordo
     */
    public Puerto getTrasbordo() {
        if (trasbordo == null) {
            trasbordo = new Puerto();
        }
        return trasbordo;
    }

    /**
     * @param trasbordo the trasbordo to set
     */
    public void setTrasbordo(Puerto trasbordo) {
        this.trasbordo = trasbordo;
    }

    /**
     * @return the destino
     */
    public Puerto getDestino() {
        if (destino == null) {
            destino = new Puerto();
        }
        return destino;
    }

    /**
     * @param destino the destino to set
     */
    public void setDestino(Puerto destino) {
        this.destino = destino;
    }

    /**
     * @return the completado
     */
    public String getCompletado() {
        return completado;
    }

    /**
     * @param completado the completado to set
     */
    public void setCompletado(String completado) {
        this.completado = completado;
    }

    /**
     * @return the listaAisv
     */
    public LazyDataModelAdvance<Transaccion> getListaAisv() {
        return listaAisv;
    }

    /**
     * @param listaAisv the listaAisv to set
     */
    public void setListaAisv(LazyDataModelAdvance<Transaccion> listaAisv) {
        this.listaAisv = listaAisv;
    }

    /**
     * @return the tipoTrasaccion
     */
    public String getTipoTrasaccion() {
        if (tipoTrasaccion == null) {
            tipoTrasaccion = "I";
        }
        return tipoTrasaccion;
    }

    /**
     * @param tipoTrasaccion the tipoTrasaccion to set
     */
    public void setTipoTrasaccion(String tipoTrasaccion) {
        this.tipoTrasaccion = tipoTrasaccion;
    }

    /**
     * @return the rutaReporteServidor
     */
    public String getRutaReporteServidor() {
        return rutaReporteServidor;
    }

    /**
     * @return the listaAisvAux
     */
    public LazyDataModelAdvance<Transaccion> getListaAisvAux() {
        if (listaAisvAux == null) {
            listaAisvAux = new LazyDataModelAdvance<>();
        }
        return listaAisvAux;
    }

    /**
     * @param listaAisvAux the listaAisvAux to set
     */
    public void setListaAisvAux(LazyDataModelAdvance<Transaccion> listaAisvAux) {
        this.listaAisvAux = listaAisvAux;
    }

    /**
     * @return the destinoFinal
     */
    public Puerto getDestinoFinal() {
        if (destinoFinal == null) {
            destinoFinal = new Puerto();
        }
        return destinoFinal;
    }

    /**
     * @param destinoFinal the destinoFinal to set
     */
    public void setDestinoFinal(Puerto destinoFinal) {
        this.destinoFinal = destinoFinal;
    }

    /**
     * @return the exportador
     */
    public String getExportador() {
        return exportador;
    }

    /**
     * @param exportador the exportador to set
     */
    public void setExportador(String exportador) {
        this.exportador = exportador;
    }

    /**
     * @return the rucExportador
     */
    public String getRucExportador() {
        return rucExportador;
    }

    /**
     * @param rucExportador the rucExportador to set
     */
    public void setRucExportador(String rucExportador) {
        this.rucExportador = rucExportador;
    }

    /**
     * @return the currentloggeduser
     */
    public String getCurrentloggeduser() {
        return currentloggeduser;
    }

    /**
     * @param currentloggeduser the currentloggeduser to set
     */
    public void setCurrentloggeduser(String currentloggeduser) {
        this.currentloggeduser = currentloggeduser;
    }

}
