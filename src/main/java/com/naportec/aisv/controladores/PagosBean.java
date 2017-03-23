/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.aisv.controladores;

import com.naportec.aisv.entidades.Pagos;
import com.naportec.aisv.logica.PagosFacade;
import com.naportec.utilidades.controladores.UtilController;
import com.naportec.utilidades.enumeraciones.Estado;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author MZambrano1
 */
public class PagosBean extends UtilController<Pagos> {
    @EJB
    private PagosFacade logicaPagos;
    private List<Pagos> listado;

    public PagosBean() {
        super(Pagos.class);
    }
    /**
     * Inicialización de listado lazy.
     **/
    @PostConstruct
    public void inicializar(){
        this.setEntidadLogica(logicaPagos);
        //this.getListadoEntidad().filtroEqual("estado", Estado.Activo.name());
    }

    public List<Pagos> getListado() {
        listado=logicaPagos.listarTodos();
        return listado;
    }

    public void setListado(List<Pagos> listado) {
        this.listado = listado;
    }
    
    
    
}
