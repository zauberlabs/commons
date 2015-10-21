/**
 * Copyright (c) 2005-2012 Zauber S.A. <http://www.zaubersoftware.com/>
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

import java.util.Map;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.message.MessagePart;
import ar.com.zauber.commons.message.message.templates.PartTemplate;

/**
 * {@link PartTemplate} que no realiza ninguna transformación
 * 
 * @author Pablo Martin Grigolatto
 * @since Oct 25, 2010
 */
public class FixedPartTemplate implements PartTemplate {

    private final MessagePart messagePart;

    /** Creates the NullPartTemplate. */
    public FixedPartTemplate(final MessagePart messagePart) {
        Validate.notNull(messagePart);
        this.messagePart = messagePart;
    }

    /** @see PartTemplate#createPart(Map) */
    @Override
    public final MessagePart createPart(final Map<String, Object> model) {
        return messagePart;
    }

}
