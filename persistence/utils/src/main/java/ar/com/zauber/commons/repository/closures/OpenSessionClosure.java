/**
 * Copyright (c) 2005-2012 Zauber S.A. <http://www.zaubersoftware.com/>
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

import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import ar.com.zauber.commons.dao.Closure;

/**
 * {@link Closure} que permite bindear una sesion al thread donde se corre el
 * mismo y ejecuta el target dentro de dicha sesiï¿½n
 * 
 * @author Pablo Grigolatto
 * @param <T>
 * @since Feb 25, 2010
 */
public class OpenSessionClosure<T> implements Closure<T> {

    private final SessionFactory sessionFactory;
    private final Closure<T> target;
    private final boolean dryrun;
    private final Logger logger = LoggerFactory.getLogger(OpenSessionClosure.class);
    
    /** Creates the OpenSessionClosure. */
    public OpenSessionClosure(final SessionFactory sessionFactory,
            final Closure<T> target, final boolean dryrun) {
        super();

        Validate.notNull(sessionFactory);
        Validate.notNull(target);

        this.sessionFactory = sessionFactory;
        this.target = target;
        this.dryrun = dryrun;
        
        if(dryrun) {
            logger.warn("Corriendo en modo MULTI-SESSION (No apto para producción)."
                    + "Cuidado con el starvation de conexiones jdbc.");
        }
    }

    /** @see Closure#execute(Object) */
    public final void execute(final T t) {
        if(dryrun) {
            //permite evitar que se hagan commit() en el ambiente de test
            target.execute(t);
        } else {
            final Session session = SessionFactoryUtils.getSession(sessionFactory,
                    true);
            TransactionSynchronizationManager.bindResource(sessionFactory,
                    new SessionHolder(session));
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                target.execute(t);
                
                session.flush();
                transaction.commit();
            } catch (final Throwable e) {
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();                
                }
                throw new UnhandledException(e);
            } finally {
                TransactionSynchronizationManager.unbindResource(sessionFactory);
                SessionFactoryUtils.closeSession(session);
            }
        }
    }
}
