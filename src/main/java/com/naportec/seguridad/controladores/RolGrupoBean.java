package com.naportec.seguridad.controladores;

import com.naportec.seguridad.entidades.SGroup;
import com.naportec.seguridad.entidades.SRole;
import com.naportec.seguridad.entidades.SRolegroup;
import com.naportec.seguridad.logica.SGroupFacade;
import com.naportec.seguridad.logica.SRolegroupFacade;
import com.naportec.utilidades.controladores.Mensaje;
import com.naportec.utilidades.controladores.UtilController;
import com.naportec.utilidades.enumeraciones.Estado;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import org.primefaces.event.SelectEvent;
/**
 * Clase controlador que nos permite manejar las acciones de lavista de rol grupo
 * @author Fernando
 */
public class RolGrupoBean extends UtilController<SRolegroup> {

    private SRole rol;
    private SGroup grupo;
    private List<SGroup> listaTodosGrupos;
    private SGroup[] listaSeleccionados;
    @EJB
    private SRolegroupFacade rolGrupoLogica;
    @EJB
    private SGroupFacade grupoLogica;

    public RolGrupoBean() {
        super(SRolegroup.class);
    }

    /**
     * Evento de seleccion de rol el cual mepermite ver grupos que tiene 
     * @param evt 
     */
    public void onSelectRol(SelectEvent evt) {
        this.listaSeleccionados = rolGrupoLogica.listaGruposSeleccionados(getRol());
    }

    /**
     * Método pra guardar los grupos que pertenecen a un rol
     * @param evt 
     */
    @Override
    public void guardarEntidad(ActionEvent evt) {
        try {
            rolGrupoLogica.guardarRolGrupo(listaSeleccionados, rol, this.getCurrentloggeduser());
            Mensaje.SUCESO("Se han asignado los Grupos al Rol: " + rol.getRolShortdescription() + " Satisfactoriamente.");
        } catch (Exception ex) {
            Mensaje.ERROR("Error al intentar Guardar: " + ex.getMessage());
        }
    }

    /**
     * @return the rol
     */
    public SRole getRol() {
        if (rol == null) {
            rol = new SRole();
        }
        return rol;
    }

    /**
     * @param rol the rol to set
     */
    public void setRol(SRole rol) {
        this.rol = rol;
    }

    /**
     * @return the grupo
     */
    public SGroup getGrupo() {
        if (grupo == null) {
            grupo = new SGroup();
        }
        return grupo;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(SGroup grupo) {
        this.grupo = grupo;
    }

    /**
     * @return the listaTodosGrupos
     */
    public List<SGroup> getListaTodosGrupos() {
        try {
            listaTodosGrupos = grupoLogica.listarPorEstado(Estado.Activo);
            return listaTodosGrupos;
        } catch (Exception ex) {
            Logger.getLogger(RolGrupoBean.class.getName()).log(Level.SEVERE, null, ex);
            return new LinkedList<SGroup>();
        }

    }

    /**
     * @param listaTodosGrupos the listaTodosGrupos to set
     */
    public void setListaTodosGrupos(List<SGroup> listaTodosGrupos) {
        this.listaTodosGrupos = listaTodosGrupos;
    }

    /**
     * @return the listaSeleccionados
     */
    public SGroup[] getListaSeleccionados() {
        return listaSeleccionados;
    }

    /**
     * @param listaSeleccionados the listaSeleccionados to set
     */
    public void setListaSeleccionados(SGroup[] listaSeleccionados) {
        this.listaSeleccionados = listaSeleccionados;
    }
}
