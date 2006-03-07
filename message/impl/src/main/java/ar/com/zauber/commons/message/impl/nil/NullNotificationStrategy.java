/*
 * Copyright (c) 2005 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.message.impl.nil;

import ar.com.zauber.commons.message.Message;
import ar.com.zauber.commons.message.NotificationAddress;
import ar.com.zauber.commons.message.NotificationStrategy;


/**
 * Null implementation for 
 * {@link ar.com.zauber.eventz.domain.event.NotificationStrategy}. 
 * <p/>
 * Usefull for tests
 *
 * @author Juan F. Codagnone
 * @since Nov 12, 2005
 */
public class NullNotificationStrategy implements NotificationStrategy {

    /** @see NotificationStrategy#execute(NotificationAddress[], Message) */
    public void execute(final NotificationAddress [] addresses,
            final Message message) {
        // nothing to do...
    }
}
