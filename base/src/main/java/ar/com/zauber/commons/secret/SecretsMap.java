/**
 * Copyright (c) 2005-2015 Zauber S.A. <http://flowics.com/>
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
package ar.com.zauber.commons.secret;

import java.util.Date;

import ar.com.zauber.commons.dao.exception.NoSuchEntityException;


/**
 * <p>
 * Background: The user can interact with the system without having a preseted
 * user/password pair. This can be accomplish identifying the guest with a
 * secret phrase.
 * </p>
 * <p>
 * The secret MUST be unique in the scope of the mapper, and SHOULD be enough
 * random so a cracker can't predict others users secret and impose.
 * </p>
 * <p>
 * As the secret should be transpotable using lots flavors of transports it MUST
 * only use the URL safe charset. (this assumption may simply the client code).
 * 
 * </p>
 *
 * @link http://www.freesoft.org/CIE/RFC/1521/7.htm
 * @link http://www.ietf.org/rfc/rfc1750.txt
 * @author Juan F. Codagnone
 * @since Jun 18, 2005
 * @param <T> hold type
 */
public interface SecretsMap<T> {

    /**
     * <p>
     * Creates a new secret for the T. If the T had a previus secret, it
     * becomes replaced by the new.
     * </p>
     * <p>
     * The secret must be unique (in the scope of each instance)
     * </p>
     * 
     * @param obj the relevant T
     * @param expirationDate
     *            from this date (>=) the secret becomes invalid
     * @return the new secret
     */
    String register(T obj, Date expirationDate);

    /**
     * Register a new secret, using the default expiration schema
     * 
     * @param t a T to register
     * @return the secret. read the "see" section 
     * @see #register(T, Date)
     */
    String register(T t);
    
    /**
     * Makes invalid the current secret for a T. If he didn't have a secret
     * nothing happens.
     * 
     * @param t the relevant T
     */
    void unregister(T t);

    /**
     * @param t the relevant T
     * @return the current secret for the T
     * @throws NoSuchEntityException
     *             if the guest hasnt a valid secret (has expired, or doesn't
     *             exists)
     */
    String peekSecret(T t) throws NoSuchEntityException;

    /**
     * @param t the relevant T
     * @return the current secret if there is such thing. If that doesn't exists
     *         it register the T, and returns the new secret.
     */
    String safeGetSecret(T t);

    /**
     * Given a secret, retrives the T.
     * 
     * @param secret
     *            secret password that ids the T
     * @return the T
     * @throws NoSuchEntityException
     *             if the secret doesn't match any T
     */
     T getT(String secret) throws NoSuchEntityException;

     /**
      * remove all secrets that belongs to a key. Implementation
      * decides how to match the key.
      * 
      * @param key key to match
      */
     void removeByKey(String key);
     
     /**
      * get all the Ts that belongs to a key. Implementation
      * decides how to match the key.
      * 
      * @param key key to match
      */
     T getByKey(String key);
     
    /**
     * Maintenance method: cleans up a bit. (secrets that expired...)
     */
    void cleanup();

    /* estos métodos podrian no estar, pero están buenos, porque le avisa al
     * programador que puede reusar estas clases en ves de reinventar la rueda 
     */
    /**
     * @return the secret generator
     */
    SecretGenerator getSecretGenerator();
    
    /**
     * @return the expiration date policy
     */
    ExpirationDatePolicy getExpirationDatePolicy();
    
    /**
     * @return the expiration date validator
     */
    ExpirationDateValidator getExpirationDateValidator();
}
