package com.naportec.aisv.entidades.EKSAT;

import java.util.Date;

/**
 *
 * @author ecuasis
 */
public class ExportacionEKSAT {
    private Long codigo;
    private String booking;
    private int espaciosBooking;
    private int espaciosDispBooking;
    private int espaciosUsedBooking;
    private String linea;
    private String naveViaje;
    private String propositoCarga;
    private String productoDeclarado;
    private String cargaPeligrosa;
    private String temperatura;
    private String selloNaviera;
    private String selloVentanilla;
    private String selloOpcional1;
    private String selloOpcional2;
    private String codigoContenedor;
    private String puertoDestino;
    private String puertoTrasbordo;
    private Date fechaSalidaPlanta;
    private String primerDae;//ejemplo 028
    private String segundoDae;//ejemplo 2014
    private String tercerDae;//ejemplo 4078999 => DAE= 028-2014-4078999

    public ExportacionEKSAT() {
    }

    //CONTENERIZADA , CONSOLIDADA
    public void exportacionEKSATContenerizada(Long codigo, String booking, int espaciosBooking, int espaciosDispBooking, int espaciosUsedBooking, String linea, String naveViaje, String propositoCarga, String productoDeclarado, String cargaPeligrosa, String temperatura, String selloNaviera, String selloVentanilla, String selloOpcional1, String selloOpcional2, String codigoContenedor, String puertoDestino, String puertoTrasbordo, Date fechaSalidaPlanta, String primerDae, String segundoDae, String tercerDae) {
        this.codigo = codigo;
        this.booking = booking;
        this.espaciosBooking = espaciosBooking;
        this.espaciosDispBooking = espaciosDispBooking;
        this.espaciosUsedBooking = espaciosUsedBooking;
        this.linea = linea;
        this.naveViaje = naveViaje;
        this.propositoCarga = propositoCarga;
        this.productoDeclarado = productoDeclarado;
        this.cargaPeligrosa = cargaPeligrosa;
        this.temperatura = temperatura;
        this.selloNaviera = selloNaviera;
        this.selloVentanilla = selloVentanilla;
        this.selloOpcional1 = selloOpcional1;
        this.selloOpcional2 = selloOpcional2;
        this.codigoContenedor = codigoContenedor;
        this.puertoDestino = puertoDestino;
        this.puertoTrasbordo = puertoTrasbordo;
        this.fechaSalidaPlanta = fechaSalidaPlanta;
        this.primerDae = primerDae;
        this.segundoDae = segundoDae;
        this.tercerDae = tercerDae;
    }
    
     //CARGA SUELTA
    public void exportacionEKSATSuelta(Long codigo, String booking, String linea, String naveViaje, String propositoCarga, String productoDeclarado, String cargaPeligrosa,String puertoDestino, String puertoTrasbordo,String primerDae, String segundoDae, String tercerDae) {
        this.codigo = codigo;
        this.booking = booking;
        this.linea = linea;
        this.naveViaje = naveViaje;
        this.propositoCarga = propositoCarga;
        this.productoDeclarado = productoDeclarado;
        this.cargaPeligrosa = cargaPeligrosa;
        this.puertoDestino = puertoDestino;
        this.puertoTrasbordo = puertoTrasbordo;
        this.primerDae = primerDae;
        this.segundoDae = segundoDae;
        this.tercerDae = tercerDae;
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
     * @return the espaciosDispBooking
     */
    public int getEspaciosDispBooking() {
        return espaciosDispBooking;
    }

    /**
     * @param espaciosDispBooking the espaciosDispBooking to set
     */
    public void setEspaciosDispBooking(int espaciosDispBooking) {
        this.espaciosDispBooking = espaciosDispBooking;
    }

    /**
     * @return the espaciosUsedBooking
     */
    public int getEspaciosUsedBooking() {
        return espaciosUsedBooking;
    }

    /**
     * @param espaciosUsedBooking the espaciosUsedBooking to set
     */
    public void setEspaciosUsedBooking(int espaciosUsedBooking) {
        this.espaciosUsedBooking = espaciosUsedBooking;
    }

    /**
     * @return the linea
     */
    public String getLinea() {
        return linea;
    }

    /**
     * @param linea the linea to set
     */
    public void setLinea(String linea) {
        this.linea = linea;
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
     * @return the productoDeclarado
     */
    public String getProductoDeclarado() {
        return productoDeclarado;
    }

    /**
     * @param productoDeclarado the productoDeclarado to set
     */
    public void setProductoDeclarado(String productoDeclarado) {
        this.productoDeclarado = productoDeclarado;
    }

    /**
     * @return the cargaPeligrosa
     */
    public String getCargaPeligrosa() {
        return cargaPeligrosa;
    }

    /**
     * @param cargaPeligrosa the cargaPeligrosa to set
     */
    public void setCargaPeligrosa(String cargaPeligrosa) {
        this.cargaPeligrosa = cargaPeligrosa;
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
     * @return the puertoDestino
     */
    public String getPuertoDestino() {
        return puertoDestino;
    }

    /**
     * @param puertoDestino the puertoDestino to set
     */
    public void setPuertoDestino(String puertoDestino) {
        this.puertoDestino = puertoDestino;
    }

    /**
     * @return the puertoTrasbordo
     */
    public String getPuertoTrasbordo() {
        return puertoTrasbordo;
    }

    /**
     * @param puertoTrasbordo the puertoTrasbordo to set
     */
    public void setPuertoTrasbordo(String puertoTrasbordo) {
        this.puertoTrasbordo = puertoTrasbordo;
    }

    /**
     * @return the fechaSalidaPlanta
     */
    public Date getFechaSalidaPlanta() {
        return fechaSalidaPlanta;
    }

    /**
     * @param fechaSalidaPlanta the fechaSalidaPlanta to set
     */
    public void setFechaSalidaPlanta(Date fechaSalidaPlanta) {
        this.fechaSalidaPlanta = fechaSalidaPlanta;
    }

    /**
     * @return the primerDae
     */
    public String getPrimerDae() {
        return primerDae;
    }

    /**
     * @param primerDae the primerDae to set
     */
    public void setPrimerDae(String primerDae) {
        this.primerDae = primerDae;
    }

    /**
     * @return the segundoDae
     */
    public String getSegundoDae() {
        return segundoDae;
    }

    /**
     * @param segundoDae the segundoDae to set
     */
    public void setSegundoDae(String segundoDae) {
        this.segundoDae = segundoDae;
    }

    /**
     * @return the tercerDae
     */
    public String getTercerDae() {
        return tercerDae;
    }

    /**
     * @param tercerDae the tercerDae to set
     */
    public void setTercerDae(String tercerDae) {
        this.tercerDae = tercerDae;
    }
    
    
    
}
