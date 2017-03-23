/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.coparn;

import com.naportec.aisv.entidades.Precarga;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.xml.sax.SAXException;

public interface IPrecarga {
    /**
     * Método para obtener las precargas desde Archivo EDI
     * @param stream
     * @return
     * @throws IOException
     * @throws SAXException 
     */
    public List<Precarga> obtenerPrecarga(InputStream stream)throws IOException, SAXException;
    
}
