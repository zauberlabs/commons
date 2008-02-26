/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.common.image.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.Validate;
import org.codehaus.plexus.util.FileUtils;


/**
 * A Flyer that lives in the classpaths
 *
 * @author Juan F. Codagnone
 * @since Nov 14, 2005
 */
public class ResourceImage extends AbstractImage {
    /** resource */
    private final String resource;

    /**
     * Creates the ResourceFlyer.
     *
     * @param resource resource to load with the classloader
     * @throws IllegalArgumentException on error
     * @throws IOException on i/o error
     */
    public ResourceImage(final String resource) 
      throws IllegalArgumentException, IOException {
        super(getFileName(resource));
        Validate.notNull(resource);
        if(getClass().getClassLoader().getResource(resource) == null) {
            throw new IllegalArgumentException("resouces doesnt exists");
        }
        
        this.resource = resource;
        validateImage(getInputStream());
        
        // thumb
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        createThumbnail(getInputStream(), out);
        setThumb(new ByteArrayResource("thumb_" + getFileName(resource), out
                .toByteArray()));
    }

    /**
     * helper
     * 
     * @param resource resource
     * @return the basename of the resource
     */ 
    private static String getFileName(final String resource) {
        return FileUtils.basename(resource) + FileUtils.extension(resource);
    }

    /** @see ar.com.zauber.eventz.domain.event.Flyer#getOutputStream() */
    public final InputStream getInputStream() throws IOException {
        final InputStream is = getClass().getClassLoader()
                .getResourceAsStream(resource);
        if(is == null) {
            throw new RuntimeException("oppps. is is null!!");
        }
        return is;
    }
}
