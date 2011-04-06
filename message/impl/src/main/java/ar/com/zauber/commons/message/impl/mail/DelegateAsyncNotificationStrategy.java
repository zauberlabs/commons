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
package ar.com.zauber.commons.message.impl.mail;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Closure;
import ar.com.zauber.commons.dao.closure.NullClosure;
import ar.com.zauber.commons.message.AsyncNotificationStrategy;
import ar.com.zauber.commons.message.Message;
import ar.com.zauber.commons.message.NotificationAddress;
import ar.com.zauber.commons.message.NotificationStrategy;

/**
 * {@link AsyncNotificationStrategy} that sends a message via a delegat
 * 
 * @author Christian Nardi
 * @since Apr 6, 2011
 */
public class DelegateAsyncNotificationStrategy implements AsyncNotificationStrategy {
    private ExecutorService executorService;
    private NotificationStrategy delegate;
    private Closure<Throwable> errorClosure;

    /**
     * Creates the DelegateAsyncNotificationStrategy.
     * 
     * @param delegate
     *            to use for sending messages
     * @param executorService for executing the thread that sends messages
     */
    public DelegateAsyncNotificationStrategy(final NotificationStrategy delegate, 
            final ExecutorService executorService) {
        Validate.notNull(delegate);
        Validate.notNull(executorService);
        this.executorService = executorService;
        this.delegate = delegate;
        errorClosure = new NullClosure<Throwable>();
    }

    /** @see NotificationStrategy#execute(NotificationAddress[], Message) */
    @Override
    public final void execute(final NotificationAddress[] addresses, final Message message) {
        executorService.submit(new AsyncNotificationStrategyRunnable(delegate, message, 
                addresses, errorClosure));
    }

    /**
     * @see AsyncNotificationStrategy#executeAsync(NotificationAddress[], Message,
     *      NotificationCallback)
     */
    @Override
    public final Future<Message> executeAsync(final NotificationAddress[] addresses, 
            final Message message) {
        return executorService.submit(new AsyncNotificationStrategyRunnable(delegate, message, 
                addresses, errorClosure));
    }

    /** @see AsyncNotificationStrategy#setErrorClosure(Closure) */
    @Override
    public final void setErrorClosure(final Closure<Throwable> errorClosure) {
        if (errorClosure != null) {
            this.errorClosure = errorClosure;
        }
    }
}
