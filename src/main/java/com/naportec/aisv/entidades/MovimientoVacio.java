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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author REY COMPUTER
 */
@Entity
@Table(name = "aisv_movimiento_vacio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MovimientoVacio.findAll", query = "SELECT m FROM MovimientoVacio m"),
    @NamedQuery(name = "MovimientoVacio.findByCodigoMova", query = "SELECT m FROM MovimientoVacio m WHERE m.codigoMova = :codigoMova"),
    @NamedQuery(name = "MovimientoVacio.findByIpMova", query = "SELECT m FROM MovimientoVacio m WHERE m.ipMova = :ipMova"),
    @NamedQuery(name = "MovimientoVacio.findByFechaMova", query = "SELECT m FROM MovimientoVacio m WHERE m.fechaMova = :fechaMova"),
    @NamedQuery(name = "MovimientoVacio.findByTipoMova", query = "SELECT m FROM MovimientoVacio m WHERE m.tipoMova = :tipoMova"),
    @NamedQuery(name = "MovimientoVacio.findByDescripcionMova", query = "SELECT m FROM MovimientoVacio m WHERE m.descripcionMova = :descripcionMova")})
public class MovimientoVacio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_mova")
    private Long codigoMova;
    @Size(max = 100)
    @Column(name = "ip_mova")
    private String ipMova;
    @Column(name = "fecha_mova")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaMova;
    @Size(max = 200)
    @Column(name = "tipo_mova")
    private String tipoMova;
    @Size(max = 600)
    @Column(name = "descripcion_mova")
    private String descripcionMova;
    @JoinColumn(name = "codigo_trav", referencedColumnName = "codigo_trav")
    @ManyToOne
    private TransaccionVacios codigoTrav;

    public MovimientoVacio() {
    }

    public MovimientoVacio(Long codigoMova) {
        this.codigoMova = codigoMova;
    }

    public Long getCodigoMova() {
        return codigoMova;
    }

    public void setCodigoMova(Long codigoMova) {
        this.codigoMova = codigoMova;
    }

    public String getIpMova() {
        return ipMova;
    }

    public void setIpMova(String ipMova) {
        this.ipMova = ipMova;
    }

    public Date getFechaMova() {
        return fechaMova;
    }

    public void setFechaMova(Date fechaMova) {
        this.fechaMova = fechaMova;
    }

    public String getTipoMova() {
        return tipoMova;
    }

    public void setTipoMova(String tipoMova) {
        this.tipoMova = tipoMova;
    }

    public String getDescripcionMova() {
        return descripcionMova;
    }

    public void setDescripcionMova(String descripcionMova) {
        this.descripcionMova = descripcionMova;
    }

    public TransaccionVacios getCodigoTrav() {
        return codigoTrav;
    }

    public void setCodigoTrav(TransaccionVacios codigoTrav) {
        this.codigoTrav = codigoTrav;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoMova != null ? codigoMova.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovimientoVacio)) {
            return false;
        }
        MovimientoVacio other = (MovimientoVacio) object;
        if ((this.codigoMova == null && other.codigoMova != null) || (this.codigoMova != null && !this.codigoMova.equals(other.codigoMova))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.naportec.aisv.entidades.MovimientoVacio[ codigoMova=" + codigoMova + " ]";
    }
    
}
