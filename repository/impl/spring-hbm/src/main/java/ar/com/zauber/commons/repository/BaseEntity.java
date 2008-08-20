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
import java.util.HashSet;
import java.util.Set;

import javax.persistence.MappedSuperclass;

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
    
    /** @see Persistible#getReference() */
    public Reference generateReference() {
        return new Reference(this.getClass(), getId());
    }
    
    public boolean naturalEquals(Object obj) {
        Set<Field> fields = this.getIdentityFields();
        if(fields != null && fields.size() > 0) {
            EqualsBuilder equalsBuilder = new EqualsBuilder();
            for (Field field : fields) {
                try {
                    equalsBuilder.append(field.get(this), field.get(obj));
                } catch (Exception e) {
                    // Este código no deberia alcanzarse nunca.
                    e.printStackTrace();
                    return false;
                }
            }
            return equalsBuilder.isEquals();
        }
        return this.equals(obj); 
    }

    /**
     * @param theClass
     * @return
     */
    private Set<Field> getIdentityFields(Class theClass) {
        Set<Field> fields = new HashSet<Field>();
        if(theClass.getAnnotation(IdentityProperties.class) != null) {
            String[] fieldNamesArray =
                theClass.getClass().getAnnotation(IdentityProperties.class).fieldNames();
            for (int i = 0; i < fieldNamesArray.length; i++) {
                try {
                    fields.add((theClass.getField(fieldNamesArray[i])));
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
        } else {
            Field[] fieldsArray = theClass.getFields();
            for (int i = 0; i < fieldsArray.length; i++) {
                if(fieldsArray[i].getAnnotation(IdentityProperty.class)!=null) {
                    fields.add(fieldsArray[i]);
                }
            }
            if(!theClass.getSuperclass().equals(Object.class)) {
                fields.addAll(this.getIdentityFields(theClass.getSuperclass()));
            }
        }
        return fields;
    }
    
    /** @see Object#hashCode() */
    public int naturalHashCode() {
        Set<Field> fields = this.getIdentityFields();
        if(fields != null && fields.size() > 0) {
            HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
            for (Field field : fields) {
                try {
                    hashCodeBuilder.append(field.get(this));
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
            return hashCodeBuilder.hashCode();
        }
        return this.hashCode(); 

    }

    /**
     * @return
     */
    private Set<Field> getIdentityFields() {
        return this.getIdentityFields(this.getClass());
    }
}
