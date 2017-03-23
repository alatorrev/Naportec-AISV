package com.naportec.aisv.controladores;

import com.naportec.aisv.entidades.Itinerario;
import com.naportec.aisv.logica.ItinerarioFacade;
import com.naportec.utilidades.controladores.UtilController;
import com.naportec.utilidades.enumeraciones.Estado;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
/**
 * Clase controlador que permite manejar las acciones para itinerarios
 * @author Fernando
 */
public class ItinerarioBean extends UtilController<Itinerario> {
    /**
     * Bean que contiene al logica de negocios de Itinerario
     */
    @EJB
    private ItinerarioFacade itinerarioLogica;
    /**
     * listado de los itinerarios
     */
    private List<Itinerario> listado;

    public ItinerarioBean() {
        super(Itinerario.class);
    }
    
    @PostConstruct
    public void inicializar() {
        this.setEntidadLogica(itinerarioLogica);
        this.getListadoEntidad().filtroEqual("estado", Estado.Activo.name());
        this.getListadoEntidad().filtroOrderBy("codigoItin", "asc");
    }

    /**
     * Método para realizar un autocompletado de los itinerarios
     * @param query
     * @return 
     */
    public List<Itinerario> autocompletado(String query){
        return this.itinerarioLogica.buscarPorCoincidencia(query);         
    }
    
    /**
     * @return the itinerarioLogica
     */
    public ItinerarioFacade getItinerarioLogica() {
        return itinerarioLogica;
    }

    /**
     * @return the listado
     */
    public List<Itinerario> getListado() throws Exception {
        listado = itinerarioLogica.listaItinerariosActivos();
        return listado;
    }

    /**
     * @param listado the listado to set
     */
    public void setListado(List<Itinerario> listado) {
        this.listado = listado;
    }
}
