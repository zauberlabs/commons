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

    /** Javascripts List property key */
    public static final String JS_KEY = Assets.class.getName() + "__javascripts__";
    /** Css List property key */
    public static final String CSS_KEY = Assets.class.getName() + "__css__";
    /** WebContext property key */
    public static final String WEBCONTEXT_KEY = Assets.class.getName() + "__web_context__";

    /** not instanciable */
    private Assets() { }
}
