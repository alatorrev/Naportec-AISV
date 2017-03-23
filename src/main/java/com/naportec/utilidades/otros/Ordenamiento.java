/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.utilidades.otros;

import com.naportec.aisv.entidades.Transaccion;
import java.util.List;

/**
 * Clase utilidad par realizar ordenamiento
 * @author Fernando
 */
public class Ordenamiento {

    /**
     * Método de la la burbuja
     * @param lista 
     */
    public static void burbuja(List<Transaccion> lista) {
        for (int j = 0; j < lista.size(); j++) {
            for (int i = 0; i < lista.size() - 1; i++) {
                if (lista.get(i).getCodigoTrans() < lista.get(i + 1).getCodigoTrans()) {
                    Transaccion aux = lista.get(i);
                    lista.set(i, lista.get(i+1));
                    lista.set(i+1, aux);
                }
            }
        }
    }
}
