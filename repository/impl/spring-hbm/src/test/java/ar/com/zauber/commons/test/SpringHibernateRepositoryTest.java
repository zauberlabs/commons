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
package ar.com.zauber.commons.test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.apache.commons.beanutils.BeanPropertyValueChangeClosure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.orm.hibernate3.HibernateTemplate;

import ar.com.zauber.commons.dao.Order;
import ar.com.zauber.commons.dao.Ordering;
import ar.com.zauber.commons.dao.Paging;
import ar.com.zauber.commons.repository.Persistible;
import ar.com.zauber.commons.repository.Reference;
import ar.com.zauber.commons.repository.Repository;
import ar.com.zauber.commons.repository.query.Query;
import ar.com.zauber.commons.repository.query.SimpleQuery;
import ar.com.zauber.commons.repository.query.aggreate.AggregateFunction;
import ar.com.zauber.commons.repository.query.aggreate.CompositeAggregateFunction;
import ar.com.zauber.commons.repository.query.aggreate.CountPropertyAggregateFunction;
import ar.com.zauber.commons.repository.query.aggreate.GroupPropertyAggregateFilter;
import ar.com.zauber.commons.repository.query.aggreate.MaxPropertyAggregateFunction;
import ar.com.zauber.commons.repository.query.aggreate.MinPropertyAggregateFunction;
import ar.com.zauber.commons.repository.query.aggreate.RowCountAggregateFilter;
import ar.com.zauber.commons.repository.query.connectors.AndConnector;
import ar.com.zauber.commons.repository.query.connectors.OrConnector;
import ar.com.zauber.commons.repository.query.filters.BaseFilter;
import ar.com.zauber.commons.repository.query.filters.BeginsLikePropertyFilter;
import ar.com.zauber.commons.repository.query.filters.CompositeFilter;
import ar.com.zauber.commons.repository.query.filters.ContainsLikePropertyFilter;
import ar.com.zauber.commons.repository.query.filters.EqualsPropertyFilter;
import ar.com.zauber.commons.repository.query.filters.Filter;
import ar.com.zauber.commons.repository.query.filters.GreaterThanPropertyFilter;
import ar.com.zauber.commons.repository.query.filters.NullFilter;
import ar.com.zauber.commons.repository.query.values.SimpleValue;
import ar.com.zauber.commons.repository.test.model.DireccionDummy;
import ar.com.zauber.commons.repository.test.model.PersonaDummy;
import ar.com.zauber.commons.test.utils.BaseTransactionalRollbackTest;

/**
 * Test <code>SpringHibernateRepository<code>.
 *
 * @author Martin A. Marquez
 * @since Aug 10, 2006
 */
public class SpringHibernateRepositoryTest extends
        BaseTransactionalRollbackTest {

    /**
     * Un codigo postal.
     */
    private static final String CODIGO_POSTAL_1 = "1111";

    /**ignoreCase
     * Un codigo postal.
     */
    private static final String CODIGO_POSTAL_2 = "2222";

    /**
     * Un codigo postal.
     */
    private static final String CODIGO_POSTAL_3 = "3333";

    /**
     * Repositorio para pruebas persistencia.
     */
    // CHECKSTYLE:ALL:OFF
    protected Repository repository;
    // CHECKSTYLE:ALL:ON

    /**
     * Crea el SpringHibernateRepositoryTest.
     *
     */
    public SpringHibernateRepositoryTest() {
        // setDefaultRollback(false);
    }

    /** test */
    public final void testRetrieve() {
        final Set<DireccionDummy> direcciones = crearGuardarDosDirecciones();

        final DireccionDummy guardada = direcciones.iterator().next();

        final DireccionDummy recuperada = repository
                .retrieve(new Reference<DireccionDummy>(DireccionDummy.class,
                        guardada.getId()));

        Assert.assertEquals(guardada.getDireccion(), recuperada.getDireccion());
    }

    /** test */
    public final void testABMColeccion() {
        final Query<DireccionDummy> nullQuery = new SimpleQuery<DireccionDummy>(
                DireccionDummy.class, new NullFilter(), null, null);

        List<DireccionDummy> direcciones = repository.find(nullQuery);

        Assert.assertEquals(0, direcciones.size());

        // se guarda la coleccion
        for (final Persistible direccion : crearGuardarDosDirecciones()) {
            repository.saveOrUpdate(direccion);
        }

        direcciones = repository.find(new SimpleQuery<DireccionDummy>(
                DireccionDummy.class, new NullFilter(), null, null));
        Assert.assertEquals(2, direcciones.size());

        for (final DireccionDummy direccion : direcciones) {
            repository.saveOrUpdate(direccion);
        }

        final Query<DireccionDummy> query = new SimpleQuery<DireccionDummy>(
                DireccionDummy.class, new EqualsPropertyFilter("direccion",
                        new SimpleValue("Santa Fe")), null, null);

        direcciones = repository.find(query);

        Assert.assertEquals(1, direcciones.size());
    }

    /** test */
    public final void testGuardarObtenerEliminarPersonaDummy() {
        // TODO Esta teniendo unos problemas al hacer findAll porque trae
        // varios
        // resultados por cada uno que existe.

        final Query<PersonaDummy> allQuery = new SimpleQuery<PersonaDummy>(
                PersonaDummy.class, new NullFilter(), null, null);

        Assert.assertEquals(0, repository.find(allQuery).size());

        PersonaDummy personaDummy = new PersonaDummy();

        personaDummy.setNombre("Luis Fernandez");
        personaDummy.setNumeroFiscal(new Integer(11111));
        personaDummy.setDescripcion("Descripcion " + personaDummy.getNombre());
        personaDummy.setDirecciones(crearGuardarDosDirecciones());

        repository.saveOrUpdate(personaDummy);

        final Collection<PersonaDummy> auxiPersonas = repository.find(allQuery);

        Assert.assertEquals(1, auxiPersonas.size());

        personaDummy = new PersonaDummy();
        personaDummy.setNombre("Juan Perez");
        personaDummy.setNumeroFiscal(new Integer(22222));
        personaDummy.setDescripcion("Descripcion " + personaDummy.getNombre());
        personaDummy.setDirecciones(crearGuardarDosDirecciones());

        repository.saveOrUpdate(personaDummy);

        Collection<PersonaDummy> personas = repository.find(allQuery);

        Assert.assertEquals(2, personas.size());

        repository.delete(personaDummy);

        personas = repository.find(allQuery);

        Assert.assertEquals(1, personas.size());
    }

    /** test */
    public final void testFiltersOrderingAndPaging() {

        createSomeData();

        List<PersonaDummy> personas;

        personas = repository.find(new SimpleQuery<PersonaDummy>(
                PersonaDummy.class, new BeginsLikePropertyFilter("nombre",
                        new SimpleValue("Al"), false), null, null));

        Assert.assertEquals(3, personas.size());

        final List<BaseFilter> filters = new ArrayList<BaseFilter>();

        final BaseFilter beginsLikeFilter = new BeginsLikePropertyFilter("nombre",
                new SimpleValue("Al"), true);

        personas = repository.find(new SimpleQuery<PersonaDummy>(
                PersonaDummy.class, beginsLikeFilter, null, null));

        Assert.assertEquals(3, personas.size());

        final BaseFilter nullFilter = new NullFilter();
        final BaseFilter greaterThanFilter = new GreaterThanPropertyFilter(
                "numeroFiscal", new SimpleValue(new Integer(1000)));

        filters.clear();
        filters.add(beginsLikeFilter);
        filters.add(greaterThanFilter);

        personas = repository.find(new SimpleQuery<PersonaDummy>(
                PersonaDummy.class, new CompositeFilter(new AndConnector(),
                        filters), null, null));

        Assert.assertEquals(1, personas.size());

        personas = repository.find(new SimpleQuery<PersonaDummy>(
                PersonaDummy.class, new CompositeFilter(new OrConnector(),
                        filters), null, null));

        Assert.assertEquals(5, personas.size());

        // El null filter no agrega nada!!!:D
        filters.add(nullFilter);
        personas = repository.find(new SimpleQuery<PersonaDummy>(
                PersonaDummy.class, new CompositeFilter(new OrConnector(),
                        filters), null, null));

        Assert.assertEquals(5, personas.size());

        filters.add(nullFilter);
        personas = repository.find(new SimpleQuery<PersonaDummy>(
                PersonaDummy.class, new CompositeFilter(new AndConnector(),
                        filters), null, null));

        Assert.assertEquals(1, personas.size());

        final List<Order> orderList = new ArrayList<Order>();
        orderList.add(new Order("nombre"));
        personas = repository.find(new SimpleQuery<PersonaDummy>(
                PersonaDummy.class, new NullFilter(), null, new Ordering(
                        orderList)));

        assertEquals("Alberto Sifran", personas.get(0).getNombre());
        assertEquals("Alfredo Alcon", personas.get(2).getNombre());
        assertEquals("Luis Fernandez", personas.get(5).getNombre());

        personas = repository.find(new SimpleQuery<PersonaDummy>(
                PersonaDummy.class, new NullFilter(), new Paging(1, 3),
                new Ordering(orderList)));
        assertEquals(3, personas.size());
        assertEquals("Alejandro Diez", personas.get(1).getNombre());
        personas = repository.find(new SimpleQuery<PersonaDummy>(
                PersonaDummy.class, new NullFilter(), new Paging(2, 3),
                new Ordering(orderList)));
        assertEquals(3, personas.size());
        assertEquals("Jose Leon", personas.get(0).getNombre());
        assertEquals("Luis Fernandez", personas.get(2).getNombre());

        // like case sensitive
        Filter filter = new ContainsLikePropertyFilter("nombre",
                new SimpleValue("ALCON"), false);
        personas = repository.find(new SimpleQuery<PersonaDummy>(
                PersonaDummy.class, filter, null, null));
        assertNotNull(personas);
        assertEquals(1, personas.size());
        
        // like case sensitive
        filter = new ContainsLikePropertyFilter("nombre",
                new SimpleValue("ALCON"), true);
        personas = repository.find(new SimpleQuery<PersonaDummy>(
                PersonaDummy.class, filter, null, null));
        assertNotNull(personas);
        assertEquals(0, personas.size());
    }

    /**
     * populates the test database
     */
    private void createSomeData() {
        // Algunas bases son case sensitive ojo con eso!!!
        final String[] nombres = {"Luis Fernandez", "Jose Leon", "Alberto Sifran",
                "Alejandro Diez", "Alfredo Alcon", "Juan Llamon" };
        final Integer[] nros = {
                new Integer(11111), new Integer(2000),
                new Integer(4000), new Integer(100), new Integer(34),
                new Integer(222) };
        final String[] descripciones = {"Un grande", "Un atleta", "Un ganzo",
                "mamuth", "otro mas", "OTRO MAS" };

        for (int i = 0; i < nombres.length; i++) {
            createPersona(nombres[i], nros[i], descripciones[i],
                    crearGuardarDosDirecciones());
        }
    }

    private void createPersona(final String nombre, final Integer nroFiscal,
            final String descripcion, final Set<DireccionDummy> direcciones) {
        final PersonaDummy personaDummy = new PersonaDummy();
        personaDummy.setNombre(nombre);
        personaDummy.setNumeroFiscal(nroFiscal);
        personaDummy.setDescripcion(descripcion);
        personaDummy.setDirecciones(direcciones);
        repository.saveOrUpdate(personaDummy);
    }

    /** test */
    public final void testActualizarEliminarColeccion() {
        final Query<PersonaDummy> query = new SimpleQuery<PersonaDummy>(
                PersonaDummy.class, new EqualsPropertyFilter("numeroFiscal",
                        new SimpleValue(new Integer(55555))), null, null);

        final PersonaDummy personaDummy = new PersonaDummy(55555, "Martin Contini",
                "Descripcion Martin Contini", crearGuardarDosDirecciones());

        // prueba actualizacion
        repository.saveOrUpdate(personaDummy);

        CollectionUtils
                .forAllDo(personaDummy.getDirecciones(),
                        new BeanPropertyValueChangeClosure("codpostal",
                                CODIGO_POSTAL_3));

        List<PersonaDummy> personas = repository.find(query);

        for (final Persistible persistible : personas) {
            final PersonaDummy persona = (PersonaDummy) persistible;
            for (final DireccionDummy direccion : persona.getDirecciones()) {
                Assert.assertEquals(CODIGO_POSTAL_3, direccion.getCodpostal());
            }
        }

        // prueba eliminacion
        personaDummy.getDirecciones().removeAll(personaDummy.getDirecciones());
        repository.saveOrUpdate(personaDummy);

        personas = repository.find(query);

        Assert.assertEquals(0, (personas.get(0))
                .getDirecciones().size());
    }

    /** crea datos de prueba */
    private Set<DireccionDummy> crearGuardarDosDirecciones() {
        final Set<DireccionDummy> direcciones = new HashSet<DireccionDummy>();

        DireccionDummy direccionDummy = new DireccionDummy();

        direccionDummy.setDireccion("Santa Fe");
        direccionDummy.setNumero(1234);
        direccionDummy.setCodpostal(CODIGO_POSTAL_1);

        repository.saveOrUpdate(direccionDummy);

        direcciones.add(direccionDummy);

        direccionDummy = new DireccionDummy();

        direccionDummy.setDireccion("Cordoba");
        direccionDummy.setNumero(5678);
        direccionDummy.setCodpostal(CODIGO_POSTAL_2);

        repository.saveOrUpdate(direccionDummy);

        direcciones.add(direccionDummy);

        return direcciones;
    }

    /** test */
    public final void testActualizarExplicitamenteColeccion() {
        PersonaDummy personaDummyGuardada;
        PersonaDummy personaDummyRecuperada;
        personaDummyGuardada = new PersonaDummy(55555, "Martin Contini",
                "Descripcion Martin Contini", crearGuardarDosDirecciones());

        // prueba actualizacion
        repository.saveOrUpdate(personaDummyGuardada);

        personaDummyRecuperada = repository
                .retrieve(new Reference<PersonaDummy>(PersonaDummy.class,
                        personaDummyGuardada.getId()));

        personaDummyRecuperada.setDescripcion("Descripcion modificada");

        repository.saveOrUpdate(personaDummyRecuperada);

    }

    /** test */
    public final void testCreateNew() {
        final Reference<PersonaDummy> ref = new Reference<PersonaDummy>(
                PersonaDummy.class);

        PersonaDummy persona;

        persona = repository.createNew(ref);

        assertNotNull(persona);

        persona = null;

        persona = repository.createNew(ref,
                new Object[] {"pepe" }, new Class[] {String.class });

        assertNotNull(persona);

        assertEquals("pepe", persona.getNombre());

    }
    
    /** test */
    public final void testProjection() {
        for (final Persistible direccion : crearGuardarDosDirecciones()) {
            repository.saveOrUpdate(direccion);
        }
        
        final Query<DireccionDummy> q =  
            new SimpleQuery<DireccionDummy>(DireccionDummy.class, 
                new NullFilter(), null, null);
        
        assertEquals(new Integer(2), repository.aggregate(q, 
                new RowCountAggregateFilter(), Integer.class));
        
        assertEquals(new Integer(5678), repository.aggregate(q, 
                new MaxPropertyAggregateFunction("numero"), Integer.class));
        
        
        assertEquals(new Integer(1234), repository.aggregate(q, 
                new MinPropertyAggregateFunction("numero"), Integer.class));
    }

    /**
     * 
     * https://tracker.zauber.com.ar/view.php?id=2803
     * 
     * When i want to paginate and order i write:
     *  <verbatim>
     *
     *   final Query<PublicacionImpl> query =
     *      new SimpleQuery<PublicacionImpl>(
     *          PublicacionImpl.class, new NullFilter(), paging, ordering);
     *
     *   if (paging != null && !paging.hasResultSize()) {
     *      paging.setResultSize((Integer)repository.aggregate(query,
     *              new RowCountAggregateFilter(), Integer.class));
     *  }
     *
     *  return repository.find(query);
     * </verbatim>
     * When  the query is executed with the RowCountAggregateFilter an exception
     * occurs:
     * "Not in aggregate function or group by clause: 
     * org.hsqldb.Expression@3041876 in statement [select count(*) as y0_ 
     *  from PUBLICACION this_ order by this_.DESCRIPCION desc]" 
     */
    public final void test2803() {
        createSomeData();
        final Paging paging = new Paging(1, 5);
        final Ordering ordering = new Ordering(Arrays.asList(new Order[] {
                new Order("numeroFiscal", true),
        }));
        final Query<PersonaDummy> query =
            new SimpleQuery<PersonaDummy>(
                    PersonaDummy.class, new NullFilter(), paging, ordering);

        if (paging != null && !paging.hasResultSize()) {
            paging.setResultSize((Integer)repository.aggregate(query,
                    new RowCountAggregateFilter(), Integer.class));
        }
        final List<PersonaDummy> d = repository.find(query);
        assertEquals(5, d.size());
        assertTrue(d.get(0).getNumeroFiscal() < d.get(1).getNumeroFiscal());
        assertTrue(d.get(1).getNumeroFiscal() < d.get(2).getNumeroFiscal());
        assertTrue(d.get(2).getNumeroFiscal() < d.get(3).getNumeroFiscal());
        assertTrue(d.get(3).getNumeroFiscal() < d.get(4).getNumeroFiscal());
    }
    
    
    /** test */
    public final void testOrder() {
        createSomeData();
        final Ordering ordering = new Ordering(Arrays.asList(new Order[] {
                new Order("descripcion", true, true),
        }));
        
        final Filter f = new CompositeFilter(new OrConnector(), 
                Arrays.asList(new BaseFilter[]{
                new BeginsLikePropertyFilter("descripcion",
                        new SimpleValue("otro"), false),
                new BeginsLikePropertyFilter("descripcion",
                        new SimpleValue("m"), false),
        }));
        
        final Query<PersonaDummy> query =
            new SimpleQuery<PersonaDummy>(PersonaDummy.class, f, null, ordering);

        final List<PersonaDummy> d = repository.find(query);
        assertEquals(3, d.size());
        assertTrue(d.get(0).getDescripcion().toLowerCase().startsWith("m"));
        assertTrue(d.get(1).getDescripcion().toLowerCase().startsWith("o"));
        assertTrue(d.get(1).getDescripcion().toLowerCase().startsWith("o"));
    }
 
    protected HibernateTemplate hibernateTemplate;
    /** test */
    public final void testCountGroupBy() {
        createSomeData();
        
        final Query<DireccionDummy> query = 
            new SimpleQuery<DireccionDummy>(DireccionDummy.class, 
                new NullFilter(), null, 
                new Ordering(
                        Arrays.asList(new Order[] { new Order("numero", true, true) }))
                );
        
        AggregateFunction function = new CompositeAggregateFunction(
                Arrays.asList(new AggregateFunction[]{
                        new CountPropertyAggregateFunction("numero"),
                        new GroupPropertyAggregateFilter("direccion"),
                        new GroupPropertyAggregateFilter("numero"),
                }));
         List<Object> rows = repository.aggregate(query, function, List.class);
         
         assertEquals(rows.size(), 2);
         
         Object[] row = (Object []) rows.get(0);
         assertEquals(6, row[0]);
         assertEquals("Santa Fe", row[1]);
         assertEquals(1234, row[2]);
         
         row = (Object []) rows.get(1);
         assertEquals(6, row[0]);
         assertEquals("Cordoba", row[1]);
         assertEquals(5678, row[2]);
    }
}
