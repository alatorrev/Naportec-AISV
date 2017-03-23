package com.naportec.aisv.logica;

import com.naportec.aisv.entidades.Contacto;
import com.naportec.aisv.entidades.Solicitud;
import com.naportec.utilidades.enumeraciones.Estado;
import com.naportec.utilidades.logica.AbstractFacade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Logica de Negocios de la entidad Contacto
 * @author Fernando
 */
@Stateless
public class ContactoFacade extends AbstractFacade<Contacto> implements Serializable {

    public ContactoFacade() {
        super(Contacto.class);
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
     * Método para obtener el listado de contactos a partir de una Solicitud
     * @param s
     * @return 
     */
    public List<Contacto> listarContactos(Solicitud s) {
        Query q = getEntityManager().createNamedQuery("Contacto.buscarPorSolicitud");
        q.setParameter("soli", s);
        q.setParameter("estado", Estado.Activo.name());
        return q.getResultList();
    }

}
