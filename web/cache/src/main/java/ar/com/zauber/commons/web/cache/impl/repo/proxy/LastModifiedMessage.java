/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.web.cache.impl.repo.proxy;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Models a message to communicate LastModified invalidation through
 * the {@link LastModifiedRepositoryProxy}.
 * 
 * @author Mariano A. Cortesi
 * @author Francisco J. González Costanzó
 * @since Apr 29, 2010
 */
public class LastModifiedMessage {
    @JsonProperty
    private Date timestamp;

    @JsonProperty
    private String entityKey;

    /** Solo para Jackson Serialization */
    @SuppressWarnings("unused")
    private LastModifiedMessage() {
    }

    /**
     * Creates the LastModifiedRecord.
     */
    public LastModifiedMessage(final Date timestamp, final String entityKey) {
        this.timestamp = timestamp;
        this.entityKey = entityKey;
    }

    public final Date getTimestamp() {
        return this.timestamp;
    }

    public final String getEntityKey() {
        return this.entityKey;
    }

    /** @see java.lang.Object#equals(java.lang.Object) */
    @Override
    public final boolean equals(final Object obj) {
        if (obj instanceof LastModifiedMessage) {
            LastModifiedMessage other = (LastModifiedMessage) obj;
            return new EqualsBuilder().append(this.entityKey, other.entityKey)
                    .isEquals();
        }
        return false;
    }

    /** @see java.lang.Object#hashCode() */
    @Override
    public final int hashCode() {
        return new HashCodeBuilder().append(this.entityKey).toHashCode();
    }
}
