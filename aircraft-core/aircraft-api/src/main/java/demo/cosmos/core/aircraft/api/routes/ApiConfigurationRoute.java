
package demo.cosmos.core.aircraft.api.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class ApiConfigurationRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		restConfiguration()
				.component("servlet")
				.port("{{server.port}}")
				.bindingMode(RestBindingMode.json)
				.dataFormatProperty("prettyPrint", "true")
				.contextPath("/{{service.name}}/{{service.version}}")
				.enableCORS(true)
				.apiContextPath("/")
				.apiProperty("api.title", "{{api.title}}")
				.apiProperty("api.version", "{{api.version}}")
				.apiProperty("api.description", "{{api.description}}")
				.apiProperty("api.termsOfService", "{{api.termsOfService}}")
				.apiProperty("api.contact.name", "{{api.contact.name}}")
				.apiProperty("api.contact.email", "{{api.contact.email}}")
				.apiProperty("api.contact.url", "{{api.contact.url}}")
				.apiProperty("api.license.name", "{{api.license.name}}")
				.apiProperty("api.license.url", "{{api.license.url}}")
				.apiProperty("apiContextIdListing", "{{apiContextIdListing}}")
				.apiProperty("apiContextIdPattern", "{{apiContextIdPattern}}")
				.setApiContextRouteId("{{api.title}}");
	}

}
