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

/**
 * 
 */
public class CriteriaTranslator implements Translator {

    private SessionFactory sessionFactory;
    private Class clazz;
    private DetachedCriteria criteria;
    private Log logger = LogFactory.getLog(getClass());
    private boolean debugging = logger.isDebugEnabled();
    
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

    /** constructor */
    public CriteriaTranslator(final SessionFactory aSessionFactory)  {
        sessionFactory = aSessionFactory;
    }

    /** @see Translator#translate(Query) */
    public final void translate(final Query aQuery)  {
        if(clazz == null) {
            clazz = ((SimpleQuery)aQuery).getClazz();
        }
        final SimpleQuery simpleQuery = (SimpleQuery) aQuery;
        
        final FilterVisitor filterVisitor = 
            new CriteriaFilterVisitor(clazz, sessionFactory);
        ((SimpleQuery) aQuery).getFilter().accept(filterVisitor);
        criteria = ((CriteriaFilterVisitor)filterVisitor).getCriteria();
        
        addOrder(simpleQuery.getOrdering());

        criteria.setResultTransformer(
                CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        
        if(debugging) {
            logger.debug("Criteria: " + criteria);    
        }
        
    }

    
    
    /** agrega un criterio de ordenamiento al criteria de hibernate */
    private void addOrder(final Ordering ordering) {
        if(ordering == null) {
            return;
        }
        
        for (final Order order : ordering.getOrders()) {
            if (order.getAscending()) {
                criteria = criteria.addOrder(
                        org.hibernate.criterion.Order.asc(order.getProperty()));
            } else {
                criteria = criteria.addOrder(
                        org.hibernate.criterion.Order.desc(order.getProperty()));
            }            
        }
    }

    /** @return */
    public final CriteriaSpecification getCriteria() {
        if(criteria == null) {
            criteria = DetachedCriteria.forClass(clazz); 
        }
        return criteria;
    }

}
