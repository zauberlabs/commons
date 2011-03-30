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
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Paging;
import ar.com.zauber.commons.gis.Result;
import ar.com.zauber.commons.gis.street.StreetsDAO;
import ar.com.zauber.commons.gis.street.model.results.GeocodeResult;
import ar.com.zauber.commons.gis.street.model.results.IntersectionResult;

/**
 * {@link StreetsDAO} that caches results of {@link StreetsDAO#getStreets(String)}
 * using EHCache.
 * 
 * @author Juan F. Codagnone
 * @since Jan 7, 2009
 */
public class EhCacheStreetsDAO implements StreetsDAO {

    private final StreetsDAO streetsDAO;
    private final Cache getStreetsCache;

    /**
     * Creates the GMapsStreetDao.
     * 
     * @param apiKey
     * @param streetsDAO
     */
    public EhCacheStreetsDAO(final StreetsDAO streetsDAO,
            final Cache getStreetsCache) {
        Validate.notNull(streetsDAO);
        Validate.notNull(getStreetsCache); 

        this.streetsDAO = streetsDAO;
        this.getStreetsCache = getStreetsCache;
    }

    /** @see StreetsDAO#getStreets(String) */
    public final List<Result> getStreets(final String query) {
        final Element e = getStreetsCache.get(query);
        final List<Result> ret; 
        
        if(e == null) {
            ret = streetsDAO.getStreets(query);
            getStreetsCache.put(new Element(query, ret));

        } else {
            ret = (List<Result>) e.getValue();
        }
        
        return ret;
    }
    
    /** @see StreetsDAO#fullNameStreetExist(String) */
    public final boolean fullNameStreetExist(final String name) {
        return streetsDAO.fullNameStreetExist(name);
    }

    /** @see StreetsDAO#geocode(String, int) */
    public final Collection<GeocodeResult> geocode(final String street,
            final int altura) {
        return streetsDAO.geocode(street, altura);
    }

    /** @see StreetsDAO#geocode(String, int, int) */
    public final Collection<GeocodeResult> geocode(final String street,
            final int altura, final int id) {
        return streetsDAO.geocode(street, altura, id);
    }

    /** @see StreetsDAO#getIntersection(String, String) */
    public final Collection<IntersectionResult> getIntersection(
            final String street1, final String street2) {
        return streetsDAO.getIntersection(street1, street2);
    }

    /** @see StreetsDAO#getIntersectionsFor(String) */
    public final List<String> getIntersectionsFor(final String fullStreetName) {
        return streetsDAO.getIntersectionsFor(fullStreetName);
    }

    /** @see StreetsDAO#getSinonimos(String) */
    public final List<String> getSinonimos(final String fullStreetName) {
        return streetsDAO.getSinonimos(fullStreetName);
    }

    /** @see StreetsDAO#guessStreetName(java.util.List, String) */
    public final List<GuessStreetResult> guessStreetName(
            final List<String> streets, final String unnomalizedStreetName) {
        return streetsDAO.guessStreetName(streets, unnomalizedStreetName);
    }

    /** @see StreetsDAO#suggestStreets(String, Paging) */
    public final List<String> suggestStreets(final String beggining,
            final Paging paging) {
        return streetsDAO.suggestStreets(beggining, paging);
    }

    /** @see StreetsDAO#suggestAddresses(String) */
    public final List<Result> suggestAddresses(final String text) {
        return streetsDAO.suggestAddresses(text);
    }
}
