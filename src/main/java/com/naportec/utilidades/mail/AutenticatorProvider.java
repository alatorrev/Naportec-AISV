package com.naportec.utilidades.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
/**
 * Clase utilidad de correos par autenticacion 
 * @author Fernando
 */
public class AutenticatorProvider extends Authenticator {

    String user;
    String pw;

    public AutenticatorProvider(String username, String password) {
        super();
        this.user = username;
        this.pw = password;
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, pw);
    }

}
