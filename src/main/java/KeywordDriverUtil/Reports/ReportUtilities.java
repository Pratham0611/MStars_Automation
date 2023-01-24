package KeywordDriverUtil.Reports;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;

import com.deloitte.framework.objects.RegressionScenario;

import Common.ReportCommon;
import Constants.ReportContants;
import KeywordDriverUtil.DefectLogger;
import KeywordDriverUtil.DriverSession;
import KeywordDriverUtil.KeywordUtilities;
import KeywordDriverUtil.Reports.ReportData.Status;
import Model.TestCaseParam;
import Model.ExtentModel.PageDetails;
import Model.ExtentModel.TestRunDetails;
import Model.KeywordDriven.KeywordModel;

public class ReportUtilities {

	
	private static ReportGenerator reportGen = ReportGeneratorFactory.getInstance().getReportGenerator();
	public static final Logger logger = Logger.getLogger(ReportUtilities.class.getName());

	
	

	/**
	 * Method Name: CreateSummaryFile
	 * Description: This method is to create the Overall Summary HTML file and create the headers in the file
	 * Return Type: Nothing
	 */
	public static void CreateSummaryFile(String ResultPath ,String scenarioName){
		
		String summaryHTML = ResultPath +File.separatorChar+"Summary.htm";
		ReportData.summaryHTML = summaryHTML;
		reportGen.createScenarioSummaryHeader(scenarioName,summaryHTML);
	//	testCase = new HashMap<String,String>();

	}
	
	/**
	 * Method Name: AddRowToSummary
	 * Description: This method is to add a row to the Summary HTML file at the end of execution of a particular scenario
	 * Return Type: Nothing
	 * @param testCase 
	 */
	public static void AddRowToSummary(String testCaseName,String scenario ){

		try
		{
			

		//Method to close the Scenario HTML file at the end of execution of the Scenario
		closeScenarioHTML();


		int noOfStepsFailed = ReportData.TestCasesFailed.get(scenario);
		int noOfStepsPassed = ReportData.TestCasesPassed.get(scenario);
		int TotalSteps =noOfStepsFailed+noOfStepsPassed;
				
		String shref = "../" + ReportData.dateNTime + "/" + testCaseName + "/"
				+ testCaseName+".htm";
		reportGen.addRowToScenarioSummary(ReportData.summaryHTML, testCaseName,
				TotalSteps, noOfStepsPassed,
				noOfStepsFailed, returnTime(ReportData.totalDuration), shref);

		ReportData.overallDuration = ReportData.overallDuration + ReportData.totalDuration;

		ReportData.totalDuration = (long) 0;
		ReportData.scenarios.add(ReportData.testcase);
		if (ReportData.TestCasesFailed.get(scenario) == 0) {
			ReportData.testCase.put(ReportData.testcase, "Passed");
		} else {
			ReportData.testCase.put(ReportData.testcase, "Failed");
		}

		}catch(Exception ex)
		{
			System.out.println(ex.getStackTrace());
		}
		}
	
	/**
	 * Method Name: CloseSummary
	 * Description: This method is to add a row at the end of the Summary HTML file at the end of entire execution
g	 * Return Type: Nothing
	 */
	public static void CloseSummary(){
		
		ReportData.totalpassTC = ReportData.totalExecutedCount - ReportData.totalfailTC;
		reportGen.closeScenarioSummary(ReportData.summaryHTML,ReportData.totalExecutedCount, ReportData.totalpassTC, ReportData.totalfailTC,returnTime(ReportData.overallDuration));
		 try
         {
 //         ExtentUtilities extentutilities = new ExtentUtilities();
//          UUID tcId = extentutilities.getTestCaseID(scenarioNameId, "", browser); 
            ExtentReport.ExtentReport extentReport = new ExtentReport.ExtentReport();
          
    extentReport.CreateExtentReport_Category("C:\\Temp\\TempResults",TestRunDetails.getTestCaseRepository(),"Test","Test","Test");
     }
          catch(Exception e)
          {
                 System.out.println("Failed to generate the extent test results");
                 System.out.println(e.getMessage());
                 System.out.println(e.getStackTrace());
                 
          }   
	}
	
	/**
	 * Method Name: CreateTestCaseHTML
	 * Description: This method is to create the TestCase HTML file and create the headers in the file
	 * Return Type: Nothing
	 * @param testCaseName 
	 */
	public static void CreateTestCaseHTML(String testCaseName){
		
		ReportData.testCaseHTML = ReportData.resultsPath+File.separatorChar+ReportData.scenario+File.separatorChar+testCaseName+".htm";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMMM yyyy");
		reportGen.createTestCaseHeader(ReportData.testCaseHTML,testCaseName,sdf.format(cal.getTime()).toString());

	}
	
	/**
	 * Method Name: Log
	 * Description: This method is to write a step to the HTML result file
	 * Return Type: Nothing 
	 */
	public static void Log(WebDriver driver,String stepName, String stepDesc, Status status,KeywordModel keywordModel){
			ReportCommon TestStepDetails = new ReportCommon();
			
			TestCaseParam testCaseParam = new TestCaseParam();
	//		String[] curr_scenario = ReportData.currentScenarioName.replaceAll(Pattern.quote("\\"), "\\\\").split("\\\\");
//				if(ReportData.onPassLog != null && !ReportData.onPassLog.trim().isEmpty() && (status.equals(Status.PASS))) {
//					stepDesc = ReportData.onPassLog;
//				} else if(ReportData.onFailLog != null && !ReportData.onFailLog.trim().isEmpty() && status.equals(Status.FAIL)) {
//					stepDesc = ReportData.onFailLog;
//				}
//				String image = null;
//				String stepStatus = "DONE";
//
				if(status.equals(Status.PASS)){
					ReportData.stepStatus = "PASS";
//					ReportData.pass++;
//					if(KeywordUtilities.getValueFromConfigProperties("SnapshotForAllPass").equalsIgnoreCase("True")){
//						createScreenshotFolder();
//						createScreenshotPassFolder();
//						KeywordUtilities.CaptureScreenshot(driver,ReportData.screenshotPassPath+"/"+ReportData.scenario+"_"+ReportData.passInc+".png", Status.pass);
//						stepStatus = "PASS";
//						image = "../"+ReportData.scenario+"/Screenshots/Passed/"+ReportData.scenario+"_"+(ReportData.passInc++)+".png";
//					}else if(KeywordUtilities.getValueFromConfigProperties("SnapshotForAllPass").equalsIgnoreCase("False")){
//						if(ReportData.passScreenshot.equalsIgnoreCase("Yes")){
//							createScreenshotFolder();
//							createScreenshotPassFolder();
//							KeywordUtilities.CaptureScreenshot(driver,ReportData.screenshotPassPath+"/"+ReportData.scenario+"_"+ReportData.passInc+".png", Status.pass);
//							stepStatus = "PASS";
//							image = "../"+ReportData.scenario+"/Screenshots/Passed/"+ReportData.scenario+"_"+(ReportData.passInc++)+".png";							
//						}else{
//							stepStatus = "PASS";
//						}
//					}
//					
				}else if(status.equals(Status.FAIL)){
					ReportData.stepStatus = "FAIL";
//					ReportData.fail++;
//					
//					createScreenshotFolder();
//					createScreenshotFailFolder();
//					KeywordUtilities.CaptureScreenshot(driver, ReportData.screenshotFailPath+"/"+ReportData.scenario+"_"+ReportData.failInc+".png", Status.FAIL);
//					captureDefect(ReportData.currentScenarioName, ReportData.scenario, ReportData.testCaseStepNo+"~"+stepName, stepDesc, ReportData.scenario+"_"+ReportData.failInc+".png");
//					stepStatus = "FAIL";
//					image = "../"+ReportData.scenario+"/Screenshots/Failed/"+ReportData.scenario+"_"+(ReportData.failInc++)+".png";
				}else if(status.equals(Status.DONE)){
					ReportData.stepStatus = "DONE";
				}
				
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");

	//			reportGen.logTestStepDetails(ReportData.testCaseStepNo,stepName, stepDesc, stepStatus, ReportData.testCaseHTML, sdf.format(cal.getTime()), image);
				testCaseParam.TestCaseName = keywordModel.scenario;
				testCaseParam.ModuleName = "";
				testCaseParam.Browser = keywordModel.browser;
				testCaseParam.Iteration = ReportData.currentIteration;
				testCaseParam.TestCaseDescription = ReportData.testCaseDesc;
				try {
					PageDetails pageDetails = new PageDetails();
					pageDetails.PageActionName = stepName;
					pageDetails.PageActionDescription = stepDesc;
					TestStepDetails.logTestStepDetails(driver, testCaseParam, stepName+". "+ stepDesc, stepDesc, pageDetails,LocalDateTime.now(), ReportData.stepStatus);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				ReportData.testCaseStepNo++;

	}
	
	
	
	/**
	 * Method Name: WriteIterationToHTML
	 * Description: This method is to write the current iteration to the HTML result file
	 * Return Type: Nothing 
	 */
	public static void WriteIterationToHTML(){
		
		reportGen.logTestCaseIterationDetails(ReportData.testCaseHTML,ReportData.currentIteration,ReportData.startIteration);

	}
	/**
	 * Method Name: closeTestCaseHTML
	 * Description: This method is to add the Summary details of the Test Case at the end of the Test Case HTML result file
	 * Return Type: Nothing
	 */
	public static void closeTestCaseHTML(){
		
		reportGen.logTestCaseSummary(ReportData.testCaseHTML,(ReportData.testCaseStepNo-1),ReportData.pass,ReportData.fail,returnTime(ReportData.duration));

		ReportData.TestCasesExecuted.put(ReportData.scenario, (ReportData.testCaseStepNo-1));

		ReportData.testCaseStepNo=1;
		if(ReportData.fail>0)
			ReportData.scenarioFail++;
		else if(ReportData.pass>0 && ReportData.fail==0)
			ReportData.scenarioPass++;
		ReportData.pass=0;
		ReportData.fail=0;
		ReportData.duration=0;
		ReportData.passScreenshot = "";
	}
	
	/**
	 * Method Name: CreateScenarioHTML
	 * Description: This method is to create the Scenario HTML file and create the header in the file
	 * Return Type: Nothing
	 */
	public static void CreateScenarioHTML(){
				
		ReportData.scenarioHTML = ReportData.resultsPath+"/"+ReportData.scenario+"/"+ReportData.testcase+"_Summary.htm";

	}
	
	/**
	 * Method Name: AddRowToScenarioHTML
	 * Description: This method is to add a row to the Scenario HTML file at the end of execution of each test case
	 * Return Type: Nothing
	 */
	public static void AddRowToScenarioHTML(){
		//Keeping it empty for now as it is not required
	}
	
	/**
	 * Method Name: closeScenarioHTML
	 * Description: This method is to Close the Scenario HTML file by adding the Execution summary of the scenario at the end of the file
	 * Return Type: Nothing
	 */
	public static void closeScenarioHTML(){
		
		ReportData.TestCasesPassed.put(ReportData.testcase, ReportData.scenarioPass);
		ReportData.TestCasesFailed.put(ReportData.testcase, ReportData.scenarioFail);
		
		ReportData.totalpassTC = ReportData.totalpassTC+ReportData.scenarioPass;
		ReportData.totalfailTC = ReportData.totalfailTC+ReportData.scenarioFail;
		
		ReportData.scenarioPass=0;
		ReportData.scenarioFail=0;

	}
	
	/**
	 * Method Name: returnTime
	 * Description: This method is to return the Execution time of the test case in the String format 'hh: mm: ss'
	 * Return Type: String
	 */
	public static String returnTime(Long duration){
		
		try
		{
		    String format = String.format("%%0%dd", 2);  
		    duration = duration / 1000;
		    String seconds = String.format(format, duration % 60);  
		    String minutes = String.format(format, (duration % 3600) / 60);  
		    String hours = String.format(format, duration / 3600);  
		    String time =  hours + " : " + minutes + " : " + seconds;
		    return time;
		}
		
		catch(Exception e)
		{
			return "00:00:00";
		}
	}
	
	public static String getFormattedDate(long dateTime, String returnformat){
		DateFormat sdf = new SimpleDateFormat(returnformat);
		sdf.setTimeZone(KeywordUtilities.getTimeZone());
		return sdf.format(new Date(dateTime));
	}
	
	public static long getTime(String date, String format) throws ParseException{
		DateFormat sdf = new SimpleDateFormat(format);
		Date pdate = sdf.parse(date);
		return pdate.getTime();
	}
	
	/**
	 * Method Name: createScreenshotFolder
	 * Description: This method is to create the Screenshot folder whenever there is a Failed step in a test case or a screenshot is to be captured for an important Passed
	 * 				step
	 * Return Type: Nothing
	 */
	public static void createScreenshotFolder(){
		
		String screenshotFolder = ReportData.resultsPath+"/"+ReportData.scenario+"/"+"Screenshots";
		File f = new File(screenshotFolder);
		if(!f.exists()){
			f.mkdir();
		}
	}
	
	/**
	 * Method Name: createScreenshotPassFolder
	 * Description: This method is to create the 'Passed' folder inside the Screenshot folder whenever there is a screenshot captured for any important step that is PASS
	 * Return Type: Nothing
	 */
	public static void createScreenshotPassFolder(){
		
		ReportData.screenshotPassPath = ReportData.resultsPath+"/"+ReportData.scenario+"/"+"Screenshots/Passed";
		File f = new File(ReportData.screenshotPassPath);
		if(!f.exists()){
			f.mkdir();
		}
	}
	
	/**
	 * Method Name: createScreenshotFailFolder
	 * Description: This method is to create the 'Failed' folder inside the Screenshot folder whenever there is a Failed step in a test case
	 * Return Type: Nothing
	 */
	public static void createScreenshotFailFolder(){
		
		ReportData.screenshotFailPath = ReportData.resultsPath+"/"+ReportData.scenario+"/"+"Screenshots/Failed";
		File f = new File(ReportData.screenshotFailPath);
		if(!f.exists()){
			f.mkdir();
		}
	}
	
	public static void resetReportParameters(){
		ReportData.homePath = "";
		ReportData.resultsPath = "";
		ReportData.summaryHTML = "";
		ReportData.testCaseHTML = "";
		ReportData.scenarioHTML = "";
		ReportData.scenario = "";
		ReportData.scenarioDesc = "";
		ReportData.testCaseDesc = "";
		ReportData.testcase = "";
		ReportData.screenshotPassPath = "";
		ReportData.screenshotFailPath = "";
		ReportData.testCaseStepNo = 1;
		ReportData.pass=0;
		ReportData.fail=0;
		ReportData.scenarioPass=0;
		ReportData.scenarioFail=0;
		ReportData.passInc=1;
		ReportData.failInc=1;
		ReportData.totalpassTC = 0;
		ReportData.totalfailTC = 0;
		ReportData.TestCasesPassed = new HashMap<String, Integer>();
		ReportData.TestCasesFailed = new HashMap<String, Integer>();		
	}
	
	public static void updateRegressionSummary(String scenarioName){
		System.out.println("Scenario name in Report.java beggining"+scenarioName);
		List<String> regressionScenario = new ArrayList<String>();
		regressionScenario.add(getScenarioNameForReport(scenarioName));
		regressionScenario.add(ReportData.summaryHTML);
		regressionScenario.add(String.valueOf(ReportData.totalExecutedCount));
		regressionScenario.add(String.valueOf(ReportData.totalpassTC));
		regressionScenario.add(String.valueOf(ReportData.totalfailTC));
		regressionScenario.add(returnTime(ReportData.overallDuration));
		//regressionScenario.add(getArtifacts());
		ReportData.regSummaryTotalDuration += ReportData.overallDuration;
		ReportData.regressionScenarios.add(regressionScenario);
		ReportData.testCaseData.put(getScenarioNameForReport(scenarioName),ReportData.testCase);
		logger.fine("regressionScenarios-->"+ReportData.regressionScenarios);
		System.out.println("RegScenarios List "+ReportData.regressionScenarios);
	}
	
	
	private static String getScenarioNameForReport(String scenarioName){
		String pattern = Pattern.quote(String.valueOf(File.separatorChar));
		String[] name = scenarioName.split(pattern);
		for(int i =0;i<name.length;i++)
		{
		System.out.println("Array name: "+name[i]);
		}
		return name[3];
	}
	
	public static void createRegressionSummaryReport() throws IOException{	
		String regSummaryHtml = ReportData.defectLogFolder + File.separatorChar +"RegressionSummary.htm";

		reportGen.createRegressionSummaryReport(regSummaryHtml,ReportData.regressionScenarios, returnTime(ReportData.regSummaryTotalDuration));
	}
	
	public static void saveRegSummaryReport(String type,String name, String regSumFolder) throws IOException{
//		for(List<String> regScenario : regressionScenarios){
//			ReportUtil rutil = new ReportUtil(regSumFolder);
//			rutil.saveRegScenarioSummary(type, name, regScenario);
//	}
	}
	
	public static void createScenarioAssitanceMap(RegressionScenario[] regScenarios, String regSumFolder){		
		reportGen.createScenarioAssitanceMap(regScenarios, regSumFolder);		
	}
	
	//Read the details and the call the method
	private static void createScenarioAssitanceMap(String regSumRptFolder) throws IOException, ClassNotFoundException{
		//Read the details and then call the above overloaded method
//		ReportUtil rutil = new ReportUtil(Util.getRootPath()+ ReportData.appendPath +File.separatorChar+"Results");
//		RegressionScenario[] regScenarios = rutil.getRegressionScenarioInfo();
//		createScenarioAssitanceMap(regScenarios,regSumRptFolder);
	}
	
	public static void createRegressionSummaryReport(String regSumRptFolder, List<List<String>> regScenarios, long executionTime, String beginTime, String environment) throws IOException, ClassNotFoundException, ParseException{
		ReportData.defectLogFolder = regSumRptFolder;
		ReportData.regressionScenarios = regScenarios;
		//showMatrixReport = false;
		ReportData.regSummaryTotalDuration = executionTime;
		createRegressionSummaryReport();
		createScenarioAssitanceMap(regSumRptFolder);
		long btime = getTime(beginTime,"yyyyMMddHHmmssSSS");
		updateBuildInfo(btime, environment);
	//	createSnapShotFile(regSumRptFolder);
	}
	
	public static String createRegressionSummaryFolder(String RootPath) throws IOException{
		String regSummaryfolder = RootPath +  File.separatorChar + "Results";
		String timeStamp = "RegressionRun_"+KeywordUtilities.getCurrentDate()+"_"+KeywordUtilities.getCurrentTime();
		regSummaryfolder = regSummaryfolder + File.separatorChar + timeStamp;
		File f= new File(regSummaryfolder);
		if(!f.exists()){
			f.mkdir();
		}
		ReportData.defectLogFolder = regSummaryfolder;
		

		return regSummaryfolder;
	}
	
	public static String createRegressionSummaryFolder(String resultRootPath, String resultsSuffix) throws IOException{
		
		System.out.println("check folder creation");
		String regSummaryfolder = resultRootPath + File.separatorChar + resultsSuffix;
		String timeStamp = "RegressionRun_"+KeywordUtilities.getCurrentDate()+"_"+KeywordUtilities.getCurrentTime();
		regSummaryfolder = regSummaryfolder + File.separatorChar + timeStamp;
		File f= new File(regSummaryfolder);
		if(!f.exists()){
			f.mkdir();
		}
		ReportData.defectLogFolder = regSummaryfolder;
		createLogsFolder(regSummaryfolder);
		return regSummaryfolder;
	}	
	
	private static void createLogsFolder(String regSummaryfolder){
		String logsDir = regSummaryfolder + File.separatorChar + "logs";
		File f= new File(logsDir);
		if(!f.exists()){
			f.mkdir();
		}
	}
	
	public static void updateBuildInfo(long beginTime, String environment){
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - beginTime;
		String buildDate = getFormattedDate(beginTime, "dd-MMM-yyyy");
		String startTime = getFormattedDate(beginTime,"hh:mm:ss.SSS a");
		String sEndTime = getFormattedDate(endTime,"hh:mm:ss.SSS a");
		reportGen.updateBuildInfo(buildDate, startTime, sEndTime, returnTime(totalTime), environment);
	}
	
	public static void CreateMatrixSummaryFile() throws IOException{
		ReportData.matrixSummaryHtml = ReportData.defectLogFolder + File.separatorChar +"MatrixReport.htm";
		File f = new File(ReportData.matrixSummaryHtml);
		if(!f.exists()){
			try {
				f.createNewFile();
			} catch (Exception e) {				
				logger.log(Level.WARNING, "Error while creating matrix summary file", e);
			}
		}
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(f));
			bw.write("<html><head><title>Matrix Summary</title></head><body><center><font size=5><b>Test Execution Summary</b><br>" +
					"<font size=4><div style=\"float:right;margin-right:165px;\"><b>Execution Performed By:</b> AUT</div><br><br>" +
					"<table border=1 width=1000><tr><td bgcolor=#F8F8FF align = left width = 500><font color = black size=3><b>"+"Project:</b> "+KeywordUtilities.getValueFromConfigProperties("ProjectName")+"</td>" +
					"<td bgcolor=#F8F8FF align = right width = 500><font color = black size=3><b>Release: </b>"+KeywordUtilities.getValueFromConfigProperties("Release")+"</td></tr></table>" +
					"<table border=1 width=1000><tr bgcolor=white><font size=4 color=black><b>"+
			 		"<table border=0 width=1000><tr></table><br>");
				bw.write("<table><tr><td><hr width=10px style=height:10px color=red></td><td><font size=1>Failed Test Cases</font></td></tr><tr><td>" +
						"<hr width=10px style=height:10px color=green></td><td><font size=1>Passed Test Cases</font></td></tr><tr><td>" +
						"<hr width=10px style=height:10px color=black></td><td><font size=1>" +
						"Not Applicable</font></td></tr></table>");
				bw.write("<br>");
				bw.write("<table border=1 width=1000><tr bgcolor=black><td colspan=5><font size=5 face=candara color=black><b><tr><td bgcolor=#F8F8FF width=50><b><font size =2 color=black face = verdana><center>Scenario</td>");
			Set<String> executedscenarios=ReportData.testCaseData.keySet();
			for(String scenarios:executedscenarios){
				bw.write("<td bgcolor=#F8F8FF width=50><b><font size =2 color=black face = verdana><center>"+scenarios+"<center></td>");
			}
			bw.write("</tr>");
			createMatrix(ReportData.testCaseData, ReportData.scenarios, bw);
           	bw.close();
		}catch(Exception e){
			logger.log(Level.WARNING, "Error while creating matrix summary file", e);
		}
	}
	
	private static void createMatrix(Map<String,Map<String,String>> data,LinkedHashSet<String> test,BufferedWriter bw){
		for(String scenarioData : test){
			try {
				bw.write("<tr><td bgcolor=#8B4513 width=50><b><font color=white size =1 face = verdana><center>"+scenarioData+"</td>");
				for(Map.Entry<String, Map<String,String>> testing:ReportData.testCaseData.entrySet()){
					if(testing.getValue().containsKey(scenarioData)){
						
						if(testing.getValue().get(scenarioData).equalsIgnoreCase("Passed")){
							bw.write("<td bgcolor=green width=50><b><font color=white size =1 face = verdana><center></td>");
						}else if(testing.getValue().get(scenarioData).equalsIgnoreCase("Failed")){
							bw.write("<td bgcolor=red width=50><b><font color=white size =1 face = verdana><center></td>");
						}
						
					}else{
						bw.write("<td bgcolor=black width=100><b><font color=white size =1 face = verdana><center></td>");
					}
				}
				bw.write("</tr>");
				
			} catch (IOException e) {				
				logger.log(Level.WARNING, "Error while creating matrix", e);
			}
		}
	}
	
	private static void captureDefect(String scenarioName, String testCaseName, String stepName, String stepDesc, String screenShotName){
		String scenarioTestCase = scenarioName +"~"+testCaseName;
		int keyIndex = getKeyIndex(scenarioTestCase);
		addTestStepDetails(keyIndex, stepName, stepDesc, screenShotName);			
	}
	
	private static int getKeyIndex(String scenarioTestCase){
		int keyIndex = ReportData.scenarioDefectList.indexOf(scenarioTestCase);
		if(keyIndex == -1){
			/*Means this scenario test case doesn't exists in the list */
			ReportData.scenarioDefectList.add(scenarioTestCase);
			keyIndex = ReportData.scenarioDefectList.size() - 1;
		}	
		return keyIndex;
	}
	
	private static void addTestStepDetails(int keyIndex, String stepName, String stepDesc, String screenShotName){
		List<Map<String, String>> testCase = ReportData.testcaseDefectList.get(keyIndex);
		if(testCase == null){
			testCase = new ArrayList<Map<String, String>>();
		}
		Map<String, String> testStepDetails = getTestCaseStepDetails(stepName, stepDesc, screenShotName);
		testCase.add(testStepDetails);
		ReportData.testcaseDefectList.put(keyIndex, testCase);
	}
	
	private static Map<String, String> getTestCaseStepDetails(String stepName, String stepDesc, String screenShotName){
		Map<String, String> testCaseStepDetails = new HashMap<String, String>();
		String[] noName = stepName.split("~");
		testCaseStepDetails.put("no", noName[0]);
		testCaseStepDetails.put("name", noName[1]);
		testCaseStepDetails.put("desc", stepDesc);
		testCaseStepDetails.put("img", screenShotName);
		return testCaseStepDetails;
	}
	
	public static void logDefects(){
		DefectLogger defectLogger = new DefectLogger(ReportData.scenarioDefectList,ReportData.testcaseDefectList, ReportData.defectLogFolder);
		defectLogger.createLog();	
//		DefectImageUtil imageUtil = new DefectImageUtil(defectLogFolder);
//		imageUtil.createFailedImageZipFile();
	}
	
//	private static String getArtifacts() {
//		StringBuffer sb;
//		try {
//			Set<String> keys = DriverSession.getInstance().getKeys();
//			if(keys.isEmpty()){
//				return "Information Not Available";
//			}
//			Iterator<String> it = keys.iterator();
//			sb = new StringBuffer();
//			
//			while (it.hasNext()) {
//				String key = (String) it.next();
//				sb.append(key);
//				sb.append("   ");
//				sb.append(":");
//				sb.append("   ");
//				sb.append(DriverSession.getInstance().get(key));
//				sb.append("<br>");
//			}
//		} catch (Exception e) {
//			return "Information Not Available";
//		}
//		
//		return sb.toString();
//	}
	
	public static  void  CreateExecutionFolder(String regSumFolder, String scenarioType, String scenarioNameId){						
		String scnTypeName = scenarioType + "-" + scenarioNameId;
		scnTypeName = scnTypeName.replaceAll(" ", "");		
		String dateNTime = scnTypeName;
		ReportData.dateNTime = dateNTime;
		ReportContants.ResultsFolder = regSumFolder + File.separatorChar + dateNTime;		
		File f= new File(ReportContants.ResultsFolder);
		if(!f.exists()){
			f.mkdir();
		}
	}
	

	public static void CreateScenarioFolder(String resultsPath, String testCase){
		if(!ReportGeneratorFactory.getInstance().isJqGridReport()){
			File f = new File(ReportContants.ResultsFolder+"\\"+testCase);
			logger.fine("Scenario folder-->"+ReportContants.ResultsFolder+"\\"+testCase);
			if(!f.exists()){
				f.mkdir();
			}
		}
	}
	

}
