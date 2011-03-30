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
package ar.com.zauber.commons.gis.spots.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import ar.com.zauber.commons.LatinStringUtils;
import ar.com.zauber.commons.dao.Paging;
import ar.com.zauber.commons.dao.exception.NoSuchEntityException;
import ar.com.zauber.commons.gis.spots.GeonameSpotDAO;
import ar.com.zauber.commons.gis.spots.model.GeonameSpot;

import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.WKTWriter;


/**
 * SQL implementation for {@link GeonameSpotDAO}
 * 
 * @author Juan F. Codagnone
 * @since Mar 9, 2006
 */
public class SQLGeonameSpotDAO implements GeonameSpotDAO {
    /** sql connection */
    private final JdbcTemplate jdbcTemplate;
    /** wkt writer */
    private final WKTWriter writer = new WKTWriter();
    /** WKT reader that creates the Geometric objects */
    private final WKTReader wktReader = new WKTReader();
    
    /**
     * Creates the SQLGeonameSpotDAO.
     *
     * @param jdbcTemplate an sql connection
     */
    public SQLGeonameSpotDAO(final JdbcTemplate jdbcTemplate) {
        Validate.notNull(jdbcTemplate);
        
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private static final String SELECT_WHAT = 
        "SELECT geonameid, name, ansiname, countrycode, population, "
        + "AsText(the_geom) FROM geoname "; 
    
    /** @see GeonameSpotDAO#getSpotsIn(Polygon) */
    public final Collection<GeonameSpot> getSpotsIn(final Polygon polygon) {
        final String sql =  SELECT_WHAT + " WHERE the_geom && " 
            + "GeometryFromText(?, 4326);";
        
        return (Collection<GeonameSpot>)jdbcTemplate.query(sql, 
           new Object[] {writer.write(polygon)},
           new int[] {Types.VARCHAR}, new GeonameSpotExtractor(wktReader));
    }

    private long getCountSpotsWithNameLike(final String name) {
        final String sql = "SELECT COUNT(*) FROM geoname WHERE name ~* ?";
        
        return jdbcTemplate.queryForLong(sql,  new Object[]{name}, 
                new int[] {Types.VARCHAR});
        
    }
    /** @see GeonameSpotDAO#getSpotsWithNameLike(String) */
    public final Collection<GeonameSpot> getSpotsWithNameLike(
            final String name, final Paging paging) {
        String ansiname = LatinStringUtils.replaceAccents(name);
        String sql = SELECT_WHAT + " WHERE ansiname ~* ? ORDER BY ansiname";
        Object []objs;
        int []classes;
        
        if(paging == null) {
            objs = new Object[] {ansiname};
            classes = new int[] {Types.VARCHAR};
        } else {
            sql += " LIMIT ? OFFSET ?";
            objs = new Object[] {ansiname, paging.getResultsPerPage(),
                    paging.getFirstResult()};
            classes = new int[] {Types.VARCHAR, Types.INTEGER, 
                    Types.INTEGER};
            
             
            if(paging.loadResultSize()) {
                paging.setResultSize((int)
                        getCountSpotsWithNameLike(ansiname));
            }
        }
        

        return (Collection<GeonameSpot>)jdbcTemplate.query(sql, 
                objs, classes, new GeonameSpotExtractor(wktReader));
    }

    /** @see GeonameSpotDAO#getNearestGeoname(Point) */
    public final GeonameSpot getNearestGeoname(final Point point) {
        final GeonameSpot ret;
        
        final Collection<GeonameSpot> c = (Collection<GeonameSpot>)
            jdbcTemplate.query("SELECT distance(the_geom, "
                + "GeometryFromText(?, 4326)) as distance, geonameid, name," 
                + " ansiname, countrycode, population, AsText(the_geom) from "
                + "geoname ORDER BY distance LIMIT 1;",
                new Object[]{point.toString()}, 
                new GeonameSpotExtractor(wktReader));
        if(c.size() == 0) {
             throw new NoSuchEntityException("point");
        } else {
            ret = new ArrayList<GeonameSpot>(c).get(0);
        }
        
        return ret;
    }
    
}

class GeonameSpotExtractor implements ResultSetExtractor {
    /** reader */
    private final WKTReader reader;
    public GeonameSpotExtractor(final WKTReader reader) {
        this.reader = reader;
    }
    /** @see ResultSetExtractor#extractData(java.sql.ResultSet) */
    public final Object extractData(final ResultSet rset) 
        throws SQLException, DataAccessException {
        final Collection<GeonameSpot> ret = new ArrayList<GeonameSpot>(rset
                .getFetchSize());
        
        while(rset.next()) {
            try {
                final Point point = (Point)reader.read(
                        rset.getString("astext"));
                final GeonameSpot spot = new GeonameSpot(point, 
                        rset.getString("name"),
                        rset.getString("ansiname"),
                        rset.getString("countrycode"),
                        rset.getLong("population"));
                spot.setId(rset.getLong("geonameid"));
                ret.add(spot);
            } catch(final ParseException e) {
                throw new RuntimeException(e);
            }
        }
        rset.close();
        
        return ret;
    }
}