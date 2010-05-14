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
package ar.com.zauber.commons.repository.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import org.apache.commons.lang.Validate;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.Dialect;
import org.hibernate.tool.hbm2ddl.DatabaseMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

/**
 * This class can be used to drop the database Schema.
 *
 * @author Martin A. Marquez
 * @since May 1, 2007
 */
public abstract class AbstractHibernateDropTables
    implements ApplicationContextAware {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ApplicationContext applicationContext;
    private DropSessionFactoriesTablesDefinition
        dropSessionFactoriesTablesDefinition;
    private String sentenceSeparator = "GO";

    /** sets the application context */
    public final void setApplicationContext(
            final ApplicationContext applicationContext) {
        Validate.notNull(applicationContext);
        this.applicationContext = applicationContext;        
    }
    
    /** Crea el/la BaseTest. */
    public AbstractHibernateDropTables() {
        super();
    }

    /** test drop schema */
    public final void testDrop() throws Exception {
        dropDatabase();
    }

    /** drop schema */
    private void dropDatabase() throws Exception {

        logger.trace("Entering runDbInstall");

        Statement statement;
        ResultSet resultSet;
        Connection connection;
        LocalSessionFactoryBean localSessionFactoryBean;
        final String testMarkerTableName = 
            dropSessionFactoriesTablesDefinition.getTestMarkerTableName();
        String message;
        
        for(Iterator<String> iter = dropSessionFactoriesTablesDefinition.
                getLocalSessionFactoryBeanNames(); iter.hasNext();) {
            localSessionFactoryBean =
               (LocalSessionFactoryBean)
                   applicationContext.getBean("&" + iter.next());
       
            connection = ((SessionFactory)localSessionFactoryBean
                    .getObject()).openSession().connection();
            
            try {
                Configuration configuration = 
                    localSessionFactoryBean.getConfiguration();
                final Dialect dialect = Dialect.getDialect(
                        configuration.getProperties());
                final DatabaseMetadata metadata = new DatabaseMetadata(
                        connection, dialect);
                final String[] sqlUpdate = 
                    configuration.generateSchemaUpdateScript(dialect, metadata);
                for(int i = 0; i < sqlUpdate.length; i++) {
                  System.out.println(sqlUpdate[i]);
                  System.out.println(sentenceSeparator);
              }
            } catch(final HibernateException e) {
                logger.warn("An exception ocurred and the database"
                    + "schema didn't finish to execute", e);
                return;
            }
            
            String columnName = "message";
            
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(
                        "select message from " + testMarkerTableName);
            } catch (SQLException e) {
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
