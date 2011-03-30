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
        String uri = callback.buildUri(uriKey, expArgs);
        if(!uri.startsWith("/")) {
            uri = '/' + uri;
        }
        return generarPath(request, uri);
    }

    /**
     * Genera el path hasta la ruta de la aplicación.
     * @param request
     * @return path
     */
    private String generarPath(final HttpServletRequest request, 
            final String destination) {
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

            final String [] ret = comunDenominador(uri, destination);
            uri = ret[0];
            // cuento los subs
            int slashes = StringUtils.countMatches(uri, "/");

            // si empezaba en barra resto 1
            if (uri.startsWith("/")) {
                slashes--;
            }

            final StringBuilder sb = new StringBuilder();
            boolean empty = true;
            for (int i = 0; i < slashes; i++) {
                empty = false;
                sb.append("..");
                if (i + 1 < slashes) {
                    sb.append('/');
                }
            }
            if(empty) {
                sb.append('.');
            }
            
            sb.append(ret[1]);
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            throw new UnhandledException(e);
        }
    }

    /** obtiene el comun denominador a nivel path */
    public static final String[] comunDenominador(final String p, final String q) {
        String []ret = null;
        final int n = Math.min(p.length(), q.length());
        int i = 0;
        int lastSlash = -1;
        for(; i < n; i++) {
            final char c = p.charAt(i);
            if(c != q.charAt(i)) {
                break;
            }
            if(c == '/') {
                lastSlash = i;
            }
        }
        
        if(lastSlash <= 0) {
            ret = new String[]{p, q};
        } else {
            ret = new String[]{p.substring(lastSlash), q.substring(lastSlash)};
        }
        return ret;
    }
}
