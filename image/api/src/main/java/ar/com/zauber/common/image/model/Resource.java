/*
 * Copyright (c) 2005 Zauber  -- All rights reserved
 */
package ar.com.zauber.common.image.model;

import java.io.IOException;
import java.io.InputStream;


/**
 * An event resource. A resource could be any type of file. For example
 * a pdf.
 * 
 * @author Juan F. Codagnone
 * @since Nov 19, 2005
 */
public interface Resource {

    /** @return the name of the flyer. for example my_party.jpg */
    String getName();
    
    /** 
     * @return a stream with the flyier. You must close the stream. 
     * @throws IOException on error
     */
    InputStream getInputStream() throws IOException;
}
