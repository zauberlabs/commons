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
package ar.com.zauber.commons.moderation;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import ar.com.zauber.commons.moderation.exceptions.IllegalModerationStateTransitionException;
import ar.com.zauber.commons.moderation.model.EnumModerationState;
import ar.com.zauber.commons.moderation.model.MockBaseModerateableEntity;

/**
 * Prueba de uso de la interface {@link Moderateable}, {@link ModerationState} 
 * y {@link ModerationEntry} 
 * 
 * @author Pablo Grigolatto
 * @since Oct 5, 2009
 */
public class ModerationStateTest {

    private Moderateable entity; 
    private ModerationState open = EnumModerationState.OPEN;
    private ModerationState ready = EnumModerationState.READY;
    private ModerationState closed = EnumModerationState.CLOSED;

    /** inicializa objetos */
    @Before
    public final void before() {
        entity = new MockBaseModerateableEntity(open);
    }
    
    /** Cambio inválido */
    @Test(expected = IllegalModerationStateTransitionException.class)
    public final void testInvalidDestinationState() {
        entity.changeState(ready);
        Assert.assertEquals(ready, entity.getModerationState());
        //can not change twice
        entity.changeState(ready);
    }

    /** Cambio válido */
    @Test
    public final void testValidDestinationState() {
        Assert.assertEquals(open, entity.getModerationState());
        entity.changeState(ready);
        Assert.assertEquals(ready, entity.getModerationState());
        entity.changeState(open);
        Assert.assertEquals(open, entity.getModerationState());
        entity.changeState(ready);
        Assert.assertEquals(ready, entity.getModerationState());
        entity.changeState(closed);
        Assert.assertEquals(closed, entity.getModerationState());
    }
    
}