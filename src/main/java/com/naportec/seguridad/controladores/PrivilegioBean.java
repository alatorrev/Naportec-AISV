package com.naportec.seguridad.controladores;

import com.naportec.seguridad.entidades.SRight;
import com.naportec.seguridad.logica.SRightFacade;
import com.naportec.utilidades.controladores.UtilController;
import com.naportec.utilidades.enumeraciones.Estado;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
/**
 * Clase controlador que permite manejar las acciones de la vista de Privilegios
 * @author Fernando
 */
public class PrivilegioBean extends UtilController<SRight> {

     @EJB
    private SRightFacade privilegioLogica;

    public PrivilegioBean() {
        super(SRight.class);
    }

    @PostConstruct
    public void inicializar() {
        this.setEntidadLogica(privilegioLogica);
        this.getListadoEntidad().filtroEqual("estado", Estado.Activo.name());
    }

    /**
     * Preparar para crear un privilegio
     * @param evt 
     */
    @Override
    public void prepararCrear(ActionEvent evt) {
        super.prepararCrear(evt); //To change body of generated methods, choose Tools | Templates.
        this.getEntidad().setHoraingresoAu(new Date());
        this.getEntidad().setUsuarioingresoAu(this.getCurrentloggeduser());
    }

    /**
     * Método para preparar para editar un privilegio
     * @param evt 
     */
    @Override
    public void prepararEditar(ActionEvent evt) {
        super.prepararEditar(evt); //To change body of generated methods, choose Tools | Templates.
        this.getEntidad().setHoramodificacionAu(new Date());
        this.getEntidad().setUsuariomodificacionAu(this.getCurrentloggeduser());
    }

}
