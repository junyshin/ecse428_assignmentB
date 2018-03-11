package ca.mcgill.ecse428.postal;

import java.util.Scanner;

import ca.mcgill.ecse428.postal.postalRate;

public class rateApplication {
	
	public static Scanner scanner = new Scanner(System.in);
	
	public static postalRate pr;
	
	/**
	 * @param caseNumber
	 */
	public static void printCases(int caseNumber) {

		String lenght = "";
		String width = "";
		String height = "";
		String weight = "";
		String postalCodeSend = "";
		String postalCodeReceive = "";
		String postalService = "";
		boolean condition = true;

		switch (caseNumber) {

		case 1:
			System.out.print("\nPlease Enter sender's postal code without space: ");
			postalCodeSend = scanner.nextLine();
			condition = pr.setPostalFrom(postalCodeSend.toLowerCase());
			if (!condition) {
				System.out.println("You have entered an invalid Canadian postal code.");
				printCases(1);
			}

		case 2:
			System.out.print("\nPlease Enter receiver's postal code without space: ");
			postalCodeReceive = scanner.nextLine();
			condition = pr.setPostalToo(postalCodeReceive.toLowerCase());
			if (!condition) {
				System.out.println("You have entered an invalid Canadian postal code.");
				printCases(2);
			}

		case 3:
			System.out.print("\nPlease Enter the lenght of the box(cm): ");
			lenght = scanner.nextLine();
			condition = pr.setNumLength(lenght);
			if (!condition) {
				System.out.println("You have entered an invalid lenght. The lenth must be greater then 0.1 cm and lower then 200.0 cm.");
				printCases(3);
			}

		case 4:
			System.out.print("\nPlease Enter the width of the box(cm): ");
			width = scanner.nextLine();
			condition = pr.setNumWidth(width);
			if (!condition) {
				System.out.println("You have entered an invalid lenght. The lenth must be greater then 0.1 cm and lower then 200.0 cm.");
				printCases(4);
			}

		case 5:
			System.out.print("\nPlease Enter the height of the box(cm): ");
			height = scanner.nextLine();
			condition = pr.setNumHeight(height);
			if (!condition) {
				System.out.println("You have entered an invalid lenght. The lenth must be greater then 0.1 cm and lower then 200.0 cm.");
				printCases(5);
			}
		
		case 6:
			condition = pr.girthLength();
			if (!condition) {
				System.out.println("The girth+lenth is greater then 300cm. Please reenter the valid length, width, and height.");
				printCases(4);
			}

		case 7:
			System.out.print("\nPlease Enter the weight of the box(kg): ");
			weight = scanner.nextLine();
			condition = pr.setNumWeight(weight);
			if (!condition) {
				System.out.println("You have entered an invalid lenght. The lenth must be greater then 0.0 kg and maximum of 30.0 kg.");
				printCases(7);
			}
		
		case 8:
			System.out.print("\nPlease Enter number associated with the method of service.");
			System.out.print("\n1: Regular ; 2: Xpresspost ; 3: Prority");
			postalService = scanner.nextLine();
			condition = pr.setType(postalService);
			if (!condition) {
				System.out.println("You have entered an invalid postal service.");
				printCases(8);
			}
		}

		System.out.println("The rate of the package is " + pr.calculateParcelRate() + "$.");
	}
	
	public static void main(String[] args) {

		pr = new postalRate();

		System.out.println(
				"This program will help you find the estimated cost of pour parsel\n according to the Canada posts specifications.");
		printCases(1);
		System.exit(0);
	}
}
