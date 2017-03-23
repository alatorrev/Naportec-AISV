/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.aisv.controladores;

import com.naportec.aisv.entidades.NavieraConsol;
import com.naportec.aisv.logica.NavieraConsolFacade;
import com.naportec.seguridad.entidades.SUser;
import com.naportec.seguridad.logica.SUserFacade;
import com.naportec.utilidades.controladores.Mensaje;
import com.naportec.utilidades.controladores.UtilController;
import com.naportec.utilidades.enumeraciones.Estado;
import com.naportec.utilidades.otros.CadenaUtil;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author MZambrano1
 */
public class NavieraConsolBean extends UtilController<NavieraConsol> {

    @EJB
    private NavieraConsolFacade navconsolFacade;
    @EJB
    private SUserFacade usuarioFacade;

    private SUser usuario;

    public NavieraConsolBean() {
        super(NavieraConsol.class);
    }

    @PostConstruct
    public void inicializar() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        usuario = usuarioFacade.buscarUsuario(auth.getName());
        this.setEntidadLogica(navconsolFacade);
        if (usuario != null) {
            this.getListadoEntidad().filtroEqual("codigoNavi", usuario.getCodigoNavi());
            this.getListadoEntidad().filtroEqual("codigoCons.estado", Estado.Activo.name());
        } else {
            this.getListadoEntidad().filtroEqual("codigoCons.estado", Estado.Suspendido.name());
        }
    }

    @Override
    public void guardarEntidad(ActionEvent evt) {
        if (usuario != null) {
            if (CadenaUtil.NullOrEmpty(this.getEntidad().getCodigoCons().getNombreCons()) || CadenaUtil.NullOrEmpty(this.getEntidad().getCodigoCons().getRucCons())) {
                Mensaje.ERROR("El nombre y ruc de la consolidadora son necesarios");
            } else {
                NavieraConsol nuevo = navconsolFacade.existeConsolidadora(usuario.getCodigoNavi(), this.getEntidad().getCodigoCons().getRucCons());
                if (nuevo == null) {
                    this.getEntidad().setCodigoNavi(usuario.getCodigoNavi());
                    this.getEntidad().getCodigoCons().setEstado(Estado.Activo.name());
                    super.guardarEntidad(evt); //To change body of generated methods, choose Tools | Templates.
                } else {
                    if(nuevo.getCodigoCons().getEstado().equals(Estado.Activo.name())){
                        Mensaje.ERROR("No se ha podido guardar la Consolidadora: Ya esta creada");
                    }else{
                        nuevo.getCodigoCons().setEstado(Estado.Activo.name());
                        nuevo.getCodigoCons().setNombreCons(this.getEntidad().getCodigoCons().getNombreCons());
                        this.navconsolFacade.modificar(nuevo);
                        Mensaje.SUCESO("Se ha activado la Consolidadora: "+nuevo.getCodigoCons().getNombreCons());
                    }
                }
            }
        } else {
            Mensaje.ERROR("No se ha podido guardar la Consolidadora: USuario no Naviera");
        }
    }

    @Override
    public void eliminarEntidad(ActionEvent evt) {
        try {
            this.getEntidad().getCodigoCons().setEstado(Estado.Inactivo.name());
            this.navconsolFacade.modificar(this.getEntidad());
            Mensaje.SUCESO("Se ha anulado la Consolidadora: "+this.getEntidad().getCodigoCons().getNombreCons());
        } catch (Exception ex) {
            Mensaje.ERROR("No se ha podido anular la consolidadora: " + ex.getMessage());
        }
    }

}
