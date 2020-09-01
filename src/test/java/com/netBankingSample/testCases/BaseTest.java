package com.netBankingSample.testCases;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import com.netBankingSample.utilities.ExcelUtils;
import com.netBankingSample.utilities.ReadConfig;

public class BaseTest {
	
	ReadConfig readConfig = new ReadConfig();
	private String baseURL = readConfig.getApplicationURL();
	private String userName = readConfig.getUsername();
	private String password = readConfig.getPassword();
	public static WebDriver driver;
	public static Logger logger;
	
	
	/*
	 * @BeforeClass public void setUp() {
	 * System.out.println(System.getProperty("user.dir"));
	 * //System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")
	 * +"//Drivers//chromedriver.exe");
	 * System.setProperty("webdriver.chrome.driver",readConfig.getChromePath());
	 * driver = new ChromeDriver(); logger = Logger.getLogger("netBankingSample");
	 * PropertyConfigurator.configure("Log4j.properties"); }
	 */
	
	@Parameters("browser")
	@BeforeClass
	public void setup(String br)
	{			
		logger = Logger.getLogger("ebanking");
		PropertyConfigurator.configure("Log4j.properties");
		
		if(br.equals("chrome"))
		{
			System.setProperty("webdriver.chrome.driver",readConfig.getChromePath());
			driver=new ChromeDriver();
		}
		else if(br.equals("firefox"))
		{
			System.setProperty("webdriver.gecko.driver",readConfig.getFirefoxPath());
			driver = new FirefoxDriver();
		}
		else if(br.equals("ie"))
		{
			System.setProperty("webdriver.ie.driver",readConfig.getIEPath());
			driver = new InternetExplorerDriver();
		}
		
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(baseURL);
	}
	
	@AfterClass
	public void tearDown() 
	{
		driver.quit();
	}
	
	public void captureScreen(WebDriver driver, String tname) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File target = new File(System.getProperty("user.dir") + "/Screenshots/" + tname + ".png");
		FileUtils.copyFile(source, target);
		System.out.println("Screenshot taken");
	}
	
	public boolean isAlertPresent() //user defined method created to check alert is present or not
	{
		try
		{
		driver.switchTo().alert();
		return true;
		}
		catch(NoAlertPresentException e)
		{
			return false;
		}
		
	}
	
	@DataProvider(name="LoginData")
	String [][] getData() throws IOException
	{
		String path=System.getProperty("user.dir")+"/src/test/java/com/netBankingSample/testData/LoginData.xlsx";
		
		int rownum=ExcelUtils.getRowCount(path, "Sheet1");
		int colcount=ExcelUtils.getCellCount(path,"Sheet1",1);
		
		String logindata[][]=new String[rownum][colcount];
		
		for(int i=1;i<=rownum;i++)
		{
			for(int j=0;j<colcount;j++)
			{
				logindata[i-1][j]=ExcelUtils.getCellData(path,"Sheet1", i,j);//1 0
			}
				
		}
	return logindata;
	}
	
	public String randomestring()
	{
		String generatedstring=RandomStringUtils.randomAlphabetic(8);
		return(generatedstring);
	}
	
	public static String randomeNum() {
		String generatedString2 = RandomStringUtils.randomNumeric(4);
		return (generatedString2);
	}
	
	public String getURL() 
	{
		return baseURL;
	}
	
	public String getUserName() 
	{
		return userName;
	}
	
	public String getPassword() 
	{
		return password;
	}

}
