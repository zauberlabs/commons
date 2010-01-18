/**
 * Copyright (c) 2005-2010 Zauber S.A. <http://www.zauber.com.ar/>
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
