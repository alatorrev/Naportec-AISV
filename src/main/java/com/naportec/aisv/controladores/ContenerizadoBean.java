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
 * Clase Controlador que nos permite utilizar las opciones de AISV para carga contenerizada en el sistema
 * @author Fernando
 */
public class ContenerizadoBean extends UtilAisvController implements Serializable {

    public ContenerizadoBean() {
        super();
    }

    /**
     * Método para inicializar listados livianos.
     */
    @PostConstruct
    @Override
    public void inicializar() {
        super.inicializar();
        listaImportacion.filtroEqual("condicionContenedorPrec", "FCL/FCL");
        listaExportacion.setCondicionContenedor("FCL/FCL");
        listaAisv.filtroEqual("codigoPrec.condicionContenedorPrec", "FCL/FCL");
    }

    /**
     * Método que permite controlar la modificación del nombre del exportador.
     * Éste método se ejecuta cuando se sale del foco de la caja de texto en la 
     * creación del Aisv y muestra un mensaje si desea efectuar los cambios.
     */
    public void blurExportador() {
        if (this.transaccion != null) {
            if (!this.getExportador().equals(this.transaccion.getCodigoPrec().getImportadorPrec())) {
                RequestContext.getCurrentInstance().execute("PF('dlgCambioExpor').show()");
            }
        }
    }
    /**
     * Método que permite cancelar la modificación del nombre del exportador 
     */
    public void cancelarExportador() {
        if (this.transaccion != null) {
            this.transaccion.getCodigoPrec().setImportadorPrec(this.getExportador());
        }
    }
    /**
     * Método que permite aceptar el cambio del nombre del exportador
     */
    public void aceptarExportador() {
        if (this.transaccion != null) {
            this.setExportador(this.transaccion.getCodigoPrec().getImportadorPrec());
        }
    }
    /**
     * Método que permite controlar la modificación del RUC del exportador.
     * Éste método se ejecuta cuando se sale del foco de la caja de texto en la 
     * creación del Aisv y muestra un mensaje si desea efectuar los cambios.
     */
    public void blurRuc() {
        if (this.transaccion != null) {
            if (!this.getRucExportador().equals(this.transaccion.getCodigoPrec().getImpExpIdPrec())) {
                RequestContext.getCurrentInstance().execute("PF('dlgCambioRuc').show()");
            }
        }
    }
     /**
     * Método que permite cancelar la modificación del RUC del exportador 
     */
    public void cancelarRuc() {
        if (this.transaccion != null) {
            this.transaccion.getCodigoPrec().setImpExpIdPrec(this.getRucExportador());
        }
    }
     /**
     * Método que permite aceptar el cambio del RUC del exportador
     */
    public void aceptarRuc() {
        if (this.transaccion != null) {
            this.setRucExportador(this.transaccion.getCodigoPrec().getImpExpIdPrec());
        }
    }
    
    /**
     * Método para guardar un AISV de carga Contenerizada 
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
            
            String ERROR = validacionDatos();
            
            String valContenedor=validacionContenedor();
            if (valContenedor != null) {
                if (ERROR != null) {
                    ERROR += valContenedor;
                } else {
                    ERROR = valContenedor;
                }
            }
            
            if (this.transaccion.getCodigoPrec().getTipoPrec().equals("E")) {
                if (this.getDestinoFinal() != null) {
                    if (this.getDestinoFinal().getCodigoPuer() != null) {
                        this.transaccion.setPuertoFinalTrans(this.getDestinoFinal().getNombrePuer());
                    } else {
                        ERROR += "El puerto de Destino final es Obligatorio.</br>";
                    }
                } else {
                    ERROR += "El puerto de Destino final es Obligatorio.</br>";
                }
            }else{
                this.transaccion.setPesoBasculaTrans(this.transaccion.getCodigoPrec().getPesoPrec());
            }
            
            String valExportador=validacionExportador();
            if (valExportador != null) {
                if (ERROR != null) {
                    ERROR += valExportador;
                } else {
                    ERROR = valExportador;
                }
            }
            
//            String valDespacho=this.validacionDespacho();
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
            Logger.getLogger(ContenerizadoBean.class
                    .getName()).log(Level.SEVERE, null, ex);
            Mensaje.ERROR_DIALOG(
                    "Guardar AISV", "Ha sucedido un error en la creación del AISV" + ex.getMessage());

            this.transaccion = new Transaccion();
        }
    }
    /**
     * Método para la validación del Contenedor
     * @return 
     */
    public String validacionContenedor() {
        StringBuilder sb = new StringBuilder();
        if (this.getTransaccion().getContenedorTrans() != null) {
            if (!this.getTransaccion().getContenedorTrans().trim().matches("^[a-zA-Z]{4}+[0-9]{7}")) {
                sb.append("El formato del contenedor no es correcto (Debe ser: 4 letras mas 7 digitos Ejm: AAAA1234567).</br>");
            }
        }
        if (sb.toString().trim().length() > 0) {
            return sb.toString();
        } else {
            return null;
        }
    }
    /**
     * Método para validar los datos delExportador omo el RUC y nombre.
     * @return 
     */
    public String validacionExportador() {
        StringBuilder sb = new StringBuilder();
        if (this.getTransaccion().getCodigoPrec().getTipoPrec().equals("E")) {
            if (this.getTransaccion().getCodigoPrec().getImpExpIdPrec() != null) {
                if (this.getTransaccion().getCodigoPrec().getImpExpIdPrec().trim().length() == 13) {
                    if (!this.getTransaccion().getCodigoPrec().getImpExpIdPrec().trim().substring(10).equals("001")) {
                        sb.append("RUC incorrecto debe terminar en 001.<br/>");
                    }
                } else {
                    sb.append("RUC incorrecto debe tener 13 digitos.<br/>");
                }
                if (!this.getTransaccion().getCodigoPrec().getImpExpIdPrec().matches("[0-9]*")) {
                    sb.append("RUC incorrecto por favor verificar.</br>");
                }
            } else {
                sb.append("Por favor coloque el RUC del Exportador.<br/>");
            }
            if (this.getTransaccion().getCodigoPrec().getImportadorPrec() == null) {
                sb.append("El RUC del exportador no puede estar vacio<br/>");
            } else if (this.getTransaccion().getCodigoPrec().getImportadorPrec().trim().length() <= 0) {
                sb.append("El RUC del exportador no puede estar vacio<br/>");
            }
        }
        if (sb.toString().trim().length() > 0) {
            return sb.toString();
        } else {
            return null;
        }
    }
    /**
     * Método para exportar el reporte a PDF cuando se crea un nuevo AISV
     * @param evt 
     */
    public void exportar(ActionEvent evt) {
        try {
            Map parametros = new HashMap();
            String rutaReporte = "";
            if (getTransaccion().getCodigoPrec().getTipoPrec().equals("I")) {
                parametros.put("tipoTransaccion", "Importador");
                rutaReporte = "/aisv/reportes/reporteAisvImportacion.jasper";
            } else {
                parametros.put("tipoTransaccion", "Exportador");
                parametros.put("bookingBl", "Booking");
                if (getTransaccion().getDaesTrans() == null) {
                    getTransaccion().setDaesTrans(this.tomarDatoDae());
                }
                StringTokenizer tk = new StringTokenizer(getTransaccion().getDaesTrans(), "/");
                parametros.put("numeroDae", "(" + (tk.countTokens() - 1) + ")");
                rutaReporte = "/aisv/reportes/reporteAisvExportacion.jasper";
            }
            String datoBarra = Aes.encryptData(this.getTransaccion().toString());
            String datoBarraDos = Aes.encryptData(this.getTransaccion().getCodigoTrans() + "");
            BufferedImage QRImage = com.google.zxing.client.j2se.MatrixToImageWriter.toBufferedImage(
                    new com.google.zxing.qrcode.QRCodeWriter().encode(
                            datoBarraDos, com.google.zxing.BarcodeFormat.QR_CODE, 300, 300));
            parametros.put("codigoBarraInf", datoBarra.substring(0, datoBarra.length() - 2));
            parametros.put("imageQR",QRImage);
            parametros.put("primercodigo", datoBarraDos.substring(0, datoBarraDos.length() - 2));
            //COLOCAR AQUI CODIGO DE BARRA
            //---------------------------------------------------------------
            this.inicializarReporteAisv(parametros, rutaReporte, this.transaccion);

        } catch (JRException ex) {
            Logger.getLogger(ContenerizadoBean.class
                    .getName()).log(Level.SEVERE, null, ex);
            Mensaje.ERROR_DIALOG(
                    "Reporte AISV", "Ha sucedido un Error" + ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(ContenerizadoBean.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

}
