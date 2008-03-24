/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.facebook.utils.impl;

import javax.servlet.http.HttpServletRequest;

import ar.com.zauber.commons.facebook.utils.CallbackRequestValidator;

/**
 * {@link CallbackRequestValidator} que siempre dice que si!
 * 
 * @author Juan F. Codagnone
 * @since Dec 24, 2007
 */
public class PromiscuousCallbackRequestValidator 
       implements CallbackRequestValidator {

    /** @see CallbackRequestValidator#validate(HttpServletRequest) */
    public final boolean validate(final HttpServletRequest request) {
        return true;
    }

}
