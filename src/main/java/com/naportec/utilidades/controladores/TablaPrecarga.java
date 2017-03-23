/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.utilidades.controladores;

import com.naportec.aisv.entidades.Precarga;
import java.util.List;

/**
 *
 * @author fernando
 */
public class TablaPrecarga {
    
    private List<Precarga> listado;
    private int count;

    public TablaPrecarga() {
    }

    
    public TablaPrecarga(List<Precarga> listado, int count) {
        this.listado = listado;
        this.count = count;
    }

    /**
     * @return the listado
     */
    public List<Precarga> getListado() {
        return listado;
    }

    /**
     * @param listado the listado to set
     */
    public void setListado(List<Precarga> listado) {
        this.listado = listado;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }
}
