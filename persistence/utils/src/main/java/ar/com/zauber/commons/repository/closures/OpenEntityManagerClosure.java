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
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import ar.com.zauber.commons.dao.Closure;

/**
 * Similar to {@link OpenEntityManagerInViewFilter} but for a closure.
 * <p>
 * By default it opens a transaction, but that can be overriden by setting {{@link #setOpenTx(boolean)},
 * this will be deprecated in next versions of this class. Making <code>false</code> as the default value.
 * 
 * 
 * @author Juan F. Codagnone
 * @since Jul 26, 2010
 */
public final class OpenEntityManagerClosure<T> implements Closure<T> {

    private static boolean warningPrinted = false;
    
    private final EntityManagerFactory emf;
    private final Closure<T> target;
    private final boolean dryrun;
    private boolean openTx = true;
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

    public final boolean isOpenTx() {
        return openTx;
    }

    public final void setOpenTx(boolean openTx) {
        this.openTx = openTx;
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
                    if (openTx) {
                        if (!warningPrinted) {
                            logger.warn("The OpenEntityManagerClosure has Transactions enabled and is not recommended"
                                    + ". This behaviour will change in the future. Check setOpenTx(), ");
                        }
                        transaction = em.getTransaction();
                        transaction.begin();
                    }
                    
                    TransactionSynchronizationManager.bindResource(emf, 
                            new EntityManagerHolder(em));
                } catch (PersistenceException ex) {
                    throw new DataAccessResourceFailureException(
                            "Could not create JPA EntityManager", ex);
                }
            }
            
            if (transaction != null) {
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
            } else {
                try {
                    target.execute(t);
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
}
