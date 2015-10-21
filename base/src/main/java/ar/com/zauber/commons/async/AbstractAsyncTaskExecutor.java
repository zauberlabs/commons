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
package ar.com.zauber.commons.async;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Esqueleto que ayuda a llevar la cuenta de tareas que se están ejecutando de forma asincronica
 * y a esperar que todas terminen.
 * 
 * @author Juan F. Codagnone
 * @since Mar 11, 2011
 */
public abstract class AbstractAsyncTaskExecutor {
    // para implementar el awaitIdleness
    private final Lock lock = new ReentrantLock();
    private final Condition emptyCondition  = lock.newCondition(); 
    private final AtomicLong activeJobs = new AtomicLong(0);
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Bloquea hasta que el motor de fetching se encuentra idle. 
     * Esto significa que no hay trabajos en las colas internas, y que todos
     * se ejecutaron todas las tareas.
     * 
     * Si una tarea que se está ejecutnado pone a ejecutar otras tareas (recursivamente)
     * entonces  {@link #awaitIdleness()} asegura que se termino de procesar todo.
     * 
     * @throws InterruptedException
     */
    public final void awaitIdleness() throws InterruptedException {
        lock.lock();
        try {
            while(activeJobs.get() != 0) {
                emptyCondition.await();
            }
        } finally {
            lock.unlock();
        }
    }
    /**
     *  Like {@link #awaitIdleness()} but with a timeout.
     *  
     *   @return <tt>true</tt> if this executor terminated and <tt>false</tt> if
     *         the timeout elapsed before termination
     */
    public final boolean awaitIdleness(final long timeout, final TimeUnit unit) 
        throws InterruptedException {
        boolean timedOut = false;
        lock.lock();
        try {
            // no hago esto desde un while ya que un spurious wakeup puede ser
            // interpretado como un wakeup.
            timedOut = emptyCondition.await(timeout, unit);
        } finally {
            lock.unlock();
        }
        
        return timedOut;
    }
    

    /** 
     *  decrementa la cantidad de trabajos activos y notifica a quien 
     *  este esperando por idleness si se llegó a 0.
     */
    protected final void decrementActiveJobs() {
        lock.lock();
        try {
            if(activeJobs.decrementAndGet() == 0) {
                emptyCondition.signalAll();
            }
        } catch(final Throwable t) {
            logger.error("decrementing active jobs. should not happen ", t);
            // nada que relanzar no queremos molestar upstream.
        } finally {
            lock.unlock();
        }
    }
    
    /** incrementa la cantidad de trabajos activos */
    protected final void incrementActiveJobs() {
        lock.lock();
        try {
            activeJobs.incrementAndGet();
        } catch(final Throwable t) {
            logger.error("incrementing  active jobs. should not happen ", t);
            // nada que relanzar no queremos molestar upstream.
        } finally {
            lock.unlock();
        }
    }
}
