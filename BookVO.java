package com.proj2;

public class BookVO {
	private String reno;
	private String id;
	private String name;
	private String airline;
	private String aircraft_num;
	private String s_day;
	private String s_time;
	private String e_time;
	private String depart;
	private String arrive;
	

	public String getReno() {
		return reno;
	}
	public void setReno(String reno) {
		this.reno = reno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepart() {
		return depart;
	}
	public void setDepart(String depart) {
		this.depart = depart;
	}
	public String getArrive() {
		return arrive;
	}
	public void setArrive(String arrive) {
		this.arrive = arrive;
	}
	
	public String getAirline() {
		return airline;
	}
	public void setAirline(String airline) {
		this.airline = airline;
	}
	public String getS_day() {
		return s_day;
	}
	public void setS_day(String s_day) {
		this.s_day = s_day;
	}
	

	public String getAircraft_num() {
		return aircraft_num;
	}
	public void setAircraft_num(String aircraft_num) {
		this.aircraft_num = aircraft_num;
	}

	public String getS_time() {
		return s_time;
	}
	public void setS_time(String s_time) {
		this.s_time = s_time;
	}
	public String getE_time() {
		return e_time;
	}
	public void setE_time(String e_time) {
		this.e_time = e_time;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
