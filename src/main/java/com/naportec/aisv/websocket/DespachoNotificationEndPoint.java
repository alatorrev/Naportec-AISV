/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.aisv.websocket;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;
import org.primefaces.push.annotation.PushEndpoint;

/**
 *
 * @author Desarrollo1
 */
@ServerEndpoint(value = "/despacho")
public class DespachoNotificationEndPoint implements Serializable {

    public static List<String> listaValores = new ArrayList<>();

    @OnMessage
    public void messageRecive(Session s, String message) {
        getListaValores().add(message);
        System.out.println(message);
    }

    @OnOpen
    public void onOpen(Session s) {
        System.out.println(s.getId());
    }

    @OnClose
    public void onClose(Session s) {

    }

    public static void resetResource() {
        getListaValores().clear();
    }

    public static List<String> getListaValores() {
        return listaValores;
    }

    public static void setListaValores(List<String> listaValores) {
        DespachoNotificationEndPoint.listaValores = listaValores;
    }

}
