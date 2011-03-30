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

import java.util.HashMap;
import java.util.Map;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.com.zauber.commons.exception.MessageKeyException;
import ar.com.zauber.commons.message.MessageFactory;
import ar.com.zauber.commons.message.NotificationAddress;
import ar.com.zauber.commons.message.NotificationStrategy;

/**
 * Aspecto que envia mails con la exception
 *
 * @author Martin A. Marquez
 * @since Jul 24, 2007
 */
public class NotificationExceptionHandler  
     extends MethodInvocationExceptionChainedHandler {
    private final NotificationStrategy notificationStrategy;
    private final MessageFactory messageFactory;
    private final NotificationAddress[] receivers;

    private static Logger log = LoggerFactory.getLogger(
            NotificationExceptionHandler.class);


    /** constructor */
    public NotificationExceptionHandler(
            final NotificationStrategy notificationStrategy,
            final MessageFactory messageFactory,
            final NotificationAddress[] receivers) {
        Validate.notNull(notificationStrategy);
        Validate.notNull(messageFactory);
        Validate.noNullElements(receivers);
        
        this.notificationStrategy = notificationStrategy;
        this.messageFactory = messageFactory;
        this.receivers = receivers;
    }
    
    /**
     * @see MethodInvocationExceptionChainedHandler#doHandle(Exception,
     *      MethodInvocation, MethodInvocationExceptionHandlerContext)
     */
    @Override
    public final boolean doHandle(final Throwable ex, 
            final MethodInvocation invocation,
            final MethodInvocationExceptionHandlerContext context) {
        return doMail(ex, context);
    }


    /**
     * @see MethodInvocationExceptionChainedHandler#doHandle(Exception, String,
     *      MethodInvocationExceptionHandlerContext)
     */
    @Override
    public final boolean doHandle(final Throwable ex, 
            final MethodInvocationExceptionHandlerContext context) {
        return doMail(ex, context);
    }

    /**
     * @param ex la excepcion
     * @return <code>true</code> si pudo tomar la acción que le compete
     */
    private boolean doMail(final Throwable ex, 
            final MethodInvocationExceptionHandlerContext context) {

        try {
            if (ex instanceof MessageKeyException) {
                final MessageKeyException messageKeyException = 
                    (MessageKeyException) ex;
                final Map<String, Object> model = new HashMap<String, Object>();
                model.put("exception", ex);
                model.put("ctx", context);
                
                notificationStrategy.execute(receivers,
                        messageFactory.createMessage(
                                messageKeyException.getMessageKey(),
                                model));
            }
        } catch (final Exception e) {
            log.error("error", e);
            e.printStackTrace();
        }
        
        return true;
    }
}
