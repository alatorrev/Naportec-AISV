package com.naportec.utilidades.otros;

import java.io.Serializable;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 * Clase para enviar correos sencillos solo texto
 * @author Fernando
 */
public class Email implements Serializable {

    private final Properties properties = new Properties();
    private Session session;

    public Email() {

    }

    /**
     * Inicializar Datos del servidor SMTP
     */
    private void init() {
        properties.put("mail.smtp.host", "172.26.32.55");
        properties.put("mail.smtp.port", 25);
//        properties.put("mail.smtp.auth", true);
//        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.mail.sender", "aisv@dole.com");
//        properties.put("mail.smtp.password", "usuario");
//        properties.put("mail.smtp.user", "usuario");
        session = Session.getDefaultInstance(properties);
    }

    /**
     * Método para enviar correo a varios correosseparados por ;
     * @param destino
     * @param asunto
     * @param mensaje
     * @throws MessagingException 
     */
    public void send(String destino, String asunto, String mensaje) throws MessagingException {
        init();
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress((String) properties.get("mail.smtp.mail.sender")));
        String d[] = destino.split(";");
        Address dir[]=new Address[d.length];
        for(int i=0;i<d.length;i++){
            dir[i]=new InternetAddress(d[i]);
        }
        message.addRecipients(Message.RecipientType.TO, dir);
        message.setSubject(asunto);
        //message.setText(mensaje);
        message.setContent(mensaje, "text/html");
        Transport t = session.getTransport("smtp");
        t.connect();
//        t.connect((String) properties.get("mail.smtp.user"), (String) properties.get("mail.smtp.password"));
        t.sendMessage(message, message.getAllRecipients());
        t.close();
    }

}
