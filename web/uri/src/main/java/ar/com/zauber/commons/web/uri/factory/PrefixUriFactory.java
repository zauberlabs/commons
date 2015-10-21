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
package ar.com.zauber.commons.web.uri.factory;

/**
 * {@link UriFactory} implementation that adds a prefix to the uri generated
 * by another {@link UriFactory}.
 * 
 * @author Mariano Cortesi
 * @since May 7, 2010
 */
public class PrefixUriFactory implements UriFactory {
    private final String prefix;
    private final UriFactory uriFactory;
    
    
    /**
     * Creates the PrefixUriFactory.
     *
     * @param prefix uri prefix
     * @param uriFactory decorated {@link UriFactory}
     */
    public PrefixUriFactory(final String prefix, final UriFactory uriFactory) {
        this.prefix = prefix;
        this.uriFactory = uriFactory;
    }


    /** @see UriFactory#buildUri(String, Object[]) */
    public final String buildUri(final String uriKey, final Object... expArgs) {
        return new StringBuilder(prefix)
            .append(uriFactory.buildUri(uriKey, expArgs))
            .toString();
    }

}
