package com.naportec.seguridad.spring;

import com.naportec.seguridad.entidades.SRole;
import com.naportec.seguridad.entidades.SUser;
import com.naportec.utilidades.otros.Aes;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.ResourceBundle;
import javax.faces.context.FacesContext;

import javax.naming.ldap.LdapContext;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import org.springframework.security.authentication.BadCredentialsException;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private transient UserService userService;
    public UserDetails _userDetails;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        SUser useraux = getUserByLoginname(username);
        Collection<GrantedAuthority> grantedAuthorities = null;

        if (useraux.getUsrTipoUsuario().equals("U")) {
            LdapContext ctx = null;
            try {
                ctx = getLdapContext(username, password);
            } catch (NamingException ex) {
                this.getUserService().setIntentosLogin(useraux);
                throw new UsernameNotFoundException("Usuario no valido en Active Directory");
            }
            if (ctx == null) {
                this.getUserService().setIntentosLogin(useraux);
                throw new UsernameNotFoundException("Usuario no valido en Active Directory");
            } else {
                this.getUserService().clearIntentosLogin(useraux);
                grantedAuthorities = getGrantedAuthority(useraux);
                Authentication auth = new UsernamePasswordAuthenticationToken(username, password, grantedAuthorities);
                return auth;
            }
        } else {
            SUser userauxData = getUserByLogin(username, Aes.encryptData(password));
            if (userauxData == null) {
                this.getUserService().setIntentosLogin(useraux);
                throw new BadCredentialsException("Usuario o Contraseña están Incorrectos");
            }
            if (!this.getUserService().getEnabledUser(useraux)) {
                throw new BadCredentialsException("Usuario Bloqueado por límites de intentos de Acceso Fallidos. Por favor espere 5 minutos y vuelva a intentarlo utilizando la clave correcta");
            }
            try {
                if (userauxData.getCodigoSoli() != null) {
                    if (this.getUserService().realizarEncuesta(userauxData.getCodigoSoli().getIdentificacionSoli())) {
                        FacesContext.getCurrentInstance().getExternalContext().redirect("http://www.naportec.com.ec/encuesta/login.php?type=importaciones");
                    }
                }
            } catch (SQLException ex) {
                throw new BadCredentialsException("No hemos podido conectarnos con la base de encuestas.");
            } catch (IOException ex) {
                throw new BadCredentialsException("No hemos podido redireccionar al portal de encuestas.");
            }
            this.getUserService().clearIntentosLogin(useraux);
            grantedAuthorities = getGrantedAuthority(useraux);
            Authentication auth = new UsernamePasswordAuthenticationToken(username, password, grantedAuthorities);
            return auth;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    public LdapContext getLdapContext(String username, String password) throws NamingException {
        if (password == null || password.trim().length() == 0) {
            throw new UsernameNotFoundException("Por favor llene el usuario y la contraseña");
        }
        LdapContext ctx = null;
        String urlldap = "la.ad.dole.net";
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "Simple");
        env.put(Context.SECURITY_PRINCIPAL, username + "@" + urlldap);
        env.put(Context.SECURITY_CREDENTIALS, password);
        env.put(Context.PROVIDER_URL, "ldap://" + urlldap);
        ctx = new InitialLdapContext(env, null);
        System.out.println("Connection Successful.");
        return ctx;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Gets the User object by his stored userName.<br>
     *
     * @param userName
     * @return
     */
    public SUser getUserByLoginname(final String userName) {
        return getUserService().getUserByLoginname(userName);
    }

    public SUser getUserByLogin(final String userName, final String password) {
        return getUserService().getUserByLoginname(userName, password);
    }

    /**
     * Fills the GrantedAuthorities List for a specified user.<br>
     * 1. Gets a unique list of rights that a user have.<br>
     * 2. Creates GrantedAuthority objects from all rights. <br>
     * 3. Creates a GrantedAuthorities list from all GrantedAuthority
     * objects.<br>
     *
     * @param user
     * @return
     */
    private Collection<GrantedAuthority> getGrantedAuthority(SUser user) {
        // get the list of rights for a specified user.
        Collection<SRole> rights = getUserService().getRolesByUser(user);

        ArrayList<GrantedAuthority> rechteGrantedAuthorities = new ArrayList<GrantedAuthority>(
                rights.size());

        // now create for all rights a GrantedAuthority
        // and fill the GrantedAuthority List with these authorities.
        for (SRole right : rights) {
            rechteGrantedAuthorities.add(new GrantedAuthorityImpl(right
                    .getRolShortdescription()));
        }
        return rechteGrantedAuthorities;
    }

    /**
     * Lee un parametro del archivo Properties
     *
     * @author jevm
     * @param propertiesfile (String, archivo Propterties) parameter (String,
     * parametro a recuperar)
     * @throws Exception
     */
    public static String getParameter(String propertiesfile, String parameter)
            throws Exception {

        String ARCHIVO_PROPERTIES = "avante." + propertiesfile;
        ResourceBundle rb = ResourceBundle.getBundle(ARCHIVO_PROPERTIES);
        try {
            return rb.getString(parameter);
        } catch (Exception e) {
            throw e;
        }
    }

}
