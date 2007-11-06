/*
 * Copyright (c) 2006 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.repository.test.model;

import javax.persistence.*;

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
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    private String direccion;
    
    private String numero;
    
    private String codpostal;
    
    /**
     * Devuelve el/la codpostal.
     *
     * @return <code>String</code> con el/la codpostal.
     */
    public String getCodpostal() {
        return codpostal;
    }

    
    /**
     * Asigna el/la codpostal.
     *
     * @param codpostal <code>String</code> con el/la codpostal.
     */
    public void setCodpostal(String codpostal) {
        this.codpostal = codpostal;
    }

    
    /**
     * Devuelve el/la direccion.
     *
     * @return <code>String</code> con el/la direccion.
     */
    public String getDireccion() {
        return direccion;
    }

    
    /**
     * Asigna el/la direccion.
     *
     * @param direccion <code>String</code> con el/la direccion.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    
    /**
     * Devuelve el/la id.
     *
     * @return <code>Long</code> con el/la id.
     */
    public Long getId() {
        return id;
    }

    
    /**
     * Asigna el/la id.
     *
     * @param id <code>Long</code> con el/la id.
     */
    public void setId(Long id) {
        this.id = id;
    }

    
    /**
     * Devuelve el/la numero.
     *
     * @return <code>String</code> con el/la numero.
     */
    public String getNumero() {
        return numero;
    }

    
    /**
     * Asigna el/la numero.
     *
     * @param numero <code>String</code> con el/la numero.
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    /**
     * Obtener una referencia al objeto.
     * 
     * @return una <code>Reference</code> que representa al objeto
     *         correspondiente.
     */
    public Reference<DireccionDummy> generateReference() {
        return new Reference<DireccionDummy>(DireccionDummy.class, id);
    }


    /**
         * @see java.lang.Object#toString()
         */
        public String toString() {
            return new ToStringBuilder(this).append("numero", this.numero).append(
                    "id", this.id).append("codpostal", this.codpostal).append(
                    "direccion", this.direccion).append("reference",
                    this.generateReference()).toString();
        }
    
    
}
