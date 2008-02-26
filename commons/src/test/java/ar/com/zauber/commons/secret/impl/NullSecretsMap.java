/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.secret.impl;

import java.util.Date;

import org.apache.commons.lang.NotImplementedException;

import ar.com.zauber.commons.dao.exception.NoSuchEntityException;
import ar.com.zauber.commons.secret.*;


/**
 * Null implementation for {@link ar.com.zauber.commons.secret.SecretsMap}
 *
 * @author Juan F. Codagnone
 * @since Nov 26, 2005
 * @param <T> type
 */
public class NullSecretsMap<T> implements SecretsMap<T> {

    /** @see SecretsMap#register(T, java.util.Date) */
    public final String register(final T guest, final Date expirationDate) {
        throw new NotImplementedException("not implemented");
    }

    /** @see SecretsMap#register(T) */
    public final String register(final T guest) {
        throw new NotImplementedException("not implemented");
    }

    /** @see SecretsMap#unregister(T) */
    public final void unregister(final T guest) {
        // nothing to do
    }

    /** @see SecretsMap#peekSecret(T) */
    public final String peekSecret(final T guest)
            throws NoSuchEntityException {
        throw new NoSuchEntityException(guest);
    }

    /** @see SecretsMap#safeGetSecret(T) */
    public final String safeGetSecret(final T guest) {
        throw new NotImplementedException("not implemented");
    }

    /** @see SecretsMap#getT(java.lang.String) */
    public final T getT(final String secret)
            throws NoSuchEntityException {
        throw new NoSuchEntityException(secret);
    }

    /** @see SecretsMap#cleanup() */
    public void cleanup() {
        // nothing to do

    }

    /** @see SecretsMap#getSecretGenerator() */
    public final SecretGenerator getSecretGenerator() {
        throw new NotImplementedException("not implemented");
    }

    /** @see SecretsMap#getExpirationDatePolicy() */
    public final ExpirationDatePolicy getExpirationDatePolicy() {
        throw new NotImplementedException("not implemented");
    }

    /** @see SecretsMap#getExpirationDateValidator() */
    public final ExpirationDateValidator getExpirationDateValidator() {
        throw new NotImplementedException("not implemented");
    }

    /** @see SecretsMap#removeByKey(java.lang.String) */
    public void removeByKey(String key) {
        throw new NotImplementedException("not implemented");
    }
    
    /** @see SecretsMap#getByKey(java.lang.String) */
    public final T getByKey(final String key) {
        throw new NotImplementedException("not implemented");
    }

}
