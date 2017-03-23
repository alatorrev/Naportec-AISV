package com.naportec.seguridad.logica;

import com.naportec.seguridad.entidades.SGroup;
import com.naportec.seguridad.entidades.SGroupright;
import com.naportec.seguridad.entidades.SGrouprightPK;
import com.naportec.seguridad.entidades.SRight;
import com.naportec.utilidades.logica.AbstractFacade;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
/**
 * Logica de Negocios de la entidad SGroupright
 * @author Fernando
 */
@Stateless
public class SGrouprightFacade extends AbstractFacade<SGroupright> {

    private final static Logger log = Logger.getLogger(SGroupright.class.getName());

    /**
     * Inicialización de  nuestro Recurso de Datos
     */
    @PersistenceContext(unitName = "com.naportec_Aisv")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    public SGrouprightFacade() {
        super(SGroupright.class);
    }

    /**
     * Método para listar los privilegios de un grupo
     * @param grupo
     * @return 
     */
    public SRight[] listaPrivSeleccionados(SGroup grupo) {
        Query q = getEntityManager().createNamedQuery("SGroupright.buscarPrivilegio");
        q.setParameter("grupo", grupo);
        List<SRight> lista = q.getResultList();
        SRight[] enviar = new SRight[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            enviar[i] = lista.get(i);
        }
        return enviar;
    }

    /**
     * Método para listar los SGroupright a partir de un grupo
     * @param grupo
     * @return 
     */
    public SGroupright[] listarPrivilegiosGrupo(SGroup grupo) {
        Query q = getEntityManager().createNamedQuery("SGroupright.buscarPrivilegioGrupo");
        q.setParameter("grupo", grupo);
        List<SGroupright> lista = q.getResultList();
        SGroupright[] enviar = new SGroupright[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            enviar[i] = lista.get(i);
        }
        return enviar;
    }

    /**
     * Método para guardar lso prinvilegios en un grupo
     * @param listaSeleccionados
     * @param grupo
     * @param usuario
     * @throws Exception 
     */
    public void guardarGrupoPrivilegio(SRight[] listaSeleccionados, SGroup grupo, String usuario) throws Exception {
       
         this.eliminarSiNoExiste(this.listaPrivSeleccionados(grupo), listaSeleccionados, grupo);
        
        for (int i = 0; i < listaSeleccionados.length; i++) {
            log.log(Level.INFO, "ENTRO A GUARDAR:{0}", String.valueOf(listaSeleccionados.length));
            SGrouprightPK pk = new SGrouprightPK(grupo.getGrpId(), listaSeleccionados[i].getRigId());
            SGroupright entidad = new SGroupright();
            entidad.setVersion(grupo.getVersion());
            entidad.setHoraingresoAu(new Date());
            entidad.setUsuarioingresoAu(usuario);
            if (!existe(pk)) {
                entidad.setSGrouprightPK(pk);
                this.guardar(entidad);
            }
        }
    }

    /**
     * Método para saber si ya existe un grupo-privilegio creado
     * @param pk
     * @return 
     */
    public boolean existe(SGrouprightPK pk) {
        Query q = getEntityManager().createNamedQuery("SGroupright.findPK");
        q.setParameter("pk", pk);
        List<SGroupright> lista = q.getResultList();
        if (lista.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Método para eliminar sino existe en el listado
     * @param Anterior
     * @param Nuevo
     * @param g 
     */
    private void eliminarSiNoExiste(SRight[] Anterior, SRight[] Nuevo, SGroup g) {
        boolean existe = false;
        //SI r no existe en el ARRAY se elimina de la base
        for (SRight r : Anterior) {
            for (SRight x : Nuevo) {
                if (r.getRigId() == x.getRigId()) {
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                this.eiminadoFisico(g, r);
            } else {
                existe = false;
            }
        }
    }
    /**
     * Método para hacer eliminado fisico de un registro grupo-privilegio
     * @param grupo
     * @param privilegio 
     */
    private void eiminadoFisico(SGroup grupo,SRight privilegio){
    	SGrouprightPK rgpk = new SGrouprightPK();
    	rgpk.setGrpId(grupo.getGrpId());
    	rgpk.setRigId(privilegio.getRigId());
    	SGroupright gr = getEntityManager().find(SGroupright.class, rgpk);
        getEntityManager().remove(getEntityManager().contains(gr) ? gr : getEntityManager().merge(gr));
    }
}
