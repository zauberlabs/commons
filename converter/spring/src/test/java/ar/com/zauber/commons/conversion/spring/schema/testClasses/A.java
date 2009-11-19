package ar.com.zauber.commons.conversion.spring.schema.testClasses;

/**
 * 
 * Source class of the conversion.
 * 
 * 
 * @author Juan Edi
 * @since Nov 18, 2009
 */
public class A {
    private final Integer integerField;
    
    /**
     * Creates the A.
     *
     * @param integerField
     */
    public A(final Integer integerField) {
        super();
        this.integerField = integerField;
    }

    /**
     * Returns the integerField.
     * 
     * @return <code>Integer</code> with the integerField.
     */
    public final Integer getIntegerField() {
        return integerField;
    }
    
    
}