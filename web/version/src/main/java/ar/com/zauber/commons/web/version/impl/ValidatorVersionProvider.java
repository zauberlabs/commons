/**
 * Copyright (c) 2005-2010 Zauber S.A. <http://www.zauber.com.ar/>
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
package ar.com.zauber.commons.web.version.impl;

import java.util.regex.Pattern;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.web.version.VersionProvider;

/**
 * <p>
 * {@link VersionProvider} que valida que lo que devuelven otros 
 *  {@link VersionProvider}s cumpla con el contrato de la interface.
 * </p><p>
 *   Lanza {@link IllegalArgumentException} si es invalido.
 *  </p>
 * @author Juan F. Codagnone
 * @since Jul 19, 2008
 */
public class ValidatorVersionProvider implements VersionProvider {
    private final VersionProvider target;
    
    /*
     * RFC 2396 states:
     * ...
     * These include upper
     * and lower case letters, decimal digits, and a limited set of
     * punctuation marks and symbols. 
     *
     * unreserved  = alphanum | mark
     *
     * mark        = "-" | "_" | "." | "!" | "~" | "*" | "'" | "(" | ")"
     */
    private final Pattern safePattern = Pattern.compile("^[A-Za-z0-9_.!~*')(-]*$");

    /**
     * Creates the ValidatorVersionProvider.
     *
     * @param target target {@link VersionProvider}
     */
    public ValidatorVersionProvider(final VersionProvider target) {
       Validate.notNull(target);
       
       this.target = target;
    }
    
    /** @see VersionProvider#getVersion() */
    public final String getVersion() {
        final String ret = target.getVersion();
        
        Validate.notNull(ret, "version can't be null");
        if(!safePattern.matcher(ret).matches()) {
            throw new IllegalArgumentException("`" + ret + "' contains unsafe "
                    + "characters");
        }
        return ret;
    }
}
