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
package ar.com.zauber.commons.message.impl.nil;

import ar.com.zauber.commons.message.Message;
import ar.com.zauber.commons.message.NotificationAddress;
import ar.com.zauber.commons.message.NotificationStrategy;


/**
 * Null implementation for 
 * {@link ar.com.zauber.eventz.domain.event.NotificationStrategy}. 
 * <p/>
 * Usefull for tests
 *
 * @author Juan F. Codagnone
 * @since Nov 12, 2005
 */
public class NullNotificationStrategy implements NotificationStrategy {

    /** @see NotificationStrategy#execute(NotificationAddress[], Message) */
    public void execute(final NotificationAddress [] addresses,
            final Message message) {
        // nothing to do...
    }
}
