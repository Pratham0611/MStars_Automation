package KeywordDriverUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.deloitte.exception.AUTException;
import com.deloitte.framework.objects.ReportSerialization;

import Common.POI_ReadExcel;
import Common.ReportCommon;
import Constants.ReportContants;
import KeywordDriverUtil.Reports.ReportData;
import KeywordDriverUtil.Reports.ReportUtilities;
import KeywordDriverUtil.Reports.ReportData.Status;
import Model.TestCaseParam;
import Model.ExtentModel.ExtentUtilities;
import Model.ExtentModel.TestCaseDetails;
import Model.ExtentModel.TestRunDetails;
import Model.KeywordDriven.KeywordModel;
import Model.KeywordDriven.KeywordTestScenarioModel;
import TestSettings.KeywordTestSettings;

public class ScenarioUtil {

	Utilities.Util util = new Utilities.Util();
	POI_ReadExcel poi_ReadExcel= new POI_ReadExcel();
	private static final Logger logger = Logger.getLogger(KeywordDriver.class.getName());

	public String getBrowser(String Browser)
	{
	
		try
		{
		
			if(KeywordTestSettings.UseBrowserfromScenarioSheet==false)
			{
				return KeywordTestSettings.Browser;
			}
			else
			{
				if(Browser.equalsIgnoreCase(""))
				{
					return "chrome";
				}
	
				else
				{
					return Browser;
				}
			}
			
		
		}
		catch (Exception e)
		{
			return "chrome";
		}
	}
	
	public void executeScenario(KeywordTestScenarioModel keywordTestScenarioModel,  String rootPath ,String regSumFolder){
		try{
			
			String scenarioName =keywordTestScenarioModel.TestScenarioPath;
			String scenarioType=keywordTestScenarioModel.TestScenarioType;
			String scenarioNameId=keywordTestScenarioModel.TestScenarioName;
			 String Browser=keywordTestScenarioModel.Browser;

			 String path = KeywordUtilities.getScenarioFileNamePath(KeywordTestSettings.homePath, scenarioName);
			String browser = getBrowser(Browser);
//			RegressionScenario[] regScenarios = getAllScenarios(homePath,"Yes", "RegressionScenarios");
			
			//Starting the WebDriver session based on the Browser given in Properties.INI and launching the URL
			ExtentUtilities extentUtilities= new ExtentUtilities();
            UUID tcId =null;
              if (extentUtilities.getTestCaseID(scenarioNameId, "", browser) == null)
             {
                  tcId = extentUtilities.InitializeNewTestCase(scenarioNameId, scenarioNameId, "", browser);
                 
             }

              
      //        ReportData.resultPath=regSumFolder;
              WebDriverHelper wdhHelper= new WebDriverHelper();
            
			WebDriver driver =   wdhHelper.SelectDriver(browser);
			//Launching the URL and maximizing the window
			Properties LoadOR = KeywordUtilities.loadObjects(driver ,rootPath ,
					keywordTestScenarioModel.TestScenarioType,
					keywordTestScenarioModel.TestScenarioType);
			Properties loadDeviceOR = KeywordUtilities.deviceLoadObjects(driver ,rootPath ,
					keywordTestScenarioModel.TestScenarioType,
					keywordTestScenarioModel.TestScenarioType);
			DriverUtil driverUtil= new DriverUtil();
			driverUtil.InvokeDriver(driver, scenarioType, KeywordTestSettings.ENV, "No");
			
			
			ArrayList<String> whereClause = new ArrayList<String>();
			whereClause.add("Execute::Yes");
			HashMap<String, ArrayList<String>> result = poi_ReadExcel.fetchWithCondition(path, "TestCases", whereClause);
			
			int totalExecutedCount=result.get("TestScript").size();			
			//Method to count the number of Test Cases that are executed
			logger.fine("Total number of testcases to execute for scenario:"+scenarioName+" is-->"+totalExecutedCount);
			//Main Execution code
			//Verifying whether the number of Test Cases marked TRUE for execution>0
			
			KeywordLibrary keywordLibrary= new KeywordLibrary();	
			
			if(totalExecutedCount!=0){
				try{
					
					//Creating the Execution folder only if at-least 1 Test Case is marked TRUE for execution
					ReportContants.ResultsFolder = regSumFolder;
				//	ReportUtilities.CreateExecutionFolder(regSumFolder,scenarioType, scenarioNameId);
				//	ReportData.currentScenarioName = scenarioNameId;
				//	ReportUtilities.CreateSummaryFile(regSumFolder, scenarioName);
										
					boolean runInDevMode = KeywordUtilities.isDevMode();

					for(int scenarioIndex =0;scenarioIndex < totalExecutedCount; scenarioIndex++){	

					//	scenario = result.get("ObjectRepository").get(scenarioIndex);
						String trackModule = result.get("BusinessModule").get(scenarioIndex);

						long endTime = 0;
						long duration = 0;
						long totalDuration = 0;
						long overallDuration = 0;
					    int totalIterations=1;
					       int startIteration=1;			
					       int endIteration=1;
					       String testCase="";
					       String testCaseDesc="";
					       String testCaseActual ="";
					       long startTime=0;					       
									try {
										
										boolean error = false;
										//Storing the Execution start time of the Test Case
										Calendar cal1 = Calendar.getInstance();
										startTime = cal1.getTimeInMillis();
										
										testCase = result.get("TestScript").get(scenarioIndex);
										testCaseDesc = result.get("Description").get(scenarioIndex);
										
										testCaseActual = KeywordUtilities.getActualTestCaseName(testCase);
																				
										setParameters(path, "" , keywordTestScenarioModel.TestScenarioName,  testCaseActual, rootPath,regSumFolder,testCase, keywordTestScenarioModel.TestScenarioDescription, testCaseDesc);

									//	ReportUtilities.CreateScenarioFolder(regSumFolder,scenarioNameId); 
									//	ReportUtilities.CreateScenarioHTML();
										ReportCommon TestStepLogDetails = new ReportCommon();
										TestCaseParam testCaseParam = new TestCaseParam();
										testCaseParam.TestCaseName = scenarioNameId;
										testCaseParam.ModuleName = "";
										testCaseParam.Browser = browser;
										TestStepLogDetails.logScreenDetails(testCaseParam, testCase);
										
								   
										//Storing the Number of Iterations and the start index in the Integer variables. Setting the default values to 1.
										try {
											totalIterations = (int)(Float.parseFloat(result.get("NumberOfIterations").get(scenarioIndex)));
										} catch (Exception e1) {
											
											totalIterations = 1;
										}
										
										if(!(result.get("StartIndexForIterations").get(scenarioIndex).startsWith("NextIteration")))
										{
											try {
												startIteration = (int)(Float.parseFloat(result.get("StartIndexForIterations").get(scenarioIndex)));
											} catch (Exception e1) {
												
												startIteration = 1;
											}
										}
										else if (result.get("StartIndexForIterations").get(scenarioIndex).startsWith("NextIteration")) //For Next Data
										{
											boolean nextData = true;
											String iterationKey = scenarioNameId+"_Iteration";
											String iteration = KeywordUtilities.getValueFromIterationConfigProperties(iterationKey);
											System.out.println("Iteration: "+iteration);
											startIteration = Integer.parseInt(iteration);
										}
										
										try {
											endIteration = startIteration+(totalIterations-1);
										} catch (Exception e1) {
											
											endIteration = startIteration;
										}
										
										//Setting the path of Test Data/Business flow sheets to the variable 'dataPath'
										String dataPath = KeywordUtilities.getScriptFilePathName(rootPath, trackModule, testCaseActual);
										logger.finest("dataPath-->"+dataPath);
										setParameters(path,dataPath ,keywordTestScenarioModel.TestScenarioName,
												 testCaseActual,rootPath,
												regSumFolder,testCase,keywordTestScenarioModel.TestScenarioDescription,testCaseDesc);


									//	ReportUtilities.CreateTestCaseHTML(testCase);
										
										int iterationCount = 0;//for checking whether to use Dynamic Object Repository or not; Dynamic Object Repository is to be used when iterationCount > 0;
										
										//Loop for repeating the test case execution based on the start and end iteration given for a test case
										for(int i=startIteration;i<=endIteration;i++)
										{
										
											KeywordModel keywordModel= new KeywordModel();
//											String methodName = "";
//											String keyword = "";
//											String objectName = "";
//											String objectID = "";
//											String dataValue = "";
//											String passScreenshot = "";
//											String onPassLog = "";
//											String onFailLog = "";
//											String jumpIndex = "";
//											String checkBoolean = "";
//											String stepIndex = "";
//											String stepStatus = "";
//											String resultsPath = "";
//											String dateNTime = "";
//											String ScenarioNameTostore = "" ;
//											 String SKIP_STEP = "n/a";
//											 boolean nextData = false;
//											String formName = "";
//											String APpageTitle = "";
//											int dynaElementIndex = 0;
//											int ddlIndex = 0;
//											String PrevScreenName = "HomePage";
//											WebElement dynaElement = null;
//											List<WebElement> dynamicObjects = null;
//											List<String> ScenarioList = null;
//											int totalScenarioCount = 0;
//											int resultSetCounter = 0; 			
											int currentIteration = i;
											String TestCaseFileName=dataPath;
											error = false;

											//System.out.println("Start: "+startIteration+" End: "+endIteration+" Current: "+i);
										//	ReportUtilities.WriteIterationToHTML();									
											
//											ArrayList<String> whereClause2 = new ArrayList<String>();
//											whereClause2.add("TestScript::"+testCaseActual);
//											POI_ReadExcel poiReadExcel= new POI_ReadExcel();
//											HashMap<String, ArrayList<String>> result2 = POI_ReadExcel.fetchWithCondition(dataPath, "TestSteps", whereClause2);
											
											
											List<String> whereClause2 = new ArrayList<String>();
											whereClause2.add("TestScript::"+testCaseActual);
											Map<String, List<String>> result2 = CustomFramework.fetchWithCondition(dataPath, "TestSteps", whereClause2);

											keywordModel.ScreenName = result2.get("TestScript").get(0);
											
											keywordModel.currentIteration=currentIteration;
											keywordModel.startIteration=startIteration;
											keywordModel.endIteration=endIteration;
											keywordModel.testCaseFileName=dataPath;
											keywordModel.testCase=testCase;
											keywordModel.scenario=scenarioNameId;
											keywordModel.dataPath=dataPath;
											keywordModel.testCaseActual=testCaseActual;
											keywordModel.homePath=rootPath;
											keywordModel.testCaseDesc=testCaseDesc;
											keywordModel.scenarioDesc=keywordTestScenarioModel.TestScenarioDescription;
											keywordModel.path=path;
											keywordModel.browser=browser;
											logger.finest("result2-->"+result2);
											
											for(int k=0; k<result2.get("TestScript").size(); k++){
												
												
												
												if(testCaseActual.equalsIgnoreCase(result2.get("TestScript").get(k))){
													keywordModel.resultSetCounter = 0;
													keywordModel.checkBoolean = "";
													keywordModel.keyword = result2.get("Keyword").get(k);
													
													
												
													
													//557173
													if(keywordModel.keyword.trim().length()==0)
														{
														//System.out.println("Unwanted Space Found In TestScript - "+ScreenName+"...Moving to Next Iteration/TestScript");
														continue;
														}
													
													if(keywordModel.keyword.startsWith("index=")){
														keywordModel.keyword = keywordModel.keyword.split("index=\\s*[a-zA-Z];")[1].trim();
													}
													
													if(keywordModel.keyword.contains("if(")){
														keywordModel.keyword = keywordModel.keyword.split("if\\(")[1].split("=")[0].trim();
														keywordModel.checkBoolean = result2.get("Keyword").get(k).split("if\\(")[1].split("=")[1].trim().split("\\);")[0].trim();
														keywordModel.jumpIndex = result2.get("Keyword").get(k).split("if\\(")[1].split(keywordModel.keyword+"=")[1].trim().split("\\);")[1].trim().split("goto=")[1].trim();
														
														ArrayList<String> whereClause3 = new ArrayList<String>();
														POI_ReadExcel poiReadExcel1= new POI_ReadExcel();

														whereClause3.add("TestScript::"+testCaseActual);
														HashMap<String, ArrayList<String>> result3 = poiReadExcel1.fetchWithCondition(dataPath, "TestSteps", whereClause3);
														
														for(int p=0; p<result3.get("TestScript").size(); p++){
														
															if(testCaseActual.equalsIgnoreCase(result3.get("TestScript").get(p))){
																keywordModel.resultSetCounter++;
																if(result3.get("Keyword").get(p).startsWith("index="+keywordModel.jumpIndex)){
																	break;
																}
															}
														}
														
													}
													try{
														//KeywordLibrary keywordLibrary= new KeywordLibrary();
														keywordModel.objectName = result2.get("ObjectID").get(k);
	if(scenarioType.trim().equalsIgnoreCase("WorkerPortal")||scenarioType.trim().equalsIgnoreCase("BenefitManagement") )
	{
														if (totalIterations > 1) {
															// to use Dynamic Object Repository
															keywordModel.objectID = KeywordUtilities.getObjectFromObjectMap(driver,keywordModel.objectName, LoadOR,loadDeviceOR);
															keywordModel.dynamicObjects = keywordLibrary.findElementsByType(driver,keywordModel);
																if(keywordModel.dynamicObjects.size()>1) {
																	//Loop to check and pick up the displayed element on the screen and skip the hidden ones.
																	for(int count=0;count<keywordModel.dynamicObjects.size();count++)
																		{
																		if(keywordModel.dynamicObjects.get(count).isDisplayed())
																			{
																			keywordModel.dynaElement = keywordModel.dynamicObjects.get(count);
																			keywordModel.dynaElementIndex = count;
																			keywordModel.ddlIndex = count+1;
																			break;
																			}
																		}
																	keywordModel.inputXPath = "("+keywordModel.inputXPath+")"+"["+keywordModel.ddlIndex+"]"; //For Kendo Box in WP
																	keywordModel.optionXPath = "("+keywordModel.optionXPath+")"+"["+keywordModel.ddlIndex+"]"; //For Kendo Box in WP
																	logger.fine("objectName-->"+keywordModel.objectName+"\nobjectID-->"+keywordModel.objectID);
																}else{
																	keywordModel.dynaElement=keywordModel.dynamicObjects.get(0);
																	//objectID = KeywordUtilities.getObjectFromObjectMap(homePath, trackModule, objectName);
																}
														}
														else {
															keywordModel.objectID = KeywordUtilities.getObjectFromObjectMap(driver,keywordModel.objectName,LoadOR,loadDeviceOR);
														}
														
	}
	
	else if(scenarioType.trim().equalsIgnoreCase("ApplicantPortal"))
	{
		//List<WebElement> dynamicObjectsDisplayed = null;
		List dynamicObjectsDisplayed = new ArrayList();
		keywordModel.objectID = KeywordUtilities.getObjectFromObjectMap(driver,keywordModel.objectName,LoadOR,loadDeviceOR);
		keywordModel.dynamicObjects = keywordLibrary.findElementsByType(driver,keywordModel);
			if(keywordModel.dynamicObjects.size()>1) 
			{
				int elementsDisplayed =0;
				for(int count=0;count<keywordModel.dynamicObjects.size();count++)
				{
					if(keywordModel.dynamicObjects.get(count).isDisplayed())
						{
						elementsDisplayed = elementsDisplayed+1;
						dynamicObjectsDisplayed.add((elementsDisplayed-1), keywordModel.dynamicObjects.get(count));
						
						}
				}
				//System.out.println("COunt for Object "+objectID+"is "+elementsDisplayed);
				if(elementsDisplayed>1)
					{
					int dynaLength = dynamicObjectsDisplayed.size();
					keywordModel.dynaElement = (WebElement) dynamicObjectsDisplayed.get(dynaLength-1);
					//dynaElementIndex = dynaLength-1;
					}
				else
				{
					for(int count=0;count<keywordModel.dynamicObjects.size();count++)
					{
					if(keywordModel.dynamicObjects.get(count).isDisplayed())
						{
						keywordModel.dynaElement = keywordModel.dynamicObjects.get(count);
						//dynaElementIndex = count;
						break;
						}
					}
				}
				
				elementsDisplayed = 0;
			}
			else{
				keywordModel.dynaElement=keywordModel.dynamicObjects.get(0);
				}

		
	}
	
													
								}
													
													
													catch(Exception e){
														//e.printStackTrace(); 557173
													}
													try {
														keywordModel.objectID = KeywordUtilities.getObjectFromObjectMap(driver,keywordModel.objectName,LoadOR,loadDeviceOR);
														keywordModel.dataValue = result2.get("KeyInData").get(k);
														logger.fine("dataValue-->"+keywordModel.dataValue);
														if(keywordModel.dataValue.startsWith("getData=")){
															keywordModel.dataValue = KeywordUtilities.getData(
																	driver,
																	dataPath,
																	testCase,
																	keywordModel.dataValue.split("getData=")[1],
																	currentIteration );
															keywordModel.dataValue = keywordLibrary.verifyAndAssignDataValue(keywordModel.dataValue, keywordModel);
														}
														if(keywordModel.dataValue.startsWith("getConfigData=")){
															keywordModel.dataValue = KeywordUtilities.getData(
																	driver,
																	dataPath,
																	testCase,
																	keywordModel.dataValue.split("getConfigData=")[1],
																	currentIteration);
															keywordModel.dataValue = KeywordUtilities.getValueFromConfigProperties(keywordModel.dataValue);

														}
														if(keywordModel.dataValue.startsWith("getSheetData=")){
															keywordModel.dataValue = KeywordUtilities.getData(
																	driver,
																	dataPath,
																	testCase,
																	keywordModel.dataValue.split("getSheetData=")[1],
																	currentIteration);
															keywordModel.dataValue = keywordLibrary.verifyAndAssignDataValue(keywordModel.dataValue, keywordModel);
														}
													} catch (Exception e) {														
														e.printStackTrace();
													}
													
													try{
														keywordModel.passScreenshot = result2.get("PassScreenshot").get(k);
														if(keywordModel.passScreenshot == null){
															keywordModel.passScreenshot="";
														}
													}catch(Exception e){														
														e.printStackTrace();
													}
													
													try {
														keywordModel.onPassLog = result2.get("OnPassLogMsg").get(k);
														if(keywordModel.onPassLog==null){
															keywordModel.onPassLog = "";
														}
													} catch (Exception e) {														
														keywordModel.onPassLog = "";
													}
													
													try {
														keywordModel.onFailLog = result2.get("OnFailLogMsg").get(k);
														if(keywordModel.onFailLog==null){
															keywordModel.onFailLog = "";
														}
													} catch (Exception e) {														
														keywordModel.onFailLog = "";
													}
													if(error==false){
														if(!keywordModel.keyword.endsWith("#")){
															if(keywordModel.keyword.startsWith("function=")){
																keywordModel.methodName = keywordModel.keyword.split("function=")[1].trim();
																ExecuteMethod(driver,keywordModel);
															}else{
																ExecuteKeyword(driver,keywordModel);
															}
														}
														
													}
													if(keywordModel.resultSetCounter==0){
														keywordModel.stepStatus="false";
													}else{
														if(keywordModel.checkBoolean.equalsIgnoreCase("true")){
															if(keywordModel.stepStatus.equalsIgnoreCase("true")){
																
																k = keywordModel.resultSetCounter - 2;
															}
														}else if(keywordModel.checkBoolean.equalsIgnoreCase("false")){
															if(keywordModel.stepStatus.equalsIgnoreCase("false")){
																
																k = keywordModel.resultSetCounter - 2;
															}
														}
														keywordModel.stepStatus="false";
													}
												}
											}
											iterationCount++;
											keywordModel.dynaElement = null;
											keywordModel.dynamicObjects=null;
											if(i==endIteration)
											{
												keywordModel.PrevScreenName = keywordModel.ScreenName;
												
											}
										}
										boolean nextData=false;
										if(nextData == true)
										{
											nextData=false;
											String iterationKey = scenarioNameId+"_Iteration";
											String iteration = KeywordUtilities.getValueFromIterationConfigProperties(iterationKey);
											int iterationUsed = Integer.parseInt(iteration);
											iterationUsed = iterationUsed+1;
											KeywordUtilities.updateIterationConfigProperties(iterationKey, Integer.toString(iterationUsed));
											
										}
										//Storing the Execution End Time of the Test Case
										Calendar cal2 = Calendar.getInstance();
										endTime = cal2.getTimeInMillis();
										duration = endTime-startTime;
										//Adding the durations of individual Test Cases to display in the Scenario HTML file
										totalDuration=totalDuration+duration;
										
										//Method to add a row in the Scenario HTML file at the end of execution of a particular test case
									//	ReportUtilities.AddRowToScenarioHTML(); 
										
										//Method to Close the Test Case HTML file by adding the Test Case Summary at the end of the result file
									//	ReportUtilities.closeTestCaseHTML();
									} catch (Exception e) {
										logger.log(Level.SEVERE, "Exception while executing-->"+testCaseActual, e);											

										//Storing the Execution End Time of the Test Case
										Calendar cal2 = Calendar.getInstance();
										endTime = cal2.getTimeInMillis();
										duration = endTime-startTime;
										//Adding the durations of individual Test Cases to display in the Scenario HTML file
										totalDuration=totalDuration+duration;
										
										//Method to add a row in the Scenario HTML file at the end of execution of a particular test case
									//	ReportUtilities.AddRowToScenarioHTML(); 
										
										//Method to Close the Test Case HTML file by adding the Test Case Summary at the end of the result file
									//	ReportUtilities.closeTestCaseHTML();  
										
										//Adding this line to move out of this scenario as the exception has occured
										scenarioIndex = totalExecutedCount + 1;
									}

						//Method to add a row to the Summary HTML file at the end of execution of a particular Scenario
					//	ReportUtilities.AddRowToSummary(testCase,scenarioNameId); 
						
					}
					
				//	ReportUtilities.CloseSummary(); 
					//store regression summary data
				//	ReportUtilities.updateRegressionSummary(scenarioName);
					//reset the report parameters
					ReportUtilities.resetReportParameters();
					//Clear Session Data
					//DriverSession.getInstance().clear();
					//Stopping the execution at the end
					driver.quit();
					
					ReportSerialization RS = new ReportSerialization();
                    
                    ArrayList<HashMap<UUID, TestCaseDetails>> TC= TestRunDetails.getTestCaseRepository();
                          
                    TestCaseDetails TCD = new TestCaseDetails();
                    for(HashMap<UUID, TestCaseDetails> testCaseDetails : TC)
                    {
                          for (UUID uuid : testCaseDetails.keySet())
                          {
                          if(testCaseDetails.get(uuid).TestCaseName.equalsIgnoreCase(scenarioNameId))
                                 {
                                        TCD=testCaseDetails.get(uuid);
                                 }
                          }
                    }
                    System.out.println(regSumFolder+"\\"+scenarioNameId+".json" );
                    
                        if(TCD.TestCaseName.equals("")==false)
                    {
                       RS.DeSerializeJSONDataWriteToFile(TCD,regSumFolder+"\\"+scenarioNameId+".json" );
                    
                    }

                        driver.quit();	
				}catch(Exception e){
					logger.log(Level.SEVERE, "", e);
				}
			}
			
			
		}catch(Exception e){
			logger.log(Level.SEVERE, "", e);
		}
	}
	
	/**
	 * Method Name: ExecuteMethod
	 * Description: This method is to execute the method that matches with the Method name given in the Input sheet for a particular test case in the Scenario
	 * Return Type: Nothing
	 */
	public static void ExecuteMethod(WebDriver driver,KeywordModel keywordModel){
		try{
			
				if(keywordModel.dataValue != null && keywordModel.dataValue.equalsIgnoreCase(keywordModel.SKIP_STEP)){
					ReportUtilities.Log(driver,"Skip Step", "This step is marked to skip for "+keywordModel.objectName,Status.PASS, keywordModel);
				}
				else {
					setParameters(keywordModel.path,keywordModel.dataPath ,keywordModel.scenario,keywordModel.testCaseActual,keywordModel.homePath,keywordModel.resultsPath, keywordModel.testCase,keywordModel.scenarioDesc,keywordModel.testCaseDesc);

			Class cls = Class.forName("KeywordDriverUtil.ReusableFunctions");
			Object obj = cls.newInstance();
			
			Method method = cls.getDeclaredMethod(keywordModel.methodName, null);
			method.invoke(obj, null);
				}
		}catch(Exception e){
			e.printStackTrace();
			//Driver.error = true;
			ReportUtilities.Log(driver,"Unhandled Exception", "There was an Unhandled Exception while executing the method "+keywordModel.methodName+". Exception: "+e, Status.FAIL, keywordModel);
		}
		
	}
	
	/**
	 * Method Name: ExecuteMethod
	 * Description: This method is to execute the method that matches with the Method name given in the Input sheet for a particular test case in the Scenario
	 * Return Type: Nothing
	 */
	private static void ExecuteKeyword(WebDriver driver,KeywordModel keywordModel){
		try{
			if(keywordModel.dataValue != null && keywordModel.dataValue.equalsIgnoreCase(keywordModel.SKIP_STEP)){
				ReportUtilities.Log(driver,"Skip Step", "This step is marked to skip for "+keywordModel.objectName,Status.PASS,keywordModel);
			}else{
				String resultsPath;
				setParameters(keywordModel.path,keywordModel.dataPath ,keywordModel.scenario,keywordModel.testCaseActual,keywordModel.homePath,keywordModel.resultsPath, keywordModel.testCase,keywordModel.scenarioDesc,keywordModel.testCaseDesc);


				Class cls = Class.forName("KeywordDriverUtil.KeywordLibrary");
				Object obj = cls.getConstructor().newInstance();
				Method method = cls.getDeclaredMethod(keywordModel.keyword, WebDriver.class,KeywordModel.class);
				method.invoke(obj, driver,keywordModel);
			}
		}
		catch(InvocationTargetException | NoSuchElementException p)
		{
			logger.log(Level.WARNING, "New Execption while executing keyword-->"+keywordModel.keyword, p); //For console
			keywordModel.error = true;
//			if((KeywordLibrary.errorMessage ||KeywordLibrary.applicationError ||KeywordLibrary.navigationError || KeywordLibrary.displayError)) 
//			{
//				//Exception Handled in Keyword Library
//			}
//			else
//			{
				ReportUtilities.Log(driver,"Unhandled Exception while performing action on Object "+keywordModel.objectName, "There was an Unhandled Exception while executing the keyword "+keywordModel.keyword+". Exception: "+p, Status.FAIL,keywordModel);//For invocation target exception
//			}
		//	keywordModel.methodName = "Logout";
		//		ExecuteMethod(driver,keywordModel);
				keywordModel.objectName = "";
				throw new RuntimeException(p);
		}
		catch(Exception e){
			logger.log(Level.WARNING, "Execption while executing keyword-->"+keywordModel.keyword, e); //For console
			keywordModel.error = true;
            if(e.getCause().getClass().equals(AUTException.class)){
                AUTException ex= (AUTException)e.getCause();
               ReportUtilities.Log(driver,"Error occured while executing the keyword" +"  "+ keywordModel.keyword  +"  " + "on" +" "+keywordModel.objectName,ex.getErrorCode() , Status.FAIL,keywordModel);
            }
            //Changed for exception
         
            else if((!keywordModel.objectName.equalsIgnoreCase("")) || (!keywordModel.objectName.equalsIgnoreCase(null))) {
            	System.out.println("Coming to old catch");
				ReportUtilities.Log(driver,"Unhandled Exception while performing action on Object "+keywordModel.objectName, "There was an Unhandled Exception while executing the keyword "+keywordModel.keyword+". Exception: "+e, Status.FAIL,keywordModel);}//For invocation target exception}
			else
				ReportUtilities.Log(driver,"Unhandled Exception", "There was an Unhandled Exception while executing the keyword "+keywordModel.keyword+". Exception: "+e, Status.FAIL,keywordModel);
            keywordModel.methodName = "Logout";
			ExecuteMethod(driver,keywordModel);
			keywordModel.objectName = "";
			throw new RuntimeException(e);
		}
	
	}
	
	private static  void setParameters(String path, String dataPath , String scenario, String testCaseActual, String homePath,
			String resultsPath,String testCase, String scenarioDesc, String testCaseDesc){
		
		ReportData.path = path;
		ReportData.dbPath = dataPath;
		ReportData.scenario = scenario;
		//ReportData.testcase = testCaseActual;
		ReportData.homePath = homePath;
	//	ReportData.resultPath = resultsPath;
		ReportData.scenario = testCase;
		ReportData.scenarioDesc = scenarioDesc;
		ReportData.testcase = scenario;
		ReportData.testCaseDesc = testCaseDesc;
	}
	

}
