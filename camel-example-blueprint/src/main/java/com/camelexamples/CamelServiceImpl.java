package com.camelexamples;

/**
 * Created by ramesh on 31/08/2015.
 */
public class CamelServiceImpl implements CamelService {

    public String sayHello(String message) {
        return "Hello " + message;
    }
}
