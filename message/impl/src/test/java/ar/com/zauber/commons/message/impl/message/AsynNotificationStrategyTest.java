/**
 * Copyright (c) 2005-2015 Zauber S.A. <http://flowics.com/>
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
package ar.com.zauber.commons.message.impl.message;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

import ar.com.zauber.commons.message.Message;
import ar.com.zauber.commons.message.NotificationAddress;
import ar.com.zauber.commons.message.NotificationStrategy;
import ar.com.zauber.commons.message.impl.mail.DelegateAsyncNotificationStrategy;
import ar.com.zauber.commons.message.impl.nil.NullNotificationStrategy;
import ar.com.zauber.commons.message.message.StringMessage;

/**
 * Test for AsyncNotificationStrategy 
 * 
 * @author Christian Nardi
 * @since Apr 6, 2011
 */
public class AsynNotificationStrategyTest {
    
    /**
     * Test the executeAsync feature
     */
    @Test
    public final void testAsyncSuccess() {
        final DelegateAsyncNotificationStrategy strategy = new DelegateAsyncNotificationStrategy(
                new NullNotificationStrategy(), new ThreadPoolExecutor(1, 1, 1L, TimeUnit.SECONDS, 
                        new LinkedBlockingQueue<Runnable>()));
        final Message message = new StringMessage("a", "a", new NullNotificationAddress());
        Future<Message> future = 
            strategy.executeAsync(new NotificationAddress[]{new NullNotificationAddress()}, 
                message);
        Message returnMessage = null;
        try {
            returnMessage = future.get();
        } catch (Exception e) {
            Assert.fail("Error");
        } 
        Assert.assertSame(message, returnMessage);
    }
    
    /**
     * Test the executeAsync feature
     */
    @Test
    public final void testAsyncError() {
        final DelegateAsyncNotificationStrategy strategy = new DelegateAsyncNotificationStrategy(
                new ErrorNotificationStrategy(), 
                new ThreadPoolExecutor(1, 1, 1L, TimeUnit.SECONDS, 
                        new LinkedBlockingQueue<Runnable>()));
        final Message message = new StringMessage("a", "a", new NullNotificationAddress());
        Future<Message> future = 
            strategy.executeAsync(new NotificationAddress[]{new NullNotificationAddress()}, 
                message);
        Message returnMessage = null;
        try {
            returnMessage = future.get();
            Assert.fail("Should have failed");
        } catch (Exception e) {
            //That's ok
        } 
    }
    /**
     * Null notificationAddress 
     * 
     * @author Christian Nardi
     * @since Apr 6, 2011
     */
    private class NullNotificationAddress implements NotificationAddress {
        
    }
    
    /**
     * Notification Strategy that thwrows Exception 
     * 
     * @author Christian Nardi
     * @since Apr 6, 2011
     */
    private class ErrorNotificationStrategy implements NotificationStrategy {

        /** @see NotificationStrategy#execute(NotificationAddress[], Message) */
        @Override
        public void execute(final NotificationAddress[] addresses, final Message message) {
            throw new IllegalArgumentException("Always error!");
        }
        
    }
}
