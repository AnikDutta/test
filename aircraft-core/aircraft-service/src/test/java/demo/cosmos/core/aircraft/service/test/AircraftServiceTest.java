package demo.cosmos.core.aircraft.service.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import demo.cosmos.core.aircraft.dao.AircraftDao;
import demo.cosmos.core.aircraft.dao.entity.AircraftEntity;
import demo.cosmos.core.aircraft.model.Aircraft;
import demo.cosmos.core.aircraft.service.AircraftService;
import demo.cosmos.core.aircraft.service.test.config.TestConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
public class AircraftServiceTest {

    @MockBean
    private AircraftDao aircraftDao;

    @Autowired
    private AircraftService aircraftService;

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
    public void testPostAircraft() {
	Aircraft aircraft = defaultAircraft();
	Mockito.doReturn(aircraftId).when(aircraftDao).postAircraft(Mockito.any(AircraftEntity.class), Mockito.anyMapOf(String.class, Object.class));
	Assert.assertEquals(aircraftId, aircraftService.postAircraft(aircraft, defaultHeaders()));
    }

    @Test
    public void testGetAircraft() {
	AircraftEntity aircraftEntity = defaultAircraftEntity();
	Mockito.when(aircraftDao.getAircraft(Mockito.same(aircraftId), Mockito.anyMapOf(String.class, Object.class))).thenReturn(aircraftEntity);
	Assert.assertNotNull(aircraftService.getAircraft(aircraftId, defaultHeaders()));
    }

    @Test
    public void testGetAircrafts() {
	AircraftEntity aircraftEntity = defaultAircraftEntity();
	List<AircraftEntity> result = new ArrayList<AircraftEntity>();
	result.add(aircraftEntity);
	Mockito.when(aircraftDao.getAircrafts(Mockito.anyMapOf(String.class, Object.class))).thenReturn(result);
	Assert.assertEquals(result.size(), aircraftService.getAircrafts(defaultHeaders()).size());
    }

    @Test
    public void testPutAircraft() {
	Aircraft aircraft = updatedAircraft();
	Mockito.doReturn(1).when(aircraftDao).putAircraft(Mockito.any(AircraftEntity.class), Mockito.anyMapOf(String.class, Object.class));
	Assert.assertEquals(1, aircraftService.putAircraft(aircraftId, aircraft, defaultHeaders()));
    }

    @Test
    public void testDeleteAircraft() {
	Mockito.when(aircraftDao.deleteAircraft(Mockito.same(aircraftId), Mockito.anyMapOf(String.class, Object.class))).thenReturn(1);
	Map<String, Object> headers = defaultHeaders();
	Assert.assertEquals(1, aircraftService.deleteAircraft(aircraftId, headers));
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

    private AircraftEntity defaultAircraftEntity() {
	AircraftEntity aircraftEntity = new AircraftEntity();
	aircraftEntity.setIata(DEFAULT_IATA);
	aircraftEntity.setIcao(DEFAULT_ICAO);
	aircraftEntity.setDescription(DEFAULT_DESCRIPTION);
	aircraftEntity.setType(DEFAULT_TYPE);
	aircraftEntity.setCategory(DEFAULT_CATEGORY);
	return aircraftEntity;
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
	return headers;
    }
}