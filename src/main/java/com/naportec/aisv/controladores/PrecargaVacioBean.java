package com.naportec.aisv.controladores;

import com.naportec.aisv.entidades.AisvListadoBookings;
import com.naportec.aisv.entidades.Itinerario;
import com.naportec.aisv.entidades.Naviera;
import com.naportec.aisv.entidades.Precarga;
import com.naportec.aisv.entidades.PrecargaVacios;
import com.naportec.aisv.entidades.Puerto;
import com.naportec.aisv.logica.ListaBookingsFacade;
import com.naportec.aisv.logica.PrecargaFacade;
import com.naportec.aisv.logica.PrecargaVacioFacade;
import com.naportec.aisv.logica.PuertoFacade;
import com.naportec.seguridad.entidades.SUser;
import com.naportec.seguridad.logica.SUserFacade;
import com.naportec.utilidades.controladores.LazyDataModelAdvance;
import com.naportec.utilidades.controladores.Mensaje;
import com.naportec.utilidades.controladores.UtilController;
import com.naportec.utilidades.enumeraciones.Estado;
import com.naportec.utilidades.logica.Filtro;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import org.primefaces.model.UploadedFile;
import java.util.Map;
import javax.ejb.EJB;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.event.SelectEvent;

/**
 * Controlador que permite manejar las acciones de Precarga de datos
 *
 * @author Fernando
 */
public class PrecargaVacioBean extends UtilController<PrecargaVacios> {
    
    private UploadedFile file;
    private Itinerario itinerario;
    private Naviera linea;
    private SUser usuario;
    @EJB
    private PrecargaVacioFacade precargaLogica;
    @EJB
    private SUserFacade userLogica;
    @EJB
    private PuertoFacade puertoFacade;
    
    private Puerto origen, destino, trasbordo;
    private final String ruta = "/aisv/reportes/reporteNavi.jasper";
    private final String rutaPrecargaExpo = "/aisv/reportes/reporteCargaExportacion.jasper";
    private final String rutaPrecargaImpo = "/aisv/reportes/reporteCargaImportacion.jasper";
    
    private String tipoCarga;
    private String tipoTrans;
    
    public PrecargaVacioBean() {
        super(PrecargaVacios.class);
    }

    /**
     * Inicializar listados
     */
    @PostConstruct
    public void inicializar() {
        System.out.println("" + this.getCurrentloggeduser());
        this.setUsuario(userLogica.buscarUsuario(this.getCurrentloggeduser()));
        //LISTADO DE PRECARGA DEFULT
        this.setEntidadLogica(precargaLogica);
//        this.getListadoEntidad().filtroMayorEqual("idItinerarioPrev.fechaZarpeItin", new java.sql.Date(new Date().getTime()));
        this.getListadoEntidad().filtroOrderBy("codigoPrev", "desc");
    }

    /**
     * Método que permite preparar datos antes de crear una Precarga
     *
     * @param evt
     */
    @Override
    public void prepararCrear(ActionEvent evt) {
        super.prepararCrear(evt); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Método que permite realizar acciones antes de selccionar una precarga
     * para modificar datos
     *
     * @param evt
     */
    @Override
    public void seleccionarEditar(SelectEvent evt) {
        this.origen = this.puertoFacade.buscarPorNombre(this.getEntidad().getPtoOrigenPrev());
        this.destino = this.puertoFacade.buscarPorNombre(this.getEntidad().getPtoDestinoPrev());
        this.trasbordo = this.puertoFacade.buscarPorNombre(this.getEntidad().getPtoTransbordoPrev());
        super.seleccionarEditar(evt); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Método que permite guardar los datos de una Precarga
     *
     * @param evt
     */
    @Override
    public void guardarEntidad(ActionEvent evt) {
        if (this.getOrigen() != null) {
            this.getEntidad().setPtoOrigenPrev(this.getOrigen().getCodPuertoPuer());
        }
        if (this.getDestino() != null) {
            this.getEntidad().setPtoDestinoPrev(this.getDestino().getCodPuertoPuer());
        }
        if (this.getTrasbordo() != null) {
            this.getEntidad().setPtoTransbordoPrev(this.getTrasbordo().getCodPuertoPuer());
        }
        String val = validaciones();
        if (val.trim().isEmpty()) {
            this.getEntidad().setEspaciosDisponiblesPrev(this.getEntidad().getTotalEspaciosPrev());
            super.guardarEntidad(evt);
        } else {
            Mensaje.ERROR_DIALOG("Nueva Precarga", "Han sucedido los siguientes errores<br/>" + val);
        }
    }
    
    @Override
    public void eliminarEntidad(ActionEvent evt) {
        this.precargaLogica.eliminar(this.getEntidad());
    }

    /**
     * Validaciones de datos de la precarga
     *
     * @return
     */
    public String validaciones() {
        String msg = "";
        if (this.getEntidad().getBookingPrev() == null) {
            msg += "El Booking es necesario<br/>";
        } else if (this.getEntidad().getBookingPrev().trim().isEmpty()) {
            msg += "El Booking es necesario<br/>";
        }
        if (this.getEntidad().getIdItinerarioPrev() == null) {
            msg += "El Itinerario es necesario<br/>";
        }
        if (this.getEntidad().getTipoContenedorPrev() == null) {
            msg += "El Tipo de contenedor es necesario<br/>";
        } else if (this.getEntidad().getTipoContenedorPrev().trim().isEmpty()) {
            msg += "El Tipo de contenedor es necesario<br/>";
        }
        if (this.getEntidad().getTotalEspaciosPrev() == null) {
            msg += "El total de espacios es necesario<br/>";
        } else if (this.getEntidad().getTotalEspaciosPrev() == 0d) {
            msg += "El total de espacios es necesario<br/>";
        }
        if (this.getEntidad().getPtoOrigenPrev() == null) {
            msg += "El Puerto de Origen es necesario<br/>";
        } else if (this.getEntidad().getPtoOrigenPrev().trim().isEmpty()) {
            msg += "El Puerto de Origen es necesario<br/>";
        }
        if (this.getEntidad().getPtoDestinoPrev() == null) {
            msg += "El Puerto de Destino es necesario<br/>";
        } else if (this.getEntidad().getPtoDestinoPrev().trim().isEmpty()) {
            msg += "El Puerto de Destino es necesario<br/>";
        }
        return msg;
    }

    /**
     * Método para guardar desde archivo las Precargas ya sea CSV o EDI
     *
     * @param evt
     */
    public void guardarDesdeArchivo(ActionEvent evt) {
        try {
            if (linea == null) {
                Mensaje.ADVERTENCIA_DIALOG("Carga de Archivos", "Por favor escoja la Linea Naviera");
            } else if (file.getFileName().length() < 5) {
                Mensaje.ADVERTENCIA_DIALOG("Carga de Archivos", "Por seleccione un archivo");
            } else {
                Map<Boolean, String> ver = this.precargaLogica.guardarDesdeArchivo(file.getInputstream(), file.getFileName(), itinerario, linea);
                if (ver == null) {
                    Mensaje.SUCESO_DIALOG("Carga de Archivos", "Se ha guardado correctamente el Archivo");
                } else {
                    Mensaje.ERROR("Carga de Archivos", ver.get(Boolean.FALSE));
                }
            }
        } catch (IOException ex) {
            Mensaje.ERROR_DIALOG("Carga de Archivos", "Por favor Seleccione un archivo");
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
     * @return the linea
     */
    public Naviera getLinea() {
        return linea;
    }

    /**
     * @param linea the linea to set
     */
    public void setLinea(Naviera linea) {
        this.linea = linea;
    }
    
}
