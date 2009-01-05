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
package ar.com.zauber.commons.message.impl.message;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;

import ar.com.zauber.commons.message.MessageFactory;


/**
 * A Message factory that renders the messages using velocity
 * 
 * @author Juan F. Codagnone
 * @since Oct 6, 2005
 */
public class VelocityMessageResolver extends AbstractMessageFactory {
    
    
    /** 
     * Creates the VelocityMessageResolver.
     *  
     * @throws Exception on error
     */
    public VelocityMessageResolver() throws Exception {
        Velocity.init();
    }
    
    /** @see MessageFactory#renderString(String, Map) */
    public final String renderString(final String message,
            final Map<String, Object> model) {
        final StringWriter writer = new StringWriter();
        final VelocityContext context = new VelocityContext(model);
        try {
            Velocity.evaluate(context, writer, "message", new StringReader(message));
        } catch(final Exception e) {
            throw new RuntimeException(e);
        }
        
        return writer.toString();
    }

    /** @see MessageFactory#renderString(String, Object) */
    public final String renderString(final String message, final Object[] params) {
        return message;
    }
}
