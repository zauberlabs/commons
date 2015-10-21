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
package ar.com.zauber.commons.spring.beans.factory.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;



/**
 * Implementacion de {@see HostnameProvider} que usa java.net.*.
 * 
 * @author Juan F. Codagnone
 * @since Aug 5, 2006
 */
public class JavaNetHostnameProvider implements HostnameProvider {
    /** localhost hostname */
    private final String hostname;
    
    /**
     * Creates the JavaNetHostnameProvider.
     *
     * @throws UnknownHostException si localhost no tiene nombre :(
     */
    public JavaNetHostnameProvider() throws UnknownHostException {
        hostname = InetAddress.getLocalHost().getHostName();
    }
    
    /** @see HostnameProvider#getHostname() */
    public final String getHostname() {
        return hostname;
    }
}
