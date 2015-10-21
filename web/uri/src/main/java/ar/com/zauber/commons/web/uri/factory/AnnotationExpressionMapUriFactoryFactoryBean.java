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
package ar.com.zauber.commons.web.uri.factory;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import ar.com.zauber.commons.web.uri.UriExpression;
import ar.com.zauber.commons.web.uri.factory.ExpressionMapUriFactory.ExpressionTemplateFactory;
import ar.com.zauber.commons.web.uri.factory.ExpressionMapUriFactory.UriTemplateExpressionFactory;

/**
 * Crea un {@link ExpressionMapUriFactory} buscando anotaciones {@link UriExpression}
 * en los metodos de las clases que pertenecen a los paquetes #setp 
 * 
 * 
 * @author Juan F. Codagnone
 * @since Sep 5, 2010
 */
public class AnnotationExpressionMapUriFactoryFactoryBean 
     extends ClassPathScanningCandidateComponentProvider
  implements FactoryBean<ExpressionMapUriFactory> {
    
    private ExpressionTemplateFactory expressionTemplateFactory = 
        new UriTemplateExpressionFactory();
    
    private MetadataReaderFactory metadataReaderFactory = 
        new CachingMetadataReaderFactory();
    
    private ResourcePatternResolver resourcePatternResolver = 
        new PathMatchingResourcePatternResolver();
    
    private Map<String, String> extraUris;
    private String resourcePattern = DEFAULT_RESOURCE_PATTERN;
    private Collection<String> packages = Collections.emptyList();
    
    /** Creates the ExpessionMapUriFactoryFactoryBean. */
    public AnnotationExpressionMapUriFactoryFactoryBean() {
        super(true);
    }
    private static final String TYPE = UriExpression.class.getName();
    private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    /** @see FactoryBean#getObject() */
    public final ExpressionMapUriFactory getObject() throws IOException {
        final Map<String, ExpressionMapUriFactory.UriExpression> uris = 
            new HashMap<String, ExpressionMapUriFactory.UriExpression>();
        if(extraUris != null) {
            for(final Entry<String, String> entry : extraUris.entrySet()) {
                uris.put(entry.getKey(), new ExpressionMapUriFactory.UriExpression(
                        expressionTemplateFactory.create(entry.getValue())));
            }
        }
        
        for(final String basePackage : packages) {
            final String packageSearchPath = 
                ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX 
                + resolveBasePackage(basePackage) + "/" + this.resourcePattern;
            final Resource[] resources = resourcePatternResolver.getResources(
                    packageSearchPath);

            for(final Resource resource : resources) {
                final MetadataReader reader = metadataReaderFactory
                              .getMetadataReader(resource);
                final Set<MethodMetadata> metas = reader.getAnnotationMetadata()
                                                   .getAnnotatedMethods(TYPE);
                for(final MethodMetadata meta : metas) {
                    final Map<String, Object> attrs = 
                        meta.getAnnotationAttributes(TYPE);
                    final String description = (String) attrs.get("description");
                    uris.put(attrs.get("name").toString(),
                            new ExpressionMapUriFactory.UriExpression(
                                expressionTemplateFactory.create(
                                        attrs.get("value").toString()),
                                        description));
                }
            }
        }
        return new ExpressionMapUriFactory(uris);            
    }

    /** @see FactoryBean#getObjectType() */
    public final Class<?> getObjectType() {
        return ExpressionMapUriFactory.class;
    }

    /** @see FactoryBean#isSingleton() */
    public final boolean isSingleton() {
        return false;
    }
    
    /**
     * Uris "cableadas" que no se reconocieron desde el contexto
     */
    public final void setExtraUris(final Map<String, String> extraUris) {
        this.extraUris = extraUris;
    }

    public final MetadataReaderFactory getMetadataReaderFactory() {
        return metadataReaderFactory;
    }

    public final void setMetadataReaderFactory(
            final MetadataReaderFactory metadataReaderFactory) {
        this.metadataReaderFactory = metadataReaderFactory;
    }

    public final Map<String, String> getExtraUris() {
        return extraUris;
    }
    
    public final ResourcePatternResolver getResourcePatternResolver() {
        return resourcePatternResolver;
    }
    
    /** Sets the resourcePatternResolver. */
    public final void setResourcePatternResolver(
            final ResourcePatternResolver resourcePatternResolver) {
        Validate.notNull(resourcePatternResolver);
        this.resourcePatternResolver = resourcePatternResolver;
    }

    public final String getResourcePattern() {
        return resourcePattern;
    }

    /**
     * Set the resource pattern to use when scanning the classpath.
     * This value will be appended to each base package name.
     * @see #findCandidateComponents(String)
     * @see #DEFAULT_RESOURCE_PATTERN
     */
    public final void setResourcePattern(final String resourcePattern) {
        this.resourcePattern = resourcePattern;
    }

    public final Collection<String> getPackages() {
        return packages;
    }

    /** Paquetes a escanear en busca de anotaciones {@link UriExpression} */
    public final void setPackages(final Collection<String> packages) {
        Validate.notNull(packages);
        this.packages = packages;
    }
    
    public final ExpressionTemplateFactory getExpressionTemplateFactory() {
        return expressionTemplateFactory;
    }
    
    /** utilizado para crear las expresiones */
    public final void setExpressionTemplateFactory(
            final ExpressionTemplateFactory expressionTemplateFactory) {
        Validate.notNull(expressionTemplateFactory);
        this.expressionTemplateFactory = expressionTemplateFactory;
    }
}
