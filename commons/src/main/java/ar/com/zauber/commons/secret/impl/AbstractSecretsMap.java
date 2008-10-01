/*
 * Copyright (c) 2005-2008 Zauber S.A. <http://www.zauber.com.ar/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ar.com.zauber.commons.secret.impl;

import java.util.Date;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.exception.NoSuchEntityException;
import ar.com.zauber.commons.secret.*;

/**
 * Common code used to implement a SecretsMap
 * 
 * @author Juan F. Codagnone
 * @since Jun 20, 2005
 * @param <T> type that holds
 */
public abstract class AbstractSecretsMap<T> implements SecretsMap<T> {

    /** policy for expirations dates... */
    private final ExpirationDatePolicy<T> expirationDatePolicy;
    /** secret generator... */
    private final SecretGenerator secretGenerator;
    /** expiratin date validator... */
    private final ExpirationDateValidator expirationDateValidator;

    /**
     * number of times to try to generate a secret (if it already exists)
     */
    private static final int UNIQUE_SECRET_TRIES = 100;

    /**
     * Creates the AbstractSecretsMap.
     * 
     * @param secretGenerator
     *            random source
     * @param expirationDatePolicy
     *            policy for expirations dates
     * @param expirationDateValidator
     *            expiration date validor
     */
    public AbstractSecretsMap(final SecretGenerator secretGenerator,
            final ExpirationDatePolicy<T> expirationDatePolicy,
            final ExpirationDateValidator expirationDateValidator) {
        Validate.notNull(secretGenerator, "secretGenerator");
        Validate.notNull(expirationDatePolicy, "expirationDatePolicy");
        Validate.notNull(expirationDateValidator, "expirationDateValidator");

        this.secretGenerator = secretGenerator;
        this.expirationDatePolicy = expirationDatePolicy;
        this.expirationDateValidator = expirationDateValidator;
    }

    /**
     * @see SecretsMap#register(T,java.util.Date)
     */
    public final String register(final T t, final Date expirationDate) {
        Validate.notNull(t, "t");
        Validate.notNull(expirationDate, "expirationDate");

        // TODO it should be atomic...shit can happen
        unregister(t);
        String secret = secretGenerator.getSecret();
        for(int i = 0; hasSecret(secret); i++) {
            secret = secretGenerator.getSecret();
            if(i == UNIQUE_SECRET_TRIES) {
                throw new IllegalStateException("the secret generation has "
                        + "trouble creating unique secrets");
            }
        }
        Validate.notNull(secret);
        put(t, secret, expirationDate);

        return secret;
    }

    /** @see SecretsMap#register(T) */
    public final String register(final T t) {
        return register(t, expirationDatePolicy.getExpirationDate(t));
    }

    /** @see SecretsMap#safeGetSecret(T) */
    public final String safeGetSecret(final T t) {
        String secret = null;

        try {
            secret = peekSecret(t);
        } catch(NoSuchEntityException e) {
            secret = register(t);
        }

        Validate.notNull(secret);
        return secret;
    }

    /** @see SecretsMap#peekSecret(T) */
    public final String peekSecret(final T t)
            throws NoSuchEntityException {
        QueryResult<T> result = get(t);

        if(!getExpirationDateValidator().isValid(result.getExpirationDate())) {
            unregister(t);
            throw new NoSuchEntityException(t);
        }

        return result.getSecret();
    }

    /** @see SecretsMap#getT(java.lang.String) */
    public final T getT(final String secret)
            throws NoSuchEntityException {
        QueryResult<T> result = get(secret);

        if(!getExpirationDateValidator().isValid(result.getExpirationDate())) {
            unregister(result.getE());
            throw new NoSuchEntityException(secret);
        }

        return result.getE();
    }

    /**
     * @see SecretsMap#getExpirationDatePolicy()
     */
    public final ExpirationDatePolicy getExpirationDatePolicy() {
        return expirationDatePolicy;
    }

    /**
     * @see SecretsMap#getExpirationDateValidator()
     */
    public final ExpirationDateValidator getExpirationDateValidator() {
        return expirationDateValidator;
    }

    /**
     * @see SecretsMap#getSecretGenerator()
     */
    public final SecretGenerator getSecretGenerator() {
        return secretGenerator;
    }

    /**
     * @param secret
     *            secret to test
     * @return <code>true</code> if the the secret already exists
     */
    protected abstract boolean hasSecret(String secret);

    /**
     * Store a tuple
     * 
     * @param t
     *            t to store
     * @param secret
     *            t's secret
     * @param expirationDate
     *            expiration date of the secret
     */
    protected abstract void put(T t, String secret, Date expirationDate);

    /**
     * @param t t to lookup
     * @return the queryresult containig the t. should't analize the dates
     * @throws NoSuchEntityException
     *             if the t doesnt exists
     */
    protected abstract QueryResult<T> get(final T t)
            throws NoSuchEntityException;

    /**
     * @param secret
     *            the secret to lookup
     * @return the queryresult containig the secret. should't analize the dates
     * @throws NoSuchEntityException
     *             if the secret doesnt exists
     */
    protected abstract QueryResult<T> get(final String secret)
            throws NoSuchEntityException;

    /**
     * Helper class, only contains data. no behaviour
     * 
     * @author Juan F. Codagnone
     * @since Jun 20, 2005
     */
    public static class QueryResult<E> {

        /** the E... */
        private final E e;
        /** the secret.... */
        private final String secret;
        /** the expiration date */
        private final Date expirationDate;

        /**
         * Creates the QueryResult.
         * 
         * @param e
         *            a e
         * @param secret
         *            a secret
         * @param expirationDate
         *            an expiration date
         */
        public QueryResult(final E e, final String secret,
                final Date expirationDate) {
            Validate.notNull(e);
            Validate.notNull(secret);
            Validate.notNull(expirationDate);

            this.e = e;
            this.secret = secret;
            this.expirationDate = expirationDate;
        }

        /**
         * @return the expiration date
         */
        public final Date getExpirationDate() {
            return expirationDate;
        }

        /**
         * @return the e
         */
        public final E getE() {
            return e;
        }

        /**
         * @return the e's secret
         */
        public final String getSecret() {
            return secret;
        }
    }
}
