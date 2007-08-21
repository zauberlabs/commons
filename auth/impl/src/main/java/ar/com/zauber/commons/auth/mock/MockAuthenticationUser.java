/*
 * Copyright (c) 2005 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.auth.mock;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.auth.AuthenticationUserMapper;



/**
 * Mock implementation usefull in non production environment.
 *
 * @author Juan F. Codagnone
 * @since Nov 11, 2005
 */
public class MockAuthenticationUser<T> implements AuthenticationUserMapper<T> {
    /** user to return */
    private final T user;

    /**
     * Creates the MockAuthenticationUser.
     *
     * @param user user to return in {@link #getUser()}.
     */
    public MockAuthenticationUser(final T user) {
        Validate.notNull(user);
        this.user = user;
    }
    
    /** @see AuthenticationUserMapper#getUser() */
    public final T getUser() {
        
        return user;
    }

    /** @see AuthenticationUserMapper#isAnonymous() */
    public final boolean isAnonymous() {
        return false;
    }

    /** @see AuthenticationUserMapper#getRoles() */
    public Set<String> getRoles() {
        return new HashSet<String>();
    }

}
