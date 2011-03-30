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
package ar.com.zauber.commons.web.version.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.web.version.VersionProvider;

/**
 * concatena un text estatico a otro {@link VersionProvider}. Por ejemplo si otro
 * {@link VersionProvider} retorna <em>25</em>, esta implentacion serviría para
 * hacer decir que la version es: "build-25". No concatena si upstream es vacio 
 * 
 * @author Juan F. Codagnone
 * @since Jul 19, 2008
 */
public class AppenderDecoratorVersionProvider implements VersionProvider {
    private final VersionProvider target;
    private final String textToAppend;
    private final boolean prefix;
    
    /**
     * Creates the AppenderDecoratorVersionProvider.
     *
     * @param target          otro {@link VersionProvider}
     * @param textToAppend    texto estatico a concatenar
     * @param prefix          concatenar como prefijo o sufijo?
     */
    public AppenderDecoratorVersionProvider(final VersionProvider target,
            final String textToAppend, final boolean prefix) {
        Validate.notNull(target);
        Validate.isTrue(!StringUtils.isBlank(textToAppend));
        
        this.target = target;
        this.textToAppend = textToAppend;
        this.prefix = prefix;
    }
    
    /** @see VersionProvider#getVersion() */
    public final String getVersion() {
        final String upstream = target.getVersion();
        final String ret;
        
        
        if(upstream.length() == 0) {
            ret = "";
        } else {
            if(prefix) {
                ret = textToAppend + upstream;
            } else {
                ret = upstream + textToAppend;
            }
        }
        
        return ret;
    }
}
