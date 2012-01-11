/**
 * Copyright (c) 2005-2012 Zauber S.A. <http://www.zaubersoftware.com/>
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
package ar.com.zauber.commons.web.utils;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link HttpHeadersUtils}.
 * 
 * 
 * @author Pablo Grigolatto
 * @since May 14, 2009
 */
public class HttpHeadersUtilsTest {
    private List<String> acceptedContentTypes = new ArrayList<String>();
    
    @Before
    public final void before() {
        acceptedContentTypes = new ArrayList<String>();
    }
    

    
    public final void parser() {
        final String acceptHeader = "text/plain; q=0.5"
            + ", text/html, text/x-dvi; q=0.8, text/x-c";
        
        for (final ContentTypeContainer container 
                : HttpHeadersUtils.getPrioritizedList(acceptHeader)) {
            
            if(container.getMediaRange().equals("*/*")) {
                Assert.assertTrue(container.matches("hol/aaa"));
            }
            if(container.getMediaRange().equals("text/*")) {
                Assert.assertTrue(container.matches("text/aaaaa"));
                Assert.assertFalse(container.matches("aaaa/bbbb"));
            }
            
            if(container.getMediaRange().equals("text/html")) {
                Assert.assertTrue(container.matches("text/html"));
                Assert.assertFalse(container.matches("text/xml"));
            }
        }
        
        
    }

    @Test
    public final void acceptAudio() {
        final String acceptHeader = "audio/*; q=0.2, audio/basic";
        /*
        RFC2616: SHOULD be interpreted as "I prefer audio/basic, but send me any
        audio type if it is the best available after an 80% mark-down in quality
        */
        
        acceptedContentTypes.add("audio/*");
        acceptedContentTypes.add("audio/basic");

        Assert.assertEquals("audio/basic", HttpHeadersUtils.getContentType(
                acceptHeader, acceptedContentTypes));
    }

    /** test */
    @Test
    public final void acceptNothing() {
        final String acceptHeader = "audio/*; q=0.2, audio/basic";

        Assert.assertNull(HttpHeadersUtils.getContentType(acceptHeader,
                acceptedContentTypes));
    }

    /** test */
    @Test
    public final void acceptPreference() {
        /*
        RFC2616: Verbally, this would be interpreted as
        "text/html and text/x-c are the preferred media types,
        but if they do not exist, then send the text/x-dvi entity,
        and if that does not exist, send the text/plain entity."
        */
        final String acceptHeader = "text/plain; q=0.5"
            + ", text/html, text/x-dvi; q=0.8, text/x-c";

        // no acepto ningun type
        Assert.assertNull(HttpHeadersUtils.getContentType(acceptHeader,
                acceptedContentTypes));

        // acepto solo plain text
        acceptedContentTypes.add("text/plain");
        Assert.assertEquals("text/plain", HttpHeadersUtils.getContentType(
                acceptHeader, acceptedContentTypes));

        // acepto tambien html, y lo selecciono por tener mayor q
        acceptedContentTypes.add("text/html");
        Assert.assertEquals("text/html", HttpHeadersUtils.getContentType(
                acceptHeader, acceptedContentTypes));

        // acepto x-dvi pero no modifica por tener menor q
        acceptedContentTypes.add("text/x-dvi");
        Assert.assertEquals("text/html", HttpHeadersUtils.getContentType(
                acceptHeader, acceptedContentTypes));
    }

    /** test */
    @Test
    public final void acceptLevelPreference() {
        final String acceptHeader = "text/*, text/html, text/html;level=1, */*";
        /*
         * RFC2616: have the following precedence: 1) text/html;level=1 2)
         * text/html 3) text/* 4) * /*
         */

        final List<ContentTypeContainer> qualityList = 
            HttpHeadersUtils.getPrioritizedList(acceptHeader);
        Assert.assertEquals(4, qualityList.size());
        Assert.assertEquals("text/*", qualityList.get(0).getMediaRange());
        Assert.assertEquals("text/html", qualityList.get(1).getMediaRange());
        Assert.assertEquals("text/html", qualityList.get(2).getMediaRange());
        Assert.assertEquals("*/*", qualityList.get(3).getMediaRange());

        acceptedContentTypes.add("text/html");
        acceptedContentTypes.add("text/*");
        acceptedContentTypes.add("text/html;level=1");
        acceptedContentTypes.add("*/*");
        Assert.assertEquals("text/html", HttpHeadersUtils
                .getContentType(acceptHeader, acceptedContentTypes));
    }

    /** test */
    @Test
    public final void firefoxAccept() {
        final String acceptHeader = "text/html"
            + ",application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
        
        //deberia ser el cuarto
        Assert.assertEquals("application/xml", HttpHeadersUtils
                .getPrioritizedList(acceptHeader).get(2).getMediaRange());
    }

}
