package com.naportec.seguridad.controladores;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * lase controlador que manejalas acciones de login
 *
 * @author Fernando
 */
public class LoginBean {

    /**
     * The user name.
     */
    private String userName = null;

    /**
     * The password.
     */
    private String password = null;

    /**
     * The remember me.
     */
    private String rememberMe = null;

    /**
     * The authentication manager.
     */
    private AuthenticationManager authenticationManager;

    /**
     * Método para logearse el sistema.
     *
     * @return the string
     */
    public void login() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        String url = extContext.encodeActionURL(
                context.getApplication().getViewHandler().getActionURL(context, "/seguridad/admin.html"));
        try {
            Authentication result = null;
            Authentication request = new UsernamePasswordAuthenticationToken(
                    this.getUserName().trim(), this.getPassword().trim());
            result = getAuthenticationManager().authenticate(request);
            SecurityContextHolder.getContext().setAuthentication(result);
            extContext.redirect(url);
        } catch (AuthenticationException e) {
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
        } catch (IOException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Cancel.
     *
     * @return the string
     */
    public String cancel() {
        return null;
    }

    /**
     * Método para desloguearse del sistema.
     */
    public void logout() {
        SecurityContextHolder.clearContext();
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        String url = extContext.encodeActionURL(
                context.getApplication().getViewHandler().getActionURL(context, "/login.html"));
        try {
            extContext.redirect(url);
        } catch (IOException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Gets the user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the user name.
     *
     * @param userName the new user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the remember me.
     *
     * @return the remember me
     */
    public String getRememberMe() {
        return rememberMe;
    }

    /**
     * Sets the remember me.
     *
     * @param rememberMe the new remember me
     */
    public void setRememberMe(String rememberMe) {
        this.rememberMe = rememberMe;
    }

    /**
     * Gets the authentication manager.
     *
     * @return the authentication manager
     */
    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    /**
     * Sets the authentication manager.
     *
     * @param authenticationManager the new authentication manager
     */
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

}
