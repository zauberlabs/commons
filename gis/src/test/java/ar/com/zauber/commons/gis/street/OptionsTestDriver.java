/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.gis.street;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import javax.sql.DataSource;

import junit.framework.TestCase;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import ar.com.zauber.commons.gis.street.impl.SQLStreetsDAO;
import ar.com.zauber.commons.gis.street.model.results.GeocodeResult;

/**
 * Este test necesita que esté instalada una base de postgres con la base gis
 * creada y cargada.
 * @author epere4
 */
public class OptionsTestDriver extends TestCase {
	private static String url = "jdbc:postgresql://localhost:5433/gis";
	private static String driver = "org.postgresql.Driver";
	private static String user = "gis";
	private static String password = "gis";
	
	private JdbcTemplate makeJdbcTemplate() {
		DataSource dataSource = new DriverManagerDataSource(driver, url, user, password);
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		return jdbcTemplate;
	}

	public void testConConexion() {
		JdbcTemplate jdbcTemplate = makeJdbcTemplate();
		StreetsDAO streetsDAOIgnoreCommonWords = new SQLStreetsDAO(jdbcTemplate, Arrays.asList(new Options[] {Options.IGNORE_COMMON_WORDS}), null);
		StreetsDAO streetsDAOIgnoreCommonWordsIgnoreExtraSpaces = new SQLStreetsDAO(jdbcTemplate, Arrays.asList(new Options[] {Options.IGNORE_COMMON_WORDS, Options.REMOVE_EXTRA_SPACES}), null);
		StreetsDAO streetsDAOIgnoreExtraSpaces = new SQLStreetsDAO(jdbcTemplate, Arrays.asList(new Options[] {Options.REMOVE_EXTRA_SPACES}), null);
		StreetsDAO streetsDAOComun = new SQLStreetsDAO(jdbcTemplate);
		
		Collection<GeocodeResult> result1 =  streetsDAOIgnoreCommonWords.geocode("Avenida Santa Fe", 1500);
		Collection<GeocodeResult> result2 =  streetsDAOIgnoreCommonWords.geocode("Santa Fe", 1500);
		Collection<GeocodeResult> result3 =  streetsDAOComun.geocode("Santa Fe", 1500);
		Collection<GeocodeResult> result4 =  streetsDAOIgnoreCommonWordsIgnoreExtraSpaces.geocode("Avenida  Santa   Fe", 1500);
		Collection<GeocodeResult> result5 =  streetsDAOIgnoreExtraSpaces.geocode("  Santa   Fe", 1500);
		
		assertGeocodeResultsEquals(result1, result2);
		assertGeocodeResultsEquals(result1, result3);
		assertGeocodeResultsEquals(result1, result4);
		assertGeocodeResultsEquals(result1, result5);
	}
	
	private void assertGeocodeResultsEquals(Collection<GeocodeResult> result1, Collection<GeocodeResult> result2) {
		assertEquals(result1.size(), result2.size());
		
		Iterator<GeocodeResult> iter1 = result1.iterator();
		Iterator<GeocodeResult> iter2 = result2.iterator();
		while (iter1.hasNext()) {
			GeocodeResult res1 = iter1.next();
			GeocodeResult res2 = iter2.next();
			assertEquals(res1.getAltura(), res2.getAltura());
			assertEquals(res1.getStreet(), res2.getStreet());
			assertEquals(res1.getPoint().getX(), res2.getPoint().getX());
			assertEquals(res1.getPoint().getY(), res2.getPoint().getY());
		}
	}
	
    public void testStreet() {
        JdbcTemplate jdbcTemplate = makeJdbcTemplate();
        StreetsDAO streetsDAO = new SQLStreetsDAO(jdbcTemplate, Arrays.asList(new Options[] {Options.IGNORE_COMMON_WORDS}), null);
        streetsDAO.getStreets("Quirno Costa");
    }
}
