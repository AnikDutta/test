package demo.cosmos.core.aircraft.dao.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.junit4.SpringRunner;

import demo.cosmos.core.aircraft.dao.AircraftDao;
import demo.cosmos.core.aircraft.dao.entity.AircraftEntity;
import demo.cosmos.core.aircraft.dao.mapper.AircraftMapper;
import demo.cosmos.core.aircraft.dao.test.config.TestConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TestConfiguration.class })
public class AircraftDaoTest {

    @Autowired
    private AircraftDao aircraftDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
	AircraftEntity aircraftEntity = defaultAircraftEntity();
	Object result = 101L;
	Mockito.when(jdbcTemplate.update(Mockito.any(PreparedStatementCreator.class), Mockito.any(KeyHolder.class))).thenAnswer(new Answer<Object>() {
	    public Object answer(InvocationOnMock invocation) {
		Map<String, Object> keyMap = new HashMap<String, Object>();
		keyMap.put("generatedId", result);
		invocation.getArgumentAt(1, KeyHolder.class).getKeyList().add(keyMap);
		return result;
	    }
	}).thenReturn(1);
	Assert.assertEquals(result, aircraftDao.postAircraft(aircraftEntity, defaultHeaders()));
    }

    @Test
    public void testGetAircraft() {
	List<AircraftEntity> result = new ArrayList<AircraftEntity>();
	AircraftEntity aircraftEntity = defaultAircraftEntity();
	result.add(aircraftEntity);
	Mockito.doReturn(result).when(jdbcTemplate).query(Mockito.anyString(), Mockito.any(Object[].class), Mockito.any(AircraftMapper.class));
	Assert.assertEquals(result.get(0), aircraftDao.getAircraft(aircraftId, defaultHeaders()));
    }

    @Test
    public void testGetAircrafts() {
	List<AircraftEntity> result = new ArrayList<AircraftEntity>();
	AircraftEntity aircraftEntity = defaultAircraftEntity();
	result.add(aircraftEntity);
	Mockito.doReturn(result).when(jdbcTemplate).query(Mockito.anyString(), Mockito.any(Object[].class), Mockito.any(AircraftMapper.class));
	Assert.assertEquals(result.size(), aircraftDao.getAircrafts(defaultHeaders()).size());
    }

    @Test
    public void testPutAircraft() {
	AircraftEntity aircraftEntity = updatedAircraftEntity();
	Mockito.doReturn(1).when(jdbcTemplate).update(Mockito.any(PreparedStatementCreator.class));
	Assert.assertEquals(1, aircraftDao.putAircraft(aircraftEntity, defaultHeaders()));
    }

    @Test
    public void testDeleteAircraft() {
	Mockito.doReturn(1).when(jdbcTemplate).update(Mockito.anyString(), Mockito.same(aircraftId));
	Assert.assertEquals(1, aircraftDao.deleteAircraft(aircraftId, defaultHeaders()));
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

    private AircraftEntity updatedAircraftEntity() {
	AircraftEntity aircraftEntity = new AircraftEntity();
	aircraftEntity.setIata(UPDATED_IATA);
	aircraftEntity.setIcao(UPDATED_ICAO);
	aircraftEntity.setDescription(UPDATED_DESCRIPTION);
	aircraftEntity.setType(UPDATED_TYPE);
	aircraftEntity.setCategory(UPDATED_CATEGORY);
	return aircraftEntity;
    }

    private Map<String, Object> defaultHeaders() {
	Map<String, Object> headers = new HashMap<>();
	return headers;
    }

}