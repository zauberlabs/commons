/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.repository.utils;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;

import org.hibernate.CallbackException;
import org.hibernate.EntityMode;
import org.hibernate.Interceptor;
import org.hibernate.Transaction;
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
public class HibernateAuditLoggerInterceptor implements Interceptor, 
                                                        InitializingBean {

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
    
    /** @see Interceptor#onLoad(Object, Serializable, Object[], String[], Type[]) */
    public boolean onLoad(final Object arg0, final Serializable arg1,
            final Object[] arg2, final String[] arg3, final Type[] arg4)
            throws CallbackException {
        // TODO Auto-generated method stub
        return false;
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

    /**
     * @see Interceptor#onDelete(Object, Serializable, Object[], String[],
     *      Type[])
     */
    public void onDelete(final Object obj, final Serializable id,
            final Object[] newValues, final String[] properties,
            final Type[] types) throws CallbackException {
    }

    /** @see Interceptor#preFlush(Iterator) */
    public void preFlush(final Iterator arg0) throws CallbackException {
        // TODO Auto-generated method stub
    }

    /** @see Interceptor#postFlush(Iterator) */
    public void postFlush(final Iterator arg0) throws CallbackException {
        // nada que hacer
    }

    /**
     * @see Interceptor#findDirty(Object, Serializable, Object[], Object[],
     *      String[], Type[])
     */
    public int[] findDirty(final Object arg0, final Serializable arg1,
            final Object[] arg2, final Object[] arg3, final String[] arg4,
            final Type[] arg5) {
        // TODO Auto-generated method stub
        return null;
    }

    /** @see Interceptor#isTransient(Object) */
    public Boolean isTransient(final Object arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    /** @see Interceptor#instantiate(String, EntityMode, Serializable) */
    public Object instantiate(final String arg0, final EntityMode arg1,
            final Serializable arg2) throws CallbackException {
        // TODO Auto-generated method stub
        return null;
    }

    /** @see Interceptor#getEntityName(Object) */
    public String getEntityName(final Object arg0) throws CallbackException {
        // nada que hacer
        return null;
    }

    /** @see Interceptor#getEntity(String, Serializable) */
    public Object getEntity(final String arg0, final Serializable arg1)
            throws CallbackException {
        // nada que hacer
        return null;
    }

    /** @see Interceptor#afterTransactionBegin(Transaction) */
    public void afterTransactionBegin(final Transaction arg0) {
        // TODO Auto-generated method stub
    }

    /** @see Interceptor#beforeTransactionCompletion(Transaction) */
    public void beforeTransactionCompletion(final Transaction arg0) {
        // TODO Auto-generated method stub

    }

    /** @see Interceptor#afterTransactionCompletion(Transaction) */
    public void afterTransactionCompletion(final Transaction arg0) {
        // TODO Auto-generated method stub
    }

    /** @see Interceptor#onCollectionRecreate(Object, Serializable) */
    public void onCollectionRecreate(final Object collection,
            final Serializable key) throws CallbackException {
        // TODO Auto-generated method stub

    }

    /** @see Interceptor#onCollectionRemove(Object, Serializable) */
    public void onCollectionRemove(final Object collection,
            final Serializable key) throws CallbackException {
        // nada que hacer
    }

    /** @see Interceptor#onCollectionUpdate(Object, Serializable) */
    public void onCollectionUpdate(final Object collection,
            final Serializable key) throws CallbackException {
        // nada que hacer
    }

    /** @see Interceptor#onPrepareStatement(String) */
    public String onPrepareStatement(final String sql) {
        return sql;
    }
}
