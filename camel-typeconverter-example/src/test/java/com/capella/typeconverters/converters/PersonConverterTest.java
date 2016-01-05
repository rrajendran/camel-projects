package com.capella.typeconverters.converters;

import com.capella.typeconverters.entities.Person;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.junit.Test;

/**
 * Copyright 2016 (c) Mastek UK Ltd
 * <p>
 * Created on : 1/5/16
 *
 * @author Ramesh Rajendran
 */
public class PersonConverterTest extends CamelBlueprintTestSupport {
    @Override
    protected String getBlueprintDescriptor() {
        return "OSGI-INF/blueprint/camelContext.xml,";
    }

    @Test
    public void test() throws InterruptedException {
        // mock expected results
        MockEndpoint mockEndpoint = getMockEndpoint("mock:a");
        mockEndpoint.expectedMessageCount(1);
        mockEndpoint.expectedBodiesReceived(new Person("Ramesh"));

        // when
        template.sendBody("direct:start", "Ramesh");

        // then
        assertMockEndpointsSatisfied();
    }

    @Test
    public void testWhenNameIsMissing() throws InterruptedException {
        // mock expected results
        MockEndpoint mockEndpoint = getMockEndpoint("mock:a");
        mockEndpoint.expectedMessageCount(1);


        // when
        template.sendBody("direct:start", null);

        // then
        assertMockEndpointsSatisfied();
    }
}