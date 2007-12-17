/*
 * Copyright (c) 2005 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.message.impl.message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.beanutils.BeanToPropertyValueTransformer;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.support.ResourceBundleMessageSource;

import ar.com.zauber.commons.message.MessageFactory;


/**
 * A Message factory that renders the messages using velocity
 *
 * @author Martín Andrés Márquez
 * @since Dic 12, 2007
 */
public class ResourceBundleMessageFactory extends AbstractMessageFactory {
    
    ResourceBundleMessageSource resourceBundleMessageSource;
    
    /** 
     * Creates the MessageBundleMessageResolver.
     *  
     * @throws Exception on error
     */
    public ResourceBundleMessageFactory(
            ResourceBundleMessageSource resourceBundleMessageSource)
            throws Exception {
        this.resourceBundleMessageSource = resourceBundleMessageSource;
    }
    
    /** @see MessageFactory#renderString(java.lang.String, java.util.Map)
     * 
     * Try not to use this one.
     * 
     * */
    public final String renderString(final String message,
            final Map<String, Object> model) {
        
        List<Map.Entry<String, Object>> entries =
            new ArrayList<Map.Entry<String, Object>>(model.entrySet()); 
        Collections.sort(entries,
                new Comparator<Map.Entry<String, Object>>(){

                    public int compare(Map.Entry<String, Object> o1,
                            Map.Entry<String, Object> o2) {
                        
                        return o1.getKey().compareTo(o2.getKey());
                    }
        });
        
    
        Collection<Object> parameters = CollectionUtils.collect(
                entries, new BeanToPropertyValueTransformer("value"));
        
        
        return renderString(message, parameters.toArray());
    }

    /** @see MessageFactory#renderString(String, Object[]) */
    public String renderString(String message, Object[] params) {
        return resourceBundleMessageSource
        .getMessage(message, params, Locale.getDefault());
    }
}