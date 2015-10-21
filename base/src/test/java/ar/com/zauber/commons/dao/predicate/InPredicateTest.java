/**
 * Copyright (c) 2005-2015 Zauber S.A. <http://flowics.com/>
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
package ar.com.zauber.commons.dao.predicate;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import ar.com.zauber.commons.dao.Predicate;


/**
 * Tests the {@link InPredicate} 
 * 
 * @author Christian Nardi
 * @since Oct 30, 2009
 */
public class InPredicateTest {
    /**
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        final Predicate<String> predicate = 
            new InPredicate<String>(Arrays.asList(
                    new String[]{"hello", "bye", "hey!"}));
        Assert.assertTrue(predicate.evaluate("hello"));
        Assert.assertTrue(predicate.evaluate("bye"));
        Assert.assertTrue(predicate.evaluate("hey!"));
        Assert.assertFalse(predicate.evaluate("hum?"));
    }
}
