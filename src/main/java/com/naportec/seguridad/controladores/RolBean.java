package com.naportec.seguridad.controladores;

import com.naportec.seguridad.entidades.SRole;
import com.naportec.seguridad.logica.SRoleFacade;
import com.naportec.utilidades.controladores.UtilController;
import com.naportec.utilidades.enumeraciones.Estado;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;

/**
 * Clase controlador que permite manejar las acciones de la vista de rol
 *
 * @author Fernando
 */
public class RolBean extends UtilController<SRole> {

    @EJB
    private SRoleFacade rolLogica;

    public RolBean() {
        super(SRole.class);
    }

    @PostConstruct
    public void inicializar() {
        this.setEntidadLogica(rolLogica);
        this.getListadoEntidad().filtroEqual("estado", Estado.Activo.name());
    }

    /**
     * Método para preparar para editar un ROL
     *
     * @param evt
     */
    @Override
    public void prepararEditar(ActionEvent evt) {
        super.prepararEditar(evt); //To change body of generated methods, choose Tools | Templates.
        this.getEntidad().setHoramodificacionAu(new Date());
        this.getEntidad().setUsuariomodificacionAu(this.getCurrentloggeduser());
    }

    /**
     * Método para preparar para crear un ROL
     *
     * @param evt
     */
    @Override
    public void prepararCrear(ActionEvent evt) {
        super.prepararCrear(evt); //To change body of generated methods, choose Tools | Templates.
        this.getEntidad().setHoraingresoAu(new Date());
        this.getEntidad().setUsuarioingresoAu(this.getCurrentloggeduser());
    }

}
