/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.assets;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.junit.Before;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import ar.com.zauber.commons.web.uri.model.AssetRepository;

/**
 * Test Generico de Tags de assets
 * 
 * @author Mariano Semelman
 * @since Jun 17, 2010
 */
@ContextConfiguration(locations= {
        "/ar/com/zauber/commons/web/uri/config/assets-commons-web-uri-spring.xml",
        })
public class AssetTagTest extends AbstractJUnit4SpringContextTests {

    protected PageContext pc;
    protected StringBuilder sb;
    protected AssetRepository repo;
    
    
    /** crea el pagecontext*/
    @Before
    public final void createPageContext() {
        repo = applicationContext.getBean(AssetRepository.class);
        ServletRequest sReq = createServletRequest();
        pc = Mockito.mock(PageContext.class, Mockito.RETURNS_MOCKS);
        sb = new StringBuilder();
        Mockito.when(pc.getRequest()).thenReturn(sReq);
        JspWriter jspToString = createJspWriter();
        Mockito.when(pc.getOut()).thenReturn(jspToString );
    }

    /** @return mocked {@link JspWriter} */
    private final JspWriter createJspWriter() {
        JspWriter jspToString = Mockito.mock(JspWriter.class);
        try {
            Mockito.doAnswer(new Answer() {

                /** @see Answer#answer(InvocationOnMock)*/
                public Object answer(InvocationOnMock invocation) 
                    throws Throwable {
                    sb.append((String)invocation.getArguments()[0]);
                    return null;
                }
            }).when(jspToString).write(Mockito.anyString());
        } catch (IOException e) {
            // mockito
        }
        return jspToString;
    }

    /** @return a mocked {@link ServletRequest}*/
    private final ServletRequest createServletRequest() {
        ServletRequest sReq = Mockito.mock(ServletRequest.class);
        WebApplicationContext wc = Mockito.mock(WebApplicationContext.class);
        Mockito.when(wc.getBean(Mockito.anyString(), Mockito.any(Class.class)))
            .thenAnswer(new Answer() {
                /** @see Answer#answer(InvocationOnMock) */
                public Object answer(InvocationOnMock invocation) throws Throwable {
                    Class c = (Class) invocation.getArguments()[1];
                    return applicationContext.getBean(
                            (String) invocation.getArguments()[0], c);
                }
            });
        Mockito.when(sReq.getAttribute(
                DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE))
                .thenReturn(wc);
        return sReq;
    }
}
