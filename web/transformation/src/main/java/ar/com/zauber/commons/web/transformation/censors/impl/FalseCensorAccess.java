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


/**
 * {@link CensorAccess} que siempre retorna <code>false</code>.
 * 
 * @author Matías Tito
 * @since Oct 31, 2008
 */
public class FalseCensorAccess extends AbstractCensorAccess {

    /** @see CensorAccess#canAccess(String) */
    public final boolean canAccess(final String uri) {
        validate(uri);
        
        return false;
    }

}
