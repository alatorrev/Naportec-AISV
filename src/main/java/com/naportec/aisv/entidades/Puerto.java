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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "aisv_puerto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Puerto.findAll", query = "SELECT p FROM Puerto p"),
    @NamedQuery(name = "Puerto.findByCodigoPuer", query = "SELECT p FROM Puerto p WHERE p.codigoPuer = :codigoPuer"),
    @NamedQuery(name = "Puerto.findByNombrePuer", query = "SELECT p FROM Puerto p WHERE UPPER(p.codPuertoPuer) = :nombrePuer AND p.estado=:estado"),
    @NamedQuery(name = "Puerto.findByNombrePuerConverter", query = "SELECT p FROM Puerto p WHERE (UPPER(p.codPuertoPuer) = :codPuer OR UPPER(p.nombrePuer) = :nomPuer) AND p.estado=:estado"),
    @NamedQuery(name = "Puerto.findByCoincidence", query = "SELECT p FROM Puerto p WHERE (UPPER(p.codPuertoPuer) LIKE :puerto OR UPPER(p.nombrePuer) LIKE :puerto ) AND p.estado=:estado")
})
public class Puerto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_puer")
    private Long codigoPuer;
    @Column(name = "nombre_puer")
    private String nombrePuer;
    @Column(name = "cod_puerto_puer")
    private String codPuertoPuer;
    @Column(name = "estado_puer")
    private String estado;

    public Puerto() {
    }

    public Puerto(Long codigoPuer) {
        this.codigoPuer = codigoPuer;
    }

    public Long getCodigoPuer() {
        return codigoPuer;
    }

    public void setCodigoPuer(Long codigoPuer) {
        this.codigoPuer = codigoPuer;
    }

    public String getNombrePuer() {
        return nombrePuer;
    }

    public void setNombrePuer(String nombrePuer) {
        this.nombrePuer = nombrePuer;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoPuer != null ? codigoPuer.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Puerto)) {
            return false;
        }
        Puerto other = (Puerto) object;
        if ((this.codigoPuer == null && other.codigoPuer != null) || (this.codigoPuer != null && !this.codigoPuer.equals(other.codigoPuer))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Puerto[ codigoPuer=" + codigoPuer + " ]";
    }

    /**
     * @return the codPuertoPuer
     */
    public String getCodPuertoPuer() {
        return codPuertoPuer;
    }

    /**
     * @param codPuertoPuer the codPuertoPuer to set
     */
    public void setCodPuertoPuer(String codPuertoPuer) {
        this.codPuertoPuer = codPuertoPuer;
    }
    
}
