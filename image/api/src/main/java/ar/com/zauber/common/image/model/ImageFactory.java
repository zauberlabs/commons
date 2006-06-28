/*
 * Copyright (c) 2005 Zauber  -- All rights reserved
 */
package ar.com.zauber.common.image.model;

import java.io.IOException;
import java.io.InputStream;


/**
 * Factory for {@link ar.com.zauber.eventz.domain.event.Flyer}s.
 *
 * @author Juan F. Codagnone
 * @since Nov 14, 2005
 */
public interface ImageFactory {

    /**
     * Creates a flyer to use with an event, reading content from is.
     * It doesnt call to close. it is your responsability.
     * 
     * @param is input source
     * @param name name of the flyer
     * @return a new Flyer
     * @throws IOException if there is an error reading is
     */
    Image createFlyer(InputStream is, final String name) throws IOException;
    
}
