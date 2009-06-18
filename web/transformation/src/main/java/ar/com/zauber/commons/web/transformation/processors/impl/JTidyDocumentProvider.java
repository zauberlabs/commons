/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.transformation.processors.impl;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.lang.Validate;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;

import ar.com.zauber.commons.web.transformation.processors.DocumentProvider;

/**
 * {@link DocumentProvider} que usa JTidy
 * 
 * 
 * @author Juan F. Codagnone
 * @since May 30, 2009
 */
public class JTidyDocumentProvider implements DocumentProvider {
    private final Tidy tidy;
    
    /** Creates the JTidyDocumentProvider. */
    public JTidyDocumentProvider() {
        tidy = new Tidy(); // obtain a new Tidy instance
        tidy.setQuiet(true);
        tidy.setShowWarnings(false);
        tidy.setXHTML(false);
    }
    
    /** Creates the JTidyDocumentProvider. */
    public JTidyDocumentProvider(int encoding) {
        this();
        tidy.setCharEncoding(encoding);
    }
    
    /** Creates the JTidyDocumentProvider. */
    public JTidyDocumentProvider(final Tidy tidy) {
        Validate.notNull(tidy);
        this.tidy = tidy;
    }
    
    /** @see DocumentProvider#getInputStream(InputStream) */
    public final Document parse(final InputStream inputStream) {
        return tidy.parseDOM(inputStream, null);
    }

    /** @see DocumentProvider#serialize(Document, OutputStream) */
    public final void serialize(final Document document, 
            final OutputStream outputStream) {
        tidy.pprint(document, outputStream);
    }
}
