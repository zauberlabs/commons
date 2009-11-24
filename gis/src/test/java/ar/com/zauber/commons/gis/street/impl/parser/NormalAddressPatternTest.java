/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.gis.street.impl.parser;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import ar.com.zauber.commons.gis.street.StreetsDAO;
import ar.com.zauber.commons.gis.street.impl.NullStreetDAO;


/**
 * Prueba {@link NormalAddressPattern}
 *  
 * @author Julián Luini
 * @since Nov 24, 2009
 */
public class NormalAddressPatternTest {

    /** caso simple */
    @Test
    public final void casoSimple() throws Exception {
        final List<Integer> l = new LinkedList<Integer>();
        final NormalAddressPattern a = new NormalAddressPattern() {
            /** @see NormalAddressPattern#createResult(String, String, StreetsDAO) */
            @Override
            protected Collection createResult(final String calle, 
                    final String alturaText,
                    final StreetsDAO streetsDAO) {
                l.add(123);
                Assert.assertEquals("CHARCAS", calle);
                Assert.assertEquals("2700", alturaText);
                return Collections.EMPTY_LIST;
            }
        };
        a.getAddressResult("CHARCAS 2700", new NullStreetDAO());
        Assert.assertEquals(1, l.size());
    }
    
    /** caso simple */
    @Test
    public final void casoConComas() throws Exception {
        final List<Integer> l = new LinkedList<Integer>();
        NormalAddressPattern a = new NormalAddressPattern() {
            /** @see NormalAddressPattern#createResult(String, String, StreetsDAO) */
            @Override
            protected Collection createResult(final String calle, 
                    final String alturaText,
                    final StreetsDAO streetsDAO) {
                l.add(123);
                Assert.assertEquals("ALSINA, ADOLFO", calle);
                Assert.assertEquals("932", alturaText);
                return Collections.EMPTY_LIST;
            }
        };
        a.getAddressResult("ALSINA, ADOLFO 932", new NullStreetDAO());
        Assert.assertEquals(1, l.size());
    }
}
