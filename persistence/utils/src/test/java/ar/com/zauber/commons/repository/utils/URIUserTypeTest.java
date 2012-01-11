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
package ar.com.zauber.commons.repository.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import junit.framework.Assert;

import org.hibernate.HibernateException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * TODO: Descripcion de la clase. Los comentarios van en castellano.
 * 
 * 
 * @author Marcelo Turrin
 * @since Sep 13, 2010
 */
public class URIUserTypeTest {
    private  URIUserType type;
    private URI value;
    
    @Before
    public void onBefore() throws URISyntaxException{
        type = new URIUserType();
        value = new URI("http://www.zaubersoftware.com");
    }

    /**
     * Test method for {@link URIUserType#isMutable()}.
     */
    @Test
    public void testIsMutable() {
        Assert.assertFalse(type.isMutable());
    }

    /**
     * Test method for {@link URIUserType#returnedClass()}.
     */
    @Test
    public void testReturnedClass() {
        Assert.assertEquals(URI.class, type.returnedClass());
    }

    /**
     * Test method for {@link URIUserType#sqlTypes()}.
     */
    @Test
    public void testSqlTypes() {
        Assert.assertEquals(1, type.sqlTypes().length);
    }

    /**
     * Test method for {@link URIUserType#nullSafeGet(ResultSet, String[], Object)}.
     * @throws SQLException 
     * @throws HibernateException 
     * @throws URISyntaxException 
     */
    @Test
    public void testNullSafeGet() throws HibernateException, SQLException, URISyntaxException {
        final String rta = "respuesta";
        ResultSet rs = Mockito.mock(ResultSet.class);
        String[] names = { "no importa"};
        Object owner = null;
        
        Assert.assertNull(type.nullSafeGet(rs, names, owner));
        
        Mockito.when(rs.getString(Mockito.any(String.class))).thenReturn(rta );
        Assert.assertEquals(new URI(rta), type.nullSafeGet(rs, names, owner));
    }
    
    /** nullSafeGet no puede recibir null en la lista de nombres*/
    @Test(expected = IllegalArgumentException.class)
    public void testNullSafeGetNullNames() throws HibernateException, SQLException{
        type.nullSafeGet(Mockito.mock(ResultSet.class), null, null);
    }
    
    /** nullSafeGet no puede recibir null en el result set*/
    @Test(expected = IllegalArgumentException.class)
    public void testNullSafeGetNullRs() throws HibernateException, SQLException{
        type.nullSafeGet(null, new String[] {}, null);
    }
    
    /** nullSafeGet no puede recibir fue la lista vacia */
    @Test(expected = IllegalArgumentException.class)
    public void testNullSafeGetNoNames() throws HibernateException, SQLException{
        type.nullSafeGet(Mockito.mock(ResultSet.class), new String[] {}, null);
    }

    /**
     * Test method for {@link URIUserType#nullSafeSet(PreparedStatement, Object, int)}.  
     */
    @Test
    public void testNullSafeSet() throws HibernateException, SQLException  {
        final PreparedStatement st = Mockito.mock(PreparedStatement.class);
        int index = 1;
        
        type.nullSafeSet(st, null, index );
        Mockito.verify(st).setNull(Mockito.anyInt(), Mockito.anyInt());
        Mockito.verify(st, Mockito.never()).setString(Mockito.anyInt(), Mockito.anyString());
        
        Mockito.reset(st);
        type.nullSafeSet(st, value, index );
        Mockito.verify(st, Mockito.never()).setNull(Mockito.anyInt(), Mockito.anyInt());
        Mockito.verify(st).setString(Mockito.anyInt(), Mockito.anyString());
        
        
    }

    /**
     * Test method for {@link URIUserType#assemble(java.io.Serializable, Object)}.
     */
    @Test
    public void testAssemble() {
        Assert.assertEquals(value, type.assemble(value,null));   
    }

    /**
     * Test method for {@link URIUserType#deepCopy(Object)}. 
     */
    @Test
    public void testDeepCopy() {
        Assert.assertEquals(value, type.deepCopy(value));   
    }

    /**
     * Test method for {@link URIUserType#disassemble(Object)}.
     */
    @Test
    public void testDisassemble() {        
        Assert.assertEquals(value, type.disassemble(value));   
    }

    /**
     * Test method for {@link URIUserType#replace(Object, Object, Object)}.
     */
    @Test
    public void testReplace() {
        Assert.assertEquals(value, type.replace(value, null, null));   
    }

}
