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
package ar.com.zauber.commons.web.transformation.censors;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ar.com.zauber.commons.web.transformation.censors.impl.StripPrefixCensorAccess;

/**
 * Tests {@link StripPrefixCensorAccess}.
 * 
 * @author Alejandro Souto
 * @since 11/11/2008
 */
public class StripPrefixCensorAccessTest {

    /** test */
    @Test
    public final void testSaca() {
        final CensorAccess censorAccess = new StripPrefixCensorAccess("/aaa", 
                new CensorAccess() {
            public boolean canAccess(final String uri) {
                assertEquals("/remote/hudson/pepe", uri);
                return false;
            }
        });
        
        censorAccess.canAccess("/aaa/remote/hudson/pepe");
    }
    
    /** test */
    @Test
    public final void testNoSaca() {
        final CensorAccess censorAccess = new StripPrefixCensorAccess("/aaa", 
                new CensorAccess() {
            public boolean canAccess(final String uri) {
                assertEquals("/bbbb/remote/hudson/pepe", uri);
                return false;
            }
        });
        
        censorAccess.canAccess("/bbbb/remote/hudson/pepe");
    }
}
