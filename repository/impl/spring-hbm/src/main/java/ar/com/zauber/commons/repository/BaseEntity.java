/*
 * Copyright (c) 2005 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.repository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.UniqueConstraint;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@MappedSuperclass
public class BaseEntity implements Persistible {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /** @see Persistible#getId() */
    public final  Long getId() {
        return id;
    }

    /** @see Persistible#getReference() */
    public Reference generateReference() {
        return new Reference(this.getClass(), getId());
    }

    /** @see Persistible#setId(Long)
     */
    public final void setId(final Long anId) {
        id = anId;
    }

    /** @see Object#equals(Object) */
    public boolean equals(final Object obj) {
        if (!this.getClass().equals(obj.getClass())) {
            return false;
        }
        if (this == obj) {
            return true;
        }

        BaseEntity anotherObject = (BaseEntity) obj;

        if (getId() != null && anotherObject.getId() != null) {
            return getId().equals(anotherObject.getId());
        }

        EqualsBuilder equalsBuilder = new EqualsBuilder();
        String[] significantPropertyNames = getSignificantPropertyNames();

        for (int i = 0; i < significantPropertyNames.length; i++) {
            String name = significantPropertyNames[i];
            try {
                equalsBuilder.append(BeanUtils.getProperty(this, name),
                        BeanUtils.getProperty(obj, name));
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        return equalsBuilder.isEquals();
    }

    /** @see Object#hashCode() */
    public int hashCode() {

        if (getId() != null) {
            return getId().hashCode();
        }

        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
        String[] significantPropertyNames = getSignificantPropertyNames();

        for (int i = 0; i < significantPropertyNames.length; i++) {
            String name = significantPropertyNames[i];
            try {
                hashCodeBuilder.append(BeanUtils.getProperty(this, name));
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }

        return hashCodeBuilder.toHashCode();

    }

    private String[] getSignificantPropertyNames() {
        String[] significantPropertyNames = this.getClass().getAnnotation(
                UniqueConstraint.class).columnNames();
        if (significantPropertyNames.length == 0) {
            Field[] fields = this.getClass().getFields();
            List<String> names = new ArrayList<String>();
            for (int i = 0; i < fields.length; i++) {
                Field field = this.getClass().getDeclaredFields()[i];
                // TODO: Evitar fields transient, volatile, etc
                if (field.getName() != "id") {
                    names.add(field.getName());
                }
            }
            return (String[]) names.toArray();
        } else {
            return significantPropertyNames;
        }

    }

}
