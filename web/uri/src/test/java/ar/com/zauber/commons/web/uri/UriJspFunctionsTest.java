/**
 * Copyright (c) 2005-2015 Zauber S.A. <http://flowics.com/>
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

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
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
    
    
    private static final String OTHER_URI_FACTORY = "myUriFactory";
    
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
    @Ignore(value = "ahora que no se envia el request...")
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
    
    /** Test que prueba si el pedido es en el mismo segmento ..*/
    @Test
    @Ignore(value = "ahora que no se envia el request...")
    public final void buildUriWithContext() {
        setException(true);
        PageContext ctx = getPc();
        HttpServletRequest req = getReq();
        Mockito.when(req.getRequestURI()).thenReturn("/app/bin/nada");
        Mockito.when(req.getContextPath()).thenReturn("/app/bin");
        Assert.assertEquals("./abc", 
                UriJspFunctions.buildVarArgs(ctx , "abc", ctx.getRequest()));
        
        Assert.assertEquals("./abc", 
                UriJspFunctions.buildVarArgs(ctx , "/abc", ctx.getRequest()));
        
        Mockito.when(req.getRequestURI()).thenReturn("/app/bin/nada/par");
        Mockito.when(req.getContextPath()).thenReturn("/app/bin");
        
        Assert.assertEquals("../par/abc", 
                UriJspFunctions.buildVarArgs(ctx , "/par/abc", ctx.getRequest()));
    }
    
    /** Test de ..*/
    @Test
    public final void buildUriContextWithBean() throws Exception {
        String uriKey = "abc";
        Assert.assertEquals(uriKey, UriJspFunctions.buildVarArgs(getPc(), uriKey, OTHER_URI_FACTORY));
    }
    
    
}
