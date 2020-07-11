package ie.michael.bank.services;

import java.util.List;

import ie.michael.bank.mappers.Account;


public interface AccountService {

	List<Account> getAllAccounts();
	int saveAccount(double iDeposit, double iLimit);
	List<Account> findAllAccountsWithCustomers(int customerId);
	Account findAccountbyAccountNumber(String accountNumber);
	double getTotalMoneyInTheBank();
	int getCountOver10000();
	void closeAccountByID(int acccountId);
	int widthdrawFromAccount(Account account, double widthdraw);
	boolean lodgeMoneyToAccount(Account account, double widthdraw);
	int transferFromToAccount(Account from, Account to, double iAmount);
	Account findAccountById(int accountId);
	
}
