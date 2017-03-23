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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Fernando
 */
@Entity
@Table(name = "aisv_embalaje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Embalaje.findAll", query = "SELECT e FROM Embalaje e"),
    @NamedQuery(name = "Embalaje.findByCodigoEmba", query = "SELECT e FROM Embalaje e WHERE e.codigoEmba = :codigoEmba"),
    @NamedQuery(name = "Embalaje.findByCodigoPrinEmba", query = "SELECT e FROM Embalaje e WHERE e.codigoPrinEmba = :codigoPrinEmba"),
    @NamedQuery(name = "Embalaje.findByCodigoAuxEmba", query = "SELECT e FROM Embalaje e WHERE e.codigoAuxEmba = :codigoAuxEmba"),
    @NamedQuery(name = "Embalaje.findByDescripcionEmba", query = "SELECT e FROM Embalaje e WHERE e.descripcionEmba = :descripcionEmba"),
    @NamedQuery(name = "Embalaje.findByEstadoEmba", query = "SELECT e FROM Embalaje e WHERE e.estado = :estado")})
public class Embalaje implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "codigo_emba")
    private Long codigoEmba;
    @Size(max = 50)
    @Column(name = "codigo_prin_emba")
    private String codigoPrinEmba;
    @Size(max = 50)
    @Column(name = "codigo_aux_emba")
    private String codigoAuxEmba;
    @Size(max = 200)
    @Column(name = "descripcion_emba")
    private String descripcionEmba;
    @Column(name = "estado_emba")
    private String estado;

    public Embalaje() {
    }

    public Embalaje(Long codigoEmba) {
        this.codigoEmba = codigoEmba;
    }

    public Long getCodigoEmba() {
        return codigoEmba;
    }

    public void setCodigoEmba(Long codigoEmba) {
        this.codigoEmba = codigoEmba;
    }

    public String getCodigoPrinEmba() {
        return codigoPrinEmba;
    }

    public void setCodigoPrinEmba(String codigoPrinEmba) {
        this.codigoPrinEmba = codigoPrinEmba;
    }

    public String getCodigoAuxEmba() {
        return codigoAuxEmba;
    }

    public void setCodigoAuxEmba(String codigoAuxEmba) {
        this.codigoAuxEmba = codigoAuxEmba;
    }

    public String getDescripcionEmba() {
        return descripcionEmba;
    }

    public void setDescripcionEmba(String descripcionEmba) {
        this.descripcionEmba = descripcionEmba;
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
        hash += (codigoEmba != null ? codigoEmba.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Embalaje)) {
            return false;
        }
        Embalaje other = (Embalaje) object;
        if ((this.codigoEmba == null && other.codigoEmba != null) || (this.codigoEmba != null && !this.codigoEmba.equals(other.codigoEmba))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.naportec.aisv.entidades.Embalaje[ codigoEmba=" + codigoEmba + " ]";
    }
    
}
