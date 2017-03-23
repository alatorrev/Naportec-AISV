package com.naportec.aisv.logica;

import com.naportec.aisv.entidades.Consolidadora;
import com.naportec.utilidades.enumeraciones.Estado;
import com.naportec.utilidades.logica.AbstractFacade;
import java.io.Serializable;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Logica de Negocios de la entidad Consolidadora
 * @author Fernando
 */
@Stateless
public class ConsolidadoraFacade extends AbstractFacade<Consolidadora> implements Serializable {

    public ConsolidadoraFacade() {
        super(Consolidadora.class);
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
    
    public String getRucConsolidadora(String ruc){
        Query q=getEntityManager().createNamedQuery("Consolidadora.findByRucCons");
        q.setParameter("rucCons", ruc);
        q.setParameter("estado", Estado.Activo.name());
        if(q.getResultList().size()>0){
            return ruc;
        }else{
                return "NO";
        }
    }
}
