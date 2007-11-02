/*
 * Copyright (c) 2007 Garbarino S.A.  -- Todos los derechos reservados
 */

package ar.com.zauber.commons.repository.query.filters;
        
import java.util.List;

import ar.com.zauber.commons.repository.query.values.Value;
import ar.com.zauber.commons.repository.query.visitor.FilterVisitor;
/**
 * TODO Descripcion de la clase. Los comenterios van en castellano.
 * 
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
