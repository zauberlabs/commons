/*
 * Copyright (c) 2005 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.repository.query;


/**
 * Interface for query translators
 * 
 * @author Gabriel V. Baños
 * @since 20/06/2005
 */
public interface Translator {
    
    /**
     * @param aQuery a Query
     */
    void translate(Query aQuery);
    
}
