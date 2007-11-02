package ar.com.zauber.commons.repository;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;

import ar.com.zauber.commons.repository.query.EqualsQuery;
import ar.com.zauber.commons.repository.query.Query;
import ar.com.zauber.commons.repository.query.SimpleQuery;
import ar.com.zauber.commons.repository.query.Translator;
import ar.com.zauber.commons.repository.query.filters.BaseFilter;
import ar.com.zauber.commons.repository.query.visitor.CriteriaFilterVisitor;
import ar.com.zauber.commons.repository.query.visitor.FilterVisitor;


public class CriteriaTranslator implements Translator
{

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
    public CriteriaTranslator(Class aClass, SessionFactory aSessionFactory)
    {
        clazz = aClass;
        sessionFactory = aSessionFactory;
    }

    public CriteriaTranslator(SessionFactory aSessionFactory)
    {
        sessionFactory = aSessionFactory;
    }

    public void translate(Query aQuery)
    {
    	if(clazz == null) {
    		clazz = ((SimpleQuery)aQuery).getClazz();
    	}
        if(aQuery instanceof EqualsQuery) {
            criteria = DetachedCriteria.forClass(clazz);
            Criterion criterion = Restrictions.eq(((EqualsQuery)aQuery).getFieldName(), ((EqualsQuery)aQuery).getValue());
            criteria.add(criterion);
        } else {
            FilterVisitor filterVisitor = new CriteriaFilterVisitor(clazz, sessionFactory);
            ((SimpleQuery) aQuery).getFilter().accept(filterVisitor);
            criteria = ((CriteriaFilterVisitor)filterVisitor).getCriteria();
        }
        criteria.setResultTransformer(
                CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        logger.info("Criteria: " + criteria);
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
