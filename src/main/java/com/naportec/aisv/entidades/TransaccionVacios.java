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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * @author REY COMPUTER
 */
@Entity
@Table(name = "aisv_transaccion_vacios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TransaccionVacios.findAll", query = "SELECT t FROM TransaccionVacios t"),
    @NamedQuery(name = "TransaccionVacios.findByCodigoTrav", query = "SELECT t FROM TransaccionVacios t WHERE t.codigoTrav = :codigoTrav"),
    @NamedQuery(name = "TransaccionVacios.findByContenedorTrav", query = "SELECT t FROM TransaccionVacios t WHERE t.contenedorTrav = :contenedorTrav"),
    @NamedQuery(name = "TransaccionVacios.findByEstadoTrav", query = "SELECT t FROM TransaccionVacios t WHERE t.estadoTrav = :estadoTrav")})
public class TransaccionVacios implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_trav")
    private Long codigoTrav;
    @Size(max = 50)
    @Column(name = "contenedor_trav")
    private String contenedorTrav;
    @Size(max = 50)
    @Column(name = "estado_trav")
    private String estadoTrav;
    @JoinColumn(name = "codigo_prev", referencedColumnName = "codigo_prev")
    @ManyToOne
    private PrecargaVacios codigoPrev;
    @OneToMany(mappedBy = "codigoTrav")
    private List<MovimientoVacio> movimientoVacioList;

    public TransaccionVacios() {
    }

    public TransaccionVacios(Long codigoTrav) {
        this.codigoTrav = codigoTrav;
    }

    public Long getCodigoTrav() {
        return codigoTrav;
    }

    public void setCodigoTrav(Long codigoTrav) {
        this.codigoTrav = codigoTrav;
    }

    public String getContenedorTrav() {
        return contenedorTrav;
    }

    public void setContenedorTrav(String contenedorTrav) {
        this.contenedorTrav = contenedorTrav;
    }

    public String getEstadoTrav() {
        return estadoTrav;
    }

    public void setEstadoTrav(String estadoTrav) {
        this.estadoTrav = estadoTrav;
    }

    public PrecargaVacios getCodigoPrev() {
        return codigoPrev;
    }

    public void setCodigoPrev(PrecargaVacios codigoPrev) {
        this.codigoPrev = codigoPrev;
    }

    @XmlTransient
    public List<MovimientoVacio> getMovimientoVacioList() {
        return movimientoVacioList;
    }

    public void setMovimientoVacioList(List<MovimientoVacio> movimientoVacioList) {
        this.movimientoVacioList = movimientoVacioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoTrav != null ? codigoTrav.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransaccionVacios)) {
            return false;
        }
        TransaccionVacios other = (TransaccionVacios) object;
        if ((this.codigoTrav == null && other.codigoTrav != null) || (this.codigoTrav != null && !this.codigoTrav.equals(other.codigoTrav))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.naportec.aisv.entidades.TransaccionVacios[ codigoTrav=" + codigoTrav + " ]";
    }
    
}
