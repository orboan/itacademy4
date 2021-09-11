package com.vehicles.domain;

import java.util.List;

public class Car extends Vehicle {

	private final int NUMBER_OF_WHEELS = 4;
	
	public Car() {}
	
	public Car(String plate, String brand, String color) {
		super(plate, brand, color);
	}
	
	@Override
	public void addWheels(List<Wheel> frontWheels, List<Wheel> backWheels) throws Exception {
		addSomeWheels(frontWheels, NUMBER_OF_WHEELS);
		addSomeWheels(backWheels, NUMBER_OF_WHEELS);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("-- Car: ");
		sb.append(super.toString());
		return sb.toString();
	}

}
