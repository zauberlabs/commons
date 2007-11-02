/*
 * Copyright (c) 2006 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.repository.query.visitor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.BeanToPropertyValueTransformer;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.CollectionType;
import org.hibernate.type.ComponentType;
import org.hibernate.type.EntityType;
import org.hibernate.type.Type;

import ar.com.zauber.commons.dao.Paging;
import ar.com.zauber.commons.repository.query.connectors.AndConnector;
import ar.com.zauber.commons.repository.query.connectors.Connector;
import ar.com.zauber.commons.repository.query.filters.BaseFilter;
import ar.com.zauber.commons.repository.query.filters.BinaryPropertyFilter;
import ar.com.zauber.commons.repository.query.filters.CompositeFilter;
import ar.com.zauber.commons.repository.query.filters.EqualsPropertyFilter;
import ar.com.zauber.commons.repository.query.filters.GreaterThanEqualsPropertyFilter;
import ar.com.zauber.commons.repository.query.filters.GreaterThanPropertyFilter;
import ar.com.zauber.commons.repository.query.filters.InPropertyFilter;
import ar.com.zauber.commons.repository.query.filters.IsNullPropertyFilter;
import ar.com.zauber.commons.repository.query.filters.LessThanEqualsPropertyFilter;
import ar.com.zauber.commons.repository.query.filters.LessThanPropertyFilter;
import ar.com.zauber.commons.repository.query.filters.LikePropertyFilter;
import ar.com.zauber.commons.repository.query.filters.NullFilter;
import ar.com.zauber.commons.repository.query.values.SimpleValue;

/**
 * Visitor concreto para construir un <code>Criteria</code> de Hibernate
 * Criteria Api. Provee las operaciones necesarias para cada componente del
 * composite para construir un <code>Criteria</code> determinado
 *
 * @author Martin Andres Marquez
 * @since Sept 24, 2007
 */
public class CriteriaFilterVisitor implements FilterVisitor {

    /**
     * <code>Criteria</code> de Hibernate resultante.
     */
    private DetachedCriteria criteria;
    
    private DetachedCriteria criteriaForCount;
    /**
     * <code>Criterion</code> que se construye por
     * cada <code>BaseFilterObject</code>.
     */
    private Criterion criterion;

    /**
     * Clase raiz por la que se hace la busqueda.
     */
    private Class clazz;

    /**
     * Alias por cada nivel de anidamiento de un atributo
     * de busqueda del tipo multipropiedad.
     */
    private Map aliases;

    /**
     * <code>SessionFactory</code> para obtener metadata de Hibernate.
     */
    private SessionFactory sessionFactory;

    /**
     * Almacena el tipo de componente de una busqueda anidada.
     */
    private ComponentType componentType;

    private Paging paging;

    /**
     * Crea el/la CriteriaVisitor.
     *
     * @param aClazz clase a buscar.
     * @param aSessionFactory para obtener metadata.
     */
    public CriteriaFilterVisitor(Class aClazz, SessionFactory aSessionFactory) {
        criteriaForCount = DetachedCriteria.forClass(aClazz);
        criteriaForCount.setResultTransformer(
                CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        
        criteria = DetachedCriteria.forClass(aClazz);
        criteria.setResultTransformer(
                CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        clazz = aClazz;
        aliases =  new HashMap();
        sessionFactory = aSessionFactory;
    }

    /**
     * Devuelve un <code>Criteria</code> de Hibernate que
     * representa la consulta.
     *
     * @return <code>Criteria</code> de Hibernate.
     */
    public DetachedCriteria getCriteria() {
        if (criterion != null) {
            criteria.add(criterion);
        }
        return criteria;
    }
    
    /**
     * Devuelve un <code>Criteria</code> de Hibernate que
     * representa la consulta para hacer un count (sin los order by).
     *
     * @return <code>Criteria</code> de Hibernate.
     */
    public DetachedCriteria getCriteriaForCount() {
        if (criterion != null) {
            criteriaForCount.add(criterion);
        }
        return criteriaForCount;
    }

    ///////////////////////////////////////////////
    // Metodos para visitar los componentes base //
    ///////////////////////////////////////////////

//    /*
//     * (non-Javadoc)
//     * @see FilterObjectVisitor#visitEqualsFilterObject(EqualsFilterObject)
//     */
//    public void visitEqualsFilterObject(EqualsFilterObject equalsFilterObject) {
//        String fieldName = getFieldName(equalsFilterObject.getFieldName());
//        criterion = Restrictions.eq(fieldName, equalsFilterObject.getValue());
//        NegateIfNeeded(equalsFilterObject);
//    }
//
//    /*
//     * (non-Javadoc)
//     * @see FilterObjectVisitor#visitOrderByFilterObject(OrderByFilterObject)
//     */
//    public void visitOrderByFilterObject(OrderByFilterObject orderByFilterObject) {
//        String fieldName = getFieldName(orderByFilterObject.getFieldName());
//        if (orderByFilterObject.getOrder()
//                .equals(OrderType.ASC_ORDER)) {
//            if (orderByFilterObject.isNegated()) {
//                criteria = criteria.addOrder(Order.desc(fieldName));
//            } else {
//                criteria = criteria.addOrder(Order.asc(fieldName));
//            }
//        } else {
//            if (orderByFilterObject.isNegated()) {
//                criteria = criteria.addOrder(Order.asc(fieldName));
//            } else {
//                criteria = criteria.addOrder(Order.desc(fieldName));
//            }
//        }
//    }

//    /*
//     * (non-Javadoc)
//     * @see FilterObjectVisitor#visitCollectionSizeEqFilterObject(CollectionSizeEqFilterObject)
//     */
//    public void visitCollectionSizeEqFilterObject(
//            CollectionSizeEqFilterObject collectionSizeEqFilterObject) {
//        String fieldName = getFieldName(
//                collectionSizeEqFilterObject.getFieldName());
//        criterion = Restrictions.sizeEq(fieldName,
//                collectionSizeEqFilterObject.getCollectionSize());
//        NegateIfNeeded(collectionSizeEqFilterObject);
//    }


    /*
     * (non-Javadoc)
     * @see FilterObjectVisitor#visitIsNullFilterObject(IsNullFilterObject)
     */
    public void visitIsNullFilterObject(IsNullPropertyFilter isNullPropertyFilter) {
        String fieldName = getFieldName(
                isNullPropertyFilter.getProperty());
        criterion = Restrictions.isNull(fieldName);
    }

    /////////////////////////////////////////////
    // Metodo que arma un criterion de un      //
    // composite en forma anidada              //
    /////////////////////////////////////////////

    /*
     * (non-Javadoc)
     * @see FilterObjectVisitor#visitCompositeFilterObject(CompositeFilterObject)
     */
    public void visitCompositeFilter(CompositeFilter compositeFilter) {
        // se itera sobre los hijos a aplicar operaciones
        visitCompositeChildren(compositeFilter);
    }

    /////////////////////////////////////////////
    // Metodos privados de ayuda para recorrer //
    // hijos y armar alias                     //
    /////////////////////////////////////////////

    /**
     * Visita hijos de un <code>CompositeFilterObject</code>.
     *
     * @param compositeFilterObject
     */
    private void visitCompositeChildren(
            final CompositeFilter compositeFilter) {
        // se tira excepcion si el composite esta vacio
        if (compositeFilter.getFilters().isEmpty()) {
            throw new IllegalStateException("Composite is empty.");
        }
        // un Criterion de union
        Junction junctionCriterion = setJunctionCriterion(
                compositeFilter.getConnector());
        // proceso de hijos
        for (BaseFilter filter : compositeFilter.getFilters()) {
            // se procesa componente hijo, si es un composite se hace recursivo
            filter.accept(this);
            // hay componentes que no crean un Criterion
            if (criterion != null) {
                junctionCriterion.add(criterion);
                criterion = null;
            } 
        }
        criterion = junctionCriterion;
    }

    /**
     * Crea alias por cada anidamiento.
     *
     * @param pathName asociacion a resolver
     */
    private void multipartQueryResolver(String pathName) {
        int dotIndex = pathName.indexOf(".");
        int lastDotIndex = 0;
        int dotIndexAcum = dotIndex;
        Class pathPartClass = clazz;
        String relativePathName = pathName;

        while (true) {
            String pathNamePrefix = pathName.substring(0, dotIndexAcum);
            String pathPart = relativePathName.substring(0, dotIndex);
            if (!aliases.containsKey(pathNamePrefix)) {
                String alias = "alias" + aliases.size();
                lastDotIndex = pathNamePrefix.lastIndexOf(".");
                if (!isComponentType(pathPartClass, pathPart)) {
                    aliases.put(pathNamePrefix, alias);
                    if (lastDotIndex > 0) { // mas de un nivel de anidamiento
                        String nestedAlias = (String) aliases.get(
                                pathNamePrefix.substring(0,
                                        pathNamePrefix.lastIndexOf(".")));
                        // un alias anidado por vez con un atributo
                        criteria = criteria.createAlias(
                                nestedAlias + "." + pathPart, alias);
                        criteriaForCount = criteriaForCount.createAlias(
                                nestedAlias + "." + pathPart, alias);
                    } else { // si es la primera porcion del path
                        criteria = criteria.createAlias(pathPart, alias);
                        criteriaForCount = criteriaForCount.createAlias(pathPart, alias);
                    }
                } else { // si es un component no se usa alias directamente
                    String prefixComponent = null;
                    if (pathNamePrefix.indexOf(".") > 0) {
                        prefixComponent = pathNamePrefix
                            .substring(0, pathNamePrefix.lastIndexOf("."));
                    } else {
                        prefixComponent = pathNamePrefix;
                    }
                    if (aliases.containsKey(prefixComponent)) {
                        aliases.put(pathNamePrefix, aliases
                                .get(prefixComponent) + "." + pathPart);
                    } else {
                        aliases.put(pathNamePrefix, pathNamePrefix);
                    }
                }
            }
            relativePathName = relativePathName.substring(
                    dotIndex + 1, relativePathName.length());
            // si es el ultimo pathPart no se necesita obtener su clase
            // y se termina proceso del path
            if (relativePathName.indexOf(".") < 0) {
                break;
            }
            // se obtiene la clase del siguiente pathPart a procesar
            pathPartClass = getNextPathPartClass(pathPartClass, pathPart);
            dotIndex = relativePathName.indexOf(".");
            dotIndexAcum += dotIndex + 1;
        }
    }

    /**
     * Obtiene la clase del siguiente pathPart a procesar.
     *
     * @param pathPartClass Clase de un partPath recien procesado
     * @param pathPart siguiente pathPart a procesar
     * @return
     */
    private Class getNextPathPartClass(Class pathPartClass, String pathPart) {
        Type hibernateType      = null;
        Class nextPathPartClass = null;

        // se obtiene el tipo de hibernate del pathPart actual
        ClassMetadata classMetadata = sessionFactory
            .getClassMetadata(pathPartClass);
        if (classMetadata != null) { // pathPartClass no es un componente
            hibernateType = classMetadata.getPropertyType(pathPart);
        } else {
            hibernateType = getHibernateTypeFromComponent(pathPart);
        }
        // si es una coleccion se obtiene el tipo del elemento de la coleccion
        if (hibernateType instanceof CollectionType) {
            CollectionType collectionType = (CollectionType) hibernateType;
            hibernateType = sessionFactory.getCollectionMetadata(
                    collectionType.getRole()).getElementType();
        }
        // se resuelve la clase del atributo de busqueda
        // sobre el que se esta actualmente
        if (hibernateType instanceof EntityType) {
            nextPathPartClass = ((EntityType) hibernateType).getReturnedClass();
        } else if (hibernateType instanceof ComponentType) {
            componentType = (ComponentType) hibernateType;
            nextPathPartClass = componentType.getReturnedClass();
        }
        return nextPathPartClass;
    }

    /**
     * Devuelve el tipo de hibernate de un pathPart contenido en un component.
     *
     * @param pathPart
     * @return
     */
    private Type getHibernateTypeFromComponent(String pathPart) {
        Type[] subTypes = componentType.getSubtypes();
        String[] propNames = componentType.getPropertyNames();
        Type hibernateType = null;
        for (int index = 0; index < propNames.length; index++) {
            if (propNames[index].equals(pathPart)) {
                hibernateType = subTypes[index];
                break;
            }
        }
        return hibernateType;
    }

    /**
     * Devuelve si el atributo con nombre pathPart de la clase pathPartClass
     * es o no un component.
     *
     * @param pathPartClass clase que contiene el atributo con nombre pathPart
     * @param pathPart nombre del atributo
     * @return
     */
    private boolean isComponentType(Class pathPartClass, String pathPart) {
        ClassMetadata classMetadata = sessionFactory.
            getClassMetadata(pathPartClass);

        // no es un component el contenedor de pathPart
        if (classMetadata != null) {
            return (classMetadata.getPropertyType(pathPart).isComponentType());
        } else { // es un component el contenedor de pathPart
            return (getHibernateTypeFromComponent(pathPart).isComponentType());
        }
    }

    /**
     * Indica si fieldName es multipropiedad.
     *
     * @param fieldName fieldName del componente
     * @return verdadero si contiene puntos, falso sino
     */
    private boolean isMultipartQuery(String fieldName) {
        return (fieldName.indexOf(".") > 0);
    }

    /**
     * Crea los alias necesarios por cada <code>BaseFilterObject</code>.
     */
    private void createAliases(String fieldName) {
        multipartQueryResolver(fieldName);
    }

     /**
     * Devuelve el nombre del campo a usar con o sin alias.
     *
     * @param fieldName nombre original
     * @return nombre con o sin alias
     */
    private String getFieldName(String fieldName) {
        if (isMultipartQuery(fieldName)) {
            createAliases(fieldName);
            return getAliasPathName(fieldName);
        }
        return fieldName;
    }

    /**
     * Resuelve el nombre de busqueda con o sin alias.
     *
     * @param fieldName nombre original
     * @return nombre con o sin alias
     */
    private String getAliasPathName(String fieldName) {
        String prefixFieldName = fieldName.substring(0,
                fieldName.lastIndexOf("."));
        if (aliases.containsKey(prefixFieldName)) {
            String aliasName = (String) aliases.get(prefixFieldName);
            String prefix = aliasName;
            String suffix = fieldName.substring(
                    fieldName.lastIndexOf("."), fieldName.length());
            return prefix + suffix;
        } else {
            return fieldName;
        }
    }

    /**
     * Niega la condicion si es necesario.
     *
     * @param baseFilterObject
     */
    private void negateIfNeeded(BaseFilter baseFilterObject) {
        if (baseFilterObject.getNegated()) {
            criterion = Restrictions.not(criterion);
        }
    }

    /**
     * Operacion logica de union de criterions de un composite.
     *
     * @param operation operacion de union logica entre componentes
     * @return un Criterion Junction
     */
    private Junction setJunctionCriterion(Connector operation) {
        if (operation instanceof AndConnector) {
            return Restrictions.conjunction();
        } else {
            return Restrictions.disjunction();
        }
    }

    /**
     * Devuelve la clase por la que se realiza la buscqueda.
     *
     * @return clase de objetos a buscar
     */
    public Class getClazz() {
        return clazz;
    }

    /**
     * Setea la clase por la que se realiza la buscqueda.
     *
     * @param aClazz clase de objetos a buscar
     */
    public void setClazz(Class aClazz) {
        clazz = aClazz;
    }

//    /* (non-Javadoc)
//     * @see FilterObjectVisitor#visitPagingFilterObject(PagingFilterObject)
//     */
//    public void visitPagingFilterObject(PagingFilterObject pagingFilterObject) {
//        this.paging = pagingFilterObject.getPaging();
//        
//    }
//
//    /**
//     * Devuelve el/la paging.
//     *
//     * @return <code>Paging</code> con el/la paging.
//     */
//    public Paging getPaging() {
//        return paging;
//    }

    /* (non-Javadoc)
     * @see FilterObjectVisitor#visitInFilterObject(InFilterObject)
     */
    public void visitInFilterObject(InPropertyFilter inPropertyFilter) {
        String fieldName = getFieldName(inPropertyFilter.getProperty());
        criterion = Restrictions.in(fieldName, CollectionUtils.collect(inPropertyFilter.getValues(), new BeanToPropertyValueTransformer("value")).toArray());
        negateIfNeeded(inPropertyFilter);
    }


    /** @see ar.com.zauber.commons.repository.query.visitor.FilterVisitor#visitBinaryPropertyFilter(ar.com.zauber.commons.repository.query.filters.BinaryPropertyFilter) */
    public void visitBinaryPropertyFilter(
            BinaryPropertyFilter binaryPropertyFilter) {
        Object value = ((SimpleValue)binaryPropertyFilter.getValue()).getValue();
        criterion = createCriterion(binaryPropertyFilter, value);
        negateIfNeeded(binaryPropertyFilter);
    }

    
    /**
     * @param binaryPropertyFilter
     * @param value
     * @return
     */
    private Criterion createCriterion(
            BinaryPropertyFilter binaryPropertyFilter, Object value) {
        String fieldName = getFieldName(
                binaryPropertyFilter.getProperty());
        if (binaryPropertyFilter instanceof EqualsPropertyFilter) {
            return Restrictions.eq(fieldName, value);    
        } else {
            if (binaryPropertyFilter instanceof LessThanPropertyFilter) {
                return Restrictions.lt(fieldName, value);
            } else {
                if(binaryPropertyFilter instanceof LessThanEqualsPropertyFilter) {
                    return Restrictions.le(fieldName, value);
                } else {
                    if(binaryPropertyFilter instanceof GreaterThanPropertyFilter) {
                        return Restrictions.gt(fieldName, value);    
                    } else {
                        if(binaryPropertyFilter instanceof GreaterThanEqualsPropertyFilter) {
                            return Restrictions.ge(fieldName, value);  
                        } else {
                            if(binaryPropertyFilter instanceof LikePropertyFilter) {
                                if(((LikePropertyFilter)binaryPropertyFilter).getCaseInsensitive()) {
                                    return Restrictions.like(fieldName, value);
                                } else {
                                    return Restrictions.ilike(fieldName, value);
                                }
                            } else {
                                throw new IllegalStateException("Unable to process filter" + binaryPropertyFilter);
                            }
                        }
                    }
                }
            }
        }

    }

    /** @see ar.com.zauber.commons.repository.query.visitor.FilterVisitor#visitInPropertyFilter(ar.com.zauber.commons.repository.query.filters.InPropertyFilter) */
    public void visitInPropertyFilter(InPropertyFilter inPropertyFilter) {
        // TODO Auto-generated method stub
        
    }

    /** @see ar.com.zauber.commons.repository.query.visitor.FilterVisitor#visitIsNullPropertyFilter(ar.com.zauber.commons.repository.query.filters.IsNullPropertyFilter) */
    public void visitIsNullPropertyFilter(
            IsNullPropertyFilter isNullPropertyFilter) {
        // TODO Auto-generated method stub
        
    }

    /** @see ar.com.zauber.commons.repository.query.visitor.FilterVisitor#visitNullFilter(ar.com.zauber.commons.repository.query.filters.NullFilter) */
    public void visitNullFilter(NullFilter nullFilter) {
        // TODO: Segun parece no hay que hacer nada
    }
}

