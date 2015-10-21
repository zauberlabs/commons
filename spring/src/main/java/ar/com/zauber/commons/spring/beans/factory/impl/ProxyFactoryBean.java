/**
 * Copyright (c) 2005-2015 Zauber S.A. <http://flowics.com/>
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
package ar.com.zauber.commons.spring.beans.factory.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.FactoryBean;

/**
 * Es un factory bean que permite inicializar un proxy  partir de la lista de
 * interfaces que recibe y un invocation handler
 * 
 * 
 * @author Cecilia Hagge
 * @since Oct 23, 2009
 */
public class ProxyFactoryBean implements FactoryBean<Object> {

    private final InvocationHandler ih;
    private final List<Class<?>> interfaces;
       
    /** Construye un ProxyFactoryBean a partir de las interfaces 
     * y el invocation handler */
    public ProxyFactoryBean(final List<Class<?>> interfaces, 
            final InvocationHandler ih) {
        Validate.noNullElements(interfaces);
        Validate.notNull(ih);
        this.ih = ih;
        this.interfaces = interfaces;
    }
    
    /** @see FactoryBean#getObject() */
    public final Object getObject() throws Exception {
        final Class<?>[] classes = new Class[interfaces.size()];
        int i = 0;
        for(Class<?> c : interfaces) {
            classes[i] = c;
            i++;
        }
       return Proxy
        .newProxyInstance(getClass().getClassLoader(),
                (Class<?>[]) classes, ih);
    }

    /** @see FactoryBean#getObjectType() */
    public final Class<?> getObjectType() {
        return null;
    }

    /** @see FactoryBean#isSingleton() */
    public final boolean isSingleton() {
        return false;
    }

}
