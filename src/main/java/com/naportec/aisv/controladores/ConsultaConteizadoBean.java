package com.naportec.aisv.controladores;

import com.naportec.utilidades.controladores.UtilAisvConsultaController;
import javax.annotation.PostConstruct;

/**
 * Clase controlador que nos permite manejarals consultas de AISV
 * @author Fernando
 */
public class ConsultaConteizadoBean extends UtilAisvConsultaController {

    public ConsultaConteizadoBean() {

    }

    @PostConstruct
    @Override
    public void inicializar() {
        super.inicializar();
    }

}
