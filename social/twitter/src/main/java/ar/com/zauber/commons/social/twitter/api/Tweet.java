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
package ar.com.zauber.commons.social.twitter.api;

import java.util.Date;

/**
 * A tweet.
 * 
 * 
 * @author Francisco J. Gonz�lez Costanz�
 * @since Sep 20, 2010
 */
public interface Tweet {

    /**
     * @return the date of publishing
     */
    Date getDate();

    /**
     * 
     * @return the author's username or screenname
     */
    String getFrom();

    /**
     * @return the tweet content
     */
    String getText();

    /**
     * @return the tweet id
     */
    Long getId();

    /**
     * @return the URL of the user avatar
     */
    String getAvatarUrl();
    
}
