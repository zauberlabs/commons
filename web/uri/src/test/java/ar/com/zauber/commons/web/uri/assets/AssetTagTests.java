/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.assets;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.RequestContextUtils;

import ar.com.zauber.commons.web.uri.SpringBeans;
import ar.com.zauber.commons.web.uri.model.AssetRepository;

/**
 * Test Generico de Tags de assets
 * 
 * @author Mariano Semelman
 * @since Jun 17, 2010
 */
@ContextConfiguration(locations = {
        "/ar/com/zauber/commons/web/uri/config/assets-commons-web-uri-spring.xml"
        })
public class AssetTagTests extends AbstractJUnit4SpringContextTests {

    

    /** contexto de la pagina mockeado para testear tags */
    private PageContext pc;
    /** Buffer donde se va guardando el contenido */
    private StringBuilder sb;
    
    /** test de contexto de test */
    @Test
    public final void testCtx() throws Exception {
        Assert.notNull(this.applicationContext.getBean(SpringBeans.REPOSITORY_KEY, 
                AssetRepository.class));
        WebApplicationContext apContext = 
            RequestContextUtils.getWebApplicationContext(this.pc.getRequest());
        Assert.notNull(apContext.getBean(SpringBeans.REPOSITORY_KEY, 
                AssetRepository.class));
    }
    
    
    /** crea el pagecontext*/
    @Before
    public final void createPageContext() {
        ServletRequest sReq = createServletRequest();
        setPc(Mockito.mock(PageContext.class, Mockito.RETURNS_MOCKS));
        setSb(new StringBuilder());
        Mockito.when(this.pc.getRequest()).thenReturn(sReq);
        JspWriter jspToString = createJspWriter();
        Mockito.when(this.pc.getOut()).thenReturn(jspToString);
    }

    /** @return mocked {@link JspWriter} */
    private JspWriter createJspWriter() {
        JspWriter jspToString = Mockito.mock(JspWriter.class);
        try {
            Mockito.doAnswer(new Answer<Object>() {

                /** @see Answer#answer(InvocationOnMock)*/
                public Object answer(final InvocationOnMock invocation) {
                    getSb().append((String)invocation.getArguments()[0]);
                    return null;
                }
            }).when(jspToString).write(Mockito.anyString());
        } catch (IOException e) {
            // mockito
        }
        return jspToString;
    }

    /** @return a mocked {@link ServletRequest}*/
    private ServletRequest createServletRequest() {
        ServletRequest sReq = Mockito.mock(ServletRequest.class);
        GetBeanMocker answer = new GetBeanMocker(this);
        WebApplicationContext wc = Mockito.mock(WebApplicationContext.class, 
                answer);
        Mockito.when(sReq.getAttribute(
                DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE))
                .thenReturn(wc);
        return sReq;
    }

    public final void setPc(final PageContext pc) {
        this.pc = pc;
    }

    public final PageContext getPc() {
        return this.pc;
    }

    public final void setSb(final StringBuilder sb) {
        this.sb = sb;
    }

    public final StringBuilder getSb() {
        return this.sb;
    }
    
    /**
     * Crea Un Mocker de application content.
     * @author Mariano Semelman
     * @since Jun 24, 2010
     */
    private final class GetBeanMocker implements Answer<Object> {
        private ApplicationContext aC;

        /** */
        public GetBeanMocker(final AssetTagTests assetTagTests) {
            this.aC = assetTagTests.applicationContext;
        }

        /** @see Answer#answer(InvocationOnMock) */
        @SuppressWarnings("unchecked")
        public Object answer(final InvocationOnMock invocation) {
            if(invocation.getMethod().getName().equals("getBean")
                    && invocation.getArguments().length == 2) {
                Class<Object> c = (Class<Object>) invocation.getArguments()[1];
                return this.aC.getBean((String) invocation.getArguments()[0], c);
            }
            return null;
        }
    }

}
