package com.netBankingSample.testCases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.netBankingSample.pageObjects.AddCustomerPage;
import com.netBankingSample.pageObjects.LoginPage;

public class TC_AddCustomerTest_003 extends BaseTest {
	
	@Test
	public void addNewCustomer() throws InterruptedException, IOException
	{
		LoginPage lp=new LoginPage(driver);
		lp.setUserName(getUserName());
		logger.info("User name is provided");
		lp.setPassword(getPassword());
		logger.info("Passsword is provided");
		lp.clickSubmit();
		
		Thread.sleep(3000);
		
		AddCustomerPage addcust=new AddCustomerPage(driver);
		
		addcust.clickAddNewCustomer();
		
		logger.info("providing customer details....");
		
		
		addcust.custName("Amit");
		addcust.custgender("male");
		addcust.custdob("06","21","1991");
		Thread.sleep(5000);
		addcust.custaddress("INDIA");
		addcust.custcity("Noida");
		addcust.custstate("UP");
		addcust.custpinno("201009");
		addcust.custtelephoneno("1234567890");
		
		String email=randomestring()+"@gmail.com";
		addcust.custemailid(email);
		addcust.custpassword("abcdef");
		Thread.sleep(3000);
		addcust.custsubmit();
		
		Thread.sleep(3000);
		
		logger.info("validation started....");
		
		boolean res=driver.getPageSource().contains("Customer Registered Successfully!!!");
		
		if(res==true)
		{
			Assert.assertTrue(true);
			logger.info("test case passed....");
			
		}
		else
		{
			logger.info("test case failed....");
			captureScreen(driver,"addNewCustomer");
			Assert.assertTrue(false);
		}
			
	}

}
