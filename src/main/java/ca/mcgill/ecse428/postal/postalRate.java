package ca.mcgill.ecse428.postal;

import java.util.Scanner;
import java.io.IOException;
import java.io.File;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class postalRate {

	public static Scanner scanner = new Scanner(System.in);
	
	private static Sheet rateCodeLookupSheet;
	private static Sheet rateSheet;
	
	private static final int regularTypeRow = 140;
	private static final int xpressTypeRow = 72;
	private static final int priorityTypeRow = 4;
	private static final int typeRange = 59;

	private double numLength = 0;
	private double numWidth = 0;
	private double numHeight = 0;
	private double numWeight = 0;
	private int caseValue = 0;
	private String senderPostalCode = "";
	private String receiverPostalCode = "";

	public postalRate(double lenght, double width, double height, double weight, String sending, String toDestonation,
			int type) {
		this.numLength = lenght;
		this.numHeight = height;
		this.numWidth = width;
		this.numWeight = weight;
		this.caseValue = type;
		this.senderPostalCode = sending;
		this.receiverPostalCode = toDestonation;
	}
	
	public postalRate() {
		
	}
	
	public double calculateParcelRate() {
		
		String source = this.senderPostalCode;
		String destination = this.receiverPostalCode;
		double length = this.numLength;
		double width = this.numWidth;
		double height = this.numHeight;
		double weight = this.numWeight;
		int postType = this.caseValue;
		
		try {
			rateCodeLookupSheet = WorkbookFactory.create(new File("CanadaPostMtlTable.xlsx")).getSheetAt(0);
			rateSheet = WorkbookFactory.create(new File("CanadaPostRates.xlsx")).getSheetAt(0);
		} catch (InvalidFormatException | IOException e) {
			e.printStackTrace();
		}

		int type = 0;
		// determine where to start looking in the sheet
		switch(postType) {
		case 1:
			type = regularTypeRow;
			break;
		case 2:
			type = xpressTypeRow;
			break;
		case 3:
			type = priorityTypeRow;
			break;
		}
		
		// find the rate code from the postal codes
		String rateCode = findRateCode(source, destination);

		// go through the corresponding rows and compare the weight
		for (int row = type; row <= type + typeRange; row++) {
			if (Double.parseDouble(rateSheet.getRow(row).getCell(0).toString()) >= weight) {
				return getParcelRate(rateCode, row, type);
			}
		}
		// if no matches, then return 0
		return 0;
	}
	
	private static String findRateCode(String source, String destination) {

		for (Row row : rateCodeLookupSheet) {
			if (row.getCell(0).toString().equals(destination.substring(0, 3))) {
				return row.getCell(1).toString();
			}
		}

		return null;
	}
	
	private static double getParcelRate(String rateCode, int row, int rowInit) {
		// find the corresponding price for the given weight and rate code
		for (Cell cell : rateSheet.getRow(rowInit - 2)) {
			if (cell.toString().equals(rateCode)) {
				return Double.parseDouble(rateSheet.getRow(row).getCell(cell.getColumnIndex()).toString());
			}
		}
		return 0;
	}

	public void printCases(int caseNumber) {

		String lenght = "";
		String width = "";
		String height = "";
		String weight = "";
		String postalCodeSend = "";
		String postalCodeReceive = "";
		String postalService = "";

		switch (caseNumber) {

		case 1:
			System.out.print("\nPlease Enter sender's postal code without space: ");
			postalCodeSend = scanner.nextLine();
			this.senderPostalCode = postalCodeSend.toLowerCase();

		case 2:
			System.out.print("\nPlease Enter receiver's postal code without space: ");
			postalCodeReceive = scanner.nextLine();
			this.receiverPostalCode = postalCodeReceive.toLowerCase();

		case 3:
			System.out.print("\nPlease Enter the lenght of the box(cm): ");
			lenght = scanner.nextLine();
			this.numLength = Double.parseDouble(lenght);

		case 4:
			System.out.print("\nPlease Enter the width of the box(cm): ");
			width = scanner.nextLine();
			this.numWidth = Double.parseDouble(width);

		case 5:
			System.out.print("\nPlease Enter the height of the box(cm): ");
			height = scanner.nextLine();
			this.numHeight = Double.parseDouble(height);

		case 6:
			System.out.print("\nPlease Enter the weight of the box(kg): ");
			weight = scanner.nextLine();
			this.numWeight = Double.parseDouble(weight);

		case 7:
			System.out.print("\nPlease Enter number associated with the method of service.");
			System.out.print("\n1: Regular ; 2: Xpresspost ; 3: Prority");
			postalService = scanner.nextLine();
			this.caseValue = Integer.parseInt(postalService);
		}

		System.out.println("The rate of the package is " + calculateParcelRate() + "$.");
	}

	public boolean setNumLength(String length) {
		if (onlyNum(length) != true)
			return false;
		double temp = Double.parseDouble(length);
		if (temp < (double) 0.1)
			return false;
		if (temp > (double) 200.0)
			return false;
		this.numLength = Double.parseDouble(length);
		return true;
	}

	public boolean setNumWidth(String width) {
		if (onlyNum(width) != true)
			return false;
		double temp = Double.parseDouble(width);
		if (temp < (double) 0.1)
			return false;
		if (temp > (double) 200.0)
			return false;
		this.numWidth = Double.parseDouble(width);
		return true;
	}

	public boolean setNumHeight(String height) {
		if (onlyNum(height) != true)
			return false;
		double temp = Double.parseDouble(height);
		if (temp < (double) 0.1)
			return false;
		if (temp > (double) 200.0)
			return false;
		this.numHeight = Double.parseDouble(height);
		return true;
	}

	public boolean setNumWeight(String weigth) {
		if (onlyNum(weigth) != true)
			return false;
		double temp = Double.parseDouble(weigth);
		if (temp < (double) 0.1)
			return false;
		if (temp > (double) 30.0)
			return false;
		this.numWeight = Double.parseDouble(weigth);
		return true;
	}

	public boolean setPostalFrom(String postalCode) {
		String temp;
		char temp1 = 0;
		char temp2 = 0;
		int j = 0;
		temp = postalCode.replaceAll("\\s+", "");
		temp = temp.toLowerCase();
		if (temp.length() == 6) {
			for (char i : temp.toCharArray()) {
				if (j == 0) {
					if (i > (int) 96 && i < (int) 123) {
						j++;
					} else {
						return false;
					}
				} else if (j == 1) {
					if (i > (int) 47 && i < (int) 58) {
						j = 0;
					} else {
						return false;
					}
				}
			}
		} else {
			return false;
		}
		this.senderPostalCode = postalCode;
		return true;
	}

	public boolean setPostalToo(String postalCode) {
		String temp;
		char temp1 = 0;
		char temp2 = 0;
		int j = 0;
		temp = postalCode.replaceAll("\\s+", "");
		temp = temp.toLowerCase();
		if (temp.length() == 6) {
			for (char i : temp.toCharArray()) {
				if (j == 0) {
					if (i > (int) 96 && i < (int) 123) {
						j++;
					} else {
						return false;
					}
				} else if (j == 1) {
					if (i > (int) 47 && i < (int) 58) {
						j = 0;
					} else {
						return false;
					}
				}
			}
		} else {
			return false;
		}
		this.receiverPostalCode = postalCode;
		return true;
	}

	public boolean setType(String type) {
		if (onlyNum(type) != true)
			return false;
		double temp1 = Double.parseDouble(type);
		if (temp1 != 3.0 && temp1 != 1.0 && temp1 != 2.0)
			return false;
		int temp = Integer.parseInt(type);
		if (temp < 0 || temp > 3)
			return false;
		this.caseValue = temp;
		return true;
	}

	public double getLength() {
		return this.numLength;
	}

	public double getHeight() {
		return this.numHeight;
	}

	public double getWidth() {
		return this.numWidth;
	}

	public double getWeight() {
		return this.numWeight;
	}

	public String getSenderPostal() {
		return this.senderPostalCode;
	}

	public String getReceiverPostal() {
		return this.receiverPostalCode;
	}

	public static boolean onlyNum(String input) {
		for (char i : input.toCharArray()) {
			if (i < 46 || i > 57 || i == 47) {
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {

		postalRate pr = new postalRate();

		System.out.println(
				"This program will help you find the estimated cost of pour parsel\n according to the Canada posts specifications.");
		pr.printCases(1);
		System.exit(0);
	}
}
