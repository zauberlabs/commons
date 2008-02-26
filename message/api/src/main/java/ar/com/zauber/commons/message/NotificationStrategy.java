/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.message;



/**
 * TODO Brief description.
 * 
 * TODO Detail
 * 
 * @author Martin Andres Marquez
 * @since Jun 22, 2005
 */
public interface NotificationStrategy {
    
    /**
     * Notifies the guest sending him some message
     * 
     * @param message The message to include in the notification
     * @param addresses destination address
     */
    void execute(NotificationAddress[] addresses, Message message);
    

}
