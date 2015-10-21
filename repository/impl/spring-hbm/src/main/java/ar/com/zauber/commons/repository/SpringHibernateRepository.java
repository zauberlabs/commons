/**
 * Copyright (c) 2005-2012 Zauber S.A. <http://www.zaubersoftware.com/>
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

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.Validate;
import org.hibernate.Criteria;
import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ar.com.zauber.commons.repository.aggregate.ProjectionAggregateFunctionVisitor;
import ar.com.zauber.commons.repository.query.Query;
import ar.com.zauber.commons.repository.query.SimpleQuery;
import ar.com.zauber.commons.repository.query.aggreate.AggregateFunction;
import ar.com.zauber.commons.repository.query.aggreate.CompositeAggregateFunction;
import ar.com.zauber.commons.repository.query.aggreate.GroupPropertyAggregateFilter;
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
    private Logger logger = LoggerFactory.getLogger(getClass());

    /** @see Repository#createNew(Reference)*/
    @SuppressWarnings("unchecked")
    public final <T extends Persistible> T  createNew(final Reference<T> aRef) {
        T persistible = null;

        try {
            persistible = (T) Class.forName(aRef.getClassName()).newInstance();
        } catch (final InstantiationException e) {
            throw new UnhandledException(e);
        } catch (final IllegalAccessException e) {
            throw new UnhandledException(e);
        } catch (final ClassNotFoundException e) {
            throw new UnhandledException(e);
        }

        return persistible;
    }

    /**
     * @see Repository
     *  #createNew( Reference, java.lang.Object[], java.lang.Class[])
     */
    @SuppressWarnings("unchecked")
    public final <T extends Persistible> T  createNew(final Reference<T> aRef,
            final Object [] args, final Class<?> [] types) {
        T persistible = null;

        try {
            persistible = (T)ConstructorUtils.invokeConstructor(
                    Class.forName(aRef.getClassName()), args, types);
        } catch (final NoSuchMethodException e) {
            logger.error("creating instance of" + aRef.getClass(), e);
        } catch (final IllegalAccessException e) {
            logger.error("creating instance of" + aRef.getClass(), e);
        } catch (final InvocationTargetException e) {
            logger.error("creating instance of" + aRef.getClass(), e);
        } catch (final InstantiationException e) {
            logger.error("creating instance of" + aRef.getClass(), e);
        } catch (final ClassNotFoundException e) {
            logger.error("creating instance of" + aRef.getClass(), e);
        }

        return persistible;
    }

    
    /** @see Repository#delete(Persistible)  */
    public final void delete(final Persistible anObject) {
        getHibernateTemplate().delete(anObject);
    }


    /** @see Repository#retrieve(Reference)  */
    @SuppressWarnings("unchecked")
    public final <T extends Persistible> T  retrieve(final Reference<T> aRef) {
        return (T)getHibernateTemplate().get(aRef.getClassName(), aRef.getId());
    }

    /** @see Repository#saveOrUpdate(Object) */
    public final void saveOrUpdate(final Persistible anObject) {
        getHibernateTemplate().saveOrUpdate(anObject);
    }
    
    /** @see Repository#refresh(Object) */
    public final void refresh(final Persistible anObject) {
        getHibernateTemplate().refresh(anObject);
    }


    /** @see Repository#find(Query) */
    @SuppressWarnings("unchecked")
    public final <T extends Persistible> List<T> find(final Query<T> query) {
        CriteriaSpecification criteria = getCriteriaSpecification(null, query, 
                false);
        final SimpleQuery<T> simpleQuery = (SimpleQuery<T>) query;
        Criteria aCriteria;
        // TODO Esto debería ir en el metodo que hace getCriteriaSpecification
        // pero como no tiene DetachedCriteria posibilidad de setearle valores
        // para paginación hubo que hacerlo así.
        if(simpleQuery.getPaging() != null) {
            int firstResult = (simpleQuery.getPaging().getPageNumber() - 1)
                * simpleQuery.getPaging().getResultsPerPage();
            DetachedCriteria idsDetachedCriteria = (DetachedCriteria)criteria;
            idsDetachedCriteria.setProjection(Projections.id());
            Criteria idsCriteria = idsDetachedCriteria
                .getExecutableCriteria(this.getSession());
            idsCriteria.setCacheable(query.getCacheable());
            idsCriteria.setFirstResult(firstResult);
            idsCriteria.setMaxResults(simpleQuery.getPaging().getResultsPerPage());
            // Se hace primero el select de los IDs y luego el de los objetos en
            // si ya que de otra manera en aquellos casos que haya objetos
            // que tienen colecciones cuenta los mismos varias veces haciendo
            // que se devuelvan menos resultados.
            List<Long> ids = idsCriteria.list();
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
    public final <T extends Persistible> int count(final Query<T> query) {
        return (Integer) aggregate(query, new RowCountAggregateFilter(), 
                Integer.class);
    }    
    
    /** @see Repository#aggregate(Query, Class) */
    @SuppressWarnings("unchecked")
    public final <R, T extends Persistible> R aggregate(final Query<T> query, 
            final AggregateFunction aggregateFunction,
            final Class<R> retClazz) {
        Validate.notNull(query);
        Validate.notNull(aggregateFunction);
        Validate.notNull(retClazz);
        
        boolean ignoreOrder = true;
        
        if(aggregateFunction instanceof CompositeAggregateFunction) {
            List<AggregateFunction> list = ((CompositeAggregateFunction) 
                    aggregateFunction).getFunctions();
            for(AggregateFunction a : list) {
                if(a instanceof GroupPropertyAggregateFilter) {
                    ignoreOrder = false;
                }
            }
        } else if(aggregateFunction instanceof GroupPropertyAggregateFilter) {
            ignoreOrder = false;
        }
        final DetachedCriteria criteria = 
            (DetachedCriteria) getCriteriaSpecification(null, query, ignoreOrder);
        final ProjectionAggregateFunctionVisitor visitor = 
            new ProjectionAggregateFunctionVisitor();
        aggregateFunction.accept(visitor);
        criteria.setProjection(visitor.getProjection());
        return  (R) getHibernateTemplate().execute(
            new HibernateCallback() {
                public Object doInHibernate(final Session session)
                        throws HibernateException, SQLException {
                    criteria.getExecutableCriteria(session).setCacheable(
                            query.getCacheable());
                    
                    final List l = criteria.getExecutableCriteria(session).list();
                    return l.size() == 0 ? null : l.size() == 1 ? l.get(0) : l;
                }
            });
    }
    
    /** @see Repository#getPersistibleClasses() */
    @SuppressWarnings("unchecked")
    public final Collection<Class<?>> getPersistibleClasses() {
        final Collection<Class<?>> classes = new ArrayList<Class<?>>();
        final Collection<ClassMetadata> classMetadatas = 
            getSessionFactory().getAllClassMetadata().values();
        
        for(final ClassMetadata classMetadata : classMetadatas) {
            classes.add(classMetadata.getMappedClass(EntityMode.POJO));
        }
        
        return classes;
    }
    
    /**
     * It's used to get a <code>CriteriaSpecification</code> from a query.
     * Finders and counters will use this method.
     * 
     * @param aClass (Now is always null as the query has it as a member)
     * @param ignoreOrder <code>true</code> if order must be ignored 
     *        (for example on aggregation funcions)
     * @return a <code>CriteriaSpecification</code>
     */
    private <T extends Persistible> CriteriaSpecification getCriteriaSpecification(
            final Class<?> aClass,
            final Query<T> query, final boolean ignoreOrder) {
        CriteriaTranslator criteriaTranslator = new CriteriaTranslator(aClass,
                getSessionFactory(), ignoreOrder);
        if(query != null) {
            query.acceptTranslator(criteriaTranslator);
        }
        return criteriaTranslator.getCriteria();
    }
}
