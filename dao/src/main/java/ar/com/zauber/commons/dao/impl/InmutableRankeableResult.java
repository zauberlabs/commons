/**
 * Copyright (c) 2009 Clarin Global S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.dao.impl;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.RankeableResult;

/**
 * Implementacion inmutable de {@link RankeableResult}.
 *  
 * @author Juan F. Codagnone
 * @since Feb 26, 2009
 * @param <T> t
 */
public class InmutableRankeableResult<T> implements RankeableResult<T> {
    private final Number hits;
    private final T result;
    
    /**
     * Creates the InmutableCountResult.
     *
     */
    public InmutableRankeableResult(final Number hits, final T result) {        
        Validate.notNull(hits);
        Validate.notNull(result);
        
        this.hits = hits;
        this.result = result;
    }
    
    /** @see RankeableResult#getRanking() */
    public final Number getRanking() {
        return hits;
    }

    /** @see RankeableResult#getResult() */
    public final T getResult() {
        return result;
    }
    
    /** @see Object#toString() */
    @Override
    public final String toString() {
        return "" + hits + ": " + result;
    }
}