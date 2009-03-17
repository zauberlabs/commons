/**
 *  Copyright (c) 2008-2009 Clarin Global S.A. -- All rights reserved
 */
package ar.com.zauber.commons.wicket.model;

import org.apache.wicket.model.IModel;

/**
 * Modelo detachable parecido a LoadableDetachableModel. La diferencia es que este
 * soporta setObject(). Para ello las subclases probablemente necesiten sobreescribir
 * afterSetObject(), momento de guardar la informaci�n del nuevo objeto seteado
 * necesaria para poder recuperarlo luego del detach.
 * 
 * @param <T>
 *            El tipo del objeto.
 * 
 * @author Juli�n Luini
 * @since Jan 29, 2009
 */
public abstract class DynamicDetachableModel<T> implements IModel<T> {

    /** <code>serialVersionUID</code> */
    private static final long serialVersionUID = 1L;

    private transient T object = null;

    private transient boolean attached = false;

    /** Constructor que inicializa el modelo sin objeto. */
    public DynamicDetachableModel() {
    }

    /** Constructor que inicializa el modelo con el objeto cargado. */
    public DynamicDetachableModel(final T object) {
        setObject(object);
    }

    /** @see org.apache.wicket.model.IModel#setObject(java.lang.Object) */
    public final void setObject(final T object) {
        this.object = object;
        attached = true;
        afterSetObject();
    }

    /** @see org.apache.wicket.model.IModel#getObject() */
    public final T getObject() {
        if (!attached) {
            object = load();
            attached = true;
        }
        return object;
    }

    /** @see org.apache.wicket.model.IDetachable#detach() */
    public final void detach() {
        if (attached) {
            object = null;
            attached = false;
            onDetach();
        }
    }

    /**
     * @return True si el objeto est� cargado. False si est� detacheado.
     */
    public final boolean isAttached() {
        return attached;
    }
    
    /**
     * @return El objeto recuperado despu�s del detach.
     */
    protected abstract T load();

    /**
     * M�todo para sobreescribir. �til cuando es necesario guardar un id del objeto
     * que luego servir� para recuperarlo.
     * 
     * Por ejemplo:
     * <code>
     *      protected final void afterSetObject() {
     *          this.id = getObject().getId();
     *      }
     * </code>
     */
    protected void afterSetObject() {
    }

    /**
     * M�todo para sobreescribir. Es llamado luego de detach().
     */
    protected void onDetach() {
    }

}
