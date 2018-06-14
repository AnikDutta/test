package demo.cosmos.core.aircraft.api.test;

import javax.validation.ValidationException;

import org.apache.camel.CamelAuthorizationException;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

import javassist.NotFoundException;

public class MockedAircraftApiImplementationRoute extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		onException(ValidationException.class)
				.log(LoggingLevel.ERROR, "${exception.stacktrace}")
				.setBody(constant(null))
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
				.handled(true);
		
		onException(NotFoundException.class)
				.log(LoggingLevel.ERROR, "${exception.stacktrace}")
				.setBody(constant(null))
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(404))
				.handled(true);
		
		onException(CamelAuthorizationException.class)
				.log(LoggingLevel.ERROR, "${exception.stacktrace}")
				.setBody(constant(null))
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(403))
				.handled(true);
		
		onException(Exception.class)
				.log(LoggingLevel.ERROR, "${exception.stacktrace}")
				.setBody(constant(null))
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
				.handled(true);
		
		from("direct:getAircrafts")
				.routeId("direct:getAircrafts")
				.policy("accessPolicy")
				.log(LoggingLevel.INFO, "{{aircraft-service.implementationRoute.log.message.key.1}}")
				.to("bean:aircraftService?method=getAircrafts(${headers})")
				.choice()
				.when(simple("${body.size} == 0"))
					.log(LoggingLevel.ERROR, "{{aircraft-service.implementationRoute.log.message.key.2}}")
					.throwException(new NotFoundException("No records found"))
				.otherwise()
				.log(LoggingLevel.INFO, "{{aircraft-service.implementationRoute.log.message.key.3}}");
		
		from("direct:postAircraft")
				.routeId("direct:postAircraft")
				.policy("accessPolicy")
				.log(LoggingLevel.INFO, "{{aircraft-service.implementationRoute.log.message.key.4}}")
				.to("bean:aircraftService?method=postAircraft(${body}, ${headers})")
				.choice()
				.when(simple("${body} != null"))
					.setHeader("Location", simple("${header.CamelHttpUri}/${body}"))
					.setBody(constant(null))
					.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201))
					.log(LoggingLevel.INFO, "{{aircraft-service.implementationRoute.log.message.key.5}}");
		
		from("direct:getAircraft")
				.routeId("direct:getAircraft")
				.policy("accessPolicy")
				.log(LoggingLevel.INFO, "{{aircraft-service.implementationRoute.log.message.key.6}}")
				.to("bean:aircraftService?method=getAircraft(${header.aircraftId}, ${headers})")
				.choice()
				.when(simple("${body} == null"))
					.log(LoggingLevel.ERROR, "{{aircraft-service.implementationRoute.log.message.key.7}}")
					.throwException(new NotFoundException("No record found"))
				.otherwise()
					.log(LoggingLevel.INFO, "{{aircraft-service.implementationRoute.log.message.key.3}}");
		
		from("direct:putAircraft")
				.routeId("direct:putAircraft")
				.policy("accessPolicy")
				.log(LoggingLevel.INFO, "{{aircraft-service.implementationRoute.log.message.key.8}}")
				.to("bean:aircraftService?method=putAircraft(${header.aircraftId}, ${body}, ${headers})")
				.choice()
				.when(simple("${body} == 0"))
					.log(LoggingLevel.ERROR, "{{aircraft-service.implementationRoute.log.message.key.9}}")
					.throwException(new NotFoundException("No record found"))
				.otherwise()
					.setBody(constant(null))
					.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204))
					.log(LoggingLevel.INFO, "{{aircraft-service.implementationRoute.log.message.key.10}}");
		
		from("direct:deleteAircraft")
				.routeId("direct:deleteAircraft")
				.policy("accessPolicy")
				.log(LoggingLevel.INFO, "{{aircraft-service.implementationRoute.log.message.key.11}}")
				.to("bean:aircraftService?method=deleteAircraft(${header.aircraftId}, ${headers})")
				.choice()
				.when(simple("${body} == 0"))
					.log(LoggingLevel.ERROR, "{{aircraft-service.implementationRoute.log.message.key.12}}")
					.throwException(new NotFoundException("No record found"))
				.otherwise()
					.setBody(constant(null))
					.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204))
					.log(LoggingLevel.INFO, "{{aircraft-service.implementationRoute.log.message.key.13}}"); 
	}

}