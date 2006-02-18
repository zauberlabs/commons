/*
 * Copyright (c) 2005 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.secret;


/**
 * @author Juan F. Codagnone
 * @since Jun 19, 2005
 * @see ar.com.zauber.commons.secret.SecretsMap
 */
public interface SecretGenerator {
    /**
     * @return the secret with the specification of 
     * {@link SecretsMap}
     * 
     * @see SecretsMap
     */
    String getSecret();
}
