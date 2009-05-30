/**
 * Copyright (c) 2005-2009 Zauber S.A. <http://www.zauber.com.ar/>
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
package ar.com.zauber.commons.spring.web.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.View;

/**
 * Vista que loguea cualquier excepcion al renderear.
 * 
 * @author Pablo Grigolatto
 * @since May 19, 2009
 */
public class WrappedView implements View {
    private final View target;
    /** logger */
    public static final Log LOGGER = LogFactory
            .getLog(WrappedView.class);
    
    /** Creates the WrapperView. */
    public WrappedView(final View target) {
        Validate.notNull(target);
        this.target = target;
    }
    
    /** @see View#getContentType() */
    public String getContentType() {
        return target.getContentType();
    }

    /** @see View#render(Map, HttpServletRequest, HttpServletResponse) */
    public void render(final Map model, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {

        try {
            target.render(model, request, response);
        } catch(final Throwable e) {
            LOGGER.error("Error processing view", e);
            throw new UnhandledException(e);
        }
    }
}