/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.naportec.seguridad.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nestor
 */
@Entity
@Table(name = "s_rolegroup")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SRolegroup.findAll", query = "SELECT s FROM SRolegroup s"),
    @NamedQuery(name = "SRolegroup.eliminar", query = "DELETE FROM SRolegroup s WHERE s.sRole=:rol AND s.sGroup=:grupo"),
    @NamedQuery(name = "SRolegroup.buscarGrupo", query = "SELECT s.sGroup FROM SRolegroup s WHERE s.sRole=:rol"),
    @NamedQuery(name = "SRolegroup.findPK", query = "SELECT s FROM SRolegroup s WHERE s.sRole.rolId = :rol AND s.sGroup.grpId = :grupo"),
    @NamedQuery(name = "SRolegroup.findByGrpId", query = "SELECT s FROM SRolegroup s WHERE s.sRolegroupPK.grpId = :grpId"),
    @NamedQuery(name = "SRolegroup.findByRolId", query = "SELECT s FROM SRolegroup s WHERE s.sRolegroupPK.rolId = :rolId"),
    @NamedQuery(name = "SRolegroup.findByVersion", query = "SELECT s FROM SRolegroup s WHERE s.version = :version"),
    @NamedQuery(name = "SRolegroup.findByHoraingresoAu", query = "SELECT s FROM SRolegroup s WHERE s.horaingresoAu = :horaingresoAu"),
    @NamedQuery(name = "SRolegroup.findByUsuarioingresoAu", query = "SELECT s FROM SRolegroup s WHERE s.usuarioingresoAu = :usuarioingresoAu"),
    @NamedQuery(name = "SRolegroup.findByHoramodificacionAu", query = "SELECT s FROM SRolegroup s WHERE s.horamodificacionAu = :horamodificacionAu"),
    @NamedQuery(name = "SRolegroup.findByUsuariomodificacionAu", query = "SELECT s FROM SRolegroup s WHERE s.usuariomodificacionAu = :usuariomodificacionAu")})
public class SRolegroup implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SRolegroupPK sRolegroupPK;
    @Basic(optional = false)
    @Column(name = "rlg_id")
    private int rlgId;
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
    @JoinColumn(name = "rol_id", referencedColumnName = "rol_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private SRole sRole;
    @JoinColumn(name = "grp_id", referencedColumnName = "grp_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private SGroup sGroup;

    public SRolegroup() {
    }

    public SRolegroup(SRolegroupPK sRolegroupPK) {
        this.sRolegroupPK = sRolegroupPK;
    }

    public SRolegroup(SRolegroupPK sRolegroupPK, int rlgId, int version) {
        this.sRolegroupPK = sRolegroupPK;
        this.rlgId = rlgId;
        this.version = version;
    }

    public SRolegroup(int grpId, int rolId) {
        this.sRolegroupPK = new SRolegroupPK(grpId, rolId);
    }

    public SRolegroupPK getSRolegroupPK() {
        return sRolegroupPK;
    }

    public void setSRolegroupPK(SRolegroupPK sRolegroupPK) {
        this.sRolegroupPK = sRolegroupPK;
    }

    public int getRlgId() {
        return rlgId;
    }

    public void setRlgId(int rlgId) {
        this.rlgId = rlgId;
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

    public SRole getSRole() {
        return sRole;
    }

    public void setSRole(SRole sRole) {
        this.sRole = sRole;
    }

    public SGroup getSGroup() {
        return sGroup;
    }

    public void setSGroup(SGroup sGroup) {
        this.sGroup = sGroup;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sRolegroupPK != null ? sRolegroupPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SRolegroup)) {
            return false;
        }
        SRolegroup other = (SRolegroup) object;
        if ((this.sRolegroupPK == null && other.sRolegroupPK != null) || (this.sRolegroupPK != null && !this.sRolegroupPK.equals(other.sRolegroupPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.SRolegroup[ sRolegroupPK=" + sRolegroupPK + " ]";
    }
    
}
