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
package ar.com.zauber.commons.exception.interceptors;

import java.io.Serializable;
import java.util.Date;

/**
 * Clase: BaseLog
 *
 * Clase base para los tipos de logs manejados por el Framework
 * 
 * @author Martin A. Marquez
 * @since Jul 24, 2007
 */
public class BaseLog {
    /** id **/
    private Long id;
    
    /** id del usuario conectado **/
    private String userLoginName;

    /** fecha completa de generación del log **/
    private Date date = new Date();;

    /** @return Retorna el/la date. */
    public final Date getDate() {
        return date;
    }

    /** @return Retorna el/la userLoginName. */
    public final String getUserLoginName() {
        return userLoginName;
    }

    /** @see AbstractPersistentObject#getId() */
    public final Serializable getId() {
        return this.id;
    }
}
