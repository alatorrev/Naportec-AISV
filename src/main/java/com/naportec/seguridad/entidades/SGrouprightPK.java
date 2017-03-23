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
public class SGrouprightPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "grp_id")
    private int grpId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rig_id")
    private int rigId;

    public SGrouprightPK() {
    }

    public SGrouprightPK(int grpId, int rigId) {
        this.grpId = grpId;
        this.rigId = rigId;
    }

    public int getGrpId() {
        return grpId;
    }

    public void setGrpId(int grpId) {
        this.grpId = grpId;
    }

    public int getRigId() {
        return rigId;
    }

    public void setRigId(int rigId) {
        this.rigId = rigId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) grpId;
        hash += (int) rigId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SGrouprightPK)) {
            return false;
        }
        SGrouprightPK other = (SGrouprightPK) object;
        if (this.grpId != other.grpId) {
            return false;
        }
        if (this.rigId != other.rigId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.SGrouprightPK[ grpId=" + grpId + ", rigId=" + rigId + " ]";
    }
    
}
