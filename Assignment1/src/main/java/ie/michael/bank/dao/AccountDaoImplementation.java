package ie.michael.bank.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ie.michael.bank.mappers.Account;
import ie.michael.bank.mappers.AccountRowMapper;



@Repository
public class AccountDaoImplementation implements AccountDao{

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public Account findById(int accountId) {
		String sql = "SELECT * FROM account WHERE accountId = ?";
		Account account = null;
		try {
			account = jdbcTemplate.queryForObject(sql, new AccountRowMapper(), accountId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return account;
	}
	
	public List<Account> findAll() {
		String sql = "SELECT * FROM account";
		List<Account> listAccounts = jdbcTemplate.query( sql, new AccountRowMapper());
	    return listAccounts;
	}
	
	public List<Account> findAllWithCustomers(int customerId) {
		String sql = "SELECT * FROM account JOIN customerAccount ON account.accountId=customerAccount.accountId AND customerAccount.customerId=?";
		List<Account> account = jdbcTemplate.query(sql, new AccountRowMapper(), customerId);
		return account;
	}

	public int saveAndReturnAccountId(double accountBalance, double accountLimit) {
		int accountId =-1;

		String sql = "INSERT INTO account(accountBalance, accountLimit) VALUES(?,?)";
		jdbcTemplate.update(sql, new Object[] {accountBalance*100, accountLimit});

		sql = "SELECT MAX(accountID) FROM account;";
		accountId =  jdbcTemplate.queryForObject(sql, Integer.class);

		return accountId;
	}
	
	public Account findByAccountNumber(String accountNumber) {
		String sql = "SELECT * FROM account WHERE accountNumber = ?";
		Account account = null;
		try {
			account = jdbcTemplate.queryForObject(sql, new AccountRowMapper(), accountNumber);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return account;
	}
	
	public void closeByID(int acccountId) {
		String sql = "UPDATE account SET accountActive = '0' WHERE accountId = ?";
		jdbcTemplate.update(sql, new Object[] {acccountId});
	}
	
	public double getAllMoney() {
		String sql = "select sum(accountBalance)*0.01 as accountBalance from account;";
		return jdbcTemplate.queryForObject(sql, Double.class);
	}
	
	public int getCountOver10000() {
		String sql = "select count(accountId) from account where accountBalance > 1000000";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
	
	
	public int widthdrawFromAccount(Account account, double widthdraw) {
		int status = -1;
		long lWidthdraw = (long) (widthdraw*100);
		if(!account.isAcountActive()) {
			//Account is NOT active
			status = -1;
		}else if((account.getAccountBalance()+account.getAccountLimit())*100 < lWidthdraw) {
			//trying withdraw more then possible
			status = 0;
		}else {
			String sql = "UPDATE account SET accountBalance = (accountBalance-?) WHERE accountId = ? AND accountActive = true";
			jdbcTemplate.update(sql, new Object[] {lWidthdraw,account.getAcccountId()});
			status = 1;
		}
		return status;
	}
	
	public boolean lodgeMoney(Account account, double lodgement) {
		long lodge = (long) (lodgement*100);
		if(!account.isAcountActive()) {
			//Account is NOT active
			return false;
		}else {
			String sql = "UPDATE account SET accountBalance = (accountBalance+?) WHERE accountId = ? AND accountActive = true";
			jdbcTemplate.update(sql, new Object[] {lodge,account.getAcccountId()});
			return true;
		}
	}
	
	public int transferMoneyFromTo(Account from, Account to, double trans) {
		int status = -1;
		
		if(from.isAcountActive() || to.isAcountActive()) {
			if((from.getAccountBalance()+from.getAccountLimit())< trans) {
				//trying withdraw more then possible
				status = 0;
			}else {
				status = widthdrawFromAccount(from, trans);
				lodgeMoney(to, trans);
			}
		} 
		return status;
	}
	
}
