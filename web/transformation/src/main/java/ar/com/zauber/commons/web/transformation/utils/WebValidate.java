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
package ar.com.zauber.commons.web.transformation.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

/**
 * Esta clase valida URIs pero esta creada para tener un espacio para 
 * validadores.
 * 
 * @author Alejandro Souto
 * @since 12/11/2008
 */
public final class WebValidate {
    
    /** Este constructor no deberia ser llamado  */
    private WebValidate() {
        // vacio
    }
    
    /**
     * Valida que la URI termine con / y que no sea nula sino 
     * lanza una excepcion.
     * 
     * @param uri URI a validar.
     */
    public static void uriNotNull(final String uri) {
        Validate.notNull(uri);
        uriWellFormed(uri);
    }

    /**
     * Chequea si la URI esta bien formada.
     * @param uri
     */
    public static void uriWellFormed(final String uri) {
        if (uri.trim().endsWith("/") || !uri.trim().startsWith("/")) {
            throw new IllegalArgumentException("The URI(" + uri
                    + ") must start with a `/` "
                    + "and must not end with a `/` character");
        }
    }
    
    /**
     * Valida que la URI termine con / y que no este en blanco sino 
     * lanza una excepcion.
     * 
     * @param uri URI a validar.
     * @throws IllegalArgumentException if uri does not validate
     */
    public static void uriNotBlank(final String uri) 
       throws IllegalArgumentException {
        Validate.isTrue(!StringUtils.isBlank(uri));
        uriWellFormed(uri);
    }
    
    /**
     * Valida que una URI pueda ser blanco en caso que no sea blan si etsa bien 
     * formada.
     * @param uri URI a validar.
     * @throws IllegalArgumentException
     */
    public static void uriBlankOrWellformed(final String uri) {
        Validate.notNull(uri);
        if(!StringUtils.isBlank(uri)) {
            uriWellFormed(uri);
        }
    }
}
