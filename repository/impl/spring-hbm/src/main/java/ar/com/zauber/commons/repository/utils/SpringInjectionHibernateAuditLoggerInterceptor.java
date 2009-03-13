/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
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
