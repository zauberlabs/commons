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

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;

import junit.framework.TestCase;
import ar.com.zauber.commons.dao.Paging;
import ar.com.zauber.commons.gis.Result;
import ar.com.zauber.commons.gis.street.StreetsDAO;
import ar.com.zauber.commons.gis.street.model.results.GeocodeResult;
import ar.com.zauber.commons.gis.street.model.results.IntersectionResult;
import ar.com.zauber.commons.gis.street.model.results.StreetResult;

/**
 * Tests {@link EhCacheStreetsDAO}
 *
 *
 * @author Juan F. Codagnone
 * @since Jan 7, 2009
 */
public class EhCacheStreetsDAOTest extends TestCase {
    private int n = 0;
    /** test */
    public final void testFoo() {
        File f1 = new File(System.getProperty("java.io.tmpdir"), 
                "streetsdao.test.data");
        File f2 = new File(System.getProperty("java.io.tmpdir"), 
            "streetsdao.test.index");
        f1.delete();
        f1.deleteOnExit();
        f2.delete();
        f2.deleteOnExit();
        
        try {
            CacheManager cacheManager = new CacheManager(getClass().getResource(
                    "ehcache.xml"));
            Cache cache = cacheManager.getCache("streetsdao.test");
            StreetsDAO streetsDAO = new EhCacheStreetsDAO(targetStreetsDAO, cache);
            System.out.println(streetsDAO.getStreets("JUNIN 1128, SAN LUIS, ARGENTINA"));
            System.out.println(streetsDAO.getStreets("JUNIN 1128, SAN LUIS, ARGENTINA"));
            System.out.println(n);
            cache.flush();
            cacheManager.shutdown();
            cacheManager = new CacheManager(getClass().getResource("ehcache.xml"));
            cache = cacheManager.getCache(cacheManager.getCacheNames()[0]);
            streetsDAO = new EhCacheStreetsDAO(targetStreetsDAO, cache);
            List<Result> r = streetsDAO.getStreets("JUNIN 1128, SAN LUIS, ARGENTINA");
            assertEquals(1, n);
            assertEquals(1, r.size());
            final Point p = r.get(0).getPoint();
            assertEquals(-54.0, p.getX());
            assertEquals(-38.0, p.getY());
        } finally {
            f1.delete();
            f2.delete();
        }
        
    }
    
    private final StreetsDAO targetStreetsDAO = new StreetsDAO() {
        private final GeometryFactory geometryFactory = new GeometryFactory(
                new PrecisionModel(), 4326);
        
        public List<Result> getStreets(final String text) {
            final List<Result> r = new ArrayList<Result>();
            r.add(new StreetResult("hola", geometryFactory
                    .createPoint(new Coordinate(-54, -38)),
                    "Buenos aires", "AR"));
            n++;
            return r;
        }

        
        public boolean fullNameStreetExist(final String name) {
            // TODO Auto-generated method stub
            return false;
        }

        public Collection<GeocodeResult> geocode(final String street, 
                final int altura) {
            // TODO Auto-generated method stub
            return null;
        }

        public Collection<GeocodeResult> geocode(final String street, 
                final int altura,
                final int id) {
            // TODO Auto-generated method stub
            return null;
        }

        public Collection<IntersectionResult> getIntersection(
                final String street1, final String street2) {
            // TODO Auto-generated method stub
            return null;
        }

        public List<String> getIntersectionsFor(final String fullStreetName) {
            // TODO Auto-generated method stub
            return null;
        }

        public List<String> getSinonimos(final String fullStreetName) {
            // TODO Auto-generated method stub
            return null;
        }
        public List<GuessStreetResult> guessStreetName(
                final List<String> streets, final String unnomalizedStreetName) {
            // TODO Auto-generated method stub
            return null;
        }

        public List<Result> suggestAddresses(final String text) {
            // TODO Auto-generated method stub
            return null;
        }

        public List<String> suggestStreets(final String beggining, 
                final Paging paging) {
            // TODO Auto-generated method stub
            return null;
        }
    };
}
