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
package ar.com.zauber.commons.passwd;

import ar.com.zauber.commons.dao.exception.InvalidPassword;


/**
 * Valida password (cantidad de caracteres, tipo de caracteres, etc)
 * 
 * @author Juan F. Codagnone
 * @since Mar 6, 2006
 */
public interface PasswordValidator {

    /**
     * valida si una password es digna del sistema
     * @param password la password a analizar
     * @throws InvalidPassword si la password no es digna
     */
    void validate(String password) throws InvalidPassword;
    
}
