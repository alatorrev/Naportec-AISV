/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.seguridad.entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
 * @author Nestor
 */
@Entity
@Table(name = "s_right")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SRight.findAll", query = "SELECT s FROM SRight s"),
    @NamedQuery(name = "SRight.findByRigId", query = "SELECT s FROM SRight s WHERE s.rigId = :rigId"),
    @NamedQuery(name = "SRight.findByRigType", query = "SELECT s FROM SRight s WHERE s.rigType = :rigType"),
    @NamedQuery(name = "SRight.findByRigName", query = "SELECT s FROM SRight s WHERE s.rigName = :rigName"),
    @NamedQuery(name = "SRight.findByVersion", query = "SELECT s FROM SRight s WHERE s.version = :version"),
    @NamedQuery(name = "SRight.findByRigJasper", query = "SELECT s FROM SRight s WHERE s.rigJasper = :rigJasper"),
    @NamedQuery(name = "SRight.findByRigWindow", query = "SELECT s FROM SRight s WHERE s.rigWindow = :rigWindow"),
    @NamedQuery(name = "SRight.findByIidnodopadre", query = "SELECT s FROM SRight s WHERE s.iidnodopadre = :iidnodopadre"),
    @NamedQuery(name = "SRight.findByInivel", query = "SELECT s FROM SRight s WHERE s.inivel = :inivel"),
    @NamedQuery(name = "SRight.findByIidplantilla", query = "SELECT s FROM SRight s WHERE s.iidplantilla = :iidplantilla"),
    @NamedQuery(name = "SRight.findByCcompromiso", query = "SELECT s FROM SRight s WHERE s.ccompromiso = :ccompromiso"),
    @NamedQuery(name = "SRight.findByCstts", query = "SELECT s FROM SRight s WHERE s.cstts = :cstts"),
    @NamedQuery(name = "SRight.findByHoraingresoAu", query = "SELECT s FROM SRight s WHERE s.horaingresoAu = :horaingresoAu"),
    @NamedQuery(name = "SRight.findByUsuarioingresoAu", query = "SELECT s FROM SRight s WHERE s.usuarioingresoAu = :usuarioingresoAu"),
    @NamedQuery(name = "SRight.findByHoramodificacionAu", query = "SELECT s FROM SRight s WHERE s.horamodificacionAu = :horamodificacionAu"),
    @NamedQuery(name = "SRight.findByUsuariomodificacionAu", query = "SELECT s FROM SRight s WHERE s.usuariomodificacionAu = :usuariomodificacionAu"),
    @NamedQuery(name = "SRight.findByVestadocolaborador", query = "SELECT s FROM SRight s WHERE s.vestadocolaborador = :vestadocolaborador"),
    @NamedQuery(name = "SRight.findByEstado", query = "SELECT s FROM SRight s WHERE s.estado = :estado")})
public class SRight implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rig_id")
    private Integer rigId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rig_type")
    private int rigType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "rig_name")
    private String rigName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "version")
    private int version;
    @Size(max = 150)
    @Column(name = "rig_jasper")
    private String rigJasper;
    @Size(max = 150)
    @Column(name = "rig_window")
    private String rigWindow;
    @Column(name = "iidnodopadre")
    private BigInteger iidnodopadre;
    @Column(name = "inivel")
    private Short inivel;
    @Column(name = "iidplantilla")
    private Integer iidplantilla;
    @Column(name = "ccompromiso")
    private Character ccompromiso;
    @Column(name = "cstts")
    private Character cstts;
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
    @Size(max = 6)
    @Column(name = "vestadocolaborador")
    private String vestadocolaborador;
    @Column(name = "estado")
    private String estado;
    @Column(name = "alto_window")
    private Integer altoWindow;
    @Column(name = "ancho_window")
    private Integer anchoWindow;
    @Column(name = "icono_window")
    private String iconoWindow;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sRight")
    private List<SGroupright> sGrouprightList;
    @Transient
    private String tipo;

    public SRight() {
    }

    public SRight(Integer rigId) {
        this.rigId = rigId;
    }

    public SRight(Integer rigId, int rigType, String rigName, int version) {
        this.rigId = rigId;
        this.rigType = rigType;
        this.rigName = rigName;
        this.version = version;
    }

    public Integer getRigId() {
        return rigId;
    }

    public void setRigId(Integer rigId) {
        this.rigId = rigId;
    }

    public int getRigType() {
        return rigType;
    }

    public void setRigType(int rigType) {
        this.rigType = rigType;
    }

    public String getRigName() {
        return rigName;
    }

    public void setRigName(String rigName) {
        this.rigName = rigName;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getRigJasper() {
        return rigJasper;
    }

    public void setRigJasper(String rigJasper) {
        this.rigJasper = rigJasper;
    }

    public String getRigWindow() {
        return rigWindow;
    }

    public void setRigWindow(String rigWindow) {
        this.rigWindow = rigWindow;
    }

    public BigInteger getIidnodopadre() {
        return iidnodopadre;
    }

    public void setIidnodopadre(BigInteger iidnodopadre) {
        this.iidnodopadre = iidnodopadre;
    }

    public Short getInivel() {
        return inivel;
    }

    public void setInivel(Short inivel) {
        this.inivel = inivel;
    }

    public Integer getIidplantilla() {
        return iidplantilla;
    }

    public void setIidplantilla(Integer iidplantilla) {
        this.iidplantilla = iidplantilla;
    }

    public Character getCcompromiso() {
        return ccompromiso;
    }

    public void setCcompromiso(Character ccompromiso) {
        this.ccompromiso = ccompromiso;
    }

    public Character getCstts() {
        return cstts;
    }

    public void setCstts(Character cstts) {
        this.cstts = cstts;
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

    public String getVestadocolaborador() {
        return vestadocolaborador;
    }

    public void setVestadocolaborador(String vestadocolaborador) {
        this.vestadocolaborador = vestadocolaborador;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
        hash += (rigId != null ? rigId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SRight)) {
            return false;
        }
        SRight other = (SRight) object;
        if ((this.rigId == null && other.rigId != null) || (this.rigId != null && !this.rigId.equals(other.rigId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.SRight[ rigId=" + rigId + " ]";
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        switch (rigType) {
            case 0:
                tipo = "";
                break;
            case 1:
                tipo = "JasperReport";
                break;
            case 2:
                tipo = "Page";
                break;
            case 3:
                tipo = "Proceso";
                break;
            case 4:
                tipo = "Method";
                break;
            case 5:
                tipo = "Component";
                break;
        }
        return tipo;
    }

    /**
     * @return the altoWindow
     */
    public Integer getAltoWindow() {
        return altoWindow;
    }

    /**
     * @param altoWindow the altoWindow to set
     */
    public void setAltoWindow(Integer altoWindow) {
        this.altoWindow = altoWindow;
    }

    /**
     * @return the anchoWindow
     */
    public Integer getAnchoWindow() {
        return anchoWindow;
    }

    /**
     * @param anchoWindow the anchoWindow to set
     */
    public void setAnchoWindow(Integer anchoWindow) {
        this.anchoWindow = anchoWindow;
    }

    /**
     * @return the iconoWindow
     */
    public String getIconoWindow() {
        return iconoWindow;
    }

    /**
     * @param iconoWindow the iconoWindow to set
     */
    public void setIconoWindow(String iconoWindow) {
        this.iconoWindow = iconoWindow;
    }

}
