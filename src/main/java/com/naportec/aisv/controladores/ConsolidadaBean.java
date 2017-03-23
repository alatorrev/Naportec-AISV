package com.naportec.aisv.controladores;

import com.naportec.aisv.entidades.Dae;
import com.naportec.aisv.entidades.Transaccion;
import com.naportec.utilidades.controladores.Mensaje;
import com.naportec.utilidades.controladores.UtilAisvController;
import com.naportec.utilidades.otros.Aes;
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
 * Clase Controlador que permite manejar las opciones de  CArga para consolidación en el sistema
 * @author Fernando
 */
public class ConsolidadaBean extends UtilAisvController implements Serializable {

    public ConsolidadaBean() {
        super();
    }

    /**
     * Método para inicializar datos para Carga para Consolidación.
     */
    @PostConstruct
    @Override
    public void inicializar() {
        this.setTipoTrasaccion("E");
        super.inicializar();
//        listaExportacion.filtroEqual("condicionContenedorPrec", "LCL/FCL");
        listaExportacion.setCondicionContenedor("LCL/FCL");
        listaAisv.filtroEqual("codigoPrec.condicionContenedorPrec", "LCL/FCL");

    }

    /**
     * Método para guardar un AISV de carga para Consolidación
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
            if (ERROR == null) {
                super.guardarAISV(); //To change body of generated methods, choose Tools | Templates.
                this.exportar(null);
                this.transaccion = new Transaccion();
                this.daesListado=new LinkedList<>();
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
     * Método para exportar el AISV en PDF y poderlo mostrar al usuario luego de crear un nuevo AISV
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
                parametros.put("numeroDae", "(" + (tk.countTokens()-1) + ")");
                rutaReporte = "/aisv/reportes/reporteAisvExportacionSueltaConsol.jasper";
            }
            String datoBarra = Aes.encryptData(this.getTransaccion().toString());
            String datoBarraDos = Aes.encryptData(this.getTransaccion().getCodigoTrans() + "");
            System.out.println("" + Aes.encryptData(this.getTransaccion().toString()));
            System.out.println("" + Aes.encryptData(this.getTransaccion().getCodigoTrans() + ""));
            parametros.put("codigoBarraInf", datoBarra.substring(0, datoBarra.length() - 2));
            parametros.put("imagen", this.getClass().getResourceAsStream("/com/naportec/utilidades/img/nap.png"));
            parametros.put("primercodigo", datoBarraDos.substring(0, datoBarraDos.length() - 2));
            //COLOCAR AQUI CODIGO DE BARRA
            //---------------------------------------------------------------
            this.inicializarReporte(parametros, rutaReporte);
        } catch (JRException ex) {
            Logger.getLogger(ContenerizadoBean.class.getName()).log(Level.SEVERE, null, ex);
            Mensaje.ERROR_DIALOG("Reporte AISV", "Ha sucedido un Error" + ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(ContenerizadoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
