/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.spring.beans.factory;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Tests for {@link StringToListFactoryBean}
 * 
 * 
 * @author Francisco J. González Costanzó
 * @since May 3, 2010
 */
public class StringToListFactoryBeanTest {

    /** test */
    @Test
    public final void testOk() throws Exception {
        StringToListFactoryBean bean = new StringToListFactoryBean(
                "1,2,3", ",");
        List<String> list = bean.getObject();
        
        Assert.assertEquals(3, list.size());
        Assert.assertTrue(list.contains("1"));
        Assert.assertTrue(list.contains("2"));
        Assert.assertTrue(list.contains("3"));
        Assert.assertEquals("1", list.get(0));
    }
    
    /** test */
    @Test
    public final void testOk2() throws Exception {
        StringToListFactoryBean bean = new StringToListFactoryBean(
                "1,2,,3", ",");
        List<String> list = bean.getObject();
        
        Assert.assertEquals(3, list.size());
        Assert.assertTrue(list.contains("1"));
        Assert.assertTrue(list.contains("2"));
        Assert.assertTrue(list.contains("3"));
        Assert.assertEquals("1", list.get(0));
    }    
    
}
