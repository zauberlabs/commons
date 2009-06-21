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
package ar.com.zauber.commons.xmpp.message;

import java.util.Map;

import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.FormField;

/**
 * Crea un formulario dinamicamente como prueba
 * 
 * 
 * @author Juan F. Codagnone
 * @since Jun 20, 2009
 */
public class FooXMPPMessagePacketExtensionTemplate 
  implements XMPPMessagePacketExtensionTemplate {

    /** @see XMPPMessagePacketExtensionTemplate#render(java.util.Map) */
    public final PacketExtension render(final Map<String, Object> model) {
        final Form form = new Form(Form.TYPE_FORM);
        form.setInstructions("bla");
        form.setTitle("Observaciones");
        
        final FormField field = new FormField("date");
        field.setType(FormField.TYPE_HIDDEN);
        field.setDescription("fecha que se esta observando");
        field.addValue("2009-06-20");
        
        form.addField(field);
        return form.getDataFormToSend();
    }
}
