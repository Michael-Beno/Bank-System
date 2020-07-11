package ie.michael.bank.services;

import java.util.List;

//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ie.michael.bank.dao.AccountDao;
import ie.michael.bank.mappers.Account;


@Service
public class AccountServiceImplementation implements AccountService{

	@Autowired
	AccountDao accountDao;

	
	public List<Account> getAllAccounts() {
		return accountDao.findAll();
	}
	
	public int saveAccount(double accountBalance, double accountLimit) {
		return accountDao.saveAndReturnAccountId(accountBalance,accountLimit);
	}
	
	public List<Account> findAllAccountsWithCustomers(int customerId) {
		return accountDao.findAllWithCustomers(customerId);
	}

	public Account findAccountbyAccountNumber(String accountNumber) {
		return accountDao.findByAccountNumber(accountNumber);
	}
	
	public double getTotalMoneyInTheBank() {
		return accountDao.getAllMoney();
	}
	
	public int getCountOver10000() {
		return accountDao.getCountOver10000();
	}
	
	public void closeAccountByID(int acccountId) {
		accountDao.closeByID(acccountId);
		
	}
	
	public int widthdrawFromAccount(Account account, double widthdraw) {
		return accountDao.widthdrawFromAccount(account,widthdraw);
	}
	
	public boolean lodgeMoneyToAccount(Account account, double lodgement) {
		return  accountDao.lodgeMoney(account,lodgement);
	}
	
	public int transferFromToAccount(Account from, Account to, double widthdraw) {
		return accountDao.transferMoneyFromTo(from, to, widthdraw);
	}

	public Account findAccountById(int accountId) {
		return accountDao.findById(accountId);
	}
	
}
