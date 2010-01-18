/**
 * Copyright (c) 2005-2010 Zauber S.A. <http://www.zauber.com.ar/>
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
package ar.com.zauber.commons.gis.street;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.springframework.jdbc.core.JdbcTemplate;

import ar.com.zauber.commons.gis.street.StreetsDAO.GuessStreetResult;
import ar.com.zauber.commons.gis.street.impl.SQLStreetsDAO;

/**
 * Test para {@link SQLStreetsDAO#guessStreetName(List, String)}
 * 
 * @author Juan F. Codagnone
 * @since Sep 21, 2007
 */
public class GuessStreetTest extends TestCase {

    /** test */
    public final void testFoo() throws Exception {
          final String [] alternativas = {
                  "24 DE NOVIEMBRE",
                  "ALBERTI",
                  "BOLIVAR",
                  "BRASIL AV.",
                  "CATAMARCA",
                  "CEVALLOS, Virrey",
                  "CHACABUCO",
                  "COMBATE DE LOS POZOS",
                  "DEAN FUNES",
                  "ENTRE RIOS AV.",
                  "ESQUEL",
                  "GIORELLO, PABLO",
                  "HORNOS, Gral.",
                  "IRIGOYEN, BERNARDO de",
                  "JUJUY AV.",
                  "LA RIOJA",
                  "LIMA",
                  "LUCA, ESTEBAN de",
                  "MATHEU",
                  "MOMPOX",
                  "PERU",
                  "PICHINCHA AV.",
                  "PIEDRAS",
                  "SAENZ PE?A, LUIS, PRES.",
                  "SALTA",
                  "SAN JOSE",
                  "SANCHEZ DE LORIA AV.",
                  "SANTIAGO DEL ESTERO",
                  "SOLIS",
                  "TACUARI",
                  "URQUIZA, Gral.",
          };
          
          SQLStreetsDAO streetsDAO = new SQLStreetsDAO(new JdbcTemplate());
          final List<GuessStreetResult>l = 
              streetsDAO.guessStreetName(Arrays.asList(alternativas), 
                      "  GENERAL,   HORNOS   .");
          System.out.println(l);
          
    }
}
