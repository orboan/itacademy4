package com.vehicles.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.vehicles.domain.Bike;
import com.vehicles.domain.Car;
import com.vehicles.domain.Holder;
import com.vehicles.domain.License;
import com.vehicles.domain.License.LicenseType;
import com.vehicles.domain.Truck;
import com.vehicles.domain.Vehicle;
import com.vehicles.domain.Wheel;

public class Main {

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);

		boolean continueAsking = true;

		// Creació d'un titular
		Holder holder = createHolderWithFeedBack(in);

		if (holder == null) // Si hi ha hagut Excepció, no continuem.
			continueAsking = false;

		// Creació dels vehicles
		while (continueAsking) {

			/*
			 * Selecciona el tipus de vehicle a crear. Ha de coincidir amb el tipus de
			 * llicència que té el titular.
			 */
			String vehicleType = selectVehicleType(in, holder);
			if (vehicleType == null)
				break; // L'usuari ha premut 'q' per a sortir

			Vehicle v;
			// Crea el cotxe, la moto o el camió
			v = createVehicle(in, vehicleType, holder);

			// Afegeix les rodes al vehicle
			addWheels(v, in);

			// Imprimim el vehicle creat
			println("\n---- Dades del vehicle creat: ----");
			print(v.toString());

			// Vols ser el conductor?
			buildDriver(in, v, holder, vehicleType);

			// Vols crear un nou vehicle?
			continueAsking = askIfNewVehicle(in, holder);
		}
		in.close();
	}

	//Pregunta si es vol crear un nou vehicle
	private static boolean askIfNewVehicle(Scanner in, Holder holder) {
		String resposta;
		do {
			print("\n\nVols crear un nou vehicle per al titular " + holder.getName() + " " + holder.getSurname()
					+ " (s/n)? ");
			resposta = in.nextLine();
			if (!resposta.equals("s") && !resposta.equals("n")) {
				println("Error: has d'indicar 's' o 'n'.");
				continue;
			}
			if (resposta.equals("n")) {
				println("Adéu!!");
				return false;
			}
			break;
		} while (true);
		return true;
	}

	//Si no volem ser conductors, crea un nou conductor
	private static void buildDriver(Scanner in, Vehicle v, Holder holder, String vehicleType) {
		boolean isDriver = askIfDriver(in, v);
		if (isDriver) {
			v.setDriver(holder);
			println("\nEl conductor d'aquest " + vehicleType.toUpperCase() + " és: " + v.getDriver().getName() + " "
					+ v.getDriver().getSurname());
		} else {
			println("\n---- Cal crear un nou titular que serà el conductor (amb llicència compatible) ----");
			holder = createHolderWithFeedBack(in, vehicleType);
			v.setDriver(holder);
			println("\nEl titular creat és ara conductor del vehicle amb matrícula " + v.getPlate());
		}
	}

	//Pregunta si volem ser conductors
	private static boolean askIfDriver(Scanner in, Vehicle v) {
		String resposta;

		do {
			print("\n\nVol ser el conductor d'aquest vehicle (s/n)? ");
			resposta = in.nextLine();
			if (!resposta.equals("s") && !resposta.equals("n")) {
				println("Error: has d'indicar 's' o 'n'.");
				continue;
			}
			if (resposta.equals("n")) {
				return false;
			}
			break;
		} while (true);

		return true;
	}

	// Creació del titular capturant la ParseException
	// i imprimint el titular creat per a oferir feedback
	private static Holder createHolderWithFeedBack(Scanner in) {
		return createHolderWithFeedBack(in, null);
	}

	// Sobrecàrrega
	private static Holder createHolderWithFeedBack(Scanner in, String vehicleType) {
		Holder holder = null;
		// Creació usuari titular (Holder)
		try {
			holder = createHolder(in, vehicleType);
			println("\n---- Dades del titular creat: ----");
			println(holder.toString());
		} catch (Exception e) {
			println(e.getMessage());
		}
		return holder;
	}

	// Creació del titular
	private static Holder createHolder(Scanner in, String vehicleType) throws ParseException {
		String name, surname, dobStr;

		print("\n--- Creació d'un Usuari (HOLDER | DRIVER) ----");
		print("\nIntrodueixi el nom del titular: ");
		name = in.nextLine();
		print("\nIntrodueixi els cognoms del titular: ");
		surname = in.nextLine();
		print("\nIntrodueixi la data de neixement del titular (dd/mm/aaaa): ");
		dobStr = in.nextLine();

		Date dob = new SimpleDateFormat("dd/MM/yyyy").parse(dobStr);

		License license = createLicense(in, vehicleType);
		license.setFullName(name + " " + surname);
		return new Holder(name, surname, dob, license);
	}

	// Creació de la llicència
	private static License createLicense(Scanner in, String vehicleType) throws ParseException {
		String ltypeStr;
		String licenseId, licenseExpirationDateStr;
		LicenseType ltype;
		boolean valid = true;
		do {
			ltype = null;
			printLicenseTypesMenu();
			ltypeStr = in.nextLine();

			switch (ltypeStr) {
			case "1": // car
				ltype = LicenseType.CAR;
				if (vehicleType == null || vehicleType.equals("car"))
					valid = true;
				else
					valid = false;
				break;
			case "2": // bike
				ltype = LicenseType.BIKE;
				if (vehicleType == null || vehicleType.equals("bike"))
					valid = true;
				else
					valid = false;
				break;
			case "3": // truck
				ltype = LicenseType.TRUCK;
				if (vehicleType == null || vehicleType.equals("truck"))
					valid = true;
				else
					valid = false;
				break;
			default:
				println("\nError: Invalid license type: " + ltypeStr);
				valid = false;
			}

			if (!valid && vehicleType != null && ltype != null) {
				println("Error: Estem creant un conductor. La llicència ha de ser compatible amb el tipus de vehicle.");
				println("VEHICLE TYPE: " + vehicleType.toUpperCase());
				println("LICENSE TYPE ENTERED (Error): " + ltype.name());
			}

		} while (!valid);

		println("LICENSE TYPE ENTERED: " + ltype.name());

		print("\nIntrodueixi l'ID de la llicència: ");
		licenseId = in.nextLine();
		print("\nIntrodueixi la data de caducitat de la llicència (dd/mm/aaaa): ");
		licenseExpirationDateStr = in.nextLine();

		Date licenseExpirationDate = new SimpleDateFormat("dd/MM/yyyy").parse(licenseExpirationDateStr);
		License license = new License(licenseId, licenseExpirationDate, ltype);
		return license;
	}

	// Selecció del tipus de vehicle a crear
	private static String selectVehicleType(Scanner in, Holder holder) {
		String vehicleType = null;
		boolean continueAsking = true;
		while (continueAsking) {
			printMainVehiclesMenu();
			String resposta = in.nextLine();
			if (resposta.equals("q")) {
				println("Adéu!!");
				break;
			}
			if (!resposta.equals("1") && !resposta.equals("2") && !resposta.equals("3")) {
				println("Error: has de seleccionar un vehicle de la llista.");
				continue;
			}

			if (resposta.equals("1") && holder.getLicense().getType().equals(LicenseType.CAR))
				vehicleType = "car";
			else if (resposta.equals("2") && holder.getLicense().getType().equals(LicenseType.BIKE))
				vehicleType = "bike";
			else if (resposta.equals("3") && holder.getLicense().getType().equals(LicenseType.TRUCK))
				vehicleType = "truck";
			else {
				try {
					println("YOUR LICENSE TYPE: " + holder.getLicense().getType().name());
					throw new Exception("Error: No tens llicència compatible amb el vehicle que vols crear.");
				} catch (Exception e) {
					println(e.getMessage());
				}
				continue;
			}
			continueAsking = false;
		}
		return vehicleType;
	}

	/**
	 * Mètode per a crear vehicles a partir de les dades que se li pregunten a
	 * l'usuari via TUI (Terminal User Interace).
	 * 
	 * @param in   Scanner per a la TUI
	 * @param type Tipus de vehicle: "car" o "bike"
	 * @return retorna una instància de Car o Bike
	 */
	private static Vehicle createVehicle(Scanner in, String type, Holder holder) {

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
		case "truck":
			v = new Truck(plate, brand, color);
			break;
		default:
			throw new IllegalArgumentException("Vehicle invalid: " + type);
		}

		v.setHolder(holder);
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
		List<Wheel> wheels = new ArrayList<>();
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
		// Bikes tenen 1 roda al davant i 1 al darrere
		// Cars tenen 2 rodes al davant i 2 al darrere (cal afegir una instància de roda
		// més).
		// Trucks tenen 4 rodes al davant i 4 al darrere (cal afegir 3 instàncies de
		// roda més).
		wheels.add(new Wheel(wheelBrand, diameter));
		if (v instanceof Car)
			wheels.add(new Wheel(wheelBrand, diameter));
		else if (v instanceof Truck) {
			for (int i = 0; i < 3; i++)
				wheels.add(new Wheel(wheelBrand, diameter));
		}

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
	private static void printMainVehiclesMenu() {
		println("\n\nIndica quin tipus de vehicle vols crear: ");
		println("1 - Cotxe");
		println("2 - Moto");
		println("3 - Camió");
		println("-----------");
		print("Prem el número del vehicle seleccionat o 'q' per a sortir: ");
	}

	private static void printLicenseTypesMenu() {
		println("\n\nIndica el tipus de llicència: ");
		println("1 - Cotxe");
		println("2 - Moto");
		println("3 - Camió");
		println("-----------");
		print("Prem el número del tipus de llicència: ");
	}

	private static void println(String str) {
		System.out.println(str);
	}

	private static void print(String str) {
		System.out.print(str);
	}
}
