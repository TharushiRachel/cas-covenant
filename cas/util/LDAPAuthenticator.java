package com.itechro.cas.util;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.config.CasProperties;
import com.itechro.cas.model.dto.master.UserDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.ldap.InitialLdapContext;
import java.util.Hashtable;

public class LDAPAuthenticator {

    private static final Logger LOG = LoggerFactory.getLogger(LDAPAuthenticator.class);

    private CasProperties casProperties;

    public LDAPAuthenticator(CasProperties casProperties) {
        this.casProperties = casProperties;
    }

    public UserDTO getAuthenticatedUser(String userName, String password) {

        String pwDescription = StringUtils.isNotEmpty(password) ? "***** : " + password.length() : "NULL";
        //LOG.info("START AUTH --------->>>> : {} : {}", userName, pwDescription);

        UserDTO userDTO = null;
        DirContext ctx = null;
        try {
            // creating environment for initial context
            Hashtable<String, String> env = new Hashtable<String, String>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, this.casProperties.getLdapServerURL());
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.SECURITY_CREDENTIALS, password);

            LOG.info("LDAPAuthenticator ______________ : {}", this.casProperties.getAuthenticationType());

            if (this.casProperties.getAuthenticationType().equals("INITIAL_LDAP_CONTEXT")) {
                env.put(Context.SECURITY_PRINCIPAL, userName.concat(this.casProperties.getUserNameTail()));
                LOG.info("LDAPAuthenticator : START InitialLdapContext");
                ctx = new InitialLdapContext(env, null);
                LOG.info("LDAPAuthenticator : END InitialLdapContext");
            } else if (this.casProperties.getAuthenticationType().equals("INITIAL_DIR_CONTEXT")) {
                env.put(Context.SECURITY_PRINCIPAL, userName);
                LOG.info("LDAPAuthenticator : START InitialDirContext");
                ctx = new InitialDirContext(env);
                LOG.info("LDAPAuthenticator : END InitialDirContext");
            }

            // env.put(Context.SECURITY_PRINCIPAL, "uid=" + userName);
            //env.put(Context.SECURITY_PRINCIPAL, "uid=" + userName + "," + USER_CONTEXT);
            //env.put(Context.SECURITY_PRINCIPAL, USER_CONTEXT);
            //  env.put(Context.SECURITY_PRINCIPAL, "CN=" + userName + "," + this.casProperties.getLdapUserContext());

            LOG.info("LDAPAuthenticator : Authenticated: " + (ctx != null));

            userDTO = new UserDTO();
            userDTO.setUserName(userName);
            userDTO.setDisplayName(userName);
            userDTO.setLastName(userName);
            userDTO.setStatus(AppsConstants.Status.ACT);

//            // get more attributes about this user
//            SearchControls scs = new SearchControls();
//            scs.setSearchScope(SearchControls.SUBTREE_SCOPE);
//            String[] attrNames = {"displayName", "givenName", "cn"};
//            scs.setReturningAttributes(attrNames);
//
//            //NamingEnumeration nes = ctx.search(USER_CONTEXT, "uid=" + userName, scs);
//            NamingEnumeration nes = ctx.search(this.casProperties.getLdapUserContext(), "CN=" + userName, scs);
//            if (nes.hasMore()) {
//                userDTO = new UserDTO();
//                Attributes attrs = ((SearchResult) nes.next()).getAttributes();
//
//                String displayName = (String) attrs.get("displayName").get();
//                String givenName = (String) attrs.get("givenName").get();
//
//                System.out.println("displayName: " + displayName);
//                System.out.println("cn: " + attrs.get("cn").get());
//
//                userDTO.setUserName(displayName);
//                userDTO.setDisplayName(displayName);
//                userDTO.setLastName(givenName);
//                userDTO.setStatus(AppsConstants.Status.ACT);
//            }
        } catch (NamingException e) {
            e.printStackTrace();
            //LOG.error("ERROR : error occur while searching user {}", userName, e);
        } finally {
            if (ctx != null)
                try {
                    ctx.close();
                } catch (NamingException ex) {
                    //LOG.error("ERROR : error occur while closing connection {}", userName, ex);
                }
        }

        //LOG.info("END AUTH --------->>>> : {} : {}", userName, pwDescription);
        return userDTO;
    }
}
