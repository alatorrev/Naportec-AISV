/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.utilidades.otros;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase utilidad para manejar archivos de configuración
 * @author Fernando
 */
public class ArchivoConfiguracion {

    public String url = "";
    /**
     * Método para obtener un archivo deconfiguración desde una carpeta 
     * y el nombre de archivo
     * @param archivo
     * @param carpeta 
     */
    public ArchivoConfiguracion(String archivo, String carpeta) {
        url = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        url = (url.replace("classes", carpeta).replace("/", File.separatorChar + "")).substring(1).split("config")[0];
        url=url+File.separatorChar+carpeta+File.separatorChar + archivo + ".properties";
    }
    
    /**
     * Método para obtener un archivo properties desde una URL
     * @return 
     */
    public Properties obtenerArchivo() {
        Properties propiedades = new Properties();
        InputStream entrada = null;
        try {
            this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
            entrada = new FileInputStream(url);
            propiedades.load(entrada);
            Logger.getLogger(ArchivoConfiguracion.class.getName()).log(Level.INFO, null, url);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ArchivoConfiguracion.class.getName()).log(Level.SEVERE, null, ex);
            return new Properties();
        } catch (IOException ex) {
            Logger.getLogger(ArchivoConfiguracion.class.getName()).log(Level.SEVERE, null, ex);
            return new Properties();
        } finally {
            if (entrada != null) {
                try {
                    entrada.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return propiedades;
    }

}
