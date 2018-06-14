
package demo.cosmos.core.aircraft.api.routes;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

import demo.cosmos.core.aircraft.model.Aircraft;

@Component
public class AircraftServiceInterfaceRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		onException(JsonProcessingException.class)
				.log(LoggingLevel.ERROR, "${exception.stacktrace}")
				.setBody(constant(null))
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
				.handled(true);
		
		rest()
				.description("aircraft-service REST service")
				.consumes("application/json")
				.produces("application/json")
				.get("/aircrafts")
					.id("GET /aircrafts")
					.outType(Aircraft[].class)
					.responseMessage()
						.code(200)
					.endResponseMessage()
					.to("direct:getAircrafts")
				.post("/aircrafts")
					.id("POST /aircrafts")
					.type(Aircraft.class)
					.responseMessage()
						.code(201)
					.endResponseMessage()
					.to("direct:postAircraft")
				.get("/aircrafts/{aircraftId}")
					.id("GET /aircrafts/{aircraftId}")
					.outType(Aircraft.class)
					.param()
						.name("aircraftId")
						.type(RestParamType.path)
						.dataType("string")
					.endParam()
					.responseMessage()
						.code(200)
					.endResponseMessage()
					.to("direct:getAircraft")
				.put("/aircrafts/{aircraftId}")
					.id("PUT /aircrafts/{aircraftId}")
					.type(Aircraft.class)
					.param()
						.name("aircraftId")
						.type(RestParamType.path)
						.dataType("string")
					.endParam()
					.responseMessage()
						.code(204)
					.endResponseMessage()
					.to("direct:putAircraft")
				.delete("/aircrafts/{aircraftId}")
					.id("DELETE /aircrafts/{aircraftId}")
					.param()
						.name("aircraftId")
						.type(RestParamType.path)
						.dataType("string")
					.endParam()
					.responseMessage()
						.code(204)
					.endResponseMessage()
					.to("direct:deleteAircraft")
				.get("/health")
					.id("health")
					.description("Health route endpoint")
					.outType(String.class)
					.responseMessage()
						.code(200)
					.endResponseMessage()
					.to("direct:health");
	}

}
