/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.aisv.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Fernando
 */
@Entity
@Table(name = "aisv_movimiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Movimiento.findAll", query = "SELECT m FROM Movimiento m"),
    @NamedQuery(name = "Movimiento.findByCodigoMovi", query = "SELECT m FROM Movimiento m WHERE m.codigoMovi = :codigoMovi"),
    @NamedQuery(name = "Movimiento.findByIpMovi", query = "SELECT m FROM Movimiento m WHERE m.ipMovi = :ipMovi"),
    @NamedQuery(name = "Movimiento.findByFechaMovi", query = "SELECT m FROM Movimiento m WHERE m.fechaMovi = :fechaMovi"),
    @NamedQuery(name = "Movimiento.findByTipoMovi", query = "SELECT m FROM Movimiento m WHERE m.tipoMovi = :tipoMovi"),
    @NamedQuery(name = "Movimiento.findByDescripcionMovi", query = "SELECT m FROM Movimiento m WHERE m.descripcionMovi = :descripcionMovi")})
public class Movimiento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_movi")
    private Long codigoMovi;
    @Column(name = "ip_movi")
    private String ipMovi;
    @Column(name = "fecha_movi")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaMovi;
    @Column(name = "tipo_movi")
    private String tipoMovi;
    @Column(name = "descripcion_movi")
    private String descripcionMovi;
    @JoinColumn(name = "codigo_trans", referencedColumnName = "codigo_trans")
    @ManyToOne
    private Transaccion codigoTrans;

    public Movimiento() {
    }

    public Movimiento(Long codigoMovi) {
        this.codigoMovi = codigoMovi;
    }

    public Long getCodigoMovi() {
        return codigoMovi;
    }

    public void setCodigoMovi(Long codigoMovi) {
        this.codigoMovi = codigoMovi;
    }

    public String getIpMovi() {
        return ipMovi;
    }

    public void setIpMovi(String ipMovi) {
        this.ipMovi = ipMovi;
    }

    public Date getFechaMovi() {
        return fechaMovi;
    }

    public void setFechaMovi(Date fechaMovi) {
        this.fechaMovi = fechaMovi;
    }

    public String getTipoMovi() {
        return tipoMovi;
    }

    public void setTipoMovi(String tipoMovi) {
        this.tipoMovi = tipoMovi;
    }

    public String getDescripcionMovi() {
        return descripcionMovi;
    }

    public void setDescripcionMovi(String descripcionMovi) {
        this.descripcionMovi = descripcionMovi;
    }

    public Transaccion getCodigoTrans() {
        return codigoTrans;
    }

    public void setCodigoTrans(Transaccion codigoTrans) {
        this.codigoTrans = codigoTrans;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoMovi != null ? codigoMovi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Movimiento)) {
            return false;
        }
        Movimiento other = (Movimiento) object;
        if ((this.codigoMovi == null && other.codigoMovi != null) || (this.codigoMovi != null && !this.codigoMovi.equals(other.codigoMovi))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Movimiento[ codigoMovi=" + codigoMovi + " ]";
    }
    
}
