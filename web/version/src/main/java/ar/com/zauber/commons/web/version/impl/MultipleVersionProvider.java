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
 * Encadena varias estrategias de {@link VersionProvider}. Si una falla
 * o no devuelve una versión, sigue con la siguiente.
 * 
 * @author Juan F. Codagnone
 * @since Jul 19, 2008
 */
public class MultipleVersionProvider implements VersionProvider {
    private final List<VersionProvider> targets;

    /**
     * Creates the MultipleVersionProvider.
     *
     */
    public MultipleVersionProvider(final List<VersionProvider> targets) {
        Validate.noNullElements(targets);
        
        this.targets = targets;
    }
    

    /** @see VersionProvider#getVersion() */
    public final String getVersion() {
        String ret = "";
        
        for(final VersionProvider target : targets) {
            try {
                ret = target.getVersion();
                if(ret.length() != 0) {
                    break;
                }
            } catch(Throwable e) {
                // skip
            }
        }
        
        return ret;
    }
}
