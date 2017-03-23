/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.aisv.websocket;

import com.naportec.aisv.entidades.Transaccion;
import com.naportec.utilidades.enumeraciones.Estado;
import org.primefaces.context.RequestContext;

/**
 *
 * @author fernando
 */
public class WebSocketUtil {
    
    public static void envioNotificacionDocumental(Transaccion tran) {
        MensajeWebSocket msg = new MensajeWebSocket();
        msg.setNumeroAisv(tran.getCodigoTrans().toString());
        Estado estado = Estado.valueOf(tran.getEstado());
        switch (estado) {
            case Documental:
                msg.setDetalle("AISV Aprobado Documentalmente");
                break;
            case Desaprobado:
                msg.setDetalle("AISV Desaprobado Documentalmente");
                break;
        }
        msg.setTipoUsuario(TipoUsuario.DESPACHO);
        msg.setTipo(TipoMensaje.DOCUMENTAL);
        RequestContext.getCurrentInstance().execute("webSocket.send('" + msg.toString() + "')");
    }
    
    public static void envioNotificacionInicial(int contIngR8){
        MensajeWebSocket msg = new MensajeWebSocket();
        msg.setNumeroAisv("0000");
        msg.setDetalle("ENVIO INICIAL");
        msg.setTipoUsuario(TipoUsuario.DESPACHO);
        msg.setTipo(TipoMensaje.INICIADOR);
        msg.setContador(contIngR8);
        msg.setUsuario("web");
        RequestContext.getCurrentInstance().execute("webSocket.send('" + msg.toString() + "')");
    }
   
    
}
