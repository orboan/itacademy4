/**
 * @author Oriol Boix Anfosso <orboan@gmail.com>
 *
 */
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
		return super.toString() + " - License id: " + this.license.getId()
		 + " - License Type: " + this.license.getType();
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
	     
	    //La llicència és única per a cada usuari
	    Driver d = (Driver) o;
	    return (this.getLicense().getId().equals(d.getLicense().getId()));
	}
	
	@Override
	public int hashCode()
	{
	    final int PRIME = 37;
	    int result = 3;
	    result = PRIME * result + this.getLicense().getId().hashCode();
	    return result;
	}
}
