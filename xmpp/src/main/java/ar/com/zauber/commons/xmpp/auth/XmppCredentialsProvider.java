/**
 * Copyright (c) 2005-2009 Zauber S.A. <http://www.zauber.com.ar/>
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
package ar.com.zauber.commons.xmpp.auth;

/**
 * Credentials provider for a xmpp connection
 * 
 * @author Juan F. Codagnone
 * @since Jul 12, 2008
 */
public interface XmppCredentialsProvider {

    /** @returns the username */
    String getUsername();
    
    /** @returns the password */
    String getPassword();
    
    /** @returns the connection resource */
    String getResource();
    
    /** @returns the priority for the resource */
    int getPriority();
}
