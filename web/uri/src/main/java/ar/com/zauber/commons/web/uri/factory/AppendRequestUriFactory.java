/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.factory;

import java.util.Arrays;

import javax.servlet.ServletRequest;

import org.apache.commons.lang.Validate;

/**
 * {@link UriFactory} que agrega como ultimo parametro el request si es que no
 * está configurado. Util para usar en conjunto con el 
 * {@link RelativePathUriFactory}. 
 * 
 * @author Juan F. Codagnone
 * @since Sep 4, 2010
 */
public class AppendRequestUriFactory implements UriFactory {
    private final UriFactory target;
    private final RequestProvider requestProvider;
    
    /** Creates the AppendRequestUriFactory. */
    public AppendRequestUriFactory(final UriFactory target, 
            final RequestProvider requestProvider) {
        Validate.notNull(target, "target");
        Validate.notNull(requestProvider, "provider");
        
        this.target = target;
        this.requestProvider = requestProvider;
    }
    
    /** @see UriFactory#buildUri(String, Object[]) */
    public final String buildUri(final String uriKey, final Object... expArgs) {
        final Object [] params;
        if(expArgs.length == 0) {
            params = new Object[1];
            params[0] = requestProvider.getRequest();
        } else if(expArgs[expArgs.length - 1] instanceof ServletRequest) {
            params = expArgs;
        } else {
            params = Arrays.copyOf(expArgs, expArgs.length + 1);
            params[params.length - 1] = requestProvider.getRequest();
        } 
        return target.buildUri(uriKey, params);
    }
}
