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

import java.io.Serializable;
import java.util.Date;

import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.Interceptor;
import org.hibernate.type.Type;
import org.springframework.beans.factory.InitializingBean;

import ar.com.zauber.commons.auth.AuthenticationUserMapper;
import ar.com.zauber.commons.repository.CreationAuditable;
import ar.com.zauber.commons.repository.ModificationAuditable;

//CHECKSTYLE:DESIGN:OFF
/**
 * Interceptor hibernate
 * 
 * 
 * @author Martin Marquez
 */
public class HibernateAuditLoggerInterceptor extends EmptyInterceptor 
                                             implements InitializingBean {
    /** <code>serialVersionUID</code> */
    private static final long serialVersionUID = 9219430516777559965L;
    private AuthenticationUserMapper<String> authenticationUserMapper;

    public void setAuthenticationUserMapper(
            final AuthenticationUserMapper<String> authenticationUserMapper) {
        this.authenticationUserMapper = authenticationUserMapper;
    }

    /** @see InitializingBean#afterPropertiesSet() */
    public final void afterPropertiesSet() throws Exception {
        if(authenticationUserMapper == null) {
            throw new IllegalArgumentException(
                    "falta setear el authentication user mapper");
        }
    }
    
    /**
     * @see Interceptor#onFlushDirty(Object, Serializable, Object[], Object[],
     *      String[], Type[])
     */
    public boolean onFlushDirty(final Object obj, final Serializable id,
            final Object[] newValues, final Object[] oldValues,
            final String[] properties, final Type[] types)
            throws CallbackException {

        if (obj instanceof ModificationAuditable) {
            for (int i = 0; i < properties.length; i++) {
                if (properties[i].equals("modifiedAt")) {
                    newValues[i] = new Date();
                }
                if (properties[i].equals("modifiedBy")) {
                    newValues[i] = authenticationUserMapper.getUser();
                }
            }
        }

        return true;
    }

    /** @see Interceptor#onSave(Object, Serializable, Object[], String[], Type[]) */
    public boolean onSave(final Object obj, final Serializable id,
            final Object[] newValues, final String[] properties,
            final Type[] types) throws CallbackException {

        if (obj instanceof CreationAuditable) {
            for (int i = 0; i < properties.length; i++) {
                if (properties[i].equals("createdAt")) {
                    newValues[i] = new Date();
                }
                if (properties[i].equals("createdBy")) {
                    newValues[i] = authenticationUserMapper.getUser();
                }
                if (obj instanceof ModificationAuditable) {
                    if (properties[i].equals("modifiedAt")) {
                        newValues[i] = new Date();
                    }
                    if (properties[i].equals("modifiedBy")) {
                        newValues[i] = authenticationUserMapper.getUser();
                    }
                }
            }
        }

        return true;
    }
}
