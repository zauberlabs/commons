/**
 * Copyright (c) 2005-2015 Zauber S.A. <http://flowics.com/>
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

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.commons.lang.Validate;
import org.hibernate.HibernateException;
import org.hibernate.type.SerializationException;
import org.hibernate.usertype.UserType;


/**
 * Sabe serializar {@link URI}s como columnas de texto
 * 
 * @author Marcelo Turrin
 * @since Jun 22, 2009
 */
public class URIUserType implements UserType {

    private static final int[] SQL_TYPE = {Types.CLOB };

    /**
     * @see UserType#equals(Object, Object)
     * @throws HibernateException
     *             on error
     */
    public final boolean equals(final Object x, final Object y)
            throws HibernateException {
        boolean ret = false;
        if (x == y) {
            ret = true;
        } else if (null == x || null == y) {
            ret = false;
        } else {
            ret = x.equals(y);
        }

        return ret;
    }

    /**
     * @see usertype.UserType#hashCode(Object)
     * @throws HibernateException
     *             on error
     */
    public final int hashCode(final Object x) throws HibernateException {
        return x.hashCode();
    }

    /** @see UserType#isMutable() */
    public final boolean isMutable() {
        return false;
    }

    /** @see UserType#returnedClass() */
    @SuppressWarnings("unchecked")
    public final Class returnedClass() {
        return URI.class;
    }

    /** @see UserType#sqlTypes() */
    public final int[] sqlTypes() {
        return SQL_TYPE;
    }

    /**
     * @see UserType#nullSafeGet(ResultSet, String[], Object)
     * @throws HibernateException
     *             on error
     * @throws SQLException
     *             on error
     */
    public final Object nullSafeGet(final ResultSet rs, final String[] names,
            final Object owner) throws HibernateException, SQLException {
        Validate.notNull(rs);
        Validate.notNull(names);
        Validate.isTrue(names.length > 0);
        final String uriString = rs.getString(names[0]);
        Object ret = null;
        if (uriString != null) {
            try {
                ret = new URI(uriString);
            } catch (final URISyntaxException ex) {
                throw new SerializationException("el texto `" + uriString
                        + "' no es una URI valida", ex);
            }
        }
        return ret;
    }

    /**
     * @see UserType#nullSafeSet(PreparedStatement, Object, int)
     * @throws HibernateException
     *             on error
     * @throws SQLException
     *             on error
     */
    public final void nullSafeSet(final PreparedStatement st,
            final Object value, final int index) throws HibernateException,
            SQLException {
        Validate.notNull(st);
        Validate.isTrue(value == null || value instanceof URI);
        if (value == null) {
            st.setNull(index, Types.VARCHAR);
        } else {
            st.setString(index, ((URI)value).toString());
        }

    }

    /**
     * @see UserType#assemble(Serializable, Object)
     * @throws HibernateException
     *             on error
     */
    public final Object assemble(final Serializable cached, final Object owner)
            throws HibernateException {
        return cached;
    }

    /**
     * @see UserType#deepCopy(java.lang.Object)
     * @throws HibernateException
     *             on error
     */
    public final Object deepCopy(final Object value) throws HibernateException {
        return value;
    }

    /**
     * @see UserType#disassemble(java.lang.Object)
     * @throws HibernateException
     *             on error
     */
    public final Serializable disassemble(final Object value)
            throws HibernateException {
        return (Serializable)value;
    }

    /**
     * @see UserType#replace(Object, Object, Object) *
     * @throws HibernateException
     *             on error
     */
    public final Object replace(final Object original, final Object target,
            final Object owner) throws HibernateException {
        return original;
    }
}
