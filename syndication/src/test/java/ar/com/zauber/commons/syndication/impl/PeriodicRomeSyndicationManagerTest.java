/*
 * Copyright (c) 2006 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.syndication.impl;

import java.io.IOException;
import java.io.Reader;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;
import ar.com.zauber.commons.syndication.FeedReader;
import ar.com.zauber.commons.syndication.SyndicationManager;


/**
 * Unit test for {@link PeriodicRomeSyndicationManager}
 * 
 * @author Juan F. Codagnone
 * @since Jul 2, 2006
 */
public class PeriodicRomeSyndicationManagerTest extends TestCase {
    /** lee el xml de testeo */
    private final FeedReader classpathFeedReader = 
        new ClasspathFeedReader("feeds/atom.xml");
    
    /** testea que funcione la carga del feed */
    public final void testLoad() {
        final SyndicationManager syn = new PeriodicRomeSyndicationManager(
                10000, classpathFeedReader);
        
        assertEquals(13, syn.getEntries().size());
        
        assertEquals("La Red....", syn.getEntries().get(0).getTitle());
        assertEquals("http://blog.flof.com.ar/2006/06/la-red.html", 
                syn.getEntries().get(0).getLink());
    }
    
    /** testea que se llame periodicamente la funcion de carga de feed */
    public final void testPeriodic() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(3);
        
        new PeriodicRomeSyndicationManager(
                1, new FeedReader() {
                    public Reader getReader() throws IOException {
                        latch.countDown();
                        return classpathFeedReader.getReader();
                    }
                });
        
        latch.await(10, TimeUnit.SECONDS);
        assertEquals(0, latch.getCount());
    }
}
