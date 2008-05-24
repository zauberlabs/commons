/*
 * Copyright (c) 2005-2008 Zauber S.A. <http://www.zauber.com.ar/>
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
package ar.com.zauber.commons.message.impl.message;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

import ar.com.zauber.commons.message.MessageFactory;


/**
 * A Message factory that renders the messages using velocity
 * 
 * @author Juan F. Codagnone
 * @since Oct 6, 2005
 */
public class VelocityMessageResolver extends AbstractMessageFactory {
    /** velocity engine */
    private final VelocityEngine ve = new VelocityEngine();
    
    /** 
     * Creates the VelocityMessageResolver.
     *  
     * @throws Exception on error
     */
    public VelocityMessageResolver() throws Exception {
        ve.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM, this);
        ve.init();
    }
    
    /** @see MessageFactory#renderString(java.lang.String, java.util.Map) */
    public final String renderString(final String message,
            final Map<String, Object> model) {
        final StringWriter writer = new StringWriter();
        final VelocityContext context = new VelocityContext(model);
        try {
            ve.evaluate(context, writer, "message", new StringReader(message));
        } catch(final Exception e) {
            throw new RuntimeException(e);
        }
        
        return writer.toString();
    }

    public String renderString(String message, Object[] params) {
        // TODO Try to do something.
        return message;
    }
}
