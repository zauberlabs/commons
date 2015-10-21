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

import java.util.regex.Pattern;

import org.apache.commons.lang.Validate;

/**
 * En caso de que sea un Absolute Path no llama al resto de los uriFactory
 * debería ser usado como una de las capas mas externas.
 * 
 * @author Mariano Semelman
 * @since Jun 18, 2010
 */
public class AbsolutePathUriFactory implements UriFactory {

    private final UriFactory uriFactory;

    /** Creates the AbsolutePathUriFactory. */
    public AbsolutePathUriFactory(final UriFactory uriFactory) {
        Validate.notNull(uriFactory);
        
        this.uriFactory = uriFactory;
    }
    
    //matchea http:// y https:// (la s es opcional '?')
    private static final Pattern SCHEMA_PATT = Pattern.compile("https?://.*");
    
    /** @see UriFactory#buildUri(String, Object[]) */
    public final String buildUri(final String uriKey, final Object... expArgs) {
        if(SCHEMA_PATT.matcher(uriKey).matches()) {
            return uriKey;
        }
        return this.uriFactory.buildUri(uriKey, expArgs);
    }

}
