/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.repository;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;

import ar.com.zauber.commons.dao.Order;
import ar.com.zauber.commons.dao.Ordering;
import ar.com.zauber.commons.repository.query.Query;
import ar.com.zauber.commons.repository.query.SimpleQuery;
import ar.com.zauber.commons.repository.query.Translator;
import ar.com.zauber.commons.repository.query.visitor.CriteriaFilterVisitor;
import ar.com.zauber.commons.repository.query.visitor.FilterVisitor;


public class CriteriaTranslator implements Translator {

    private SessionFactory sessionFactory;
    private Class clazz;
    private DetachedCriteria criteria;
    /** <code>logger</code>. */
    private Log logger = LogFactory.getLog(getClass());
    
    /**
     * Crea el/la CriteriaTranslator. Como la Query ahora es la que tiene
     * la clase no es necesario que el translator la contenga.
     *
     * @param aClass
     * @param aSessionFactory
     */
    public CriteriaTranslator(final Class aClass, 
            final SessionFactory aSessionFactory) {
        clazz = aClass;
        sessionFactory = aSessionFactory;
    }

    public CriteriaTranslator(final SessionFactory aSessionFactory)  {
        sessionFactory = aSessionFactory;
    }

    public void translate(final Query aQuery)  {
    	if(clazz == null) {
    		clazz = ((SimpleQuery)aQuery).getClazz();
    	}
        SimpleQuery simpleQuery = (SimpleQuery) aQuery;
        
        FilterVisitor filterVisitor = new CriteriaFilterVisitor(clazz, sessionFactory);
        ((SimpleQuery) aQuery).getFilter().accept(filterVisitor);
        criteria = ((CriteriaFilterVisitor)filterVisitor).getCriteria();
        
        addOrder(simpleQuery.getOrdering());

        criteria.setResultTransformer(
                CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        
        logger.info("Criteria: " + criteria);
    }

    
    
    private void addOrder(final Ordering ordering) {
        if(ordering == null) {
            return;
        }
        for (Order order : ordering.getOrders()) {
            if (order.getAscending()) {
                criteria = criteria.addOrder(org.hibernate.criterion.Order.asc(order.getProperty()));
            } else {
                criteria = criteria.addOrder(org.hibernate.criterion.Order.desc(order.getProperty()));
            }            
        }
    }

    /**
     * @return
     */
    public CriteriaSpecification getCriteria() {
        if(criteria == null) {
            criteria = DetachedCriteria.forClass(clazz); 
        }
        return criteria;
    }

}
