package com.capella.typeconverters.converters;

import com.capella.typeconverters.entities.Person;
import org.apache.camel.Converter;
import org.apache.camel.Exchange;
import org.apache.camel.TypeConversionException;

/**
 * Copyright 2016 (c) Mastek UK Ltd
 * <p>
 * Created on : 1/5/16
 *
 * @author Ramesh Rajendran
 */
@Converter
public class PersonConverter {

    @Converter
    public static Person convert(String name, Exchange exchange){
        if(name == null | name.length() == 0){
            throw new TypeConversionException(name,Person.class, new IllegalArgumentException("Required parameter is missing"));
        }
        return new Person(name);
    }
}
