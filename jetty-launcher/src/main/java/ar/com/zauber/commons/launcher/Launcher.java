/*
 * Copyright (c) 2005-2008 Zauber S.A. <http://www.zauber.com.ar/>
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
package ar.com.zauber.commons.launcher;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.ContextHandlerCollection;
import org.mortbay.jetty.handler.DefaultHandler;
import org.mortbay.jetty.handler.HandlerCollection;
import org.mortbay.jetty.handler.RequestLogHandler;
import org.mortbay.jetty.webapp.WebAppContext;

/**
 * Starts a web application via Jetty
 * 
 * @author Christian Nardi
 * @since Nov 12, 2007
 */
public class Launcher {
    /** <code>webApplicationPath</code> */
    private String webApplicationPath;
    /** <code>webApplicationPath</code> */
    private String contextPath;
    /** <code>port</code> */
    private int port;
    
    /**
     * Creates the Launcher.
     *
     * @param webApplicationPath el path a la aplicacion (puede ser un war)
     * @param contextPath donde publicar
     * @param port donde publicar
     */
    public Launcher(final String webApplicationPath, final String contextPath,
            final int port) {
        super();
        Validate.notNull(webApplicationPath);
        Validate.notNull(contextPath);
        Validate.isTrue(!StringUtils.strip(webApplicationPath).equals(""));
        Validate.isTrue(!StringUtils.strip(contextPath).equals(""));
        Validate.isTrue(port > 0);
        this.contextPath = contextPath;
        this.webApplicationPath = webApplicationPath;
        this.port = port;
    }

    /**
     * @param args
     * @throws Exception . 
     */
    public final void run() throws Exception {
        final Server server = new Server(port);
        final WebAppContext appContext = new WebAppContext();

        appContext.setContextPath(contextPath);
        appContext.setWar(webApplicationPath);

        ContextHandlerCollection contexts = (ContextHandlerCollection)
                server.getChildHandlerByClass(ContextHandlerCollection.class);
        
        if (contexts == null) {
            contexts = new ContextHandlerCollection();
            HandlerCollection handlers = (HandlerCollection)server.
                                getChildHandlerByClass(HandlerCollection.class);
            if (handlers == null) {
                handlers = new HandlerCollection();               
                server.setHandler(handlers);                            
                handlers.setHandlers(new Handler[]{contexts, 
                        new DefaultHandler(), new RequestLogHandler()});
            } else {
                handlers.addHandler(contexts);
            }
        }
        contexts.addHandler(appContext);
        appContext.start();
        server.start();
        server.join();
    }
}
