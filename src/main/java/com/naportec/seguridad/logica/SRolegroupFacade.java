package com.naportec.seguridad.logica;

import com.naportec.seguridad.entidades.SGroup;
import com.naportec.seguridad.entidades.SRole;
import com.naportec.seguridad.entidades.SRolegroup;
import com.naportec.seguridad.entidades.SRolegroupPK;
import com.naportec.utilidades.logica.AbstractFacade;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class SRolegroupFacade extends AbstractFacade<SRolegroup> {

    /**
     * Inicialización de  nuestro Recurso de Datos
     */
    @PersistenceContext(unitName = "com.naportec_Aisv")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SRolegroupFacade() {
        super(SRolegroup.class);
    }

    public SGroup[] listaGruposSeleccionados(SRole rol) {
        Query q = getEntityManager().createNamedQuery("SRolegroup.buscarGrupo");
        q.setParameter("rol", rol);
        List<SGroup> lista = q.getResultList();
        SGroup[] enviar = new SGroup[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            enviar[i] = lista.get(i);
        }
        return enviar;
    }

    public void guardarRolGrupo(SGroup[] grupos, SRole rol, String usuario) throws Exception {
        this.eliminarSiNoExiste(this.listaGruposSeleccionados(rol), grupos, rol);
        for (int i = 0; i < grupos.length; i++) {
            SRolegroup entidad = new SRolegroup();
            SRolegroupPK pk = new SRolegroupPK(grupos[i].getGrpId(), rol.getRolId());
            entidad.setVersion(rol.getVersion());
            entidad.setHoraingresoAu(new Date());
            entidad.setUsuarioingresoAu(usuario);
            if (!existe(pk)) {
                entidad.setSRolegroupPK(pk);
                this.guardar(entidad);
            }
        }
    }

    public boolean existe(SRolegroupPK pk) {
        Query q = getEntityManager().createNamedQuery("SRolegroup.findPK");
        q.setParameter("rol", pk.getRolId());
        q.setParameter("grupo", pk.getGrpId());
        List<SRolegroup> lista = q.getResultList();
        if (lista.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    private void eliminarSiNoExiste(SGroup[] Anterior, SGroup[] Nuevo, SRole role) {
        boolean existe = false;
        //SI r no existe en el ARRAY se elimina de la base
        for (SGroup r : Anterior) {
            for (SGroup x : Nuevo) {
                if (r.getGrpId() == x.getGrpId()) {
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                this.eiminadoFisico(role, r);
            } else {
                existe = false;
            }
        }
    }

    private void eiminadoFisico(SRole role, SGroup grupo) {
        SRolegroupPK rgpk = new SRolegroupPK();
        rgpk.setRolId(role.getRolId());
        rgpk.setGrpId(grupo.getGrpId());
        SRolegroup rg = getEntityManager().find(SRolegroup.class, rgpk);
        getEntityManager().remove(getEntityManager().contains(rg) ? rg : getEntityManager().merge(rg));
    }

}
