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
package ar.com.zauber.commons.gis.spots.model;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.gis.Result;

import com.vividsolutions.jts.geom.Point;


/**
 * TODO Brief description.
 * 
 * TODO Detail
 * 
 * @author Juan F. Codagnone
 * @since Mar 9, 2006
 */
public class GeonameSpot implements Result {
    /** the id */
    private long id;
    /** location */
    private final Point location;
    /** the name */
    private final String name;
    /** the ansi name */
    private final String ansiName;
    /** the ISOCountry */
    private final String countryCode;
    /** population */
    private final long population;
    
    // TODO: poner el resto de los atributos
  
    /**
     * @param location the location of the spot
     * @param name the spot name
     * @param ansiName the ansi name
     * @param countryCode country code 
     * @param population population
     */
    public GeonameSpot(final Point location, final String name, 
            final String ansiName, final String countryCode,
            final long population) {
        Validate.notNull(location, "location");
        Validate.notNull(name);
        Validate.notNull(ansiName);
        Validate.notEmpty(countryCode);
        Validate.isTrue(population >= 0);
        
        this.location = location;
        this.name = name;
        this.ansiName = ansiName;
        this.countryCode = countryCode;
        this.population = population;
    }
    
    /**
     * Sets the id. 
     *
     * @param id <code>long</code> with the id.
     */
    public final void setId(final long id) {
        this.id = id;
    }


    /**
     * Returns the id.
     * 
     * @return <code>long</code> with the id.
     */
    public final long getId() {
        return id;
    }


    
    /**
     * Returns the location.
     * 
     * @return <code>Point</code> with the location.
     */
    public final Point getLocation() {
        return location;
    }
  

    /**
     * Returns the ansiName.
     * 
     * @return <code>String</code> with the ansiName.
     */
    public final String getAnsiName() {
        return ansiName;
    }

    
    /**
     * Returns the name.
     * 
     * @return <code>String</code> with the name.
     */
    public final String getName() {
        return name;
    }

    /**
     * Returns the countryCode.
     * 
     * @return <code>String</code> with the countryCode.
     */
    public final String getCountryCode() {
        return countryCode;
    }

    
    /**
     * Returns the population.
     * 
     * @return <code>long</code> with the population.
     */
    public long getPopulation() {
        return population;
    }
    
    public final String getDescription() {
        return "Ciudad de " + name;
    }
    
    public final Point getPoint() {
        return location;
    }
}
