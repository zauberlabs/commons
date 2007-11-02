/*
 * Copyright (c) 2007 Garbarino S.A.  -- Todos los derechos reservados
 */

package ar.com.zauber.commons.repository.query.filters;

import java.util.List;

import ar.com.zauber.commons.repository.query.connectors.Connector;
import ar.com.zauber.commons.repository.query.visitor.FilterVisitor;
        

/**
 * TODO Descripcion de la clase. Los comenterios van en castellano.
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
