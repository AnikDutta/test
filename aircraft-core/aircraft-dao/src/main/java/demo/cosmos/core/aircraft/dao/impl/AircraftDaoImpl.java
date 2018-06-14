
package demo.cosmos.core.aircraft.dao.impl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.google.common.base.CaseFormat;

import demo.cosmos.core.aircraft.dao.AircraftDao;
import demo.cosmos.core.aircraft.dao.entity.AircraftEntity;
import demo.cosmos.core.aircraft.dao.mapper.AircraftMapper;
import demo.cosmos.core.aircraft.dao.util.IQuery;

@Repository
public class AircraftDaoImpl implements AircraftDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<AircraftEntity> getAircrafts(Map<String, Object> headers) {
		StringBuffer query = new StringBuffer();
		Class<AircraftEntity> aircraftClass = AircraftEntity.class;
		Field[] fields = aircraftClass.getDeclaredFields();
		List<String> paramNames = new ArrayList<String>();
		List<String> paramValues = new ArrayList<String>();

		for (Field field : fields) {
			String fieldName = field.getName();
			if (headers.containsKey(fieldName)) {
				paramNames.add(fieldName);
				paramValues.add((String) headers.get(fieldName));
			}
		}
		query.append(IQuery.queryForGetAll);
		if (paramNames.size() > 0) {
			query.append(" WHERE ");
			for (int i = 0; i < paramNames.size(); i++) {
				if (i == paramNames.size() - 1) {
					query.append(getUnderScoreSeparatedFromCamelCasing(paramNames.get(i))).append(" LIKE ? ");
				} else {
					query.append(getUnderScoreSeparatedFromCamelCasing(paramNames.get(i))).append(" LIKE ? AND ");
				}
			}
		}
		List<AircraftEntity> aircrafts = jdbcTemplate.query(query.toString(), paramValues.toArray(),
				new AircraftMapper());
		return aircrafts;
	}

	public Object postAircraft(AircraftEntity aircraftEntity, Map<String, Object> headers) {
		String query = IQuery.queryForCreate;
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		PreparedStatementCreator psc = new PreparedStatementCreator() {

			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(query, new String[] { "ID" });
				ps.setString(1, aircraftEntity.getIata());
				ps.setString(2, aircraftEntity.getIcao());
				ps.setString(3, aircraftEntity.getDescription());
				ps.setString(4, aircraftEntity.getType());
				ps.setString(5, aircraftEntity.getCategory());
				return ps;
			}
		};
		jdbcTemplate.update(psc, generatedKeyHolder);
		Long generatedId = (generatedKeyHolder.getKey() != null ? generatedKeyHolder.getKey().longValue() : null);
		return generatedId;
	}

	public AircraftEntity getAircraft(String aircraftid, Map<String, Object> headers) {
		String query = IQuery.queryForGetById;
		List<AircraftEntity> aircrafts = jdbcTemplate.query(query, new Object[] { aircraftid }, new AircraftMapper());
		if (aircrafts.size() == 1) {
			return aircrafts.get(0);
		}
		return null;
	}

	public int putAircraft(AircraftEntity aircraftEntity, Map<String, Object> headers) {
		String SQL = IQuery.queryForUpdate;
		return jdbcTemplate.update(new PreparedStatementCreator() {

			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(SQL);
				ps.setString(1, aircraftEntity.getIata());
				ps.setString(2, aircraftEntity.getIcao());
				ps.setString(3, aircraftEntity.getDescription());
				ps.setString(4, aircraftEntity.getType());
				ps.setString(5, aircraftEntity.getCategory());
				ps.setLong(6, aircraftEntity.getId());
				return ps;
			}
		});
	}

	public int deleteAircraft(String aircraftid, Map<String, Object> headers) {
		String sql = IQuery.queryForDelete;
		return jdbcTemplate.update(sql, aircraftid);
	}

	private String getUnderScoreSeparatedFromCamelCasing(String camelCasingWord) {
		return CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, camelCasingWord);
	}

}
