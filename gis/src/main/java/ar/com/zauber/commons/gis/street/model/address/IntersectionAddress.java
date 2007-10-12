/*
 * Copyright (c) 2007 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.gis.street.model.address;

/**
 * Representa una direccion del estilo CALLE Y CALLE
 * 
 * @author Christian Nardi
 * @since Oct 1, 2007
 */
public class IntersectionAddress {
    /** <code>street1</code> */
    private String street1;
    /** <code>street2</code> */
    private String street2;
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
     * Creates the IntersectionAddress.
     *
     * @param street1
     * @param street2
     */
    public IntersectionAddress(String street1, String street2, String countryCode) {
        super();
        this.street1 = street1;
        this.street2 = street2;
        this.countryCode = countryCode;
    }
    /**
     * Returns the street1.
     * 
     * @return <code>String</code> with the street1.
     */
    public final String getStreet1() {
        return street1;
    }
    /**
     * Sets the street1. 
     *
     * @param street1 <code>String</code> with the street1.
     */
    public final void setStreet1(String street1) {
        this.street1 = street1;
    }
    /**
     * Returns the street2.
     * 
     * @return <code>String</code> with the street2.
     */
    public final String getStreet2() {
        return street2;
    }
    /**
     * Sets the street2. 
     *
     * @param street2 <code>String</code> with the street2.
     */
    public final void setStreet2(String street2) {
        this.street2 = street2;
    }
    
}
