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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * @author Percho
 */
@Entity
@Table(name = "s_auditoria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SAuditoria.findAll", query = "SELECT s FROM SAuditoria s"),
    @NamedQuery(name = "SAuditoria.findByAudId", query = "SELECT s FROM SAuditoria s WHERE s.audId = :audId"),
    @NamedQuery(name = "SAuditoria.findByAudOperacion", query = "SELECT s FROM SAuditoria s WHERE s.audOperacion = :audOperacion"),
    @NamedQuery(name = "SAuditoria.findByAudData", query = "SELECT s FROM SAuditoria s WHERE s.audData = :audData"),
    @NamedQuery(name = "SAuditoria.findByAudUsuario", query = "SELECT s FROM SAuditoria s WHERE s.audUsuario = :audUsuario"),
    @NamedQuery(name = "SAuditoria.findByAudTabla", query = "SELECT s FROM SAuditoria s WHERE s.audTabla = :audTabla"),
    @NamedQuery(name = "SAuditoria.findByAudInformacion", query = "SELECT s FROM SAuditoria s WHERE s.audInformacion = :audInformacion")})
public class SAuditoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "aud_id")
    private Integer audId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "aud_operacion")
    private String audOperacion;
    @Column(name = "aud_data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audData;
    @Size(max = 50)
    @Column(name = "aud_usuario")
    private String audUsuario;
    @Size(max = 50)
    @Column(name = "aud_tabla")
    private String audTabla;
    @Size(max = 2147483647)
    @Column(name = "aud_informacion")
    private String audInformacion;
    @Column(name = "primaryKeyField")
    private String primaryKeyField;
    @Column(name = "primaryKeyValue")
    private String primaryKeyValue;
    @Column(name = "fieldName")
    private String fieldName;
    @Column(name = "oldValue")
    private String oldValue;
    @Column(name = "newValue")
    private String newValue;

    public SAuditoria() {
    }

    public SAuditoria(Integer audId) {
        this.audId = audId;
    }

    public SAuditoria(Integer audId, String audOperacion) {
        this.audId = audId;
        this.audOperacion = audOperacion;
    }

    public Integer getAudId() {
        return audId;
    }

    public void setAudId(Integer audId) {
        this.audId = audId;
    }

    public String getAudOperacion() {
        return audOperacion;
    }

    public void setAudOperacion(String audOperacion) {
        this.audOperacion = audOperacion;
    }

    public Date getAudData() {
        return audData;
    }

    public void setAudData(Date audData) {
        this.audData = audData;
    }

    public String getAudUsuario() {
        return audUsuario;
    }

    public void setAudUsuario(String audUsuario) {
        this.audUsuario = audUsuario;
    }

    public String getAudTabla() {
        return audTabla;
    }

    public void setAudTabla(String audTabla) {
        this.audTabla = audTabla;
    }

    public String getAudInformacion() {
        return audInformacion;
    }

    public void setAudInformacion(String audInformacion) {
        this.audInformacion = audInformacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (audId != null ? audId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SAuditoria)) {
            return false;
        }
        SAuditoria other = (SAuditoria) object;
        if ((this.audId == null && other.audId != null) || (this.audId != null && !this.audId.equals(other.audId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.es.sess.backend.model.SAuditoria[ audId=" + audId + " ]";
    }

    /**
     * @return the primaryKeyField
     */
    public String getPrimaryKeyField() {
        return primaryKeyField;
    }

    /**
     * @param primaryKeyField the primaryKeyField to set
     */
    public void setPrimaryKeyField(String primaryKeyField) {
        this.primaryKeyField = primaryKeyField;
    }

    /**
     * @return the primaryKeyValue
     */
    public String getPrimaryKeyValue() {
        return primaryKeyValue;
    }

    /**
     * @param primaryKeyValue the primaryKeyValue to set
     */
    public void setPrimaryKeyValue(String primaryKeyValue) {
        this.primaryKeyValue = primaryKeyValue;
    }

    /**
     * @return the fieldName
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * @param fieldName the fieldName to set
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * @return the oldValue
     */
    public String getOldValue() {
        return oldValue;
    }

    /**
     * @param oldValue the oldValue to set
     */
    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    /**
     * @return the newValue
     */
    public String getNewValue() {
        return newValue;
    }

    /**
     * @param newValue the newValue to set
     */
    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

}
