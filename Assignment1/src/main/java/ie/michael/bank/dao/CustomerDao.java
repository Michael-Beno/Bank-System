package ie.michael.bank.dao;

import java.util.List;

import ie.michael.bank.mappers.Customer;


public interface CustomerDao {
	
	
	List<Customer> findAll();
	List<Customer> findAllWithAccounts(int customerId);
	Customer findById(int customerId);
	List<Customer> findByPhoneOrDOB(String customerPhoneOrDOB);
	int saveAndReturnCustomerId(String customerName, String customerAddress,
			String customerEmail, String customerPhone, String customerDOB);
	boolean addCustomerToAcount(int customerId, int accountId);
	List<Customer> getCustomerByAccountNumber(String accountNumber);
	

}
