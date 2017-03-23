package com.naportec.seguridad.logica;

import com.naportec.aisv.entidades.Contacto;
import com.naportec.aisv.entidades.Naviera;
import com.naportec.aisv.entidades.Solicitud;
import com.naportec.aisv.logica.ContactoFacade;
import com.naportec.seguridad.entidades.SUser;
import com.naportec.seguridad.entidades.SUserrole;
import com.naportec.seguridad.entidades.SUserrolePK;
import com.naportec.utilidades.enumeraciones.Estado;
import com.naportec.utilidades.logica.AbstractFacade;
import com.naportec.utilidades.otros.Aes;
import com.naportec.utilidades.otros.Email;
import com.naportec.utilidades.otros.GeneradorClaves;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class SUserFacade extends AbstractFacade<SUser>{

    @EJB
    private ContactoFacade contactoFacade;

    /**
     * Inicialización de nuestro Recurso de Datos
     */
    @PersistenceContext(unitName = "com.naportec_Aisv")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SUserFacade() {
        super(SUser.class);
    }

    public SUser buscarUsuario(String nombre) {
        Query q = getEntityManager().createNamedQuery("SUser.findByUsrLoginname");
        q.setParameter("usrLoginname", nombre);
        if (q.getResultList().size() > 0) {
            return (SUser) q.getResultList().get(0);
        } else {
            return null;
        }
    }

    public boolean inhabilitarUsuario(Contacto x, Solicitud s, SUser usr) throws Exception {
        try {
            Query q = getEntityManager().createNamedQuery("SUser.buscarContactoUser");
            q.setParameter("nombre", x.getNombreCont());
            q.setParameter("soli", s);
            SUser usuario = null;
            if (q.getResultList().size() > 0) {
                usuario = (SUser) q.getResultList().get(0);
            } else {
                usuario = null;
                return false;
            }
            if (usr.getUsrId() != usuario.getUsrId()) {
                usuario.setEstado(Estado.Inactivo.name());
                usuario.setUsrEnabled(false);
                this.modificar(usuario);
                x.setEstadoCont(Estado.Inactivo.name());
                contactoFacade.modificar(x);
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            throw new Exception(ex);
        }
    }

    public boolean agregarUsuarioMisContacto(Solicitud s, Contacto x, int numero, String usuario) {
        try {
            String nombreLogin = s.getUsuarioSoli() + this.nextListValue(s, buscarSolicitudLoginName(s));
            SUser user = new SUser();
            user.setEstado(Estado.Activo.name());
            user.setUsrEnabled(true);
            user.setUsrLoginname(nombreLogin);
            user.setUsrFirstname(x.getNombreCont());
            user.setUsrToken("");
            user.setUsrPassword(GeneradorClaves.getPassword(GeneradorClaves.MAYUSCULAS, 2) + GeneradorClaves.getPassword(GeneradorClaves.MINUSCULAS, 2) + GeneradorClaves.getPassword(GeneradorClaves.NUMEROS, 2));
            user.setUsrPassword(Aes.encryptData(user.getUsrPassword()));
            user.setUsrEmail(x.getEmailCont());
            user.setVersion(0);
            user.setHoraingresoAu(new Date());
            user.setUsuarioingresoAu(usuario);
            user.setUsrTipoUsuario("E");
            user.setUsrTelefono(x.getTelefonoCont());
            user.setUsrCelular(x.getCelularCont());
            user.setCodigoSoli(s);
            if (s.getTipoContactoNaviera() == true) {
                if (s.getsUserList().size() > 0) {
                    user.setCodigoNavi(s.getsUserList().get(0).getCodigoNavi());
                }
            }
            this.getEntityManager().persist(user);
            user = this.buscarUsuario(nombreLogin);
            Email email = new Email();
            email.send(user.getUsrEmail(), "APROBACIÓN DE SOLICITUD DE [" + user.getCodigoSoli().getNombreSoli() + "] - [" + user.getUsrFirstname() + "]",
                    "<h3>Estimado(a) sus credenciales de acceso son:</h3>"
                    + "<br/>"
                    + "<p>USUARIO   : " + user.getUsrLoginname() + "</p>"
                    + "<br/>"
                    + "<p>CONTRASEÑA: " + Aes.decryptData(user.getUsrPassword()) + "</p>"
                    + "<p>Favor no responda a este e-mail, el mismo fue Generado por un Sistema Automático</p>"
                    + "<br/><br/>"
                    + "<h4>Saludos cordiales</h4><br/><h4>NAPORTEC S.A.</h4>");
            SUserrole entidad = new SUserrole();
            entidad.setVersion(0);
            entidad.setHoraingresoAu(new Date());
            entidad.setUsuarioingresoAu(usuario);
            if (s.getTipoContactoSoli().equals("NA")) {
                SUserrolePK pk = new SUserrolePK(user.getUsrId(), 3);
                entidad.setSUserrolePK(pk);
            }
            if (s.getTipoContactoSoli().equals("IE") || s.getTipoContactoSoli().equals("AA")) {
                SUserrolePK pk = new SUserrolePK(user.getUsrId(), 2);
                entidad.setSUserrolePK(pk);
            }
            this.getEntityManager().persist(entidad);
            this.getEntityManager().persist(x);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(SUserFacade.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean agregarUsuarioContacto(Solicitud s, Contacto x, int numero, String usuario) {
        try {
            Naviera nav = new Naviera();
            if (s.getTipoContactoNaviera() == true) {
                nav.setCodigoCaeNavi(s.getDescripcionSoli());
                nav.setCodigoOceNavi(s.getOceSoli());
                nav.setNombreNavi(s.getNombreSoli());
                nav.setEstado(Estado.Activo.name());
                nav.setCorreoNotifNavi(s.getCorreoSoli());
                this.getEntityManager().persist(nav);
            }
            String nombreLogin = s.getUsuarioSoli() + this.nextListValue(s, buscarSolicitudLoginName(s));//s.getUsuarioSoli() + (s.getContactoList().size() + 2);
            SUser user = new SUser();
            user.setEstado(Estado.Activo.name());
            user.setUsrEnabled(true);
            user.setUsrLoginname(nombreLogin);
            user.setUsrFirstname(x.getNombreCont());
            user.setUsrToken("");
            user.setUsrPassword(GeneradorClaves.getPassword(GeneradorClaves.MAYUSCULAS, 2) + GeneradorClaves.getPassword(GeneradorClaves.MINUSCULAS, 2) + GeneradorClaves.getPassword(GeneradorClaves.NUMEROS, 2));
            user.setUsrPassword(Aes.encryptData(user.getUsrPassword()));
            user.setUsrEmail(x.getEmailCont());
            user.setVersion(0);
            user.setHoraingresoAu(new Date());
            user.setUsuarioingresoAu(usuario);
            user.setUsrTipoUsuario("E");
            user.setUsrTelefono(x.getTelefonoCont());
            user.setUsrCelular(x.getCelularCont());
            user.setCodigoSoli(s);
            if (s.getTipoContactoNaviera() == true) {
                user.setCodigoNavi(nav);
            }
            this.getEntityManager().persist(user);
            user = this.buscarUsuario(nombreLogin);
            Email email = new Email();
            email.send(user.getUsrEmail(), "APROBACIÓN DE SOLICITUD DE [" + user.getCodigoSoli().getNombreSoli() + "] - [" + user.getUsrFirstname() + "]",
                    "<h3>Estimado(a) sus credenciales de acceso son:</h3>"
                    + "<br/>"
                    + "<p>USUARIO   : " + user.getUsrLoginname() + "</p>"
                    + "<br/>"
                    + "<p>CONTRASEÑA: " + Aes.decryptData(user.getUsrPassword()) + "</p>"
                    + "<p>Favor no responda a este e-mail, el mismo fue Generado por un Sistema Automático</p>"
                    + "<br/><br/>"
                    + "<h4>Saludos cordiales</h4><br/><h4>NAPORTEC S.A.</h4>");
            SUserrole entidad = new SUserrole();
            entidad.setVersion(0);
            entidad.setHoraingresoAu(new Date());
            entidad.setUsuarioingresoAu(usuario);
            if (s.getTipoContactoSoli().equals("NA")) {
                SUserrolePK pk = new SUserrolePK(user.getUsrId(), 3);
                entidad.setSUserrolePK(pk);
            }
            if (s.getTipoContactoSoli().equals("IE") || s.getTipoContactoSoli().equals("AA")) {
                SUserrolePK pk = new SUserrolePK(user.getUsrId(), 2);
                entidad.setSUserrolePK(pk);
            }
            this.getEntityManager().persist(entidad);
            this.getEntityManager().persist(x);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(SUserFacade.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public Solicitud buscarSolicitud(SUser usuario) {
        Query q = getEntityManager().createNamedQuery("SUser.findSolicitud");
        q.setParameter("usuario", usuario.getUsrId());
        return (Solicitud) q.getSingleResult();
    }

    public String buscarSolicitudLoginName(Solicitud s) {
        Query q = getEntityManager().createNamedQuery("Solicitud.findByCodigoSoli");
        q.setParameter("codigoSoli", s.getCodigoSoli());
        Solicitud result = (Solicitud) q.getSingleResult();
        return result.getUsuarioSoli();
    }

    public int nextListValue(Solicitud s, String usr_loginname) {
        int max = 0;
        if (!s.getContactoList().isEmpty()) {
            for (SUser u : s.getsUserList()) {
                int valor = Integer.parseInt(u.getUsrLoginname().replace(usr_loginname, " ").trim());
                if (valor > max) {
                    max = valor;
                }
            }
        }else{
            max=0;
        }
        return max + 1;
    }

    public boolean login(String usuario, String clave) {
        Query q = getEntityManager().createNamedQuery("SUser.findLogin");
        q.setParameter("login", usuario);
        q.setParameter("pass", clave);
        if (q.getResultList().size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
