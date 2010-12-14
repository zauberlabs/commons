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
package ar.com.zauber.commons.message.impl.message;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import ar.com.zauber.commons.dao.Resource;
import ar.com.zauber.commons.message.message.templates.AbstractMessagePartTemplate;
import ar.com.zauber.commons.message.message.templates.AbstractTemplate;
import ar.com.zauber.commons.message.message.templates.PartTemplate;

/**
 * {@link PartTemplate} that uses Velocity
 * 
 * @author Christian Nardi
 * @since Dec 28, 2009
 */
public class VelocityMessagePartTemplate 
    extends AbstractMessagePartTemplate implements PartTemplate {

    /** Creates the VelocityMessageTemplate. */
    public VelocityMessagePartTemplate(final String contentType, 
            final Resource resource, final String charset) {
        super(resource, contentType, charset);
    }

    /** @see AbstractTemplate#renderString(java.lang.String, java.util.Map) */
    @Override
    protected final String renderString(final String message, 
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
}
