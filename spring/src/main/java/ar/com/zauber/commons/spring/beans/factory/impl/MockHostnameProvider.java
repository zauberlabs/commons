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
package ar.com.zauber.commons.spring.beans.factory.impl;

import org.apache.commons.lang.Validate;



/**
 * Implementacion tonta de {@link HostnameProvider} util para testeos
 * 
 * @author Juan F. Codagnone
 * @since Aug 5, 2006
 */
public class MockHostnameProvider implements HostnameProvider {
    /** hostname */
    private final String hostname;

    /**
     * Creates the MockHostnameProvider.
     *
     * @param hostname hostname
     */
    public MockHostnameProvider(final String hostname) {
        Validate.notEmpty(hostname);
        this.hostname = hostname;
    }
   
    /** @see HostnameProvider#getHostname()  */
    public final String getHostname() {
        return hostname;
    }

}
