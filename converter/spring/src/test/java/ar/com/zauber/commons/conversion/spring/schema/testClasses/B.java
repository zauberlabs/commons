package ar.com.zauber.commons.conversion.spring.schema.testClasses;

/**
 * 
 * Target class of the conversion.
 * 
 * 
 * @author Juan Edi
 * @since Nov 18, 2009
 */
public class B {
    private Integer integerField;
    private Integer anotherNameIntegerField;
    private Integer plusOneIntegerField;
    private String  otherClassName;
    
    /**
     * Creates the B.
     *
     */
    public B() {
        super();
    }
    
    /**
     * Sets the integerField. 
     *
     * @param integerField <code>Integer</code> with the integerField.
     */
    public final void setIntegerField(final Integer integerField) {
        this.integerField = integerField;
    }
    /**
     * Sets the anotherNameIntegerField. 
     *
     * @param anotherNameIntegerField
     *                  <code>Integer</code> with the anotherNameIntegerField.
     */
    public final void setAnotherNameIntegerField(
                                    final Integer anotherNameIntegerField) {
        this.anotherNameIntegerField = anotherNameIntegerField;
    }
    /**
     * Sets the plusOneIntegerField. 
     *
     * @param plusOneIntegerField
     *                      <code>Integer</code> with the plusOneIntegerField.
     */
    public final void setPlusOneIntegerField(final Integer plusOneIntegerField) {
        this.plusOneIntegerField = plusOneIntegerField;
    }
    /**
     * Sets the otherClassName. 
     *
     * @param otherClassName <code>String</code> with the otherClassName.
     */
    public final void setOtherClassName(final String otherClassName) {
        this.otherClassName = otherClassName;
    }
    /**
     * Returns the integerField.
     * 
     * @return <code>Integer</code> with the integerField.
     */
    public final Integer getIntegerField() {
        return integerField;
    }
    /**
     * Returns the anotherNameIntegerField.
     * 
     * @return <code>Integer</code> with the anotherNameIntegerField.
     */
    public final Integer getAnotherNameIntegerField() {
        return anotherNameIntegerField;
    }
    /**
     * Returns the plusOneIntegerField.
     * 
     * @return <code>Integer</code> with the plusOneIntegerField.
     */
    public final Integer getPlusOneIntegerField() {
        return plusOneIntegerField;
    }
    /**
     * Returns the otherClassName.
     * 
     * @return <code>String</code> with the otherClassName.
     */
    public final String getOtherClassName() {
        return otherClassName;
    }

    
}