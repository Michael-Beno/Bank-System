package ie.michael.bank.services;

import java.util.List;

import ie.michael.bank.mappers.Customer;


public interface CustomerService {

	List<Customer> getAllCustomers();
	List<Customer> findAllCustomersWithAccounts(int customerId);
	int saveCustomer(String name, String address, String dob, String email, String phone);
	Customer getACustomerByItsId(int customerId);
	List<Customer> getACustomerByItsPhoneorDOB(String string);
	void addCustomerToAccount(int customerId, int accountId);
	List<Customer> getACustomerByItsAccountNo(String accountNumber);
}
