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
package ar.com.zauber.commons.social.twitter.api.streaming.filter;

/**
 * Models a geographic square, specified by two points: the south west and the
 * north east corners of the box.
 * 
 * 
 * @author Francisco J. González Costanzó
 * @since Sep 20, 2010
 */
public interface BoundingBox {

    /**
     * South-West Latitude.
     */
    double getSwLat();

    /**
     * South-West Longitude.
     */
    double getSwLong();

    /**
     * North-East Latitude.
     */
    double getNeLat();

    /**
     * North-East Longitude.
     */
    double getNeLong();

}
