/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.aisv.entidades.EKSAT;

import java.util.Date;

public class TransaccionEKSAT {

    private Long codigo;
    private String blMaster;
    private String blHijo;
    private String contenedor;
    private String naveViaje;
    private String lineaNaviera;
    private String productoCarga;
    private String propositoCarga;
    private String tipoContenedor;
    private String referenciaCargaPeligrosa;
    private String codigoCargaPeligrosa;
    private String temperatura;
    private String sellos;
    //--------------------------------------------------------------------------
    private String numeroTarja;
    private String itinerario;
    //--------------------------------------------------------------------------
    private String agenteAfianzado;
    private String codigoAgenteAfianzado;
    private String nombreImportador;
    private String codigoImportador;
    //--------------------------------------------------------------------------
    private String booking;
    private int espaciosBooking;
    private int disponiblesBooking;
    private int usadosBooking;
    private String selloNaviera;
    private String selloVentanilla;
    private String selloOpcional1;
    private String selloOpcional2;
    private long puertoDestino;
    private long puertoTrasbordo;
    private long puertoOrigen;
    //private Date fechaSalida;
    private String daePrimero;
    private String daeSegundo;
    private String daeTercero;
    private String cupo;
    private String numeroCajas;
    //--------------------------------------------------------------------------
/**
 * USADO PARA IMPORTACION DE CARGA CONTENERIZADA
 * @param codigo
 * @param blMaster
 * @param blHijo
 * @param contenedor
 * @param naveViaje
 * @param lineaNaviera
 * @param productoCarga
 * @param tipoContenedor
 * @param referenciaCargaPeligrosa
 * @param codigoCargaPeligrosa
 * @param temperatura
 * @param sellos
 * @param itinerario 
 */
    public TransaccionEKSAT(Long codigo, String blMaster, String blHijo, String contenedor, String naveViaje, String lineaNaviera, String productoCarga, String tipoContenedor, String referenciaCargaPeligrosa, String codigoCargaPeligrosa, String temperatura, String sellos, String itinerario) {
        this.codigo = codigo;
        this.blMaster = blMaster;
        this.blHijo = blHijo;
        this.contenedor = contenedor;
        this.naveViaje = naveViaje;
        this.lineaNaviera = lineaNaviera;
        this.productoCarga = productoCarga;
        this.tipoContenedor = tipoContenedor;
        this.referenciaCargaPeligrosa = referenciaCargaPeligrosa;
        this.codigoCargaPeligrosa = codigoCargaPeligrosa;
        this.temperatura = temperatura;
        this.sellos = sellos;
        this.itinerario = itinerario;
    }
/**
 * USADO PARA IOMPORTACION DE CARGA SUELTA
 * @param codigo
 * @param blHijo
 * @param naveViaje
 * @param lineaNaviera
 * @param productoCarga
 * @param numeroTarja 
 */
    public TransaccionEKSAT(Long codigo, String blHijo, String naveViaje, String lineaNaviera, String productoCarga, String numeroTarja) {
        this.codigo = codigo;
        this.blHijo = blHijo;
        this.naveViaje = naveViaje;
        this.lineaNaviera = lineaNaviera;
        this.productoCarga = productoCarga;
        this.numeroTarja = numeroTarja;
    }
/**
 * USADO PARA EXPORTACION DE CARGA CONTENERIZADA
 * @param codigo
 * @param contenedor
 * @param naveViaje
 * @param lineaNaviera
 * @param productoCarga
 * @param tipoContenedor
 * @param referenciaCargaPeligrosa
 * @param codigoCargaPeligrosa
 * @param temperatura
 * @param booking
 * @param espaciosBooking
 * @param disponiblesBooking
 * @param usadosBooking
 * @param selloNaviera
 * @param selloVentanilla
 * @param selloOpcional1
 * @param selloOpcional2
 * @param puertoDestino
 * @param puertoTrasbordo
 * @param puertoOrigen
 * @param fechaSalida
 * @param daePrimero
 * @param daeSegundo
 * @param daeTercero
 * @param cupo
 * @param numeroCajas 
 */
    public TransaccionEKSAT(Long codigo, String contenedor, String naveViaje, String lineaNaviera, String productoCarga, String tipoContenedor, String referenciaCargaPeligrosa, String codigoCargaPeligrosa, String temperatura, String booking, int espaciosBooking, int disponiblesBooking, int usadosBooking, String selloNaviera, String selloVentanilla, String selloOpcional1, String selloOpcional2, long puertoDestino, long puertoTrasbordo, long puertoOrigen, String daePrimero, String daeSegundo, String daeTercero, String cupo, String numeroCajas) {
        this.codigo = codigo;
        this.contenedor = contenedor;
        this.naveViaje = naveViaje;
        this.lineaNaviera = lineaNaviera;
        this.productoCarga = productoCarga;
        this.tipoContenedor = tipoContenedor;
        this.referenciaCargaPeligrosa = referenciaCargaPeligrosa;
        this.codigoCargaPeligrosa = codigoCargaPeligrosa;
        this.temperatura = temperatura;
        this.booking = booking;
        this.espaciosBooking = espaciosBooking;
        this.disponiblesBooking = disponiblesBooking;
        this.usadosBooking = usadosBooking;
        this.selloNaviera = selloNaviera;
        this.selloVentanilla = selloVentanilla;
        this.selloOpcional1 = selloOpcional1;
        this.selloOpcional2 = selloOpcional2;
        this.puertoDestino = puertoDestino;
        this.puertoTrasbordo = puertoTrasbordo;
        this.puertoOrigen = puertoOrigen;
        this.daePrimero = daePrimero;
        this.daeSegundo = daeSegundo;
        this.daeTercero = daeTercero;
        this.cupo = cupo;
        this.numeroCajas = numeroCajas;
    }
/**
 * USADO PARA EXPORTACION CARGA SUELTA
 * @param codigo
 * @param naveViaje
 * @param lineaNaviera
 * @param productoCarga
 * @param referenciaCargaPeligrosa
 * @param codigoCargaPeligrosa
 * @param booking
 * @param puertoDestino
 * @param puertoTrasbordo
 * @param puertoOrigen
 * @param daePrimero
 * @param daeSegundo
 * @param daeTercero
 * @param cupo
 * @param numeroCajas 
 */
    public TransaccionEKSAT(Long codigo, String naveViaje, String lineaNaviera, String productoCarga, String referenciaCargaPeligrosa, String codigoCargaPeligrosa, String booking, long puertoDestino, long puertoTrasbordo, long puertoOrigen, String daePrimero, String daeSegundo, String daeTercero, String cupo, String numeroCajas) {
        this.codigo = codigo;
        this.naveViaje = naveViaje;
        this.lineaNaviera = lineaNaviera;
        this.productoCarga = productoCarga;
        this.referenciaCargaPeligrosa = referenciaCargaPeligrosa;
        this.codigoCargaPeligrosa = codigoCargaPeligrosa;
        this.booking = booking;
        this.puertoDestino = puertoDestino;
        this.puertoTrasbordo = puertoTrasbordo;
        this.puertoOrigen = puertoOrigen;
        this.daePrimero = daePrimero;
        this.daeSegundo = daeSegundo;
        this.daeTercero = daeTercero;
        this.cupo = cupo;
        this.numeroCajas = numeroCajas;
    }

    
    
    /**
     * @return the codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the blMaster
     */
    public String getBlMaster() {
        return blMaster;
    }

    /**
     * @param blMaster the blMaster to set
     */
    public void setBlMaster(String blMaster) {
        this.blMaster = blMaster;
    }

    /**
     * @return the blHijo
     */
    public String getBlHijo() {
        return blHijo;
    }

    /**
     * @param blHijo the blHijo to set
     */
    public void setBlHijo(String blHijo) {
        this.blHijo = blHijo;
    }

    /**
     * @return the contenedor
     */
    public String getContenedor() {
        return contenedor;
    }

    /**
     * @param contenedor the contenedor to set
     */
    public void setContenedor(String contenedor) {
        this.contenedor = contenedor;
    }

    /**
     * @return the naveViaje
     */
    public String getNaveViaje() {
        return naveViaje;
    }

    /**
     * @param naveViaje the naveViaje to set
     */
    public void setNaveViaje(String naveViaje) {
        this.naveViaje = naveViaje;
    }

    /**
     * @return the lineaNaviera
     */
    public String getLineaNaviera() {
        return lineaNaviera;
    }

    /**
     * @param lineaNaviera the lineaNaviera to set
     */
    public void setLineaNaviera(String lineaNaviera) {
        this.lineaNaviera = lineaNaviera;
    }

    /**
     * @return the productoCarga
     */
    public String getProductoCarga() {
        return productoCarga;
    }

    /**
     * @param productoCarga the productoCarga to set
     */
    public void setProductoCarga(String productoCarga) {
        this.productoCarga = productoCarga;
    }

    /**
     * @return the propositoCarga
     */
    public String getPropositoCarga() {
        return propositoCarga;
    }

    /**
     * @param propositoCarga the propositoCarga to set
     */
    public void setPropositoCarga(String propositoCarga) {
        this.propositoCarga = propositoCarga;
    }

    /**
     * @return the tipoContenedor
     */
    public String getTipoContenedor() {
        return tipoContenedor;
    }

    /**
     * @param tipoContenedor the tipoContenedor to set
     */
    public void setTipoContenedor(String tipoContenedor) {
        this.tipoContenedor = tipoContenedor;
    }

    /**
     * @return the referenciaCargaPeligrosa
     */
    public String getReferenciaCargaPeligrosa() {
        return referenciaCargaPeligrosa;
    }

    /**
     * @param referenciaCargaPeligrosa the referenciaCargaPeligrosa to set
     */
    public void setReferenciaCargaPeligrosa(String referenciaCargaPeligrosa) {
        this.referenciaCargaPeligrosa = referenciaCargaPeligrosa;
    }

    /**
     * @return the codigoCargaPeligrosa
     */
    public String getCodigoCargaPeligrosa() {
        return codigoCargaPeligrosa;
    }

    /**
     * @param codigoCargaPeligrosa the codigoCargaPeligrosa to set
     */
    public void setCodigoCargaPeligrosa(String codigoCargaPeligrosa) {
        this.codigoCargaPeligrosa = codigoCargaPeligrosa;
    }

    /**
     * @return the temperatura
     */
    public String getTemperatura() {
        return temperatura;
    }

    /**
     * @param temperatura the temperatura to set
     */
    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    /**
     * @return the sellos
     */
    public String getSellos() {
        return sellos;
    }

    /**
     * @param sellos the sellos to set
     */
    public void setSellos(String sellos) {
        this.sellos = sellos;
    }

    /**
     * @return the numeroTarja
     */
    public String getNumeroTarja() {
        return numeroTarja;
    }

    /**
     * @param numeroTarja the numeroTarja to set
     */
    public void setNumeroTarja(String numeroTarja) {
        this.numeroTarja = numeroTarja;
    }

    /**
     * @return the itinerario
     */
    public String getItinerario() {
        return itinerario;
    }

    /**
     * @param itinerario the itinerario to set
     */
    public void setItinerario(String itinerario) {
        this.itinerario = itinerario;
    }

    /**
     * @return the agenteAfianzado
     */
    public String getAgenteAfianzado() {
        return agenteAfianzado;
    }

    /**
     * @param agenteAfianzado the agenteAfianzado to set
     */
    public void setAgenteAfianzado(String agenteAfianzado) {
        this.agenteAfianzado = agenteAfianzado;
    }

    /**
     * @return the codigoAgenteAfianzado
     */
    public String getCodigoAgenteAfianzado() {
        return codigoAgenteAfianzado;
    }

    /**
     * @param codigoAgenteAfianzado the codigoAgenteAfianzado to set
     */
    public void setCodigoAgenteAfianzado(String codigoAgenteAfianzado) {
        this.codigoAgenteAfianzado = codigoAgenteAfianzado;
    }

    /**
     * @return the nombreImportador
     */
    public String getNombreImportador() {
        return nombreImportador;
    }

    /**
     * @param nombreImportador the nombreImportador to set
     */
    public void setNombreImportador(String nombreImportador) {
        this.nombreImportador = nombreImportador;
    }

    /**
     * @return the codigoImportador
     */
    public String getCodigoImportador() {
        return codigoImportador;
    }

    /**
     * @param codigoImportador the codigoImportador to set
     */
    public void setCodigoImportador(String codigoImportador) {
        this.codigoImportador = codigoImportador;
    }

    /**
     * @return the booking
     */
    public String getBooking() {
        return booking;
    }

    /**
     * @param booking the booking to set
     */
    public void setBooking(String booking) {
        this.booking = booking;
    }

    /**
     * @return the espaciosBooking
     */
    public int getEspaciosBooking() {
        return espaciosBooking;
    }

    /**
     * @param espaciosBooking the espaciosBooking to set
     */
    public void setEspaciosBooking(int espaciosBooking) {
        this.espaciosBooking = espaciosBooking;
    }

    /**
     * @return the disponiblesBooking
     */
    public int getDisponiblesBooking() {
        return disponiblesBooking;
    }

    /**
     * @param disponiblesBooking the disponiblesBooking to set
     */
    public void setDisponiblesBooking(int disponiblesBooking) {
        this.disponiblesBooking = disponiblesBooking;
    }

    /**
     * @return the usadosBooking
     */
    public int getUsadosBooking() {
        return usadosBooking;
    }

    /**
     * @param usadosBooking the usadosBooking to set
     */
    public void setUsadosBooking(int usadosBooking) {
        this.usadosBooking = usadosBooking;
    }

    /**
     * @return the selloNaviera
     */
    public String getSelloNaviera() {
        return selloNaviera;
    }

    /**
     * @param selloNaviera the selloNaviera to set
     */
    public void setSelloNaviera(String selloNaviera) {
        this.selloNaviera = selloNaviera;
    }

    /**
     * @return the selloVentanilla
     */
    public String getSelloVentanilla() {
        return selloVentanilla;
    }

    /**
     * @param selloVentanilla the selloVentanilla to set
     */
    public void setSelloVentanilla(String selloVentanilla) {
        this.selloVentanilla = selloVentanilla;
    }

    /**
     * @return the selloOpcional1
     */
    public String getSelloOpcional1() {
        return selloOpcional1;
    }

    /**
     * @param selloOpcional1 the selloOpcional1 to set
     */
    public void setSelloOpcional1(String selloOpcional1) {
        this.selloOpcional1 = selloOpcional1;
    }

    /**
     * @return the selloOpcional2
     */
    public String getSelloOpcional2() {
        return selloOpcional2;
    }

    /**
     * @param selloOpcional2 the selloOpcional2 to set
     */
    public void setSelloOpcional2(String selloOpcional2) {
        this.selloOpcional2 = selloOpcional2;
    }

    /**
     * @return the puertoDestino
     */
    public long getPuertoDestino() {
        return puertoDestino;
    }

    /**
     * @param puertoDestino the puertoDestino to set
     */
    public void setPuertoDestino(long puertoDestino) {
        this.puertoDestino = puertoDestino;
    }

    /**
     * @return the puertoTrasbordo
     */
    public long getPuertoTrasbordo() {
        return puertoTrasbordo;
    }

    /**
     * @param puertoTrasbordo the puertoTrasbordo to set
     */
    public void setPuertoTrasbordo(long puertoTrasbordo) {
        this.puertoTrasbordo = puertoTrasbordo;
    }

    /**
     * @return the puertoOrigen
     */
    public long getPuertoOrigen() {
        return puertoOrigen;
    }

    /**
     * @param puertoOrigen the puertoOrigen to set
     */
    public void setPuertoOrigen(long puertoOrigen) {
        this.puertoOrigen = puertoOrigen;
    }

    /**
     * @return the daePrimero
     */
    public String getDaePrimero() {
        return daePrimero;
    }

    /**
     * @param daePrimero the daePrimero to set
     */
    public void setDaePrimero(String daePrimero) {
        this.daePrimero = daePrimero;
    }

    /**
     * @return the daeSegundo
     */
    public String getDaeSegundo() {
        return daeSegundo;
    }

    /**
     * @param daeSegundo the daeSegundo to set
     */
    public void setDaeSegundo(String daeSegundo) {
        this.daeSegundo = daeSegundo;
    }

    /**
     * @return the daeTercero
     */
    public String getDaeTercero() {
        return daeTercero;
    }

    /**
     * @param daeTercero the daeTercero to set
     */
    public void setDaeTercero(String daeTercero) {
        this.daeTercero = daeTercero;
    }

    /**
     * @return the cupo
     */
    public String getCupo() {
        return cupo;
    }

    /**
     * @param cupo the cupo to set
     */
    public void setCupo(String cupo) {
        this.cupo = cupo;
    }

    /**
     * @return the numeroCajas
     */
    public String getNumeroCajas() {
        return numeroCajas;
    }

    /**
     * @param numeroCajas the numeroCajas to set
     */
    public void setNumeroCajas(String numeroCajas) {
        this.numeroCajas = numeroCajas;
    }
}
