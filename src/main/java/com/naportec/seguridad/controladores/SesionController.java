/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.seguridad.controladores;

import com.naportec.seguridad.prueba.Permiso;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 * Controlador de Sesion para manejar los datos del login y permisos. Este
 * controlador de Tipo Session permite que este disponible durante la sesion del
 * usuario
 * NO UTILIZADO - Se cambio por VistaNaportec 
 * @author Percho
 */
public class SesionController implements Serializable {

    private String user;
    private String password;
    private List<Permiso> permisosUsuario;

    public SesionController() {

    }

    public void login(ActionEvent evt) throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        if (isUser(getUser(), getPassword())) {
            String url = extContext.encodeActionURL(
                    context.getApplication().getViewHandler().getActionURL(context, "/seguridad/appPrincipal.jsf"));
            extContext.getSessionMap().put("auth_user", getUser());
            extContext.redirect(url);
            return;
        }
        FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Login", "Su intento para conectarse no tuvo éxito, inténtelo de nuevo."));
    }

    private boolean isUser(String user, String password) {
        if (user.trim().equals("feuser") && password.trim().equals("feuser")) {
            //Se traen los permisos de la base de datos 
            this.permisosUsuario = this.permisosUsuarioLogin(user, password);
            return true;
        } else {
            return false;
        }
    }

    public List<Permiso> permisosUsuarioLogin(String user, String pass) {
        List<Permiso> itemPermisosPrincipales = new LinkedList<Permiso>();
        //Administtracion-Usuarios-Privilegios
        List<Permiso> itemPermisos = new LinkedList<Permiso>();
        itemPermisos.add(new Permiso(4, "Rol", "../seguridad/rol/listado"));
        itemPermisos.add(new Permiso(5, "Usuarios Roles", "../seguridad/usuario_rol/listado"));
        itemPermisos.add(new Permiso(6, "Roles Grupos", "../seguridad/rol_grupo/listado"));
        itemPermisos.add(new Permiso(7, "Grupos", "../seguridad/grupo/listado"));
        itemPermisos.add(new Permiso(8, "Grupos Privilegios", "../seguridad/grupo_privilegio/listado"));
        itemPermisos.add(new Permiso(9, "Privilegios", "../seguridad/privilegio/listado"));
        itemPermisos.add(new Permiso(10, "Parámetros de Clave", "../seguridad/parametros/configuracion"));
        //Administracion-Usuarios
        List<Permiso> itemPermisos1 = new LinkedList<Permiso>();
        itemPermisos1.add(new Permiso(1, "Usuarios", "../seguridad/usuario/listado"));
        itemPermisos1.add(new Permiso(2, "Administración de Privilegios", "../seguridad/privilegio/administracion", itemPermisos));
        itemPermisosPrincipales.add(new Permiso(3, "Administración", "../seguridad/usuario/listado", itemPermisos1));
        return itemPermisosPrincipales;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the permisosUsuario
     */
    public List<Permiso> getPermisosUsuario() {
        return permisosUsuario;
    }

}
