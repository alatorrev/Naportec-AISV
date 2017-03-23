package com.naportec.seguridad.logica;

import com.naportec.seguridad.entidades.SRole;
import com.naportec.utilidades.logica.AbstractFacade;
import java.io.Serializable;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
/**
 * Logica de Negocios de la entidad SRole
 * @author Fernando
 */
@Stateless
public class SRoleFacade extends AbstractFacade<SRole> implements Serializable {

    /**
     * Inicialización de  nuestro Recurso de Datos
     */
    @PersistenceContext(unitName = "com.naportec_Aisv")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SRoleFacade() {
        super(SRole.class);
    }

}
