/*
 * Copyright (c) 2006 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.syndication;

import java.io.IOException;
import java.io.Reader;


/**
 * Obtiene un reader de un feed
 * 
 * @author Juan F. Codagnone
 * @since Jul 2, 2006
 */
public interface FeedReader {

    Reader getReader() throws IOException;
}
