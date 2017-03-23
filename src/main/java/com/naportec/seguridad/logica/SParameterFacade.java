package com.naportec.seguridad.logica;

import com.naportec.seguridad.entidades.SParameter;
import com.naportec.utilidades.controladores.UtilParametrosPassword;
import com.naportec.utilidades.logica.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Logica de Negocios de la entidad SParameter
 * @author Fernando
 */
@Stateless
public class SParameterFacade extends AbstractFacade<SParameter> {
    
    /**
     * Inicialización de  nuestro Recurso de Datos
     */
    @PersistenceContext(unitName = "com.naportec_Aisv")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    public SParameterFacade() {
        super(SParameter.class);
    }
    /**
     * Validación de la contraseña segun los parametros establecidos
     * @param pass
     * @return
     * @throws Exception 
     */
     public String validacionPassword(String pass) throws Exception{
        String mensajeError="";
        SParameter parametros=this.listarTodos().get(0);
        if(parametros.getParamCantNumeros()>0){
            if(UtilParametrosPassword.cantidadNumeros(pass.toString().trim())<parametros.getParamCantNumeros()){
                mensajeError+="Se piden por lo menos "+parametros.getParamCantNumeros()+" Numeros\n";
            }
        }
        if(parametros.getParamCantLetras()>0){
            if(UtilParametrosPassword.cantidadLetras(pass.toString().trim())<parametros.getParamCantLetras()){
                mensajeError+="Se piden por lo menos "+parametros.getParamCantLetras()+" Letras\n";
            }
        }
        if(parametros.getParamCantEspeciales()>0){
            if(UtilParametrosPassword.cantidadEspeciales(pass.toString().trim())<parametros.getParamCantEspeciales()){
                mensajeError+="Se piden por lo menos "+parametros.getParamCantEspeciales()+" caracteres Especiales\n";
            }
        }
        if(!(pass.trim().length()>=parametros.getParamTamMin() && pass.trim().length()<=parametros.getParamTamMax())){
             mensajeError+="La contraseña debe tener minimo"+parametros.getParamTamMin()+" y máximo "+parametros.getParamTamMax()+" caracteres\n";
        }
        if(mensajeError.length()>0){
            return "ERROR:"+mensajeError;
        }else{
            return "";
        }
    }
}
