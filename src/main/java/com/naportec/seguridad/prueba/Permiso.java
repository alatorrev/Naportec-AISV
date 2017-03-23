/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.seguridad.prueba;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Permiso implements Serializable {

    private Integer numero;
    private Integer ancho;
    private Integer alto;
    private String nombre;
    private String pagina;
    private String icono;

    private List<Permiso> permisosListado;

    public Permiso(String nombre, String pagina, List<Permiso> permisosListado) {
        this.nombre = nombre;
        this.pagina = pagina;
        this.permisosListado = permisosListado;
    }
     public Permiso(String nombre, String pagina, List<Permiso> permisosListado,String icono) {
        this.nombre = nombre;
        this.pagina = pagina;
        this.permisosListado = permisosListado;
        this.icono=icono;
    }

    public Permiso(Integer numero, Integer ancho, Integer alto, String nombre, String pagina, List<Permiso> permisosListado) {
        this.numero = numero;
        this.ancho = ancho;
        this.alto = alto;
        this.nombre = nombre;
        this.pagina = pagina;
        this.permisosListado = permisosListado;
    }
     public Permiso(Integer numero, Integer ancho, Integer alto, String nombre, String pagina, List<Permiso> permisosListado,String icono) {
        this.numero = numero;
        this.ancho = ancho;
        this.alto = alto;
        this.nombre = nombre;
        this.pagina = pagina;
        this.permisosListado = permisosListado;
        this.icono=icono;
    }
    
     public Permiso(Integer numero, String nombre, String pagina, List<Permiso> permisosListado) {
        this.numero = numero;
        this.nombre = nombre;
        this.pagina = pagina;
        this.permisosListado = permisosListado;
    }

    public Permiso(Integer numero, Integer ancho, Integer alto, String nombre, String pagina) {
        this.numero = numero;
        this.ancho = ancho;
        this.alto = alto;
        this.nombre = nombre;
        this.pagina = pagina;
    }
  public Permiso(Integer numero, Integer ancho, Integer alto, String nombre, String pagina,String icono) {
        this.numero = numero;
        this.ancho = ancho;
        this.alto = alto;
        this.nombre = nombre;
        this.pagina = pagina;
        this.icono=icono;
    }
     public Permiso(Integer numero, String nombre, String pagina) {
        this.numero = numero;
        this.nombre = nombre;
        this.pagina = pagina;
    }
    public Permiso(String nombre, String pagina) {
        this.nombre = nombre;
        this.pagina = pagina;
    }

    public Permiso() {
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the pagina
     */
    public String getPagina() {
        return pagina;
    }

    /**
     * @param pagina the pagina to set
     */
    public void setPagina(String pagina) {
        this.pagina = pagina;
    }

    /**
     * @return the permisosListado
     */
    public List<Permiso> getPermisosListado() {
        if (permisosListado == null) {
            permisosListado = new LinkedList<Permiso>();
        }
        return permisosListado;
    }

    /**
     * @param permisosListado the permisosListado to set
     */
    public void setPermisosListado(List<Permiso> permisosListado) {
        this.permisosListado = permisosListado;
    }

    /**
     * @return the numero
     */
    public Integer getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getAncho() {
        return ancho;
    }

    public void setAncho(Integer ancho) {
        this.ancho = ancho;
    }

    public Integer getAlto() {
        return alto;
    }

    public void setAlto(Integer alto) {
        this.alto = alto;
    }

    /**
     * @return the icono
     */
    public String getIcono() {
        return icono;
    }

    /**
     * @param icono the icono to set
     */
    public void setIcono(String icono) {
        this.icono = icono;
    }
}
