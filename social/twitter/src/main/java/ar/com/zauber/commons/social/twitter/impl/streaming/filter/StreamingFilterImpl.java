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
package ar.com.zauber.commons.social.twitter.impl.streaming.filter;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import ar.com.zauber.commons.social.twitter.api.streaming.filter.BoundingBox;
import ar.com.zauber.commons.social.twitter.api.streaming.filter.StreamingFilter;

/**
 * A Streaming API filter.
 * 
 * 
 * @author Francisco J. González Costanzó
 * @since Sep 15, 2010
 */
public final class StreamingFilterImpl implements StreamingFilter {

    private int previousStatuses = 0;
    private int[] userIds = new int[] {};
    private String[] keywords = new String[] {};
    private BoundingBox[] boxes = new BoundingBox[] {};

    @Override
    public int getPreviousStatuses() {
        return previousStatuses;
    }

    @Override
    public int[] getUserIds() {
        return userIds;
    }

    @Override
    public String[] getKeywords() {
        return keywords;
    }

    @Override
    public BoundingBox[] getBoxes() {
        return boxes;
    }
    
    public void setPreviousStatuses(final int previousStatuses) {
        this.previousStatuses = previousStatuses;
    }

    public void setUserIds(final int[] userIds) {
        this.userIds = userIds;
    }

    public void setKeywords(final String[] keywords) {
        this.keywords = keywords;
    }

    public void setBoxes(final BoundingBox[] boxes) {
        this.boxes = boxes;
    }

    /** @see java.lang.Object#toString() */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append(previousStatuses).append(userIds).append(keywords)
                .append(boxes).toString();
    }
}
