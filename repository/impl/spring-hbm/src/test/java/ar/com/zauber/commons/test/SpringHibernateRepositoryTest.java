package ar.com.zauber.commons.test;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.apache.commons.beanutils.BeanPropertyValueChangeClosure;
import org.apache.commons.collections.CollectionUtils;

import ar.com.zauber.commons.repository.Persistible;
import ar.com.zauber.commons.repository.Repository;
import ar.com.zauber.commons.repository.SpringHibernateRepository;
import ar.com.zauber.commons.repository.query.Query;
import ar.com.zauber.commons.repository.query.SimpleQuery;
import ar.com.zauber.commons.repository.query.filters.EqualsPropertyFilter;
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
        setDefaultRollback(false);
    }
 
    public void testABMColeccion() {

        for(DireccionDummy direccion : crearGuardarDosDirecciones()) {
           repository.delete(direccion);	
        }
                
        Collection<Persistible> persistibles = repository.find(new SimpleQuery(DireccionDummy.class, new NullFilter(), null, null));
        Assert.assertEquals(0, persistibles.size());
        
        // se guarda la coleccion
        for(Persistible direccion : crearGuardarDosDirecciones()) {
            repository.saveOrUpdate(direccion);	
        }
                
        persistibles = repository.find(new SimpleQuery(DireccionDummy.class, new NullFilter(), null, null));
        Assert.assertEquals(2, persistibles.size());
        
        for(Persistible direccion : persistibles) {
            repository.saveOrUpdate(direccion);	
        }
        
        Query query = new SimpleQuery(DireccionDummy.class, new EqualsPropertyFilter("direccion", new SimpleValue("Santa Fe")), null, null);
        
        persistibles = repository.find(query);
        
        Assert.assertEquals(1, persistibles.size());
    }
    
//    public void testFind() {
//        FilterObject simpleFilter = null;
//        CriteriaSpecification criteria = DetachedCriteria.forClass(DireccionDummy.class);
//        Collection direcciones = null;
//        
//        direcciones = repository.find(DireccionDummy.class, "direccion", "Santa Fe");
//        
//        ((SpringHibernateRepository)repository).findByCriteria(DireccionDummy.class, criteria);
//        try {
//            ((SpringHibernateRepository)repository).find(DireccionDummy.class, new Object());
//            fail("UnsupportedOperationException not thrown.");
//            
//        } catch (UnsupportedOperationException uoe) {
//        }
//    }
    
//    public void testEvict() {
//        DireccionDummy direccionDummy = new DireccionDummy();
//        direccionDummy.setDireccion("direccion");
//        direccionDummy.setNumero("1212");
//        repository.save(direccionDummy);
//        ((SpringHibernateRepository)repository).evict(direccionDummy);    
//    }
    
    
    public void testGuardarObtenerEliminarPersonaDummy() {
//  TODO: Esta teniendo unos problemas al hacer findAll porque trae varios
//        resultados por cada uno que existe.
//        
    	Query allQuery = new SimpleQuery(PersonaDummy.class, new NullFilter(), null, null); 
    	
        Assert.assertEquals(0, repository.find(allQuery).size());
        
        PersonaDummy personaDummy = new PersonaDummy();
                
        personaDummy.setNombre("Luis Fernandez");
        personaDummy.setNumeroFiscal(new Integer(11111));
        personaDummy.setDescripcion("Descripcion " + personaDummy.getNombre());
        personaDummy.setDirecciones(crearGuardarDosDirecciones());
        
        repository.saveOrUpdate(personaDummy);
        
        Collection auxiPersonas = repository.find(allQuery);
        
        Assert.assertEquals(1, auxiPersonas.size());
        
        personaDummy = new PersonaDummy();
        personaDummy.setNombre("Juan Perez");
        personaDummy.setNumeroFiscal(new Integer(22222));
        personaDummy.setDescripcion("Descripcion " + personaDummy.getNombre());
        personaDummy.setDirecciones(crearGuardarDosDirecciones());
        
        repository.saveOrUpdate(personaDummy);
        
        Collection personas = repository.find(allQuery);
        
        Assert.assertEquals(2, personas.size());
        
        repository.delete(personaDummy);
        
        personas = repository.find(allQuery);
       
        Assert.assertEquals(1, personas.size());       
    }    
    
    public void testActualizarEliminarColeccion() {        
        Query query = new SimpleQuery(PersonaDummy.class,
                new EqualsPropertyFilter("numeroFiscal", new SimpleValue(new Integer(55555))), null, null);
        
        
        PersonaDummy personaDummy = new PersonaDummy(55555, 
                "Martin Contini", 
                "Descripcion Martin Contini", 
                crearGuardarDosDirecciones());
        
        // prueba actualizacion
        repository.saveOrUpdate(personaDummy);        

        CollectionUtils.forAllDo(
                personaDummy.getDirecciones(), new BeanPropertyValueChangeClosure("codpostal", CODIGO_POSTAL_3));
        

        List<Persistible> personas = repository.find(query);
                
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
        
        DireccionDummy dir = repository.retrieve(direccionDummy.getReference());
        
        Assert.assertEquals("5678", dir.getNumero());
        
        return direcciones;
    }
    
//    public void testActualizarExplicitamenteColeccion() {        
//        PersonaDummy personaDummyGuardada;
//        PersonaDummy personaDummyRecuperada;
//        personaDummyGuardada = new PersonaDummy(55555, 
//                "Martin Contini", 
//                "Descripcion Martin Contini", 
//                crearGuardarDosDirecciones());
//
//        // prueba actualizacion
//        Long id = repository.save(personaDummyGuardada);
//
//        personaDummyRecuperada = (PersonaDummy)
//            repository.retrieve(new Reference(PersonaDummy.class, id));
//
//        personaDummyRecuperada.setDescripcion("Descripcion modificada");
//
//        repository.update(personaDummyRecuperada);
//
//    }
//
//    public void testCreateNew() {
//        Reference ref = new Reference(PersonaDummy.class);
//
//        PersonaDummy persona;
//        
//        persona = (PersonaDummy)repository.createNew(ref);
//        
//        assertNotNull(persona);
//
//        persona = null;
//        
//        persona = (PersonaDummy)repository.createNew(ref, new Object[] {"pepe"}, new Class[] {String.class} );
//        
//        assertNotNull(persona);
//        
//        assertEquals("pepe", persona.getNombre());
//        
//
//    }
}
