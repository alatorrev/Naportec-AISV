package com.naportec.utilidades.mail;

import com.naportec.aisv.entidades.Solicitud;
import com.naportec.aisv.entidades.Transaccion;
import com.naportec.seguridad.entidades.SUser;
import com.naportec.utilidades.otros.Aes;
import com.naportec.utilidades.otros.Fechas;
import java.io.Serializable;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Clase utilidad para llenar los datos en un html
 * @author Fernando
 */
public class UtilHtml implements Serializable{

    public String obtenerRuta(String ruta){
        String x="";
        
        return x;
    }
    /**
     * Método para llenar los datos de anulación de solicitud en la plantilla html
     * @param s
     * @param plantillaHTML
     * @param motivo
     * @return 
     */
    public static String importarDatosAnulacion(Solicitud s, String plantillaHTML, String motivo) {
        String texto = plantillaHTML;
        texto = texto.replace("#{empresa}", s.getNombreSoli());
        texto = texto.replace("#{motivo}", motivo);
        return texto;
    }
    /**
     * Método para llenar los datos de nueva solicitud en la plantilla html
     * @param s
     * @param plantillaHTML
     * @return 
     */
    public static String importarDatosNuevaSolicitud(Solicitud s, String plantillaHTML) {
        String texto = plantillaHTML;
        texto = texto.replace("#{empresa}", s.getNombreSoli());
        return texto;
    }
    /**
     * Método para llenar los datos de solicitud pendiente en la plantilla html
     * @param s
     * @param plantillaHTML
     * @return 
     */
    public static String importarDatosPendiente(Solicitud s, String plantillaHTML) {
        String texto = plantillaHTML;
        texto = texto.replace("#{empresa}", s.getNombreSoli());
        return texto;
    }

    /**
     * Método para llenar los datos de aprobación de solicitud en la plantilla html
     * @param u
     * @param plantillaHTML
     * @return 
     */
    public static String importarDatosAprobacion(SUser u, String plantillaHTML) {
        String texto = plantillaHTML;
        texto = texto.replace("#{contacto}", u.getUsrFirstname());
        texto = texto.replace("#{usuario}", u.getUsrLoginname());
        try {
            texto = texto.replace("#{password}", Aes.decryptData(u.getUsrPassword()));
        } catch (Exception ex) {
            texto = texto.replace("#{password}", "");
            Logger.getLogger(UtilHtml.class.getName()).log(Level.SEVERE, null, ex);
        }
        return texto;
    }
    /**
     * Método para llenar los datos de aprobación documental del AISV en la plantilla html
     * @param t
     * @param plantillaHTML
     * @param correo
     * @return
     * @throws ParseException 
     */
    public static String importarDatosAprobacionDocumental(Transaccion t, String plantillaHTML,String correo) throws ParseException {
        String texto = plantillaHTML;
        texto = texto.replace("#{correos}", correo);
        texto = texto.replace("#{exportador}", t.getCodigoPrec().getImportadorPrec());
        texto = texto.replace("#{linea}", t.getCodigoPrec().getIdLineaPrec().getNombreNavi());
        texto = texto.replace("#{itinerario}", t.getCodigoPrec().getIdItinerarioPrec().getDescripcionItin());
        texto = texto.replace("#{producto}", t.getCodigoPrec().getDescripcionPrec());
        texto = texto.replace("#{fechaIngreso}", Fechas.Fecha(t.getFechaEirTrans()));
        texto = texto.replace("#{numeroAisv}", t.getCodigoTrans() + "");
        texto = texto.replace("#{booking}", (t.getCodigoPrec().getBlmasterPrec()+" "+t.getCodigoPrec().getBlhijoPrec()));
        texto = texto.replace("#{fechaInspeccion}", Fechas.Fecha(t.getFechaInspeccionVista()));
        return texto;
    }
    /**
     * Método para llenar los datos de bloqueo PAN del AISV en la plantilla html
     * @param t
     * @param plantillaHTML
     * @param correo
     * @return
     * @throws ParseException 
     */
    public static String importarDatosBloqueoPan(Transaccion t, String plantillaHTML,String correo) throws ParseException {
        String texto = plantillaHTML;
        texto = texto.replace("#{correos}", correo);
        texto = texto.replace("#{exportador}", t.getCodigoPrec().getImportadorPrec());
        texto = texto.replace("#{linea}", t.getCodigoPrec().getIdLineaPrec().getNombreNavi());
        texto = texto.replace("#{itinerario}", t.getCodigoPrec().getIdItinerarioPrec().getDescripcionItin());
        texto = texto.replace("#{producto}", t.getCodigoPrec().getDescripcionPrec());
        texto = texto.replace("#{fechaIngreso}", Fechas.Fecha(t.getFechaEirTrans()));
        texto = texto.replace("#{numeroAisv}", t.getCodigoTrans() + "");
        texto = texto.replace("#{contenedor}", t.getContenedorTrans());
        texto = texto.replace("#{booking}", t.getCodigoPrec().getBookingPrec());
        texto = texto.replace("#{fechaInspeccion}", Fechas.Fecha(t.getFechaInspeccionVista()));
        return texto;
    }
    /**
     * Método para llenar los datos de Bloqueo Preembarque del AISV en la plantilla html
     * @param t
     * @param plantillaHTML
     * @return
     * @throws ParseException 
     */
    public static String importarDatosBloqueoPreembarque(Transaccion t, String plantillaHTML) throws ParseException {
        String texto = plantillaHTML;
        texto = texto.replace("#{correos}", t.getCorreos());
        texto = texto.replace("#{exportador}", t.getCodigoPrec().getImportadorPrec());
        texto = texto.replace("#{linea}", t.getCodigoPrec().getIdLineaPrec().getNombreNavi());
        texto = texto.replace("#{itinerario}", t.getCodigoPrec().getIdItinerarioPrec().getDescripcionItin());
        texto = texto.replace("#{producto}", t.getCodigoPrec().getDescripcionPrec());
        texto = texto.replace("#{fechaIngreso}", Fechas.Fecha(t.getFechaEirTrans()));
        texto = texto.replace("#{numeroAisv}", t.getCodigoTrans() + "");
        texto = texto.replace("#{contenedor}", t.getContenedorTrans());
        texto = texto.replace("#{booking}", t.getCodigoPrec().getBookingPrec());
        return texto;
    }

    /**
     * Método para llenar los datos de desaprobación de solicitud en la plantilla html
     * @param s
     * @param plantillaHTML
     * @param motivo
     * @return 
     */
    public static String importarDatosDesaprobacion(Solicitud s, String plantillaHTML, String motivo) {
        String texto = plantillaHTML;
        texto = texto.replace("#{empresa}", s.getNombreSoli());
        texto = texto.replace("#{motivo}", motivo);
        return texto;
    }
    /**
     * Método para llenar los datos de Des-Bloqueo PAN del AISV en la plantilla html
     * @param t
     * @param plantillaHTML
     * @return
     * @throws ParseException 
     */
    public static String importarDatosDesbloqueo(Transaccion t, String plantillaHTML) throws ParseException {
        String texto = plantillaHTML;
        texto = texto.replace("#{correos}", t.getCorreos());
        texto = texto.replace("#{exportador}", t.getCodigoPrec().getImportadorPrec());
        texto = texto.replace("#{linea}", t.getCodigoPrec().getIdLineaPrec().getNombreNavi());
        texto = texto.replace("#{itinerario}", t.getCodigoPrec().getIdItinerarioPrec().getDescripcionItin());
        texto = texto.replace("#{producto}", t.getCodigoPrec().getDescripcionPrec());
        texto = texto.replace("#{fechaIngreso}", Fechas.Fecha(t.getFechaEirTrans()));
        texto = texto.replace("#{numeroAisv}", t.getCodigoTrans() + "");
        texto = texto.replace("#{contenedor}", t.getContenedorTrans());
        texto = texto.replace("#{booking}", t.getCodigoPrec().getBookingPrec());
        return texto;
    }
    /**
     * Método para llenar los datos de Des-bloqueo Preembarque del AISV en la plantilla html
     * @param t
     * @param plantillaHTML
     * @return
     * @throws ParseException 
     */
     public static String importarDatosDesbloqueoPreembarque(Transaccion t, String plantillaHTML) throws ParseException {
        String texto = plantillaHTML;
        texto = texto.replace("#{correos}", t.getCorreos());
        texto = texto.replace("#{exportador}", t.getCodigoPrec().getImportadorPrec());
        texto = texto.replace("#{linea}", t.getCodigoPrec().getIdLineaPrec().getNombreNavi());
        texto = texto.replace("#{itinerario}", t.getCodigoPrec().getIdItinerarioPrec().getDescripcionItin());
        texto = texto.replace("#{producto}", t.getCodigoPrec().getDescripcionPrec());
        texto = texto.replace("#{fechaIngreso}", Fechas.Fecha(t.getFechaEirTrans()));
        texto = texto.replace("#{numeroAisv}", t.getCodigoTrans() + "");
        texto = texto.replace("#{contenedor}", t.getContenedorTrans());
        texto = texto.replace("#{booking}", t.getCodigoPrec().getBookingPrec());
        return texto;
    }
}