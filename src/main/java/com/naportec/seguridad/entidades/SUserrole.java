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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nestor
 */
@Entity
@Table(name = "s_userrole")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SUserrole.findAll", query = "SELECT s FROM SUserrole s"),
    @NamedQuery(name = "SUserrole.eliminar", query = "DELETE FROM SUserrole s WHERE s.sUser=:user AND s.sRole=:rol"),
    @NamedQuery(name = "SUserrole.findPK", query = "SELECT s FROM SUserrole s WHERE s.sUserrolePK=:pk"),
    @NamedQuery(name = "SUserrole.findByUrrId", query = "SELECT s FROM SUserrole s WHERE s.urrId = :urrId"),
    @NamedQuery(name = "SUserrole.findByUsrId", query = "SELECT s.sRole FROM SUserrole s WHERE s.sUser.usrId= :usrId"),
    @NamedQuery(name = "SUserrole.findByRolId", query = "SELECT s FROM SUserrole s WHERE s.sUserrolePK.rolId = :rolId"),
    @NamedQuery(name = "SUserrole.findByVersion", query = "SELECT s FROM SUserrole s WHERE s.version = :version"),
    @NamedQuery(name = "SUserrole.findByHoraingresoAu", query = "SELECT s FROM SUserrole s WHERE s.horaingresoAu = :horaingresoAu"),
    @NamedQuery(name = "SUserrole.findByUsuarioingresoAu", query = "SELECT s FROM SUserrole s WHERE s.usuarioingresoAu = :usuarioingresoAu"),
    @NamedQuery(name = "SUserrole.findByHoramodificacionAu", query = "SELECT s FROM SUserrole s WHERE s.horamodificacionAu = :horamodificacionAu"),
    @NamedQuery(name = "SUserrole.findByUsuariomodificacionAu", query = "SELECT s FROM SUserrole s WHERE s.usuariomodificacionAu = :usuariomodificacionAu")})
public class SUserrole implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SUserrolePK sUserrolePK;
    @Basic(optional = false)
    @Column(name = "urr_id")
    private long urrId;
    @Column(name = "version")
    private Integer version;
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
    @JoinColumn(name = "usr_id", referencedColumnName = "usr_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private SUser sUser;

    public SUserrole() {
    }

    public SUserrole(SUserrolePK sUserrolePK) {
        this.sUserrolePK = sUserrolePK;
    }

    public SUserrole(SUserrolePK sUserrolePK, long urrId) {
        this.sUserrolePK = sUserrolePK;
        this.urrId = urrId;
    }

    public SUserrole(long usrId, int rolId) {
        this.sUserrolePK = new SUserrolePK(usrId, rolId);
    }

    public SUserrolePK getSUserrolePK() {
        return sUserrolePK;
    }

    public void setSUserrolePK(SUserrolePK sUserrolePK) {
        this.sUserrolePK = sUserrolePK;
    }

    public long getUrrId() {
        return urrId;
    }

    public void setUrrId(long urrId) {
        this.urrId = urrId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
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

    public SUser getSUser() {
        return sUser;
    }

    public void setSUser(SUser sUser) {
        this.sUser = sUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sUserrolePK != null ? sUserrolePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SUserrole)) {
            return false;
        }
        SUserrole other = (SUserrole) object;
        if ((this.sUserrolePK == null && other.sUserrolePK != null) || (this.sUserrolePK != null && !this.sUserrolePK.equals(other.sUserrolePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.SUserrole[ sUserrolePK=" + sUserrolePK + " ]";
    }
    
}
