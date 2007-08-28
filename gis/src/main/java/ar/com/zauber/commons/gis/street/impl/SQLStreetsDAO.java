/*
 * Copyright (c) 2006 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.gis.street.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import ar.com.zauber.commons.dao.Paging;
import ar.com.zauber.commons.gis.street.Options;
import ar.com.zauber.commons.gis.street.StreetsDAO;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;


/**
 * SQL implementation for {@link StreetsDAO}.
 * 
 * @author Juan F. Codagnone
 * @since Mar 21, 2006
 */
public class SQLStreetsDAO implements StreetsDAO {
    /** jdbc template */
    private final JdbcTemplate template;
    /** options */
    private final List<Options> optionsList;
    /** der */
    private final WKTReader wktReader = new WKTReader();
    /**
     * Creates the SQLStreetsDAO.
     *
     * @param template jdbc template
     */
    public SQLStreetsDAO(final JdbcTemplate template) {
    	this(template, null);
    }
    /**
     * @param template jdbc template
     * @param optionsList la lista de opciones. Notar que las opciones se aplican en el orden
     * en que se reciben.
     */
    public SQLStreetsDAO(final JdbcTemplate template, List<Options> optionsList) {
    	Validate.notNull(template);
    	
    	if (optionsList == null) {
    		optionsList = Collections.emptyList();
    	}
    	
    	this.template = template;
    	this.optionsList = Collections.unmodifiableList(optionsList);
    }
    
    /** @see StreetsDAO#getIntersection(String, String) */
    public final Collection<IntersectionResult> getIntersection(
            final String street1Param, final String street2Param) {
        final Collection<IntersectionResult> ret = 
             new ArrayList<IntersectionResult>();

        // Si busco calles con longitud <= 2 se muere la base.
        if((street1Param != null && street1Param.length() <= 2) ||
                (street2Param != null && street2Param.length() <= 2)) {
            return ret;
        }
        
        final String sql = "select DISTINCT AsText(intersection(c1.the_geom, "
            + "c2.the_geom)) AS the_geom, c1.nomoficial AS c1, "
            + "c2.nomoficial AS c2  from streets "
            + "AS c1, streets As c2 WHERE c1.nomoficial ILIKE ? ESCAPE '+' "
            + "AND  c2.nomoficial ILIKE ? ESCAPE '+' "
            + "AND c1.ciudad = c2.ciudad AND c1.ciudad = 'buenos aires' "
            + "LIMIT 10";

        String street1Filtered = executeFilters(street1Param);
        String street2Filtered = executeFilters(street2Param);
        
        template.query(sql , new Object[] {
                "%" + escapeForLike(street1Filtered, '+') + "%",
                "%" + escapeForLike(street2Filtered, '+') + "%"},
            new ResultSetExtractor() {
                /** @see ResultSetExtractor#extractData(java.sql.ResultSet) */
                public Object extractData(final ResultSet rs) 
                throws SQLException, DataAccessException {
                    while(rs.next()) {
                        try {
                            final Geometry geom  = 
                                wktReader.read(rs.getString("the_geom"));
                            if(!geom.isEmpty()) {
                                ret.add(new IntersectionResult(
                                        rs.getString("c1"), 
                                        rs.getString("c2"), 
                                        (Point)geom));
                            }
                            
                        } catch(final ParseException e) {
                            throw new DataRetrievalFailureException(
                                "parsing feature geom");
                        }
                    }
                    return null;
                }
            });
            
        
        return ret;
    }

    /**
     * Escapes a character from a string intended to be used in a like clause.
     * 
     * @param text string to escape
     * @param escapeChar escape character 
     * @return the escaped string
     */
    private String escapeForLike(final String text, 
            final Character escapeChar) {
        String escape = escapeChar.toString();
        return text.replace("%", escape.concat("%")).replace("_", 
                escape.concat("_"));
    }

    /** @see StreetsDAO#geocode(String, int) */
    public final Collection<GeocodeResult> geocode(final String street, 
            final int altura) {
        return geocode_(street, altura, null);
    }
    
    /** @see StreetsDAO#geocode(String, int, int) */
    public final Collection<GeocodeResult> geocode(final String street, 
            final int altura, final int id) {
        return geocode_(street, altura, id);
    }
    
    
    /** @see StreetsDAO#geocode(String, int) */
    public final Collection<GeocodeResult> geocode_(final String streetParam, 
            final int altura, Integer id) {
        Validate.notEmpty(streetParam);
        
        String streetFiltered = executeFilters(streetParam);
        
        final Collection<Object> args = new ArrayList<Object>();
        args.add(streetFiltered);
        args.add(altura);
        if(id != null) {
            args.add(id.intValue());
        }
        
        final Collection<GeocodeResult> ret = new ArrayList<GeocodeResult>();
        final String q = "select * from geocode_ba(?, ?)" + 
                        (id == null ? "" : " where id=?");
        template.query(q, args.toArray(), new ResultSetExtractor() {
                    public final Object extractData(final ResultSet rs) 
                       throws SQLException, DataAccessException {
                        
                        while(rs.next()) {
                            try {
                                ret.add(new GeocodeResult(
                                        rs.getInt("id"),
                                        rs.getString("nomoficial"),
                                        rs.getInt("altura"),
                                        (Point)wktReader.read(rs.getString
                                                  ("astext"))
                                        ));
                            } catch(ParseException e) {
                                throw new DataRetrievalFailureException(
                                    "parsing feature geom"); 
                            } 
                        }
                        
                        return null;
                    }
        });
        
        return ret;
    }

    /**  @see StreetsDAO#suggestStreets(String, Paging) */
    public List<String> suggestStreets(final String beggining, 
            final Paging paging) {
        final List<String> usernames = new ArrayList<String>();
        final String q = "%" + escapeForLike(beggining, '+') + "%";
        final List<Object> args = new ArrayList<Object>(4);
        args.add(q);
        if(paging != null) {
            args.add(paging.getResultsPerPage());
            args.add(paging.getFirstResult());
        }
        
        template.query("select distinct nomoficial from streets WHERE "
                + "nomoficial ILIKE ? ESCAPE '+' AND ciudad = 'buenos aires' "
                + "ORDER BY nomoficial"
                + (paging == null ? "" : " LIMIT ? OFFSET ? "),
                args.toArray(),
                new ResultSetExtractor() {
                    public Object extractData(final ResultSet rset) 
                        throws SQLException, DataAccessException {
                        while(rset.next()) {
                            usernames.add(rset.getString("nomoficial"));
                        }
                        return null;
                    }
                });
        
        return usernames;
    }
    
    /**
     * Ejecuta los filters en el orden de la lista {@link #optionsList}.
     * @param street el texto a filtrar.
     * @return el texto filtrado
     */
    private String executeFilters(String street) {
    	for (Iterator<Options> optionsIter = this.optionsList.iterator(); optionsIter.hasNext();) {
			Options options = optionsIter.next();
			street = options.filter(street);
		}
    	return street.trim();
    }
}
