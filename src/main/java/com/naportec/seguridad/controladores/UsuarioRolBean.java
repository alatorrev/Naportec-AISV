package com.naportec.seguridad.controladores;

import com.naportec.seguridad.entidades.SRole;
import com.naportec.seguridad.entidades.SUser;
import com.naportec.seguridad.entidades.SUserrole;
import com.naportec.seguridad.logica.SRoleFacade;
import com.naportec.seguridad.logica.SUserroleFacade;
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
 * Clase controlador que nso permite manejar las acciones de la vista usuario-rol
 * @author Fernando
 */
public class UsuarioRolBean extends UtilController<SUserrole> {

    private SRole rol;
    private SUser user;
    private List<SRole> listaTodos;
    private SRole[] listaSeleccionados;
    @EJB
    private SUserroleFacade userRolLogica;
    @EJB
    private SRoleFacade rolLogica;

    public UsuarioRolBean() {
        super(SUserrole.class);
    }

    /**
     * Método para seleccionar un rol
     * @param evt 
     */
    public void onSelectUser(SelectEvent evt) {
        this.setListaSeleccionados(userRolLogica.listaRolSeleccionados(getUser()));
    }

    /**
     * Método para guardar entidad
     * @param evt 
     */
    @Override
    public void guardarEntidad(ActionEvent evt) {
        try {
            userRolLogica.guardarUsuarioRol(getListaSeleccionados(), getUser(), this.getCurrentloggeduser());
            Mensaje.SUCESO("Se han asignado los Roles al Usuario: " + getUser().getUsrFirstname() + " Satisfactoriamente.");
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
     * @return the user
     */
    public SUser getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(SUser user) {
        this.user = user;
    }

    /**
     * @return the listaTodos
     */
    public List<SRole> getListaTodos() {
        try {
            listaTodos = rolLogica.listarPorEstado(Estado.Activo);
            return listaTodos;
        } catch (Exception ex) {
            Logger.getLogger(UsuarioRolBean.class.getName()).log(Level.SEVERE, null, ex);
            return new LinkedList<SRole>();
        }
    }

    /**
     * @param listaTodos the listaTodos to set
     */
    public void setListaTodos(List<SRole> listaTodos) {
        this.listaTodos = listaTodos;
    }

    /**
     * @return the listaSeleccionados
     */
    public SRole[] getListaSeleccionados() {
        return listaSeleccionados;
    }

    /**
     * @param listaSeleccionados the listaSeleccionados to set
     */
    public void setListaSeleccionados(SRole[] listaSeleccionados) {
        this.listaSeleccionados = listaSeleccionados;
    }

}
