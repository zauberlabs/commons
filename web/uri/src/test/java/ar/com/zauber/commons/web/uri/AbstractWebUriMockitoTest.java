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
package ar.com.zauber.commons.web.uri;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import org.junit.Before;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Clase abstracta que mockea pagecontext y request con mockito.
 * 
 * @author Mariano Semelman
 * @since Jul 8, 2010
 */
public abstract class AbstractWebUriMockitoTest 
    extends AbstractJUnit4SpringContextTests {

    /** contexto de la pagina mockeado para testear tags */
    private PageContext pc;
    private HttpServletRequest req;
    private boolean exception;
    
    /** crea el pagecontext*/
    @Before
    public final void createPageContext() {
        exception = false;
        createServletRequest();
        setPc(Mockito.mock(PageContext.class, Mockito.RETURNS_MOCKS));
        Mockito.when(pc.getRequest()).thenReturn(getReq());
    }

    /** creates a mocked {@link ServletRequest}*/
    private void createServletRequest() {
        setReq(Mockito.mock(HttpServletRequest.class));
        GetBeanMocker answer = new GetBeanMocker();
        WebApplicationContext wc = 
            Mockito.mock(WebApplicationContext.class, answer);
        Mockito.when(getReq()
                .getAttribute(
                DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE))
                .thenReturn(wc);
    }

    public final void setPc(final PageContext pc) {
        this.pc = pc;
    }

    public final PageContext getPc() {
        return pc;
    }
    
    
    public final void setReq(final HttpServletRequest sReq) {
        req = sReq;
    }

    public final HttpServletRequest getReq() {
        return req;
    }


    public final void setException(final boolean exception) {
        this.exception = exception;
    }

    public final boolean isException() {
        return exception;
    }


    /**
     * Crea Un Mocker de application content.
     * @author Mariano Semelman
     * @since Jun 24, 2010
     */
    public final class GetBeanMocker implements Answer<Object> {
        /** @see Answer#answer(InvocationOnMock) */
        @SuppressWarnings("unchecked")
        public Object answer(final InvocationOnMock invocation) {
            if(invocation.getMethod().getName().equals("getBean")
                    && invocation.getArguments().length == 2) {
                if(isException()) {
                    throw new NoSuchBeanDefinitionException(
                            invocation.getArguments()[0].toString());
                } else {
                    Class<Object> c = (Class<Object>) invocation.getArguments()[1];
                    return applicationContext.getBean(
                            (String) invocation.getArguments()[0], c);
                }
            }
            return null;
        }
    }
}
