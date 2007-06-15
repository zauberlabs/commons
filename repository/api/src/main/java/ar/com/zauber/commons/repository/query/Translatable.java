/*
 * Copyright (c) 2005 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.repository.query;


/**
 * Interface for translatable queries or expressions
 * 
 * Classes implementing this interface would be subject to being "visited"
 * by a Translator
 * 
 * @author Gabriel V. Baños
 * @since 20/06/2005
 */
public interface Translatable {
    
    /**
     * @param aTranslator A translator
     */
    void acceptTranslator(Translator aTranslator);
}
