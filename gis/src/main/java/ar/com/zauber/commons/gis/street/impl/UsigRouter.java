/*
 * Copyright (c) 2007 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.gis.street.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import ar.com.zauber.commons.dao.Closure;
import ar.com.zauber.commons.dao.exception.NoSuchEntityException;

import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

/**
 * TODO Descripcion de la clase. Los comenterios van en castellano.
 * 
 * 
 * @author Juan F. Codagnone
 * @since Sep 22, 2007
 */
public class UsigRouter {
    private final JdbcTemplate jdbcTemplate;
    private final static String SQL_NEDGE = 
        "select gid,source, target, distance(the_geom, GeomFromText(?,4326)) "
      + "from streets order by distance limit 1";
    private final WKTReader reader = new WKTReader();
    
    public UsigRouter(final JdbcTemplate jdbcTemplate) {
        Validate.notNull(jdbcTemplate);
        
        this.jdbcTemplate = jdbcTemplate;
    }

    public final void route(final Point from, final Point to, 
            final Closure<LineString> ret) {
        Validate.notNull(from);
        Validate.notNull(to);
        Validate.notNull(ret);
        
        int vertex_from = sourceOrTarget(from, findNearestEdge(from));
        int vertex_to = sourceOrTarget(to, findNearestEdge(to));
//        System.out.println(vertex_from);
//        System.out.println(vertex_to);
//        String s = "select nomoficial,alt_izqini, d.* from (select * from rutear_ba('streets',?,?)) d, streets g where d.edge_id = g.gid ORDER BY step;";
          String s = "SELECT AsText(LineMerge(Collect(the_geom))) FROM streets WHERE gid in (SELECT edge_id  FROM shortest_path('SELECT gid as id, source, target, cost   FROM streets', ?,  ?, false, false))";
        jdbcTemplate.query(s, 
                new Object[]{vertex_from, vertex_to}, 
                new ResultSetExtractor() {
            public Object extractData(final ResultSet rset) throws SQLException,
                    DataAccessException {
                int i = 0;
                while(rset.next()) {
                    try {
                        ret.execute((LineString) reader.read(rset.getString(1)));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    
                }
        
                return null;
            }
        });
    }
    
    private final RoutingEdge findNearestEdge(final Point geom) {
        final List<RoutingEdge> ret = new ArrayList<RoutingEdge>();
        
        jdbcTemplate.query(SQL_NEDGE, new Object[]{geom.toString()},
                new ResultSetExtractor() {
                    public Object extractData(final ResultSet rs)
                            throws SQLException, DataAccessException {
                        
                        while(rs.next()) {
                            final RoutingEdge edge = new RoutingEdge();
                            edge.gid = rs.getLong("gid");
                            edge.source = rs.getLong("source");
                            edge.target = rs.getLong("target");
                            edge.distance = rs.getLong("distance");
                            
                            ret.add(edge);
                        }
                        return null;
                    }
            }
        );
        
        if(ret.size() == 0) {
            throw new NoSuchEntityException(geom);
        }
        if(ret.size() > 1) {
            throw new IllegalStateException("bla");
        }
        
        return ret.get(0);
    }
    
    private final int sourceOrTarget(Point geom, RoutingEdge edge) {
        String s = "select distance(line_interpolate_point(LineMerge(the_geom),0), GeomFromText(?, 4326)), distance(line_interpolate_point(LineMerge(the_geom),1), GeomFromText(?, 4326)),source, target from streets where gid=?";
        final List<Integer> ret = new ArrayList<Integer>();    
        jdbcTemplate.query(s, new Object[]{geom.toString(), geom.toString(), edge.gid},
                new ResultSetExtractor() {
                    public Object extractData(final ResultSet rs)
                            throws SQLException, DataAccessException {
                        
                        while(rs.next()) {
                            int r;
                            if(rs.getDouble(1) > rs.getDouble(2)) {
                                r = rs.getInt(4);
                            } else {
                                r = rs.getInt(3);
                            }
                            ret.add(r);
                        }
                        
                        return null;
                    }
            
        });
        
        if(ret.size() == 0) {
            throw new NoSuchEntityException(geom);
        }
        if(ret.size() > 1) {
            throw new IllegalStateException("bla");
        }
        
        
        return ret.get(0);
    }
}

class RoutingEdge {
    long gid;
    long source;
    long target;
    double distance;
}