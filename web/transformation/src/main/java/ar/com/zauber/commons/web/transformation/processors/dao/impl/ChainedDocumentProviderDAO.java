/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.transformation.processors.dao.impl;

import java.security.InvalidParameterException;
import java.util.List;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.web.transformation.processors.DocumentProvider;
import ar.com.zauber.commons.web.transformation.processors.dao.DocumentProviderDAO;

/**
 * Implementacion de {@link DocumentProvider} que permite encadenar
 * 
 * @author Pablo Grigolatto
 * @since Jun 22, 2009
 */
public class ChainedDocumentProviderDAO implements DocumentProviderDAO {
    private final DocumentProviderDAO defaultDocumentProviderDAO;
    private final List<DocumentProviderDAO> daos;

    /** Creates the ChainedDocumentProviderDAO. */
    public ChainedDocumentProviderDAO(
            final DocumentProviderDAO defaultDocumentProviderDAO,
            final List<DocumentProviderDAO> daos) {
        Validate.notNull(defaultDocumentProviderDAO);
        Validate.notNull(daos);
        this.defaultDocumentProviderDAO = defaultDocumentProviderDAO;
        this.daos = daos;
    }

    /** @see DocumentProviderDAO#getDocumentProvider(String) */
    public final DocumentProvider getDocumentProvider(final String url) {
        for (DocumentProviderDAO dao : daos) {
            if(dao.accept(url)) {
                return dao.getDocumentProvider(url);
            }
        }
        if(defaultDocumentProviderDAO.accept(url)) {
            return defaultDocumentProviderDAO.getDocumentProvider(url);
        }
        throw new InvalidParameterException("no document provider for: " + url);
    }

    /** @see DocumentProviderDAO#accept(String) */
    public final boolean accept(final String url) {
        for (DocumentProviderDAO dao : daos) {
            if(dao.accept(url)) {
                return true;
            }
        }
        return defaultDocumentProviderDAO.accept(url);
    }

}
