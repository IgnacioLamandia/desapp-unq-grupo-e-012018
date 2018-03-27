package model;

import java.util.HashSet;
import java.util.Set;

public class User {
	
	private String cuil;
	private String name;
	private String surname;
	private String address;
	private String email;
	private Double credit;
	private Set<Vehicle> myVehicles;
	private Set<Publication> myPublications;
	private Rating rating;
	
	public User() {}
	
	public User(String cuil, String name, String surname, String address, String email) {
		this.cuil = cuil;
		this.name = name;
		this.surname = surname;
		this.address = address;
		this.email = email;
		this.credit = 0.0;
		this.myVehicles = new HashSet<Vehicle>();
		this.myPublications = new HashSet<Publication>();
		this.rating = new Rating();
	}

	public String getCuil() {
		return cuil;
	}

	public void setCuil(String cuil) {
		this.cuil = cuil;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Double getCredit() {
		return credit;
	}

	public void setCredit(Double credit) {
		this.credit = credit;
	}
	
	public void addCredit(Double moreCredit) {
		this.credit += moreCredit;
	}
	
	public void useCredit(Double lessCredit) {
		if(lessCredit <= this.credit) {
			this.credit -= lessCredit;
		} 
	}

	public Set<Vehicle> getMyVehicles() {
		return myVehicles;
	}

	public void setMyVehicles(Set<Vehicle> myVehicles) {
		this.myVehicles = myVehicles;
	}
	
	public void addVehicle(Vehicle newVehicle) {
		this.myVehicles.add(newVehicle);
	}
	
	public Set<Publication> getMyPublications() {
		return myPublications;
	}

	public void setMyPublications(Set<Publication> myPublications) {
		this.myPublications = myPublications;
	}
	
	public void addPublication(Publication newPublication) {
		this.myPublications.add(newPublication);
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}
	
}