/**
 * Copyright (c) 2005-2009 Zauber S.A. <http://www.zauber.com.ar/>
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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ar.com.zauber.commons.repository.aggregate.ProjectionAggregateFunctionVisitor;
import ar.com.zauber.commons.repository.query.Query;
import ar.com.zauber.commons.repository.query.SimpleQuery;
import ar.com.zauber.commons.repository.query.aggreate.AggregateFunction;
import ar.com.zauber.commons.repository.query.aggreate.RowCountAggregateFilter;


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
                            Class.forName(aRef.getClassName()), args, types);
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
    public void saveOrUpdate(final Persistible anObject) {
        getHibernateTemplate().saveOrUpdate(anObject);
    }
    
    /**
     * @see Repository#refresh(java.lang.Object)
     */
    public void refresh(final Persistible anObject) {
        getHibernateTemplate().refresh(anObject);
    }


    /** @see Repository#find(Query) */
    public <T> List<T> find(final Query<T> query) {
        CriteriaSpecification criteria = getCriteriaSpecification(null, query, 
                false);
        SimpleQuery simpleQuery = (SimpleQuery) query;
        Criteria aCriteria;
        // TODO: Esto deberí ir en el metodo que hace getCriteriaSpecification
        // pero como no tiene DetachedCriteria posibilidad de setearle valores
        // para paginación hubo que hacerlo así.
        if(simpleQuery.getPaging() != null) {
            int firstResult = (simpleQuery.getPaging().getPageNumber() - 1)
                * simpleQuery.getPaging().getResultsPerPage();
            DetachedCriteria idsCriteria = (DetachedCriteria)criteria;
            idsCriteria.setProjection(Projections.id());
            // Se hace primero el select de los IDs y luego el de los objetos en
            // si ya que de otra manera en aquellos casos que haya objetos
            // que tienen colecciones cuenta los mismos varias veces haciendo
            // que se devuelvan menos resultados.
            List ids = getHibernateTemplate().findByCriteria(
                    idsCriteria,
                    firstResult,
                    simpleQuery.getPaging().getResultsPerPage());
            DetachedCriteria theCriteria =
                (DetachedCriteria) getCriteriaSpecification(null, query, false);
            if(ids.isEmpty()) {
                return new ArrayList<T>();
            }
            theCriteria.add(Restrictions.in("id", ids));
            aCriteria = theCriteria.getExecutableCriteria(this.getSession());
            
        } else {
            aCriteria = ((DetachedCriteria)criteria).getExecutableCriteria(
                    this.getSession());
        }
        aCriteria.setCacheable(query.getCacheable());
        return aCriteria.list();
    }


    /** @see Repository#count(Query) */
    @Deprecated
    public final int count(final Query query) {
        return (Integer) aggregate(query, new RowCountAggregateFilter(), 
                Integer.class);
    }    
    
    /** @see Repository#aggregate(Query, Class) */
    public final <R, T> R aggregate(final Query<T> query, 
            final AggregateFunction aggregateFunction,
            final Class<R> retClazz) {
        Validate.notNull(query);
        Validate.notNull(aggregateFunction);
        Validate.notNull(retClazz);
        
        final DetachedCriteria criteria = 
            (DetachedCriteria) getCriteriaSpecification(null, query, true);
        final ProjectionAggregateFunctionVisitor visitor = 
            new ProjectionAggregateFunctionVisitor();
        aggregateFunction.accept(visitor);
        criteria.setProjection(visitor.getProjection());
        return  (R) getHibernateTemplate().execute(
            new HibernateCallback() {
                public Object doInHibernate(final Session session)
                        throws HibernateException, SQLException {
                    return criteria.getExecutableCriteria(session)
                            .uniqueResult();
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
     * It's used to get a <code>CriteriaSpecification</code> from a query.
     * Finders and counters will use this method.
     * 
     * @param aClass
     *            (Now is always null as the query has it as a member)
     * @param query
     * @param ignoreOrder <code>true</code> if order must be ignored 
     *        (for example on aggregation funcions)
     * @return a <code>CriteriaSpecification</code>
     */
    private CriteriaSpecification getCriteriaSpecification(final Class aClass,
            final Query query, final boolean ignoreOrder) {
        CriteriaTranslator criteriaTranslator = new CriteriaTranslator(aClass,
                getSessionFactory(), ignoreOrder);
        if(query != null) {        	
            query.acceptTranslator(criteriaTranslator);
        }
        CriteriaSpecification criteria = criteriaTranslator.getCriteria();
        return criteria;
    }
    
}
