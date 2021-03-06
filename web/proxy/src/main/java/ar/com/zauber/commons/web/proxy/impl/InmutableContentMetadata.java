/**
 * Copyright (c) 2005-2015 Zauber S.A. <http://flowics.com/>
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
package ar.com.zauber.commons.web.proxy.impl;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.web.proxy.ContentTransformer;

/**
 * Inmutable implementation of ContentMetadata.
 * 
 * @author Alejandro Souto
 * @since 12/11/2008
 */
public class InmutableContentMetadata implements ContentTransformer.ContentMetadata {
    private final String uri;
    private final String contentType;
    private final int statusCode;

    /** Creates the InmutableContentMetadata. */
    public InmutableContentMetadata(final String uri, final String contentType, 
            final int statusCode) {
        Validate.notNull(uri);
        // contentType puede ser nulo.
    
        this.uri = uri;
        this.contentType = contentType;
        this.statusCode = statusCode;
    }

    /** @see ContentMetadata#getUri() */
    public final String getUri() {
        return uri;
    }
        
    /** @see ContentMetadata#getContentType() */
    public final String getContentType() {
        return contentType;
    }

    /** @see ContentMetadata#getStatusCode() */
    public final int getStatusCode() {
        return statusCode;
    }
}
