/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.seguridad.prueba;

import org.primefaces.model.menu.DefaultMenuItem;
/**
 * Una Clase que extiende de DefaulMenu pero contiene ademas un objeto de tipo
 * de dato permiso
 *
 * @author Percho
 */
public class MenuItemPrueba extends DefaultMenuItem {

    private Permiso permiso;

    public MenuItemPrueba() {
    }

    public MenuItemPrueba(Object value) {
        super(value);
    }

    public MenuItemPrueba(Object value, String icon) {
        super(value, icon);
    }

    public MenuItemPrueba(Object value, String icon, String url) {
        super(value, icon, url);
    }

    /**
     * @return the permiso
     */
    public Permiso getPermiso() {
        return permiso;
    }

    /**
     * @param permiso the permiso to set
     */
    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }
}
