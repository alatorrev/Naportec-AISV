package com.naportec.seguridad.logica;

import com.naportec.seguridad.entidades.SRight;
import com.naportec.utilidades.logica.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
/**
 * Logica de Negocios de la entidad SRigth
 * @author Fernando
 */
@Stateless
public class SRightFacade extends AbstractFacade<SRight> {

    /**
     * Inicialización de  nuestro Recurso de Datos
     */
    @PersistenceContext(unitName = "com.naportec_Aisv")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SRightFacade() {
        super(SRight.class);
    }

}
