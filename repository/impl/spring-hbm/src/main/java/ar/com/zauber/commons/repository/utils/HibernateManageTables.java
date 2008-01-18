package ar.com.zauber.commons.repository.utils;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class HibernateManageTables
    implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    private DropSessionFactoriesTablesDefinition
        dropSessionFactoriesTablesDefinition;
    private Repository repository;
    private String sentenceSeparator = "GO";
    Log logger = LogFactory.getLog(this.getClass());

    public void setApplicationContext(ApplicationContext applicationContext)
    {
        this.applicationContext = applicationContext;        
    }
    
    /**
     * Crea el/la BaseTest.
     *
     */
    public HibernateManageTables(
            DropSessionFactoriesTablesDefinition definition,
            Repository repository) {
        super();
        this.dropSessionFactoriesTablesDefinition = definition;
        this.repository = repository;
    }

    
    /**
     * 
     * This method is used to dry run or really modify the database
     * schema.
     * 
     * @param dropOrUpdate indicates if the schema will suffer changes.
     */
    public void manage(boolean dropOrUpdate) {
        dropDatabase(dropOrUpdate, System.out);
    }
    
    
    /**
     * Print to the standard output the schema modifications
     */
    public void print() {
        manage(false);
    }
    
    private void dropDatabase(boolean dropOrUpdate, PrintStream printStream) {

        logger.trace("Entering runDbInstall");
        
        for(Iterator iter = dropSessionFactoriesTablesDefinition.getLocalSessionFactoryBeanNames(); iter.hasNext();) {

            LocalSessionFactoryBean localSessionFactoryBean =
               (LocalSessionFactoryBean)
                   applicationContext.getBean("&" + iter.next());
              
            Connection connection = ((SessionFactory)localSessionFactoryBean
                    .getObject()).openSession().connection();
        
            
            try {
                Configuration configuration = localSessionFactoryBean.getConfiguration();
                final Dialect dialect = Dialect.getDialect(configuration.getProperties());
                String[] sqlCreate = configuration.generateSchemaCreationScript(dialect);
                printScript(printStream, sqlCreate);
                
                DatabaseMetadata metadata = new DatabaseMetadata(connection, dialect);
                String[] sqlUpdate = configuration.generateSchemaUpdateScript(dialect, metadata);
                printScript(printStream, sqlUpdate);
                dropOrUpdate(localSessionFactoryBean, checkDrop(connection), dropOrUpdate);
                connection.commit();
            } catch(Exception e) {
                logger.warn("An exception ocurred and the database" +
                    "schema didn't finish to execute");
                return;
            }
            
        }
    }

    private void dropOrUpdate(LocalSessionFactoryBean localSessionFactoryBean,
            String message, boolean doSomething) {
        
        if(!doSomething || message == null) {
            return;
        }

        if("NO, DONT DROP ME".equals(message)) {
            try {
                localSessionFactoryBean.updateDatabaseSchema();
            } catch(Exception e) {
                logger.warn("An exception ocurred and the database" +
                    "schema didn't finish to execute");
                return;
            }
            return;
        } else {
            if("YES, DROP ME".equals(message)) {
                try {
                    localSessionFactoryBean.dropDatabaseSchema();
                    localSessionFactoryBean.createDatabaseSchema();
                } catch(Exception e) {
                    logger.warn("An exception ocurred and the database" +
                        "schema didn't finish to execute");
                    return;
                }
            } else {
                logger.warn("An exception ocurred and the database" +
                    "schema didn't finish to execute");
                return;
            }
        }
    }

    private String checkDrop(Connection connection)
            throws SQLException, Exception {

        String columnName = "message";

        String testMarkerTableName = dropSessionFactoriesTablesDefinition.getTestMarkerTableName();

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
                        + "("+ columnName +" varchar (50));"
                        + "(if you want to recreate the schema)");
            logger.warn("\tinsert into "
                        + testMarkerTableName
                        + " values ('NO, DONT DROP ME');"
                        + "(if you want to update the schema)");
            throw e;
        }
        
        if (resultSet.next()) {
              return resultSet.getString(columnName);
        } else {
            return null;
        }
    }

    private void printScript(PrintStream printStream, String[] sqlCreate) {
        for(int i = 0; i < sqlCreate.length; i++) {
            printStream.println(sqlCreate[i]);
            printStream.println(sentenceSeparator);
        }
    }
}
