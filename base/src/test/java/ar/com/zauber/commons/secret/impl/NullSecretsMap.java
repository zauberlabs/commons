/**
 * Copyright (c) 2005-2012 Zauber S.A. <http://www.zaubersoftware.com/>
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

    /** @see SecretsMap#removeByKey(String) */
    public final void removeByKey(final String key) {
        throw new NotImplementedException("not implemented");
    }
    
    /** @see SecretsMap#getByKey(String) */
    public final T getByKey(final String key) {
        throw new NotImplementedException("not implemented");
    }

}
