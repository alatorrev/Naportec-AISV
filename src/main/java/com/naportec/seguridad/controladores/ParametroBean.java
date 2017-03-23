package com.naportec.seguridad.controladores;

import com.naportec.seguridad.entidades.SParameter;
import com.naportec.seguridad.logica.SParameterFacade;
import com.naportec.utilidades.controladores.Mensaje;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
/**
 * Clase controlador que maneja las acciones dela vista de Parámetros
 * @author Fernando
 */
public class ParametroBean implements Serializable {

    private SParameter parametro;
    private int guardareditar;

     @EJB
    private SParameterFacade parametroLogica;

    public ParametroBean() {
        guardareditar = 0;
    }

    /**
     * Métodopara modificar lso parametros existentes del sistema
     */
    public void modificarParametros() {
        try {
            if (parametro.getParamTamMin() >= parametro.getParamTamMax()) {
                Mensaje.ERROR("El tamaño mínimo no puede ser mayor que el tamaño máximo. Por favor verifique");
            } else {
                if (parametro.getParamTamMin() < parametro.getNumParametros()) {
                    Mensaje.ERROR("La cantidad de carateres especiales, numeros y letras no deben ser mayores al tamaño Mínimo de la clave");
                } else {
                    if (guardareditar == 0) {
                        parametroLogica.guardar(parametro);
                        Mensaje.SUCESO("Se han Agregado los Parametros");
                    } else {
                        parametroLogica.modificar(parametro);
                        Mensaje.SUCESO("Se han Modificado los Parametros");
                    }
                }
            }
        } catch (Exception ex) {
            Mensaje.ERROR("No se ha podido Modificar los Parametros" + ex.getMessage());
        }
    }

    /**
     * @return the parametro
     */
    public SParameter getParametro() {
        if (parametro == null) {
            try {
                if (parametroLogica.listarTodos().size() == 0) {
                    parametro = new SParameter();
                    guardareditar = 0;
                } else {
                    parametro = parametroLogica.listarTodos().get(0);
                    guardareditar = 1;
                }
            } catch (Exception ex) {
                Logger.getLogger(ParametroBean.class.getName()).log(Level.SEVERE, null, ex);
                return new SParameter();
            }
        }
        return parametro;
    }

    /**
     * @param parametro the parametro to set
     */
    public void setParametro(SParameter parametro) {
        this.parametro = parametro;
    }

    /**
     * @return the guardareditar
     */
    public int getGuardareditar() {
        return guardareditar;
    }

    /**
     * @param guardareditar the guardareditar to set
     */
    public void setGuardareditar(int guardareditar) {
        this.guardareditar = guardareditar;
    }

}
