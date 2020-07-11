package ie.michael.bank;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ie.michael.bank.mappers.Account;
import ie.michael.bank.services.AccountService;
import junit.framework.Assert;


@SuppressWarnings("deprecation")
@RunWith(JUnitPlatform.class)// assures runs as Junit
@ExtendWith(MockitoExtension.class)
public class Testing {
	
	
	@Test // import org.junit.jupiter.api.Test
	public void mytest1(){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		AccountService accountService = (AccountService) context.getBean("accountServiceImplementation");
		double crediting = 0.04;
		double real = 0.2*0.2;
		System.out.println("\nTEST 1\ncreating accout and crediting "+crediting+" (floating point error "+real+") \n");

		int accountId = accountService.saveAccount(real, 50);
		Account acount = accountService.findAccountById(accountId);
		double balance = acount.getAccountBalance();
		System.out.println(acount.display());
		
		System.out.println("\nThus balance must be also "+balance);
		Assert.assertEquals(balance,crediting);
		context.close();
	}
	
	@Test // import org.junit.jupiter.api.Test
	public void mytest2(){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		AccountService accountService = (AccountService) context.getBean("accountServiceImplementation");

		System.out.println("\nTEST 2\ncreating two new accounts and obtaining its numbers:");

		int 	accountId = accountService.saveAccount(99.99, 33.33);
		Account acount = accountService.findAccountById(accountId);
		int 	accountNo1 = acount.getAccountNumber();
		System.out.println(accountNo1);
				accountId = accountService.saveAccount(99.99, 33.33);
				acount = accountService.findAccountById(accountId);
		int 	accountNo2 = acount.getAccountNumber();
		System.out.println(accountNo2);
		System.out.println("The account numbers cannot be equal");
		Assert.assertTrue(accountNo1 != accountNo2);
		context.close();
	}
	

	@Test // import org.junit.jupiter.api.Test
	public void mytest3(){
			
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		AccountService accountService = (AccountService) context.getBean("accountServiceImplementation");
		String accNo = "100002";
		Account account = accountService.findAccountbyAccountNumber(accNo);
		System.out.println("\nTEST 3\nfrom given account No: "+accNo+"\n");
		System.out.println(account.display());
		double balanceBefore = account.getAccountBalance();
		
		double widthdraw = 1+account.getAccountBalance()+account.getAccountLimit();
		
		System.out.println("\ntrying to widthdraw "+widthdraw+ " which is 1 more then balance+limit");
		int status = accountService.widthdrawFromAccount(account, widthdraw);
		System.out.println("receiving status \"" +status + "\" which means insufficient founds");
		
		account = accountService.findAccountbyAccountNumber(accNo);
		System.out.println("\nWhen account No: "+accNo+" balance is checked after, it should be unchanged\n");
		System.out.println(account.display());
		double balanceAfter = account.getAccountBalance();
		
		Assert.assertEquals(balanceAfter, balanceBefore);
		context.close();

	}
	
	@Test // import org.junit.jupiter.api.Test
	public void mytest4(){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		AccountService accountService = (AccountService) context.getBean("accountServiceImplementation");
		String accNo = "100002";
		Account account = accountService.findAccountbyAccountNumber(accNo);
		accountService.closeAccountByID(account.getAcccountId());
		account = accountService.findAccountbyAccountNumber(accNo);
		System.out.println("\nTEST 4\nClosing acount No: "+accNo+"\n");
		accountService.closeAccountByID(account.getAcccountId());
		System.out.println(account.display());
		
		double balanceBefore = account.getAccountBalance();		
		double widthdraw = 1;
		
		System.out.println("\ntrying to widthdraw "+widthdraw);
		int status = accountService.widthdrawFromAccount(account, widthdraw);
		System.out.println("receiving status \"" +status + "\" which means account is closed");
		
		account = accountService.findAccountbyAccountNumber(accNo);
		System.out.println("\nWhen account No: "+accNo+" balance is checked after, it should be unchanged\n");
		System.out.println(account.display());
		double balanceAfter = account.getAccountBalance();
		
		Assert.assertEquals(balanceAfter, balanceBefore);
		context.close();
	}
	
	@Test // import org.junit.jupiter.api.Test
	public void mytest5(){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		AccountService accountService = (AccountService) context.getBean("accountServiceImplementation");
		String accNo = "100001";
		Account account = accountService.findAccountbyAccountNumber(accNo);
		accountService.closeAccountByID(account.getAcccountId());
		account = accountService.findAccountbyAccountNumber(accNo);
		System.out.println("\nTEST 5\nClosing acount No: "+accNo+"\n");
		accountService.closeAccountByID(account.getAcccountId());
		System.out.println(account.display());
		
		double balanceBefore = account.getAccountBalance();		
		double widthdraw = 1;
		
		System.out.println("\ntrying to lodge "+widthdraw);
		boolean status = accountService.lodgeMoneyToAccount(account, widthdraw);
		System.out.println("receiving status \"" +status + "\" which means account is closed");
		
		account = accountService.findAccountbyAccountNumber(accNo);
		System.out.println("\nWhen account No: "+accNo+" balance is checked after, it should be unchanged\n");
		System.out.println(account.display());
		double balanceAfter = account.getAccountBalance();
		
		Assert.assertEquals(balanceAfter, balanceBefore);
		context.close();
	}
	
	@Mock
	Account account;
	@Test
	public void myTest6() {
		System.out.println("TEST 6\nAn account should be set just once");
		account.setAcccountId(1);
		verify(account, times(1)).setAcccountId(1);;
	}
}