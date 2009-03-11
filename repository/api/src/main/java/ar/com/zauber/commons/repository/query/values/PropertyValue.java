/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.repository.query.values;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

/**
 * Value to compare other properties.
 * 
 * @author Juan F. Codagnone
 * @since Mar 11, 2009
 */
public class PropertyValue implements Value {
    private final String otherProperty;


    /** Creates the PropertyValue. */
    public PropertyValue(final String otherProperty) {
        Validate.isTrue(!StringUtils.isBlank(otherProperty));
        
        this.otherProperty = otherProperty;
    }
    
    /** @return the name of the other property */
     public final String getOtherProperty() {
        return otherProperty;
    }
}
