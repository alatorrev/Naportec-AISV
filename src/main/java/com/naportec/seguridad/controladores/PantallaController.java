/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.seguridad.controladores;

import com.naportec.seguridad.prueba.MenuItemPrueba;
import com.naportec.seguridad.prueba.Permiso;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;

/**
 * Controlador View para manejar los procesos de la pantalla principal Los datos
 * de controlador tipo View solo permanecen mientras no se salga de la misma
 * pantalla.
 *
 * NO UTILIZADA - Este controlador se cambio por VistaNaportec
 * @author Percho
 */
public class PantallaController implements Serializable {

    /**
     * Permite tener un valor del tab que cerramos y seleccionamos al dar clik
     * en el arbol
     */
    private int indiceTabs;
    /**
     * Es un listado de los permisos de usuario que seran cargados al momento de
     * efectuar el login
     */
    private List<Permiso> permisosUsuario;
    /**
     * Es un listado de los permisos actuales(Los tabs que se hayan abierto
     * luego de seleccionar en el arbol).
     */
    private List<Permiso> permisosTabActuales;
    /**
     * Objeto que define un modelo para colocar el menu hotizontal.
     */
    private MenuModel menu;
    /**
     * Saludo al usuario que ingresa.
     */
    private String detalle;
    /**
     * Este es un modelo de datos de el menu Arbol principal.
     */
    private TreeNode raiz;
    /**
     * Este es el objeto nodo que permite la seleccion en el menu arbol
     * principal.
     */
    private TreeNode seleccionado;

    public PantallaController() {
        this.detalle = "Bienvenido";
        this.iniciarMenuPrincipal();
    }

    private List<Permiso> permisosUsuarioLogin() {
        List<Permiso> itemPermisosPrincipales = new LinkedList<Permiso>();
        //Administtracion-Usuarios-Privilegios
        List<Permiso> itemPermisos = new LinkedList<Permiso>();
        itemPermisos.add(new Permiso(5, "Rol", "seguridad/rol/listado"));
        itemPermisos.add(new Permiso(6, "Usuarios Roles", "seguridad/usuario_rol/listado"));
        itemPermisos.add(new Permiso(7, "Roles Grupos", "seguridad/rol_grupo/listado"));
        itemPermisos.add(new Permiso(8, "Grupos", "seguridad/grupo/listado"));
        itemPermisos.add(new Permiso(9, "Grupos Privilegios", "seguridad/grupo_privilegio/listado"));
        itemPermisos.add(new Permiso(10, "Privilegios", "seguridad/privilegio/listado"));
        itemPermisos.add(new Permiso(11, "Parámetros de Clave", "seguridad/parametros/configuracion"));
        //Administracion-Usuarios
        List<Permiso> itemPermisos1 = new LinkedList<Permiso>();
        itemPermisos1.add(new Permiso(1, "Usuarios", "seguridad/usuario/listado"));
         itemPermisos1.add(new Permiso(2, "Auditoría", "seguridad/auditoria/listado"));
        itemPermisos1.add(new Permiso(3, "Administración de Privilegios", "seguridad/privilegio/administracion", itemPermisos));
        itemPermisosPrincipales.add(new Permiso(4, "Administración", "seguridad/usuario/listado", itemPermisos1));
        return itemPermisosPrincipales;
    }

    /**
     * Metodo para inicializar el MenuBar principal mediante la lisat de
     * permisos extraida del controlador de login
     */
    private void iniciarMenuPrincipal() {
        this.permisosUsuario = permisosUsuarioLogin();
        menu = new DefaultMenuModel();
        for (Permiso p : this.permisosUsuario) {
            MenuItemPrueba item = new MenuItemPrueba(p.getNombre());
            item.setCommand("#{pantallaController.iniciarMenuArbol('" + p.getNombre() + "')}");
            item.setUpdate(":formPrincipal:arbol :formPrincipal:detalle");
            
            menu.addElement(item);
        }
        MenuItemPrueba item = new MenuItemPrueba("Salir");
        item.setCommand("#{loginMgmtBean.logout}");
        menu.addElement(item);
    }

    /**
     * Metodo para inicar el arbol de la izquierda cada vez que se de click en
     * el menuBar principal
     *
     * @param m Es un identificador de la opcion escogida MenuPrincipal
     */
    public void iniciarMenuArbol(Object m) {
        Permiso aux = null;
        for (Permiso p : this.permisosUsuario) {
            if (p.getNombre().equals(m)) {
                aux = p;
                break;
            }
        }
        raiz = new DefaultTreeNode("Root", null);
        TreeNode node0 = new DefaultTreeNode(aux, raiz);
        for (Permiso p : aux.getPermisosListado()) {
            TreeNode node1 = new DefaultTreeNode(p, node0);
            if (p.getPermisosListado().size() > 0) {
                for (Permiso y : p.getPermisosListado()) {
                    TreeNode nodex = new DefaultTreeNode(y, node1);
                }
            }
        }
        //Colocar Detalle en pantalla
        this.detalle = "Ingreso en: " + aux.getNombre();
        System.out.println("Ingreso en: " + aux.getNombre());
    }

    /**
     * Evento de seleccion de un Nodo del arbol; sirve para colocar los tabs en
     * la aplicacion segun correspondan
     *
     * @param event
     */
    public void onNodeSelect(NodeSelectEvent event) {
        Permiso aux = (Permiso) event.getTreeNode().getData();
        boolean ver = false;
        for (int i = 0; i < getPermisosTabActuales().size(); i++) {
            if (aux.getNumero() == getPermisosTabActuales().get(i).getNumero()) {
                ver = true;
                indiceTabs = i;
            }
        }
        if (ver == false) {
            getPermisosTabActuales().add(aux);
            indiceTabs = getPermisosTabActuales().size() - 1;
        }
    }

    /**
     * Metodo que nos permite eliminar un tab de la lista de memoria
     */
    public void tabClose() {
        getPermisosTabActuales().remove(indiceTabs);
    }
    /**
     * @return the indiceTabs
     */
    public int getIndiceTabs() {
        return indiceTabs;
    }

    /**
     * @param indiceTabs the indiceTabs to set
     */
    public void setIndiceTabs(int indiceTabs) {
        this.indiceTabs = indiceTabs;
    }

    /**
     * @return the permisosUsuario
     */
    public List<Permiso> getPermisosUsuario() {
        return permisosUsuario;
    }

    /**
     * @param permisosUsuario the permisosUsuario to set
     */
    public void setPermisosUsuario(List<Permiso> permisosUsuario) {
        this.permisosUsuario = permisosUsuario;
    }

    /**
     * @return the permisosTabActuales
     */
    public List<Permiso> getPermisosTabActuales() {
        if (this.permisosTabActuales == null) {
            this.permisosTabActuales = new LinkedList<Permiso>();
        }
        return permisosTabActuales;
    }

    /**
     * @param permisosTabActuales the permisosTabActuales to set
     */
    public void setPermisosTabActuales(List<Permiso> permisosTabActuales) {
        this.permisosTabActuales = permisosTabActuales;
    }

    /**
     * @return the menu
     */
    public MenuModel getMenu() {
        return menu;
    }

    /**
     * @param menu the menu to set
     */
    public void setMenu(MenuModel menu) {
        this.menu = menu;
    }

    /**
     * @return the detalle
     */
    public String getDetalle() {
        return detalle;
    }

    /**
     * @param detalle the detalle to set
     */
    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    /**
     * @return the raiz
     */
    public TreeNode getRaiz() {
        return raiz;
    }

    /**
     * @param raiz the raiz to set
     */
    public void setRaiz(TreeNode raiz) {
        this.raiz = raiz;
    }

    /**
     * @return the seleccionado
     */
    public TreeNode getSeleccionado() {
        return seleccionado;
    }

    /**
     * @param seleccionado the seleccionado to set
     */
    public void setSeleccionado(TreeNode seleccionado) {
        this.seleccionado = seleccionado;
    }

}
