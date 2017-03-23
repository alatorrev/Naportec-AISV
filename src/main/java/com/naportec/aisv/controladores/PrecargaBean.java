package com.naportec.aisv.controladores;

import com.naportec.aisv.entidades.AisvListadoBookings;
import com.naportec.aisv.entidades.Itinerario;
import com.naportec.aisv.entidades.Precarga;
import com.naportec.aisv.entidades.Puerto;
import com.naportec.aisv.logica.ListaBookingsFacade;
import com.naportec.aisv.logica.PrecargaFacade;
import com.naportec.aisv.logica.PuertoFacade;
import com.naportec.seguridad.entidades.SUser;
import com.naportec.seguridad.logica.SUserFacade;
import com.naportec.utilidades.controladores.LazyDataModelAdvance;
import com.naportec.utilidades.controladores.Mensaje;
import com.naportec.utilidades.controladores.UtilController;
import com.naportec.utilidades.logica.Filtro;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import org.primefaces.model.UploadedFile;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.event.SelectEvent;

/**
 * Controlador que permite manejar las acciones de Precarga de datos
 * @author Fernando
 */
public class PrecargaBean extends UtilController<Precarga> {
    
    private UploadedFile file;
    private Itinerario itinerario;
    private SUser usuario;
    @EJB
    private PrecargaFacade precargaLogica;
    @EJB
    private ListaBookingsFacade bookingsFacade;
    @EJB
    private SUserFacade userLogica;
    @EJB
    private PuertoFacade puertoFacade;
    
    private Puerto origen, destino, trasbordo;
    private final String ruta = "/aisv/reportes/reporteNavi.jasper";
    private final String rutaPrecargaExpo = "/aisv/reportes/reporteCargaExportacion.jasper";
    private final String rutaPrecargaImpo = "/aisv/reportes/reporteCargaImportacion.jasper";
    
    private LazyDataModelAdvance<Precarga> listaCarga;
    private LazyDataModelAdvance<AisvListadoBookings> listaCargaNaviera;
    
    private String tipoCarga;
    private String tipoTrans;
    
    public PrecargaBean() {
        super(Precarga.class);
    }
    
    /**
     * Inicializar listados
     */
    @PostConstruct
    public void inicializar() {
        tipoCarga = "FCL/FCL";
        tipoTrans = "E";
        System.out.println("" + this.getCurrentloggeduser());
        this.setUsuario(userLogica.buscarUsuario(this.getCurrentloggeduser()));
        //LISTADO DE PRECARGA DEFULT
        this.setEntidadLogica(precargaLogica);
        this.getListadoEntidad().filtroEqual("tipoPrec", "E");
        //LISTADO DE TODAS LAS CARGAS
        this.listaCarga = new LazyDataModelAdvance<>(precargaLogica);
        //this.listaCarga.filtroOrderBy("codigoPrec", "desc");
        listaCargaNaviera = new LazyDataModelAdvance<>(bookingsFacade);
        listaCargaNaviera.filtroNotEqual("estadoTrans", "Anulado");
        listaCargaNaviera.filtroEqual("tipoPrec", "E");
        if (getUsuario().getCodigoNavi() != null) {
            listaCargaNaviera.filtroEqual("linea", getUsuario().getCodigoNavi().getCodigoCaeNavi());
            this.getListadoEntidad().filtroEqual("idLineaPrec", getUsuario().getCodigoNavi());
        }
        this.getListadoEntidad().filtroMayorEqual("idItinerarioPrec.fechaZarpeItin", new java.sql.Date(new Date().getTime()));
        this.getListadoEntidad().filtroOrderBy("codigoPrec", "desc");
    }
    
    public void prepDatosCarga() {
        if (tipoCarga != null && tipoTrans != null) {
            this.listaCarga.setFiltros(new LinkedList<Filtro>());
            this.listaCarga.filtroEqual("tipoPrec", tipoTrans);
            this.listaCarga.filtroEqual("condicionContenedorPrec", tipoCarga);
        }
    }
    /**
     * Método que permite preparar datos antes de crear una Precarga
     * @param evt 
     */
    @Override
    public void prepararCrear(ActionEvent evt) {
        super.prepararCrear(evt); //To change body of generated methods, choose Tools | Templates.
        this.getEntidad().setCondicionContenedorPrec("FCL/FCL");
    }
    /**
     * Método que permite realizar acciones antes de selccionar una precarga para modificar datos
     * @param evt 
     */
    @Override
    public void seleccionarEditar(SelectEvent evt) {
        this.origen = this.puertoFacade.buscarPorNombre(this.getEntidad().getPtoOrigenPrec());
        this.destino = this.puertoFacade.buscarPorNombre(this.getEntidad().getPtoDestinoPrec());
        this.trasbordo = this.puertoFacade.buscarPorNombre(this.getEntidad().getPtoTransbordoPrec());
        super.seleccionarEditar(evt); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Método que permite incializat los objetos necesarios para luego poder 
     * exportar a PDF o EXCEL
     */
    public void prepExportar() {
        try {
            this.inicializarReporteObject(null, this.getListaCargaNaviera().loadTotalList(), ruta);
        } catch (JRException ex) {
            Mensaje.ERROR_DIALOG("Error", "No se ha podido mostrar el rpeorte: " + ex.getMessage());
        } catch (Exception ex) {
            Mensaje.ERROR_DIALOG("Error", "No se ha podido mostrar el rpeorte: " + ex.getMessage());
        }
    }
    /**
     * Método que permite incializat los objetos necesarios para luego poder 
     * exportar a PDF o EXCEL
     */
    public void prepExportarPrecargas() {
        try {
            if (this.tipoTrans.trim().equals("E")) {
                this.inicializarReporte(null, this.getListaCarga().loadTotalList(), rutaPrecargaExpo);
            } else {
                this.inicializarReporte(null, this.getListaCarga().loadTotalList(), rutaPrecargaImpo);
            }
        } catch (JRException ex) {
            Mensaje.ERROR_DIALOG("Error", "No se ha podido mostrar el rpeorte: " + ex.getMessage());
        } catch (Exception ex) {
            Mensaje.ERROR_DIALOG("Error", "No se ha podido mostrar el rpeorte: " + ex.getMessage());
        }
    }
    
    /**
     * Método que permite guardar los datos de una Precarga
     * @param evt 
     */
    @Override
    public void guardarEntidad(ActionEvent evt) {
        if (this.getOrigen() != null) {
            this.getEntidad().setPtoOrigenPrec(this.getOrigen().getCodPuertoPuer());
        }
        if (this.getDestino() != null) {
            this.getEntidad().setPtoDestinoPrec(this.getDestino().getCodPuertoPuer());
        }
        if (this.getTrasbordo() != null) {
            this.getEntidad().setPtoTransbordoPrec(this.getTrasbordo().getCodPuertoPuer());
        }
        String val = validaciones();
        if (val.trim().isEmpty()) {
            if (this.getEntidad().getCargaPeligrosaPrec().equals("true")) {
                this.getEntidad().setCargaPeligrosaPrec("S");
            } else {
                this.getEntidad().setCargaPeligrosaPrec("N");
            }
            this.getEntidad().setIdLineaPrec(getUsuario().getCodigoNavi());
            this.getEntidad().setEspaciosDisponiblesPrec(this.getEntidad().getTotalEspaciosPrec());
            this.getEntidad().setTipoPrec("E");
            super.guardarEntidad(evt);
        } else {
            Mensaje.ERROR_DIALOG("Nueva Precarga", "Han sucedido los siguientes errores<br/>" + val);
        }
    }
    /**
     * Validaciones de datos de la precarga
     * @return 
     */
    public String validaciones() {
        String msg = "";
        if (this.getEntidad().getBookingPrec() == null) {
            msg += "El Booking es necesario<br/>";
        } else if (this.getEntidad().getBookingPrec().trim().isEmpty()) {
            msg += "El Booking es necesario<br/>";
        }
        if (this.getEntidad().getIdItinerarioPrec() == null) {
            msg += "El Itinerario es necesario<br/>";
        }
        if (this.getEntidad().getImpExpIdPrec() == null) {
            msg += "El RUC del exportadior es necesario<br/>";
        } else if (this.getEntidad().getImpExpIdPrec().trim().isEmpty()) {
            msg += "El RUC del exportadior es necesario<br/>";
        }
        if (this.getEntidad().getImportadorPrec() == null) {
            msg += "El Exportador es necesario<br/>";
        } else if (this.getEntidad().getImportadorPrec().trim().isEmpty()) {
            msg += "El Exportador es necesario<br/>";
        }
        if (this.getEntidad().getDescripcionPrec() == null) {
            msg += "El Producto es necesario<br/>";
        } else if (this.getEntidad().getDescripcionPrec().trim().isEmpty()) {
            msg += "El Producto es necesario<br/>";
        }
        if (this.getEntidad().getCondicionContenedorPrec().equals("FCL/FCL")) {
            if (this.getEntidad().getTipoContenedorPrec() == null) {
                msg += "El Tipo de contenedor es necesario<br/>";
            } else if (this.getEntidad().getTipoContenedorPrec().trim().isEmpty()) {
                msg += "El Tipo de contenedor es necesario<br/>";
            }
            if (this.getEntidad().getTotalEspaciosPrec() == null) {
                msg += "El total de espacios es necesario<br/>";
            } else if (this.getEntidad().getTotalEspaciosPrec() == 0d) {
                msg += "El total de espacios es necesario<br/>";
            }
            if (this.getEntidad().getPesoPrec() == null) {
                msg += "El Peso es necesario<br/>";
            } else if (this.getEntidad().getPesoPrec() == 0d) {
                msg += "El Peso es necesario<br/>";
            }
            
        }
        if (this.getEntidad().getPtoOrigenPrec() == null) {
            msg += "El Puerto de Origen es necesario<br/>";
        } else if (this.getEntidad().getPtoOrigenPrec().trim().isEmpty()) {
            msg += "El Puerto de Origen es necesario<br/>";
        }
        if (this.getEntidad().getPtoDestinoPrec() == null) {
            msg += "El Puerto de Destino es necesario<br/>";
        } else if (this.getEntidad().getPtoDestinoPrec().trim().isEmpty()) {
            msg += "El Puerto de Destino es necesario<br/>";
        }
        return msg;
    }
    /**
     * Método para guardar desde archivo las Precargas ya sea CSV o EDI
     * @param evt 
     */
    public void guardarDesdeArchivo(ActionEvent evt) {
        try {
            Map<Boolean, String> ver = this.precargaLogica.guardarDesdeArchivo(file.getInputstream(), file.getFileName(), itinerario, getUsuario().getCodigoNavi());
            if (ver == null) {
                Mensaje.SUCESO_DIALOG("Carga de Archivos", "Se ha guardado correctamente el Archivo");
            } else {
                Mensaje.ERROR("Carga de Archivos", ver.get(Boolean.FALSE));
            }
        } catch (IOException ex) {
            Mensaje.ERROR_DIALOG("Carga de Archivos", "Por favor Seleccione un archivo");
        }
    }

    @Override
    public void eliminarEntidad(ActionEvent evt) {
        try{
            this.precargaLogica.eliminar(this.getEntidad());
        }catch(EJBException ex){
             Mensaje.ERROR_DIALOG("Eliminación de Booking", "Error al tratar de eliminar el Booking:"+ex.getMessage());
        }
    }
    
    

    /**
     * @return the file
     */
    public UploadedFile getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(UploadedFile file) {
        this.file = file;
    }

    /**
     * @return the itinerario
     */
    public Itinerario getItinerario() {
        return itinerario;
    }

    /**
     * @param itinerario the itinerario to set
     */
    public void setItinerario(Itinerario itinerario) {
        this.itinerario = itinerario;
    }

    /**
     * @return the usuario
     */
    public SUser getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(SUser usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the origen
     */
    public Puerto getOrigen() {
        return origen;
    }

    /**
     * @param origen the origen to set
     */
    public void setOrigen(Puerto origen) {
        this.origen = origen;
    }

    /**
     * @return the destino
     */
    public Puerto getDestino() {
        return destino;
    }

    /**
     * @param destino the destino to set
     */
    public void setDestino(Puerto destino) {
        this.destino = destino;
    }

    /**
     * @return the trasbordo
     */
    public Puerto getTrasbordo() {
        return trasbordo;
    }

    /**
     * @param trasbordo the trasbordo to set
     */
    public void setTrasbordo(Puerto trasbordo) {
        this.trasbordo = trasbordo;
    }

    /**
     * @return the listaCarga
     */
    public LazyDataModelAdvance<Precarga> getListaCarga() {
        return listaCarga;
    }

    /**
     * @param listaCarga the listaCarga to set
     */
    public void setListaCarga(LazyDataModelAdvance<Precarga> listaCarga) {
        this.listaCarga = listaCarga;
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

    /**
     * @return the tipoTrans
     */
    public String getTipoTrans() {
        return tipoTrans;
    }

    /**
     * @param tipoTrans the tipoTrans to set
     */
    public void setTipoTrans(String tipoTrans) {
        this.tipoTrans = tipoTrans;
    }

    /**
     * @return the listaCargaNaviera
     */
    public LazyDataModelAdvance<AisvListadoBookings> getListaCargaNaviera() {
        return listaCargaNaviera;
    }

    /**
     * @param listaCargaNaviera the listaCargaNaviera to set
     */
    public void setListaCargaNaviera(LazyDataModelAdvance<AisvListadoBookings> listaCargaNaviera) {
        this.listaCargaNaviera = listaCargaNaviera;
    }
    
}
