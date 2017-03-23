/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.aisv.controladores;

import com.naportec.aisv.entidades.TransaccionVacios;
import com.naportec.aisv.logica.TransaccionVacioFacade;
import com.naportec.utilidades.controladores.UtilController;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author REY COMPUTER
 */
public class VaciosBean extends UtilController<TransaccionVacios>{

    @EJB
    private TransaccionVacioFacade tranVacioFacade;
    /**
     * Creates a new instance of VaciosBean
     */
    public VaciosBean() {
        super(TransaccionVacios.class);
    }
    
    @PostConstruct
    public void inicializar() {
        this.setEntidadLogica(tranVacioFacade);
    }
}
