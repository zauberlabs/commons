/**
 * Copyright (c) 2005-2010 Zauber S.A. <http://www.zauber.com.ar/>
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
package ar.com.zauber.commons.spring.beans.factory;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.FactoryBean;

/**
 * {@link FactoryBean} que arma una lista de strings splitteando otro string. Ej:
 * 
 * <pre>
 * &lt;bean class="ar.com.terra.stream.util.StringToListFactoryBean"&gt;
 *    &lt;constructor-arg index="0" value="uno,dos,tres" /&gt;
 *    &lt;constructor-arg index="1" value="," /&gt;
 * &lt;/bean&gt;
 * </pre>
 * <p>
 * Crea una lista de 3 strings: <code>"uno", "dos" y "tres"</code>
 * 
 * 
 * @author Francisco J. Gonz�lez Costanz�
 * @since May 3, 2010
 */
public class StringToListFactoryBean implements FactoryBean<List<String>> {

    private final String property;
    private final String separatorRegex;

    private List<String> instance;

    /** Creates the StringToListFactoryBean. */
    public StringToListFactoryBean(final String property, final String separator) {
        Validate.notNull(separator);
        Validate.notNull(separator);
        this.separatorRegex = separator;
        this.property = property;
    }

    /** @see org.springframework.beans.factory.FactoryBean#getObject() */
    public final List<String> getObject() throws Exception {
        if (instance == null) {
            instance = getInstance();
        }

        return instance;
    }

    /**
     * @return la instancia
     */
    private List<String> getInstance() {
        ArrayList<String> out = new ArrayList<String>();
        String[] items = property.split(separatorRegex);
        for (String string : items) {
            if (StringUtils.isNotBlank(string)) {
                out.add(string.trim());
            }
        }
        return out;
    }

    /** @see org.springframework.beans.factory.FactoryBean#getObjectType() */
    public Class<? extends List<String>> getObjectType() {
        return null;
    }
    
    /** @see org.springframework.beans.factory.FactoryBean#isSingleton() */
    public final boolean isSingleton() {
        return true;
    }

}