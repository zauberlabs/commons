/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.syndication;

import java.util.List;

import com.sun.syndication.feed.synd.SyndEntry;


/**
 * Retorna entradas sindicadas de otro sitio
 * 
 * @author Juan F. Codagnone
 * @since Jul 2, 2006
 */
public interface SyndicationManager {

    List<SyndEntry> getEntries();
}
