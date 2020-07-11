package ie.michael.bank.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CustomerRowMapper implements RowMapper<Customer>{

	public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Customer customer = new Customer();
		customer.setCustomerId(rs.getInt("customerId"));
		customer.setCustomerName(rs.getString("customerName"));
		customer.setCustomerAddress(rs.getString("customerAddress"));
		customer.setCustomerEmail(rs.getString("customerEmail"));
		customer.setCustomerPhone(rs.getString("customerPhone"));
		customer.setCustomerDOB(rs.getString("customerDOB"));
		return customer;
	}
}
