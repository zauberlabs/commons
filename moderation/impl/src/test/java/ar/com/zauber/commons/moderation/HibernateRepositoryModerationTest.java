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

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.test.AbstractSingleSpringContextTests;
import org.springframework.test.AbstractTransactionalSpringContextTests;

import ar.com.zauber.commons.auth.AuthenticationUserMapper;
import ar.com.zauber.commons.auth.mock.MockAuthenticationUser;
import ar.com.zauber.commons.date.DateProvider;
import ar.com.zauber.commons.date.impl.InmutableDateProvider;
import ar.com.zauber.commons.moderation.exceptions.IllegalModerationStateTransitionException;
import ar.com.zauber.commons.moderation.model.EnumModerationState;
import ar.com.zauber.commons.moderation.model.MockInmutableModerationEntry;
import ar.com.zauber.commons.moderation.model.MockRepositoryModerateableEntity;
import ar.com.zauber.commons.repository.Reference;
import ar.com.zauber.commons.repository.SpringHibernateRepository;

/**
 * Pruebas de uso y persistencia de las interfaces de moderación
 *  
 * @author Pablo Grigolatto
 * @since Oct 6, 2009
 */
public class HibernateRepositoryModerationTest 
    extends AbstractTransactionalSpringContextTests {

    private static final String ANONYMOUS = "Anonymous";

    /** dependency injection */
    // CHECKSTYLE:ALL:OFF
    protected ModerationEntryRepository moderationEntryRepository;
    protected SpringHibernateRepository repository;
    // CHECKSTYLE:ALL:ON
    
    private ModerationState open = EnumModerationState.OPEN;
    private ModerationState ready = EnumModerationState.READY;
    private ModerationState closed = EnumModerationState.CLOSED;
    
    /** Inyección de dependencias */
    public HibernateRepositoryModerationTest() {
        setDefaultRollback(true);
        setPopulateProtectedVariables(true);
    }

    /** @see AbstractSingleSpringContextTests#getConfigPaths() */
    @Override
    protected final String[] getConfigPaths() {
        final String s = "/ar/com/zauber/commons/moderation/config/"; 
        return new String[] {
                s + "test-property-context-spring.xml",
                s + "test-hibernate-mapping-spring.xml",
                s + "test-services-spring.xml",
                s + "test-injection-hibernate-spring.xml",
        };
    }
    
    /** Guardar una entrada de moderacion mediante un repositorio genérico */
    @Test
    public final void testSaveModerationEntry() {
        final Date date = new Date();
        final DateProvider dateProvider = new InmutableDateProvider(date);
        final AuthenticationUserMapper<String> aum 
            = new MockAuthenticationUser<String>(ANONYMOUS);
        
        final Moderateable entity = new MockRepositoryModerateableEntity(
                open, moderationEntryRepository);
        repository.saveOrUpdate(entity);
        final Long id = entity.getId();
        
        final ModerationEntry entry = new MockInmutableModerationEntry(
                (Reference<Moderateable>) entity.generateReference(), open, closed, 
                dateProvider.getDate(), aum.getUser());
        
        repository.saveOrUpdate(entry);
        repository.getHibernateTemplate().flush();
        repository.getHibernateTemplate().clear();
        
        Reference<?> aRef = entry.generateReference();
        final ModerationEntry entryFromDb = 
            (ModerationEntry) repository.retrieve(aRef);

        Assert.assertEquals(open, entryFromDb.getInitialState());
        Assert.assertEquals(closed, entryFromDb.getFinalState());
        Assert.assertEquals(ANONYMOUS, entryFromDb.getModeratedBy());
        Assert.assertEquals(date, entryFromDb.getModeratedAt());
        
        Assert.assertEquals(id.longValue(), 
                entryFromDb.getEntityReference().getId());
        Assert.assertEquals(entity.getClass().getName(), 
                entryFromDb.getEntityReference().getClazz().getName());
    }
    
    /** Salvar entradas del historial */
    @Test
    public final void testSaveEntries() {
        final Moderateable entity 
            = new MockRepositoryModerateableEntity(open, moderationEntryRepository);
        repository.saveOrUpdate(entity);
        
        entity.changeState(ready);
        Assert.assertEquals(1, entity.getModerationHistory().size());
        entity.changeState(closed);
        Assert.assertEquals(2, entity.getModerationHistory().size());
    }

    /** Salvar una entidad y su estado de moderación */
    @Test
    public final void testModerationEntitySave() {
        final Moderateable entity = new MockRepositoryModerateableEntity(
                open, moderationEntryRepository);
        repository.saveOrUpdate(entity);
        final Reference<?> ref = entity.generateReference();
        repository.getHibernateTemplate().flush();
        repository.getHibernateTemplate().clear();
        
        final Moderateable entityFromDb = (Moderateable) repository.retrieve(ref);
        Assert.assertEquals(open, entityFromDb.getModerationState());
        Assert.assertNotNull(entityFromDb.getModerationHistory());
        Assert.assertEquals(0, entityFromDb.getModerationHistory().size());
        
        Assert.assertEquals(EnumModerationState.OPEN.getName(), 
                entityFromDb.getModerationState().getName());
        Assert.assertEquals(1, 
                entityFromDb.getModerationState().getValidDestinations().size());
        Assert.assertFalse(entityFromDb.getModerationState().canChangeTo(closed));
    }
    
    /** Salvar entradas de múltiples entidades */
    @Test
    public final void testSaveMultipleEntities() {
        final Moderateable entityA 
            = new MockRepositoryModerateableEntity(open, moderationEntryRepository);
        final Moderateable entityB 
            = new MockRepositoryModerateableEntity(ready, moderationEntryRepository);
        final Moderateable entityC 
            = new MockRepositoryModerateableEntity(ready, moderationEntryRepository);
        repository.saveOrUpdate(entityA);
        repository.saveOrUpdate(entityB);
        repository.saveOrUpdate(entityC);
        
        entityA.changeState(ready);
        Assert.assertEquals(1, entityA.getModerationHistory().size());
        entityB.changeState(open);
        Assert.assertEquals(1, entityB.getModerationHistory().size());
        entityA.changeState(closed);
        Assert.assertEquals(2, entityA.getModerationHistory().size());
        entityB.changeState(ready);
        Assert.assertEquals(2, entityB.getModerationHistory().size());
        entityB.changeState(closed);
        Assert.assertEquals(3, entityB.getModerationHistory().size());
        final List<ModerationEntry> l = entityB.getModerationHistory();
        for (int i = 0; i < l.size() - 1; i++) {
            Assert.assertTrue("La fecha i deberia ser anteior a i+1", 
                l.get(i).getModeratedAt().before(l.get(i + 1).getModeratedAt()));
        }
        Assert.assertTrue(entityC.getModerationHistory().isEmpty());
    }
    
    /** No se puede cambiar al mismo estado */
    @Test
    public final void testSameState() {
        final Moderateable entity = 
            new MockRepositoryModerateableEntity(open, moderationEntryRepository);
        repository.saveOrUpdate(entity);
        
        entity.changeState(ready);
        Assert.assertEquals(1, entity.getModerationHistory().size());
        entity.changeState(closed);
        Assert.assertEquals(2, entity.getModerationHistory().size());

        final Reference<?> ref = entity.generateReference();
        repository.getHibernateTemplate().flush();
        repository.getHibernateTemplate().clear();

        final Moderateable entityFromDb = (Moderateable) repository.retrieve(ref);
        
        try {
            entityFromDb.changeState(closed);
            
            fail("No se puede cambiar al mismo estado");
        } catch (IllegalModerationStateTransitionException e) {
            // ok!
        }
        Assert.assertEquals(2, entityFromDb.getModerationHistory().size());
        List<ModerationEntry> moderationHistory = entityFromDb.getModerationHistory();
        moderationHistory.size();
    }
    
}

