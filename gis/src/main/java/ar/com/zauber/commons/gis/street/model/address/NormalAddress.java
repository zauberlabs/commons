/*
 * Copyright (c) 2007 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.gis.street.model.address;

/**
 * Una direccion del tipo CALLE ALTURA
 * 
 * @author Christian Nardi
 * @since Oct 1, 2007
 */
public class NormalAddress {
    /** <code>street</code> */
    private String street;
    /** <code>altura</code> */
    private int altura;
    private String countryCode;
    /**
     * Returns the countryCode.
     * 
     * @return <code>String</code> with the countryCode.
     */
    public final String getCountryCode() {
        return countryCode;
    }
    /**
     * Sets the countryCode. 
     *
     * @param countryCode <code>String</code> with the countryCode.
     */
    public final void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    /**
     * Creates the NormalAddress.
     *
     * @param street
     * @param altura
     */
    public NormalAddress(final String street, final int altura, 
            final String countryCode) {
        super();
        this.street = street;
        this.altura = altura;
        this.countryCode = countryCode;
    }
    /**
     * Returns the street.
     * 
     * @return <code>String</code> with the street.
     */
    public final String getStreet() {
        return street;
    }
    /**
     * Sets the street. 
     *
     * @param street <code>String</code> with the street.
     */
    public final void setStreet(final String street) {
        this.street = street;
    }
    /**
     * Returns the altura.
     * 
     * @return <code>int</code> with the altura.
     */
    public final int getAltura() {
        return altura;
    }
    /**
     * Sets the altura. 
     *
     * @param altura <code>int</code> with the altura.
     */
    public final void setAltura(final int altura) {
        this.altura = altura;
    }
    
}
