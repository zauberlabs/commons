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
package ar.com.zauber.commons.repository;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Tests para reproducir un {@link NullPointerException} al generar referencias 
 * 
 * @author Pablo Grigolatto
 * @since Oct 9, 2009
 */
public class GenerateReferenceTest {

    /** generar la referencia de un {@link BaseEntity} con id nulo */
    @Test
    public final void testBaseEntityReferenceNullId() {
        final BaseEntity baseEntity = new MockBaseEntity();
        Reference<?> ref = baseEntity.generateReference();
        Assert.assertNotNull(ref);
        Assert.assertNotNull(ref.getClass());
        Assert.assertEquals(-1L, ref.getId());
        Assert.assertEquals(-1, ref.getVersion());
        
        baseEntity.setId(Long.valueOf(666));
        ref = baseEntity.generateReference();
        Assert.assertEquals(666, ref.getId());
    }
    
    /** generar la referencia de un {@link BaseModifiableEntity} con id nulo */
    @Test
    public final void testBaseModifiableEntityReferenceNullId() {
        final BaseModifiableEntity baseModifiableEntity = 
            new MockBaseModifiableEntity();
        Reference<?> ref = baseModifiableEntity.generateReference();
        Assert.assertNotNull(ref);
        Assert.assertNotNull(ref.getClass());
        Assert.assertEquals(-1L, ref.getId());
        Assert.assertEquals(-1, ref.getVersion());
        
        baseModifiableEntity.setId(Long.valueOf(666));
        ref = baseModifiableEntity.generateReference();
        Assert.assertEquals(666, ref.getId());
    }
    
    
    /** Implementación para prueba */
    class MockBaseEntity extends BaseEntity {
        private Long id;
        
        public Long getId() {
            return id;
        }
        
        public void setId(final Long anId) {
            id = anId;
        }
    }
    
    /** Implementación para prueba */
    class MockBaseModifiableEntity extends BaseModifiableEntity {
        private Long id;
        
        public Long getId() {
            return id;
        }
        
        public void setId(final Long anId) {
            id = anId;
        }
    }
}
