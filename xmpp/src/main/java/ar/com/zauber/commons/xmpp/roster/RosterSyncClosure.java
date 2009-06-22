/**
 * Copyright (c) 2005-2009 Zauber S.A. <http://www.zauber.com.ar/>
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

import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.Validate;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import ar.com.zauber.commons.dao.Closure;
import ar.com.zauber.commons.dao.closure.processors.MergeResult;
import ar.com.zauber.commons.dao.closure.processors.MergeResult.Operation;

/**
 * Aplica las reglas de sincronización a  una conexión de jabber.  
 * 
 * @author Juan F. Codagnone
 * @since Jun 19, 2009
 */
public class RosterSyncClosure implements Closure<MergeResult<String>> {
    private final XMPPConnection connection;
    /** constructor */
    public RosterSyncClosure(final XMPPConnection connection) {
        Validate.notNull(connection);
        
        this.connection = connection;
    }
    
    /** @see Closure#execute(Object) */
    public final void execute(final MergeResult<String> closure) {
        final Roster roster = connection.getRoster();
        
        try {
            if(closure.getOperation() == Operation.ADD) {
                roster.createEntry(closure.getEntity(), null, null);
            } else if(closure.getOperation() == Operation.REMOVE) {
                roster.removeEntry(roster.getEntry(closure.getEntity()));
            }
        } catch(final XMPPException e) {
            throw new UnhandledException(e);
        }
    }
}
