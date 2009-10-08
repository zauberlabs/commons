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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.moderation.ModerationState;
import ar.com.zauber.commons.repository.Persistible;
import ar.com.zauber.commons.repository.Reference;
import ar.com.zauber.commons.repository.misc.Nameable;

/**
 * Implementación de prueba de {@link ModerationState}
 * 
 * @author Pablo Grigolatto
 * @since Oct 8, 2009
 */
public enum EnumModerationState implements ModerationState {
    /** State open   -> {ready} */
    OPEN,
    /** State ready  -> {open, closed} */
    READY,
    /** State closed -> {} */
    CLOSED;

    /** Define transiciones válidas */
    static {
        OPEN.setValidDestinations(Arrays.asList(
                (ModerationState)READY));
        
        READY.setValidDestinations(Arrays.asList(
                (ModerationState)OPEN, (ModerationState)CLOSED));
        
        CLOSED.setValidDestinations(Collections.EMPTY_LIST);
    }

    private Collection<ModerationState> validDestinations;
    
    /** @see ModerationState#canChangeTo(ModerationState) */
    public boolean canChangeTo(final ModerationState state) {
        return validDestinations.contains(state);
    }

    /** @see ModerationState#getValidDestinations() */
    public Collection<ModerationState> getValidDestinations() {
        return Collections.unmodifiableCollection(validDestinations);
    }
    
    /** @see Nameable#getName() */
    public String getName() {
        return name();
    }

    /** @see Persistible#generateReference() */
    public <T> Reference<? extends Persistible> generateReference() {
        throw new NotImplementedException();
    }

    /** @see Persistible#getId() */
    public Long getId() {
        throw new NotImplementedException();
    }

    /** @see Persistible#setId(java.lang.Long) */
    public void setId(final Long anId) {
        throw new NotImplementedException();
    }
    
    /** Define los estados alcanzables */
    private void setValidDestinations(
            final Collection<ModerationState> validDestinations) {
        Validate.notNull(validDestinations);
        this.validDestinations = validDestinations;
    }
    
}
