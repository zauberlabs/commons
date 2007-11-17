package ar.com.zauber.commons.repository.utils;

import java.util.Iterator;
import java.util.List;


public class DropSessionFactoriesTablesDefinition {

    private List localSessionFactoryBeanNames;
    private String testMarkerTableName;

    /**
     * Crea el/la DropSessionFactoriesTablesDefinition.
     *
     * @param localSessionFactoryBeanNames
     * @param testMarkerTableName
     */
    public DropSessionFactoriesTablesDefinition(List localSessionFactoryBeanNames, String testMarkerTableName) {
        super();
        this.localSessionFactoryBeanNames = localSessionFactoryBeanNames;
        this.testMarkerTableName = testMarkerTableName;
    }

    public Iterator getLocalSessionFactoryBeanNames()
    {
        return localSessionFactoryBeanNames.iterator();
    }
    
    /**
     * Devuelve el/la testMarkerTableName.
     *
     * @return <code>String</code> con el/la testMarkerTableName.
     */
    public String getTestMarkerTableName() {
        return testMarkerTableName;
    }

}
