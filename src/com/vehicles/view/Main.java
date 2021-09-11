package com.vehicles.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import com.vehicles.domain.Bike;
import com.vehicles.domain.Car;
import com.vehicles.domain.Driver;
import com.vehicles.domain.Holder;
import com.vehicles.domain.License;
import com.vehicles.domain.License.LicenseType;
import com.vehicles.domain.Person;
import com.vehicles.domain.Truck;
import com.vehicles.domain.Vehicle;
import com.vehicles.domain.Wheel;

public class Main {

	private static List<Person> users = new ArrayList<>();
	private static List<Vehicle> vehicles = new ArrayList<>();
	private static Messages messages = Messages.getInstance();

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		in.useLocale(Locale.ENGLISH);

		Boolean goToMainMenu = false;
		Boolean quit = false;
		Vehicle v = null;

		// Creació dels vehicles
		while (true) {

			int menuSelection = showMainMenu(in);

			boolean added = false;

			if (menuSelection == -1) {
				println(messages.goodBye);
				break;
			} else if (menuSelection == 1) { // CREAR USUARI
				menuSelection = showUsersMenu(in);
				if (menuSelection == -1) {
					println(messages.goodBye);
					break;
				} else if (menuSelection == 0) {
					continue;
				} else { // Si s'ha seleccionat crear un titular o un conductor, cal que existeixi algun
							// vehicle
					int size = getVehicles().size();
					if (size == 0) { // Si no existeix cap vehicle, cal crear-ne almenys un.
						println(messages.emptyVehiclesListError);
						menuSelection = showVehiclesMenu(in);
						if (menuSelection == -1) {
							println(messages.goodBye);
							break;
						}
						if (menuSelection == 0)
							continue;
						goToMainMenu = false;
						v = setNewVehicleInstance(in, menuSelection, v, goToMainMenu, quit);
						if (quit) {
							println(messages.goodBye);
							break;
						}
						if (goToMainMenu)
							continue;

						// Afegeix les rodes al vehicle
						addWheels(in, v);

						// Guarda el vehicle
						getVehicles().add(v);
						println(messages.vehicleAddedInfo);// feedback
						continue;
					} else {
						// Abans de crear un usuari, cal seleccionar un vehicle
						int vehicleSelection = showSelectVehiclesMenu(in, getVehicles());
						if (vehicleSelection == -1) {
							println(messages.goodBye);
							break;
						}
						if (vehicleSelection == -2) {
							continue;
						}
						v = getVehicles().get(vehicleSelection - 1);
						println("Selected vehicle is: " + v.getPlate());

						if (menuSelection == 1) { // S'ha seleccionat crear un titular
							Holder holder;
							boolean wannaBeDriver = false;

							// Comprova si el vehicle seleccionat ja té titular o no
							boolean holderExists = checkHolder(v);

							if (holderExists) { // El vehicle ja té titular

								// Pregunta si es crea un nou titular que sobreescriurà l'existent
								menuSelection = showOverwriteHolderMenu(in, v);

								if (menuSelection == 1) {
									holder = buildHolder(in, v); // Creació d'un titular (cal llicència compatible)
									setHolderToVehicle(holder, v); // (Sobreescriu) Assignació del titular al vehicle
																	// escollit
									added = addUserIfNotExists(holder, getUsers());// Intenta afegir el titular al
																					// repositori
									if (added)
										println(messages.userAddedInfo);// feedback

									wannaBeDriver = showWannaBeDriverMenu(in, v);
									if (wannaBeDriver) {
										added = addDriverIfNotExists(holder, v.getDrivers());// Intenta afegir el
																								// titular com a
																								// conductor del vehicle
										if (added)
											println(String.format(messages.holderAddedAsDriverInfo, holder.getName(),
													holder.getSurname(), v.getPlate())); // feedback

									} else if (menuSelection == 2) { // No creem cap titular i mantenim el que ja té el
																		// vehicle
										println(String.format(messages.holderNotUpdatedInfo, v.getPlate()));// feedback
									}
								}
							} else { // Si el vehicle no té titular, en creem un i l'assignem
								println(String.format(messages.holderNotFoundInfo, v.getPlate()));// feedback
								holder = buildHolder(in, v); // Creació d'un titular
								setHolderToVehicle(holder, v); // Assignació del titular al vehicle escollit

								added = addUserIfNotExists(holder, getUsers());// Intenta afegir el titular al
																				// repositori
								if (added)
									println(messages.userAddedInfo);// feedback

								wannaBeDriver = showWannaBeDriverMenu(in, v);
								if (wannaBeDriver) {
									added = addDriverIfNotExists(holder, v.getDrivers());// Intenta afegir el
																							// titular com a
																							// conductor del vehicle
									if (added)
										println(String.format(messages.holderAddedAsDriverInfo, holder.getName(),
												holder.getSurname(), v.getPlate())); // feedback
								}
							}
						} else if (menuSelection == 2) { // S'ha seleccionat crear un conductor
							Driver driver = buildDriver(in, v); // Crea conductor (cal llicència compatible)

							added = addDriverIfNotExists(driver, v.getDrivers());// Intenta afegir el conductor al
																					// vehicle
							if (added)
								println(String.format(messages.driverAddedInfo, v.getPlate()));// feedback

							added = addUserIfNotExists(driver, getUsers());// Intenta afegir el conductor al
																			// repositori
							if (added)
								println(messages.userAddedInfo);// feedback
						}
					}

				}
			} else if (menuSelection == 2) { // CREAR VEHICLE
				menuSelection = showVehiclesMenu(in);
				if (menuSelection == -1) {
					println(messages.goodBye);
					break;
				}
				if (menuSelection == 0)
					continue;
				goToMainMenu = false;
				v = setNewVehicleInstance(in, menuSelection, v, goToMainMenu, quit);
				if (quit) {
					println(messages.goodBye);
					break;
				}
				if (goToMainMenu)
					continue;

				// Afegeix les rodes al vehicle
				addWheels(in, v);

				println(messages.createdVehicleInfoHeader);
				print(v.toString());

				// Guarda el vehicle
				added = addVehicleIfNotExists(v, getVehicles());// Intenta afegir el vehicle al
																// repositori
				if (added)
					println(messages.vehicleAddedInfo);// feedback

			} else if (menuSelection == 3) { // CONSULTAR USUARIS
				if (getUsers().size() == 0) {
					println(messages.noUsersInRepoError);
				} else {
					println(messages.usersFoundInRepoHeader);
					for (Person p : getUsers())
						println(((Driver) p).toString());
				}
			} else if (menuSelection == 4) { // CONSULTAR VEHICLES
				if (getVehicles().size() == 0) {
					println(messages.noVehiclesInRepoError);
				} else {
					println(messages.vehiclesFoundInRepoHeader);
					for (Vehicle ve : getVehicles()) {
						if (ve.getHolder() != null)
							println(ve.getPlate() + "\t\t" + ve.getClass().getSimpleName() + "\t- "
									+ ve.getHolder().getName() + " " + ve.getHolder().getSurname());
						else
							println(ve.getPlate() + "\t\t" + ve.getClass().getSimpleName() + "\t- No Holder set");
					}
				}
			}
		}
		in.close();
	}

	// ################## MENUS START #########################

	// MAIN MENU
	private static int showMainMenu(Scanner in) {

		// Imprimim menu principal
		print(messages.mainMenu);

		// Capturem la resposta
		String resposta = in.nextLine();

		// Gestionem la lògica de la resposta
		if (resposta.equals("q") || resposta.equals("5")) {
			return -1;
		}
		if (!resposta.equals("1") && !resposta.equals("2") && !resposta.equals("3") && !resposta.equals("4")) {
			println(messages.selectFromListError);
			return showMainMenu(in);
		}
		int selection = -1;
		try {
			selection = Integer.parseInt(resposta);
		} catch (NumberFormatException e) {
			println(e.getMessage());
			return showMainMenu(in);
		}
		return selection;
	}

	// USERS MENU
	private static int showUsersMenu(Scanner in) {

		// Imprimim el menu
		println("");
		print(messages.usersMenu);

		// Capturem la resposta
		String resposta = in.nextLine();

		// Gestionem la lògica de la resposta
		if (resposta.equals("q") || resposta.equals("4")) {
			return -1;
		}
		if (resposta.equals("h") || resposta.equals("3")) {
			return 0;
		}
		if (!resposta.equals("1") && !resposta.equals("2")) {
			println(messages.selectFromListError);
			return showUsersMenu(in);
		}
		int selection = -1;
		try {
			selection = Integer.parseInt(resposta);
		} catch (NumberFormatException e) {
			println(e.getMessage());
			return showUsersMenu(in);
		}
		return selection;
	}

	// VEHICLES MENU
	private static int showVehiclesMenu(Scanner in) {

		// Imprimim el menu
		println("");
		print(messages.vehiclesMenu);

		// Capturem la resposta
		String resposta = in.nextLine();

		// Gestionem la lògica de la resposta
		if (resposta.equals("q") || resposta.equals("5")) {
			return -1;
		}
		if (resposta.equals("h") || resposta.equals("4")) {
			return 0;
		}
		if (!resposta.equals("1") && !resposta.equals("2") && !resposta.equals("3")) {
			println(messages.selectFromListError);
			return showVehiclesMenu(in);
		}
		int selection = -1;
		try {
			selection = Integer.parseInt(resposta);
		} catch (NumberFormatException e) {
			println(e.getMessage());
			return showVehiclesMenu(in);
		}
		return selection;
	}

	// SELECT VEHICLE MENU
	private static int showSelectVehiclesMenu(Scanner in, List<Vehicle> vehicles) {

		// Imprimim el menu
		println("");
		print(String.format(messages.selectVehicleMenu, messages.buildListOfVehiclesByPlate(Main.vehicles),
				Main.vehicles.size() + 1, Main.vehicles.size() + 2));

		// Capturem la resposta
		String resposta = in.nextLine();

		// Gestionem la lògica de la resposta
		if (resposta.equals("q") || resposta.equals("" + (vehicles.size() + 2))) {
			return -1;
		}
		if (resposta.equals("h") || resposta.equals("" + (vehicles.size() + 1))) {
			return -2;
		}
		int selection = -1;
		try {
			selection = Integer.parseInt(resposta);
			if (selection < 1 || selection > vehicles.size()) {
				println(messages.selectFromListError);
				return showSelectVehiclesMenu(in, vehicles);
			}
		} catch (NumberFormatException e) {
			println(e.getMessage());
			return showSelectVehiclesMenu(in, vehicles);
		}
		return selection;
	}

	// OVERWRITE HOLDER MENU
	private static int showOverwriteHolderMenu(Scanner in, Vehicle v) {

		// Imprimim menu principal
		print(String.format(messages.overwriteHolderMenu, v.getHolder().getName(), v.getHolder().getSurname(),
				v.getPlate()));

		// Capturem la resposta
		String resposta = in.nextLine();

		// Gestionem la lògica de la resposta
		if (resposta.equals("q") || resposta.equals("4")) {
			return -1;
		}
		if (resposta.equals("h") || resposta.equals("3")) {
			return 0;
		}
		if (resposta.toLowerCase().equals("s"))
			return 1;
		if (resposta.toLowerCase().equals("n"))
			return 2;
		if (!resposta.equals("1") && !resposta.equals("2")) {
			println(messages.selectFromListError);
			return showOverwriteHolderMenu(in, v);
		}
		int selection = -1;
		try {
			selection = Integer.parseInt(resposta);
		} catch (NumberFormatException e) {
			println(e.getMessage());
			return showOverwriteHolderMenu(in, v);
		}
		return selection;
	}

	// WANNA BE DRIVER MENU
	private static boolean showWannaBeDriverMenu(Scanner in, Vehicle v) {
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

	// ################## MENUS END #########################

	// ################## BUILDERS START #########################

	// BUILD HOLDER
	private static Holder buildHolder(Scanner in, Vehicle vehicle) {
		String name, surname, dobStr;

		println(messages.buildHolderHeader);
		print(messages.buildHolderQuestion1);// nom del titular
		name = in.nextLine();
		print(messages.buildHolderQuestion2);// cognoms del titular
		surname = in.nextLine();

		Date dob = null;
		do {
			print(messages.buildHolderQuestion3); // data neixement del titular
			dobStr = in.nextLine();
			try {
				dob = new SimpleDateFormat("dd/MM/yyyy").parse(dobStr);
				break;
			} catch (ParseException e) {
				println(e.getMessage());
			}

		} while (true);

		License license = buildLicense(in, vehicle.getClass().getSimpleName());
		license.setFullName(name + " " + surname);
		return new Holder(name, surname, dob, license);
	}

	// BUILD DRIVER
	private static Driver buildDriver(Scanner in, Vehicle vehicle) {
		String name, surname, dobStr;

		println(messages.buildDriverHeader);
		print(messages.buildDriverQuestion1);// nom del titular
		name = in.nextLine();
		print(messages.buildDriverQuestion2);// cognoms del titular
		surname = in.nextLine();

		Date dob = null;
		do {
			print(messages.buildDriverQuestion3); // data neixement del titular
			dobStr = in.nextLine();
			try {
				dob = new SimpleDateFormat("dd/MM/yyyy").parse(dobStr);
				break;
			} catch (ParseException e) {
				println(e.getMessage());
			}

		} while (true);

		License license = buildLicense(in, vehicle.getClass().getSimpleName());
		license.setFullName(name + " " + surname);
		return new Driver(name, surname, dob, license);
	}

	// BUILD LICENSE
	// Només es pot crear la llicència si és compatible amb el tipus de vehicle !!!
	private static License buildLicense(Scanner in, String vehicleType) {
		String ltypeStr;
		String licenseId, licenseExpirationDateStr;
		LicenseType ltype;
		boolean valid = true;
		do {
			ltype = null;
			print(messages.licenseTypesMenu);
			ltypeStr = in.nextLine();

			switch (ltypeStr) {
			case "1": // car
				ltype = LicenseType.CAR;
				if (vehicleType == null || vehicleType.equals("Car"))
					valid = true;
				else
					valid = false;
				break;
			case "2": // bike
				ltype = LicenseType.BIKE;
				if (vehicleType == null || vehicleType.equals("Bike"))
					valid = true;
				else
					valid = false;
				break;
			case "3": // truck
				ltype = LicenseType.TRUCK;
				if (vehicleType == null || vehicleType.equals("Truck"))
					valid = true;
				else
					valid = false;
				break;
			default:
				println("");
				println(messages.invalidLicenseTypeError + ltypeStr);
				println(messages.selectFromListError);
				valid = false;
			}

			if (!valid && vehicleType != null && ltype != null) {
				println(messages.licenseCompatibilityError);
				print(String.format(messages.currentVehicleType, vehicleType.toUpperCase()));
				println(String.format(messages.selectedLicenseTypeError, ltype.name()));
			}

		} while (!valid);

		println(String.format(messages.selectedLicenseType, ltype.name()));

		print(messages.buildLicenseQuestion1);
		licenseId = in.nextLine();
		Date licenseExpirationDate = null;
		do {
			print(messages.buildLicenseQuestion2);
			licenseExpirationDateStr = in.nextLine();
			try {
				licenseExpirationDate = new SimpleDateFormat("dd/MM/yyyy").parse(licenseExpirationDateStr);
				break;
			} catch (ParseException e) {
				println(e.getMessage());
			}

		} while (true);

		License license = new License(licenseId, licenseExpirationDate, ltype);
		return license;
	}

	// BUILD VEHICLE
	private static Vehicle buildVehicle(Scanner in, Vehicle vehicle) {

		String plate, brand, color;

		String type = vehicle.getClass().getSimpleName();

		println(String.format(messages.buildVehicleHeader, type.toUpperCase()));

		// Demanem dades del vehicle
		boolean valid = true;
		do {
			print(messages.buildVehicleQuestion1);
			plate = in.nextLine();
			valid = checkPlate(plate);
			if (!valid)
				print(messages.plateError);
		} while (!valid);
		print(messages.buildVehicleQuestion2);
		brand = in.nextLine();
		print(messages.buildVehicleQuestion3);
		color = in.nextLine();

		// Creem el vehicle
		Vehicle v;
		switch (type) {
		case "Car":
			v = new Car(plate, brand, color);
			break;
		case "Bike":
			v = new Bike(plate, brand, color);
			break;
		case "Truck":
			v = new Truck(plate, brand, color);
			break;
		default:
			throw new IllegalArgumentException(String.format(messages.invalidVehicleError + type));
		}
		return v;
	}

	// BUILD WHEELS LIST
	private static List<Wheel> buildWheels(Vehicle v, String wheelsLocation, Scanner in) {
		List<Wheel> wheels = new ArrayList<>();
		String wheelBrand;
		double diameter = 2.0;

		// Demanem dades de les rodes
		print(String.format(messages.wheelsAddingMsg, wheelsLocation));
		print(String.format(messages.buildWheelQuestion1, wheelsLocation));
		wheelBrand = in.nextLine();

		boolean valid = true;
		do {
			print(String.format(messages.buildWheelQuestion2, wheelsLocation));
			try {
				diameter = Double.parseDouble(in.nextLine());
				valid = checkDiameter(diameter);
				if (!valid)
					print(messages.diameterSizeError);
			} catch (NumberFormatException e) {
				print(messages.diameterFormatError);
				valid = false;
			}
		} while (!valid);

		/*
		 * Introduim les dades de les rodes al List de rodes Bikes tenen 1 roda al
		 * davant i 1 al darrere Cars tenen 2 rodes al davant i 2 al darrere (cal afegir
		 * una instància de roda + més). Trucks tenen 4 rodes al davant i 4 al darrere
		 * (cal afegir 3 instàncies de roda més).
		 */
		wheels.add(new Wheel(wheelBrand, diameter));
		if (v instanceof Car)
			wheels.add(new Wheel(wheelBrand, diameter));
		else if (v instanceof Truck) {
			for (int i = 0; i < 3; i++)
				wheels.add(new Wheel(wheelBrand, diameter));
		}

		return wheels;
	}

	// ################## BUILDERS END #########################

	// ################## HELPERS START #########################

	// ADDS WHEELS TO VEHICLE
	private static void addWheels(Scanner in, Vehicle v) {
		List<Wheel> rearWheels;
		List<Wheel> frontWheels;

		// Preguntem a l'usuari dades de les rodes
		// davanteres i posteriors:

		rearWheels = buildWheels(v, messages.rear, in);
		frontWheels = buildWheels(v, messages.front, in);

		// Afegim les rodes al vehicle
		try {
			v.addWheels(frontWheels, rearWheels);
			println(messages.wheelsAddedInfo);// feedback
		} catch (Exception e) {
			println(e.getMessage());
		}
	}

	// CREATES A NEW CAR, BIKE OR TRUCK, OR GO TO HOME MENU, OR EXIT APP
	private static Vehicle setNewVehicleInstance(Scanner in, int menuSelection, Vehicle v, Boolean goToMainMenu,
			Boolean quit) {
		switch (menuSelection) {
		case 1: // Car (cotxe)
			v = buildVehicle(in, new Car());
			break;
		case 2: // Bike (moto)
			v = buildVehicle(in, new Bike());
			break;
		case 3: // Truck (camió)
			v = buildVehicle(in, new Truck());
			break;
		case 0: // Menú inici
			goToMainMenu = true;
			break;
		case -1: // Sortir del programa
			quit = true;
			break;
		default:
			;
		}
		return v;
	}

	// SET HOLDER (TITULAR) TO VEHICLE
	private static void setHolderToVehicle(Holder holder, Vehicle v) {
		printPerson(holder); // feedback
		v.setHolder(holder); // Assignem el titular al vehicle
		println(String.format(messages.holderUpdatedInfo, v.getPlate()));// feedback
	}

	// ADDS DRIVER TO LIST IF NOT EXISTS
	private static boolean addDriverIfNotExists(Driver driver, List<Driver> drivers) {
		if (!drivers.contains(driver)) {
			drivers.add(driver); // Podem afegir tants conductors com vulguem, però no repetits
			return true;
		} else {
			println(messages.driverAlreadyExistsError);
			return false;
		}
	}

	// ADDS USER TO LIST IF NOT EXISTS
	private static boolean addUserIfNotExists(Person person, List<Person> persons) {
		if (!persons.contains(person)) {
			persons.add(person); // Podem afegir tants conductors com vulguem, però no repetits
			return true;
		} else {
			println(messages.userAlreadyExistsError);
			return false;
		}
	}

	// ADDS VEHICLE TO LIST IF NOT EXISTS
	private static boolean addVehicleIfNotExists(Vehicle vehicle, List<Vehicle> vehicles) {
		if (!vehicles.contains(vehicle)) {
			vehicles.add(vehicle);
			return true;
		} else {
			println(messages.vehicleAlreadyExistsError);
			return false;
		}
	}

	private static boolean checkHolder(Vehicle v) {
		if (v.getHolder() == null)
			return false;
		return true;
	}

	private static void printPerson(Person p) {
		println(messages.createdUserInfoHeader);
		println(p.toString());
	}

	private static void println(String str) {
		System.out.println(str);
	}

	private static void print(String str) {
		System.out.print(str);
	}

	private static void printf(String format, Object... args) {
		System.out.printf(format, args);
	}

	// ################## HELPERS END #########################

	// ################## NEGOCI START #########################

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

	// ################## NEGOCI END #########################

	// ################# ACCESORS && MUTATORS ###################

	public static List<Vehicle> getVehicles() {
		return vehicles;
	}

	public static List<Person> getUsers() {
		return users;
	}

	// ################ ALL MESSAGES HERE ######################
	public static class Messages {

		// Menus
		public final String mainMenu = (new StringBuilder()).append("\n\n------------ MENU INICI ------------")
				.append("\n---- Indica què vols fer: ----").append("\n--------------------------------")
				.append("\n1 - Crear USUARIS (titulars, conductors)")
				.append("\n2 - Crear VEHICLES (cotxes, motos, camions)").append("\n3 - Consultar llista d'USUARIS")
				.append("\n4 - Consultar llista de VEHICLES").append("\n5 - Sortir del programa (prem 'q')")
				.append("\n--------------------------------").append("\nPrem el número de la teva selecció: ")
				.toString();

		public final String usersMenu = (new StringBuilder()).append("\n\n------------ MENU USUARIS ------------")
				.append("\n---- Indica el tipus d'usuari que vols crear: ----")
				.append("\n--------------------------------")
				.append("\n1 - TITULAR (el podràs afegir com a conductor si vols)")
				.append("\n2 - CONDUCTOR (la llicència ha de ser compatible)")
				.append("\n3 - Tornar al menú d'inici (prem 'h')").append("\n4 - Sortir del programa (prem 'q')")
				.append("\n--------------------------------").append("\nPrem el número de la teva selecció: ")
				.toString();

		public final String vehiclesMenu = (new StringBuilder()).append("\n\n------------ MENU VEHICLES ------------")
				.append("\n---- Indica quin tipus de vehicle vols crear: ----").append("\n1 - COTXE")
				.append("\n2 - MOTO").append("\n3 - CAMIÓ").append("\n4 - Tornar al menú d'inici (prem 'h')")
				.append("\n5 - Sortir del programa (prem 'q')").append("\n--------------------------------")
				.append("\nPrem el número de la teva selecció: ").toString();

		public final String licenseTypesMenu = (new StringBuilder())
				.append("\n\nIndica el tipus de llicència: ")
				.append("\n1 - Cotxe (CAR)")
				.append("\n2 - Moto (BIKE)")
				.append("\n3 - Camió (TRUCK)").append("\n-----------")
				.append("\nPrem el número del tipus de llicència: ")
				.toString();

		public final String selectVehicleMenu = (new StringBuilder())
				.append("\n\n------------ LLISTA DE VEHICLES EXISTENTS ------------")
				.append("\nNota: Per a crear un usuari, has de seleccionar primer un vehicle per a aquest nou usuari.")
				.append("\n---- Selecciona el vehicle: ----").append("%s")
				.append("\n%d - Tornar al menú d'inici (prem 'h')").append("\n%d - Sortir del programa (prem 'q')")
				.append("\n--------------------------------").append("\nPrem el número de la teva selecció: ")
				.toString();

		public final String newVehicleMenu = (new StringBuilder())
				.append("Vols crear un nou vehicle per al titular %s %s ?").append("\n- Sí (prem s)")
				.append("\n- No (prem n)").toString();

		public final String overwriteHolderMenu = (new StringBuilder()).append(
				"Vols sobreescriure el titular %s %s \n del vehicle (matrícula %s) seleccionat \n (creant un nou titular)?")
				.append("\n- Sí (prem s)").append("\n- No (prem n)").toString();

		// Headers
		public final String buildHolderHeader = "\n---- Creació d'un TITULAR ----";
		public final String buildDriverHeader = "\n---- Creació d'un CONDUCTOR ----";
		public final String buildVehicleHeader = "\n---- Creació d'un(a) %s ----";
		public final String createdVehicleInfoHeader = "\n---- Dades del vehicle creat: ----";
		public final String createdUserInfoHeader = "\n---- Dades de l'usuari creat: ----";
		public final String usersFoundInRepoHeader = "\n---- Usuaris trobats al repositori: ----";
		public final String vehiclesFoundInRepoHeader = "\n---- Vehicles trobats al repositori: ----"
				+ "\nMatrícula\t- Tipus\t- Titular" + "\n---------\t- -----\t- -------\n";

		// Questions
		public final String buildHolderQuestion1 = "\nIntrodueixi el nom del titular: ";
		public final String buildHolderQuestion2 = "\nIntrodueixi els cognoms del titular: ";
		public final String buildHolderQuestion3 = "\nIntrodueixi la data de neixement del titular (dd/mm/aaaa): ";

		public final String buildDriverQuestion1 = "\nIntrodueixi el nom del conductor: ";
		public final String buildDriverQuestion2 = "\nIntrodueixi els cognoms del conductor: ";
		public final String buildDriverQuestion3 = "\nIntrodueixi la data de neixement del conductor (dd/mm/aaaa): ";

		public final String buildLicenseQuestion1 = "\nIntrodueixi l'ID de la llicència: ";
		public final String buildLicenseQuestion2 = "\nIntrodueixi la data de caducitat de la llicència (dd/mm/aaaa): ";

		public final String buildVehicleQuestion1 = "\nIntrodueixi la matrícula del vehicle: ";
		public final String buildVehicleQuestion2 = "\nIntrodueixi la marca del vehicle: ";
		public final String buildVehicleQuestion3 = "\nIntrodueixi el color del vehicle: ";

		public final String buildWheelQuestion1 = "\nIntrodueixi la marca de la roda/es del %s: ";
		public final String buildWheelQuestion2 = "\nIntrodueixi el diàmetre de la roda/es del %s: ";

		// Altres i feedbacks
		public final String goodBye = "Adéu!\n#### Final del programa ####";
		public final String holderUpdatedInfo = "Info: Titular actualitzat en el vehicle amb matrícula %s.";
		public final String holderNotUpdatedInfo = "Info: No hi ha hagut cap canvi en el titular del vehicle amb matrícula %s.";
		public final String holderNotFoundInfo = "Info: El vehicle seleccionat (matrícula %s) no té cap titular assignat.";
		public final String driverAddedInfo = "Info: Conductor afegit en el vehicle amb matrícula %s.";
		public final String holderAddedAsDriverInfo = "Info: El titular %s %s s'ha afegit com a conductor del vehicle amb matrícula %s.";
		public final String vehicleAddedInfo = "Info: el vehicle s'ha afegit al repositori.";
		public final String userAddedInfo = "Info: l'usuari s'ha afegit al repositori.";
		public final String currentVehicleType = "CURRENT VEHICLE TYPE: %s";
		public final String selectedLicenseType = "LICENSE TYPE SELECTED: %s";
		public final String wheelsAddingMsg = "\n>>> Afegim les rodes del %s ...";
		public final String wheelsAddedInfo = "Info: Rodes afegides correctament al vehicle.";
		public final String rear = "darrere";
		public final String front = "davant";

		// Errors
		public final String selectFromListError = "\nERROR: Has de seleccionar una opció de la llista.";
		public final String invalidLicenseTypeError = "\nERROR: Tipus de llicència no vàlid: ";
		public final String invalidVehicleError = "\nERROR: Vehicle invalid: %s";
		public final String emptyVehiclesListError = "\nERROR: No hi ha vehicles.\nPer a donar d'alta un usuari has de seleccionar un vehicle primer.\n*** Cal crear un nou vehicle. ***";
		public final String driverAlreadyExistsError = "\nERROR adding driver: driver already exists.";
		public final String userAlreadyExistsError = "\nERROR adding user in repository: user already exists.";
		public final String vehicleAlreadyExistsError = "\nERROR adding vehicle in repository: vehicle already exists.";
		public final String licenseCompatibilityError = "\nERROR: Estem creant un usuari. La llicència ha de ser compatible amb el tipus de vehicle.";
		public final String selectedLicenseTypeError = "\nERROR: " + this.selectedLicenseType;
		public final String plateError = "\nERROR: matrícula incorrecte.\nUna matrícula correcte té 4 dígits seguits de 2 o 3 lletres.\n";
		public final String diameterSizeError = "\nERROR: diàmetre de roda no correcte.\nEl diàmetre ha d'estar entre 0.4 i 4.\n";
		public final String diameterFormatError = "\nERROR: el diàmetre a introduir ha de ser numèric.\n";
		public final String noUsersInRepoError = "\nERROR: no hi ha cap usuari al repositori.";
		public final String noVehiclesInRepoError = "\nERROR: no hi ha cap vehicle al repositori.";

		// SINGLETON
		private static Messages messages;

		private Messages() {

		}

		public static Messages getInstance() {
			if (messages == null)
				return new Messages();
			else
				return messages;
		}

		// PER A CREAR EL MENÚ AMB TOTS ELS VEHICLES
		private String buildListOfVehiclesByPlate(List<Vehicle> vehicles) {
			StringBuilder sb = new StringBuilder();
			int counter = 1;
			for (Vehicle v : vehicles) {
				sb.append("\n" + counter + " - ");
				sb.append(v.getPlate());
				sb.append(" - " + v.getClass().getSimpleName());
				counter++;
			}
			return sb.toString();
		}

	} // Final class Messages

} // Final class Main
