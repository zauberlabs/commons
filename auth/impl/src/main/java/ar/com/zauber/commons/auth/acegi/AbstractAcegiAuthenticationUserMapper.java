/*
 * Copyright (c) 2005 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.auth.acegi;

import javax.servlet.http.HttpServletRequest;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;
import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.auth.AuthenticationUserMapper;



/**
 * Implementación de {@link AuthenticationUserMapper} para usar
 * en arquitecturas que usan acegi.
 * 
 * @author Juan F. Codagnone
 * @since Sep 29, 2005
 */
public abstract class AbstractAcegiAuthenticationUserMapper<T> implements AuthenticationUserMapper<T> {
    /** the anonymous username */
    private final String anonymousString;
    
    /**
     * Creates the AcegiAuthenticationUserMapper.
     *
     * @param userService <code>UserService</code> used to resolve users
     * @param anonymousString the username of the anonymous user
     */
    public AbstractAcegiAuthenticationUserMapper(final String anonymousString) {
        Validate.notNull(anonymousString);
        this.anonymousString = anonymousString;
    }
    
    /** @see AuthenticationUserMapper#getUser(HttpServletRequest) */
    public abstract T getUser();
    
    /**  @see AuthenticationUserMapper#isAnonymous() */
    public final boolean isAnonymous() {
        return getUsername().equals(anonymousString);
    }
    
    /** @return the username of the current session */
    protected String getUsername() {
        final SecurityContext context = SecurityContextHolder
                .getContext();
        String ret = null;
        
        // context.validate();
        // TODO leer un poco mas sobre acegis para emprolijar el codigo
        final Authentication auth = context.getAuthentication();
        if(auth.isAuthenticated()) {
            final Object o = auth.getPrincipal();
            if(o instanceof String) {
                ret = (String)o;
            } else {
                ret = ((UserDetails)auth.getPrincipal()).getUsername();
            }
            Validate.notNull(ret);
        } else {
            throw new IllegalStateException("someone didn't "
                    + "authenticate the user. Shame on ...!!");
        }
        
        return ret;
    }
}
