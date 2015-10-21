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

import ar.com.zauber.commons.message.Message;
import ar.com.zauber.commons.message.NotificationAddress;
import ar.com.zauber.commons.message.mail.EmailService;

/**
 * Test for {@link BufferedServiceEmailNotificationStrategy}.
 * 
 * 
 * @author Andrés Moratti
 * @since Jan 27, 2011
 */
public class BufferedServiceEmailNotificationStrategyTest {

    /**
     * Tests execute
     */
    @SuppressWarnings("unchecked")
    @Test
    public final void testExecute() {
        final EmailService emailService = mock(EmailService.class);
        final BufferedServiceEmailNotificationStrategy bsens = 
            new BufferedServiceEmailNotificationStrategy(emailService, 
                    5, "domain.com", "account");
        final Message message = mock(Message.class);
        when(message.getReplyToAddress()).thenReturn(
                new JavaMailEmailAddress("a@a.com"));
        when(message.getSubject()).thenReturn("subject");
        when(message.getContent()).thenReturn("content");
        
        for (int i = 0; i < 12; i++) {
            bsens.execute(new NotificationAddress[] {
                    new JavaMailEmailAddress("b@b.com"), }, message);
        }
        
        verify(emailService, times(2)).sendEmails(anyList());
    }
    
}
