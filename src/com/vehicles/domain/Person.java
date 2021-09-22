/**
 * @author Oriol Boix Anfosso <orboan@gmail.com>
 *
 */
package com.vehicles.domain;

import java.util.Date;

public class Person {
	//nom, cognoms, data de naixement
	private final String name;
	private final String surname;
	private final Date dob; //Date Of Birth
	
	public Person(String name, String surname, Date dob) {
		this.name = name;
		this.surname = surname;
		this.dob = dob;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public Date getDob() {
		return dob;
	}
	
	@Override
	public String toString() {
		return name + " " + surname + " - " + dob;
	}
}
