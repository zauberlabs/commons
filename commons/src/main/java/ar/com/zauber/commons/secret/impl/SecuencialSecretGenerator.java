/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.secret.impl;

import ar.com.zauber.commons.secret.SecretGenerator;


/**
 * Dummy implementation for 
 * {@link ar.com.zauber.commons.secret.SecretGenerator}. Don't use it
 * in production. The secret is not a secret!! is a secuential numering.
 * <p>
 * It is usefull for tests.
 * </p>
 * 
 * @author Juan F. Codagnone
 * @since Nov 29, 2005
 */
public class SecuencialSecretGenerator implements SecretGenerator {
    /** the index */
    private long i = 0;
    
    /** @see SecretGenerator#getSecret() */
    public final String getSecret() {
        return Long.toString(i++);
    }

}
