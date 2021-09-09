package com.vehicles.domain;

import java.util.Date;

//Conductor
public class Driver extends Person {
	
	protected final License license;
	
	public Driver(String name, String surname, Date dob,
			License license) {
		super(name,surname,dob);
		this.license = license;

	}

	public License getLicense() {
		return license;
	}
	
	@Override
	public String toString() {
		return super.toString() + " - License id: " + this.license.getID();
	}
}
