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
package ar.com.zauber.commons.dao.exception;


/**
 * The entry is duplicated
 * 
 * @author Juan F. Codagnone
 * @since Sep 1, 2005
 */
public final class DuplicatedEntityException extends AbstractEntityException {
    /**
     * @see AbstractEntityException#AbstractEntityException(Object, Throwable)
     */
    public DuplicatedEntityException(final Object entity, final Throwable th) {
        super(entity, th);
    }

    /**
     * @see AbstractEntityException#AbstractEntityException(Object)
     */
    public DuplicatedEntityException(final Object entity) {
        super(entity);
    }
}
