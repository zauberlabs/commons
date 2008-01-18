package ar.com.zauber.commons.test.utils;

import ar.com.zauber.commons.repository.utils.HibernateManageTables;



/**
 * Test del mecanismo de manejo de tablas.
 * 
 * 
 * @author Martin A. Marquez
 * @since Jan 17, 2008
 */
public class DropTablesTest
    extends  BaseTransactionalRollbackTest {

    protected HibernateManageTables hibernateManageTables;

    public DropTablesTest() {
        super();
        setDefaultRollback(false);
            
    }

    public void testPrint() throws Exception {
        hibernateManageTables.print();
    }
    
}
