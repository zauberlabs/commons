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
    public final void setCountryCode(final String countryCode) {
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
