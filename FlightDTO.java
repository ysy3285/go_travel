package com.proj2;

public class FlightDTO {
	private String airline;
	private String aircraft_num;
	private String depart;
	private String arrive;
	private String s_time;
	private String e_time;
	
	public String getAirline() {
		return airline;
	}
	public void setAirline(String airline) {
		this.airline = airline;
	}
	public String getAircraft_num() {
		return aircraft_num;
	}
	public void setAircraft_num(String aircraft_num) {
		this.aircraft_num = aircraft_num;
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
	@Override
	public String toString() {
		String str;
		str = String.format("%s\t%s\t%s\t%s\t%s\t%s\t\n", getAirline(),getAircraft_num(),getDepart(),getArrive(),getS_time(),getE_time());
		return str;
	}
	
}
