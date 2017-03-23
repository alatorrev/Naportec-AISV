package com.naportec.utilidades.controladores;

import java.util.Date;
/**
 * Clase con métodos para verifcar contraseñas
 * @author Fernando
 */
public class UtilParametrosPassword {

    static final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;

    public static long diasEntreFechas(Date fecha,int dias) {
        Date d = new Date();
        long diasDesdeFecha=((d.getTime() - fecha.getTime()) / MILLSECS_PER_DAY);
        return dias-diasDesdeFecha; 
    }

    public static int cantidadNumeros(String clave) {
        int cont = 0;
        for (char c : clave.toCharArray()) {
            if (Character.isDigit(c)) {
                cont++;
            }
        }
        return cont;
    }

    public static int cantidadLetras(String clave) {
        int cont = 0;
        for (char c : clave.toCharArray()) {
            if (Character.isLetter(c)) {
                cont++;
            }
        }
        return cont;
    }

    public static int cantidadEspeciales(String clave) {
        int cont = 0;
        for (char c : clave.toCharArray()) {
            if (!Character.isLetter(c) && !Character.isDigit(c)) {
                cont++;
            }
        }
        return cont;
    }

    public static int tamanioClave(String clave) {
        return clave.trim().length();
    }

}
