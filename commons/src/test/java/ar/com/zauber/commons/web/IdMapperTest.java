/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.web;

import junit.framework.TestCase;


/**
 * Test for ID mapper.
 * 
 * @author Andrés Moratti
 * @since Nov 8, 2005
 */
public class IdMapperTest extends TestCase {
    
    /** unit test*/
    public final void testMapId() {
        IdMapper idMapper = new LongIdMapper();
        
        final long []ids = {0, 1, 10000000};
        
        for(long l : ids) {
            assertEquals(l, idMapper.unMap(idMapper.map(l)));
        }
    }
}
