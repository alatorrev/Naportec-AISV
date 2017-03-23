/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.aisv.logica;

import com.naportec.aisv.entidades.MovimientoVacio;
import com.naportec.aisv.entidades.Puerto;
import com.naportec.aisv.entidades.TransaccionVacios;
import com.naportec.seguridad.entidades.SUser;
import com.naportec.utilidades.enumeraciones.Estado;
import com.naportec.utilidades.logica.AbstractFacade;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author REY COMPUTER
 */
@Stateless
public class TransaccionVacioFacade extends AbstractFacade<TransaccionVacios> {

    private String ipTransaccion;
    private SUser usuario;
    
    
    @PersistenceContext(unitName = "com.naportec_Aisv")
    private EntityManager em;

    public TransaccionVacioFacade() {
        super(TransaccionVacios.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Puerto ObtenerPuerto(String nombre) {
        Query q = getEntityManager().createNamedQuery("Puerto.findByNombrePuer");
        q.setParameter("estado", Estado.Activo.name());
        q.setParameter("nombrePuer", nombre.toUpperCase());
        if (q.getResultList().size() > 0) {
            return (Puerto) q.getResultList().get(0);
        } else {
            return null;
        }
    }

     @Override
    public void eliminar(TransaccionVacios entity) {
        this.getEntityManager().merge(llenadoDatos(entity, Estado.Anulado.name().toUpperCase(), usuario.getUsrLoginname()));
        entity.getCodigoPrev().setEspaciosDisponiblesPrev(entity.getCodigoPrev().getEspaciosDisponiblesPrev() + 1);
        this.getEntityManager().merge(entity.getCodigoPrev());
        this.getEntityManager().merge(entity);
    }
    private MovimientoVacio llenadoDatos(TransaccionVacios t, String tipo, String usuario) {
        MovimientoVacio m = new MovimientoVacio();
        m.setCodigoTrav(t);
        m.setFechaMova(new Date());
        m.setTipoMova(tipo);
        m.setIpMova(getIpTransaccion());
        m.setDescripcionMova(usuario);
        return m;
    }
    
    
    /**
     * @return the ipTransaccion
     */
    public String getIpTransaccion() {
        return ipTransaccion;
    }

    /**
     * @param ipTransaccion the ipTransaccion to set
     */
    public void setIpTransaccion(String ipTransaccion) {
        this.ipTransaccion = ipTransaccion;
    }

    /**
     * @return the usuario
     */
    public SUser getUsuario() {
        if (usuario == null) {
            usuario = new SUser();
        }
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(SUser usuario) {
        this.usuario = usuario;
    }
}
