/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.rest.impl;

import java.net.URI;
import java.net.URISyntaxException;
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
    
    /** Creates the FixedContentProviderTest. */
    public FixedContentProviderTest() throws URISyntaxException {
        final Map<URI, String> map = new HashMap<URI, String>();
        map.put(new URI("http://foo"), "ar/com/zauber/commons/web/rest/impl/foo.txt");
        map.put(new URI("http://bar"), "bar.txt");
        contentProvider = new FixedContentProvider(map);
    }
    
    @Test
    public void testFound() throws URISyntaxException  {
        Assert.assertNotNull(contentProvider.getContent(new URI("http://foo")));
    }
    
    @Test
    public void testNotFound() throws URISyntaxException {
        final URI url = new URI("http://bar");
        try {
            contentProvider.getContent(url);
            Assert.fail();
        } catch(final NoSuchEntityException e) {
            // ok
        }
    }
}
