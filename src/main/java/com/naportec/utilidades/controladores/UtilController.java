/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.utilidades.controladores;

import com.naportec.aisv.entidades.AisvListadoBookings;
import com.naportec.aisv.entidades.Pagos;
import com.naportec.aisv.entidades.Transaccion;
import com.naportec.utilidades.enumeraciones.Estado;
import com.naportec.utilidades.logica.AbstractFacade;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.event.SelectEvent;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

import org.primefaces.model.StreamedContent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Clase abstracta que contiene información similar para varios controladores y
 * nos permite manejar los metodos basicos de guardado, eliminado y modificado.
 *
 * @author Fernando
 * @param <T>
 */
public class UtilController<T> implements Serializable {

    private String rutaReporteServidor;
    /**
     * Este atributo sirve para habilitar o desabilitar boton nuevo
     */
    private boolean botonNuevo;
    private boolean botonGuardar;
    private boolean botonEditar;
    private boolean botonBloquear;
    private boolean botonEliminar;
    /**
     * Este atributo permite saber si se guardara o se editará un registro
     * Guardar=0, Editar=1
     */
    private int guardarEdicion;
    /**
     * Indica si los datos de la entidad son solo lectura. Por defecto es false
     */
    protected boolean readOnly = false;
    /**
     * Entidad es la entidad o el objeto al cual se aplicara el CRUD
     */
    protected T entidad;
    /**
     * Es el nombre que se le dara al acrhivo de Exel o PDF el cual sera
     * descargado por el cliente web
     */
    private String nombreArchivo;
    /**
     * Aqui el LazyDataModel que se usara para los listados de tipo Lazy
     */
    protected LazyDataModelAdvance<T> listadoEntidad;
    /**
     * Aqui colocamos el AbstractFacade el cual necesitaremos para realizar el
     * CRUD y las consultas
     */
    private AbstractFacade<T> entidadLogica;
    /**
     * StreamContent nos permite relizar descargas hacia el cliente web
     */
    protected StreamedContent archivoDescarga;
    /**
     * Class<T> nos permite tener la clase generica para poder realizar varias
     * acciones y poder usar reflexion
     */
    private Class<T> claseEntidad;

    /**
     *
     */
    private JasperPrint jasper;
    /**
     *
     * @param claseEntidad
     */
    private String currentloggeduser;

    public UtilController(Class<T> claseEntidad) {
        //get logged in username
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        this.setCurrentloggeduser(auth.getName());
        this.claseEntidad = claseEntidad;
        this.listadoEntidad = new LazyDataModelAdvance<T>();
    }

    public UtilController() {
    }

    /**
     * Método para incializar reporte
     *
     * @param parametros
     * @param listadoObjetos
     * @param ruta
     * @throws JRException
     */
    protected void inicializarReporte(Map parametros, List<T> listadoObjetos, String ruta) throws JRException {
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listadoObjetos);
        String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(ruta);
        jasper = JasperFillManager.fillReport(reportPath, parametros, beanCollectionDataSource);
//        Connection con=null;

//        jasper = JasperFillManager.fillReport(reportPath, parametros,con);
    }

    protected void inicializarReporte(Map parametros, List<T> listadoObjetos, String ruta, Connection con) throws JRException {
//        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listadoObjetos);
        String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(ruta);
//        jasper = JasperFillManager.fillReport(reportPath, parametros, beanCollectionDataSource);

        jasper = JasperFillManager.fillReport(reportPath, parametros, con);
    }

    /**
     * Método para incializar reporte
     *
     * @param parametros
     * @param listadoObjetos
     * @param ruta
     * @throws JRException
     */
    protected void inicializarReporteObject(Map parametros, List<AisvListadoBookings> listadoObjetos, String ruta) throws JRException {
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listadoObjetos);
        String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(ruta);
        jasper = JasperFillManager.fillReport(reportPath, parametros, beanCollectionDataSource);
    }

    /**
     * Método para incializar reporte
     *
     * @param parametros
     * @param listadoObjetos
     * @param ruta
     * @param dato
     * @throws JRException
     */
    protected void inicializarReporte(Map parametros, List<T> listadoObjetos, String ruta, int dato) throws JRException {
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listadoObjetos);
        String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(ruta);
        String serverPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/temporal/tmp.txt");
        jasper = JasperFillManager.fillReport(reportPath, parametros, beanCollectionDataSource);
        this.rutaReporteServidor = "/temporal/tmp.txt" + ((Transaccion) listadoObjetos.get(0)).getCodigoTrans() + ".pdf";
        JasperExportManager.exportReportToPdfFile(jasper, serverPath + ((Transaccion) listadoObjetos.get(0)).getCodigoTrans() + ".pdf");
        jasper = null;
        beanCollectionDataSource = null;
        listadoObjetos = null;
    }

    /**
     * Metodo para inicializar la entidad
     *
     * @param evt
     */
    public void prepararCrear(ActionEvent evt) {
        try {
            this.entidad = this.claseEntidad.newInstance();
            this.nuevoClick();
        } catch (InstantiationException ex) {
            Logger.getLogger(UtilController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(UtilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método para cuando se selecciona para editar una entidad
     *
     * @param evt
     */
    public void seleccionarEditar(SelectEvent evt) {
        this.guardarEdicion = 1;
        this.entidad = (T) evt.getObject();
        this.dobleClick();
        this.botonBloquear = true;
    }

    /**
     * Preparar para editar una entidad
     *
     * @param evt
     */
    public void prepararEditar(ActionEvent evt) {
        this.nuevoClick();
        this.guardarEdicion = 1;
        this.botonBloquear = false;
    }

    /**
     * Bloquedar los textos y componentes de la pantalla
     *
     * @param evt
     */
    public void bloquearEntidad(ActionEvent evt) {
        this.dobleClick();
        this.botonBloquear = true;
    }

    /**
     * Método para guardar la entidad
     *
     * @param evt
     */
    public void guardarEntidad(ActionEvent evt) {
        try {
            if (this.guardarEdicion == 0) {
                if (!this.entidad.getClass().equals(Pagos.class)) {
                    Method m = this.entidad.getClass().getMethod("setEstado", new Class[]{String.class});
                    m.invoke(this.entidad, new Object[]{Estado.Activo.name()});
                }
                entidadLogica.guardar(this.entidad);
                Mensaje.SUCESO("Se ha guardado Correctamente en " + this.claseEntidad.getSimpleName());
            } else {
                entidadLogica.modificar(this.entidad);
                Mensaje.SUCESO("Se ha modificado Correctamente en " + this.claseEntidad.getSimpleName());
            }
            this.entidad = claseEntidad.newInstance();
            this.nuevoClick();
        } catch (Exception ex) {
            Mensaje.ERROR("No se ha podido guardar " + this.claseEntidad.getSimpleName() + " por el sigueinte ERROR: " + ex.getMessage());
        }
    }

    /**
     * Método para modificar une entidad
     *
     * @param evt
     */
    public void modificarEntidad(ActionEvent evt) {
        try {
            entidadLogica.modificar(this.entidad);
            Mensaje.SUCESO("Se ha modificado Correctamente en " + this.claseEntidad.getSimpleName());
            this.entidad = claseEntidad.newInstance();
            this.nuevoClick();
        } catch (Exception ex) {
            Mensaje.ERROR("No se ha podido modificar " + this.claseEntidad.getSimpleName() + " por el sigueinte ERROR: " + ex.getMessage());
        }
    }

    /**
     * Método para eliminar una entidad
     *
     * @param evt
     */
    public void eliminarEntidad(ActionEvent evt) {
        try {
            Method m = this.entidad.getClass().getMethod("setEstado", new Class[]{String.class});
            m.invoke(this.entidad, new Object[]{Estado.Inactivo.name()});
            entidadLogica.modificar(this.entidad);
            Mensaje.SUCESO("Se ha eliminado Correctamente en " + this.claseEntidad.getSimpleName());
            this.entidad = claseEntidad.newInstance();
            this.nuevoClick();
        } catch (Exception ex) {
            Mensaje.ERROR("No se ha podido eliminar " + this.claseEntidad.getSimpleName() + " por el sigueinte ERROR: " + ex.getMessage());
        }
    }

    public T getEntidad() {
        if (entidad == null) {
            try {
                this.entidad = this.claseEntidad.newInstance();
            } catch (InstantiationException ex) {
                Logger.getLogger(UtilController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(UtilController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return entidad;
    }

    /**
     * Método cuando se da clik en el boton nuevo
     */
    private void nuevoClick() {
        this.botonGuardar = false;
        this.botonEditar = true;
        this.botonEliminar = true;
        this.botonBloquear = true;
        this.guardarEdicion = 0;
        this.readOnly = false;
    }

    /**
     * Método para cuando se da doble clikc en un registro
     */
    private void dobleClick() {
        this.readOnly = true;
        this.botonGuardar = true;
        this.botonEditar = false;
        this.botonEliminar = false;
        this.botonBloquear = false;
    }

    public void setEntidad(T entidad) {
        this.entidad = entidad;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public LazyDataModelAdvance<T> getListadoEntidad() {
        listadoEntidad.setFacade(entidadLogica);
        return listadoEntidad;
    }

    public void setListadoEntidad(LazyDataModelAdvance<T> listadoEntidad) {
        this.listadoEntidad = listadoEntidad;
    }

    public AbstractFacade<T> getEntidadLogica() {
        return entidadLogica;
    }

    public void setEntidadLogica(AbstractFacade<T> entidadLogica) {
        this.entidadLogica = entidadLogica;
    }

    public StreamedContent getArchivoDescarga() {
        return archivoDescarga;
    }

    public void setArchivoDescarga(StreamedContent archivoDescarga) {
        this.archivoDescarga = archivoDescarga;
    }

    public Class<T> getClaseEntidad() {
        return claseEntidad;
    }

    public void setClaseEntidad(Class<T> claseEntidad) {
        this.claseEntidad = claseEntidad;
    }

    /**
     * @return the readOnly
     */
    public boolean isReadOnly() {
        return readOnly;
    }

    /**
     * @param readOnly the readOnly to set
     */
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    /**
     * @return the botonNuevo
     */
    public boolean isBotonNuevo() {
        return botonNuevo;
    }

    /**
     * @return the botonGuardar
     */
    public boolean isBotonGuardar() {
        return botonGuardar;
    }

    /**
     * @return the botonEditar
     */
    public boolean isBotonEditar() {
        return botonEditar;
    }

    /**
     * @return the botonBloquear
     */
    public boolean isBotonBloquear() {
        return botonBloquear;
    }

    /**
     * @return the botonEliminar
     */
    public boolean isBotonEliminar() {
        return botonEliminar;
    }

    /**
     * @return the jasper
     */
    public JasperPrint getJasper() {
        return jasper;
    }

    /**
     * @param jasper the jasper to set
     */
    public void setJasper(JasperPrint jasper) {
        this.jasper = jasper;
    }

    public int getGuardarEdicion() {
        return guardarEdicion;
    }

    public void setGuardarEdicion(int guardarEdicion) {
        this.guardarEdicion = guardarEdicion;
    }

    /**
     * Método para exportar reporte a PDF
     *
     * @param actionEvent
     * @throws JRException
     * @throws IOException
     */
    public void reportePDF(ActionEvent actionEvent) throws JRException, IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.pdf");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasper, servletOutputStream);
        FacesContext.getCurrentInstance().responseComplete();
    }

    /**
     * Método para exportar reporte a WORD
     *
     * @param actionEvent
     * @throws JRException
     * @throws IOException
     */
    public void reporteWORD(ActionEvent actionEvent) throws JRException, IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.docx");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JRDocxExporter docxExporter = new JRDocxExporter();
        docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasper);
        docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
        docxExporter.exportReport();
        FacesContext.getCurrentInstance().responseComplete();
    }

    /**
     * Método para exportar reporte a EXCEL
     *
     * @param actionEvent
     * @throws JRException
     * @throws IOException
     */
    public void reporteEXCEL(ActionEvent actionEvent) throws JRException, IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.xlsx");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JRXlsxExporter docxExporter = new JRXlsxExporter();
        docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasper);
        docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
        docxExporter.exportReport();
        FacesContext.getCurrentInstance().responseComplete();
    }

    /**
     * Método para exportar reporte a ODT
     *
     * @param actionEvent
     * @throws JRException
     * @throws IOException
     */
    public void reporteODT(ActionEvent actionEvent) throws JRException, IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.odt");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JROdtExporter docxExporter = new JROdtExporter();
        docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasper);
        docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
        docxExporter.exportReport();
        FacesContext.getCurrentInstance().responseComplete();
    }

    /**
     * Método para exportar reporte a POWEr POINT
     *
     * @param actionEvent
     * @throws JRException
     * @throws IOException
     */
    public void reportePOWER_POINT(ActionEvent actionEvent) throws JRException, IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.pptx");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JRPptxExporter docxExporter = new JRPptxExporter();
        docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasper);
        docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
        docxExporter.exportReport();
        FacesContext.getCurrentInstance().responseComplete();
    }

    public String getCurrentloggeduser() {
        return currentloggeduser;
    }

    public void setCurrentloggeduser(String currentloggeduser) {
        this.currentloggeduser = currentloggeduser;
    }

    /**
     * @return the rutaReporteServidor
     */
    public String getRutaReporteServidor() {
        return rutaReporteServidor;
    }

    /**
     * @param rutaServidor the rutaReporteServidor to set
     */
    public void setRutaReporteServidor(String rutaServidor) {
        this.rutaReporteServidor = rutaServidor;
    }

}
