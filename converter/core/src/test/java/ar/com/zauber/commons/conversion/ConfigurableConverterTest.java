/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.conversion;

import junit.framework.Assert;

import org.junit.Test;


/**
 * 
 * Tests conversion between classes. 
 * 
 * 
 * @author Juan Edi
 * @since Nov 18, 2009
 */
public class ConfigurableConverterTest {

    private A sourceObject = new A(new Integer(5));


    @Test
    public final void singleFieldCopyTest() {

        ConfigurableJavaBeanConverter<A, B> converter = 
            new ConfigurableJavaBeanConverter<A, B>(B.class);
        
        converter.getConfig().addSinglePropertyField("integerField");
        
        B targetObject = converter.convert(sourceObject, new ConversionContext());
        
        Assert.assertEquals(sourceObject.getIntegerField(), 
                targetObject.getIntegerField());
    }
    
    
    
    
    @Test
    public final void singleFieldRenameAndCopyTest() {
        
        ConfigurableJavaBeanConverter<A, B> converter = 
            new ConfigurableJavaBeanConverter<A, B>(B.class);
        
        converter.getConfig().addSinglePropertyField("anotherNameIntegerField",
        "integerField");
        
        B targetObject = converter.convert(sourceObject, new ConversionContext());
        
        Assert.assertEquals(sourceObject.getIntegerField(),
                targetObject.getAnotherNameIntegerField());
    }
    

    @Test
    public final void singleFieldConversionTest() {
        
        ConfigurableJavaBeanConverter<A, B> converter = 
            new ConfigurableJavaBeanConverter<A, B>(B.class);

        converter.getConfig().addSinglePropertyField("plusOneIntegerField",
                "integerField", new PlusOneConverter());
        
        B targetObject = converter.convert(sourceObject, new ConversionContext());
        
        Assert.assertEquals(new Integer(sourceObject.getIntegerField() + 1), 
                            targetObject.getPlusOneIntegerField());
    }
    
    

    @Test
    public final void complexFieldConversionTest() {
        
        ConfigurableJavaBeanConverter<A, B> converter = 
            new ConfigurableJavaBeanConverter<A, B>(B.class);
        
        converter.getConfig().addField("otherClassName", new ClassNameConverter());
        
        B targetObject = converter.convert(sourceObject, new ConversionContext());
        
        Assert.assertEquals(sourceObject.getClass().getName(),
                            targetObject.getOtherClassName());
        
    }
    
    
    // Test Classes.
    
    /**
     * 
     * Source class of the conversion.
     * 
     * 
     * @author Juan Edi
     * @since Nov 18, 2009
     */
    private static class A {
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
        public Integer getIntegerField() {
            return integerField;
        }
        
    }
    
    /**
     * 
     * Target class of the conversion.
     * 
     * 
     * @author Juan Edi
     * @since Nov 18, 2009
     */
    @SuppressWarnings("unused")
    private static class B {
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
        public void setIntegerField(final Integer integerField) {
            this.integerField = integerField;
        }
        /**
         * Sets the anotherNameIntegerField. 
         *
         * @param anotherNameIntegerField
         *                  <code>Integer</code> with the anotherNameIntegerField.
         */
        public void setAnotherNameIntegerField(
                                        final Integer anotherNameIntegerField) {
            this.anotherNameIntegerField = anotherNameIntegerField;
        }
        /**
         * Sets the plusOneIntegerField. 
         *
         * @param plusOneIntegerField
         *                      <code>Integer</code> with the plusOneIntegerField.
         */
        public void setPlusOneIntegerField(final Integer plusOneIntegerField) {
            this.plusOneIntegerField = plusOneIntegerField;
        }
        /**
         * Sets the otherClassName. 
         *
         * @param otherClassName <code>String</code> with the otherClassName.
         */
        public void setOtherClassName(final String otherClassName) {
            this.otherClassName = otherClassName;
        }
        /**
         * Returns the integerField.
         * 
         * @return <code>Integer</code> with the integerField.
         */
        public Integer getIntegerField() {
            return integerField;
        }
        /**
         * Returns the anotherNameIntegerField.
         * 
         * @return <code>Integer</code> with the anotherNameIntegerField.
         */
        public Integer getAnotherNameIntegerField() {
            return anotherNameIntegerField;
        }
        /**
         * Returns the plusOneIntegerField.
         * 
         * @return <code>Integer</code> with the plusOneIntegerField.
         */
        public Integer getPlusOneIntegerField() {
            return plusOneIntegerField;
        }
        /**
         * Returns the otherClassName.
         * 
         * @return <code>String</code> with the otherClassName.
         */
        public String getOtherClassName() {
            return otherClassName;
        }
        
        
    }
    
    /**
     * 
     * Converts an Object to a String with its class name.
     * 
     * 
     * @author Juan Edi
     * @since Nov 18, 2009
     */
    public static class ClassNameConverter implements Converter<A, String> {

        /** @see Converter#convert(java.lang.Object, ConversionContext) */
        public String convert(final A source, final ConversionContext ctx) {
            return source.getClass().getName();
        }
        
    }
    
    /**
     * 
     * Converts an Integer to its immediate following. 
     * 
     * 
     * @author Juan Edi
     * @since Nov 18, 2009
     */
    public class PlusOneConverter implements Converter<Integer, Integer> {

        /** @see Converter#convert(java.lang.Object, ConversionContext) */
        public Integer convert(final Integer source, final ConversionContext ctx) {
            return source + 1;
        }
        
    }
}