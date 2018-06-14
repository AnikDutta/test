
package demo.cosmos.core.aircraft.service;

import java.util.List;
import java.util.Map;
import demo.cosmos.core.aircraft.model.Aircraft;

public interface AircraftService {


    List<Aircraft> getAircrafts(Map<String, Object> headers);

    Object postAircraft(Aircraft aircraft, Map<String, Object> headers);

    Aircraft getAircraft(String aircraftid, Map<String, Object> headers);

    int putAircraft(String aircraftid, Aircraft aircraft, Map<String, Object> headers);

    int deleteAircraft(String aircraftid, Map<String, Object> headers);

}
