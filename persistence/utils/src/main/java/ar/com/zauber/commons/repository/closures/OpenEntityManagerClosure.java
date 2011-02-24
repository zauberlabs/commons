/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.repository.closures;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import ar.com.zauber.commons.dao.Closure;

/**
 * TODO Descripcion de la clase. Los comenterios van en castellano.
 * 
 * 
 * @author Juan F. Codagnone
 * @since Jul 26, 2010
 */
public final class OpenEntityManagerClosure<T> implements Closure<T> {

    private final EntityManagerFactory emf;
    private final Closure<T> target;
    private final boolean dryrun;
    private final Logger logger = LoggerFactory.getLogger(OpenSessionClosure.class);
    
    /** Creates the OpenSessionClosure. */
    public OpenEntityManagerClosure(final EntityManagerFactory emf,
            final Closure<T> target) {
        this(emf, target, false);
    }
    
    /** Creates the OpenSessionClosure. */
    public OpenEntityManagerClosure(final EntityManagerFactory emf,
            final Closure<T> target, final boolean dryrun) {

        Validate.notNull(emf);
        Validate.notNull(target);

        this.emf = emf;
        this.target = target;
        this.dryrun = dryrun;
        
        if(dryrun) {
            logger.warn("Corriendo en modo MULTI-SESSION (No apto para producci�n)."
                    + "Cuidado con el starvation de conexiones jdbc.");
        }
    }

    /** @see Closure#execute(Object) */
    public final void execute(final T t) {
        if(dryrun) {
            //permite evitar que se hagan commit() en el ambiente de test
            target.execute(t);
        } else {
            boolean participate = false;
            EntityTransaction transaction = null;
            
            if (TransactionSynchronizationManager.hasResource(emf)) {
                // Do not modify the EntityManager: just set the participate flag.
                participate = true;
            } else {
                try {
                    final EntityManager em = emf.createEntityManager();
                    transaction = em.getTransaction();
                    transaction.begin();
                    
                    TransactionSynchronizationManager.bindResource(emf, 
                            new EntityManagerHolder(em));
                } catch (PersistenceException ex) {
                    throw new DataAccessResourceFailureException(
                            "Could not create JPA EntityManager", ex);
                }
            }
            
            try {
                target.execute(t);
                if(transaction.getRollbackOnly()) {
                	transaction.rollback();
                }else {
                	transaction.commit();
                }
            } catch (final Throwable e) {
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();                
                }
                throw new UnhandledException(e);
            } finally {
                if (!participate) {
                    final EntityManagerHolder emHolder = (EntityManagerHolder)
                            TransactionSynchronizationManager.unbindResource(emf);
                    EntityManagerFactoryUtils.closeEntityManager(
                            emHolder.getEntityManager());
                }
            }
        }
    }
}
