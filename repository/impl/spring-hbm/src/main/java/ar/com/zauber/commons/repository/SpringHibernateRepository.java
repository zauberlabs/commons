/*
 * Copyright (c) 2005 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.EntityMode;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.IdentifierProjection;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.RowCountProjection;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ar.com.zauber.commons.repository.query.Query;
import ar.com.zauber.commons.repository.query.SimpleQuery;
import ar.com.zauber.commons.repository.query.filters.NullFilter;


/**
 * Implementacion del repositorio usando Hibernate y Spring. Repositorio que
 * sirve para acceder a los datos usando el soporte de Spring para Hibernate. Es
 * en la mayoria de los casos un Wrapper de la clase provista por Spring
 * <code>HibernateDaoSupport</code>. TODO Detail
 * 
 * @author Martin A. Marquez
 * @since Jul 19, 2006
 */
public class SpringHibernateRepository extends HibernateDaoSupport implements
        Repository {

    /** <code>logger</code>. */
    private Log logger = LogFactory.getLog(getClass());

    /**
     * @see Repository#createNew(Reference)
     */
    public final <T> T  createNew(final Reference<T> aRef) {
        T persistible = null;

        try {
            persistible =
                (T)
                    Class.forName(aRef.getClassName()).newInstance();
        } catch(Exception e) {
            e.printStackTrace();
            logger.error("Error, la clase" + aRef.getClass()
                    + "no pudo ser instanciada", e);
        }
        return persistible;
    }

    /**
     * @see Repository
     *  #createNew(
     *              Reference,
     *              java.lang.Object[],
     *              java.lang.Class[])
     */
    public final <T> T  createNew(final Reference<T> aRef,
            final Object [] args, final Class [] types) {
        T persistible = null;

        try {
            persistible =
                (T)ConstructorUtils
                    .invokeConstructor(
                            Class.forName(aRef.getClassName()),args, types);
        } catch(Exception e) {
            e.printStackTrace();
            logger.error("Error, la clase" + aRef.getClass() +
                    "no pudo ser instanciada", e);
        }

        return persistible;
    }

    /**
     * @see Repository#delete(Persistible)
     */
    public final void delete(final Persistible anObject) {
        getHibernateTemplate().delete(anObject);
    }


    /**
     * @see Repository#retrieve(Reference)
     */
    public <T> T  retrieve(final Reference<T> aRef) {
        if(aRef.getId()==null) {
            return createNew(aRef);
        }
        else {
            T persistible;
            persistible =
                (T)this.getHibernateTemplate().get(
                    aRef.getClassName(), aRef.getId());
            //this.getHibernateTemplate().evict(persistible);
            return persistible;
        }
        
    }

    /**
     * @see Repository#saveOrUpdate(Object)
     */
    public void saveOrUpdate(Object anObject) {
        getHibernateTemplate().saveOrUpdate(anObject);
    }
    
    /**
     * @see Repository#refresh(java.lang.Object)
     */
    public void refresh(Object anObject) {
        getHibernateTemplate().refresh(anObject);
    }


    /** @see Repository#find(Query) */
    public <T> List<T> find(final Query<T> query) {
        CriteriaSpecification criteria = getCriteriaSpecification(null, query);
        SimpleQuery simpleQuery = (SimpleQuery) query;
        // TODO: Esto deberí ir en el metodo que hace getCriteriaSpecification
        // pero como no tiene DetachedCriteria posibilidad de setearle valores
        // para paginación hubo que hacerlo así.
        if(simpleQuery.getPaging()!=null) {
            int firstResult = (simpleQuery.getPaging().getPageNumber()-1)
                * simpleQuery.getPaging().getResultsPerPage();
            DetachedCriteria idsCriteria = (DetachedCriteria)criteria;
            idsCriteria.setProjection(Projections.id());
            List ids = getHibernateTemplate().findByCriteria(
                    idsCriteria,
                    firstResult,
                    simpleQuery.getPaging().getResultsPerPage());
            DetachedCriteria theCriteria =
                (DetachedCriteria) getCriteriaSpecification(null, query);
            if(ids.isEmpty()) {
                return new ArrayList<T>();
            }
            theCriteria.add(Restrictions.in("id", ids));
            return getHibernateTemplate()
                .findByCriteria(theCriteria);
        } else {
            return getHibernateTemplate()
                .findByCriteria((DetachedCriteria)criteria);
        }
    }
	
	
	/**
	 * @see Repository#count(Query)
	 */
	public int count(Query query) {
	    final DetachedCriteria criteria = (DetachedCriteria) getCriteriaSpecification(null, query);
	    criteria.setProjection(Projections.rowCount());
	    return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return criteria.getExecutableCriteria(session).uniqueResult();
			}
	    	
	    });
	}    
    
    /**
     * @see Repository#getPersistibleClasses()
     */
    public Collection<Class> getPersistibleClasses() {
        ClassMetadata aClassMetadata;
        Collection classMetadatas;
        Collection<Class> classes = new ArrayList<Class>();
        classMetadatas = getSessionFactory().getAllClassMetadata().values();
        
        for(Iterator iter = classMetadatas.iterator(); iter.hasNext();) {
            aClassMetadata = (ClassMetadata)iter.next();
            classes.add(aClassMetadata.getMappedClass(EntityMode.POJO));
        }
        
        return classes;
    }
    
	/**
	 * 
	 * It's used to get a <code>CriteriaSpecification</code> from a query. Finders
	 * and counters will use this method.
	 * 
	 * @param aClass (Now is always null as the query has it as a member)
	 * @param query
	 * @return a <code>CriteriaSpecification</code>
	 */
	private CriteriaSpecification getCriteriaSpecification(final Class aClass,
			final Query query) {
		CriteriaTranslator criteriaTranslator = new CriteriaTranslator(aClass, getSessionFactory());
        if(query != null) {        	
        	query.acceptTranslator(criteriaTranslator);	        	
        }
        CriteriaSpecification criteria = criteriaTranslator.getCriteria();
		return criteria;
	}
    
}
