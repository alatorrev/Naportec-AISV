/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.aisv.controladores;

import com.naportec.aisv.entidades.PropositoCarga;
import com.naportec.aisv.logica.TipoPropositoFacade;
import com.naportec.utilidades.controladores.UtilController;
import com.naportec.utilidades.enumeraciones.Estado;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
/**
 * Clase controlador que permite manejar las acciones del Proposito de Carga
 * @author Fernando
 */
public class PropositoBean extends UtilController<PropositoCarga> implements Serializable{
    @EJB
    private TipoPropositoFacade logicaProposito;
    private List<PropositoCarga> listado;
    /**
     * Creates a new instance of PropositoBean
     */
    public PropositoBean() {
        super(PropositoCarga.class);
    }
    
    @PostConstruct
    public void inicializar(){
        this.setEntidadLogica(logicaProposito);
    }

    /**
     * @return the logicaProposito
     */
    public TipoPropositoFacade getLogicaProposito() {
       if(logicaProposito==null){
            this.logicaProposito=new TipoPropositoFacade();
       }
        return logicaProposito;
    }
    /**
     * @return the listado
     */
    public List<PropositoCarga> getListado() throws Exception {
        listado=this.getLogicaProposito().listarPorEstado(Estado.Activo);
        return listado;
    }
    
    /**
     * Método para obtener el listado de propósitos de Importación Contenerizada
     * @return 
     */
    public List<PropositoCarga> listadoImpoCont(){
        try {
            return this.getLogicaProposito().buscarPropositos("I", true, false, false);
        } catch (Exception ex) {
            Logger.getLogger(PropositoBean.class.getName()).log(Level.SEVERE, null, ex);
            return new LinkedList<PropositoCarga>();
        }
    }
      /**
     * Método para obtener el listado de propósitos de Importación carga Suelta
     * @return 
     */
     public List<PropositoCarga> listadoImpoSuelta(){
        try {
            return this.getLogicaProposito().buscarPropositos("I", false, true, false);
        } catch (Exception ex) {
            Logger.getLogger(PropositoBean.class.getName()).log(Level.SEVERE, null, ex);
            return new LinkedList<PropositoCarga>();
        }
    }
    @Deprecated
    public List<PropositoCarga> listadoImpoConsol(){
        try {
            return this.getLogicaProposito().buscarPropositos("I", false, false, true);
        } catch (Exception ex) {
            Logger.getLogger(PropositoBean.class.getName()).log(Level.SEVERE, null, ex);
            return new LinkedList<PropositoCarga>();
        }
    }
    /**
    * Método para obtener el listado de propósitos de Exportación Contenerizada
    * @return 
    */
       public List<PropositoCarga> listadoExpoCont(){
        try {
            return this.getLogicaProposito().buscarPropositos("E", true, false, false);
        } catch (Exception ex) {
            Logger.getLogger(PropositoBean.class.getName()).log(Level.SEVERE, null, ex);
            return new LinkedList<PropositoCarga>();
        }
    }
     /**
     * Método para obtener el listado de propósitos de Exportación Carga Suelta
     * @return 
     */
     public List<PropositoCarga> listadoExpoSuelta(){
        try {
            return this.getLogicaProposito().buscarPropositos("E", false, true, false);
        } catch (Exception ex) {
            Logger.getLogger(PropositoBean.class.getName()).log(Level.SEVERE, null, ex);
            return new LinkedList<PropositoCarga>();
        }
    }
       /**
     * Método para obtener el listado de propósitos de Exportación de carga para Consolidación
     * @return 
     */
      public List<PropositoCarga> listadoExpoConsol(){
        try {
            return this.getLogicaProposito().buscarPropositos("E", false, false, true);
        } catch (Exception ex) {
            Logger.getLogger(PropositoBean.class.getName()).log(Level.SEVERE, null, ex);
            return new LinkedList<PropositoCarga>();
        }
    }
    
}
