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
package ar.com.zauber.commons.message.impl.mail;

import static org.mockito.Mockito.*;

import org.junit.Test;

import ar.com.zauber.commons.message.NotificationStrategy;

/**
 * Test for {@link BalancerNotificationStrategy}.
 * 
 * 
 * @author Andrés Moratti
 * @since Jan 27, 2011
 */
public class BalancerNotificationStrategyTest {

    /**
     * Tests execute
     */
    @Test
    public final void testExecute() {
        final NotificationStrategy ns1 = mock(NotificationStrategy.class);
        final NotificationStrategy ns2 = mock(NotificationStrategy.class);
        
        final BalancerNotificationStrategy bns1 = 
            new BalancerNotificationStrategy(1, ns1, ns2);
        
        for (int i = 0; i < 10; i++) {
            bns1.execute(null, null);
        }
        
        verify(ns1, times(10)).execute(null, null);
        verify(ns2, never()).execute(null, null);
        
        reset(ns1);
        reset(ns2);
        
        final BalancerNotificationStrategy bns2 = 
            new BalancerNotificationStrategy(0, ns1, ns2);
        
        for (int i = 0; i < 10; i++) {
            bns2.execute(null, null);
        }
        
        verify(ns1, never()).execute(null, null);
        verify(ns2, times(10)).execute(null, null);
     
        final BalancerNotificationStrategy bns3 = 
            new BalancerNotificationStrategy(0.5, ns1, ns2);
        
        for (int i = 0; i < 50; i++) {
            bns3.execute(null, null);
        }
        
        verify(ns1, atLeastOnce()).execute(null, null);
        verify(ns2, atLeastOnce()).execute(null, null);
    }
    
}
