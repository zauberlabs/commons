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

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.web.transformation.censors.CensorAccess;

/**
 * Clase base para implementaciones de {@link CensorAccess}.
 * 
 * @author Matías Tito
 * @since Nov 11, 2008
 */
public abstract class AbstractCensorAccess implements CensorAccess {

    /** uri */
    public final void validate(final String uri) {
        try {
            new URL(uri);
        } catch (final MalformedURLException e) {
            Validate.isTrue(uri.startsWith("/"),
                    "URI `" + uri + "' is not absolute");
        }
    }
}
