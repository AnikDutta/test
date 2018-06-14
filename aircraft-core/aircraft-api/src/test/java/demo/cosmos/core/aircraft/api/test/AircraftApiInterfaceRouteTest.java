package demo.cosmos.core.aircraft.api.test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HeaderOnlyWebRequest;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.PutMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletUnitClient;

import demo.cosmos.core.aircraft.model.Aircraft;

public class AircraftApiInterfaceRouteTest extends ServletCamelRouterTestSupport {

    private String aircraftId = "1";

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
    public void testPostAircraft() throws Exception {
	MockEndpoint mockEndpoint = getMockEndpoint("mock:postAircraft");
	mockEndpoint.expectedMessageCount(1);
	mockEndpoint.message(0).body().isInstanceOf(Aircraft.class);
	mockEndpoint.expectedHeaderReceived(Exchange.HTTP_METHOD, "POST");

	Aircraft defaultAircraft = defaultAircraft();
	String body = objectMapper.writeValueAsString(defaultAircraft);

	WebRequest req = new PostMethodWebRequest(CONTEXT_URL + "/services/aircrafts", new ByteArrayInputStream(body.getBytes()), "application/json");
	ServletUnitClient client = newClient();
	client.setExceptionsThrownOnErrorStatus(false);
	WebResponse response = client.getResponse(req);
	assertMockEndpointsSatisfied();

	Aircraft aircraft = mockEndpoint.getReceivedExchanges().get(0).getIn().getBody(Aircraft.class);
	assertNotNull(aircraft);
	assertEquals(200, response.getResponseCode());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetAircrafts() throws Exception {
	MockEndpoint mockEndpoint = getMockEndpoint("mock:getAircrafts");
	mockEndpoint.expectedHeaderReceived(Exchange.HTTP_METHOD, "GET");
	mockEndpoint.returnReplyBody(new Expression() {
	    @Override
	    public List<Aircraft> evaluate(Exchange exchange, Class type) {
		ArrayList<Aircraft> aircrafts = new ArrayList<Aircraft>();
		aircrafts.add(defaultAircraft());
		return aircrafts;
	    }
	});

	WebRequest req = new GetMethodWebRequest(CONTEXT_URL + "/services/aircrafts");
	ServletUnitClient client = newClient();
	client.setExceptionsThrownOnErrorStatus(false);
	WebResponse response = client.getResponse(req);
	assertMockEndpointsSatisfied();

	String responseAsText = response.getText();
	assertNotNull(responseAsText);
	ArrayList<Aircraft> aircrafts = objectMapper.readValue(responseAsText, new TypeReference<List<Aircraft>>() {
	});
	assertEquals(200, response.getResponseCode());
	assertTrue(aircrafts.size() > 0);
    }

    @Test
    public void testGetAircraft() throws Exception {
	MockEndpoint mockEndpoint = getMockEndpoint("mock:getAircraft");
	mockEndpoint.expectedHeaderReceived("aircraftId", aircraftId);
	mockEndpoint.expectedHeaderReceived(Exchange.HTTP_METHOD, "GET");

	mockEndpoint.returnReplyBody(new Expression() {
	    @Override
	    public Aircraft evaluate(Exchange exchange, Class type) {
		Aircraft defaultAircraft = defaultAircraft();
		return defaultAircraft;
	    }
	});

	WebRequest req = new GetMethodWebRequest(CONTEXT_URL + "/services/aircrafts/" + aircraftId);
	ServletUnitClient client = newClient();
	client.setExceptionsThrownOnErrorStatus(false);
	WebResponse response = client.getResponse(req);
	assertMockEndpointsSatisfied();

	String responseAsText = response.getText();
	assertNotNull(responseAsText);
	assertEquals(200, response.getResponseCode());
    }

    @Test
    public void testPutAircraft() throws Exception {
	MockEndpoint mockEndpoint = getMockEndpoint("mock:putAircraft");
	mockEndpoint.expectedMessageCount(1);
	mockEndpoint.message(0).body().isInstanceOf(Aircraft.class);
	mockEndpoint.expectedHeaderReceived("aircraftId", aircraftId);
	mockEndpoint.expectedHeaderReceived(Exchange.HTTP_METHOD, "PUT");

	Aircraft defaultAircraft = updatedAircraft();
	String body = objectMapper.writeValueAsString(defaultAircraft);

	WebRequest req = new PutMethodWebRequest(CONTEXT_URL + "/services/aircrafts/" + aircraftId, new ByteArrayInputStream(body.getBytes()),
		"application/json");
	ServletUnitClient client = newClient();
	client.setExceptionsThrownOnErrorStatus(false);
	WebResponse response = client.getResponse(req);
	assertMockEndpointsSatisfied();

	Aircraft aircraft = mockEndpoint.getReceivedExchanges().get(0).getIn().getBody(Aircraft.class);
	assertNotNull(aircraft);
	assertEquals(200, response.getResponseCode());
    }

    @Test
    public void testDeleteAircraft() throws Exception {
	MockEndpoint mockEndpoint = getMockEndpoint("mock:deleteAircraft");
	mockEndpoint.expectedHeaderReceived("aircraftId", aircraftId);
	mockEndpoint.expectedHeaderReceived(Exchange.HTTP_METHOD, "DELETE");

	WebRequest req = new HeaderOnlyWebRequest(CONTEXT_URL + "/services/aircrafts/" + aircraftId) {
	    @Override
	    public String getMethod() {
		return "DELETE";
	    }
	};

	ServletUnitClient client = newClient();
	client.setExceptionsThrownOnErrorStatus(false);
	WebResponse response = client.getResponse(req);
	assertMockEndpointsSatisfied();
	assertEquals(200, response.getResponseCode());
    }

    @Override
    protected RoutesBuilder[] createRouteBuilders() throws Exception {
	return new RoutesBuilder[] { new MockedAircraftApiInterfaceRoute(), new MockedApiConfigurationRoute() };
    }

    @Override
    protected CamelContext createCamelContext() throws Exception {
	CamelContext context = new DefaultCamelContext(new SimpleRegistry());
	PropertiesComponent prop = context.getComponent("properties", PropertiesComponent.class);
	prop.setLocation("classpath:application.properties");
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
}
