/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.utilidades.otros;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.List;
import javax.faces.context.FacesContext;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 * Esta clase no es utilizada en ningun proceso
 * Clase Utilidad para exportar archivos a EXCEL
 * @author Fernando
 */
public class UtilExcel<T> {

    private Class<T> objeto;
    private String plantilla;
    private String titulo;
    private String nombreReporte;
    private int columnaInicio;
    private int filaInicio;
    private List<T> listadoDatos;
    private String[] columnasReporte;

    //------------------------------
    private StreamedContent file;

    public UtilExcel(Class<T> objeto, String plantilla, String titulo, String nombreReporte, int columnaInicio, int filaInicio, List<T> listadoDatos, String[] columnas) {
        this.objeto = objeto;
        this.plantilla = plantilla;
        this.titulo = titulo;
        this.nombreReporte = nombreReporte;
        this.columnaInicio = columnaInicio;
        this.filaInicio = filaInicio;
        this.listadoDatos = listadoDatos;
        this.columnasReporte = columnas;
    }

    public UtilExcel(Class<T> objeto, String plantilla, String titulo, String nombreReporte, int columnaInicio, int filaInicio) {
        this.objeto = objeto;
        this.plantilla = plantilla;
        this.titulo = titulo;
        this.nombreReporte = nombreReporte;
        this.columnaInicio = columnaInicio;
        this.filaInicio = filaInicio;
    }

    public StreamedContent generar() {
        String ver = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources");
        File archivo = new File(ver + "/plantillas/" + this.plantilla + ".xls");
        try {
            FileInputStream fileArch = new FileInputStream(archivo);
            HSSFWorkbook workbook = new HSSFWorkbook(fileArch);
            HSSFSheet sheet = workbook.getSheetAt(0);
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            // ESTABLECER FUENTE
            Font fuente = workbook.createFont();
            fuente.setFontHeightInPoints((short) 8);
            cellStyle.setFont(fuente);
            // ESTABLECER BORDES
            cellStyle.setWrapText(true);
            cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBottomBorderColor((short) 8);
            cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            cellStyle.setLeftBorderColor((short) 8);
            cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            cellStyle.setRightBorderColor((short) 8);
            cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            cellStyle.setTopBorderColor((short) 8);
            // APLICAR COLOR DE FONDO
//            cellStyle.setFillBackgroundColor(new XSSFColor(new Color(220, 230, 241)));
//            cellStyle.setFillForegroundColor(new XSSFColor(new Color(220, 230, 241)));
//            cellStyle.setFillPattern(CellStyle.FINE_DOTS);
            //------------------------------------------------------------------------------
            HSSFCellStyle fecha = workbook.createCellStyle();
            fecha.cloneStyleFrom(cellStyle);
            CreationHelper createHelper = workbook.getCreationHelper();
            fecha.setDataFormat(createHelper.createDataFormat().getFormat("MMMM dd, yyyy"));
            //------------------------------------------------------------------------------
            for (int i = 0; i < this.listadoDatos.size(); i++) {
                T obj = listadoDatos.get(i);
                HSSFRow fila = sheet.getRow(i + filaInicio);
                for (int k = 0; k < columnasReporte.length; k++) {
                    String e = columnasReporte[k];
                    String[] sub_objeto = e.split(":");
                    //-------------------------------------------------------------------------------------------
                    Method m;
                    if (sub_objeto.length == 1) {
                        m = objeto.getMethod("get" + ((e.charAt(0) + "").toUpperCase()) + e.substring(1, e.length()), null);
                    } else {
                        m = objeto.getMethod("get" + ((sub_objeto[0].charAt(0) + "").toUpperCase()) + sub_objeto[0].substring(1, sub_objeto[0].length()), null);
                    }
                    Object valor = m.invoke(obj, null);
                    //-PARA USAR EL VALOR DE OTRO OBJETO

                    //------------------------------------------------------------------------------------------
                    if (sub_objeto.length == 1) {
                        java.lang.reflect.Field campo = objeto.getDeclaredField(columnasReporte[k]);
                        Object vf = "";
                        try {
                            campo.setAccessible(true);
                            vf = campo.get(objeto);
                            campo.setAccessible(false);
                        } catch (IllegalArgumentException ex) {
                            System.out.println(ex.getMessage() + " " + ex.getLocalizedMessage());
                        }
                        if (fila != null) {
                            if (valor != null) {
                                if (valor.getClass().getSimpleName().equals("Date")) {
                                    fila.getCell(columnaInicio + k).setCellStyle(fecha);
                                    fila.getCell(columnaInicio + k).setCellValue((java.util.Date) valor);
                                } else {
                                    fila.getCell(columnaInicio + k).setCellStyle(cellStyle);
                                    fila.getCell(columnaInicio + k).setCellValue(valor + "");
                                }
                            } else {
                                fila.getCell(columnaInicio + k).setCellStyle(cellStyle);
                            }
                        }
                    } else {
                        if (sub_objeto.length == 2) {
                            java.lang.reflect.Field campo = objeto.getDeclaredField(sub_objeto[0]);
                            campo.setAccessible(true);
                            Class sub = campo.get(obj).getClass();
                            campo.setAccessible(false);
                            Method sub_met = sub.getMethod("get" + ((sub_objeto[1].charAt(0) + "").toUpperCase()) + sub_objeto[1].substring(1, sub_objeto[1].length()), null);
                            Object sub_valor = sub_met.invoke(sub.cast(valor), null);
                            //----------------------------------------------------------------------------------
                            java.lang.reflect.Field campo1 = sub.getDeclaredField(sub_objeto[1]);
                            campo1.setAccessible(true);
                            Object vf = campo1.get(sub.cast(valor));
                            campo1.setAccessible(false);
                            if (fila != null) {
                                if (valor != null) {
                                    if (vf.getClass().getSimpleName().equals("Date")) {
                                        fila.getCell(columnaInicio + k).setCellStyle(fecha);
                                        fila.getCell(columnaInicio + k).setCellValue((java.util.Date) sub_valor);
                                    } else {
                                        fila.getCell(columnaInicio + k).setCellStyle(cellStyle);
                                        fila.getCell(columnaInicio + k).setCellValue(sub_valor + "");
                                    }
                                } else {
                                    fila.getCell(columnaInicio + k).setCellStyle(cellStyle);
                                }
                            }
                        } else {
                            if (sub_objeto.length == 3) {

                            }
                        }
                    }
                }
            }
            fileArch.close();
            File f = new File(ver + "/plantillas/" + plantilla + ".xls");
            FileOutputStream outFile = new FileOutputStream(f);
            workbook.write(outFile);
            outFile.flush();
            outFile.close();
            InputStream stream = new FileInputStream(f);
            file = new DefaultStreamedContent(stream, "", nombreReporte + ".xls");
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Class<T> getObjeto() {
        return objeto;
    }

    public void setObjeto(Class<T> objeto) {
        this.objeto = objeto;
    }

    public String getPlantilla() {
        return plantilla;
    }

    public void setPlantilla(String plantilla) {
        this.plantilla = plantilla;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNombreReporte() {
        return nombreReporte;
    }

    public void setNombreReporte(String nombreReporte) {
        this.nombreReporte = nombreReporte;
    }

    public int getColumnaInicio() {
        return columnaInicio;
    }

    public void setColumnaInicio(int columnaInicio) {
        this.columnaInicio = columnaInicio;
    }

    public int getFilaInicio() {
        return filaInicio;
    }

    public void setFilaInicio(int filaInicio) {
        this.filaInicio = filaInicio;
    }

    public List<T> getListadoDatos() {
        return listadoDatos;
    }

    public void setListadoDatos(List<T> listadoDatos) {
        this.listadoDatos = listadoDatos;
    }

    public String[] getColumnasReporte() {
        return columnasReporte;
    }

    public void setColumnasReporte(String[] columnasReporte) {
        this.columnasReporte = columnasReporte;
    }

}
