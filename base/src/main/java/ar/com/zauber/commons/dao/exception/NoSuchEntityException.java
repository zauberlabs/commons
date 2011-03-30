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
package ar.com.zauber.commons.dao.exception;


/**
 * Excepción indicando que no existe la entidad. [ Basada en el
 * NoSuchEntityException de Mariano de conae, nada mas que lo pasé a Runtime.
 * Seria interesante hacer tipada a la exception <T>, y poder cachear la
 * excepcion esperando un tipo especifico, pero el compilador no me deja --
 * wiki:Main.JuanCodagnone Sun Jun 19 00:32:02 ART 2005 ]
 * 
 * @since Jun 19, 2005
 */
public class NoSuchEntityException extends AbstractEntityException {
    private static final long serialVersionUID = -3346843923690605245L;

    /**
     * @see AbstractEntityException#AbstractEntityException(Object, Throwable)
     */
    public NoSuchEntityException(final Object entity, final Throwable th) {
        super(entity, th);
    }

    /**
     * @see AbstractEntityException#AbstractEntityException(Object)
     */
    public NoSuchEntityException(final Object entity) {
        super(entity);
    }
}
