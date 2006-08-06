/*
 * Copyright (c) 2006 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.spring.beans.factory;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.FactoryBean;


/**
 * Factory bean muuuuuy tonto (tan tonto que huele raro), que
 * retorna siempre un objeto que se le pasa en el constructor.
 * 
 * @author Juan F. Codagnone
 * @since Aug 6, 2006
 */
public class SingletonSimpleFactoryBean implements FactoryBean {
    /** bean a retornar */
    private Object bean;

    /**
     * Creates the SingletonSimpleFactoryBean.
     *
     * @param bean bean a retornar en el {@link #getObject()}
     */
    public SingletonSimpleFactoryBean(final Object bean) {
        Validate.notNull(bean);
        this.bean = bean;
    }
    
    /** @see actoryBean#getObject()     */
    public final Object getObject() throws Exception {
        return bean;
    }

    /** @see FactoryBean#getObjectType() */
    public final Class getObjectType() {
        return bean.getClass();
    }

    /** @see FactoryBean#isSingleton() */
    public final boolean isSingleton() {
        return true;
    }

}
