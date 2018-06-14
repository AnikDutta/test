package demo.cosmos.core.aircraft.model;

import java.io.Serializable;

public class Aircraft implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String iata;
    private String icao;
    private String description;
    private String type;
    private String category;
 
    public Aircraft() {
	super();
    }

    public Aircraft(Long id, String iata, String icao, String description, String type, String category) {
	super();
	this.id = id;
	this.iata = iata;
	this.icao = icao;
	this.description = description;
	this.type = type;
	this.category = category;
    } 

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getIata() {
	return iata;
    }

    public void setIata(String iata) {
	this.iata = iata;
    }

    public String getIcao() {
	return icao;
    }

    public void setIcao(String icao) {
	this.icao = icao;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public String getCategory() {
	return category;
    }

    public void setCategory(String category) {
	this.category = category;
    }
}
