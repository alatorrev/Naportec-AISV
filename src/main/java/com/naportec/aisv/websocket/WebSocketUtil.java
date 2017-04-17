/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.aisv.websocket;

import java.util.List;
import javax.websocket.Session;

/**
 *
 * @author fernando
 */
public class WebSocketUtil {

    public static void sendNotificationDocumental(String mensaje) {
        List<Session> list = DespachoNotificationEndPoint.getListaSession();
        for (Session s : list) {
            if (s.isOpen()) {
               s.getAsyncRemote().sendText(mensaje);
            }
        }
    }

}
