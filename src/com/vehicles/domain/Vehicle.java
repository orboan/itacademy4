package com.vehicles.domain;

import java.util.ArrayList;
import java.util.List;

public abstract class Vehicle {

	protected String plate;
	protected String brand;
	protected String color;
	protected List<Wheel> wheels = new ArrayList<Wheel>();

	public Vehicle(String plate, String brand, String color) {
		this.plate = plate;
		this.brand = brand;
		this.color = color;
	}
	
	public abstract void addWheels(List<Wheel> frontWheels, List<Wheel> backWheels) 
			throws Exception;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n> Plate: " + this.plate);
		sb.append("\n> Brand: " + this.brand);
		sb.append("\n> Color: " + this.color);
		
		return sb.toString();
	}

}
