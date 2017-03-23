/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.seguridad.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Percho
 */
@Entity
@Table(name = "s_parameter")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SParameter.findAll", query = "SELECT s FROM SParameter s"),
    @NamedQuery(name = "SParameter.findByParamId", query = "SELECT s FROM SParameter s WHERE s.paramId = :paramId"),
    @NamedQuery(name = "SParameter.findByParamTamMin", query = "SELECT s FROM SParameter s WHERE s.paramTamMin = :paramTamMin"),
    @NamedQuery(name = "SParameter.findByParamTamMax", query = "SELECT s FROM SParameter s WHERE s.paramTamMax = :paramTamMax"),
    @NamedQuery(name = "SParameter.findByParamCantNumeros", query = "SELECT s FROM SParameter s WHERE s.paramCantNumeros = :paramCantNumeros"),
    @NamedQuery(name = "SParameter.findByParamCantLetras", query = "SELECT s FROM SParameter s WHERE s.paramCantLetras = :paramCantLetras"),
    @NamedQuery(name = "SParameter.findByParamCantEspeciales", query = "SELECT s FROM SParameter s WHERE s.paramCantEspeciales = :paramCantEspeciales"),
    @NamedQuery(name = "SParameter.findByParamCaducidadClave", query = "SELECT s FROM SParameter s WHERE s.paramCaducidadClave = :paramCaducidadClave")})
public class SParameter implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "param_id", nullable = false)
    private Integer paramId;
    @Column(name = "param_tam_min")
    private Integer paramTamMin;
    @Column(name = "param_tam_max")
    private Integer paramTamMax;
    @Column(name = "param_cant_numeros")
    private Integer paramCantNumeros;
    @Column(name = "param_cant_letras")
    private Integer paramCantLetras;
    @Column(name = "param_cant_especiales")
    private Integer paramCantEspeciales;
    @Column(name = "param_caducidad_clave")
    private Integer paramCaducidadClave;
    @Transient
    private int numParametros;

    public SParameter() {
    }

    public SParameter(Integer paramId) {
        this.paramId = paramId;
    }

    public Integer getParamId() {
        return paramId;
    }

    public void setParamId(Integer paramId) {
        this.paramId = paramId;
    }

    public Integer getParamTamMin() {
        return paramTamMin;
    }

    public void setParamTamMin(Integer paramTamMin) {
        this.paramTamMin = paramTamMin;
    }

    public Integer getParamTamMax() {
        return paramTamMax;
    }

    public void setParamTamMax(Integer paramTamMax) {
        this.paramTamMax = paramTamMax;
    }

    public Integer getParamCantNumeros() {
        return paramCantNumeros;
    }

    public void setParamCantNumeros(Integer paramCantNumeros) {
        this.paramCantNumeros = paramCantNumeros;
    }

    public Integer getParamCantLetras() {
        return paramCantLetras;
    }

    public void setParamCantLetras(Integer paramCantLetras) {
        this.paramCantLetras = paramCantLetras;
    }

    public Integer getParamCantEspeciales() {
        return paramCantEspeciales;
    }

    public void setParamCantEspeciales(Integer paramCantEspeciales) {
        this.paramCantEspeciales = paramCantEspeciales;
    }

    public Integer getParamCaducidadClave() {
        return paramCaducidadClave;
    }

    public void setParamCaducidadClave(Integer paramCaducidadClave) {
        this.paramCaducidadClave = paramCaducidadClave;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paramId != null ? paramId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SParameter)) {
            return false;
        }
        SParameter other = (SParameter) object;
        if ((this.paramId == null && other.paramId != null) || (this.paramId != null && !this.paramId.equals(other.paramId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.SParameter[ paramId=" + paramId + " ]";
    }

    /**
     * @return the numParametros
     */
    public int getNumParametros() {
        numParametros=0;
        if(paramCantEspeciales!=null){
            numParametros+=paramCantEspeciales;
        }
        if(paramCantLetras!=null){
            numParametros+=paramCantLetras;
        }
        if(paramCantNumeros!=null){
            numParametros+=paramCantNumeros;
        }
        return numParametros;
    }

}
