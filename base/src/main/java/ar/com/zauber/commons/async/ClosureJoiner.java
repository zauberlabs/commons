/*
 * Copyright (c) 2011 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.async;

import java.util.Collection;

import ar.com.zauber.commons.dao.Closure;
import ar.com.zauber.commons.validate.Validate;


/**
 * An implementation of {@link Joiner} that delegates the notifications using {@link Closure} 
 * 
 * @author Guido Marucci Blas
 * @since Apr 26, 2011
 * @param <T> The type of object to be joined.
 * @param <F> The type of failed flows.
 */
public final class ClosureJoiner<T, F> extends AbstractJoiner<T, F> {

    private final Closure<T> onSuccessNotificationClosure;
    private final Closure<FailedTask<F>> onFailureNotificationClosure;
    private final Closure<Joiner<T, F>> onAllNotificationsReceivedClosure;

    /**
     * Creates the ClosureJoiner.
     *
     * @param expectedNotifications
     * @param onAllNotificationsReceivedClosure
     * @param onSuccessNotificationClosure
     * @param onFailureNotificationClosure
     */
    public ClosureJoiner(
            final int expectedNotifications,
            final Closure<Joiner<T, F>> onAllNotificationsReceivedClosure,
            final Closure<T> onSuccessNotificationClosure,
            final Closure<FailedTask<F>> onFailureNotificationClosure) {
        this(expectedNotifications, null, null, onAllNotificationsReceivedClosure, 
                onSuccessNotificationClosure, onFailureNotificationClosure);
    }

    /**
     * Creates the ClosureJoiner.
     *
     * @param expectedNotifications
     * @param onAllNotificationsReceivedClosure
     */
    public ClosureJoiner(
            final int expectedNotifications,
            final Closure<Joiner<T, F>> onAllNotificationsReceivedClosure) {
        this(expectedNotifications, null, null, onAllNotificationsReceivedClosure, null, null);
    }
    

    /**
     * Creates the ClosureJoiner.
     *
     * @param expectedNotifications
     * @param onAllNotificationsReceivedClosure
     * @param onSuccessNotificationClosure
     */
    public ClosureJoiner(
            final int expectedNotifications,
            final Closure<Joiner<T, F>> onAllNotificationsReceivedClosure,
            final Closure<T> onSuccessNotificationClosure) {
        this(expectedNotifications, null, null, onAllNotificationsReceivedClosure, 
                onSuccessNotificationClosure, null);
    }
    
    /**
     * Creates the ClosureJoiner.
     *
     * @param expectedNotifications
     * @param succededObjects
     * @param onAllNotificationsReceivedClosure
     * @param failedTasks
     * @param onSuccessNotificationClosure
     */
    public ClosureJoiner(
            final int expectedNotifications, 
            final Collection<T> succededObjects,
            final Closure<Joiner<T, F>> onAllNotificationsReceivedClosure,
            final Collection<FailedTask<F>> failedTasks,
            final Closure<T> onSuccessNotificationClosure) {
        this(expectedNotifications, succededObjects, failedTasks, onAllNotificationsReceivedClosure, 
                onSuccessNotificationClosure, null);
    }

    /**
     * Creates the ClosureJoiner.
     *
     * @param expectedNotifications
     * @param succededObjects
     * @param failedTasks
     * @param onAllNotificationsReceivedClosure
     * @param onSuccessNotificationClosure
     * @param onFailureNotificationClosure
     */
    public ClosureJoiner(
            final int expectedNotifications, 
            final Collection<T> succededObjects,
            final Collection<FailedTask<F>> failedTasks,
            final Closure<Joiner<T, F>> onAllNotificationsReceivedClosure,
            final Closure<T> onSuccessNotificationClosure,
            final Closure<FailedTask<F>> onFailureNotificationClosure) {
        super(expectedNotifications, succededObjects, failedTasks);
        Validate.notNull(onAllNotificationsReceivedClosure);
        
        this.onAllNotificationsReceivedClosure = onAllNotificationsReceivedClosure;
        this.onSuccessNotificationClosure = onSuccessNotificationClosure;
        this.onFailureNotificationClosure = onFailureNotificationClosure;
    }

    /**
     * Creates the ClosureJoiner.
     *
     * @param expectedNotifications
     * @param succededObjects
     * @param onAllNotificationsReceivedClosure
     * @param onSuccessNotificationClosure
     * @param onFailureNotificationClosure
     */
    public ClosureJoiner(
            final int expectedNotifications, 
            final Collection<T> succededObjects,
            final Closure<Joiner<T, F>> onAllNotificationsReceivedClosure,
            final Closure<T> onSuccessNotificationClosure,
            final Closure<FailedTask<F>> onFailureNotificationClosure) {
        this(expectedNotifications, succededObjects, null, onAllNotificationsReceivedClosure, 
                onSuccessNotificationClosure, onFailureNotificationClosure);
    }
    
    /**
     * Creates the ClosureJoiner.
     *
     * @param expectedNotifications
     * @param succededObjects
     * @param onAllNotificationsReceivedClosure
     * @param onSuccessNotificationClosure
     */
    public ClosureJoiner(
            final int expectedNotifications, 
            final Collection<T> succededObjects,
            final Closure<Joiner<T, F>> onAllNotificationsReceivedClosure,
            final Closure<T> onSuccessNotificationClosure) {
        this(expectedNotifications, succededObjects, onAllNotificationsReceivedClosure, 
                onSuccessNotificationClosure, null);
    }


    @Override
    protected void onSuccessNotification(final T object) {
        if (onSuccessNotificationClosure != null) {
            onSuccessNotificationClosure.execute(object);
        }
    }

    @Override
    protected void onFailureNotification(final F failedTask, final Throwable t) {
        if (onFailureNotificationClosure != null) {
            onFailureNotificationClosure.execute(new DefaultFailedTask<F>(failedTask, t));
        }
    }

    @Override
    protected void onAllNotificationsReceived() {
        onAllNotificationsReceivedClosure.execute(this);
    }
}
