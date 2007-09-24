/*
 * Copyright (c) 2005 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.EntityMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ar.com.zauber.commons.repository.query.Query;


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
     * @see Repository#save(Persistible, boolean)
     */
    public final Long save(final Persistible anObject) {

        HibernateTemplate template = getHibernateTemplate();

        template.save(anObject);

        return getId(anObject);
    }

    /**
     * @see Repository#update(Persistible, boolean)
     */
    public final void update(final Persistible anObject) {
        getHibernateTemplate().merge(anObject);
    }

    /**
     * @see Repository#deleteAll(java.util.Collection)
     */
    public void deleteAll(final Collection lista) {
        getHibernateTemplate().deleteAll(lista);
    }

    /**
     * @see Repository#findAll(java.lang.Class)
     */
    public List findAll(final Class clazz) {

        return getHibernateTemplate().loadAll(clazz);
    }


    /* (non-Javadoc)
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
     * @see Repository#saveAll(java.util.Collection)
     */
    public void saveAll(final Collection list) {
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            save((Persistible)iter.next());

        }

    }
    
//    public List findByCriteria(final CriteriaSpecification criteria) {
//        return getHibernateTemplate().findByCriteria((DetachedCriteria)criteria);
//    }
//
//    public List findByCriteria(final CriteriaSpecification criteria, Paging paging) {
//        HibernateTemplate hibernateTemplate = getHibernateTemplate();
//        List result = getHibernateTemplate().findByCriteria((DetachedCriteria)criteria, 
//                paging.getFirstResult().intValue(), paging.getResultsPerPage().intValue());
//        return result;
//    }
//    
//        
//    /**
//     * @param aClass
//     * @param criteria
//     * @return
//     */
//    public List findByCriteria(final Class aClass, final CriteriaSpecification criteria) {
//        return getHibernateTemplate().findByCriteria((DetachedCriteria)criteria);
//    }
//
//    /* (non-Javadoc)
//     * @see Repository#find(java.lang.Class, java.lang.String, java.lang.Object)
//     */
//    public List find(final Class clazz, final String property, final Object value) {
//
//    	return null;
//    }
//
//    public void evict(final Object anObject) {
//        getHibernateTemplate().evict(anObject);
//
//    }
//
//    public List findByCriteria(final Class aClass, final Object criteria) {
//        throw new UnsupportedOperationException();
//    }
//
//    public List findByCriteria(final Class aClass, final FilterObject filterObject) {
//        CriteriaSpecification criteria = QueryBuilder.createCriteria(filterObject, aClass);
//        return this.findByCriteria(aClass, criteria);
//    }
//
    public List find(final Class aClass, final Query query) {
        CriteriaTranslator criteriaTranslator = new CriteriaTranslator(aClass, getSessionFactory());        
        query.acceptTranslator(criteriaTranslator);
        CriteriaSpecification criteria = criteriaTranslator.getCriteria();
//        CriteriaSpecification criteriaForCount =
//            criteriaTranslator.getCriteriaForCount(); 
//        if (criteriaTranslator.getPaging() != null){
//            if (criteriaVisitor.getPaging().getResultSize().equals(new Integer(0)) ) {
//                ((DetachedCriteria) criteriaForCount).setProjection(Projections.rowCount());
//                Integer rowCount = (Integer) getHibernateTemplate().findByCriteria((DetachedCriteria) criteriaForCount).
//                                            iterator().next();
//                criteriaVisitor.getPaging().setResultSize(rowCount);
//            }    
//            return this.findByCriteria(criteria, criteriaVisitor.getPaging());
//        }
        
        logger.debug("HOLAAAA: " + criteria.toString());
        
        return getHibernateTemplate()
            .findByCriteria((DetachedCriteria)criteria);
    }
    
    public Long getId(Persistible anObject) {
        return anObject.getId();
    }

    public void updateAll(Collection aCollection) {
        for (Iterator iter = aCollection.iterator(); iter.hasNext();) {
            update((Persistible)iter.next());

        }
    }

    public Collection getPersistibleClasses() {
        ClassMetadata aClassMetadata;
        Collection classMetadatas;
        Collection classes = new ArrayList();
        classMetadatas = getSessionFactory().getAllClassMetadata().values();
        
        for(Iterator iter = classMetadatas.iterator(); iter.hasNext();) {
            aClassMetadata = (ClassMetadata)iter.next();
            classes.add(aClassMetadata.getMappedClass(EntityMode.POJO));
        }
        
        return classes;
    }

    public void saveOrUpdate(Object anObject) {
        getHibernateTemplate().saveOrUpdate(anObject);
    }
    
    /**
     * @see Repository#refresh(java.lang.Object)
     */
    public void refresh(Object anObject) {
        getHibernateTemplate().refresh(anObject);
    }

    public List find(Class arg0, List arg1)
    {
        throw new UnsupportedOperationException();
    }
}
