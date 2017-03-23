package com.naportec.aisv.logica;

import com.naportec.aisv.entidades.Itinerario;
import com.naportec.utilidades.enumeraciones.Estado;
import com.naportec.utilidades.logica.AbstractFacade;
import com.naportec.utilidades.otros.Fechas;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Logica de Negocios de la entidad Itinerarios
 * @author Fernando
 */
@Stateless
public class ItinerarioFacade extends AbstractFacade<Itinerario> implements Serializable {

    public ItinerarioFacade() {
        super(Itinerario.class);
    }

    /**
     * Inicialización de  nuestro Recurso de Datos
     */
    @PersistenceContext(unitName = "com.naportec_Aisv")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Método para listar itinerarios que tengan cierto estado entre la fecha de
     * hoy y 30 días
     * @param estado
     * @return 
     */
    public List<Itinerario> listaItinerarios(Estado estado) {
        Date inicio = new Date();
        Date fin = Fechas.sumarDiasFecha(new Date(), 30);
        Query q = getEntityManager().createNamedQuery("Itinerario.listaItinerario");
        q.setParameter("estado", estado.name());
        q.setParameter("inicio", inicio);
        q.setParameter("fin", fin);
        return q.getResultList();
    }

    /**
     * Método para obtener itinerarios con estado activo y que la fecha de zarpe 
     * sea mayor a la fecha actual.
     * @return 
     */
    public List<Itinerario> listaItinerariosActivos() {
        Query q = getEntityManager().createNamedQuery("Itinerario.listaItinerarioActivo");
        q.setParameter("estado", Estado.Activo.name());
        q.setParameter("fecha", new java.sql.Date(new Date().getTime()));
        return q.getResultList();
    }

    /**
     * Método para buscar por coincidencia los itinerarios activos
     * @param query
     * @return 
     */
    public List<Itinerario> buscarPorCoincidencia(String query) {
        query = "%" + query + "%";
        Query q = getEntityManager().createNamedQuery("Itinerario.listaItinerario");
        q.setParameter("estado", Estado.Activo.name());
        q.setParameter("buque", query);
        q.setParameter("viaje", query);
        return q.getResultList();
    }

}
