package com.naportec.seguridad.logica;

import com.naportec.seguridad.entidades.SRole;
import com.naportec.seguridad.entidades.SUser;
import com.naportec.seguridad.entidades.SUserrole;
import com.naportec.seguridad.entidades.SUserrolePK;
import com.naportec.utilidades.logica.AbstractFacade;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class SUserroleFacade extends AbstractFacade<SUserrole> {

    /**
     * Inicialización de  nuestro Recurso de Datos
     */
    @PersistenceContext(unitName = "com.naportec_Aisv")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SUserroleFacade() {
        super(SUserrole.class);
    }

    public SRole[] listaRolSeleccionados(SUser user) {
        Query q = getEntityManager().createNamedQuery("SUserrole.findByUsrId");
        q.setParameter("usrId", user.getUsrId());
        List<SRole> lista = q.getResultList();
        SRole[] enviar = new SRole[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            enviar[i] = lista.get(i);
        }
        return enviar;
    }

    public List<SRole> listaRolUsuario(SUser user) {
        Query q = getEntityManager().createNamedQuery("SUserrole.findByUsrId");
        q.setParameter("usrId", user.getUsrId());
        return q.getResultList();
    }

    public void guardarUsuarioRol(SRole[] rol, SUser user, String usuario) throws Exception {
        this.eliminarSiNoExiste(this.listaRolSeleccionados(user), rol, user);
        for (int i = 0; i < rol.length; i++) {
            SUserrole entidad = new SUserrole();
            entidad.setVersion(0);
            entidad.setHoraingresoAu(new Date());
            entidad.setUsuarioingresoAu(usuario);
            SUserrolePK pk = new SUserrolePK(user.getUsrId(), rol[i].getRolId());
            if (!existe(pk)) {
                entidad.setSUserrolePK(pk);
                this.guardar(entidad);
            }
        }
    }

    private boolean existe(SUserrolePK pk) {
        Query q = getEntityManager().createNamedQuery("SUserrole.findPK");
        q.setParameter("pk", pk);
        List<SUserrole> lista = q.getResultList();
        if (lista.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    private void eliminarSiNoExiste(SRole[] rolAnterior, SRole[] rolNuevo, SUser user) {
        boolean existe = false;
        //SI r no existe en el ARRAY se elimina de la base
        for (SRole r : rolAnterior) {
            for (SRole x : rolNuevo) {
                if (r.getRolId() == x.getRolId()) {
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                this.eiminadoFisico(user, r);
            } else {
                existe = false;
            }
        }
    }

    private void eiminadoFisico(SUser user, SRole rol) {
        SUserrolePK urpk = new SUserrolePK();
        urpk.setRolId(rol.getRolId());
        urpk.setUsrId(user.getUsrId());
        SUserrole ur = getEntityManager().find(SUserrole.class, urpk);
        getEntityManager().remove(getEntityManager().contains(ur) ? ur : getEntityManager().merge(ur));
    }

}
