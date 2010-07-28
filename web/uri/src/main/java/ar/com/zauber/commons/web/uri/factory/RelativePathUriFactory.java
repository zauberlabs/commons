/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.factory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.Validate;

/**
 * UriFactory que dado un un uri relativo al root de la aplicación, este
 * {@link UriFactory} genera el path relativo al root de la aplicación y se lo
 * agrega al principio del URI.
 * 
 * Requiere tener <code>scope="request"</code>
 * 
 * @author Mariano Semelman
 * @since Jul 7, 2010
 */
public class RelativePathUriFactory implements UriFactory {

    private final UriFactory callback;
    private String defaultEncoding;

    /** Creates a new {@link RelativePathUriFactory} */
    public RelativePathUriFactory(final UriFactory callback, final String encoding) {
        Validate.notNull(callback);
        Validate.notNull(encoding);
        Validate.isTrue(Charset.isSupported(encoding), "Encoding no soportado");
        
        defaultEncoding = encoding;
        this.callback = callback;
    }
    
    /** Creates a new {@link RelativePathUriFactory} */
    public RelativePathUriFactory(final UriFactory callback) {
        this(callback, "utf-8");
    }

    /** @see UriFactory#buildUri(String, Object[]) */
    public final String buildUri(final String uriKey, final Object... expArgs) {
        final HttpServletRequest request = getRequest(expArgs);
        final String ret = generarPath(request);
        String uri = callback.buildUri(uriKey, expArgs);
        if(!uri.startsWith("/")) {
            uri = '/' + uri;
        }
        return ret + uri;
    }

    /**
     * Genera el path hasta la ruta de la aplicación.
     * @param request
     * @return path
     */
    private String generarPath(final HttpServletRequest request) {
        String ret = StringUtils.EMPTY;
        try {
            String encoding = request.getCharacterEncoding();
            if (StringUtils.isBlank(encoding)) {
                encoding = defaultEncoding;
            }
            // si estamos en un jsp...
            String uri = (String) request
                    .getAttribute("javax.servlet.forward.request_uri");

            if (uri == null) {
                uri = request.getRequestURI();
            }
            uri = URLDecoder.decode(uri, encoding);
            uri = uri.substring(URLDecoder.decode(request.getContextPath(),
                    encoding).length());
            if (uri.startsWith("/")) {
                uri = uri.substring(1);
            }
            int slashses = 0;
            for (int i = 0; i < uri.length(); i++) {
                if (uri.charAt(i) == '/') {
                    slashses++;
                }
            }

            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < slashses; i++) {
                sb.append("..");
                if (i + 1 < slashses) {
                    sb.append('/');
                }
            }
            if (!sb.toString().isEmpty()) {
                ret = sb.toString();
            }
        } catch (UnsupportedEncodingException e) {
            throw new UnhandledException(e);
        }
        return ret;
    }

    /**
     * Obtiene el requeste entre los argumentos de buildUri.
     * @param expArgs
     * @return request
     */
    private HttpServletRequest getRequest(final Object... expArgs) {
        HttpServletRequest request = null;
        if (expArgs.length > 0 
                && expArgs[expArgs.length - 1] instanceof HttpServletRequest) {
            request = (HttpServletRequest) expArgs[expArgs.length - 1];
        } else {
            throw new IllegalStateException(
            "Can not reach request. Expected Request as last argument.");
        }
        return request;
    }

}
