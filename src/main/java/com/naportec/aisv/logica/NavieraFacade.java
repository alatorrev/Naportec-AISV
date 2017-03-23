package com.naportec.aisv.logica;

import com.naportec.aisv.entidades.Naviera;
import com.naportec.utilidades.logica.AbstractFacade;
import java.io.Serializable;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Logica de Negocios de la entidad Naviera
 * @author Fernando
 */
@Stateless
public class NavieraFacade extends AbstractFacade<Naviera> implements Serializable {

    public NavieraFacade() {
        super(Naviera.class);
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
