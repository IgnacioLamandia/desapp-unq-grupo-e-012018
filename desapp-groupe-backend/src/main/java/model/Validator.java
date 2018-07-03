package model;

public class Validator {

	public Boolean isValidCuil(String cuil) {

		Boolean valid = false;
	if(!this.isNullOrEmptyString(cuil))
	{
		int factor = 5;
		int[] c = new int[11];
		int resultado = 0;

		for (int i = 0; i < 10; i++) {
			c[i] = Integer.valueOf(Character.toString(cuil.charAt(i))).intValue();
			resultado = resultado + c[i] * factor;
			factor = (factor == 2) ? 7 : factor - 1;
		}
		c[10] = Integer.valueOf(Character.toString(cuil.charAt(10))).intValue();
		int control = (11 - (resultado % 11)) % 11;
		if (control == c[10]) {
			valid = true;
		}
	}
		return valid;
	}
	
	public Boolean isValidStringWithMinAndMax(String string, int min , int max) {
		if (string != null && (string.length() >= min && string.length() <= max))
			return true;
		else
			return false;
	}
	
	public Boolean isNullOrEmptyString(String string) {
	    if  (string==null || string.isEmpty()) {
	           return true;
	    } 
	    return false;
	}

	public Boolean isValidEmailAddress(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
	}

	public Boolean isValidTelephone(String telephone) {
//		String tPattern = "/^((?:\\(?\\d{3}\\)?[- .]?\\d{4}|\\(?\\d{4}\\)?[- .]?\\d{3}|\\(?\\d{5}\\)?[- .]?\\d{2})[- .]?\\d{4})$/";
//		java.util.regex.Pattern p = java.util.regex.Pattern.compile(tPattern);
//		java.util.regex.Matcher m = p.matcher(telephone);
//		return m.matches();
		
		if (telephone != null && (telephone.length() >= 6 && telephone.length() <= 13))
		{
			try {
			Integer.valueOf(telephone);
			}
			catch(NumberFormatException nfe)
			{
				return false;
			}
			return true;
		}
		else
			return false;
	}
	
	public Boolean validateUserAttributes(User user) throws IllegalArgumentException { 
		
		if (this.isValidCuil(user.getCuil()) &&
				this.isValidStringWithMinAndMax(user.getName(),4,50) &&
				this.isValidStringWithMinAndMax(user.getSurname(),4,50) &&
				!this.isNullOrEmptyString(user.getAddress()) &&
				this.isValidEmailAddress(user.getEmail()))
		{
			
			return true;
		} else {
			System.out.println(this.isValidCuil(user.getCuil()) + "***" +
					this.isValidStringWithMinAndMax(user.getName(),4,50) +  "***" +
					this.isValidStringWithMinAndMax(user.getSurname(),4,50) + "***" +
					!this.isNullOrEmptyString(user.getAddress()) + "***" +
					this.isValidEmailAddress(user.getEmail()));
			throw new IllegalArgumentException("Invalid argument");
		}
	}
	
	public Boolean validatePublicationAttributes(Publication publication) { 
		
		if ( (!this.isNullOrEmptyString(publication.getRetireAddress())) &&
			 (!this.isNullOrEmptyString(publication.getReturnAddress())) &&
			 (this.isValidTelephone(publication.getTelephone())) &&
			 (publication.getCostPerHour() > 0 && publication.getCostPerHour() != null) &&
			 (publication.getVehicle() != null) &&
			 (publication.getOwner() != null)) {
			return true;
		}
		else {
			throw new IllegalArgumentException("Invalid argument");
		}
	}
	
	public Boolean validateVehicleAttributes(Vehicle vehicle) { 
		
		if ( (!this.isNullOrEmptyString(vehicle.getModel())) &&
			 (vehicle.getType() != null) &&
			 (vehicle.getNumberOfPassengers() > 0 && vehicle.getNumberOfPassengers() != null) &&
			 (this.isValidStringWithMinAndMax(vehicle.getDescription(),30,200)) &&
			 (!this.isNullOrEmptyString(vehicle.getPhoto()))) 
		{
			return true;
		}
		else {
			throw new IllegalArgumentException("Invalid argument");
		}
	}
		
}