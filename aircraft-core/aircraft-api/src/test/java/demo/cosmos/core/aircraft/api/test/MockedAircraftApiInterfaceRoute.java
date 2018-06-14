package demo.cosmos.core.aircraft.api.test;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestParamType;

import com.fasterxml.jackson.core.JsonProcessingException;

import demo.cosmos.core.aircraft.model.Aircraft;

public class MockedAircraftApiInterfaceRoute extends RouteBuilder {

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
					.to("mock:getAircrafts")
				.post("/aircrafts")
					.id("POST /aircrafts")
					.type(Aircraft.class)
					.responseMessage()
						.code(201)
					.endResponseMessage()
					.to("mock:postAircraft")
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
					.to("mock:getAircraft")
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
					.to("mock:putAircraft")
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
					.to("mock:deleteAircraft")
				.get("/health")
					.id("health")
					.description("Health route endpoint")
					.outType(String.class)
					.responseMessage()
						.code(200)
					.endResponseMessage()
					.to("mock:health");
	}

}