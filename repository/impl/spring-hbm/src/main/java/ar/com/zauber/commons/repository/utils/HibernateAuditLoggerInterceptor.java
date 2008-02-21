package ar.com.zauber.commons.repository.utils;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;

import org.hibernate.CallbackException;
import org.hibernate.EntityMode;
import org.hibernate.Interceptor;
import org.hibernate.Transaction;
import org.hibernate.type.Type;

import ar.com.zauber.commons.auth.AuthenticationUserMapper;
import ar.com.zauber.commons.repository.CreationAuditable;
import ar.com.zauber.commons.repository.ModificationAuditable;


public class HibernateAuditLoggerInterceptor implements Interceptor {

    private AuthenticationUserMapper<String> authenticationUserMapper;
    
    public void setAuthenticationUserMapper(AuthenticationUserMapper<String> authenticationUserMapper) {
        this.authenticationUserMapper = authenticationUserMapper;
    }


    /** @see Interceptor#onLoad(Object, Serializable, Object[], String[], Type[]) */
    public boolean onLoad(Object arg0, Serializable arg1, Object[] arg2, String[] arg3, Type[] arg4)
            throws CallbackException {
        // TODO Auto-generated method stub
        return false;
    }


    /** @see Interceptor#onFlushDirty(Object, Serializable, Object[], Object[], String[], Type[]) */
    public boolean onFlushDirty(Object obj, Serializable id, Object[] newValues, Object[] oldValues,
            String[] properties, Type[] types) throws CallbackException {

        if (obj instanceof ModificationAuditable) 
        {
            for(int i = 0; i < properties.length; i++) {
                if(properties[i].equals("modifiedAt")) {
                    newValues[i] = new Date();
                }
                if(properties[i].equals("modifiedBy")) {
                    newValues[i] = authenticationUserMapper.getUser();
                }
            }
        }
        
        return true;
    }


    /** @see Interceptor#onSave(Object, Serializable, Object[], String[], Type[]) */
    public boolean onSave(Object obj, Serializable id, Object[] newValues, String[] properties, Type[] types)
            throws CallbackException {

        
        if (obj instanceof CreationAuditable) 
        {
            for(int i = 0; i < properties.length; i++) {
                if(properties[i].equals("createdAt")) {
                    newValues[i] = new Date();
                }
                if(properties[i].equals("createdBy")) {
                    newValues[i] = authenticationUserMapper.getUser();
                }
                if (obj instanceof ModificationAuditable) {
                    if(properties[i].equals("modifiedAt")) {
                        newValues[i] = new Date();
                    }
                    if(properties[i].equals("modifiedBy")) {
                        newValues[i] = authenticationUserMapper.getUser();
                    }
                }
            }
        }
        
        return true;
    }

    
    /** @see Interceptor#onDelete(Object, Serializable, Object[], String[], Type[]) */
    public void onDelete(Object obj, Serializable id, Object[] newValues, String[] properties, Type[] types)
            throws CallbackException {
    }

    /** @see Interceptor#preFlush(Iterator) */
    public void preFlush(Iterator arg0) throws CallbackException {
        // TODO Auto-generated method stub
    }


    /** @see Interceptor#postFlush(Iterator) */
    public void postFlush(Iterator arg0) throws CallbackException {
        // TODO Auto-generated method stub
    }

    /** @see Interceptor#findDirty(Object, Serializable, Object[], Object[], String[], Type[]) */
    public int[] findDirty(Object arg0, Serializable arg1, Object[] arg2, Object[] arg3, String[] arg4, Type[] arg5) {
        // TODO Auto-generated method stub
        return null;
    }

    /** @see Interceptor#isTransient(Object) */
    public Boolean isTransient(Object arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    /** @see Interceptor#instantiate(String, EntityMode, Serializable) */
    public Object instantiate(String arg0, EntityMode arg1, Serializable arg2) throws CallbackException {
        // TODO Auto-generated method stub
        return null;
    }

    /** @see Interceptor#getEntityName(Object) */
    public String getEntityName(Object arg0) throws CallbackException {
        // TODO Auto-generated method stub
        return null;
    }

    /** @see Interceptor#getEntity(String, Serializable) */
    public Object getEntity(String arg0, Serializable arg1) throws CallbackException {
        // TODO Auto-generated method stub
        return null;
    }

    /** @see Interceptor#afterTransactionBegin(Transaction) */
    public void afterTransactionBegin(Transaction arg0) {
        // TODO Auto-generated method stub
    }

    /** @see Interceptor#beforeTransactionCompletion(Transaction) */
    public void beforeTransactionCompletion(Transaction arg0) {
        // TODO Auto-generated method stub

    }

    /** @see Interceptor#afterTransactionCompletion(Transaction) */
    public void afterTransactionCompletion(Transaction arg0) {
        // TODO Auto-generated method stub
    }

    /** @see Interceptor#onCollectionRecreate(Object, Serializable) */
    public void onCollectionRecreate(Object collection, Serializable key) throws CallbackException {
        // TODO Auto-generated method stub
        
    }

    /** @see Interceptor#onCollectionRemove(Object, Serializable) */
    public void onCollectionRemove(Object collection, Serializable key) throws CallbackException {
        // TODO Auto-generated method stub
        
    }

    /** @see Interceptor#onCollectionUpdate(Object, Serializable) */
    public void onCollectionUpdate(Object collection, Serializable key) throws CallbackException {
        // TODO Auto-generated method stub
        
    }

    /** @see Interceptor#onPrepareStatement(String) */
    public String onPrepareStatement(String sql) {
        // TODO Auto-generated method stub
        return sql;
    }

}
