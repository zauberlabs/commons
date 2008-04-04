/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.gis.street.impl.parser;

import java.util.Collection;

import ar.com.zauber.commons.gis.Result;
import ar.com.zauber.commons.gis.street.StreetsDAO;

/**
 * Representa un patron de forma de escribir direcciones
 * 
 * @author Christian Nardi
 * @since Sep 28, 2007
 */
public interface AddressPattern {
    /** expresión regular que tienen los nombres de las calles */
    String STREET_NAME_PATTERN = "[[a-zA-Z0-9\\.]+\\s*]";
    
    /** busca calles */
    Collection<Result> getAddressResult(String text, StreetsDAO streetsDAO);
}
