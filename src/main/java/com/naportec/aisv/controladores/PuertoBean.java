/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.aisv.controladores;

import com.naportec.aisv.entidades.Puerto;
import com.naportec.aisv.logica.PuertoFacade;
import com.naportec.utilidades.controladores.UtilController;
import com.naportec.utilidades.enumeraciones.Estado;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 * Clase controlador para el manejo de las acciones de Puertos
 * @author Fernando
 */
public class PuertoBean extends UtilController<Puerto> {
    /**
     * Bean que contiene la lógica de negocios de Puerto
     */
    @EJB
    private PuertoFacade logicaPuerto;
    
    private List<Puerto> listadoPuerto;

    public PuertoBean() {
        super(Puerto.class);
    }
    
    /**
     * Iniciar datos del controlador
     */
    @PostConstruct
    public void inicializar() {
        this.setEntidadLogica(getLogicaPuerto());
        this.getListadoEntidad().filtroEqual("estado", Estado.Activo.name());
    }
    
    /**
     * Método que permite llenar los datos de lso autocompletados de Puertos en las vistas
     * @param puerto
     * @return 
     */
    public List<Puerto> completePuerto(String puerto){
        List<Puerto> p=new LinkedList<>();
        p=this.logicaPuerto.listaPuertosCoincidencia(puerto);
        return p;
    }

    /**
     * @return the listadoPuerto
     */
    public List<Puerto> getListadoPuerto() throws Exception {
        listadoPuerto=getLogicaPuerto().listarPorEstado(Estado.Activo);
        return listadoPuerto;
    }

    /**
     * @param listadoPuerto the listadoPuerto to set
     */
    public void setListadoPuerto(List<Puerto> listadoPuerto) {
        this.listadoPuerto = listadoPuerto;
    }

    /**
     * @return the logicaPuerto
     */
    public PuertoFacade getLogicaPuerto() {
        return logicaPuerto;
    }

}
