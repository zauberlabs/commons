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
package ar.com.zauber.commons.web.version.impl;

import java.util.List;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.web.version.VersionProvider;

/**
 * Concatena multiples {@link VersionProvider} si todos retornaron una version
 * 
 * 
 * @author Juan F. Codagnone
 * @since Jul 19, 2008
 */
public class AppenderMultipleVersionProvider implements VersionProvider {
    private final List<VersionProvider> providers;

    /** constructor */
    public AppenderMultipleVersionProvider(final List<VersionProvider> providers) {
        Validate.noNullElements(providers);
        
        this.providers = providers;
    }

    /** @see VersionProvider#getVersion() */
    public final String getVersion() {
        final StringBuilder sb = new StringBuilder();
        boolean error = false;
        for(final VersionProvider provider : providers) {
            
            try {
                String v = provider.getVersion();
                sb.append(v);
                if(v.length() == 0) {
                    error = true;
                    break;
                }
            } catch(Throwable e) {
                error = true;
                break;
            }
        }
        return error ? "" : sb.toString();
    }

}
