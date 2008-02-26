/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.repository.query.filters;

import ar.com.zauber.commons.repository.query.visitor.FilterVisitor;


/**
 * Interfaz que debe implementar un filtro.
 * 
 * 
 * @author Martin A. Marquez
 * @since Sep 21, 2007
 */
public interface Filter {
    public void accept(FilterVisitor visitor);
}
