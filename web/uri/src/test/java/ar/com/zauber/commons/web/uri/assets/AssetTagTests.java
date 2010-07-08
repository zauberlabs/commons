/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.assets;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import ar.com.zauber.commons.web.uri.AbstractWebUriMockitoTest;
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
public abstract class AssetTagTests extends AbstractWebUriMockitoTest {

    /** Buffer donde se va guardando el contenido */
    protected StringBuilder sb;
    
    /** test de contexto de test */
    @Test
    public final void testCtx() throws Exception {
        Assert.notNull(applicationContext.getBean(SpringBeans.REPOSITORY_KEY, 
                AssetRepository.class));
        WebApplicationContext apContext = 
            RequestContextUtils.getWebApplicationContext(getPc().getRequest());
        Assert.notNull(apContext.getBean(SpringBeans.REPOSITORY_KEY, 
                AssetRepository.class));
    }
    
    @Before
    public final void init() {
        JspWriter jspToString = createJspWriter();
        setSb(new StringBuilder());
        Mockito.when(getPc().getOut()).thenReturn(jspToString);
    }

    /** @return mocked {@link JspWriter} */
    public final JspWriter createJspWriter() {
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

    public final void setSb(final StringBuilder sb) {
        this.sb = sb;
    }

    public final StringBuilder getSb() {
        return sb;
    }
    
    

}
