package ca.mcgill.ecse428.postal;

import java.util.Scanner;
import java.io.IOException;
import java.io.File;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * @author Jun Young Shin
 *
 */
public class postalRate {
	
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

	/**
	 * @param lenght
	 * @param width
	 * @param height
	 * @param weight
	 * @param sending
	 * @param toDestonation
	 * @param type
	 */
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
	
	/**
	 * Default way to create a new class
	 */
	public postalRate() {
		
	}
	
	/**
	 * @return price of parcel
	 */
	public double calculateParcelRate() {
		
		String source = this.senderPostalCode;
		String destination = this.receiverPostalCode;
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
	
	/**
	 * @param source
	 * @param destination
	 * @return The postal index. If not found, it will return null.
	 */
	private static String findRateCode(String source, String destination) {

		for (Row row : rateCodeLookupSheet) {
			if (row.getCell(0).toString().equals(destination.substring(0, 3))) {
				return row.getCell(1).toString();
			}
		}

		return null;
	}
	
	/**
	 * @param rateCode
	 * @param row
	 * @param rowInit
	 * @return The price of the parcel. If not found, it will return 0.
	 */
	private static double getParcelRate(String rateCode, int row, int rowInit) {
		// find the corresponding price for the given weight and rate code
		for (Cell cell : rateSheet.getRow(rowInit - 2)) {
			if (cell.toString().equals(rateCode)) {
				return Double.parseDouble(rateSheet.getRow(row).getCell(cell.getColumnIndex()).toString());
			}
		}
		return 0;
	}

	/**
	 * @param length
	 * @return boolean
	 */
	public boolean setNumLength(String length) {
		if (length.isEmpty()) {
			return false;
		}
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

	/**
	 * @param width
	 * @return boolean
	 */
	public boolean setNumWidth(String width) {
		if (width.isEmpty()) {
			return false;
		}
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

	/**
	 * @param height
	 * @return boolean
	 */
	public boolean setNumHeight(String height) {
		if (height.isEmpty()) {
			return false;
		}
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

	/**
	 * @param weight
	 * @return boolean
	 */
	public boolean setNumWeight(String weight) {
		if (weight.isEmpty()) {
			return false;
		}
		if (onlyNum(weight) != true)
			return false;
		double temp = Double.parseDouble(weight);
		if (temp < (double) 0.1)
			return false;
		if (temp > (double) 30.0)
			return false;
		this.numWeight = Double.parseDouble(weight);
		return true;
	}

	/**
	 * @param postalCode
	 * @return boolean
	 */
	public boolean setPostalFrom(String postalCode) {
		String temp;
		int j = 0;
		if (postalCode.isEmpty()) {
			return false;
		}
		temp = postalCode.replaceAll("\\s+", "");
		temp = temp.toUpperCase();
		if (temp.length() == 6) {
			for (char i : temp.toCharArray()) {
				if (j == 0) {
					if (i > (int) 64 && i < (int) 91) {
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
		this.senderPostalCode = temp;
		return true;
	}

	/**
	 * @param postalCode
	 * @return boolean
	 */
	public boolean setPostalToo(String postalCode) {
		String temp;
		int j = 0;
		if (postalCode.isEmpty()) {
			return false;
		}
		temp = postalCode.replaceAll("\\s+", "");
		temp = temp.toUpperCase();
		if (temp.length() == 6) {
			for (char i : temp.toCharArray()) {
				if (j == 0) {
					if (i > (int) 64 && i < (int) 91) {
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
		this.receiverPostalCode = temp;
		return true;
	}

	/**
	 * @param type
	 * @return boolean
	 */
	public boolean setType(String type) {
		if (type.isEmpty()) {
			return false;
		}
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

	/**
	 * @return length
	 */
	public double getLength() {
		return this.numLength;
	}

	/**
	 * @return height
	 */
	public double getHeight() {
		return this.numHeight;
	}

	/**
	 * @return width
	 */
	public double getWidth() {
		return this.numWidth;
	}

	/**
	 * @return numWeight
	 */
	public double getWeight() {
		return this.numWeight;
	}

	/**
	 * @return senderPostalCode
	 */
	public String getSenderPostal() {
		return this.senderPostalCode;
	}

	/**
	 * @return receiverPostalCode
	 */
	public String getReceiverPostal() {
		return this.receiverPostalCode;
	}
	
	public boolean girthLength() {
		
		double lenght = this.numLength;
		double height = this.numHeight;
		double width = this.numWidth;
		
		double girthLength1 = (2*lenght)+(2*height)+width;
		double girthLength2 = (2*lenght)+(2*width)+height;
		double girthLength3 = (2*height)+(2*width)+lenght;
		
		if ((girthLength1 <= 300.0 && girthLength1 > 0.0) || (girthLength2 <= 300.0 && girthLength2 > 0.0) || (girthLength3 <= 300.0 && girthLength3 > 0.0)) {
			return true;
		}
		
		return false;
	}

	/**
	 * @param input
	 * @return
	 */
	public static boolean onlyNum(String input) {
		for (char i : input.toCharArray()) {
			if (i < 46 || i > 57 || i == 47) {
				return false;
			}
		}
		return true;
	}
}
