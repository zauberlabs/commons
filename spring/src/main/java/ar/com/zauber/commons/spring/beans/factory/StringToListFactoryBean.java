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
package ar.com.zauber.commons.spring.beans.factory;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.FactoryBean;

import ar.com.zauber.commons.validate.Validate;

/**
 * {@link FactoryBean} that creates a {@link List} of String's by splitting one
 * single String. Ex:
 * 
 * <pre>
 * &lt;bean class="ar.com.terra.stream.util.StringToListFactoryBean"&gt;
 *    &lt;constructor-arg index="0" value="uno,dos,tres" /&gt;
 *    &lt;property name="separatorRegex" value="," /&gt;
 * &lt;/bean&gt;
 * </pre>
 * <p>
 * Creates a list of 3 strings: <code>"uno", "dos" y "tres"</code>
 * <p>
 * Properties:
 * <ul>
 * <li><b>separatorRegex</b>: default is <code>","</code>.
 * </ul>
 * 
 * 
 * @author Francisco J. González Costanzó
 * @since May 3, 2010
 */
public class StringToListFactoryBean implements FactoryBean<List<String>> {

    private final String property;
    private String separatorRegex = ",";

    private List<String> instance;
    
    /** Creates the StringToListFactoryBean. */
    public StringToListFactoryBean(final String property) {
        Validate.notNull(property);
        this.property = property;
    }

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
    public final Class<? extends List<String>> getObjectType() {
        return null;
    }
    
    /** @see org.springframework.beans.factory.FactoryBean#isSingleton() */
    public final boolean isSingleton() {
        return true;
    }
    
    public final void setSeparatorRegex(final String separatorRegex) {
        this.separatorRegex = separatorRegex;
    }

}
