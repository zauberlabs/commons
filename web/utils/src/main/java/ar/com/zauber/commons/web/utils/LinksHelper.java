/**
 * Copyright (c) 2005-2009 Zauber S.A. <http://www.zauber.com.ar/>
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
package ar.com.zauber.commons.web.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.Validate;

/**
 * Helps to build links.
 * 
 * @author Juan F. Codagnone
 * @since Aug 24, 2007
 */
public class LinksHelper {
    private final String prefix;
    private String defaultEncoding = "ISO-8859-1";
    
    /** no proxy */
    public LinksHelper() {
        prefix = null;
    }

    /**
     * Creates the LinksHelper.
     *
     * @param prefix <code>true</code> si la aplicación tiene su propio dominio
     *   y se sirve mediante un proxy, y no se requiere que se mantenga
     *   el contexto de la aplicación (ej. flof.com.ar -> /geotag-x.y.z)
     */
    public LinksHelper(final String prefix) {
        Validate.notNull(prefix);
        this.prefix = prefix;
    }

    /** @return the context path  */
    public final String getCtx(final HttpServletRequest request) {
        final String ret;
        
        if(prefix == null) {
            try {
                String encoding = request.getCharacterEncoding();
                if(StringUtils.isBlank(encoding)) {
                    encoding = this.defaultEncoding;
                }
                // si estamos en un jsp...
                String uri = (String)request.getAttribute(
                        "javax.servlet.forward.request_uri");
                
                if(uri == null) {
                    uri = request.getRequestURI();
                }
                uri = URLDecoder.decode(uri, encoding);
                uri = uri.substring(URLDecoder.decode(request.getContextPath(),
                        encoding).length());
                if(uri.startsWith("/")) {
                    uri = uri.substring(1);
                }
                int slashses = 0;
                for(int i = 0; i < uri.length(); i++) {
                    if(uri.charAt(i) == '/') {
                        slashses++;
                    }
                }
                
                final StringBuilder sb = new StringBuilder();
                for(int i = 0; i < slashses; i++) {
                    sb.append("..");
                    if(i + 1 < slashses) {
                        sb.append('/');
                    }
                }
                ret = sb.toString();
            } catch(UnsupportedEncodingException e) {
                throw new UnhandledException(e);
            }
        } else {
            ret = prefix;
        }
        
        return ret;
    }

    public final String getDefaultEncoding() {
        return defaultEncoding;
    }

    /** 
     * setea el default encoding utilizado para decodificar las urls si el 
     * request no tiene la informacion de encoding
     */
    public final void setDefaultEncoding(final String defaultEncoding) {
        Validate.isTrue(!StringUtils.isBlank(defaultEncoding));
        this.defaultEncoding = defaultEncoding;
    }
}
