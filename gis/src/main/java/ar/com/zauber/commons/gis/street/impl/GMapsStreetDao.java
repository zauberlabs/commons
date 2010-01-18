/**
 * Copyright (c) 2005-2010 Zauber S.A. <http://www.zauber.com.ar/>
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
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.Validate;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
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
    private int precision;

    /**
     * Creates the GMapsStreetDao.
     *
     * @param apiKey api key
     * @param streetsDAO streets dao
     */
    public GMapsStreetDao(final String apiKey, final StreetsDAO streetsDAO) {
        this(apiKey, streetsDAO, 0);
    }

    /**
     * Creates the GMapsStreetDao.
     *
     * @param apiKey api key
     * @param streetsDAO streets dao
     */
    public GMapsStreetDao(final String apiKey, final StreetsDAO streetsDAO,
            final String countryCodeBias) {
        this(apiKey, streetsDAO, 0);
    }



    /**
     * Creates the GMapsStreetDao.
     *
     * @param apiKey api key
     * @param streetsDAO streets dao
     * @param precision la precision minima q se desea.
     * @see http://code.google.com/apis/maps/documentation/reference.html#GGeoAddressAccuracy
     */
    public GMapsStreetDao(final String apiKey,
            final StreetsDAO streetsDAO, final int precision) {
        Validate.isTrue(!StringUtils.isEmpty(apiKey));
        Validate.notNull(streetsDAO);
        this.apiKey = apiKey;
        this.streetsDAO = streetsDAO;
        this.precision = precision;
    }

    /** @see StreetsDAO#getStreets(String) */
    public final List<Result> getStreets(final String query) {
        Validate.isTrue(!StringUtils.isEmpty(query));
        final List<Result> results = new LinkedList<Result>();
        final DocumentBuilderFactory factory = DocumentBuilderFactory
                .newInstance();
        factory.setNamespaceAware(true);

        try {
            final String uri = "http://maps.google.com/maps/geo?" + "q="
                    + URLEncoder.encode(query, "UTF-8") + "&output=xml"
                    + "&sensor=true_or_false" + "&oe=utf8" + "&key=" + apiKey;

            final DocumentBuilder parser = factory.newDocumentBuilder();
            final Document document = parser.parse(uri);
            GMapsResultGenerator resultGenerator =
                new GMapsResultGenerator(precision);
            results.addAll(resultGenerator.getResults(document));
        } catch (final IOException e) {
            results.addAll(streetsDAO.getStreets(query));
            throw new UnhandledException(e);
        } catch (final ParserConfigurationException e) {
            throw new UnhandledException(e);
        } catch (final SAXException e) {
            throw new UnhandledException(e);
        } catch (final DOMException e) {
            throw new UnhandledException(e);
        }

        return results;
    }

    /** @see StreetsDAO#suggestAddresses(String) */
    public final List<Result> suggestAddresses(final String text) {
        final List<Result> suggestResults = streetsDAO.suggestAddresses(text);
        final List<Result> results = new ArrayList<Result>();

        // Los resultados que se sugieren son solamente de CABA
        // producto del geocoding de flof
        for (final Result suggestResult : suggestResults) {
            results.add(new StreetResult(suggestResult.getDescription(),
                    suggestResult.getPoint(),
                    "Ciudad Autónoma de Buenos Aires", suggestResult
                            .getCountryCode()));
        }

        return results;
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

}

/**
 *Devuelve un List de results a partir de un document
 *
 * @author Mariano Semelman
 * @since Dec 14, 2009
 */
class GMapsResultGenerator {


    private final GeometryFactory geometryFactory;
    private int accu;

    /** Creates the GMapsResultGenerator. */
    public GMapsResultGenerator() {
        this(8);

    }

    /** constructor que permite definir la precision minima que se desea
     * http://code.google.com/apis/maps/documentation/reference.html#GGeoAddressAccuracy */
    public GMapsResultGenerator(final int accu) {
        geometryFactory = new GeometryFactory(
                new PrecisionModel(), 4326);
        this.accu = accu;
    }

    /**
     *
     * @param document de donde obtener resultados
     * @return la lista de resultados.
     */
    public final Collection<Result> getResults(final Document document) {
        Collection<Result> collection = new LinkedList<Result>();
        getPlacemarks(document, collection);
        return collection;
    }

    /**
     *
     * @param node nodo donde buscar los "placemark" (ver kml reference)
     * @param collection donde almacenar los results
     */
    private void getPlacemarks(
            final Node node,
            final Collection<Result> collection) {
        Validate.notNull(node);
        Validate.notNull(collection);
        final NodeList list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node subnode = list.item(i);
            String nombre = subnode.getNodeName().trim();
            if (nombre.equals("Placemark")) {
                Result element = getPlace(subnode);
                if(element != null) {
                    collection.add(element);
                }
            } else if(!nombre.equals("#text")) {
                getPlacemarks(subnode, collection);
            }
        }

    }

    /**
     *
     * @param node en este nodo esta el placemark
     * @return un {@link Result} con los datos.
     */
    private Result getPlace(final Node node) throws NumberFormatException {
        Validate.notNull(node);
        Validate.isTrue(node.getNodeName().equals("Placemark"));
        Result res = null;

        Node details = find("AddressDetails", node);
        Node coordenadas = find("coordinates", node);
        // obtengo coordenadas y si es preciso
        if(coordenadas != null && details != null && details.hasAttributes()) {
            Node nprecision = details.getAttributes().getNamedItem("Accuracy");
            if(nprecision != null) {
                String precision =    nprecision.getTextContent();
                boolean preciso = Integer.parseInt(precision) >= accu;
                String coord = coordenadas.getTextContent();
                final String[] lonLat = coord.split(",");
                if (lonLat.length > 2 && preciso) {
                    final Double lon = Double.valueOf(lonLat[0]);
                    final Double lat = Double.valueOf(lonLat[1]);

                    // obtengo nombre entero de la direccion
                    String name = find("address", node).getTextContent();

                    //obtengo pais
                    Node country = find("CountryNameCode", details);
                    String countryCode = (country == null) ? null
                            : country.getTextContent();

                    // obtengo ciudad
                    Node place = find("LocalityName", details);
                    Node ciudad = (place == null)
                    ? find("AdministrativeAreaName", details) : place;
                    String city = (ciudad == null) ? null : ciudad.getTextContent();

                    //armo el resultado
                    if (lon != null && lat != null && name != null
                            && city != null && countryCode != null) {
                        res = new StreetResult(name,
                                geometryFactory.createPoint(
                                        new Coordinate(lon, lat)),
                                city, countryCode);
                    }
                }
            }
        }
        return res;
    }

    /**
     * @param key nombre del tag a buscar
     * @param node estructura donde buscar.
     * @return el nodo con el tag con ese key como nombre
     */
    private Node find(final String key, final Node node) {
        Validate.notNull(node);
        Validate.isTrue(StringUtils.isNotBlank(key));
        NodeList list = node.getChildNodes();
        Node res = null;
        for (int index = 0; index < list.getLength(); index++) {
            String name = list.item(index).getNodeName();
            if(name.equals(key) && res == null) {
                res = list.item(index);
            } else if(name.equals(key)) {
                throw new UnhandledException(
                        "Este metodo debe ser llamado cuando se sabe que"
                        + "no existen mas de un nodo con este nombre", null);
            } else if(res == null) {
                res = find(key, list.item(index));
            }
        }
        return res;
    }

}
