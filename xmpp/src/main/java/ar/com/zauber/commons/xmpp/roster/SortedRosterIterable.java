/**
 * Copyright (c) 2005-2011 Zauber S.A. <http://www.zaubersoftware.com/>
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
package ar.com.zauber.commons.xmpp.roster;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;

/**
 * Iterable sobre la lista de contactos de la persona logueada.
 * Retorna la lista de forma ordenada lexicograficamente por jid.
 * 
 * @author Juan F. Codagnone
 * @since Jun 18, 2009
 */
public class SortedRosterIterable implements Iterable<String> {
    private final XMPPConnection connection;
    
    /** @param connection connection al servidor */
    public SortedRosterIterable(final XMPPConnection connection) {
        Validate.notNull(connection);
        
        this.connection = connection;
    }
    
    /** @see Iterable#iterator() */
    public final Iterator<String> iterator() {
        final Collection<RosterEntry> roster = connection.getRoster().getEntries();
        final List<String> ret = new LinkedList<String>();
        
        for(final RosterEntry entry : roster) {
            ret.add(entry.getUser());
        }
        
        Collections.sort(ret);
        return ret.iterator();
    }
}
