package com.naportec.utilidades.controladores;

import com.naportec.aisv.entidades.Dae;
import com.naportec.aisv.entidades.Transaccion;
import com.naportec.aisv.logica.TransaccionFacade;
import com.naportec.seguridad.entidades.SUser;
import com.naportec.seguridad.logica.SUserFacade;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
/**
 * Clase abstracta para manejar varios aspectos que son similares para varios 
 * controladores 
 * 
 * NO ES UTILIZADA se cambio a esta por UtilAisvcontroller
 * @author Fernando 
 */
public abstract class AbstractAisvController {
    protected boolean disabled;
    protected LazyDataModelAdvance<Transaccion> listaImportacion;
    protected LazyDataModelAdvance<Transaccion> listaExportacion;
    @EJB
    protected TransaccionFacade logicaTransaccion;
    @EJB
    protected SUserFacade userLogica;
    protected Transaccion transaccion;
    //-------------------------------------------------
    protected List<Dae> daesListado;
    private Dae dae;
    //-------------------------------------------------
    protected SUser usuario;
    

    public AbstractAisvController() {

    }

    public void inicializar() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        usuario = userLogica.buscarUsuario(auth.getName());
        listaImportacion = new LazyDataModelAdvance<>(logicaTransaccion);
        listaImportacion.filtroEqual("codigoPrec.tipoPrec", "I");
        if (usuario.getCodigoSoli().getTipoContactoSoli().equals("IE")) {
            listaImportacion.filtroEqual("codigoPrec.impExpIdPrec", usuario.getCodigoSoli().getIdentificacionSoli());
        } else {
            if (usuario.getCodigoSoli().getTipoContactoSoli().equals("AA")) {
                listaImportacion.filtroEqual("codigoPrec.agenteIdPrec", usuario.getCodigoSoli().getIdentificacionSoli());
            }
        }
        listaExportacion = new LazyDataModelAdvance<>(logicaTransaccion);
        listaExportacion.filtroEqual("codigoPrec.tipoPrec", "E");
        listaExportacion.filtroEqual("codigoPrec.impExpIdPrec", usuario.getCodigoSoli().getIdentificacionSoli());
    }

    /**
     * Método para guardar importación 
     */
    public void guardarImportacion() {
        try {
            transaccion.setUsrId(usuario);
            this.logicaTransaccion.guardar(transaccion);
            Mensaje.SUCESO_DIALOG("Guardar AISV", "Se ha guardado correctamente el AISV");
            transaccion = new Transaccion();
        } catch (Exception ex) {
            Mensaje.ERROR_DIALOG("Guardar AISV - Importación", "Ha sucedido un error en la creación del AISV" + ex.getMessage() + transaccion.getNombreTrasnportistaTrans());
        }
    }

    /**
     * Método para guardar una Exportación
     */
    public void guardarExportacion() {
        try {
            transaccion.setUsrId(usuario);
            transaccion.setDaesTrans(tomarDatoDae());
            this.logicaTransaccion.guardar(transaccion);
            Mensaje.SUCESO_DIALOG("Guardar AISV", "Se ha guardado correctamente el AISV");
            transaccion = new Transaccion();
        } catch (Exception ex) {
            Mensaje.ERROR_DIALOG("Guardar AISV - Exportación", "Ha sucedido un error en la creación del AISV");
        }
    }
    
    /**
     * Método para agregar DAES
     * @param evt 
     */
    public void agregarDAE(ActionEvent evt) {
        try {
            if (dae.getPrimerDato() == null || dae.getSegundoDato() == null || dae.getTercerDato() == null) {
                Mensaje.ADVERTENCIA_DIALOG("Agregacion de DAE", "Por favor llene <br/>todos los Campos DAE");
            } else {
                if (dae.getPrimerDato().length() == 3 && dae.getSegundoDato().length() == 4 && dae.getTercerDato().length() == 11) {
                    if (this.getDaesListado().isEmpty()) {
                        dae.setCodigo(1);
                    } else {
                        dae.setCodigo(this.getDaesListado().get(this.getDaesListado().size() - 1).getCodigo() + 1);
                    }
                    this.getDaesListado().add(dae);
                    dae = new Dae();
                    dae.setPrimerDato("028");
                    int numero=(new Date().getYear()+1900);
                    dae.setSegundoDato("" +numero );
                } else {
                    Mensaje.ADVERTENCIA_DIALOG("Agregacion de DAE", "Por favor verifique los datos.<br/>No son correctos.");
                }
            }
        } catch (Exception ex) {
            Mensaje.ERROR_DIALOG("Agregacion de DAE", "Error con DAE");
        }
    }

    /**
     * Métodos para quitar DAES
     */
    public void quitarDAE() {
        try {
            if (dae != null) {
                this.getDaesListado().remove(dae);
            }
        } catch (Exception ex) {
            Mensaje.ERROR_DIALOG("AgregaciÃ³n de DAE", "Error con DAE");
        }
    }

    protected String tomarDatoDae() {
        String dato = "";
        for (Dae dx : daesListado) {
            dato += dx.getNumeroCompleto() + ";";
        }
        return dato;
    }

    public void prepMostrar() {
        
    }

    /**
     * @return the listaImportacion
     */
    public LazyDataModelAdvance<Transaccion> getListaImportacion() {
        return listaImportacion;
    }

    /**
     * @param listaImportacion the listaImportacion to set
     */
    public void setListaImportacion(LazyDataModelAdvance<Transaccion> listaImportacion) {
        this.listaImportacion = listaImportacion;
    }

    /**
     * @return the listaExportacion
     */
    public LazyDataModelAdvance<Transaccion> getListaExportacion() {
        return listaExportacion;
    }

    /**
     * @param listaExportacion the listaExportacion to set
     */
    public void setListaExportacion(LazyDataModelAdvance<Transaccion> listaExportacion) {
        this.listaExportacion = listaExportacion;
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
            dae.setPrimerDato("028");
            dae.setSegundoDato("" + (new Date().getYear()+1900));
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
     * @return the disabled
     */
    public boolean getDisabled() {
        return disabled;
    }

    /**
     * @param disabled the disabled to set
     */
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
