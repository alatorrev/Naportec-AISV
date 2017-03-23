/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.aisv.entidades;

import com.naportec.utilidades.enumeraciones.Estado;
import com.naportec.utilidades.otros.Ordenamiento;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Fernando
 */
@Entity
@Table(name = "aisv_precarga")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Precarga.findAll", query = "SELECT p FROM Precarga p"),
    @NamedQuery(name = "Precarga.findByCodigoPrec", query = "SELECT p FROM Precarga p WHERE p.codigoPrec = :codigoPrec"),
    @NamedQuery(name = "Precarga.findByBookingPrec", query = "SELECT p FROM Precarga p WHERE p.bookingPrec = :bookingPrec"),
    @NamedQuery(name = "Precarga.findByTotalEspaciosPrec", query = "SELECT p FROM Precarga p WHERE p.totalEspaciosPrec = :totalEspaciosPrec"),
    @NamedQuery(name = "Precarga.findByTipoContenedorPrec", query = "SELECT p FROM Precarga p WHERE p.tipoContenedorPrec = :tipoContenedorPrec"),
    @NamedQuery(name = "Precarga.findByContenedorPrec", query = "SELECT p FROM Precarga p WHERE p.contenedorPrec = :contenedorPrec"),
    @NamedQuery(name = "Precarga.findByCondicionContenedorPrec", query = "SELECT p FROM Precarga p WHERE p.condicionContenedorPrec = :condicionContenedorPrec"),
    @NamedQuery(name = "Precarga.findByImpExpIdPrec", query = "SELECT p FROM Precarga p WHERE p.impExpIdPrec = :impExpIdPrec"),
    @NamedQuery(name = "Precarga.findByImportadorPrec", query = "SELECT p FROM Precarga p WHERE p.importadorPrec = :importadorPrec"),
    @NamedQuery(name = "Precarga.findByAgenteIdPrec", query = "SELECT p FROM Precarga p WHERE p.agenteIdPrec = :agenteIdPrec"),
    @NamedQuery(name = "Precarga.findByAgentePrec", query = "SELECT p FROM Precarga p WHERE p.agentePrec = :agentePrec"),
    @NamedQuery(name = "Precarga.findByDescripcionPrec", query = "SELECT p FROM Precarga p WHERE p.descripcionPrec = :descripcionPrec"),
    @NamedQuery(name = "Precarga.findByCantidadPrec", query = "SELECT p FROM Precarga p WHERE p.cantidadPrec = :cantidadPrec"),
    @NamedQuery(name = "Precarga.findByPesoPrec", query = "SELECT p FROM Precarga p WHERE p.pesoPrec = :pesoPrec"),
    @NamedQuery(name = "Precarga.findByCargaPeligrosaPrec", query = "SELECT p FROM Precarga p WHERE p.cargaPeligrosaPrec = :cargaPeligrosaPrec"),
    @NamedQuery(name = "Precarga.findByCodigoImoPre", query = "SELECT p FROM Precarga p WHERE p.codigoImoPre = :codigoImoPre"),
    @NamedQuery(name = "Precarga.findBySello1Prec", query = "SELECT p FROM Precarga p WHERE p.sello1Prec = :sello1Prec"),
    @NamedQuery(name = "Precarga.findBySello2Prec", query = "SELECT p FROM Precarga p WHERE p.sello2Prec = :sello2Prec"),
    @NamedQuery(name = "Precarga.findBySello3Prec", query = "SELECT p FROM Precarga p WHERE p.sello3Prec = :sello3Prec"),
    @NamedQuery(name = "Precarga.findByTemperaturaPrec", query = "SELECT p FROM Precarga p WHERE p.temperaturaPrec = :temperaturaPrec"),
    @NamedQuery(name = "Precarga.findByVentilacionPrec", query = "SELECT p FROM Precarga p WHERE p.ventilacionPrec = :ventilacionPrec"),
    @NamedQuery(name = "Precarga.findByPtoOrigenPrec", query = "SELECT p FROM Precarga p WHERE p.ptoOrigenPrec = :ptoOrigenPrec"),
    @NamedQuery(name = "Precarga.findByPtoDestinoPrec", query = "SELECT p FROM Precarga p WHERE p.ptoDestinoPrec = :ptoDestinoPrec"),
    @NamedQuery(name = "Precarga.findByPtoTransbordoPrec", query = "SELECT p FROM Precarga p WHERE p.ptoTransbordoPrec = :ptoTransbordoPrec"),
    @NamedQuery(name = "Precarga.findByTarjaPrec", query = "SELECT p FROM Precarga p WHERE p.tarjaPrec = :tarjaPrec"),
    @NamedQuery(name = "Precarga.findByBlmasterPrec", query = "SELECT p FROM Precarga p WHERE p.blmasterPrec = :blmasterPrec"),
    @NamedQuery(name = "Precarga.findByBlhijoPrec", query = "SELECT p FROM Precarga p WHERE p.blhijoPrec = :blhijoPrec"),
    @NamedQuery(name = "Precarga.findByEstadoPrec", query = "SELECT p FROM Precarga p WHERE p.estado = :estadoPrec"),
    @NamedQuery(name = "Precarga.findByEspaciosDisponiblesPrec", query = "SELECT p FROM Precarga p WHERE p.espaciosDisponiblesPrec = :espaciosDisponiblesPrec"),
    @NamedQuery(name = "Precarga.findByTipoPrec", query = "SELECT p FROM Precarga p WHERE p.tipoPrec = :tipoPrec")})
public class Precarga implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo_prec")
    private Long codigoPrec;
    @Column(name = "booking_prec")
    private String bookingPrec;
    @Column(name = "total_espacios_prec")
    private Integer totalEspaciosPrec;
    @Column(name = "tipo_contenedor_prec")
    private String tipoContenedorPrec;
    @Column(name = "contenedor_prec")
    private String contenedorPrec;
    @Column(name = "condicion_contenedor_prec")
    private String condicionContenedorPrec;
    @Column(name = "imp_exp_id_prec")
    private String impExpIdPrec;
    @Column(name = "importador_prec")
    private String importadorPrec;
    @Column(name = "agente_id_prec")
    private String agenteIdPrec;
    @Column(name = "agente_prec")
    private String agentePrec;
    @Column(name = "descripcion_prec")
    private String descripcionPrec;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cantidad_prec")
    private Double cantidadPrec;
    @Column(name = "peso_prec")
    private Double pesoPrec;
    @Column(name = "carga_peligrosa_prec")
    private String cargaPeligrosaPrec;
    @Column(name = "codigo_imo_pre")
    private String codigoImoPre;
    @Column(name = "sello1_prec")
    private String sello1Prec;
    @Column(name = "sello2_prec")
    private String sello2Prec;
    @Column(name = "sello3_prec")
    private String sello3Prec;
    @Column(name = "temperatura_prec")
    private Double temperaturaPrec;
    @Column(name = "ventilacion_prec")
    private Double ventilacionPrec;
    @Column(name = "pto_origen_prec")
    private String ptoOrigenPrec;
    @Column(name = "pto_destino_prec")
    private String ptoDestinoPrec;
    @Column(name = "pto_transbordo_prec")
    private String ptoTransbordoPrec;
    @Column(name = "tarja_prec")
    private Integer tarjaPrec;
    @Column(name = "blmaster_prec")
    private String blmasterPrec;
    @Column(name = "blhijo_prec")
    private String blhijoPrec;
    @Column(name = "estado_prec")
    private String estado;
    @Column(name = "habilitado")
    private boolean habilitado;
    @Column(name = "espacios_disponibles_prec")
    private Integer espaciosDisponiblesPrec;
    @Column(name = "tipo_prec")
    private String tipoPrec;
    @OneToMany(mappedBy = "codigoPrec")
    private List<Transaccion> transaccionImpExpList;
    @JoinColumn(name = "id_linea_prec", referencedColumnName = "codigo_navi")
    @ManyToOne
    private Naviera idLineaPrec;
    @JoinColumn(name = "id_itinerario_prec", referencedColumnName = "codigo_itin")
    @ManyToOne(cascade = CascadeType.REFRESH)
    private Itinerario idItinerarioPrec;
    @Transient
    private boolean activo;
    @Transient
    private boolean peligrosa;
    @Transient
    private String mostrarTipo;
    @Transient
    private boolean activoImpo;
    @Transient
    private String mostrarCondicion;
    @Transient
    private String daesUnionTransaccion;
    @Transient
    private int cantidadAisv;

    public Precarga() {
    }

    public Precarga(Long codigoPrec) {
        this.codigoPrec = codigoPrec;
    }

    public Long getCodigoPrec() {
        return codigoPrec;
    }

    public void setCodigoPrec(Long codigoPrec) {
        this.codigoPrec = codigoPrec;
    }

    public String getBookingPrec() {
        return bookingPrec;
    }

    public void setBookingPrec(String bookingPrec) {
        this.bookingPrec = bookingPrec;
    }

    public Integer getTotalEspaciosPrec() {
        return totalEspaciosPrec;
    }

    public void setTotalEspaciosPrec(Integer totalEspaciosPrec) {
        this.totalEspaciosPrec = totalEspaciosPrec;
    }

    public String getTipoContenedorPrec() {
        return tipoContenedorPrec;
    }

    public void setTipoContenedorPrec(String tipoContenedorPrec) {
        this.tipoContenedorPrec = tipoContenedorPrec;
    }

    public String getContenedorPrec() {
        return contenedorPrec;
    }

    public void setContenedorPrec(String contenedorPrec) {
        this.contenedorPrec = contenedorPrec;
    }

    public String getCondicionContenedorPrec() {
        return condicionContenedorPrec;
    }

    public void setCondicionContenedorPrec(String condicionContenedorPrec) {
        this.condicionContenedorPrec = condicionContenedorPrec;
    }

    public String getImpExpIdPrec() {
        return impExpIdPrec;
    }

    public void setImpExpIdPrec(String impExpIdPrec) {
        this.impExpIdPrec = impExpIdPrec;
    }

    public String getImportadorPrec() {
        if (importadorPrec != null) {
            this.importadorPrec = this.importadorPrec.toUpperCase();
        }
        return importadorPrec;
    }

    public void setImportadorPrec(String importadorPrec) {
        if (importadorPrec != null) {
            this.importadorPrec = importadorPrec.toUpperCase();
        } else {
            this.importadorPrec = importadorPrec;
        }
    }

    public String getAgenteIdPrec() {
        return agenteIdPrec;
    }

    public void setAgenteIdPrec(String agenteIdPrec) {
        this.agenteIdPrec = agenteIdPrec;
    }

    public String getAgentePrec() {
        return agentePrec;
    }

    public void setAgentePrec(String agentePrec) {
        this.agentePrec = agentePrec;
    }

    public String getDescripcionPrec() {
        return descripcionPrec;
    }

    public void setDescripcionPrec(String descripcionPrec) {
        this.descripcionPrec = descripcionPrec;
    }

    public Double getCantidadPrec() {
        return cantidadPrec;
    }

    public void setCantidadPrec(Double cantidadPrec) {
        this.cantidadPrec = cantidadPrec;
    }

    public Double getPesoPrec() {
        if (pesoPrec == null) {
            pesoPrec = 0d;
        }
        return pesoPrec;
    }

    public void setPesoPrec(Double pesoPrec) {
        this.pesoPrec = pesoPrec;
    }

    public String getCargaPeligrosaPrec() {
        return cargaPeligrosaPrec;
    }

    public void setCargaPeligrosaPrec(String cargaPeligrosaPrec) {
        this.cargaPeligrosaPrec = cargaPeligrosaPrec;
    }

    public String getCodigoImoPre() {
        return codigoImoPre;
    }

    public void setCodigoImoPre(String codigoImoPre) {
        this.codigoImoPre = codigoImoPre;
    }

    public String getSello1Prec() {
        return sello1Prec;
    }

    public void setSello1Prec(String sello1Prec) {
        this.sello1Prec = sello1Prec;
    }

    public String getSello2Prec() {
        return sello2Prec;
    }

    public void setSello2Prec(String sello2Prec) {
        this.sello2Prec = sello2Prec;
    }

    public String getSello3Prec() {
        return sello3Prec;
    }

    public void setSello3Prec(String sello3Prec) {
        this.sello3Prec = sello3Prec;
    }

    public Double getTemperaturaPrec() {
        return temperaturaPrec;
    }

    public void setTemperaturaPrec(Double temperaturaPrec) {
        this.temperaturaPrec = temperaturaPrec;
    }

    public Double getVentilacionPrec() {
        return ventilacionPrec;
    }

    public void setVentilacionPrec(Double ventilacionPrec) {
        this.ventilacionPrec = ventilacionPrec;
    }

    public String getPtoOrigenPrec() {
        return ptoOrigenPrec;
    }

    public void setPtoOrigenPrec(String ptoOrigenPrec) {
        this.ptoOrigenPrec = ptoOrigenPrec;
    }

    public String getPtoDestinoPrec() {
        return ptoDestinoPrec;
    }

    public void setPtoDestinoPrec(String ptoDestinoPrec) {
        this.ptoDestinoPrec = ptoDestinoPrec;
    }

    public String getPtoTransbordoPrec() {
        return ptoTransbordoPrec;
    }

    public void setPtoTransbordoPrec(String ptoTransbordoPrec) {
        this.ptoTransbordoPrec = ptoTransbordoPrec;
    }

    public Integer getTarjaPrec() {
        return tarjaPrec;
    }

    public void setTarjaPrec(Integer tarjaPrec) {
        this.tarjaPrec = tarjaPrec;
    }

    public String getBlmasterPrec() {
        return blmasterPrec;
    }

    public void setBlmasterPrec(String blmasterPrec) {
        this.blmasterPrec = blmasterPrec;
    }

    public String getBlhijoPrec() {
        return blhijoPrec;
    }

    public void setBlhijoPrec(String blhijoPrec) {
        this.blhijoPrec = blhijoPrec;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getEspaciosDisponiblesPrec() {
        if (espaciosDisponiblesPrec == null) {
            espaciosDisponiblesPrec = 0;
        }
        return espaciosDisponiblesPrec;
    }

    public void setEspaciosDisponiblesPrec(Integer espaciosDisponiblesPrec) {
        this.espaciosDisponiblesPrec = espaciosDisponiblesPrec;
    }

    public String getTipoPrec() {
        return tipoPrec;
    }

    public void setTipoPrec(String tipoPrec) {
        this.tipoPrec = tipoPrec;
    }

    @XmlTransient
    public List<Transaccion> getTransaccionImpExpList() {
        return transaccionImpExpList;
    }

    public void setTransaccionImpExpList(List<Transaccion> transaccionImpExpList) {
        this.transaccionImpExpList = transaccionImpExpList;
    }

    public Naviera getIdLineaPrec() {
        return idLineaPrec;
    }

    public void setIdLineaPrec(Naviera idLineaPrec) {
        this.idLineaPrec = idLineaPrec;
    }

    public Itinerario getIdItinerarioPrec() {
        return idItinerarioPrec;
    }

    public void setIdItinerarioPrec(Itinerario idItinerarioPrec) {
        this.idItinerarioPrec = idItinerarioPrec;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoPrec != null ? codigoPrec.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Precarga)) {
            return false;
        }
        Precarga other = (Precarga) object;
        if ((this.codigoPrec == null && other.codigoPrec != null) || (this.codigoPrec != null && !this.codigoPrec.equals(other.codigoPrec))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Precarga[ codigoPrec=" + codigoPrec + " ]";
    }

    /**
     * @return the activo
     */
    public boolean getActivo() {
        if (this.espaciosDisponiblesPrec != 0) {
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
     * @return the peligrosa
     */
    public boolean getPeligrosa() {
        if (this.cargaPeligrosaPrec.equals("S") || this.cargaPeligrosaPrec.equals("true")) {
            peligrosa = true;
        } else {
            peligrosa = false;
        }
        return peligrosa;
    }

    /**
     * @param peligrosa the peligrosa to set
     */
    public void setPeligrosa(boolean peligrosa) {
        if (peligrosa) {
            this.cargaPeligrosaPrec = "S";
        } else {
            this.cargaPeligrosaPrec = "N";
        }
        this.peligrosa = peligrosa;
    }

    /**
     * @return the mostrarTipo
     */
    public String getMostrarTipo() {
        if (this.tipoPrec.equals("I")) {
            mostrarTipo = "IMPORTACION";
        } else {
            mostrarTipo = "EXPORTACION";
        }
        return mostrarTipo;
    }

    /**
     * @return the activoImpo
     */
    public boolean getActivoImpo() {
        if (this.transaccionImpExpList != null) {
            Ordenamiento.burbuja(transaccionImpExpList);
            if (transaccionImpExpList.size() > 0) {
                if (transaccionImpExpList.get(0).getEstado().equals(Estado.Anulado.name())) {
                    activoImpo = true;
                } else {
                    activoImpo = false;
                }
            } else {
                activoImpo = true;
            }
        } else {
            activoImpo = true;
        }
        return activoImpo;
    }

    /**
     * @param activoImpo the activoImpo to set
     */
    public void setActivoImpo(boolean activoImpo) {
        this.activoImpo = activoImpo;
    }

    /**
     * @return the mostrarCondicion
     */
    public String getMostrarCondicion() {
        if (this.condicionContenedorPrec != null) {
            if (this.contenedorPrec.equals("LCL/LCL")) {
                mostrarCondicion = "SUELTA";
            } else if (this.contenedorPrec.equals("FCL/FCL")) {
                mostrarCondicion = "CONTENERIZADA";
            } else {
                mostrarCondicion = "CONSOLIDADA";
            }
        }
        return mostrarCondicion;
    }

    /**
     * @param mostrarCondicion the mostrarCondicion to set
     */
    public void setMostrarCondicion(String mostrarCondicion) {
        this.mostrarCondicion = mostrarCondicion;
    }

    /**
     * @return the habilitado
     */
    public boolean getHabilitado() {
        return habilitado;
    }

    /**
     * @param habilitado the habilitado to set
     */
    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    /**
     * @return the daesUnionTransaccion
     */
    public String getDaesUnionTransaccion() {
        this.daesUnionTransaccion = "";
        if (this.getTransaccionImpExpList() != null) {
            if (this.getTransaccionImpExpList().size() > 0) {
                Ordenamiento.burbuja(this.getTransaccionImpExpList());
                this.daesUnionTransaccion = this.getTransaccionImpExpList().get(this.getTransaccionImpExpList().size() - 1).getDaesTrans();
            }
        }
        return daesUnionTransaccion;
    }

    /**
     * @param daesUnionTransaccion the daesUnionTransaccion to set
     */
    public void setDaesUnionTransaccion(String daesUnionTransaccion) {
        this.daesUnionTransaccion = daesUnionTransaccion;
    }
     /**
     * @return the cantidadAisv
     */
    public int getCantidadAisv() {
        if(totalEspaciosPrec!=null && espaciosDisponiblesPrec!=null){
            cantidadAisv=totalEspaciosPrec-espaciosDisponiblesPrec;
        }
        return cantidadAisv;
    }

    /**
     * @param cantidadAisv the cantidadAisv to set
     */
    public void setCantidadAisv(int cantidadAisv) {
        this.cantidadAisv = cantidadAisv;
    }

}
