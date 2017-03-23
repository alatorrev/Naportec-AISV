/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.naportec.aisv.entidades.EKSAT;

/**
 *
 * @author ecuasis
 */
public class ImportacionEKSAT {
    
    private Long codigo;
    private String blMaster;
    private String blHijo;
    private String numeroContenedor;
    private String naveViaje;
    private String lineaNaviera;
    private String codigoContenedor;
    private String descripcionCarga;
    private String propositoCarga;
    private String tipoContenedor;
    private String referenciaCargaPeligrosa;
    private String temperatura;
    private String sellos;
    //--------------------------------------------------------------------------
    private String numeroTarja;
    private String itinerario;
    //--------------------------------------------------------------------------

    public void importacionEKSATContenerizada(Long codigo, String blMaster, String blHijo, String numeroContenedor, String naveViaje, String lineaNaviera, String codigoContenedor, String descripcionCarga, String tipoContenedor, String referenciaCargaPeligrosa, String temperatura, String sellos,String itinerario) {
        this.codigo = codigo;
        this.blMaster = blMaster;
        this.blHijo = blHijo;
        this.numeroContenedor = numeroContenedor;
        this.naveViaje = naveViaje;
        this.lineaNaviera = lineaNaviera;
        this.codigoContenedor = codigoContenedor;
        this.descripcionCarga = descripcionCarga;
        this.tipoContenedor = tipoContenedor;
        this.referenciaCargaPeligrosa = referenciaCargaPeligrosa;
        this.temperatura = temperatura;
        this.sellos = sellos;
        this.itinerario=itinerario;
    }
    public void importacionEKSATSuelta(Long codigo,String tarja, String blHijo,String naveViaje, String lineaNaviera, String descripcionCarga, String tipoContenedor, String referenciaCargaPeligrosa, String temperatura, String sellos) {
        this.codigo = codigo;
        this.blHijo = blHijo;
        this.naveViaje = naveViaje;
        this.lineaNaviera = lineaNaviera;
        this.descripcionCarga = descripcionCarga;
        this.tipoContenedor = tipoContenedor;
        this.referenciaCargaPeligrosa = referenciaCargaPeligrosa;
        this.temperatura = temperatura;
        this.sellos = sellos;
        this.numeroTarja=tarja;
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
     * @return the numeroContenedor
     */
    public String getNumeroContenedor() {
        return numeroContenedor;
    }

    /**
     * @param numeroContenedor the numeroContenedor to set
     */
    public void setNumeroContenedor(String numeroContenedor) {
        this.numeroContenedor = numeroContenedor;
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
     * @return the codigoContenedor
     */
    public String getCodigoContenedor() {
        return codigoContenedor;
    }

    /**
     * @param codigoContenedor the codigoContenedor to set
     */
    public void setCodigoContenedor(String codigoContenedor) {
        this.codigoContenedor = codigoContenedor;
    }

    /**
     * @return the descripcionCarga
     */
    public String getDescripcionCarga() {
        return descripcionCarga;
    }

    /**
     * @param descripcionCarga the descripcionCarga to set
     */
    public void setDescripcionCarga(String descripcionCarga) {
        this.descripcionCarga = descripcionCarga;
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
    
    
}
