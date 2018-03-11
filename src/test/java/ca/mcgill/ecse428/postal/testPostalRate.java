package ca.mcgill.ecse428.postal;

import static org.junit.Assert.*;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import ca.mcgill.ecse428.postal.postalRate;

public class testPostalRate {

	postalRate pr;

	/**
	 * This validation tests: The postalRate code will output the price of the
	 * parcel according to the weight and destination. From the information .xlsx
	 * file available through Post Canada, this the rate chart demonstrate the cost
	 * of shipping the a parcel from Montreal to anywhere in Canada.
	 * 
	 * The validation test will assert according to the price expected.
	 */
	@Test
	public void validationTest() {

		// Case 1
		pr = new postalRate(50.0, 50.0, 25.0, 10.0, "H3E1R6", "H2X2H3", (int) 1);
		assertEquals(16.19, pr.calculateParcelRate(), 0.0);
		// Case 2
		pr = new postalRate(25.0, 35.0, 70.0, 5.0, "H3E1R6", "H2X2H3", (int) 2);
		assertEquals(15.44, pr.calculateParcelRate(), 0.0);
		// Case 3
		pr = new postalRate(120.0, 60.0, 20.0, 20.0, "H3E1R6", "H2X2H3", (int) 1);
		assertEquals(22.97, pr.calculateParcelRate(), 0.0);
		// Case 4
		pr = new postalRate(100.0, 80.0, 20.0, 20.0, "H3E1R6", "H2X2H3", (int) 3);
		assertEquals(37.46, pr.calculateParcelRate(), 0.0);
	}

	/**
	 * Invalidation test for postal code of sender.
	 * 
	 * Multiple invalid Strings are used for the test which does not match with the
	 * Canadian postal code standard. There is also a valid postal case to make sure
	 * the codes written inside of the setPostalFrom is written correctly.
	 * 
	 * The setPostalFrom outputs a boolean value if the input is correct or
	 * incorrect. This is used to valid the input.
	 */
	@Test
	public void invalidPostalCodeSender() {

		pr = new postalRate();

		// Case 1
		assertEquals(false, pr.setPostalFrom("112233"));
		assertEquals(false, pr.setPostalFrom("h3e1r"));
		assertEquals(true, pr.setPostalFrom("h3e1r6"));
		assertEquals(true, pr.setPostalFrom("H3E1R6"));
		assertEquals(true, pr.setPostalFrom("H3E 1R6"));
		assertEquals(false, pr.setType(""));
	}

	/**
	 * Same as the invalidPostalCodeSender test.
	 */
	@Test
	public void invalidPostalCodeReceiver() {

		pr = new postalRate();

		// Case 1
		assertEquals(false, pr.setPostalToo("112233"));
		assertEquals(false, pr.setPostalToo("h3e1r"));
		assertEquals(true, pr.setPostalToo("h3e1r6"));
		assertEquals(true, pr.setPostalToo("H3E1R6"));
		assertEquals(true, pr.setPostalToo("H3E 1R6"));
		assertEquals(false, pr.setType(""));
	}

	/**
	 * Invalid length.
	 * 
	 * This tests the invalid String inputs that the user may put. There should only
	 * a numbers and dot. Other characters should not me allowed. Empty case is also
	 * tested.
	 * 
	 * The setNumLength outputs a boolean value which is used to know the state of
	 * the input (if valid or invalid).
	 * 
	 * This test also makes sure that the Post Canada's minimum and maximum are met.
	 */
	@Test
	public void invalidLength() {
		pr = new postalRate();

		// Case 1
		assertEquals(false, pr.setNumLength("Hello World!"));
		assertEquals(false, pr.setNumLength("!22.33"));
		assertEquals(false, pr.setNumLength("22,33"));
		assertEquals(true, pr.setNumLength("22.33"));
		assertEquals(false, pr.setNumLength("123.444cm"));
		assertEquals(false, pr.setNumLength("0.0"));
		assertEquals(false, pr.setNumLength("0.01"));
		assertEquals(true, pr.setNumLength("0.1"));
		assertEquals(true, pr.setNumLength("200.0"));
		assertEquals(false, pr.setNumLength("200.1"));
		assertEquals(false, pr.setType(""));

	}

	/**
	 * Invalid Width test.
	 * 
	 * Same pattern as the invalidLenth test.
	 */
	@Test
	public void invalidWidth() {
		pr = new postalRate();

		// Case 1
		assertEquals(false, pr.setNumWidth("Hello World!"));
		assertEquals(false, pr.setNumWidth("!22.33"));
		assertEquals(false, pr.setNumWidth("22,33"));
		assertEquals(true, pr.setNumWidth("22.33"));
		assertEquals(false, pr.setNumWidth("123.444cm"));
		assertEquals(false, pr.setNumWidth("0.0"));
		assertEquals(false, pr.setNumWidth("0.01"));
		assertEquals(true, pr.setNumWidth("0.1"));
		assertEquals(true, pr.setNumWidth("200.0"));
		assertEquals(false, pr.setNumWidth("200.1"));
		assertEquals(false, pr.setType(""));

	}

	/**
	 * Invalid Height test.
	 * 
	 * Same pattern as the invalidLenth test.
	 */
	@Test
	public void invalidHeight() {
		pr = new postalRate();

		// Case 1
		assertEquals(false, pr.setNumHeight("Hello World!"));
		assertEquals(false, pr.setNumHeight("!22.33"));
		assertEquals(false, pr.setNumHeight("22,33"));
		assertEquals(true, pr.setNumHeight("22.33"));
		assertEquals(false, pr.setNumHeight("123.444cm"));
		assertEquals(false, pr.setNumHeight("0.0"));
		assertEquals(false, pr.setNumHeight("0.01"));
		assertEquals(true, pr.setNumHeight("0.1"));
		assertEquals(true, pr.setNumHeight("200.0"));
		assertEquals(false, pr.setNumHeight("200.1"));
		assertEquals(false, pr.setType(""));

	}

	/**
	 * Invalid weight test.
	 * 
	 * Same pattern as the invalidLenth test.
	 */
	@Test
	public void invalidWeight() {
		pr = new postalRate();

		// Case 1
		assertEquals(false, pr.setNumWeight("Hello World!"));
		assertEquals(false, pr.setNumWeight("!22.33"));
		assertEquals(false, pr.setNumWeight("22,33"));
		assertEquals(true, pr.setNumWeight("22.33"));
		assertEquals(false, pr.setNumWeight("123.444cm"));
		assertEquals(false, pr.setNumWeight("0.0"));
		assertEquals(false, pr.setNumWeight("0.01"));
		assertEquals(true, pr.setNumWeight("0.1"));
		assertEquals(true, pr.setNumWeight("30.0"));
		assertEquals(false, pr.setNumWeight("30.1"));
		assertEquals(false, pr.setType(""));

	}

	/**
	 * Invalid Type test.
	 * 
	 * Same pattern as the invalidLenth test.
	 */
	@Test
	public void invalidType() {
		pr = new postalRate();

		// Case 1
		assertEquals(false, pr.setType("Hello World!"));
		assertEquals(false, pr.setType("123"));
		assertEquals(false, pr.setType("1.1"));
		assertEquals(false, pr.setType("1.4"));
		assertEquals(false, pr.setType("1.9"));
		assertEquals(true, pr.setType("1"));
		assertEquals(true, pr.setType("2"));
		assertEquals(true, pr.setType("3"));
		assertEquals(false, pr.setType(""));

	}

	/**
	 * Invalid Girth+length test.
	 * 
	 * This sets the one invalid combinations of length, height and width. This
	 * makes sure that tree possible cases are met. ( > 300 ; < 300 but > 0 ; = 300)
	 */
	@Test
	public void invalidGirthLenght() {

		System.out.println("Girth+lenth invalid tests > 300");
		pr = new postalRate();
		pr.setNumHeight("200.0");
		pr.setNumLength("50.0");
		pr.setNumWidth("50.0");
		System.out.println(pr.girthLength());
		assertFalse(pr.girthLength());

		System.out.println("Girth+lenth valid tests < 300");
		pr = new postalRate();
		pr.setNumHeight("30.0");
		pr.setNumLength("40.0");
		pr.setNumWidth("20.0");
		System.out.println(pr.girthLength());
		assertTrue(pr.girthLength());

		System.out.println("Girth+lenth valid tests = 300");
		pr = new postalRate();
		pr.setNumHeight("180");
		pr.setNumLength("30.0");
		pr.setNumWidth("30.0");
		System.out.println(pr.girthLength());
		assertTrue(pr.girthLength());

	}

}
