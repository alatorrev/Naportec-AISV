/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.naportec.aisv.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Fernando
 */
@Entity
@Table(name = "aisv_itinerario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Itinerario.findAll", query = "SELECT i FROM Itinerario i"),
    @NamedQuery(name = "Itinerario.listaItinerarioActivo", query = "SELECT i FROM Itinerario i WHERE i.estado=:estado AND i.fechaZarpeItin >= :fecha"),
    @NamedQuery(name = "Itinerario.listaItinerario", query = "SELECT i FROM Itinerario i WHERE (i.descripcionViajeItin LIKE :viaje OR i.buqueItin LIKE :buque) AND  i.estado=:estado"),
    @NamedQuery(name = "Itinerario.findByCodigoItin", query = "SELECT i FROM Itinerario i WHERE i.codigoItin = :codigoItin"),
    @NamedQuery(name = "Itinerario.findByDescripcionItin", query = "SELECT i FROM Itinerario i WHERE i.descripcionItin = :descripcionItin"),
    @NamedQuery(name = "Itinerario.findByEstadoItin", query = "SELECT i FROM Itinerario i WHERE i.estado = :estado"),
    @NamedQuery(name = "Itinerario.findByManifiestoItin", query = "SELECT i FROM Itinerario i WHERE i.manifiestoItin = :manifiestoItin"),
    @NamedQuery(name = "Itinerario.findByCodItinerarioItin", query = "SELECT i FROM Itinerario i WHERE i.codItinerarioItin = :codItinerarioItin"),
    @NamedQuery(name = "Itinerario.findByFechaZarpeItin", query = "SELECT i FROM Itinerario i WHERE i.fechaZarpeItin = :fechaZarpeItin"),
    @NamedQuery(name = "Itinerario.findByFechaArriboItin", query = "SELECT i FROM Itinerario i WHERE i.fechaArriboItin = :fechaArriboItin"),
    @NamedQuery(name = "Itinerario.findByBuqueItin", query = "SELECT i FROM Itinerario i WHERE i.buqueItin = :buqueItin"),
    @NamedQuery(name = "Itinerario.findByDescripcionViajeItin", query = "SELECT i FROM Itinerario i WHERE i.descripcionViajeItin = :descripcionViajeItin"),
    @NamedQuery(name = "Itinerario.findByCodigoLloydItin", query = "SELECT i FROM Itinerario i WHERE i.codigoLloydItin = :codigoLloydItin")})
public class Itinerario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_itin")
    private Long codigoItin;
    @Column(name = "descripcion_itin")
    private String descripcionItin;
    @Column(name = "estado_itin")
    private String estado;
    @Column(name = "manifiesto_itin")
    private String manifiestoItin;
    @Column(name = "cod_itinerario_itin")
    private String codItinerarioItin;
    @Column(name = "fecha_zarpe_itin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaZarpeItin;
    @Column(name = "fecha_arribo_itin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaArriboItin;
    @Column(name = "buque_itin")
    private String buqueItin;
    @Column(name = "descripcion_viaje_itin")
    private String descripcionViajeItin;
    @Column(name = "codigo_lloyd_itin")
    private String codigoLloydItin;
    @OneToMany(mappedBy = "idItinerarioPrec")
    private List<Precarga> precargaList;

    public Itinerario() {
    }

    public Itinerario(Long codigoItin) {
        this.codigoItin = codigoItin;
    }

    public Long getCodigoItin() {
        return codigoItin;
    }

    public void setCodigoItin(Long codigoItin) {
        this.codigoItin = codigoItin;
    }

    public String getDescripcionItin() {
        return descripcionItin;
    }

    public void setDescripcionItin(String descripcionItin) {
        this.descripcionItin = descripcionItin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getManifiestoItin() {
        return manifiestoItin;
    }

    public void setManifiestoItin(String manifiestoItin) {
        this.manifiestoItin = manifiestoItin;
    }

    public String getCodItinerarioItin() {
        return codItinerarioItin;
    }

    public void setCodItinerarioItin(String codItinerarioItin) {
        this.codItinerarioItin = codItinerarioItin;
    }

    public Date getFechaZarpeItin() {
        return fechaZarpeItin;
    }

    public void setFechaZarpeItin(Date fechaZarpeItin) {
        this.fechaZarpeItin = fechaZarpeItin;
    }

    public Date getFechaArriboItin() {
        return fechaArriboItin;
    }

    public void setFechaArriboItin(Date fechaArriboItin) {
        this.fechaArriboItin = fechaArriboItin;
    }

    public String getBuqueItin() {
        return buqueItin;
    }

    public void setBuqueItin(String buqueItin) {
        this.buqueItin = buqueItin;
    }

    public String getDescripcionViajeItin() {
        return descripcionViajeItin;
    }

    public void setDescripcionViajeItin(String descripcionViajeItin) {
        this.descripcionViajeItin = descripcionViajeItin;
    }

    public String getCodigoLloydItin() {
        return codigoLloydItin;
    }

    public void setCodigoLloydItin(String codigoLloydItin) {
        this.codigoLloydItin = codigoLloydItin;
    }

    @XmlTransient
    public List<Precarga> getPrecargaList() {
        return precargaList;
    }

    public void setPrecargaList(List<Precarga> precargaList) {
        this.precargaList = precargaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoItin != null ? codigoItin.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Itinerario)) {
            return false;
        }
        Itinerario other = (Itinerario) object;
        if ((this.codigoItin == null && other.codigoItin != null) || (this.codigoItin != null && !this.codigoItin.equals(other.codigoItin))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Itinerario[ codigoItin=" + codigoItin + " ]";
    }
    
}
