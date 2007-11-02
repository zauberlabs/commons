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
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
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
    public final Persistible createNew(final Reference aRef) {
        Persistible persistible = null;

        try {
            persistible =
                (Persistible)
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
    public final Persistible createNew(final Reference aRef,
            final Object [] args, final Class [] types) {
        Persistible persistible = null;

        try {
            persistible =
                (Persistible)ConstructorUtils
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
    public Persistible retrieve(final Reference aRef) {
        if(aRef.getId()==null) {
            return createNew(aRef);
        }
        else {
            Persistible persistible;
            persistible =
                (Persistible)this.getHibernateTemplate().get(
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
    public List find(final Query query) {
        CriteriaSpecification criteria = getCriteriaSpecification(null, query);
        return getHibernateTemplate()
            .findByCriteria((DetachedCriteria)criteria);
    }
	
	
	/**
	 * @see Repository#count(Query)
	 */
	public Integer count(Query query) {
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

    
    
    /**
     * @see Repository#save(Persistible, boolean)
     */
    public final Long save(final Persistible anObject) {
    	throw new UnsupportedOperationException("Esta operacion se va a eliminar en las proximas versiones");
    }

    /**
     * @see Repository#update(Persistible, boolean)
     */
    public final void update(final Persistible anObject) {
    	throw new UnsupportedOperationException("Esta operacion se va a eliminar en las proximas versiones");
    }

    /**
     * @see Repository#deleteAll(java.util.Collection)
     */
    public void deleteAll(final Collection lista) {
    	throw new UnsupportedOperationException("Esta operacion se va a eliminar en las proximas versiones");
    }

    /**
     * @see Repository#findAll(java.lang.Class)
     */
    public List findAll(final Class clazz) {
    	throw new UnsupportedOperationException("Esta operacion se va a eliminar en las proximas versiones");
    }
    
    
    /**
     * @see Repository#saveAll(java.util.Collection)
     */
    public void saveAll(final Collection list) {
    	throw new UnsupportedOperationException("Esta operacion se va a eliminar en las proximas versiones");
    }
    
    public List<Persistible> find(final Class aClass, final Query query) {
    	throw new UnsupportedOperationException("Esta operacion se va a eliminar en las proximas versiones");    }

    
    public void updateAll(Collection aCollection) {
    	throw new UnsupportedOperationException("Esta operacion se va a eliminar en las proximas versiones");    }



    /** @see ar.com.zauber.commons.repository.Repository#find(java.lang.Class, java.util.List) */
    public List find(Class class1, List parameters) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Esta operacion se va a eliminar en las proximas versiones");
    }

	public Integer count(Class clazz, Query query) {
		throw new UnsupportedOperationException("Esta operacion se va a eliminar en las proximas versiones");
	}

    
}
