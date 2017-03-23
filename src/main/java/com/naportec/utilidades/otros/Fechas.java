/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.utilidades.otros;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase utilidad para manejar Fechas
 *
 * @author Fernando
 */
public class Fechas {

    private static final Logger LOG = Logger.getLogger(Fechas.class.getName());

    /**
     * Método para sumar días a una fecha
     *
     * @param fecha
     * @param dias
     * @return
     */
    public static Date sumarDiasFecha(Date fecha, int dias) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); // Configuramos la fecha que se recibe
        calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0	
        return (Date) calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos	
    }

    /**
     * Método para sumar o restar minutos a una fecha
     *
     * @param fecha
     * @param minutos
     * @return
     */
    public static Date sumarRestarMinFecha(Date fecha, int minutos) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); // Configuramos la fecha que se recibe	
        calendar.add(Calendar.MINUTE, minutos);  // numero de horas a añadir, o restar en caso de horas<0		
        return calendar.getTime(); // Devuelve el objeto Date con las nuevas horas añadidas		
    }

    /**
     * Método para convertir una cadena de texto en Date
     *
     * @param valor
     * @return
     * @throws ParseException
     */
    public static Date Fecha(String valor) throws ParseException {
        SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyy/dd/MM");
        return formatoDeFecha.parse(valor);
    }

    /**
     * Método para convertir una cadena de texto
     *
     * @param valor
     * @param formato
     * @return
     * @throws ParseException
     */
    public static Date Fecha(String valor, String formato) throws ParseException {
        SimpleDateFormat formatoDeFecha = new SimpleDateFormat(formato);
        return formatoDeFecha.parse(valor);
    }

    /**
     * Método para convertir una Fecha en cadena de texto
     *
     * @param d
     * @return
     */
    public static String Fecha(Date d) {
        if (d == null) {
            return "";
        }
        try {
            SimpleDateFormat formateador = new SimpleDateFormat(
                    "dd 'de' MMMM 'de' yyyy HH:mm:ss", new Locale("es_ES"));
            String fecha = formateador.format(d);
            return fecha;
        } catch (NullPointerException ex) {
            LOG.log(Level.SEVERE, "LA FECHA ES NULA{0}", ex.getMessage());
            return "";
        } catch (IllegalArgumentException ex) {
            LOG.log(Level.SEVERE, "EL DATO FEHCA  ACONVERTIR ES ILEGAL {0}", d);
            return "";
        }
    }

    public static Date FechaFormat(Date d,String formato) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(sdf.format(d));
    }

}
