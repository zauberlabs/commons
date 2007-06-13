package ar.com.zauber.commons.repository;

import java.util.HashMap;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;

import ar.com.zauber.commons.repository.query.EqualsQuery;
import ar.com.zauber.commons.repository.query.Query;
import ar.com.zauber.commons.repository.query.Translator;


public class CriteriaTranslator implements Translator
{

    private SessionFactory sessionFactory;
    private Class clazz;
    private Criterion criterion;
    private DetachedCriteria criteria;
    
    public CriteriaTranslator(Class aClass, SessionFactory aSessionFactory)
    {
        
        clazz = aClass;
        sessionFactory = aSessionFactory;
        
        criteria = DetachedCriteria.forClass(clazz);
        criteria.setResultTransformer(
                CriteriaSpecification.DISTINCT_ROOT_ENTITY);
    }

    public void translate(EqualsQuery aQuery)
    {
        criterion = Restrictions.eq(aQuery.getFieldName(), aQuery.getValue());       
    }

    public CriteriaSpecification getCriteria()
    {
        if(criterion != null) {
            criteria.add(criterion);
        }
        return criteria;
    }

    public void translate(Query aQuery)
    {
    }

}
