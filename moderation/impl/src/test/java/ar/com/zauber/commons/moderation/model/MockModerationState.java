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
package ar.com.zauber.commons.moderation.model;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import ar.com.zauber.commons.moderation.ModerationState;

/**
 * Implementacion para test
 * 
 * @author Pablo Grigolatto
 * @since Oct 6, 2009
 */
public class MockModerationState implements ModerationState {
    private final String name;
    private final Collection<ModerationState> validDestinations;

    /** constructor */
    public MockModerationState(final String name) {
        this.name = name;
        this.validDestinations = new LinkedList<ModerationState>();
    }
    
    /** @see ModerationState#getName() */
    public final String getName() {
        return name;
    }

    /** @see ModerationState#getValidDestinations() */
    public final Collection<ModerationState> getValidDestinations() {
        return Collections.unmodifiableCollection(validDestinations);
    }

    /** @see ModerationState#canChangeTo(ModerationState) */
    public final boolean canChangeTo(final ModerationState state) {
        return getValidDestinations().contains(state);
    }

    /** Agrega un nuevo estado final válido */
    public final void addValidDestination(final ModerationState state) {
        validDestinations.add(state);
    }
    
}