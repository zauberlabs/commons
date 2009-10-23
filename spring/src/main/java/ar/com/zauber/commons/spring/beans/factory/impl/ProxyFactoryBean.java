/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
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
