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

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * Pruebas sobre {@link FacebookCookieFinder}
 * 
 * @author Pablo Grigolatto
 * @since May 17, 2010
 */
public class FacebookCookieFinderTest {

    /** https://tracker.zaubersoftware.com/view.php?id=7204 */
    @Test
    public final void testIssue7204() {
        FacebookCookieFinder.getCookie("foo", new MockHttpServletRequest());
    }
    
}
