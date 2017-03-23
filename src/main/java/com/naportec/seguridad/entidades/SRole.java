
package com.naportec.seguridad.entidades;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Nestor
 */
@Entity
@Table(name = "s_role")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SRole.findAll", query = "SELECT s FROM SRole s"),
    @NamedQuery(name = "SRole.findByRolId", query = "SELECT s FROM SRole s WHERE s.rolId = :rolId"),
    @NamedQuery(name = "SRole.findByRolShortdescription", query = "SELECT s FROM SRole s WHERE s.rolShortdescription = :rolShortdescription"),
    @NamedQuery(name = "SRole.findByRolLongdescription", query = "SELECT s FROM SRole s WHERE s.rolLongdescription = :rolLongdescription"),
    @NamedQuery(name = "SRole.findByVersion", query = "SELECT s FROM SRole s WHERE s.version = :version"),
    @NamedQuery(name = "SRole.findByHoraingresoAu", query = "SELECT s FROM SRole s WHERE s.horaingresoAu = :horaingresoAu"),
    @NamedQuery(name = "SRole.findByUsuarioingresoAu", query = "SELECT s FROM SRole s WHERE s.usuarioingresoAu = :usuarioingresoAu"),
    @NamedQuery(name = "SRole.findByHoramodificacionAu", query = "SELECT s FROM SRole s WHERE s.horamodificacionAu = :horamodificacionAu"),
    @NamedQuery(name = "SRole.findByUsuariomodificacionAu", query = "SELECT s FROM SRole s WHERE s.usuariomodificacionAu = :usuariomodificacionAu"),
    @NamedQuery(name = "SRole.findByEstado", query = "SELECT s FROM SRole s WHERE s.estado = :estado")})
public class SRole implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rol_id")
    private Integer rolId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "rol_shortdescription")
    private String rolShortdescription;
    @Size(max = 1000)
    @Column(name = "rol_longdescription")
    private String rolLongdescription;
    @Basic(optional = false)
    @NotNull
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
    @Column(name = "estado")
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sRole")
    private List<SUserrole> sUserroleList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sRole")
    private List<SRolegroup> sRolegroupList;

    public SRole() {
    }

    public SRole(Integer rolId) {
        this.rolId = rolId;
    }

    public SRole(Integer rolId, String rolShortdescription, int version) {
        this.rolId = rolId;
        this.rolShortdescription = rolShortdescription;
        this.version = version;
    }

    public Integer getRolId() {
        return rolId;
    }

    public void setRolId(Integer rolId) {
        this.rolId = rolId;
    }

    public String getRolShortdescription() {
        return rolShortdescription;
    }

    public void setRolShortdescription(String rolShortdescription) {
        this.rolShortdescription = rolShortdescription;
    }

    public String getRolLongdescription() {
        return rolLongdescription;
    }

    public void setRolLongdescription(String rolLongdescription) {
        this.rolLongdescription = rolLongdescription;
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

    @XmlTransient
    public List<SRolegroup> getSRolegroupList() {
        return sRolegroupList;
    }

    public void setSRolegroupList(List<SRolegroup> sRolegroupList) {
        this.sRolegroupList = sRolegroupList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rolId != null ? rolId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SRole)) {
            return false;
        }
        SRole other = (SRole) object;
        if ((this.rolId == null && other.rolId != null) || (this.rolId != null && !this.rolId.equals(other.rolId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.SRole[ rolId=" + rolId + " ]";
    }
    
}
