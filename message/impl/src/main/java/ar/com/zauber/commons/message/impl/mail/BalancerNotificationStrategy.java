/**
 * Copyright (c) 2005-2012 Zauber S.A. <http://www.zaubersoftware.com/>
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
package ar.com.zauber.commons.message.impl.mail;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.com.zauber.commons.message.Message;
import ar.com.zauber.commons.message.NotificationAddress;
import ar.com.zauber.commons.message.NotificationStrategy;

/**
 * Balanced (2) {@link NotificationStrategy}.
 * 
 * 
 * @author Andrés Moratti
 * @since Jan 27, 2011
 */
public class BalancerNotificationStrategy implements NotificationStrategy {

    private final Logger logger = LoggerFactory.getLogger(
            BalancerNotificationStrategy.class);
    
    private final double ratio;
    private final NotificationStrategy strategy1;
    private final NotificationStrategy strategy2;
    
    
    /**
     * Creates the BalancerNotificationStrategy.
     *
     * @param ratio 1 sends everything over strategy1, 0 over strategy2
     * @param strategy1
     * @param strategy2
     */
    public BalancerNotificationStrategy(final double ratio,
            final NotificationStrategy strategy1, 
            final NotificationStrategy strategy2) {
        Validate.isTrue(ratio >= 0.0 && ratio <= 1.0);
        Validate.notNull(strategy1);
        Validate.notNull(strategy2);
        
        this.ratio = ratio;
        this.strategy1 = strategy1;
        this.strategy2 = strategy2;
    }


    /** @see NotificationStrategy#execute(NotificationAddress[], Message) */
    @Override
    public final void execute(final NotificationAddress[] addresses, 
            final Message message) {
        
        if(ratio == 0.0) {
            logger.debug("Sending using strategy2");
            strategy2.execute(addresses, message);
        } else if(ratio == 1.0) {
            logger.debug("Sending using strategy1");
            strategy1.execute(addresses, message);
        } else {
            if(Math.random() > ratio) {
                logger.debug("Sending using strategy2");
                strategy2.execute(addresses, message);
            } else {
                logger.debug("Sending using strategy1");
                strategy1.execute(addresses, message);
            }
        }
    }

}