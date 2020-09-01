package com.netBankingSample.testCases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.netBankingSample.pageObjects.LoginPage;

public class TC_LoginTest_001 extends BaseTest{
	
	@Test
	public void loginTest() throws IOException 
	{
		
		logger.info("URL is opened");
		
		LoginPage lp=new LoginPage(driver);
		lp.setUserName(getUserName());
		logger.info("Entered username");
		
		lp.setPassword(getPassword());
		logger.info("Entered password");
		
		lp.clickSubmit();
		logger.info("Submit button clicked");
		
		if(driver.getTitle().equals("Guru99 Bank Manager HomePage"))
		{
			Assert.assertTrue(true);
			logger.info("Login test passed");
		}
		else
		{
			captureScreen(driver,"loginTest");
			Assert.assertTrue(false);
			logger.info("Login test failed");
		}
	}

}
