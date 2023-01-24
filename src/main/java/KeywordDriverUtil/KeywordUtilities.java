package KeywordDriverUtil;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Common.POI_ReadExcel;
import Constants.PrjConstants;
import KeywordDriverUtil.Reports.ReportUtilities;
import KeywordDriverUtil.Reports.ReportData.Status;
import TestSettings.KeywordTestSettings;


public class KeywordUtilities {
	private static final Logger logger = Logger.getLogger(KeywordUtilities.class.getName());
		
	/**
	 * Method Name: getData
	 * Description: This method is to fetch Data from the column in the Input sheet against the test case that is currently executing based on the current iteration
	 * Return Type: String
	 * @throws Exception 
	 */
	public static String getData(WebDriver driver, String ExcelFileName, String testCase,String colName,  int currentIteration) throws Exception{
		String dataValue = "";
		try {		
			
			List<String> whereClause = new ArrayList<String>();
			whereClause.add("TestScript::"+testCase);
			whereClause.add("Iteration::"+currentIteration);
			Map<String, List<String>> result = CustomFramework.fetchWithCondition(ExcelFileName, "TestData", whereClause);
						
			if(result.get("TestScript").size()==0){
//				ReportUtilities.Log(driver,"Blank column in Test Data", "There is no data in the  "+colName+" for the Iteration "+ currentIteration+" of test case "+testCase, Status.FAIL);
			}
			
			for(int i=0; i<result.get("TestScript").size(); i++){
				if(testCase.equalsIgnoreCase(result.get("TestScript").get(i)) && currentIteration==Integer.parseInt(result.get("Iteration").get(i))){
					if(result.get(colName).get(i).equals("NULL")) {
						dataValue = "";
						break;
					}
					dataValue = result.get(colName).get(i);
					break;
				}
			}
			
			return dataValue;
		} catch (Exception e) {
			throw e;
		}
		
	}
	/**
	 * Method Name: getDataFromSheet
	 * Description: This method is to create path for test data sheet
	 * Return Type: String
	 * @throws Exception 
	 */
	public static String dataSheetPath(String rootPath)
	{
		StringBuilder filePathName = new StringBuilder();
		filePathName.append(rootPath);
		filePathName.append(File.separatorChar);
		filePathName.append(PrjConstants.TEST_SUITE);
		filePathName.append(File.separatorChar);
		filePathName.append(PrjConstants.TEST_DATA);
		filePathName.append(".xlsx");
		return filePathName.toString();
		 
	}
	
	/**
	 * Method Name: getDataFromSheet
	 * Description: This method is to fetch Data from the column in the Input sheet against the test case that is currently executing based on the current iteration
	 * Return Type: String
	 * @throws Exception 
	 */
	public static String getDataFromSheet(WebDriver driver,String rootPath,String testCase,String colName, int currentIteration) throws Exception{
		
		//System.out.println("TC Sheet path: " +filePathName);
		
		String dataValue = "";
		String dataSheet= dataSheetPath(rootPath);
		try {			
			ArrayList<String> whereClause = new ArrayList<String>();
			POI_ReadExcel pOI_ReadExcel= new POI_ReadExcel();
			whereClause.add("TestScript::"+testCase);
			whereClause.add("Iteration::"+currentIteration);
			HashMap<String, ArrayList<String>> result = pOI_ReadExcel.fetchWithCondition(dataSheet, "TestData", whereClause);
			
			if(result.get("TestScript").size()==0){
//				ReportUtilities.Log(driver,"Blank column in Test Data", "There is no data in the column "+colName+" for the Iteration "+currentIteration+" of test case "+testCase, Status.FAIL);
			}
		
			for(int i=0; i<result.get("TestScript").size(); i++){
				if(testCase.equalsIgnoreCase(result.get("TestScript").get(i)) && currentIteration==Integer.parseInt(result.get("Iteration").get(i))){
					if(result.get(colName).get(i).equals("NULL")) {
						dataValue = "";
						break;
					}
					dataValue = result.get(colName).get(i);
					break;
				}
			}
			
			return dataValue;
		} 
		catch (Exception e)
		{
			throw e;
		}
		
	}
	
	
	/**
	 * Method Name: getConnection
	 * Description: This method is to create a connection between java and Excel sheets using the JdbcOdbc driver
	 * Return Type: Nothing
	 */
	public static Connection getConnection(String filePath) throws Exception{
				
		Class.forName("com.googlecode.sqlsheet.Driver");
		Connection con = DriverManager.getConnection("jdbc:xls:file:"+filePath);
		return con;
	}
	
	/**
	 * Method Name: putData
	 * Description: This method is to write Data to the column in the Input sheet against the test case that is currently executing based on the current iteration
	 * Return Type: Nothing
	 */
	public static void putData(Connection con,String dbPath,String  testCase, String colName, String colValue, int currentIteration){
		
		try {			
			getConnection(dbPath);
			Statement s = con.createStatement();
			//String query = "Update [TestData$] set "+colName+"='"+colValue+"' where TestScript='"+testCase+"' and Iteration="+Driver.currentIteration;
			String query = "Update TestData set "+colName+"='"+colValue+"' where TestScript='"+testCase+"' and Iteration="+currentIteration;
			s.executeUpdate(query);
			con.close();
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}
	
	/*
	 * Reads the timezone property from config and creates the timezone
	 */
	public static TimeZone getTimeZone(){
		String timeZone =getValueFromConfigProperties("timezone");
		return TimeZone.getTimeZone(timeZone);		
	}
	
	/**
	 * Method Name: getCurrentDate
	 * Description: This method is get the Current Date in the format 'MM-dd-yyyy' for the specified timezone
	 * Return Type: String
	 */
	public static String getCurrentDate(){
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		sdf.setTimeZone(getTimeZone());
		String date = sdf.format(cal.getTime());
		return date;
		
	}
	
	/**
	 * Method Name: getCurrentTime
	 * Description: This method is get the Current Time in the format 'hh-mm-ssa' for the specified timezone
	 * Return Type: String
	 */
	public static String getCurrentTime(){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("hh-mm-ssa");
		sdf.setTimeZone(getTimeZone());
		String time = sdf.format(cal.getTime());
		return time;
		
	}
	
	
	
	/**
	 * Method Name: CaptureScreenshot
	 * Description: This method is capture the screenshot of the current page
	 * Return Type: String
	 */
	public static void CaptureScreenshot(WebDriver driver,String screenshotPath, Status status){
		
		try {			
			if(status.equals(Status.FAIL)){
				handleErrorPage(driver);
			}
			
			if(getValueFromConfigProperties("SnapshotType").equalsIgnoreCase("Desktop")){
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				Dimension screenSize = toolkit.getScreenSize();
				Robot robot = new Robot();
				Rectangle rectangle = new Rectangle(0, 0, screenSize.width-15, screenSize.height);
				BufferedImage image = robot.createScreenCapture(rectangle);
				ImageIO.write(image, "png", new File(screenshotPath));
			}else if(getValueFromConfigProperties("SnapshotType").equalsIgnoreCase("Browser")){
				File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(screenshot, new File(screenshotPath));
			}
		} catch (Exception e) {			
			logger.log(Level.WARNING, "Error while capturing screen shot", e);
		}
	}
	
	
	/**
	 * Method Name: connectDatabse
	 * Description: This method is to connect to a Oracle Database
	 * Return Type: String
	 */
	public static Connection connectDatabse(){
		String url = "jdbc:microsoft:sqlserver://";
		String serverName= "localhost";
		String portNumber = "";
		String databaseName= "";
		String userName = "";
		String password = "";
		
		String connectionString = url+serverName+":"+portNumber+";databaseName="+databaseName+";";
		
		Connection con = null;
		try
		{
			Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
			con = java.sql.DriverManager.getConnection(connectionString,userName,password); 
			if(con!=null) logger.info("Connection Successful!");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("Error Trace in getConnection() : " + e.getMessage());
			
		}
		
		return con;
	}
	
	/**
	 * Method Name: getValueFromConfigProperties
	 * Description: This method is to retrieve value from Config.properties file for the key given
	 * Return Type: String
	 */
	public static String getValueFromConfigProperties(String key){
		String value = "";
		Properties prop = new Properties();
		try {
			FileInputStream	propFile = new FileInputStream (new File(".").getCanonicalPath()+PrjConstants.appendPath + PrjConstants.KeywordConfigPath);
			prop.load(propFile);
			value = prop.getProperty(key);
		} catch (FileNotFoundException e1) {			
			e1.printStackTrace();
		} catch (IOException e1) {			
			e1.printStackTrace();
		}
		return value;		
	}
	
	public static String getValueFromErrorConfigProperties(String key){
		String value = "";
		Properties prop = new Properties();
		try {
			FileInputStream	propFile = new FileInputStream (new File(".").getCanonicalPath()+PrjConstants.appendPath + PrjConstants.KeywordErrorsConfigPath);
			prop.load(propFile);
			value = prop.getProperty(key);
		} catch (FileNotFoundException e1) {			
			e1.printStackTrace();
		} catch (IOException e1) {			
			e1.printStackTrace();
		}
		return value;		
	}
	
	public static String getValueFromAPErrorConfigProperties(String key){
		String value = "";
		Properties prop = new Properties();
		try {
			FileInputStream propFile = new FileInputStream (new File(".").getCanonicalPath()+PrjConstants.appendPath + PrjConstants.KeywordAPErrorsConfigPath);
			prop.load(propFile);
			value = prop.getProperty(key);
		} catch (FileNotFoundException e1) {			
			e1.printStackTrace();
		} catch (IOException e1) {			
			e1.printStackTrace();
		}
		return value;		
	}
	
	public static void updateIterationConfigProperties(String key, String value)
	{
//		prop = new Properties();
		try {
//			propFile = new FileInputStream (new File(".").getCanonicalPath()+"\\Config\\Config.properties");
//			prop.load(propFile);
//			propFile.close();
			
		FileInputStream in = new FileInputStream(new File(".").getCanonicalPath()+PrjConstants.appendPath + PrjConstants.KeywordIterationConfigPath);
		Properties props = new Properties();
		props.load(in);
		in.close();

		FileOutputStream out = new FileOutputStream(new File(".").getCanonicalPath()+PrjConstants.appendPath + PrjConstants.KeywordIterationConfigPath);
		props.setProperty(key,value);
		props.store(out, null);
		out.close();
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
	
	}
	
	/**
	 * Method Name: getValueFromDBConfigProperties
	 * Description: This method is to retrieve value from DBConfig.properties file for the key given for Database validations
	 * Return Type: String
	 */
	public static String getValueFromDBConfigProperties(String key){
		String value = "";
		Properties prop = new Properties();
		try {
			FileInputStream propFile = new FileInputStream (new File(".").getCanonicalPath()+PrjConstants.appendPath + PrjConstants.KeywordDBQueriesConfigPath);
			prop.load(propFile);
			value = prop.getProperty(key);
		} catch (FileNotFoundException e1) {			
			e1.printStackTrace();
		} catch (IOException e1) {			
			e1.printStackTrace();
		}
		return value;		
	}

	public static String getValueFromRepository(Properties prop,String key){
		String value = "";
		try {
			value = prop.getProperty(key);
		} catch (Exception e1) {			
			e1.printStackTrace();
		}
		return value;		
	}

	/**
	 * Method Name: getValueFromDBRefConfigProperties
	 * Description: This method is to retrieve value from DBRefConfig.properties file for the key given for Database validations
	 * Return Type: String
	 */
	public static String getValueFromDBRefConfigProperties(String key){
		String value = "";
		Properties prop = new Properties();
		try {
			FileInputStream propFile = new FileInputStream (new File(".").getCanonicalPath()+PrjConstants.appendPath + PrjConstants.KeywordDBRefConfigPath);
			prop.load(propFile);
			value = prop.getProperty(key);
		} catch (FileNotFoundException e1) {			
			e1.printStackTrace();
		} catch (IOException e1) {			
			e1.printStackTrace();
		}
		return value;		
	}
	
	/**
	 * Method Name: getValueFromIterationConfigProperties
	 * Description: This method is to retrieve value from DBRefConfig.properties file for the key given for Database validations
	 * Return Type: String
	 */
	public static String getValueFromIterationConfigProperties(String key){
		String value = "";
		Properties prop = new Properties();
		try {
			FileInputStream	propFile = new FileInputStream (new File(".").getCanonicalPath()+PrjConstants.appendPath + PrjConstants.KeywordIterationConfigPath);
			prop.load(propFile);
			value = prop.getProperty(key);
		} catch (FileNotFoundException e1) {			
			e1.printStackTrace();
		} catch (IOException e1) {			
			e1.printStackTrace();
		}
		return value;		
	}
	/**
	 * Method Name: getObjectFromObjectMap
	 * Description: This method is to retrieve object from the particular module ObjectMap.properties file for the key given
	 * Return Type: String
	 */
	public static String getObjectFromObjectMap(WebDriver driver,String key , Properties loadOR, Properties loadDeviceOR){
		String value = "";
		try {
			if((key!=null) && (KeywordUtilities.getValueFromConfigProperties("Browser").contains("Android")))
			{
				value = loadDeviceOR.getProperty(key);
				
			}
				
		    if(value == null || value.isEmpty()) 
			{
				value = loadOR.getProperty(key);
			}
				/*else 
				{
					value = Driver.loadOR.getProperty(key);
				}*/
		
			/*else if (value == null || value.isEmpty()) {
			value = Driver.loadOR.getProperty(key);
			}*/
//				else
//					{
//						value = null;
//					}
		}  catch (Exception e1) {			
//			ReportUtilities.Log(driver, "Object Reference Name Wrong","The Object Reference Name given is not present/matching with Object Map", Status.FAIL,keywordModel);
		}
		return value;
	}
	
	/**
	 * Method Name: loadObjects
	 * Description: This method is to retrieve object from the particular module ObjectMap.properties file for the key given
	 * Return Type: Properties
	 */
	public static Properties loadObjects(WebDriver driver,String rootPath, String module,String scenarioOR ){
		
		Properties prop = new Properties();
		try {
			String objectRepoFilePathName = getObjectFilePathName(rootPath,module,scenarioOR);
			FileInputStream propFile = new FileInputStream (objectRepoFilePathName);
			prop.load(propFile);
			
		} catch (FileNotFoundException e1) {			
			e1.printStackTrace();
		} catch (IOException e1) {			
//			ReportUtilities.Log(driver,"Object Reference Name Wrong","The Object Reference Name given is not present/matching with Object Map", Status.FAIL,keywordModel);
		}
		return prop;
	}
	
	public static Properties deviceLoadObjects(WebDriver driver,String rootPath, String module,String scenarioOR ){
		
		Properties prop = new Properties();
		try {
			String deviceObjectRepoFilePathName = getDeviceObjectFilePathName(rootPath,module,scenarioOR);
			System.out.println("OR Loaded from : " + deviceObjectRepoFilePathName);
			FileInputStream propFile = new FileInputStream (deviceObjectRepoFilePathName);
			prop.load(propFile);
			
		} catch (FileNotFoundException e1) {			
			e1.printStackTrace();
		} catch (IOException e1) {			
			//ReportUtilities.Log("Object Reference Name Wrong","The Object Reference Name given is not present/matching with Object Map", Status.FAIL);
		}
		return prop;
	} 
	
	
	/**
	 * Method Name: getObjectFromObjectMap
	 * Description: This method is to retrieve object from the particular module ObjectMap.properties file for the key given
	 * Return Type: String
	 */
	public static String getDynamicObjectFromObjectMap(WebDriver driver, String rootPath, String module, String  scenario,String key,String DynmicStringAppender){
		String value = "";
		Properties prop = new Properties();
		try {
			scenario=scenario+DynmicStringAppender;
			String objectRepoFilePathName = getObjectFilePathName(rootPath,module,scenario);
			FileInputStream propFile = new FileInputStream (objectRepoFilePathName);
			prop.load(propFile);
			if(key!=null)
				value = prop.getProperty(key);
			else
				value = null;
		} catch (FileNotFoundException e1) {			
			e1.printStackTrace();
		} catch (IOException e1) {			
//			ReportUtilities.Log(driver,"Object Reference Name Wrong","The Object Reference Name given is not present/matching with Object Map", Status.FAIL);
		}
		return value;
	}
	
	public static String getObjectFromExcelObjectMap(String dbPath, String key){
		String objectID = "";
		try {
			POI_ReadExcel poi_ReadExcel= new POI_ReadExcel();
			ArrayList<String> whereClause = new ArrayList<String>();
			whereClause.add("ObjectReferenceName::"+key);
			HashMap<String, ArrayList<String>> result = poi_ReadExcel.fetchWithCondition(dbPath, "ObjectMap", whereClause);
			
			for(int i=0; i<result.get("ObjectReferenceName").size(); i++){
				if(key.equalsIgnoreCase(result.get("ObjectReferenceName").get(i))){
					objectID = result.get("ObjectID").get(i);
					break;
				}
			}
			
			return objectID;
		} catch (Exception e) {
			logger.log(Level.WARNING, "", e);
		}
		return objectID;
	}
	
	
	public static String getObjectMappingSheet(String key){
		String objectID = "";
		try {
			
			String homePath = new File (".").getCanonicalPath();
			String mappingSheetPath = homePath+ PrjConstants.appendPath +"\\ObjectRepository\\SalesForceObjectMapping.xlsx";
			try {
				
				POI_ReadExcel poi_ReadExcel= new POI_ReadExcel();
				
				ArrayList<String> whereClause = new ArrayList<String>();
				HashMap<String, ArrayList<String>> result = poi_ReadExcel.fetchWithCondition(mappingSheetPath, "ObjectMap", whereClause);
				
				for(int i=0; i<result.get("Module").size(); i++){
					if(key.equalsIgnoreCase(result.get("Module").get(i)+";"+result.get("APIName").get(i))){
						objectID = result.get("SalesForceID").get(i);
						break;
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} catch (IOException e) {			
			e.printStackTrace();
		}
		return objectID;
	}
	
	public static String getActualTestCaseName(String testCaseName){
		return testCaseName.split(PrjConstants.TEST_CASE_ALIAS)[0]; 
	}
	
	/*
	 * Verify if the data has to be retrieved from session or not and
	 * return the data if it is in session else the value if it is not in session
	 */
//	public static String verifyAndAssignDataValue(String elementData){
//		if(elementData.startsWith("session:")){
//			String key =  elementData.split(":")[1];
//			Object obj = DriverSession.getInstance().get(key);
//			return (obj == null) ? "" : obj.toString();
//		}else{
//			return elementData;
//		}
//	}
//	
	public static String getRootPath() {
		String rootPath = "";
		try {
			rootPath =  new File (".").getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rootPath;
	}
	
	
	public static String getScenarioNamePath(String scenarioType, String scenarioName){
		StringBuilder namePath = new StringBuilder();
		namePath.append(KeywordTestSettings.branch);
		namePath.append(File.separatorChar);
		namePath.append(PrjConstants.SCENARIOS);
		namePath.append(File.separatorChar);
		namePath.append(scenarioType);
		namePath.append(File.separatorChar);		
		namePath.append(scenarioName);		
		//System.out.println("Scenario Path: "+namePath);
		return namePath.toString();
	}	
		
	
	public static String getScenarioFileNamePath(String rootPath, String scenarioName){
		StringBuilder namePath = new StringBuilder();
		namePath.append(rootPath);
		namePath.append(File.separatorChar);
		namePath.append(PrjConstants.TEST_SUITE);
		namePath.append(File.separatorChar);
//		namePath.append(Driver.Environment);//Added fr parallel exec
//		namePath.append(File.separatorChar);//parallel exec
		namePath.append(scenarioName);
		namePath.append(".xlsx");
		
		return namePath.toString();
	}
	
	public static String getScriptFilePathName(String rootPath, String modulePath, String testScriptName){
		StringBuilder filePathName = new StringBuilder();
		filePathName.append(rootPath);
		filePathName.append(File.separatorChar);
		filePathName.append(PrjConstants.TEST_SUITE);
		filePathName.append(File.separatorChar);
		filePathName.append(KeywordTestSettings.branch);
		filePathName.append(File.separatorChar);
		filePathName.append(getScriptName(modulePath,testScriptName));
		//System.out.println("TC Sheet path: " +filePathName);
		return filePathName.toString();
	
	}
	
	public static String getObjectFilePathName(String rootPath, String modulePath, String repoName){
		StringBuilder objectPathName = new StringBuilder();
		objectPathName.append(rootPath);
		objectPathName.append(File.separatorChar);
		objectPathName.append(PrjConstants.TEST_SUITE);
		objectPathName.append(File.separatorChar);	
		objectPathName.append(KeywordTestSettings.branch);
		objectPathName.append(File.separatorChar);
		objectPathName.append("ObjectRepository");
		objectPathName.append(File.separatorChar);
		objectPathName.append(modulePath);
		objectPathName.append(File.separatorChar);
		objectPathName.append(repoName);		
		objectPathName.append(".properties");
		//System.out.println("ObjectRepo Path: "+objectPathName);
		return objectPathName.toString();
	}
	
	public static String getDeviceObjectFilePathName(String rootPath, String modulePath, String repoName){
		//swmhatre changes end
		StringBuilder objectPathName = new StringBuilder();
		objectPathName.append(rootPath);
		objectPathName.append(File.separatorChar);
		objectPathName.append(PrjConstants.TEST_SUITE);
		objectPathName.append(File.separatorChar);	
		objectPathName.append(KeywordTestSettings.branch);
		objectPathName.append(File.separatorChar);
		objectPathName.append("ObjectRepository");
		objectPathName.append(File.separatorChar);
		objectPathName.append(modulePath);
		objectPathName.append(File.separatorChar);
		objectPathName.append(repoName);
		if (KeywordUtilities.getValueFromConfigProperties("Browser").contains("Android"))
		{
			objectPathName.append("_");
			objectPathName.append("Android");
		}
		else if	(KeywordUtilities.getValueFromConfigProperties("Browser").contains("iOS"))
			{
				objectPathName.append("_");
				objectPathName.append("iOS");
			}
		objectPathName.append(".properties");
		//System.out.println("ObjectRepo Path: "+objectPathName);
		return objectPathName.toString();
	}
	
	public static String getTestScriptName(String sheetPath,String dbPath ){
		logger.fine("sheetPath-->"+sheetPath);
		String pattern = Pattern.quote(String.valueOf(File.separatorChar));
		String[] pathnames = sheetPath.split(pattern);
		pattern = Pattern.quote(".");
		String name[] = pathnames[pathnames.length - 1].split(pattern);
		if(name[0].equalsIgnoreCase(PrjConstants.TEST_DATA))
		{
			name = null;
			pathnames = null;
			pattern = null;
			sheetPath=dbPath;
			pattern = Pattern.quote(String.valueOf(File.separatorChar));
			pathnames = sheetPath.split(pattern);
			pattern = Pattern.quote(".");
			name = pathnames[pathnames.length - 1].split(pattern);
		}
		return name[0];
	}
	
	public static boolean isDevMode(){
		String devMode = getValueFromConfigProperties("dev.mode");
		if(devMode != null && devMode.equalsIgnoreCase("Yes")){
			return true;
		}
		return false;
	}
	
	public static void runInDevMode(String rootPath, int stepNumber){
		boolean continueRun = true;
		while(continueRun){
			Properties devModeProps  = new Properties();
			continueRun = false;
			try {
				FileInputStream fin = new FileInputStream(rootPath+File.separator+"devmode.properties");
				devModeProps.load(fin);
				String strWaitAfterStep = devModeProps.getProperty("wait.after.step");
				if(strWaitAfterStep != null){
					int afterStep = Integer.parseInt(strWaitAfterStep);
					if(stepNumber != (afterStep+1)){
						continue;
					}
				}
				String waitForPage = devModeProps.getProperty("wait.for.page");
				if(waitForPage != null && waitForPage.equalsIgnoreCase("Yes")){
					continueRun = true;
					String waitTime = devModeProps.getProperty("wait.time");
					if(waitTime != null){
						try {
							Thread.sleep(Long.parseLong(waitTime));
						} catch (NumberFormatException e) {							
							logger.log(Level.WARNING, "", e);
						} catch (InterruptedException e) {							
							logger.log(Level.WARNING, "", e);
						}
					}
				}
				
			} catch (IOException e) {			
				logger.log(Level.WARNING, "", e);				
			}
			
		}
	}
	

	
	private static String getScriptName(String modulePath,String testScriptName){
		StringBuilder scriptName = new StringBuilder();		
		if(testScriptName.contains("-UT-")){
			scriptName.append("UnitTestCases");
		}else{
			scriptName.append("TestCases");
		}		
		scriptName.append(File.separatorChar);
		scriptName.append(modulePath);
		scriptName.append(File.separatorChar);
		scriptName.append(testScriptName);
		scriptName.append(".xlsx");
		
		return scriptName.toString();
	}
	
	public static void handleErrorPage(WebDriver driver){
		if(isErrorPage(driver)){
			try{
				WebElement detailsLink = driver.findElement(By.linkText("Details"));
				if(detailsLink != null){
					detailsLink.click();
				}
			}catch(Exception e){
				logger.fine("Can't find element 'Details' on the eror page");
			}
		}
	}
	
	private static boolean isErrorPage(WebDriver driver){
		WebElement headerElement= null;
		try{
			headerElement = driver.findElement(By.xpath("/html/body/div[2]"));
		}catch(Exception e){
			logger.info("Can't find header element to check for error page for web element-->//*[@id='errorpage']/div/h1");
		}
		if(headerElement != null && headerElement.getText().startsWith("An error has occurred")){
			String header = headerElement.getText();
//			ReportUtilities.Log(driver,"Application Error. ", "Error Code: "+driver.findElement(By.xpath("/html/body/div[2]")).getText(), Status.DONE);
			//driver.quit();
			return header.equalsIgnoreCase("Application Error Page");
			
		}
		return false;
	}
	
	
	
	public static String getScenarioInformationPath(){
		StringBuilder namePath = new StringBuilder();
		try {
			namePath.append(getRootPath()+ PrjConstants.appendPath);
			namePath.append(File.separatorChar);
			namePath.append(PrjConstants.TEST_SUITE);
			namePath.append(File.separatorChar);	
			namePath.append(PrjConstants.SCENARIOS);
			namePath.append(File.separatorChar);
			namePath.append("REGRESSIONSCENARIOS_INDEX");
			namePath.append(".xlsx");
			
			return namePath.toString();
		} catch (Exception e) {
			return null;
	
		}
	}


	public static Map<String, HashMap> getScenarioInformation(String sheetPath) {

		FileInputStream file = null;
		XSSFWorkbook workbook = null;
		HashMap<String, HashMap> indexMap = new HashMap();
		HashMap<String, String> infoMap = new HashMap();
		HashMap<String, String> toaMap = new HashMap();
		try {
			file = new FileInputStream(new File(sheetPath));
			workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);

			Iterator<Row> iterator = sheet.iterator();
			while (iterator.hasNext()) {
				Row row = iterator.next();
				String ScnName = (row.getCell(1).getStringCellValue());
				String info = StringEscapeUtils.escapeHtml4(row.getCell(2).getStringCellValue());
				String toa = StringEscapeUtils.escapeHtml4(row.getCell(3).getStringCellValue());
				info = info.replace("\n", "<br>");
				infoMap.put(ScnName, info);
				toaMap.put(ScnName, toa);
			}

		} catch (Exception e) {
		}
		indexMap.put("info", infoMap);
		indexMap.put("toa", toaMap);
		return indexMap;

	}
	
}
