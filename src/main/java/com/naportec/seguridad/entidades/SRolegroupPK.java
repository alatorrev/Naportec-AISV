/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.naportec.seguridad.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Nestor
 */
@Embeddable
public class SRolegroupPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "grp_id")
    private int grpId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rol_id")
    private int rolId;

    public SRolegroupPK() {
    }

    public SRolegroupPK(int grpId, int rolId) {
        this.grpId = grpId;
        this.rolId = rolId;
    }

    public int getGrpId() {
        return grpId;
    }

    public void setGrpId(int grpId) {
        this.grpId = grpId;
    }

    public int getRolId() {
        return rolId;
    }

    public void setRolId(int rolId) {
        this.rolId = rolId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) grpId;
        hash += (int) rolId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SRolegroupPK)) {
            return false;
        }
        SRolegroupPK other = (SRolegroupPK) object;
        if (this.grpId != other.grpId) {
            return false;
        }
        if (this.rolId != other.rolId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.SRolegroupPK[ grpId=" + grpId + ", rolId=" + rolId + " ]";
    }
    
}
