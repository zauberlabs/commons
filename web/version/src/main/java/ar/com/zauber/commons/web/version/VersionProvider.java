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
package ar.com.zauber.commons.web.version;

/**
 * Provee la versi�n de un m�dulo. Por ejemplo, de una aplicaci�n web.
 * 
 * La idea de esta clase es que se utilize para versionar los archivos
 * estaticos de un proyecto web, para que sean facilmente cacheables por 
 * tiempo.
 * 
 * @author Juan F. Codagnone
 * @since Jul 19, 2008
 */
public interface VersionProvider {
    /** @return la version o un string vacio. nunca null. 
     * Lo que retorne debe poder ser parte de una URL (url encoded)
     */
    String getVersion();
}
