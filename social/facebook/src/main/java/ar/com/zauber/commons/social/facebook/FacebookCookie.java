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
