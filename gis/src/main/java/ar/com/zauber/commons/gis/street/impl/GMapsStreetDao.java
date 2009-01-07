/**
 * Copyright (c) 2005-2009 Zauber S.A. <http://www.zauber.com.ar/>
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

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.Validate;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ar.com.zauber.commons.dao.Paging;
import ar.com.zauber.commons.gis.Result;
import ar.com.zauber.commons.gis.street.StreetsDAO;
import ar.com.zauber.commons.gis.street.model.results.GeocodeResult;
import ar.com.zauber.commons.gis.street.model.results.IntersectionResult;
import ar.com.zauber.commons.gis.street.model.results.StreetResult;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.PrecisionModel;

/**
 * Finds streets using gmaps API. StreetDAO decorator for the rest.
 * 
 * @author C. Andrés Moratti
 * @since Dec 2, 2008
 */
public class GMapsStreetDao implements StreetsDAO {

    private final String apiKey;
    private final StreetsDAO streetsDAO;
    
    /**
     * Creates the GMapsStreetDao.
     *
     * @param apiKey
     * @param streetsDAO
     */
    public GMapsStreetDao(final String apiKey, final StreetsDAO streetsDAO) {
        Validate.isTrue(!StringUtils.isEmpty(apiKey));
        Validate.notNull(streetsDAO);
        
        this.apiKey = apiKey;
        this.streetsDAO = streetsDAO;
    }

    
    /** @see StreetsDAO#getStreets(String) */
    public List<Result> getStreets(String query) {
        Validate.isTrue(!StringUtils.isEmpty(query));
        final List<Result> results = new ArrayList<Result>();
        final GeometryFactory geometryFactory = 
            new GeometryFactory(new PrecisionModel(), 4326);  
        final DocumentBuilderFactory factory = 
            DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        
        try {
            final String uri = "http://maps.google.com/maps/geo?"
                + "q=" + URLEncoder.encode(query, "UTF-8")
                + "&output=xml" 
                + "&sensor=true_or_false"
                + "&oe=utf8"
                + "&key=" + apiKey;

            final DocumentBuilder parser = factory.newDocumentBuilder();
            final Document document = parser.parse(uri);
            final NodeList addresses = document.getElementsByTagName(
                "address");
            final NodeList points = document.getElementsByTagName(
                "coordinates");
            final NodeList countryCodes = document.getElementsByTagName(
                "CountryNameCode");
            final NodeList adminArea = document.getElementsByTagName(
                "AdministrativeAreaName");
            final NodeList locality = document.getElementsByTagName(
                "LocalityName");
            
            if(addresses.getLength() > 0
                    && addresses.getLength() == points.getLength()
                    && points.getLength() == countryCodes.getLength()
                    && (countryCodes.getLength() == adminArea.getLength() 
                        || countryCodes.getLength() == locality.getLength())) {
                
                for(int i = 0; i < addresses.getLength(); i++) {
                    final String point = points.item(i).getTextContent();
                    if(point == null) {
                        continue;
                    }
                    
                    final String[] lonLat = point.split(",");
                    if (lonLat.length > 2) {
                        final Double lon = Double.valueOf(lonLat[0]);
                        final Double lat = Double.valueOf(lonLat[1]);
                        final String name = addresses.item(i).getTextContent();
                        final String countryCode = 
                            countryCodes.item(i).getTextContent();
                        // En algunos casos aparece la administrative area
                        // y en otros la locality name. Damos prioridad a la
                        // primera para mostrarla como ciudad.
                        final String city;
                        if(countryCodes.getLength() 
                                == adminArea.getLength()) {
                            city = adminArea.item(i).getTextContent();
                        } else {
                            city = locality.item(i).getFirstChild() != null ?
                                    locality.item(i).getFirstChild().getTextContent()
                                    : null;
                        }
                        
                        if(lon != null && lat != null && name != null
                                && city != null && countryCode != null) {
                            results.add(new StreetResult(
                                    name,
                                    geometryFactory.createPoint(
                                            new Coordinate(lon, lat)),
                                    city,
                                    countryCode));
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new UnhandledException(e);
        } catch (ParserConfigurationException e) {
            throw new UnhandledException(e);
        } catch (SAXException e) {
            throw new UnhandledException(e);
        } catch (DOMException e) {
            throw new UnhandledException(e);
        } 
        
        return results;
    }
    
    /** @see StreetsDAO#suggestAddresses(String) */
    public List<Result> suggestAddresses(String text) {
        final List<Result> suggestResults = streetsDAO.suggestAddresses(text);
        final List<Result> results = new ArrayList<Result>();
        
        // Los resultados que se sugieren son solamente de CABA
        // producto del geocoding de flof
        for (Result suggestResult : suggestResults) {
            results.add(new StreetResult(
                    suggestResult.getDescription(),
                    suggestResult.getPoint(),
                    "Ciudad Autónoma de Buenos Aires",
                    suggestResult.getCountryCode()));
        }
        
        return results;
    }
    
    /** @see StreetsDAO#fullNameStreetExist(String) */
    public boolean fullNameStreetExist(String name) {
        return streetsDAO.fullNameStreetExist(name);
    }

    /** @see StreetsDAO#geocode(String, int) */
    public Collection<GeocodeResult> geocode(String street, int altura) {
        return streetsDAO.geocode(street, altura);
    }

    /** @see StreetsDAO#geocode(String, int, int) */
    public Collection<GeocodeResult> geocode(String street, int altura, int id) {
        return streetsDAO.geocode(street, altura, id);
    }

    /** @see StreetsDAO#getIntersection(String, String) */
    public Collection<IntersectionResult> getIntersection(String street1,
            String street2) {
        return streetsDAO.getIntersection(street1, street2);
    }

    /** @see StreetsDAO#getIntersectionsFor(String) */
    public List<String> getIntersectionsFor(String fullStreetName) {
        return streetsDAO.getIntersectionsFor(fullStreetName);
    }

    /** @see StreetsDAO#getSinonimos(String) */
    public List<String> getSinonimos(String fullStreetName) {
        return streetsDAO.getSinonimos(fullStreetName);
    }

    /** @see StreetsDAO#guessStreetName(java.util.List, String) */
    public List<GuessStreetResult> guessStreetName(List<String> streets,
            String unnomalizedStreetName) {
        return streetsDAO.guessStreetName(streets, unnomalizedStreetName);
    }

    /** @see StreetsDAO#suggestStreets(String, Paging) */
    public List<String> suggestStreets(String beggining, Paging paging) {
        return streetsDAO.suggestStreets(beggining, paging);
    }

}
