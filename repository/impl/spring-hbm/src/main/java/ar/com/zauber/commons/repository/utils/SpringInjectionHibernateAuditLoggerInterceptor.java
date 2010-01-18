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

import java.io.Serializable;

import org.apache.commons.lang.Validate;
import org.hibernate.type.Type;

/**
 * Hibernate Interceptor that combines {@link SpringInjectionInterceptor} and
 * {@link HibernateAuditLoggerInterceptor} 
 * 
 * 
 * @author Juan F. Codagnone
 * @since Mar 12, 2009
 */
public class SpringInjectionHibernateAuditLoggerInterceptor 
    extends HibernateAuditLoggerInterceptor {
    private static final long serialVersionUID = -2102379795863832857L;
    private final SpringInjectionInterceptor injectionInterceptor;

    /** Creates the SpringInjectionHibernateAuditLoggerInterceptor. */
    public SpringInjectionHibernateAuditLoggerInterceptor(
            final SpringInjectionInterceptor injectionInterceptor) {
        Validate.notNull(injectionInterceptor);
        
        this.injectionInterceptor = injectionInterceptor;
    }
    
    /** @see EmptyInterceptor#onLoad(Object, Serializable, 
     *  Object[], String[], Type[]) */
    @Override
    public final boolean onLoad(final Object entity, 
            final Serializable id, final Object[] state,
            final String[] propertyNames, final Type[] types) {
        return injectionInterceptor.onLoad(entity, id, state, propertyNames, 
                types);
    }
}
