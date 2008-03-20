/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.repository.utils;

import java.io.Serializable;
import java.util.Iterator;

import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.EntityMode;
import org.hibernate.Interceptor;
import org.hibernate.Transaction;
import org.hibernate.type.Type;



/**
 * Mock implementation of the logger.
 *
 * @deprecated usar {@link EmptyInterceptor}.
 * @author Martin A. Marquez
 * @since Jan 30, 2008
 */
public class MockHibernateAuditLogger implements Interceptor {

    //CHECKSTYLE:DESIGN:OFF
    /** @see Interceptor#afterTransactionBegin(Transaction) */
    public void afterTransactionBegin(final Transaction tx) {
        // void
    }

    /** @see Interceptor#afterTransactionCompletion(Transaction) */
    public void afterTransactionCompletion(final Transaction tx) {
        // void
    }

    /** @see Interceptor#beforeTransactionCompletion(Transaction) */
    public void beforeTransactionCompletion(final Transaction tx) {
        // void
    }

    /** @see Interceptor#findDirty(Object, Serializable, Object[], Object[], 
     *                             String[], Type[]) */
    public int[] findDirty(final Object entity, final Serializable id,
            final Object[] currentState, final Object[] previousState,
            final String[] propertyNames, final Type[] types) {
        // nada que hacer
        return null;
    }

    /** @see Interceptor#getEntity(String, Serializable) */
    public Object getEntity(final String entityName, final Serializable id)
            throws CallbackException {
        // nada que hacer
        return null;
    }

    /** @see org.hibernate.Interceptor#getEntityName(java.lang.Object) */
    public String getEntityName(final Object object) throws CallbackException {
        // nada que hacer
        return null;
    }

    /** @see Interceptor#instantiate(String, EntityMode, Serializable) */
    public Object instantiate(final String entityName, final EntityMode entityMode,
            final Serializable id) throws CallbackException {
        // nada que hacer
        return null;
    }

    /** @see Interceptor#isTransient(Object) */
    public Boolean isTransient(final Object entity) {
        // nada que hacer
        return null;
    }

    /** @see Interceptor#onCollectionRecreate(Object, Serializable) */
    public void onCollectionRecreate(final Object collection, final Serializable key)
            throws CallbackException {
        // nada que hacer
    }

    /** @see Interceptor#onCollectionRemove(java.lang.Object, java.io.Serializable) */
    public void onCollectionRemove(final Object collection, final Serializable key)
            throws CallbackException {
        // nada que hacer
    }

    /** @see Interceptor#onCollectionUpdate(Object, Serializable) */
    public void onCollectionUpdate(final Object collection, final Serializable key)
            throws CallbackException {
        // nada que hacer
    }

    /** @see Interceptor#onDelete(Object, Serializable, Object[], String[], 
     *                            Type[]) */
    public void onDelete(final Object entity, final Serializable id, 
            final Object[] state, final String[] propertyNames, 
            final Type[] types) throws CallbackException {
        // TODO Auto-generated method stub

    }

    /** @see Interceptor#onFlushDirty(Object, Serializable, Object[], Object[], 
     *                                String[], Type[]) */
    public boolean onFlushDirty(final Object entity, final Serializable id,
            final Object[] currentState, final Object[] previousState,
            final String[] propertyNames, final Type[] types) 
            throws CallbackException {
        // TODO Auto-generated method stub
        return false;
    }

    /** @see org.hibernate.Interceptor#onLoad(Object, Serializable, Object[], 
     *                                  String[], org.hibernate.type.Type[]) */
    public boolean onLoad(final Object entity, final Serializable id, 
            final Object[] state, final String[] propertyNames, 
            final Type[] types) throws CallbackException {
        // TODO Auto-generated method stub
        return false;
    }

    /** @see org.hibernate.Interceptor#onPrepareStatement(java.lang.String) */
    public String onPrepareStatement(final String sql) {
        // TODO Auto-generated method stub
        return null;
    }

    /** @see Interceptor#onSave(Object, Serializable, Object[], String[], Type[]) */
    public final boolean onSave(final Object entity, final Serializable id, 
            final Object[] state, final String[] propertyNames, 
            final Type[] types) throws CallbackException {
        return false;
    }

    /** @see org.hibernate.Interceptor#postFlush(java.util.Iterator) */
    public void postFlush(final Iterator entities) throws CallbackException {
        // nada que hacer

    }

    /** @see org.hibernate.Interceptor#preFlush(java.util.Iterator) */
    public void preFlush(final Iterator entities) throws CallbackException {
        // nada que hacer
    }
}
