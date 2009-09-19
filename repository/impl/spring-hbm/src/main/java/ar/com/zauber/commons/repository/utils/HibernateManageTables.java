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
package ar.com.zauber.commons.repository.utils;

import java.io.PrintStream;
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

/**
 * This class can be used to drop/update the database Schema.
 *
 * @author Martin A. Marquez
 * @since May 1, 2007
 */
public class HibernateManageTables
    implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    private DropSessionFactoriesTablesDefinition
        dropSessionFactoriesTablesDefinition;
    private String filePath;
    
    /**
     * @param filePath
     */
    public final void setFilePath(final String filePath) {
        this.filePath = filePath;
    }

    /**
     * @param sentenceSeparator
     */
    public final void setSentenceSeparator(final String sentenceSeparator) {
        this.sentenceSeparator = sentenceSeparator;
    }

    private String sentenceSeparator = "GO";
    
    private Log logger = LogFactory.getLog(this.getClass());

    /** @see ApplicationContextAware#setApplicationContext(ApplicationContext) */
    public final void setApplicationContext(
            final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;        
    }
    
    /**
     * Crea el/la BaseTest.
     *
     */
    public HibernateManageTables(
            final DropSessionFactoriesTablesDefinition definition) {
        super();
        this.dropSessionFactoriesTablesDefinition = definition;
    }

    
    /**
     * 
     * This method is used to dry run or really modify the database
     * schema.
     * 
     * @param dropOrUpdate indicates if the schema will suffer changes.
     */
    public final void manage(final boolean dropOrUpdate) {
        PrintStream printStream = System.out;
        if(filePath != null && filePath.length() > 0) {
            try {
                printStream = new PrintStream(filePath);
            } catch (Exception e) {
                logger.info("Unable to open " + filePath);
            }
        }
        dropDatabase(dropOrUpdate, printStream);
    }
    
    
    /**
     * Print to the standard output the schema modifications
     */
    public final void print() {
        manage(false);
    }
    
    /**
     * @param dropOrUpdate
     * @param printStream
     */
    private void dropDatabase(
            final boolean dropOrUpdate, final PrintStream printStream) {

        logger.trace("Entering runDbInstall");
        
        for(Iterator<String> iter = dropSessionFactoriesTablesDefinition
                .getLocalSessionFactoryBeanNames(); iter.hasNext();) {

            LocalSessionFactoryBean localSessionFactoryBean =
               (LocalSessionFactoryBean)
                   applicationContext.getBean("&" + iter.next());
              
            Connection connection = ((SessionFactory)localSessionFactoryBean
                    .getObject()).openSession().connection();
        
            
            try {
                Configuration configuration =
                    localSessionFactoryBean.getConfiguration();
                final Dialect dialect =
                    Dialect.getDialect(configuration.getProperties());
                
                String[] sqlCreate =
                    configuration.generateSchemaCreationScript(dialect);
                printScript(printStream, sqlCreate, "CREATE");

                DatabaseMetadata metadata =
                    new DatabaseMetadata(connection, dialect);
                String[] sqlUpdate =
                    configuration.generateSchemaUpdateScript(dialect, metadata);
                printScript(printStream, sqlUpdate, "UPDATE");

                dropOrUpdate(
                        localSessionFactoryBean,
                        checkDrop(connection),
                        dropOrUpdate);
                connection.commit();
            } catch(Exception e) {
                logger.warn("An exception ocurred and the database"
                        + "schema didn't finish to execute");
                return;
            }
            
        }
    }

    /**
     * @param localSessionFactoryBean
     * @param message
     * @param doSomething
     */
    private void dropOrUpdate(
            final LocalSessionFactoryBean localSessionFactoryBean,
            final String message,
            final boolean doSomething) {
        
        if(!doSomething || message == null) {
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
    }

    /**
     * @param connection
     * @return a String with the message.
     * 
     * @throws Exception if a problem occurred.
     */
    private String checkDrop(final Connection connection)
            throws Exception {

        String columnName = "message";

        String testMarkerTableName =
            dropSessionFactoriesTablesDefinition.getTestMarkerTableName();

        ResultSet resultSet;
        Statement statement;
        
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
                        + "(" + columnName + " varchar (50));"
                        + "(if you want to recreate the schema)");
            logger.warn("\tinsert into "
                        + testMarkerTableName
                        + " values ('NO, DONT DROP ME');"
                        + "(if you want to update the schema)");
            logger.warn("\tOR insert into "
                        + testMarkerTableName
                        + " values ('YES, DROP ME');"
                        + "(if you want to recreate the schema)");
            throw e;
        }
        
        if (resultSet.next()) {
              return resultSet.getString(columnName);
        } else {
            return null;
        }
    }

    /**
     * @param printStream
     * @param sqlCreate
     * @param name
     */
    private void printScript(
            final PrintStream printStream,
            final String[] sqlCreate,
            final String name) {
        printStream.println();
        printStream.println(
            "--------------------" + name + " SCRIPT-----------------------");
        for(int i = 0; i < sqlCreate.length; i++) {
            printStream.println(sqlCreate[i]);
            printStream.println(sentenceSeparator);
        }
        printStream.println(
                "--------------------------------------------------------");
        printStream.println();

    }
}
