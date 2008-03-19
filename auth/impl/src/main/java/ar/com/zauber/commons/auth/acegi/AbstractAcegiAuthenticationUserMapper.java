/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.auth.acegi;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
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
public abstract class AbstractAcegiAuthenticationUserMapper<T> 
       implements AuthenticationUserMapper<T> {
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
    
    /** @see AuthenticationUserMapper#getRoles() */
    public Set<String> getRoles() {
        final SecurityContext context = SecurityContextHolder.getContext();
        final Authentication auth = context.getAuthentication();
        final Set<String>ret = new HashSet<String>();
        if(auth.isAuthenticated()) {
            final GrantedAuthority [] roles = auth.getAuthorities();
            for (int i = 0; i < roles.length; i++) {
                ret.add(roles[i].getAuthority());
            }
        }
            
        return ret;
    }
}
