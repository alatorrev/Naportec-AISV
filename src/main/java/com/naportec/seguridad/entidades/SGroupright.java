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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "s_groupright")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SGroupright.findAll", query = "SELECT s FROM SGroupright s"),
    @NamedQuery(name = "SGroupright.eliminar", query = "DELETE FROM SGroupright s WHERE s.sGroup=:grupo AND s.sRight=:privilegio"),
    @NamedQuery(name = "SGroupright.buscarPrivilegio", query = "SELECT s.sRight FROM SGroupright s WHERE s.sGroup=:grupo"),
    @NamedQuery(name = "SGroupright.buscarPrivilegioGrupo", query = "SELECT s FROM SGroupright s WHERE s.sGroup=:grupo"),
    @NamedQuery(name = "SGroupright.findPK", query = "SELECT s FROM SGroupright s WHERE s.sGrouprightPK=:pk"),
    @NamedQuery(name = "SGroupright.findByGriId", query = "SELECT s FROM SGroupright s WHERE s.griId = :griId"),
    @NamedQuery(name = "SGroupright.findByGrpId", query = "SELECT s FROM SGroupright s WHERE s.sGrouprightPK.grpId = :grpId"),
    @NamedQuery(name = "SGroupright.findByRigId", query = "SELECT s FROM SGroupright s WHERE s.sGrouprightPK.rigId = :rigId"),
    @NamedQuery(name = "SGroupright.findByVersion", query = "SELECT s FROM SGroupright s WHERE s.version = :version"),
    @NamedQuery(name = "SGroupright.findByHoraingresoAu", query = "SELECT s FROM SGroupright s WHERE s.horaingresoAu = :horaingresoAu"),
    @NamedQuery(name = "SGroupright.findByUsuarioingresoAu", query = "SELECT s FROM SGroupright s WHERE s.usuarioingresoAu = :usuarioingresoAu"),
    @NamedQuery(name = "SGroupright.findByHoramodificacionAu", query = "SELECT s FROM SGroupright s WHERE s.horamodificacionAu = :horamodificacionAu"),
    @NamedQuery(name = "SGroupright.findByUsuariomodificacionAu", query = "SELECT s FROM SGroupright s WHERE s.usuariomodificacionAu = :usuariomodificacionAu")})
public class SGroupright implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "gri_id")
    private int griId;
    @EmbeddedId
    protected SGrouprightPK sGrouprightPK;
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
    @JoinColumn(name = "grp_id", referencedColumnName = "grp_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private SGroup sGroup;
    @JoinColumn(name = "rig_id", referencedColumnName = "rig_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private SRight sRight;

    public SGroupright() {
    }

    public SGroupright(SGrouprightPK sGrouprightPK) {
        this.sGrouprightPK = sGrouprightPK;
    }

    public SGroupright(SGrouprightPK sGrouprightPK, int griId, int version) {
        this.sGrouprightPK = sGrouprightPK;
        this.griId = griId;
        this.version = version;
    }

    public SGroupright(int grpId, int rigId) {
        this.sGrouprightPK = new SGrouprightPK(grpId, rigId);
    }

    public SGrouprightPK getSGrouprightPK() {
        return sGrouprightPK;
    }

    public void setSGrouprightPK(SGrouprightPK sGrouprightPK) {
        this.sGrouprightPK = sGrouprightPK;
    }

    public int getGriId() {
        return griId;
    }

    public void setGriId(int griId) {
        this.griId = griId;
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

    public SGroup getSGroup() {
        return sGroup;
    }

    public void setSGroup(SGroup sGroup) {
        this.sGroup = sGroup;
    }

    public SRight getSRight() {
        return sRight;
    }

    public void setSRight(SRight sRight) {
        this.sRight = sRight;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sGrouprightPK != null ? sGrouprightPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SGroupright)) {
            return false;
        }
        SGroupright other = (SGroupright) object;
        if ((this.sGrouprightPK == null && other.sGrouprightPK != null) || (this.sGrouprightPK != null && !this.sGrouprightPK.equals(other.sGrouprightPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.SGroupright[ sGrouprightPK=" + sGrouprightPK + " ]";
    }

}
