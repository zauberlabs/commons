/**
 * Copyright (c) 2005-2011 Zauber S.A. <http://www.zaubersoftware.com/>
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
package ar.com.zauber.commons.gis.street.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import ar.com.zauber.commons.dao.Paging;
import ar.com.zauber.commons.gis.Result;
import ar.com.zauber.commons.gis.street.StreetsDAO;
import ar.com.zauber.commons.gis.street.model.results.GeocodeResult;
import ar.com.zauber.commons.gis.street.model.results.IntersectionResult;

/**
 * Null Object Patter for {@link StreetsDAO}. Util tambien para crear
 * test de unidad.
 * 
 * @author Juan F. Codagnone
 * @since Jan 7, 2009
 */
public class NullStreetDAO implements StreetsDAO {

    //CHECKSTYLE:DESIGN:OFF
    /** @see StreetsDAO#fullNameStreetExist(String) */
    public boolean fullNameStreetExist(final String name) {
        return false;
    }

    /** @see StreetsDAO#geocode(String, int) */
    public Collection<GeocodeResult> geocode(final String street,
            final int altura) {
        return Collections.EMPTY_LIST;
    }

    /** @see StreetsDAO#geocode(String, int, int) */
    public Collection<GeocodeResult> geocode(final String street,
            final int altura, final int id) {
        return Collections.emptyList();
    }

    /** @see StreetsDAO#getIntersection(String, String) */
    public Collection<IntersectionResult> getIntersection(final String street1,
            final String street2) {
        return Collections.emptyList();
    }

    /** @see StreetsDAO#getIntersectionsFor(String) */
    public List<String> getIntersectionsFor(final String fullStreetName) {
        return Collections.emptyList();
    }

    /** @see StreetsDAO#getSinonimos(String) */
    public List<String> getSinonimos(final String fullStreetName) {
        return Collections.emptyList();
    }

    /** @see StreetsDAO#getStreets(String) */
    public List<Result> getStreets(final String text) {
        return Collections.emptyList();
    }

    /** @see StreetsDAO#guessStreetName(java.util.List, String) */
    public List<GuessStreetResult> guessStreetName(final List<String> streets,
            final String unnomalizedStreetName) {
        return Collections.emptyList();
    }

    /** @see StreetsDAO#suggestAddresses(String) */
    public List<Result> suggestAddresses(final String text) {
        return Collections.emptyList();
    }

    /** @see StreetsDAO#suggestStreets(String, Paging) */
    public List<String> suggestStreets(final String beggining,
            final Paging paging) {
        return Collections.emptyList();
    }
}
