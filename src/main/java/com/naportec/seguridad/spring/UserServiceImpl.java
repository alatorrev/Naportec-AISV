package com.naportec.seguridad.spring;

import com.naportec.seguridad.entidades.SGroup;
import com.naportec.seguridad.entidades.SRight;
import com.naportec.seguridad.entidades.SRole;
import com.naportec.seguridad.entidades.SUser;
import com.naportec.utilidades.logica.Conexion;
import com.naportec.utilidades.otros.Fechas;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.apache.commons.collections.list.SetUniqueList;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Service implementation for methods that depends on <b>Users</b>.<br>
 *
 * @author bbruhns
 * @author sgerth
 */
@PersistenceContext(
        name = "persistence/LogicalName",
        unitName = "com.naportec_Aisv" // defined in a persistence.xml file
)
public class UserServiceImpl implements UserService {

    private EntityManager em;

    /*
     * Gets the rights for a specified user.<br> (non-Javadoc)
     * 
     * @see
     * de.forsthaus.backend.service.UserService#getRightsByUser(de.forsthaus
     * .backend .model.SecUser)
     */
    public Collection<SRight> getRightsByUser(SUser user) {

        List rightList = new ArrayList<SRight>();

        // 1. erst die zum User zugeteilten Rollen ermitteln
        // 1. First get the roles that are attached to a user
        List<SRole> listRoles = new ArrayList<SRole>();
        listRoles = getRolesByUser(user);

        // 2. die zu den Rollen die zugehÃ¯Â¿Â½rigen Gruppen ermitteln
        // 2. get the groups that belongs to the roles
        List<SGroup> listGroup = new ArrayList<SGroup>();

        if (listRoles != null) {
            for (SRole role : listRoles) {

                List<SGroup> tmpListGroup = new ArrayList<SGroup>();
                tmpListGroup = getGroupsByRole(role);

                if (tmpListGroup != null) {
                    for (SGroup secGroup : tmpListGroup) {
                        listGroup.add(secGroup);
                    }
                }

            }
        }

        // 3. zu den Gruppen die zugeordneten Rechte ermitteln
        // 3. get the rights that belongs to the groups
        List<SRight> listRight = new ArrayList<SRight>();

        if (listGroup != null) {
            for (SGroup group : listGroup) {

                List<SRight> tmpListRight = new ArrayList<SRight>();
                tmpListRight = getRightsByGroup(group);

                if (tmpListRight != null) {
                    for (SRight secRight : tmpListRight) {
                        listRight.add(secRight);
                    }
                }
            }
        }

        // 4. Doppelte Rechte unterdrÃ¯Â¿Â½cken
        // 4. filter double rights out
        if (listRight != null) {

            List decorateList = SetUniqueList.decorate(rightList);
            for (int i = 0; i < listRight.size(); i++) {
                decorateList.add(listRight.get(i));
            }
        }

        return rightList;

    }

//     List<SUser> user = getEm()
//                .createQuery(
//                        "select u from SUser u where u.usrLoginname = :username AND u.usrPassword = :password AND u.usrEnabled=true")
//                .setParameter("username", userName)
//                .setParameter("password", password).getResultList();
    @Override
    public SUser getUserByLoginname(String userName, String password) {
        List<SUser> user = getEm()
                .createQuery(
                        "select u from SUser u where u.usrLoginname = :username AND u.usrPassword = :password AND u.usrEnabled=true")
                .setParameter("username", userName)
                .setParameter("password", password)
                .getResultList();
        if (user == null || user.isEmpty()) {
            System.out.println("El usuario: " + userName + " no existe");
            return null;
        } else {
            return (SUser) user.get(0);
        }
    }

    public SUser getUserByLoginname(String userName) {

        List<SUser> user = getEm()
                .createQuery(
                        "select u from SUser u where u.usrLoginname = :username AND u.usrEnabled=true")
                .setParameter("username", userName).getResultList();
        if (user == null || user.isEmpty()) {
            System.out.println("El usuario: " + userName + " no existe");
            throw new UsernameNotFoundException("El Usuario '" + userName
                    + "' no Existe...");
        } else {
            return (SUser) user.get(0);
        }

    }

    public List<SGroup> getGroupsByRole(SRole aRole) {

        StringBuffer sb = new StringBuffer();

        sb.append("Select g ");
        sb.append("From ");
        sb.append("SGroup g, ");
        sb.append("SRolegroup rg ");
        sb.append("Where rg.sGroup.grpId = g.grpId ");
        sb.append("And  rg.sRole.rolId =" + aRole.getRolId());

        List roles = getEm().createQuery(sb.toString()).getResultList();
        if (roles.isEmpty()) {
            return null;
        } else {
            return (List<SGroup>) roles;
        }
    }

    public List<SRight> getRightsByGroup(SGroup group) {

        StringBuffer sb = new StringBuffer();

        sb.append("Select r ");
        sb.append("From SRight r ");
        sb.append("JOIN r.sGrouprightList gr ");
        sb.append("Where gr.sGroup.grpId =" + group.getGrpId());

        List rights = getEm().createQuery(sb.toString()).getResultList();

        if (rights.isEmpty()) {
            return null;
        } else {
            return (List<SRight>) rights;
        }

    }

    public List<SRole> getRolesByUser(SUser aUser) {
        /*
         * Query q = em.createQuery(
         * "SELECT p FROM Person p WHERE p.lastName = ?1 AND p.firstName = ?2");
         * q.setParameter(1, theSurname); q.setParameter(2, theForename);
         */

        StringBuffer sb = new StringBuffer();
        sb.append("Select r ");
        sb.append("From SRole r ");
        sb.append("JOIN r.sUserroleList ur ");
        sb.append("Where ur.sUser.usrId =" + aUser.getUsrId());

        List roles = getEm().createQuery(sb.toString()).getResultList();
        // if (roles.isEmpty()) {
        // return null;
        // } else {
        return (List<SRole>) roles;
        // }

    }

    /**
     * @return the em
     */
    public EntityManager getEm() {
        try {
            Context initCtx = new InitialContext();
            em = (javax.persistence.EntityManager) initCtx.lookup("java:comp/env/persistence/LogicalName");
            return em;
        } catch (NamingException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public void setIntentosLogin(SUser user) {
        UserTransaction ut = null;
        try {
            Context initCtx = new InitialContext();
            ut = (UserTransaction) initCtx.lookup("java:comp/UserTransaction");
            ut.begin();
            user.setUsrIntento(user.getUsrIntento() + 1);
            user.setUsrFechaIntento(new Date());
            this.getEm().merge(user);
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
            } catch (IllegalStateException ex1) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (SecurityException ex1) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (SystemException ex1) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex1);
            }

        }
    }

    @Override
    public boolean getEnabledUser(SUser user) {
        int datoBloqueo = 0;
        if (user.getUsrTipoUsuario().equals("U")) {
            datoBloqueo = 2;
        } else {
            datoBloqueo = 7;
        }
        UserTransaction ut = null;
        try {
            Context initCtx = new InitialContext();
            ut = (UserTransaction) initCtx.lookup("java:comp/UserTransaction");

            if (user.getUsrIntento() >= datoBloqueo) {
                Date actual = new Date();
                Date proceso = Fechas.sumarRestarMinFecha(user.getUsrFechaIntento(), 5);
                if (actual.compareTo(proceso) == 0) {
                    ut.begin();
                    user.setUsrIntento(0);
                    user.setUsrFechaIntento(new Date());
                    this.getEm().merge(user);
                    ut.commit();
                    return true;
                } else if (actual.compareTo(proceso) > 0) {
                    ut.begin();
                    user.setUsrIntento(0);
                    user.setUsrFechaIntento(new Date());
                    this.getEm().merge(user);
                    ut.commit();
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } catch (Exception ex) {
            try {
                ut.rollback();
            } catch (IllegalStateException ex1) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (SecurityException ex1) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (SystemException ex1) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex1);
            }
            return false;
        }
    }

    @Override
    public void clearIntentosLogin(SUser user) {
        UserTransaction ut = null;
        try {
            Context initCtx = new InitialContext();
            ut = (UserTransaction) initCtx.lookup("java:comp/UserTransaction");
            ut.begin();
            user.setUsrIntento(0);
            user.setUsrFechaIntento(new Date());
            this.getEm().merge(user);
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
            } catch (IllegalStateException ex1) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (SecurityException ex1) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (SystemException ex1) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex1);
            }

        }
    }

    @Override
    public boolean realizarEncuesta(String ruc) throws SQLException {
        Conexion conexion = new Conexion();
        conexion.conexion_Encuestas_Naportec();
        String sql = "SELECT Count(*) as contador FROM vi_answerinquiry where ruc = ?;";
        ResultSet rs = conexion.ejecutarSQLSelect(sql,ruc);
        int cont = 0;
        while (rs.next()) {
            cont = rs.getInt(1);
            System.err.println("CANTIDAD DE REGISTROS:" + cont);
        }
        if(cont!=0){
            return true; 
        }else{
            return false;
        }
          
    }

    @Override
    public void configuracionPermisos(HttpSecurity http) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p.rol_shortdescription");
        sql.append(",stuff(( ");
        sql.append("SELECT distinct(b.pagina)+'.html,' ");
        sql.append("FROM permisos b ");
        sql.append("WHERE b.rol_shortdescription = p.rol_shortdescription ");
        sql.append("FOR XML PATH('') ");
        sql.append("), 1, 1, '') paginas ");
        sql.append("FROM permisos p GROUP BY p.rol_shortdescription");

        List<Object[]> lista = getEm().createNativeQuery(sql.toString()).getResultList();
        for (Object[] o : lista) {
            String acceso = "hasRole('" + o[0] + "')";
            String paginas = o[1].toString();
            paginas = "/" + paginas.substring(0, paginas.length() - 1);
            http.authorizeRequests().antMatchers(paginas.split("\\,")).access(acceso).and().csrf().disable();
        }
        http.authorizeRequests().antMatchers("/seguridad/admin.html").fullyAuthenticated();
    }
}
