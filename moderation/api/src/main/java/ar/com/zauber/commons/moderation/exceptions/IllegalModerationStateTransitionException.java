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
package ar.com.zauber.commons.moderation.exceptions;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.moderation.ModerationState;

/**
 * Indica que se ha intentado realizar una transición no permitida entre dos
 * {@link ModerationState}
 * 
 * @author Pablo Grigolatto
 * @since Oct 5, 2009
 */
public class IllegalModerationStateTransitionException extends RuntimeException {
    private static final long serialVersionUID = 721102751340551553L;
    private final ModerationState initialState;
    private final ModerationState destinationState;

    /** 
     * @param initialState estado desde el cual se está intentando cambiar el estado 
     * @param destinationState estado al cual se está intentando ir
     */
    public IllegalModerationStateTransitionException(final String msg, 
            final ModerationState initialState, 
            final ModerationState destinationState) {
        super(msg);
        Validate.notNull(initialState);
        Validate.notNull(destinationState);
        
        this.initialState = initialState;
        this.destinationState = destinationState;
    }

    public final ModerationState getInitialState() {
        return initialState;
    }

    public final ModerationState getDestinationState() {
        return destinationState;
    }
}
