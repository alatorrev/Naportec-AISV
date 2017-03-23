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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Fernando
 */
@Entity
@Table(name = "aisv_proposito_carga")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PropositoCarga.findAll", query = "SELECT p FROM PropositoCarga p"),
    @NamedQuery(name = "PropositoCarga.findByCodigoProp", query = "SELECT p FROM PropositoCarga p WHERE p.codigoProp = :codigoProp"),
    @NamedQuery(name = "PropositoCarga.findByCodigoPrinProp", query = "SELECT p FROM PropositoCarga p WHERE p.codigoPrinProp = :codigoPrinProp"),
    @NamedQuery(name = "PropositoCarga.findByCodigoAuxProp", query = "SELECT p FROM PropositoCarga p WHERE p.codigoAuxProp = :codigoAuxProp"),
    @NamedQuery(name = "PropositoCarga.findByDescripcionProp", query = "SELECT p FROM PropositoCarga p WHERE p.descripcionProp = :descripcionProp"),
    @NamedQuery(name = "PropositoCarga.findByEstadoProp", query = "SELECT p FROM PropositoCarga p WHERE p.estado = :estado"),
    @NamedQuery(name = "PropositoCarga.findByTipoProp", query = "SELECT p FROM PropositoCarga p WHERE p.tipoProp = :tipoProp"),
    @NamedQuery(name = "PropositoCarga.findByContenerizadaProp", query = "SELECT p FROM PropositoCarga p WHERE p.contenerizadaProp = :contenerizadaProp"),
    @NamedQuery(name = "PropositoCarga.findByConsolidadaProp", query = "SELECT p FROM PropositoCarga p WHERE p.consolidadaProp = :consolidadaProp"),
    @NamedQuery(name = "PropositoCarga.findBySueltaProp", query = "SELECT p FROM PropositoCarga p WHERE p.sueltaProp = :sueltaProp")})
public class PropositoCarga implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo_prop")
    private Long codigoProp;
    @Basic(optional = false)
    @Column(name = "codigo_prin_prop")
    private String codigoPrinProp;
    @Column(name = "codigo_aux_prop")
    private String codigoAuxProp;
    @Basic(optional = false)
    @Column(name = "descripcion_prop")
    private String descripcionProp;
    @Column(name = "estado_prop")
    private String estado;
    @Basic(optional = false)
    @Column(name = "tipo_prop")
    private String tipoProp;
    @Column(name = "contenerizada_prop")
    private Boolean contenerizadaProp;
    @Column(name = "consolidada_prop")
    private Boolean consolidadaProp;
    @Column(name = "suelta_prop")
    private Boolean sueltaProp;

    public PropositoCarga() {
    }

    public PropositoCarga(Long codigoProp) {
        this.codigoProp = codigoProp;
    }

    public PropositoCarga(Long codigoProp, String codigoPrinProp, String descripcionProp, String estadoProp, String tipoProp) {
        this.codigoProp = codigoProp;
        this.codigoPrinProp = codigoPrinProp;
        this.descripcionProp = descripcionProp;
        this.estado = estadoProp;
        this.tipoProp = tipoProp;
    }

    public Long getCodigoProp() {
        return codigoProp;
    }

    public void setCodigoProp(Long codigoProp) {
        this.codigoProp = codigoProp;
    }

    public String getCodigoPrinProp() {
        return codigoPrinProp;
    }

    public void setCodigoPrinProp(String codigoPrinProp) {
        this.codigoPrinProp = codigoPrinProp;
    }

    public String getCodigoAuxProp() {
        return codigoAuxProp;
    }

    public void setCodigoAuxProp(String codigoAuxProp) {
        this.codigoAuxProp = codigoAuxProp;
    }

    public String getDescripcionProp() {
        return descripcionProp;
    }

    public void setDescripcionProp(String descripcionProp) {
        this.descripcionProp = descripcionProp;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipoProp() {
        return tipoProp;
    }

    public void setTipoProp(String tipoProp) {
        this.tipoProp = tipoProp;
    }

    public Boolean getContenerizadaProp() {
        return contenerizadaProp;
    }

    public void setContenerizadaProp(Boolean contenerizadaProp) {
        this.contenerizadaProp = contenerizadaProp;
    }

    public Boolean getConsolidadaProp() {
        return consolidadaProp;
    }

    public void setConsolidadaProp(Boolean consolidadaProp) {
        this.consolidadaProp = consolidadaProp;
    }

    public Boolean getSueltaProp() {
        return sueltaProp;
    }

    public void setSueltaProp(Boolean sueltaProp) {
        this.sueltaProp = sueltaProp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoProp != null ? codigoProp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PropositoCarga)) {
            return false;
        }
        PropositoCarga other = (PropositoCarga) object;
        if ((this.codigoProp == null && other.codigoProp != null) || (this.codigoProp != null && !this.codigoProp.equals(other.codigoProp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.naportec.aisv.entidades.PropositoCarga[ codigoProp=" + codigoProp + " ]";
    }
    
}
