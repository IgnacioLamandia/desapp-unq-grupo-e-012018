package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ValidatorTest {
	
	private Validator validator = new Validator();

	@Test
	public void checkIfACuilIsValid() {
		String cuil = "20317359692";
		assertTrue(validator.isValidCuil(cuil));
	}
	
	@Test
	public void checkIfACuilIsInvalid() {
		String cuil = "11111111111";
		assertFalse(validator.isValidCuil(cuil));
	}
	
	@Test
	public void checkIfANameOrSurnameIsValid() {
		String name = "Pedro";
		assertTrue(validator.isValidStringWithMinAndMax(name,4,50));
	}
	
	@Test
	public void checkIfANameOrSurnameIsInvalidWhenIsEmpty() {
		String name = "";
		assertFalse(validator.isValidStringWithMinAndMax(name,4,50));
	}
	
	@Test
	public void checkIfANameOrSurnameIsInvalidWhenIsNull() {
		String name = null;
		assertFalse(validator.isValidStringWithMinAndMax(name,4,50));
	}
	
	@Test
	public void checkIfANameOrSurnameIsInvalidWhenIsLessThanFour() {
		String name = "foo";
		assertFalse(validator.isValidStringWithMinAndMax(name,4,50));
	}
	
	@Test
	public void checkIfANameOrSurnameIsInvalidWhenIsMoreThanFifty() {
		String name = "ggggggggggggggggggggggggggggggggggggggggggggggggggggggg"; //55 chars
		assertFalse(validator.isValidStringWithMinAndMax(name,4,50));
	}
	
	@Test
	public void checkIfAnEmailIsValid() {
		String mail = "pepe@gmail.com";
		assertTrue(validator.isValidEmailAddress(mail));
	}
	
	@Test
	public void checkIfAnEmailIsInvalid() {
		String mail = "pepe.com";
		assertFalse(validator.isValidEmailAddress(mail));
	}
	
	@Test
	public void checkIfATelephoneIsValid() {
		String telephone = "01142249785";
		assertTrue(validator.isValidTelephone(telephone));
	}
	
	@Test
	public void checkIfATelephoneIsInvalid() {
		String telephone = "485";
		assertFalse(validator.isValidTelephone(telephone));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkIfAUserIsInvalidAllAttributesInvalid() throws IllegalArgumentException {
		User u = new User("", "","","","" );
		validator.validateUserAttributes(u);
		
	}
	
	@Test
	public void checkIfAUserIsValid() {
		User u = new User("20317359692", "ooooooo","Looooopez","gaboto 32","pepe@gmail.com" );
		assertTrue(validator.validateUserAttributes(u));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkIfAVehicleIsInvalidAllAttributesInvalid() throws IllegalArgumentException {
		Vehicle  v = new Vehicle("", null, -5, "", "");
		validator.validateVehicleAttributes(v);
	}
	
	@Test
	public void checkIfAVehicleIsValid() {
		Vehicle  v = new Vehicle("focus", VehicleType.Car, 5, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "http://xxxxxx.com");
		assertTrue(validator.validateVehicleAttributes(v));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkIfAPublicationIsInvalidAllAttributesInvalid() throws IllegalArgumentException {
		Publication  p = new Publication(null, "","","",-5d,null);
		validator.validatePublicationAttributes(p);
	}
	
	@Test
	public void checkIfAPublicationIsValid() {
		Vehicle  v = new Vehicle();
		User u = new User();
		Publication  p = new Publication(v,"gaboto 50", "gaboto 5", "42248888", 50d, u);
		assertTrue(validator.validatePublicationAttributes(p));
	}
}
