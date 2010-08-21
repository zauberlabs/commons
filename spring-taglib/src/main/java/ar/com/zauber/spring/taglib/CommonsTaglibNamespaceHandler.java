/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.spring.taglib;

import org.springframework.beans.factory.xml.NamespaceHandler;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import ar.com.zauber.commons.dao.closure.ListClosure;
import ar.com.zauber.commons.dao.closure.NullClosure;
import ar.com.zauber.commons.dao.predicate.FalsePredicate;
import ar.com.zauber.commons.dao.predicate.TruePredicate;

/**
 * TODO Descripcion de la clase. Los comenterios van en castellano.
 * 
 * 
 * @author Juan F. Codagnone
 * @since Aug 20, 2010
 */
public class CommonsTaglibNamespaceHandler extends NamespaceHandlerSupport {

    /** @see NamespaceHandler#init() */
    public final void init() {
        registerBeanDefinitionParser("null-closure",
                new DefaultConstructorBeanDefinitionParser(NullClosure.class));
        registerBeanDefinitionParser("compose-closure",
                new ComposeClosureBeanDefinitionParser());
        registerBeanDefinitionParser("list-closure",
                new DefaultConstructorBeanDefinitionParser(ListClosure.class));
        registerBeanDefinitionParser("error-logger-wrapper-closure",
                new ErrorLoggerWrapperClosureBeanDefinitionParser());
        registerBeanDefinitionParser("executor-closure",
                new ExecutorClosureSingleBeanDefinitionParser());
        registerBeanDefinitionParser("mutable-closure",
                new MutableClosureBeanDefinitionParser());
        registerBeanDefinitionParser("filtered-closure", 
                new FilteredClosureBeanDefinitionParser());
        
        
        registerBeanDefinitionParser("true-predicate",
                new DefaultConstructorBeanDefinitionParser(TruePredicate.class));
        registerBeanDefinitionParser("false-predicate",
                new DefaultConstructorBeanDefinitionParser(FalsePredicate.class));
        registerBeanDefinitionParser("and-predicate",
                new AndPredicateBeanDefinitionParser());
        registerBeanDefinitionParser("not-predicate",
                new NotPredicateBeanDefinitionParser());
        
        registerBeanDefinitionParser("throws-max-predicate", 
                new ThrowableMaxAmountPredicateBeanDefinitionParser());
        
    }
}
