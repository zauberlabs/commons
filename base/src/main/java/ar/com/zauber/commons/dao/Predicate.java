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
package ar.com.zauber.commons.dao;

/**
 * Predicate
 * 
 * @author Juan F. Codagnone
 * @since Jul 12, 2008
 * @param <T> type
 */
@FunctionalInterface
public interface Predicate<T> extends java.util.function.Predicate<T> {
    /** @return true if the predicate is true */
    boolean evaluate(T value);
    
    
    default boolean test(T t) {
        return evaluate(t);
    }
}
