/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.tasks.api;

import java.util.Date;

import ar.com.zauber.commons.validate.Validate;

/**
 * ValueObject para representar a un milestone una {@link Task}
 * 
 * @author Mariano A Cortesi
 * @since Dec 14, 2010
 */
public class Milestone {

    private final Date date;
    private final String milestoneName;

    /** Creates the Milestone. */
    public Milestone(final Date date, final String milestoneName) {
        Validate.notNull(date, milestoneName);
        this.date = new Date(date.getTime());
        this.milestoneName = milestoneName;
    }

    public final Date getDate() {
        return date;
    }

    public final String getMilestoneName() {
        return milestoneName;
    }

    @Override
    public final String toString() {
        return "Milestone[" + date + ":" + milestoneName + "]";
    }

}
