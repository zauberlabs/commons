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
package ar.com.zauber.commons.spring.secrets;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import org.apache.commons.lang.Validate;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.SqlRowSetResultSetExtractor;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;

import ar.com.zauber.commons.dao.exception.NoSuchEntityException;
import ar.com.zauber.commons.secret.*;
import ar.com.zauber.commons.secret.impl.AbstractSecretsMap;


/**
 * Implementation of {@link ar.com.zauber.commons.secret.SecretsMap}
 * that stores the secret in an SQL table. 
 * 
 * @param <T> is command storing.
 * @author Juan F. Codagnone
 * @since Mar 9, 2006
 */
public class SQLSecretMap<T> extends AbstractSecretsMap {
    
    /**
     * Maps Java classes to SQL things
     *  
     * @author Juan F. Codagnone
     * @since Mar 11, 2006
     */
    public interface Mapper<E> {
        /** 
         * @param secret secret a mapear
         * @param cmd comando a mapear
         * @param expirationDate fecha a mapear
         * @return un array de objetos con la representación de
         *         secret, la fecha y el comando listo para 
         *         ser insertado en una tabla sql. 
         */
        Object [] insertMap(String secret, E cmd, Date expirationDate);
        
        /**
         * @return las clases (java.sql.Types) del array de insertMap();
         */
        int []insertClasses();
        
        /**
         *  @return un array de objectos con la representacion de cmd para ser
         *           borrado
         * @param cmd cmd
         */
        Object [] removeMap(E cmd);
        
        /** @return las clases (java.sql.Types) del array de removeMap */
        int []removeClasses();
        /** @return the name of the fields to remove */
        String []removeFields();
        
        /**
         * @param rset resultset a mapear
         * @return el QueryResult equivalente a un Select * from 
         *          <table del secreto>
         * @throws SQLException on error
         * @throws DataAccessException on error
         */
        QueryResult map(ResultSet rset) throws SQLException,
                DataAccessException;
        
        /**
         * @return <code>null</code> si no se soporta borrar por llave. 
         *         sino la columna que contiene la llave.
         * @see SecretsMap#removeByKey(String) 
         */
        String getRemoveByKeyColumn();
    };
    
    /** sql table name */
    private final String table;
    /** the jdbc template */
    private final JdbcTemplate template;
    /** the mapper */
    private final Mapper mapper;
    
    /** extractor */
    private final SqlRowSetResultSetExtractor extractor = 
        new SqlRowSetResultSetExtractor();

    /** query extractor used to map results to a ResultQuery */
    private ResultQueryResultSetExtractor queryExtrator = 
        new ResultQueryResultSetExtractor();
    
    /** unregister cmd sql */
    private final String unregisterCmdSQL;
    
    /**
     * Creates the SQLSecretMap.
     *
     * @param secretGeneartor see AbstractSecretMap
     * @param expirationDatePolicy see AbstractSecretMap
     * @param expirationDateValidator see AbstractSecretMap
     * 
     * @param table sql table name
     * @param template spring jdbc template used to do the queries
     * @param mapper sql mapper
     */
    public SQLSecretMap(final SecretGenerator secretGeneartor, 
            final ExpirationDatePolicy expirationDatePolicy, 
            final ExpirationDateValidator expirationDateValidator,
            final String table, final JdbcTemplate template,
            final Mapper mapper) {
        super(secretGeneartor, expirationDatePolicy, expirationDateValidator);
        
        Validate.notEmpty(table, "table");
        Validate.notNull(template, "template");
        Validate.notNull(mapper, "mapper");
        
        this.table = table;
        this.template = template;
        this.mapper = mapper;
        
        unregisterCmdSQL = getUnregisterCommandSQL();
    }


    /** @see AbstractSecretsMap#put(T, String, Date)  */
    @Override
    protected final void put(final Object cmd, final String secret,
            final Date date) {
        final StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(table);
        sb.append(" VALUES (");
        
        final Object []values = mapper.insertMap(secret, cmd, date);
        boolean first = true;
        for(final Object value : values) {
            if(first) {
                first = false;
            } else {
                sb.append(",");
            }
            sb.append("?");
        }
        sb.append(")");
        
        template.update(sb.toString(), values, mapper.insertClasses());
    }

    /** @see AbstractSecretsMap#get(T) */
    @Override
    protected final QueryResult get(final Object cmd)
            throws NoSuchEntityException {
        final StringBuilder sb = new StringBuilder();
        sb.append("SELECT * from ");
        sb.append(table);
        sb.append(" WHERE ");
        sb.append(getWhereCommandSQL());
        
        final QueryResult result = (QueryResult)template.query(sb.toString(),
                mapper.removeMap(cmd), mapper.removeClasses(),
                queryExtrator);
        
        return result;
    }

    /** @see AbstractSecretsMap#get(java.lang.String) */
    @Override
    protected final QueryResult get(final String secret)
            throws NoSuchEntityException {
        final String sql = "SELECT * FROM " + table + " WHERE secret=? LIMIT 1";
        final QueryResult result = (QueryResult)template.query(sql,
                new Object[] {secret}, new int[] {Types.VARCHAR},
                queryExtrator);
        
        return result;
    }

    /** @see ar.com.zauber.commons.secret.SecretsMap#unregister(T) */
    public final void unregister(final Object cmd) {
        
        template.update(unregisterCmdSQL, mapper.removeMap(cmd), 
                mapper.removeClasses());
    }


    /** @see ar.com.zauber.commons.secret.SecretsMap#cleanup() */
    public final void cleanup() {
        final String sql = "DELETE FROM " + table + " WHERE date <?"; 
        template.update(sql, 
                new Object[] {getExpirationDateValidator().getNowInvalid()},
                new int [] {Types.TIMESTAMP});
    }


    /** @see AbstractSecretsMap#hasSecret(java.lang.String) */
    @Override
    protected final boolean hasSecret(final String secret) {
        final String sql = "SELECT secret FROM " + table 
                + " WHERE secret=?";
        
        final ResultSetWrappingSqlRowSet rset = (ResultSetWrappingSqlRowSet)
                template.query(sql, new Object[] {secret}, 
                new int[] {Types.VARCHAR}, extractor);
        boolean ret = false;
        while(!ret && rset.next()) {
            ret = true;
        }
        
        return ret;
    }
    
    /** @return the sql code to remove a command */
    private String getUnregisterCommandSQL() {
        final StringBuilder sb = new StringBuilder();
        
        sb.append("DELETE FROM ");
        sb.append(table);
        sb.append(" WHERE ");
        sb.append(getWhereCommandSQL());
        
        return sb.toString();
    }

    /** @return the conditions to select a command */
    private String getWhereCommandSQL() {
        final StringBuilder sb = new StringBuilder();
        final String []fields = mapper.removeFields();
        boolean first = true;
        for(final String field : fields) {
            if(first) {
                first = false;
            } else {
                sb.append("and ");
            }
            sb.append(field);
            sb.append("=? ");
        }
        
        return sb.toString();
    }
    
    /**
     * implementation of {@link ResultSetExtractor} that map the results 
     * of the query to a {@link QueryResult}.
     *  
     * @author Juan F. Codagnone
     * @since Mar 11, 2006
     */
    class ResultQueryResultSetExtractor implements ResultSetExtractor {
        /** @see ResultSetExtractor#extractData(java.sql.ResultSet) */
        public Object extractData(final ResultSet rs)
                throws SQLException, DataAccessException {
            return mapper.map(rs);
        }
    }

    /** @see SecretsMap#removeByKey(java.lang.String) */
    public final void removeByKey(final String key) {
        Validate.notNull(key);
        
        final String col = mapper.getRemoveByKeyColumn();
        
        if(col != null) {
            final String sql = "DELETE from " + table + " WHERE " 
                               + col + "= ?";  
            template.update(sql, new Object[]{key});
        }
    }


    /** @see SecretsMap#getByKey(java.lang.String) */
    public final T getByKey(final String key) {
        Validate.notNull(key);
        final String col = mapper.getRemoveByKeyColumn();
        final T ret;
        
        if(col == null) {
            ret = null;
        } else {
            final String sql = "SELECT * FROM " + table + " WHERE " 
                + mapper.getRemoveByKeyColumn() + "=? ";
            final QueryResult<T> result = (QueryResult<T>)template.query(sql,
                    new Object[] {key}, new int[] {Types.VARCHAR},
                    queryExtrator);
            ret = result.getE();
        }
        
        return ret;
    }
}
