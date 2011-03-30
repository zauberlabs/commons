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
package ar.com.zauber.commons.repository.utils;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * <p>
 * Hibernate interceptor used to inject dependencies from a spring context
 * to achieve a Domain Driven Design
 * </p>
 * <p>
 * The classes wishing being injected with this interceptor, must be
 * annotated with the annotation {@link Configurable}. And each field
 * that will be injected must be transient and annotated with {@link Qualifier}.
 * The qualifier name can be used to set the bean name (else it uses the field name)
 * </p>
 * Example:
 * <pre>
 *  @Entity
 *  @Configurable
 *  public class DomainEntityExample implements Persistible {
 *      @Id
 *      private Long id;
 *      
 *      @Qualifier(value = "someService")
 *      private transient SomeService service;
 *      @Qualifier
 *      private transient SomeService someService;
 *     
 *      public final SomeService getService() {
 *          return service;
 *      }
 *      public final SomeService getSomeService() {
 *          return someService;
 *      }
 *      ....
 *  }
 *  
 * 
 * </pre>
 * @author Juan F. Codagnone
 * @since Mar 12, 2009
 */
public class SpringInjectionInterceptor extends EmptyInterceptor 
                                     implements ApplicationContextAware,
                                                InitializingBean {
    private static final long serialVersionUID = 2501930754071241444L;
    private ApplicationContext ctx;
    private final Map<Class<?>, DependencyInjection> dependencyCache = 
        new HashMap<Class<?>, DependencyInjection>();
    private final Logger log =  LoggerFactory.getLogger(SpringInjectionInterceptor.class);
 
    /**
     * @param persistibleClasses persistible classes that may requiere dependency
     *                           injection
     */
    public SpringInjectionInterceptor(final List<Class<?>> persistibleClasses) {
        Validate.noNullElements(persistibleClasses);
        
        for(final Class<?> clazz : persistibleClasses) {
            final List<Entry<Field, String>> fields = 
                new LinkedList<Entry<Field, String>>();
            for(final Annotation annotation : clazz.getAnnotations()) {
                if(annotation instanceof Configurable) {
                    final List<Field> clazzFields = new LinkedList<Field>();
                    Class<?> c = clazz;
                    while(c != null) {
                        clazzFields.addAll(Arrays.asList(c.getDeclaredFields()));
                        c = c.getSuperclass();
                    }
                    
                    
                    for(final Field field : clazzFields) {
                        for(final Annotation a : field.getAnnotations()) {
                            if(a instanceof Qualifier) {
                                String n = ((Qualifier) a).value();
                                if(StringUtils.isBlank(n)) {
                                    n = field.getName();
                                }
                                final String name = n;
                                fields.add(new Entry<Field, String>() {
                                    public Field getKey() {
                                        return field;
                                    }

                                    public String getValue() {
                                        return name;
                                    }

                                    public String setValue(final String value) {
                                        return null;
                                    }
                                });
                            }
                        }
                    }
                }
            }
            
            if(fields.size() > 0) {
                dependencyCache.put(clazz, new DependencyInjection(fields,
                        InitializingBean.class.isAssignableFrom(clazz)));
            }
        }
    }
    
    
    /** @see ApplicationContextAware#setApplicationContext(ApplicationContext) */
    public final void setApplicationContext(
            final ApplicationContext applicationContext) throws BeansException {
        Validate.notNull(applicationContext);
        this.ctx = applicationContext;
    }

    /** @see InitializingBean#afterPropertiesSet() */
    public final void afterPropertiesSet() throws Exception {
        Validate.notNull(ctx, "the application context is missing ");
    }

    
    /** @see EmptyInterceptor#onLoad(Object, Serializable, Object[], String[], 
     *                              Type[]) */
    @Override
    public final boolean onLoad(final Object entity, final Serializable id, 
            final Object[] state, final String[] propertyNames, 
            final Type[] types) {
        
        final DependencyInjection d = dependencyCache.get(entity.getClass());
        if(d != null) {
            d.inject(entity);
        }
        //embeeded objects may need to be injected
        if(state != null) {
            for(final Object o : state) {
                if(o != null) {
                    final DependencyInjection dio = 
                        dependencyCache.get(o.getClass());
                    if(dio != null) {
                        dio.inject(o);
                    }
                }
            }
        }
        return super.onLoad(entity, id, state, propertyNames, types);
    }
    
    /** injection */
    class DependencyInjection {
        private final List<Entry<Field, String>> fields;
        private final boolean initializingBean;

        /**  
         * @param fields list of fields and bean name to inject 
         * @param initializingBean <code>true</code> if the class implements
         *   {@link InitializingBean}. 
         */
        public DependencyInjection(final List<Entry<Field, String>> fields, 
                final boolean initializingBean) {
            Validate.noNullElements(fields);
            
            this.fields = fields;
            this.initializingBean = initializingBean;
        }
        
        /** inject the dependency */
        public void inject(final Object o) {
            for(final Entry<Field, String> field : fields) {
                final String beanName = field.getValue();
                try {
                    final Object dependency = ctx.getBean(beanName);
                    
                    final Field f =  field.getKey();
                    final boolean lastAccessible = f.isAccessible();
                    try {
                        f.setAccessible(true);
                        f.set(o, dependency);
                    } catch (final IllegalArgumentException e) {
                        throw new UnhandledException(e);
                    } catch (final IllegalAccessException e) {
                        throw new UnhandledException(e);
                    } finally {
                        f.setAccessible(lastAccessible);
                    }
                } catch(final NoSuchBeanDefinitionException e) {
                    log.error("unable to inject bean named `" + beanName  
                            + "' to class " + o.getClass().getName(), e);
                }
            }
            
            if(initializingBean) {
                try {
                    ((InitializingBean)o).afterPropertiesSet();
                } catch (final Exception e) {
                    throw new UnhandledException(e);
                }
            }
        }
    }
}

