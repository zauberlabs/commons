/*
 * Copyright (c) 2007 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.exception;

import java.util.ResourceBundle;

import org.springframework.core.NestedRuntimeException;

/**
 * Interceptor que se aplica a todos los servicios
 * 
 * Implementa auditorÃ­a de invocaciones, registrando cada
 * llamada que se realiza a un servicio
 * 
 * @author Martín Andrés Márquez
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