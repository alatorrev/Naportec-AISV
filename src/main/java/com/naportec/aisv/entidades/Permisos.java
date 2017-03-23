/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.aisv.entidades;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Fernando
 */
@Entity
@Table(name = "permisos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Permisos.findAll", query = "SELECT p FROM Permisos p"),
    @NamedQuery(name = "Permisos.usuario", query = "SELECT p FROM Permisos p where p.username=:user AND p.nodopadre=:nodo"),
    @NamedQuery(name = "Permisos.findByUsername", query = "SELECT p FROM Permisos p WHERE p.username = :username"),
    @NamedQuery(name = "Permisos.findByAuthority", query = "SELECT p FROM Permisos p WHERE p.authority = :authority"),
    @NamedQuery(name = "Permisos.findByPagina", query = "SELECT p FROM Permisos p WHERE p.pagina = :pagina"),
    @NamedQuery(name = "Permisos.findByNodopadre", query = "SELECT p FROM Permisos p WHERE p.nodopadre = :nodopadre")})
public class Permisos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "username")
    private String username;
    @Column(name = "authority")
    private String authority;
    @Column(name = "pagina")
    private String pagina;
    @Column(name = "nodopadre")
    private BigInteger nodopadre;
    @Column(name = "alto_window")
    private Integer altoWindow;
    @Column(name = "ancho_window")
    private Integer anchoWindow;
    @Column(name = "icono_window")
    private String iconoWindow;

    public Permisos() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getPagina() {
        return pagina;
    }

    public void setPagina(String pagina) {
        this.pagina = pagina;
    }

    public BigInteger getNodopadre() {
        return nodopadre;
    }

    public void setNodopadre(BigInteger nodopadre) {
        this.nodopadre = nodopadre;
    }

    /**
     * @return the codigo
     */
    public Integer getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the altoWindow
     */
    public Integer getAltoWindow() {
        return altoWindow;
    }

    /**
     * @param altoWindow the altoWindow to set
     */
    public void setAltoWindow(Integer altoWindow) {
        this.altoWindow = altoWindow;
    }

    /**
     * @return the anchoWindow
     */
    public Integer getAnchoWindow() {
        return anchoWindow;
    }

    /**
     * @param anchoWindow the anchoWindow to set
     */
    public void setAnchoWindow(Integer anchoWindow) {
        this.anchoWindow = anchoWindow;
    }

    /**
     * @return the iconoWindow
     */
    public String getIconoWindow() {
        return iconoWindow;
    }

    /**
     * @param iconoWindow the iconoWindow to set
     */
    public void setIconoWindow(String iconoWindow) {
        this.iconoWindow = iconoWindow;
    }

}
