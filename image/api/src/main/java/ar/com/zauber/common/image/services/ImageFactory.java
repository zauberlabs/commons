/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.common.image.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import ar.com.zauber.common.image.model.Image;


/**
 * Factory for {@link ar.com.zauber.eventz.domain.event.Flyer}s.
 *
 * @author Juan F. Codagnone
 * @since Nov 14, 2005
 */
public interface ImageFactory {

    /**
     * Creates an image to use with an object, reading content from is.
     * It doesnt call to close. it is your responsability.
     * 
     * @param is input source
     * @param name name of the image
     * @return a new Image
     * @throws IOException if there is an error reading is
     */
    Image createImage(InputStream is, final String name) throws IOException;
    
    /**
     * @param id
     * @return
     */
    Image retrieveImage(Serializable id) throws IOException;
}
