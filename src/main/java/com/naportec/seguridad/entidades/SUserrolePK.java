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
public class SUserrolePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "usr_id")
    private long usrId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rol_id")
    private int rolId;

    public SUserrolePK() {
    }

    public SUserrolePK(long usrId, int rolId) {
        this.usrId = usrId;
        this.rolId = rolId;
    }

    public long getUsrId() {
        return usrId;
    }

    public void setUsrId(long usrId) {
        this.usrId = usrId;
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
        hash += (int) usrId;
        hash += (int) rolId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SUserrolePK)) {
            return false;
        }
        SUserrolePK other = (SUserrolePK) object;
        if (this.usrId != other.usrId) {
            return false;
        }
        if (this.rolId != other.rolId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.SUserrolePK[ usrId=" + usrId + ", rolId=" + rolId + " ]";
    }
    
}
