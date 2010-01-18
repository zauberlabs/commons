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
package ar.com.zauber.commons.conversion;

import org.apache.commons.lang.UnhandledException;

import ar.com.zauber.commons.conversion.config.ConversionConfig;

/**
 * <code>Converter</code> that can be configured using a <code>ConversionConfig</code>
 * describing which properties of the target class will be populated and how
 * to obtain their values from the source object.
 * It is constructed using a class that must have a default empty constructor. 
 * 
 * 
 * @author Juan Edi
 * @since Nov 16, 2009
 */
public class ConfigurableJavaBeanConverter<S, T> extends
        ConfigurableMapper<S, T> implements Converter<S, T> {

    private Class<T> clazz;
    


    /**
     * Creates the ConfigurableJavaBeanConverter.
     *
     * @param clazz
     *              The class of the object to be returned.
     */
    public ConfigurableJavaBeanConverter(Class<T> clazz) {
        super(new ConversionConfig<S>());
        this.clazz = clazz;
    }


    /** @see Converter#convert(java.lang.Object, ConversionContext) */
    public T convert(S source, ConversionContext ctx) {
        try {
            T targetInstance = this.clazz.newInstance();
            this.map(source, targetInstance, ctx);
            return targetInstance;
        } catch (InstantiationException e) {
            throw new UnhandledException(e);
        } catch (IllegalAccessException e) {
            throw new UnhandledException(e);
        }
    }


    /**
     * Returns the clazz.
     * 
     * @return <code>Class<T></code> with the clazz.
     */
    public Class<T> getClazz() {
        return clazz;
    }

    
}
