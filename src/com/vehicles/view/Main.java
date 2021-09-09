package com.vehicles.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.vehicles.domain.Bike;
import com.vehicles.domain.Car;
import com.vehicles.domain.Vehicle;
import com.vehicles.domain.Wheel;

public class Main {

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);

		boolean continueAsking = true;
		do {
			printMainMenu();
			String resposta = in.nextLine();
			if (resposta.equals("q")) {
				println("Adéu!!");
				break;
			}
			if (!resposta.equals("1") && !resposta.equals("2")) {
				println("Error: has de seleccionar un vehicle de la llista.");
				continue;
			}
			
			Vehicle v;
			String vehicleType = null; // null que mai s'usa
			
			if (resposta.equals("1")) {
				vehicleType = "car";
			} else if (resposta.equals("2")) {
				vehicleType = "bike";
			}
			//Crea el cotxe o la moto
			v = createVehicle(in, vehicleType);
			
			//Afegeix les rodes al vehicle
			addWheels(v, in);
			
			//Imprimim el vehicle creat
			println("\n---- Dades del vehicle creat: ----");
			print(v.toString());
			
			do {
				print("\n\nVols crear un nou vehicle (s/n)?");
				resposta = in.nextLine();
				if (!resposta.equals("s") && !resposta.equals("n")) {
					println("Error: has d'indicar 's' o 'n'.");
					continue;
				}
				if(resposta.equals("n")) {
					println("Adéu!!");
					continueAsking = false;
				}
				break;
			} while (true);
		} while (continueAsking);

		in.close();

	}

	/**
	 * Mètode per a crear vehicles a partir de les dades que
	 * se li pregunten a l'usuari via TUI (Terminal User Interace).
	 * 
	 * @param in Scanner per a la TUI
	 * @param type Tipus de vehicle: "car" o "bike"
	 * @return retorna una instància de Car o Bike
	 */
	private static Vehicle createVehicle(Scanner in, String type) {
		String plate, brand, color;

		println("---- Creació d'un(a) " + type.toUpperCase() + " ----");

		// Demanem dades del vehicle
		boolean valid = true;
		do {
			print("\nIntrodueixi la matrícula del vehicle: ");
			plate = in.nextLine();
			valid = checkPlate(plate);
			if (!valid)
				print("\nError: matrícula incorrecte.\nUna matrícula correcte té 4 dígits seguits de 2 o 3 lletres.\n");
		} while (!valid);
		print("\nIntrodueixi la marca del vehicle: ");
		brand = in.nextLine();
		print("\nIntrodueixi el color del vehicle: ");
		color = in.nextLine();

		// Creem el vehicle
		Vehicle v;
		switch (type) {
		case "car":
			v = new Car(plate, brand, color);
			break;
		case "bike":
			v = new Bike(plate, brand, color);
			break;
		default:
			throw new IllegalArgumentException("Vehicle invalid: " + type);
		}

		return v;
	}

	private static void addWheels(Vehicle v, Scanner in) {
		List<Wheel> rearWheels;
		List<Wheel> frontWheels;

		// Preguntem a l'usuari dades de les rodes
		// davanteres i posteriors:
			
		rearWheels = createWheels(v, "darrere", in);
		frontWheels = createWheels(v, "davant", in);

		// Afegim les rodes al vehicle
		try {
			v.addWheels(frontWheels, rearWheels);
		} catch (Exception e) {
			println(e.getMessage());
		}
	}

	// Rodes dels vehicles
	private static List<Wheel> createWheels(Vehicle v, String wheelsLocation, Scanner in) {
		List<Wheel> wheels = new ArrayList<>(2);
		String wheelBrand;
		double diameter = 2.0;

		// Demanem dades de les rodes
		print("\n> Afegim les rodes del " + wheelsLocation + "...");
		print("\nIntrodueixi la marca de la roda/es del " + wheelsLocation + ": ");
		wheelBrand = in.nextLine();

		boolean valid = true;
		do {
			print("\nIntrodueixi el diàmetre de la roda/es del " + wheelsLocation + ": ");
			try {
				diameter = Double.parseDouble(in.nextLine());
				valid = checkDiameter(diameter);
				if (!valid)
					print("\nError: diàmetre de roda no correcte.\nEl diàmetre ha d'estar entre 0.4 i 4.\n");
			} catch (NumberFormatException e) {
				print("\nError: el diàmetre a introduir ha de ser numèric.\n");
				valid = false;
			}
		} while (!valid);

		// Introduim les dades de les rodes al List de rodes
		//Bikes tenen 1 roda al davant i al darrere
		//Cars tenen 2 rodes al davant i al darrere (cal afegir una instància més).
		wheels.add(0, new Wheel(wheelBrand, diameter));
		if(v instanceof Car) wheels.add(1, new Wheel(wheelBrand, diameter));

		return wheels;
	}

	// Comprova matricula
	private static boolean checkPlate(String plate) {
		if (plate.matches("^[0-9]{4}[a-zA-Z]{2,3}$"))
			return true;
		else
			return false;
	}

	// Comprova diametre
	private static boolean checkDiameter(double diameter) {
		if (diameter >= 0.4 && diameter <= 4.0)
			return true;
		else
			return false;
	}

	// Helpers
	private static void printMainMenu() {
		println("\n\nIndica quin tipus de vehicle vols crear: ");
		println("1 - Cotxe");
		println("2 - Moto");
		println("-----------");
		print("Prem el número del vehicle seleccionat o 'q' per a sortir: ");
	}
	
	private static void println(String str) {
		System.out.println(str);
	}

	private static void print(String str) {
		System.out.print(str);
	}
}
