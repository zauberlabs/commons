/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
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
