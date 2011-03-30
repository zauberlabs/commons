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

import org.apache.commons.lang.exception.ExceptionUtils;

/**
 * Clase: ErrorLog
 *
 * Representa el log de error
 * 
 * @author Martin A. Marquez
 * @since Jul 24, 2007
 */
public class ErrorLog extends BaseLog {
    /** clase de la excepcion  **/
    private String exceptionClass;
    
    /** mensaje de la excepción **/
    private String exceptionMessage;
    
    /** el stack trace de la excepción */
    private String stackTrace;
    
    /** comentarios ingresados por el usuario */
    private String userComments;
    
    /** 
     * constructor default privado
     * (para Hibernate)
     */
    private ErrorLog() {
        super();
    }
    
    /**
     * @param ex la excepcion que origina el log
     */
    public ErrorLog(final Throwable ex) {
        this.exceptionClass = ex.getClass().getName();
        String msg = ex.getMessage();
        if (msg != null && msg.length() > 250) {
            msg = msg.substring(0, 249);
        }
        this.exceptionMessage = msg;
        this.stackTrace = ExceptionUtils.getStackTrace(ex);
    }
    
    /**
     * @return Retorna el/la exceptionClass.
     */
    public final String getExceptionClass() {
        return exceptionClass;
    }
    
    /**
     * @return Retorna el/la exceptionMessage.
     */
    public final String getExceptionMessage() {
        return exceptionMessage;
    }
    
    /**
     * @return Retorna el/la stackTrace.
     */
    public final String getStackTrace() {
        return stackTrace;
    }
    
    /**
     * @return Retorna el/la userComments
     */
    public final String getUserComments() {
        return userComments;
    }
    
    /**
     * Setter del userComments
     * 
     * @param userComments Comentarios sobre la excpecion ingresados
     * por el usuario.
     */
    public final void setUserComments(final String userComments) {
        this.userComments = userComments;
    }
}
