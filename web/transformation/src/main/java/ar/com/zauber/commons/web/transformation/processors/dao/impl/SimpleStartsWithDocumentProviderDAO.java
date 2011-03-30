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
package ar.com.zauber.commons.web.transformation.processors.dao.impl;

import java.security.InvalidParameterException;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.web.transformation.processors.DocumentProvider;
import ar.com.zauber.commons.web.transformation.processors.dao.DocumentProviderDAO;

/**
 * Implementacion de {@link DocumentProviderDAO} 
 * que devuelve siempre su instancia
 *  
 * @author Pablo Grigolatto
 * @since Jun 22, 2009
 */
public class SimpleStartsWithDocumentProviderDAO implements DocumentProviderDAO {
    private final DocumentProvider documentProvider;
    private final String prefix;

    /**
     * Creates the SimpleDocumentProviderDAO.
     *
     * @param prefix el prefijo de las URL que seran aceptadas,
     * string vacio ("") para aceptar todo
     * @param documentProvider
     */
    public SimpleStartsWithDocumentProviderDAO(final String prefix, 
            final DocumentProvider documentProvider) {
        Validate.notNull(prefix);
        Validate.notNull(documentProvider);
        this.prefix = prefix;
        this.documentProvider = documentProvider;
    }

    /** @see DocumentProviderDAO#getDocumentProvider(String) */
    public final DocumentProvider getDocumentProvider(final String url) {
        if(accept(url)) {
            return this.documentProvider;
        }
        throw new InvalidParameterException("no document provider for: " + url);
    }

    /** @see DocumentProviderDAO#accept(String) */
    public final boolean accept(final String url) {
        return url.startsWith(prefix);
    }

}
