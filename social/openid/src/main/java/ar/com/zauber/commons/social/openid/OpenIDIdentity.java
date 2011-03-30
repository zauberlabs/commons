/**
 * Copyright (c) 2005-2011 Zauber S.A. <http://www.zaubersoftware.com/>
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
package ar.com.zauber.commons.social.openid;

import java.io.Serializable;

import org.apache.commons.lang.Validate;

/**
 * Identificador OpenID de un usuario.
 * 
 * @author Francisco J. González Costanzó
 * @since Feb 11, 2010
 */
public class OpenIDIdentity implements Serializable {

    /** <code>serialVersionUID</code> */
    private static final long serialVersionUID = 3083539803765210139L;
    
    private final String identifier;

    /** Creates the OpenIDIdentity. */
    public OpenIDIdentity(final String identifier) {
        Validate.notNull(identifier);
        this.identifier = identifier;
    }

    public final String getIdentity() {
        return identifier;
    }

}
