/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.spring.servlet.view;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.web.servlet.view.tiles.TilesJstlView;


/**
 * 
 * Custom {@link org.springframework.web.servlet.view.TilesJstlView} that lets 
 * you expose extra helper objects to the JSPs. All the added attributed are 
 * prefixed with a prefix to avoid namespace problems.
 * <p>
 * It take it parameters from the Application Context. You must define the bean
 * <code>customHelpersJstlViewMap</code> and
 * <code>customHelpersJstlViewPrefix</code>.
 * 
 * @author Juan F. Codagnone
 * @author Fernando J. Zunino
 * @since Oct 4, 2005
 */
public class CustomHelpersTilesJstlView extends TilesJstlView {
    /** map of helpers */
    private Map<String, Object> helperMap;
    
    /** 
     * prefix each helper name to help to mantain a namespace between model
     * objects and helper objects
     */
    private String prefix = "";

    /** @see JstlView#initApplicationContext() */
    @SuppressWarnings("unchecked")
    @Override
    protected final void initApplicationContext() {
        super.initApplicationContext();
        
        helperMap = (Map<String, Object>)getApplicationContext().getBean(
                "customHelpersJstlViewMap");
        try {
            String p = (String)getApplicationContext().getBean(
                    "customHelpersJstlViewPrefix");
            if(p != null) {
                this.prefix = p;
            }
        } catch (NoSuchBeanDefinitionException e) {
            // this is ok
        }
        
        Validate.notNull(helperMap, "helperMap");
    }

    
    /** @see JstlView#exposeHelpers(javax.servlet.http.HttpServletRequest)*/
    @Override
    protected final void exposeHelpers(final HttpServletRequest request) 
    throws Exception {
        /* exposeModelAsRequestAttributes(helperMap, request); */
        for(Map.Entry<String, Object> entry : helperMap.entrySet()) {
            String modelName =  entry.getKey();
            final Object modelValue = entry.getValue();
            if(modelName == null || modelValue == null) {
                throw new ServletException("Null entry");
            } 
            modelName = prefix + modelName;
            if(request.getAttribute(modelName) != null) {
                if(logger.isWarnEnabled()) {
                    logger.warn("Will override existing model object `" 
                            + modelName + "' of type [" 
                            + modelValue.getClass().getName() + "]" 
                            + "to request in InternalResourceView '" 
                            + getBeanName() + "'");
                }
            }
            
            request.setAttribute(modelName, modelValue);
            if (logger.isDebugEnabled()) {
                logger.debug("Added model object '" + modelName 
                        + "' of type [" + modelValue.getClass().getName()
                        + "] to request in InternalResourceView '" 
                        + getBeanName() + "'");
            }
        }
    }
}
