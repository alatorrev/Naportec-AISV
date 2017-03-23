/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.aisv.entidades;

import com.naportec.seguridad.entidades.SUser;
import java.io.Serializable;
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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Fernando
 */
@Entity
@Table(name = "aisv_naviera")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Naviera.findAll", query = "SELECT n FROM Naviera n"),
    @NamedQuery(name = "Naviera.findByCodigoNavi", query = "SELECT n FROM Naviera n WHERE n.codigoNavi = :codigoNavi"),
    @NamedQuery(name = "Naviera.findByCodigoOceNavi", query = "SELECT n FROM Naviera n WHERE n.codigoOceNavi = :codigoOceNavi"),
    @NamedQuery(name = "Naviera.findByEstadoNavi", query = "SELECT n FROM Naviera n WHERE n.estado = :estadoNavi"),
    @NamedQuery(name = "Naviera.findByCodigoCaeNavi", query = "SELECT n FROM Naviera n WHERE n.codigoCaeNavi = :codigoCaeNavi"),
    @NamedQuery(name = "Naviera.findByNombreNavi", query = "SELECT n FROM Naviera n WHERE n.nombreNavi = :nombreNavi"),
    @NamedQuery(name = "Naviera.findByCorreoNotifNavi", query = "SELECT n FROM Naviera n WHERE n.correoNotifNavi = :correoNotifNavi")})
public class Naviera implements Serializable {

    @Column(name = "perfilamiento_navi")
    private boolean perfilamientoNavi;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoNavi")
    private List<NavieraConsol> navieraConsolList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo_navi")
    private Long codigoNavi;
    @Column(name = "codigo_oce_navi")
    private String codigoOceNavi;
    @Column(name = "estado_navi")
    private String estado;
    @Column(name = "codigo_cae_navi")
    private String codigoCaeNavi;
    @Column(name = "nombre_navi")
    private String nombreNavi;
    @Column(name = "correo_notif_navi")
    private String correoNotifNavi;
    @OneToMany(mappedBy = "codigoNavi")
    private List<SUser> sUserList;
    @OneToMany(mappedBy = "idLineaPrec")
    private List<Precarga> precargaList;
    @Transient
    private String correo;

    public Naviera() {
    }

    public Naviera(Long codigoNavi) {
        this.codigoNavi = codigoNavi;
    }

    public Long getCodigoNavi() {
        return codigoNavi;
    }

    public void setCodigoNavi(Long codigoNavi) {
        this.codigoNavi = codigoNavi;
    }

    public String getCodigoOceNavi() {
        return codigoOceNavi;
    }

    public void setCodigoOceNavi(String codigoOceNavi) {
        this.codigoOceNavi = codigoOceNavi;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodigoCaeNavi() {
        return codigoCaeNavi;
    }

    public void setCodigoCaeNavi(String codigoCaeNavi) {
        this.codigoCaeNavi = codigoCaeNavi;
    }

    public String getNombreNavi() {
        return nombreNavi;
    }

    public void setNombreNavi(String nombreNavi) {
        this.nombreNavi = nombreNavi;
    }

    public String getCorreoNotifNavi() {
        return correoNotifNavi;
    }

    public void setCorreoNotifNavi(String correoNotifNavi) {
        this.correoNotifNavi = correoNotifNavi;
    }

    @XmlTransient
    public List<SUser> getSUserList() {
        return sUserList;
    }

    public void setSUserList(List<SUser> sUserList) {
        this.sUserList = sUserList;
    }

    @XmlTransient
    public List<Precarga> getPrecargaList() {
        return precargaList;
    }

    public void setPrecargaList(List<Precarga> precargaList) {
        this.precargaList = precargaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoNavi != null ? codigoNavi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Naviera)) {
            return false;
        }
        Naviera other = (Naviera) object;
        if ((this.codigoNavi == null && other.codigoNavi != null) || (this.codigoNavi != null && !this.codigoNavi.equals(other.codigoNavi))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Naviera[ codigoNavi=" + codigoNavi + " ]";
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        if(this.getSUserList()!=null){
            if(this.getSUserList().size()>0){
                correo=this.getSUserList().get(0).getCodigoSoli().getCorreoSoli();
            }
        }
        return correo;
    }

    /**
     * @return the perfilamientoNavi
     */
    public boolean getPerfilamientoNavi() {
        return perfilamientoNavi;
    }

    /**
     * @param perfilamientoNavi the perfilamientoNavi to set
     */
    public void setPerfilamientoNavi(boolean perfilamientoNavi) {
        this.perfilamientoNavi = perfilamientoNavi;
    }

    public void setPerfilamientoNavi(Boolean perfilamientoNavi) {
        this.perfilamientoNavi = perfilamientoNavi;
    }

    @XmlTransient
    public List<NavieraConsol> getNavieraConsolList() {
        return navieraConsolList;
    }

    public void setNavieraConsolList(List<NavieraConsol> navieraConsolList) {
        this.navieraConsolList = navieraConsolList;
    }
    
}
