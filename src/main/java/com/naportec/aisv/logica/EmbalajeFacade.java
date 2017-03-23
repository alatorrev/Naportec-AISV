package com.naportec.aisv.logica;

import com.naportec.aisv.entidades.Embalaje;
import com.naportec.utilidades.logica.AbstractFacade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Logica de Negocios de la entidad Embalaje
 * @author Fernando
 */
@Stateless
public class EmbalajeFacade extends AbstractFacade<Embalaje> implements Serializable{
    
    public EmbalajeFacade() {
        super(Embalaje.class);
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
     * Método para obtener una entidad Embalaje apartir de el codigo principal
     * @param codigoPrincipal
     * @return
     * @throws Exception 
     */
    public Embalaje buscarPorCodigoPrin(String codigoPrincipal) throws Exception{
            Query q=getEntityManager().createNamedQuery("Embalaje.findByCodigoPrinEmba");
            q.setParameter("codigoPrinEmba", codigoPrincipal);
            List<Embalaje> lista=q.getResultList();
            if(lista.size()>0){
                return lista.get(0);
            }else{
                return null;
            }
    }

}
