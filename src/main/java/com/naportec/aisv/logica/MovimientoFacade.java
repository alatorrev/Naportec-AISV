package com.naportec.aisv.logica;

import com.naportec.aisv.entidades.Movimiento;
import com.naportec.utilidades.logica.AbstractFacade;
import java.io.Serializable;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
/**
 * Logica de Negocios de la entidad Movimiento
 * @author Fernando
 */
@Stateless
public class MovimientoFacade extends AbstractFacade<Movimiento> implements Serializable {

    /**
     * Inicialización de  nuestro Recurso de Datos
     */
    @PersistenceContext(unitName = "com.naportec_Aisv")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MovimientoFacade() {
        super(Movimiento.class);
    }

}
