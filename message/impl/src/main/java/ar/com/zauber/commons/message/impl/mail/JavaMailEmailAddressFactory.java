/**
 * Copyright (c) 2005-2010 Zauber S.A. <http://www.zauber.com.ar/>
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

import ar.com.zauber.commons.message.NotificationAddress;
import ar.com.zauber.commons.message.NotificationAddressFactory;

/**
 * {@link NotificationAddressFactory} that creates {@link JavaMailEmailAddress}.
 * 
 * 
 * @author Juan F. Codagnone
 * @since Aug 10, 2008
 */
public class JavaMailEmailAddressFactory implements NotificationAddressFactory {

    /** @see NotificationAddressFactory#createNotificationAddress(String) */
    public final NotificationAddress createNotificationAddress(
            final String notificationAddress) {
        return new JavaMailEmailAddress(notificationAddress);
    }

}
