/*
 * Copyright (c) 2005-2008 Zauber S.A. <http://www.zauber.com.ar/>
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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang.builder.ToStringBuilder;

import ar.com.zauber.commons.repository.Persistible;
import ar.com.zauber.commons.repository.Reference;


/**
 * Direccion dumy
 *
 * @author Martin A. Marquez
 * @since Aug 11, 2006
 */
@Entity
public class DireccionDummy implements Persistible {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String direccion;

    private String numero;

    private String codpostal;

    /**
     * Devuelve el/la codpostal.
     *
     * @return <code>String</code> con el/la codpostal.
     */
    public final String getCodpostal() {
        return codpostal;
    }


    /**
     * Asigna el/la codpostal.
     *
     * @param codpostal <code>String</code> con el/la codpostal.
     */
    public final void setCodpostal(final String codpostal) {
        this.codpostal = codpostal;
    }


    /**
     * Devuelve el/la direccion.
     *
     * @return <code>String</code> con el/la direccion.
     */
    public final String getDireccion() {
        return direccion;
    }


    /**
     * Asigna el/la direccion.
     *
     * @param direccion <code>String</code> con el/la direccion.
     */
    public final void setDireccion(final String direccion) {
        this.direccion = direccion;
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
     * @param id <code>Long</code> con el/la id.
     */
    public final void setId(final Long id) {
        this.id = id;
    }


    /**
     * Devuelve el/la numero.
     *
     * @return <code>String</code> con el/la numero.
     */
    public final String getNumero() {
        return numero;
    }


    /**
     * Asigna el/la numero.
     *
     * @param numero <code>String</code> con el/la numero.
     */
    public final void setNumero(final String numero) {
        this.numero = numero;
    }

    /**
     * Obtener una referencia al objeto.
     *
     * @return una <code>Reference</code> que representa al objeto
     *         correspondiente.
     */
    public final Reference<DireccionDummy> generateReference() {
        return new Reference<DireccionDummy>(DireccionDummy.class, id);
    }


    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public final String toString() {
        return new ToStringBuilder(this).append("numero", numero).append(
                "id", id).append("codpostal", codpostal).append(
                "direccion", direccion).append("reference",
                this.generateReference()).toString();
    }
}
