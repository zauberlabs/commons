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
package ar.com.zauber.commons.web.version.impl;

import java.util.Arrays;

import junit.framework.TestCase;
import ar.com.zauber.commons.web.version.VersionProvider;
import ar.com.zauber.commons.web.version.impl.EmptyVersionProvider;
import ar.com.zauber.commons.web.version.impl.InmutableVersionProvider;
import ar.com.zauber.commons.web.version.impl.MultipleVersionProvider;
import ar.com.zauber.commons.web.version.impl.ValidatorVersionProvider;

/**
 * Tests for {@link MultipleVersionProvider}.
 * 
 * 
 * @author Juan F. Codagnone
 * @since Jul 19, 2008
 */
public class MultipleVersionProviderTest extends TestCase {

    /** test */
    public final void testFoo() {
        final VersionProvider vp = 
            new MultipleVersionProvider(Arrays.asList(new VersionProvider[]{
                new EmptyVersionProvider(),
                new VersionProvider() {
                    public String getVersion() {
                        throw new RuntimeException("bla");
                    }
                },
                new ValidatorVersionProvider(new InmutableVersionProvider("%+")),
                new InmutableVersionProvider("ok"),
                new InmutableVersionProvider("no"),
        }));
        
        assertEquals("ok", vp.getVersion());
    }
}

