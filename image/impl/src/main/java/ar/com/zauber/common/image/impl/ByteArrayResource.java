/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.common.image.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.Validate;

import ar.com.zauber.common.image.model.Resource;


/**
 * A resource that lives in the memory
 *
 * @author Juan F. Codagnone
 * @since Nov 19, 2005
 */
public class ByteArrayResource implements Resource {
    /** the resource name */
    private String name;
    
    /** the real data */
    private byte [] data;
    
    /**
     * Creates the ByteArrayResource.
     *
     * @param name name of the resource
     * @param data real data. we use the reference and not a copy 
     */
    public ByteArrayResource(final String name, final byte []data) {
        Validate.notNull(name, "name");
        Validate.notNull(data, "data");
        
        this.name = name;
        this.data = data;
    }
    
    /** Creates the ByteArrayResource. used by the persistence */
    protected ByteArrayResource() {
        // void - used by the persistence
    }
    
    /** @see ar.com.zauber.eventz.domain.event.Resource#getName() */
    public final String getName() {
        return name;
    }

    /** @see ar.com.zauber.eventz.domain.event.Resource#getInputStream() */
    public final InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(data);
    }

}
