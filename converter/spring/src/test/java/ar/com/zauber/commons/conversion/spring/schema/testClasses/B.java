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