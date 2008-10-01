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
package ar.com.zauber.commons.web.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.Validate;

/**
 * Helps to build links.
 * 
 * @author Juan F. Codagnone
 * @since Aug 24, 2007
 */
public class LinksHelper {
    private final String prefix;

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
            ret = request.getContextPath();
        } else {
            ret = prefix;
        }
        
        return ret;
    }
}
