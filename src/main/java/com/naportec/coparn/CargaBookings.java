/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.coparn;

import com.naportec.aisv.entidades.Precarga;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import org.milyn.edi.unedifact.d00b.COPARN.Coparn;
import org.milyn.edi.unedifact.d00b.D00BInterchangeFactory;
import org.milyn.smooks.edi.unedifact.model.UNEdifactInterchange;
import org.milyn.smooks.edi.unedifact.model.r41.UNEdifactInterchange41;
import org.milyn.smooks.edi.unedifact.model.r41.UNEdifactMessage41;
import org.primefaces.model.UploadedFile;
import org.xml.sax.SAXException;

/**
 *
 * @author MZambrano1
 */
public class CargaBookings implements IPrecarga,IArchivo {

    @Override
    public InputStream obtenerArchivo(String url) throws IOException {
        InputStream stream = new FileInputStream(url);
        return stream;
    }

    @Override
    public InputStream obtenerArchivo(UploadedFile ups) throws IOException {
        InputStream stream = ups.getInputstream();
        return stream;
    }

    @Override
    public List<Precarga> obtenerPrecarga(InputStream stream) throws IOException, SAXException {
        List<Precarga> lista = new LinkedList<Precarga>();
        List<Precarga> listaAux = new LinkedList<Precarga>();
        D00BInterchangeFactory factory = D00BInterchangeFactory.getInstance();
        UNEdifactInterchange interchange = factory.fromUNEdifact(stream);
        if (interchange instanceof UNEdifactInterchange41) {
            UNEdifactInterchange41 interchange41 = (UNEdifactInterchange41) interchange;
            for (UNEdifactMessage41 messageWithControlSegments : interchange41.getMessages()) {
                Object messageObj = messageWithControlSegments.getMessage();
                if (messageObj instanceof Coparn) {
                    Coparn invoice = (Coparn) messageObj;
                    Precarga x = new Precarga();
                    x.setBookingPrec(invoice.getSegmentGroup1().get(0).getReference().getReference().getReferenceIdentifier());
                    x.setPtoOrigenPrec("ECGYE");
                    String condicionAux = "";
                    condicionAux = invoice.getSegmentGroup13().get(0).getEquipmentDetails().getFullOrEmptyIndicatorCode();
                    if (condicionAux.equals("5") || condicionAux.equals("8")) {
                        x.setCondicionContenedorPrec("FCL");
                    } else {
                        if (condicionAux.equals("7")) {
                            x.setCondicionContenedorPrec("LCL");
                        }
                    }
                    x.setTipoContenedorPrec(invoice.getSegmentGroup13().get(0).getEquipmentDetails().getEquipmentSizeAndType().getEquipmentSizeAndTypeDescriptionCode());
                    
                    listaAux.add(x);
                }
            }
        }

        return lista;
    }
}
