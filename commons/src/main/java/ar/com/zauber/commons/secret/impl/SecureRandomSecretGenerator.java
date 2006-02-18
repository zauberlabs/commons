/*
 * Copyright (c) 2005 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.secret.impl;

import java.security.SecureRandom;

import org.codehaus.plexus.util.Base64;

import ar.com.zauber.commons.secret.SecretGenerator;

/**
 * SecretGenerator that uses a {@link java.security.SecureRandom} as data 
 * source. It should be enought random.
 * 
 * @author Juan F. Codagnone
 * @since Jun 19, 2005
 */
public class SecureRandomSecretGenerator implements SecretGenerator {
    /** the source of the randomness */
    private final SecureRandom secureRandom;
    /** bytes to read from the random pool */
    private static final int BYTES_TO_READ = 30;
    
    /**
     * Creates the SecureRandomSecreGenerator.
     *
     * @param secureRandom the source of randomness
     */
    public SecureRandomSecretGenerator(final SecureRandom secureRandom) {
        this.secureRandom = secureRandom;
    }

    /** @see SecretGenerator#getSecret()*/
    public final synchronized String getSecret() {
        final byte [] bytes = new byte [BYTES_TO_READ];
        secureRandom.nextBytes(bytes);
        //return String.copyValueOf(Hex.encodeHex(bytes));
        return new String(Base64.encodeBase64(bytes)).replace('+', 'A')
                                                     .replace('/', 'B')
                                                     .replace('=', 'C');
    }
}
