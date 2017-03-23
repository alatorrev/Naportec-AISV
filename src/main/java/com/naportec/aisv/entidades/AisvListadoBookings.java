/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.aisv.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Vista de datos de Bookings
 * @author Fernando
 */
@Entity
@Table(name = "VIE_AISV_LISTADO_BOOKINGS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AisvListadoBookings.findAll", query = "SELECT a FROM AisvListadoBookings a")
})

public class AisvListadoBookings implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "Linea")
    private String linea;
    @Column(name = "Itinerario")
    private String itinerario;
    @Column(name = "Booking")
    private String booking;
    @Column(name = "Totales")
    private Integer totales;
    @Column(name = "espacios_disponibles_prec")
    private Integer disponibles;
    @Column(name = "Contenedor")
    private String contenedor;
    @Column(name = "TipoContenedor")
    private String tipoContenedor;
    @Column(name = "CondicionContenedor")
    private String condicionContenedor;
    @Column(name = "RUC")
    private String ruc;
    @Column(name = "Exportador")
    private String exportador;
    @Column(name = "Producto")
    private String producto;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Peso")
    private Double peso;
    @Column(name = "Peligrosa")
    private String peligrosa;
    @Column(name = "IMO")
    private String imo;
    @Column(name = "Sello1")
    private String sello1;
    @Column(name = "Sello2")
    private String sello2;
    @Column(name = "Sello3")
    private String sello3;
    @Column(name = "Temperatura")
    private Double temperatura;
    @Column(name = "Ventilacion")
    private Double ventilacion;
    @Column(name = "DAE")
    private String dae;
    @Column(name = "tipo_prec")
    private String tipoPrec;
    @Column(name = "estado_trans")
    private String estadoTrans;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_eir_trans")
    private Date fechaEirTrans;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_creacion_trans")
    private Date fechaCreacionTrans;
    @Id
    @Column(name = "row")
    private long codigo;
    @Column(name = "estado_pan")
    private String estadoPan;

    public AisvListadoBookings() {
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String getItinerario() {
        return itinerario;
    }

    public void setItinerario(String itinerario) {
        this.itinerario = itinerario;
    }

    public String getBooking() {
        return booking;
    }

    public void setBooking(String booking) {
        this.booking = booking;
    }

    public Integer getTotales() {
        return totales;
    }

    public void setTotales(Integer totales) {
        this.totales = totales;
    }

    public String getContenedor() {
        return contenedor;
    }

    public void setContenedor(String contenedor) {
        this.contenedor = contenedor;
    }

    public String getTipoContenedor() {
        return tipoContenedor;
    }

    public void setTipoContenedor(String tipoContenedor) {
        this.tipoContenedor = tipoContenedor;
    }

    public String getCondicionContenedor() {
        return condicionContenedor;
    }

    public void setCondicionContenedor(String condicionContenedor) {
        this.condicionContenedor = condicionContenedor;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getExportador() {
        return exportador;
    }

    public void setExportador(String exportador) {
        this.exportador = exportador;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getPeligrosa() {
        return peligrosa;
    }

    public void setPeligrosa(String peligrosa) {
        this.peligrosa = peligrosa;
    }

    public String getImo() {
        return imo;
    }

    public void setImo(String imo) {
        this.imo = imo;
    }

    public String getSello1() {
        return sello1;
    }

    public void setSello1(String sello1) {
        this.sello1 = sello1;
    }

    public String getSello2() {
        return sello2;
    }

    public void setSello2(String sello2) {
        this.sello2 = sello2;
    }

    public String getSello3() {
        return sello3;
    }

    public void setSello3(String sello3) {
        this.sello3 = sello3;
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }

    public Double getVentilacion() {
        return ventilacion;
    }

    public void setVentilacion(Double ventilacion) {
        this.ventilacion = ventilacion;
    }

    public String getDae() {
        return dae;
    }

    public void setDae(String dae) {
        this.dae = dae;
    }

    public String getTipoPrec() {
        return tipoPrec;
    }

    public void setTipoPrec(String tipoPrec) {
        this.tipoPrec = tipoPrec;
    }

    public String getEstadoTrans() {
        return estadoTrans;
    }

    public void setEstadoTrans(String estadoTrans) {
        this.estadoTrans = estadoTrans;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the disponibles
     */
    public Integer getDisponibles() {
        return disponibles;
    }

    /**
     * @param disponibles the disponibles to set
     */
    public void setDisponibles(Integer disponibles) {
        this.disponibles = disponibles;
    }

    /**
     * @return the fechaEirTrans
     */
    public Date getFechaEirTrans() {
        return fechaEirTrans;
    }

    /**
     * @param fechaEirTrans the fechaEirTrans to set
     */
    public void setFechaEirTrans(Date fechaEirTrans) {
        this.fechaEirTrans = fechaEirTrans;
    }

    /**
     * @return the fechaCreacionTrans
     */
    public Date getFechaCreacionTrans() {
        return fechaCreacionTrans;
    }

    /**
     * @param fechaCreacionTrans the fechaCreacionTrans to set
     */
    public void setFechaCreacionTrans(Date fechaCreacionTrans) {
        this.fechaCreacionTrans = fechaCreacionTrans;
    }

    /**
     * @return the estadoPan
     */
    public String getEstadoPan() {
        return estadoPan;
    }

    /**
     * @param estadoPan the estadoPan to set
     */
    public void setEstadoPan(String estadoPan) {
        this.estadoPan = estadoPan;
    }

}
