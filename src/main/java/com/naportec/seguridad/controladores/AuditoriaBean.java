
package com.naportec.seguridad.controladores;

import com.naportec.seguridad.entidades.SAuditoria;
import com.naportec.seguridad.logica.SAuditoriaFacade;
import com.naportec.utilidades.controladores.Mensaje;
import com.naportec.utilidades.controladores.UtilController;
import com.naportec.utilidades.logica.Filtro;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import net.sf.jasperreports.engine.JRException;

/**
 * Clase controlador que nso permite manejar las acciones de la vista de auditoria
 * @author Fernando
 */
public class AuditoriaBean extends UtilController<SAuditoria> {

    private Date inicio;
    private Date fin;
    @EJB
    private SAuditoriaFacade auditoriaLogica;


    public AuditoriaBean() {
        super(SAuditoria.class);
    }

    @PostConstruct
    public void inicializar() {
        this.setEntidadLogica(auditoriaLogica);
    }

    /**
     * Método para mostrar el listado de auditoria entre fechas
     */
    public void buscar() {
        this.getListadoEntidad().setFiltros(new LinkedList<Filtro>());
        this.getListadoEntidad().filtroBetweenFecha("audData", inicio, "otra", fin);
    }

    /**
     * Método que prepara lo necesario para exportar un reporte en PDF o EXCEL
     */
    public void prepararExportacion() {
        try {
            String rutaReporte="/seguridad/auditoria/reportes/rptAuditoria.jasper";
            this.inicializarReporte(null,this.getListadoEntidad().loadTotalList(),rutaReporte);
        } catch (JRException ex) {
            Mensaje.ERROR("No se ha podido acceder al Reporte");
        } catch (Exception ex) {
            Logger.getLogger(AuditoriaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * @return the inicio
     */
    public Date getInicio() {
        return inicio;
    }

    /**
     * @param inicio the inicio to set
     */
    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    /**
     * @return the fin
     */
    public Date getFin() {
        return fin;
    }

    /**
     * @param fin the fin to set
     */
    public void setFin(Date fin) {
        this.fin = fin;
    }

}
