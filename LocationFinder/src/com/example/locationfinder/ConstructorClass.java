package com.example.locationfinder;

public class ConstructorClass {

	private String latiude;
	private String longitude;
	private String address;
	public ConstructorClass(String latiude, String longitude,
			String address) {
		super();
		this.latiude = latiude;
		this.longitude = longitude;
		this.address = address;
	}
	public String getLatiude() {
		return latiude;
	}
	public void setLatiude(String latiude) {
		this.latiude = latiude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
