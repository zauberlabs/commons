/**
 * Copyright (c) 2005-2009 Zauber S.A. <http://www.zauber.com.ar/>
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
package ar.com.zauber.commons.secret;

import java.util.Date;

/**
 * Validates that dates didn't expired
 * 
 * @author Juan F. Codagnone
 * @since Jun 19, 2005
 */
public interface ExpirationDateValidator {

    /**
     * @param date
     *            the date to check
     * @return <code>true</code> if the date has not expired
     */
    boolean isValid(Date date);
    
    /** @return the last invalid date */
    Date getNowInvalid();
}
