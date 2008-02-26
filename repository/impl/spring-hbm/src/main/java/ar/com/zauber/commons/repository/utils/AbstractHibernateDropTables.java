/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.repository.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.Dialect;
import org.hibernate.tool.hbm2ddl.DatabaseMetadata;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

import ar.com.zauber.commons.repository.Repository;

/**
 * This class can be used to drop the database Schema.
 *
 * @author Martin A. Marquez
 * @since May 1, 2007
 */
public abstract class AbstractHibernateDropTables
    implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    private DropSessionFactoriesTablesDefinition
        dropSessionFactoriesTablesDefinition;
    private Repository repository;
    private String sentenceSeparator = "GO";
    Log logger = LogFactory.getLog(this.getClass());

    public final void setApplicationContext(
            final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;        
    }
    
    /**
     * Crea el/la BaseTest.
     *
     */
    public AbstractHibernateDropTables() {
        super();
    }

    public final void testDrop() throws Exception {
        dropDatabase();
    }

    private void dropDatabase() throws Exception {

        logger.trace("Entering runDbInstall");

        Statement statement;
        ResultSet resultSet;
        Connection connection;
        LocalSessionFactoryBean localSessionFactoryBean;
        String testMarkerTableName = dropSessionFactoriesTablesDefinition.getTestMarkerTableName();
        String message;
        
        for(Iterator iter = dropSessionFactoriesTablesDefinition.getLocalSessionFactoryBeanNames(); iter.hasNext();) {

            localSessionFactoryBean =
               (LocalSessionFactoryBean)
                   applicationContext.getBean("&" + iter.next());
       
              
            connection = ((SessionFactory)localSessionFactoryBean
                    .getObject()).openSession().connection();
        
            
            try {
                Configuration configuration = localSessionFactoryBean.getConfiguration();
                final Dialect dialect = Dialect.getDialect(configuration.getProperties());
//                String[] sqlCreate = configuration.generateSchemaCreationScript(dialect);
//                for(int i = 0; i < sqlCreate.length; i++) {
//                  writer.write(sqlCreate[i]);
//                  System.out.println(sqlCreate[i]);
//                }                
                DatabaseMetadata metadata = new DatabaseMetadata(connection, dialect);
                String[] sqlUpdate = configuration.generateSchemaUpdateScript(dialect, metadata);
                for(int i = 0; i < sqlUpdate.length; i++) {
//                  writer.write(sqlCreate[i]);
                  System.out.println(sqlUpdate[i]);
                  System.out.println(sentenceSeparator);
              }
                
//                File file;
//                file = File.createTempFile("schemaCreate" + Calendar.getInstance(), ".sql");
//                OutputStream stream;
//                stream = new FileOutputStream(file, true);
//                OutputStreamWriter writer;
//                writer = new OutputStreamWriter(stream);


            } catch(Exception e) {
                logger.warn("An exception ocurred and the database"
                    + "schema didn't finish to execute");
                return;
            }
            
            String columnName = "message";
            
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(
                        "select message from " + testMarkerTableName);
            } catch (Exception e) {
                
                // An exeption. Give some explanation just in case.
                logger.warn("An exception was caught selecting from " 
                            + testMarkerTableName
                            + " for the connection url "
                            + connection.getMetaData().getURL());
                logger.warn("It is probable because the table does not exist."
                            + " Please create it with:");
                logger.warn("\tcreate table "
                            + testMarkerTableName 
                            + "(" + columnName + " varchar (50));");
                logger.warn("\tinsert into " 
                            + testMarkerTableName
                            + " values ('YES, DROP ME');");
                throw e;
            }

            message = null;

            if (resultSet.next()) {
                message = resultSet.getString(columnName);
            }

            if(message == null) {
                return;
            }

            if("NO, DONT DROP ME".equals(message)) {
                try {
                    localSessionFactoryBean.updateDatabaseSchema();
                } catch(Exception e) {
                    logger.warn("An exception ocurred and the database"
                       + "schema didn't finish to execute");
                    return;
                }
                return;
            } else {
                if("YES, DROP ME".equals(message)) {
                    try {
                        localSessionFactoryBean.dropDatabaseSchema();
                        localSessionFactoryBean.createDatabaseSchema();
                    } catch(Exception e) {
                        logger.warn("An exception ocurred and the database" 
                                + "schema didn't finish to execute");
                        return;
                    }
                } else {
                    logger.warn("An exception ocurred and the database" 
                            + "schema didn't finish to execute");
                    return;
                }
            }
            
            connection.commit();
        }
    }
}
