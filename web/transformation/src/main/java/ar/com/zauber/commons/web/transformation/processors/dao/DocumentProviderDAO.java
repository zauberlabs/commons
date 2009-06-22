/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.transformation.processors.dao;

import ar.com.zauber.commons.web.transformation.processors.DocumentProvider;

/**
 * Devuelve un {@link DocumentProvider} segun la url
 * 
 * @author Pablo Grigolatto
 * @since Jun 22, 2009
 */
public interface DocumentProviderDAO {

    /** decide si puede manejar esa url */
    boolean accept(final String url);
    
    /** @return el {@link DocumentProvider} que sabe manejar esa url*/
    DocumentProvider getDocumentProvider(final String url);

}
