package com.naportec.aisv.logica;

import com.naportec.aisv.entidades.Itinerario;
import com.naportec.aisv.entidades.Naviera;
import com.naportec.aisv.entidades.PrecargaVacios;
import com.naportec.utilidades.controladores.Mensaje;
import com.naportec.utilidades.enumeraciones.Estado;
import com.naportec.utilidades.logica.AbstractFacade;
import com.naportec.utilidades.otros.Archivos;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Logica de Negocios de la entidad Naviera
 *
 * @author Fernando
 */
@Stateless
public class PrecargaVacioFacade extends AbstractFacade<PrecargaVacios> implements Serializable {

    /**
     * Inicialización de nuestro Recurso de Datos
     */
    @PersistenceContext(unitName = "com.naportec_Aisv")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public PrecargaVacioFacade() {
        super(PrecargaVacios.class);
    }

    /**
     * Método para guardar Precargas desde un archivo CSV
     *
     * @param inputstream
     * @param nombreArchivo
     * @param itinerario
     * @param naviera
     * @return
     * @throws IOException
     */
    public Map<Boolean, String> guardarDesdeArchivo(InputStream inputstream, String nombreArchivo, Itinerario itinerario, Naviera naviera) throws IOException {
        Map<Boolean, String> retornar = new HashMap<Boolean, String>();
        List<PrecargaVacios> listado = new LinkedList<>();
        if (Archivos.tomarExtension(nombreArchivo, 3).equals("CSV")) {
            listado = Archivos.leerCsvPrecargaVacio(inputstream);
        } else {
            retornar.put(Boolean.FALSE, "El Archivo debe ser CSV o EDI");
            return retornar;
        }
        try {
            this.setAutoCommit(true);
            for (PrecargaVacios p : listado) {
                PrecargaVacios busqueda = existeVacio(p);
                if (busqueda == null) {
                    p.setIdItinerarioPrev(itinerario);
                    p.setIdLineaPrev(naviera);
                    p.setEstado(Estado.Activo.name());
                    super.guardar(p);
                } else if (busqueda.getCantidadAisv() <= p.getTotalEspaciosPrev()) {
                    p.setCodigoPrev(busqueda.getCodigoPrev());
                    p.setIdItinerarioPrev(itinerario);
                    p.setIdLineaPrev(naviera);
                    p.setEstado(Estado.Activo.name());
                    this.modificar(p);
                }
            }
        } catch (Exception ex) {
            retornar.put(Boolean.FALSE, "ERROR:" + ex.getMessage() + ex.getCause());
            return retornar;
        }
        return null;
    }

    /**
     * Método para guardar Precarga
     *
     * @param entity
     */
    @Override
    public void guardar(PrecargaVacios entity) {
        if (entity.getIdLineaPrev() != null) {
            super.guardar(entity); //To change body of generated methods, choose Tools | Templates.
        } else {
            Mensaje.FATAL_DIALOG("Nueva Precarga", "La Linea es necesaria");
        }
    }

    /**
     * Método para eliminar Precarga
     *
     * @param entity
     */
    @Override
    public void eliminar(PrecargaVacios entity) {
        if (entity != null) {
            if (entity.getCantidadAisv() == 0) {
                entity.setEstado(Estado.Inactivo.name());
                super.modificar(entity); //To change body of generated methods, choose Tools | Templates.
                Mensaje.SUCESO("Eliminar Precarga","Se ha eliminado correctamente");
            } else {
                Mensaje.FATAL("Nueva Precarga", "Ya se han creado AISV de esta carga. Imposible Eliminar");
            }
        }
    }

    /**
     * Método para modificar datos de Precarga
     *
     * @param entity
     */
    @Override
    public void modificar(PrecargaVacios entity) {
        PrecargaVacios busqueda = existeVacio(entity);
        if (entity.getTotalEspaciosPrev() == 0) {
            entity.setTotalEspaciosPrev(1);
        }
        if (busqueda != null) {
            if (busqueda.getCantidadAisv() <= entity.getTotalEspaciosPrev()) {
                entity.setEspaciosDisponiblesPrev(entity.getTotalEspaciosPrev() - busqueda.getCantidadAisv());
                super.modificar(entity); //To change body of generated methods, choose Tools | Templates.
            } else {
                Mensaje.FATAL_DIALOG("Nueva Precarga", "Ya se han creado AISV de esta carga. Imposible Modificar");
            }
        }
    }

    /**
     * Método para saber si una Precarga de Vacio ya existe de acuerdo a su
     * numero de booking
     *
     * @param p
     * @return
     */
    private PrecargaVacios existeVacio(PrecargaVacios p) {
        Query q = getEntityManager().createNamedQuery("PrecargaVacios.findByBookingPrev");
        q.setParameter("bookingPrev", p.getBookingPrev());
        if (q.getResultList().size() > 0) {
            return (PrecargaVacios) q.getResultList().get(0);
        } else {
            return null;
        }
    }
    
}
