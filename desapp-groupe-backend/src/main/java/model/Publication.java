package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Publication {

	private Vehicle vehicle;
	private String city;
	private Ubication retireAddress;
	private Set<Ubication> returnAddress;
	private String telephone;
	private Set<LocalDate> datesAvailable;
	private Double cost;
	private List<Reservation> reservations;
	private User owner;

	public Publication() {
	}

	public Publication(Vehicle vehicle, String city, Ubication retireAddress, Set<Ubication> returnAddress,
			String description, String telephone, Set<LocalDate> datesAvailable, Double cost, User owner) {
		this.vehicle = vehicle;
		this.city = city;
		this.retireAddress = retireAddress;
		this.returnAddress = returnAddress;
		this.telephone = telephone;
		this.datesAvailable = datesAvailable;
		this.cost = cost;
		this.reservations = new ArrayList<Reservation>();
		this.owner = owner;
	}
	
	public void makeReservation(User client, LocalDate fromDate, LocalDate toDate){
		Reservation newReservation = new Reservation(this.owner, client, toDate, toDate);
		this.reservations.add(newReservation);
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Ubication getRetireAddress() {
		return retireAddress;
	}

	public void setRetireAddress(Ubication retireAddress) {
		this.retireAddress = retireAddress;
	}

	public Set<Ubication> getReturnAddress() {
		return returnAddress;
	}

	public void setReturnAddress(Set<Ubication> returnAddress) {
		this.returnAddress = returnAddress;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Set<LocalDate> getDatesAvailable() {
		return datesAvailable;
	}

	public void setDatesAvailable(Set<LocalDate> datesAvailable) {
		this.datesAvailable = datesAvailable;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

}