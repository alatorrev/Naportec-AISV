package com.naportec.utilidades.logica;

import com.naportec.utilidades.enumeraciones.Estado;
import com.naportec.utilidades.mail.UtilMail;
import java.io.Serializable;
import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.EJBException;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.eclipse.persistence.internal.jpa.EntityManagerImpl;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.sessions.Session;
import org.primefaces.model.SortOrder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Clase que nos permite realizar el CRUD sobre entidades JPA
 *
 * @author Fernando
 * @param <T>
 */
public abstract class AbstractFacade<T> implements Serializable {

    private Class<T> entityClass;
    private int countFiltro;
    private boolean autoCommit;
    private boolean entityWithState;
    private String currentloggeduser;
    private FacesContext contexto;
    public UtilMail email;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            this.setCurrentloggeduser(auth.getName());
        }
        autoCommit = true;
        entityWithState = true;
        email = new UtilMail();
    }

    /**
     * Método para obetner ruta completa de un archivo en el servidor
     *
     * @param rutaArchivo
     * @return
     */
    public String obtenerArchivos(String rutaArchivo) {
        return this.getContexto().getExternalContext().getRealPath(rutaArchivo);
    }

    protected abstract EntityManager getEntityManager();

    public Connection getConnection() {
        Connection con = getEntityManager().unwrap(JpaEntityManager.class).getServerSession().getAccessor().getConnection();
      // return ((EntityManagerImpl) (getEntityManager().getDelegate())).getServerSession().getReadConnectionPool().acquireConnection().getConnection();
      return con;
    }

    /**
     * Método para guardar una entidad JPA
     *
     * @param entity
     * @throws EJBException
     */
    public void guardar(T entity) throws EJBException {
        getEntityManager().persist(entity);
    }

    /**
     * Método para modificar una entidad JPA
     *
     * @param entity
     * @throws EJBException
     */
    public void modificar(T entity) throws EJBException {
        getEntityManager().merge(entity);
    }

    /**
     * Método para eliminar JPA
     *
     * @param entity
     * @throws EJBException
     */
    public void eliminar(T entity) throws EJBException {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    /**
     * Método para buscar una entidad JPA por su primary key
     *
     * @param id
     * @return
     * @throws EJBException
     */
    public T buscarPorCodigo(Object id) throws EJBException {
        return getEntityManager().find(getEntityClass(), id);
    }

    /**
     * Método para listar todos lso datos de una entidad JPA
     *
     * @return
     * @throws EJBException
     */
    public List<T> listarTodos() throws EJBException {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(getEntityClass()));
        return getEntityManager().createQuery(cq).getResultList();
    }

    /**
     * Método para listar por estado todos los datos de uan entidad JPA
     *
     * @param estado
     * @return
     * @throws EJBException
     */
    public List<T> listarPorEstado(Estado estado) throws EJBException {
        if (entityWithState) {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<T> q = cb.createQuery(getEntityClass());
            Root<T> c = q.from(getEntityClass());
            List<Predicate> lista = new LinkedList<Predicate>();
            lista.add(cb.equal(c.get("estado"), estado.name()));
            q.select(c).where(lista.<Predicate>toArray(
                    new Predicate[lista.size()]));
            return getEntityManager().createQuery(q).getResultList();
        } else {
            return new LinkedList<>();
        }
    }

    /**
     * Método para utilizar una datatable de primefaces con lazy loading
     *
     * @param first
     * @param count
     * @param sortField
     * @param sortOrder
     * @param filters
     * @param filtros
     * @return
     * @throws Exception
     */
    public List<T> load(int first, int count, String sortField, SortOrder sortOrder, Map<String, Object> filters, List<Filtro> filtros) throws Exception {
        List<T> datos = new LinkedList<T>();
        List<Filtro> filtrados = new LinkedList<Filtro>();
        filtrados.addAll(filtros);
        System.out.println("TIEMPO 1: " + System.currentTimeMillis());
        if (filters != null) {
            Set<Map.Entry<String, Object>> entries = filters.entrySet();
            for (Map.Entry<String, Object> filter : entries) {
                if (filter.getValue().toString().trim().length() != 0) {
                    filtrados.add(new Filtro("LIKE", filter.getKey(), filter.getValue()));
                }
            }
        }
        Consulta cq = new Consulta(getEntityClass().getSimpleName());
        cq.agregarFiltrado(filtrados);
        System.out.println("TIEMPO 2: " + System.currentTimeMillis());
        datos = cq.crearConsulta(getEntityManager(), first, count).getResultList();
        System.out.println("TIEMPO 3: " + System.currentTimeMillis());
        countFiltro = cq.getFilas();
        System.out.println("TIEMPO 4: " + System.currentTimeMillis());
        return datos;
    }

    public Object[] loadLazy(int first, int count, String sortField, SortOrder sortOrder, Map<String, Object> filters, List<Filtro> filtros) throws Exception {
        List<T> datos = new LinkedList<T>();
        List<Filtro> filtrados = new LinkedList<Filtro>();
        filtrados.addAll(filtros);
        System.out.println("TIEMPO 1: " + System.currentTimeMillis());
        if (filters != null) {
            Set<Map.Entry<String, Object>> entries = filters.entrySet();
            for (Map.Entry<String, Object> filter : entries) {
                if (filter.getValue().toString().trim().length() != 0) {
                    filtrados.add(new Filtro("LIKE", filter.getKey(), filter.getValue()));
                }
            }
        }
        Consulta cq = new Consulta(getEntityClass().getSimpleName());
        cq.agregarFiltrado(filtrados);
//      System.out.println("TIEMPO 2: "+System.currentTimeMillis());
//        datos = cq.crearConsulta(getEntityManager(), first, count).getResultList();
//      System.out.println("TIEMPO 3: "+System.currentTimeMillis());
//        countFiltro = cq.getFilas();
//      System.out.println("TIEMPO 4: " + System.currentTimeMillis());
        return cq.generarLazyQuery(getEntityManager(), first, count);
    }

    /**
     * Método para selecionar todos los registros que exsiten de una carga
     * liviana
     *
     * @param filters
     * @param filtros
     * @return
     * @throws Exception
     */
    public List<T> loadFull(Map<String, Object> filters, List<Filtro> filtros) throws Exception {
        List<T> datos = new LinkedList<T>();
        List<Filtro> filtrados = new LinkedList<Filtro>();
        filtrados.addAll(filtros);
        if (filters != null) {
            Set<Map.Entry<String, Object>> entries = filters.entrySet();
            for (Map.Entry<String, Object> filter : entries) {
                if (filter.getValue().toString().trim().length() != 0) {
                    filtrados.add(new Filtro("LIKE", filter.getKey(), filter.getValue()));
                }
            }
        }
        Consulta cq = new Consulta(getEntityClass().getSimpleName());
        cq.agregarFiltrado(filtrados);
        datos = cq.crearConsulta(getEntityManager()).getResultList();
        countFiltro = cq.getFilas();
        return datos;
    }

    /**
     * Método para obtener lso filtros desde el datatable primefaces
     *
     * @param filters
     * @param filtros
     * @return
     */
    public List<Filtro> load(Map<String, Object> filters, List<Filtro> filtros) {
        List<Filtro> filtrados = new LinkedList<Filtro>();
        filtrados.addAll(filtros);
        if (filters != null) {
            Set<Map.Entry<String, Object>> entries = filters.entrySet();
            for (Map.Entry<String, Object> filter : entries) {
                if (filter.getValue().toString().trim().length() != 0) {
                    filtrados.add(new Filtro("LIKE", filter.getKey(), filter.getValue()));
                }
            }
        }
        return filtrados;
    }

    /**
     * @return the count
     */
    public int getCountFiltro() {
        return countFiltro;
    }

    /**
     * @param count the count to set
     */
    public void setCountFiltro(int countFiltro) {
        this.countFiltro = countFiltro;
    }

    /**
     * @return the autoCommit
     */
    public boolean getAutoCommit() {
        return autoCommit;
    }

    /**
     * @param autoCommit the autoCommit to set
     */
    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

    /**
     * @return the entityWithState
     */
    public boolean getEntityWithState() {
        return entityWithState;
    }

    /**
     * @param entityWithState the entityWithState to set
     */
    public void setEntityWithState(boolean entityWithState) {
        this.entityWithState = entityWithState;
    }

    /**
     * @return the currentloggeduser
     */
    public String getCurrentloggeduser() {
        return currentloggeduser;
    }

    /**
     * @param currentloggeduser the currentloggeduser to set
     */
    public void setCurrentloggeduser(String currentloggeduser) {
        this.currentloggeduser = currentloggeduser;
    }

    /**
     * @return the entityClass
     */
    public Class<T> getEntityClass() {
        return entityClass;
    }

    /**
     * @return the contexto
     */
    public FacesContext getContexto() {
        return contexto;
    }

    /**
     * @param contexto the contexto to set
     */
    public void setContexto(FacesContext contexto) {
        this.contexto = contexto;
    }
}
