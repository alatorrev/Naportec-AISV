package com.naportec.seguridad.controladores;

import com.naportec.seguridad.entidades.SGroup;
import com.naportec.seguridad.logica.SGroupFacade;
import com.naportec.utilidades.controladores.UtilController;
import com.naportec.utilidades.enumeraciones.Estado;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;

/**
 * Clase Controlador quenos permite manejar las acciones de la vista de Grupos
 * @author Fernando 
 */
public class GrupoBean extends UtilController<SGroup> {

     @EJB
    private SGroupFacade grupoLogica;

    public GrupoBean() {
        super(SGroup.class);
    }

    /**
     * Método para preparar para crear un nuevo grupo
     * @param evt 
     */
    @Override
    public void prepararCrear(ActionEvent evt) {
        super.prepararCrear(evt); //To change body of generated methods, choose Tools | Templates.
        this.getEntidad().setHoraingresoAu(new Date());
        this.getEntidad().setUsuarioingresoAu(this.getCurrentloggeduser());
    }

    /**
     * Método para preparar para editar un grupo ya creado
     * @param evt 
     */
    @Override
    public void prepararEditar(ActionEvent evt) {
        super.prepararEditar(evt); //To change body of generated methods, choose Tools | Templates.
        this.getEntidad().setHoramodificacionAu(new Date());
        this.getEntidad().setUsuariomodificacionAu(this.getCurrentloggeduser());
    }

    /**
     * Método para inicializar ellistado de lso grupos activos
     */
    @PostConstruct
    public void inicializar() {
        this.setEntidadLogica(grupoLogica);
        this.getListadoEntidad().filtroEqual("estado", Estado.Activo.name());
    }

}
