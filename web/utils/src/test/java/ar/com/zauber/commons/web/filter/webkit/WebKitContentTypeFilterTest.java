/**
 * Copyright (c) 2005-2011 Zauber S.A. <http://www.zaubersoftware.com/>
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
package ar.com.zauber.commons.web.filter.webkit;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Testeo de WebkitContentTypeFilter
 * 
 * @author Mariano Semelman
 * @since Jun 11, 2010
 */
public class WebKitContentTypeFilterTest {
    
    
    /** <code>ACCEPT</code> */
    private static final String HEADER_ACCEPT = "Accept";


    /** */
    @Test
    public final void setUp() throws Exception {
        new WebKitContentTypeFilter();
    }
    
    /** */
    @Test
    public final void testTrueFilter() throws Exception {
        Filter a = new WebKitContentTypeFilter();
        FilterChain chain = Mockito.mock(FilterChain.class);
        DoFilterAnswer answer = new DoFilterAnswer();
        Mockito.doAnswer(answer).when(chain).doFilter(
                Mockito.any(ServletRequest.class),
                Mockito.any(ServletResponse.class));
        HttpServletRequest request = createWebKitRequest(true);
        ServletResponse response = Mockito.mock(HttpServletResponse.class);
        a.doFilter(request, response, chain);
        
        Assert.assertTrue(!answer.req.getHeader(HEADER_ACCEPT).isEmpty());
        Assert.assertEquals("text/html,application/xhtml+xml,"
                + "application/xml;q=0.9,*/*;q=0.8", 
                answer.req.getHeader(HEADER_ACCEPT));
    }
    
    @Test
    public void testFalse() throws Exception {
        Filter a = new WebKitContentTypeFilter();
        FilterChain chain = Mockito.mock(FilterChain.class);
        DoFilterAnswer answer = new DoFilterAnswer();
        Mockito.doAnswer(answer).when(chain).doFilter(
                Mockito.any(ServletRequest.class),
                Mockito.any(ServletResponse.class));
        ServletResponse response = Mockito.mock(HttpServletResponse.class);
        HttpServletRequest request = createWebKitRequest(false);
        a.doFilter(request, response, chain);
        Assert.assertEquals(request.getHeader(HEADER_ACCEPT), 
                answer.req.getHeader(HEADER_ACCEPT));
    }
    
    @Test
    public void testNone() throws Exception {
        Filter a = new WebKitContentTypeFilter();
        FilterChain chain = Mockito.mock(FilterChain.class);
        DoFilterAnswer answer = new DoFilterAnswer();
        Mockito.doAnswer(answer).when(chain).doFilter(
                Mockito.any(ServletRequest.class),
                Mockito.any(ServletResponse.class));
        ServletResponse response = Mockito.mock(HttpServletResponse.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getHeader("User-Agent"))
            .thenReturn("Mozilla/5.0 (X11; U; Linux x86_64; en-US; rv:1.9.2.3)"
                    + " Gecko/20100423 Ubuntu/10.04 (lucid) Firefox/3.6.3");
        String accept = "accept";
        Mockito.when(request.getHeader(HEADER_ACCEPT)).thenReturn(accept);
        a.doFilter(request, response, chain);
        Assert.assertEquals(accept, answer.req.getHeader(HEADER_ACCEPT));
    }

    /** @param acceptsHtml si tiene text/html entre sus contenido preferidos
     * @return un request de webkit.*/
    private HttpServletRequest createWebKitRequest(final boolean acceptsHtml) {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
      //CHECKSTYLE:ALL:OFF
        Mockito.when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0 (X11; U; Linux x86_64; en-US) AppleWebKit/533.4 (KHTML, like Gecko) Chrome/5.0.375.70 Safari/533.4");
        //CHECKSTYLE:ALL:ON
        String value;
        if(acceptsHtml) {
            //CHECKSTYLE:ALL:OFF
            value = "application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5";
            //CHECKSTYLE:ALL:ON
        } else {
            value = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
        }
        Mockito.when(request.getHeader(HEADER_ACCEPT)).thenReturn(value);
        return request;
    }

    
    /**
     * Answer para mockear la llamada a doFilter
     * @author Mariano Semelman
     * @since Jun 11, 2010
     */
    private final class DoFilterAnswer implements Answer<Object> {
        private HttpServletRequest req;
        private HttpServletResponse resp;

        /** @see Answer#answer(InvocationOnMock) */
        public Object answer(final InvocationOnMock invocation) 
            throws Throwable {
            req = (HttpServletRequest)invocation.getArguments()[0];
            resp = (HttpServletResponse)invocation.getArguments()[1];
            return null;
        }
    }
    
}
