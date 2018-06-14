package demo.cosmos.core.aircraft.api.test;

import org.apache.camel.Processor;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.spi.Policy;
import org.apache.camel.spi.RouteContext;

public class MockedAccessPolicy implements Policy {

    @Override
    public void beforeWrap(RouteContext routeContext, ProcessorDefinition<?> definition) {
    }

    @Override
    public Processor wrap(RouteContext routeContext, Processor processor) {
	return processor;
    }

}
