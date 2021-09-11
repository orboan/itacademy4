package com.vehicles.domain;

import java.util.Date;

public class License {
	private final String Id;
	private Date expirationDate;
	private String fullName;
	private LicenseType type;
	
	public License(String id, Date expirationDate, LicenseType type) {
		this.Id = id;
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

	public String getId() {
		return Id;
	}
	
	public LicenseType getType() {
		return type;
	}

	public void setType(LicenseType type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object o) {
	    if(o == null)
	    {
	        return false;
	    }
	    if (o == this)
	    {
	        return true;
	    }
	    if (getClass() != o.getClass())
	    {
	        return false;
	    }
	     
	    License l = (License) o;
	    return (this.getId() == l.getId());
	}
	
	@Override
	public int hashCode()
	{
	    final int PRIME = 31;
	    int result = 1;
	    result = PRIME * result + getId().hashCode();
	    return result;
	}
	
	public enum LicenseType{
		CAR, BIKE, TRUCK
	}
	
}
