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
package ar.com.zauber.commons.exception;

import java.util.ResourceBundle;

import org.springframework.core.NestedRuntimeException;

/**
 * Interceptor que se aplica a todos los servicios
 * 
 * Implementa auditoría de invocaciones, registrando cada
 * llamada que se realiza a un servicio
 * 
 * @author Mart�n Andr�s M�rquez
 * @since Jun 16, 2007
 */
public class MessageKeyException extends NestedRuntimeException
{
    private Object[] args;
    private String messageKey;
    
    public MessageKeyException(String messageKey, Object[] args, Throwable ex) {
        super(messageKey, ex);
        this.args = args;
        this.messageKey = messageKey;
    }

    public Object[] getArgs() {
        return args;
    }

    public String getMessageKey() {
        return messageKey;
    }
}