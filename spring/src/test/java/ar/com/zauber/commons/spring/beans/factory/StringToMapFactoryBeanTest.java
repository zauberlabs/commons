/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
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
