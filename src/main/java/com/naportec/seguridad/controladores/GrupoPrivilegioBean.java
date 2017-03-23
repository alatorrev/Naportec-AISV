/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.seguridad.controladores;

import com.naportec.seguridad.entidades.SGroup;
import com.naportec.seguridad.entidades.SGroupright;
import com.naportec.seguridad.entidades.SRight;
import com.naportec.seguridad.logica.SGrouprightFacade;
import com.naportec.seguridad.logica.SRightFacade;
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
 * Clase controladro para manejar las acciones de la vista de Grupos Privilegios
 * @author Fernando
 */
public class GrupoPrivilegioBean extends UtilController<SGroupright> {

    private SGroup grupo;
    private SRight privilegio;
    private List<SRight> listarTodosPrivilegios;
    private SRight[] listaSeleccionados;

    @EJB
    private SGrouprightFacade grupoPrivilegioLogica;
    @EJB
    private SRightFacade privilegioLogica;

    public GrupoPrivilegioBean() {
        super(SGroupright.class);
    }

    public void onSelectRol(SelectEvent evt) {
        this.setListaSeleccionados(grupoPrivilegioLogica.listaPrivSeleccionados(getGrupo()));
        Mensaje.SUCESO("Grupo: " + getGrupo().getGrpShortdescription());
    }

    /**
     * Método para guardar los privilegios para un grupo
     * @param evt 
     */
    @Override
    public void guardarEntidad(ActionEvent evt) {
        try {
            grupoPrivilegioLogica.guardarGrupoPrivilegio(getListaSeleccionados(), getGrupo(), this.getCurrentloggeduser());
            Mensaje.SUCESO("Se han asignado los Privilegios al Grupo: " + getGrupo().getGrpShortdescription() + " Satisfactoriamente." + getListaSeleccionados().length);
        } catch (Exception ex) {
            Mensaje.ERROR("Error al intentar Guardar: " + ex.getMessage());
        }
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
     * @return the privilegio
     */
    public SRight getPrivilegio() {
        if (privilegio == null) {
            privilegio = new SRight();
        }
        return privilegio;
    }

    /**
     * @param privilegio the privilegio to set
     */
    public void setPrivilegio(SRight privilegio) {
        this.privilegio = privilegio;
    }

    /**
     * @return the listarTodosPrivilegios
     */
    public List<SRight> getListarTodosPrivilegios() {
        try {
            listarTodosPrivilegios = privilegioLogica.listarPorEstado(Estado.Activo);
            return listarTodosPrivilegios;
        } catch (Exception ex) {
            Logger.getLogger(GrupoPrivilegioBean.class.getName()).log(Level.SEVERE, null, ex);
            return new LinkedList<SRight>();
        }
    }

    /**
     * @param listarTodosPrivilegios the listarTodosPrivilegios to set
     */
    public void setListarTodosPrivilegios(List<SRight> listarTodosPrivilegios) {
        this.listarTodosPrivilegios = listarTodosPrivilegios;
    }

    /**
     * @return the listaSeleccionados
     */
    public SRight[] getListaSeleccionados() {
        return listaSeleccionados;
    }

    /**
     * @param listaSeleccionados the listaSeleccionados to set
     */
    public void setListaSeleccionados(SRight[] listaSeleccionados) {
        this.listaSeleccionados = listaSeleccionados;
    }

}
