package demo.cosmos.core.aircraft.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import demo.cosmos.core.aircraft.dao.entity.AircraftEntity;

public class AircraftMapper implements RowMapper<AircraftEntity> {

	public AircraftEntity mapRow(ResultSet rs, int rowNo) throws SQLException {
		AircraftEntity aircarft = new AircraftEntity();
		aircarft.setId(rs.getLong("ID"));
		aircarft.setCategory(rs.getString("CATEGORY"));
		aircarft.setDescription(rs.getString("DESCRIPTION"));
		aircarft.setIata(rs.getString("IATA"));
		aircarft.setIcao(rs.getString("ICAO"));
		aircarft.setType(rs.getString("TYPE"));
		return aircarft;
	}

}
