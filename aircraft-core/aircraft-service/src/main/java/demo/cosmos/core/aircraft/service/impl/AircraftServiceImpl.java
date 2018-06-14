
package demo.cosmos.core.aircraft.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import demo.cosmos.core.aircraft.dao.AircraftDao;
import demo.cosmos.core.aircraft.dao.entity.AircraftEntity;
import demo.cosmos.core.aircraft.model.Aircraft;
import demo.cosmos.core.aircraft.service.AircraftService;
import demo.cosmos.core.aircraft.service.mapper.AircraftMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("aircraftService")
public class AircraftServiceImpl
    implements AircraftService
{

    @Autowired
    private AircraftDao aircraftDao;
    @Autowired
    private AircraftMapper aircraftMapper;

    @Override
    public List<Aircraft> getAircrafts(Map<String, Object> headers) {
        List<Aircraft> aircraftList = new ArrayList<Aircraft>();
        for (AircraftEntity aircraftEntity: aircraftDao.getAircrafts(headers)) {
            aircraftList.add(aircraftMapper.convertToResource(aircraftEntity));
        }
        return aircraftList;
    }

    @Override
    public Object postAircraft(Aircraft aircraft, Map<String, Object> headers) {
        return aircraftDao.postAircraft(aircraftMapper.convertToEntity(aircraft), headers);
    }

    @Override
    public Aircraft getAircraft(String aircraftid, Map<String, Object> headers) {
        return aircraftMapper.convertToResource(aircraftDao.getAircraft(aircraftid, headers));
    }

    @Override
    public int putAircraft(String aircraftid, Aircraft aircraft, Map<String, Object> headers) {
    	aircraft.setId(Long.parseLong(aircraftid));
        return aircraftDao.putAircraft(aircraftMapper.convertToEntity(aircraft), headers);
    }

    @Override
    public int deleteAircraft(String aircraftid, Map<String, Object> headers) {
        return aircraftDao.deleteAircraft(aircraftid, headers);
    }

}
