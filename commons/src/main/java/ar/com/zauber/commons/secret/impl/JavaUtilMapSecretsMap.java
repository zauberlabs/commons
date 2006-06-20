/*
 * Copyright (c) 2005 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.secret.impl;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.exception.NoSuchEntityException;
import ar.com.zauber.commons.secret.*;

/**
 * Implementation of the SecretsMap using a <code>java.util.Map</code>
 * 
 * @author Juan F. Codagnone
 * @since Jun 19, 2005
 * @param <T> type that holds
 */
public class JavaUtilMapSecretsMap<T> extends AbstractSecretsMap<T> 
                                      implements SecretsMap<T> {
    /** expiration date map ... */
    private final Map<String, T> tMap;
    /** expiration date map ... */
    private final Map<T, Date> expirationDateMap;

    /**
     * Creates the JavaUtilMapSecretsMap.
     * 
     * @param secretGenerator
     *            random source
     * @param tMap
     *            guests map
     * @param expirationDateMap
     *            expiration date map
     * @param expirationDatePolicy
     *            expiration date policy
     * @param expirationDateValidator
     *            expiration date validator
     */
    public JavaUtilMapSecretsMap(final SecretGenerator secretGenerator,
            final Map<String, T> tMap,
            final Map<T, Date> expirationDateMap,
            final ExpirationDatePolicy<T> expirationDatePolicy,
            final ExpirationDateValidator expirationDateValidator) {
        super(secretGenerator, expirationDatePolicy, expirationDateValidator);
        Validate.notNull(tMap, "tMap");
        Validate.notNull(expirationDateMap, "expirationMap");

        this.tMap = tMap;
        this.expirationDateMap = expirationDateMap;
    }

    /** @see SecretsMap#unregister(T) */
    public final void unregister(final T t) {
        String key = null;

        // TODO performance
        for(Entry<String, T> entry : tMap.entrySet()) {
            if(entry.getValue().equals(t)) {
                key = entry.getKey();
                break;
            }
        }

        if(key != null) {
            final T oldT = tMap.remove(key);
            Validate.isTrue(t.equals(oldT));
            final Date oldDate = expirationDateMap.get(t);
            if(oldDate != null) {
                expirationDateMap.remove(oldDate);
            }
        }
    }

    /** @see SecretsMap#cleanup() */
    public final void cleanup() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * @see AbstractSecretsMap#hasSecret(java.lang.String)
     */
    @Override
    protected final boolean hasSecret(final String secret) {
        return tMap.containsKey(secret);
    }

    /**
     * @see AbstractSecretsMap#put(T,java.lang.String, java.util.Date)
     */
    @Override
    protected final void put(final T t, final String secret,
            final Date expirationDate) {
        tMap.put(secret, t);
        expirationDateMap.put(t, expirationDate);
    }

    /**
     * @see AbstractSecretsMap#get(T)
     */
    @Override
    protected final QueryResult<T> get(final T t)
            throws NoSuchEntityException {

        // TODO performace
        String secret = null;
        Date expirationDate = null;

        for(Entry<String, T> entry : tMap.entrySet()) {
            if(entry.getValue().equals(t)) {
                secret = entry.getKey();
                expirationDate = expirationDateMap.get(t);
                Validate.notNull(expirationDate);
                break;
            }
        }

        if(secret == null || expirationDate == null) {
            throw new NoSuchEntityException(t);
        }
        return new QueryResult<T>(t, secret, expirationDate);
    }

    /**
     * @see AbstractSecretsMap#get(java.lang.String)
     */
    @Override
    protected final QueryResult<T> get(final String secret)
            throws NoSuchEntityException {
        final T t = tMap.get(secret);

        if(t == null) {
            throw new NoSuchEntityException(secret);
        } else {
            final Date date = expirationDateMap.get(t);
            if(date == null) {
                throw new IllegalStateException("missing date!");
            } else {
                return new QueryResult<T>(t, secret, date);
            }
        }
    }

    /** @see SecretsMap#removeByKey(java.lang.String) */
    public final void removeByKey(final String key) {
        throw new NotImplementedException("todo: implement ;^)");
    }
    
    /** @see SecretsMap#getByKey(String) */
    public final T getByKey(final String key) {
        throw new NotImplementedException("todo: implement ;^)");
    }
}
