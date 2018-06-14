
package demo.cosmos.core.aircraft.dao;

import java.util.List;
import java.util.Map;
import demo.cosmos.core.aircraft.dao.entity.AircraftEntity;

public interface AircraftDao {


    List<AircraftEntity> getAircrafts(Map<String, Object> headers);

    Object postAircraft(AircraftEntity aircraftEntity, Map<String, Object> headers);

    AircraftEntity getAircraft(String aircraftid, Map<String, Object> headers);

    int putAircraft(AircraftEntity aircraftEntity, Map<String, Object> headers);

    int deleteAircraft(String aircraftid, Map<String, Object> headers);

}
