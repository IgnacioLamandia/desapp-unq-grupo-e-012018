package model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import model.exceptions.UnableToDoTransactionException;
import model.states.user.ActiveState;
import model.states.user.BannedState;
import model.states.user.UserState;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int id;
	
	@Column(unique = true)
	private String cuil;
	private String name;
	private String surname;
	private String address;
	@Column(unique = true)
	private String email;
	
	@OneToOne(cascade = {CascadeType.ALL})
	private CurrentAccount currentAccount;	
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	private Set<Vehicle> myVehicles;
//	@JsonIgnore
//	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
//	private Set<Publication> myPublications;
	@ManyToOne(cascade = {CascadeType.ALL})
	private RatingCalculator rating;
//	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
//	private Set<Reservation> myReservations;
	@ManyToOne(cascade = {CascadeType.ALL})
	private UserState state;
	
	public User() {
		this.currentAccount = new CurrentAccount();
		this.myVehicles = new HashSet<Vehicle>();
//		this.myPublications = new HashSet<Publication>();
		this.rating = new RatingCalculator();
//		this.myReservations = new HashSet<Reservation>();
		this.state = new ActiveState();		
	}
	
	public User(String cuil, String name, String surname, String address, String email) {
		this.cuil = cuil;
		this.name = name;
		this.surname = surname;
		this.address = address;
		this.email = email;
		this.currentAccount = new CurrentAccount();
		this.myVehicles = new HashSet<Vehicle>();
//		this.myPublications = new HashSet<Publication>();
		this.rating = new RatingCalculator();
//		this.myReservations = new HashSet<Reservation>();
		this.state = new ActiveState();
	}

	public int getId() {
		return id;
	}
	
	public CurrentAccount getCurrentAccount() {
		return currentAccount;
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

	public Set<Vehicle> getMyVehicles() {
		return myVehicles;
	}

	public void setMyVehicles(Set<Vehicle> myVehicles) {
		this.myVehicles = myVehicles;
	}
	
	public void addVehicle(Vehicle newVehicle) {
		this.myVehicles.add(newVehicle);
	}
	
//	public Set<Publication> getMyPublications() {
//		return myPublications;
//	}
//
//	public void setMyPublications(Set<Publication> myPublications) {
//		this.myPublications = myPublications;
//	}
//	
//	public void addPublication(Publication newPublication) {
//		this.myPublications.add(newPublication);
//	}

	public Double getRating() {
		return rating.getCurrentRating();
	}
	
	public void newRating(Integer rat) {
		this.rating.addNewRating(new Rating(rat));
	}

//	public Set<Reservation> getReservations() {
//		return this.myReservations;
//	}
//
//	public void addReservation(Reservation newReservation) {
//		this.myReservations.add(newReservation);
//		
//	}

	public void addCredit(float credit) {
		this.currentAccount.addCredit(credit);
	}
	
	public void retireCredit(float credit) {
		try {
		this.currentAccount.retireCredit(credit);
		} catch (UnableToDoTransactionException e) {
			e.getMessage();
			e.printStackTrace();
		}
	}
	
	public void trasnferCreditTo(float transfer, User vehicleOwner) {
		try {
			this.currentAccount.transferCreditTo(transfer, vehicleOwner);
		} catch (UnableToDoTransactionException e) {
			e.getMessage();
			e.printStackTrace();
		}
	}

	public Publication createNewPublication(Vehicle vehicle, String retireAddress, String returnAddress, String telephone, Double costPerHour) {
		
		Publication newPublication = new Publication(vehicle, retireAddress, returnAddress, telephone, costPerHour, this);
//		this.myPublications.add(newPublication);		
		return newPublication;
	}

	public void giveScore(Integer score) {
		Rating newRating = new Rating(score);
		this.rating.addNewRating(newRating);
		this.updateState();
	}

	private void updateState() {
		if(this.rating.getCurrentRating() < 4){
			this.state = new BannedState();		
		} else {
			this.state = new ActiveState();
		}		
	}

	public UserState getState() {
		return this.state;
	}
	
	public Boolean validateUserAttributes(String cuil, String name, String surname, String email) { 
		Validator validator = new Validator();
		
		return (validator.isValidCuil(cuil) &&
				validator.isValidStringWithMinAndMax(name,4,50) &&
				validator.isValidStringWithMinAndMax(surname,4,50) &&
				validator.isValidEmailAddress(email));
	}
	
}
