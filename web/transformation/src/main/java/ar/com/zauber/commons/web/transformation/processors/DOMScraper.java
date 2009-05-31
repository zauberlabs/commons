/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.transformation.processors;

import java.io.Writer;
import java.util.Map;

import org.w3c.dom.Node;

/**
 * Scraper sobre documentos DOM (un html o un xml). Extraen la informacion
 * util writer (seguramente otro xml que sabe de un dominio especifico)
 * 
 * @author Juan F. Codagnone
 * @since May 31, 2009
 */
public interface DOMScraper {

    /** 
     * @param node  nodo DOM desde donde extraer la información
     * @param model model con datos extras necesarios para scrapear
     * @param writer donde dejar los resultados
     */
    void scrap(final Node node, Map<String, Object> model, 
            final Writer writer);
}
