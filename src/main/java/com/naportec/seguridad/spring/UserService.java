package com.naportec.seguridad.spring;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import com.naportec.seguridad.entidades.SRight;
import com.naportec.seguridad.entidades.SRole;
import com.naportec.seguridad.entidades.SUser;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public interface UserService {

    public Collection<SRight> getRightsByUser(SUser user);

    public SUser getUserByLoginname(final String userName);

    public SUser getUserByLoginname(String userName, String password);
    
    public void setIntentosLogin(SUser user);
    
    public void clearIntentosLogin(SUser user);
    
    public boolean getEnabledUser(SUser user);
    
    public List<SRole> getRolesByUser(SUser aUser) ;
    
    public boolean realizarEncuesta(String ruc)throws SQLException ;
    
    public void configuracionPermisos(HttpSecurity http)throws Exception;

}
