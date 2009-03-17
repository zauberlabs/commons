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
package ar.com.zauber.commons.wicket.model;

import org.apache.wicket.model.IModel;

/**
 * Modelo detachable parecido a LoadableDetachableModel. La diferencia es que este
 * soporta setObject(). Para ello las subclases probablemente necesiten sobreescribir
 * afterSetObject(), momento de guardar la información del nuevo objeto seteado
 * necesaria para poder recuperarlo luego del detach.
 * 
 * @param <T>
 *            El tipo del objeto.
 * 
 * @author Julián Luini
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
     * @return True si el objeto está cargado. False si está detacheado.
     */
    public final boolean isAttached() {
        return attached;
    }
    
    /**
     * @return El objeto recuperado después del detach.
     */
    protected abstract T load();

    /**
     * Método para sobreescribir. Útil cuando es necesario guardar un id del objeto
     * que luego servirá para recuperarlo.
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
     * Método para sobreescribir. Es llamado luego de detach().
     */
    protected void onDetach() {
    }

}
