/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.utilidades.otros;

/**
 *
 * @author ecuasis
 */
import com.csvreader.CsvReader;
import com.naportec.aisv.entidades.Precarga;
import com.naportec.aisv.entidades.PrecargaVacios;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;
import org.milyn.edi.unedifact.d95b.D95BInterchangeFactory;
import org.milyn.smooks.edi.unedifact.model.UNEdifactInterchange;
import org.milyn.smooks.edi.unedifact.model.r41.UNEdifactInterchange41;
import org.milyn.smooks.edi.unedifact.model.r41.UNEdifactMessage41;
import org.milyn.SmooksException;
import java.io.FileInputStream;
import java.io.StringWriter;
import org.xml.sax.SAXException;
/**
 * Clase para leer archivos y obetner Precargas
 * @author Fernando
 */
public class Archivos {

    /**
     * Método para leer datos de Precarga desde un CSV
     * @param f
     * @return
     * @throws IOException 
     */
    public static List<Precarga> leerCsvPrecarga(InputStream f) throws IOException {
        List<Precarga> listado = new LinkedList<>();//Se crea el linkedList
        List<StringTokenizer> auxiliar = new LinkedList<StringTokenizer>();//Se utiliza el StringTokenizer debido a el ";" que se general al leer cada fila del archivo
        CsvReader leerCsv = new CsvReader(f, Charset.forName("utf8"));//Se carga el archivo y se utiliza la codificaciÃ³n utf8
//        leerCsv.readHeaders();//Se lee la cabecera del archivo
        leerCsv.setDelimiter(';');
        while (leerCsv.readRecord()) {
            Precarga p = new Precarga();
            p.setBookingPrec(leerCsv.get(0));//Lee el booking
            if (leerCsv.get(1).length() > 0) { //Lee el total de espacios
                p.setTotalEspaciosPrec(Integer.parseInt(leerCsv.get(1)));
            }
            if (leerCsv.get(2).length() > 0) {//Lee el total de espacios disponibles
                p.setEspaciosDisponiblesPrec(Integer.parseInt(leerCsv.get(2)));
            }
            p.setTipoContenedorPrec(leerCsv.get(3));//Lee el tipo de contenedores
            String condicion = leerCsv.get(4);
            if (condicion.equals("FCL/LCL") || condicion.equals("LCL/FCL")) {
                p.setCondicionContenedorPrec("LCL/FCL");//Lee el tipo de contenedor
            } else {
                p.setCondicionContenedorPrec(condicion);//Lee el tipo de contenedor
            }

            if (leerCsv.get(5).length() > 0) {
                p.setImpExpIdPrec(leerCsv.get(5));//Lee el tipo de id del importador o exportador   
            }
            p.setImportadorPrec(leerCsv.get(6));//Lee el nombre del importador o exportador
            p.setDescripcionPrec(leerCsv.get(7));//Lee la descripciÃ³n
            if (leerCsv.get(8).length() > 0) {
                p.setPesoPrec(Double.parseDouble(leerCsv.get(8)));
            }
            p.setCargaPeligrosaPrec(leerCsv.get(9));
            p.setCodigoImoPre(leerCsv.get(10));

            if (leerCsv.get(11).length() > 0) {
                p.setTemperaturaPrec(Double.parseDouble(leerCsv.get(11)));
            }

            if (leerCsv.get(12).length() > 0) {
                p.setVentilacionPrec(Double.parseDouble(leerCsv.get(12)));
            }
            p.setPtoOrigenPrec(leerCsv.get(13));
            p.setPtoDestinoPrec(leerCsv.get(14));
            p.setPtoTransbordoPrec(leerCsv.get(15));
            listado.add(p);
        }

        leerCsv.close();
        return listado;
    }

    
       /**
     * Método para leer datos de Precarga de VACIOS desde un CSV
     * @param f
     * @return
     * @throws IOException 
     */
    public static List<PrecargaVacios> leerCsvPrecargaVacio(InputStream f) throws IOException {
        List<PrecargaVacios> listado = new LinkedList<>();//Se crea el linkedList
        List<StringTokenizer> auxiliar = new LinkedList<StringTokenizer>();//Se utiliza el StringTokenizer debido a el ";" que se general al leer cada fila del archivo
        CsvReader leerCsv = new CsvReader(f, Charset.forName("utf8"));//Se carga el archivo y se utiliza la codificaciÃ³n utf8
//        leerCsv.readHeaders();//Se lee la cabecera del archivo
        leerCsv.setDelimiter(';');
        while (leerCsv.readRecord()) {
            PrecargaVacios p = new PrecargaVacios();
            p.setBookingPrev(leerCsv.get(0));//Lee el booking
            if (leerCsv.get(1).length() > 0) { //Lee el total de espacios
                p.setTotalEspaciosPrev(Integer.parseInt(leerCsv.get(1)));
            }
            if (leerCsv.get(2).length() > 0) {//Lee el total de espacios disponibles
                p.setEspaciosDisponiblesPrev(Integer.parseInt(leerCsv.get(2)));
            }
            p.setTipoContenedorPrev(leerCsv.get(3));//Lee el tipo de contenedores
            p.setPtoOrigenPrev(leerCsv.get(4));
            p.setPtoDestinoPrev(leerCsv.get(5));
            p.setPtoTransbordoPrev(leerCsv.get(6));
            listado.add(p);
        }

        leerCsv.close();
        return listado;
    }

    /**
     * Método para leer datos de Precarga desde un archivo EDI
     * @param f
     * @return 
     */
    public static List<Precarga> leerEDIPrecarga(InputStream f) throws IOException, SAXException {
         List<Precarga> listado = new LinkedList<Precarga>();
        D95BInterchangeFactory factory = D95BInterchangeFactory.getInstance();
        InputStream stream = f;
        UNEdifactInterchange interchange;
        try {
            interchange = factory.fromUNEdifact(stream);
            if (interchange instanceof UNEdifactInterchange41) {
                UNEdifactInterchange41 interchange41 = (UNEdifactInterchange41) interchange;
                for (UNEdifactMessage41 messageWithControlSegments : interchange41.getMessages()) {
                    System.out.println("\tMessage Name: " + messageWithControlSegments.getMessageHeader().getMessageIdentifier().getId());

                    Object messageObj = messageWithControlSegments.getMessage();
                    if (messageObj instanceof org.milyn.edi.unedifact.d95b.COPARN.Coparn) {
                        Precarga p=new Precarga();
                        org.milyn.edi.unedifact.d95b.COPARN.Coparn invoice = (org.milyn.edi.unedifact.d95b.COPARN.Coparn) messageObj;
                        //indica el modo si es reemplazar las cargas, nuevo, modificacion
                        String modo = invoice.getBeginningOfMessage().getMessageFunctionCoded();
                        String booking = invoice.getReference().get(0).getReference().getReferenceNumber();
                        //String puertoOrigen = invoice.getSegmentGroup1().get(0).getPlaceLocationIdentification().get(1).getLocationIdentification().getPlaceLocationIdentification();
                        String exportador = invoice.getSegmentGroup2().get(0).getNameAndAddress().getNameAndAddress().getNameAndAddressLine1();
                        String ruc = invoice.getSegmentGroup2().get(0).getNameAndAddress().getPartyIdentificationDetails().getPartyIdIdentification();
                        String producto = invoice.getSegmentGroup3().get(0).getFreeText().get(0).getTextLiteral().getFreeText1();
                        String tipoContenedor = invoice.getSegmentGroup7().get(0).getEquipmentDetails().getEquipmentSizeAndType().getEquipmentSizeAndTypeIdentification();
                        String condicionContenedor = "", condicionAux = "";
                        condicionAux = invoice.getSegmentGroup7().get(0).getEquipmentDetails().getFullEmptyIndicatorCoded();
                        if (condicionAux.equals("5") || condicionAux.equals("8")) {
                            condicionContenedor = "FCL/FCL";
                        } else {
                            if (condicionAux.equals("7")) {
                                condicionContenedor = "LCL/FCL";
                            }
                        }
                        String totalEspacios = invoice.getSegmentGroup7().get(0).getNumberOfUnits().getNumberOfUnitDetails().getNumberOfUnits().toString();
                        String puertoDestino = invoice.getSegmentGroup7().get(0).getPlaceLocationIdentification().get(0).getLocationIdentification().getPlaceLocationIdentification();
                        String puertoTrasbordo = invoice.getSegmentGroup7().get(0).getPlaceLocationIdentification().get(2).getLocationIdentification().getPlaceLocationIdentification();
                        String puertoOrigen = invoice.getSegmentGroup7().get(0).getPlaceLocationIdentification().get(1).getLocationIdentification().getPlaceLocationIdentification();
                        String temperatura="0";
                        if(invoice.getSegmentGroup7().get(0).getTemperature().size()>0){
                            temperatura=invoice.getSegmentGroup7().get(0).getTemperature().get(0).getTemperatureSetting().getTemperatureSetting().replace(',', '.');
                        }
                        p.setBookingPrec(booking);
                        p.setDescripcionPrec(producto);
                        p.setImportadorPrec(exportador);
                        p.setImpExpIdPrec(ruc);
                        p.setCondicionContenedorPrec(condicionContenedor);
                        p.setTipoContenedorPrec(tipoContenedor);
                        p.setPtoOrigenPrec(puertoOrigen);
                        p.setPtoDestinoPrec(puertoDestino);
                        p.setPtoTransbordoPrec(puertoTrasbordo);
                        p.setTemperaturaPrec(Double.parseDouble(temperatura));
                        p.setTipoPrec("E");
                        p.setCargaPeligrosaPrec("N");
                        p.setTotalEspaciosPrec(Integer.parseInt(totalEspacios));
                        p.setEspaciosDisponiblesPrec(Integer.parseInt(totalEspacios));
                        p.setPesoPrec(0d);
                        p.setVentilacionPrec(0d);
                        listado.add(p);
                    }
                }
            }
        } finally {
            stream.close();
        }
        StringWriter ediOutStream = new StringWriter();
        factory.toUNEdifact(interchange, ediOutStream);
        System.out.println("\n\nSerialized Interchanged:");
        System.out.println("\t" + ediOutStream);
        System.out.println("\n\n**** RUN INSIDE YOUR IDE... Set a breakpoint in the example.Main Class... inspect values etc !!\n");
        return listado;
    }

    /**
     * Método para obtener la extension del archivo
     * @param nombreArchivo
     * @param tamanio
     * @return 
     */
    public static String tomarExtension(String nombreArchivo, int tamanio) {
        String ext = nombreArchivo.substring(nombreArchivo.length() - tamanio, nombreArchivo.length());
        return ext.trim().toUpperCase();
    }
}
