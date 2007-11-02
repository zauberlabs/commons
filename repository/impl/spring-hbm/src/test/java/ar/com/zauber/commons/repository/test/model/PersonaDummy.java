/*
 * Copyright (c) 2006 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.repository.test.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.apache.commons.lang.builder.ToStringBuilder;

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
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    private Integer numeroFiscal;
    
    private String nombre;
    
    private String descripcion;
    
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="persona_id")    
    private Set<DireccionDummy> direcciones;

    /**
     * Crea el/la PersonaDummy.
     *
     */
    public PersonaDummy() {

    }

    public PersonaDummy(int numeroFiscal, String nombre, String descripcion, Set<DireccionDummy> direcciones) {
        this.numeroFiscal = new Integer(numeroFiscal);
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.direcciones = direcciones;
    }

    public PersonaDummy(String nombre) {
        this.nombre = nombre;
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
    public void setId(Long anId) {
        this.id = anId;
    } 
    
    
    /**
     * Devuelve el/la descripcion.
     *
     * @return <code>String</code> con el/la descripcion.
     */
    public String getDescripcion() {
        return descripcion;
    }

    
    /**
     * Asigna el/la descripcion.
     *
     * @param descripcion <code>String</code> con el/la descripcion.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    
    /**
     * Devuelve el/la direcciones.
     *
     * @return <code>Collection</code> con el/la direcciones.
     */
    public Set<DireccionDummy> getDirecciones() {
        return direcciones;
    }

    
    /**
     * Asigna el/la direcciones.
     *
     * @param direcciones <code>Collection</code> con el/la direcciones.
     */
    public void setDirecciones(Set<DireccionDummy> direcciones) {
        this.direcciones = direcciones;
    }

    
    /**
     * Devuelve el/la nombre.
     *
     * @return <code>String</code> con el/la nombre.
     */
    public String getNombre() {
        return nombre;
    }

    
    /**
     * Asigna el/la nombre.
     *
     * @param nombre <code>String</code> con el/la nombre.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    
    /**
     * Devuelve el/la numeroFiscal.
     *
     * @return <code>Integer</code> con el/la numeroFiscal.
     */
    public Integer getNumeroFiscal() {
        return numeroFiscal;
    }

    
    /**
     * Asigna el/la numeroFiscal.
     *
     * @param numeroFiscal <code>Integer</code> con el/la numeroFiscal.
     */
    public void setNumeroFiscal(Integer numeroFiscal) {
        this.numeroFiscal = numeroFiscal;
    }
    
    /**
     * Obtener una referencia al objeto.
     * 
     * @return una <code>Reference</code> que representa al objeto
     *         correspondiente.
     */
    public Reference getReference() {
        return new Reference(this.getClass(), id);
    }

    /**
         * @see java.lang.Object#toString()
         */
        public String toString() {
            return new ToStringBuilder(this).append("numeroFiscal",
                    this.numeroFiscal).append("id", this.id).append("nombre",
                    this.nombre).append("direcciones", this.direcciones).append(
                    "reference", this.getReference()).append("descripcion",
                    this.descripcion).toString();
        }
}
