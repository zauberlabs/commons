/**
 * Copyright (c) 2005-2010 Zauber S.A. <http://www.zauber.com.ar/>
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
 * Constructor de URIs en forma din�mica.
 * 
 * @author Mariano Cortesi
 * @since Apr 23, 2010
 */
public interface UriFactory {

    /**
     * Construye la uri referida por uriKey con los parametros expArgs.
     * 
     * @param uriKey
     *            Clave del Uri
     * @param expArgs
     *            Parametros de la expresi�n referida por el uriKey.
     * @return uri generado.
     */
    String buildUri(final String uriKey, final Object... expArgs);

}