package ie.michael.bank;

import java.util.List;
import java.util.Scanner;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import ie.michael.bank.errors.Errors;
import ie.michael.bank.mappers.Account;
import ie.michael.bank.mappers.Customer;
import ie.michael.bank.services.AccountService;
import ie.michael.bank.services.CustomerService;


public class App {
	
	private Scanner myObj = new Scanner(System.in);  // Create a Scanner object
	private Errors error;
	private ClassPathXmlApplicationContext context;
	private CustomerService customerService;
	private AccountService accountService;
	
	public App() {
		context = new ClassPathXmlApplicationContext("beans.xml");
		customerService = (CustomerService) context.getBean("customerServiceImplementation");
		accountService = (AccountService) context.getBean("accountServiceImplementation");

		mainMenu();
		
		context.close();
	}
	
	private void mainMenu() {
		String mainMenu = null;
		while (mainMenu == null || mainMenu.length() == 0 ||mainMenu == "-1") {		
			System.out.println("\n SELECT A NUMBER");
			System.out.println(" [1] Register new custmer become a bank customer i.e. register with the bank ");
			System.out.println(" [2] Create a new account");
			System.out.println(" [3] Add person to an account (for joint accounts)");
			System.out.println(" [4] view customers acounts - view his/her own accounts");
			
			System.out.println("\n [5] withdraw money from his/her account, subject to an overdraft limit");
			System.out.println(" [6] Deposit money into his/her account");
			System.out.println(" [7] Transfer money from one account to another");
			System.out.println(" [8] Close an account");
			
			System.out.println("\n [9] Total amount of money deposited in the bank ");
			System.out.println("     The number of accounts with deposits over €10,000");
			System.out.println(" [0] EXIT the app\n");
			System.out.print("ENTER THE NUMBER: ");
			mainMenu = myObj.nextLine();  // Read user input
			if(mainMenu.length() > 0)
			switch (mainMenu.charAt(0)) {
				case '0': break;
				case '1': mainMenu = null; register(); break;
				case '2': mainMenu = null; account();  break;
				case '3': mainMenu = null; joinAccount(); break;
				case '4': mainMenu = viewAccount();  break;
				case '5': mainMenu = widthdrawFromAccount();  break;
				case '6': mainMenu = depositToAccount();  break;
				case '7': mainMenu = transferMoney();  break;
				case '8': mainMenu = closeAccount();  break;
				case '9': mainMenu = bankInfo();  break;
				default: mainMenu = null; break;
			}
		}
		System.out.println("\n Program finish");
	}
	
	
	private void joinAccount() {
		int customerID = -1;
		customerID = createCustomer(customerID,-1);
		joinCustomerWithAccount(customerID, -1);
	}

	private void account() {
		 int accID = createAccount(-1,-1);
		 joinCustomerWithAccount(-1, accID);
	}

	private void register() {
		int customerID = -1;
		customerID = createCustomer(customerID,-1);
		int accountID = createAccount(customerID, -1);
		joinCustomerWithAccount(customerID, accountID);
		
	}

	
	private int createCustomer(int custId, int accID) {
		
		int customerId = custId;

		String input = "Y";
		boolean isError = true;
		while (input.compareTo("Y") == 0 || input.compareTo("y") == 0) {
			System.out.println("REGISTER NEW CUSTOMER ");
			System.out.print("Enter the NAME (max 30):");
			String name = myObj.nextLine();
			System.out.print("Enter the ADDRESS (max 100):");
			String address = myObj.nextLine();
			System.out.print("Enter the DOB (DD-MM-YYYY): ");
			String dob = myObj.nextLine();
			System.out.print("Enter the EMAIL (max 30):");
			String email = myObj.nextLine(); 
			System.out.print("Enter the Phone (9-16 digits):");
			String phone = myObj.nextLine();
			
			System.out.println("\nYou ENTERED:\n"
					+ "Name   : "+name
					+ "\nAddress: "+address
					+ "\nDOB    : "+dob
					+ "\nEmail  : "+email
					+ "\nPhone  : "+phone
					+ "\n\nPress ENTER to SAVE");

			if( name.length() > 30 ||
				address.length() >  100 ||
				email.length() >  30 ||
				phone.length() >  16 ||
				name.length() == 0 ||
				address.length() ==  0 ||
				email.length() == 0 ||
				phone.length() < 9 ||
				dob.length() != 10) {
				isError = true;
			}else {isError = false;}
			
			try {
				Integer.parseInt(phone);
				String chDOB = dob.charAt(0)+"";
				   chDOB += dob.charAt(1)+"";
				   chDOB += dob.charAt(3)+"";
				   chDOB += dob.charAt(4)+"";
				   chDOB += dob.charAt(6)+"";
				   chDOB += dob.charAt(7)+"";
				   chDOB += dob.charAt(8)+"";
				   chDOB += dob.charAt(9)+"";
				   Integer.parseInt(chDOB);
				   if(dob.charAt(2) != '-' && dob.charAt(5) != '-') {isError = true;}
			} catch (Exception e) {
				isError = true;
			}
			
			if(isError) {
				error = (Errors) context.getBean("customerContainsErrors");
				System.out.println(error.toString());
			}
			System.out.print("Start over? (Y)" /*Exit without save (E)"*/);
			input = myObj.nextLine();
			if(isError )input = "Y";
			else if(input.length() == 0) {
				customerId = customerService.saveCustomer(name,address,dob,email,phone);
				System.out.print("***SAVED***");
				input = "";
				return customerId;
			}
		}

		return -1;
	}

	private int createAccount(int custId, int accId) {

		int accountId = accId;
		
		String input = "Y";
		boolean isError = true;
		while (input.compareTo("Y") == 0 || input.compareTo("y") == 0) {
			System.out.println("\nCREATE NEW ACCOUNT");
			System.out.print("Enter the deposit     :");
			String deposit = myObj.nextLine();
			System.out.print("Enter overdraft limit :");
			String limit = myObj.nextLine();
			
			
			double iDeposit = -1; 
			double iLimit = -1;
			try {
				iDeposit = Double.parseDouble(deposit);
				iLimit = Double.parseDouble(limit);
			} catch (Exception e) {
				isError = true;
			}
			if(iDeposit >= 0 && iLimit >= 0)
				isError = false;
			
			System.out.println("\nYou ENTERED:\n"
					+   "Deposit        : "+deposit
					+ "\nOverdraft limit: "+limit
					+ "\n\nPress ENTER to SAVE");
			if(isError) {
				error = (Errors) context.getBean("customerContainsErrors");
				System.out.println(error.toString());
			}
			System.out.print( "Start over? (Y)"/* Exit without save (E)" */);
			input = myObj.nextLine();
			if(isError )input = "Y";
			else if(input.length() == 0) {
				
				accountId = accountService.saveAccount(iDeposit, iLimit);
				System.out.print("***SAVED***\n\n");
				return accountId;
			}
		}

		return -1;
	}
	
	private int joinCustomerWithAccount(int custId, int accID) {

		int customerId = custId;
		int accountID = accID;
		
		if(customerId > 0 && accountID > 0) {
			System.out.println("******JOINED*****");
			customerService.addCustomerToAccount(customerId,accountID);

			printCustomersWithAccount(accountID);
			printAccountsWithCustomer(customerId);
			myObj.nextLine();
			customerId = -1; accountID =-1;
		
			return -1;
		}
		if(accountID == -1 && customerId == -1) {
			System.out.println("JOIN CUSTOMER WITH ACCOUNT");
			System.out.println("ADD customer to the account (join account)(J)");
			System.out.println("ADD account to the customer (Multiple account)(M)");
			String input = myObj.nextLine();
			
			while(input.compareTo("J") == 0 || input.compareTo("j") == 0 || 
					   input.compareTo("M") == 0 || input.compareTo("m") == 0) {

				if(input.compareTo("J") == 0 || input.compareTo("j") == 0) {
					
					System.out.println("register New  Customer(N)");	
					System.out.println("FIND existing Account(F)");
					input = myObj.nextLine();
					if(input.compareTo("N") == 0 || input.compareTo("n") == 0) {
						customerId =  createCustomer(custId, accID);
					}else if(input.compareTo("F") == 0 || input.compareTo("f") == 0) {
							
						accountID = findAccount().getAcccountId();

						customerId = createAccount(customerId, accountID);
						return joinCustomerWithAccount(customerId, accountID);
					}
				}else if(input.compareTo("M") == 0 || input.compareTo("m") == 0) {
					System.out.println("create   New  Account (N)");
					System.out.println("FIND existing Account (F)");
					input = myObj.nextLine();
					if(input.compareTo("N") == 0 || input.compareTo("n") == 0) {
						accountID = createAccount(custId,accID);
					}else if(input.compareTo("F") == 0 || input.compareTo("f") == 0) {

						accountID = findAccount().getAcccountId();
						return joinCustomerWithAccount(customerId, accountID);
					}
				return -1;	
				}else {System.out.println("Cancel Exit to main"); return -1;}
			}
			
		}
		if(accountID == -1 && customerId > 0){   //TODO 
			System.out.println("JOIN CUSTOMER WITH ACCOUNT");
			System.out.print("Create New account?                              (N)\n"
							+"Add Customer to existing account? (Join Account) (A)");
			String input = myObj.nextLine();
			while(input.compareTo("N") == 0 || input.compareTo("A") == 0 || 
			   input.compareTo("n") == 0 || input.compareTo("a") == 0) {
		   
				if(input.compareTo("N") == 0 || input.compareTo("n") == 0) {
	
					accountID = createAccount(customerId, accountID);

				} else if(input.compareTo("A") == 0 || input.compareTo("a") == 0) {
					accountID = findAccount().getAcccountId();
					joinCustomerWithAccount(customerId, accountID);
					return -1;
				}
			}
		}
		
		if(accountID > 0 && customerId == -1){
			System.out.println("JOIN ACCOUNT WITH CUSTOMER (customer with more accounts)");
			System.out.println("   Register New User?  (R)\n"
							 + "   FIND existing User? (F)");
			String input = myObj.nextLine();
			while(input.compareTo("R") == 0 || input.compareTo("F") == 0 || 
			   input.compareTo("r") == 0 || input.compareTo("f") == 0) {
		   
				if(input.compareTo("R") == 0 || input.compareTo("r") == 0) {
					input = "";
					customerId = createCustomer(customerId,accountID);
					
				}else if(input.compareTo("F") == 0 || input.compareTo("f") == 0) {
					input = "";
					customerId = findCustomer();
					return joinCustomerWithAccount(customerId, accountID);
				}
			}
		}
		return -1;
	}
	
	private int findCustomer() {
		int customerId = -1;
		System.out.println("Find CUstomers by account number");
		System.out.println("Enter the account number");
		String input = myObj.nextLine();
		
		List<Customer>  customers = null;
		if(input.compareTo("") != 0)
			customers = customerService.getACustomerByItsAccountNo(input);
		if(customers != null) {
			for (Customer customer : customers) {
				System.out.println(customer.display());
			}
			if(customers.size() > 1) {
				System.out.print("Enter customer ID: ");
				input = myObj.nextLine();
				try {
					customerId = Integer.parseInt(input);
				} catch (Exception e) {
					System.out.println("****wrong input***");
					customerId = -1;
				}
			}else if (customers.size() == 1){ 
				customerId = customers.get(0).getCustomerId();
			}
		}else {
			error = (Errors) context.getBean("customerNotFound");
			System.out.println(error.toString());
		}
		
		if(customerId == -1) {
			System.out.println("Find Customers by Phone or D-O-B");
			System.out.println("Enter the phone number or DOB (DD-MM-YYYY): ");
			input = myObj.nextLine();
			if(input.compareTo("") != 0)
				customers = customerService.getACustomerByItsPhoneorDOB(input);
			if(customers != null) {
				for (Customer customer : customers) {
					System.out.println(customer.display());
				}
				if(customers.size() > 1) {
					System.out.print("Enter customer ID: ");
					input = myObj.nextLine();
					try {
						customerId = Integer.parseInt(input);
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println("****wrong input***");
						customerId = -1;
					}
				}else if (customers.size() == 1){ 
					customerId = customers.get(0).getCustomerId();
				}
			}else {
				error = (Errors) context.getBean("customerNotFound");
				System.out.println(error.toString());
			}
		}

		return customerId;
	}
	
	private String viewAccount() {
		Account account = findAccount();
		if(account == null) { 
			error = (Errors) context.getBean("accountNotFound");
			System.out.println(error.toString());
			
		}else {
			System.out.println(account.display());
		}
		System.out.print("Press enter to conttinue...");
		myObj.nextLine();
		return null;
	}

	private String widthdrawFromAccount() {
		// TODO Auto-generated method stub
		System.out.print("WIDTHDRAW MONEY FROM AN ACCOUNT\n");
		Account account = findAccount();
		if(account != null) {
			double maximium = (account.getAccountBalance()+account.getAccountLimit());
			//maximium = Math.round(maximium * 100.0) / 100.0;
			System.out.println(account.display());
			if(account.isAcountActive()) {
			System.out.print("Amount you like to widthdraw (maximum "+maximium+"): ");
			String amount = myObj.nextLine();
			double iAmount = -1.0;
			try {
				iAmount = Double.parseDouble(amount);
			} catch (Exception e) {
				System.out.println("***INVALID NUMBER***");
				myObj.nextLine();
				return null;
			}
			
				if(maximium >= iAmount) {
					iAmount = Math.round(iAmount * 100.0) / 100.0;

					System.out.println("Are you sure? (Y)");
					String input = myObj.nextLine();

					if(input.compareTo("Y") == 0 || input.compareTo("y") == 0) {

						accountService.widthdrawFromAccount(account,iAmount);
						System.out.println("***ACCOUNT DEBETED***");
						myObj.nextLine();
					}else {
						System.out.println("***TRANSACTION CANCELED***");
						myObj.nextLine();
					}
					
				}else {
					System.out.println("Insuficience founds");
					myObj.nextLine();
				}
			}else System.out.println("***ACCOUNT CLOSED***");
			myObj.nextLine();
		}else {
			System.out.println("***ACCOUNT NOT FOUND***");
			myObj.nextLine();
		}
		return null;
	}
	
	private String depositToAccount() {

		System.out.print("DEPOSIT MONEY TO ACCOUNT");
		Account account = findAccount();
		if(account != null) {
			System.out.println(account.display());
			if(account.isAcountActive()) {
			System.out.print("Amount you like to deposit: ");
			String amount = myObj.nextLine();
			double iAmount = -1.0;
			try {
				iAmount = Double.parseDouble(amount);
			} catch (Exception e) {
				System.out.println("***INVALID NUMBER***");
				myObj.nextLine();
				return null;
			}
			
				iAmount = Math.round(iAmount * 100.0) / 100.0;
				System.out.println("Will be lodged:"+ iAmount);
				System.out.println("Are you sure? (Y)");
				String input = myObj.nextLine();
				boolean isActive = true;;
				if(input.compareTo("Y") == 0 || input.compareTo("y") == 0) {
					isActive = accountService.lodgeMoneyToAccount(account,iAmount);
					if(isActive) System.out.println("***ACCOUNT LODGED***");
					else System.out.println("***ACCOUNT CLOSED***");
						
				}else {
					System.out.println("***TRANSACTION CANCELED***");
					myObj.nextLine();
				}

			}else{System.out.println("***ACCOUNT CLOSED***");
			myObj.nextLine();
			}
			
		}else {
			System.out.println("***ACCOUNT NOT FOUND***");
			myObj.nextLine();
		}
		return null;
	}
 
	private String transferMoney() {

		System.out.println("TRANSFER MONEY\nAccount number you transferint FROM");
		Account from = findAccount();
		if (from == null) {
			System.out.println("***ACCOUNT NOT FOUND***");
			myObj.nextLine();
			return null;
		} else {
		System.out.println("FROM:"+from.display());
		}
		Account to = findAccount();
		if (to == null) {
			System.out.println("***ACCOUNT NOT FOUND***");
			myObj.nextLine();
			return null;
		} else {
		System.out.println("Account number you transferint TO");
		System.out.println("TO  :"+to.display());
		}
		if(!from.isAcountActive() || !to.isAcountActive()) {
			System.out.println("***ACCOUNT IS CLOSED, CAN'T COMPLETE TRANSACTION***");
			myObj.nextLine();
		}
		double maximium = (from.getAccountBalance()+from.getAccountLimit());
		maximium = Math.round(maximium * 100.0) / 100.0;

		System.out.print("Amount You Like To transfer (maximum "+maximium+"): ");
		String amount = myObj.nextLine();
		double iAmount = -1.0;
		try {
			iAmount = Double.parseDouble(amount);
		} catch (Exception e) {
	
			System.out.println("***INVALID NUMBER***");
			myObj.nextLine();
			return null;
		}
		
			if(maximium >= iAmount) {
				iAmount = Math.round(iAmount * 100.0) / 100.0;

				System.out.println("FROM account: "+from.getAccountNumber()+" Will be transfered:"+ iAmount +" TO:" + to.getAccountNumber());
				System.out.println("Are you sure? (Y)");
				String input = myObj.nextLine();

				if(input.compareTo("Y") == 0 || input.compareTo("y") == 0) {
					accountService.transferFromToAccount(from,to,iAmount);
					System.out.println("***TRANSFER COMPLETED***");
					myObj.nextLine();
				}else {
					System.out.println("***TRANSACTION CANCELED***");
					myObj.nextLine();
				}
				
			}else {
				System.out.println("Insuficience founds");
				myObj.nextLine();
			}
		
		return null;
	}
	
	private Account findAccount() {
		System.out.print("Enter the account number:");
		String accountNumber = myObj.nextLine();
		return  accountService.findAccountbyAccountNumber(accountNumber);
	}
	
	private String closeAccount() {
		Account account = findAccount();
		if(account == null) { 
			error = (Errors) context.getBean("accountNotFound");
			System.out.println(error.toString());
			System.out.print("Press enter to back into the main menu...");
			myObj.nextLine();
			
		}else {
			System.out.println(account.display());
			System.out.print("really close this account? (Y)");
			String input = myObj.nextLine();
			if(input.compareTo("Y") == 0 || input.compareTo("y") == 0) {
				accountService.closeAccountByID(account.getAcccountId());
				System.out.println("***ACCOUNT CLOSED***");
			}	
		}
		System.out.print("Press enter to back into the main menu...");
		myObj.nextLine();
		
		return null;
	}
	
	private String bankInfo() {
		
		printAllAccountsWithCustomers();
		printAllCustomersWithAccounts();
		
		double totalMoney = accountService.getTotalMoneyInTheBank();
		totalMoney = Math.round(totalMoney * 100.0) / 100.0;
		int over10000 = accountService.getCountOver10000();
		System.out.println("\nBANK INFO\n"
						 + "Total amount of money deposited in the bank is €"+totalMoney
						 + "\nThe number of accounts with deposits over €10,000 is: "+over10000);
		myObj.nextLine();
		return null;
	}
	
	public static void main(String[] args) {
		new App();
	}
	
	@SuppressWarnings("unused")
	private void printAllCustomers() {
		System.out.println("\n===================================================================\n");
		List<Customer> customers = customerService.getAllCustomers();
		for(Customer customer: customers)
			System.out.println(customer.toString());
		System.out.println("\n===================================================================\n");
	}
	
	private void printAllCustomersWithAccounts() {
		System.out.println("\n===================================================================\n");
		List<Account> accounts = accountService.getAllAccounts();
		for(Account account: accounts) {
			System.out.println("\n"+account.display());
			List<Customer> customers = customerService.findAllCustomersWithAccounts(account.getAcccountId());
			
			for(Customer customer: customers) {
				System.out.println("    "+customer.display());
			}
		}	
	}
	private void printCustomersWithAccount(int accountId) {
		System.out.println("\n===================================================================\n");
		Account account = accountService.findAccountById(accountId);
		System.out.println("\n"+account.display());
		List<Customer> customers = customerService.findAllCustomersWithAccounts(accountId);
		
		for(Customer customer: customers) {
			System.out.println("    "+customer.display());
		}
		
	}
	private void printAccountsWithCustomer(int customerId) {
		System.out.println("\n===================================================================\n");
		Customer customer = customerService.getACustomerByItsId(customerId);
		System.out.println("\n"+customer.display());
		List<Account> accounts = accountService.findAllAccountsWithCustomers(customer.getCustomerId());
		
		for(Account account: accounts) {
			System.out.println("    "+account.display());
		}
			
	}	
	private void printAllAccountsWithCustomers() {
		System.out.println("\n===================================================================\n");
		List<Customer> customers = customerService.getAllCustomers();
		for(Customer customer: customers) {
			System.out.println("\n"+customer.display());
			List<Account> accounts = accountService.findAllAccountsWithCustomers(customer.getCustomerId());
			
			for(Account account: accounts) {
				System.out.println("    "+account.display());
			}
		}	
	}
	@SuppressWarnings("unused")
	private void printAllAccounts() {
		System.out.println("\n===================================================================\n");
		List<Account> accounts = accountService.getAllAccounts();
		for(Account account: accounts)
			System.out.println(account.toString());
		System.out.println("\n===================================================================\n");
	}
}
