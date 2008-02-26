/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.auth.acegi.password.ldap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Parsea un string de password de ldap (en general 
 * de la forma <code>{algorithm}data</code>
 * 
 * @author Juan F. Codagnone
 * @since Oct 31, 2006
 */
class LdapPassword {
    /** patron de las claves guardadas en un ldap */
    private static final Pattern PATT = Pattern.compile("[{](.+)[}](.*)");
    /** algoritmo */
    private final String algorithm;
    /** password */
    private final String data;
    
    /**
     * Creates the LdapPassword.
     *
     * @param passwordString
     */
    public LdapPassword(final String passwordString) {
        final Matcher m = PATT.matcher(passwordString);
        
        if(m.lookingAt()) {
            algorithm = m.group(1);
            data = m.group(2);
        } else {
            algorithm = null;
            data = passwordString;
        }
    }

    
    /**
     * Returns the algorithm.
     * 
     * @return <code>String</code> with the algorithm.
     */
    public final String getAlgorithm() {
        return algorithm;
    }

    
    /**
     * Returns the data.
     * 
     * @return <code>String</code> with the data.
     */
    public final String getData() {
        return data;
    }
 
    /** @see java.lang.Object#toString() */
    @Override
    public final String toString() {
        return "{" + algorithm + "}" + data;
    }
}