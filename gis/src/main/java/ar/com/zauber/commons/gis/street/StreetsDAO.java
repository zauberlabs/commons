/*
 * Copyright (c) 2006 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.gis.street;

import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Paging;

import com.vividsolutions.jts.geom.Point;


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
     * @return la lista de calles que cruzan a una calle. La calle tiene
     * que estar dado por el nombre completo. 
     */
    List<String> getIntersectionsFor(String fullStreetName);
    

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
    
    /**
     * Collect items. 
     * 
     * @author Juan F. Codagnone
     * @since Mar 25, 2006
     */
    interface Collector<T> {
        /**
         * @param object to collect
         */
        void collect(T object);
    }
    
    public class GeocodeResult {
        private final int id;
        private final String street;
        private final int altura;
        private final Point point;
        
        /**
         * Creates the GeocodeResult.
         *
         * @param id id
         * @param street street
         * @param altura altua
         * @param point punto
         */
        public GeocodeResult(final int id, final String street,
                final int altura, final Point point) {
            Validate.notNull(id, "id");
            Validate.notNull(street, "street");
            Validate.notNull(altura, "altura");
            Validate.notNull(point, "point");
            
            this.id = id;
            this.street = street;
            this.altura = altura;
            this.point = point;
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
         * Returns the id.
         * 
         * @return <code>int</code> with the id.
         */
        public final int getId() {
            return id;
        }
        
        /**
         * Returns the point.
         * 
         * @return <code>Point</code> with the point.
         */
        public final Point getPoint() {
            return point;
        }
        
        /**
         * Returns the street.
         * 
         * @return <code>String</code> with the street.
         */
        public final String getStreet() {
            return street;
        }
        
    }
    
    public class IntersectionResult {
        /** ver constructor */
        private final String street1;
        /** ver constructor */
        private final String street2;
        /** ver constructor */
        private final Point point;
        
        /**
         * Creates the IntersectionResult.
         *
         * @param street1 calle1 de la interseccion
         * @param street2 calle2 de la interseccion
         * @param point punto donde las calles se intersectan
         */
        public IntersectionResult(final String street1, final String street2, 
                                  final Point point) {
            
            Validate.notNull(street1);
            Validate.notNull(street2);
            Validate.notNull(point);
            
            this.street1 = street1;
            this.street2 = street2;
            this.point = point;
        }
        
        /**
         * Returns the point.
         * 
         * @return <code>Point</code> with the point.
         */
        public final Point getPoint() {
            return point;
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
         * Returns the street2.
         * 
         * @return <code>String</code> with the street2.
         */
        public final String getStreet2() {
            return street2;
        }
        
        /** @see java.lang.Object#toString() */
        @Override
        public final String toString() {
            return street1 + " y " + street2 + " en " + point;
        }
    }

    
}
