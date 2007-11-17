package ar.com.zauber.commons.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.apache.commons.beanutils.BeanPropertyValueChangeClosure;
import org.apache.commons.collections.CollectionUtils;

import ar.com.zauber.commons.dao.Order;
import ar.com.zauber.commons.dao.Ordering;
import ar.com.zauber.commons.dao.Paging;
import ar.com.zauber.commons.repository.Persistible;
import ar.com.zauber.commons.repository.Reference;
import ar.com.zauber.commons.repository.Repository;
import ar.com.zauber.commons.repository.query.Query;
import ar.com.zauber.commons.repository.query.SimpleQuery;
import ar.com.zauber.commons.repository.query.connectors.AndConnector;
import ar.com.zauber.commons.repository.query.connectors.OrConnector;
import ar.com.zauber.commons.repository.query.filters.BaseFilter;
import ar.com.zauber.commons.repository.query.filters.BeginsLikePropertyFilter;
import ar.com.zauber.commons.repository.query.filters.CompositeFilter;
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
public class SpringHibernateRepositoryTest
    extends BaseTransactionalRollbackTest {

    /**
     *  Un codigo postal.
     */
    private static final String CODIGO_POSTAL_1 = "1111";

    /**
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
    protected Repository repository;

    /**
     * Crea el SpringHibernateRepositoryTest.
     *
     */
    public SpringHibernateRepositoryTest() {
        //setDefaultRollback(false);
    }
    
    public void testRetrieve() {
    	Set<DireccionDummy> direcciones = crearGuardarDosDirecciones();
    	
    	DireccionDummy guardada = direcciones.iterator().next();
    	
    	DireccionDummy recuperada = repository.retrieve(
    			new Reference<DireccionDummy>(DireccionDummy.class, guardada.getId()));
        
    	Assert.assertEquals(guardada.getDireccion(), recuperada.getDireccion());
	}
    
    public void testABMColeccion() {

        Query<DireccionDummy> nullQuery = new SimpleQuery<DireccionDummy>(
        		DireccionDummy.class, new NullFilter(), null, null);
        
        List<DireccionDummy> direcciones = repository.find(nullQuery); 
        
        Assert.assertEquals(0, direcciones.size());
        
        // se guarda la coleccion
        for(Persistible direccion : crearGuardarDosDirecciones()) {
            repository.saveOrUpdate(direccion);	
        }
                
        direcciones = repository.find(new SimpleQuery<DireccionDummy>(DireccionDummy.class, new NullFilter(), null, null));
        Assert.assertEquals(2, direcciones.size());
        
        for(DireccionDummy direccion : direcciones) {
            repository.saveOrUpdate(direccion);	
        }
        
        Query<DireccionDummy> query = new SimpleQuery<DireccionDummy>(
                DireccionDummy.class, new EqualsPropertyFilter("direccion", new SimpleValue("Santa Fe")), null, null);
        
        direcciones = repository.find(query);
        
        Assert.assertEquals(1, direcciones.size());
    }
        
    
    public void testGuardarObtenerEliminarPersonaDummy() {
//  TODO: Esta teniendo unos problemas al hacer findAll porque trae varios
//        resultados por cada uno que existe.
        
    	Query<PersonaDummy> allQuery = new SimpleQuery<PersonaDummy>(PersonaDummy.class, new NullFilter(), null, null); 
    	
        Assert.assertEquals(0, repository.find(allQuery).size());
        
        PersonaDummy personaDummy = new PersonaDummy();
                
        personaDummy.setNombre("Luis Fernandez");
        personaDummy.setNumeroFiscal(new Integer(11111));
        personaDummy.setDescripcion("Descripcion " + personaDummy.getNombre());
        personaDummy.setDirecciones(crearGuardarDosDirecciones());
        
        repository.saveOrUpdate(personaDummy);
        
        Collection<PersonaDummy> auxiPersonas = repository.find(allQuery);
        
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

    public void testFiltersOrderingAndPaging() {
        
        //Algunas bases son case sensitive ojo con eso!!!
        String[] nombres = {"Luis Fernandez", "Jose Leon", "Alberto Sifran", "Alejandro Diez", "Alfredo Alcon", "Juan Llamon"};
        Integer[] nros = {new Integer(11111), new Integer(2000), new Integer(4000), new Integer(100), new Integer(34), new Integer(222)};
        String[] descripciones = {"Un grande", "Un atleta", "Un ganzo", "Un famoso", "y este?", "otro mas"};
        
        for (int i = 0; i < nombres.length; i++) {
            createPersona(nombres[i], nros[i], descripciones[i], crearGuardarDosDirecciones());
        }

        List<PersonaDummy> personas;

        personas = repository.find(new SimpleQuery<PersonaDummy>(PersonaDummy.class, new BeginsLikePropertyFilter("nombre", new SimpleValue("Al"), false), null, null));

        Assert.assertEquals(3, personas.size());
        
        List<BaseFilter> filters = new ArrayList<BaseFilter>();
        
        BaseFilter beginsLikeFilter = new BeginsLikePropertyFilter("nombre", new SimpleValue("Al"), true);
        
        personas = repository.find(new SimpleQuery<PersonaDummy>(PersonaDummy.class, beginsLikeFilter, null, null));

        Assert.assertEquals(3, personas.size());
        
        BaseFilter nullFilter = new NullFilter();
        BaseFilter greaterThanFilter = new GreaterThanPropertyFilter("numeroFiscal", new SimpleValue(new Integer(1000)));
        
        filters.clear();
        filters.add(beginsLikeFilter);
        filters.add(greaterThanFilter);
        
        personas = repository.find(new SimpleQuery<PersonaDummy>(
                PersonaDummy.class, new CompositeFilter(new AndConnector(), filters), null, null));

        Assert.assertEquals(1, personas.size());
        
        personas = repository.find(new SimpleQuery<PersonaDummy>(
                PersonaDummy.class, new CompositeFilter(new OrConnector(), filters), null, null));

        Assert.assertEquals(5, personas.size());

        // El null filter no agrega nada!!!:D
        filters.add(nullFilter);
        personas = repository.find(new SimpleQuery<PersonaDummy>(
                PersonaDummy.class, new CompositeFilter(new OrConnector(), filters), null, null));

        Assert.assertEquals(5, personas.size());

        filters.add(nullFilter);
        personas = repository.find(new SimpleQuery<PersonaDummy>(
                PersonaDummy.class, new CompositeFilter(new AndConnector(), filters), null, null));

        Assert.assertEquals(1, personas.size());
        
        List<Order> orderList = new ArrayList<Order>();
        orderList.add(new Order("nombre"));
        personas = repository.find(new SimpleQuery<PersonaDummy>(
                PersonaDummy.class, new NullFilter(), null,
                new Ordering(orderList)));
        
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
        
        
    }

    private void createPersona(String nombre, Integer nroFiscal,
            String descripcion, Set<DireccionDummy> direcciones) {
        PersonaDummy personaDummy = new PersonaDummy();
        personaDummy.setNombre(nombre);
        personaDummy.setNumeroFiscal(nroFiscal);
        personaDummy.setDescripcion(descripcion);
        personaDummy.setDirecciones(direcciones);        
        repository.saveOrUpdate(personaDummy);
    }
    
    public void testActualizarEliminarColeccion() {        
        Query<PersonaDummy> query = new SimpleQuery<PersonaDummy>(PersonaDummy.class,
                new EqualsPropertyFilter("numeroFiscal", new SimpleValue(new Integer(55555))), null, null);
        
        
        PersonaDummy personaDummy = new PersonaDummy(55555, 
                "Martin Contini", 
                "Descripcion Martin Contini", 
                crearGuardarDosDirecciones());
        
        // prueba actualizacion
        repository.saveOrUpdate(personaDummy);        

        CollectionUtils.forAllDo(
                personaDummy.getDirecciones(), new BeanPropertyValueChangeClosure("codpostal", CODIGO_POSTAL_3));
        

        List<PersonaDummy> personas = repository.find(query);
                
        for (Persistible persistible : personas) {
        	PersonaDummy persona = (PersonaDummy) persistible;
            for (DireccionDummy direccion : persona.getDirecciones()) {
                Assert.assertEquals(CODIGO_POSTAL_3, direccion.getCodpostal());
            }
        }
        
        // prueba eliminacion
        personaDummy.getDirecciones().removeAll(personaDummy.getDirecciones());
        repository.saveOrUpdate(personaDummy);
        
        personas = repository.find(query);
        
        Assert.assertEquals(0, ((PersonaDummy)personas.get(0)).getDirecciones().size());
    }

    private Set<DireccionDummy> crearGuardarDosDirecciones() {
        Set<DireccionDummy> direcciones = new HashSet<DireccionDummy>();
        
        DireccionDummy direccionDummy = new DireccionDummy();
        
        direccionDummy.setDireccion("Santa Fe");
        direccionDummy.setNumero("1234");
        direccionDummy.setCodpostal(CODIGO_POSTAL_1);
        
        repository.saveOrUpdate(direccionDummy);
        
        direcciones.add(direccionDummy);

        direccionDummy = new DireccionDummy();
        
        direccionDummy.setDireccion("Cordoba");
        direccionDummy.setNumero("5678");
        direccionDummy.setCodpostal(CODIGO_POSTAL_2);
        
        repository.saveOrUpdate(direccionDummy);
        
        direcciones.add(direccionDummy);
                
        return direcciones;
    }
    
    public void testActualizarExplicitamenteColeccion() {        
        PersonaDummy personaDummyGuardada;
        PersonaDummy personaDummyRecuperada;
        personaDummyGuardada = new PersonaDummy(55555, 
                "Martin Contini", 
                "Descripcion Martin Contini", 
                crearGuardarDosDirecciones());

        // prueba actualizacion
        repository.saveOrUpdate(personaDummyGuardada);

        personaDummyRecuperada = (PersonaDummy)
            repository.retrieve(new Reference<PersonaDummy>(PersonaDummy.class, personaDummyGuardada.getId()));

        personaDummyRecuperada.setDescripcion("Descripcion modificada");

        repository.saveOrUpdate(personaDummyRecuperada);

    }

    public void testCreateNew() {
        Reference<PersonaDummy> ref = new Reference<PersonaDummy>(PersonaDummy.class);

        PersonaDummy persona;
        
        persona = repository.createNew(ref);
        
        assertNotNull(persona);

        persona = null;
        
        persona = (PersonaDummy)repository.createNew(ref, new Object[] {"pepe"}, new Class[] {String.class} );
        
        assertNotNull(persona);
        
        assertEquals("pepe", persona.getNombre());
        

    }
}
