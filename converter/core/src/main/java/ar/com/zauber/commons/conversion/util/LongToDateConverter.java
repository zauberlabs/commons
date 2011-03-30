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
package ar.com.zauber.commons.conversion.util;

import java.util.Date;

import ar.com.zauber.commons.conversion.ConversionContext;
import ar.com.zauber.commons.conversion.Converter;

/**
 * Crea una fecha en base a un long
 * 
 * @author Juan F. Codagnone
 * @since Sep 2, 2010
 */
public class LongToDateConverter implements Converter<Long, Date> {

    /** @see Converter#convert(Object, ConversionContext) */
    public final Date convert(final Long source, final ConversionContext ctx) {
        return new Date(source);
    }
}
