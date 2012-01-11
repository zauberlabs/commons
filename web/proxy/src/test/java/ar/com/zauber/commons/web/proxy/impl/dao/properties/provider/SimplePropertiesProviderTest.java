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
package ar.com.zauber.commons.web.proxy.impl.dao.properties.provider;

import java.util.Properties;

import junit.framework.TestCase;
import ar.com.zauber.commons.web.proxy.impl.dao.properties.PropertiesProvider;

/**
 * Tests {@link SimplePropertiesProvider}.
 * 
 * @author Juan F. Codagnone
 * @since Aug 29, 2008
 */
public class SimplePropertiesProviderTest extends TestCase {

    /** test */
    public final void testEmpty() {
        final PropertiesProvider p =  new SimplePropertiesProvider();
        assertEquals(0, p.getProperties().size());
    }
    
    /** test */
    public final void testChange() {
        final Properties props = new Properties();
        props.put("foo", "bar");
        final PropertiesProvider p =  new SimplePropertiesProvider(props);
        assertEquals("bar", p.getProperties().get("foo"));
        assertFalse(p.getProperties().contains("laa"));
        props.put("laa", "loo");
        assertEquals("loo", p.getProperties().get("laa"));
        assertEquals(2, p.getProperties().size());
    }
}
