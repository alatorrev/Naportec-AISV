/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.aisv.websocket;

public class MensajeWebSocket {
    private int contador;
    private String numeroAisv;
    private TipoMensaje tipo;
    private TipoUsuario tipoUsuario;
    private String detalle;
    private String usuario;

    public MensajeWebSocket() {
    }

    public MensajeWebSocket(int contador, String numeroAisv, TipoMensaje tipo, TipoUsuario tipoUsuario, String detalle,String usuario) {
        this.contador = contador;
        this.numeroAisv = numeroAisv;
        this.tipo = tipo;
        this.tipoUsuario = tipoUsuario;
        this.detalle = detalle;
        this.usuario=usuario;
    }

  
    public MensajeWebSocket(String msg) {
        String[] s=msg.split("\\|");
        this.contador = Integer.parseInt(s[0]);
        this.numeroAisv = s[1];
        this.tipo = TipoMensaje.valueOf(s[2]);
        this.tipoUsuario=TipoUsuario.valueOf(s[3]);
        this.detalle = s[4];
        this.usuario=s[5];
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public String getNumeroAisv() {
        return numeroAisv;
    }

    public void setNumeroAisv(String numeroAisv) {
        this.numeroAisv = numeroAisv;
    }

    public TipoMensaje getTipo() {
        return tipo;
    }

    public void setTipo(TipoMensaje tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return contador + "|" + numeroAisv + "|" + tipo.name()+"|"+getTipoUsuario().name()+"|"+getDetalle()+"|"+getUsuario();
    }

    /**
     * @return the detalle
     */
    public String getDetalle() {
        return detalle;
    }

    /**
     * @param detalle the detalle to set
     */
    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    /**
     * @return the tipoUsuario
     */
    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    /**
     * @param tipoUsuario the tipoUsuario to set
     */
    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    
    
}
