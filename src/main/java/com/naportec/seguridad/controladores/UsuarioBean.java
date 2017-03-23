package com.naportec.seguridad.controladores;

import com.naportec.seguridad.entidades.SRole;
import com.naportec.seguridad.entidades.SUser;
import com.naportec.seguridad.logica.SParameterFacade;
import com.naportec.seguridad.logica.SUserFacade;
import com.naportec.seguridad.logica.SUserroleFacade;
import com.naportec.utilidades.controladores.Mensaje;
import com.naportec.utilidades.controladores.UtilController;
import com.naportec.utilidades.enumeraciones.Estado;
import com.naportec.utilidades.otros.Aes;
import com.naportec.utilidades.otros.Email;
import com.naportec.utilidades.otros.GeneradorClaves;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;
import javax.mail.MessagingException;

import org.primefaces.event.SelectEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
/**
 * Clase controlador que permite manejar las acciones de la vista de usuarios,
 * recuperacion de clave
 * @author Fernando
 */
public class UsuarioBean extends UtilController<SUser> {

    /**
     * Nueva Password al realizar cambiod e clave
     */
    private String nuevaClave;
    private String confirmarClave;
    private String claveActual;
    private String nombreUsuario;
    /**
     * Dias de caducidad de las claves
     */
    private long diasCaducidadClave = 0;

    private List<SRole> rolesUsuario;
    @EJB
    private SUserFacade usuarioLogica;
    @EJB
    private SUserroleFacade userRolLogica;
    @EJB
    private SParameterFacade parametroLogica;

    public UsuarioBean() {
        super(SUser.class);
    }

    /**
     * Método para recuperar la password de un usuario unicamente con su nombre de usuario
     * luego le llegará una notificiación por correo electrónico.
     */
    public void recuperarPassword() {
        SUser u = this.usuarioLogica.buscarUsuario(nombreUsuario);
        if (u != null) {
            if (!u.getUsrTipoUsuario().equals("U")) {
                try {
                    Email email = new Email();
                    email.send(u.getUsrEmail(), "RECUPERACION DE CONTRASEA [" + u.getCodigoSoli().getNombreSoli() + "] - [" + u.getUsrFirstname() + "]",
                            "<h3>Estimado(a) sus credenciales de acceso son:</h3>"
                            + "<br/>"
                            + "<p>USUARIO   : " + u.getUsrLoginname() + "</p>"
                            + "<br/>"
                            + "<p>CONTRASEÑA: " + Aes.decryptData(u.getUsrPassword()) + "</p>"
                            + "<p>Favor no responda a este e-mail, el mismo fue Generado por un Sistema Automático</p>"
                            + "<br/><br/>"
                            + "<h4>Saludos cordiales</h4><br/><h4>NAPORTEC S.A.</h4>");
                    Mensaje.SUCESO_DIALOG("Recuperación de Contraseña", "Su contraseña ha sido enviada a su correo, por favor verifique");
                } catch (MessagingException ex) {
                    Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
                    Mensaje.ERROR_DIALOG("Recuperación de Contraseña", "No se ha podido enviar su contraseña por favor intente nuevamente.");
                } catch (Exception ex) {
                    Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Mensaje.ADVERTENCIA_DIALOG("Recuperación de Contraseña", "Esta opción no es válida para usted.<br/>Por favor Contactese con el Administrador de Seguridad");
            }
        } else {
            Mensaje.ADVERTENCIA_DIALOG("Recuperación de Contraseña", "Su Usuario no existe o ha sido Eliminado");
        }
    }

    /**
     * Método para guardar un nuevo usuario
     * @param evt 
     */
    @Override
    public void guardarEntidad(ActionEvent evt) {
        try {
            if (this.getEntidad().getUsrTipoUsuario().equals("E")) {
                String pass = GeneradorClaves.getPassword(GeneradorClaves.MAYUSCULAS, 2) + GeneradorClaves.getPassword(GeneradorClaves.MINUSCULAS, 2) + GeneradorClaves.getPassword(GeneradorClaves.NUMEROS, 2);
                this.getEntidad().setUsrPassword(Aes.encryptData(pass));
                Email email = new Email();
                email.send(this.getEntidad().getUsrEmail(), "NUEVO USUARIO DEL SISTEMA [" + this.getEntidad().getUsrFirstname() + "] - [" + this.getEntidad().getUsrLastname() + "]",
                        "<h3>Estimado(a) sus credenciales de acceso son:</h3>"
                        + "<br/>"
                        + "<p>USUARIO   : " + this.getEntidad().getUsrLoginname() + "</p>"
                        + "<br/>"
                        + "<p>CONTRASEÑA: " + pass + "</p>"
                        + "<p>Favor no responda a este e-mail, el mismo fue Generado por un Sistema Automático</p>"
                        + "<br/><br/>"
                        + "<h4>Saludos cordiales</h4><br/><h4>NAPORTEC S.A.</h4>");
            }
            super.guardarEntidad(evt); //To change body of generated methods, choose Tools | Templates.
        } catch (Exception ex) {
            Mensaje.SUCESO("No se ha podido agregar el Usuario: " + ex.getMessage());
        }
    }

    @PostConstruct
    public void inicializar() {
        this.setEntidadLogica(usuarioLogica);
        this.getListadoEntidad().filtroEqual("estado", Estado.Activo.name());
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        String nombre = auth.getName();
        this.setEntidad(usuarioLogica.buscarUsuario(nombre));
    }

    /**
     * Método para guardar un cambio de clave del usuario
     */
    public void guardarCambioClave() {
        try {
            this.getEntidad().setUsuariomodificacionAu(this.getCurrentloggeduser());
            this.getEntidad().setFechamodificacionclaveAu(new Date());
            if (this.claveActual.length() != 0 && this.nuevaClave.length() != 0) {
                if (this.usuarioLogica.login(this.getEntidad().getUsrLoginname(), Aes.encryptData(claveActual))) {
                    if (this.nuevaClave.equals(this.confirmarClave)) {
                        this.getEntidad().setUsrPassword(Aes.encryptData(nuevaClave));
                        usuarioLogica.modificar(this.getEntidad());
                        Email email = new Email();
                        email.send(this.getEntidad().getUsrEmail(), "MODIFICACIÓN DE DATOS [" + this.getEntidad().getCodigoSoli().getNombreSoli() + "] - [" + this.getEntidad().getUsrFirstname() + "]",
                                "<h3>Estimado(a) sus credenciales de acceso son:</h3>"
                                + "<br/>"
                                + "<p>USUARIO   : " + this.getEntidad().getUsrLoginname() + "</p>"
                                + "<br/>"
                                + "<p>CONTRASEÑA: " + this.nuevaClave + "</p>"
                                + "<p>Favor no responda a este e-mail, el mismo fue Generado por un Sistema Automático</p>"
                                + "<br/><br/>"
                                + "<h4>Saludos cordiales</h4><br/><h4>NAPORTEC S.A.</h4>");
                        Mensaje.SUCESO_DIALOG("AISV - Modificación de Datos", "Se ha modificado sus datos");
                    } else {
                        Mensaje.ADVERTENCIA_DIALOG("AISV - Modificación de Datos", "Revise su nueva contraseña no coincide con la confirmación");
                    }
                } else {
                    Mensaje.ERROR_DIALOG("AISV - Modificación de Datos", "Su contraseña actual no es Correcta");
                }
            } else {
                usuarioLogica.modificar(this.getEntidad());
                Mensaje.SUCESO_DIALOG("AISV - Modificación de Datos", "Se han modificado sus datos");
            }
            claveActual = "";
            nuevaClave = "";
            confirmarClave = "";
        } catch (Exception ex) {
            Mensaje.ERROR_DIALOG("AISV - Modificación de Datos", "Ha Sucedido un ERROR");
        }
    }

    /**
     * Método para modificr datos de usuario de la empresa a la que pertenece el usuario
     */
    public void modificarEmpresa() {
        try {
            this.usuarioLogica.modificar(this.getEntidad());
            Mensaje.SUCESO_DIALOG("AISV - Empresa", "Se ha modificado la información correctamente");
        } catch (Exception ex) {
            Mensaje.ERROR_DIALOG("AISV - Empresa", "Ha sucedido un error al tratar de hacer cambios");
        }
    }

    @Override
    public void seleccionarEditar(SelectEvent evt) {
        super.seleccionarEditar(evt);
        this.rolesUsuario = this.userRolLogica.listaRolUsuario(this.entidad);
    }

    /**
     * Método para preparar para crear un nuevo usuario
     * @param evt 
     */
    @Override
    public void prepararCrear(ActionEvent evt) {
        super.prepararCrear(evt);
        rolesUsuario = new LinkedList<SRole>();
        this.getEntidad().setHoraingresoAu(new Date());
        this.getEntidad().setUsuarioingresoAu(this.getCurrentloggeduser());
        this.getEntidad().setUsrTipoUsuario("U");
    }

    /**
     * Método para preparar para editar un usuario existente
     * @param evt 
     */
    @Override
    public void prepararEditar(ActionEvent evt) {
        super.prepararEditar(evt); // To change body of generated methods,						// choose Tools | Templates.
        this.getEntidad().setUsuariomodificacionAu(this.getCurrentloggeduser());
        this.getEntidad().setFechamodificacionclaveAu(new Date());
    }

    /**
     * Método para validar si las contraseñas son correctas
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException 
     */
    public void validatePassword(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        try {
            String mensaje = parametroLogica.validacionPassword(value.toString());
            if (mensaje.length() != 0) {
                throw new ValidatorException(new FacesMessage(mensaje));
            }
        } catch (Exception ex) {
            throw new ValidatorException(new FacesMessage("No hay conexion"));
        }
    }

    /**
     * @return the rolesUsuario
     */
    public List<SRole> getRolesUsuario() {
        if (rolesUsuario == null) {
            rolesUsuario = new LinkedList<SRole>();
        }
        return rolesUsuario;
    }

    /**
     * @return the diasCaducidadClave
     */
    public long getDiasCaducidadClave() {
        return diasCaducidadClave;
    }

    /**
     * @param diasCaducidadClave the diasCaducidadClave to set
     */
    public void setDiasCaducidadClave(long diasCaducidadClave) {
        this.diasCaducidadClave = diasCaducidadClave;
    }

    /**
     * @return the nuevaClave
     */
    public String getNuevaClave() {
        return nuevaClave;
    }

    /**
     * @param nuevaClave the nuevaClave to set
     */
    public void setNuevaClave(String nuevaClave) {
        this.nuevaClave = nuevaClave;
    }

    /**
     * @return the confirmarClave
     */
    public String getConfirmarClave() {
        return confirmarClave;
    }

    /**
     * @param confirmarClave the confirmarClave to set
     */
    public void setConfirmarClave(String confirmarClave) {
        this.confirmarClave = confirmarClave;
    }

    /**
     * @return the claveActual
     */
    public String getClaveActual() {
        return claveActual;
    }

    /**
     * @param claveActual the claveActual to set
     */
    public void setClaveActual(String claveActual) {
        this.claveActual = claveActual;
    }

    /**
     * @return the nombreUsuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * @param nombreUsuario the nombreUsuario to set
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
