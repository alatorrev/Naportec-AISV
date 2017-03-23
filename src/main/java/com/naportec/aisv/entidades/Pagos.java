/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.aisv.entidades;

import com.naportec.seguridad.entidades.SUserrole;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author MZambrano1
 */
@Entity
@Table(name = "aisv_pagos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pagos.findAll", query = "SELECT p FROM Pagos p"),
    @NamedQuery(name = "Pagos.findByNumeroPago", query = "SELECT p FROM Pagos p WHERE p.numeroPago = :numeroPago"),
    @NamedQuery(name = "Pagos.findByFechaPago", query = "SELECT p FROM Pagos p WHERE p.fechaPago = :fechaPago"),
    @NamedQuery(name = "Pagos.findByEstadoPago", query = "SELECT p FROM Pagos p WHERE p.estadoPago = :estadoPago"),
    @NamedQuery(name = "Pagos.findByTipoPago", query = "SELECT p FROM Pagos p WHERE p.tipoPago = :tipoPago")})
public class Pagos implements Serializable {

    private static final long serialVersionUID = 1L;
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    @Basic(optional = false)
//    @Column(name = "codigo_pago")
//    @Transient
//    private Long codigoPago;
    @Id
    @Basic(optional = false)
    @Column(name = "numero_pago")
    private String numeroPago;
    @Column(name = "fecha_pago")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPago;
    @Size(max = 40)
    @Column(name = "estado_pago")
    private String estadoPago;
    @Size(max = 15)
    @Column(name = "tipo_pago")
    private String tipoPago;

    public Pagos() {
    }

//    public Long getCodigoPago() {
//        return codigoPago;
//    }
//
//    public void setCodigoPago(Long codigoPago) {
//        this.codigoPago = codigoPago;
//    }
    public Pagos(String numeroPago) {
        this.numeroPago = numeroPago;
    }

    public String getNumeroPago() {
        return numeroPago;
    }

    public void setNumeroPago(String numeroPago) {
        this.numeroPago = numeroPago;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getEstado() {
        return estadoPago;
    }

    public void setEstado(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numeroPago != null ? numeroPago.hashCode() : 0);
        return hash;
    }
//    @Override
//    public int hashCode() {
//        int hash = 0;
//        hash += (codigoPago != null ? codigoPago.hashCode() : 0);
//        return hash;
//    }
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pagos)) {
            return false;
        }
        Pagos other = (Pagos) object;
        if ((this.numeroPago == null && other.numeroPago != null) || (this.numeroPago != null && !this.numeroPago.equals(other.numeroPago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Pagos[ numeroPago=" + numeroPago + " ]";
//return "com.naportec.aisv.entidades.Pagos[ numeroPago=" + numeroPago + " ]";
    }

}
