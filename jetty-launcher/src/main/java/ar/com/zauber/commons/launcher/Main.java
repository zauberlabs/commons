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

/**
 * Creates the launcher
 * 
 * @author Christian Nardi
 * @since Nov 14, 2007
 */
public final class Main {
    /**
     * Creates the Main.
     *
     */
    private Main() { 
    }
    
    /**
     * @param args 1 - port
     *             2 - application directory
     *             3 - context path
     * @throws Exception if the server could not be run
     */
    public static void main(final String[] args) throws Exception {
        if (args.length < 3) {
            showUssage();
            System.exit(1);
        }
        int port = 0;
        String contextpath = "";
        String path = "";
        try {
            port = Integer.parseInt(args[0]);
            if (port <= 0) {
                showUssage();
                System.exit(1);
            }
        } catch (final NumberFormatException e) {
            showUssage();
            System.exit(1);
        }
        contextpath = args[2];
        path = args[1];
        new Launcher(path, contextpath, port).run();
    }

    /**
     * 
     */
    private static void showUssage() {
        System.out.println("You need three arguments:");
        System.out.println("\t port");
        System.out.println("\t application directory or war path");
        System.out.println("\t context path");
    }
}

