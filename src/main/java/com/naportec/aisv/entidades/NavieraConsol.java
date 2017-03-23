/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.aisv.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author MZambrano1
 */
@Entity
@Table(name = "aisv_naviera_consol")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NavieraConsol.findAll", query = "SELECT n FROM NavieraConsol n"),
    @NamedQuery(name = "NavieraConsol.existe", query = "SELECT n FROM NavieraConsol n WHERE n.codigoNavi=:naviera AND n.codigoCons.rucCons=:ruc"),
    @NamedQuery(name = "NavieraConsol.findByCodigoNaco", query = "SELECT n FROM NavieraConsol n WHERE n.codigoNaco = :codigoNaco")})
public class NavieraConsol implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_naco")
    private Long codigoNaco;
    @JoinColumn(name = "codigo_navi", referencedColumnName = "codigo_navi")
    @ManyToOne(optional = false)
    private Naviera codigoNavi;
    @JoinColumn(name = "codigo_cons", referencedColumnName = "codigo_cons")
    @ManyToOne(cascade = CascadeType.ALL)
    private Consolidadora codigoCons;
    @Transient
    private String estado;

    public NavieraConsol() {
    }

    public NavieraConsol(Long codigoNaco) {
        this.codigoNaco = codigoNaco;
    }

    public Long getCodigoNaco() {
        return codigoNaco;
    }

    public void setCodigoNaco(Long codigoNaco) {
        this.codigoNaco = codigoNaco;
    }

    public Naviera getCodigoNavi() {
        return codigoNavi;
    }

    public void setCodigoNavi(Naviera codigoNavi) {
        this.codigoNavi = codigoNavi;
    }

    public Consolidadora getCodigoCons() {
        if(codigoCons==null){
            codigoCons=new Consolidadora();
        }
        return codigoCons;
    }

    public void setCodigoCons(Consolidadora codigoCons) {
        this.codigoCons = codigoCons;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoNaco != null ? codigoNaco.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NavieraConsol)) {
            return false;
        }
        NavieraConsol other = (NavieraConsol) object;
        if ((this.codigoNaco == null && other.codigoNaco != null) || (this.codigoNaco != null && !this.codigoNaco.equals(other.codigoNaco))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.naportec.aisv.entidades.NavieraConsol[ codigoNaco=" + codigoNaco + " ]";
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
