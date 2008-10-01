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