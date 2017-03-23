/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.utilidades.validaciones;

/**
 * Clase donde se encuentran algunas validaciones necesarias
 * @author Fernando
 */
public class Validaciones {

    public static final int ERROR_CEDULA=0;
    public static final int ERROR_RUC=1;
    public static final int ERROR_NO_DEFINIDO=2;
    public static final int IDENTIFICACION_OK=3;
    
    /**
     * Método para validar cédual ecuatoriana
     * @param cedula
     * @return 
     */
    public static int validarCedula(String cedula) {
        if(cedula==null){
            return ERROR_CEDULA;
        }
        for(char c:cedula.toCharArray()){
            if(!Character.isDigit(c)){
                 return ERROR_CEDULA;
            }
        }
        int suma = 0;
        if (cedula.length() == 9) {
            System.out.println("Ingrese su cedula de 10 digitos");
            return ERROR_CEDULA;
        } else {
            int a[] = new int[cedula.length() / 2];
            int b[] = new int[(cedula.length() / 2)];
            int c = 0;
            int d = 1;
            for (int i = 0; i < cedula.length() / 2; i++) {
                a[i] = Integer.parseInt(String.valueOf(cedula.charAt(c)));
                c = c + 2;
                if (i < (cedula.length() / 2) - 1) {
                    b[i] = Integer.parseInt(String.valueOf(cedula.charAt(d)));
                    d = d + 2;
                }
            }

            for (int i = 0; i < a.length; i++) {
                a[i] = a[i] * 2;
                if (a[i] > 9) {
                    a[i] = a[i] - 9;
                }
                suma = suma + a[i] + b[i];
            }
            int aux = suma / 10;
            int dec = (aux + 1) * 10;
            if ((dec - suma) == Integer.parseInt(String.valueOf(cedula.charAt(cedula.length() - 1)))) {
                return IDENTIFICACION_OK;
            } else if (suma % 10 == 0 && cedula.charAt(cedula.length() - 1) == '0') {
                return IDENTIFICACION_OK;
            } else {
                return ERROR_CEDULA;
            }

        }
    }
    /**
     * Método par avalidar RUC
     * @param ruc
     * @return 
     */
    public static int validarRuc(String ruc) {
        if (ruc == null) {
            return ERROR_RUC;
        }
        if (ruc.length() == 13) {
            return IDENTIFICACION_OK;
        } else {
            return ERROR_RUC;
        }
    }
    
    /**
     * Método para validar documentos de identificación de acuerdo a un tipo
     * ya sea ruc, pasaporte y cédula
     * @param tipo
     * @param identificacion
     * @return 
     */
    public static int validarIdentificacion(String tipo,String identificacion){
        if(tipo.equals("pasaporte")){
            return IDENTIFICACION_OK;
        }
        if(tipo.equals("ruc")){
            return validarRuc(identificacion);
        }
        if(tipo.equals("cedula")){
            return validarCedula(identificacion);
        }
        return ERROR_NO_DEFINIDO;
    }
    
}
