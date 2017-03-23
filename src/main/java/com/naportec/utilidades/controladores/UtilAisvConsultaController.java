package com.naportec.utilidades.controladores;

import com.naportec.aisv.entidades.Puerto;
import com.naportec.aisv.entidades.Transaccion;
import com.naportec.aisv.logica.TransaccionFacade;
import com.naportec.seguridad.entidades.SUser;
import com.naportec.seguridad.logica.SUserFacade;
import com.naportec.utilidades.enumeraciones.Estado;
import javax.ejb.EJB;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class UtilAisvConsultaController {

    private Puerto origen;
    private Puerto trasbordo;
    private Puerto destino;
    //--------------------------------------------------------
    private LazyDataModelAdvance<Transaccion> listaImportacion;
    private LazyDataModelAdvance<Transaccion> listaExportacion;
    //-------------------------------------------------
    @EJB
    protected TransaccionFacade logTran;
    @EJB
    protected SUserFacade userLogica;
    //-------------------------------------------------
    protected Transaccion transaccion;
    protected SUser usuario;

    public UtilAisvConsultaController() {

    }

    public void inicializar() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        usuario = userLogica.buscarUsuario(auth.getName());
        setListaImportacion(new LazyDataModelAdvance<>(logTran));
        getListaImportacion().filtroEqual("estado", Estado.Activo.name());
        getListaImportacion().filtroEqual("usrId", usuario);

        setListaExportacion(new LazyDataModelAdvance<>(logTran));
        getListaExportacion().filtroEqual("estado", Estado.Activo.name());
        getListaImportacion().filtroEqual("usrId", usuario);
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
        return destino;
    }

    /**
     * @param destino the destino to set
     */
    public void setDestino(Puerto destino) {
        this.destino = destino;
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

}
