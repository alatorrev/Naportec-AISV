/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.seguridad.entidades;

import com.naportec.aisv.entidades.Naviera;
import com.naportec.aisv.entidades.Solicitud;
import com.naportec.aisv.entidades.Transaccion;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Nestor
 */
@Entity
@Table(name = "s_user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SUser.findAll", query = "SELECT s FROM SUser s"),
    @NamedQuery(name = "SUser.findLogin", query = "SELECT s FROM SUser s WHERE s.usrLoginname=:login AND s.usrPassword=:pass"),
    @NamedQuery(name = "SUser.findSolicitud", query = "SELECT s.codigoSoli FROM SUser s where s.usrId=:usuario"),
    @NamedQuery(name = "SUser.buscarContactoUser", query = "SELECT s FROM SUser s WHERE s.codigoSoli=:soli AND s.usrFirstname=:nombre"),
    @NamedQuery(name = "SUser.findByUsrId", query = "SELECT s FROM SUser s WHERE s.usrId = :usrId"),
    @NamedQuery(name = "SUser.findByUsrLoginname", query = "SELECT s FROM SUser s WHERE s.usrLoginname = :usrLoginname"),
    @NamedQuery(name = "SUser.findByUsrPassword", query = "SELECT s FROM SUser s WHERE s.usrPassword = :usrPassword"),
    @NamedQuery(name = "SUser.findByUsrLastname", query = "SELECT s FROM SUser s WHERE s.usrLastname = :usrLastname"),
    @NamedQuery(name = "SUser.findByUsrFirstname", query = "SELECT s FROM SUser s WHERE s.usrFirstname = :usrFirstname"),
    @NamedQuery(name = "SUser.findByUsrEmail", query = "SELECT s FROM SUser s WHERE s.usrEmail = :usrEmail"),
    @NamedQuery(name = "SUser.findByUsrLocale", query = "SELECT s FROM SUser s WHERE s.usrLocale = :usrLocale"),
    @NamedQuery(name = "SUser.findByUsrEnabled", query = "SELECT s FROM SUser s WHERE s.usrEnabled = :usrEnabled"),
    @NamedQuery(name = "SUser.findByUsraccountNonExpired", query = "SELECT s FROM SUser s WHERE s.usraccountNonExpired = :usraccountNonExpired"),
    @NamedQuery(name = "SUser.findByUsrcredentialsNonExpired", query = "SELECT s FROM SUser s WHERE s.usrcredentialsNonExpired = :usrcredentialsNonExpired"),
    @NamedQuery(name = "SUser.findByUsraccountNonLocked", query = "SELECT s FROM SUser s WHERE s.usraccountNonLocked = :usraccountNonLocked"),
    @NamedQuery(name = "SUser.findByUsrToken", query = "SELECT s FROM SUser s WHERE s.usrToken = :usrToken"),
    @NamedQuery(name = "SUser.findByVersion", query = "SELECT s FROM SUser s WHERE s.version = :version"),
    @NamedQuery(name = "SUser.findByHoraingresoAu", query = "SELECT s FROM SUser s WHERE s.horaingresoAu = :horaingresoAu"),
    @NamedQuery(name = "SUser.findByUsuarioingresoAu", query = "SELECT s FROM SUser s WHERE s.usuarioingresoAu = :usuarioingresoAu"),
    @NamedQuery(name = "SUser.findByHoramodificacionAu", query = "SELECT s FROM SUser s WHERE s.horamodificacionAu = :horamodificacionAu"),
    @NamedQuery(name = "SUser.findByUsuariomodificacionAu", query = "SELECT s FROM SUser s WHERE s.usuariomodificacionAu = :usuariomodificacionAu"),
    @NamedQuery(name = "SUser.findByCliId", query = "SELECT s FROM SUser s WHERE s.cliId = :cliId"),
    @NamedQuery(name = "SUser.findByUsrIsmaster", query = "SELECT s FROM SUser s WHERE s.usrIsmaster = :usrIsmaster"),
    @NamedQuery(name = "SUser.findByEstado", query = "SELECT s FROM SUser s WHERE s.estado = :estado")})
public class SUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "usr_id")
    private Long usrId;
    @Size(min = 1, max = 50)
    @Column(name = "usr_loginname")
    private String usrLoginname;
    @Size(max = 50)
    @Column(name = "usr_password")
    private String usrPassword;
    @Size(max = 50)
    @Column(name = "usr_lastname")
    private String usrLastname;
    @Column(name = "usr_firstname")
    private String usrFirstname;
    @Size(max = 200)
    @Column(name = "usr_email")
    private String usrEmail;
    @Size(max = 5)
    @Column(name = "usr_locale")
    private String usrLocale;
    @Column(name = "usr_enabled")
    private boolean usrEnabled;
    @Column(name = "usr_accountNonExpired")
    private boolean usraccountNonExpired;
    @Column(name = "usr_credentialsNonExpired")
    private boolean usrcredentialsNonExpired;
    @Column(name = "usr_accountNonLocked")
    private boolean usraccountNonLocked;
    @Size(max = 20)
    @Column(name = "usr_token")
    private String usrToken;
    @Column(name = "version")
    private int version;
    @Column(name = "horaingreso_au")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaingresoAu;
    @Size(max = 50)
    @Column(name = "usuarioingreso_au")
    private String usuarioingresoAu;
    @Column(name = "horamodificacion_au")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horamodificacionAu;
    @Size(max = 50)
    @Column(name = "usuariomodificacion_au")
    private String usuariomodificacionAu;
    @Column(name = "cli_id")
    private Integer cliId;
    @Column(name = "usr_ismaster")
    private boolean usrIsmaster;
    @Column(name = "usr_tipo_usuario")
    private String usrTipoUsuario;
    @Column(name = "estado")
    private String estado;
    @Column(name = "usr_telefono")
    private String usrTelefono;
    @Column(name = "usr_codigo_oce")
    private String usrCodigoOce;
    @Column(name = "usr_descripcion")
    private String usrDescripcion;
    @Column(name = "usr_celular")
    private String usrCelular;
    @Column(name = "fechamodificacionclave_au")
    @Temporal(TemporalType.DATE)
    private Date fechamodificacionclaveAu;
    @Column(name = "usr_intentos")
    private int usrIntento;
    @Column(name = "usr_fecha_intento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date usrFechaIntento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sUser")
    private List<SUserrole> sUserroleList;
    @JoinColumn(name = "codigo_soli", referencedColumnName = "codigo_soli")
    @ManyToOne(cascade = CascadeType.MERGE)
    private Solicitud codigoSoli;
    @JoinColumn(name = "codigo_navi", referencedColumnName = "codigo_navi")
    @ManyToOne
    private Naviera codigoNavi;
    @OneToMany(mappedBy = "usrId")
    private List<Transaccion> transaccionList;
    @Transient
    private String confirmPassword;

    public SUser() {
    }

    public SUser(Long usrId) {
        this.usrId = usrId;
    }

    public SUser(Long usrId, String usrLoginname, String usrPassword, boolean usrEnabled, boolean usraccountNonExpired, boolean usrcredentialsNonExpired, boolean usraccountNonLocked, int version, boolean usrIsmaster) {
        this.usrId = usrId;
        this.usrLoginname = usrLoginname;
        this.usrPassword = usrPassword;
        this.usrEnabled = usrEnabled;
        this.usraccountNonExpired = usraccountNonExpired;
        this.usrcredentialsNonExpired = usrcredentialsNonExpired;
        this.usraccountNonLocked = usraccountNonLocked;
        this.version = version;
        this.usrIsmaster = usrIsmaster;
    }

    
    public Long getUsrId() {
        return usrId;
    }

    public void setUsrId(Long usrId) {
        this.usrId = usrId;
    }

    public String getUsrLoginname() {
        return usrLoginname;
    }

    public void setUsrLoginname(String usrLoginname) {
        this.usrLoginname = usrLoginname;
    }

    public String getUsrPassword() {
        return usrPassword;
    }

    public void setUsrPassword(String usrPassword) {
        this.usrPassword = usrPassword;
    }

    public String getUsrLastname() {
        return usrLastname;
    }

    public void setUsrLastname(String usrLastname) {
        this.usrLastname = usrLastname;
    }

    public String getUsrFirstname() {
        return usrFirstname;
    }

    public void setUsrFirstname(String usrFirstname) {
        this.usrFirstname = usrFirstname;
    }

    public String getUsrEmail() {
        return usrEmail;
    }

    public void setUsrEmail(String usrEmail) {
        this.usrEmail = usrEmail;
    }

    public String getUsrLocale() {
        return usrLocale;
    }

    public void setUsrLocale(String usrLocale) {
        this.usrLocale = usrLocale;
    }

    public boolean getUsrEnabled() {
        return usrEnabled;
    }

    public void setUsrEnabled(boolean usrEnabled) {
        this.usrEnabled = usrEnabled;
    }

    public boolean getUsraccountNonExpired() {
        return usraccountNonExpired;
    }

    public void setUsraccountNonExpired(boolean usraccountNonExpired) {
        this.usraccountNonExpired = usraccountNonExpired;
    }

    public boolean getUsrcredentialsNonExpired() {
        return usrcredentialsNonExpired;
    }

    public void setUsrcredentialsNonExpired(boolean usrcredentialsNonExpired) {
        this.usrcredentialsNonExpired = usrcredentialsNonExpired;
    }

    public boolean getUsraccountNonLocked() {
        return usraccountNonLocked;
    }

    public void setUsraccountNonLocked(boolean usraccountNonLocked) {
        this.usraccountNonLocked = usraccountNonLocked;
    }

    public String getUsrToken() {
        return usrToken;
    }

    public void setUsrToken(String usrToken) {
        this.usrToken = usrToken;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Date getHoraingresoAu() {
        return horaingresoAu;
    }

    public void setHoraingresoAu(Date horaingresoAu) {
        this.horaingresoAu = horaingresoAu;
    }

    public String getUsuarioingresoAu() {
        return usuarioingresoAu;
    }

    public void setUsuarioingresoAu(String usuarioingresoAu) {
        this.usuarioingresoAu = usuarioingresoAu;
    }

    public Date getHoramodificacionAu() {
        return horamodificacionAu;
    }

    public void setHoramodificacionAu(Date horamodificacionAu) {
        this.horamodificacionAu = horamodificacionAu;
    }

    public String getUsuariomodificacionAu() {
        return usuariomodificacionAu;
    }

    public void setUsuariomodificacionAu(String usuariomodificacionAu) {
        this.usuariomodificacionAu = usuariomodificacionAu;
    }

    public Integer getCliId() {
        return cliId;
    }

    public void setCliId(Integer cliId) {
        this.cliId = cliId;
    }

    public boolean getUsrIsmaster() {
        return usrIsmaster;
    }

    public void setUsrIsmaster(boolean usrIsmaster) {
        this.usrIsmaster = usrIsmaster;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<SUserrole> getSUserroleList() {
        return sUserroleList;
    }

    public void setSUserroleList(List<SUserrole> sUserroleList) {
        this.sUserroleList = sUserroleList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usrId != null ? usrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SUser)) {
            return false;
        }
        SUser other = (SUser) object;
        if ((this.usrId == null && other.usrId != null) || (this.usrId != null && !this.usrId.equals(other.usrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.SUser[ usrId=" + usrId + " ]";
    }

    /**
     * @return the confirmPassword
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * @param confirmPassword the confirmPassword to set
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /**
     * @return the usrTipoUsuario
     */
    public String getUsrTipoUsuario() {
        return usrTipoUsuario;
    }

    /**
     * @param usrTipoUsuario the usrTipoUsuario to set
     */
    public void setUsrTipoUsuario(String usrTipoUsuario) {
        this.usrTipoUsuario = usrTipoUsuario;
    }

    /**
     * @return the fechamodificacionclaveAu
     */
    public Date getFechamodificacionclaveAu() {
        return fechamodificacionclaveAu;
    }

    /**
     * @param fechamodificacionclaveAu the fechamodificacionclaveAu to set
     */
    public void setFechamodificacionclaveAu(Date fechamodificacionclaveAu) {
        this.fechamodificacionclaveAu = fechamodificacionclaveAu;
    }

    /**
     * @return the codigoSoli
     */
    public Solicitud getCodigoSoli() {
        return codigoSoli;
    }

    /**
     * @param codigoSoli the codigoSoli to set
     */
    public void setCodigoSoli(Solicitud codigoSoli) {
        this.codigoSoli = codigoSoli;
    }

    /**
     * @return the usrTelefono
     */
    public String getUsrTelefono() {
        return usrTelefono;
    }

    /**
     * @param usrTelefono the usrTelefono to set
     */
    public void setUsrTelefono(String usrTelefono) {
        this.usrTelefono = usrTelefono;
    }

    /**
     * @return the usrCelular
     */
    public String getUsrCelular() {
        return usrCelular;
    }

    /**
     * @param usrCelular the usrCelular to set
     */
    public void setUsrCelular(String usrCelular) {
        this.usrCelular = usrCelular;
    }

    @XmlTransient
    public List<Transaccion> getTransaccionList() {
        return transaccionList;
    }

    public void setTransaccionList(List<Transaccion> transaccionList) {
        this.transaccionList = transaccionList;
    }

    /**
     * @return the usrCodigoOce
     */
    public String getUsrCodigoOce() {
        return usrCodigoOce;
    }

    /**
     * @param usrCodigoOce the usrCodigoOce to set
     */
    public void setUsrCodigoOce(String usrCodigoOce) {
        this.usrCodigoOce = usrCodigoOce;
    }

    /**
     * @return the usrDescripcion
     */
    public String getUsrDescripcion() {
        return usrDescripcion;
    }

    /**
     * @param usrDescripcion the usrDescripcion to set
     */
    public void setUsrDescripcion(String usrDescripcion) {
        this.usrDescripcion = usrDescripcion;
    }

    /**
     * @return the codigoNavi
     */
    public Naviera getCodigoNavi() {
        return codigoNavi;
    }

    /**
     * @param codigoNavi the codigoNavi to set
     */
    public void setCodigoNavi(Naviera codigoNavi) {
        this.codigoNavi = codigoNavi;
    }

    /**
     * @return the usrIntento
     */
    public int getUsrIntento() {
        return usrIntento;
    }

    /**
     * @param usrIntento the usrIntento to set
     */
    public void setUsrIntento(int usrIntento) {
        this.usrIntento = usrIntento;
    }

    /**
     * @return the usrFechaIntento
     */
    public Date getUsrFechaIntento() {
        return usrFechaIntento;
    }

    /**
     * @param usrFechaIntento the usrFechaIntento to set
     */
    public void setUsrFechaIntento(Date usrFechaIntento) {
        this.usrFechaIntento = usrFechaIntento;
    }

}
