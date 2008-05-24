/*
 * Copyright (c) 2005-2008 Zauber S.A. <http://www.zauber.com.ar/>
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
package ar.com.zauber.commons.web;

import org.apache.commons.lang.Validate;


/**
 * Dummy Long implementation.
 * 
 * @author Andrés Moratti
 * @since Nov 8, 2005
 */
public class LongIdMapper implements IdMapper {

    /**
     * @see ar.com.zauber.commons.web.IdMapper#map(java.lang.Object)
     */
    public final String map(final Object l) {
        Validate.notNull(l);
        return "a" + l.toString();
    }

    /**
     * @see ar.com.zauber.commons.web.IdMapper#unMap(java.lang.String)
     */
    public final Object unMap(final String string) {
        Validate.notNull(string);
        return Long.parseLong(string.substring(1));
    }
}
