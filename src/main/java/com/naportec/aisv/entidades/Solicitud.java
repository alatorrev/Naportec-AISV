/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.aisv.entidades;

import com.naportec.seguridad.entidades.SUser;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Fernando
 */
@Entity
@Table(name = "aisv_solicitud")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Solicitud.findAll", query = "SELECT s FROM Solicitud s"),
    @NamedQuery(name = "Solicitud.existe", query = "SELECT s FROM Solicitud s WHERE s.identificacionSoli=:iden"),
    @NamedQuery(name = "Solicitud.existeSolicitud", query = "SELECT s FROM Solicitud s WHERE s.identificacionSoli=:iden AND s.estado != :estado"),
    @NamedQuery(name = "Solicitud.findByCodigoSoli", query = "SELECT s FROM Solicitud s WHERE s.codigoSoli = :codigoSoli"),
    @NamedQuery(name = "Solicitud.findByUsuarioSoli", query = "SELECT s FROM Solicitud s WHERE s.usuarioSoli = :usuarioSoli"),
    @NamedQuery(name = "Solicitud.findByNombreSoli", query = "SELECT s FROM Solicitud s WHERE s.nombreSoli = :nombreSoli"),
    @NamedQuery(name = "Solicitud.findByTipoIdentificacionSoli", query = "SELECT s FROM Solicitud s WHERE s.tipoIdentificacionSoli = :tipoIdentificacionSoli"),
    @NamedQuery(name = "Solicitud.findByIdentificacionSoli", query = "SELECT s FROM Solicitud s WHERE s.identificacionSoli = :identificacionSoli"),
    @NamedQuery(name = "Solicitud.findByDireccionSoli", query = "SELECT s FROM Solicitud s WHERE s.direccionSoli = :direccionSoli"),
    @NamedQuery(name = "Solicitud.findByTipoContactoSoli", query = "SELECT s FROM Solicitud s WHERE s.tipoContactoSoli = :tipoContactoSoli"),
    @NamedQuery(name = "Solicitud.findByTelefonoSoli", query = "SELECT s FROM Solicitud s WHERE s.telefonoSoli = :telefonoSoli"),
    @NamedQuery(name = "Solicitud.findByCelularSoli", query = "SELECT s FROM Solicitud s WHERE s.celularSoli = :celularSoli"),
    @NamedQuery(name = "Solicitud.findByCorreoSoli", query = "SELECT s FROM Solicitud s WHERE s.correoSoli = :correoSoli"),
    @NamedQuery(name = "Solicitud.findByFechaCreacionSoli", query = "SELECT s FROM Solicitud s WHERE s.fechaCreacionSoli = :fechaCreacionSoli"),
    @NamedQuery(name = "Solicitud.findByIpCreacionSoli", query = "SELECT s FROM Solicitud s WHERE s.ipCreacionSoli = :ipCreacionSoli"),
    @NamedQuery(name = "Solicitud.findByEstadoSoli", query = "SELECT s FROM Solicitud s WHERE s.estado = :estado")})
public class Solicitud implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_soli")
    private Long codigoSoli;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "usuario_soli")
    private String usuarioSoli;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nombre_soli")
    private String nombreSoli;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "tipo_identificacion_soli")
    private String tipoIdentificacionSoli;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "identificacion_soli")
    private String identificacionSoli;
    @Size(max = 300)
    @Column(name = "direccion_soli")
    private String direccionSoli;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "tipo_contacto_soli")
    private String tipoContactoSoli;
    @Size(max = 20)
    @Column(name = "telefono_soli")
    private String telefonoSoli;
    @Size(max = 20)
    @Column(name = "celular_soli")
    private String celularSoli;
    @Size(max = 200)
    @Column(name = "correo_soli")
    private String correoSoli;
    @Column(name = "fecha_creacion_soli")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacionSoli;
    @Size(max = 20)
    @Column(name = "ip_creacion_soli")
    private String ipCreacionSoli;
    @Column(name = "estado_soli")
    private String estado;
    @Column(name = "fecha_mod_soli")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacionSoli;
     @Column(name = "usuario_mod_soli")
    private String usuarioModificacionSoli;
      @Column(name = "motivo_soli")
    private String motivoSoli;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoSoli",fetch = FetchType.EAGER)
    private List<Contacto> contactoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoSoli",fetch = FetchType.EAGER)
    private List<SUser> sUserList;
    @Column(name = "oce_soli")
    private String oceSoli;
    @Column(name = "descripcion_soli")
    private String descripcionSoli;
    @Transient
    private String tipo;
    @Transient
    private boolean tipoContactoNaviera;

    public Solicitud() {
    }

    public Solicitud(Long codigoSoli) {
        this.codigoSoli = codigoSoli;
    }

    public Solicitud(Long codigoSoli, String usuarioSoli, String nombreSoli, String tipoIdentificacionSoli, String identificacionSoli, String tipoContactoSoli, String estado) {
        this.codigoSoli = codigoSoli;
        this.usuarioSoli = usuarioSoli;
        this.nombreSoli = nombreSoli;
        this.tipoIdentificacionSoli = tipoIdentificacionSoli;
        this.identificacionSoli = identificacionSoli;
        this.tipoContactoSoli = tipoContactoSoli;
        this.estado = estado;
    }

    public Long getCodigoSoli() {
        return codigoSoli;
    }

    public void setCodigoSoli(Long codigoSoli) {
        this.codigoSoli = codigoSoli;
    }

    public String getUsuarioSoli() {
        return usuarioSoli;
    }

    public void setUsuarioSoli(String usuarioSoli) {
        this.usuarioSoli = usuarioSoli;
    }

    public String getNombreSoli() {
        return nombreSoli;
    }

    public void setNombreSoli(String nombreSoli) {
        this.nombreSoli = nombreSoli;
    }

    public String getTipoIdentificacionSoli() {
        return tipoIdentificacionSoli;
    }

    public void setTipoIdentificacionSoli(String tipoIdentificacionSoli) {
        this.tipoIdentificacionSoli = tipoIdentificacionSoli;
    }

    public String getIdentificacionSoli() {
        return identificacionSoli;
    }

    public void setIdentificacionSoli(String identificacionSoli) {
        this.identificacionSoli = identificacionSoli;
    }

    public String getDireccionSoli() {
        return direccionSoli;
    }

    public void setDireccionSoli(String direccionSoli) {
        this.direccionSoli = direccionSoli;
    }

    public String getTipoContactoSoli() {
        return tipoContactoSoli;
    }

    public void setTipoContactoSoli(String tipoContactoSoli) {
        this.tipoContactoSoli = tipoContactoSoli;
    }

    public String getTelefonoSoli() {
        return telefonoSoli;
    }

    public void setTelefonoSoli(String telefonoSoli) {
        this.telefonoSoli = telefonoSoli;
    }

    public String getCelularSoli() {
        return celularSoli;
    }

    public void setCelularSoli(String celularSoli) {
        this.celularSoli = celularSoli;
    }

    public String getCorreoSoli() {
        return correoSoli;
    }

    public void setCorreoSoli(String correoSoli) {
        this.correoSoli = correoSoli;
    }

    public Date getFechaCreacionSoli() {
        return fechaCreacionSoli;
    }

    public void setFechaCreacionSoli(Date fechaCreacionSoli) {
        this.fechaCreacionSoli = fechaCreacionSoli;
    }

    public String getIpCreacionSoli() {
        return ipCreacionSoli;
    }

    public void setIpCreacionSoli(String ipCreacionSoli) {
        this.ipCreacionSoli = ipCreacionSoli;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<Contacto> getContactoList() {
        return contactoList;
    }

    public void setContactoList(List<Contacto> contactoList) {
        this.contactoList = contactoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoSoli != null ? codigoSoli.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Solicitud)) {
            return false;
        }
        Solicitud other = (Solicitud) object;
        if ((this.codigoSoli == null && other.codigoSoli != null) || (this.codigoSoli != null && !this.codigoSoli.equals(other.codigoSoli))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.naportec.aisv.entidades.Solicitud[ codigoSoli=" + codigoSoli + " ]";
    }

    /**
     * @return the sUserList
     */
    @XmlTransient
    public List<SUser> getsUserList() {
        return sUserList;
    }

    /**
     * @param sUserList the sUserList to set
     */
    public void setsUserList(List<SUser> sUserList) {
        this.sUserList = sUserList;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        if (tipoContactoSoli != null) {
            if (tipoContactoSoli.equals("AA")) {
                tipo = "AGENTE AFIANZADO";
            }
            if (tipoContactoSoli.equals("IE")) {
                tipo = "IMPORTADOR/EXPORTADOR";
            }
            if (tipoContactoSoli.equals("NA")) {
                tipo = "NAVIERA";
            }
        }
        return tipo;
    }

    /**
     * @return the oceSoli
     */
    public String getOceSoli() {
        return oceSoli;
    }

    /**
     * @param oceSoli the oceSoli to set
     */
    public void setOceSoli(String oceSoli) {
        this.oceSoli = oceSoli;
    }

    /**
     * @return the descripcionSoli
     */
    public String getDescripcionSoli() {
        return descripcionSoli;
    }

    /**
     * @param descripcionSoli the descripcionSoli to set
     */
    public void setDescripcionSoli(String descripcionSoli) {
        this.descripcionSoli = descripcionSoli;
    }

    /**
     * @return the tipoContactoNaviera
     */
    public boolean getTipoContactoNaviera() {
        if (this.tipoContactoSoli == null) {
            tipoContactoNaviera = false;
        } else {
            if (this.tipoContactoSoli.equals("NA")) {
                tipoContactoNaviera = true;
            } else {
                tipoContactoNaviera = false;
            }
        }
        return tipoContactoNaviera;
    }

    /**
     * @return the fechaModificacionSoli
     */
    public Date getFechaModificacionSoli() {
        return fechaModificacionSoli;
    }

    /**
     * @param fechaModificacionSoli the fechaModificacionSoli to set
     */
    public void setFechaModificacionSoli(Date fechaModificacionSoli) {
        this.fechaModificacionSoli = fechaModificacionSoli;
    }

    /**
     * @return the usuarioModificacionSoli
     */
    public String getUsuarioModificacionSoli() {
        return usuarioModificacionSoli;
    }

    /**
     * @param usuarioModificacionSoli the usuarioModificacionSoli to set
     */
    public void setUsuarioModificacionSoli(String usuarioModificacionSoli) {
        this.usuarioModificacionSoli = usuarioModificacionSoli;
    }

    /**
     * @return the motivoSoli
     */
    public String getMotivoSoli() {
        return motivoSoli;
    }

    /**
     * @param motivoSoli the motivoSoli to set
     */
    public void setMotivoSoli(String motivoSoli) {
        this.motivoSoli = motivoSoli;
    }

}
