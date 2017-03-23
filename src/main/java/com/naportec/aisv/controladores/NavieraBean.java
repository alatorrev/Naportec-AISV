package com.naportec.aisv.controladores;

import com.naportec.aisv.entidades.Naviera;
import com.naportec.aisv.logica.NavieraFacade;
import com.naportec.utilidades.controladores.UtilController;
import com.naportec.utilidades.enumeraciones.Estado;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
/**
 * Clase controlador para manejar las acciones de Navieras
 * @author Fernando
 */
public class NavieraBean extends UtilController<Naviera> {
    
    /**
     * Bean que contiene lalogica de negociuos de Naviera
     * Este objeto no debe ser inicializado
     */
    @EJB
    private NavieraFacade logicaNaviera;
    
    private List<Naviera> listado;

    public NavieraBean() {
        super(Naviera.class);
    }

    /**
     * Inicialización de listado lazy.
     */
    @PostConstruct
    public void inicializar() {
        this.setEntidadLogica(logicaNaviera);
        this.getListadoEntidad().filtroEqual("estado", Estado.Activo.name());
    }

    /**
     * @return the listado
     */
    public List<Naviera> getListado() {
        listado=logicaNaviera.listarPorEstado(Estado.Activo);
        return listado;
    }

    /**
     * @param listado the listado to set
     */
    public void setListado(List<Naviera> listado) {
        this.listado = listado;
    }
}
