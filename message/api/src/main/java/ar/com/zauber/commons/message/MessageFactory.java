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
package ar.com.zauber.commons.message;

import java.util.Map;

import ar.com.zauber.commons.dao.exception.NoSuchEntityException;


/**
 * Renders Messages
 *
 * @author Juan F. Codagnone
 * @since Oct 6, 2005
 */
public interface MessageFactory {
    /**
     * Create a {@link Message} refering to the content "by name".
     * 
     * @param viewName name that is used to create the message
     * @param model    model for the message
     * @return        a new message.
     * @throws NoSuchEntityException if the viewname can't be resolved.
     */
    Message createMessage(String viewName, Map<String, Object> model)
            throws NoSuchEntityException;
}
