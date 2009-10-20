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
package ar.com.zauber.commons.dao;

import org.junit.Assert;
import org.junit.Test;


/**
 * Test {@link OrderingBuilder}.
 * 
 * @author Juan F. Codagnone
 * @since Aug 7, 2009
 */
public class OrderingBuilderTest {

    /**
     *  test the construction of a new {@link Ordering} using 
     * {@link OrderingBuilder} 
     */
    @Test
    public final void build() throws Exception {
        final Ordering ordering = Ordering.builder()
                             .asc("name")
                             .ascIgnoringCase("username")
                             .desc("points")
                             .descIgnoringCase("foo")
                             .ordering();
        
        final Ordering expected = new Ordering(
                new Order("name"), 
                new Order("username", true, true),
                new Order("points", false), 
                new Order("foo", false, true));
        
        Assert.assertEquals(expected, ordering);
    }
}
