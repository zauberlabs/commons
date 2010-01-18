/**
 * Copyright (c) 2005-2010 Zauber S.A. <http://www.zauber.com.ar/>
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
package ar.com.zauber.commons.repository.test.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.apache.commons.lang.builder.ToStringBuilder;

import ar.com.zauber.commons.repository.IdentityProperty;
import ar.com.zauber.commons.repository.Persistible;
import ar.com.zauber.commons.repository.Reference;


/**
 * Entidad dummy para testear el repositorio de hibernate
 *
 * @author Martin A. Marquez
 * @since Aug 10, 2006
 */
@Entity
public class PersonaDummy implements Persistible {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer numeroFiscal;

    @IdentityProperty
    private String nombre;

    @Column
    private String descripcion;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "persona_id")
    private Set<DireccionDummy> direcciones;

    /**
     * Crea el/la PersonaDummy.
     *
     */
    public PersonaDummy() {

    }
    /** constructor */
    public PersonaDummy(final int numeroFiscal, final String nombre, 
            final String descripcion, final Set<DireccionDummy> direcciones) {
        this.numeroFiscal = new Integer(numeroFiscal);
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.direcciones = direcciones;
    }

    /** constructor */
    public PersonaDummy(final String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve el/la id.
     *
     * @return <code>Long</code> con el/la id.
     */
    public final Long getId() {
        return id;
    }

    /**
     * Asigna el/la id.
     *
     * @param anId <code>Long</code> con el/la id.
     */
    public final void setId(final Long anId) {
        id = anId;
    }


    /**
     * Devuelve el/la descripcion.
     *
     * @return <code>String</code> con el/la descripcion.
     */
    public final String getDescripcion() {
        return descripcion;
    }


    /**
     * Asigna el/la descripcion.
     *
     * @param descripcion <code>String</code> con el/la descripcion.
     */
    public final void setDescripcion(final String descripcion) {
        this.descripcion = descripcion;
    }


    /**
     * Devuelve el/la direcciones.
     *
     * @return <code>Collection</code> con el/la direcciones.
     */
    public final Set<DireccionDummy> getDirecciones() {
        return direcciones;
    }


    /**
     * Asigna el/la direcciones.
     *
     * @param direcciones <code>Collection</code> con el/la direcciones.
     */
    public final void setDirecciones(final Set<DireccionDummy> direcciones) {
        this.direcciones = direcciones;
    }


    /**
     * Devuelve el/la nombre.
     *
     * @return <code>String</code> con el/la nombre.
     */
    public final String getNombre() {
        return nombre;
    }


    /**
     * Asigna el/la nombre.
     *
     * @param nombre <code>String</code> con el/la nombre.
     */
    public final void setNombre(final String nombre) {
        this.nombre = nombre;
    }


    /**
     * Devuelve el/la numeroFiscal.
     *
     * @return <code>Integer</code> con el/la numeroFiscal.
     */
    public final Integer getNumeroFiscal() {
        return numeroFiscal;
    }


    /**
     * Asigna el/la numeroFiscal.
     *
     * @param numeroFiscal <code>Integer</code> con el/la numeroFiscal.
     */
    public final void setNumeroFiscal(final Integer numeroFiscal) {
        this.numeroFiscal = numeroFiscal;
    }

    /**
     * Obtener una referencia al objeto.
     *
     * @return una <code>Reference</code> que representa al objeto
     *         correspondiente.
     */
    public final Reference<PersonaDummy> generateReference() {
        return new Reference<PersonaDummy>(PersonaDummy.class, id);
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public final String toString() {
        return new ToStringBuilder(this).append("numeroFiscal",
                numeroFiscal).append("id", id).append("nombre",
                nombre).append("direcciones", direcciones).append(
                "reference", this.generateReference()).append("descripcion",
                descripcion).toString();
    }
}
