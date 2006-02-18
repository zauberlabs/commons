/*
 * Copyright (c) 2005 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.secret;

import java.util.Date;


/**
 * Política de duración de fechas de vencimiento
 * 
 * @author Juan F. Codagnone
 * @since Jun 19, 2005
 * @param <T> generic type
 */
public interface ExpirationDatePolicy<T> {

    /**
     * @param guest a guest
     * @return a new expiration date for this guest
     */
    Date getExpirationDate(T guest);
}
