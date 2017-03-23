package com.naportec.aisv.logica;

import com.naportec.aisv.entidades.AisvListadoBookings;
import com.naportec.utilidades.logica.AbstractFacade;
import java.io.Serializable;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
/**
 * Logica de Negocios de la entidad AisvListadoBookings que es una Vista
 * @author Fernando
 */
@Stateless
public class ListaBookingsFacade  extends AbstractFacade<AisvListadoBookings> implements Serializable{

    public ListaBookingsFacade() {
        super(AisvListadoBookings.class);
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

    
}
