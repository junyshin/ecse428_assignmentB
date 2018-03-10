package ca.mcgill.ecse428.postal;

import static org.junit.Assert.*;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import ca.mcgill.ecse428.postal.postalRate;

public class testPostalRate {

	postalRate pr;

	@Test
	public void validationTest() {

		// Case 1
		pr = new postalRate((float) 50.0, (float) 50.0, (float) 25.0, (float) 10.0, "h3e1r6", "h2x2h3", 1);
		System.out.println(pr.calculateParcelRate());
		assertEquals((float) 21.11, pr.calculateParcelRate(), (float) 0.0);
		// Case 2
		pr = new postalRate((float) 25.0, (float) 35.0, (float) 70.0, (float) 5.0, "h3e1r6", "h2x2h3", 2);
		System.out.println(pr.calculateParcelRate());
		assertEquals((float) 27.95, pr.calculateParcelRate(), (float) 0.0);
		// Case 3
		pr = new postalRate((float) 120.0, (float) 60.0, (float) 20.0, (float) 20.0, "h3e1r6", "h2x2h3", 1);
		System.out.println(pr.calculateParcelRate());
		assertEquals((float) 43.95, pr.calculateParcelRate(), (float) 0.0);
		// Case 4
		pr = new postalRate((float) 100.0, (float) 80.0, (float) 20.0, (float) 20.0, "h3e1r6", "h2x2h3", 1);
		System.out.println(pr.calculateParcelRate());
		assertEquals((float) 34.70, pr.calculateParcelRate(), (float) 0.0);
	}

	@Test
	public void invalidPostalCodeSender() {

		pr = new postalRate();

		// Case 1
		assertEquals(false, pr.setPostalFrom("112233"));
		assertEquals(false, pr.setPostalFrom("h3e1r"));
		assertEquals(true, pr.setPostalFrom("h3e1r6"));
		assertEquals(true, pr.setPostalFrom("H3E1R6"));
		assertEquals(true, pr.setPostalFrom("H3E 1R6"));
	}

	@Test
	public void invalidPostalCodeReceiver() {

		pr = new postalRate();

		// Case 1
		assertEquals(false, pr.setPostalToo("112233"));
		assertEquals(false, pr.setPostalToo("h3e1r"));
		assertEquals(true, pr.setPostalToo("h3e1r6"));
		assertEquals(true, pr.setPostalToo("H3E1R6"));
		assertEquals(true, pr.setPostalToo("H3E 1R6"));
	}

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

	}

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

	}

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

	}

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

	}

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

	}

}
