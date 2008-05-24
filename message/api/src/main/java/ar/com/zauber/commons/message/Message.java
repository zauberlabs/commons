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
package ar.com.zauber.commons.message;



/**
 * A (human) message
 *  
 * @author Juan F. Codagnone
 * @since Jun 18, 2005
 */
public interface Message {
    
    /** @return the content of the message */
    String getContent();

    /** @return the from address */
    NotificationAddress getReplyToAddress();

    /** @return the subject of the message */
    String getSubject();
    
    // TODO idea: por hay, en un futuro, un mensaje pueda tener muchas partes  
    // [ Part getPart() ] de diferentes tipos (texto o imagen). Esto permitiria
    // mandar el email de la invitacion y un flyer (o algo asi). Bah por hay
    // sino es algo que se le puede preguntar a Event.. ideas?
    
}
