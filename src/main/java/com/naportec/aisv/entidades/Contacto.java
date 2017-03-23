/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.naportec.aisv.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Fernando
 */
@Entity
@Table(name = "aisv_contacto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contacto.findAll", query = "SELECT c FROM Contacto c"),
    @NamedQuery(name = "Contacto.buscarPorSolicitud", query = "SELECT c FROM Contacto c WHERE c.codigoSoli=:soli and c.estado=:estado"),
    @NamedQuery(name = "Contacto.findByCodigoCont", query = "SELECT c FROM Contacto c WHERE c.codigoCont = :codigoCont"),
    @NamedQuery(name = "Contacto.findByNombreCont", query = "SELECT c FROM Contacto c WHERE c.nombreCont = :nombreCont"),
    @NamedQuery(name = "Contacto.findByEmailCont", query = "SELECT c FROM Contacto c WHERE c.emailCont = :emailCont"),
    @NamedQuery(name = "Contacto.findByTelefonoCont", query = "SELECT c FROM Contacto c WHERE c.telefonoCont = :telefonoCont"),
    @NamedQuery(name = "Contacto.findByCelularCont", query = "SELECT c FROM Contacto c WHERE c.celularCont = :celularCont"),
    @NamedQuery(name = "Contacto.findByEstadoCont", query = "SELECT c FROM Contacto c WHERE c.estado = :estado")})
public class Contacto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_cont")
    private Long codigoCont;
    @Size(max = 200)
    @Column(name = "nombre_cont")
    private String nombreCont;
    @Size(max = 250)
    @Column(name = "email_cont")
    private String emailCont;
    @Size(max = 20)
    @Column(name = "telefono_cont")
    private String telefonoCont;
    @Size(max = 20)
    @Column(name = "celular_cont")
    private String celularCont;
    @Column(name = "estado_cont")
    private String estado;
    @JoinColumn(name = "codigo_soli", referencedColumnName = "codigo_soli")
    @ManyToOne(optional = false)
    private Solicitud codigoSoli;

    public Contacto() {
    }

    public Contacto(Long codigoCont) {
        this.codigoCont = codigoCont;
    }

    public Contacto(Long codigoCont, String nombreCont, String emailCont, String telefonoCont, String celularCont, String estado) {
        this.codigoCont = codigoCont;
        this.nombreCont = nombreCont;
        this.emailCont = emailCont;
        this.telefonoCont = telefonoCont;
        this.celularCont = celularCont;
        this.estado = estado;
    }

    public Long getCodigoCont() {
        return codigoCont;
    }

    public void setCodigoCont(Long codigoCont) {
        this.codigoCont = codigoCont;
    }

    public String getNombreCont() {
        return nombreCont;
    }

    public void setNombreCont(String nombreCont) {
        this.nombreCont = nombreCont;
    }

    public String getEmailCont() {
        return emailCont;
    }

    public void setEmailCont(String emailCont) {
        this.emailCont = emailCont;
    }

    public String getTelefonoCont() {
        return telefonoCont;
    }

    public void setTelefonoCont(String telefonoCont) {
        this.telefonoCont = telefonoCont;
    }

    public String getCelularCont() {
        return celularCont;
    }

    public void setCelularCont(String celularCont) {
        this.celularCont = celularCont;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstadoCont(String estado) {
        this.estado = estado;
    }

    public Solicitud getCodigoSoli() {
        return codigoSoli;
    }

    public void setCodigoSoli(Solicitud codigoSoli) {
        this.codigoSoli = codigoSoli;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoCont != null ? codigoCont.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contacto)) {
            return false;
        }
        Contacto other = (Contacto) object;
        if ((this.codigoCont == null && other.codigoCont != null) || (this.codigoCont != null && !this.codigoCont.equals(other.codigoCont))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.naportec.aisv.entidades.Contacto[ codigoCont=" + codigoCont + " ]";
    }
    
}
