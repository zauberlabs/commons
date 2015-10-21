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
package ar.com.zauber.commons.message;

import java.util.concurrent.Future;

import ar.com.zauber.commons.dao.Closure;

/**
 * NotificationStrategy that sends messages asynchronously and adds the possibility to 
 * add a callback 
 * 
 * @author Christian Nardi
 * @since Apr 6, 2011
 */
public interface AsyncNotificationStrategy extends NotificationStrategy {
    /**
     * Notifies the guest sending him some message
     * 
     * @param message The message to include in the notification
     * @param addresses destination address
     */
    Future<Message> executeAsync(NotificationAddress[] addresses, Message message);
    
    /**
     * @param errorClosure closure to execute if something goes wrong
     */
    void setErrorClosure(Closure<Throwable> errorClosure);
}
