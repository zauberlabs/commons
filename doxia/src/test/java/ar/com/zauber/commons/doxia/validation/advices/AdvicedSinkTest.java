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
package ar.com.zauber.commons.doxia.validation.advices;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

import junit.framework.TestCase;

import org.apache.maven.doxia.sink.Sink;
import org.apache.maven.doxia.sink.SinkAdapter;

/**
 * unit test
 * 
 * @author Juan F. Codagnone
 * @since Nov 6, 2005
 */
public class AdvicedSinkTest extends TestCase {

    /** unit test */
    public final void testException() {
        final Sink sink = new AdvicedSink(new MethodBeforeAdvice[] {},
                new SinkAdapter() {
                    /** @see SinkAdapter#anchor(java.lang.String) */
                    public void anchor(final String arg0) {
                        throw new IllegalArgumentException("foo");
                    }
                });

        try {
            sink.anchor("ok");
            fail();
        } catch (final IllegalArgumentException e) {
            assertEquals(e.getMessage(), "foo");
        }
    }
}
