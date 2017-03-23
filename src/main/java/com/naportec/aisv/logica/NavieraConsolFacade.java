package com.naportec.aisv.logica;

import com.naportec.aisv.entidades.Consolidadora;
import com.naportec.aisv.entidades.Naviera;
import com.naportec.aisv.entidades.NavieraConsol;
import com.naportec.utilidades.logica.AbstractFacade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Logica de Negocios de la entidad NavieraConsol
 * @author Fernando
 */
@Stateless
public class NavieraConsolFacade extends AbstractFacade<NavieraConsol> implements Serializable {

    public NavieraConsolFacade() {
        super(NavieraConsol.class);
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

    public NavieraConsol existeConsolidadora(Naviera nav,String ruc){
        Query q=getEntityManager().createNamedQuery("NavieraConsol.existe");
        q.setParameter("naviera", nav);
        q.setParameter("ruc", ruc.trim());
        List<NavieraConsol> l=q.getResultList();
        if(l.size()>0){
            return l.get(0);
        }else{
            return null;
        }
    }
}
