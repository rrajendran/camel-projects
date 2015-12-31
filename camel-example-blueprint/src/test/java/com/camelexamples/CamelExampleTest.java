package com.camelexamples;

import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.apache.camel.util.KeyValueHolder;
import org.junit.Test;

import java.util.Dictionary;
import java.util.Map;

/**
 * Created by ramesh on 31/08/2015.
 */
public class CamelExampleTest extends CamelBlueprintTestSupport {


   @Override
    protected String getBlueprintDescriptor() {
        return "OSGI-INF/blueprint/camelContext-services.xml,OSGI-INF/blueprint/camelContext.xml,";
    }

    @Override
    protected void addServicesOnStartup(Map<String, KeyValueHolder<Object, Dictionary>> services) {
        CamelService camelService = new CamelServiceImpl();

       services.put(CamelService.class.getName(), asService(camelService, null));
    }

    @Test
    public void test() throws InterruptedException {
        // mock expected results
        MockEndpoint mockEndpoint = getMockEndpoint("mock:a");
        mockEndpoint.expectedMessageCount(1);
        mockEndpoint.expectedBodiesReceived("Hello World");

        // when
        template.sendBody("direct:start", "World");

        // then
        assertMockEndpointsSatisfied();
    }
}

