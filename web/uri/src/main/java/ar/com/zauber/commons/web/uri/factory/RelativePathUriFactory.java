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
    private final RequestProvider requestProvider;
    private final String defaultEncoding;

    /** Creates a new {@link RelativePathUriFactory} */
    public RelativePathUriFactory(final UriFactory callback, final String encoding,
            final RequestProvider requestProvider) {
        Validate.notNull(callback);
        Validate.notNull(encoding);
        Validate.notNull(requestProvider);
        Validate.isTrue(Charset.isSupported(encoding), "Encoding no soportado");
        
        defaultEncoding = encoding;
        this.callback = callback;
        this.requestProvider = requestProvider;
    }
    
    /** Creates a new {@link RelativePathUriFactory} */
    public RelativePathUriFactory(final UriFactory callback) {
        this(callback, "utf-8", new ContextListenerRequestProvider());
    }

    /** @see UriFactory#buildUri(String, Object[]) */
    public final String buildUri(final String uriKey, final Object... expArgs) {
        final HttpServletRequest request = requestProvider.getRequest();
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

            // Saco el contexto
            uri = uri.substring(URLDecoder.decode(request.getContextPath(),
                    encoding).length());

            // cuento los subs
            int slashes = StringUtils.countMatches(uri, "/");

            // si empezaba en barra resto 1
            if (uri.startsWith("/")) {
                slashes--;
            }

            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < slashes; i++) {
                sb.append("..");
                if (i + 1 < slashes) {
                    sb.append('/');
                }
            }
            return (sb.toString().isEmpty()) ? "." : sb.toString();

        } catch (UnsupportedEncodingException e) {
            throw new UnhandledException(e);
        }
    }
}
