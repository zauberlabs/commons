/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.dao.predicate;

import java.util.Collection;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Predicate;


/**
 * Condition que da true si la cantidad de throwables es mayor a un parametro.
 * @author Mariano Semelman
 * @since Oct 13, 2009
 */
public class ThrowableMaxAmountPredicate  
    implements Predicate<Collection<Throwable>> {

    private int maxElements;
    
    
    /**
     * Creates the MaxAmountCondition.
     *true si la cantidad de throwables es mayor a el parametro.
     * @param maxElements
     */
    public ThrowableMaxAmountPredicate(final int maxElements) {
        super();
        this.maxElements = maxElements;
    }


    /** @see Condition#evaluate(Object) */
    public final boolean evaluate(final Collection<Throwable> conjunto) {
        Validate.notNull(conjunto);
        return conjunto.size() > maxElements;
    }
    

    

}
