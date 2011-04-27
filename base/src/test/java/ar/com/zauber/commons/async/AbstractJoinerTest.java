/*
 * Copyright (c) 2011 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.async;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;

import ar.com.zauber.commons.async.Joiner.FailedTask;


/**
 * Test class for the abstract implementation of {@link Joiner} 
 * 
 * @author Guido Marucci Blas
 * @since Apr 27, 2011
 */
public final class AbstractJoinerTest {

    private AtomicInteger successNotificationCounter;
    private AtomicInteger failureNotificationCounter;
    private AtomicInteger allNotificationReceivedCounter;
    
    /**
     * Initializes all the counters for each test case.
     */
    @Before
    public void setUp() {
        this.successNotificationCounter = new AtomicInteger();
        this.failureNotificationCounter = new AtomicInteger();
        this.allNotificationReceivedCounter = new AtomicInteger();
    }
    
    /**
     * Tests the {@link Joiner#notifySuccess(Object)} method with a joiner that is still waiting for
     * notifications.
     */
    @Test
    public void testNotifySuccessOnWaitingJoiner() {
        final Joiner<Integer, Integer> joiner = new MockJoiner(3);
        assertEquals(0, successNotificationCounter.get());
        assertEquals(0, joiner.getSuccessfulNotificationsCount());
        assertEquals(0, joiner.getFailureNotificationsCount());
        assertEquals(3, joiner.getRemainingNotificationsCount());
        joiner.notifySuccess(1);
        assertEquals(2, joiner.getRemainingNotificationsCount());
        assertEquals(1, joiner.getSuccessfulNotificationsCount());
        assertEquals(0, joiner.getFailureNotificationsCount());
        assertEquals(1, successNotificationCounter.get());
    }
    
    /**
     * Tests the {@link Joiner#notifySuccess(Object)} method passing null as the notification.
     * The method should throw an {@link IllegalArgumentException}.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNotifySuccessOnWaitingJoinerWithNullTask() {
        final Joiner<Integer, Integer> joiner = new MockJoiner(3);
        joiner.notifySuccess(null);
    }
    
    /**
     * Tests the {@link Joiner#notifyFailure()} method with a joiner that is still waiting for
     * notifications.
     */
    @Test
    public void testNotifyFailureOnWaitingJoiner() {
        final Joiner<Integer, Integer> joiner = new MockJoiner(3);
        assertEquals(0, failureNotificationCounter.get());
        assertEquals(0, joiner.getFailureNotificationsCount());
        assertEquals(3, joiner.getRemainingNotificationsCount());
        assertEquals(0, joiner.getSuccessfulNotificationsCount());
        joiner.notifyFailure();
        assertEquals(0, joiner.getSuccessfulNotificationsCount());
        assertEquals(2, joiner.getRemainingNotificationsCount());
        assertEquals(1, joiner.getFailureNotificationsCount());
        assertEquals(1, failureNotificationCounter.get());
    }
    
    /**
     * Tests the {@link Joiner#notifyFailure(Throwable)} method with a joiner that is still waiting for
     * notifications.
     */
    @Test
    public void testNotifyFailureOnWaitingJoinerWithThrowable() {
        final Joiner<Integer, Integer> joiner = new MockJoiner(3);
        assertEquals(0, failureNotificationCounter.get());
        assertEquals(0, joiner.getFailureNotificationsCount());
        assertEquals(3, joiner.getRemainingNotificationsCount());
        assertEquals(0, joiner.getSuccessfulNotificationsCount());
        joiner.notifyFailure(new Exception());
        assertEquals(0, joiner.getSuccessfulNotificationsCount());
        assertEquals(2, joiner.getRemainingNotificationsCount());
        assertEquals(1, joiner.getFailureNotificationsCount());
        assertEquals(1, failureNotificationCounter.get());
    }
    
    /**
     * Tests the {@link Joiner#notifyFailure(Object, Throwable)} method with a joiner that is still waiting for
     * notifications.
     */
    @Test
    public void testNotifyFailureOnWaitingJoinerWithThrowableAndTask() {
        final Joiner<Integer, Integer> joiner = new MockJoiner(3);
        assertEquals(0, failureNotificationCounter.get());
        assertEquals(0, joiner.getFailureNotificationsCount());
        assertEquals(3, joiner.getRemainingNotificationsCount());
        assertEquals(0, joiner.getSuccessfulNotificationsCount());
        joiner.notifyFailure(1, new Exception());
        assertEquals(0, joiner.getSuccessfulNotificationsCount());
        assertEquals(2, joiner.getRemainingNotificationsCount());
        assertEquals(1, joiner.getFailureNotificationsCount());
        assertEquals(1, failureNotificationCounter.get());
    }

    /**
     * Tests the {@link Joiner#notifyFailure(Throwable)} method passing null as the throwable.
     * The method should throw an {@link IllegalArgumentException}.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNotifyFailureOnWaitingJoinerWithNullThrowable() {
        final Joiner<Integer, Integer> joiner = new MockJoiner(3);
        joiner.notifyFailure(null);
    }

    /**
     * Tests the {@link Joiner#notifyFailure(Object, Throwable)} method passing null as the throwable and object.
     * The method should throw an {@link IllegalArgumentException}.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNotifyFailureOnWaitingJoinerWithNullThrowableAndTask() {
        final Joiner<Integer, Integer> joiner = new MockJoiner(3);
        joiner.notifyFailure(null, null);
    }
    
    /**
     * Tests the {@link Joiner#notifySuccess(Object)} method with a completed joiner.
     */
    @Test(expected = IllegalStateException.class)
    public void testNotifySuccessOnCompletedJoiner() {
        final Joiner<Integer, Integer> joiner = new MockJoiner(1);
        joiner.notifyFailure();
        joiner.notifySuccess(1);
    }
    
    /**
     * Tests the {@link Joiner#notifyFailure()} method with a completed joiner.
     */
    @Test(expected = IllegalStateException.class)
    public void testNotifyFailureOnCompletedJoiner() {
        final Joiner<Integer, Integer> joiner = new MockJoiner(1);
        joiner.notifyFailure();
        joiner.notifyFailure();

    }
    
    /**
     * Tests the {@link Joiner#notifyFailure(Throwable)} method with a completed joiner.
     */
    @Test(expected = IllegalStateException.class)
    public void testNotifyFailureOnCompletedJoinerWithThrowable() {
        final Joiner<Integer, Integer> joiner = new MockJoiner(1);
        joiner.notifyFailure();
        joiner.notifyFailure(new Exception());
    }
    
    /**
     * Tests the {@link Joiner#notifyFailure(Object, Throwable)} method with a completed joiner.
     */
    @Test(expected = IllegalStateException.class)
    public void testNotifyFailureOnCompletedJoinerWithThrowableAndTask() {
        final Joiner<Integer, Integer> joiner = new MockJoiner(1);
        joiner.notifyFailure();
        joiner.notifyFailure(1, new Exception());
    }
    
    /**
     * Tests the {@link Joiner#getSuccessfulNotificationsCount()} method.
     */
    @Test
    public void testGetSuccessfulNotificationsCount() {
        final Joiner<Integer, Integer> joiner = new MockJoiner(1);
        assertEquals(0, joiner.getSuccessfulNotificationsCount());
        joiner.notifySuccess(1);
        assertEquals(1, joiner.getSuccessfulNotificationsCount());
        assertEquals(1, joiner.getSuccessfulNotificationsCount());
    }
    
    /**
     * Tests the {@link Joiner#getFailureNotificationsCount()} method.
     */
    @Test
    public void testGetFailureNotificationsCount() {
        final Joiner<Integer, Integer> joiner = new MockJoiner(1);
        assertEquals(0, joiner.getFailureNotificationsCount());
        joiner.notifyFailure();
        assertEquals(1, joiner.getFailureNotificationsCount());
        assertEquals(1, joiner.getFailureNotificationsCount());
    }
    
    /**
     * Tests the {@link Joiner#getExpectedNotificationsCount()} method.
     */
    @Test
    public void testGetExpectedNotificationsCount() {
        final Joiner<Integer, Integer> joiner = new MockJoiner(2);
        assertEquals(2, joiner.getExpectedNotificationsCount());
        joiner.notifyFailure();
        assertEquals(2, joiner.getExpectedNotificationsCount());
        joiner.notifySuccess(1);
        assertEquals(2, joiner.getExpectedNotificationsCount());
    }
    
    /**
     * Tests the {@link Joiner#getRemainingNotificationsCount()} method.
     */
    @Test
    public void testGetRemainingNotificationsCount() {
        final Joiner<Integer, Integer> joiner = new MockJoiner(2);
        assertEquals(2, joiner.getRemainingNotificationsCount());
        joiner.notifyFailure();
        assertEquals(1, joiner.getRemainingNotificationsCount());
        joiner.notifySuccess(1);
        assertEquals(0, joiner.getRemainingNotificationsCount());
    }
    
    /**
     * Tests if the {@link AbstractJoiner#onAllNotificationsReceived()} method is called
     * once all the notification have been received.
     */
    @Test
    public void testOnAllNotifcationsReceived() {
        final Joiner<Integer, Integer> joiner = new MockJoiner(2);
        assertEquals(0, allNotificationReceivedCounter.get());
        joiner.notifyFailure();
        assertEquals(0, allNotificationReceivedCounter.get());
        joiner.notifySuccess(1);
        assertEquals(1, allNotificationReceivedCounter.get());
    }
    
    /**
     * Tests the {@link Joiner#hasFailureNotifications()} method.
     */
    @Test
    public void testHasFailureNotifications() {
        final Joiner<Integer, Integer> joiner = new MockJoiner(2);
        assertFalse(joiner.hasFailureNotifications());
        joiner.notifySuccess(0);
        assertFalse(joiner.hasFailureNotifications());
        joiner.notifyFailure();
        assertTrue(joiner.hasFailureNotifications());
    }
    
    /**
     * Tests the {@link Joiner#joinedObjects()} method.
     */
    @Test
    public void testJoinedObjects() {
        final Joiner<Integer, Integer> joiner = new MockJoiner(6);
        joiner.notifySuccess(4);
        joiner.notifySuccess(3);
        joiner.notifyFailure();
        joiner.notifySuccess(1);
        final Collection<Integer> joinedObjects = joiner.joinedObjects();
        assertNotNull(joinedObjects);
        assertEquals(3, joinedObjects.size());
        final Iterator<Integer> iterator = joinedObjects.iterator();
        assertEquals(new Integer(4), iterator.next());
        assertEquals(new Integer(3), iterator.next());
        assertEquals(new Integer(1), iterator.next());
    }
    
    /**
     * Tests the {@link Joiner#failedTasks()} method.
     */
    @Test
    public void testFailedTasks() {
        final Joiner<Integer, Integer> joiner = new MockJoiner(6);
        joiner.notifyFailure();
        joiner.notifyFailure(new Exception("Foo3"));
        joiner.notifySuccess(2);
        joiner.notifyFailure(1, new Exception("Foo1"));
        final Collection<FailedTask<Integer>> failedTasks = joiner.failedTasks();
        assertNotNull(failedTasks);
        assertEquals(2, failedTasks.size());
        final Iterator<FailedTask<Integer>> iterator = failedTasks.iterator();
        
        FailedTask<Integer> failedTask = iterator.next();
        assertNotNull(failedTask);
        assertNull(failedTask.getFailedTask());
        assertNotNull(failedTask.getCause());
        assertEquals(Exception.class, failedTask.getCause().getClass());
        assertEquals("Foo3", failedTask.getCause().getMessage());
        
        failedTask = iterator.next();
        assertNotNull(failedTask);
        assertEquals(new Integer(1), failedTask.getFailedTask());
        assertNotNull(failedTask.getCause());
        assertEquals(Exception.class, failedTask.getCause().getClass());
        assertEquals("Foo1", failedTask.getCause().getMessage());
    }
    
    /**
     * Mock extension of the {@link AbstractJoiner} for testing
     * 
     * @author Guido Marucci Blas
     * @since Apr 27, 2011
     */
    private class MockJoiner extends AbstractJoiner<Integer, Integer> {

        /**
         * Creates the MockJoiner.
         *
         * @param expectedNotifications
         */
        public MockJoiner(final int expectedNotifications) {
            super(expectedNotifications);
        }

        @Override
        protected void onSuccessNotification(final Integer object) {
            successNotificationCounter.incrementAndGet();
        }

        @Override
        protected void onFailureNotification(final Integer failedTask, final Throwable t) {
            failureNotificationCounter.incrementAndGet();
        }

        @Override
        protected void onAllNotificationsReceived() {
            allNotificationReceivedCounter.incrementAndGet();
        }
        
    }
}
