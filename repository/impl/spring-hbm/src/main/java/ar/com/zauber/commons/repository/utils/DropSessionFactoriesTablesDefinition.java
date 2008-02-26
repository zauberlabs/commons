/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.repository.utils;

import java.util.Iterator;
import java.util.List;

public class DropSessionFactoriesTablesDefinition {

    private List<String> localSessionFactoryBeanNames;
    private String testMarkerTableName;

    /**
     * Crea el/la DropSessionFactoriesTablesDefinition.
     * 
     * @param localSessionFactoryBeanNames
     * @param testMarkerTableName
     */
    public DropSessionFactoriesTablesDefinition(
            final List localSessionFactoryBeanNames, 
            final String testMarkerTableName) {
        super();
        this.localSessionFactoryBeanNames = localSessionFactoryBeanNames;
        this.testMarkerTableName = testMarkerTableName;
    }

    public final Iterator getLocalSessionFactoryBeanNames() {
        return localSessionFactoryBeanNames.iterator();
    }

    /**
     * Devuelve el/la testMarkerTableName.
     * 
     * @return <code>String</code> con el/la testMarkerTableName.
     */
    public final String getTestMarkerTableName() {
        return testMarkerTableName;
    }

}
