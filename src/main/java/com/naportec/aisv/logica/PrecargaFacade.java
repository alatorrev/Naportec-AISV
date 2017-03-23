package com.naportec.aisv.logica;

import com.naportec.aisv.entidades.Itinerario;
import com.naportec.aisv.entidades.Naviera;
import com.naportec.aisv.entidades.Precarga;
import com.naportec.aisv.entidades.Transaccion;
import com.naportec.utilidades.controladores.Mensaje;
import com.naportec.utilidades.controladores.TablaPrecarga;
import com.naportec.utilidades.enumeraciones.Estado;
import com.naportec.utilidades.logica.AbstractFacade;
import com.naportec.utilidades.logica.Consulta;
import com.naportec.utilidades.logica.Filtro;
import com.naportec.utilidades.otros.Archivos;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.primefaces.model.SortOrder;
import org.xml.sax.SAXException;

/**
 * Logica de Negocios de la entidad Naviera
 *
 * @author Fernando
 */
@Stateless
public class PrecargaFacade extends AbstractFacade<Precarga> implements Serializable {

    /**
     * Inicialización de nuestro Recurso de Datos
     */
    @PersistenceContext(unitName = "com.naportec_Aisv")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrecargaFacade() {
        super(Precarga.class);
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
        List<Precarga> listado = new LinkedList<>();
        if (Archivos.tomarExtension(nombreArchivo, 3).equals("EDI")) {
            try {
                listado = Archivos.leerEDIPrecarga(inputstream);
            } catch (SAXException ex) {
                Logger.getLogger(PrecargaFacade.class.getName()).log(Level.SEVERE, null, ex);
                retornar.put(Boolean.FALSE, "ERROR:" + ex.getMessage());
                return retornar;
            }
        } else if (Archivos.tomarExtension(nombreArchivo, 3).equals("CSV")) {
            listado = Archivos.leerCsvPrecarga(inputstream);
        } else {
            retornar.put(Boolean.FALSE, "El Archivo debe ser CSV o EDI");
            return retornar;
        }
        try {
            this.setAutoCommit(true);
            for (Precarga p : listado) {
                Precarga busqueda = existeExpo(p);
                if (busqueda == null) {
                    p.setIdItinerarioPrec(itinerario);
                    p.setIdLineaPrec(naviera);
                    p.setEstado(Estado.Activo.name());
                    p.setTipoPrec("E");//indica Exportacion
                    this.guardar(p);
                } else if (busqueda.getCantidadAisv() <= p.getTotalEspaciosPrec()) {
                    p.setCodigoPrec(busqueda.getCodigoPrec());
                    p.setIdItinerarioPrec(itinerario);
                    p.setIdLineaPrec(naviera);
                    p.setEstado(Estado.Activo.name());
                    p.setTipoPrec("E");//indica Exportacion
                    this.modificar(p);
                }
            }
        } catch (Exception ex) {
            retornar.put(Boolean.FALSE, "ERROR:" + ex.getMessage());
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
    public void guardar(Precarga entity) {
        entity.setTipoPrec("E");
        super.guardar(entity); //To change body of generated methods, choose Tools | Templates.
    }

     /**
     * Método para eliminar Precarga
     *
     * @param entity
     */
    @Override
    public void eliminar(Precarga entity) {
        if (existeAISV(entity) == null) {
            super.eliminar(entity);
            Mensaje.SUCESO_DIALOG("Eliminar Precarga", "Se ha Eliminado el Booking Satisfactoriamente");
        } else {
            entity.setEstado(Estado.Anulado.name());
            super.modificar(entity);
            Mensaje.SUCESO_DIALOG("Eliminar Precarga", "Se ha Actualizado el estado del Booking Satisfactoriamente");
        }
    }

    /**
     * Método para modificar datos de Precarga
     *
     * @param entity
     */
    @Override
    public void modificar(Precarga entity) {
        Precarga busqueda = existeExpo(entity);
        if (entity.getTotalEspaciosPrec() == 0) {
            entity.setTotalEspaciosPrec(1);
        }
        if (busqueda != null) {
            if (busqueda.getCantidadAisv() <= entity.getTotalEspaciosPrec()) {
                entity.setEspaciosDisponiblesPrec(entity.getTotalEspaciosPrec() - busqueda.getCantidadAisv());
                super.modificar(entity); //To change body of generated methods, choose Tools | Templates.
            } else {
                Mensaje.FATAL_DIALOG("Nueva Precarga", "Ya se han creado AISV de esta carga. Imposible Modificar");
            }
        }
    }

    /**
     * Método para saber si una Precarga ya existe de acuerdo a su numero de
     * booking
     *
     * @param p
     * @return
     */
    private Precarga existeExpo(Precarga p) {
        Query q = getEntityManager().createNamedQuery("Precarga.findByBookingPrec");
        q.setParameter("bookingPrec", p.getBookingPrec());
        if (q.getResultList().size() > 0) {
            return (Precarga) q.getResultList().get(0);
        } else {
            return null;
        }
    }

    /**
     * Método para saber si se han creado AISVs desde la Precarga
     *
     * @param p
     * @return
     */
    private Precarga existeAISV(Precarga p) {
        Query q = getEntityManager().createNamedQuery("Transaccion.findByPrecarga");
        q.setParameter("codigoPrec", p);
        if (q.getResultList().size() > 0) {
            return ((Transaccion) q.getResultList().get(0)).getCodigoPrec();
        } else {
            return null;
        }
    }

//    public List<Precarga> loadData(int first, int count,String jpql,String jpqlUno,String jpqlDos,String jpqlTres,String tipoPerfil, Map<String, Object> parameters) throws Exception {
//        List<Precarga> datos = new LinkedList<>();
//        
//        
//        return datos;
//    }
    public TablaPrecarga loadData(int first, int count, String jpql, String jpqlUno, String jpqlDos, String jpqlTres, String tipoPerfil, Map<String, Object> parameters) throws Exception {
        TablaPrecarga tp = new TablaPrecarga();
        Query q = getEntityManager().createQuery(jpql);
        Set<Map.Entry<String, Object>> entries = parameters.entrySet();
        for (Map.Entry<String, Object> filter : entries) {
            if (filter.getValue().toString().trim().length() != 0) {
                q.setParameter(filter.getKey(), filter.getValue());
            }
        }
        q.setFirstResult(first);
        q.setMaxResults(count);

        tp.setListado(q.getResultList());

        if (tipoPerfil.trim().equals("IE")) {
            Query countQueryUno = getEntityManager().createQuery(jpqlUno.replace("SELECT p", "SELECT Count(p)"));
            Query countQueryDos = getEntityManager().createQuery(jpqlDos.replace("SELECT q", "SELECT Count(q)"));
            Query countQueryTres = getEntityManager().createQuery(jpqlTres.replace("SELECT r", "SELECT Count(r)"));
            Set<Map.Entry<String, Object>> en = parameters.entrySet();
            for (Map.Entry<String, Object> filter : en) {
                if (filter.getValue().toString().trim().length() != 0) {
                    countQueryDos.setParameter(filter.getKey(), filter.getValue());
                    if (!filter.getKey().equals("impExpIdPrec")) {
                        countQueryUno.setParameter(filter.getKey(), filter.getValue());
                        countQueryTres.setParameter(filter.getKey(), filter.getValue());
                    }
                }
            }
            long c1=(long) countQueryUno.getSingleResult();
            long c2=(long) countQueryDos.getSingleResult();
            long c3=(long) countQueryTres.getSingleResult();
            tp.setCount((int) (c1+c2+c3));

        } else {
            StringBuilder countJpql = new StringBuilder();
            countJpql.append(jpql.replace("SELECT p", "SELECT Count(p)")).append("");
            Query countQuery = getEntityManager().createQuery(countJpql.toString());
            Set<Map.Entry<String, Object>> en = parameters.entrySet();
            for (Map.Entry<String, Object> filter : en) {
                if (filter.getValue().toString().trim().length() != 0) {
                    countQuery.setParameter(filter.getKey(), filter.getValue());
                }
            }
            tp.setCount(Integer.parseInt(countQuery.getSingleResult().toString()));
        }

        return tp;
    }

}
