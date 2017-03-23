/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.converters;

import com.naportec.aisv.controladores.PropositoBean;
import com.naportec.aisv.controladores.PuertoBean;
import com.naportec.aisv.entidades.PropositoCarga;
import com.naportec.aisv.entidades.Puerto;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Convertidor usado para Combos y autocompletados
 * @author Fernando
 */
@FacesConverter(value = "propositoConverter")
public class PropositoConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
       if(string==null || string.length()==0 || string.toUpperCase().contains("SELECCIONE")){
           return null;
       }
        PropositoBean bean=(PropositoBean)fc.getApplication().getELResolver().getValue(fc.getELContext(), null, "propositoBean");
        try {
            return bean.getLogicaProposito().buscarPorCodigo(Long.parseLong(string));
        } catch (Exception ex) {
            Logger.getLogger(PropositoConverter.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
       if(o==null){
        return null;   
       }
       if(o instanceof String){
           return null;
       }
       if(o instanceof PropositoCarga){
           PropositoCarga p=(PropositoCarga)o;
           if(p.getCodigoProp()==null){
               return null;
           }else{
               return Long.toString(p.getCodigoProp());
           }
       }else{
           throw new IllegalArgumentException(" object "+ o);
       }
    }
    
}
