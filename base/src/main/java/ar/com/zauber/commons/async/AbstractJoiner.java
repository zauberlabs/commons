/*
 * Copyright (c) 2011 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.async;

import java.util.ArrayList;
import java.util.Collection;

import ar.com.zauber.commons.validate.Validate;

/**
 * An abstract skeleton implementation of {@link Joiner} that defines templates method
 * that will be called every time the joiner is notified.
 * 
 * <p>
 * This class is thread safe
 * </p>
 * 
 * @param <T> The type of object to be joined.
 * @param <F> The type of failed flows.
 * @author Guido Marucci Blas
 * @since Apr 20, 2011
 */
public abstract class AbstractJoiner<T, F> implements Joiner<T, F> {

    private final Collection<T> succededObjects;
    private final Collection<FailedTask<F>> failedTasks;
    private final int expectedNotifications;
    private int failureNotifications = 0;
    
    /**
     * Creates the AbstractJoiner.
     *
     * @param expectedNotifications The amount of expected notifications. Must be a positive number.
     * @param succededObjects A collection were the succeeded objects will be added. May be null.
     * @param failedTasks A collection were the failed tasks will be added. May be null.
     * @throws IllegalArgumentException if <code>expectedNotifications</code> is not a positive number.
     */
    public AbstractJoiner(
            final int expectedNotifications, 
            final Collection<T> succededObjects, 
            final Collection<FailedTask<F>> failedTasks) {
        if (expectedNotifications < 1) {
            throw new IllegalArgumentException(String.format(
            "The amount of expected notifications must be a positive number. %d was given", 
            expectedNotifications));
        }
        
        this.expectedNotifications = expectedNotifications;
        this.succededObjects = (succededObjects == null) ? new ArrayList<T>(expectedNotifications) 
                : succededObjects;
        this.failedTasks = (failedTasks == null) ? new ArrayList<FailedTask<F>>(expectedNotifications) 
                : failedTasks;
    }
    
    /**
     * Creates the AbstractJoiner.
     *
     * @param expectedNotifications The amount of expected notifications. Must be a positive number.
     * @param succededObjects A collection were the succeeded objects will be added. May be null.
     * @throws IllegalArgumentException if <code>expectedNotifications</code> is not a positive number.
     */
    public AbstractJoiner(final int expectedNotifications, final Collection<T> succededObjects) {
        this(expectedNotifications, succededObjects, null);
    }
    
    /**
     * Creates the AbstractJoiner.
     *
     * @param expectedNotifications The amount of expected notifications. Must be a positive number.
     * @throws IllegalArgumentException if <code>expectedNotifications</code> is not a positive number.
     */
    public AbstractJoiner(final int expectedNotifications) {
        this(expectedNotifications, null, null);
    }

    @Override
    public final synchronized void notifySuccess(final T object) {
        Validate.notNull(object, "The object to be joined cannot be null");
        Validate.isTrue(getRemainingNotificationsCount() == 0, 
                "All the expected notification have been received.");
        succededObjects.add(object);
        onSuccessNotification(object);
    }

    @Override
    public final synchronized void notifyFailure() {
        Validate.isTrue(getRemainingNotificationsCount() == 0, 
        "All the expected notification have been received.");
        ++failureNotifications;
        onFailureNotification(null, null);
    }

    @Override
    public final synchronized void notifyFailure(final Throwable t) {
        notifyFailure(null, t);
    }

    @Override
    public final synchronized void notifyFailure(final F failedTask, final Throwable t) {
        Validate.notNull(t, "The failed task cannot be null");
        Validate.isTrue(getRemainingNotificationsCount() == 0, 
            "All the expected notification have been received.");
        failedTasks.add(new DefaultFailedTask<F>(failedTask, t));
        ++failureNotifications;
        onFailureNotification(failedTask, t);
    }

    @Override
    public final synchronized int getSuccessfulNotificationsCount() {
        return succededObjects.size();
    }

    @Override
    public final synchronized int getFailureNotificationsCount() {
        return failureNotifications;
    }

    @Override
    public final synchronized int getExpectedNotificationsCount() {
        return expectedNotifications;
    }

    @Override
    public final synchronized int getRemainingNotificationsCount() {
        return expectedNotifications - getSuccessfulNotificationsCount() - getFailureNotificationsCount();
    }

    @Override
    public final synchronized boolean hasFailureNotifications() {
        return getFailureNotificationsCount() != 0;
    }

    @Override
    public final synchronized boolean isWaitingForNotifications() {
        return getRemainingNotificationsCount() != 0;
    }

    @Override
    public final synchronized Collection<T> joinedObjects() {
        return new ArrayList<T>(succededObjects);
    }

    @Override
    public final synchronized Collection<FailedTask<F>> failedTasks() {
        return new ArrayList<FailedTask<F>>(failedTasks);
    }
    
    /**
     * Template method that will be called when a success notification is received.
     * 
     * @param object The joined object. Cannot be null.
     */
    protected abstract void onSuccessNotification(final T object);
    
    /**
     * Template method that will be called when a failure notification is received.
     * 
     * @param failedTask The failed task. May be null.
     * @param t The cause of failure. May be null in which case failedTask is null.
     */
    protected abstract void onFailureNotification(final F failedTask, final Throwable t);
    
    /**
     * Default immutable implementation of {@link FailedTask}
     * 
     * @author Guido Marucci Blas
     * @since Apr 20, 2011
     */
    private static class DefaultFailedTask<F> implements FailedTask<F> {

        private final F failedTask;
        private final Throwable t;
        
        /**
         * Creates the DefaultFailedTask.
         *
         * @param failedTask The failed task. May be null.
         * @param t The cause of the failed task. Cannot be null.
         * @throws IllegalArgumentException if t is null.
         */
        public DefaultFailedTask(final F failedTask, final Throwable t) {
            Validate.notNull(t, "The cause cannot be null");
            this.failedTask = failedTask;
            this.t = t;
        }

        @Override
        public F getFailedTask() {
            return failedTask;
        }

        @Override
        public Throwable getCause() {
            return t;
        }

        @Override
        public boolean hasFailedTasks() {
            return failedTask != null;
        }
        
    }
}
