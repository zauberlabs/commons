/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.social.facebook;

/**
 * Representa una cookie seteada por la API JavaScript de Facebook
 * 
 * @author Francisco J. González Costanzó
 * @since Feb 24, 2010
 */
public class FacebookCookie {
    
    /** USER_SUFFIX */
    public static final String USER_SUFFIX = "_user";
    /** SESSION_SUFFIX */
    public static final String SESSION_SUFFIX = "_session_key";
    
    private final Long fbUID;
    private final String fbSession;
    
    /**
     * Creates the FacebookCookie.
     *
     * @param fbUID
     * @param fbSession
     */
    public FacebookCookie(final Long fbUID, final String fbSession) {
        this.fbUID = fbUID;
        this.fbSession = fbSession;
    }
    
    public final String getFbSession() {
        return fbSession;
    }
    
    public final Long getFbUID() {
        return fbUID;
    }
    

}
