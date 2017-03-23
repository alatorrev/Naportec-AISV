/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.utilidades.mail;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Clase utilidad para e envio de correos electrónicos
 *
 * @author Fernando
 */
public class UtilMail implements Serializable {

    /**
     * Algunas constantes
     */
    static public int SIMPLE = 0;
    static public int MULTIPART = 1;
    static public int AUTENTICACION = 0;
    /**
     * Algunos mensajes de error
     */
    public static String ERROR_01_LOADFILE = "Error al cargar el fichero";
    public static String ERROR_02_SENDMAIL = "Error al enviar el mail";
    /**
     * Variables
     */
    private Properties props = new Properties();
    private String host, protocol, user, password;
    private String from, content, to, cco;
    private String subject = "";
    /**
     * MultiPart para crear mensajes compuestos
     */
    MimeMultipart multipart = new MimeMultipart();
// -----

    /**
     * Constructor
     *
     * @param host nombre del servidor de correo
     * @param user usuario de correo
     * @param password password del usuario
     */
    public UtilMail() {
        props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", "172.26.32.55");
        props.put("mail.smtp.port", "25");
        this.setFrom("aisv@dole.com");
    }

    /**
     * Muestra un mensaje de trazas
     *
     * @param metodo nombre del metodo
     * @param mensaje mensaje a mostrar
     */
    static public void trazas(String metodo, String mensaje) {
// TODO: reemplazar para usar Log4J
        System.out.println("[" + UtilMail.class.getName() + "][" + metodo
                + "]:[" + mensaje + "]");
    }
// -----

    /**
     * Carga el contenido de un fichero de texto HTML en un String
     *
     * @param pathname ruta del fichero
     * @return un String con el contenido del fichero
     * @throws Exception Excepcion levantada en caso de error
     */
    static public String loadHTMLFile(String pathname) throws IOException {
        String content = "";
        File f = null;
        BufferedReader in = null;
        try {
            f = new File(pathname);
            if (f.exists()) {
                long len_bytes = f.length();
                trazas("loadHTMLFile", "pathname:" + pathname + ", len:" + len_bytes);
            }
            in = new BufferedReader(new FileReader(f));
            String str;
            while ((str = in.readLine()) != null) {
// process(str);
                str = str.trim();
                String d = new String(str.getBytes("UTF-8"));
                content = content + d;
            }
            in.close();
            return content;
        } catch (IOException e) {
            String MENSAJE_ERROR = ERROR_01_LOADFILE + ": ['" + pathname + "'] : " + e.toString();
            throw new IOException(MENSAJE_ERROR);
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    /**
     * Añade el contenido base al multipart
     *
     * @throws Exception Excepcion levantada en caso de error
     */
    public void addContentToMultipart() throws Exception {
// first part (the html)
        BodyPart messageBodyPart = new MimeBodyPart();
        String htmlText = this.getContent();
        messageBodyPart.setContent(htmlText, "text/html");
// add it
        this.multipart.addBodyPart(messageBodyPart);
    }
// -----

    /**
     * Añade el contenido base al multipart
     *
     * @param htmlText contenido html que se muestra en el mensaje de correo
     * @throws Exception Excepcion levantada en caso de error
     */
    public void addContent(String htmlText) throws MessagingException {
// first part (the html)
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(htmlText, "text/html");
// add it
        this.multipart.addBodyPart(messageBodyPart);
    }
// -----

    /**
     * Añade al mensaje un cid:name utilizado para guardar las imagenes
     * referenciadas en el HTML de la forma <img src=cid:name />
     *
     * @param cidname identificador que se le da a la imagen. suele ser un
     * string generado aleatoriamente.
     * @param pathname ruta del fichero que almacena la imagen
     * @throws Exception excepcion levantada en caso de error
     */
    public void addCID(String cidname, String pathname) throws Exception {
        DataSource fds = new FileDataSource(pathname);
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setDataHandler(new DataHandler(fds));
        messageBodyPart.setHeader("Content-ID", "<" + cidname + ">");
        this.multipart.addBodyPart(messageBodyPart);
    }
// ----

    /**
     * Añade un attachement al mensaje de email
     *
     * @param pathname ruta del fichero
     * @throws Exception excepcion levantada en caso de error
     */
    public void addAttach(String pathname) throws Exception {
        File file = new File(pathname);
        BodyPart messageBodyPart = new MimeBodyPart();
        DataSource ds = new FileDataSource(file);
        messageBodyPart.setDataHandler(new DataHandler(ds));
        messageBodyPart.setFileName(file.getName());
        messageBodyPart.setDisposition(Part.ATTACHMENT);
        this.multipart.addBodyPart(messageBodyPart);
    }

    public void addAttach(File file) throws Exception {
        BodyPart messageBodyPart = new MimeBodyPart();
        DataSource ds = new FileDataSource(file);
        messageBodyPart.setDataHandler(new DataHandler(ds));
        messageBodyPart.setFileName(file.getName());
        messageBodyPart.setDisposition(Part.ATTACHMENT);
        this.multipart.addBodyPart(messageBodyPart);
    }
// ----

    /**
     * Envia un correo multipart
     *
     * @throws javax.mail.NoSuchProviderException
     */
    public void sendMultipart() throws NoSuchProviderException, MessagingException {
        Session mailSession = null;
        Transport transport = null;
        try {
            mailSession = Session.getDefaultInstance(this.props);
            //mailSession.setDebug(true);
            transport = mailSession.getTransport();
            MimeMessage message = new MimeMessage(mailSession);
            message.setSubject(this.getSubject());
            message.setFrom(new InternetAddress(this.getFrom()));
//            String listaCorreos[] = this.getTo().split(";");
//            Address[] lista = new Address[listaCorreos.length];
//            for (int i = 0; i < listaCorreos.length; i++) {
//                if (listaCorreos[i].trim().length() > 3) {
//                    lista[i] = new InternetAddress(listaCorreos[i].trim());
//                }
//            }
//            message.addRecipients(Message.RecipientType.TO, lista);
            if (this.getTo() != null) {
                message.addRecipients(Message.RecipientType.TO, listaAddresses(this.getTo()));
            }
            if (this.getCco() != null) {
                message.addRecipients(Message.RecipientType.BCC, listaAddresses(this.getCco()));
            }
             Address[] direcciones = message.getAllRecipients();
            message.setContent(multipart);
            transport.connect();
            transport.sendMessage(message,direcciones);
            transport.close();
            this.multipart = new MimeMultipart("related");
        } catch (MessagingException ex) {
            if (transport != null) {
                transport.close();
            }
            throw new MessagingException(ex.getMessage());
        }
    }

    public void inicializar() {
        this.multipart = new MimeMultipart("related");
    }

    public void cerraCorreo() {

    }
// -----

    /**
     *
     * @throws NoSuchProviderException
     * @throws MessagingException
     */
    public void send() throws NoSuchProviderException, MessagingException {
        Transport transport = null;
        try {
            Session mailSession = Session.getDefaultInstance(this.props);
            mailSession.setDebug(true);
            transport = mailSession.getTransport();
            MimeMessage message = new MimeMessage(mailSession);
            message.setSubject(this.getSubject());
            message.setFrom(new InternetAddress(this.getFrom()));
            message.setContent(this.getContent(), "text/html");

//            String[] listaCorreosTo = this.getTo().split(";");
//            List<String> listaSaneada = new LinkedList<>();
//            for (String listaCorreo : listaCorreosTo) {
//                if (listaCorreo.trim().length() > 5) {
//                    listaSaneada.add(listaCorreo.trim());
//                }
//            }
//            Address[] lista = new Address[listaSaneada.size()];
//            for (int i = 0; i < listaSaneada.size(); i++) {
//                lista[i] = new InternetAddress(listaSaneada.get(i));
//            }
            if (this.getTo() != null) {
                message.addRecipients(Message.RecipientType.TO, listaAddresses(this.getTo()));
            }
            if (this.getCco() != null) {
                message.addRecipients(Message.RecipientType.BCC, listaAddresses(this.getCco()));
            }
            Address[] direcciones = message.getAllRecipients();
            transport.connect();
            transport.sendMessage(message, direcciones);
            transport.close();
        } catch (NoSuchProviderException ex) {
            if (transport != null) {
                transport.close();
            }
            throw new NoSuchProviderException(ex.getMessage());
        } catch (MessagingException ex) {
            if (transport != null) {
                transport.close();
            }
            throw new MessagingException(ex.getMessage());
        }
    }

    public Address[] listaAddresses(String correos) throws AddressException {
        String[] listaCorreosTo = correos.split(";");
        List<String> listaSaneada = new LinkedList<>();
        for (String listaCorreo : listaCorreosTo) {
            if (listaCorreo.trim().length() > 5) {
                listaSaneada.add(listaCorreo.trim());
            }
        }
        Address[] lista = new Address[listaSaneada.size()];
        for (int i = 0; i < listaSaneada.size(); i++) {
            lista[i] = new InternetAddress(listaSaneada.get(i));
        }
        return lista;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    /**
     * @return the cco
     */
    public String getCco() {
        return cco;
    }

    /**
     * @param cco the cco to set
     */
    public void setCco(String cco) {
        this.cco = cco;
    }
}
