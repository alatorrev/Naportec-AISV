package com.naportec.aisv.logica;

import com.naportec.aisv.entidades.PropositoCarga;
import com.naportec.utilidades.logica.AbstractFacade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Logica de Negocios de la entidad Proposito de Carga
 * @author Fernando
 */
@Stateless
public class TipoPropositoFacade extends AbstractFacade<PropositoCarga> implements Serializable {

    /**
     * Inicialización de  nuestro Recurso de Datos
     */
    @PersistenceContext(unitName = "com.naportec_Aisv")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoPropositoFacade() {
        super(PropositoCarga.class);
    }

    /**
     * Método para buscar propósito de carga desde el codigo principal
     * @param codigoPrincipal
     * @return
     * @throws Exception 
     */
    public PropositoCarga buscarPorCodigoPrin(String codigoPrincipal) throws Exception {
        Query q = getEntityManager().createNamedQuery("PropositoCarga.findByCodigoPrinProp");
        q.setParameter("codigoPrinProp", codigoPrincipal);
        List<PropositoCarga> lista = q.getResultList();
        if (lista.size() > 0) {
            return lista.get(0);
        } else {
            return null;
        }
    }

    /**
     * Método para listar los propósitos de carga segun si son para importación,
     * exportación y también si son carga suelta, contenerizada y para consolidación
     * @param tipo_mov
     * @param cont
     * @param suel
     * @param consol
     * @return
     * @throws Exception 
     */
    public List<PropositoCarga> buscarPropositos(String tipo_mov, boolean cont, boolean suel, boolean consol) throws Exception {
        StringBuilder sb = new StringBuilder("SELECT p FROM PropositoCarga p");
        if (cont == true || suel == true || consol == true) {
            sb.append(" WHERE ");
        }
        if (cont == true) {
            sb.append(" p.contenerizadaProp = true ");
        }
        if (suel == true) {
            sb.append(" p.sueltaProp = true ");
        }
        if (consol == true) {
            sb.append(" p.consolidadaProp = true ");
        }
        if (tipo_mov != null) {
            if (tipo_mov.trim().length() == 1) {
                if (!tipo_mov.trim().equals("E")) {
                    if (!tipo_mov.trim().equals("I")) {
                        tipo_mov = "E";
                    }
                }
            } else {
                tipo_mov = "E";
            }
        } else {
            tipo_mov = "E";
        }
        if (cont == true || suel == true || consol == true) {
            sb.append(" AND p.tipoProp=:tipo ORDER BY p.descripcionProp ASC");
        }
        Query q = getEntityManager().createQuery(sb.toString());
        q.setParameter("tipo", tipo_mov);
        return q.getResultList();
    }

}
