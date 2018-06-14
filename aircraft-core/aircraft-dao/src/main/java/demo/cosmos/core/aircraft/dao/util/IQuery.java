package demo.cosmos.core.aircraft.dao.util;

public interface IQuery {
	public String queryForGetAll = "SELECT * FROM AIRCRAFT";
	public String queryForGetById = "SELECT * FROM AIRCRAFT  where id = ?";
	public String queryForCreate = "INSERT INTO AIRCRAFT (IATA, ICAO, DESCRIPTION, TYPE, CATEGORY) VALUES (?, ?, ?, ?, ?)";
	public String queryForUpdate = "update AIRCRAFT set IATA = ?, ICAO = ? , DESCRIPTION = ?, TYPE = ?, CATEGORY = ?  where ID = ?";
	public String queryForDelete = "delete from AIRCRAFT where ID = ?";
}
