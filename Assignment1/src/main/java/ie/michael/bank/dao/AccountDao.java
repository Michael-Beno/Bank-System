package ie.michael.bank.dao;

import java.util.List;

import ie.michael.bank.mappers.Account;


public interface AccountDao {
	
	List<Account> findAll();
	List<Account> findAllWithCustomers(int customerId);
	int saveAndReturnAccountId(double accountBalance, double accountLimit);
	Account findById(int accountId);
	Account findByAccountNumber(String accountNumber);
	int widthdrawFromAccount(Account account, double widthdraw);
	boolean lodgeMoney(Account account, double lodgement);
	int transferMoneyFromTo(Account from, Account to, double widthdraw);
	double getAllMoney();
	void closeByID(int acccountId);
	int getCountOver10000();
}
