/*
 * Copyright (c) 2005 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.secret;

import java.security.SecureRandom;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.zauber.commons.secret.impl.SecureRandomSecretGenerator;

/**
 * Test the Secrets generators
 * 
 * @author Juan F. Codagnone
 * @since Jun 19, 2005
 */
public class SecretGeneratorTest extends TestCase {
    /** algoritmo a testear */
    public static final String SECURE_RANDOM_ALGO = "SHA1PRNG"; 
    /** logger */
    private final Log logger = LogFactory.getLog(SecretGeneratorTest.class);

    /**
     * test that the secret use base64 charset
     * 
     * @throws Exception
     *             on error
     */
    public final void testGetSecret() throws Exception {
        final SecretGenerator [] secretGenerators = { 
                new SecureRandomSecretGenerator(SecureRandom.getInstance(
                        SECURE_RANDOM_ALGO)), };

        final int times = 1000;
        for(SecretGenerator generator : secretGenerators) {
            logger.debug("using " + generator);
            for(int i = 0; i < times; i++) {
                generator.getSecret();
            }
        }
    }
}
