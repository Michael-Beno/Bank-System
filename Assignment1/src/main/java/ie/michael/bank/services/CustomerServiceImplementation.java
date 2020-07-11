package ie.michael.bank.services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ie.michael.bank.dao.CustomerDao;
import ie.michael.bank.mappers.Customer;



@Service
public class CustomerServiceImplementation implements CustomerService{

	@Autowired
	CustomerDao customerDao;

	
	public Customer getACustomerByItsId(int customerId) {
		return customerDao.findById(customerId);
	}
	
	public List<Customer> getAllCustomers() {
		return customerDao.findAll();
	}
	
	public List<Customer> findAllCustomersWithAccounts(int customerId) {
		return customerDao.findAllWithAccounts(customerId);
	}
	
	public List<Customer> getACustomerByItsPhoneorDOB(String customerPhoneOrDOB) {
		return customerDao.findByPhoneOrDOB(customerPhoneOrDOB);
	}
	
	public int saveCustomer(String customerName, String customerAddress, String customerDOB, String customerEmail, String customerPhone) {
		return customerDao.saveAndReturnCustomerId(customerName, customerAddress, customerEmail, customerPhone, customerDOB);
	}

	public void addCustomerToAccount(int customerId, int accountId) {
		customerDao.addCustomerToAcount(customerId, accountId);
		
	}

	@Override
	public List<Customer> getACustomerByItsAccountNo(String accountNumber) {
		return customerDao.getCustomerByAccountNumber(accountNumber);
		
	}

}
