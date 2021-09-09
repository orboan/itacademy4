package com.vehicles.domain;

import java.util.List;

public class Bike extends Vehicle {

	public Bike(String plate, String brand, String color) {
		super(plate, brand, color);
	}
	
	@Override
	public void addWheels(List<Wheel> frontWheels, List<Wheel> backWheels) throws Exception {
		addOneWheel(frontWheels);
		addOneWheel(backWheels);
	}

	private void addOneWheel(List<Wheel> wheels) throws Exception {
		if (wheels.size() != 1)
			throw new Exception("Error: número de rodes incorrecte.");

		Wheel wheel = wheels.get(0);

		this.wheels.add(wheel);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("-- Bike: ");
		sb.append(super.toString());
		sb.append("\n> Rear wheel: ");
		sb.append("\n\t* brand: " + this.wheels.get(1).getBrand());
		sb.append("\n\t* diameter: " + this.wheels.get(1).getDiameter());
		sb.append("\n> Front wheel: ");
		sb.append("\n\t* brand: " + this.wheels.get(0).getBrand());
		sb.append("\n\t* diameter: " + this.wheels.get(0).getDiameter());
		
		return sb.toString();
	}
}
