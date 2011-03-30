/**
 * Copyright (c) 2005-2011 Zauber S.A. <http://www.zaubersoftware.com/>
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
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

import ar.com.zauber.commons.dao.Resource;
import ar.com.zauber.commons.message.message.templates.AbstractMessagePartTemplate;
import ar.com.zauber.commons.message.message.templates.PartTemplate;

/**
 * {@link PartTemplate} that uses Velocity Engine to render. The difference
 * between Velocity Singleton usage and VelocityEngine is that with this 
 * implementation you can create an instance of VelocityEngine on your own and 
 * inject it as a configurable bean (this lets you configure the 
 * velocity.properties as you wish instead of using the default one). 
 * If you chose not to inject your own velocity engine, 
 * it uses a default one provided by Velocity framework.
 * 
 * 
 * @author Cecilia Hagge
 * @since 09/03/2011
 */
public class VelocityEngineMessagePartTemplate 
    extends AbstractMessagePartTemplate implements PartTemplate {

    private final VelocityEngine velocityEngine;
    
    /**
     * Creates the VelocityEngineMessageTemplate.
     */
    public VelocityEngineMessagePartTemplate(final String contentType, 
            final Resource content, final String charset,
            final VelocityEngineFactoryBean velocityEngineFactory) {
        super(content, contentType, charset);
        this.velocityEngine = velocityEngineFactory.getObject();
    }
    
    /**
     * Creates the VelocityEngineMessageTemplate.
     */
    public VelocityEngineMessagePartTemplate(final Resource content,
            final String contentType, final VelocityEngine velocityEngine,
            final String charset) {
        super(content, contentType, charset);
        this.velocityEngine = velocityEngine;
    }
    /**
     * Creates the VelocityEngineMessagePartTemplate.
     */
    public VelocityEngineMessagePartTemplate(final Resource content,
            final String contentType, final String charset) {
        super(content, contentType, charset);
        this.velocityEngine = new VelocityEngine();
    }

    /** @see AbstractTemplate#renderString(String, Map) */
    @Override
    protected final String renderString(final String message, 
            final Map<String, Object> model) {
        final StringWriter writer = new StringWriter();
        final VelocityContext context = new VelocityContext(model);
        try {
            this.velocityEngine.evaluate(context, writer, "message", new StringReader(message));
        } catch(final Exception e) {
            throw new RuntimeException(e);
        }
        
        return writer.toString();
    }

}
