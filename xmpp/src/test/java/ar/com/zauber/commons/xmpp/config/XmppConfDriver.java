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
package ar.com.zauber.commons.xmpp.config;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.jivesoftware.smack.XMPPConnection;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Prueba la configuracion de spring que trae este módulo
 * 
 * @author Juan F. Codagnone
 * @since Jun 19, 2009
 */
public class XmppConfDriver {

    /** test */
    @Test
    public final void foo() {
        final String base = "classpath:ar/com/zauber/commons/xmpp/config/";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{
           base + "connection-xmmp-commons-spring.xml",
           base + "properties-test-xmpp-commons-spring.xml",
        });
        final XMPPConnection connection = 
            (XMPPConnection) ctx.getBean("commonsZauberXMPPConnection");
        System.out.println(connection.getRoster().getEntries());
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("key", new String("asdad"));
        model.put("date", new GregorianCalendar(2009, 05, 20).getTime());
    }
}
