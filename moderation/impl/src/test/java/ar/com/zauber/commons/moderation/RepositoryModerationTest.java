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
package ar.com.zauber.commons.moderation;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import ar.com.zauber.commons.auth.mock.MockAuthenticationUser;
import ar.com.zauber.commons.date.impl.InmutableDateProvider;
import ar.com.zauber.commons.moderation.model.MockModerationEntryRepository;
import ar.com.zauber.commons.moderation.model.MockModerationState;
import ar.com.zauber.commons.moderation.model.MockRespositoryModerateableEntity;

/**
 * Prueba de uso de la interface {@link Moderateable}, {@link ModerationState} 
 * y {@link ModerationEntry} 
 * 
 * @author Pablo Grigolatto
 * @since Oct 5, 2009
 */
public class RepositoryModerationTest {

    private static final String ANONYMOUS = "Anonymous";
    private Moderateable entity; 
    private ModerationState open;
    private ModerationState ready;
    private ModerationState closed;
    private ModerationEntryRepository moderationRepository;
    private Date date;

    /** inicializa objetos */
    @Before
    public final void before() {
        open = new MockModerationState("open");
        ready = new MockModerationState("ready");
        closed = new MockModerationState("closed");
        ((MockModerationState)open).addValidDestination(ready);
        ((MockModerationState)ready).addValidDestination(open);
        ((MockModerationState)ready).addValidDestination(closed);

        date = new Date();
        moderationRepository = new MockModerationEntryRepository(
                new InmutableDateProvider(date), 
                new MockAuthenticationUser<String>(ANONYMOUS));
        entity = new MockRespositoryModerateableEntity(
                Long.valueOf(46), open, moderationRepository);
    }

    /** Historial de cambios */
    @Test
    public final void testModerationHistory() {
        entity.changeState(ready);
        
        Assert.assertEquals(ready, entity.getModerationState());
        Assert.assertEquals(1, entity.getModerationHistory().size());
        Assert.assertEquals(open, 
                entity.getModerationHistory().get(0).getInitialState());
        Assert.assertEquals(ready, 
                entity.getModerationHistory().get(0).getFinalState());
        Assert.assertEquals(entity.getClass().getName(), 
                entity.getModerationHistory().get(0).getEntityReference()
                    .getClassName());
        Assert.assertEquals(ANONYMOUS, 
                entity.getModerationHistory().get(0).getModeratedBy());
        Assert.assertEquals(date, 
                entity.getModerationHistory().get(0).getModeratedAt());
    }
    
    /** Mas de un cambio */
    @Test
    public final void testModerationHistoryEntries() {
        entity.changeState(ready);
        entity.changeState(open);
        entity.changeState(ready);
        entity.changeState(closed);
        
        Assert.assertEquals(4, entity.getModerationHistory().size());
        //La lista esta ordenada por contrato
        Assert.assertEquals(ready, 
                entity.getModerationHistory().get(0).getFinalState());
        Assert.assertEquals(open, 
                entity.getModerationHistory().get(1).getFinalState());
        Assert.assertEquals(ready, 
                entity.getModerationHistory().get(2).getFinalState());
        Assert.assertEquals(closed, 
                entity.getModerationHistory().get(3).getFinalState());
    }
    
}