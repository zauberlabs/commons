/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.dao;

/**
 * Predicate
 * 
 * @author Juan F. Codagnone
 * @since Jul 12, 2008
 * @param <T> type
 */
public interface Predicate<T> {
    /** @return true if the predicate is true */
    boolean evaluate(T value);
}
