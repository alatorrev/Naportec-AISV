/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.converters;

import com.naportec.aisv.controladores.ItinerarioBean;
import com.naportec.aisv.controladores.PuertoBean;
import com.naportec.aisv.entidades.Itinerario;
import com.naportec.aisv.entidades.Puerto;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Convertidor usado para Combos y autocompletados
 * @author Fernando
 */
@FacesConverter(value = "itinerarioConverter")
public class ItinerarioConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        ItinerarioBean bean = (ItinerarioBean) fc.getApplication().getELResolver().getValue(fc.getELContext(), null, "itinerarioBean");
        try {
            return bean.getItinerarioLogica().buscarPorCodigo(Long.parseLong(string));
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof String) {
            return null;
        }
        if (o instanceof Itinerario) {
            Itinerario p = (Itinerario) o;
            if (p.getCodigoItin() == null) {
                return null;
            } else {
                return Long.toString(p.getCodigoItin());
            }
        } else {
            throw new IllegalArgumentException(" object " + o);
        }
    }

}
