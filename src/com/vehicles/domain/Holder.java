/**
 * @author Oriol Boix Anfosso <orboan@gmail.com>
 *
 */
package com.vehicles.domain;

import java.util.Date;

//Titular
public class Holder extends Driver {
	
	private boolean hasInsurance;
	private boolean hasGarage;
	
	public Holder(String name, String surname, Date dob,
			License license) {
		super(name,surname,dob, license);

	}

	public boolean getHasInsurance() {
		return hasInsurance;
	}

	public void setHasInsurance(boolean hasInsurance) {
		this.hasInsurance = hasInsurance;
	}

	public boolean getHasGarage() {
		return hasGarage;
	}

	public void setHasGarage(boolean hasGarage) {
		this.hasGarage = hasGarage;
	}
	
	@Override
	public String toString() {
		return super.toString() + " - És TITULAR";
	}
}
