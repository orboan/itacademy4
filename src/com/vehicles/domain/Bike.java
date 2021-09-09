package com.vehicles.domain;

import java.util.List;

public class Bike extends Vehicle {
	
	private final int NUMBER_OF_WHEELS = 2;

	public Bike(String plate, String brand, String color) {
		super(plate, brand, color);
	}
	
	@Override
	public void addWheels(List<Wheel> frontWheels, List<Wheel> backWheels) throws Exception {
		addSomeWheels(frontWheels, NUMBER_OF_WHEELS);
		addSomeWheels(backWheels, NUMBER_OF_WHEELS);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("-- Bike: ");
		sb.append(super.toString());
		return sb.toString();
	}
}
