/**
 * Copyright (c) 2005-2009 Zauber S.A. <http://www.zauber.com.ar/>
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
package ar.com.zauber.commons.auth; 

import java.util.Set;


/**
 * Obtiene el usuario logueado en el contexto actual.
 *  
 * @author Juan F. Codagnone
 * @since Sep 29, 2005
 */
public interface AuthenticationUserMapper<T> {

    /**@return el usuario de la sesión actual, null si no hay ningun usuario logueado*/
    T getUser();

    /**  @return <code>true</code> if the current user is anonymous */
    boolean isAnonymous();
    
    /** @return a list of granted roles*/
    Set<String> getRoles();
}