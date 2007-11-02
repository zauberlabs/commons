/*
 * Copyright (c) 2007 Garbarino S.A.  -- Todos los derechos reservados
 */

package ar.com.zauber.commons.repository.query.visitor;

import ar.com.zauber.commons.repository.query.filters.BinaryPropertyFilter;
import ar.com.zauber.commons.repository.query.filters.CompositeFilter;
import ar.com.zauber.commons.repository.query.filters.InPropertyFilter;
import ar.com.zauber.commons.repository.query.filters.IsNullPropertyFilter;
import ar.com.zauber.commons.repository.query.filters.NullFilter;
        

/**
 * TODO Descripcion de la clase. Los comenterios van en castellano.
 * 
 * 
 * @author Martin A. Marquez
 * @since Sep 24, 2007
 */
public interface FilterVisitor {

    
    /**
     * @param binaryPropertyFilter
     */
    void visitBinaryPropertyFilter(BinaryPropertyFilter binaryPropertyFilter);

    
    /**
     * @param compositeFilter
     */
    void visitCompositeFilter(CompositeFilter compositeFilter);


    
    /**
     * @param isNullPropertyFilter
     */
    void visitIsNullPropertyFilter(IsNullPropertyFilter isNullPropertyFilter);


    
    /**
     * @param inPropertyFilter
     */
    void visitInPropertyFilter(InPropertyFilter inPropertyFilter);


    
    /**
     * @param nullFilter
     */
    void visitNullFilter(NullFilter nullFilter);
    
}
