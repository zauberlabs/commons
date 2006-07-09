/*
 * Copyright (c) 2006 Zauber  -- All rights reserved
 */
package ar.com.zauber.common.image.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.lang.NotImplementedException;

import ar.com.zauber.common.image.services.ImageRetriver;


/**
 * dummy implementation of {@link ImageRetriver}
 * 
 * @author Juan F. Codagnone
 * @since Jul 9, 2006
 */
public class NotImplementedImageRetriver implements ImageRetriver {

    /** @see ImageRetriver#retrive(java.net.URL) */
    public final InputStream retrive(final URL url) throws IOException {
        throw new NotImplementedException("wont implement");
    }

}
