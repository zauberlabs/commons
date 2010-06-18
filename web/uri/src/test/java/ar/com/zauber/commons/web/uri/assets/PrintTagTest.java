/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.assets;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


/**
 * Test de PrintTag
 * @author Mariano Semelman
 * @since Jun 17, 2010
 */
public class PrintTagTest extends AssetTagTest {

    @Test
    public final void testPrintTag() throws Exception {
        new PrintTag();
    }
    
    @Test
    public final void testDoStartTag() throws Exception {
        PrintTag p = new PrintTag();
        p.setPageContext(pc);
        p.doStartTag();
    }
    
    @Test
    public final void test() throws Exception {
        PrintTag p = new PrintTag();
        CssTag css = new CssTag();
        css.setPageContext(pc);
        css.setKey("Un key");
        css.setMedia("un media");
        css.doStartTag();
        p.setPageContext(pc);
        p.doStartTag();
        Assert.assertEquals("<link rel=\"stylesheet\" type=\"text/css\""
                + " href=\"Un key\" media=\"un media\" />", sb.toString());
    }

}
