/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.seguridad.controladores;

import com.naportec.aisv.entidades.Permisos;
import com.naportec.aisv.logica.PermisoFacade;
import com.naportec.seguridad.prueba.MenuItemPrueba;
import com.naportec.seguridad.prueba.Permiso;
import com.naportec.utilidades.controladores.Mensaje;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Clase controlador que me permite manejar la acciones de la vista principal
 * luego de que ha sido logueado elusuario con exito
 *
 * @author Fernando
 */
public class VistaNaportec implements Serializable {

    private MenuModel model;
    @EJB
    private PermisoFacade permisoLogica;

    private Permisos permisoSelect;

    private List<String> paginasAcivas;

    public VistaNaportec() {

    }

    @PostConstruct
    public void init() {
        llenarMenu();
    }

    /**
     * Método para llenar el Menú de opciones
     */
    private void llenarMenu() {
        model = new DefaultMenuModel();
        DefaultMenuItem inicial = new DefaultMenuItem();
        inicial.setStyleClass("ui-menuitem ui-widget ui-corner-all menuInicial");
        model.addElement(inicial);
        for (Permiso p : permisosUsuarioLogin()) {
            DefaultSubMenu sub = new DefaultSubMenu(p.getNombre(), p.getIcono());
            sub.setStyleClass("ui-menuitem ui-widget ui-corner-all menuDato");
            for (Permiso x : p.getPermisosListado()) {
                MenuItemPrueba item = new MenuItemPrueba(x.getNombre());
                item.setIcon(x.getIcono());
                item.setStyle("font-size:10px");
                item.setCommand("#{vistaNaportec.viewPermisoSelect(" + x.getNumero() + ")}");
                item.setOncomplete("comprobarStatus()");
                item.setOnclick("reiniciarStatus()");
////                item.setOncomplete("PF('dlgPrincipal').show();");
//                item.setIncludeViewParams(true);
//                item.setForm("principal");
//                item.setOnclick("LoadHtml('#content-dialog','"+x.getPagina().substring(1)+".xhtml');PF('dlgPrincipal').show();");
//                item.setOnclick("LoadHtml('#content-dialog','"+x.getPagina()+".xhtml')");
//                item.setParam("pagina", x.getPagina());
//                item.setParam("ancho", x.getAncho());
//                item.setParam("alto", x.getAlto());
////                item.setUpdate(":mensaje :principal:dlgPrincipal");
//                item.setOncomplete("PF('dlgPrincipal').show();");
//                item.setUpdate(":pages");
                item.setAjax(true);
                sub.getElements().add(item);
            }
            if (p.getPermisosListado().size() > 0) {
                model.addElement(sub);
            }
        }

    }

    public void viewPermisoSelect(String codigo) {
        this.permisoSelect = this.permisoLogica.buscarPorCodigo(Integer.parseInt(codigo));
        RequestContext.getCurrentInstance().execute("cargarHtml('#page','" + this.getPermisoSelect().getPagina() + ".xhtml')");
    }

    public void redireccionar() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        String url = extContext.encodeActionURL(context.getApplication().getViewHandler().getActionURL(context, "/login.html"));
        try {
            extContext.redirect(url);
        } catch (IOException ex) {
            Mensaje.FATAL_DIALOG("Redireccion", "No se ha encotrado la pagina a la cual redireccionar");
        }
    }

    /**
     * Método para abrir cuandros de dialogo
     */
    public void viewDialog() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        String myName1 = params.get("pagina");
        Integer ancho = Integer.parseInt(params.get("ancho"));
        Integer alto = Integer.parseInt(params.get("alto"));
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("draggable", true);
        options.put("resizable", false);
        options.put("closable", true);
        options.put("contentHeight", alto);
        options.put("contentWidth", ancho);
        RequestContext.getCurrentInstance().openDialog(myName1, options, null);
    }

    /**
     * Método para obtener una lista de objetos permiso a partid de entidades
     * s_rigth
     *
     * @return
     */
    private List<Permiso> permisosUsuarioLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = auth.getName();
        List<Permiso> itemPermisos = new LinkedList<Permiso>();
        List<Permiso> itemPermisosAISV = new LinkedList<Permiso>();
        List<Permiso> itemPermisosConsulta = new LinkedList<Permiso>();

        List<Permisos> lista = permisoLogica.permisosUsuario(user, 0);
        for (Permisos p : lista) {
            itemPermisos.add(new Permiso(p.getCodigo(), p.getAnchoWindow(), p.getAltoWindow(), p.getAuthority(), p.getPagina(), p.getIconoWindow()));
//            System.out.println("WTF: " + p.getPagina());
        }
        lista = permisoLogica.permisosUsuario(user, 1);
        for (Permisos p : lista) {
            itemPermisosAISV.add(new Permiso(p.getCodigo(), p.getAnchoWindow(), p.getAltoWindow(), p.getAuthority(), p.getPagina(), p.getIconoWindow()));
//            System.out.println("WTF: " + p.getPagina());
        }
        lista = permisoLogica.permisosUsuario(user, 2);
        for (Permisos p : lista) {
            itemPermisosConsulta.add(new Permiso(p.getCodigo(), p.getAnchoWindow(), p.getAltoWindow(), p.getAuthority(), p.getPagina(), p.getIconoWindow()));
//            System.out.println("WTF: " + p.getPagina());
        }
        Permiso admin = new Permiso("ADMINISTRACION", "", itemPermisos, "admin");
        Permiso aisv = new Permiso("AISV", "", itemPermisosAISV, "aisv");
        Permiso consulta = new Permiso("CONSULTA", "", itemPermisosConsulta, "consulta");

        List<Permiso> itemPermisosPrincipales = new LinkedList<Permiso>();
        itemPermisosPrincipales.add(admin);
        itemPermisosPrincipales.add(aisv);
        itemPermisosPrincipales.add(consulta);

        return itemPermisosPrincipales;
    }

    /**
     * @return the model
     */
    public MenuModel getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(MenuModel model) {
        this.model = model;
    }

    /**
     * @return the permisoSelect
     */
    public Permisos getPermisoSelect() {
        return permisoSelect;
    }

    /**
     * @param permisoSelect the permisoSelect to set
     */
    public void setPermisoSelect(Permisos permisoSelect) {
        this.permisoSelect = permisoSelect;
    }

    /**
     * @return the paginasAcivas
     */
    public List<String> getPaginasAcivas() {
        if (paginasAcivas == null) {
            paginasAcivas = new LinkedList<>();
        }
        return paginasAcivas;
    }

}
