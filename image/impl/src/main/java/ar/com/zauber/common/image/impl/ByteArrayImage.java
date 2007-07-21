/*
 * Copyright (c) 2005 Zauber  -- All rights reserved
 */
package ar.com.zauber.common.image.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang.Validate;

import ar.com.zauber.common.image.model.Image;
import ar.com.zauber.common.image.model.Resource;


/**
 * TODO Brief description.
 *
 * TODO Detail
 *
 * @author Juan F. Codagnone
 * @since Nov 21, 2005
 */
public class ByteArrayImage extends ByteArrayResource implements Image {
    /** thumbnial */
    private ByteArrayResource thumbnail;
    
    /**
     * Creates the ByteArrayFlyer.
     *
     * @param name name of the flyer
     * @param data data
     * @throws IOException on i/o error
     */
    public ByteArrayImage(final String name, final byte [] data)
            throws IOException {
        super(name, data);
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        final ByteArrayInputStream is = new ByteArrayInputStream(data); 
        AbstractImage.createThumbnail(is, os);
        thumbnail = new ByteArrayResource("thumb_" + name, os.toByteArray());
    }
    
    /** Creates the ByteArrayFlyer. used by the persistence  */
    @SuppressWarnings("unused")
    private ByteArrayImage() {
        super();
    }
    
    /** @see ar.com.zauber.eventz.domain.event.Flyer#getThumbnail() */
    public final Resource getThumbnail() {
        Validate.notNull(thumbnail);
        return thumbnail;
    }
}
