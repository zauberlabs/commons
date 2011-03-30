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
package ar.com.zauber.commons.moderation;

import java.util.Collection;

import javax.persistence.MappedSuperclass;

import org.apache.commons.lang.Validate;
import org.hibernate.annotations.Type;

import ar.com.zauber.commons.dao.Transformer;
import ar.com.zauber.commons.dao.closure.ListClosure;
import ar.com.zauber.commons.dao.closure.TargetTransformerClosure;
import ar.com.zauber.commons.moderation.exceptions.IllegalModerationStateTransitionException;
import ar.com.zauber.commons.repository.BaseCreationModificationAuditableEntity;

/**
 * Clase base para las entidades que requieren moderación
 * 
 * @author Pablo Grigolatto
 * @since Oct 5, 2009
 */
@MappedSuperclass
public abstract class BaseModerateableEntity 
    extends BaseCreationModificationAuditableEntity
    implements Moderateable {

    /** El tipo 'type_moderationState' debe ser especificado en las subclases */
    @Type(type = "type_moderationState")
    private ModerationState moderationState;
    
    /** Constructor para hibernate */
    protected BaseModerateableEntity() {
        // default
    }
    
    /** Constructor */
    public BaseModerateableEntity(final ModerationState moderationState) {
        Validate.notNull(moderationState);
        this.moderationState = moderationState;
    }
    
    /** @see Moderateable#getModerationState() */
    public final ModerationState getModerationState() {
        return moderationState;
    }

    /** @see Moderateable#changeState(ModerationState) */
    public final void changeState(final ModerationState newState) {
        Validate.notNull(newState);
        if(!moderationState.canChangeTo(newState)) {
            throw new IllegalModerationStateTransitionException(
                "Invalid state change attemp, possible states are: " 
                + getValidDestinationStatesMessage(), moderationState,
                newState);
        }
        beforeStateChange(moderationState, newState);
        ModerationState oldState = moderationState;
        moderationState = newState;
        afterStateChange(oldState, moderationState);
    }
    
    /** Mensaje enviado justo antes de cambiar de estado, 
     *  luego de realizadas las validaciones */
    protected void beforeStateChange(final ModerationState actualState,
            final ModerationState newState) {
        // Override
    }
    
    /** Mensaje enviado justo después de cambiar de estado */
    protected void afterStateChange(final ModerationState oldState,
            final ModerationState actualState) {
        // Override
    }
    
    /** @return una colección de nombre de estados posibles */
    private Collection<String> getValidDestinationStatesMessage() {
        final Transformer<ModerationState, String> transformer 
            = new Transformer<ModerationState, String>() {
                public String transform(final ModerationState input) {
                    return input.getName();
                }
            }; 
        final ListClosure<String> result = new ListClosure<String>();
        final TargetTransformerClosure<ModerationState, String> transformClosure 
            = new TargetTransformerClosure<ModerationState, String>(
                    transformer, result);
        
        for(ModerationState state : moderationState.getValidDestinations()) {
            transformClosure.execute(state);
        }
        return result.getList();
    }
}
