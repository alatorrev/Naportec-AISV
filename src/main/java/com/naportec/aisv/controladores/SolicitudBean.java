package com.naportec.aisv.controladores;

import com.naportec.aisv.entidades.Contacto;
import com.naportec.aisv.entidades.Solicitud;
import com.naportec.aisv.logica.ContactoFacade;
import com.naportec.aisv.logica.SolicitudFacade;
import com.naportec.seguridad.entidades.SUser;
import com.naportec.seguridad.logica.SUserFacade;
import com.naportec.utilidades.controladores.Mensaje;
import com.naportec.utilidades.enumeraciones.Estado;
import com.naportec.utilidades.validaciones.Validaciones;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Clase Controlador que nos permite Manejar las solcitudes de contacto para la
 * creación, agregación de contactos.
 *
 * @author Fernando
 */
public class SolicitudBean implements Serializable {

    private static final Logger LOG = Logger.getLogger(SolicitudBean.class.getName());
    @EJB
    private SolicitudFacade logicaSolicitud;
    @EJB
    private ContactoFacade logicaContacto;
    @EJB
    private SUserFacade logicaUsuario;

    private Contacto seleccionado;
    private Contacto contacto;
    private Solicitud entidad;
    private SUser usuario;
    private Solicitud solicitud;
    private List<Contacto> listaContactos;
    private String mensajeConfirmacionSolicitud;
    private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public SolicitudBean() {
    }

    /**
     * Método para inicializar los objetos necesarios
     */
    @PostConstruct
    public void inicializar() {
        this.getEntidad().setContactoList(new LinkedList<Contacto>());
        this.seleccionado = new Contacto();
        this.contacto = new Contacto();
    }

    /**
     * Método para obtener los datos de solicitud de contacto a partir del
     * nombre de usuario que esta logeado en el sistema actualmente.
     */
    public void iniciarPerfil() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = auth.getName();
        this.usuario = this.logicaUsuario.buscarUsuario(user);
        this.setEntidad(this.logicaUsuario.buscarSolicitud(usuario));
    }

    /**
     * Método para mostrar el mensaje de Confirmación cuando hemos ingresado
     * menos de 2 contactos en la solicitud
     *
     * @return
     */
    public String getMensajeConfirmacionSolicitud() {
        mensajeConfirmacionSolicitud = "";
        if (this.entidad != null) {
            if (this.entidad.getContactoList() != null) {
                if (this.entidad.getContactoList().size() < 2) {
                    mensajeConfirmacionSolicitud = "Se sugiere que por lo menos se agreguen 2 Contactos";
                } else {
                    mensajeConfirmacionSolicitud = "";
                }
            }
        }
        return mensajeConfirmacionSolicitud;
    }

    /**
     * Método para guardar los datos de la empresa de nuestro perfil.
     *
     * @param evt
     */
    public void guardarPerfil(ActionEvent evt) {
        try {
            this.logicaSolicitud.guardar(solicitud);
            Mensaje.SUCESO_DIALOG("Aisv - Empresa", "Se ha guardado Correctamente");
        } catch (Exception ex) {
            Mensaje.ERROR_DIALOG("Aisv - Empresa", "Error al intentar realizar Cambios");
        }
    }

    /**
     * Método para Guardar los datos de usuario
     *
     * @param evt
     * @throws Exception
     */
    public void guardarUsuario(ActionEvent evt) throws Exception {
        this.logicaUsuario.guardar(usuario);
    }

    /**
     * Método para guardar una nueva solicitud de contacto.
     *
     * @param evt
     */
    public void guardarEntidad(ActionEvent evt) {
        try {

            int cont = 0;
            if (this.getEntidad().getContactoList() != null) {
                cont = this.getEntidad().getContactoList().size();
            }
            if (cont > 0) {
                if (!this.logicaSolicitud.existeSolicitud(entidad)) {
                    if (!this.logicaSolicitud.existeNick(entidad)) {
                        StringTokenizer token = new StringTokenizer(entidad.getCorreoSoli(), ";");
                        boolean validaCorreo = true;
                        while (token.hasMoreTokens()) {
                            String correo = token.nextToken().trim();
                            if (!correo.matches(PATTERN_EMAIL)) {
                                validaCorreo = false;
                                break;
                            }
                        }
                        if (validaCorreo) {
                            boolean validaCodigosNaviera = true;
                            if (entidad.getTipoContactoSoli().equals("NA")) {
                                if (entidad.getOceSoli() == null || entidad.getDescripcionSoli() == null) {
                                    validaCodigosNaviera = false;
                                } else if (entidad.getOceSoli().trim().length() <= 0 || entidad.getDescripcionSoli().trim().length() <= 0) {
                                    validaCodigosNaviera = false;
                                }
                            }
                            switch (Validaciones.validarIdentificacion(entidad.getTipoIdentificacionSoli(), entidad.getIdentificacionSoli())) {
                                case Validaciones.IDENTIFICACION_OK:
                                    if (validaCodigosNaviera == true) {
                                        if (entidad.getTipoContactoSoli().equals("NA") || entidad.getTipoContactoSoli().equals("AA")) {
                                            entidad.setEstado(Estado.Pendiente.name());
                                        } else {
                                            entidad.setEstado(Estado.PenAuto.name());
                                        }
                                        entidad.setIpCreacionSoli(((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr());
                                        entidad.setFechaCreacionSoli(new Date());
                                        this.logicaSolicitud.setContexto(FacesContext.getCurrentInstance());
                                        this.logicaSolicitud.guardar(entidad);
                                        entidad.setContactoList(new LinkedList<Contacto>());
                                        Mensaje.SUCESO_DIALOG("Envio de Solicitud", "Se envio la Solicitud Sr(a) " + entidad.getNombreSoli() + "<br/>Por favor esperar correo de confirmación de la solicitud");
                                        entidad = new Solicitud();
                                    } else {
                                        Mensaje.ERROR_DIALOG("Envio de Solicitud", "Los Códigos OCE y CAE son obligatorios");
                                    }
                                    break;
                                case Validaciones.ERROR_CEDULA:
                                    Mensaje.ERROR_DIALOG("Envio de Solicitud", "La cédula ingresada es incorrecta");
                                    break;
                                case Validaciones.ERROR_RUC:
                                    Mensaje.ERROR_DIALOG("Envio de Solicitud", "El RUC ingresado es incorrecto");
                                    break;
                                case Validaciones.ERROR_NO_DEFINIDO:
                                    Mensaje.ERROR_DIALOG("Envio de Solicitud", "Sun informacion es erronea por favor verifique");
                                    break;
                            }
                        } else {
                            Mensaje.ADVERTENCIA_DIALOG("Envio de Solicitud", "Por favor revise los Correos ya que no estÃ¡n correctos.");
                        }
                    } else {
                        Mensaje.ADVERTENCIA_DIALOG("Envio de Solicitud", "Nombre de Usuario NO DISPONIBLE ");
                    }
                } else {
                    Mensaje.ADVERTENCIA_DIALOG("Envio de Solicitud", "Usted ya ha registrado una Solicitud.");
                }
            } else {
                Mensaje.ADVERTENCIA_DIALOG("Envio de Solicitud", "Por favor agregue contactos a su Solicitud.");
            }
        } catch (Exception ex) {
            Mensaje.ERROR_DIALOG("Envio de Solicitud", "Ha sucedido un error!!" + ex.getMessage());
        }
    }

    /**
     * Método para agregar un contacto en la vista de creación de una nueva
     * solicitud de contacto.
     */
    public void agregarContacto() {
        if (this.getEntidad().getContactoList() == null) {
            this.getEntidad().setContactoList(new LinkedList<Contacto>());
        }
        if (contacto == null) {
            contacto = new Contacto();
        }
        if (contacto.getTelefonoCont().length() != 0 && contacto.getNombreCont().length() != 0) {
            if (contacto.getEmailCont().matches(PATTERN_EMAIL)) {
                if (this.getEntidad().getContactoList().size() < 3) {
                    contacto.setCodigoSoli(this.getEntidad());
                    contacto.setEstadoCont(Estado.Activo.name());
                    this.getEntidad().getContactoList().add(contacto);
                    contacto = new Contacto();
                }
            } else {
                Mensaje.ADVERTENCIA_DIALOG("Agregar Contactos", "Por favor revise su Email ya que no es correcto.");
            }
        } else {
            Mensaje.ADVERTENCIA_DIALOG("Solicitud de Contacto", "Por favor ingrese todos los datos obligatorios");
        }
    }

    /**
     * Método para quitar un contacto en la vista de creación de una nueva
     * solicitud de contacto
     */
    public void quitarContacto() {
        if (seleccionado != null) {
            this.getEntidad().getContactoList().remove(seleccionado);
        }
    }

    /**
     * Método para agregar un contacto en la vista de Contactos cuando el
     * usuario ya esta logeado puede agregar mas contactos al perfil.
     */
    public void agregarContactoPerfil() {
        setEntidad(this.logicaUsuario.buscarSolicitud(usuario));
        if (this.listaContactos != null) {
            if (this.listaContactos.size() < 3) {
                if (contacto == null) {
                    contacto = new Contacto();
                }
                if (contacto.getTelefonoCont().length() != 0 && contacto.getNombreCont().length() != 0) {
                    if (contacto.getEmailCont().matches(PATTERN_EMAIL)) {
                        contacto.setCodigoSoli(this.getEntidad());
                        contacto.setEstadoCont(Estado.Activo.name());
                        this.listaContactos.add(contacto);
                        if (!this.logicaUsuario.agregarUsuarioMisContacto(entidad, contacto, 0, this.usuario.getUsrLoginname())) {
                            new FacesException("Ha sucedido un Error!! No se ha podido Guardar el Contacto");
                        }
                        contacto = new Contacto();
                    } else {
                        Mensaje.ADVERTENCIA_DIALOG("Agregar Contactos", "Por favor revise su Email ya que no es correcto.");
                    }
                } else {
                    Mensaje.ADVERTENCIA_DIALOG("Solicitud de Contacto", "Por favor ingrese todos los datos obligatorios");
                }
            } else {
                Mensaje.ADVERTENCIA_DIALOG("Solicitud de Contacto", "No se pueden ingresar mas de 3 contactos.");
            }
        }
    }

    /**
     * Método para quitar contactos en la Vista Contactos
     */
    public void quitarContactoPerfil() {
        if (seleccionado != null) {
            try {
                boolean si = this.logicaUsuario.inhabilitarUsuario(seleccionado, entidad, usuario);
                if (si) {
                    this.listaContactos.remove(seleccionado);
                } else {
                    Mensaje.ADVERTENCIA_DIALOG("Quitar Contacto", "No se puede eliminar su propio Usuario!");
                }
            } catch (Exception ex) {
                Mensaje.ERROR_DIALOG("Quitar Contacto", "Ha sucedido el siguiente Error: " + ex.getMessage());
                Logger.getLogger(SolicitudBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Método para salir en la vista de creacion de nueva solicitud de contacto
     * para lo cual inicializa los objetos usados paraque queden en blanco
     *
     * @param evt
     */
    public void salirSolicitud(ActionEvent evt) {
        this.entidad = new Solicitud();
        this.entidad.setContactoList(new LinkedList<Contacto>());
    }

    /**
     * @return the seleccionado
     */
    public Contacto getSeleccionado() {
        if (seleccionado == null) {
            seleccionado = new Contacto();
        }
        return seleccionado;
    }

    /**
     * @param seleccionado the seleccionado to set
     */
    public void setSeleccionado(Contacto seleccionado) {
        this.seleccionado = seleccionado;
    }

    /**
     * @return the contacto
     */
    public Contacto getContacto() {
        if (contacto == null) {
            contacto = new Contacto();
        }
        return contacto;
    }

    /**
     * @param contacto the contacto to set
     */
    public void setContacto(Contacto contacto) {
        this.contacto = contacto;
    }

    /**
     * @return the entidad
     */
    public Solicitud getEntidad() {
        if (entidad == null) {
            entidad = new Solicitud();
        }
        return entidad;
    }

    /**
     * @param entidad the entidad to set
     */
    public void setEntidad(Solicitud entidad) {
        this.entidad = entidad;
    }

    /**
     * @return the usuario
     */
    public SUser getUsuario() {
        if (usuario == null) {
            usuario = new SUser();
        }
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(SUser usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the solicitud
     */
    public Solicitud getSolicitud() {
        if (solicitud == null) {
            solicitud = new Solicitud();
        }
        return solicitud;
    }

    /**
     * @param solicitud the solicitud to set
     */
    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    /**
     * @return the listaContactos
     */
    public List<Contacto> getListaContactos() {
        if (this.getEntidad() != null) {
            this.listaContactos = logicaContacto.listarContactos(entidad);
        }
        return listaContactos;
    }

    /**
     * @param listaContactos the listaContactos to set
     */
    public void setListaContactos(List<Contacto> listaContactos) {
        this.listaContactos = listaContactos;
    }
}
