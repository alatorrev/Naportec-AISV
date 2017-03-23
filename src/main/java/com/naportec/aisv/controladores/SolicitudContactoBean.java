package com.naportec.aisv.controladores;

import com.naportec.aisv.entidades.Solicitud;
import com.naportec.aisv.logica.ContactoFacade;
import com.naportec.aisv.logica.SolicitudFacade;
import com.naportec.utilidades.controladores.Mensaje;
import com.naportec.utilidades.controladores.UtilController;
import com.naportec.utilidades.enumeraciones.Estado;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;

/**
 * Clase controlador que maneja las acciones sobre una solicitud de contacto que
 * ya ha sido creada
 *
 * @author Fernando
 */
public class SolicitudContactoBean extends UtilController<Solicitud> implements Serializable {

    @EJB
    private SolicitudFacade logicaSolicitud;
    @EJB
    private ContactoFacade logicaContacto;
    private String motivo;

    public SolicitudContactoBean() {
        super(Solicitud.class);
    }

    @PostConstruct
    public void inicializar() {
        this.setEntidadLogica(logicaSolicitud);
        this.getListadoEntidad().filtroOrderBy("codigoSoli", "desc");
    }

    /**
     * Método para actualizar la lista de contactos de la solicitud para mostrar
     * en la vista
     */
    public void actualizar() {
        this.getEntidad().setContactoList(logicaContacto.listarContactos(this.getEntidad()));
    }

    /**
     * Método para realizar la aprobación de la solicitud de contacto
     */
    public void aprobarSolicitud() {
        String anterior = this.getEntidad().getEstado();
        try {
            if (this.getEntidad().getCodigoSoli() != null) {
                if (!this.getEntidad().getEstado().equals(Estado.Aprobado.name())) {
                    this.logicaSolicitud.setContexto(FacesContext.getCurrentInstance());
                    this.logicaSolicitud.aprobarSolicitud(this.getEntidad(), this.getCurrentloggeduser());
                    Mensaje.SUCESO_DIALOG("Solicitud", "Se ha aprobado Correctamente");
                } else {
                    Mensaje.ADVERTENCIA_DIALOG("Solicitud", "Operacion Imposible, la solicitud ya ha sido Aprobada");
                }
            } else {
                Mensaje.ADVERTENCIA_DIALOG("Solicitud", "Por favor Selecione un Registro");
            }
        } catch (Exception ex) {
            this.getEntidad().setEstado(anterior);
            Mensaje.ERROR_DIALOG("Solicitud", "Ha sucedido un error al tratar de Aprobar la solicitud" + ex.getMessage());
        }
    }

    /**
     * Método para realizar la desaprobación de la solicitud contacto
     */
    public void desaprobarSolicitud() {
        try {
            if (this.getEntidad().getCodigoSoli() != null) {
                if (this.getEntidad().getEstado().equals(Estado.Aprobado.name())) {
                    this.logicaSolicitud.setContexto(FacesContext.getCurrentInstance());
                    this.logicaSolicitud.desaprobarSolicitud(this.getEntidad(), this.getCurrentloggeduser(), motivo);

                    Mensaje.SUCESO_DIALOG("Solicitud", "Se ha desaprobado Correctamente");
                } else {
                    Mensaje.ADVERTENCIA_DIALOG("Solicitud", "Operacion Imposible, la solicitud no se puede Desaprobar");
                }
            } else {
                Mensaje.ADVERTENCIA_DIALOG("Solicitud", "Por favor Selecione un Registro");
            }
        } catch (Exception ex) {
            Mensaje.ERROR_DIALOG("Solicitud", "Ha sucedido un error al tratar de Desaprobar la solicitud: " + ex.getMessage() + ex.getCause() + ex.getStackTrace());
        }
        motivo = "";
    }

    /**
     * Método para anular una solicitud de contacto
     */
    public void anularSolicitud() {
        try {
            if (this.getEntidad().getCodigoSoli() != null) {
                if (!this.getEntidad().getEstado().equals(Estado.Aprobado.name()) && !this.getEntidad().getEstado().equals(Estado.Anulado.name())) {
                    this.logicaSolicitud.setContexto(FacesContext.getCurrentInstance());
                    this.logicaSolicitud.anularSolicitud(this.getEntidad(), this.getCurrentloggeduser(), motivo);

                    Mensaje.SUCESO_DIALOG("Solicitud", "Se ha Anulado Correctamente");
                } else {
                    Mensaje.ADVERTENCIA_DIALOG("Solicitud", "Operacion Imposible, la solicitud no se puede Anular");
                }
            } else {
                Mensaje.ADVERTENCIA_DIALOG("Solicitud", "Por favor Selecione un Registro");
            }
        } catch (MessagingException ex) {
            Mensaje.ERROR_DIALOG("Solicitud", "Ha sucedido un error al tratar de enviar el correo de Anulacion de la solicitud " + ex.getMessage());
        } catch (Exception ex) {
            Mensaje.ERROR_DIALOG("Solicitud", "Ha sucedido un error al tratar de Anular la solicitud " + ex.getMessage());
        }
        motivo = "";
    }

    /**
     * @return the motivo
     */
    public String getMotivo() {
        return motivo;
    }

    /**
     * @param motivo the motivo to set
     */
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

}
