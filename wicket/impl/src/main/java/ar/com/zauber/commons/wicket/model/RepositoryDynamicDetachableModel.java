/**
 *  Copyright (c) 2008-2009 Clarin Global S.A. -- All rights reserved
 */
package ar.com.zauber.commons.wicket.model;

import org.apache.commons.lang.Validate;
import org.apache.wicket.injection.web.InjectorHolder;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.com.zauber.commons.repository.Persistible;
import ar.com.zauber.commons.repository.Reference;
import ar.com.zauber.commons.repository.Repository;

/**
 * Dynamic Detachable Model para entidades Persistibles.
 * 
 * @param <T>
 * 
 * @author Fernando Resco
 * @since Jan 30, 2009
 */
public class RepositoryDynamicDetachableModel<T extends Persistible>
    extends DynamicDetachableModel<T> {

    /** <code>serialVersionUID</code> */
    private static final long serialVersionUID = 1L;

    @SpringBean(name = "repository")
    private Repository repository;
    
    private Reference<T> reference;
    
   
    /**
     * Creates the RepositoryDynamicDetachableModel.
     *
     */
    public RepositoryDynamicDetachableModel() {
        super();
        InjectorHolder.getInjector().inject(this);
    }
    
    /**
     * Creates the RepositoryDynamicDetachableModel.
     *
     * @param object
     */
    public RepositoryDynamicDetachableModel(final T object) {
        super(object);
        Validate.notNull(object);
        InjectorHolder.getInjector().inject(this);
    }
    
    /**
     * Creates the RepositoryDynamicDetachableModel.
     *
     * @param reference
     */
    public RepositoryDynamicDetachableModel(final Reference<T> reference) {
        super();
        Validate.notNull(reference);
        InjectorHolder.getInjector().inject(this);
        this.reference = reference;
    }
    
    // CHECKSTYLE:DESIGN:OFF    
    /** @see com.clarin.golmix.utils.DynamicDetachableModel#load() */
    @Override
    protected T load() {
        return reference != null ? repository.retrieve(reference) : null; 
    }
    
    /** @see com.clarin.golmix.utils.DynamicDetachableModel#afterSetObject() */
    @SuppressWarnings("unchecked")
    @Override
    protected void afterSetObject() {
        this.reference = getObject() != null
            ? (Reference<T>)getObject().generateReference() : null;
    }
    // CHECKSTYLE:DESIGN::ON
}
