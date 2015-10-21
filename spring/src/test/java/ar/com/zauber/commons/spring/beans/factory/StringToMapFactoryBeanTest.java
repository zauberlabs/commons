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
package ar.com.zauber.commons.spring.beans.factory;

import java.util.Map;
import java.util.Set;

import org.junit.Test;

import junit.framework.Assert;

/**
 * Tests for {@link StringToMapFactoryBean}
 * 
 * 
 * @author Francisco J. González Costanzó
 * @since May 3, 2010
 */
public class StringToMapFactoryBeanTest {

    /** test */
    @Test
    public final void testOk() throws Exception {
        StringToMapFactoryBean bean = new StringToMapFactoryBean(
                "1=uno,2=dos,3=tres", "=", ",");
        Map<String, String> map = bean.getObject();
        
        Assert.assertEquals(3, map.size());
        Set<String> keySet = map.keySet();
        Assert.assertTrue(keySet.contains("1"));
        Assert.assertTrue(keySet.contains("2"));
        Assert.assertTrue(keySet.contains("3"));
        Assert.assertEquals("uno", map.get("1"));
    }
    
    /** test */
    @Test
    public final void testOk2() throws Exception {
        StringToMapFactoryBean bean = new StringToMapFactoryBean(
                "  1 = uno,,2=dos ,3=tres ,,", "=", ",");
        Map<String, String> map = bean.getObject();
        
        Assert.assertEquals(3, map.size());
        Set<String> keySet = map.keySet();
        Assert.assertTrue(keySet.contains("1"));
        Assert.assertTrue(keySet.contains("2"));
        Assert.assertTrue(keySet.contains("3"));
        Assert.assertEquals("uno", map.get("1"));
        Assert.assertEquals("dos", map.get("2"));
        Assert.assertEquals("tres", map.get("3"));
    }    
    
    /** test */
    @Test(expected = IllegalArgumentException.class)
    public final void testBad1() throws Exception {
        StringToMapFactoryBean bean = new StringToMapFactoryBean(
                "  1 =,,2=dos ,3=tres ,,", "=", ",");
        bean.getObject();
    } 
    
    /** test */
    @Test(expected = IllegalArgumentException.class)
    public final void testBad2() throws Exception {
        StringToMapFactoryBean bean = new StringToMapFactoryBean(
                "  1,,2=dos ,3=tres ,,", "=", ",");
        bean.getObject();
    }     

}
