/**
 * Copyright (c) 2005-2009 Zauber S.A. <http://www.zauber.com.ar/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ar.com.zauber.commons.dao.impl;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

import ar.com.zauber.commons.dao.RankeableResult;


/**
 * Tests para {@link InmutableRankeableResult}
 * 
 * @author Juan Almeyda
 * @since Oct 2, 2009
 */
public class InmutableRankeableResultTest {

    /** test del equals*/
    @Test
    public final void testEqualsYHashCode() {
        final RankeableResult<String> rr1 = new InmutableRankeableResult<String>(1, "jaja");
        final RankeableResult<String> rr2 = new InmutableRankeableResult<String>(1, "jaja");
        Assert.assertEquals(rr1, rr1);
        Assert.assertFalse(rr1.equals(null));
        Assert.assertEquals(rr1, rr2);
        Assert.assertEquals(rr1.hashCode(), rr2.hashCode());
        Assert.assertEquals(rr1.hashCode(), rr1.hashCode());
    }
    
    /** test de not equals*/
    @Test
    public final void testNotEquals() {
        final RankeableResult<String> rr1 =  new InmutableRankeableResult<String>(99, "armando");
        final RankeableResult<String> rr2 =  new InmutableRankeableResult<String>(99.5, "armando");
        final RankeableResult<String> rr3 =  new InmutableRankeableResult<String>(99.5, "paredes");
        assertNotSame(rr1, rr2);
        assertNotSame(rr3, rr2);
        assertNotSame(rr3, rr1);
        assertNotSame(rr1.hashCode(), rr2.hashCode());
        assertNotSame(rr1.hashCode(), rr3.hashCode());
        assertNotSame(rr3.hashCode(), rr2.hashCode());
    }
    
}
