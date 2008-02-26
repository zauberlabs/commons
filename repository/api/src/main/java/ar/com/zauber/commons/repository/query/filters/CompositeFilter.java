/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.repository.query.filters;

import java.util.List;

import ar.com.zauber.commons.repository.query.connectors.Connector;
import ar.com.zauber.commons.repository.query.visitor.FilterVisitor;
        

/**
 * Filtro que es la composición de otros filtros definiendo un conector.
 * 
 * 
 * @author Martin A. Marquez
 * @since Sep 21, 2007
 */
public class CompositeFilter extends BaseFilter {

    private Connector connector;
    private List<BaseFilter> filters;
    
    public CompositeFilter(Connector connector, List<BaseFilter> filters) {
		super();
		this.connector = connector;
		this.filters = filters;
	}

	/** @see ar.com.zauber.commons.repository.query.filters.Filter#accept(ar.com.zauber.commons.repository.query.visitor.FilterVisitor) */
    public void accept(FilterVisitor visitor) {
        visitor.visitCompositeFilter(this);
    }

    public Connector getConnector() {
        return connector;
    }

    public List<BaseFilter> getFilters() {
        return filters;
    }
    
}
