package com.naportec.aisv.controladores;

import com.naportec.aisv.entidades.Dae;
import com.naportec.aisv.entidades.Transaccion;
import com.naportec.utilidades.controladores.Mensaje;
import com.naportec.utilidades.controladores.UtilAisvController;
import com.naportec.utilidades.otros.Aes;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.context.RequestContext;

/**
 * Clase controlador quemaneja las acciones de la Carga Suelta
 *
 * @author Fernando
 */
public class SueltaBean extends UtilAisvController implements Serializable {

    public SueltaBean() {
        super();
    }

    /**
     * Método para inicializar listados de datos carga suelta
     */
    @PostConstruct
    @Override
    public void inicializar() {
        super.inicializar();
        listaImportacion.filtroEqual("condicionContenedorPrec", "LCL/LCL");
//        listaExportacion.filtroEqual("condicionContenedorPrec", "LCL/LCL");
        listaExportacion.setCondicionContenedor("LCL/LCL");
        listaAisv.filtroEqual("codigoPrec.condicionContenedorPrec", "LCL/LCL");
    }

    /**
     * Método para guardar el AISV de carga suelta
     */
    @Override
    public void guardarAISV() {
        try {
            this.transaccion.setUsrId(usuario);
            if (this.getOrigen() != null) {
                this.transaccion.setPuertoOrigenTrans(this.getOrigen().getNombrePuer());
            }
            if (this.getDestino() != null) {
                this.transaccion.setPuertoDestinoTrans(this.getDestino().getNombrePuer());
            }
            if (this.getTrasbordo() != null) {
                this.transaccion.setPuertoTrasbordoTrans(this.getTrasbordo().getNombrePuer());
            }
            
            if (this.transaccion.getCodigoPrec().getTipoPrec().equals("I")) {
                this.transaccion.setPesoBasculaTrans(this.transaccion.getCodigoPrec().getPesoPrec());
            }
            
            String ERROR = validacionDatos();

//            String valDespacho = this.validacionDespacho();
//            if (valDespacho != null) {
//                if (ERROR != null) {
//                    ERROR += valDespacho;
//                } else {
//                    ERROR = valDespacho;
//                }
//            }

            if (ERROR == null) {
                super.guardarAISV(); //To change body of generated methods, choose Tools | Templates.
                this.exportar(null);
                this.transaccion = new Transaccion();
                this.daesListado = new LinkedList<>();
                this.setDae(new Dae());
            } else {
                RequestContext.getCurrentInstance().addCallbackParam("notValid", true);
                Mensaje.ERROR_DIALOG("Guardar AISV", ERROR);
            }
        } catch (Exception ex) {
            Logger.getLogger(ContenerizadoBean.class.getName()).log(Level.SEVERE, null, ex);
            Mensaje.ERROR_DIALOG("Guardar AISV", "Ha sucedido un error en la creación del AISV" + ex.getMessage());
        }
    }

    /**
     * Método para exportar el reporte en PDF del AISV de carga suelta nuevo
     *
     * @param evt
     */
    public void exportar(ActionEvent evt) {
        try {
            Map parametros = new HashMap();
            String rutaReporte = "";
            if (getTransaccion().getCodigoPrec().getTipoPrec().equals("I")) {
                parametros.put("tipoTransaccion", "Importador");
                rutaReporte = "/aisv/reportes/reporteAisvImportacionSueltaConsol.jasper";
            } else {
                parametros.put("tipoTransaccion", "Exportador");
                parametros.put("bookingBl", "Booking");
                StringTokenizer tk = new StringTokenizer(this.getTransaccion().getDaesTrans(), "/");
                parametros.put("numeroDae", "(" + (tk.countTokens() - 1) + ")");
                rutaReporte = "/aisv/reportes/reporteAisvExportacionSueltaConsol.jasper";
            }
            String datoBarra = Aes.encryptData(this.transaccion.toString());
            String datoBarraDos = Aes.encryptData(this.transaccion.getCodigoTrans() + "");
            parametros.put("codigoBarraInf", datoBarra.substring(0, datoBarra.length() - 2));
            BufferedImage QRImage = com.google.zxing.client.j2se.MatrixToImageWriter.toBufferedImage(
                    new com.google.zxing.qrcode.QRCodeWriter().encode(
                            datoBarraDos, com.google.zxing.BarcodeFormat.QR_CODE, 300, 300));
            //parametros.put("imagen", this.getClass().getResourceAsStream("/com/naportec/utilidades/img/nap.png"));
            parametros.put("imageQR",QRImage);
            parametros.put("primercodigo", datoBarraDos.substring(0, datoBarraDos.length() - 2));
            //COLOCAR AQUI CODIGO DE BARRA
            //---------------------------------------------------------------
            this.inicializarReporteAisv(parametros, rutaReporte, this.transaccion);
        } catch (JRException ex) {
            Logger.getLogger(ContenerizadoBean.class.getName()).log(Level.SEVERE, null, ex);
            Mensaje.ERROR_DIALOG("Reporte AISV", "Ha sucedido un Error" + ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(ContenerizadoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
