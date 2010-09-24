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
package ar.com.zauber.commons.social.twitter.api.streaming.filter;


/**
 * A Streaming API filter.
 * 
 * 
 * @author Francisco J. González Costanzó
 * @since Sep 20, 2010
 */
public interface StreamingFilter {

    /**
     * The number of previous statuses to fetch before real time.
     */
    int getPreviousStatuses();

    /**
     * The users id to receive public tweets from. Never returns null.
     */
    int[] getUserIds();

    /**
     * Keywords to track. Never returns null.
     */
    String[] getKeywords();

    /**
     * Geographical bounding boxes to track. Never returns null.
     */
    BoundingBox[] getBoxes();

}
