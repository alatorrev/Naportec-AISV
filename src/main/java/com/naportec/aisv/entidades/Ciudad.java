/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.aisv.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
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
@Table(name = "aisv_ciudad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ciudad.findAll", query = "SELECT c FROM Ciudad c"),
    @NamedQuery(name = "Ciudad.findByCodigoCiud", query = "SELECT c FROM Ciudad c WHERE c.codigoCiud = :codigoCiud"),
    @NamedQuery(name = "Ciudad.findByNombreCiud", query = "SELECT c FROM Ciudad c WHERE c.nombreCiud = :nombreCiud"),
    @NamedQuery(name = "Ciudad.findByEstadoCiud", query = "SELECT c FROM Ciudad c WHERE c.estadoCiud = :estadoCiud")})
public class Ciudad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo_ciud")
    private Long codigoCiud;
    @Column(name = "nombre_ciud")
    private String nombreCiud;
    @Column(name = "estado_ciud")
    private String estadoCiud;

    public Ciudad() {
    }

    public Ciudad(Long codigoCiud) {
        this.codigoCiud = codigoCiud;
    }

    public Long getCodigoCiud() {
        return codigoCiud;
    }

    public void setCodigoCiud(Long codigoCiud) {
        this.codigoCiud = codigoCiud;
    }

    public String getNombreCiud() {
        return nombreCiud;
    }

    public void setNombreCiud(String nombreCiud) {
        this.nombreCiud = nombreCiud;
    }

    public String getEstadoCiud() {
        return estadoCiud;
    }

    public void setEstadoCiud(String estadoCiud) {
        this.estadoCiud = estadoCiud;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoCiud != null ? codigoCiud.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ciudad)) {
            return false;
        }
        Ciudad other = (Ciudad) object;
        if ((this.codigoCiud == null && other.codigoCiud != null) || (this.codigoCiud != null && !this.codigoCiud.equals(other.codigoCiud))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.naportec.aisv.entidades.Ciudad[ codigoCiud=" + codigoCiud + " ]";
    }
    
}
