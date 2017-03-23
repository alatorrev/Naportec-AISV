package com.naportec.aisv.logica;

import com.naportec.aisv.entidades.Permisos;
import com.naportec.utilidades.logica.AbstractFacade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Logica de Negocios de la entidad Permisos
 * @author Fernando
 */
@Stateless
public class PermisoFacade extends AbstractFacade<Permisos> implements Serializable {

    /**
     * Inicialización de  nuestro Recurso de Datos
     */
    @PersistenceContext(unitName = "com.naportec_Aisv")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PermisoFacade() {
        super(Permisos.class);
    }

    /**
     * Método para obtener una lista de permisos desde un nodo padre
     * @param userName
     * @param nodo
     * @return 
     */
    public List<Permisos> permisosUsuario(String userName, int nodo) {
        Query q = getEntityManager().createNamedQuery("Permisos.usuario");
        q.setParameter("user", userName);
        q.setParameter("nodo", nodo);
        return q.getResultList();
    }

}
