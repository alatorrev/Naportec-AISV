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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author REY COMPUTER
 */
@Entity
@Table(name = "aisv_precarga_vacios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrecargaVacios.findAll", query = "SELECT p FROM PrecargaVacios p"),
    @NamedQuery(name = "PrecargaVacios.findByCodigoPrev", query = "SELECT p FROM PrecargaVacios p WHERE p.codigoPrev = :codigoPrev"),
    @NamedQuery(name = "PrecargaVacios.findByBookingPrev", query = "SELECT p FROM PrecargaVacios p WHERE p.bookingPrev = :bookingPrev"),
    @NamedQuery(name = "PrecargaVacios.findByTotalEspaciosPrev", query = "SELECT p FROM PrecargaVacios p WHERE p.totalEspaciosPrev = :totalEspaciosPrev"),
    @NamedQuery(name = "PrecargaVacios.findByTipoContenedorPrev", query = "SELECT p FROM PrecargaVacios p WHERE p.tipoContenedorPrev = :tipoContenedorPrev"),
    @NamedQuery(name = "PrecargaVacios.findByContenedorPrev", query = "SELECT p FROM PrecargaVacios p WHERE p.contenedorPrev = :contenedorPrev"),
    @NamedQuery(name = "PrecargaVacios.findByPtoOrigenPrev", query = "SELECT p FROM PrecargaVacios p WHERE p.ptoOrigenPrev = :ptoOrigenPrev"),
    @NamedQuery(name = "PrecargaVacios.findByPtoDestinoPrev", query = "SELECT p FROM PrecargaVacios p WHERE p.ptoDestinoPrev = :ptoDestinoPrev"),
    @NamedQuery(name = "PrecargaVacios.findByPtoTransbordoPrev", query = "SELECT p FROM PrecargaVacios p WHERE p.ptoTransbordoPrev = :ptoTransbordoPrev"),
    @NamedQuery(name = "PrecargaVacios.findByEstado", query = "SELECT p FROM PrecargaVacios p WHERE p.estado = :estado"),
    @NamedQuery(name = "PrecargaVacios.findByEspaciosDisponiblesPrev", query = "SELECT p FROM PrecargaVacios p WHERE p.espaciosDisponiblesPrev = :espaciosDisponiblesPrev")})
public class PrecargaVacios implements Serializable {
    @OneToMany(mappedBy = "codigoPrev")
    private List<TransaccionVacios> transaccionVaciosList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_prev")
    private Long codigoPrev;
    @Column(name = "booking_prev")
    private String bookingPrev;
    @Column(name = "total_espacios_prev")
    private Integer totalEspaciosPrev;
    @Column(name = "tipo_contenedor_prev")
    private String tipoContenedorPrev;
    @Column(name = "contenedor_prev")
    private String contenedorPrev;
    @Column(name = "pto_origen_prev")
    private String ptoOrigenPrev;
    @Column(name = "pto_destino_prev")
    private String ptoDestinoPrev;
    @Column(name = "pto_transbordo_prev")
    private String ptoTransbordoPrev;
    @Column(name = "estado_prev")
    private String estado;
    @Column(name = "espacios_disponibles_prev")
    private Integer espaciosDisponiblesPrev;
    @JoinColumn(name = "id_itinerario_prev", referencedColumnName = "codigo_itin")
    @ManyToOne
    private Itinerario idItinerarioPrev;
    @JoinColumn(name = "id_linea_prev", referencedColumnName = "codigo_navi")
    @ManyToOne
    private Naviera idLineaPrev;
    @Transient
    private boolean activo;
    @Transient
    private int cantidadAisv;

    public PrecargaVacios() {
    }

    public PrecargaVacios(Long codigoPrev) {
        this.codigoPrev = codigoPrev;
    }

    public Long getCodigoPrev() {
        return codigoPrev;
    }

    public void setCodigoPrev(Long codigoPrev) {
        this.codigoPrev = codigoPrev;
    }

    public String getBookingPrev() {
        return bookingPrev;
    }

    public void setBookingPrev(String bookingPrev) {
        this.bookingPrev = bookingPrev;
    }

    public Integer getTotalEspaciosPrev() {
        return totalEspaciosPrev;
    }

    public void setTotalEspaciosPrev(Integer totalEspaciosPrev) {
        this.totalEspaciosPrev = totalEspaciosPrev;
    }

    public String getTipoContenedorPrev() {
        return tipoContenedorPrev;
    }

    public void setTipoContenedorPrev(String tipoContenedorPrev) {
        this.tipoContenedorPrev = tipoContenedorPrev;
    }

    public String getContenedorPrev() {
        return contenedorPrev;
    }

    public void setContenedorPrev(String contenedorPrev) {
        this.contenedorPrev = contenedorPrev;
    }

    public String getPtoOrigenPrev() {
        return ptoOrigenPrev;
    }

    public void setPtoOrigenPrev(String ptoOrigenPrev) {
        this.ptoOrigenPrev = ptoOrigenPrev;
    }

    public String getPtoDestinoPrev() {
        return ptoDestinoPrev;
    }

    public void setPtoDestinoPrev(String ptoDestinoPrev) {
        this.ptoDestinoPrev = ptoDestinoPrev;
    }

    public String getPtoTransbordoPrev() {
        return ptoTransbordoPrev;
    }

    public void setPtoTransbordoPrev(String ptoTransbordoPrev) {
        this.ptoTransbordoPrev = ptoTransbordoPrev;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getEspaciosDisponiblesPrev() {
        return espaciosDisponiblesPrev;
    }

    public void setEspaciosDisponiblesPrev(Integer espaciosDisponiblesPrev) {
        this.espaciosDisponiblesPrev = espaciosDisponiblesPrev;
    }

    public Itinerario getIdItinerarioPrev() {
        return idItinerarioPrev;
    }

    public void setIdItinerarioPrev(Itinerario idItinerarioPrev) {
        this.idItinerarioPrev = idItinerarioPrev;
    }

    public Naviera getIdLineaPrev() {
        return idLineaPrev;
    }

    public void setIdLineaPrev(Naviera idLineaPrev) {
        this.idLineaPrev = idLineaPrev;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoPrev != null ? codigoPrev.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrecargaVacios)) {
            return false;
        }
        PrecargaVacios other = (PrecargaVacios) object;
        if ((this.codigoPrev == null && other.codigoPrev != null) || (this.codigoPrev != null && !this.codigoPrev.equals(other.codigoPrev))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.naportec.aisv.entidades.PrecargaVacios[ codigoPrev=" + codigoPrev + " ]";
    }
    /**
     * @return the activo
     */
    public boolean getActivo() {
        if (this.espaciosDisponiblesPrev != 0) {
            activo = true;
        } else {
            activo = false;
        }
        return activo;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
     /**
     * @return the cantidadAisv
     */
    public int getCantidadAisv() {
        if(totalEspaciosPrev!=null && espaciosDisponiblesPrev!=null){
            cantidadAisv=totalEspaciosPrev-espaciosDisponiblesPrev;
        }
        return cantidadAisv;
    }

    /**
     * @param cantidadAisv the cantidadAisv to set
     */
    public void setCantidadAisv(int cantidadAisv) {
        this.cantidadAisv = cantidadAisv;
    }

    @XmlTransient
    public List<TransaccionVacios> getTransaccionVaciosList() {
        return transaccionVaciosList;
    }

    public void setTransaccionVaciosList(List<TransaccionVacios> transaccionVaciosList) {
        this.transaccionVaciosList = transaccionVaciosList;
    }

    
}
