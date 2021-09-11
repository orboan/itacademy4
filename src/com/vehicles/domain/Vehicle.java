package com.vehicles.domain;

import java.util.ArrayList;
import java.util.List;

public abstract class Vehicle {
	
	protected String plate;
	protected String brand;
	protected String color;
	protected List<Wheel> wheels = new ArrayList<Wheel>();
	protected Holder holder;
	protected List<Driver> drivers = new ArrayList<>();

	public Vehicle() {}
	
	public Vehicle(String plate, String brand, String color) {
		this.plate = plate;
		this.brand = brand;
		this.color = color;
	}

	
	public abstract void addWheels(List<Wheel> frontWheels, List<Wheel> backWheels) throws Exception;

	//Afegim les rodes del davant, o les del darrere, que són iguals entre sí.
	protected void addSomeWheels(List<Wheel> wheels, int numberOfWheels) throws Exception {
		if (wheels.size() != numberOfWheels / 2) {
			throw new Exception("Error: número de rodes incorrecte: " + numberOfWheels);
			}

		Wheel modelWheel = wheels.get(0);

		// Cal comprovar que totes les rodes són iguals.
		for (Wheel wheel : wheels)
			if (!modelWheel.equals(wheel))
				throw new Exception("Error: les rodes són diferents.");

		//Finalment, les afegim a la llista de rodes del vehicle
		for (Wheel wheel : wheels)
			this.wheels.add(wheel);
	}

	
	
	public Holder getHolder() {
		return holder;
	}


	public void setHolder(Holder holder) {
		this.holder = holder;
	}

	public List<Driver> getDrivers() {
		return drivers;
	}


	public String getPlate() {
		return plate;
	}


	@Override
	public String toString() {
		int lastIndex = this.wheels.size() - 1;
		StringBuilder sb = new StringBuilder();
		sb.append("\n> Plate: " + this.plate);
		sb.append("\n> Brand: " + this.brand);
		sb.append("\n> Color: " + this.color);
		sb.append("\n> Rear wheels: ");
		sb.append("\n\t* brand: " + this.wheels.get(lastIndex).getBrand());
		sb.append("\n\t* diameter: " + this.wheels.get(lastIndex).getDiameter());
		sb.append("\n> Front wheels: ");
		sb.append("\n\t* brand: " + this.wheels.get(0).getBrand());
		sb.append("\n\t* diameter: " + this.wheels.get(0).getDiameter());
		sb.append("\nNúmero total de rodes: " + this.wheels.size() + "\n");
		if(this.holder != null)
			sb.append("Titular: " + this.holder.toString());
		return sb.toString();
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
	    Vehicle v = (Vehicle) o;
	    return (this.getPlate().equals(v.getPlate()));
	}
	
	@Override
	public int hashCode()
	{
	    final int PRIME = 41;
	    int result = 7;
	    result = PRIME * result + this.getPlate().hashCode();
	    return result;
	}
	
}
