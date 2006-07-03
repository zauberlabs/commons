/*
 * Copyright (c) 2006 Zauber  -- All rights reserved
 */
package ar.com.zauber.common.image.impl;


import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.apache.commons.lang.NotImplementedException;

import ar.com.zauber.common.image.model.Image;
import ar.com.zauber.common.image.services.ImageFactory;


/**
 * Dummy implementation
 * 
 * Useful for testing purposes
 * 
 * @author Gabriel V. Baños
 * @since 03/07/2006
 */
public class NotImplementedImageFactory implements ImageFactory {

    /**
     * @see ar.com.zauber.common.image.services.ImageFactory#createImage(
     * java.io.InputStream, java.lang.String)
     */
    public Image createImage(InputStream is, String name) throws IOException {
        throw new NotImplementedException("Won't implement in this class.");
    }

    /**
     * @see ar.com.zauber.common.image.services.ImageFactory#retrieveImage(
     * java.io.Serializable)
     */
    public Image retrieveImage(Serializable id) throws IOException {
        throw new NotImplementedException("Won't implement in this class.");
    }

}
