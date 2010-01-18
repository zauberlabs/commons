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
package ar.com.zauber.commons.test.utils;

import ar.com.zauber.commons.repository.utils.HibernateManageTables;



/**
 * Test del mecanismo de manejo de tablas.
 * 
 * 
 * @author Martin A. Marquez
 * @since Jan 17, 2008
 */
public class DropTablesTest extends  BaseTransactionalRollbackTest {
    // CHECKSTYLE:ALL:OFF
    protected HibernateManageTables hibernateManageTables;
    // CHECKSTYLE:ALL:ON
    
    /** constructor */
    public DropTablesTest() {
        super();
        setDefaultRollback(false);
            
    }
    
    /** print */
    public final void testPrint() throws Exception {
        hibernateManageTables.print();
    }
    
}
