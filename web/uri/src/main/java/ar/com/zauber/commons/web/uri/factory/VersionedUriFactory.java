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
 * {@link UriFactory} implementation thats adds a version parameter to 
 * the uri generated by another {@link UriFactory}.
 * 
 * @author Mariano Cortesi
 * @since May 7, 2010
 */
public class VersionedUriFactory implements UriFactory {

    private final String versionParameter;
    private final UriFactory uriFactory;

    /**
     * Creates the VersionedUriFactory.
     *
     * @param version version 
     * @param uriFactory decorated {@link UriFactory}
     */
    public VersionedUriFactory(final String version, final UriFactory uriFactory) {
        this.versionParameter = "v=" + version;
        this.uriFactory = uriFactory;
    }

    /** @see UriFactory#buildUri(String, Object[]) */
    public final String buildUri(final String uriKey, final Object... expArgs) {
        final String uri = uriFactory.buildUri(uriKey, expArgs);
        final int ampIdx = uri.lastIndexOf("?");
        final String separator;
        if (ampIdx >= 0) {
            separator = "&";
        } else {
            separator = "?";
        }
        return new StringBuilder(uri)
            .append(separator)
            .append(versionParameter)
            .toString();
    }

}
