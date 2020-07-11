package ie.michael.bank.mappers;

public class Customer {
	private int customerId;
	private String customerName;
	private String customerAddress;
	private String customerEmail;
	private String customerPhone;
	private String customerDOB;
	
	public Customer() {
		super();
	}
	public Customer(String customerName, String customerAddress, String customerEmail, String customerPhone) {
		super();
		this.customerName = customerName;
		this.customerAddress = customerAddress;
		this.customerEmail = customerEmail;
		this.customerPhone = customerPhone;
	}
	
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getCustomerPhone() {
		return customerPhone;
	}
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	public String getCustomerDOB() {
		return customerDOB;
	}
	public void setCustomerDOB(String customerDOB) {
		this.customerDOB = customerDOB;
	}
	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", customerName=" + customerName + ", customerAddress="
				+ customerAddress + ", customerEmail=" + customerEmail + ", customerPhone=" + customerPhone
				+ ", customerDOB=" + customerDOB + "]";
	}
	public String display() {
		return "(" + customerId +") NAME: " + customerName + "    ADDRESS: "
				+ customerAddress + "    EMAIL: " + customerEmail + "    PHONE: " + customerPhone
				+ "    DOB: " + customerDOB ;
	}
	
}
