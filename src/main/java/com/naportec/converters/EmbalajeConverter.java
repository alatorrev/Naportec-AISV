/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.converters;

import com.naportec.aisv.controladores.EmabalejBean;
import com.naportec.aisv.entidades.Embalaje;
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
@FacesConverter(value = "embalajeConverter")
public class EmbalajeConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
       if(string==null || string.length()==0 || string.toUpperCase().contains("SELECCIONE")){
           return null;
       }
        EmabalejBean bean=(EmabalejBean)fc.getApplication().getELResolver().getValue(fc.getELContext(), null, "emabalejBean");
        try {
            return bean.getLogicaEmbalaje().buscarPorCodigoPrin(string);
        } catch (Exception ex) {
            Logger.getLogger(EmbalajeConverter.class.getName()).log(Level.SEVERE, null, ex);
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
       if(o instanceof Embalaje){
           Embalaje p=(Embalaje)o;
           if(p.getCodigoEmba()==null){
               return null;
           }else{
               return p.getCodigoPrinEmba();
           }
       }else{
           throw new IllegalArgumentException(" object "+ o);
       }
    }
    
}
