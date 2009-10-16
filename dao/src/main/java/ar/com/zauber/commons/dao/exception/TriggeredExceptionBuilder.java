/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.dao.exception;

import java.util.Collection;
import java.util.LinkedList;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Predicate;


/**
 * Builder que construye una exception 
 * recibe un Predicate sobre Set<Throwable>
 * se le van agregando excpetion y cuando el predicado da true
 * devuelve una triggered exception.
 * 
 * 
 * @author Mariano Semelman
 * @since Oct 13, 2009
 */
public class TriggeredExceptionBuilder  {


    private final Collection<Throwable> exceptions = new LinkedList<Throwable>();
    private final Predicate<Collection<Throwable>> condition;
    
    
    
    /**
     * Creates the BuilderTriggeredException.
     *
     * @param condition de 
     */
    public TriggeredExceptionBuilder(
            final Predicate<Collection<Throwable>> condition) {
        super();
        Validate.notNull(condition);
        this.condition = condition;
    }
    
    /** agrega un throwable a si mismo */
    public final TriggeredExceptionBuilder add(final Throwable t) {
        Validate.notNull(t);
        exceptions.add(t);
        if(condition.evaluate(exceptions)) {
            throw new TriggeredException(exceptions);
        }
        return this;
    }
    
    /** devuelve todas las excepciones ocurridas */
    public final Collection<Throwable> getThrowablesOcurred() {
        return this.exceptions;
    }
}
