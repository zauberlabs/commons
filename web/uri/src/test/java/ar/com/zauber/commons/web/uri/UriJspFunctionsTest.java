/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.uri;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;


/**
 * Test de {@link UriJspFunctions} con contexto configurado.
 * 
 * @author Mariano Semelman
 * @since Jul 8, 2010
 */
@ContextConfiguration(locations = {
        "/ar/com/zauber/commons/web/uri/config/"
        + "jsp-functions-commons-web-uri-spring.xml"
        })
public class UriJspFunctionsTest extends AbstractWebUriMockitoTest {
    
    
    
    /** recargar jspFunctions en cada test*/
    @Before
    public final void resetJspFunctions() throws Exception {
        Class<UriJspFunctions> a = UriJspFunctions.class;
        Field[] fs = a.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field field = fs[i];
            if(field.getName().contains("initialized")) {
                field.setAccessible(true);
                ((AtomicBoolean)field.get(null)).set(false);
                field.setAccessible(false);
            }
        }
        
    }
    
    /** Test de UriJsp sin cotexto*/
    @Test
    public final void buildUriDefault() throws Exception {
        setException(true);
        PageContext ctx = getPc();
        HttpServletRequest req = getReq();
        Mockito.when(req.getRequestURI()).thenReturn("/123/abc/asd/asd");
        Mockito.when(req.getContextPath()).thenReturn(StringUtils.EMPTY);
        Assert.assertEquals("../../../abc", 
                UriJspFunctions.buildVarArgs(ctx , "abc", ctx.getRequest()));
    }
    
    /** Test de ..*/
    @Test
    public final void buildUriContext() throws Exception {
        String uriKey = "abc";
        Assert.assertEquals(uriKey, UriJspFunctions.buildVarArgs(getPc(), uriKey));
    }
    
}
