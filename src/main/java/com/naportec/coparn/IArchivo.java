/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.naportec.coparn;

import java.io.IOException;
import java.io.InputStream;
import org.primefaces.model.UploadedFile;

/**
 * Interfaz para manejo de Archivos
 * @author Diseñador
 */
public interface IArchivo {
    /**
     * Método para obtener un InputStream desde una URL de archivo
     * @param url
     * @return
     * @throws IOException 
     */
    public InputStream obtenerArchivo(String url)throws IOException;
    /**
     * Método para obtener un InputStream desde un UploadedFile
     * @param upf
     * @return
     * @throws IOException 
     */
    public InputStream obtenerArchivo(UploadedFile upf)throws IOException;
}
