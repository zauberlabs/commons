package ar.com.zauber.commons.test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;
import ar.com.zauber.commons.repository.Repository;
import ar.com.zauber.commons.repository.SpringHibernateRepository;
import ar.com.zauber.commons.repository.query.EqualsQuery;
import ar.com.zauber.commons.repository.query.Query;
import ar.com.zauber.commons.repository.test.model.DireccionDummy;
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
        Collection direcciones = crearGuardarDosDirecciones();

        // se elimina la coleccion
        repository.deleteAll(direcciones);        
        direcciones = repository.findAll(DireccionDummy.class);
        Assert.assertEquals(0, direcciones.size());
        
        // se guarda la coleccion
        direcciones = crearGuardarDosDirecciones();
        repository.saveAll(direcciones);        
        direcciones = ((SpringHibernateRepository)repository).findAll(
                DireccionDummy.class);
        Assert.assertEquals(2, direcciones.size());
        
        repository.updateAll(direcciones);
        
        Query query = new EqualsQuery("direccion", "Santa Fe");
        
        direcciones = repository.find(DireccionDummy.class, query);
        
        Assert.assertEquals(1, direcciones.size());
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
//            ((SpringHibernateRepository)repository).findByCriteria(DireccionDummy.class, new Object());
//            fail("UnsupportedOperationException not thrown.");
//            
//        } catch (UnsupportedOperationException uoe) {
//        }
//    }
//    
//    public void testEvict() {
//        DireccionDummy direccionDummy = new DireccionDummy();
//        direccionDummy.setDireccion("direccion");
//        direccionDummy.setNumero("1212");
//        repository.save(direccionDummy);
//        ((SpringHibernateRepository)repository).evict(direccionDummy);    
//    }
//    
//    
//    public void testGuardarObtenerEliminarPersonaDummy() {
//        PersonaDummy personaDummy = new PersonaDummy();
//                
//        personaDummy.setNombre("Luis Fernandez");
//        personaDummy.setNumeroFiscal(new Integer(11111));
//        personaDummy.setDescripcion("Descripcion " + personaDummy.getNombre());
//        personaDummy.setDirecciones(crearGuardarDosDirecciones());
//        
//        repository.save(personaDummy);
//        
//        personaDummy = new PersonaDummy();
//        personaDummy.setNombre("Juan Perez");
//        personaDummy.setNumeroFiscal(new Integer(22222));
//        personaDummy.setDescripcion("Descripcion " + personaDummy.getNombre());
//        personaDummy.setDirecciones(crearGuardarDosDirecciones());
//        
//        repository.save(personaDummy);
//        
//        Collection personas = repository.findAll(PersonaDummy.class);
//        
//        Assert.assertEquals(2, personas.size());
//        
//        repository.delete(personaDummy);
//        
//        personas = repository.findAll(PersonaDummy.class);
//        
//        Assert.assertEquals(1, personas.size());       
//    }    
//    
//    public void testActualizarEliminarColeccion() {        
//        FilterCriteria[] fc = { new FilterCriteria("numeroFiscal",
//                FilterCriteria.EQUALS, 
//                new Integer(55555)) };
//        
//        SimpleFilterObject sfo = new SimpleFilterObject();
//        
//        sfo.setCriterias(fc);
//        
//        PersonaDummy personaDummy = new PersonaDummy(55555, 
//                "Martin Contini", 
//                "Descripcion Martin Contini", 
//                crearGuardarDosDirecciones());
//        
//        // prueba actualizacion
//        repository.save(personaDummy);        
//
//        for(Iterator iter = personaDummy.getDirecciones().iterator(); iter.hasNext();) {
//            DireccionDummy direccionDummy = (DireccionDummy)iter.next();
//            direccionDummy.setCodpostal(CODIGO_POSTAL_3);            
//        }
//
//        List personas = repository.findByCriteria(PersonaDummy.class, sfo);
//                
//        for(Iterator personaIter = personas.iterator(); personaIter.hasNext();) {
//            PersonaDummy persona = (PersonaDummy)personaIter.next();
//            for(Iterator direccionIter = persona.getDirecciones().iterator(); direccionIter.hasNext();) {
//                DireccionDummy direccionDummy = (DireccionDummy)direccionIter.next();
//                Assert.assertEquals(CODIGO_POSTAL_3, direccionDummy.getCodpostal());
//            }
//        }
//
//        // prueba eliminacion
//        personaDummy.getDirecciones().removeAll(personaDummy.getDirecciones());
//        repository.save(personaDummy);
//        
//        personas = repository.findByCriteria(PersonaDummy.class, sfo);
//        
//        Assert.assertEquals(0, ((PersonaDummy)personas.get(0)).getDirecciones().size());
//    }

    private Set<DireccionDummy> crearGuardarDosDirecciones() {
        Set<DireccionDummy> direcciones = new HashSet<DireccionDummy>();
        
        DireccionDummy direccionDummy = new DireccionDummy();
        
        direccionDummy.setDireccion("Santa Fe");
        direccionDummy.setNumero("1234");
        direccionDummy.setCodpostal(CODIGO_POSTAL_1);
        
        repository.save(direccionDummy);
        
        direcciones.add(direccionDummy);

        direccionDummy = new DireccionDummy();
        
        direccionDummy.setDireccion("Cordoba");
        direccionDummy.setNumero("5678");
        direccionDummy.setCodpostal(CODIGO_POSTAL_2);
        
        repository.save(direccionDummy);
        
        direcciones.add(direccionDummy);
        
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
