/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.converters;

import com.naportec.aisv.controladores.PuertoBean;
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
@FacesConverter(value = "puertoConverter")
public class PuertoConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
       if(string==null || string.length()==0 || string.toUpperCase().contains("SELECCIONE")){
           return null;
       }
        PuertoBean bean=(PuertoBean)fc.getApplication().getELResolver().getValue(fc.getELContext(), null, "puertoBean");
        try {
            return bean.getLogicaPuerto().buscarPorCodigo(Long.parseLong(string));
        } catch (Exception ex) {
            Logger.getLogger(PuertoConverter.class.getName()).log(Level.SEVERE, null, ex);
            return bean.getLogicaPuerto().buscarPorNombrePuertoConverter(string);
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
       if(o instanceof Puerto){
           Puerto p=(Puerto)o;
           if(p.getCodigoPuer()==null){
               return null;
           }else{
               return Long.toString(p.getCodigoPuer());
           }
       }else{
           throw new IllegalArgumentException(" object "+ o);
       }
    }
    
}
