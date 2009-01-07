/**
 * Copyright (c) 2005-2009 Zauber S.A. <http://www.zauber.com.ar/>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
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
 * Null Object Patter for {@link StreetsDAO}.
 * 
 * @author Juan F. Codagnone
 * @since Jan 7, 2009
 */
public class NullStreetDAO implements StreetsDAO {

    /** @see StreetsDAO#fullNameStreetExist(String) */
    public final boolean fullNameStreetExist(final String name) {
        return false;
    }

    /** @see StreetsDAO#geocode(String, int) */
    public final Collection<GeocodeResult> geocode(final String street,
            final int altura) {
        return Collections.EMPTY_LIST;
    }

    /** @see StreetsDAO#geocode(String, int, int) */
    public final Collection<GeocodeResult> geocode(final String street,
            final int altura, final int id) {
        return Collections.EMPTY_LIST;
    }

    /** @see StreetsDAO#getIntersection(String, String) */
    public final Collection<IntersectionResult> getIntersection(final String street1,
            final String street2) {
        return Collections.EMPTY_LIST;
    }

    /** @see StreetsDAO#getIntersectionsFor(String) */
    public final List<String> getIntersectionsFor(final String fullStreetName) {
        return Collections.EMPTY_LIST;
    }

    /** @see StreetsDAO#getSinonimos(String) */
    public final List<String> getSinonimos(final String fullStreetName) {
        return Collections.EMPTY_LIST;
    }

    /** @see StreetsDAO#getStreets(String) */
    public final List<Result> getStreets(final String text) {
        return Collections.EMPTY_LIST;
    }

    /** @see StreetsDAO#guessStreetName(java.util.List, String) */
    public final List<GuessStreetResult> guessStreetName(final List<String> streets,
            final String unnomalizedStreetName) {
        return Collections.EMPTY_LIST;
    }

    /** @see StreetsDAO#suggestAddresses(String) */
    public final List<Result> suggestAddresses(final String text) {
        return Collections.EMPTY_LIST;
    }

    /** @see StreetsDAO#suggestStreets(String, Paging) */
    public final List<String> suggestStreets(final String beggining,
            final Paging paging) {
        return Collections.EMPTY_LIST;
    }
}
