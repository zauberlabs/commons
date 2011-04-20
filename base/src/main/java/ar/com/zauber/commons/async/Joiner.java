/*
 * Copyright (c) 2011 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.async;

import java.util.Collection;

/**
 * Defines the behavior of a joiner.
 * 
 * <p>
 * A joiner could be used when several asynchronous flows of tasks or objects need to be collected into
 * one point.
 * </p>
 * 
 * <p><em>NOTE: Implementations of this interface should be thread safe</em></p>
 * 
 * @param <T> The type of object to be joined.
 * @param <F> The type of failed flows.
 * @author Guido Marucci Blas
 * @since Apr 19, 2011
 */
public interface Joiner<T, F> {

    /**
     * Notifies the {@link Joiner} that <code>object</code>
     * needs to be joined.
     * 
     * <p>
     * This method will increase the amount of successful notifications and will decrease the amount
     * of remaining notifications
     * </p>
     * 
     * @param object The object to be joined. Must not be null.
     * @throws IllegalStateException if the amount of remaining notifications is zero.
     * @throws IllegalArgumentException if object is null.
     */
    void notifySuccess(T object);
    
    /**
     * Notifies the {@link Joiner} that one of the expected object could not be joined.
     * 
     * <p>
     * This method will increase the amount of failure notifications and will decrease the amount
     * of remaining notifications
     * </p>
     * 
     * @throws IllegalStateException if the amount of remaining notifications is zero.
     */
    void notifyFailure();
    
    /**
     * Notifies the {@link Joiner} that one of the expected object could not be joined.
     * 
     * <p>
     * This method will increase the amount of failure notifications and will decrease the amount
     * of remaining notifications
     * </p>
     * 
     * @param t The reason why one of the objects could not joined. Must not be null.
     * @throws IllegalStateException if the amount of remaining notifications is zero.
     * @throws IllegalArgumentException if t is null.
     */
    void notifyFailure(Throwable t);
    
    /**
     * Notifies the {@link Joiner} that one of the expected object could not be joined.
     * 
     * <p>
     * This method will increase the amount of failure notifications and will decrease the amount
     * of remaining notifications
     * </p>
     * 
     * @param failedTask The task or flow that failed to create the object to be joined. Must not be null.
     * @param t The reason why one of the objects could not joined. Must not be null.
     * @throws IllegalStateException if the amount of remaining notifications is zero.
     * @throws IllegalArgumentException if t or failedTask are null.
     */
    void notifyFailure(F failedTask, Throwable t);
    
    /**
     * Obtains the amount of successful notifications.
     * 
     * @return The amount of successful notifications.
     */
    int getSuccessfulNotificationsCount();
    
    /**
     * Obtains the amount of failure notifications.
     * 
     * @return The amount of failure notifications.
     */
    int getFailureNotificationsCount();
    
    /**
     * Obtains the amount of expected notifications.
     * 
     * @return The amount of expected notifications.
     */
    int getExpectedNotificationsCount();
    
    /**
     * Obtains the amount of remaining notifications.
     * 
     * @return The amount of remaining notifications.
     */
    int getRemainingNotificationsCount();
    
    /**
     * @return True if the joiner has received failure notifications. False otherwise.
     */
    boolean hasFailureNotifications();
    
    /**
     * @return True if the joiner is still waiting for notifications, in which case 
     * {@link Joiner#getRemainingNotificationsCount()} will return a non-zero value. False otherwise.
     */
    boolean isWaitingForNotifications();
    
    /**
     * Obtains a {@link Collection} of joined objects.
     * 
     * <p>
     * The returned joined objects are the objects that were joined at the moment of the method call. 
     * The order of the collection is given by the order in which the {@link Joiner} was notified.
     * </p>
     * 
     * @return A {@link Collection} of joined objects.
     */
    Collection<T> joinedObjects();
    
    /**
     * Obtains a {@link Collection} of failed tasks.
     * 
     * <p>
     * The returned failed tasks are the ones that were notified at the moment of the method call. 
     * The order of the collection is given by the order in which the {@link Joiner} was notified.
     * </p>
     * 
     * @return A {@link Collection} of joined objects.
     */
    Collection<FailedTask<F>> failedTasks();
    
    /**
     * A wrapper type for grouping failed tasks with it's cause.
     * 
     * @param <F> The type of failed flows.
     * @author Guido Marucci Blas
     * @since Apr 20, 2011
     */
    interface FailedTask<F> {
        
        /**
         * @return The failed task. May be null.
         */
        F getFailedTask();
        
        /**
         * @return The cause of failure.
         */
        Throwable getCause();
        
        /**
         * @return True if the failed task can be obtained. False otherwise.
         */
        boolean hasFailedTasks();
    }
}
