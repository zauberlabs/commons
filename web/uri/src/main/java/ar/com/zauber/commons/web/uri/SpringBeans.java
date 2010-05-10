/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.uri;

/**
 * Defines keys for spring beans dependencies within the module
 * 
 * @author Mariano Cortesi
 * @since May 10, 2010
 */
public final class SpringBeans {

    /** Spring Bean name for AssetRepository (MUST have REQUEST scope) */
    public static final String REPOSITORY_KEY = "requestAssetsRepository";

    /** Spring Bean name for Assets UriFactory */
    public static final String ASSET_URIFACTORY_KEY = "assetsUriFactory";
    
    /** Spring Bean name for links UriFactory */
    public static final String LINK_URIFACTORY_KEY = "linksUriFactory";

    /** utility class */
    private SpringBeans() {
    }
}
