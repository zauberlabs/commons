/*
 * Copyright (c) 2009 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.assets;

/**
 * Utiliy class to define public constants
 * 
 * 
 * @author Mariano Cortesi
 * @since Dec 14, 2009
 */
public final class Assets {

    public static final String REPOSITORY_KEY = 
        Assets.class.getName() + ".__repository__";
    /** WebContext property key */
    public static final String WEBCONTEXT_KEY = Assets.class.getName() + "__web_context__";
    
    /** Name of the default set */
    public static final String DEFAULT_SET = "_default_";
    
    /** Default ApplicationVersion property key */
    public static final String APPVERSION_KEY = "applicationVersion";

    /** not instanciable */
    private Assets() { }
}
