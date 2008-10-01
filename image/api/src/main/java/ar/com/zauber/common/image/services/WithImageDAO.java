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
package ar.com.zauber.common.image.services;

import java.io.Serializable;

import ar.com.zauber.common.image.model.WithImage;



/**
 * TODO Brief description.
 * 
 * TODO Detail
 * 
 * @author Gabriel V. Ba�os
 * @since 28/06/2006
 */
public interface WithImageDAO {

    /**
     * @param id the id of the item with image
     * @return ble
     */
    WithImage getWithImage(Serializable id);
}
