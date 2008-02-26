/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.message;



/**
 * A (human) message
 *  
 * @author Juan F. Codagnone
 * @since Jun 18, 2005
 */
public interface Message {
    
    /** @return the content of the message */
    String getContent();

    /** @return the from address */
    NotificationAddress getReplyToAddress();

    /** @return the subject of the message */
    String getSubject();
    
    // TODO idea: por hay, en un futuro, un mensaje pueda tener muchas partes  
    // [ Part getPart() ] de diferentes tipos (texto o imagen). Esto permitiria
    // mandar el email de la invitacion y un flyer (o algo asi). Bah por hay
    // sino es algo que se le puede preguntar a Event.. ideas?
    
}
