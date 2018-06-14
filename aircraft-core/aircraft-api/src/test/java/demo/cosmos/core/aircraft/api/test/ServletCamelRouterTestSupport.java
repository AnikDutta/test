package demo.cosmos.core.aircraft.api.test;

import java.io.InputStream;

import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.After;
import org.junit.Before;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

public class ServletCamelRouterTestSupport extends CamelTestSupport {
    public static final String CONTEXT = "/testContext";
    public static final String CONTEXT_URL = "http://localhost/testContext";
    protected ServletRunner sr;
    protected boolean startCamelContext = true;
    protected ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
	objectMapper = new ObjectMapper();
	InputStream is = this.getClass().getResourceAsStream(getConfiguration());
	assertNotNull("The configuration input stream should not be null", is);
	sr = new ServletRunner(is, CONTEXT);
	HttpUnitOptions.setExceptionsThrownOnErrorStatus(true);
	if (startCamelContext) {
	    super.setUp();
	}
    }

    @After
    public void tearDown() throws Exception {
	objectMapper = null;
	if (startCamelContext) {
	    super.tearDown();
	}
	sr.shutDown();
    }

    /**
     * @return The web.xml to use for testing.
     */
    protected String getConfiguration() {
	return "/org/apache/camel/component/servlet/web.xml";
    }

    protected ServletUnitClient newClient() {
	return sr.newClient();
    }

}
