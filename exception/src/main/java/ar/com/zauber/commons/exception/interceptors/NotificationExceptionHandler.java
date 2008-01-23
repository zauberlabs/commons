/*
 * Copyright (c) 2007 Zauber S.A.  -- All rights reserved
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
