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
package ar.com.zauber.commons.exception.interceptors;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.zauber.commons.exception.MessageKeyException;
import ar.com.zauber.commons.message.Message;
import ar.com.zauber.commons.message.MessageFactory;
import ar.com.zauber.commons.message.NotificationAddress;
import ar.com.zauber.commons.message.NotificationStrategy;

/**
 * TODO Brief description.
 * 
 * TODO Detail
 * 
 * @author Martin A. Marquez
 * @since Jul 24, 2007
 */
public class NotificationExceptionHandler  extends
	MethodInvocationExceptionChainedHandler {
	
	private MessageFactory messageFactory;
	private NotificationStrategy notificationStrategy;
	private NotificationAddress fromAddress;
	private NotificationAddress[] receivers;
	private String subjectKey;
	
	/** logger */
    private static Log log = LogFactory.getLog(NotificationExceptionHandler.class);
	
	/**
	 * @see MethodInvocationExceptionChainedHandler#doHandle(Exception,
	 *      MethodInvocation, MethodInvocationExceptionHandlerContext)
	 */
	public boolean doHandle(Throwable ex, MethodInvocation invocation,
			MethodInvocationExceptionHandlerContext context) {
		return doMail(ex, context);
	}


	/**
	 * @see MethodInvocationExceptionChainedHandler#doHandle(Exception, String,
	 *      MethodInvocationExceptionHandlerContext)
	 */
	public boolean doHandle(Throwable ex, MethodInvocationExceptionHandlerContext context) {
		return doMail(ex, context);
	}

	/**
	 * @param ex la excepcion
	 * @param app la aplicacion
	 * @param context TODO
	 * @return <code>true</code> si pudo tomar la acción
	 * que le compete
	 */
	private boolean doMail(Throwable ex, MethodInvocationExceptionHandlerContext context) {
	    
        try {
	    
	    if (ex instanceof MessageKeyException) {
            MessageKeyException messageKeyException = (MessageKeyException) ex;
            notificationStrategy.execute(receivers,
                    messageFactory.createMessage(
                            messageKeyException.getMessageKey(), subjectKey,
                            messageKeyException.getArgs(), fromAddress));
            
        }
	    
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		
		return true;
	}


	public NotificationExceptionHandler(Message message, NotificationStrategy notificationStrategy) {
		super();
	}
}
