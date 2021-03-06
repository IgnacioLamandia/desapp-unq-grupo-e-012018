package model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import model.exceptions.UnableToDoTransactionException;

@Entity
public class CurrentAccount {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int id;
	private float credit;
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> movements;

	public CurrentAccount() {
		this.credit = 0f;
		this.movements = new HashSet<String>();
	}
	
	public float getCredit() {
		return credit;
	}

	public Set<String> getMovements() {
		return movements;
	}

	public void addCredit(float moreCredit) {
		this.credit += moreCredit;
		LocalDateTime date = LocalDateTime.now();
		String movement = "[" + date.getDayOfMonth()+"/"+date.getMonthValue()+"/"+date.getYear()+" - " +date.getHour()+":"+date.getMinute()+":"+date.getSecond()+ 
						  "] - Se acreditaron $" + moreCredit + " en tu cuenta.";
		this.movements.add(movement);
	}
	
	public void retireCredit(float lessCredit) throws UnableToDoTransactionException {
		if (lessCredit <= this.credit) {
			this.credit -= lessCredit;
			LocalDateTime date = LocalDateTime.now();
			String movement = "[" + date.getDayOfMonth()+"/"+date.getMonthValue()+"/"+date.getYear()+" - " +date.getHour()+":"+date.getMinute()+":"+date.getSecond()+
							  "] - Se debitaron $" + lessCredit + " de tu cuenta.";
			this.movements.add(movement);
		} else
			throw new UnableToDoTransactionException();
	}

	public void transferCreditTo(float transfer, User vehicleOwner) throws UnableToDoTransactionException {
			this.retireCredit(transfer);
			vehicleOwner.addCredit(transfer);
	}
}
