package model;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import model.exceptions.UnableToDoTransactionException;

public class CurrentAccountTest {

	@Test
	public void whenIAddCreditToAnAccountCheckThatTheOperationIsDone() {

		CurrentAccount account = new CurrentAccount();

		account.addCredit(500.5f);
		assertTrue(account.getCredit() == (500.5f));
		//assertTrue(account.getMovements().contains("Se acreditaron $500.5 en tu cuenta"));
	}

	@Test
	public void whenIDoATransferToAVehicleOwnerCheckThatTheOperationIsDone() throws UnableToDoTransactionException {

		User vehicleOwner = mock(User.class);
		CurrentAccount account = new CurrentAccount();
		account.addCredit(500.5f);

		account.transferCreditTo(250.3f, vehicleOwner);

		verify(vehicleOwner, atLeastOnce()).addCredit(250.3f);

		assertTrue(account.getCredit() == (250.2f));
		//assertTrue(account.getMovements().contains("Se debitaron $250.3 de tu cuenta"));
	}

	@Test(expected = UnableToDoTransactionException.class)
	public void whenIDoATransferToAVehicleOwnerAndIDontHaveEnoughMoneyCheckThatTheOperationIsNotDone()
			throws UnableToDoTransactionException {

		User vehicleOwner = mock(User.class);
		CurrentAccount account = new CurrentAccount();
		account.addCredit(100.5f);

		account.transferCreditTo(250.3f, vehicleOwner);

	}

}
