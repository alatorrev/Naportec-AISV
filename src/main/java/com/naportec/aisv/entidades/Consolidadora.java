/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.aisv.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author MZambrano1
 */
@Entity
@Table(name = "aisv_consolidadora")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Consolidadora.findAll", query = "SELECT c FROM Consolidadora c"),
    @NamedQuery(name = "Consolidadora.findByCodigoCons", query = "SELECT c FROM Consolidadora c WHERE c.codigoCons = :codigoCons"),
    @NamedQuery(name = "Consolidadora.findByNombreCons", query = "SELECT c FROM Consolidadora c WHERE c.nombreCons = :nombreCons"),
    @NamedQuery(name = "Consolidadora.findByRucCons", query = "SELECT c FROM Consolidadora c WHERE c.rucCons = :rucCons AND  c.estado = :estado"),
    @NamedQuery(name = "Consolidadora.findByEstadoCons", query = "SELECT c FROM Consolidadora c WHERE c.estado = :estado")})

public class Consolidadora implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_cons")
    private Long codigoCons;
    @Size(max = 300)
    @Column(name = "nombre_cons")
    private String nombreCons;
    @Size(max = 13)
    @Column(name = "ruc_cons")
    private String rucCons;
    @Size(max = 50)
    @Column(name = "estado_cons")
    private String estado;
    @OneToMany(mappedBy = "codigoCons")
    private List<NavieraConsol> navieraConsolList;

    public Consolidadora() {
    }

    public Consolidadora(Long codigoCons) {
        this.codigoCons = codigoCons;
    }

    public Long getCodigoCons() {
        return codigoCons;
    }

    public void setCodigoCons(Long codigoCons) {
        this.codigoCons = codigoCons;
    }

    public String getNombreCons() {
        return nombreCons;
    }

    public void setNombreCons(String nombreCons) {
        this.nombreCons = nombreCons;
    }

    public String getRucCons() {
        return rucCons;
    }

    public void setRucCons(String rucCons) {
        this.rucCons = rucCons;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<NavieraConsol> getNavieraConsolList() {
        return navieraConsolList;
    }

    public void setNavieraConsolList(List<NavieraConsol> navieraConsolList) {
        this.navieraConsolList = navieraConsolList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoCons != null ? codigoCons.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Consolidadora)) {
            return false;
        }
        Consolidadora other = (Consolidadora) object;
        if ((this.codigoCons == null && other.codigoCons != null) || (this.codigoCons != null && !this.codigoCons.equals(other.codigoCons))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.naportec.aisv.entidades.Consolidadora[ codigoCons=" + codigoCons + " ]";
    }
    
}
