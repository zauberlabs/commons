/*
 * Copyright (c) 2008 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.repository.utils;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.auth.AuthenticationUserMapper;

/**
 * {@link AuthenticationUserMapper} util para no depender de commons-auth,
 * y poder usarlo de prueba, o en entornos batch (por ejemplo populators
 * que no tiene el contexto acegi)
 *
 * @author Juan F. Codagnone
 * @since Mar 22, 2008
 */
public class AnonymousAuthenticationUserMapper<T>
       implements AuthenticationUserMapper<String> {
    private final Set<String> empty = new HashSet<String>();
    private final String username;

    /** constructor */
    public AnonymousAuthenticationUserMapper(final String username) {
        Validate.notEmpty(username);
        this.username = username;
    }
    /** @see AuthenticationUserMapper#getRoles() */
    public final Set<String> getRoles() {
        return empty;
    }

    /** @see AuthenticationUserMapper#getUser() */
    public final String getUser() {
        return username;
    }

    /** @see AuthenticationUserMapper#isAnonymous() */
    public final boolean isAnonymous() {
        return false;
    }
}
