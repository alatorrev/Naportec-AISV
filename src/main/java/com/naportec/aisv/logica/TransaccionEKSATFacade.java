/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.aisv.logica;

import com.naportec.aisv.entidades.EKSAT.TransaccionEKSAT;
import java.util.LinkedList;
import java.util.List;

/**
 * Datos utilziados en las primeras pruebas 
 * @author Fernando
 */
public class TransaccionEKSATFacade {
    
    public List<TransaccionEKSAT> importacionContenerizada(){
        List<TransaccionEKSAT> lista=new LinkedList<TransaccionEKSAT>();
        TransaccionEKSAT t=new TransaccionEKSAT(1l,"BL-ITGOA", "001", "123", "ELIZA", "DOLE", "HK0", "BANANAS", "AK-7", "NO", "30", "SELLOS","itinerario");
        lista.add(t);
        return lista;
    }
    public List<TransaccionEKSAT> exportacionContenerizada(){
        List<TransaccionEKSAT> lista=new LinkedList<TransaccionEKSAT>();
        TransaccionEKSAT t=new TransaccionEKSAT(2l,"BL-ITGOAA", "001", "123", "ELIZA", "DOLE", "HK0", "BANANAS", "AK-7", "NO", "30", "SELLOS","itinerario");
        lista.add(t);
        return lista;
    }
}
