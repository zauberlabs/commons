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

import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * Abstract Tag with Spring Support
 * 
 * @author Mariano Cortesi
 * @since May 7, 2010
 */
public abstract class AbstractSpringTag extends TagSupport {

    /** <code>serialVersionUID</code> */
    private static final long serialVersionUID = 1526552888077046751L;

    /** @return Request {@link ApplicationContext} */
    protected final WebApplicationContext getApplicationContext() {
        if(pageContext == null) {
            throw new IllegalStateException("pageContext is null!");
        }
        return RequestContextUtils.getWebApplicationContext(
                pageContext.getRequest());
    }

}