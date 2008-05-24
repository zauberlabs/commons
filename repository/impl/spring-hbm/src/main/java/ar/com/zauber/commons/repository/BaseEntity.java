/*
 * Copyright (c) 2005-2008 Zauber S.A. <http://www.zauber.com.ar/>
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


/**
 * Clase base que provee la generación de una referencia y el metodo de
 * equals para comparar entidades persistentes.
 * 
 * En esta clase no se define el Id ya que el mismo es responsabilidad de las
 * sub-clases para que tengan la versatilidad de mappearlo de la manera que
 * sea mejor en cada caso.
 * 
 * @author Martin A. Marquez
 * @since Feb 18, 2008
 */
@MappedSuperclass
public abstract class BaseEntity implements Persistible {
    
    
    // \
    
    /** @see Persistible#getReference() */
    public Reference generateReference() {
        return new Reference(this.getClass(), getId());
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
        String[] significantPropertyNames = findSignificantPropertyNames();

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
        String[] significantPropertyNames = findSignificantPropertyNames();

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

    private String[] findSignificantPropertyNames() {
        if(this.getClass().getAnnotation(UniqueConstraint.class) != null) {
            String[] significantPropertyNames = this.getClass()
                .getAnnotation(UniqueConstraint.class).columnNames();
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
            }            
        }
        
        return new String[0];
    }
}
