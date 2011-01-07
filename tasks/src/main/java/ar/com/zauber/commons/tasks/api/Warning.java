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
package ar.com.zauber.commons.tasks.api;

import java.util.Date;

import ar.com.zauber.commons.validate.Validate;

/**
 * ValueObject representing a warning of a {@link Task}
 * 
 * @author Mariano A Cortesi
 * @since Dec 14, 2010
 */
public class Warning {

    private final Date timestamp;
    private final String warningType;
    private final String description;

    /** Creates the Warning. */
    public Warning(final Date date, final String warningName, final String description) {
        Validate.notNull(date, warningName);
        this.timestamp = new Date(date.getTime());
        this.warningType = warningName;
        this.description = description;
    }

    public final String getDescription() {
        return description;
    }

    public final Date getTimestamp() {
        return timestamp;
    }

    public final String getWarningType() {
        return warningType;
    }

    @Override
    public final String toString() {
        return "Warning[" + timestamp + ":" + warningType + "]";
    }

}
