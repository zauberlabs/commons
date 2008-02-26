/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.repository.query.filters;
        
import java.util.List;

import ar.com.zauber.commons.repository.query.values.Value;
import ar.com.zauber.commons.repository.query.visitor.FilterVisitor;
/**
 * Filtro que limita a aquellos objetos cuya propiedad este incluida en el
 * conjunto de valores.
 * 
 * @author Martin A. Marquez
 * @since Sep 21, 2007
 */
public class InPropertyFilter extends PropertyFilter {
    
    private List<Value> values;

    public InPropertyFilter(String property, List<Value>values) {
        super(property);
        // TODO: Clone the collection.
        this.values = values;
    }

    /** @see ar.com.zauber.commons.repository.query.filters.Filter#accept(ar.com.zauber.commons.repository.query.visitor.FilterVisitor) */
    public void accept(FilterVisitor visitor) {
        visitor.visitInPropertyFilter(this);
    }

    
    /**
     * @return
     */
    public List<Value> getValues() {
        return values;
    }
    
}
