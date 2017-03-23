/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.aisv.logica;

import com.naportec.aisv.entidades.Pagos;
import com.naportec.utilidades.logica.AbstractFacade;
import java.io.Serializable;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author MZambrano1
 */
@Stateless
public class PagosFacade extends AbstractFacade<Pagos> implements Serializable {

    public PagosFacade() {
        super(Pagos.class);
    }

    @PersistenceContext(unitName = "com.naportec_Aisv")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
