/*
 * Copyright (c) 2006 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.gis.street;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Paging;
import ar.com.zauber.commons.gis.Result;
import ar.com.zauber.commons.gis.street.model.results.GeocodeResult;
import ar.com.zauber.commons.gis.street.model.results.IntersectionResult;


/**
 * Streets data access object
 * 
 * @author Juan F. Codagnone
 * @since Mar 21, 2006
 */
public interface StreetsDAO {
    
    /**
     * @param street1 calle1 
     * @param street2 calle2
     * @return donde se intersectan la calle1 con la calle2 
     *         (o nombres parecidos)
     */
    Collection<IntersectionResult> getIntersection(String street1, 
                                                   String street2);
    
    
    /**
     * @param street calle a buscar (o parte del nombre)
     * @param altura altura 
     * @return una collección con los resultados de la busqueda. puede ser vacia.
     */
    Collection<GeocodeResult> geocode(String street, int altura);
    
    /**
     * @param street calle a buscar (o parte del nombre)
     * @param altura altura
     * @param id a filtrar
     * @return una collección con los resultados de la busqueda. puede ser vacia.
     * 
     */
    Collection<GeocodeResult> geocode(String street, int altura, int id);
    
    
    /**
     * Sugiere nombre de calles de capital que comienzan con beggining
     * 
     * @param beggining comiezo del nombre de la calle
     * @param paging paginado
     */
    List<String> suggestStreets(String beggining, Paging paging);
    
    /**
     * Sugiere direcciones posibles para el texto indicado
     * 
     * @param text texto que representa la direccion
     * @param paging paginado
     */
    List<Result> suggestAddresses(String text);
    
    /**
     * 
     * @param text
     * @return un listado de resultados que contienen el punto intermedio de las 
     * calles que matchean con text.
     */
    List<Result> getStreets(final String text);
    
    boolean fullNameStreetExist(String name);
    
    /**
     * @return la lista de calles que cruzan a una calle. La calle tiene
     * que estar dado por el nombre completo. 
     */
    List<String> getIntersectionsFor(String fullStreetName);
    
    /** retorna otros nombres para una calle */
    List<String> getSinonimos(String fullStreetName);

    /**
     * Dado una lista de nombres  de calle y un nombre de calle, retorna una
     * lista ordenada por puntos
     */
    List<GuessStreetResult> guessStreetName(List<String> streets, String unnomalizedStreetName);
    
    class GuessStreetResult implements Comparable<GuessStreetResult> {
        private String streetName;
        private double points;
        
        public GuessStreetResult(final String streetName, final double points) {
            Validate.notEmpty(streetName);
            this.streetName = streetName;
            this.points = points;
        }
        
        public final String getStreetName() {
            return streetName;
        }
        public final double getPoints() {
            return points;
        }
        
        /** @see java.lang.Object#toString() */
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append(streetName);
            sb.append(' ');
            sb.append("points: ");
            sb.append(points);
            
            return sb.toString();
        }

        /** @see java.lang.Comparable#compareTo(java.lang.Object) */
        public int compareTo(final GuessStreetResult o) {
            return Double.compare(o.points, points);
        }
    };
    
}
