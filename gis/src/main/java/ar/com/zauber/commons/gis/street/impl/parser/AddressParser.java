/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.gis.street.impl.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ar.com.zauber.commons.gis.Result;
import ar.com.zauber.commons.gis.street.StreetsDAO;
import ar.com.zauber.commons.gis.street.model.address.IntersectionAddress;
import ar.com.zauber.commons.gis.street.model.address.NormalAddress;

/**
 * Class para parsear direcciones
 * 
 * @author Christian Nardi
 * @since Sep 28, 2007
 */
public final class AddressParser {
     
    final private Collection<AddressPattern> patterns;
    
    
    
    /**
     * Creates the AddressParser.
     *
     * @param patterns
     */
    public AddressParser(final Collection<AddressPattern> patterns) {
        super();
        this.patterns = patterns;
    }

    /**
     * Parsea el texto intentando matchear algun tipo de resultado
     * {@link IntersectionAddress} o {@link NormalAddress}
     * @param text
     * @param streetsDAO 
     * @param paging
     * @param dao
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Result> parse(final String text, final StreetsDAO streetsDAO) {
        final List ret = new ArrayList();
        final String trimText = text.trim();
        
        for (AddressPattern pattern : patterns){
            final Collection result = pattern.getAddressResult(trimText, streetsDAO);
            if (result != null) {
                ret.addAll(result);
            }
        }
        return ret;
    }
    
}
