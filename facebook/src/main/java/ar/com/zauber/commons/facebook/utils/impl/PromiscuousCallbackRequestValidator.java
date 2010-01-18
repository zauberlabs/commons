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
package ar.com.zauber.commons.facebook.utils.impl;

import javax.servlet.http.HttpServletRequest;

import ar.com.zauber.commons.facebook.utils.CallbackRequestValidator;

/**
 * {@link CallbackRequestValidator} que siempre dice que si!
 * 
 * @author Juan F. Codagnone
 * @since Dec 24, 2007
 */
public class PromiscuousCallbackRequestValidator 
       implements CallbackRequestValidator {

    /** @see CallbackRequestValidator#validate(HttpServletRequest) */
    public final boolean validate(final HttpServletRequest request) {
        return true;
    }

}
