/*
 * Copyright (c) 2007 Garbarino S.A.  -- Todos los derechos reservados
 */

package ar.com.zauber.commons.repository.query.filters;

import ar.com.zauber.commons.repository.query.values.Value;
import ar.com.zauber.commons.repository.query.visitor.FilterVisitor;
        

/**
 * TODO Descripcion de la clase. Los comenterios van en castellano.
 * 
 * 
 * @author Martin A. Marquez
 * @since Sep 21, 2007
 */
public abstract class BinaryPropertyFilter extends PropertyFilter {

    private Value value;
    
    public BinaryPropertyFilter(String property, Value value) {
        super(property);
        this.value = value;
    }
    
    public Value getValue() {
        return value;
    }

    /** @see Filter#accept(FilterVisitor) */
    public void accept(FilterVisitor visitor) {
        visitor.visitBinaryPropertyFilter(this);
    }
    
    public abstract String getSymbol();
}