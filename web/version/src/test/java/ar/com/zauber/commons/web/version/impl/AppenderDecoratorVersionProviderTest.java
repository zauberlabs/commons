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
package ar.com.zauber.commons.web.version.impl;

import junit.framework.TestCase;
import ar.com.zauber.commons.web.version.VersionProvider;

/**
 * Tests {@link AppenderDecoratorVersionProvider}.
 * 
 * @author Juan F. Codagnone
 * @since Jul 19, 2008
 */
public class AppenderDecoratorVersionProviderTest extends TestCase {

    /** test */
    public final void testPrefix() {
        final VersionProvider vp =  new AppenderDecoratorVersionProvider(
                new InmutableVersionProvider("1"), "build-", true);
        
        assertEquals("build-1", vp.getVersion());
        assertEquals("build-1", vp.getVersion());
    }
    
    /** test */
    public final void testSufix() {
        final VersionProvider vp =  new AppenderDecoratorVersionProvider(
                new InmutableVersionProvider("1"), "-build", false);
        
        assertEquals("1-build", vp.getVersion());
        assertEquals("1-build", vp.getVersion());
    }
    
    /** test */
    public final void testEmpty() {
        final VersionProvider vp =  new AppenderDecoratorVersionProvider(
                new EmptyVersionProvider(), "-build", false);
        
        assertEquals("", vp.getVersion());
        assertEquals("", vp.getVersion());
    }
}
