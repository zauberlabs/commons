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
package ar.com.zauber.commons.message;

/**
 * Creates {@link NotificationAddress} based on a string. It is usefull
 * to hide the {@link NotificationAddress} implementation, and also
 * to manage better the dependencies. 
 * 
 * 
 * @author Juan F. Codagnone
 * @since Aug 10, 2008
 */
public interface NotificationAddressFactory {

    /** 
     * @return a {@link NotificationAddress} for the string representation.
     *  it nevers returns null
     */
    NotificationAddress createNotificationAddress(String notificationAddress);
}
