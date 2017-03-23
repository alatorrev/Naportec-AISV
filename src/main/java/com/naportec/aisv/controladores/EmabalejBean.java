package com.naportec.aisv.controladores;

import com.naportec.aisv.entidades.Embalaje;
import com.naportec.aisv.logica.EmbalajeFacade;
import com.naportec.utilidades.controladores.UtilController;
import com.naportec.utilidades.enumeraciones.Estado;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
/**
 * Clase controlador para manejar los Embalajes
 * @author Fernando
 */
public class EmabalejBean extends UtilController<Embalaje> {
    /**
     * Bean quecontiene la logica de negocios de Embalaje.
     * Este objeto no debe inicializarse.
     */
    @EJB
    private EmbalajeFacade logicaEmbalaje;
    /**
     * Listado de embalalajes usado para llenar un combo en la vista
     */
    private List<Embalaje> listado;

    public EmabalejBean() {
        super(Embalaje.class);
    }

    @PostConstruct
    public void inicializar() {
        this.setEntidadLogica(logicaEmbalaje);
    }

    public EmbalajeFacade getLogicaEmbalaje() {
        return logicaEmbalaje;
    }

    public List<Embalaje> getListado() throws Exception {
        listado=this.getLogicaEmbalaje().listarPorEstado(Estado.Activo);
        return listado;
    }

    public void setListado(List<Embalaje> listado) {
        this.listado = listado;
    }

}
