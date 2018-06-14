package demo.cosmos.core.aircraft.api.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.PropertyPlaceholderDelegateRegistry;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.spi.Registry;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import demo.cosmos.core.aircraft.model.Aircraft;
import demo.cosmos.core.aircraft.service.AircraftService;

@RunWith(SpringRunner.class)
public class AircraftApiImplementationRouteTest extends CamelTestSupport {

    @Mock
    private AircraftService aircraftService;

    private int aircraftId = 1;

    private String aircraftIdString = String.valueOf(aircraftId);

    private final static String DEFAULT_IATA = "defaultIata";
    private final static String UPDATED_IATA = "updatedIata";
    private final static String DEFAULT_ICAO = "defaultIcao";
    private final static String UPDATED_ICAO = "updatedIcao";
    private final static String DEFAULT_DESCRIPTION = "defaultDescription";
    private final static String UPDATED_DESCRIPTION = "updatedDescription";
    private final static String DEFAULT_TYPE = "defaultType";
    private final static String UPDATED_TYPE = "updatedType";
    private final static String DEFAULT_CATEGORY = "defaultCategory";
    private final static String UPDATED_CATEGORY = "updatedCategory";

    @Test
    public void testGetAircrafts() {
	ArrayList<Aircraft> result = new ArrayList<Aircraft>();
	result.add(defaultAircraft());
	Mockito.doReturn(result).when(aircraftService).getAircrafts(Mockito.anyMapOf(String.class, Object.class));
	@SuppressWarnings("unchecked")
	List<Aircraft> aircraftList = (List<Aircraft>) template.requestBodyAndHeaders("direct:getAircrafts", null, defaultHeaders());
	Assert.assertEquals(result, aircraftList);
    }

    @Test
    public void testGetAircraftsWithException() {
	ArrayList<Aircraft> result = new ArrayList<Aircraft>();
	Mockito.doReturn(result).when(aircraftService).getAircrafts(Mockito.anyMapOf(String.class, Object.class));
	Exchange request = template.request("direct:getAircrafts", new Processor() {
	    @Override
	    public void process(Exchange exchange) throws Exception {
		exchange.getIn().setHeaders(defaultHeaders());
	    }
	});
	Assert.assertEquals(404, request.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE));
    }

    @Test
    public void testGetAircraft() {
	Aircraft result = defaultAircraft();
	Mockito.doReturn(result).when(aircraftService).getAircraft(Mockito.same(aircraftIdString), Mockito.anyMapOf(String.class, Object.class));
	Aircraft aircraft = (Aircraft) template.requestBodyAndHeaders("direct:getAircraft", null, defaultHeaders());
	Assert.assertEquals(result, aircraft);
    }

    @Test
    public void testGetAircraftWithException() {
	Mockito.doReturn(null).when(aircraftService).getAircraft(Mockito.same(aircraftIdString), Mockito.anyMapOf(String.class, Object.class));
	Exchange request = template.request("direct:getAircraft", new Processor() {
	    @Override
	    public void process(Exchange exchange) throws Exception {
		exchange.getIn().setHeaders(defaultHeaders());
	    }
	});
	Assert.assertEquals(404, request.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE));
    }

    @Test
    public void testPostAircraft() {
	Aircraft aircraft = defaultAircraft();
	Mockito.doReturn(1).when(aircraftService).postAircraft(Mockito.any(Aircraft.class), Mockito.anyMapOf(String.class, Object.class));
	Exchange request = template.request("direct:postAircraft", new Processor() {
	    @Override
	    public void process(Exchange exchange) throws Exception {
		exchange.getIn().setBody(aircraft);
		exchange.getIn().setHeaders(defaultHeaders());
	    }
	});
	Assert.assertEquals("/" + 1, request.getIn().getHeader("Location"));
    }

    @Test
    public void testPutAircraft() {
	Aircraft aircraft = updatedAircraft();
	Mockito.doReturn(1).when(aircraftService).putAircraft(Mockito.same(aircraftIdString), Mockito.any(Aircraft.class),
		Mockito.anyMapOf(String.class, Object.class));
	Object updatedRowCount = template.requestBodyAndHeaders("direct:putAircraft", aircraft, defaultHeaders());
	Assert.assertNull(updatedRowCount);
    }

    @Test
    public void testPutAircraftWithException() {
	Aircraft aircraft = updatedAircraft();
	Mockito.doReturn(0).when(aircraftService).putAircraft(Mockito.same(aircraftIdString), Mockito.any(Aircraft.class),
		Mockito.anyMapOf(String.class, Object.class));
	Exchange request = template.request("direct:putAircraft", new Processor() {
	    @Override
	    public void process(Exchange exchange) throws Exception {
		exchange.getIn().setBody(aircraft);
		exchange.getIn().setHeaders(defaultHeaders());
	    }
	});
	Assert.assertEquals(404, request.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE));
    }

    @Test
    public void testDeleteAircraft() {
	Mockito.doReturn(1).when(aircraftService).deleteAircraft(Mockito.same(aircraftIdString), Mockito.anyMapOf(String.class, Object.class));
	Object updatedRowCount = template.requestBodyAndHeaders("direct:deleteAircraft", null, defaultHeaders());
	Assert.assertNull(updatedRowCount);
    }

    @Test
    public void testDeleteAircraftWithException() {
	Mockito.doReturn(0).when(aircraftService).deleteAircraft(Mockito.same(aircraftIdString), Mockito.anyMapOf(String.class, Object.class));
	Exchange request = template.request("direct:deleteAircraft", new Processor() {
	    @Override
	    public void process(Exchange exchange) throws Exception {
		exchange.getIn().setHeaders(defaultHeaders());
	    }
	});
	Assert.assertEquals(404, request.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE));
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
	return new MockedAircraftApiImplementationRoute();
    }

    @Override
    protected CamelContext createCamelContext() throws Exception {
	CamelContext context = new DefaultCamelContext(new SimpleRegistry());
	PropertiesComponent prop = context.getComponent("properties", PropertiesComponent.class);
	prop.setLocation("classpath:log.messages");
	Registry registry = context.getRegistry();
	if (registry instanceof PropertyPlaceholderDelegateRegistry) {
	    registry = ((PropertyPlaceholderDelegateRegistry) registry).getRegistry();
	    ((SimpleRegistry) registry).put("aircraftService", aircraftService);
	    ((SimpleRegistry) registry).put("accessPolicy", new MockedAccessPolicy());
	}
	return context;
    }

    private Aircraft defaultAircraft() {
	Aircraft aircraft = new Aircraft();
	aircraft.setIata(DEFAULT_IATA);
	aircraft.setIcao(DEFAULT_ICAO);
	aircraft.setDescription(DEFAULT_DESCRIPTION);
	aircraft.setType(DEFAULT_TYPE);
	aircraft.setCategory(DEFAULT_CATEGORY);
	return aircraft;
    }

    private Aircraft updatedAircraft() {
	Aircraft aircraft = new Aircraft();
	aircraft.setIata(UPDATED_IATA);
	aircraft.setIcao(UPDATED_ICAO);
	aircraft.setDescription(UPDATED_DESCRIPTION);
	aircraft.setType(UPDATED_TYPE);
	aircraft.setCategory(UPDATED_CATEGORY);
	return aircraft;
    }

    private Map<String, Object> defaultHeaders() {
	Map<String, Object> headers = new HashMap<>();
	headers.put("aircraftId", aircraftIdString);
	return headers;
    }
}