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
package ar.com.zauber.commons.web.uri.assets;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.Validate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockPageContext;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Test diferent configurations 
 * 
 * @author Juan F. Codagnone
 * @since Jul 27, 2010
 */
@RunWith(Parameterized.class)
public class AssetsTest {
    /** parametros de junit */
    @Parameters
    public static List<Object[]> data() {
        return Arrays.asList(PARAMS);
    }
    private static final Object[][] PARAMS = {
        new String[]{"empty"},
        new String[]{"standalone"},
        new String[]{"absolute"},
    };
    private final String profile;

    /** Creates the AssetsTest. */
    public AssetsTest(final String profile) {
        Validate.notNull(profile, "null profile");
        this.profile = profile;
    }
    
    /** test */
    @Test
    public final void testAssetsUriFactory() throws Exception {
        // Creado el servlet
        final XmlWebApplicationContext ctx = new XmlWebApplicationContext();
        ctx.setConfigLocations(new String[] {
                "classpath:ar/com/zauber/commons/web/uri/assets/"
                 + profile + "-assets-spring.xml",
        });
        ctx.setServletContext(new MockServletContext());
        ctx.refresh();
        for(int i = 0; i <  2; i++) {
            // Armo el request
            final PageContext pageCtx = createPageContext(ctx);
            
            // <assets:javascript key="/_js/lib/jquery-1.4.2.js"/>
            JavascriptTag js = new JavascriptTag();
            js.setPageContext(pageCtx);
            js.setKey("/_js/lib/jquery-1.4.2.js");
            js.doStartTag();
            js.doEndTag();
            
            // <assets:javascript  key="/_js/model/foo.js"/>
            js = new JavascriptTag();
            js.setPageContext(pageCtx);
            js.setKey("/_js/model/foo.js");
            js.setCharset("utf-8");
            js.doStartTag();
            js.doEndTag();
            
            // <assets:css key="/stylesheet.css"/>
            final CssTag t = new CssTag();
            t.setPageContext(pageCtx);
            t.setCharset("utf-8");
            t.setKey("/stylesheet.css");
            t.doStartTag();
            t.doEndTag();
            
            // <assets:css key="/stylesheet.css"/>
            final ImageTag image = new ImageTag();
            image.setPageContext(pageCtx);
            image.setKey("/1.gif");
            image.doStartTag();
            image.doEndTag();
            
            // <assets:print/>
            final PrintTag printTag = new PrintTag();
            printTag.setPageContext(pageCtx);
            printTag.doStartTag();
            printTag.doEndTag();
            
            // Validaciones
            final MockHttpServletResponse response = (MockHttpServletResponse) 
                pageCtx.getResponse();
            final String result = new String(response.getContentAsByteArray(), 
                    "utf8");
            final InputStream is = getClass().getResourceAsStream(profile 
                    + "-expected.txt");
            try {
                Assert.assertEquals(IOUtils.toString(is), result);
            } finally {
                is.close();
            }
        }
    }

    /** create the {@link PageContext} to test */
    private MockPageContext createPageContext(final XmlWebApplicationContext ctx) {
        final HttpServletRequest request = createRequest(ctx);
        final MockPageContext pageCtx = new MockPageContext(ctx.getServletContext(), 
                request);
        pageCtx.setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, 
                ctx);
        return pageCtx;
    }

    /** create request */
    private MockHttpServletRequest createRequest(
            final XmlWebApplicationContext ctx) {
        final MockHttpServletRequest request = new MockHttpServletRequest("GET", 
                "/foo/organizations/zauber/projects");
        request.setContextPath("/foo");
        // esto se requiere para que funcione el buscar el ctx dado un request 
        request.setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, 
                ctx);
        final ServletRequestAttributes attributes = new ServletRequestAttributes(
                request);
        request.setAttribute(RequestContextListener.class.getName() 
                + ".REQUEST_ATTRIBUTES", attributes);
        LocaleContextHolder.setLocale(request.getLocale());
        RequestContextHolder.setRequestAttributes(attributes);
        return request;
    }
}
