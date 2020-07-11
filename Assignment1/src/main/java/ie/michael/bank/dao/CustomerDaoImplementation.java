package ie.michael.bank.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ie.michael.bank.mappers.Customer;
import ie.michael.bank.mappers.CustomerRowMapper;

@Repository 
public class CustomerDaoImplementation implements CustomerDao{

	@Autowired
	JdbcTemplate jdbcTemplate;

	public Customer findById(int customerId) {
		String sql = "SELECT * FROM customer WHERE customerId = ?";
		Customer customer = jdbcTemplate.queryForObject(sql, new CustomerRowMapper(), customerId);
		return customer;
	}
	
	public List<Customer> findByPhoneOrDOB(String customerPhoneOrDOB) {
		String sql = "SELECT * FROM customer WHERE (customerPhone = ? OR customerDOB = ?)";
		List<Customer>  customer = null;
		try {
			customer = jdbcTemplate.query(sql, new CustomerRowMapper(),  customerPhoneOrDOB, customerPhoneOrDOB);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return customer;
	}
	
	public List<Customer> findAll() {
		String sql = "SELECT * FROM customer";
		List<Customer> listCustomer = jdbcTemplate.query( sql, new CustomerRowMapper());
	    return listCustomer;
	}
	
	public List<Customer> findAllWithAccounts(int accountId) { //power
		String sql = "SELECT * FROM customer JOIN customerAccount ON customer.customerId=customerAccount.customerId AND customerAccount.accountId=?";
		List<Customer> customers = jdbcTemplate.query(sql, new CustomerRowMapper(), accountId);
		return customers;
	}
	
	public int saveAndReturnCustomerId(String customerName, String customerAddress, String customerEmail,
			String customerPhone, String customerDOB) {
		int customerId =-1;

		String sql = "INSERT INTO customer(customerName, customerAddress, customerEmail, customerPhone, customerDOB) VALUES(?,?,?,?,?)";
		jdbcTemplate.update(sql, new Object[] {customerName, customerAddress, customerEmail, customerPhone, customerDOB});

		sql = "SELECT MAX(customerID) FROM customer;";
		customerId =  jdbcTemplate.queryForObject(sql, Integer.class);
		return customerId;
	}


	public boolean addCustomerToAcount(int customerId, int accountId) {

		String sql = "INSERT INTO customerAccount(customerId, accountId) VALUES(?,?)";
		jdbcTemplate.update(sql, new Object[] {customerId, accountId});

		return false;
	}

	@Override
	public List<Customer> getCustomerByAccountNumber(String accountNumber) {
		String sql = "SELECT  CUSTOMER.customerId, CUSTOMER.customerName, CUSTOMER.customerAddress, CUSTOMER.customerEmail, CUSTOMER.customerPhone, CUSTOMER.customerDOB "
				+ "FROM ACCOUNT, CUSTOMER, CUSTOMERACCOUNT  "
				+ "WHERE ACCOUNT.accountId = CUSTOMERACCOUNT.accountId "
				+ "and CUSTOMER.customerId = CUSTOMERACCOUNT.customerId "
				+ "and ACCOUNT.accountNumber = '"+accountNumber+"';";
		
		List<Customer> listCustomer = jdbcTemplate.query( sql, new CustomerRowMapper());
	    return listCustomer;
	}

}
