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
package ar.com.zauber.commons.spring.mail;

import java.awt.Dimension;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;


/**
 * Debug implementation of {@link org.springframework.mail.MailSender} that 
 * don't send the emails at all. It only show it in the screen using swing
 *
 * @author Juan F. Codagnone
 * @since Oct 5, 2005
 */
public class SwingMailSender implements MailSender {
    /** number of mails sent */
    private int i = 0; 
    
    /** @see MailSender#send(org.springframework.mail.SimpleMailMessage) */
    public final void send(final SimpleMailMessage simpleMessage)
           throws MailException {
        send(new SimpleMailMessage[] {simpleMessage});
        
    }

    /** @see SimpleMailMessage[]) */
    public final void send(final SimpleMailMessage [] simpleMessages) 
           throws MailException {
        final JTextArea area = new JTextArea(AbstractMailSender.toString(
                simpleMessages));
        area.setLineWrap(true);
        final JFrame frame = new JFrame("email sent");
        frame.getContentPane().add(new JScrollPane(area,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        final int frameWidth = 640;
        final int frameHeight = 480;
        frame.setPreferredSize(new Dimension(frameWidth, frameHeight));
        frame.pack();
        frame.setVisible(true);
    }
    
}
