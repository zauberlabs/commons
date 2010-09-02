/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.conversion.setters;

import ar.com.zauber.commons.conversion.FieldSetterStrategy;

/**
 * Utility 
 * 
 * @author Juan F. Codagnone
 * @since Sep 2, 2010
 */
public interface FieldSetterStrategies {
    /** singleton */
    FieldSetterStrategy FIELD_SETTER_STRATEGY = new FieldSetSetterStrategy();
    
    /** singleton */
    FieldSetterStrategy COLLECTION_ADD_STRATEGY = 
        new CollectionGetAndAddFieldSetterStrategy();
}
