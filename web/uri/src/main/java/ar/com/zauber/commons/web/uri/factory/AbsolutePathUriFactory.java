/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.factory;

import java.util.regex.Pattern;

import org.apache.commons.lang.Validate;

/**
 * En caso de que sea un Absolute Path no llama al resto de los uriFactory
 * debería ser usado como una de las capas mas externas.
 * 
 * @author Mariano Semelman
 * @since Jun 18, 2010
 */
public class AbsolutePathUriFactory implements UriFactory {

    private final UriFactory uriFactory;

    /** Creates the AbsolutePathUriFactory. */
    public AbsolutePathUriFactory(final UriFactory uriFactory) {
        Validate.notNull(uriFactory);
        
        this.uriFactory = uriFactory;
    }
    
    /** @see UriFactory#buildUri(String, Object[]) */
    public final String buildUri(final String uriKey, final Object... expArgs) {
        Pattern p = Pattern.compile("(https|http)://");
        if(p.matcher(uriKey).matches()) {
            return uriKey;
        }
        return this.uriFactory.buildUri(uriKey, expArgs);
    }

}
