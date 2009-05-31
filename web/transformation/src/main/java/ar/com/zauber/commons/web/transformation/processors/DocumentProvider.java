/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.transformation.processors;

import java.io.InputStream;
import java.io.OutputStream;

import org.w3c.dom.Document;

/**
 * Dado {@link InputStream} crea un {@link Document}.  
 * 
 * @author Juan F. Codagnone
 * @since May 30, 2009
 */
public interface DocumentProvider {

    /** crea un document en base a un inputstream */
    Document parse(InputStream inputStream);
    
    /** escribe un document a un output stream*/
    void serialize(Document document, OutputStream outputStream);
}
