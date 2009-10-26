/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.rest.impl;

import java.io.IOException;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import ar.com.zauber.commons.dao.exception.NoSuchEntityException;
import ar.com.zauber.commons.web.rest.ContentProvider;


/**
 * Test Driver for {@link JREContentProvider}.
 * 
 * @author Matías G. Tito
 * @since Oct 23, 2009
 */
public class JREContentProviderTestDriver {

    private final ContentProvider cp;

    /**
     * Creates the JREContentProvider.
     * @throws NoSuchAlgorithmException 
     *
     */
    public JREContentProviderTestDriver() throws NoSuchAlgorithmException {
        cp = new MyJREContentProvider();
    }
    
    /** test 
     * @throws IOException */
    @Test
    public final void testFound() throws URISyntaxException, IOException  {
        final Reader r = cp.getContent(new URI(
        "https://labs.zauber.com.ar/scrummage/2.2.4s/0.1/product/juantest"));
        Assert.assertNotNull(r);
        System.out.println(IOUtils.toString(r));
    }
    
    /** test */
    @Test
    public final void testNotFound() throws URISyntaxException {
        final URI url = new URI("http://www.google.com.ar/asdasdasd");
        try {
            cp.getContent(url);
            Assert.fail();
        } catch(final NoSuchEntityException e) {
            // ok
        }
    }
}

/** implementación que prueba configurar algunos headers */
class MyJREContentProvider extends JREContentProvider {
    
    /** @see JREContentProvider#modifyURLConnection(URI, HttpURLConnection) */
    @Override
    protected void modifyURLConnection(final URI url, final HttpURLConnection huc) {
        huc.addRequestProperty("Authorization", 
                "Basic " + new String(new Base64().encode(
                        "juantest:juantest".getBytes())));
        huc.addRequestProperty("Accept", "text/xml");
    }
}
