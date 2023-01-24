package KeywordDriverUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

//import APIHelpers.APIController;
import Common.POI_ReadExcel;
//import Constants.LoginConstants;
//import DBUtilities.MSSQLUtilities;
import KeywordDriverUtil.Reports.ReportUtilities;
import KeywordDriverUtil.Reports.ReportData.Status;
import Model.KeywordDriven.KeywordModel;
import Model.KeywordDriven.KeywordTestScenarioModel;
//import Models.APIReportModel;
//import TestSettings.APITestSettings;
//import TestSettings.InitializeAPISettings;
import TestSettings.KeywordTestSettings;
import TestSettings.TestRunSettings;
import Constants.UIPerfConstants;
import Model.UIPerfModel;
import utilities.NavigationTimeHelper;

public class KeywordLibrary extends WebDriverHelper {
	DriverSession sessionManager = new DriverSession();
//	ReusableFunctions reusableFunctions = new ReusableFunctions();
//	MSSQLUtilities dbUtil = new MSSQLUtilities();
	public KeywordLibrary()
	{
		
	}
	
	public WebElement findElementByType(WebDriver driver,KeywordModel keywordModel) {
		keywordModel.inputXPath = "";
		keywordModel.optionXPath = "";
		WebElement object = null;
		String identifier, actualObjectID = "";
		identifier = keywordModel.objectID.split(":")[0].trim();
		if (keywordModel.objectID.split(":").length > 2) {
			for (int z = 1; z < (keywordModel.objectID.split(":").length); z++) {
				if (z != 1) {
					actualObjectID = actualObjectID + ":" + keywordModel.objectID.split(":")[z].trim();
				} else if (z == 1) {
					actualObjectID = actualObjectID + keywordModel.objectID.split(":")[z].trim();
				}
			}
		} else {
			actualObjectID = keywordModel.objectID.split(":")[1].trim();
		}

		if (actualObjectID.contains("#getMappingID=")) {
			String[] splittedString = actualObjectID.split("#");

			String finalObjectID = "";
			for (int i = 0; i < splittedString.length; i++) {
				if (splittedString[i].startsWith("getMappingID=")) {
					splittedString[i] = KeywordUtilities.getObjectMappingSheet((splittedString[i]).split("getMappingID=")[1]);
				}
				finalObjectID = finalObjectID + splittedString[i];
			}
			actualObjectID = finalObjectID;
		} else if (actualObjectID.contains("#getData=")) {
			String[] splittedString = actualObjectID.split("#");
			String finalObjectID = "";
			for (int i = 0; i < splittedString.length; i++) {
				if (splittedString[i].startsWith("getData=")) {
					try {
						splittedString[i] = KeywordUtilities.getData(
								driver, 
								keywordModel.testCaseFileName, 
								keywordModel.testCase, 
								(splittedString[i]).split("getData=")[1],
								keywordModel.currentIteration);

								
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				finalObjectID = finalObjectID + splittedString[i];
			}
			actualObjectID = finalObjectID;

		}

		object = getWebElement(driver,identifier, actualObjectID);
		keywordModel.inputXPath = actualObjectID;
		keywordModel.optionXPath = actualObjectID;
		// System.out.println("Object ID is "+keywordModel.inputXPath);
		return object;

	}

	
	public static List findElementsByType(WebDriver driver, KeywordModel keywordModel) {
		keywordModel.inputXPath = "";
		keywordModel.optionXPath = "";
		List<WebElement> object = null;
		try {

			String identifier, actualObjectID = "";
			// System.out.println("ObjectID: " + Driver.objectID);
			identifier = keywordModel.objectID.split(":")[0].trim();
			// System.out.println("Identifier: " + identifier);
			if (keywordModel.objectID.split(":").length > 2) {
				for (int z = 1; z < (keywordModel.objectID.split(":").length); z++) {
					if (z != 1) {
						actualObjectID = actualObjectID + ":" + keywordModel.objectID.split(":")[z].trim();
					} else if (z == 1) {
						actualObjectID = actualObjectID + keywordModel.objectID.split(":")[z].trim();
					}
				}
			} else {
				actualObjectID = keywordModel.objectID.split(":")[1].trim();
			}

			if (actualObjectID.contains("#getMappingID=")) {
				String[] splittedString = actualObjectID.split("#");

				String finalObjectID = "";
				for (int i = 0; i < splittedString.length; i++) {
					if (splittedString[i].startsWith("getMappingID=")) {
						splittedString[i] = KeywordUtilities.getObjectMappingSheet((splittedString[i]).split("getMappingID=")[1]);
					}
					finalObjectID = finalObjectID + splittedString[i];
				}
				actualObjectID = finalObjectID;

			} else if (actualObjectID.contains("#getData=")) {
				String[] splittedString = actualObjectID.split("#");
				String finalObjectID = "";
				for (int i = 0; i < splittedString.length; i++) {
					if (splittedString[i].startsWith("getData=")) {
						try {
							splittedString[i] =  KeywordUtilities.getData(
									driver, 
									keywordModel.testCaseFileName, 
									keywordModel.testCase, 
									(splittedString[i]).split("getData=")[1],
									keywordModel.currentIteration);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					finalObjectID = finalObjectID + splittedString[i];
				}
				actualObjectID = finalObjectID;
			}

			object = getWebElementList(driver,identifier, actualObjectID);
			keywordModel.inputXPath = actualObjectID;
			keywordModel.optionXPath = actualObjectID;
			System.out.println("FindByelements...." + actualObjectID);
			return object;
		} catch (NullPointerException e) {
			//
			return object;

		}

	}


	private static WebElement getWebElement(WebDriver driver,String identifier, String elementId) {
		By bySelector = getSelector(identifier, elementId);
		return driver.findElement(bySelector);
	}

	private static List<WebElement> getWebElementList(WebDriver driver,String identifier, String elementId) {
		By bySelector = getSelector(identifier, elementId);
		return driver.findElements(bySelector);
	}

	private static By getSelector(String identifier, String elementId) {
		By bySelector = null;
		if (identifier.equalsIgnoreCase("id")) {
			bySelector = By.id(elementId);
		} else if (identifier.equalsIgnoreCase("xpath")) {
			bySelector = By.xpath(elementId);
		} else if (identifier.equalsIgnoreCase("className")) {
			bySelector = By.className(elementId);
		} else if (identifier.equalsIgnoreCase("name")) {
			bySelector = By.name(elementId);
		} else if (identifier.equalsIgnoreCase("css")) {
			bySelector = By.cssSelector(elementId);
		} else if (identifier.equalsIgnoreCase("linkText")) {
			bySelector = By.linkText(elementId);
		} else if (identifier.equalsIgnoreCase("partialLinkText")) {
			bySelector = By.partialLinkText(elementId);
		}
		return bySelector;
	}

	
//	public  void click(WebDriver driver, KeywordModel keywordModel ) {
//		if (keywordModel.dynaElement != null) {
//			try {
//				if (keywordModel.dynaElement.isDisplayed()) {
//					keywordModel.dynaElement.click();
//					ReportUtilities.Log(driver,"Clicking on the Element " + keywordModel.objectName, "Clicked on the object", Status.PASS , keywordModel);
//				}
//			} catch (NoSuchElementException p) {
//				keywordModel.error = true;
//				keywordModel.keywordModel.displayError = true;
//				ReportUtilities.Log(driver,"Cannot Click on the object.", "The Element " + keywordModel.objectName
//						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
//				throw new RuntimeException(p);
//			}
//		} else {
//			try {
//				if (findElementByType(driver,keywordModel).isDisplayed()) {
//					findElementByType(driver,keywordModel).click();
//					ReportUtilities.Log(driver,"Clicking on the Element " + keywordModel.objectName, "Clicked on the object", Status.PASS , keywordModel);
//				}
//			} catch (NoSuchElementException p) {
//				keywordModel.error = true;
//				keywordModel.keywordModel.displayError = true;
//				ReportUtilities.Log(driver,"Cannot Click on the object.", "The Element " + keywordModel.objectName
//						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
//				throw new RuntimeException(p);
//			}
//		}
//	}
//
//	

	
	/**
	 * 
	 * Method Name: ScrollToElement Description:
	 * This method scrolls to an element present on screen
	 */
	public void scrollToElement(WebDriver driver, KeywordModel keywordModel) 
	{
		WebElement elementSelected= findElementByType(driver, keywordModel);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementSelected);
	}
	
	/**
	 * Method Name: Clear_text Return Type: Nothing Description: This method get the
	 * Web-element text and remove the specified text.
	 */
	public void clear_text(WebDriver driver, KeywordModel keywordModel) {

		try {
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			jsExecutor.executeScript(
					"$(document.querySelector('#HtmlCommentsText')).data(\'kendoEditor\').value('Deleted for Automation')");

			ReportUtilities.Log(driver,"Clear the text and Entering text in the text box " + keywordModel.objectName,
					"Entered the text " + keywordModel.dataValue, Status.PASS , keywordModel);
		} catch (NoSuchElementException p) {
			keywordModel.error = true;
			keywordModel.displayError = true;
			System.out.println(p);
			ReportUtilities.Log(driver,"Cannot Clear the text enter text on the object.",
					"The Element " + keywordModel.objectName + " is  Not displayed on the current screen" + keywordModel.ScreenName,
					Status.FAIL , keywordModel);
		}
	}


	/**
	 * Method Name: clear Return Type: Nothing Description: This method clears
	 * the text in the field
	 */
	public void clear(WebDriver driver, KeywordModel keywordModel) {
		if (keywordModel.dynaElement != null) {
			try {
				if (keywordModel.dynaElement.isDisplayed()) {
					keywordModel.dynaElement.click();
					keywordModel.dynaElement.sendKeys(Keys.CONTROL + "a");
					keywordModel.dynaElement.sendKeys(Keys.DELETE); 
				
					ReportUtilities.Log(driver,"Entering text in the text box " + keywordModel.objectName,
							"Entered the text " + keywordModel.dataValue, Status.PASS , keywordModel);
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot enter text on the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		} else {
			try {
				if (findElementByType(driver, keywordModel).isDisplayed()) {
					findElementByType(driver, keywordModel).click();
					findElementByType(driver, keywordModel).sendKeys(Keys.CONTROL + "a");
					findElementByType(driver, keywordModel).sendKeys(Keys.DELETE); 
				

					ReportUtilities.Log(driver,"Entering text in the text box " + keywordModel.objectName,
							"Entered the text " + keywordModel.dataValue, Status.PASS , keywordModel);
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot enter text on the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		}
	}



	/**
	 * Method Name: enter_text Return Type: Nothing Description: This method enters
	 * the text specified in KeyInData column of Test case
	 */
	public void enter_text(WebDriver driver, KeywordModel keywordModel) {
		if (keywordModel.dynaElement != null) {
			try {
				if (keywordModel.dynaElement.isDisplayed()) {
					// keywordModel.dynaElement.click();
					// keywordModel.dynaElement.sendKeys(Keys.CONTROL + "a");
					// keywordModel.dynaElement.sendKeys(Keys.DELETE); 
				//	Driver.dynaElement.clear();
					keywordModel.dynaElement.clear();
					//	Thread.sleep(200);
					keywordModel.dynaElement.sendKeys(keywordModel.dataValue);
					ReportUtilities.Log(driver,"Entering text in the text box " + keywordModel.objectName,
							"Entered the text " + keywordModel.dataValue, Status.PASS , keywordModel);
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot enter text on the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		} else {
			try {
				if (findElementByType(driver, keywordModel).isDisplayed()) {
				//	findElementByType(driver, keywordModel).click();
				//	findElementByType(driver, keywordModel).sendKeys(Keys.CONTROL + "a");
				//	findElementByType(driver, keywordModel).sendKeys(Keys.DELETE); 
					findElementByType(driver, keywordModel).clear();
					//	Thread.sleep(200);
					findElementByType(driver, keywordModel).sendKeys(keywordModel.dataValue);

					ReportUtilities.Log(driver,"Entering text in the text box " + keywordModel.objectName,
							"Entered the text " + keywordModel.dataValue, Status.PASS , keywordModel);
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot enter text on the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		}
	}

	public void enter(WebDriver driver, KeywordModel keywordModel) throws InterruptedException {
		if (keywordModel.dynaElement != null) {
			try {
				if (keywordModel.dynaElement.isDisplayed()) {
					keywordModel.dynaElement.click();
					//Driver.dynaElement.sendKeys(Keys.CONTROL + "a");
					//Driver.dynaElement.sendKeys(Keys.DELETE); 
					//Driver.dynaElement.clear();
					keywordModel.dynaElement.clear();
					keywordModel.dynaElement.click();
					//	Thread.sleep(200);
					keywordModel.dynaElement.sendKeys(keywordModel.dataValue);
					ReportUtilities.Log(driver,"Entering text in the text box " + keywordModel.objectName,
							"Entered the text " + keywordModel.dataValue, Status.PASS , keywordModel);
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot enter text on the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		} else {
			try {
				if (findElementByType(driver, keywordModel).isDisplayed()) {
					findElementByType(driver, keywordModel).click();
				//	findElementByType().sendKeys(Keys.CONTROL + "a");
				//	findElementByType().sendKeys(Keys.DELETE); 
					findElementByType(driver, keywordModel).clear();
					findElementByType(driver, keywordModel).click();
					//	Thread.sleep(200);
					findElementByType(driver, keywordModel).sendKeys(keywordModel.dataValue);

					ReportUtilities.Log(driver,"Entering text in the text box " + keywordModel.objectName,
							"Entered the text " + keywordModel.dataValue, Status.PASS , keywordModel);
					
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot enter text on the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		}
	}
	
	
	
	/*
	 * Method Name: enter_RandomName Return Type: Nothing Description: This method
	 * enters the Random text with the length of the value that is mentioned in
	 * KeyInData column of Test case in the field specified as ObjectId in
	 * TestScript sheet
	 */

	public void enter_RandomName(WebDriver driver, KeywordModel keywordModel) throws InterruptedException {


		if (keywordModel.dynaElement != null) {
			try {
				if (keywordModel.dynaElement.isDisplayed()) {
					keywordModel.dynaElement.clear();
					String Chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
					StringBuilder builder = new StringBuilder();
					int len = Integer.parseInt(keywordModel.dataValue);
					while (len-- != 0) {
						int character = (int) (Math.random() * Chars.length());
						builder.append(Chars.charAt(character));
					}
					String str = builder.toString();
					// str = "Automation"+str;
					keywordModel.dynaElement.sendKeys(str);
					ReportUtilities.Log(driver,"Entering text in the text box " + keywordModel.objectName, "Entered the text " + str,
							Status.PASS , keywordModel);
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot enter text on the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}

		} else {
			try {
				if (findElementByType(driver, keywordModel).isDisplayed()) {
					findElementByType(driver, keywordModel).clear();
					String Chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
					StringBuilder builder = new StringBuilder();
					int len = Integer.parseInt(keywordModel.dataValue);
					while (len-- != 0) {
						int character = (int) (Math.random() * Chars.length());
						builder.append(Chars.charAt(character));
					}
					String str = builder.toString();
					// str = "Automation"+str;
					findElementByType(driver, keywordModel).sendKeys(str);
					ReportUtilities.Log(driver,"Entering text in the text box " + keywordModel.objectName, "Entered the text " + str,
							Status.PASS , keywordModel);
				}

			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot enter text on the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		}
	}

	/*
	 * Method Name: enter_RandomAddress Return Type: Nothing Description: This method
	 * enters the Random text with the length of the value that is mentioned in
	 * KeyInData column of Test case in the field specified as ObjectId in
	 * TestScript sheet
	 */

	public void enter_RandomAddress(WebDriver driver, KeywordModel keywordModel) throws InterruptedException {

		String Auto_text = "Auto";
		if (keywordModel.dynaElement != null) {
			try {
				if (keywordModel.dynaElement.isDisplayed()) {
					keywordModel.dynaElement.clear();
					
					String Chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
					StringBuilder builder = new StringBuilder();
					int len = Integer.parseInt(keywordModel.dataValue);
					while (len-- != 0) {
						int character = (int) (Math.random() * Chars.length());
						builder.append(Chars.charAt(character));
					}
					String str = builder.toString();
					str = Auto_text+str;
					keywordModel.dynaElement.sendKeys(str);
					ReportUtilities.Log(driver,"Entering text in the text box " + keywordModel.objectName, "Entered the text " + str,
							Status.PASS , keywordModel);
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot enter text on the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}

		} else {
			try {
				if (findElementByType(driver, keywordModel).isDisplayed()) {
					findElementByType(driver, keywordModel).clear();
					String Chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
					StringBuilder builder = new StringBuilder();
					int len = Integer.parseInt(keywordModel.dataValue);
					while (len-- != 0) {
						int character = (int) (Math.random() * Chars.length());
						builder.append(Chars.charAt(character));
					}
					String str = builder.toString();
					str = Auto_text+str;
					findElementByType(driver, keywordModel).sendKeys(str);
					ReportUtilities.Log(driver,"Entering text in the text box " + keywordModel.objectName, "Entered the text " + str,
							Status.PASS , keywordModel);
				}

			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot enter text on the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		}
	}

	
	/**
	 * Method Name: enter_RandomName Return Type: Nothing Description: This method
	 * enters the Random text with the length of the value that is mentioned in
	 * KeyInData column of Test case in the field specified as ObjectId in
	 * TestScript sheet
	 */

	public void enter_RandomSSN1(WebDriver driver, KeywordModel keywordModel) {

	//	findElementByType().clear();
		String Chars = "012345";
		StringBuilder builder = new StringBuilder();
		int len = Chars.length();
		System.out.println(keywordModel.dataValue);
		builder.append(Integer.parseInt(keywordModel.dataValue));
		while (len-- != 0) {
			int character = (int) (Math.random() * Chars.length());
			if (character == 0) {
				do {
					System.out.println("0 comes  :"+character);
					character = (int) (Math.random() * Chars.length());
					System.out.println(" After changing  :"+character);
				}while (character== 0); 
			}
			builder.append(Chars.charAt(character));
			System.out.println("ssn " + builder.toString());
		}
		findElementByType(driver, keywordModel).click();
		findElementByType(driver, keywordModel).sendKeys(builder.toString());
		System.out.println("SSN given in Individual info is" + builder.toString());
		ReportUtilities.Log(driver,"Entering text in the text box " + keywordModel.objectName, "Entered the text " + keywordModel.dataValue,
				Status.PASS , keywordModel);
	}
	
	
	public void enter_RandomSSN(WebDriver driver, KeywordModel keywordModel) {
		findElementByType(driver, keywordModel).clear();
		String Chars = "0123456789";
		StringBuilder builder = new StringBuilder();
		int len = Integer.parseInt(keywordModel.dataValue);
		while (len-- != 0) {
			int character = (int) (Math.random() * Chars.length());
			if (len == 8 && character == 9) { // If first digit is 9 then again genarating Random number
				System.out.println("First nine value" + character);
				while (character == 9) {
					character = (int) (Math.random() * Chars.length());
				}
			}
			builder.append(Chars.charAt(character));
			System.out.println("ssn " + builder.toString());
		}
		findElementByType(driver, keywordModel).sendKeys(builder.toString());
		System.out.println("SSN given in Individual info is" + builder.toString());
		ReportUtilities.Log(driver,"Entering text in the text box " + keywordModel.objectName, "Entered the text " + keywordModel.dataValue,
				Status.PASS , keywordModel);
	}

	/**
	 * Method Name: enter_decode_text Return Type: Nothing Description: This method
	 * enters the text specified in KeyInData column of Test case
	 * 
	 * @throws Exception
	 */
	public void enter_decode_text(WebDriver driver, KeywordModel keywordModel) throws Exception {
		findElementByType(driver, keywordModel).clear();
		String res2 = decode(keywordModel.dataValue);
		findElementByType(driver, keywordModel).sendKeys(res2);
		//		System.out.println( res1 + " string --> "  + res2);
		ReportUtilities.Log(driver,"Entering text in the text box " + keywordModel.objectName, "Entered the text " + res2, Status.PASS , keywordModel);
	}

	public static String decode(String value) throws Exception {
		byte[] decodedValue = Base64.getDecoder().decode(value);
		return new String(decodedValue, StandardCharsets.UTF_8);
	}

	/**
	 * Method Name: jsEnterText Return Type: Nothing Description: Same as
	 * enter_text() keyword except that this keyword uses javascript to exter text.
	 * The object id must be specified using ID selector
	 */
	public void enter1(WebDriver driver, KeywordModel keywordModel) {
		WebElement webElementObjectId = driver.findElement(By.xpath(keywordModel.objectID.split(":")[1]));
		String dataValue = keywordModel.dataValue;
		findElementByType(driver, keywordModel).click();
		JavascriptExecutor js = (JavascriptExecutor)driver;

		js.executeScript("arguments[0].value='"+dataValue+"';", webElementObjectId);
		ReportUtilities.Log(driver,"Entering text(JS) in the text box " + keywordModel.objectName, "Entered the text " + keywordModel.dataValue,
				Status.PASS , keywordModel);
	}
	
	
	
	
	
	/**
	 * Method Name: selectByVisibleText Return Type: Nothing Description: This
	 * method selects the visible text specified in KeyInData column of Test case in
	 * a select box
	 * @throws InterruptedException 
	 */
	public  void selectByVisibleText(WebDriver driver, KeywordModel keywordModel) throws InterruptedException {
		if (keywordModel.dynaElement != null) {
			try {
				if (keywordModel.dynaElement.isDisplayed()) {
					findElementByType(driver, keywordModel).click();
					Thread.sleep(1000);
					driver.findElement(By.xpath("//mat-option/span[contains(text(),'" + keywordModel.dataValue + "')]")).click();
					//Select selectBox = new Select(keywordModel.dynaElement);
					//selectBox.selectByVisibleText(keywordModel.dataValue);
					ReportUtilities.Log(driver,"Selecting by visible text in the dropdown " + keywordModel.objectName,
							"Selected the value " + keywordModel.dataValue + " in the select box ", Status.PASS , keywordModel);
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot select any value on the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		} else {
			try {
				if (findElementByType(driver, keywordModel).isDisplayed()) {
					//Select selectBox = new Select(findElementByType(driver, keywordModel));
					//selectBox.selectByVisibleText(keywordModel.dataValue);
					findElementByType(driver, keywordModel).click();
					Thread.sleep(1000);
					//driver.findElement(By.xpath("@id='"+keywordModel.objectID+"'")).click();
					driver.findElement(By.xpath("//mat-option/span[contains(text(),'" + keywordModel.dataValue + "')]")).click();
					ReportUtilities.Log(driver,"Selecting by visible text in the dropdown " + keywordModel.objectName,
							"Selected the value " + keywordModel.dataValue + " in the select box ", Status.PASS , keywordModel);
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot select any value on the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		}
	}
		
	public  void selectByVisibleTextKendoUI(WebDriver driver, KeywordModel keywordModel) throws InterruptedException {

        if (keywordModel.dynaElement != null) {
        	keywordModel.inputXPath = keywordModel.inputXPath + "/preceding-sibling::span/child::input";
               System.out.println("XPath to inputbox: " + keywordModel.inputXPath);
               try {
                     WebElement selectInputBox = driver.findElement(By.xpath(keywordModel.inputXPath));
                     if (selectInputBox.isDisplayed()) {
                            //                               Select selectBox = new Select(keywordModel.dynaElement);  
                            //                               selectBox.selectByVisibleText(keywordModel.dataValue);
                            selectInputBox.click();
                            //Thread.sleep(1000);
                            selectInputBox.sendKeys(Keys.CONTROL + "a");
                            selectInputBox.sendKeys(Keys.DELETE); 
                            //selectInputBox.clear();

                            selectInputBox.sendKeys(keywordModel.dataValue);
                            Thread.sleep(500);
                            selectInputBox.sendKeys(Keys.TAB);

                            ReportUtilities.Log(driver,"Selecting by visible text in the dropdown " + keywordModel.objectName,
                                         "Selected the value " + keywordModel.dataValue + " in the select box ", Status.PASS , keywordModel);
                     }
               } catch (NoSuchElementException p) {
                     keywordModel.error = true;
                     keywordModel.displayError = true;
                     ReportUtilities.Log(driver,"Cannot select any value on the object.", "The input box for " + keywordModel.objectName
                                  + " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
                     throw new RuntimeException(p);
               }
        } else  {
               findElementByType(driver, keywordModel);
               String ddXpath = keywordModel.inputXPath;
               int ddCount=0;
               int ddPosition = 0;
               int selectedDDCount=0;
               keywordModel.inputXPath = keywordModel.inputXPath + "/preceding-sibling::span/child::input";
               String optionList = ddXpath + "/option";
               List<WebElement> ddOptions = driver.findElements(By.xpath(optionList));
               ddCount = ddOptions.size();
               try {
                     WebElement selectInputBox = driver.findElement(By.xpath(keywordModel.inputXPath));
                     if (selectInputBox.isDisplayed()) {
                            //                         Select selectBox = new Select(findElementByType(driver, keywordModel)); 
                            //                         selectBox.selectByVisibleText(keywordModel.dataValue);
                     
                                   selectInputBox.click();
                                   selectInputBox.sendKeys(Keys.CONTROL + "a");
                                   selectInputBox.sendKeys(Keys.DELETE); 
                                   selectInputBox.clear();
                                   selectInputBox.sendKeys(keywordModel.dataValue);
                                   Thread.sleep(300);
                                   String selectedOptionList = ddXpath + "/option";
                                   List<WebElement> selectedDDOptions = driver.findElements(By.xpath(selectedOptionList));
                                   selectedDDCount = selectedDDOptions.size();       
                           
                           System.out.println(selectedDDCount);
                           if(selectedDDCount==1)
                           {
                        	   Thread.sleep(200);
                               selectInputBox.sendKeys(Keys.ARROW_DOWN);
                               selectInputBox.sendKeys(Keys.TAB);
                           } 
                           
                           
                            else
                            {      
	                                 selectInputBox.click();
	                                 selectInputBox.sendKeys(Keys.ENTER);
	                          
		                           while(ddCount!=0 && (!selectInputBox.getAttribute("value").equalsIgnoreCase(keywordModel.dataValue)))
		                           {
		                                   
		                                       selectInputBox.sendKeys(Keys.ARROW_DOWN);
					                           if(selectInputBox.getAttribute("value").equalsIgnoreCase(keywordModel.dataValue))
					                                   {
					                                         break; 
					                                   }      
					                                   ddCount--;
					               }
                           }
                           
                            
                            
                            ReportUtilities.Log(driver,"Selecting by visible text in the dropdown " + keywordModel.objectName,
                                         "Selected the value " + keywordModel.dataValue + " in the select box ", Status.PASS , keywordModel);
                     }
               }      
               
               catch (NoSuchElementException p) {
                     keywordModel.error = true;
                     keywordModel.displayError = true;
                     ReportUtilities.Log(driver,"Cannot select any value on the object.", "The input box for " + keywordModel.objectName
                                  + " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
                     throw new RuntimeException(p);
               }
        }
  }


	public  void verifyDropdownText(WebDriver driver, KeywordModel keywordModel) {
		Select selectBox = new Select(findElementByType(driver, keywordModel));
		// List<WebElement> Array = selectBox.getOptions();
		// String StringText= Array.get(0).getText();
		String StringText = selectBox.getFirstSelectedOption().getText();
		// System.out.println(StringText);
		if (keywordModel.checkBoolean.equalsIgnoreCase("")) {

			if (StringText.equals(keywordModel.dataValue))
				// if(findElementByType(driver, keywordModel).getText().equals(keywordModel.dataValue))
			{
				ReportUtilities.Log(driver,"Verifying the Text of the object " + keywordModel.objectName,
						"The element " + keywordModel.objectName + " has the text " + keywordModel.dataValue, Status.PASS , keywordModel);
			} else if (StringText.equals(keywordModel.sessionid)) {
				ReportUtilities.Log(driver,"Verifying the Text of the object " + keywordModel.objectName,
						"The element " + keywordModel.objectName + " has the text " + keywordModel.sessionid, Status.PASS , keywordModel);
			} else {
				ReportUtilities.Log(driver,"Verifying the Text of the object " + keywordModel.objectName,
						"The element " + keywordModel.objectName + " does not have the text " + keywordModel.dataValue,
						Status.FAIL , keywordModel);
			}
		}

	}
	
	
	
	
	/**
	 * Method Name: selectByIndex Return Type: Nothing Description: This method
	 * selects the visible text specified in KeyInData column of Test case in a
	 * select box
	 */
	public  void selectByIndex(WebDriver driver, KeywordModel keywordModel) {
		if (keywordModel.dynaElement != null) {
			try {
				if (keywordModel.dynaElement.isDisplayed()) {
					Select selectBox = new Select(keywordModel.dynaElement);
					selectBox.selectByIndex((int) Float.parseFloat(keywordModel.dataValue));
					ReportUtilities.Log(driver,"Selecting by index in the dropdown " + keywordModel.objectName,
							"Selected the index " + (int) Float.parseFloat(keywordModel.dataValue) + " in the select box",
							Status.PASS , keywordModel);
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot select any value on the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		} else {
			try {
				if (findElementByType(driver, keywordModel).isDisplayed()) {
					Select selectBox = new Select(findElementByType(driver, keywordModel));
					selectBox.selectByIndex((int) Float.parseFloat(keywordModel.dataValue));
					ReportUtilities.Log(driver,"Selecting by index in the dropdown " + keywordModel.objectName,
							"Selected the index " + (int) Float.parseFloat(keywordModel.dataValue) + " in the select box",
							Status.PASS , keywordModel);
				}

			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot select any value on the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		}
	}

	/**
	 * Method Name: selectByIndexKendoUI Return Type: Nothing Description: This
	 * method selects the visible text specified in KeyInData column of Test case in
	 * a Kendo Box
	 * @throws InterruptedException 
	 */
	public  void selectByIndexKendoUI(WebDriver driver, KeywordModel keywordModel) throws InterruptedException {

		if (keywordModel.dynaElement != null) {
			keywordModel.inputXPath = keywordModel.inputXPath + "/preceding-sibling::span/child::input";
			keywordModel.optionXPath = keywordModel.optionXPath + "/child::option";

			try {
				WebElement selectInputBox = driver.findElement(By.xpath(keywordModel.inputXPath));
				if (selectInputBox.isDisplayed()) {
					System.out.println("Xpath to options " + keywordModel.optionXPath);
					List<WebElement> allOptions = driver.findElements(By.xpath(keywordModel.optionXPath));
					List<String> allOptionsStr = new ArrayList<String>();

					for (int i = 0; i < allOptions.size(); i++) {
						// System.out.println(allOptions.get(i).getAttribute("innerHTML"));
						if (allOptions.get(i).getAttribute("innerHTML").toString() != null
								&& !allOptions.get(i).getAttribute("innerHTML").toString().trim().isEmpty()) {
							allOptionsStr.add(allOptions.get(i).getAttribute("innerHTML").toString());
						}
					}
					System.out.println(allOptionsStr);
					selectInputBox.click();
					selectInputBox.clear();
					String indexValue = allOptionsStr.get(((int) Float.parseFloat(keywordModel.dataValue)) - 1);
					selectInputBox.sendKeys(indexValue);
					Thread.sleep(500);
					selectInputBox.sendKeys(Keys.TAB);
					ReportUtilities.Log(driver,"Selecting by index in the dropdown " + keywordModel.objectName,
							"Selected the index " + (int) Float.parseFloat(keywordModel.dataValue) + " in the select box",
							Status.PASS , keywordModel);
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot select any value on the object.", "The Input Box for Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		} else {

			findElementByType(driver, keywordModel);
			keywordModel.inputXPath = keywordModel.inputXPath + "/preceding-sibling::span/child::input";
			keywordModel.optionXPath = keywordModel.optionXPath + "/child::option";

			try {
				WebElement selectInputBox = driver.findElement(By.xpath(keywordModel.inputXPath));
				if (selectInputBox.isDisplayed()) {
					System.out.println("else Xpath to options " + keywordModel.optionXPath);
					List<WebElement> allOptions = driver.findElements(By.xpath(keywordModel.optionXPath));
					List<String> allOptionsStr = new ArrayList<String>();

					for (int i = 0; i < allOptions.size(); i++) {
						// System.out.println(allOptions.get(i).getAttribute("innerHTML"));
						if (allOptions.get(i).getAttribute("innerHTML").toString() != null
								&& !allOptions.get(i).getAttribute("innerHTML").toString().trim().isEmpty()) {
							allOptionsStr.add(allOptions.get(i).getAttribute("innerHTML").toString());
						}
					}
					System.out.println(allOptionsStr);
					selectInputBox.click();
					selectInputBox.clear();
					String indexValue = allOptionsStr.get(((int) Float.parseFloat(keywordModel.dataValue)) - 1);
					selectInputBox.sendKeys(indexValue);
					Thread.sleep(500);
					selectInputBox.sendKeys(Keys.TAB);
					ReportUtilities.Log(driver,"Selecting by index in the dropdown " + keywordModel.objectName,
							"Selected the index " + (int) Float.parseFloat(keywordModel.dataValue) + " in the select box",
							Status.PASS , keywordModel);
				}

			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot select any value on the object.", "The input Box for Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		}
	}

	/**
	 * Method Name: selectByIndex Return Type: Nothing Description: This method
	 * selects the visible text specified in KeyInData column of Test case in a
	 * select box
	 */
	public  void selectByValue(WebDriver driver, KeywordModel keywordModel) {
		if (keywordModel.dynaElement != null) {
			try {
				if (keywordModel.dynaElement.isDisplayed()) {
					Select selectBox = new Select(keywordModel.dynaElement);
					selectBox.selectByValue(keywordModel.dataValue);
					ReportUtilities.Log(driver,"Selecting by index in the dropdown " + keywordModel.objectName,
							"Selected the index " + (int) Float.parseFloat(keywordModel.dataValue) + " in the select box",
							Status.PASS , keywordModel);
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot select any value on the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		} else {
			try {
				if (findElementByType(driver, keywordModel).isDisplayed()) {
					Select selectBox = new Select(findElementByType(driver, keywordModel));
					selectBox.selectByValue(keywordModel.dataValue);
					ReportUtilities.Log(driver,"Selecting by value in the dropdown " + keywordModel.objectName,
							"Selected the value " + keywordModel.dataValue + " in the select box", Status.PASS , keywordModel);
				}
			}

			catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot select any value on the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		}
	}

	/**
	 * Method Name: selectByValueKendoUI Return Type: Nothing Description: This
	 * method selects the visible text specified in KeyInData column of Test case in
	 * a Kendo Box
	 * @throws InterruptedException 
	 */
	public  void selectByValueKendoUI(WebDriver driver, KeywordModel keywordModel) throws InterruptedException {
		String dataValue = "";

		if (keywordModel.dynaElement != null) {
			keywordModel.inputXPath = keywordModel.inputXPath + "/preceding-sibling::span/child::input";
			keywordModel.optionXPath = keywordModel.optionXPath + "/child::option";

			try {
				WebElement selectInputBox = driver.findElement(By.xpath(keywordModel.inputXPath));
				if (selectInputBox.isDisplayed()) {
					System.out.println("Xpath to options " + keywordModel.optionXPath);
					List<WebElement> allOptions = driver.findElements(By.xpath(keywordModel.optionXPath));
					List<String> allOptionsVal = new ArrayList<String>();
					List<String> allOptionsStr = new ArrayList<String>();

					for (int i = 0; i < allOptions.size(); i++) {
						// System.out.println(allOptions.get(i).getAttribute("innerHTML"));
						if (allOptions.get(i).getAttribute("value").toString() != null
								&& !allOptions.get(i).getAttribute("value").toString().trim().isEmpty()) {
							allOptionsVal.add(allOptions.get(i).getAttribute("value").toString());
							allOptionsStr.add(allOptions.get(i).getAttribute("innerHTML").toString());
						}
					}
					// System.out.println(allOptionsStr);
					for (int i = 0; i < allOptionsVal.size(); i++) {
						if (keywordModel.dataValue.trim().equalsIgnoreCase(allOptionsVal.get(i))) {
							dataValue = allOptionsStr.get(i).toString();
							break;
						}
					}
					selectInputBox.clear();
					selectInputBox.sendKeys(dataValue);



					ReportUtilities.Log(driver,"Selecting by value in the dropdown " + keywordModel.objectName,
							"Selected the value " + keywordModel.dataValue + " in the select box", Status.PASS , keywordModel);
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot select any value on the object.", "The Input Box for Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		} else {

			findElementByType(driver, keywordModel);
			keywordModel.inputXPath = keywordModel.inputXPath + "/preceding-sibling::span/child::input";
			keywordModel.optionXPath = keywordModel.optionXPath + "/child::option";

			try {
				WebElement selectInputBox = driver.findElement(By.xpath(keywordModel.inputXPath));
				if (selectInputBox.isDisplayed()) {
					System.out.println("Xpath to options " + keywordModel.optionXPath);
					List<WebElement> allOptions = driver.findElements(By.xpath(keywordModel.optionXPath));
					List<String> allOptionsVal = new ArrayList<String>();
					List<String> allOptionsStr = new ArrayList<String>();

					for (int i = 0; i < allOptions.size(); i++) {
						// System.out.println(allOptions.get(i).getAttribute("innerHTML"));
						if (allOptions.get(i).getAttribute("value").toString() != null
								&& !allOptions.get(i).getAttribute("value").toString().trim().isEmpty()) {
							allOptionsVal.add(allOptions.get(i).getAttribute("value").toString());
							allOptionsStr.add(allOptions.get(i).getAttribute("innerHTML").toString());
						}
					}
					// System.out.println(allOptionsStr);
					for (int i = 0; i < allOptionsVal.size(); i++) {
						if (keywordModel.dataValue.trim().equalsIgnoreCase(allOptionsVal.get(i))) {
							dataValue = allOptionsStr.get(i).toString();
							break;
						}
					}
					selectInputBox.clear();
					selectInputBox.sendKeys(dataValue);
					//		Thread.sleep(2000);
					selectInputBox.sendKeys(Keys.ENTER);
					ReportUtilities.Log(driver,"Selecting by value in the dropdown " + keywordModel.objectName,
							"Selected the value " + keywordModel.dataValue + " in the select box", Status.PASS , keywordModel);
				}

			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot select any value on the object.", "The input Box for Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		}
	}

	/**
	 * Method Name: click Return Type: Nothing Description: This method clicks on an
	 * object specified in the Object Repository and referred in the Object ID
	 * column
	 */
	public  void click(WebDriver driver, KeywordModel keywordModel) {
	//	JavascriptExecutor js =(JavascriptExecutor) driver;
		if (keywordModel.dynaElement != null) {
			try {
				if (keywordModel.dynaElement.isDisplayed()) {
					keywordModel.dynaElement.click();
				//	js.executeScript("arguments[0].click();",keywordModel.dynaElement);
					ReportUtilities.Log(driver,"Clicking on the Element " + keywordModel.objectName, "Clicked on the object", Status.PASS , keywordModel);
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot Click on the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		} else {
			try {
				if (findElementByType(driver, keywordModel).isDisplayed()) {
					findElementByType(driver, keywordModel).click();
				//	js.executeScript("arguments[0].click();",keywordModel.dynaElement);
					ReportUtilities.Log(driver,"Clicking on the Element " + keywordModel.objectName, "Clicked on the object", Status.PASS , keywordModel);
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot Click on the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		}
	}
	// For debugging purpose
	
	public  void navigateToNextPage(WebDriver driver, KeywordModel keywordModel) throws InterruptedException {
		//	JavascriptExecutor js =(JavascriptExecutor) driver;
		
		NavigationTimeHelper navigationTimeHelper = new NavigationTimeHelper(); 
		
		long StartTime= System.currentTimeMillis();
    	
    	UIPerfModel uiPerfModel = new UIPerfModel();
    	
    	String getHeaderBeforeClick ="";
    	String getHeaderAfterClick="";
    	
    
    	try {
    		
    		getHeaderBeforeClick = driver.findElement(By.xpath("//div[@class=\"title-1\" or @class=\"title-1 ng-star-inserted\"]")).getText();
    		
    	}catch (Exception e){
    		
    		System.out.println("Unable to fetch Header");
    		
    	}
    
    	
		if (keywordModel.dynaElement != null) {
			try {
				if (keywordModel.dynaElement.isDisplayed()) {
					JavascriptExecutor executor = (JavascriptExecutor)driver;
					executor.executeScript("arguments[0].click();", keywordModel.dynaElement);
					ReportUtilities.Log(driver,"Clicking on the Element " + keywordModel.objectName, "Clicked on the object", Status.PASS , keywordModel);
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot Click on the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		} else {
			try {
				if (findElementByType(driver, keywordModel).isDisplayed()) {
					WebElement ele = findElementByType(driver, keywordModel);
					JavascriptExecutor executor = (JavascriptExecutor)driver;
					executor.executeScript("arguments[0].click();", ele);
					
				//	Thread.sleep(60000);
					
		
					
					try {
			    		
			    		getHeaderAfterClick = driver.findElement(By.xpath("//div[@class=\"title-1\" or @class=\"title-1 ng-star-inserted\"]")).getText();
			    		
			    	}catch (Exception e){
			    		
			    		System.out.println("Unable to fetch Header");
			    		
			    	}
					
					if(getHeaderBeforeClick.equals("")==false  && getHeaderAfterClick.equals("")==false) {
						String timing=navigationTimeHelper.getAllTiming(driver);
						
				    	uiPerfModel= uiPerfModel.AddUIPerfModel(getHeaderBeforeClick, getHeaderAfterClick,  keywordModel.ScreenName, keywordModel.scenario, keywordModel.browser, StartTime, timing);
				    	
				    	UIPerfConstants uiPerfConstants = new UIPerfConstants();
				    	
				    	uiPerfConstants.addUpdatePerfData(getHeaderBeforeClick, getHeaderAfterClick, uiPerfModel);
				    	
						ReportUtilities.Log(driver,"Clicking on the Element " + keywordModel.objectName, "Clicked on the object", Status.PASS , keywordModel);
					
					} 
					
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot Click on the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		}
	}
	
	public  void VerifyAndClickCheckBox(WebDriver driver, KeywordModel keywordModel) {
		if (keywordModel.dynaElement != null) {
			try {
				if (keywordModel.dynaElement.isDisplayed()) {
					if ((keywordModel.dynaElement.getAttribute("checked")) == null) {
						keywordModel.dynaElement.click();
						ReportUtilities.Log(driver,"Clicking on the Element " + keywordModel.objectName, "Clicked on the object",
								Status.PASS , keywordModel);
					}
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot Click on the object.", "The check box " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		} else {
			try {
				if (findElementByType(driver, keywordModel).isDisplayed()) {
					if ((findElementByType(driver, keywordModel).getAttribute("checked") == null)) {
						findElementByType(driver, keywordModel).click();
						ReportUtilities.Log(driver,"Clicking on the Element " + keywordModel.objectName, "Clicked on the object",
								Status.PASS , keywordModel);
					}
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot Click on the object.", "The check box " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		}
	}
	
	
	
	/**
	 * Method Name: selectMultipleByVisibleText Return Type: Nothing Description:
	 * This method selects the multiple text's specified in KeyInData column of Test
	 * case in a select box
	 */
	public  void selectMultipleByVisibleText(WebDriver driver, KeywordModel keywordModel) {
		String[] dataValuesArr = keywordModel.dataValue.split(";");
		if (dataValuesArr != null && dataValuesArr.length > 0) {
			Select selectBox = new Select(findElementByType(driver, keywordModel));
			for (int i = 0; i < dataValuesArr.length; i++) {
				selectBox.selectByVisibleText(dataValuesArr[i].trim());
			}
		}
		ReportUtilities.Log(driver,"Selecting by visible text in the select box " + keywordModel.objectName,
				"Selected the value " + keywordModel.dataValue + " in the select box ", Status.PASS , keywordModel);
	}
	
	
	
	
	
	public void getCaseNumberFromExcel(WebDriver driver, KeywordModel keywordModel)
	{
		keywordModel.caseNumber = keywordModel.dataValue;
	}
	
	public void getCaseDataExcel(WebDriver driver, KeywordModel keywordModel)
	{
		String[] casedata  = new String[10];
				casedata =	keywordModel.dataValue.split(";");
				keywordModel.caseNumber = casedata[0];
				keywordModel.individualID = casedata[1];
				
	}

	//Method : 	EnterCaseNumber
	//Description: Enter the AP case number to quicksearch
	public  void EnterCaseNumber(WebDriver driver, KeywordModel keywordModel) {
		findElementByType(driver, keywordModel).sendKeys(keywordModel.caseNumber);
	}


	public  void getApplicationNumber(WebDriver driver, KeywordModel keywordModel) {
		try {
			if (findElementByType(driver, keywordModel).isDisplayed()) {
				WebElement webElement = findElementByType(driver, keywordModel);
				String elementValue = webElement.getText();
				elementValue = elementValue.replace("Application: ", "");
				keywordModel.applicationNumber = elementValue;
			}
		} catch (NoSuchElementException p) {
			keywordModel.error = true;
			keywordModel.displayError = true;
			ReportUtilities.Log(driver,"Cannot get Data from the object.",
					"The element " + keywordModel.objectName + " is NOT displayed on the current screen" + keywordModel.ScreenName,
					Status.FAIL , keywordModel);
			throw new RuntimeException(p);
		}

	}

	/**
	 * Method Name: extract Case Number From String Return Type: Cloned Case Number
	 * Description: This method gets the case number from the server side validation
	 * message thrown on Case/Copy/Clone screen.
	 * 
	 * @throws Exception
	 */
//	public  String extractCaseNumberFromString(WebDriver driver, KeywordModel keywordModel) throws Exception {
//		String generatedString = driver.findElement(By.xpath("//*[@id='errorSpanClient']/span")).getText();
//		String[] arrayOfString = generatedString.split("\\s+");
//		List<String> arrayListOfStrings = new ArrayList<String>(Arrays.asList(arrayOfString));
//		if (arrayListOfStrings != null && !arrayListOfStrings.isEmpty()) {
//			String keywordModel.caseNumber = arrayListOfStrings.get(arrayListOfStrings.size() - 1);
//			keywordModel.sessionid = keywordModel.caseNumber;
//		}
//		setIfAutoCaseNum();
//		ReportUtilities.Log(driver,"Returning the newly generated case number" + keywordModel.objectName,
//				"Returned the value " + keywordModel.sessionid + " generated case num ", Status.PASS , keywordModel);
//		return keywordModel.sessionid;
//	}

	
	
	/**
	 * Method Name: enterKeyBoard Return Type: Nothing Description: This method
	 * clicks on an object specified in the Object Repository and referred in the
	 * Object ID column
	 */
	public  void enterKeyBoard(WebDriver driver, KeywordModel keywordModel) {
		findElementByType(driver, keywordModel).sendKeys(Keys.ENTER);
		ReportUtilities.Log(driver,"Clicking on the Element " + keywordModel.objectName, "Clicked on the object", Status.PASS , keywordModel);
	}

	/**
	 * Method Name: jsClick Return Type: Nothing Description: Same as click keyword
	 * except that this method uses Selenium WebDriver JavaScript API to click the
	 * object. The selector must be ID selector.
	 */
	public  void jsClick(WebDriver driver, KeywordModel keywordModel) 
{
		if (keywordModel.dynaElement != null) {
			try {
				if (keywordModel.dynaElement.isDisplayed()) {
					JavascriptExecutor executor = (JavascriptExecutor)driver;
					executor.executeScript("arguments[0].click();", keywordModel.dynaElement);
					ReportUtilities.Log(driver,"Clicking on the Element " + keywordModel.objectName, "Clicked on the object", Status.PASS , keywordModel);
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot Click on the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		} else {
			try {
				if (findElementByType(driver, keywordModel).isDisplayed()) {
					WebElement ele = findElementByType(driver, keywordModel);
					JavascriptExecutor executor = (JavascriptExecutor)driver;
					executor.executeScript("arguments[0].click();", ele);
					ReportUtilities.Log(driver,"Clicking on the Element " + keywordModel.objectName, "Clicked on the object", Status.PASS , keywordModel);
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot Click on the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		}
	}

	/*
	 * Convenient method to execute Java Script
	 */
	private  void executeJavaScript(String jsStatement, WebDriver driver, KeywordModel keywordModel) {

		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript(jsStatement);
	}
	
	/**
	 * Method Name: doubleClick Return Type: Nothing Description: This method double
	 * clicks on an object specified in the Object Repository and referred in the
	 * Object ID column
	 */
	public  void doubleClick(WebDriver driver, KeywordModel keywordModel) {
		Actions ac1 = new Actions(driver);
		ac1.doubleClick(findElementByType(driver, keywordModel)).perform();
		ReportUtilities.Log(driver,"Double Clicking on the Element " + keywordModel.objectName, "Double clicked on the object", Status.PASS , keywordModel);
	}
	
	/**
	 * Method Name: delay Return Type: Nothings Description: This method inserts a
	 * sleep time of timeout value specified in the WebDriverHelper class
	 */
	public  void delay(WebDriver driver, KeywordModel keywordModel) {
		try {
			Thread.sleep(Long.parseLong(Integer.toString(((int) Float.parseFloat(keywordModel.dataValue)))));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Method Name: longDelay Return Type: Nothings Description: This method inserts
	 * a sleep time of timeout value specified in the WebDriverHelper class
	 */
	public  void longDelay(String dataValue, WebDriver driver, KeywordModel keywordModel) {
		try {
			Thread.sleep(Long.parseLong(Integer.toString(((int) Float.parseFloat(dataValue)))));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Method Name: WriteDataToOutputFile Return Type: Nothing Description: This
	 * method writes data to Output INI file
	 */
	/*
	 * public  void WriteDataToOutputFile(){ String columnValue =
	 * keywordModel.dataValue; String data = columnValue.split(";")[0].trim(); String key
	 * = columnValue.split(";")[1].trim();
	 * 
	 * if(data.startsWith("getData=")){ try { data =
	 * KeywordUtilities.getData(data.split("getData=")[1].trim()); } catch (Exception e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); } KeywordUtilities.setDataINI(key,
	 * data); }else if(data.startsWith("ObjectText")){ try { data =
	 * findElementByType(driver, keywordModel).getText().toString(); } catch (Exception e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } KeywordUtilities.setDataINI(key, data);
	 * } else{ KeywordUtilities.setDataINI(key, data); } }
	 */

	// ------------------------Verifying keywords

	/**
	 * Method Name: verifyElementPresent Return Type: Nothing Description: This
	 * method verifies for the existence of an object
	 */
	public  void verifyElementPresent(WebDriver driver, KeywordModel keywordModel) {
		if (findElementsByType(driver, keywordModel).size() != 0) {
			if (!keywordModel.checkBoolean.equalsIgnoreCase("false")) {
				if (!keywordModel.onPassLog.equalsIgnoreCase("")) {
					ReportUtilities.Log(driver,"Verifying the Presence of Element " + keywordModel.objectName, keywordModel.onPassLog, Status.PASS , keywordModel);
					keywordModel.elementPresence = true;
				} else {
					ReportUtilities.Log(driver,"Verifying the Presence of Element " + keywordModel.objectName,
							"The Element " + keywordModel.objectName + " is present", Status.PASS , keywordModel);
					keywordModel.elementPresence = true;
				}
			}
		} else {
			if (keywordModel.checkBoolean.equalsIgnoreCase("")) {
				if (!keywordModel.onFailLog.equalsIgnoreCase("")) {
					ReportUtilities.Log(driver,"Verifying the Presence of Element " + keywordModel.objectName, keywordModel.onFailLog, Status.FAIL , keywordModel);
					keywordModel.elementPresence = false;
				} else {
					ReportUtilities.Log(driver,"Verifying the Presence of Element " + keywordModel.objectName,
							"The Element " + keywordModel.objectName + " is  not present", Status.FAIL , keywordModel);
					keywordModel.elementPresence = false;
				}
			}
		}
	}

	/**
	 * Method Name: verifyElementNotPresent Return Type: Nothing Description: This
	 * method verifies for the non-existence of an object
	 */
	public  void verifyElementNotPresent(WebDriver driver, KeywordModel keywordModel) {
		if (findElementsByType(driver, keywordModel).size() == 0) {
			if (!keywordModel.checkBoolean.equalsIgnoreCase("false")) {
				if (!keywordModel.onPassLog.equalsIgnoreCase("")) {
					ReportUtilities.Log(driver,"Verifying that " + keywordModel.objectName + " Element is not present", keywordModel.onPassLog,
							Status.PASS , keywordModel);
				} else {
					ReportUtilities.Log(driver,"Verifying that " + keywordModel.objectName + " Element is not present",
							"The Element " + keywordModel.objectName + " is not present", Status.PASS , keywordModel);
				}
			}

		} else {
			if (!keywordModel.onFailLog.equalsIgnoreCase("")) {
				ReportUtilities.Log(driver,"Verifying that " + keywordModel.objectName + " Element is not present", keywordModel.onFailLog,
						Status.FAIL , keywordModel);
			} else {
				ReportUtilities.Log(driver,"Verifying that " + keywordModel.objectName + " Element is not present",
						"The Element " + keywordModel.objectName + " is  present", Status.FAIL , keywordModel);
			}
		}
	}

	/**
	 * Method Name: verifyElementDisplayed Return Type: Nothing Description: This
	 * method verifies if the object is present in the Page structure, and is also
	 * displayed on the screen.
	 */

	public  void verifyElementDisplayed(WebDriver driver, KeywordModel keywordModel) {
		if (keywordModel.dynaElement != null) {
			if (keywordModel.dynaElement.isDisplayed()) {
				ReportUtilities.Log(driver,"Verifying that " + keywordModel.objectName + " Element is displayed",
						"The Element " + keywordModel.objectName + " is  displayed", Status.PASS , keywordModel);
			} else {
				ReportUtilities.Log(driver,"Verifying that " + keywordModel.objectName + " Element is displayed",
						"The Element " + keywordModel.objectName + " is  Not displayed", Status.FAIL , keywordModel);
			}
		} else {
			if (findElementByType(driver, keywordModel).isDisplayed()) {
				ReportUtilities.Log(driver,"Verifying that " + keywordModel.objectName + " Element is displayed",
						"The Element " + keywordModel.objectName + " is  displayed", Status.PASS , keywordModel);
			} else {
				ReportUtilities.Log(driver,"Verifying that " + keywordModel.objectName + " Element is displayed",
						"The Element " + keywordModel.objectName + " is  Not displayed", Status.FAIL , keywordModel);
			}

		}

	}

	/**
	 * Method Name: verifyElementNotDisplayed Return Type: Nothing Description: This
	 * method verifies if the object is present in the Page structure, but is NOT
	 * displayed on the screen.
	 */

	public  void verifyElementNotDisplayed(WebDriver driver, KeywordModel keywordModel) {
		if (keywordModel.dynaElement != null) {
			if (keywordModel.dynaElement.isDisplayed()) {
				ReportUtilities.Log(driver,"Verifying that " + keywordModel.objectName + " Element is not displayed",
						"The Element " + keywordModel.objectName + " is  displayed", Status.FAIL , keywordModel);
			} else {
				ReportUtilities.Log(driver,"Verifying that " + keywordModel.objectName + " Element is not displayed",
						"The Element " + keywordModel.objectName + " is  Not displayed", Status.PASS , keywordModel);
			}
		} else {
			if (findElementByType(driver, keywordModel).isDisplayed()) {
				ReportUtilities.Log(driver,"Verifying that " + keywordModel.objectName + " Element is not displayed",
						"The Element " + keywordModel.objectName + " is  displayed", Status.FAIL , keywordModel);
			} else {
				ReportUtilities.Log(driver,"Verifying that " + keywordModel.objectName + " Element is not displayed",
						"The Element " + keywordModel.objectName + " is  Not displayed", Status.PASS , keywordModel);
			}
		}

	}
	/*Method :fetchIndName
	 * Description : This method fetches name of individual2 from right pane */

//	public  void fetchIndName(WebDriver driver, KeywordModel keywordModel) {
//		String IndID = findElementByType(driver, keywordModel).getText();
//		xpathIndName = driver.findElement(By.xpath("//*[@id='individual-"+IndID+"']//div[contains(@class,'IndividualName')]")).getText();
//		ReportUtilities.Log(driver,"Verifying the Text of the object " + keywordModel.objectName,"The element " + keywordModel.objectName + " has the text " + xpathIndName,
//				Status.PASS , keywordModel);
//
//	}
//	/*Method :fetchIndNameNotice
//	 * Description : This method fetches name of denied individual in the notice reason */
//	public  void fetchIndNameNotice(WebDriver driver, KeywordModel keywordModel) {
//
//		String IndIDNotice = findElementByType(driver, keywordModel).getText();
//		CompareName(IndIDNotice);
//
//	}
	/*Method :CompareName
	 * Description : This method compares the name in notice reason to the name of ind2 in right pane*/
//	public  void CompareName(String indName, WebDriver driver, KeywordModel keywordModel) {
//
//		if(xpathIndName.contains(indName))
//		{
//			ReportUtilities.Log(driver,"Verifying the Text of the object " + keywordModel.objectName,"The element " + keywordModel.objectName + " has the text " + indName,
//					Status.PASS , keywordModel);
//		}
//		else
//		{
//			ReportUtilities.Log(driver,"Verifying the Text of the object " + keywordModel.objectName,"The element " + keywordModel.objectName + " does not has the text " + keywordModel.dataValue,
//					Status.FAIL , keywordModel);
//		}
//	}

	
	/**
	 * Method Name: verifyElementText Return Type: Nothing Description: This method
	 * verifies for the text of the element
	 * @throws InterruptedException 
	 */
	public  void verifyElementText(WebDriver driver, KeywordModel keywordModel) throws InterruptedException {
		if (keywordModel.dynaElement != null) {
			try {
				if (keywordModel.dynaElement.isDisplayed()) {

					if (keywordModel.checkBoolean.equalsIgnoreCase("")) {
						if (keywordModel.dynaElement.getText().trim().contains(keywordModel.dataValue)) {
							System.out.println(keywordModel.dataValue);
							ReportUtilities.Log(driver,"Verifying the Text of the object " + keywordModel.objectName,
									"The element " + keywordModel.objectName + " has the text " + keywordModel.dataValue,
									Status.PASS , keywordModel);
						} else if (keywordModel.dynaElement.getText().contains(keywordModel.sessionid)) {
							System.out.println(keywordModel.sessionid);
							ReportUtilities.Log(driver,"Verifying the Text of the object " + keywordModel.objectName,
									"The element " + keywordModel.objectName + " has the text " + keywordModel.sessionid, Status.PASS , keywordModel);
						} else {
							ReportUtilities.Log(driver,"Verifying the Text of the object " + keywordModel.objectName,
									"The element " + keywordModel.objectName + " does not have the text " + keywordModel.dataValue
									+ ". Text found: " + keywordModel.dynaElement.getText(),
									Status.FAIL , keywordModel);
							storeEDMResult(driver, keywordModel);
						//	driver.wait();
						}
					}
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot Verify text for the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		} else {
			try {
				if (findElementByType(driver, keywordModel).isDisplayed()) {
					if (keywordModel.checkBoolean.equalsIgnoreCase("")) {
						if (findElementByType(driver, keywordModel).getText().trim().contains(keywordModel.dataValue)) {
							System.out.println("Verifying the Text of the object " + keywordModel.objectName+
									"The element " + keywordModel.objectName + " has the text " + keywordModel.dataValue);
							ReportUtilities.Log(driver,"Verifying the Text of the object " + keywordModel.objectName,
									"The element " + keywordModel.objectName + " has the text " + keywordModel.dataValue,
									Status.PASS , keywordModel);
						} else if (findElementByType(driver, keywordModel).getText().contains(keywordModel.sessionid) && keywordModel.sessionid != "" && keywordModel.sessionid != null) {
							System.out.println("Verifying the Text of the object " + keywordModel.objectName+
									"The element " + keywordModel.objectName + " has the text " + keywordModel.sessionid);
							ReportUtilities.Log(driver,"Verifying the Text of the object " + keywordModel.objectName,
									"The element " + keywordModel.objectName + " has the text " + keywordModel.sessionid, Status.PASS , keywordModel);
						} else {

							System.out.println("Verifying the Text of the object " + keywordModel.objectName+
									"The element " + keywordModel.objectName + " does not have the text " + keywordModel.dataValue
									+ ". Text found: " + findElementByType(driver, keywordModel).getText());
							ReportUtilities.Log(driver,"Verifying the Text of the object " + keywordModel.objectName,
									"The element " + keywordModel.objectName + " does not have the text " + keywordModel.dataValue
									+ ". Text found: " + findElementByType(driver, keywordModel).getText(),
									Status.FAIL , keywordModel);
							storeEDMResult(driver, keywordModel);
							//driver.wait();
						}
					}
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot Verify text for the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
				
			}
		}

	}

	public  void verifyElementDateFromSheet(WebDriver driver, KeywordModel keywordModel) {
		if (keywordModel.dynaElement != null) {
			try {
				if (keywordModel.dynaElement.isDisplayed()) {
					String test = keywordModel.dataValue;
					System.out.println("Date from sheet is:" + test);
					char[] charTest = test.toCharArray();
					String test1 = String.valueOf(charTest[0]) + String.valueOf(charTest[1]);
					String test2 = String.valueOf(charTest[2]) + String.valueOf(charTest[3]);
					String test3 = String.valueOf(charTest[4]) + String.valueOf(charTest[5])
					+ String.valueOf(charTest[6]) + String.valueOf(charTest[7]);
					String finalTest = test1 + "/" + test2 + "/" + test3;
					// System.out.println(finalTest);

					if (keywordModel.checkBoolean.equalsIgnoreCase("")) {

						if (keywordModel.dynaElement.getText().trim().equals(finalTest)) {
							ReportUtilities.Log(driver,"Verifying the Date displayed on the object " + keywordModel.objectName,
									"The element " + keywordModel.objectName + " has the Date " + finalTest, Status.PASS , keywordModel);
						} else if (keywordModel.dynaElement.getText().equals(keywordModel.sessionid)) {
							ReportUtilities.Log(driver,"Verifying the Date displayed on the object " + keywordModel.objectName,
									"The element " + keywordModel.objectName + " has the Date " + keywordModel.sessionid, Status.PASS , keywordModel);
						} else {
							ReportUtilities.Log(driver,"Verifying the Date displayed on the object " + keywordModel.objectName,
									"The element " + keywordModel.objectName + " does not have the Date " + finalTest
									+ ". Date found: " + keywordModel.dynaElement.getText(),
									Status.FAIL , keywordModel);
						}
					}
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot Verify text for the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		} else {
			try {
				if (findElementByType(driver, keywordModel).isDisplayed()) {
					String test = keywordModel.dataValue;
					char[] charTest = test.toCharArray();
					String test1 = String.valueOf(charTest[0]) + String.valueOf(charTest[1]);
					String test2 = String.valueOf(charTest[2]) + String.valueOf(charTest[3]);
					String test3 = String.valueOf(charTest[4]) + String.valueOf(charTest[5])
					+ String.valueOf(charTest[6]) + String.valueOf(charTest[7]);
					String finalTest = test1 + "/" + test2 + "/" + test3;
					System.out.println("Date from sheet is:" + test);
					System.out.println("Final Date from sheet is:" + finalTest);
					if (keywordModel.checkBoolean.equalsIgnoreCase("")) {
						if (keywordModel.dynaElement.getText().trim().equals(finalTest)) {
							ReportUtilities.Log(driver,"Verifying the Date displayed on the object " + keywordModel.objectName,
									"The element " + keywordModel.objectName + " has the Date " + finalTest, Status.PASS , keywordModel);
						} else if (keywordModel.dynaElement.getText().equals(keywordModel.sessionid)) {
							ReportUtilities.Log(driver,"Verifying the Date displayed on the object " + keywordModel.objectName,
									"The element " + keywordModel.objectName + " has the Date " + keywordModel.sessionid, Status.PASS , keywordModel);
						} else {
							ReportUtilities.Log(driver,"Verifying the Date displayed on the object " + keywordModel.objectName,
									"The element " + keywordModel.objectName + " does not have the Date " + finalTest
									+ ". Date found: " + keywordModel.dynaElement.getText(),
									Status.FAIL , keywordModel);
						}
					}
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot Verify text for the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		}

	}

	/**
	 * Method Name: verifyTextNotEqual Return Type: Nothing Description: This method
	 * verifies if the Element Text is NOT equal to the one given in datavlaue.
	 */
	public  void verifyTextNotEqual(WebDriver driver, KeywordModel keywordModel) {
		if (keywordModel.dynaElement != null) {
			try {
				if (keywordModel.dynaElement.isDisplayed()) {
					if (keywordModel.checkBoolean.equalsIgnoreCase("")) {
						if (keywordModel.dynaElement.getText().trim().equals(keywordModel.dataValue)) {
							ReportUtilities.Log(driver,"Verifying if the Text is different for the object " + keywordModel.objectName,
									"The element " + keywordModel.objectName + " has the text " + keywordModel.dataValue,
									Status.FAIL , keywordModel);
						} else if (keywordModel.dynaElement.getText().equals(keywordModel.sessionid)) {
							ReportUtilities.Log(driver,"Verifying if the Text is different for the object " + keywordModel.objectName,
									"The element " + keywordModel.objectName + " has the text " + keywordModel.sessionid, Status.FAIL , keywordModel);
						} else {
							ReportUtilities.Log(driver,"Verifying if the Text is different for the object " + keywordModel.objectName,
									"The element " + keywordModel.objectName + " does not have the text " + keywordModel.dataValue,
									Status.PASS , keywordModel);
						}
					}
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot Verify text for the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		} else {
			try {
				if (findElementByType(driver, keywordModel).isDisplayed()) {
					if (keywordModel.checkBoolean.equalsIgnoreCase("")) {
						if (findElementByType(driver, keywordModel).getText().trim().equals(keywordModel.dataValue)) {
							ReportUtilities.Log(driver,"Verifying if the Text is different for the object " + keywordModel.objectName,
									"The element " + keywordModel.objectName + " has the text " + keywordModel.dataValue,
									Status.FAIL , keywordModel);
						} else if (findElementByType(driver, keywordModel).getText().equals(keywordModel.sessionid)) {
							ReportUtilities.Log(driver,"Verifying if the Text is different for the object " + keywordModel.objectName,
									"The element " + keywordModel.objectName + " has the text " + keywordModel.sessionid, Status.FAIL , keywordModel);
						} else {
							ReportUtilities.Log(driver,"Verifying if the Text is different for the object " + keywordModel.objectName,
									"The element " + keywordModel.objectName + " does not have the text " + keywordModel.dataValue,
									Status.PASS , keywordModel);
						}
					}
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot Verify text for the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		}
	}

	/**
	 * Method Name: verifyElementByValue Return Type: Nothing Description: This
	 * method verifies the value of a HTML ELement
	 */
	public  void verifyElementByValue(WebDriver driver, KeywordModel keywordModel) {
		if (keywordModel.checkBoolean.equalsIgnoreCase("")) {
			if (findElementByType(driver, keywordModel).getAttribute("value").contains(keywordModel.dataValue)) {
				ReportUtilities.Log(driver,"Verifying the Text of the object " + keywordModel.objectName,
						"The element " + keywordModel.objectName + " has the text " + keywordModel.dataValue, Status.PASS , keywordModel);
			} else if (findElementByType(driver, keywordModel).getAttribute("value").contains(keywordModel.sessionid)) {
				//System.out.println(keywordModel.sessionid);
				ReportUtilities.Log(driver,"Verifying the Text of the object " + keywordModel.objectName,
						"The element " + keywordModel.objectName + " has the text " + keywordModel.sessionid, Status.PASS , keywordModel);
			}else if (findElementByType(driver, keywordModel).getAttribute("value").startsWith("0")) {
				if (findElementByType(driver, keywordModel).getAttribute("value").substring(1).equals(keywordModel.dataValue))
					ReportUtilities.Log(driver,"Verifying the Text of the object " + keywordModel.objectName,
							"The element " + keywordModel.objectName + " has the text " + keywordModel.dataValue, Status.PASS , keywordModel);
				else
					ReportUtilities.Log(driver,"Verifying the Text of the object " + keywordModel.objectName,
							"The element " + keywordModel.objectName + " does not have the text " + keywordModel.dataValue,
							Status.FAIL , keywordModel);
			} else {
				ReportUtilities.Log(driver,"Verifying the Text of the object " + keywordModel.objectName,
						"The element " + keywordModel.objectName + " does not have the text " + keywordModel.dataValue,
						Status.FAIL , keywordModel);
			}
		} else {
			ReportUtilities.Log(driver,"Verification of Element's value is not PASS", "", Status.PASS , keywordModel);
		}
	}

	/**
	 * Method Name: verifyMandatoryElementHighlight Return Type: Nothing
	 * Description: This method verifies if the mandatory field is highlighted
	 */
	public  void verifyMandatoryElementHighlight(WebDriver driver, KeywordModel keywordModel) {
		if (findElementByType(driver, keywordModel).getAttribute("style").equals(
				"background-color: rgb(255, 223, 223); border-width: 1px; border-color: rgb(255, 0, 0); border-style: solid;")) {
			ReportUtilities.Log(driver,"Verify the highlight of the object" + keywordModel.objectName,
					"The field " + keywordModel.objectName + " is highlighted for mandatory validation" + keywordModel.dataValue,
					Status.PASS , keywordModel);
		} else {
			ReportUtilities.Log(driver,"Highlight of the object failed " + keywordModel.objectName,
					"The element " + keywordModel.objectName + " does not have the highlight" + keywordModel.dataValue,
					Status.FAIL , keywordModel);
		}
	}

	/**
	 * Method Name: verifyElementDisabled Return Type: Nothing Description: This
	 * method verifies for the editable state of the object
	 */
	public  void verifyElementDisabledByAttribute(WebDriver driver, KeywordModel keywordModel) {
		if (keywordModel.checkBoolean.equalsIgnoreCase("")) {
			if (findElementByType(driver, keywordModel).getAttribute("isenabled").equals("false")) {
				ReportUtilities.Log(driver,"Verifying the State of the object " + keywordModel.objectName,
						"The element " + keywordModel.objectName + " is in disabled state", Status.PASS , keywordModel);
			} else {
				ReportUtilities.Log(driver,"Verifying the State of the object " + keywordModel.objectName,
						"The element " + keywordModel.objectName + " is not in disabled state", Status.FAIL , keywordModel);
			}
		}
	}

	public  void verifyElementDisabledByAttributeDisabled(WebDriver driver, KeywordModel keywordModel) {
		String disabledFlag = "";
		if (keywordModel.checkBoolean.equalsIgnoreCase("")) {
			try {
				disabledFlag = findElementByType(driver, keywordModel).getAttribute("disabled");
			} catch (NullPointerException e) {
				if (disabledFlag.equals(null)) {
					ReportUtilities.Log(driver,"Verifying the State of the object " + keywordModel.objectName,
							"The element " + keywordModel.objectName + " is in disabled state", Status.PASS , keywordModel);
				} else {
					ReportUtilities.Log(driver,"Verifying the State of the object " + keywordModel.objectName,
							"The element " + keywordModel.objectName + " is not in disabled state", Status.FAIL , keywordModel);
				}
			}
		}
	}

	public  void verifyElementDisabled(WebDriver driver, KeywordModel keywordModel) {
		if (keywordModel.checkBoolean.equalsIgnoreCase("")) {
			if (!findElementByType(driver, keywordModel).isEnabled()) {
				ReportUtilities.Log(driver,"Verifying the State of the object " + keywordModel.objectName,
						"The element " + keywordModel.objectName + " is in disabled state", Status.PASS , keywordModel);
			} else {
				ReportUtilities.Log(driver,"Verifying the State of the object " + keywordModel.objectName,
						"The element " + keywordModel.objectName + " is not in disabled state", Status.FAIL , keywordModel);
			}
		}
	}

	public  void verifyDropdownDisabledKendoUI(WebDriver driver, KeywordModel keywordModel) {
		if (keywordModel.checkBoolean.equalsIgnoreCase("")) {
			findElementByType(driver, keywordModel);
			keywordModel.inputXPath = keywordModel.inputXPath + "/preceding-sibling::span/child::input";
			System.out.println(keywordModel.inputXPath);

			WebElement selectInputBox = driver.findElement(By.xpath(keywordModel.inputXPath));
			System.out.println(selectInputBox.getAttribute("disabled"));
			if (!selectInputBox.getAttribute("disabled").equalsIgnoreCase("disabled")) {
				ReportUtilities.Log(driver,"Verifying the State of the object " + keywordModel.objectName,
						"The element " + keywordModel.objectName + " is in disabled state", Status.PASS , keywordModel);
			} else {
				ReportUtilities.Log(driver,"Verifying the State of the object " + keywordModel.objectName,
						"The element " + keywordModel.objectName + " is not in disabled state", Status.FAIL , keywordModel);
			}
		}
	}

	public  void verifyElementEnabled(WebDriver driver, KeywordModel keywordModel) {
		if (keywordModel.checkBoolean.equalsIgnoreCase("")) {
			if (findElementByType(driver, keywordModel).isEnabled()) {
				ReportUtilities.Log(driver,"Verifying the State of the object " + keywordModel.objectName,
						"The element " + keywordModel.objectName + " is in enabled state", Status.PASS , keywordModel);
			} else {
				ReportUtilities.Log(driver,"Verifying the State of the object " + keywordModel.objectName,
						"The element " + keywordModel.objectName + " is not in enabled state", Status.FAIL , keywordModel);
			}
		}
	}

	/**
	 * Method Name: verifyDropDownValues Return Type: Nothing Description: This
	 * method verifies the options present in the drop down
	 */
	public  void verifyDropDownValues(WebDriver driver, KeywordModel keywordModel) {
		if (keywordModel.checkBoolean.equalsIgnoreCase("")) {
			boolean flag = true;
			ArrayList<String> dataValues = new ArrayList<String>();
			String[] array_dataValues = null;
			String data = keywordModel.dataValue;
			if (data.contains(";")) {
				array_dataValues = data.split(";");
				for (int i = 0; i < array_dataValues.length; i++) {
					dataValues.add(array_dataValues[i].toString());
				}
			} else {
				dataValues.add(data);
			}

			// Get the options of the drop down list.
			List<WebElement> listOfOptions = findElementByType(driver, keywordModel).findElements(By.tagName("option"));

			if (!(listOfOptions.size() == dataValues.size())) {
				flag = false;
			} else {
				for (int j = 0; j < listOfOptions.size(); j++) {
					if (!(listOfOptions.get(j).getText().trim().equals(dataValues.get(j)))) {
						flag = false;
						break;
					}
				}
			}

			if (flag) {
				ReportUtilities.Log(driver,"Verifying the values in the dropdown " + keywordModel.objectName,
						"The expected values are present in the dropdown", Status.PASS , keywordModel);
			} else {
				ReportUtilities.Log(driver,"Verifying the values in the dropdown " + keywordModel.objectName,
						"The expected values are not present in the dropdown", Status.FAIL , keywordModel);
			}
		}
	}

	/**
	 * Method Name: verifyCheckBoxChecked Return Type: Nothing Description: This
	 * method verifies whether a check box is checked
	 */
	public  void verifyCheckBoxChecked(WebDriver driver, KeywordModel keywordModel) {
		if (keywordModel.checkBoolean.equalsIgnoreCase("")) {
			if (findElementByType(driver, keywordModel).isSelected()) {
				ReportUtilities.Log(driver,"Verifying the State of the checkbox " + keywordModel.objectName,
						"The checkbox " + keywordModel.objectName + " is checked", Status.PASS , keywordModel);
			} else {
				ReportUtilities.Log(driver,"Verifying the State of the checkbox " + keywordModel.objectName,
						"The checkbox " + keywordModel.objectName + " is not checked", Status.FAIL , keywordModel);
			}
		}
	}

//	public  void closePopupWindow(WebDriver driver, KeywordModel keywordModel) {
//		closeAllOtherWindows();
//		driver.switchTo().window(WebDriverHelper.mainWindowHandle);
//	}
//	
	
	/**
	 * Method Name: scrollDown Return Type: Nothing Description: This method scrolls down the bottm of page
	 * 
	 */
	public  void scrollDown(WebDriver driver, KeywordModel keywordModel) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		//This will scroll the web page till end.		
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	/**
	 * Method Name: verifyElementTitle Return Type: Nothing Description: This method
	 * verifies for the title attribute of the element
	 */
	public  void verifyElementTitle(WebDriver driver, KeywordModel keywordModel) {
		if (keywordModel.checkBoolean.equalsIgnoreCase("")) {
			if (findElementByType(driver, keywordModel).getAttribute("title").equals(keywordModel.dataValue)) {
				ReportUtilities.Log(driver,"Verifying the title of the object " + keywordModel.objectName,
						"The element " + keywordModel.objectName + " has the title " + keywordModel.dataValue, Status.PASS , keywordModel);
			} else {
				ReportUtilities.Log(driver,"Verifying the title of the object " + keywordModel.objectName,
						"The element " + keywordModel.objectName + " does not have the title " + keywordModel.dataValue,
						Status.FAIL , keywordModel);
			}
		}
	}

	/**
	 * Method Name: verifyRequiredField Return Type: Nothing Description: This
	 * method verifies for the required fields src attribute
	 */
	public  void verifyRequiredField(WebDriver driver, KeywordModel keywordModel) {
		if (keywordModel.checkBoolean.equalsIgnoreCase("")) {
			if (findElementByType(driver, keywordModel).getAttribute("src").endsWith(keywordModel.dataValue)) {
				ReportUtilities.Log(driver,"Verify required field: " + keywordModel.objectName,
						"The element " + keywordModel.objectName + " is required ", Status.PASS , keywordModel);
			} else {
				ReportUtilities.Log(driver,"Verify required field: " + keywordModel.objectName,
						"The element " + keywordModel.objectName + " is not a required field " + keywordModel.dataValue,
						Status.FAIL , keywordModel);
			}
		}
	}
	
	
	/*
	 * Method get all the records in grids
	 * Eg : Number of individuals on case summary page
	 */
	public int getRecordsInGrid(WebDriver driver, KeywordModel keywordModel)
	{
		int recordCount=0;
		try {
			if (findElementByType(driver, keywordModel).isDisplayed()) {
				recordCount =  findElementsByType(driver, keywordModel).size();
				System.out.println("record count is "+recordCount);
				ReportUtilities.Log(driver,"Record Count  " + keywordModel.objectName , "is obtained", Status.PASS , keywordModel);

			}
		} catch (NoSuchElementException p) {
			keywordModel.error = true;
			keywordModel.displayError = true;
			ReportUtilities.Log(driver,"Cannot get count.", "The Element " + keywordModel.objectName
					+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
			throw new RuntimeException(p);
		}
		return recordCount;
	}
	
	
	
	
	
	/* Emp ID : 506096
	 * Method is for selecting dropdowns for N number of individuals dynamically
	 */
	public  void selectByVisibleTextDyna(String xPath, WebDriver driver, KeywordModel keywordModel) throws InterruptedException {
		try {
			WebElement selectInputBox = driver.findElement(By.xpath(xPath));
			if (selectInputBox.isDisplayed()) {
				selectInputBox.click();
				selectInputBox.sendKeys(Keys.CONTROL + "a");
				selectInputBox.sendKeys(Keys.DELETE); 

				selectInputBox.sendKeys(keywordModel.dataValue);
				Thread.sleep(1000);
				selectInputBox.sendKeys(Keys.TAB);
				ReportUtilities.Log(driver,"Selecting by visible text in the dropdown " + keywordModel.objectName,
						"Selected the value " + keywordModel.dataValue + " in the select box ", Status.PASS , keywordModel);
			}
		} catch (NoSuchElementException p) {
			keywordModel.error = true;
			keywordModel.displayError = true;
			ReportUtilities.Log(driver,"Cannot select any value on the object.", "The input box for " + keywordModel.objectName
					+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
			throw new RuntimeException(p);
		}
	}

	/* Emp ID : 506096
	 * Method is for entering date and text for N number of individuals dynamically
	 */
	public  void enterDyna(String xPath, WebDriver driver, KeywordModel keywordModel) throws InterruptedException {
		try {
			WebElement enterInputBox = driver.findElement(By.xpath(xPath));

			if (enterInputBox.isDisplayed()) {
				enterInputBox.click();
				enterInputBox.sendKeys(Keys.CONTROL + "a");
				enterInputBox.sendKeys(Keys.DELETE); 

				Thread.sleep(1500);
				enterInputBox.sendKeys(keywordModel.dataValue);

				ReportUtilities.Log(driver,"Entering text in the text box " + keywordModel.objectName,
						"Entered the text " + keywordModel.dataValue, Status.PASS , keywordModel);
			}
		} catch (NoSuchElementException p) {
			keywordModel.error = true;
			keywordModel.displayError = true;
			ReportUtilities.Log(driver,"Cannot enter text on the object.", "The Element " + keywordModel.objectName
					+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
			throw new RuntimeException(p);
		}
	}





//	/*Method does fills all mandatory info on DC_Individual screen for AP CV cases
//	 */
//
//	public  void selectByVisibleTextDynaInd(WebDriver driver, KeywordModel keywordModel) throws InterruptedException {
//		String xpathInd= "";
//		String xpathIndSSNVerDate="";
//		String xpathSSNVerification="";
//		String xpathLastName="";
//		String xpathProgramName="//input[@id='casepgm_ProgramCode_0']";
//		String xpathFederalRecognizedTribe="";
//		String xpathAIAN="";
//		String xpathTribalHealthProgram="";
//		String xpathLegallyEmanciapted="";
//		String xpathGrandparentAlaskaNative ="";
//
//		int countOfIndividuals = getRecordsInGrid(driver, keywordModel);
//		try {
//			int i=0;
//
//			String programName = driver.findElement(By.xpath(xpathProgramName)).getAttribute("value");
//			for( i=0;i<countOfIndividuals;i++)
//			{	
//				xpathIndSSNVerDate =	"//input[@name='Individuals["+i+"].SsnVerificationDate']";
//				xpathFederalRecognizedTribe="//select[@name='Individuals["+i+"].MemberOfFederalRecognizedTribe']/preceding-sibling::span/child::input";
//				xpathAIAN="//select[@name='Individuals["+i+"].IsIndividualAIAN']/preceding-sibling::span/child::input";
//				xpathTribalHealthProgram = "//select[@name='Individuals["+i+"].ReceivedServicesFromTibeHealthProgram']/preceding-sibling::span/child::input";
//				xpathLegallyEmanciapted="//select[@name='Individuals["+i+"].IsIndividualLegallyEmancipated']/preceding-sibling::span/child::input";
//				xpathGrandparentAlaskaNative = "//select[@name='Individuals["+i+"].GrandparentMemberofFederallyRecognizedTribe']/preceding-sibling::span/child::input";
//				System.out.println("xpath" +xpathIndSSNVerDate );
//				xpathInd = "//input[@id='member_IndividualId_"+i+"']/preceding-sibling::div[contains(@class,'rpIndividualId')]";
//				xpathLastName = "//input[@name='Individuals["+i+"].LastName']";
//
//				xpathSSNVerification = "//select[@name='Individuals["+i+"].SsnVerification']/preceding-sibling::span/child::input";
//
//				if(i != 0)
//
//				{
//					driver.findElement(By.xpath(xpathInd)).click();
//					Thread.sleep(5000);
//					selectEnabledDropdowns(xpathLegallyEmanciapted);
//
//				}
//
//				driver.findElement(By.xpath(xpathSSNVerification)).click();
//				driver.findElement(By.xpath(xpathSSNVerification)).clear();
//				driver.findElement(By.xpath(xpathSSNVerification)).sendKeys("Social Security Card");
//				Thread.sleep(2000);
//				driver.findElement(By.xpath(xpathLastName)).click();
//
//
//				driver.findElement(By.xpath(xpathIndSSNVerDate)).click();
//				driver.findElement(By.xpath(xpathIndSSNVerDate)).sendKeys("01012018");
//				Thread.sleep(2000);
//				driver.findElement(By.xpath(xpathLastName)).click();
//
//
//				selectEnabledDropdowns(xpathAIAN);
//
//				selectEnabledDropdowns(xpathFederalRecognizedTribe);
//
//				if(programName.equalsIgnoreCase("MA"))
//				{
//					selectEnabledDropdowns(xpathTribalHealthProgram);
//
//					selectEnabledDropdowns(xpathGrandparentAlaskaNative);
//				}
//
//				ReportUtilities.Log(driver,"Selecting by visible text in the dropdown " + keywordModel.objectName,
//						"Selected the value " + keywordModel.dataValue + " in the select box ", Status.PASS , keywordModel);
//			}
//		}
//		catch (NoSuchElementException p) {
//			keywordModel.error = true;
//			keywordModel.displayError = true;
//			ReportUtilities.Log(driver,"Cannot select any value on the object.", "The input box for " + keywordModel.objectName
//					+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
//			throw new RuntimeException(p);
//		}
//	}
	
	
	
	public  void getDatafromRightPane(WebDriver driver, KeywordModel keywordModel) {
		WebElement webElement = findElementByType(driver, keywordModel);
		String elementValue = webElement.getText();
		System.out.println(elementValue);
		// elementValue.replace("/", "");
		if (elementValue.contains(" ")) {
			String[] elementval = elementValue.split(" ");
			elementValue = elementval[0] + " " + elementval[1];

			sessionManager.add(keywordModel.dataValue, elementValue, keywordModel);

		} else {
			// System.out.println("Application or Case Number 1: " + elementValue);
		sessionManager.add(keywordModel.dataValue, elementValue, keywordModel);
			// System.out.println("Element Text is : " + elementValue);
		}

	}

	public  void enter_RandomZIP(WebDriver driver, KeywordModel keywordModel) {
		try {
			if (findElementByType(driver, keywordModel).isDisplayed()) {

				findElementByType(driver, keywordModel).clear();
				String Chars = "0123456789";
				StringBuilder builder = new StringBuilder();
				int len = Integer.parseInt(keywordModel.dataValue);
				while (len-- != 0) {
					int character = (int) (Math.random() * Chars.length());
					builder.append(Chars.charAt(character));
				}
				findElementByType(driver, keywordModel).sendKeys(builder.toString());
				ReportUtilities.Log(driver,"Entering text in the text box " + keywordModel.objectName, "Entered the text " + keywordModel.dataValue,
						Status.PASS , keywordModel);
			}
		} catch (NoSuchElementException p) {
			keywordModel.error = true;
			keywordModel.displayError = true;
			ReportUtilities.Log(driver,"Cannot enter Data on the object.",
					"The Element " + keywordModel.objectName + " is NOT displayed on the current screen" + keywordModel.ScreenName,
					Status.FAIL , keywordModel);
			throw new RuntimeException(p);
		}

	}

	public  void EnterCurrentMonthDate(WebDriver driver, KeywordModel keywordModel) throws Exception {
		String DD = keywordModel.dataValue;
		if (keywordModel.dataValue.length() < 8) {
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			String GetDate = jsExecutor.executeScript("return window.ApplicationDate").toString();
			String MM = "";
			String YYYY = "";
			String MMDDYYYY = "";
			String[] CurrentDate = GetDate.split(" ")[0].split("/");

			for (int i = 0; i <= CurrentDate.length - 1; i++) {
				MM = CurrentDate[0];
				YYYY = CurrentDate[2];
				int CurrentMonth = Integer.parseInt(MM);
				int CurrentYear = Integer.parseInt(YYYY);

			}
			MMDDYYYY = MM + DD + YYYY;
			if (MMDDYYYY.length() < 8) {
				MMDDYYYY = new StringBuilder(MMDDYYYY).insert(MMDDYYYY.length() - 7, "0").toString();
			}

			if (keywordModel.dynaElement != null) {
				try {
					if (keywordModel.dynaElement.isDisplayed()) {
						keywordModel.dynaElement.clear();
						keywordModel.dynaElement.sendKeys(MMDDYYYY);
						ReportUtilities.Log(driver,"Entering CurrentMonth Date  in the text box, The Value is Pass :" + MMDDYYYY,
								"Entered the Current Date+ " + MMDDYYYY, Status.PASS , keywordModel);
					}
				} catch (NoSuchElementException p) {
					keywordModel.error = true;
					keywordModel.displayError = true;
					ReportUtilities.Log(driver,"Cannot enter data on the object.", "The Element " + keywordModel.objectName
							+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
					throw new RuntimeException(p);
				}
			} else {
				try {
					if (findElementByType(driver, keywordModel).isDisplayed()) {
						findElementByType(driver, keywordModel).clear();
						findElementByType(driver, keywordModel).sendKeys(MMDDYYYY);
						ReportUtilities.Log(driver,"Entering CurrentMonth in the text box,, The Value is Fail : " + MMDDYYYY,
								"Entered the Current Date " + MMDDYYYY, Status.PASS , keywordModel);
					}
				} catch (NoSuchElementException p) {
					keywordModel.error = true;
					keywordModel.displayError = true;
					ReportUtilities.Log(driver,"Cannot enter data on the object.", "The Element " + keywordModel.objectName
							+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
					throw new RuntimeException(p);
				}
			}

		} else {
			if (keywordModel.dynaElement != null) {
				try {
					if (keywordModel.dynaElement.isDisplayed()) {
						keywordModel.dynaElement.clear();
						keywordModel.dynaElement.sendKeys(keywordModel.dataValue);
						ReportUtilities.Log(driver,
								"Entering CurrentMonth Date  in the text box, The Value is Pass :" + keywordModel.dataValue,
								"Entered the Current Date+ " + keywordModel.dataValue, Status.PASS , keywordModel);
					}
				} catch (NoSuchElementException p) {
					keywordModel.error = true;
					keywordModel.displayError = true;
					ReportUtilities.Log(driver,"Cannot enter data on the object.", "The Element " + keywordModel.objectName
							+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
					throw new RuntimeException(p);
				}
			} else {
				try {
					if (findElementByType(driver, keywordModel).isDisplayed()) {

						findElementByType(driver, keywordModel).clear();
						findElementByType(driver, keywordModel).sendKeys(keywordModel.dataValue);
						ReportUtilities.Log(driver,"Entering CurrentMonth in the text box,, The Value is Fail : " + keywordModel.dataValue,
								"Entered the Current Date " + keywordModel.dataValue, Status.PASS , keywordModel);
					}
				} catch (NoSuchElementException p) {
					keywordModel.error = true;
					keywordModel.displayError = true;
					ReportUtilities.Log(driver,"Cannot enter data on the object.", "The Element " + keywordModel.objectName
							+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
					throw new RuntimeException(p);
				}
			}
		}
	}

	public  void EnterPriorMonthDate(WebDriver driver, KeywordModel keywordModel) throws Exception {
		String DD = keywordModel.dataValue;
		if (keywordModel.dataValue.length() < 8) {
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			String GetDate = jsExecutor.executeScript("return window.ApplicationDate").toString();
			String MM = "";
			String YYYY = "";
			String MMDDYYYY = "";
			String[] CurrentDate = GetDate.split(" ")[0].split("/");

			for (int i = 0; i <= CurrentDate.length - 1; i++) {
				MM = CurrentDate[0];
				YYYY = CurrentDate[2];
				int CurrentMonth = Integer.parseInt(MM);
				int CurrentYear = Integer.parseInt(YYYY);
				if (CurrentMonth == 01 || CurrentMonth == 1) {
					MM = "12";
					YYYY = Integer.toString(CurrentYear - 1);
				} else {
					MM = Integer.toString(CurrentMonth - 1);
				}
			}
			MMDDYYYY = MM + DD + YYYY;
			if (MMDDYYYY.length() < 8) {
				MMDDYYYY = new StringBuilder(MMDDYYYY).insert(MMDDYYYY.length() - 7, "0").toString();
			}

			if (keywordModel.dynaElement != null) {
				try {
					if (keywordModel.dynaElement.isDisplayed()) {
						keywordModel.dynaElement.clear();
						keywordModel.dynaElement.sendKeys(MMDDYYYY);
						ReportUtilities.Log(driver,"Entering PriorMonth Date  in the text box, The Value is Pass :" + MMDDYYYY,
								"Entered the Current Date+ " + MMDDYYYY, Status.PASS , keywordModel);
					}
				} catch (NoSuchElementException p) {
					keywordModel.error = true;
					keywordModel.displayError = true;
					ReportUtilities.Log(driver,"Cannot enter data on the object.", "The Element " + keywordModel.objectName
							+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
					throw new RuntimeException(p);
				}
			} else {
				try {
					if (findElementByType(driver, keywordModel).isDisplayed()) {
						findElementByType(driver, keywordModel).clear();
						findElementByType(driver, keywordModel).sendKeys(MMDDYYYY);
						ReportUtilities.Log(driver,"Entering PriorMonth in the text box,, The Value is Fail : " + MMDDYYYY,
								"Entered the Current Date " + MMDDYYYY, Status.PASS , keywordModel);
					}
				} catch (NoSuchElementException p) {
					keywordModel.error = true;
					keywordModel.displayError = true;
					ReportUtilities.Log(driver,"Cannot enter data on the object.", "The Element " + keywordModel.objectName
							+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
					throw new RuntimeException(p);
				}
			}
		}
		//		 else {
		//			if (keywordModel.dynaElement != null) {
		//				try {
		//					if (keywordModel.dynaElement.isDisplayed()) {
		//						keywordModel.dynaElement.clear();
		//						keywordModel.dynaElement.sendKeys(keywordModel.dataValue);
		//						ReportUtilities.Log(driver,"Entering PriorMonth Date  in the text box, The Value is Pass :" + keywordModel.dataValue,
		//								"Entered the Current Date+ " + keywordModel.dataValue, Status.PASS , keywordModel);
		//					}
		//				} catch (NoSuchElementException p) {
		//					keywordModel.error = true;
		//					keywordModel.displayError = true;
		//					ReportUtilities.Log(driver,"Cannot enter data on the object.", "The Element " + keywordModel.objectName
		//							+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
		//					throw new RuntimeException(p);
		//				}
		//			} else {
		//				try {
		//					if (findElementByType(driver, keywordModel).isDisplayed()) {
		//						findElementByType(driver, keywordModel).clear();
		//						findElementByType(driver, keywordModel).sendKeys(keywordModel.dataValue);
		//						ReportUtilities.Log(driver,"Entering PriorMonth in the text box,, The Value is Fail : " + keywordModel.dataValue,
		//								"Entered the Current Date " + keywordModel.dataValue, Status.PASS , keywordModel);
		//					}
		//				} catch (NoSuchElementException p) {
		//					keywordModel.error = true;
		//					keywordModel.displayError = true;
		//					ReportUtilities.Log(driver,"Cannot enter data on the object.", "The Element " + keywordModel.objectName
		//							+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
		//					throw new RuntimeException(p);
		//				}
		//			}
		//		}
	}
	// Database Validation Methods for Oregon

	public  void EnterNextMonthDate(WebDriver driver, KeywordModel keywordModel) throws Exception {

		String DD = keywordModel.dataValue;
		if (keywordModel.dataValue.length() < 8 && !DD.equalsIgnoreCase("01")) {
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			String GetDate = jsExecutor.executeScript("return window.ApplicationDate").toString();
			System.out.println(GetDate);
			String MM = "";
			String YYYY = "";
			String MMDDYYYY = "";
			String[] CurrentDate = GetDate.split(" ")[0].split("/");

			for (int i = 0; i <= CurrentDate.length - 1; i++) {
				MM = CurrentDate[0];
				YYYY = CurrentDate[2];
				int CurrentMonth = Integer.parseInt(MM);
				int CurrentYear = Integer.parseInt(YYYY);
				if (CurrentMonth == 12) {
					MM = "01";
					YYYY = Integer.toString(CurrentYear + 1);
				} else {
					MM = Integer.toString(CurrentMonth + 1);
					if (MM.equalsIgnoreCase("01")) {
						DD = "28";
					} else if (MM.equalsIgnoreCase("03") || MM.equalsIgnoreCase("05") || MM.equalsIgnoreCase("08")
							|| MM.equalsIgnoreCase("10")) {
						DD = "30";
					} else {
						DD = "30";
					}
				}
			}
			MMDDYYYY = MM + DD + YYYY;
			if (MMDDYYYY.length() < 8) {
				MMDDYYYY = new StringBuilder(MMDDYYYY).insert(MMDDYYYY.length() - 7, "0").toString();
			}

			if (keywordModel.dynaElement != null) {
				try {
					if (keywordModel.dynaElement.isDisplayed()) {
						keywordModel.dynaElement.clear();
						keywordModel.dynaElement.sendKeys(MMDDYYYY);
						ReportUtilities.Log(driver,"Entering NextMonth Date  in the text box, The Value is Pass :" + MMDDYYYY,
								"Entered the Current Date+ " + MMDDYYYY, Status.PASS , keywordModel);
					}
				} catch (NoSuchElementException p) {
					keywordModel.error = true;
					keywordModel.displayError = true;
					ReportUtilities.Log(driver,"Cannot enter data on the object.", "The Element " + keywordModel.objectName
							+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
					throw new RuntimeException(p);
				}
			} else {
				try {
					if (findElementByType(driver, keywordModel).isDisplayed()) {
						findElementByType(driver, keywordModel).clear();
						findElementByType(driver, keywordModel).sendKeys(MMDDYYYY);
						ReportUtilities.Log(driver,"Entering NextMonth Date in the text box,, The Value is Pass : " + MMDDYYYY,
								"Entered the Current Date " + MMDDYYYY, Status.PASS , keywordModel);
					}
				} catch (NoSuchElementException p) {
					keywordModel.error = true;
					keywordModel.displayError = true;
					ReportUtilities.Log(driver,"Cannot enter data on the object.", "The Element " + keywordModel.objectName
							+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
					throw new RuntimeException(p);
				}
			}

		} else if (keywordModel.dataValue.length() < 8 && DD.equalsIgnoreCase("01")) {

			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			String GetDate = jsExecutor.executeScript("return window.ApplicationDate").toString();
			String MM = "";
			String YYYY = "";
			String MMDDYYYY = "";
			String[] CurrentDate = GetDate.split(" ")[0].split("/");

			for (int i = 0; i <= CurrentDate.length - 1; i++) {
				MM = CurrentDate[0];
				YYYY = CurrentDate[2];
				int CurrentMonth = Integer.parseInt(MM);
				int CurrentYear = Integer.parseInt(YYYY);
				if (CurrentMonth == 12) {
					MM = "01";
					YYYY = Integer.toString(CurrentYear + 1);
				} else {
					MM = Integer.toString(CurrentMonth + 1);
				}
			}
			MMDDYYYY = MM + DD + YYYY;
			if (MMDDYYYY.length() < 8) {
				MMDDYYYY = new StringBuilder(MMDDYYYY).insert(MMDDYYYY.length() - 7, "0").toString();
			}

			if (keywordModel.dynaElement != null) {
				try {
					if (keywordModel.dynaElement.isDisplayed()) {
						keywordModel.dynaElement.clear();
						keywordModel.dynaElement.sendKeys(MMDDYYYY);
						ReportUtilities.Log(driver,"Entering NextMonth Date  in the text box, The Value is Pass :" + MMDDYYYY,
								"Entered the Current Date+ " + MMDDYYYY, Status.PASS , keywordModel);
					}
				} catch (NoSuchElementException p) {
					keywordModel.error = true;
					keywordModel.displayError = true;
					ReportUtilities.Log(driver,"Cannot enter data on the object.", "The Element " + keywordModel.objectName
							+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
					throw new RuntimeException(p);
				}
			} else {
				try {
					if (findElementByType(driver, keywordModel).isDisplayed()) {
						findElementByType(driver, keywordModel).clear();
						findElementByType(driver, keywordModel).sendKeys(MMDDYYYY);
						ReportUtilities.Log(driver,"Entering NextMonth Date in the text box,, The Value is Pass : " + MMDDYYYY,
								"Entered the Current Date " + MMDDYYYY, Status.PASS , keywordModel);
					}
				} catch (NoSuchElementException p) {
					keywordModel.error = true;
					keywordModel.displayError = true;
					ReportUtilities.Log(driver,"Cannot enter data on the object.", "The Element " + keywordModel.objectName
							+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
					throw new RuntimeException(p);
				}
			}

		} else {
			if (keywordModel.dynaElement != null) {
				try {
					if (keywordModel.dynaElement.isDisplayed()) {
						keywordModel.dynaElement.clear();
						keywordModel.dynaElement.sendKeys(keywordModel.dataValue);
						ReportUtilities.Log(driver,"Entering NextMonth Date  in the text box, The Value is Pass :" + keywordModel.dataValue,
								"Entered the Current Date+ " + keywordModel.dataValue, Status.PASS , keywordModel);
					}
				} catch (NoSuchElementException p) {
					keywordModel.error = true;
					keywordModel.displayError = true;
					ReportUtilities.Log(driver,"Cannot enter data on the object.", "The Element " + keywordModel.objectName
							+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
					throw new RuntimeException(p);
				}
			} else {
				try {
					if (findElementByType(driver, keywordModel).isDisplayed()) {
						findElementByType(driver, keywordModel).clear();
						findElementByType(driver, keywordModel).sendKeys(keywordModel.dataValue);
						ReportUtilities.Log(driver,"Entering NextMonth Date in the text box,, The Value is Pass : " + keywordModel.dataValue,
								"Entered the Current Date " + keywordModel.dataValue, Status.PASS , keywordModel);

					}
				} catch (NoSuchElementException p) {
					keywordModel.error = true;
					keywordModel.displayError = true;
					ReportUtilities.Log(driver,"Cannot enter data on the object.", "The Element " + keywordModel.objectName
							+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
					throw new RuntimeException(p);
				}
			}
		}
	}

//	public  void writeToOutputFile(String Casenumber, WebDriver driver, KeywordModel keywordModel) {
//
//		String OutputFilewithCaceNumbers = Report.defectLogFolder + File.separatorChar + "ScenarioData.txt";
//		File f = new File(OutputFilewithCaceNumbers);
//		if (!f.exists()) {
//			try {
//				f.createNewFile();
//				FileWriter writer = new FileWriter(OutputFilewithCaceNumbers, true);
//				writer.write(keywordModel.ScenarioNameTostore + ": " + Casenumber);
//				writer.write("\r\n"); // write new line
//				writer.close();
//
//			} catch (Exception e) {
//				logger.log(Level.WARNING, "Error while creating ScenarioData file", e);
//			}
//		} else {
//			try {
//				FileWriter writer = new FileWriter(OutputFilewithCaceNumbers, true);
//				writer.write(keywordModel.ScenarioNameTostore + ": " + Casenumber);
//				writer.write("\r\n"); // write new line
//				writer.close();
//			} catch (Exception e) {
//				logger.log(Level.WARNING, "Error while writing ScenarioData into file", e);
//			}
//		}
//
//	}

	public  void ElementsCount(WebDriver driver, KeywordModel keywordModel) {

		if (findElementsByType(driver, keywordModel).size() != 0) {
			List a = findElementsByType(driver, keywordModel);
			sessionManager.add(keywordModel.dataValue, findElementsByType(driver, keywordModel).size(), keywordModel);
			System.out.println("Session has : " + findElementsByType(driver, keywordModel).size() + "in Key: " + keywordModel.dataValue);
			ReportUtilities.Log(driver,"Storing the data in session: ", Integer.toString(findElementsByType(driver, keywordModel).size()), Status.PASS , keywordModel);
		} else {
			sessionManager.add(keywordModel.dataValue, findElementsByType(driver, keywordModel).size(), keywordModel);
			System.out.println("Session has : " + findElementsByType(driver, keywordModel).size() + "in Key: " + keywordModel.dataValue);
			ReportUtilities.Log(driver,"Storing the data in session: ", Integer.toString(findElementsByType(driver, keywordModel).size()), Status.PASS , keywordModel);
		}
	}

	/*Method Selects the date from the TT screen
	 *creator : hariom sinha 557173
	 * 
	 */
//	public  void selectTT(WebDriver driver, KeywordModel keywordModel)
//	{
//
//
//
//
//		String dataValue = keywordModel.dataValue;	
//		String TTXpath = "//div[contains(text(),'" + dataValue + "')]/preceding-sibling::div[contains(@class,'30percent')]/input";
//		String TTNextXpath = "//div[contains(text(),'" + dataValue + "')]/following-sibling::div[contains(@class,'15percent')]//input";
//
//		try
//		{
//			driver.findElement(By.xpath(TTXpath)).click();
//			driver.findElement(By.xpath(TTNextXpath)).click();
//			ReportUtilities.Log(driver,"Selecting TT , The Value selected is :" + dataValue,
//					"Selected the TTDate+ " + dataValue, Status.PASS , keywordModel);
//		}
//
//		catch(Exception e)
//		{
//			System.out.println("Exception occured while SelectTT. Exception : "+e);
//			ReportUtilities.Log(driver,"Failed Selecting TT , The Value selected is :" + dataValue,
//					"TTDate failed to get selected + " + dataValue, Status.FAIL , keywordModel);
//		}
//	}

	

	/*Method Calculates the date with base date and offset as parameters and verifies the date with UI.
	 * Business Use : Verifying RFI due date
	 *creator : Ronak Shah 506096
	 * 
	 */
	public  void verifyDate(WebDriver driver, KeywordModel keywordModel) throws InterruptedException
	{
		int year = Integer.valueOf(KeywordUtilities.getValueFromConfigProperties("Year"));
		int mm = Integer.valueOf(KeywordUtilities.getValueFromConfigProperties("Month"));
		int dd = Integer.valueOf(KeywordUtilities.getValueFromConfigProperties("Date"));
		int offset = Integer.valueOf(keywordModel.dataValue);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c1 = Calendar.getInstance();
		c1.set(year, mm , dd); // 1999 jan 20

		c1.add(Calendar.DATE,offset);   // or  Calendar.DAY_OF_MONTH which is a synonym

		String finalDate = sdf.format(c1.getTime());
		String date = findElementByType(driver, keywordModel).getText();
		try {

			if (finalDate.equals(date)) 
			{
				System.out.println("Verified date , The date on UI is :" + date + "Expected date is " +finalDate);	
				ReportUtilities.Log(driver,"Verifying date, The Date on UI is :" + date,
						"Expected date is Verified" + finalDate, Status.PASS , keywordModel);
			}
		
		
		else 
		{
			System.out.println("Failed verfying date , The date on UI is :" + date + "Expected date is " +finalDate);
			ReportUtilities.Log(driver,"Failed verfying date , The date selected is :" + date,
					"Expected date is + " + finalDate, Status.FAIL , keywordModel);
		storeEDMResult(driver, keywordModel);
		}
			
	}

		catch(Exception e)
		{
			System.out.println("Failed verfying date , The date on UI is :" + date + "Expected date is " +finalDate +"Exception : "+e);
			ReportUtilities.Log(driver,"Failed verfying date , The date selected is :" + date,
					"Expected date is + " + finalDate, Status.FAIL , keywordModel);
		driver.wait();
		}
	}


	/*Method Calculates the date with base date and offset as parameters and verifies the date with UI.
	 * Offset is passed as an argument 
	 * Business Use : Verifying RFI due date
	 *creator : Ronak Shah 506096
	 * 
	 */
	public  String verifyAPRFIDate(String Offset, WebDriver driver, KeywordModel keywordModel)
	{
		int year = Integer.valueOf(KeywordUtilities.getValueFromConfigProperties("Year"));
		int mm = Integer.valueOf(KeywordUtilities.getValueFromConfigProperties("Month"));
		int dd = Integer.valueOf(KeywordUtilities.getValueFromConfigProperties("Date"));
		int offset = Integer.valueOf(KeywordUtilities.getValueFromConfigProperties(Offset));
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c1 = Calendar.getInstance();
		c1.set(year, mm , dd); // 1999 jan 20

		c1.add(Calendar.DATE,offset);   // or  Calendar.DAY_OF_MONTH which is a synonym

		String finalDate = sdf.format(c1.getTime());

		return finalDate;
	}
	
	
	public void verifyElementPartialText(WebDriver driver, KeywordModel keywordModel)

	{
		String str = keywordModel.dataValue.split(" ")[0];
		if (keywordModel.dynaElement != null) {
			try {
				if (keywordModel.dynaElement.isDisplayed()) {
					if (keywordModel.checkBoolean.equalsIgnoreCase("")) {
						String str1 = keywordModel.dynaElement.getText().toLowerCase();
						// System.out.println("Element Text: "+str1);
						// System.out.println("Sheet data: "+str);
						if (str1.contains(str.toLowerCase())) {
							ReportUtilities.Log(driver,"Verifying the Text of the object " + keywordModel.objectName,
									"The element " + keywordModel.objectName + " contains the text " + keywordModel.dataValue,
									Status.PASS , keywordModel);
						} else {
							ReportUtilities.Log(driver,"Verifying the Text of the object " + keywordModel.objectName,
									"The element " + keywordModel.objectName
									+ " does not contain the text expected. Value found is : " + str1,
									Status.FAIL , keywordModel);
						}
					}
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot Verify text of the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		} else {
			try {
				if (findElementByType(driver, keywordModel).isDisplayed()) {
					if (keywordModel.checkBoolean.equalsIgnoreCase("")) {
						String stri = findElementByType(driver, keywordModel).getText().toLowerCase();
						if (stri.contains(str.toLowerCase())) {
							ReportUtilities.Log(driver,"Verifying the Text of the object " + keywordModel.objectName,
									"The element " + keywordModel.objectName + " contains the text " + keywordModel.dataValue,
									Status.PASS , keywordModel);
						} else {
							ReportUtilities.Log(driver,"Verifying the Text of the object " + keywordModel.objectName,
									"The element " + keywordModel.objectName
									+ " does not contain the text expected. Value found is : " + stri,
									Status.FAIL , keywordModel);
						}
					}
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot Verify text of the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		}
	}

	/**
	 * Method Name : pollForElement Return Type: Nothing Description: This
	 * method keeps polling until element is located.
	 */
	
//	private   void selectMultipleCheckbox(List webElements, String[] value,WebDriver driver, KeywordModel keywordModel) {
//		for (int index = 0; index < value.length; index++) {
//			int elementIndex = Integer.parseInt(value[index]);
//			RemoteWebElement element = ((RemoteWebElement) webElements.get(elementIndex - 1));
//			element.click();
//		}
//		ReportUtilities.Log(driver,"Selected multiple check box with index ", value.toString(), Status.PASS , keywordModel);
//	}

	
	public void hover(WebDriver driver, KeywordModel keywordModel){

		Actions action = new Actions(driver);
		action.moveToElement(findElementByType(driver, keywordModel)).build().perform();

	} 
	/**
	 * Method Name: storeSessionData Return Type: Nothing Description: Stores the
	 * session data for the given KeyInData value
	 */
	public void storeSessionData(WebDriver driver, KeywordModel keywordModel) {
		try {
			if (findElementByType(driver, keywordModel).isDisplayed()) {
				WebElement webElement = findElementByType(driver, keywordModel);
				String elementValue = webElement.getText();
				// elementValue = elementValue.replace("/", "");
			//	elementValue = elementValue.replace("Case: ", "").trim();
			//	elementValue = elementValue.replace("Application: ", "").trim();
				/*if (elementValue.contains("$")) {
					elementValue=elementValue.replace("$","");// For Storing SSN from UI
				}*/
			sessionManager.add(keywordModel.dataValue, elementValue, keywordModel);
				System.out.println("Session has : " + elementValue + "in Key: " + keywordModel.dataValue);
				ReportUtilities.Log(driver,"Storing the data in session: ", elementValue, Status.PASS , keywordModel);
			}
		} catch (NoSuchElementException p) {
			keywordModel.error = true;
			keywordModel.displayError = true;
			ReportUtilities.Log(driver,"Cannot store data in session from the object.",
					"The Element " + keywordModel.objectName + " is NOT displayed on the current screen" + keywordModel.ScreenName,
					Status.FAIL , keywordModel);
			throw new RuntimeException(p);
		}
	}

	/**
	 * Method Name: storeNameInSessionData Return Type: Nothing Description: Stores
	 * the session data for the given KeyInData value
	 */
	public   void storeNameInSessionData(WebDriver driver, KeywordModel keywordModel) {
		try {
			if (findElementByType(driver, keywordModel).isDisplayed()) {
				WebElement webElement = findElementByType(driver, keywordModel);
				String elementValue = webElement.getText();
				List<String> TempName = Arrays.asList(elementValue.split(" "));
				String FullName = TempName.get(0).toString() + " " + TempName.get(1).toString();
				System.out.println(FullName);
				sessionManager.add(keywordModel.dataValue, FullName, keywordModel);
				// System.out.println("Session has : "+elementValue);
				ReportUtilities.Log(driver,"Storing the Name in session: ", FullName, Status.PASS , keywordModel);
			}
		} catch (NoSuchElementException p) {
			keywordModel.error = true;
			keywordModel.displayError = true;
			ReportUtilities.Log(driver,"Cannot store data in session from the object.",
					"The Element " + keywordModel.objectName + " is NOT displayed on the current screen" + keywordModel.ScreenName,
					Status.FAIL , keywordModel);
			throw new RuntimeException(p);
		}
	}

	public void storeSheetDataInSession(WebDriver driver, KeywordModel keywordModel) {
		System.out.println("Sheet has: " + keywordModel.dataValue);
		sessionManager.add("temp", keywordModel.dataValue, keywordModel);
		ReportUtilities.Log(driver,"Storing the data in session: ", keywordModel.dataValue, Status.PASS , keywordModel);
	}

	public   void storeOtherSheetDataInSession(WebDriver driver, KeywordModel keywordModel) {
		System.out.println("Sheet has: " + keywordModel.dataValue);
		sessionManager.add("tempValue", keywordModel.dataValue, keywordModel);
		ReportUtilities.Log(driver,"Storing the data in session: ", keywordModel.dataValue, Status.PASS , keywordModel);
		System.out.println("Storing " + keywordModel.dataValue + "In session key tempvalue");
	}

	public   void storeSheetDateInSession(WebDriver driver, KeywordModel keywordModel) {
		System.out.println("Sheet has: " + keywordModel.dataValue);
		sessionManager.add("tempDate", keywordModel.dataValue, keywordModel);
		ReportUtilities.Log(driver,"Storing the date in session: ", keywordModel.dataValue, Status.PASS , keywordModel);
		System.out.println("Storing " + keywordModel.dataValue + "In session key tempvalue");
	}

	/**
	 * Method Name: storePrepopulatedSessionData Return Type: Nothing Description:
	 * Stores the session data for the given KeyInData value which is Pre populated
	 * on page Load, ie, has the text to be stored in its Value attribute.
	 */
	public   void storePrepopulatedSessionData(WebDriver driver, KeywordModel keywordModel) {
		if (keywordModel.dynaElement != null) {
			try {
				if (keywordModel.dynaElement.isDisplayed()) {
					String elementValue = keywordModel.dynaElement.getAttribute("value");

					elementValue = elementValue.replace("/", "");
				sessionManager.add(keywordModel.dataValue, elementValue, keywordModel);
					System.out.println("Stored session data: " + elementValue);
					ReportUtilities.Log(driver,"Storing the data in session: ", elementValue, Status.PASS , keywordModel);
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot store data in session from the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		}

		else {
			try {
				if (findElementByType(driver, keywordModel).isDisplayed()) {
					String elementValue = findElementByType(driver, keywordModel).getAttribute("value");
					elementValue = elementValue.replace("/", "");
				sessionManager.add(keywordModel.dataValue, elementValue, keywordModel);
					System.out.println("Stored session data: " + elementValue);
					ReportUtilities.Log(driver,"Storing the data in session: ", elementValue, Status.PASS , keywordModel);
				}
			}

			catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot store data in session from the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		}
	}

	public   void storePrepopulatedData(WebDriver driver, KeywordModel keywordModel) {
		if (keywordModel.dynaElement != null) {
			try {
				if (keywordModel.dynaElement.isDisplayed()) {
					String elementValue = keywordModel.dynaElement.getAttribute("value");
				sessionManager.add(keywordModel.dataValue, elementValue, keywordModel);
					System.out.println("Stored session data: " + elementValue);
					ReportUtilities.Log(driver,"Storing the data in session: ", elementValue, Status.PASS , keywordModel);
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot store data in session from the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		} else {
			try {
				if (findElementByType(driver, keywordModel).isDisplayed()) {
					String elementValue = findElementByType(driver, keywordModel).getAttribute("value");
				sessionManager.add(keywordModel.dataValue, elementValue, keywordModel);
					System.out.println("Stored session data: " + elementValue);
					ReportUtilities.Log(driver,"Storing the data in session: ", elementValue, Status.PASS , keywordModel);
				}
			}

			catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot store data in session from the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		}
	}
	
	public void storeAttributeDataByValue(WebDriver driver, KeywordModel keywordModel) {
		
		if (keywordModel.dynaElement != null) {
			try {
				if (keywordModel.dynaElement.isDisplayed()) {
					keywordModel.textByValue = keywordModel.dynaElement.getAttribute("value");
					System.out.println("Stored data: " + keywordModel.textByValue);
					ReportUtilities.Log(driver,"Storing the data : ", keywordModel.textByValue, Status.PASS , keywordModel);
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot store data from the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		} else {
			try {
				if (findElementByType(driver, keywordModel).isDisplayed()) {
					keywordModel.textByValue = findElementByType(driver, keywordModel).getAttribute("value");
					sessionManager.add(keywordModel.dataValue, keywordModel.textByValue, keywordModel);
					System.out.println("Stored data: " + keywordModel.textByValue);
					ReportUtilities.Log(driver,"Storing the data in session: ", keywordModel.textByValue, Status.PASS , keywordModel);
				}
			}

			catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot store data from the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		}
	}
	
	public void verifyDynamicText(WebDriver driver, KeywordModel keywordModel) {
		String webDynaText = null;
		String expText = null;
		String sessionVar = null;
		String DynaText = null;
		String[] textArray =null;
		textArray = keywordModel.dataValue.split(":"); 
		for (int i=0;i<textArray.length;i++)
		{
			sessionVar = "session:"+textArray[0];
		}
		if (keywordModel.dynaElement != null) {
			try {
				if (keywordModel.dynaElement.isDisplayed()) {
					webDynaText = keywordModel.dynaElement.getText();
					if(!keywordModel.dataValue.isEmpty() && keywordModel.dataValue.contains("session:"))
					{
						DynaText = verifyAndAssignDataValue(sessionVar, keywordModel);
						expText = keywordModel.dataValue.replaceAll(sessionVar, DynaText);
						if(expText.equalsIgnoreCase(webDynaText))
						{
							System.out.println("Dynamic text validation is passed " + expText);
							ReportUtilities.Log(driver,"Dynamic text validation is passed for object " + keywordModel.objectName,
									"Expected text is "+expText +" Actual text is "+ webDynaText,Status.PASS , keywordModel);
						}
						
					}
					
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Dynamic text validation is failed for object " + keywordModel.objectName,
						"Expected text is "+expText +" Actual text is "+ webDynaText,Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		} else {
			try {
				if (findElementByType(driver, keywordModel).isDisplayed()) {
					webDynaText = findElementByType(driver, keywordModel).getText();
					if(!keywordModel.dataValue.isEmpty() && keywordModel.dataValue.contains("session:"))
					{
						DynaText = verifyAndAssignDataValue(sessionVar, keywordModel);
						expText = keywordModel.dataValue.replaceAll(sessionVar, DynaText);
						if(expText.equalsIgnoreCase(webDynaText))
						{
							System.out.println("Dynamic text validation is passed " + expText);
							ReportUtilities.Log(driver,"Dynamic text validation is passed for object " + keywordModel.objectName,
									"Expected text is "+expText +" Actual text is "+ webDynaText,Status.PASS , keywordModel);
						}
						
					}
				}
			}

			catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Dynamic text validation is failed for object " + keywordModel.objectName,
						"Expected text is "+expText +" Actual text is "+ webDynaText,Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		}
	}
	

	public   void storeSelectedDropdownValue(WebDriver driver, KeywordModel keywordModel) {
		if (keywordModel.dynaElement != null) {
			try {
				if (keywordModel.dynaElement.isDisplayed()) {
					Select selectBox = new Select(keywordModel.dynaElement);
					WebElement option = selectBox.getFirstSelectedOption();
					String defaultItem = option.getText();
					System.out.println("DD value: " + defaultItem);
					sessionManager.add(keywordModel.dataValue, defaultItem, keywordModel);
					System.out.println("Stored session data: " + defaultItem);
					ReportUtilities.Log(driver,"Storing the data in session: ", defaultItem, Status.PASS , keywordModel);
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot store data in session from the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		} else {
			try {
				if (findElementByType(driver, keywordModel).isDisplayed()) {
					Select selectBox = new Select(findElementByType(driver, keywordModel));
					WebElement option = selectBox.getFirstSelectedOption();
					String defaultItem = option.getText();
					System.out.println("DD value: " + defaultItem);
					sessionManager.add(keywordModel.dataValue, defaultItem, keywordModel);
					System.out.println("Stored session data: " + defaultItem);
					ReportUtilities.Log(driver,"Storing the data in session: ", defaultItem, Status.PASS , keywordModel);
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot store data in session from the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		}

	}
	
	/*
	 * Verify if the data has to be retrieved from session or not and
	 * return the data if it is in session else the value if it is not in session
	 */
	public String verifyAndAssignDataValue(String elementData ,KeywordModel keywordModel){
		if(elementData.startsWith("session:")){
			String key =  elementData.split(":")[1];
			Object obj = sessionManager.get(key,keywordModel);
			return (obj == null) ? "" : obj.toString();
		}else{
			return elementData;
		}
	}
	

	/**
	 * Method Name: clicks on different cases for multiple individuals on case-summary page Return Type: Nothing Description:
	 * @throws InterruptedException 
	 */


	/**
	 * Method Name: storeSessionData and Compare Return Type: Nothing Description:
	 * Stores the session data for the given KeyInData value
	 */
	public   void storeSessionDataAndCompare(WebDriver driver, KeywordModel keywordModel) {
		WebElement webElement = findElementByType(driver, keywordModel);
		String elementValue = webElement.getText();
		if ((elementValue).equals(keywordModel.sessionid)) {
			ReportUtilities.Log(driver,"Verifying the Selected Value of the object " + keywordModel.objectName,
					"The selected value for " + keywordModel.objectName + " is " + keywordModel.dataValue, Status.PASS , keywordModel);
		} else if ((elementValue).equals(keywordModel.applicationNumber)) {
			ReportUtilities.Log(driver,"Verifying the Selected Value of the object " + keywordModel.objectName,
					"The selected value for " + keywordModel.objectName + " is " + keywordModel.dataValue, Status.PASS , keywordModel);
		} else {
			ReportUtilities.Log(driver,"Verifying the Selected Value of the object " + keywordModel.objectName,
					"The selected value for " + keywordModel.objectName + " is not " + keywordModel.dataValue, Status.FAIL , keywordModel);
		}
	}

	/**
	 * Method Name: enterSSN1 Return Type: Nothing Description: Auto generates SSN
	 * based on user settings in the TestData tab. If the corresponding value in
	 * TestData tab is "auto" then it will auto generate the value otherwise it will
	 * use the value as is in TestData tab
	 */
//	public   void enterSSN1(WebDriver driver, KeywordModel keywordModel) {
//		setIfAutoSSN(3);
//		enter_text();
//	}
//
//	/**
//	 * Method Name: enterSSN2 Return Type: Nothing Description: Auto generates SSN
//	 * based on user settings in the TestData tab. If the corresponding value in
//	 * TestData tab is "auto" then it will auto generate the value otherwise it will
//	 * use the value as is in TestData tab
//	 */
//	public   void enterSSN2(WebDriver driver, KeywordModel keywordModel) {
//		setIfAutoSSN(2);
//		enter_text();
//	}
//
//	/**
//	 * Method Name: enterSSN3 Return Type: Nothing Description: Auto generates SSN
//	 * based on user settings in the TestData tab. If the corresponding value in
//	 * TestData tab is "auto" then it will auto generate the value otherwise it will
//	 * use the value as is in TestData tab
//	 */
//	public   void enterSSN3(WebDriver driver, KeywordModel keywordModel) {
//		setIfAutoSSN(4);
//		enter_text();
//	}

	private   int getSSN(int length,WebDriver driver, KeywordModel keywordModel) {
		if (length == 3) {
			return getRandomNum(100, 999, driver, keywordModel);
		} else if (length == 2) {
			return getRandomNum(10, 99, driver, keywordModel);
		} else if (length == 4) {
			return getRandomNum(1000, 9999, driver, keywordModel);
		} 
		return 0;
	}

	private   int generateNum(int x,WebDriver driver, KeywordModel keywordModel) {
		return getRandomNum(1, x, driver, keywordModel);
	}

	

	private   int getRandomNum(int aStart, int aEnd,WebDriver driver, KeywordModel keywordModel) {
		if (aStart > aEnd) {
			throw new IllegalArgumentException("Start cannot exceed End value for SSN range.");
		}
		Random aRandom = new Random();
		// get the range, casting to long to avoid overflow problems
		long range = (long) aEnd - (long) aStart + 1;
		// compute a fraction of the range, 0 <= frac < range
		long fraction = (long) (range * aRandom.nextDouble());
		int randomNumber = (int) (fraction + aStart);

		return randomNumber;

	}

	/**
	 * Method Name: verifySelectedValue Return Type: Nothing Description: This
	 * method verifies for the selected value in the drop down
	 */

	public   void verifySelectedValue(WebDriver driver, KeywordModel keywordModel) {
		Select selectBox = new Select(findElementByType(driver, keywordModel));
		WebElement selectedValue = selectBox.getFirstSelectedOption();
		if ((keywordModel.dataValue).equals(selectedValue.getText())) {
			ReportUtilities.Log(driver,"Verifying the Selected Value of the object " + keywordModel.objectName,
					"The selected value for " + keywordModel.objectName + " is " + keywordModel.dataValue, Status.PASS , keywordModel);
		} else {
			ReportUtilities.Log(driver,"Verifying the Selected Value of the object " + keywordModel.objectName,
					"The selected value for " + keywordModel.objectName + " is not " + keywordModel.dataValue, Status.FAIL , keywordModel);
		}
	}

	
	/**
	 * Method Name: enterSystemDate Return Type: Nothing Description: Auto generates
	 * Day, Month, Year based on user inputs in the TestData tab. If the
	 * corresponding value in TestData tab is "mm;0" or "dd;0" or "yy;0" then it
	 * will auto generate the date value based on the Day or Month or Year format
	 * and it will be given as For Past Dates : [mm;-1/dd;-1/yy;-1] For Future Dates
	 * : [mm;1/dd;1/yy;1] where -1/+1 implies the difference with from the current
	 * time else the data in the TestData tab will be used
	 */
//
//	public   void enterSystemDate(WebDriver driver, KeywordModel keywordModel) {
//		validateAndSetSystemDate();
//		enter_text();
//	}


	/**
	 * Method Name : verifyMedicalEDM Return Type : Nothing Description : This
	 * method is used to verify medical eligibility results on eligibility summary page 
		 which takes medical edm records as locator and EDM's as datavalue
	 * @throws InterruptedException 
       */
       
//       public void verifyMedicalEDM(WebDriver driver, KeywordModel keywordModel) throws InterruptedException 
//       {
//        String dataValue = keywordModel.dataValue;
//        String indivName = "";
//        
//        String[] indivEligibilityDetails = null;
//        String status = "";
//        String toa = "";
//        String beginDt = "";
//        String endDt = "";
//        int num = 100;
//       
//        String[] indivDetail = null;
//        
//        String calBeginDate = "";
//        String calEndDate = "";
//        
//        try
//        {
//            if (dataValue.contains(";"))
//            {
//                indivEligibilityDetails = dataValue.split(";");
//            }
//            else
//            {
//                indivEligibilityDetails = new String[1];
//                indivEligibilityDetails[0] = dataValue;
//            }
//
//
//			int count = findElementsByType(driver, keywordModel).size();
//
//
//			int indSize = driver.findElements(By.xpath("//div[contains(@class,'rpIndividualName')]")).size();
//
//
//			int[] temp = new int[count];
//			for (int l = 0; l < count; l++)
//			{
//				temp[l] = num;
//				num = num + 1;
//			}
//
//			int x = 0;
//			int j = 0;
//
//			for (j = 1; j <= count; j++)
//			{
//
//				indivName = driver.findElement(By.xpath("(//div[contains(@class,'MedicalGrid')]//div[4])[" + j + "]")).getText();
//
//
//				for (int k = 1; k <= indSize; k++)
//				{
//					String ind_name = driver.findElement(By.xpath("(//div[contains(@class,'rpIndividualName')])[" + k + "]")).getText();
//
//					if (ind_name.contains(indivName) /*&& indivDetail[0].Equals("Indiv"+k+"")*/)
//					{
//						x = j;
//						for (int i = 0, z = 0; i < count && z < temp.length; i++, z++)
//						{
//							indivDetail = indivEligibilityDetails[i].split("\\|");
//
//							if (indivDetail[0].equals("Indiv" + k + "") && temp[z] != i)
//							{
//								temp[z] = i;
//
//								break;
//							}
//						}
//						break;
//					}
//
//
//				}
//
//
//
//                toa = driver.findElement(By.xpath("(//div[contains(@class,'MedicalGrid')]//div[3])[" + x + "]")).getText();
//               
//                beginDt = driver.findElement(By.xpath("(//div[contains(@class,'MedicalGrid')]//div[5])[" + x + "]")).getText();
//               
//                endDt = driver.findElement(By.xpath("(//div[contains(@class,'MedicalGrid')]//div[6])[" + x + "]")).getText();
//                
//                status = driver.findElement(By.xpath("(//div[contains(@class,'MedicalGrid')]//div[8])[" + x + "]")).getText();
//               
//            //    calBeginDate = calMABeginDate(indivDetail[3]);
//             //   calEndDate = calMAEndDate(indivDetail[4]);
//                
//                if (status.equals(indivDetail[1]))
//                {
//                    
//                    System.out.println("Eligibility Status is as expected, Eligibility Status for record " + j +"is "+status);
//                    ReportUtilities.Log(driver,"Verifying Status for record "+ j,"The eligibility Status are  as expected for record  " + j + " having text "  + status, Status.PASS , keywordModel);
//                }
//                else
//                {
//                    
//                    System.out.println("Eligibility Status is not as expected, Eligibility Status for record " + j + " on UI is "+status+"While expected Eligibility Status is"+indivDetail[1]);
//                    ReportUtilities.Log(driver,"Verifying Status for record " + j, "The eligibility Status is not as expected for record  " + j + " having text on UI as " + status+"while expected status is"+ indivDetail[1], Status.FAIL , keywordModel);
//                    driver.wait();
//                }
//                if (toa.equals(indivDetail[2]))
//                {
//                    System.out.println("TOA, for the record " + j + " are as expected");
//                    ReportUtilities.Log(driver,"Verifying TOA for record " + j, "The eligibility TOA are as expected for record  " + j + " having text " + toa, Status.PASS , keywordModel);
//                }
//                else
//                {
//                   
//                    System.out.println("TOA, for the record " + j + " has failed");
//                    ReportUtilities.Log(driver,"Verifying TOA for record " + j,"The eligibility TOA are not as expected for record  [" + j + "]having text " + toa, Status.FAIL , keywordModel);
//                    driver.wait();
//                }
//                if (beginDt.equals(indivDetail[3]))
//                {
//                   
//                    System.out.println("Begin Date for the record " + j + " are as expected");
//                    ReportUtilities.Log(driver,"Verifying Begin Date for record " + j,"The Begin Date are as expected for record  " + j + " having text " + beginDt, Status.PASS , keywordModel);
//                   
//                }
//                else
//                {
//                   
//                    System.out.println("Begin Date for the record " + j + " has failed");
//                    ReportUtilities.Log(driver,"Verifying Begin Date for record " + j, "The Begin Date are not as expected for record  " + j + " having text " + beginDt, Status.FAIL , keywordModel);
//                    driver.wait();
//                }
//                if (endDt.equals(indivDetail[4]))
//                {
//                    
//                    System.out.println("End Date for the record " + j + " are as expected");
//                    ReportUtilities.Log(driver,"Verifying End Date for record " + j, "The End Date are as expected for record  " + j + " having text " + endDt, Status.PASS , keywordModel);
//                }
//                else
//                {
//                    
//                    System.out.println(" End Date for the record " + j + " has failed");
//                    ReportUtilities.Log(driver,"Verifying End Date for record " + j, "The End Date are not as expected for record  " + j + " having text " + endDt, Status.FAIL , keywordModel);
//                    driver.wait();
//                }
//
//              
//
//            }
//            
//
//        }
//        catch (Exception e)
//        {
//             ReportUtilities.Log(driver,"Verifying the medical EDM records " + keywordModel.objectName , "The values are not displayed as expected", Status.FAIL , keywordModel);
//             driver.wait();
//        }
//
//    }
//       
       

	/**
	 * Method Name : verifyAuthSummaryMedicalEDM Return Type : Nothing Description : This
	 * method is used to verify medical eligibility results on authorization summary page 
 		  which requires EDM's as datavalue
     * @throws InterruptedException 
        */
        
//        public   void verifyAuthSummaryMedicalEDM(WebDriver driver, KeywordModel keywordModel) throws InterruptedException 
//        {
//         String dataValue = keywordModel.dataValue;
//         String indivName = "";
//         
//         String[] indivEligibilityDetails = null;
//         String status = "";
//         String toa = "";
//         String beginDt = "";
//         String endDt = "";
//         int num = 100;
//        
//         String[] indivDetail = null;
//        
//     
//         
//         try
//         {
//             if (dataValue.contains(";"))
//             {
//                 indivEligibilityDetails = dataValue.split(";");
//             }
//             else
//             {
//                 indivEligibilityDetails = new String[1];
//                 indivEligibilityDetails[0] = dataValue;
//             }
//
//
//			int indSize = driver.findElements(By.xpath("//div[contains(@class,'rpIndividualName')]")).size();
//			String[] indNameList = new String[indSize];
//
//			driver.findElement(By.xpath("//a[@id = 'viewAuthorizationHistory']")).click();
//			Thread.sleep(3000);
//			driver.findElement(By.xpath("//input[@id='btnSearch']")).click();
//			Thread.sleep(3000);
//			int count = driver.findElements(By.xpath("//div[contains(@class,'MedicalGrid')]")).size();
//			System.out.println("Total Medical EDMs on Auth History is : " + count);
//
//
//			for (int i = 1; i <= indSize; i++)
//			{
//				indNameList[i - 1] = driver.findElement(By.xpath("(//div[contains(@class,'rpIndividualName')])[" + i + "]")).getText();
//
//			}
//
//			int[] temp = new int[count];
//			for (int l = 0; l < count; l++)
//			{
//				temp[l] = num;
//				num = num + 1;
//			}
//
//			int x = 0;
//			int j = 0;
//
//			for (j = 1; j <= count; j++)
//			{
//
//				indivName = driver.findElement(By.xpath("(//div[contains(@class,'MedicalGrid')]//div[4])[" + j + "]")).getText();
//
//
//				for (int k = 1; k <= indSize; k++)
//				{
//					String ind_name = indNameList[k - 1];		
//
//					if (ind_name.contains(indivName) /*&& indivDetail[0].Equals("Indiv"+k+"")*/)
//					{
//						x = j;
//						for (int i = 0, z = 0; i < count && z < temp.length; i++, z++)
//						{
//							indivDetail = indivEligibilityDetails[i].split("\\|");
//
//							if (indivDetail[0].equals("Indiv" + k + "") && temp[z] != i)
//							{
//								temp[z] = i;
//
//								break;
//							}
//						}
//						break;
//					}
//
//
//				}
//
//
//
//				toa = driver.findElement(By.xpath("(//div[contains(@class,'MedicalGrid')]//div[3])[" + x + "]")).getText();
//
//				beginDt = driver.findElement(By.xpath("(//div[contains(@class,'MedicalGrid')]//div[5])[" + x + "]")).getText();
//
//				endDt = driver.findElement(By.xpath("(//div[contains(@class,'MedicalGrid')]//div[6])[" + x + "]")).getText();
//
//				status = driver.findElement(By.xpath("(//div[contains(@class,'MedicalGrid')]//div[8])[" + x + "]")).getText();
//
//
//				if (status.contains(indivDetail[1]))
//				{
//
//					System.out.println(" Eligibility Status for record " + j + " are as expected");
//					ReportUtilities.Log(driver,"Verifying Status for record "+ j,"The eligibility Status are  as expected for record  " + j + " having text "  + status, Status.PASS , keywordModel);
//				}
//				else
//				{
//
//					System.out.println(" Eligibility Status for record " + j + " has failed");
//					ReportUtilities.Log(driver,"Verifying Status for record " + j, "The eligibility Status are not as expected for record  " + j + " having text " + status, Status.FAIL , keywordModel);
//					storeEDMResult(driver, keywordModel);
//					//driver.wait();
//				}
//				if (toa.contains(indivDetail[2]))
//				{
//
//					System.out.println("TOA, for the record " + j + " are as expected");
//					ReportUtilities.Log(driver,"Verifying TOA for record " + j, "The eligibility TOA are as expected for record  " + j + " having text " + toa, Status.PASS , keywordModel);
//				}
//				else
//				{
//
//					System.out.println("TOA, for the record " + j + " has failed");
//					ReportUtilities.Log(driver,"Verifying TOA for record " + j,"The eligibility TOA are not as expected for record  [" + j + "]having text " + toa, Status.FAIL , keywordModel);
//				storeEDMResult(driver, keywordModel);
//					//driver.wait();
//				}
//				if (beginDt.contains(indivDetail[3]))
//				{
//
//					System.out.println("Begin Date for the record " + j + " are as expected");
//					ReportUtilities.Log(driver,"Verifying Begin Date for record " + j,"The Begin Date are as expected for record  " + j + " having text " + beginDt, Status.PASS , keywordModel);
//				}
//				else
//				{
//
//					System.out.println("Begin Date for the record " + j + " has failed");
//					ReportUtilities.Log(driver,"Verifying Begin Date for record " + j, "The Begin Date are not as expected for record  " + j + " having text " + beginDt, Status.FAIL , keywordModel);
//					storeEDMResult(driver, keywordModel);
//					//	driver.wait();
//				}
//				if (endDt.contains(indivDetail[4]))
//				{
//
//					System.out.println("End Date for the record " + j + " are as expected");
//					ReportUtilities.Log(driver,"Verifying End Date for record " + j, "The End Date are as expected for record  " + j + " having text " + endDt, Status.PASS , keywordModel);
//				}
//				else
//				{
//
//					System.out.println(" End Date for the record " + j + " has failed");
//					ReportUtilities.Log(driver,"Verifying End Date for record " + j, "The End Date are not as expected for record  " + j + " having text " + endDt, Status.FAIL , keywordModel);
//					storeEDMResult(driver, keywordModel);
//					//driver.wait();
//				}
//
//
//
//			}
//
//
//         }
//         catch (Exception e)
//         {
//              ReportUtilities.Log(driver,"Verifying the medical EDM records " + keywordModel.objectName , "The values are not displayed as expected", Status.FAIL , keywordModel);
//              driver.wait();
//         }
//
//     }

      




	
	
	/**
	 * Method Name : clickDynaTableEditButton Return Type : Nothing Description :
	 * This method is used to edit Specific row in the dyna table in the Summary
	 * Screens. e.g. input in Scenario Sheet 1 where 1 = row to be edited i.e 1st
	 * row in this case
	 */

	public void clickDynaTableEditButton(WebDriver driver, KeywordModel keywordModel) {
		String xpathString = keywordModel.objectID;
		String newXpathString = xpathString.replaceAll("\\?", keywordModel.dataValue).split("xpath:")[1];
		driver.findElement(By.xpath(newXpathString)).click();
		findElementByType(driver, keywordModel).click();
		ReportUtilities.Log(driver,"ClickDynaTableEditButton " + keywordModel.dataValue,
				"Clicked on a DynaTableEditButton " + keywordModel.dataValue, Status.PASS , keywordModel);
	}

	
	
	
	public void fetchjcount(WebDriver driver, KeywordModel keywordModel) {
		keywordModel.jcount = Integer.parseInt(keywordModel.dataValue);
		
	}
	
	
	
	public void hideElement(WebDriver driver, KeywordModel keywordModel)
	{
	    WebElement element = findElementByType(driver, keywordModel);       
	    ((JavascriptExecutor)driver).executeScript("arguments[0].style.visibility='hidden'", element);
	}
	
	
	/**
	 * Method Name : clickNextFilingUnit Return Type : Nothing Description : This
	 * method is used to edit Specific row in the search results of search screens.
	 */

	public void clickNextFilingUnit(WebDriver driver, KeywordModel keywordModel) {
		String xpathString = keywordModel.objectID;
		String newXpathString = xpathString.replaceAll("\\?", keywordModel.dataValue).split("xpath:")[1];
		int icon_size = driver.findElements(By.xpath(newXpathString)).size();
		driver.findElements(By.xpath(newXpathString)).get(icon_size - 1).click();
		ReportUtilities.Log(driver,"ClickButton " + keywordModel.dataValue,
				"Clicked on a Filing Unit Next or Eligibility Determination Button " + keywordModel.dataValue, Status.PASS , keywordModel);
	}

	/**
	 * Method Name : clickByPartialLinkText Return Type : Nothing Description : This
	 * method is used to edit Specific row in the search results of search screens.
	 */

//	public void clickByPartialLinkText(WebDriver driver, KeywordModel keywordModel) {
//		// String xpathString = keywordModel.objectID;
//		// String newXpathString = xpathString.replaceAll("\\?",
//		// keywordModel.dataValue).split("xpath:")[1];
//		driver.findElement(By.linkText("NEXT")).click();
//		// driver.findElements(By.xpath(newXpathString)).get(icon_size-1).click();
//		ReportUtilities.Log(driver,"ClickButton " + keywordModel.dataValue, "Clicked on a  Next Button " + keywordModel.dataValue, Status.PASS , keywordModel);
//	}

	/**
	 * Method Name: validateDateFields Return Type: Nothing Description : This
	 * method is used to validate date fields in a dyna table. It is internally
	 * called by insertDynaTableData.
	 */

//	private boolean validateDateFields(String xPath, String dataValue,WebDriver driver, KeywordModel keywordModel) {
//		boolean isValid = false;
//		ReportUtilities.Log(driver,"Dyna Table Date Validation", "Expected Date :: " + dataValue, Status.PASS , keywordModel);
//		String actualData = keywordModel.driver.findElement(By.id(xPath)).getAttribute("value");
//		if (dataValue != null && !dataValue.equalsIgnoreCase("") && !dataValue.equalsIgnoreCase(SKIP_STEP)) {
//			if (actualData.equalsIgnoreCase(dataValue)) {
//				ReportUtilities.Log(driver,"Dyna Table Date Validation",
//						"Expected Date :: " + dataValue + " Actual Value :: " + actualData, Status.PASS , keywordModel);
//				isValid = true;
//			} else {
//				ReportUtilities.Log(driver,"Dyna Table Date Validation",
//						"Expected Date :: " + dataValue + " Actual Value :: " + actualData, Status.FAIL , keywordModel);
//			}
//		} else {
//			ReportUtilities.Log(driver,"Dyna Table Date Validation not PASS", "", Status.PASS , keywordModel);
//		}
//		return isValid;
//	}

	/**
	 * Method Name : validateDynaTableData Return Type : Nothing Description : This
	 * method is used to validate data field values in a dyna table. It will verify
	 * all the data in the dyna table with few restrictions, If the dyna table has
	 * data which is not wrapped and it is in 2 lines i.e; From HTML perspective, if
	 * they are separated by a tag (<br />
	 * ) or scenarios where the data is not a direct text i.e. they are either a
	 * hyperlink or span is explicitly mentioned to the cell. This can be used to
	 * Verify Expected data in the Summary Screens wherever required. e.g. input in
	 * Scenario Sheet 1,2,Refer - Mandatory TANF where 1 = row Number i.e 1st row in
	 * this case 2 = Column Number i.e 2nd Column in this case Refer - Mandatory
	 * TANF = Data to be Verified or Expected Value in this case
	 */

//	public   void validateDynaTableData(WebDriver driver, KeywordModel keywordModel) {
//		String data[] = keywordModel.dataValue.split(",");
//		String row = data[0];
//		String column = data[1];
//		String expectedValue = "";
//		StringBuffer tempData = new StringBuffer();
//		if (data.length == 3) {
//			expectedValue = data[2];
//		} else if (data.length > 3) {
//			for (int i = 2; i < data.length; i++) {
//				tempData.append(data[i]);
//				if (i != data.length - 1) {
//					tempData.append(",");
//				}
//			}
//			expectedValue = tempData.toString().trim();
//		}
//
//		ReportUtilities.Log(driver,"Validation of Dyna Table Data", "Expected Data :: " + expectedValue, Status.PASS , keywordModel);
//		String objectIds[] = keywordModel.objectID.replaceAll("\\?", "1").split(";");
//		String toBeVerifiedObject = objectIds[Integer.parseInt(String.valueOf(Integer.parseInt(column) - 1))];
//		String xPathtemp = toBeVerifiedObject.replaceFirst("1", row);
//		String xPath = xPathtemp.split("xpath:")[1];
//		String actualValue = keywordModel.driver.findElement(By.xpath(xPath)).getAttribute("innerHTML").replaceAll("&nbsp;",
//				" ");
//		if (expectedValue != null && !expectedValue.equalsIgnoreCase("")
//				&& !expectedValue.equalsIgnoreCase(SKIP_STEP)) {
//			if (expectedValue.replaceAll(" ", "").trim()
//					.equalsIgnoreCase(actualValue.replaceAll(" ", "").replaceAll("\t", "").trim())) {
//				ReportUtilities.Log(driver,"Validation of Dyna Table Data",
//						"Expected Data :: " + expectedValue + "  Actual Data :: " + actualValue, Status.PASS , keywordModel);
//			} else {
//				ReportUtilities.Log(driver,"Validation of Dyna Table Data",
//						"Expected Data :: " + expectedValue + "  Actual Data :: " + actualValue, Status.FAIL , keywordModel);
//			}
//		} else {
//			ReportUtilities.Log(driver,"Validation of Dyna Table Data", " Skipped" + actualValue, Status.PASS , keywordModel);
//		}
//
//	}

	/**
	 * Method Name: jsOpenNewWindow Return Type: Nothing Description: This method
	 * will open a new browser window with specified url in getData
	 */
	public void jsOpenNewWindow(WebDriver driver, KeywordModel keywordModel) {
		StringBuilder sb = new StringBuilder();
		sb.append("window.open('" + keywordModel.dataValue + "').focus();");

		executeJavaScript(sb.toString(), driver, keywordModel);
		Set<String> allWindowHandles = driver.getWindowHandles();
		for (String currentWindowHandle : allWindowHandles) {
			if (!currentWindowHandle.equals(keywordModel.mainWindowHandle)) {
				try {
					driver.switchTo().window(currentWindowHandle);
					// driver.close();
				} catch (Exception e) {
					keywordModel.logger.warning("Failed to give control to window with windowhandle:" + currentWindowHandle);
					keywordModel.logger.warning(e.getMessage());
				}
			}
		}

		ReportUtilities.Log(driver,"Opened a new window", "", Status.PASS , keywordModel);
	}

	/**
	 * Method Name : handleAlertBox Return Type: Nothing Description : This method
	 * will handle the Alert box Click Events.
	 */
	public void handleAlertBox(WebDriver driver, KeywordModel keywordModel) {
		String dataValue = keywordModel.dataValue;
		Alert alert = driver.switchTo().alert();
		ReportUtilities.Log(driver,"Alert Box Confirmation", "Expected Data :: " + dataValue, Status.PASS , keywordModel);
		if (dataValue.equalsIgnoreCase("OK") || dataValue.equalsIgnoreCase("YES")) {
			ReportUtilities.Log(driver,"Alert Box Confirmation", "Expected Data :: " + dataValue, Status.PASS , keywordModel);
			alert.accept();
		} else if (dataValue.equalsIgnoreCase("No") || dataValue.equalsIgnoreCase("Cancel")) {
			ReportUtilities.Log(driver,"Validation of Dyna Table Data", "Expected Data :: " + dataValue, Status.PASS , keywordModel);
			alert.dismiss();
		} else {
			ReportUtilities.Log(driver,"Validation of Dyna Table Data", "Expected Data :: " + dataValue, Status.FAIL , keywordModel);
		}
	}



	public void sendDownArrow(WebDriver driver, KeywordModel keywordModel) {
		findElementByType(driver, keywordModel).sendKeys(Keys.ARROW_DOWN);
		ReportUtilities.Log(driver,"Entering text in the text box " + keywordModel.objectName, "Entered the text " + keywordModel.dataValue,
				Status.PASS , keywordModel);
	}

	public void getUrl(WebDriver driver, KeywordModel keywordModel) {
		driver.get(keywordModel.dataValue);
	}

	public void retrieveSessionId(WebDriver driver, KeywordModel keywordModel) {
		findElementByType(driver, keywordModel).clear();
		findElementByType(driver, keywordModel).sendKeys(keywordModel.sessionid);
		ReportUtilities.Log(driver,"Entering text in the text box " + keywordModel.objectName, "Entered the text " + keywordModel.sessionid, Status.PASS , keywordModel);
	}

	public String fetchSessionId(WebDriver driver, KeywordModel keywordModel) {
		ReportUtilities.Log(driver,"Returning the session ID " + keywordModel.objectName, "Returned the value " + keywordModel.sessionid, Status.PASS , keywordModel);
		return keywordModel.sessionid;
	}

	public void retrieveApplicationNumber(WebDriver driver, KeywordModel keywordModel) {
		findElementByType(driver, keywordModel).clear();
		findElementByType(driver, keywordModel).sendKeys(keywordModel.applicationNumber);
		ReportUtilities.Log(driver,"Entering text in the text box " + keywordModel.objectName, "Entered the text " + keywordModel.applicationNumber,
				Status.PASS , keywordModel);
	}

//	public void uploadFile(String dataValue,WebDriver driver, KeywordModel keywordModel) throws IOException {
//		//		findElementByType(driver, keywordModel).sendKeys(new File (".").getCanonicalPath()+"\\FileUpload\\BCP_2016.jpg");
//		findElementByType(driver, keywordModel).sendKeys(new File(".").getCanonicalPath() + "\\FileUpload\\dataValue");
//	}

	/*
	 * Code for switching frames within same window
	 */

	public void switchToFrame(String dataValue,WebDriver driver, KeywordModel keywordModel) {
		driver.switchTo().frame(dataValue);
		ReportUtilities.Log(driver,"Switch to Frame on Same window" + keywordModel.objectName, "Switched Frame", Status.PASS , keywordModel);
	}

	public void switchToMainFrame(WebDriver driver, KeywordModel keywordModel) {
		driver.switchTo().defaultContent();
		ReportUtilities.Log(driver,"Switch to Frame on Same window" + keywordModel.objectName, "Switched Frame", Status.PASS , keywordModel);
	}

//	public void verifyAndClick(WebDriver driver, KeywordModel keywordModel) {
//		verifyElementPresent(driver, keywordModel);
//		if (keywordModel.elementPresence) {
//			driver.findElement(By.xpath("//*[@id='actionButtonNext'] ")).click();
//			ReportUtilities.Log(driver,"Verify required field: " + keywordModel.objectName,
//					"The element " + keywordModel.objectName + " is present ", Status.PASS , keywordModel);
//		} else {
//			ReportUtilities.Log(driver,"Verify required field: " + keywordModel.objectName,
//					"The element " + keywordModel.objectName + " is absent ", Status.PASS , keywordModel);
//		}
//	}

	/**
	 * Method Name : connectToDB Return Type: Boolean Description : This method is
	 * handles the establishment of Database connectivity
	 */
//	public Boolean connectToDB(WebDriver driver, KeywordModel keywordModel) {
//		String url = KeywordUtilities.getValueFromDBConfigProperties(KeywordTestSettings.ENV+"_URL");
//		String serverName = KeywordUtilities.getValueFromDBConfigProperties(KeywordTestSettings.ENV+"_SERVER");
//		String portNumber = KeywordUtilities.getValueFromDBConfigProperties(KeywordTestSettings.ENV+"_PORT");
//		String userName= KeywordUtilities.getValueFromDBConfigProperties(KeywordTestSettings.ENV+"_USERNAME");
//		String password= KeywordUtilities.getValueFromDBConfigProperties(KeywordTestSettings.ENV+"_PASSWORD");
//		boolean connectivityEstablished = false;
//		keywordModel.con = dbUtil.DBConnection(url,serverName,portNumber,userName,password);
//		
//		if (keywordModel.con != null) {
//			connectivityEstablished = true;
//			System.out.println("Connection With "+url+" established");
//		}
//		
//		return connectivityEstablished;
//	}

	/**
	 * Method Name : timetravel Return Type: Nothing Description : This method time
	 * travels the environment to the date specified in the excel sheet
	 */
//	public void timetravel1(WebDriver driver, KeywordModel keywordModel) throws ParseException, SQLException {
//		// String connectionString =
//		// "jdbc:oracle:thin:@itdracscantest.nd.gov:1521/DHS_ELIG_WP_DEV3svc.itd.nd.gov";
//
//		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
//		date = formatter.parse(keywordModel.dataValue);
//		connectToDB(driver, keywordModel);
//		if (connectToDB(driver, keywordModel)) {
//			PreparedStatement ps = keywordModel.con
//					.prepareStatement("UPDATE DHS_ELIG_WP_DEV3.FW_PARAMETERS SET PARM_VALUE = ? WHERE PARM_ID=51");
//			ps.setDate(1, new java.sql.Date(date.getTime()));
//			int row = ps.executeUpdate();
//			commit(driver, keywordModel);
//			ReportUtilities.Log(driver,"Time travelling the environment ",
//					"Time travelled to date " + keywordModel.dataValue + "successfully", Status.PASS , keywordModel);
//			closeConnectivity(driver, keywordModel);
//		}
//
//	}

//	public   void timetravel(WebDriver driver, KeywordModel keywordModel) throws ParseException, SQLException {
//		// String connectionString =
//		// "jdbc:oracle:thin:@itdracscantest.nd.gov:1521/DHS_ELIG_WP_DEV3svc.itd.nd.gov";
//
//		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
//		if (keywordModel.dataValue.equals("systemDate")) {
//			date = Calendar.getInstance().getTime();
//
//		} else {
//			date = formatter.parse(keywordModel.dataValue);
//		}
//		// connectToDB();
//		if (connectToDB()) {
//			PreparedStatement ps = con
//					.prepareStatement("UPDATE DHS_ELIG_WP_DEV3.FW_PARAMETERS SET PARM_VALUE = ? WHERE PARM_ID=51");
//			ps.setDate(1, new java.sql.Date(date.getTime()));
//			int row = ps.executeUpdate();
//			commit();
//			ReportUtilities.Log(driver,"Time travelling the environment ",
//					"Time travelled to date " + keywordModel.dataValue + "successfully", Status.PASS , keywordModel);
//			closeConnectivity();
//		}
//
//	}

	public   void commit(WebDriver driver, KeywordModel keywordModel) throws SQLException {
		keywordModel.con.commit();
	}

	/**
	 * Method Name : closeConnectivity Return Type: Nothing Description : This
	 * method closed the established database connectivity
	 */
//	public void closeConnectivity(WebDriver driver, KeywordModel keywordModel) {
//		// closing DB Connection
//		try {
//			keywordModel.con.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
	
	//created by Hariom Sinha 557173
	//method to store the Framework Date in A Global Variable
//	public void getFrameWorkDate(WebDriver driver, KeywordModel keywordModel) throws SQLException, ParseException
//	{
//		String QueryKey = keywordModel.dataValue;
//		if (connectToDB(driver, keywordModel)) {
//			
//			keywordModel.st = keywordModel.con.createStatement();
//			keywordModel.Query = KeywordUtilities.getValueFromDBConfigProperties(QueryKey);
//			ResultSet rs = keywordModel.st.executeQuery(keywordModel.Query);
//			String temp = "";
//			while(rs.next())
//			temp = rs.getString(1);
//			
//			//Parse the Date to the standard Format for Automation Config 
//			temp = temp.substring(0, 10);
//			DateFormat srcDf = new SimpleDateFormat("yyyy-MM-dd");
//            
//            // parse the date string into Date object
//            Date date = srcDf.parse(temp);            
//            DateFormat destDf = new SimpleDateFormat("MM-dd-yyyy");     
//            
//            // format the date into another format
//            FrameWorkDate  = destDf.format(date);
//            System.out.println("FrameWorkDate is : "+FrameWorkDate);
//			closeConnectivity();
//		}
//		
//	}


	//this method will pull the data value from the excel for MMIS Vaklidation and then it will 
	//saperate then for validations into Column Label and data
	public void storeDBData(WebDriver driver, KeywordModel keywordModel) throws ParseException, InterruptedException
	{		
		
		//initialize MMIS table header and data Array everytime Data is Pushed form the Excel
		keywordModel.DBTableLabel = new String[1000]; // for now the size is being taken as 10--we can change that based on Req.
		keywordModel.DBTableData = new String[1000];
		int count1 = 0, count2 = 0;

		String data = keywordModel.dataValue;
		//System.out.println(data);
		String dataArray[] = data.split("\\|");
		String dataRow[] = data.split("&");
		keywordModel.numberOfRows = dataRow.length;
		for(int i=0; i<dataArray.length; i++)
		{
			String tempData[] = dataArray[i].split("#");
			//System.out.println(tempData[0]);
			//System.out.println(tempData[1]);
			keywordModel.DBTableLabel[count1] = tempData[0].trim();
			keywordModel.DBTableData[count2] = tempData[1].trim();
			count1++;
			count2++;
		}
		keywordModel.DBExcelTableSize = count1;
		
//		//for debug purposes
//		for(int i=0; i<count1; i++)
//		{
//			System.out.println(keywordModel.DBTableLabel[i]+" : "+keywordModel.DBTableData[i]);
//		}
		
		
		//handling Offset Values 
//		for ( int i= 0; i < dataArray.length; i++)
//        {
//               if(keywordModel.DBTableData[i].contains("Offset"))
//               {
//                  if(keywordModel.DBTableLabel[i].equalsIgnoreCase("EligibilityStartDate") || keywordModel.DBTableLabel[i].equalsIgnoreCase("EligibilityEndDate") || keywordModel.DBTableLabel[i].equalsIgnoreCase("ExpectedDeliveryDate")) {
//                            keywordModel.DBTableData[i] =  verifyDBDateFormat1(keywordModel.DBTableData[i]);
//                     }else if(keywordModel.DBTableLabel[i].equalsIgnoreCase("MemberCertificationEndDate") ) {
//                            keywordModel.DBTableData[i] =  verifyDBDateFormat2(keywordModel.DBTableData[i]);
//                     }else if(keywordModel.DBTableLabel[i].equalsIgnoreCase("BenefitBeginDate") || keywordModel.DBTableLabel[i].equalsIgnoreCase("BenefitEndDate") || keywordModel.DBTableLabel[i].equalsIgnoreCase("BenefitMonth")  ) {
//                            keywordModel.DBTableData[i] =  verifyDBDateFormat3(keywordModel.DBTableData[i]);
//                     }
//               }
//        }



	}

	/**
	 * Method Name : fireQuery Return Type: Nothing Description : This
	 * method fires the SQL select/update/delete query and returns the fetched results The query
	 * given in DB.properties has 3 kinds of place holders. This method replaces the
	 * place holders with their respective values, and then fires the final query.
	 * Place holders used are: 1. #text# : Used to replace
	 * CaseNumber/ApplicationNumber/TTdate or Individual ID in the query. The given 3
	 * variables will come from the methods getCasenumber(), getApplicationNumber()
	 * and getIndividualID() 
	 * @throws Exception
	 */
	
//	public void fireQuery(WebDriver driver, KeywordModel keywordModel) {
//	
//	String QueryKey = keywordModel.dataValue;
//	try {
//	//check connection with DB and create keywordModel.Query with Replace Actions
//	if (connectToDB(driver, keywordModel)) {
//		keywordModel.st = keywordModel.con.createStatement();
//		keywordModel.Query = KeywordUtilities.getValueFromDBConfigProperties(QueryKey);
//
//
//		List<String> TempQuery = Arrays.asList(keywordModel.Query.replaceAll("\\s", "").split("#"));
//
//
//		List Fnl = new ArrayList();
//		int i = 0;
//		for (String a : TempQuery) {
//			if (a.equalsIgnoreCase("individualID") || a.equalsIgnoreCase("caseNumber")
//					|| a.equalsIgnoreCase("applicationNumber") || a.equalsIgnoreCase("OFFSET") ) {
//				Fnl.add(i, a);
//				i++;
//			}
//		}
//
//
//		//System.out.println(Fnl);
//		for (i = 0; i < Fnl.size(); i++) {
//			if (Fnl.get(i).equals("individualID")) {
//				// System.out.println("Replacing Ind ID");
//				keywordModel.Query = keywordModel.Query.replace("#individualID#", keywordModel.individualID);
//				// System.out.println("Replaced Ind ID"+keywordModel.Query);
//			} else if (Fnl.get(i).equals("caseNumber")) {
//				// System.out.println("Replacing Case");
//				keywordModel.Query = keywordModel.Query.replace("#caseNumber#", keywordModel.caseNumber);
//				// System.out.println("Replaced Case"+keywordModel.Query);
//			} else if (Fnl.get(i).equals("applicationNumber")) {
//				// System.out.println("Replacing Application");
//				keywordModel.Query = keywordModel.Query.replace("#applicationNumber#", keywordModel.applicationNumber);
//				// System.out.println("Replaced Case"+keywordModel.Query);
//			}
//			else if (Fnl.get(i).equals("OFFSET")) {
//				// System.out.println("Replacing OFFSET");
//				keywordModel.Query = keywordModel.Query.replace("#OFFSET#", keywordModel.TTDate);
//				// System.out.println("Replaced Case"+keywordModel.Query);
//			}
//		}
//
//		//for delete query
//		if (keywordModel.Query.startsWith("delete")) {
//			keywordModel.st.executeUpdate(keywordModel.Query);
//			System.out.println("****deletion successful!!!****");
//			ReportUtilities.Log(driver,"Performing Delete Operation for " + QueryKey,
//					"Validation Successful", Status.PASS , keywordModel);
//		
//		//for update query
//		} else if(keywordModel.Query.startsWith("update"))
//			{
//			keywordModel.st.executeUpdate(keywordModel.Query);
//			System.out.println("****updation successful****");
//			ReportUtilities.Log(driver,"Performing Update Operation for " + QueryKey,
//					"Validation Successful", Status.PASS , keywordModel);
//			
//		}else {
//			
//			//fire Select keywordModel.Query
//			
//			keywordModel.st.executeQuery(keywordModel.Query);
//			System.out.println("****select query ran successfully****");
//			ReportUtilities.Log(driver,"Performing select Operation for " + QueryKey,
//					"Validation Successful", Status.PASS , keywordModel);
//		}
//	}
//	dbUtil.closeConnectivity(keywordModel.con);
//	}
//	catch (Exception e)
//	{
//		e.printStackTrace();
//	}
//	
//	}
	
	/**
	 * Method Name : validateDBData Return Type: Nothing Description : This
	 * method fires the SQL select query and returns the fetched results The query
	 * given in DB.properties has 3 kinds of place holders. This method replaces the
	 * place holders with their respective values, and then fires the final query.
	 * Place holders used are: 1. #text# : Used to replace
	 * CaseNumber/ApplicationNumber or Individual ID in the query. The given 3
	 * variables will come from the methods getCasenumber(), getApplicationNumber()
	 * and getIndividualID() 2. &text& : Used to replace any Session data to the
	 * query. It will replace &text& with the value of "text" as stored in the
	 * session. 3. $text$ : Used to replace any Session data in the query, and then
	 * finally replacing it with the equivalent code that is used in the DB.
	 * Example: Gender has session value stored as Female. Female is getting stored
	 * in DB as "F". The key-value Female = F should be stored in DBRef.properties.
	 * Use $Gender$ in the query as given in DB.properties. It will replace %Gender%
	 * with F in the final query.
	 * 
	 * @throws Exception
	 */

//	public void validateDBData(WebDriver driver, KeywordModel keywordModel) throws Exception {
//		
//		
//		//Read Query type --MMIS/BM/Eligibility
//		String QueryKey = keywordModel.dataValue;
//		Status stepStatus ;
//		System.out.println("\n-------------------------------VALIDATION STARTS FOR CASE : "+keywordModel.caseNumber+"---------------------------------------------------------------------------------");		System.out.println("Query to be validated :" + QueryKey);
//		//String QueryKeyError = QueryKey + "_ERROR";
//		
//		
//		//check connection with DB and create Query with Replace Actions
//		try {
//			if (connectToDB(driver, keywordModel)) {
//				keywordModel.st = keywordModel.con.createStatement();
//				keywordModel.Query =KeywordUtilities.getValueFromDBConfigProperties(QueryKey);
//				//QueryError = Util.getValueFromDBConfigProperties(QueryKeyError);
//				
//			ArrayList<String> reportStatusList = MSSQLUtilities.DBDataValidation(keywordModel.st,keywordModel.Query,QueryKey,keywordModel.individualID,keywordModel.caseNumber,keywordModel.applicationNumber,keywordModel.DBExcelTableSize,keywordModel.DBTableLabel,keywordModel.DBTableData);
//			 
//			  for(int i=0;3*i + 2<reportStatusList.size();i++){ 
//				  	if( reportStatusList.get(3*i + 2).equalsIgnoreCase("Pass"))
//				  	{
//				  		stepStatus = Status.PASS;
//				  	}
//				  	else {
//				  	 stepStatus = Status.FAIL;
//				  	}
//				    ReportUtilities.Log(driver, reportStatusList.get(3*i)+" Operation is done for",reportStatusList.get(3*i + 1)+ " and validation is done", stepStatus,keywordModel);    
//				  
//			  }  
//			
//			dbUtil.closeConnectivity(keywordModel.con);
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
//	public void apiMainTest(WebDriver driver, KeywordModel keywordModel) throws IOException {
//		// TODO Auto-generated method stub
//		
//		String ConfigLocation= "C:\\chfs\\HBE\\HBE\\Dev1\\Source\\Tests\\AutomationTest\\Keyword_Java\\GPSCommon\\GPSAutomation"+"\\ApiUtilities\\config\\InterfaceConfig.properties";
//		String ArtifactsPath="C:\\chfs\\HBE\\HBE\\Dev1\\Source\\Tests\\AutomationTest\\Keyword_Java\\GPSCommon\\GPSAutomation"+"\\ApiUtilities\\Artifacts\\";
//
//		String PrjPath="C:\\chfs\\HBE\\HBE\\Dev1\\Source\\Tests\\AutomationTest\\Keyword_Java\\GPSCommon\\GPSAutomation"+"\\ApiUtilities";
//		String Env="PERF";
//		String apiSpecs = keywordModel.dataValue;
//		String[] splitApiSpecs = apiSpecs.split(",");
//		InitializeAPISettings initializeTestSettings= new InitializeAPISettings();
//		
//		APITestSettings.apiTestSettings = initializeTestSettings.InitializeInterfaceSettings(PrjPath,ArtifactsPath,ConfigLocation,Env);
//		System.out.println("****************************************************");
//		try {
//		System.out.println("***********************Next API*********************");
//		APIController apiController = new APIController();
//		
//		ArrayList<APIReportModel> apiModels=apiController.ExecuteAPI(splitApiSpecs[0],splitApiSpecs[1],splitApiSpecs[2]);
////		ArrayList<APIReportModel> apiModels1=apiController.ExecuteAPI("MCI","CreateAndUpdateMember","1");
////		ArrayList<APIReportModel> apiModels2=apiController.ExecuteAPI("Test","Weather","1");
////		System.out.println(apiModels.get(0).boolResponseStringValidation);
//		
//		System.out.println("");
//		
//		for (int i=0; i < apiModels.size() - 1 ; i++)
//        {
//			System.out.println("For XPathJsonKey       =  " + apiModels.get(i).XPathJSONKey + ",");
//			System.out.println("Response obtained is   =  " + apiModels.get(i).ActualResponse + ",");
//			System.out.println("Expected response was  =  " + apiModels.get(i).ExpectedResponse);
//			
//			if((apiModels.get(i).TestStepResult).equalsIgnoreCase("PASS"))
//			{
//				System.out.println("API for Interface " + splitApiSpecs[1] + " is validated successfully!");
//				ReportUtilities.Log(driver, "Module " + splitApiSpecs[0] + " for Interface " + splitApiSpecs[1] + 
//						" having Json/XML Path Key = " + apiModels.get(i).XPathJSONKey +
//						" has expected value = " + apiModels.get(i).ExpectedResponse,
//						"Actual value = " + apiModels.get(i).ActualResponse, Status.PASS, keywordModel);
//			
//			}
//			else
//			{
//				System.out.println("API for Interface " + splitApiSpecs[1] + " validation has failed!");
//				ReportUtilities.Log(driver, "Module " + splitApiSpecs[0] + " for Interface " + splitApiSpecs[1] + 
//						" having Json/XML Path Key = " + apiModels.get(i).XPathJSONKey +
//						" has expected value = " + apiModels.get(i).ExpectedResponse,
//						"Actual value = " + apiModels.get(i).ActualResponse, Status.FAIL, keywordModel);
//			
//			}
//			
//			System.out.println("");
//        }
//		
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	

//	public   void validateQueryResult(WebDriver driver, KeywordModel keywordModel) throws SQLException {
//		String queryCount;
//		if (connectToDB(driver, keywordModel)) {
//			keywordModel.st = keywordModel.con.createStatement();
//			ResultSet rs = keywordModel.st.executeQuery("select KYHBE.Framework.udfGetTimeTravelDate();");
//			while (rs.next()) {
//				System.out.println("Time Travel Date: " + rs.getString(1));
//			}
//		}
//		if (queryCount.equals(keywordModel.dataValue)) {
//			ReportUtilities.Log(driver,"Checking the Resultset count.", "The keywordModel.Query :" + keywordModel.Query + " returned " + queryCount + " rows",
//					Status.PASS , keywordModel);
//		} else {
//			ReportUtilities.Log(driver,"The Database Validation Step Failed", QueryError, Status.FAIL , keywordModel);
//		}
//	}

//	public   void validateDBQueryResult(WebDriver driver, KeywordModel keywordModel) {
//
//		int qc = Integer.parseInt(queryCount);
//		int dc = Integer.parseInt(keywordModel.dataValue);
//		if (qc >= dc) {
//			ReportUtilities.Log(driver,"Checking the Resultset count.", "The keywordModel.Query :" + keywordModel.Query + " returned " + queryCount + " rows",
//					Status.PASS , keywordModel);
//		} else {
//			ReportUtilities.Log(driver,"The Database Validation Step Failed", QueryError, Status.FAIL , keywordModel);
//		}
//	}

//	public   void fireSelectQuery(WebDriver driver, KeywordModel keywordModel) throws Exception {
//		connectToDB(driver, keywordModel);
//		if (connectToDB(driver, keywordModel)) {
//			PreparedStatement ps = keywordModel.con.prepareStatement(KeywordUtilities.getData(driver, "selectQuery", null, null, 0));
//			String val = fetchSessionId(driver, keywordModel);
//			System.out.println("Case Number from session is:" + val);
//			ps.setLong(1, Integer.valueOf(val));
//			// keywordModel.st = con.createStatement();
//			// System.out.println(KeywordUtilities.getData("selectQuery"));
//			// System.out.println("The query is"+ ps);
//			ResultSet rs = ps.executeQuery();
//			while (rs.next()) {
//				String JOB_ID = rs.getString(1);
//				String myDateStr = rs.getString(2);
//				System.out.println(JOB_ID);
//				System.out.println(myDateStr);
//
//				// Date CREATE_DT = valueOf(myDateStr);
//
//			}
//			closeConnectivity(driver, keywordModel);
//		}
//	}

	/* To switch Window */

	public void SwitchToChildWindow(WebDriver driver, KeywordModel keywordModel) {
		keywordModel.parentWindow = driver.getWindowHandle();
		Set<String> handles = driver.getWindowHandles();
		for (String windowHandle : handles) {
			if (!windowHandle.equals(keywordModel.parentWindow)) {
				driver.switchTo().window(windowHandle);

			}
		}
	}

	public void SwitchToParentWindow(WebDriver driver, KeywordModel keywordModel) {

		driver.switchTo().window(keywordModel.parentWindow);
	}

	public   void SwitchToWindowByTitle(WebDriver driver, KeywordModel keywordModel) {
		Set<String> tabs = (Set<String>) driver.getWindowHandles();

		for (String tab : tabs) {
			driver.switchTo().window(tab);
			if ((!tab.equals(keywordModel.parentWindow)) && (driver.getTitle().equalsIgnoreCase(keywordModel.dataValue))) {
				driver.switchTo().window(tab);
				break;
			}
		}
	}

	public void SetParentWindow(WebDriver driver, KeywordModel keywordModel) {
		keywordModel.parentWindow = driver.getWindowHandle();
	}

	public   void VerifyURL(WebDriver driver, KeywordModel keywordModel) {
		String URL = driver.getCurrentUrl();
		if (URL.trim().equalsIgnoreCase(keywordModel.dataValue)) {
			ReportUtilities.Log(driver,"Verifying the URL of the window", "The URL is same as " + keywordModel.dataValue, Status.PASS , keywordModel);
		} else {
			ReportUtilities.Log(driver,"Verifying the URL of the window", "The URL is not same as " + keywordModel.dataValue, Status.FAIL , keywordModel);
		}
	}
	/*
	 * OpenURL() Function opens the URL given as datavalue in the same driver
	 * window.
	 */

//	public   void OpenURL(WebDriver driver, KeywordModel keywordModel) {
//		driver.get(keywordModel.dataValue);
//		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
//		Pageloadstatus = jsExecutor.executeScript("return document.readyState").toString();
//		if (Pageloadstatus.equalsIgnoreCase("Complete")) {
//			ReportUtilities.Log(driver,"Opening the URL " + keywordModel.dataValue, "Opened", Status.PASS , keywordModel);
//		}
//	}

	/*
	 * OpenURLInDiffTab() Function opens the URL given as datavalue in a different
	 * tab window.
	 */

//	public   void OpenURLInDiffTab(WebDriver driver, KeywordModel keywordModel) throws AWTException {
//		keywordModel.parentWindow = driver.getWindowHandle();
//		//        Robot r = new Robot();
//		//		r.keyPress(KeyEvent.VK_CONTROL); 
//		//		r.keyPress(KeyEvent.VK_T); 
//		//		r.keyRelease(KeyEvent.VK_CONTROL); 
//		//		r.keyRelease(KeyEvent.VK_T);
//		// 
//		//		driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"t");
//		((JavascriptExecutor) driver).executeScript("window.open();");
//		Set<String> tabs = (Set<String>) driver.getWindowHandles();
//
//		for (String tab : tabs) {
//			if (!tab.equals(keywordModel.parentWindow)) {
//				driver.switchTo().window(tab);
//				// if(driver.getTitle().contains("New Tab"))
//				if(KeywordTestSettings.ENV.equalsIgnoreCase("STL3"))
//				{
//					driver.get(KeywordUtilities.getValueFromConfigProperties("WP_STL3_URL"));
//				}else if(KeywordTestSettings.ENV.equalsIgnoreCase("STS3"))
//				{
//					driver.get(KeywordUtilities.getValueFromConfigProperties("WP_STS3_URL"));
//				}else if(KeywordTestSettings.ENV.equalsIgnoreCase("STL2"))
//				{
//					driver.get(KeywordUtilities.getValueFromConfigProperties("WP_STL2_URL"));
//				}
//				else if(KeywordTestSettings.ENV.equalsIgnoreCase("DEV2"))
//				{
//					driver.get(KeywordUtilities.getValueFromConfigProperties("WP_DEV2_URL"));
//				}
//				else if(KeywordTestSettings.ENV.equalsIgnoreCase("INS1"))
//				{
//					driver.get(KeywordUtilities.getValueFromConfigProperties("WP_INS1_URL"));
//				}
//				else if(KeywordTestSettings.ENV.equalsIgnoreCase("UAT1"))
//				{
//					driver.get(KeywordUtilities.getValueFromConfigProperties("WP_UAT1_URL"));
//				}
//				else if(KeywordTestSettings.ENV.equalsIgnoreCase("UAT2"))
//				{
//					driver.get(KeywordUtilities.getValueFromConfigProperties("WP_UAT2_URL"));
//				}
//				else if(KeywordTestSettings.ENV.equalsIgnoreCase("UAT3"))
//				{
//					driver.get(KeywordUtilities.getValueFromConfigProperties("WP_UAT3_URL"));
//				}
//
//			}
//		}
//		if (Pageloadstatus.equalsIgnoreCase("Complete")) {
//			ReportUtilities.Log(driver,"Opening the URL " + keywordModel.dataValue, "Opened", Status.PASS , keywordModel);
//		}
//	}

	/* This function extracts PDF contents */

	//	public    void ExtractPDF_ByDownloadUsingFunction() throws Exception
	//	{
	//		   ClickAndDownload();
	//		   PDFManager pdfManager = new PDFManager();
	//	       pdfManager.setFilePath("C:/Users/schenthamarakshan/Desktop/ND/test.pdf");
	//	       String filePath = pdfManager.setFilePath("C:/Users/schenthamarakshan/AppData/Local/Temp/ControllerServletPDF.pdf");
	//	       System.out.println(pdfManager.ToText(filePath)); 
	//	       
	//		
	//	}

	/*
	 * This function finds the URL of the file to be downloaded and downloads the
	 * file
	 */

//	private   void ClickAndDownload() throws Exception {
//
//		WebElement downloadLink = driver.findElement(By.xpath("//*[@id='buttonPreviewBottom']"));
//		String fileUrl = downloadLink.getAttribute("href");
//		driver.findElement(By.xpath("//*[@id='buttonPreviewBottom']")).click();
//		Thread.sleep(50000);
//		// String fileUrl = "https://spacesdev3.dhs.nd.gov/jsp/co/COloadPDF.jsp";
//		try {
//
//			downloadFile(fileUrl, "C:\\temp\\my_report.pdf");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	public   void downloadFile(String downloadUrl, String outputFilePath) throws Exception {
//
//		// Generate an HTTP client object and set its CookieStore
//
//		CookieStore cookieStore = seleniumCookiesToCookieStore();
//		DefaultHttpClient httpClient = new DefaultHttpClient();
//		httpClient.setCookieStore(cookieStore);
//
//		// Generate HTTP client request to get the URL of the the file downloaded
//
//		HttpGet httpGet = new HttpGet(downloadUrl);
//		System.out.println("Downloding file form: " + downloadUrl);
//		waitForPageToLoad();
//		HttpResponse response = httpClient.execute(httpGet);
//
//		HttpEntity entity = response.getEntity();
//		if (entity != null) {
//			File outputFile = new File(outputFilePath);
//			InputStream inputStream = entity.getContent();
//			FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
//			int read = 0;
//			byte[] bytes = new byte[1024];
//			while ((read = inputStream.read(bytes)) != -1) {
//				fileOutputStream.write(bytes, 0, read);
//			}
//			fileOutputStream.close();
//			System.out.println("Downloded " + outputFile.length() + " bytes. " + entity.getContentType());
//		} else {
//			System.out.println("Download failed!");
//		}
//	}

	/*
	 * Transform/extract the Selenium set of cookie objects to its Apache HttpClient
	 * equivalent (CookieStore)
	 */
//
//	private   CookieStore seleniumCookiesToCookieStore() {
//
//		Set<Cookie> seleniumCookies = driver.manage().getCookies();
//		CookieStore cookieStore = new BasicCookieStore();
//
//		for (Cookie seleniumCookie : seleniumCookies) {
//			BasicClientCookie basicClientCookie = new BasicClientCookie(seleniumCookie.getName(),
//					seleniumCookie.getValue());
//			basicClientCookie.setDomain(seleniumCookie.getDomain());
//			basicClientCookie.setExpiryDate(seleniumCookie.getExpiry());
//			basicClientCookie.setPath(seleniumCookie.getPath());
//			cookieStore.addCookie(basicClientCookie);
//		}
//
//		return cookieStore;
//	}

	//	public    void validateNotice() throws Exception
	//	{
	//		
	//		driver.findElement(By.xpath("//*[@id='buttonPreviewBottom']")).click();
	//		Thread.sleep(45000);
	//		//driver.close();

	//		PDFManager pdfManager = new PDFManager();
	//	     String filePath = pdfManager.setFilePath("C:/AUT Framework/PDFDownloads/ControllerServletPDF");
	//	       try {
	//			String pdfToText = pdfManager.ToText(filePath);
	//			if (pdfToText == null) {
	//				               System.out.println("PDF to Text Conversion failed.");
	//		    }
	//			else {
	//				               System.out.println("\nThe text parsed from the PDF Document....\n" + pdfToText);
	//				               File fileNameSuccess = pdfManager.createAndWriteIntoOutputFile("NoticeRun", pdfToText);
	//				               Boolean resultOfValidation = pdfManager.validate(pdfToText);
	//				               
	//				               if(resultOfValidation.TRUE){
	//				            	   ReportUtilities.Log(driver,"The newly generated notice contains the value"+ KeywordUtilities.getData("Expected") , " Notice Validation Successful ", Status.PASS , keywordModel);
	//				               }
	//				               else if(resultOfValidation.FALSE){
	//				            	   ReportUtilities.Log(driver,"The newly generated notice does not contain the value"+ KeywordUtilities.getData("Expected") , " Notice Validation Failure ", Status.FAIL , keywordModel);
	//				               }
	//				               
	//			}
	//		    pdfManager.deleteNotice();
	//		} catch (IOException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		} 
	//		
	//	}
	private   void WaitForPopup(WebDriver driver, KeywordModel keywordModel) {
		// TODO Auto-generated method stub

	}

	public   void PageRefresh(WebDriver driver, KeywordModel keywordModel) {
		if (keywordModel.dynaElement != null) {
			try {

				if (keywordModel.dynaElement.isDisplayed()) {
					keywordModel.dynaElement.sendKeys(Keys.F5);
					ReportUtilities.Log(driver,"Page has ", "been Refreshed ", Status.PASS , keywordModel);
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot perform action on the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		} else {
			try {
				if (findElementByType(driver, keywordModel).isDisplayed()) {
					findElementByType(driver, keywordModel).sendKeys(Keys.F5);
					ReportUtilities.Log(driver,"Page has ", "been Refreshed ", Status.PASS , keywordModel);
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot perform action on the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		}
	}

	public void EnterCurrentDate(WebDriver driver, KeywordModel keywordModel) throws Exception {

		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		String GetDate = jsExecutor.executeScript("return window.ApplicationDate").toString();
		String[] CurrentDate = GetDate.replaceAll("/", "").trim().split(" ");
		if (CurrentDate[0].length() < 8) {
			CurrentDate[0] = "0" + CurrentDate[0];
		}

		if (keywordModel.dynaElement != null) {
			try {

				if (keywordModel.dynaElement.isDisplayed()) {
					keywordModel.dynaElement.click();
					keywordModel.dynaElement.sendKeys(Keys.CONTROL + "a");
					keywordModel.dynaElement.sendKeys(Keys.DELETE);
				//	keywordModel.dynaElement.clear();
					Thread.sleep(500);
					keywordModel.dynaElement.sendKeys(CurrentDate[0]);
					ReportUtilities.Log(driver,"Entering Current Date in the text box " + CurrentDate[0],
							"Entered the Current Date " + keywordModel.dataValue, Status.PASS , keywordModel);
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot enter Current Date on the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		} else { 
			try {
				if (findElementByType(driver, keywordModel).isDisplayed()) {
					findElementByType(driver, keywordModel).click();
					findElementByType(driver, keywordModel).sendKeys(Keys.CONTROL + "a");
					findElementByType(driver, keywordModel).sendKeys(Keys.DELETE);
				//	findElementByType(driver, keywordModel).clear();
					Thread.sleep(500);
					
					findElementByType(driver, keywordModel).sendKeys(CurrentDate[0]);
					ReportUtilities.Log(driver,"Entering Current Date in the text box " + CurrentDate[0],
							"Entered the Current Date " + keywordModel.dataValue, Status.PASS , keywordModel);
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot enter Current Date on the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		}
	}


	public  void verifyEligEndDate(WebDriver driver, KeywordModel keywordModel) throws ParseException, InterruptedException
	{
		int year = Integer.valueOf(KeywordUtilities.getValueFromConfigProperties("Year"));
		int mm = Integer.valueOf(KeywordUtilities.getValueFromConfigProperties("Month"));
		int dd = Integer.valueOf(KeywordUtilities.getValueFromConfigProperties("Date"));
		String date = findElementByType(driver, keywordModel).getText();
		String finalDate="";
		if (date.equalsIgnoreCase("Ongoing"))
		{
			if(keywordModel.dataValue.equalsIgnoreCase(date))
			{
				System.out.println("Verified date , The date on UI is :" + date + "Expected date is " +keywordModel.dataValue);
				ReportUtilities.Log(driver,"Verifying date, The Date on UI is :" + date,
							"Expected date is Verified" + keywordModel.dataValue, Status.PASS , keywordModel);
			}
			
		}
		else
		{
		try
		{
		int offset = Integer.valueOf(keywordModel.dataValue);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	    Calendar c1 = Calendar.getInstance();
	    c1.set(year, mm , dd); // 1999 jan 20
	    
	    c1.add(Calendar.DATE,offset);   // or  Calendar.DAY_OF_MONTH which is a synonym
	    c1.set(Calendar.DAY_OF_MONTH, c1.getActualMaximum(Calendar.DAY_OF_MONTH)); 
		finalDate = sdf.format(c1.getTime());
		System.out.println("End date is"+finalDate );
		
		
			
			if (finalDate.equals(date))
		    {
				System.out.println("Verified date , The date on UI is :" + date + "Expected date is " +finalDate);
				ReportUtilities.Log(driver,"Verifying date, The Date on UI is :" + date,
							"Expected date is Verified" + finalDate, Status.PASS , keywordModel);	
		    }
			
			else
			{
			
				System.out.println("The date on UI is not as expected :" + date + "Expected date is " +finalDate);
				ReportUtilities.Log(driver,"Verifying date, The Date on UI is :" + date,
								"Date is not as expected" + finalDate, Status.FAIL , keywordModel);	
				storeEDMResult(driver, keywordModel);
				//driver.wait();
			}
		}
		
       
        catch(Exception e)
        {
        	System.out.println("Failed verfying date , The date on UI is :" + date + "Expected date is " +finalDate +"Exception : "+e);
        	ReportUtilities.Log(driver,"Failed verfying date , The date selected is :" + date,
					"Expected date is + " + finalDate, Status.FAIL , keywordModel);
        	driver.wait();
        }
		}
	}

	/*Method Calculates the last date of the month using offset as parameters and verifies the Eligibility End date with UI.
	 *creator : Ronak Shah 506096
	 * 
	 */
	public  String calMAEndDate(String Offset,WebDriver driver, KeywordModel keywordModel) throws ParseException
	{
		String finalDate = "";
		int year = Integer.valueOf(KeywordUtilities.getValueFromConfigProperties("Year"));
		int mm = Integer.valueOf(KeywordUtilities.getValueFromConfigProperties("Month"));
		int dd = Integer.valueOf(KeywordUtilities.getValueFromConfigProperties("Date"));
		String temp = KeywordUtilities.getValueFromConfigProperties(Offset);
		int offset=0;
		if(temp.equalsIgnoreCase("Ongoing"))
		{
			return temp;
		}

		else
		{	
			try {
				offset = Integer.valueOf(temp);
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				Calendar c1 = Calendar.getInstance();
				c1.set(year, mm , dd); // 1999 jan 20

				c1.add(Calendar.DATE,offset);   // or  Calendar.DAY_OF_MONTH which is a synonym
				c1.set(Calendar.DAY_OF_MONTH, c1.getActualMaximum(Calendar.DAY_OF_MONTH)); 
				finalDate = sdf.format(c1.getTime());

			}
		
        catch(Exception e)
        {
        	ReportUtilities.Log(driver,"Failed Calculating MA date , The date calculated is :" + finalDate,
					"Calculated date is + " + finalDate, Status.FAIL , keywordModel);
        	
        }
		
		return finalDate;
	
		}
	}

	/*Method Calculates the last date of the month using offset as parameters and verifies the Eligibility End date with UI.
	 *creator : Ronak Shah 506096
	 * 
	 */
	public  String calMABeginDate(String Offset,WebDriver driver, KeywordModel keywordModel) throws ParseException
	{
		int year = Integer.valueOf(KeywordUtilities.getValueFromConfigProperties("Year"));
		int mm = Integer.valueOf(KeywordUtilities.getValueFromConfigProperties("Month"));
		int dd = Integer.valueOf(KeywordUtilities.getValueFromConfigProperties("Date"));
		int offset = Integer.valueOf(KeywordUtilities.getValueFromConfigProperties(Offset));
		String finalDate = "";
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			Calendar c1 = Calendar.getInstance();
			c1.set(year, mm , dd); // 1999 jan 20

			c1.add(Calendar.DATE,offset);   // or  Calendar.DAY_OF_MONTH which is a synonym
			c1.set(Calendar.DAY_OF_MONTH, c1.getActualMinimum(Calendar.DAY_OF_MONTH)); 
			finalDate = sdf.format(c1.getTime());

		}

		catch(Exception e)
		{
			ReportUtilities.Log(driver,"Failed Calculating MA date , The date calculated is :" + finalDate,
					"Calculated date is + " + finalDate, Status.FAIL , keywordModel);
		}

		return finalDate;
	}


	/*Method Calculates the date with base date and offset as parameters and enters the date to the field.
	 *creator : Ronak Shah 506096
	 * 
	 */
	public  void enterDate(WebDriver driver, KeywordModel keywordModel) throws ParseException
	{
		int year = Integer.valueOf(KeywordUtilities.getValueFromConfigProperties("Year"));
		int mm = Integer.valueOf(KeywordUtilities.getValueFromConfigProperties("Month"));
		int dd = Integer.valueOf(KeywordUtilities.getValueFromConfigProperties("Date"));
		int offset = Integer.valueOf(keywordModel.dataValue);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c1 = Calendar.getInstance();
		c1.set(year, mm , dd); // 1999 jan 20

		c1.add(Calendar.DATE,offset);   // or  Calendar.DAY_OF_MONTH which is a synonym

		String finalDate = sdf.format(c1.getTime());
		
		try 
		{
			if (findElementByType(driver, keywordModel).isDisplayed())
			{
			//	findElementByType(driver, keywordModel).click();
			//	findElementByType(driver, keywordModel).sendKeys(Keys.CONTROL + "a");
			//	findElementByType(driver, keywordModel).sendKeys(Keys.DELETE); 
				findElementByType(driver, keywordModel).clear();
				findElementByType(driver, keywordModel).sendKeys(finalDate);
				ReportUtilities.Log(driver,"Entering date, The Value selected is :" + finalDate,
						"Entered the Date+ " + finalDate, Status.PASS , keywordModel);
			}
		}

		catch(Exception e)
		{
			System.out.println("Exception occured while entering date. Exception : "+e);
			ReportUtilities.Log(driver,"Failed entering date , The Value selected is :" + finalDate,
					"Date failed to get entered + " + finalDate, Status.FAIL , keywordModel);
		}
	}
	
	


	/*Method Calculates the DOB using offset as parameters and enters in Individual screen.
	 *creator : Ronak Shah 506096
	 * 
	 */
	public  void enterDOB(WebDriver driver, KeywordModel keywordModel) throws ParseException, InterruptedException
	{
		int year = Integer.valueOf(KeywordUtilities.getValueFromConfigProperties("Year"));
		int mm = Integer.valueOf(KeywordUtilities.getValueFromConfigProperties("Month"));
		int dd = Integer.valueOf(KeywordUtilities.getValueFromConfigProperties("Date"));
		int offset = Integer.valueOf(keywordModel.dataValue);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c1 = Calendar.getInstance();
		c1.set(year, mm , dd); // 1999 jan 20

		c1.add(Calendar.YEAR,offset);   // or  Calendar.DAY_OF_MONTH which is a synonym

		String finalDate = sdf.format(c1.getTime());
		
		if (keywordModel.dynaElement != null) {
			try {
				if (keywordModel.dynaElement.isDisplayed()) {
					keywordModel.dynaElement.click();
					System.out.println(keywordModel.dynaElement);
					keywordModel.dynaElement.sendKeys(Keys.CONTROL + "a");
					keywordModel.dynaElement.sendKeys(Keys.DELETE); 
			//		keywordModel.dynaElement.clear();
					Thread.sleep(200);
					keywordModel.dynaElement.sendKeys(finalDate);
					Thread.sleep(200);
					ReportUtilities.Log(driver,"Entering text in the text box " + keywordModel.objectName,
							"Entered the text " + keywordModel.dataValue, Status.PASS , keywordModel);
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot enter text on the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		} else {
			try {
				if (findElementByType(driver, keywordModel).isDisplayed()) {
					findElementByType(driver, keywordModel).click();
					findElementByType(driver, keywordModel).sendKeys(Keys.CONTROL + "a");
					findElementByType(driver, keywordModel).sendKeys(Keys.DELETE); 
					findElementByType(driver, keywordModel).clear();
				//	Thread.sleep(200);
					findElementByType(driver, keywordModel).sendKeys(finalDate);
					Thread.sleep(500);
					ReportUtilities.Log(driver,"Entering text in the text box " + keywordModel.objectName,
							"Entered the text " + keywordModel.dataValue, Status.PASS , keywordModel);
				}
			} catch (NoSuchElementException p) {
				keywordModel.error = true;
				keywordModel.displayError = true;
				ReportUtilities.Log(driver,"Cannot enter text on the object.", "The Element " + keywordModel.objectName
						+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
				throw new RuntimeException(p);
			}
		}
	}


	/*Method Calculates the first date of the month using offset as parameters and verifies the Eligibility Begin date with UI.
	 *creator : Ronak Shah 506096
	 */
	public  void verifyEligBeginDate(WebDriver driver, KeywordModel keywordModel) throws ParseException, InterruptedException
	{
		int year = Integer.valueOf(KeywordUtilities.getValueFromConfigProperties("Year"));
		int mm = Integer.valueOf(KeywordUtilities.getValueFromConfigProperties("Month"));
		int dd = Integer.valueOf(KeywordUtilities.getValueFromConfigProperties("Date"));
		int offset = Integer.valueOf(keywordModel.dataValue);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c1 = Calendar.getInstance();
		c1.set(year, mm , dd); // 1999 jan 20

		c1.add(Calendar.DATE,offset);   // or  Calendar.DAY_OF_MONTH which is a synonym
		c1.set(Calendar.DAY_OF_MONTH, c1.getActualMinimum(Calendar.DAY_OF_MONTH)); 
		String beginDate = sdf.format(c1.getTime());
		System.out.println("Begin date is"+beginDate );
		String date = findElementByType(driver, keywordModel).getText();
		try {
			
			if (beginDate.equals(date))
			{
				System.out.println("Verified date , The date on UI is :" + date + "Expected date is " +beginDate);		
				ReportUtilities.Log(driver,"Verifying date, The Date on UI is :" + date,
							"Begin date is Verified and is as expected" + beginDate, Status.PASS , keywordModel);	
		
			}
			else
			{
				System.out.println("The date on UI is not as expected :" + date + "Expected date is " +beginDate);		
				ReportUtilities.Log(driver,"Verifying date, The Date on UI is :" + date,
							"Begin Date is not as expected" + beginDate, Status.FAIL , keywordModel);
				storeEDMResult(driver, keywordModel);
				//driver.wait();
				
			}	
			
		}

		catch(Exception e)
		{
			System.out.println("Failed verfying date , The date on UI is :" + date + "Expected date is " +beginDate +"Exception : "+e);
			ReportUtilities.Log(driver,"Failed verfying date , The date selected is :" + date,
					"Expected date is + " + beginDate, Status.FAIL , keywordModel);
        	driver.wait();
        }
	}




	/*Method wait for Element ( Small Wait )
	 *creator : hariom sinha 557173
	 * 
	 */
	public  void smallWaitForElementPresent(WebDriver driver, KeywordModel keywordModel) throws InterruptedException
	{
		int counter = 0;


		try
		{
			while (true)
			{
				counter++;

				try
				{
					Thread.sleep(1000);
					if(findElementByType(driver, keywordModel).isDisplayed())
					{	 
						if(findElementByType(driver, keywordModel).isEnabled())
						{
							// Thread.sleep(10000);
							System.out.println("Object found - Small Wait: "+keywordModel.objectName+" at "+counter);
							ReportUtilities.Log(driver,"Small Wait for Element , Element found  :",
									"Locating the element + " + keywordModel.objectName, Status.PASS , keywordModel);
							break;
						}
					}
				}

				catch (Exception e)
				{
					System.out.println("Object not found - Small wait: "+keywordModel.objectName + "Retrying...");

				}

				if (counter >= 60)
				{
					System.out.println("Object not found  - Small wait: "+keywordModel.objectName);

					break;
				}


			}

		}
		catch(Exception e)
		{
			System.out.println("Object not found by small wait: "+keywordModel.objectName);
			ReportUtilities.Log(driver,"Small Wait for Element , Element not found  :",
					"Locating the element + " + keywordModel.objectName, Status.FAIL , keywordModel);
		}

	}


	/*Method wait for Element ( Small Wait )
	 *creator : hariom sinha 557173
	 * 
	 */
	public  void LongWaitForElementPresent(WebDriver driver, KeywordModel keywordModel) throws InterruptedException
	{
		int counter = 0;


		try
		{
			while (true)
			{
				counter++;

				try
				{
					Thread.sleep(30000);
					if(findElementByType(driver, keywordModel).isDisplayed())
					{
						if(findElementByType(driver, keywordModel).isEnabled())
						{
							System.out.println("Object found - Long Wait: "+keywordModel.objectName+" at "+counter);
							ReportUtilities.Log(driver,"Long Wait for Element , Element found  :",
									"Locating the element + " + keywordModel.objectName, Status.PASS , keywordModel);
							break;
						}
					}
				}

				catch (Exception e)
				{
					System.out.println("Object not found - Long Wait: "+keywordModel.objectName + "Retrying...");

				}

				if (counter >= 24)
				{
					System.out.println("Object not found - Long Wait: "+keywordModel.objectName);

					break;
				}


			}

		}
		catch(Exception e)
		{
			System.out.println("Object not found by Long Wait : "+keywordModel.objectName);
			ReportUtilities.Log(driver,"Long Wait for Element , Element found  :",
					"Locating the element + " + keywordModel.objectName, Status.PASS , keywordModel);
		}

	}

	/*Method for  count Medical EDMs
	 *creator : hariom sinha 557173
	 * 
	 */
	
	public  void verifyCount(WebDriver driver, KeywordModel keywordModel) throws InterruptedException
	{
		int edm_count = findElementsByType(driver, keywordModel).size();
		int datavalue = Integer.parseInt(keywordModel.dataValue);
		try
		{

			System.out.println("Total value on EDM are : "+edm_count);
			ReportUtilities.Log(driver,"EDM Count Method , Total count fetched :",
					"Count on EDM is + " + edm_count, Status.PASS , keywordModel);

			if(edm_count == datavalue)
			{
				System.out.println("Count value is validated successfully");
				ReportUtilities.Log(driver,"EDM Count Method , All passed successfully :",
						"Count on EDM is + " + edm_count, Status.PASS , keywordModel);

			}
			else
			{
				System.out.println("Count value did not matched");
				ReportUtilities.Log(driver,"EDM Count Method , Failed  :",
						"Count did not matched+ ", Status.FAIL , keywordModel);
				storeEDMResult(driver, keywordModel);
			
				//driver.wait();
			}




		}
		catch(Exception e)
		{
			ReportUtilities.Log(driver,"EDM Count Method failed , Total count fetched :",
					"Count on EDM is + " + edm_count, Status.FAIL , keywordModel);
			System.out.println("edm count failed!");
			driver.wait();
		}


	}

	/*Method  - Multiple Select
	 *creator : hariom sinha 557173
	 * 
	 */

	public  void multipleSelect(WebDriver driver, KeywordModel keywordModel) 
	{

		findElementByType(driver, keywordModel);
		String select1 = keywordModel.inputXPath + "/option[1]";
		String select2 = keywordModel.inputXPath + "/option[2]";

		System.out.println("Hitting on multiple select"+select1);
		System.out.println("Hitting on multiple Select"+select2);


		try
		{


			WebElement element1 = driver.findElement(By.xpath(select1));
			WebElement element2 = driver.findElement(By.xpath(select2));
			Actions action = new Actions(driver);
			action.keyDown(Keys.CONTROL).click(element1).click(element2).build().perform();


			ReportUtilities.Log(driver,"Multiple checkboxes selection is ",
					"Passed ", Status.PASS , keywordModel);
		}
		catch(Exception e)
		{
			ReportUtilities.Log(driver,"Multiple Select Failed , Unable to select :",
					"FAILED ", Status.FAIL , keywordModel);
			e.printStackTrace();
		}
		
	}
	
//	public  void downloadAndRename(WebDriver driver, KeywordModel keywordModel) {
//
//			try {
//				if (findElementByType(driver, keywordModel).isDisplayed()) {
//					findElementByType(driver, keywordModel).click();
//					ReportUtilities.Log(driver,"Clicking on the Element " + keywordModel.objectName, "Downloading: Waiting for 15 sec", Status.PASS , keywordModel);
//					
//					try {
//						Thread.sleep(Long.parseLong(Integer.toString(15000))); //Sleeping for 15 sec to download CO
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//					// Renaming logic goes here
//					String JSXpath = "//*[@id=\"MainPanel\"]/script[1]";
//					String js = driver.findElement(By.xpath(JSXpath)).getAttribute("innerHTML");
//					String co_status = js.split("correspondenceStatus = \"")[1].split("\"")[0];
////					System.out.println(co_status);
//					String downloadedFile = null;
//					if(co_status.contentEquals("Completed")) {
//						String generatedDocId = js.split("generateDocId = \"")[1].split("\"")[0];
//						downloadedFile = generatedDocId + "_LocalPrint_Generated.pdf";
//					} else {
//						String triggerId = js.split("reqTriggerId = \"")[1].split("\"")[0];
//						downloadedFile = triggerId + "_LocalPrint_Pending.pdf";
//					}
//					
//					System.out.println("Downloaded File: " + downloadedFile);
//					
//					renameFile(downloadedFile, keywordModel.dataValue);
//					ReportUtilities.Log(driver,"Renaming the file to " + keywordModel.dataValue, "Renamed File", Status.PASS , keywordModel);
//					
//				}
//			} catch (NoSuchElementException p) {
//				keywordModel.error = true;
//				keywordModel.displayError = true;
//				ReportUtilities.Log(driver,"Cannot Click on the object. Download failed.", "The Element " + keywordModel.objectName
//						+ " or JS with Co ID is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
//				throw new RuntimeException(p);
//			}		
//	}
	
	
//	public  void getPDFFile(WebDriver driver, KeywordModel keywordModel) {
//		expected_co_pdf = co_destination + "Expected_CO/" + keywordModel.dataValue + ".pdf";
//		
//		//Fetch current date in a string
//		LocalDateTime now = LocalDateTime.now();
//		String date = DateTimeFormatter.ofPattern("MM-dd-yyyy").format(now);
//		String actual_dest = co_destination + "Actual_CO/RegressionRun_" + date + "/";
//				
//		downloaded_co_pdf = null;
//		String[] files = new File(actual_dest).list();
//		for (String s : files)
//			if(s.matches("^" + keywordModel.dataValue + ".*"))
//				downloaded_co_pdf = actual_dest + "/" + s;
//		
//		System.out.println("Expected : " + expected_co_pdf);
//		System.out.println("Downloaded : " + downloaded_co_pdf);
//		
//		if(downloaded_co_pdf == null) {
//			ReportUtilities.Log(driver,"Downloaded file not found on shared drive", "Failed: getPDFFile()", Status.FAIL , keywordModel);
//			return;
//		}
//	}
	
//	public void storeName(WebDriver driver, KeywordModel keywordModel){
//			int indvCount = Integer.parseInt(keywordModel.dataValue);
//			String extractedData = driver.findElement(By.xpath("(//div[contains(@class,'rpIndividualName')])["+indvCount+"]")).getText();
//			String tempArray[] = extractedData.split(" ");
//			firstName = tempArray[0];
//			lastName = tempArray[1];
//			System.out.println(firstName);
//			System.out.println(lastName);
//		}
		

		
		//This method will fetch the SSN of Inds from Ui and Store them in a  class variable
		//creator : hariom sinha , 557173
		
//		public  void storeSSNName(WebDriver driver, KeywordModel keywordModel){
//			int indvCount = Integer.parseInt(keywordModel.dataValue);
//			SSN = driver.findElement(By.xpath("(//div[contains(text(),'SSN')])["+indvCount+"]/following-sibling::div")).getText();
//			System.out.println(SSN);
//			
//		}
//		
//		//This method will store the Application Number from Applicant Portal 
//		//creator : hariom sinha , 557173
//		
//		public  void storeApplicationNumberFromAP(WebDriver driver, KeywordModel keywordModel) {
//			applicationNumber_AP = driver.findElement(By.xpath("//*[@class='textLinkForReadonlyLink appnumber']")).getText();
//			System.out.println("application no : "+applicationNumber_AP);
//		}

		
	
			public  void FileUpload(WebDriver driver, KeywordModel keywordModel) throws Exception {


		          WebElement chooseFileButton = findElementByType(driver, keywordModel);
		       	     new Actions(driver).click(chooseFileButton).perform();

		       	  Runtime.getRuntime().exec(keywordModel.homePath+"\\Resources\\"+keywordModel.dataValue); 
		     
			}


	/**********************************************************************************************************************************
	 ***************************Application specific keywords begins from this section*************************************************
	 ***************************Any generic keywords should be not included below this************************************************* 
	 ***************************section and must be written above this section*********************************************************
	 **********************************************************************************************************************************/
			  /**
		     * Method Name: initialiseEDMResult Return Type: Nothing Description: This method
		     * calculates total no of EDM fields validated for case and stored as EDM size
		     * @throws InterruptedException 
		     */
//
//		    	public void initialiseEDMResult(WebDriver driver, KeywordModel keywordModel)
//		    	{
//		    		reusableFunctions.initialiseEDMResult(driver,keywordModel);
//		    	}
			
			/**
			 * Method Name: storeEDMResult Return Type: Nothing Description: This method
			 * stores all the EDM validation failures
			 * @throws InterruptedException 
			 */
//			public void storeEDMResult(WebDriver driver, KeywordModel keywordModel) {
//				
//				for(int i=0;i<keywordModel.resultSize;i++)
//				{
//					if(i!=keywordModel.resultCounter)
//					{
//						keywordModel.edmResult[i]="F";
//						keywordModel.resultCounter=i;
//						break;
//					}
//				}
//			}
			
			/**
			 * Method Name: retrieveEDMResult Return Type: Nothing Description: This method
			 * retrieves all the EDM validation failures
			 * @throws InterruptedException 
			 */
//			public void retrieveEDMResult(WebDriver driver, KeywordModel keywordModel) throws InterruptedException {
//				
//				for(int i=0;i<keywordModel.resultSize;i++)
//				{
//					if(keywordModel.edmResult[i].equalsIgnoreCase("F"))
//					{
//						System.out.println("Failures found in EDBC result");
//						driver.wait();
//						break;
//					}
//				}
//				
//				
//			}
//		
//			  /**
//	         * Method Name : verifyAuthSummaryMedicalEDMDyna Return Type : Nothing Description : This
//	         * method is used to verify medical eligibility results on authorization summary page 
//	  		  which requires EDM's as datavalue and date as offsets
//	         * @throws InterruptedException 
//	         */
//	         
//	         public void verifyAuthSummaryMedicalEDMDyna(WebDriver driver, KeywordModel keywordModel) throws InterruptedException 
//	         {
//	          String dataValue = keywordModel.dataValue;
//	          
//	          String[] indivEligibilityDetails = null;
//	         
//	              if (dataValue.contains(";"))
//	              {
//	                  indivEligibilityDetails = dataValue.split(";");
//	              }
//	              else
//	              {
//	                  indivEligibilityDetails = new String[1];
//	                  indivEligibilityDetails[0] = dataValue;
//	              }
//
//	          reusableFunctions.verifyAuthSummaryMedicalEDMDyna(driver,keywordModel,indivEligibilityDetails);     
//	      }
//
//	       
//	       
//	   	/**
//	        * Method Name : StoreAPIndNames Return Type : Array having individual names in case. Description : This
//	        * method is used to store individual names from individual program request page from Applicant Portal
//	        */
//	        public void StoreAPIndNames(WebDriver driver, KeywordModel keywordModel)
//	       
//	       {
//	               int indSize = findElementsByType(driver, keywordModel).size();
//	               reusableFunctions.StoreAPIndNames(driver, keywordModel,indSize);	
//		}
//
//
//		/**
//		 * Method Name : verifyAPMedicalRFI Return Type : Nothing Description : This
//		 * method is used to verify medical RFI on eligibility summary page 
//	   		 which takes medical RFI records as locator and RFI's as datavalue
//	   	 * @throws InterruptedException 
//	          */
//	          
//	          public void verifyAPMedicalRFI(WebDriver driver, KeywordModel keywordModel) throws InterruptedException 
//	          {
//	           String dataValue = keywordModel.dataValue;
//	           String[] indivEligibilityDetails = null;
//	           
//	               if (dataValue.contains(";"))
//	               {
//	                   indivEligibilityDetails = dataValue.split(";");
//	               }
//	               else
//	               {
//	                   indivEligibilityDetails = new String[1];
//	                   indivEligibilityDetails[0] = dataValue;
//	               }
//
//	               reusableFunctions.verifyAPMedicalRFI(driver, keywordModel,indivEligibilityDetails);
//				
//
//	       }
//
//
//	     	/**
//	            * Method Name : verifyAPMedicalRFI Return Type : Nothing Description : This
//	            * method is used to verify medical RFI on eligibility summary page 
//	     		 which takes medical RFI records as locator and RFI's as datavalue and date as offset
//	     	 	* @throws InterruptedException 
//	            */ 
//	            public void verifyAPMedicalRFIDyna(WebDriver driver, KeywordModel keywordModel) throws InterruptedException 
//	                {
//	                 String dataValue = keywordModel.dataValue;
//	                 String[] indivEligibilityDetails = null;
//	                
//	                  if (dataValue.contains(";"))
//	                     {
//	                         indivEligibilityDetails = dataValue.split(";");
//	                     }
//	                     else
//	                     {
//	                         indivEligibilityDetails = new String[1];
//	                         indivEligibilityDetails[0] = dataValue;
//	                     }
//
//	                  reusableFunctions.verifyAPMedicalRFIDyna(driver, keywordModel,indivEligibilityDetails);  
//	                    
//	             }
//	            
//	            public void clickAnnualIncomeEditIcon(WebDriver driver, KeywordModel keywordModel) {
//	        		int icount = Integer.parseInt(keywordModel.dataValue);
//	        		reusableFunctions.clickAnnualIncomeEditIcon(driver,keywordModel,icount);
//	        	}
//	        	
//	        	public void clickMedicalNoticeReasonlink(WebDriver driver, KeywordModel keywordModel) {
//	        		int icount = Integer.parseInt(keywordModel.dataValue);
//	        		reusableFunctions.clickMedicalNoticeReasonlink(driver,keywordModel,icount);
//	        	}
//	        	
//	        	public void clickMedicalRFIlink(WebDriver driver, KeywordModel keywordModel) {
//	        		int icount = Integer.parseInt(keywordModel.dataValue);
//	        		reusableFunctions.clickMedicalRFIlink(driver,keywordModel,icount);
//	        	}       
//	
//	        	/*Method Calculates the TT date and selects from the TT screen
//	        	 *creator : Ronak Shah 506096
//	        	 * 
//	        	 */
//	        	public  void calculateTT(WebDriver driver, KeywordModel keywordModel) throws ParseException
//	        	{
//	        		int year = Integer.valueOf(KeywordUtilities.getValueFromConfigProperties("Year"));
//	        		int mm = Integer.valueOf(KeywordUtilities.getValueFromConfigProperties("Month"));
//	        		int dd = Integer.valueOf(KeywordUtilities.getValueFromConfigProperties("Date"));
//	        		int offset = Integer.valueOf(keywordModel.dataValue);
//	        		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
//	        		Calendar c1 = Calendar.getInstance();
//	        		c1.set(year, mm , dd); // 1999 jan 20
//
//	        		c1.add(Calendar.DATE,offset);   // or  Calendar.DAY_OF_MONTH which is a synonym
//
//	        		String finalDate = sdf.format(c1.getTime());
//
//	        		String TTXpath = "//div[contains(text(),'" + finalDate + "')]/preceding-sibling::div[contains(@class,'30percent')]/input";
//	        		String TTNextXpath = "//div[contains(text(),'" + finalDate + "')]/following-sibling::div[contains(@class,'15percent')]//input";
//
//	        		try
//	        		{
//	        			driver.findElement(By.xpath(TTXpath)).click();
//	        			driver.findElement(By.xpath(TTNextXpath)).click();
//	        			ReportUtilities.Log(driver,"Selecting TT , The Value selected is :" + finalDate,
//	        					"Selected the TTDate+ " + finalDate, Status.PASS , keywordModel);
//	        		}
//
//	        		catch(Exception e)
//	        		{
//	        			//System.out.println("Exception occured while executing calculate TT. Exception : "+e);
//	        			ReportUtilities.Log(driver,"Failed calculating TT , The Value selected is :" + finalDate,
//	        					"TTDate failed to get selected + " + finalDate, Status.FAIL , keywordModel);
//	        		}
//	        	}
//	        	
			
			public void calculateTT(WebDriver driver, KeywordModel keywordModel) throws ParseException
			{
				int year = Integer.valueOf(KeywordUtilities.getValueFromConfigProperties("Year"));
				int mm = Integer.valueOf(KeywordUtilities.getValueFromConfigProperties("Month"));
				int dd = Integer.valueOf(KeywordUtilities.getValueFromConfigProperties("Date"));
				int offset = Integer.valueOf(keywordModel.dataValue);
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				Calendar c1 = Calendar.getInstance();
				c1.set(year, mm , dd); // 1999 jan 20

				c1.add(Calendar.DATE,offset);   // or  Calendar.DAY_OF_MONTH which is a synonym

				String finalDate = sdf.format(c1.getTime());

				String TTXpath = "//div[contains(text(),'" + finalDate + "')]/preceding-sibling::div[contains(@class,'30percent')]/input";
				String TTNextXpath = "//div[contains(text(),'" + finalDate + "')]/following-sibling::div[contains(@class,'15percent')]//input";

				try
				{
					driver.findElement(By.xpath(TTXpath)).click();
					driver.findElement(By.xpath(TTNextXpath)).click();
					ReportUtilities.Log(driver, "Selecting TT , The Value selected is :" + finalDate,
							"Selected the TTDate+ " + finalDate, Status.PASS, keywordModel);
				}

				catch(Exception e)
				{
					//System.out.println("Exception occured while executing calculate TT. Exception : "+e);
					ReportUtilities.Log(driver, "Failed calculating TT , The Value selected is :" + finalDate,
							"TTDate failed to get selected + " + finalDate, Status.FAIL, keywordModel);
				}
			}
//	        	/**
//	             * Method Name : verifyMedicalEDMDyna Return Type : Nothing Description : This
//	             * method is used to verify medical eligibility results on eligibility summary page 
//	      		 which takes medical edm records as locator and EDM's as datavalue with date as offsets
//	             * @throws InterruptedException 
//	             */
//	             
//	             public void verifyMedicalEDMDyna(WebDriver driver, KeywordModel keywordModel) throws InterruptedException 
//	             {
//	              String dataValue = keywordModel.dataValue;
//	              
//	              String[] indivEligibilityDetails = null;
//	               if (dataValue.contains(";"))
//	                  {
//	                      indivEligibilityDetails = dataValue.split(";");
//	                  }
//	                  else
//	                  {
//	                      indivEligibilityDetails = new String[1];
//	                      indivEligibilityDetails[0] = dataValue;
//	                  }
//
//	                 
//	                  int count = findElementsByType(driver, keywordModel).size();
//	               reusableFunctions.verifyMedicalEDMDyna(driver,keywordModel,indivEligibilityDetails,count);   
//	          }
//	             
			
			public void verifyMedicalEDMDyna(WebDriver driver, KeywordModel keywordModel) throws InterruptedException 
	        {
	         String dataValue = keywordModel.dataValue;
	         String indivName = "";
	         
	         String[] indivEligibilityDetails = null;
	         String status = "";
	         String toa = "";
	         String beginDt = "";
	         String endDt = "";
	         int num = 100;
	        
	         String[] indivDetail = null;
	         
	         String calBeginDate = "";
	         String calEndDate = "";
	         
	         try
	         {
	             if (dataValue.contains(";"))
	             {
	                 indivEligibilityDetails = dataValue.split(";");
	             }
	             else
	             {
	                 indivEligibilityDetails = new String[1];
	                 indivEligibilityDetails[0] = dataValue;
	             }

	            
	             int count = findElementsByType(driver, keywordModel).size();
	            

	             int indSize = driver.findElements(By.xpath("//div[contains(@class,'rpIndividualName')]")).size();


	             int[] temp = new int[count];
	             for (int l = 0; l < count; l++)
	             {
	                 temp[l] = num;
	                 num = num + 1;
	             }

	             int x = 0;
	             int j = 0;
	           
	             for (j = 1; j <= count; j++)
	             {

	                 indivName = driver.findElement(By.xpath("(//div[contains(@class,'MedicalGrid')]//div[4])[" + j + "]")).getText();


	                 for (int k = 1; k <= indSize; k++)
	                 {
	                     String ind_name = driver.findElement(By.xpath("(//div[contains(@class,'rpIndividualName')])[" + k + "]")).getText();

	                     if (ind_name.contains(indivName) /*&& indivDetail[0].Equals("Indiv"+k+"")*/)
	                     {
	                         x = j;
	                         for (int i = 0, z = 0; i < count && z < temp.length; i++, z++)
	                         {
	                             indivDetail = indivEligibilityDetails[i].split("\\|");

	                             if (indivDetail[0].equals("Indiv" + k + "") && temp[z] != i)
	                             {
	                                 temp[z] = i;

	                                 break;
	                             }
	                         }
	                         break;
	                     }
	                  

	                 }

	                 

	                 toa = driver.findElement(By.xpath("(//div[contains(@class,'MedicalGrid')]//div[3])[" + x + "]")).getText();
	                
	                 beginDt = driver.findElement(By.xpath("(//div[contains(@class,'MedicalGrid')]//div[5])[" + x + "]")).getText();
	                
	                 endDt = driver.findElement(By.xpath("(//div[contains(@class,'MedicalGrid')]//div[6])[" + x + "]")).getText();
	                 
	                 status = driver.findElement(By.xpath("(//div[contains(@class,'MedicalGrid')]//div[8])[" + x + "]")).getText();
	                
	                calBeginDate = calMABeginDate(indivDetail[3], driver, keywordModel);
	                calEndDate = calMAEndDate(indivDetail[4], driver, keywordModel);
	                 
	                 if (status.equals(indivDetail[1]))
	                 {
	                     
	                     System.out.println("Eligibility Status is as expected, Eligibility Status for record " + j +"is "+status);
	                     ReportUtilities.Log(driver,"Verifying Status for record "+ j,"The eligibility Status are  as expected for record  " + j + " having text "  + status, Status.PASS,keywordModel);
	                 }
	                 else
	                 {
	                     
	                     System.out.println("Eligibility Status is not as expected, Eligibility Status for record " + j + " on UI is "+status+"While expected Eligibility Status is"+indivDetail[1]);
	                     ReportUtilities.Log(driver,"Verifying Status for record " + j, "The eligibility Status is not as expected for record  " + j + " having text on UI as " + status+"while expected status is"+ indivDetail[1], Status.FAIL,keywordModel);
	                     storeEDMResult(driver, keywordModel);
	                     //  driver.wait();
	                 }
	                 if (toa.equals(indivDetail[2]))
	                 {
	                     System.out.println("TOA, for the record " + j + " are as expected");
	                     ReportUtilities.Log(driver,"Verifying TOA for record " + j, "The eligibility TOA are as expected for record  " + j + " having text " + toa, Status.PASS, keywordModel);
	                 }
	                 else
	                 {
	                    
	                     System.out.println("TOA, for the record " + j + " has failed");
	                     ReportUtilities.Log(driver,"Verifying TOA for record " + j,"The eligibility TOA are not as expected for record  [" + j + "]having text " + toa, Status.FAIL, keywordModel);
	                     storeEDMResult(driver, keywordModel);
	                    // driver.wait();
	                 }
	                 if (beginDt.equals(calBeginDate))
	                 {
	                    
	                     System.out.println("Begin Date for the record " + j + " are as expected");
	                     ReportUtilities.Log(driver,"Verifying Begin Date for record " + j,"The Begin Date are as expected for record  " + j + " having text " + beginDt, Status.PASS, keywordModel);
	                    
	                 }
	                 else
	                 {
	                    
	                     System.out.println("Begin Date for the record " + j + " has failed");
	                     ReportUtilities.Log(driver,"Verifying Begin Date for record " + j, "The Begin Date are not as expected for record  " + j + " having text " + beginDt, Status.FAIL, keywordModel);
	                     storeEDMResult(driver, keywordModel);
	                     //driver.wait();
	                 }
	                 if (endDt.equals(calEndDate))
	                 {
	                     
	                     System.out.println("End Date for the record " + j + " are as expected");
	                     ReportUtilities.Log(driver,"Verifying End Date for record " + j, "The End Date are as expected for record  " + j + " having text " + endDt, Status.PASS,keywordModel);
	                 }
	                 else
	                 {
	                     
	                     System.out.println(" End Date for the record " + j + " has failed");
	                     ReportUtilities.Log(driver,"Verifying End Date for record " + j, "The End Date are not as expected for record  " + j + " having text " + endDt, Status.FAIL,keywordModel);
	                     storeEDMResult(driver, keywordModel);
	                    // driver.wait();
	                 }

	               

	             }
	             

	         }
	         catch (Exception e)
	         {
	        	 ReportUtilities.Log(driver,"Verifying the medical EDM records " + keywordModel.objectName , "The values are not displayed as expected", Status.FAIL,keywordModel);
	              driver.wait();
	         }

	     }
//	             public void PerformLogin(WebDriver driver, KeywordModel keywordModel)
//	             {
//	         		
//	         		POI_ReadExcel poiObject = new POI_ReadExcel();
//	         		ArrayList<String> whereClause_TestData = new ArrayList<String>();
//	         		whereClause_TestData.add("Environment::" + KeywordTestSettings.ENV);
//	         		String application = KeywordUtilities.getValueFromConfigProperties("App1");
//	         		
//	         		
//	         			try {
//	         				
//	         					KeywordTestSettings.ApplicationCredentialsSheetName = application;	
//	         					KeywordTestSettings.LoginResult = poiObject.fetchWithCondition(KeywordTestSettings.ApplicationCredentialsFileName, KeywordTestSettings.ApplicationCredentialsSheetName, whereClause_TestData);
//	         					reusableFunctions.LoginApp1(KeywordTestSettings.LoginResult, driver, keywordModel);
//	         					
//	         				
//	         			} catch (Exception e) {
//	         				// TODO Auto-generated catch block
//	         				e.printStackTrace();
//	         			}
//	         		
//	         		
//	         	//	System.out.println(result);
//	         		
//	         	}   
//	             
//	             public  void getIndividualID(WebDriver driver, KeywordModel keywordModel) {
//	         		try {
//	         			int i = Integer.parseInt(keywordModel.dataValue);
//	         				WebElement webElement = driver.findElement(By.xpath("(//div[contains(@class,'rpIndividualId')])["+ i +"]"));
//	         				String elementValue = webElement.getText();
//	         				// System.out.println("Individual ID: " + elementValue);
//	         				keywordModel.individualID = elementValue;
//	         				// System.out.println("Individual Id is : " + keywordModel.individualID);
//	         			
//	         		} catch (NoSuchElementException p) {
//	         			keywordModel.error = true;
//	         			keywordModel.displayError = true;
//	         			ReportUtilities.Log(driver,"Cannot get Data from the object.",
//	         					"The element " + keywordModel.objectName + " is NOT displayed on the current screen" + keywordModel.ScreenName,
//	         					Status.FAIL , keywordModel);
//	         			throw new RuntimeException(p);
//	         		}
//	         	}
//
			
	//		public void getIndividualID(WebDriver driver, KeywordModel keywordModel) {
		//		try {
		//			if (findElementByType(driver, keywordModel).isDisplayed()) {
			//			WebElement webElement = findElementByType(driver, keywordModel);
				//		String elementValue = webElement.getText();
					//	// System.out.println("Individual ID: " + elementValue);
						//keywordModel.individualID = elementValue;
						// System.out.println("Individual Id is : " + individualID);
				//	}
			//	} catch (NoSuchElementException p) {
				//	keywordModel.error = true;
				//	keywordModel.displayError = true;
				//	ReportUtilities.Log(driver,"Cannot get Data from the object.",
				//			"The element " + keywordModel.objectName + " is NOT displayed on the current screen" + keywordModel.ScreenName,
				//			Status.FAIL, keywordModel);
				//	throw new RuntimeException(p);
				//}
			//}

	public static void getIndividualID(WebDriver driver, KeywordModel keywordModel) {
				try {
					int i = Integer.parseInt(keywordModel.dataValue);
						WebElement webElement = driver.findElement(By.xpath("(//div[contains(@class,'rpIndividualId')])["+ i +"]"));
						String elementValue = webElement.getText();
						// System.out.println("Individual ID: " + elementValue);
						keywordModel.individualID = elementValue;
						// System.out.println("Individual Id is : " + individualID);
					
				} catch (NoSuchElementException p) {
					keywordModel.error = true;
					keywordModel.displayError = true;
					ReportUtilities.Log(driver,"Cannot get Data from the object.",
							"The element " + keywordModel.objectName + " is NOT displayed on the current screen" + keywordModel.ScreenName,
							Status.FAIL,keywordModel);
					throw new RuntimeException(p);
				}
			}		
			
//	         	public  void getCaseNumber(WebDriver driver, KeywordModel keywordModel) {
//	         		try {
//	         			if (findElementByType(driver, keywordModel).isDisplayed()) {
//	         				WebElement webElement = findElementByType(driver, keywordModel);
//	         				String elementValue = webElement.getText();
//	         				elementValue = elementValue.replace("Case:", "");
//	         				keywordModel.caseNumber = elementValue;
//	         				System.out.println("Case Number : " + keywordModel.caseNumber);
//	         				// writeToOutputFile(keywordModel.caseNumber);
//	         			}
//	         		} catch (NoSuchElementException p) {
//	         			keywordModel.error = true;
//	         			keywordModel.displayError = true;
//	         			ReportUtilities.Log(driver,"Cannot get Data from the object.",
//	         					"The element " + keywordModel.objectName + " is NOT displayed on the current screen" + keywordModel.ScreenName,
//	         					Status.FAIL , keywordModel);
//	         			throw new RuntimeException(p);
//	         		}
//	         	}
//	         	
			
			public void getCaseNumber(WebDriver driver, KeywordModel keywordModel) {
				try {
					if (findElementByType(driver, keywordModel).isDisplayed()) {
						WebElement webElement = findElementByType(driver, keywordModel);
						String elementValue = webElement.getText();
						elementValue = elementValue.replace("Case:", "");
						keywordModel.caseNumber = elementValue;
						System.out.println("Case Number : " + keywordModel.caseNumber);
						// writeToOutputFile(caseNumber);
					}
				} catch (NoSuchElementException p) {
					keywordModel.error = true;
					keywordModel.displayError = true;
					ReportUtilities.Log(driver,"Cannot get Data from the object.",
							"The element " + keywordModel.objectName + " is NOT displayed on the current screen" + keywordModel.ScreenName,
							Status.FAIL, keywordModel);
					throw new RuntimeException(p);
				}
			}
//	         	public  void getTTDate(WebDriver driver, KeywordModel keywordModel) {
//	         		try {
//	         			if (findElementByType(driver, keywordModel).isDisplayed()) {
//	         				WebElement webElement = findElementByType(driver, keywordModel);
//	         				String elementValue = webElement.getText();
//	         				String date[] = elementValue.split("/");
//	         		
//	         				keywordModel.TTDate = "'"+date[2]+date[0]+date[1]+"'";
//	         				System.out.println("Time Tavelled date is : " + keywordModel.TTDate);
//	         				// writeToOutputFile(keywordModel.caseNumber);
//	         			}
//	         		} catch (NoSuchElementException p) {
//	         			keywordModel.error = true;
//	         			keywordModel.displayError = true;
//	         			ReportUtilities.Log(driver,"Cannot get Data from the object.",
//	         					"The element " + keywordModel.objectName + " is NOT displayed on the current screen" + keywordModel.ScreenName,
//	         					Status.FAIL , keywordModel);
//	         			throw new RuntimeException(p);
//	         		}
//	         	}  
			
			public void getTTDate(WebDriver driver, KeywordModel keywordModel) {
				try {
					if (findElementByType(driver, keywordModel).isDisplayed()) {
						WebElement webElement = findElementByType(driver, keywordModel);
						String elementValue = webElement.getText();
						String date[] = elementValue.split("/");
				
						keywordModel.TTDate = "'"+date[2]+date[0]+date[1]+"'";
						System.out.println("Time Tavelled date is : " + keywordModel.TTDate);
						// writeToOutputFile(caseNumber);
					}
				} catch (NoSuchElementException p) {
					keywordModel.error = true;
					keywordModel.displayError = true;
					ReportUtilities.Log(driver,"Cannot get Data from the object.",
							"The element " + keywordModel.objectName + " is NOT displayed on the current screen" + keywordModel.ScreenName,
							Status.FAIL, keywordModel);
					throw new RuntimeException(p);
				}
			}
			
			
			
//			public  void storeEDMResult(WebDriver driver, KeywordModel keywordModel) {
//				
//				
//				for(int i=0;i<keywordModel.resultSize;i++)
//				{
//					if(i!=keywordModel.resultCounter)
//					{
//						keywordModel.edmResult[i]="W";
//						keywordModel.resultCounter=i;
//						break;
//					}
//				}
//			}


//			public  void retrieveEDMResult(WebDriver driver, KeywordModel keywordModel) throws InterruptedException 
//			{
//				for(int i=0;i<keywordModel.resultSize;i++)
//					{
//						if(keywordModel.edmResult[i].equalsIgnoreCase("W"))
//							{
//								System.out.println("Failures found in EDBC result");
//								driver.wait();
//								break;
//							}
//					}
//			}
						


//						 public void verifyAuthSummaryMedicalEDMDyna(WebDriver driver, KeywordModel keywordModel) throws InterruptedException 
//				         {
//				          String dataValue = keywordModel.dataValue;
//				          String indivName = "";
//				          
//				          String[] indivEligibilityDetails = null;
//				          String status = "";
//				          String toa = "";
//				          String beginDt = "";
//				          String endDt = "";
//				          int num = 100;
//				         
//				          String[] indivDetail = null;
//				          String calBeginDate="";
//				          String calEndDate="";
//				         
//				          
//				          try
//				          {
//				              if (dataValue.contains(";"))
//				              {
//				                  indivEligibilityDetails = dataValue.split(";");
//				              }
//				              else
//				              {
//				                  indivEligibilityDetails = new String[1];
//				                  indivEligibilityDetails[0] = dataValue;
//				              }
//
//				             
//				              int indSize = driver.findElements(By.xpath("//div[contains(@class,'rpIndividualName')]")).size();
//				              String[] indNameList = new String[indSize];
//
//				              driver.findElement(By.xpath("//a[@id = 'viewAuthorizationHistory']")).click();
//				              Thread.sleep(3000);
//				              driver.findElement(By.xpath("//input[@id='btnSearch']")).click();
//				              Thread.sleep(3000);
//				              int count = driver.findElements(By.xpath("//div[contains(@class,'MedicalGrid')]")).size();
//				              System.out.println("Total Medical EDMs on Auth History is : " + count);
//
//
//				              for (int i = 1; i <= indSize; i++)
//				              {
//				                  indNameList[i - 1] = driver.findElement(By.xpath("(//div[contains(@class,'rpIndividualName')])[" + i + "]")).getText();
//
//				              }
//
//				              int[] temp = new int[count];
//				              for (int l = 0; l < count; l++)
//				              {
//				                  temp[l] = num;
//				                  num = num + 1;
//				              }
//
//				              int x = 0;
//				              int j = 0;
//				            
//				              for (j = 1; j <= count; j++)
//				              {
//
//				                  indivName = driver.findElement(By.xpath("(//div[contains(@class,'MedicalGrid')]//div[4])[" + j + "]")).getText();
//
//
//				                  for (int k = 1; k <= indSize; k++)
//				                  {
//				                 	 String ind_name = indNameList[k - 1];		
//
//				                      if (ind_name.contains(indivName) /*&& indivDetail[0].Equals("Indiv"+k+"")*/)
//				                      {
//				                          x = j;
//				                          for (int i = 0, z = 0; i < count && z < temp.length; i++, z++)
//				                          {
//				                              indivDetail = indivEligibilityDetails[i].split("\\|");
//
//				                              if (indivDetail[0].equals("Indiv" + k + "") && temp[z] != i)
//				                              {
//				                                  temp[z] = i;
//
//				                                  break;
//				                              }
//				                          }
//				                          break;
//				                      }
//				                   
//
//				                  }
//
//				                  calBeginDate = calMABeginDate(indivDetail[3], driver, keywordModel);
//				                  calEndDate = calMAEndDate(indivDetail[4], driver, keywordModel);
//
//				                  toa = driver.findElement(By.xpath("(//div[contains(@class,'MedicalGrid')]//div[3])[" + x + "]")).getText();
//				                 
//				                  beginDt = driver.findElement(By.xpath("(//div[contains(@class,'MedicalGrid')]//div[5])[" + x + "]")).getText();
//				                 
//				                  endDt = driver.findElement(By.xpath("(//div[contains(@class,'MedicalGrid')]//div[6])[" + x + "]")).getText();
//				                  
//				                  status = driver.findElement(By.xpath("(//div[contains(@class,'MedicalGrid')]//div[8])[" + x + "]")).getText();
//				                 
//				                  
//				                  if (status.contains(indivDetail[1]))
//				                  {
//				                      
//				                      System.out.println(" Eligibility Status for record " + j + " are as expected");
//				                      ReportUtilities.Log(driver,"Verifying Status for record "+ j,"The eligibility Status are  as expected for record  " + j + " having text "  + status,Status.PASS , keywordModel);
//				                  }
//				                  else
//				                  {
//				                      
//				                      System.out.println(" Eligibility Status for record " + j + " has failed");
//				                      ReportUtilities.Log(driver,"Verifying Status for record " + j, "The eligibility Status are not as expected for record  " + j + " having text " + status, Status.FAIL, keywordModel);
//				                      storeEDMResult(driver, keywordModel);
//				                     // driver.wait();
//				                  }
//				                  if (toa.contains(indivDetail[2]))
//				                  {
//				                     
//				                      System.out.println("TOA, for the record " + j + " are as expected");
//				                      ReportUtilities.Log(driver,"Verifying TOA for record " + j, "The eligibility TOA are as expected for record  " + j + " having text " + toa, Status.PASS, keywordModel);
//				                  }
//				                  else
//				                  {
//				                     
//				                      System.out.println("TOA, for the record " + j + " has failed");
//				                      ReportUtilities.Log(driver,"Verifying TOA for record " + j,"The eligibility TOA are not as expected for record  [" + j + "]having text " + toa, Status.FAIL, keywordModel);
//				                      storeEDMResult(driver, keywordModel);
//				                      //driver.wait();
//				                  }
//				                  if (beginDt.contains(calBeginDate))
//				                  {
//				                     
//				                      System.out.println("Begin Date for the record " + j + " are as expected");
//				                      ReportUtilities.Log(driver,"Verifying Begin Date for record " + j,"The Begin Date are as expected for record  " + j + " having text " + beginDt, Status.PASS, keywordModel);
//				                  }
//				                  else
//				                  {
//				                     
//				                      System.out.println("Begin Date for the record " + j + " has failed");
//				                      ReportUtilities.Log(driver,"Verifying Begin Date for record " + j, "The Begin Date are not as expected for record  " + j + " having text " + beginDt, Status.FAIL, keywordModel);
//				                     storeEDMResult(driver, keywordModel);
//				                      // driver.wait();
//				                  }
//				                  if (endDt.contains(calEndDate))
//				                  {
//				                      
//				                      System.out.println("End Date for the record " + j + " are as expected");
//				                      ReportUtilities.Log(driver,"Verifying End Date for record " + j, "The End Date are as expected for record  " + j + " having text " + endDt, Status.PASS, keywordModel);
//				                  }
//				                  else
//				                  {
//				                      
//				                      System.out.println(" End Date for the record " + j + " has failed");
//				                      ReportUtilities.Log(driver,"Verifying End Date for record " + j, "The End Date are not as expected for record  " + j + " having text " + endDt, Status.FAIL, keywordModel);
//				                      storeEDMResult(driver, keywordModel);
//				                      //driver.wait();
//				                  }
//
//				                
//
//				              }
//				              
//
//				          }
//				          catch (Exception e)
//				          {
//				        	  ReportUtilities.Log(driver,"Verifying the medical EDM records " + keywordModel.objectName , "The values are not displayed as expected", Status.FAIL, keywordModel);
//				               driver.wait();
//				          }
//
//				      }



//			  public  String[] StoreAPIndNames(WebDriver driver, KeywordModel keywordModel)
//					       
//					       {
//					           try
//					           {
//					               int indSize = findElementsByType(driver, keywordModel).size();
//
//					               keywordModel.indNameList = new String[indSize];
//								for (int i = 1; i <= indSize; i++)
//								{
//									keywordModel.indNameList[i - 1] = driver.findElement(By.xpath("(//table[@id='tableUsersProgram']//div[@class='caption'])[" + i + "]")).getText();
//								}
//
//							}
//							catch (Exception e)
//							{
//								System.out.println("Storing AP individual names has failed");
//
//							}
//							return keywordModel.indNameList;
//						}
						

//			public  void verifyAPMedicalRFI(WebDriver driver, KeywordModel keywordModel) throws InterruptedException 
//				          {
//				           String dataValue = keywordModel.dataValue;
//				           String indivName = "";
//				           String programName = "";
//				           String RFI = "";
//				           String DueDate = "";
//				           String[] indivEligibilityDetails = null;
//				          
//				           int num = 100;
//				          
//				           String[] indivDetail = null;
//				           
//				       
//				           
//				           try
//				           {
//				               if (dataValue.contains(";"))
//				               {
//				                   indivEligibilityDetails = dataValue.split(";");
//				               }
//				               else
//				               {
//				                   indivEligibilityDetails = new String[1];
//				                   indivEligibilityDetails[0] = dataValue;
//				               }
//
//
//							int count = driver.findElements(By.xpath("//*[@id='0~ProofListToSubmitGrid~']//tbody/tr")).size();
//
//
//
//							int[] temp = new int[count];
//							for (int l = 0; l < count; l++)
//							{
//								temp[l] = num;
//								num = num + 1;
//							}
//
//							int x = 0;
//							int j = 0;
//
//							for (j = 1; j <= count; j++)
//							{
//
//								indivName = driver.findElement(By.xpath("(//*[contains(@id,'ProofListToSubmit~HouseHoldMemeberName~td')])[" + j + "]")).getText();
//
//
//								for (int k = 1; k <= keywordModel.indNameList.length; k++)
//								{
//									String ind_name = keywordModel.indNameList[k - 1];
//
//									if (ind_name.contains(indivName) /*&& indivDetail[0].Equals("Indiv"+k+"")*/)
//									{
//										x = j;
//										for (int i = 0, z = 0; i < count && z < temp.length; i++, z++)
//										{
//											indivDetail = indivEligibilityDetails[i].split("\\|");
//
//											if (indivDetail[0].equals("Indiv" + k + "") && temp[z] != i)  //3
//											{
//												temp[z] = i;
//
//												break;
//											}
//										}
//										break;
//									}
//
//
//								}
//
//
//								programName = driver.findElement(By.xpath("(//*[contains(@id,'ProofListToSubmit~Program~td')])[" + x + "]")).getText();
//
//								RFI = driver.findElement(By.xpath("(//*[contains(@id,'ProofListToSubmit~ProofType~td')])[" + x + "]")).getText();
//
//								DueDate = driver.findElement(By.xpath("(//*[contains(@id,'ProofListToSubmit~VerificationChecklistDueDateNeeded~td')])[" + x + "]")).getText();
//
//								//   RFIDate = verifyAPRFIDate(indivDetail[3]);
//
//				                   if (programName.equals(indivDetail[1]))
//				                   {
//				                       
//				                       System.out.println(" Program Name for record " + j + " are as expected");
//				                       ReportUtilities.Log(driver,"Verifying Program Name for record "+ j,"The Program Names are  as expected for record  " + j + " having text "  + programName, Status.PASS, keywordModel);
//				                   }
//				                   else
//				                   {
//				                       
//				                       System.out.println(" Program Name for record " + j + " has failed");
//				                       ReportUtilities.Log(driver,"Verifying Program Name for record " + j, "The Program Name are not as expected for record  " + j + " having text " + programName, Status.FAIL, keywordModel);
//				                       driver.wait();
//				                   }
//				                   if (RFI.equals(indivDetail[2]))
//				                   {
//				                      
//				                       System.out.println("RFI, for the record " + j + " are as expected");
//				                       ReportUtilities.Log(driver,"Verifying RFI for record " + j, "The RFI are as expected for record  " + j + " having text " + RFI, Status.PASS, keywordModel);
//				                       
//				                   }
//				                   else
//				                   {
//				                      
//				                       System.out.println("RFI, for the record " + j + " has failed");
//				                       ReportUtilities.Log(driver,"Verifying RFI for record " + j,"The eligibility TOA are not as expected for record  [" + j + "]having text " + RFI, Status.FAIL, keywordModel);
//				                       driver.wait();
//				                   }
//				                   if (DueDate.equals(indivDetail[3]))
//				                   {
//				                      
//				                       System.out.println("Due Date for the record " + j + " are as expected");
//				                       ReportUtilities.Log(driver,"Verifying Due Date for record " + j,"The Begin Date are as expected for record  " + j + " having text " + DueDate, Status.PASS, keywordModel);
//				                   }
//				                   else
//				                   {
//				                      
//				                       System.out.println("Due Date for the record " + j + " has failed");
//				                       ReportUtilities.Log(driver,"Verifying Due Date for record " + j, "The Begin Date are not as expected for record  " + j + " having text " + DueDate, Status.FAIL, keywordModel);
//				                       driver.wait();
//				                   }
//				                  
//
//				               }
//				               
//
//				           }
//				           catch (Exception e)
//				           {
//				        	   ReportUtilities.Log(driver,"Verifying the Medical RFI records " + keywordModel.objectName , "The values are not displayed as expected", Status.FAIL, keywordModel);
//				                driver.wait();
//				           }
//
//				       }


//			 public  void verifyAPMedicalRFIDyna(WebDriver driver, KeywordModel keywordModel) throws InterruptedException 
//			               {
//			                String dataValue = keywordModel.dataValue;
//			                String indivName = "";
//			                String programName = "";
//			                String RFI = "";
//			                String DueDate = "";
//			                String[] indivEligibilityDetails = null;
//			                String RFIDate="";
//			                int num = 100;
//			               
//			                String[] indivDetail = null;
//			                
//			            
//			                
//			                try
//			                {
//			                    if (dataValue.contains(";"))
//			                    {
//			                        indivEligibilityDetails = dataValue.split(";");
//			                    }
//			                    else
//			                    {
//			                        indivEligibilityDetails = new String[1];
//			                        indivEligibilityDetails[0] = dataValue;
//			                    }
//
//			                   
//			                    int count = driver.findElements(By.xpath("//*[@id='0~ProofListToSubmitGrid~']//tbody/tr")).size();
//			                   
//
//
//			                    int[] temp = new int[count];
//			                    for (int l = 0; l < count; l++)
//			                    {
//			                        temp[l] = num;
//			                        num = num + 1;
//			                    }
//
//			                    int x = 0;
//			                    int j = 0;
//			                  
//			                    for (j = 1; j <= count; j++)
//			                    {
//
//			                        indivName = driver.findElement(By.xpath("(//*[contains(@id,'ProofListToSubmit~HouseHoldMemeberName~td')])[" + j + "]")).getText();
//
//
//			                        for (int k = 1; k <= keywordModel.indNameList.length; k++)
//			                        {
//			                            String ind_name = keywordModel.indNameList[k - 1];
//
//			                            if (ind_name.contains(indivName) /*&& indivDetail[0].Equals("Indiv"+k+"")*/)
//			                            {
//			                                x = j;
//			                                for (int i = 0, z = 0; i < count && z < temp.length; i++, z++)
//			                                {
//			                                    indivDetail = indivEligibilityDetails[i].split("\\|");
//
//			                                    if (indivDetail[0].equals("Indiv" + k + "") && temp[z] != i)  //3
//			                                    {
//			                                        temp[z] = i;
//			                                     
//			                                        break;
//			                                    }
//			                                }
//			                                break;
//			                            }
//			                          
//
//			                        }
//			                      
//
//			                        programName = driver.findElement(By.xpath("(//*[contains(@id,'ProofListToSubmit~Program~td')])[" + x + "]")).getText();
//			                    
//			                        RFI = driver.findElement(By.xpath("(//*[contains(@id,'ProofListToSubmit~ProofType~td')])[" + x + "]")).getText();
//			                      
//			                        DueDate = driver.findElement(By.xpath("(//*[contains(@id,'ProofListToSubmit~VerificationChecklistDueDateNeeded~td')])[" + x + "]")).getText();
//			                      
//			                        RFIDate = verifyAPRFIDate(indivDetail[3], driver, keywordModel);
//
//			                        if (programName.equals(indivDetail[1]))
//			                        {
//			                            
//			                            System.out.println(" Program Name for record " + j + " are as expected");
//			                            ReportUtilities.Log(driver,"Verifying Program Name for record "+ j,"The Program Names are  as expected for record  " + j + " having text "  + programName, Status.PASS, keywordModel);
//			                        }
//			                        else
//			                        {
//			                            
//			                            System.out.println(" Program Name for record " + j + " has failed");
//			                            ReportUtilities.Log(driver,"Verifying Program Name for record " + j, "The Program Name are not as expected for record  " + j + " having text " + programName, Status.FAIL, keywordModel);
//			                           storeEDMResult(driver, keywordModel);
//			                            // driver.wait();
//			                        }
//			                        if (RFI.equals(indivDetail[2]))
//			                        {
//			                           
//			                            System.out.println("RFI, for the record " + j + " are as expected");
//			                            ReportUtilities.Log(driver,"Verifying RFI for record " + j, "The RFI are as expected for record  " + j + " having text " + RFI, Status.PASS, keywordModel);
//			                            
//			                        }
//			                        else
//			                        {
//			                           
//			                            System.out.println("RFI, for the record " + j + " has failed");
//			                            ReportUtilities.Log(driver,"Verifying RFI for record " + j,"The eligibility TOA are not as expected for record  [" + j + "]having text " + RFI, Status.FAIL, keywordModel);
//			                            storeEDMResult(driver, keywordModel);
//			                           // driver.wait();
//			                        }
//			                        if (DueDate.equals(RFIDate))
//			                        {
//			                           
//			                            System.out.println("Due Date for the record " + j + " are as expected");
//			                            ReportUtilities.Log(driver,"Verifying Due Date for record " + j,"The Begin Date are as expected for record  " + j + " having text " + DueDate, Status.PASS, keywordModel);
//			                        }
//			                        else
//			                        {
//			                           
//			                            System.out.println("Due Date for the record " + j + " has failed");
//			                            ReportUtilities.Log(driver,"Verifying Due Date for record " + j, "The Begin Date are not as expected for record  " + j + " having text " + DueDate, Status.FAIL, keywordModel);
//			                           storeEDMResult(driver, keywordModel);
//			                         //  driver.wait();
//			                        }
//			                       
//
//			                    }
//			                    
//
//			                }
//			                catch (Exception e)
//			                {
//			                	ReportUtilities.Log(driver,"Verifying the Medical RFI records " + keywordModel.objectName , "The values are not displayed as expected", Status.FAIL, keywordModel);
//			                     driver.wait();
//			                }
//
//			            }

//			   public void clickAnnualIncomeEditIcon(WebDriver driver, KeywordModel keywordModel) {
//								int icount = Integer.parseInt(keywordModel.dataValue);
//								String indCompleteName = driver.findElement(By.xpath("(//div[contains(@class,'rpIndividualName')])["+icount+"]")).getText();
//								String[] arrayName = indCompleteName.split(" ");
//								String indName = arrayName[0]+" "+arrayName[1];
//								System.out.println("Clicking 100% Annual income edit icon for ind "+indName);
//								String newXpathString ="(//td[contains(text(),'"+indName+"')]/following-sibling::td[4]//img[@class='editRecord'])["+keywordModel.jcount+"]";
//								driver.findElement(By.xpath(newXpathString)).click();
//								ReportUtilities.Log(driver,"clicking on 100% annual income edit icon " + keywordModel.dataValue,
//										"clicked on 100% annual income edit icon " + keywordModel.dataValue, Status.PASS, keywordModel);
//							}

//			 public void clickMedicalNoticeReasonlink(WebDriver driver, KeywordModel keywordModel) {
//								int icount = Integer.parseInt(keywordModel.dataValue);
//								String indCompleteName = driver.findElement(By.xpath("(//div[contains(@class,'rpIndividualName')])["+icount+"]")).getText();
//								String[] arrayName = indCompleteName.split(" ");
//								String indName = arrayName[0]+" "+arrayName[1];
//								System.out.println("Clicking medical notice reason link for ind "+indName);
//								String newXpathString ="(//div[contains(@class,'MedicalGrid')]/div[contains(text(),'"+indName+"')]/following-sibling::div[5]/a[1])["+keywordModel.jcount+"]" ;
//								driver.findElement(By.xpath(newXpathString)).click();
//								ReportUtilities.Log(driver,"clicking on medical notice reason link " + keywordModel.dataValue,
//										"clicked on medical notice reason link " + keywordModel.dataValue, Status.PASS, keywordModel);
//							}

//					public  void clickMedicalRFIlink(WebDriver driver, KeywordModel keywordModel) {
//								int icount = Integer.parseInt(keywordModel.dataValue);
//								String indCompleteName = driver.findElement(By.xpath("(//div[contains(@class,'rpIndividualName')])["+icount+"]")).getText();
//								String[] arrayName = indCompleteName.split(" ");
//								String indName = arrayName[0]+" "+arrayName[1];
//								System.out.println("Clicking on medical rfi link for ind "+indName);
//								String newXpathString ="(//div[contains(@class,'MedicalGrid')]/div[contains(text(),'"+indName+"')]/following-sibling::div[5]/a[2])["+keywordModel.jcount+"]" ;
//								driver.findElement(By.xpath(newXpathString)).click();
//								ReportUtilities.Log(driver,"clicking on medical rfi link " + keywordModel.dataValue,
//										"clicked on medical rfi link " + keywordModel.dataValue, Status.PASS, keywordModel);
//							}				
//				
					

					public void clickCheckBox(WebDriver driver, KeywordModel keywordModel) {
						List<String> result = Arrays.asList(keywordModel.dataValue.replaceAll("\\s", "").split("#"));
						Boolean click = false;
						if (keywordModel.dynaElement != null) {
							try {
								if (keywordModel.dynaElement.isDisplayed()) {
									for (String a : result) {
										String temp = keywordModel.dynaElement.getAttribute("value");
										if (temp.equalsIgnoreCase(a)) {
											findElementByType(driver, keywordModel).click();
											ReportUtilities.Log(driver,"Clicking on the Element " + keywordModel.objectName,
													"With @value " + a + "Clicked on the object", Status.PASS, keywordModel);
											click = true;
											break;
										}
									}
									if (click == false) {
										ReportUtilities.Log(driver,"Data: " + (keywordModel.dataValue) + "is not available for" + keywordModel.objectName,
												"Passing the execution on to next steps.", Status.PASS, keywordModel);
									}
								}
							} catch (NoSuchElementException p) {
								keywordModel.error = true;
								keywordModel.displayError = true;
								ReportUtilities.Log(driver,"Cannot Click on the object.", "The check box " + keywordModel.objectName
										+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL, keywordModel);
								throw new RuntimeException(p);
							}
						} else {
							try {
								if (findElementByType(driver, keywordModel).isDisplayed()) {
									for (String a : result) {
										String temp = findElementByType(driver, keywordModel).getAttribute("value");
										if (temp.equalsIgnoreCase(a)) {
											findElementByType(driver, keywordModel).click();
											ReportUtilities.Log(driver,"Clicking on the Element " + keywordModel.objectName,
													"With @value " + a + "Clicked on the object", Status.PASS, keywordModel);
											click = true;
											break;
										}
									}
									if (click == false) {
										ReportUtilities.Log(driver,"Data: " + (keywordModel.dataValue) + "is not available for" + keywordModel.objectName,
												"Passing the execution on to next steps.", Status.PASS, keywordModel);
									}
								}
							} catch (NoSuchElementException p) {
								keywordModel.error = true;
								keywordModel.displayError = true;
								ReportUtilities.Log(driver,"Cannot Click on the object.", "The check box " + keywordModel.objectName
										+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL, keywordModel);
								throw new RuntimeException(p);
							}
						}
					}

public void initialiseEDMResult(WebDriver driver, KeywordModel keywordModel)
			{
			
				
				int count = driver.findElements(By.xpath("(//div[contains(@class,'33percent')])")).size();
				String[] prog = new String[count-1];
				for (int j=0,i=2;i<=count;i++,j++)
				{
					prog[j] =  driver.findElement(By.xpath("(//div[contains(@class,'33percent')])["+i+"]")).getText();
				}		//edmResult = new String[];

				for (int i=0;i<count-1;i++)
				{
					if(prog[i].equalsIgnoreCase("Medical"))
					{
						int med_count = driver.findElements(By.xpath("//div[contains(@class,'MedicalGrid')]")).size();
						keywordModel.resultSize = keywordModel.resultSize + (med_count * 4);
					}
					else if (prog[i].equalsIgnoreCase("SNAP"))	
					{
						int snap_count = driver.findElements(By.xpath("//*[@id='tblSNAPEligibilitySummary']//tbody/tr[@role='row']")).size();
						keywordModel.resultSize = keywordModel.resultSize + (snap_count * 6);	
					}
					
					else if (prog[i].equalsIgnoreCase("CASH"))	
					{
						int cash_count = driver.findElements(By.xpath("//*[@id='tblCASHEligibilitySummary_wrapper']//tbody/tr[@role='row']")).size();
						keywordModel.resultSize = keywordModel.resultSize + (cash_count * 6);	
					}
					
					else if (prog[i].equalsIgnoreCase("TA-DVS"))
					{
						int tadvs_count = driver.findElements(By.xpath("//*[@id='tblTADVSEligibilitySummary']//tbody/tr[@role='row']")).size();
						keywordModel.resultSize =keywordModel.resultSize + (tadvs_count * 5);	
					}
					
					else if (prog[i].equalsIgnoreCase("ERDC"))	
					{
						int erdc_count = driver.findElements(By.xpath("(//table[@id='tblERDCEligibilitySummary'])//tbody/tr[@role='row']")).size();
						keywordModel.resultSize = keywordModel.resultSize + (erdc_count * 7);	
					}
					
					else if (prog[i].equalsIgnoreCase("DSNAP"))	
					{
						int dsnap_count = driver.findElements(By.xpath("//*[@id='tblDSNAPEligibilitySummary']//tbody/tr[@role='row']")).size();
						keywordModel.resultSize =keywordModel.resultSize + (dsnap_count * 7);	
					}
					
					else if (prog[i].equalsIgnoreCase("Presumptive Eligiblity"))	
					{
						int hpe_count = driver.findElements(By.xpath("//table[@id='tblPEEligibilitySummary']")).size();
						keywordModel.resultSize =keywordModel.resultSize + (hpe_count * 4);	
					}

				}
				
				System.out.println(keywordModel.resultSize);
				keywordModel.edmResult = new String[keywordModel.resultSize];
				
				for(int i=0;i<keywordModel.resultSize;i++)
				{
					keywordModel.edmResult[i]="P";
						
					}

			}


public  void storeEDMResult(WebDriver driver, KeywordModel keywordModel) {
	
	
	for(int i=0;i<keywordModel.resultSize;i++)
	{
		if(i!=keywordModel.resultCounter)
		{
			keywordModel.edmResult[i]="F";
			keywordModel.resultCounter=i;
			break;
		}
	}
}


public  void retrieveEDMResult(WebDriver driver, KeywordModel keywordModel) throws InterruptedException 
{
	for(int i=0;i<keywordModel.resultSize;i++)
		{
			if(keywordModel.edmResult[i].equalsIgnoreCase("F"))
				{
					System.out.println("Failures found in EDBC result");
					driver.wait();
					break;
				}
		}
}
			


			 public void verifyAuthSummaryMedicalEDMDyna(WebDriver driver, KeywordModel keywordModel) throws InterruptedException 
	         {
	          String dataValue = keywordModel.dataValue;
	          String indivName = "";
	          
	          String[] indivEligibilityDetails = null;
	          String status = "";
	          String toa = "";
	          String beginDt = "";
	          String endDt = "";
	          int num = 100;
	         
	          String[] indivDetail = null;
	          String calBeginDate="";
	          String calEndDate="";
	         
	          
	          try
	          {
	              if (dataValue.contains(";"))
	              {
	                  indivEligibilityDetails = dataValue.split(";");
	              }
	              else
	              {
	                  indivEligibilityDetails = new String[1];
	                  indivEligibilityDetails[0] = dataValue;
	              }

	             
	              int indSize = driver.findElements(By.xpath("//div[contains(@class,'rpIndividualName')]")).size();
	              String[] indNameList = new String[indSize];

	              driver.findElement(By.xpath("//a[@id = 'viewAuthorizationHistory']")).click();
	              Thread.sleep(3000);
	              driver.findElement(By.xpath("//input[@id='btnSearch']")).click();
	              Thread.sleep(3000);
	              int count = driver.findElements(By.xpath("//div[contains(@class,'MedicalGrid')]")).size();
	              System.out.println("Total Medical EDMs on Auth History is : " + count);


	              for (int i = 1; i <= indSize; i++)
	              {
	                  indNameList[i - 1] = driver.findElement(By.xpath("(//div[contains(@class,'rpIndividualName')])[" + i + "]")).getText();

	              }

	              int[] temp = new int[count];
	              for (int l = 0; l < count; l++)
	              {
	                  temp[l] = num;
	                  num = num + 1;
	              }

	              int x = 0;
	              int j = 0;
	            
	              for (j = 1; j <= count; j++)
	              {

	                  indivName = driver.findElement(By.xpath("(//div[contains(@class,'MedicalGrid')]//div[4])[" + j + "]")).getText();


	                  for (int k = 1; k <= indSize; k++)
	                  {
	                 	 String ind_name = indNameList[k - 1];		

	                      if (ind_name.contains(indivName) /*&& indivDetail[0].Equals("Indiv"+k+"")*/)
	                      {
	                          x = j;
	                          for (int i = 0, z = 0; i < count && z < temp.length; i++, z++)
	                          {
	                              indivDetail = indivEligibilityDetails[i].split("\\|");

	                              if (indivDetail[0].equals("Indiv" + k + "") && temp[z] != i)
	                              {
	                                  temp[z] = i;

	                                  break;
	                              }
	                          }
	                          break;
	                      }
	                   

	                  }

	                  calBeginDate = calMABeginDate(indivDetail[3], driver, keywordModel);
	                  calEndDate = calMAEndDate(indivDetail[4], driver, keywordModel);

	                  toa = driver.findElement(By.xpath("(//div[contains(@class,'MedicalGrid')]//div[3])[" + x + "]")).getText();
	                 
	                  beginDt = driver.findElement(By.xpath("(//div[contains(@class,'MedicalGrid')]//div[5])[" + x + "]")).getText();
	                 
	                  endDt = driver.findElement(By.xpath("(//div[contains(@class,'MedicalGrid')]//div[6])[" + x + "]")).getText();
	                  
	                  status = driver.findElement(By.xpath("(//div[contains(@class,'MedicalGrid')]//div[8])[" + x + "]")).getText();
	                 
	                  
	                  if (status.contains(indivDetail[1]))
	                  {
	                      
	                      System.out.println(" Eligibility Status for record " + j + " are as expected");
	                      ReportUtilities.Log(driver,"Verifying Status for record "+ j,"The eligibility Status are  as expected for record  " + j + " having text "  + status,Status.PASS , keywordModel);
	                  }
	                  else
	                  {
	                      
	                      System.out.println(" Eligibility Status for record " + j + " has failed");
	                      ReportUtilities.Log(driver,"Verifying Status for record " + j, "The eligibility Status are not as expected for record  " + j + " having text " + status, Status.FAIL, keywordModel);
	                      storeEDMResult(driver, keywordModel);
	                     // driver.wait();
	                  }
	                  if (toa.contains(indivDetail[2]))
	                  {
	                     
	                      System.out.println("TOA, for the record " + j + " are as expected");
	                      ReportUtilities.Log(driver,"Verifying TOA for record " + j, "The eligibility TOA are as expected for record  " + j + " having text " + toa, Status.PASS, keywordModel);
	                  }
	                  else
	                  {
	                     
	                      System.out.println("TOA, for the record " + j + " has failed");
	                      ReportUtilities.Log(driver,"Verifying TOA for record " + j,"The eligibility TOA are not as expected for record  [" + j + "]having text " + toa, Status.FAIL, keywordModel);
	                      storeEDMResult(driver, keywordModel);
	                      //driver.wait();
	                  }
	                  if (beginDt.contains(calBeginDate))
	                  {
	                     
	                      System.out.println("Begin Date for the record " + j + " are as expected");
	                      ReportUtilities.Log(driver,"Verifying Begin Date for record " + j,"The Begin Date are as expected for record  " + j + " having text " + beginDt, Status.PASS, keywordModel);
	                  }
	                  else
	                  {
	                     
	                      System.out.println("Begin Date for the record " + j + " has failed");
	                      ReportUtilities.Log(driver,"Verifying Begin Date for record " + j, "The Begin Date are not as expected for record  " + j + " having text " + beginDt, Status.FAIL, keywordModel);
	                     storeEDMResult(driver, keywordModel);
	                      // driver.wait();
	                  }
	                  if (endDt.contains(calEndDate))
	                  {
	                      
	                      System.out.println("End Date for the record " + j + " are as expected");
	                      ReportUtilities.Log(driver,"Verifying End Date for record " + j, "The End Date are as expected for record  " + j + " having text " + endDt, Status.PASS, keywordModel);
	                  }
	                  else
	                  {
	                      
	                      System.out.println(" End Date for the record " + j + " has failed");
	                      ReportUtilities.Log(driver,"Verifying End Date for record " + j, "The End Date are not as expected for record  " + j + " having text " + endDt, Status.FAIL, keywordModel);
	                      storeEDMResult(driver, keywordModel);
	                      //driver.wait();
	                  }

	                

	              }
	              

	          }
	          catch (Exception e)
	          {
	        	  ReportUtilities.Log(driver,"Verifying the medical EDM records " + keywordModel.objectName , "The values are not displayed as expected", Status.FAIL, keywordModel);
	               driver.wait();
	          }

	      }



 /* public  String[] StoreAPIndNames(WebDriver driver, KeywordModel keywordModel)
		       
		       {
		           try
		           {
		               int indSize = findElementsByType(driver, keywordModel).size();

		               keywordModel.indNameList = new String[indSize];
					for (int i = 1; i <= indSize; i++)
					{
						keywordModel.indNameList[i - 1] = driver.findElement(By.xpath("(//table[@id='tableUsersProgram']//div[@class='caption'])[" + i + "]")).getText();
					}

				}
				catch (Exception e)
				{
					System.out.println("Storing AP individual names has failed");

				}
				return keywordModel.indNameList;
			}*/
			
/*
public  void verifyAPMedicalRFI(WebDriver driver, KeywordModel keywordModel) throws InterruptedException 
	          {
	           String dataValue = keywordModel.dataValue;
	           String indivName = "";
	           String programName = "";
	           String RFI = "";
	           String DueDate = "";
	           String[] indivEligibilityDetails = null;
	          
	           int num = 100;
	          
	           String[] indivDetail = null;
	           
	       
	           
	           try
	           {
	               if (dataValue.contains(";"))
	               {
	                   indivEligibilityDetails = dataValue.split(";");
	               }
	               else
	               {
	                   indivEligibilityDetails = new String[1];
	                   indivEligibilityDetails[0] = dataValue;
	               }


				int count = driver.findElements(By.xpath("//*[@id='0~ProofListToSubmitGrid~']//tbody/tr")).size();



				int[] temp = new int[count];
				for (int l = 0; l < count; l++)
				{
					temp[l] = num;
					num = num + 1;
				}

				int x = 0;
				int j = 0;

				for (j = 1; j <= count; j++)
				{

					indivName = driver.findElement(By.xpath("(//*[contains(@id,'ProofListToSubmit~HouseHoldMemeberName~td')])[" + j + "]")).getText();


					for (int k = 1; k <= keywordModel.indNameList.length; k++)
					{
						String ind_name = keywordModel.indNameList[k - 1];

						if (ind_name.contains(indivName) /*&& indivDetail[0].Equals("Indiv"+k+""))
						{
							x = j;
							for (int i = 0, z = 0; i < count && z < temp.length; i++, z++)
							{
								indivDetail = indivEligibilityDetails[i].split("\\|");

								if (indivDetail[0].equals("Indiv" + k + "") && temp[z] != i)  //3
								{
									temp[z] = i;

									break;
								}
							}
							break;
						}


					}


					programName = driver.findElement(By.xpath("(//*[contains(@id,'ProofListToSubmit~Program~td')])[" + x + "]")).getText();

					RFI = driver.findElement(By.xpath("(//*[contains(@id,'ProofListToSubmit~ProofType~td')])[" + x + "]")).getText();

					DueDate = driver.findElement(By.xpath("(//*[contains(@id,'ProofListToSubmit~VerificationChecklistDueDateNeeded~td')])[" + x + "]")).getText();

					//   RFIDate = verifyAPRFIDate(indivDetail[3]);

	                   if (programName.equals(indivDetail[1]))
	                   {
	                       
	                       System.out.println(" Program Name for record " + j + " are as expected");
	                       ReportUtilities.Log(driver,"Verifying Program Name for record "+ j,"The Program Names are  as expected for record  " + j + " having text "  + programName, Status.PASS, keywordModel);
	                   }
	                   else
	                   {
	                       
	                       System.out.println(" Program Name for record " + j + " has failed");
	                       ReportUtilities.Log(driver,"Verifying Program Name for record " + j, "The Program Name are not as expected for record  " + j + " having text " + programName, Status.FAIL, keywordModel);
	                       driver.wait();
	                   }
	                   if (RFI.equals(indivDetail[2]))
	                   {
	                      
	                       System.out.println("RFI, for the record " + j + " are as expected");
	                       ReportUtilities.Log(driver,"Verifying RFI for record " + j, "The RFI are as expected for record  " + j + " having text " + RFI, Status.PASS, keywordModel);
	                       
	                   }
	                   else
	                   {
	                      
	                       System.out.println("RFI, for the record " + j + " has failed");
	                       ReportUtilities.Log(driver,"Verifying RFI for record " + j,"The eligibility TOA are not as expected for record  [" + j + "]having text " + RFI, Status.FAIL, keywordModel);
	                       driver.wait();
	                   }
	                   if (DueDate.equals(indivDetail[3]))
	                   {
	                      
	                       System.out.println("Due Date for the record " + j + " are as expected");
	                       ReportUtilities.Log(driver,"Verifying Due Date for record " + j,"The Begin Date are as expected for record  " + j + " having text " + DueDate, Status.PASS, keywordModel);
	                   }
	                   else
	                   {
	                      
	                       System.out.println("Due Date for the record " + j + " has failed");
	                       ReportUtilities.Log(driver,"Verifying Due Date for record " + j, "The Begin Date are not as expected for record  " + j + " having text " + DueDate, Status.FAIL, keywordModel);
	                       driver.wait();
	                   }
	                  

	               }
	               

	           }
	           catch (Exception e)
	           {
	        	   ReportUtilities.Log(driver,"Verifying the Medical RFI records " + keywordModel.objectName , "The values are not displayed as expected", Status.FAIL, keywordModel);
	                driver.wait();
	           }

	       }*/

/*
 public  void verifyAPMedicalRFIDyna(WebDriver driver, KeywordModel keywordModel) throws InterruptedException 
               {
                String dataValue = keywordModel.dataValue;
                String indivName = "";
                String programName = "";
                String RFI = "";
                String DueDate = "";
                String[] indivEligibilityDetails = null;
                String RFIDate="";
                int num = 100;
               
                String[] indivDetail = null;
                
            
                
                try
                {
                    if (dataValue.contains(";"))
                    {
                        indivEligibilityDetails = dataValue.split(";");
                    }
                    else
                    {
                        indivEligibilityDetails = new String[1];
                        indivEligibilityDetails[0] = dataValue;
                    }

                   
                    int count = driver.findElements(By.xpath("//*[@id='0~ProofListToSubmitGrid~']//tbody/tr")).size();
                   


                    int[] temp = new int[count];
                    for (int l = 0; l < count; l++)
                    {
                        temp[l] = num;
                        num = num + 1;
                    }

                    int x = 0;
                    int j = 0;
                  
                    for (j = 1; j <= count; j++)
                    {

                        indivName = driver.findElement(By.xpath("(//*[contains(@id,'ProofListToSubmit~HouseHoldMemeberName~td')])[" + j + "]")).getText();


                        for (int k = 1; k <= keywordModel.indNameList.length; k++)
                        {
                            String ind_name = keywordModel.indNameList[k - 1];

                            if (ind_name.contains(indivName) /*&& indivDetail[0].Equals("Indiv"+k+""))
                            {
                                x = j;
                                for (int i = 0, z = 0; i < count && z < temp.length; i++, z++)
                                {
                                    indivDetail = indivEligibilityDetails[i].split("\\|");

                                    if (indivDetail[0].equals("Indiv" + k + "") && temp[z] != i)  //3
                                    {
                                        temp[z] = i;
                                     
                                        break;
                                    }
                                }
                                break;
                            }
                          

                        }
                      

                        programName = driver.findElement(By.xpath("(//*[contains(@id,'ProofListToSubmit~Program~td')])[" + x + "]")).getText();
                    
                        RFI = driver.findElement(By.xpath("(//*[contains(@id,'ProofListToSubmit~ProofType~td')])[" + x + "]")).getText();
                      
                        DueDate = driver.findElement(By.xpath("(//*[contains(@id,'ProofListToSubmit~VerificationChecklistDueDateNeeded~td')])[" + x + "]")).getText();
                      
                        RFIDate = verifyAPRFIDate(indivDetail[3], driver, keywordModel);

                        if (programName.equals(indivDetail[1]))
                        {
                            
                            System.out.println(" Program Name for record " + j + " are as expected");
                            ReportUtilities.Log(driver,"Verifying Program Name for record "+ j,"The Program Names are  as expected for record  " + j + " having text "  + programName, Status.PASS, keywordModel);
                        }
                        else
                        {
                            
                            System.out.println(" Program Name for record " + j + " has failed");
                            ReportUtilities.Log(driver,"Verifying Program Name for record " + j, "The Program Name are not as expected for record  " + j + " having text " + programName, Status.FAIL, keywordModel);
                           storeEDMResult(driver, keywordModel);
                            // driver.wait();
                        }
                        if (RFI.equals(indivDetail[2]))
                        {
                           
                            System.out.println("RFI, for the record " + j + " are as expected");
                            ReportUtilities.Log(driver,"Verifying RFI for record " + j, "The RFI are as expected for record  " + j + " having text " + RFI, Status.PASS, keywordModel);
                            
                        }
                        else
                        {
                           
                            System.out.println("RFI, for the record " + j + " has failed");
                            ReportUtilities.Log(driver,"Verifying RFI for record " + j,"The eligibility TOA are not as expected for record  [" + j + "]having text " + RFI, Status.FAIL, keywordModel);
                            storeEDMResult(driver, keywordModel);
                           // driver.wait();
                        }
                        if (DueDate.equals(RFIDate))
                        {
                           
                            System.out.println("Due Date for the record " + j + " are as expected");
                            ReportUtilities.Log(driver,"Verifying Due Date for record " + j,"The Begin Date are as expected for record  " + j + " having text " + DueDate, Status.PASS, keywordModel);
                        }
                        else
                        {
                           
                            System.out.println("Due Date for the record " + j + " has failed");
                            ReportUtilities.Log(driver,"Verifying Due Date for record " + j, "The Begin Date are not as expected for record  " + j + " having text " + DueDate, Status.FAIL, keywordModel);
                           storeEDMResult(driver, keywordModel);
                         //  driver.wait();
                        }
                       

                    }
                    

                }
                catch (Exception e)
                {
                	ReportUtilities.Log(driver,"Verifying the Medical RFI records " + keywordModel.objectName , "The values are not displayed as expected", Status.FAIL, keywordModel);
                     driver.wait();
                }

            }
*/
   public void clickAnnualIncomeEditIcon(WebDriver driver, KeywordModel keywordModel) {
					int icount = Integer.parseInt(keywordModel.dataValue);
					String indCompleteName = driver.findElement(By.xpath("(//div[contains(@class,'rpIndividualName')])["+icount+"]")).getText();
					String[] arrayName = indCompleteName.split(" ");
					String indName = arrayName[0]+" "+arrayName[1];
					System.out.println("Clicking 100% Annual income edit icon for ind "+indName);
					String newXpathString ="(//td[contains(text(),'"+indName+"')]/following-sibling::td[4]//img[@class='editRecord'])["+keywordModel.jcount+"]";
					driver.findElement(By.xpath(newXpathString)).click();
					ReportUtilities.Log(driver,"clicking on 100% annual income edit icon " + keywordModel.dataValue,
							"clicked on 100% annual income edit icon " + keywordModel.dataValue, Status.PASS, keywordModel);
				}

 public void clickMedicalNoticeReasonlink(WebDriver driver, KeywordModel keywordModel) {
					int icount = Integer.parseInt(keywordModel.dataValue);
					String indCompleteName = driver.findElement(By.xpath("(//div[contains(@class,'rpIndividualName')])["+icount+"]")).getText();
					String[] arrayName = indCompleteName.split(" ");
					String indName = arrayName[0]+" "+arrayName[1];
					System.out.println("Clicking medical notice reason link for ind "+indName);
					String newXpathString ="(//div[contains(@class,'MedicalGrid')]/div[contains(text(),'"+indName+"')]/following-sibling::div[5]/a[1])["+keywordModel.jcount+"]" ;
					driver.findElement(By.xpath(newXpathString)).click();
					ReportUtilities.Log(driver,"clicking on medical notice reason link " + keywordModel.dataValue,
							"clicked on medical notice reason link " + keywordModel.dataValue, Status.PASS, keywordModel);
				}

		public  void clickMedicalRFIlink(WebDriver driver, KeywordModel keywordModel) {
					int icount = Integer.parseInt(keywordModel.dataValue);
					String indCompleteName = driver.findElement(By.xpath("(//div[contains(@class,'rpIndividualName')])["+icount+"]")).getText();
					String[] arrayName = indCompleteName.split(" ");
					String indName = arrayName[0]+" "+arrayName[1];
					System.out.println("Clicking on medical rfi link for ind "+indName);
					String newXpathString ="(//div[contains(@class,'MedicalGrid')]/div[contains(text(),'"+indName+"')]/following-sibling::div[5]/a[2])["+keywordModel.jcount+"]" ;
					driver.findElement(By.xpath(newXpathString)).click();
					ReportUtilities.Log(driver,"clicking on medical rfi link " + keywordModel.dataValue,
							"clicked on medical rfi link " + keywordModel.dataValue, Status.PASS, keywordModel);
				}				
					

					
					/**
					 * Method Name: WaitforPagewithJs Return Type: Nothing Description: Waits for
					 * the page to load with document.ready state as parameter
					 */

		/*			public void WaitforPagewithJs(WebDriver driver, KeywordModel keywordModel) throws Exception {

						do {
							JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
							keywordModel.Pageloadstatus = jsExecutor.executeScript("return document.readyState").toString();
							// System.out.println("Pageloadstatus:: " + Pageloadstatus + " " +
							// Driver.formName);
						} while (!(keywordModel.Pageloadstatus.equalsIgnoreCase("complete")));
						Thread.sleep(10000);
						
					}*/


//****************************Ky_Keywords from here	*****************************************				
					public void WaitElementClickable(WebDriver driver, KeywordModel keywordModel)
					{

						try
						{

							WebElement element=findElementByType(driver, keywordModel);
							WebDriverWait wait = new WebDriverWait(driver, 5000);
							wait.until(ExpectedConditions.elementToBeClickable(element));
							scrollToElement(driver, keywordModel);
						}
						catch (Exception e)
						{
							throw e;
						}
					}
					
					public void WaitElementVisible(WebDriver driver, KeywordModel keywordModel)
					{
						
						

												try
												{
													WebElement element=findElementByType(driver, keywordModel);
							WebDriverWait wait1 = new WebDriverWait(driver, 5000);
				            wait1.until(ExpectedConditions.visibilityOf(element));
				            scrollToElement(driver, keywordModel);
						}
						catch (Exception e)
						{
							throw e;
						}
					}
					
					public void Navigate(WebDriver driver, String url) throws Exception
					{
						
						try
						{

							if (!(url.startsWith("http://") || url.startsWith("https://")))
								throw new Exception("URL is invalid format and cannot open page");
							driver.navigate().to(url);
							
						}
						catch (Exception e)
						{
							throw e;
						}


					}
					
					
					
					//****************************Ky_Keywords ends here	*****************************************
					
					
//***********************************MDOT_Keywords starts************************************
					
/*Method selects the radio button based on the data passed in the data sheet.
	 *creator : Prasanna Kumar 712352
*/
					
	public void clickRadioButtonByName(WebDriver driver,KeywordModel keywordModel) {
						
		try {
			String[] actualObject=keywordModel.objectID.split(":"); 
			driver.findElement(By.xpath("//*[@id='"+actualObject[1]+"']//child::*[@value='"+keywordModel.dataValue+"']")).click();
			ReportUtilities.Log(driver,"Selecting by visible text in the radio button " + keywordModel.objectName,
					"Selected the value " + keywordModel.dataValue + " in the options ", Status.PASS , keywordModel);
							
			}
		catch(NoSuchElementException p){
			keywordModel.error = true;
			keywordModel.displayError = true;
			ReportUtilities.Log(driver,"Cannot select any value on the object.", "The Element " + keywordModel.objectName
					+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
			throw new RuntimeException(p);				
			}
		}
		
	
	/*Method uses robot class to automate windows application used for uploading the attachments.
	 *creator : Prasanna Kumar 712352
	 */
	
	public void robotUploadDocuments(WebDriver driver,KeywordModel keywordModel) throws AWTException {
		Robot robot = new Robot();
		try {
		
		     StringSelection ss = new StringSelection(keywordModel.dataValue);
		     Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		     
		   //imitate mouse events like ENTER, CTRL+C, CTRL+V
		     robot.delay(250);
		     robot.keyPress(KeyEvent.VK_ENTER);
		     robot.keyRelease(KeyEvent.VK_ENTER);
		     robot.keyPress(KeyEvent.VK_CONTROL);
		     robot.keyPress(KeyEvent.VK_V);
		     robot.keyRelease(KeyEvent.VK_V);
		     robot.keyRelease(KeyEvent.VK_CONTROL);
		     robot.keyPress(KeyEvent.VK_ENTER);
		     robot.delay(90);
		     robot.keyRelease(KeyEvent.VK_ENTER);
		}
		
		catch(NoSuchElementException p) {
			keywordModel.error = true;
			keywordModel.displayError = true;
			ReportUtilities.Log(driver,"Cannot upload the documents.", "The Element " + keywordModel.objectName
					+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
			throw new RuntimeException(p);
		}
		
	}
	
	/*Method used for uploading the attachments.
	 *creator : Prasanna Kumar 712352
	 */
	
	public void uploadDocuments(WebDriver driver,KeywordModel keywordModel) throws InterruptedException  {

		try {
		
			WebElement element = findElementByType(driver,keywordModel);
			//JavascriptExecutor js = (JavascriptExecutor) driver;
			// Setting value for "style" attribute to make textbox visible
			//js.executeScript("arguments[0].style.display='block';", element);
			//element.sendKeys(keywordModel.dataValue);
			
			String absolutepath=new File(keywordModel.dataValue).getAbsolutePath();
	        element.sendKeys(absolutepath);
	        
		}
		
		catch(NoSuchElementException p) {
			keywordModel.error = true;
			keywordModel.displayError = true;
			ReportUtilities.Log(driver,"Cannot upload the documents.", "The Element " + keywordModel.objectName
					+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
			throw new RuntimeException(p);
		}
		
	}
	
	/*Method used for generating alpha numeric random number of fixed length .
	 *creator : Prasanna Kumar 712352
	 */
	
	public void enterRandomAlphaNumber(WebDriver driver,KeywordModel keywordModel) {
		
		try {
			
			findElementByType(driver, keywordModel).clear();
			
			//Length:6;StartsWith:T
			//Above is the format example to be passed in the test data sheet
			
			String[] randomDetails = keywordModel.dataValue.split(";");
			int length = Integer.parseInt(randomDetails[0].split(":")[1]);
			String startsWith = randomDetails[1].split(":")[1];
		
			String Chars = "0123456789";
			StringBuilder builder = new StringBuilder();
			while (length-- != 0) {
				int character = (int) (Math.random() * Chars.length());
				builder.append(Chars.charAt(character));	
			}
			
			if(startsWith=="") {
				keywordModel.dataValue = builder.toString();
			}
			else {
			keywordModel.dataValue = startsWith + builder.toString();
			}
			
			findElementByType(driver,keywordModel).sendKeys(keywordModel.dataValue);
			
		}
		catch(NoSuchElementException p) {
			
			keywordModel.error = true;
			keywordModel.displayError = true;
			ReportUtilities.Log(driver,"Cannot generate random alpha numeric number.", "The Element " + keywordModel.objectName
					+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
			throw new RuntimeException(p);
		}
		
	}
	
	/*Method used for getting current system date .
	 *creator : Prasanna Kumar 712352
	 */
	
	public void enterPresentDate(WebDriver driver, KeywordModel keywordModel) throws Exception{
		
		try {
			if(findElementByType(driver,keywordModel).isDisplayed()) {
				
				findElementByType(driver,keywordModel).clear();
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
				LocalDate now = LocalDate.now(); 
				System.out.println(dtf.format(now));   
				findElementByType(driver,keywordModel).sendKeys(dtf.format(now));
			}			
		}
		catch(NoSuchElementException p) {
			keywordModel.error = true;
			keywordModel.displayError = true;
			ReportUtilities.Log(driver,"Cannot generate current system date.", "The Element " + keywordModel.objectName
					+ " is NOT displayed on the current screen" + keywordModel.ScreenName, Status.FAIL , keywordModel);
			throw new RuntimeException(p);
		}
	}
	
	/*Method used for Storing Session Amount Without $ Symbol .
     *creator : Manoj Kumar 670560
     */

    public void storeSessionAmount(WebDriver driver, KeywordModel keywordModel) {
        try {
            if (findElementByType(driver, keywordModel).isDisplayed()) {
                WebElement webElement = findElementByType(driver, keywordModel);
                String elementValue = webElement.getText();
                // elementValue = elementValue.replace("/", "");
            //    elementValue = elementValue.replace("Case: ", "").trim();
            //    elementValue = elementValue.replace("Application: ", "").trim();
                if (elementValue.contains("$")) {
                    elementValue=elementValue.replace("$","");// For Storing SSN from UI
                }
            sessionManager.add(keywordModel.dataValue, elementValue, keywordModel);
                System.out.println("Session has : " + elementValue + "in Key: " + keywordModel.dataValue);
                ReportUtilities.Log(driver,"Storing the data in session: ", elementValue, Status.PASS , keywordModel);
            }
        } catch (NoSuchElementException p) {
            keywordModel.error = true;
            keywordModel.displayError = true;
            ReportUtilities.Log(driver,"Cannot store data in session from the object.",
                    "The Element " + keywordModel.objectName + " is NOT displayed on the current screen" + keywordModel.ScreenName,
                    Status.FAIL , keywordModel);
            throw new RuntimeException(p);
        }
    }
    
    /*Method wait for Element ( long Wait )
     *creator : Manoj kumar 670560
     *
     */
    public  void longWaitForElementPresent(WebDriver driver, KeywordModel keywordModel) throws InterruptedException
    {
        int counter = 0;
        try
        {
            while (true)
            {
                counter++;
               try
                {
                    Thread.sleep(1000);
                    if(findElementByType(driver, keywordModel).isDisplayed())
                    {     
                        if(findElementByType(driver, keywordModel).isEnabled())
                        {
                            // Thread.sleep(10000);
                            System.out.println("Object found - Small Wait: "+keywordModel.objectName+" at "+counter);
                            ReportUtilities.Log(driver,"long Wait for Element , Element found  :",
                                    "Locating the element + " + keywordModel.objectName, Status.PASS , keywordModel);
                            break;
                        }
                    }
                }
               catch (Exception e)
                {
                    System.out.println("Object not found - long wait: "+keywordModel.objectName + "Retrying...");
               }
               if (counter >= 180)
                {
                    System.out.println("Object not found  - long wait: "+keywordModel.objectName);
                   break;
                }
            }
       }
        catch(Exception e)
        {
            System.out.println("Object not found by long wait: "+keywordModel.objectName);
            ReportUtilities.Log(driver,"long Wait for Element , Element not found  :",
                    "Locating the element + " + keywordModel.objectName, Status.FAIL , keywordModel);
        }
   }
    
//***********************************MDOT_Keywords stops************************************

}

