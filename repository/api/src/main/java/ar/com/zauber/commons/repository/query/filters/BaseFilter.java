/**
 * Copyright (c) 2005-2015 Zauber S.A. <http://flowics.com/>
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
package ar.com.zauber.commons.repository.query.filters;


/**
 * Clase base para todos los filtros, permite negarlo.
 *
 *
 * @author Martin A. Marquez
 * @since Sep 24, 2007
 */
public abstract class BaseFilter implements Filter {

    private Boolean negated = Boolean.FALSE;

    public final Boolean getNegated() {
        return negated;
    }

    public final void setNegated(final Boolean negated) {
        this.negated = negated;
    }

}
