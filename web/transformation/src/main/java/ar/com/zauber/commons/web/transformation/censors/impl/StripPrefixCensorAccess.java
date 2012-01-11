/**
 * Copyright (c) 2005-2012 Zauber S.A. <http://www.zaubersoftware.com/>
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
package ar.com.zauber.commons.web.transformation.censors.impl;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.web.transformation.censors.CensorAccess;
import ar.com.zauber.commons.web.transformation.utils.WebValidate;

/**
 * Quita de las URIs que llegan un prefijo.
 * 
 * @author Alejandro Souto
 * @since 11/11/2008
 */
public class StripPrefixCensorAccess extends AbstractCensorAccess {
    private final String prefixToStrip;
    private final CensorAccess target;

    /** 
     * Creates the StripPrefixCensorAccess.
     *
     * @param prefixToStrip prefijo a quitar de todas las uris que llegan al censor
     * @param target {@link CensorAccess} posta posta
     */
    public StripPrefixCensorAccess(final String prefixToStrip, 
            final CensorAccess target) {
        WebValidate.uriNotBlank(prefixToStrip);
        Validate.notNull(target);
        
        this.prefixToStrip = prefixToStrip;
        this.target = target;
    }
    
    /** @see CensorAccess#canAccess(String) */
    public final boolean canAccess(final String uri) {
        String s;
        if(uri.startsWith(prefixToStrip)) {
            s = uri.substring(prefixToStrip.length());
        } else {
            s = uri;
        }
        return target.canAccess(s);
    }

}
