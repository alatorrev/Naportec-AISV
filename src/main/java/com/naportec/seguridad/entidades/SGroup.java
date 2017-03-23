/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
@Table(name = "s_group")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SGroup.findAll", query = "SELECT s FROM SGroup s"),
    @NamedQuery(name = "SGroup.findByGrpId", query = "SELECT s FROM SGroup s WHERE s.grpId = :grpId"),
    @NamedQuery(name = "SGroup.findByGrpShortdescription", query = "SELECT s FROM SGroup s WHERE s.grpShortdescription = :grpShortdescription"),
    @NamedQuery(name = "SGroup.findByGrpLongdescription", query = "SELECT s FROM SGroup s WHERE s.grpLongdescription = :grpLongdescription"),
    @NamedQuery(name = "SGroup.findByVersion", query = "SELECT s FROM SGroup s WHERE s.version = :version"),
    @NamedQuery(name = "SGroup.findByHoraingresoAu", query = "SELECT s FROM SGroup s WHERE s.horaingresoAu = :horaingresoAu"),
    @NamedQuery(name = "SGroup.findByUsuarioingresoAu", query = "SELECT s FROM SGroup s WHERE s.usuarioingresoAu = :usuarioingresoAu"),
    @NamedQuery(name = "SGroup.findByHoramodificacionAu", query = "SELECT s FROM SGroup s WHERE s.horamodificacionAu = :horamodificacionAu"),
    @NamedQuery(name = "SGroup.findByUsuariomodificacionAu", query = "SELECT s FROM SGroup s WHERE s.usuariomodificacionAu = :usuariomodificacionAu"),
    @NamedQuery(name = "SGroup.findByEstado", query = "SELECT s FROM SGroup s WHERE s.estado = :estado")})
public class SGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "grp_id")
    private Integer grpId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "grp_shortdescription")
    private String grpShortdescription;
    @Size(max = 1000)
    @Column(name = "grp_longdescription")
    private String grpLongdescription;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sGroup")
    private List<SRolegroup> sRolegroupList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sGroup")
    private List<SGroupright> sGrouprightList;

    public SGroup() {
    }

    public SGroup(Integer grpId) {
        this.grpId = grpId;
    }

    public SGroup(Integer grpId, String grpShortdescription, int version) {
        this.grpId = grpId;
        this.grpShortdescription = grpShortdescription;
        this.version = version;
    }

    public Integer getGrpId() {
        return grpId;
    }

    public void setGrpId(Integer grpId) {
        this.grpId = grpId;
    }

    public String getGrpShortdescription() {
        return grpShortdescription;
    }

    public void setGrpShortdescription(String grpShortdescription) {
        this.grpShortdescription = grpShortdescription;
    }

    public String getGrpLongdescription() {
        return grpLongdescription;
    }

    public void setGrpLongdescription(String grpLongdescription) {
        this.grpLongdescription = grpLongdescription;
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
    public List<SRolegroup> getSRolegroupList() {
        return sRolegroupList;
    }

    public void setSRolegroupList(List<SRolegroup> sRolegroupList) {
        this.sRolegroupList = sRolegroupList;
    }

    @XmlTransient
    public List<SGroupright> getSGrouprightList() {
        return sGrouprightList;
    }

    public void setSGrouprightList(List<SGroupright> sGrouprightList) {
        this.sGrouprightList = sGrouprightList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (grpId != null ? grpId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SGroup)) {
            return false;
        }
        SGroup other = (SGroup) object;
        if ((this.grpId == null && other.grpId != null) || (this.grpId != null && !this.grpId.equals(other.grpId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.SGroup[ grpId=" + grpId + " ]";
    }
    
}
