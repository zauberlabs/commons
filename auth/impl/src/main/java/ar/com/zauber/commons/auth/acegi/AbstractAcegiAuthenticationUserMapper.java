/*
 * Copyright (c) 2005 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.auth.acegi;

import net.sf.acegisecurity.Authentication;
import net.sf.acegisecurity.context.ContextHolder;
import net.sf.acegisecurity.context.security.SecureContextImpl;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.auth.AuthenticationUserMapper;



/**
 * Implementation for Acegi
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
    private String getUsername() {
        final SecureContextImpl context = (SecureContextImpl)
                                           ContextHolder.getContext();
        String ret = null;
        
        context.validate();
        // TODO leer un poco mas sobre acegis para emprolijar el codigo
        final Authentication auth = context.getAuthentication();
        if(auth.isAuthenticated()) {
            final Object o = auth.getPrincipal();
            if(o instanceof String) {
                ret = (String)o;
            } else {
                ret = ((net.sf.acegisecurity.providers.dao.User)
                        auth.getPrincipal()).getUsername();
            }
            Validate.notNull(ret);
        } else {
            throw new IllegalStateException("someone didn't "
                    + "authenticate the user. Shame on ...!!");
        }
        
        return ret;
    }
}
