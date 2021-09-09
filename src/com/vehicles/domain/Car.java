package com.vehicles.domain;

import java.util.List;

public class Car extends Vehicle {

	public Car(String plate, String brand, String color) {
		super(plate, brand, color);
	}
	
	@Override
	public void addWheels(List<Wheel> frontWheels, List<Wheel> backWheels) throws Exception {
		addTwoWheels(frontWheels);
		addTwoWheels(backWheels);
	}

	private void addTwoWheels(List<Wheel> wheels) throws Exception {
		if (wheels.size() != 2)
			throw new Exception("Error: número de rodes incorrecte.");

		Wheel rightWheel = wheels.get(0);
		Wheel leftWheel = wheels.get(1);

		if (!rightWheel.equals(leftWheel))
			throw new Exception("Error: les rodes són diferents.");

		this.wheels.add(leftWheel);
		this.wheels.add(rightWheel);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("-- Car: ");
		sb.append(super.toString());
//		sb.append("\n> Plate: " + this.plate);
//		sb.append("\n> Brand: " + this.brand);
//		sb.append("\n> Color: " + this.color);
		sb.append("\n> Rear wheels: ");
		sb.append("\n\t* brand: " + this.wheels.get(2).getBrand());
		sb.append("\n\t* diameter: " + this.wheels.get(2).getDiameter());
		sb.append("\n> Front wheels: ");
		sb.append("\n\t* brand: " + this.wheels.get(0).getBrand());
		sb.append("\n\t* diameter: " + this.wheels.get(0).getDiameter());
		
		return sb.toString();
	}

}
