/**
 * Copyright (c) 2005-2012 Zauber S.A. <http://www.zaubersoftware.com/>
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
package ar.com.zauber.commons.message.impl.mail;

import java.util.concurrent.Callable;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Closure;
import ar.com.zauber.commons.message.Message;
import ar.com.zauber.commons.message.NotificationAddress;
import ar.com.zauber.commons.message.NotificationStrategy;

/**
 * Sends messages via a delegate 
 * 
 * @author Christian Nardi
 * @since Apr 6, 2011
 */
class AsyncNotificationStrategyRunnable implements Callable<Message> {
    private final NotificationStrategy delegate;
    private final Message message;
    private final NotificationAddress[] notidicationAddresses;
    private Closure<Throwable> errorClosure;
    /**
     * Creates the AsyncNotificationStrategyRunnable.
     *
     * @param message to send
     * @param notidicationAddresses to send
     * @param delegate used for sending messages
     * @param errorClosure 
     */
    public AsyncNotificationStrategyRunnable(final NotificationStrategy delegate, 
            final Message message, final NotificationAddress[] notidicationAddresses,
            final Closure<Throwable> errorClosure) {
        Validate.notNull(message);
        Validate.notNull(notidicationAddresses);
        Validate.notNull(delegate);
        Validate.notNull(errorClosure);

        this.errorClosure = errorClosure;
        this.notidicationAddresses = notidicationAddresses;
        this.delegate = delegate;
        this.message = message;
    }

    /** @see java.util.concurrent.Callable#call() */
    @Override
    public Message call() throws Exception {
        try {
            delegate.execute(notidicationAddresses, message);
        } catch (Exception e) {
            errorClosure.execute(e);
            throw e;
        }
        return message;
    }
}