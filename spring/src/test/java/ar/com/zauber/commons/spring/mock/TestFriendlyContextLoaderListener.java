/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.spring.mock;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;

/**
 * @see TestFriendlyContextLoader
 * 
 * @author Juan F. Codagnone
 * @since Nov 13, 2007
 */
public class TestFriendlyContextLoaderListener extends ContextLoaderListener {
    
    /** @see ContextLoaderListener#createContextLoader() */
    @Override
    protected final ContextLoader createContextLoader() {
        return new TestFriendlyContextLoader();
    }

}
