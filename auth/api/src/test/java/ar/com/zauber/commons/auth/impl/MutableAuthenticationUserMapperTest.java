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
package ar.com.zauber.commons.auth.impl;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import ar.com.zauber.commons.auth.AuthenticationUserMapper;


/**
 * Test {@link MutableAuthenticationUserMapper}
 * 
 * 
 * @author Juan F. Codagnone
 * @since Mar 22, 2010
 */
public class MutableAuthenticationUserMapperTest {

    /** test */
    @Test(expected = IllegalArgumentException.class)
    public final void nullConstructor() throws Exception {
        new MutableAuthenticationUserMapper<String>(null);
    }
    
    /** test */
    @SuppressWarnings("unchecked")
    @Test
    public final void okConstructor() throws Exception {
        final AuthenticationUserMapper<String> t1 = 
            mock(AuthenticationUserMapper.class);
        final AuthenticationUserMapper<String> t2 = 
            mock(AuthenticationUserMapper.class);
        assertNotSame(t1,  t2);
        final MutableAuthenticationUserMapper c = 
            new MutableAuthenticationUserMapper<String>(t1);
        assertSame(t1,  c.getTarget());
        c.setTarget(t2);
        assertSame(t2,  c.getTarget());
        
        try {
            c.setTarget(null);
            fail();
        } catch(IllegalArgumentException e) {
            // ok
        }
    }
}
