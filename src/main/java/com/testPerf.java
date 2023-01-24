package com;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import Constants.UIPerfConstants;
import Model.UIPerfModel;
import reports.extentReports.UIPerfExtentReport;
import utilities.NavigationTimeHelper;

public class testPerf {

/*	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		
	  
	    
		NavigationTimeHelper navigationTimeHelper = new NavigationTimeHelper();
		
		System.setProperty("webdriver.chrome.driver", "C:\\SeleniumDrivers\\chromedriver.exe");
    	ChromeOptions options= new ChromeOptions();    	
    	options.setAcceptInsecureCerts(true);
    	options.addArguments("start-maximized"); // open Browser in maximized mode
    	options.addArguments("disable-infobars"); // disabling infobars
    	options.addArguments("--disable-extensions"); // disabling extensions
    	options.addArguments("--disable-gpu"); // applicable to windows os only
    	options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
    	options.addArguments("--no-sandbox"); // Bypass OS security model   	
    	WebDriver driver= new ChromeDriver(options);
    	driver.manage().deleteAllCookies();
    	String TestCaseName="TC001";
    	String ModuleName="Module";
    	String BrowserName="Chrome";
    	
    	LocalDateTime StartTime= LocalDateTime.now();
    	
    	UIPerfModel uiPerfModel = new UIPerfModel();
    	
    	driver.navigate().to("https://www.google.com/");
    	String timing=navigationTimeHelper.getAllTiming(driver);
    	uiPerfModel= uiPerfModel.AddUIPerfModel("HomePage", "GooglePage",  TestCaseName, ModuleName, BrowserName, StartTime, timing);

    	
    	UIPerfConstants uiPerfConstants = new UIPerfConstants();
    	uiPerfConstants.addUpdatePerfData("HomePage", "GooglePage", uiPerfModel);

    	driver.findElement(By.name("q")).sendKeys("test");
    	LocalDateTime StartTime2= LocalDateTime.now();

    	driver.findElement(By.cssSelector("center:nth-child(1) > .gNO89b")).click();

    	
    	UIPerfModel uiPerfModel2 = new UIPerfModel();
    	uiPerfModel2= uiPerfModel.AddUIPerfModel("GooglePage", "SearchPage",  TestCaseName, ModuleName, BrowserName, StartTime2, timing);


    	UIPerfConstants uiPerfConstants2 = new UIPerfConstants();
    	uiPerfConstants2.addUpdatePerfData("GooglePage", "SearchPage", uiPerfModel);

    	
    	
    	
    	UIPerfExtentReport perfextentReport =  new UIPerfExtentReport();
    	
    	
    	
    	
    	perfextentReport.CreateExtentReport_Category("C:\\Test\\ExtentReport", ModuleName, BrowserName, BrowserName, timing);
    	driver.quit();
	}*/

}
