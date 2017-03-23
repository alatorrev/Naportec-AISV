package com.naportec.aisv.logica;

import com.naportec.aisv.entidades.Puerto;
import com.naportec.utilidades.enumeraciones.Estado;
import com.naportec.utilidades.logica.AbstractFacade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Logica de Negocios de la entidad Puerto
 * @author Fernando
 */
@Stateless
public class PuertoFacade extends AbstractFacade<Puerto> implements Serializable {

    /**
     * Inicialización de  nuestro Recurso de Datos
     */
    @PersistenceContext(unitName = "com.naportec_Aisv")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PuertoFacade() {
        super(Puerto.class);
    }

    /**
     * Método para buscar Puerto por nombre y que este activo
     * @param nombre
     * @return 
     */
    public Puerto buscarPorNombre(String nombre) {
        if (nombre != null) {
            nombre = nombre.toUpperCase();
        } else {
            nombre = "";
        }
        Query q = getEntityManager().createNamedQuery("Puerto.findByNombrePuer");
        q.setParameter("nombrePuer", nombre);
        q.setParameter("estado", Estado.Activo.name());
        if (q.getResultList().size() > 0) {
            return (Puerto) q.getResultList().get(0);
        } else {
            return null;
        }
    }

    /**
     * Método para buscar un puerto por nombre o por codigo y que este activo
     * @param nombre
     * @return 
     */
    public Puerto buscarPorNombrePuertoConverter(String nombre) {
        if (nombre != null) {
            nombre = nombre.toUpperCase();
        } else {
            nombre = "";
        }
        Query q = getEntityManager().createNamedQuery("Puerto.findByNombrePuerConverter");
        q.setParameter("nomPuer", nombre);
        q.setParameter("codPuer", nombre);
        q.setParameter("estado", Estado.Activo.name());
        if (q.getResultList().size() > 0) {
            return (Puerto) q.getResultList().get(0);
        } else {
            return null;
        }
    }

    /**
     * Método para listar puertos que comiencen con el parametro (dato)
     * @param dato
     * @return 
     */
    public List<Puerto> listaPuertosCoincidencia(String dato) {
        dato = dato + "%";
        Query q = getEntityManager().createNamedQuery("Puerto.findByCoincidence");
        q.setParameter("puerto", dato);
        q.setParameter("estado", Estado.Activo.name());
        q.setMaxResults(6);
        return q.getResultList();
    }

}
