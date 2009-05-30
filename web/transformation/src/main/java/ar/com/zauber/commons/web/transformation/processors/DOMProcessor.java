/**
 *  Copyright (c) 2008-2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.transformation.processors;

import org.w3c.dom.Document;

/**
 * Estrategia para procesar un arbol DOM. Por ejemplo
 * se puede utilizar para remover partes del árbol, o 
 * cambiar algunos atributos.
 * 
 * @author Matías Tito
 * @since Oct 22, 2008
 */
public interface DOMProcessor {
    
    /**
     * Metodo quese encarga de procesar el arbol DOM.
     * @param uri         URI del document
     * @param document    Arbol DOM que representa al documento
     */
    void process (String uri, Document document);
}
