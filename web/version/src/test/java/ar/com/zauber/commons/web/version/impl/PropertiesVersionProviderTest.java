/*
 * Copyright (c) 2005-2008 Zauber S.A. <http://www.zauber.com.ar/>
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
package ar.com.zauber.commons.web.version.impl;

import java.util.Properties;

import junit.framework.TestCase;
import ar.com.zauber.commons.web.version.impl.PropertiesVersionProvider;

/**
 * Tests for {@link PropertiesVersionProvider}.
 * 
 * 
 * @author Juan F. Codagnone
 * @since Jul 19, 2008
 */
public class PropertiesVersionProviderTest extends TestCase {

    /** test */
    public final void testNotExists() {
        assertEquals("", new PropertiesVersionProvider(
                new Properties(), "a").getVersion());
    }
    
    
    /** test */
    public final void testExists() {
        Properties p = new Properties();
        p.put("a", "b");
        assertEquals("b", new PropertiesVersionProvider(p, "a").getVersion());
    }
    
    
    /** test */
    public final void testResourceNot() {
        assertEquals("", new PropertiesVersionProvider("asdads", "a").getVersion());
    }
    
    /** test */
    public final void testResource() {
        assertEquals("0.0-SNAPSHOT", 
         new PropertiesVersionProvider(
           "classpath:ar/com/zauber/commons/web/version/impl/test-pom.properties", 
           "version").getVersion());
    }
}
