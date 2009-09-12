/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.rest.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import ar.com.zauber.commons.dao.exception.NoSuchEntityException;
import ar.com.zauber.commons.web.rest.ContentProvider;


/**
 * Tests {@link FixedContentProvider}.
 * 
 * @author Juan F. Codagnone
 * @since Sep 12, 2009
 */
public class FixedContentProviderTest {
    private final ContentProvider contentProvider;
    
    /** Creates the FixedContentProviderTest.  */
    public FixedContentProviderTest() throws MalformedURLException {
        final Map<URL, String> map = new HashMap<URL, String>();
        map.put(new URL("http://foo"), "ar/com/zauber/commons/web/rest/impl/foo.txt");
        map.put(new URL("http://bar"), "bar.txt");
        contentProvider = new FixedContentProvider(map);
    }
    
    @Test
    public void testFound() throws NoSuchEntityException, MalformedURLException {
        Assert.assertNotNull(contentProvider.getContent(new URL("http://foo")));
    }
    
    @Test
    public void testNotFound() throws MalformedURLException {
        final URL url = new URL("http://bar");
        try {
            contentProvider.getContent(url);
            Assert.fail();
        } catch(final NoSuchEntityException e) {
            // ok
        }
    }
}
