package com.vehicles.domain;

import java.util.Date;

public class License {
	private final String ID;
	private Date expirationDate;
	private String fullName;
	private LicenseType type;
	
	public License(String id, Date expirationDate, LicenseType type) {
		this.ID = id;
		this.expirationDate = expirationDate;
		this.type = type;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getID() {
		return ID;
	}
	
	public LicenseType getType() {
		return type;
	}

	public void setType(LicenseType type) {
		this.type = type;
	}



	public enum LicenseType{
		CAR, BIKE, TRUCK
	}
	
}
