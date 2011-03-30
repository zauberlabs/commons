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
package ar.com.zauber.commons.dao.exception;

import java.util.Collection;
import java.util.LinkedList;

import org.apache.commons.lang.Validate;

/**
 * 
 * 
 * 
 * @author Mariano Semelman
 * @since Oct 13, 2009
 */
public class TriggeredException extends RuntimeException {

    /** <code>serialVersionUID</code> */
    private static final long serialVersionUID = 1L;
    private Collection<Throwable> exceptions;
    
    
    /**
     * Creates the TriggeredException.
     *
     * @param list
     */
    public TriggeredException(final Collection<Throwable> exceptions) {
        super();
        Validate.notNull(exceptions);
        this.exceptions = exceptions;
    }


    /** constructor por defecto */
    public TriggeredException() {
        super();
        exceptions = new LinkedList<Throwable>();
    }
    
    /** @see java.lang.Throwable#getMessage() */
    public final String getMessage() {
        String message = (this.getClass().getSimpleName());
        if(!this.exceptions.isEmpty()) {
            message += "The following Exceptions ocurred:\n";
            for(Throwable t : exceptions) {
                message += t.toString() + "\n\n";
            }
        }
        return message;
    }


}
