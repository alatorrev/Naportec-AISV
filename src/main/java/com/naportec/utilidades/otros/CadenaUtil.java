/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.utilidades.otros;

/**
 *
 * @author MZambrano1
 */
public class CadenaUtil {

    public static boolean NullOrEmpty(String cadena) {
        if (cadena == null) {
            return true;
        } else {
            return cadena.trim().isEmpty();
        }
    }
}
