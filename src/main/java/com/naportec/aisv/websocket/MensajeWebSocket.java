/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.aisv.websocket;

import javax.websocket.Session;

public class MensajeWebSocket {
    private String aisv;
    private Session session;

    public MensajeWebSocket(String aisv, Session session) {
        this.aisv = aisv;
        this.session = session;
    }

    public String getAisv() {
        return aisv;
    }

    public void setAisv(String aisv) {
        this.aisv = aisv;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
    
    
}
