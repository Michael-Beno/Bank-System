package ie.michael.bank.mappers;

public class Account {

	
	private int acccountId, accountNumber, accountLimit;
	private long accountBalance;
	private boolean accountActive = true;

	
	
	public Account() {
		super();
	}
	public Account(int accountLimit) {
		super();
		this.accountLimit = accountLimit;
	}

	public int getAcccountId() {
		return acccountId;
	}

	public void setAcccountId(int acccountId) {
		this.acccountId = acccountId;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public int getAccountLimit() {
		return accountLimit;
	}

	public void setAccountLimit(int accountLimit) {
		this.accountLimit = accountLimit;
	}

	public double getAccountBalance() {
		return (Math.round((accountBalance*0.01) * 100.0) / 100.0);
	}

	public void setAccountBalance(long accountBalance) {
		this.accountBalance = accountBalance;
	}

	public boolean isAcountActive() {
		return accountActive;
	}

	public void setAcountActive(boolean acountActive) {
		this.accountActive = acountActive;
	}

	@Override
	public String toString() {
		return "Account [acccountId=" + acccountId + ", accountNumber=" + accountNumber + ", accountLimit="
				+ accountLimit + ", accountBalance=" + accountBalance + ", acountActive=" + accountActive + "]";
	}
	
	public String display() {
		String active = "";
		
		if(accountActive) active="[ACTIVE]";
		else active="[CLOSED]";
		
		return "(" + acccountId + ") ACCOUNT NUMBER: " + accountNumber + "    BALANCE: " + (Math.round((accountBalance*0.01) * 100.0) / 100.0)
				+ "    overdraft: " + accountLimit  + "    " + active;
	}
}
