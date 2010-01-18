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
package ar.com.zauber.commons.xmpp.common;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import ar.com.zauber.commons.dao.Closure;

/**
 * Closure que logea via Log4j todo lo que recibe.
 * 
 * @author Juan F. Codagnone
 * @since Jun 19, 2009
 * @param <T> param
 */
public class DebugLoggerClosure<T> implements Closure<T> {
    private final Logger logger = Logger.getLogger(DebugLoggerClosure.class);
    private final Closure<?> target;
    
    /** Creates the LoggerClosure.*/
    public DebugLoggerClosure(final Closure<?> target) {
        Validate.notNull(target);
        
        this.target = target;
    }

    /** @see Closure#execute(Object) */
    public final void execute(final T t) {
        if(logger.isDebugEnabled()) {
            logger.debug(t);
        }
        target.equals(t);        
    }
}
