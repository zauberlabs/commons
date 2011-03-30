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
package ar.com.zauber.commons.web.cache.impl.repo.hibernate;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Models a db record to store LastModified timestamp for entities represented
 * as a string key; to be used by {@link HibernateLastModifiedRepository}.
 * 
 * @author Mariano A. Cortesi
 * @since May 13, 2010
 */
@Entity
@Table(name = "last_modified")
@NamedQueries({
@NamedQuery(name = "lastModified.deleteForEntity",
    query = "delete LastModifiedRecord lm where lm.entityKey = :entityKey"),
@NamedQuery(name = "lastModified.updateForEntity",
        query = "update LastModifiedRecord lm set lm.timestamp = :timestamp " 
            + " where lm.entityKey = :entityKey",
        hints = @QueryHint(name = "org.hibernate.readOnly", value = "true")),
@NamedQuery(name = "lastModified.orderedTimestampsForEntities",
    query = "select lm.timestamp " 
        + " from LastModifiedRecord lm" 
        + " where lm.entityKey in (:entitiesKey) "
        + " order by lm.timestamp desc",
    hints = @QueryHint(name = "org.hibernate.readOnly", value = "true")),
@NamedQuery(name = "lastModified.timestampForEntity",
    query = "select lm.timestamp from LastModifiedRecord lm " 
        + " where lm.entityKey = :entityKey")
})
public class LastModifiedRecord {

    @Column(nullable = false)
    private Date timestamp;

    @Id
    @Column(name = "entity_key")
    private String entityKey;

    /** For Hibernate */
    @SuppressWarnings("unused")
    private LastModifiedRecord() {
    }

    /**
     * Creates the LastModifiedRecord.
     */
    public LastModifiedRecord(final Date timestamp, final String entityKey) {
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
        if (obj instanceof LastModifiedRecord) {
            LastModifiedRecord other = (LastModifiedRecord) obj;
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
