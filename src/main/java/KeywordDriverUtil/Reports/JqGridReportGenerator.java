package KeywordDriverUtil.Reports;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.deloitte.framework.objects.RegressionScenario;

import Constants.PrjConstants;
import KeywordDriverUtil.KeywordUtilities;

public class JqGridReportGenerator  extends AbstractReportGenerator{
	private static final Logger logger = Logger.getLogger(JqGridReportGenerator.class.getName());
	
	private static final String UNDERSCORE = "_";
	private static final String DOUBLE_QUOTE = "\"";
	private static final String COMMA = ",";
	
	private String currentScenarioName = null;
	private String scenarioFolder = null;
	private String testCaseNameWithSequence = null;
	private String testCaseNameWithSequenceUnderScore = null;
	private StringBuilder testCaseDetails;
	private StringBuilder scenarioDetails;
	private boolean addCommaForScenario;
	private boolean newIteration=true;
	private StringBuffer scenarioImports;
	private String scenarioFolderName;
	private String regSumFolder = null;
	private StringBuilder allImports;
	private StringBuilder regSumDetails = new StringBuilder(2500);
	private StringBuilder failedScenarioDetails;
	/**
	 * 
	 */
	public JqGridReportGenerator() {
		super();
	}

	public void createScenarioSummaryHeader(String scenarioName,
			String summaryFileName) {
		logger.fine("scenarioName==>"+scenarioName);
		logger.fine("summaryFileName==>"+summaryFileName);
		String pattern = Pattern.quote(String.valueOf(File.separatorChar));
		String[] nameParts = scenarioName.split(pattern);
		currentScenarioName = nameParts[nameParts.length - 1];
		String pattern2 = Pattern.quote(String.valueOf(File.separatorChar)+"Summary.htm");
		scenarioFolder = summaryFileName.replaceFirst(pattern2, "");
		String[] parts = scenarioFolder.split(pattern);
		scenarioFolderName = parts[parts.length-1];
		assignRegSumFolder();
		scenarioDetails = new StringBuilder(2000);
		scenarioDetails.append("var ")
					   .append(currentScenarioName)
					   .append(" = [\n");
		addCommaForScenario = false;
		scenarioImports = new StringBuffer(2000);
		failedScenarioDetails = new StringBuilder(1000);
		logger.fine("currentScenarioName==>"+currentScenarioName);
		logger.fine("scenarioFolder==>"+scenarioFolder);
		logger.fine("scenarioFolderName==>"+scenarioFolderName);
	}

	public void addRowToScenarioSummary(String summaryFileName,
			String testCase, int testCasesExecuted, int passed, int failed,
			String execTime, String shref) {
		
		if(addCommaForScenario){
			scenarioDetails.append(COMMA).append("\n");
		}
		addCommaForScenario = true;
		scenarioDetails.append("[")
		   .append(DOUBLE_QUOTE)
		   .append(testCase)
		   .append(DOUBLE_QUOTE)
		   .append(COMMA)
		   .append(DOUBLE_QUOTE)
		   .append(testCasesExecuted)
		   .append(DOUBLE_QUOTE)
		   .append(COMMA)
		   .append(DOUBLE_QUOTE)
		   .append(passed)
		   .append(DOUBLE_QUOTE)
		   .append(COMMA)
		   .append(DOUBLE_QUOTE)
		   .append(failed)
		   .append(DOUBLE_QUOTE)
		   .append(COMMA)
		   .append(DOUBLE_QUOTE)
		   .append(execTime)
		   .append(DOUBLE_QUOTE)
		   .append("]");		   
		
	}

	public void closeScenarioSummary(String summaryFileName, int totalExecuted,
			int totalTCPassed, int totalTCFailed, String execTime) {
		
		scenarioDetails.append("];\n\n");
		addCommaForScenario = false;
		scenarioDetails.append("var ")
		.append(currentScenarioName)
		.append("Totals = [")
		.append(DOUBLE_QUOTE)
		.append(totalExecuted)
		.append(DOUBLE_QUOTE)
		.append(COMMA)
		.append(DOUBLE_QUOTE)
		.append(totalTCPassed)
		.append(DOUBLE_QUOTE)
		.append(COMMA)
		.append(DOUBLE_QUOTE)
		.append(totalTCFailed)
		.append(DOUBLE_QUOTE)
		.append(COMMA)
		.append(DOUBLE_QUOTE)
		.append(execTime)
		.append(DOUBLE_QUOTE)
		.append("];");	
		
		//add failed scenario details if any
		//scenarioDetails.append(getFailedScenarioDetails());
		
		//Need to write to file
		writeScenarioDetails();
		logScenarioTestCaseImports(currentScenarioName+".js");
		//Need to write scenario tc imports file
		writeScenarioTestCaseImports();
		//write failed scenarios
		writeFailedScenarioDetails();
	}

	public void createTestCaseHeader(String testCaseFileName, String testCase,
			String date) {
		
		logger.fine("testCaseFileName==>"+testCaseFileName);
		logger.fine("testCase==>"+testCase);
		String pattern = Pattern.quote(String.valueOf(File.separatorChar));
		String[] fileNameParts =  testCaseFileName.split(pattern);
		testCaseNameWithSequence = fileNameParts[fileNameParts.length - 2];
		testCaseNameWithSequenceUnderScore = testCaseNameWithSequence.replaceAll("-", UNDERSCORE);
		testCaseDetails = new StringBuilder(2000);
		testCaseDetails.append("var ")
					   .append(getTestCaseIterationsString("Iterations"))		
					   .append(" = [];\n");
	}

	public void logTestStepDetails(int stepNum, String stepName,
			String stepDesc, String status, String testCaseFileName,
			String executionTime, String image) {
		
		if(stepNum != 1 && !newIteration){
			testCaseDetails.append(",\n");
		}
		newIteration = false;
		StringBuilder sbTc = new StringBuilder(200);
		testCaseDetails.append("[");
					sbTc.append(DOUBLE_QUOTE)
					   .append(stepNum)
					   .append(DOUBLE_QUOTE)
					   .append(COMMA)
					   .append(DOUBLE_QUOTE)
					   .append(stepName)
					   .append(DOUBLE_QUOTE)
					   .append(COMMA)
					   .append(DOUBLE_QUOTE)
					   .append(stepDesc)
					   .append(DOUBLE_QUOTE)
					   .append(COMMA)
					   .append(DOUBLE_QUOTE)
					   .append(status)
					   .append(DOUBLE_QUOTE)	
					   .append(COMMA)
					   .append(DOUBLE_QUOTE)
					   .append(executionTime)
					   .append(DOUBLE_QUOTE);
		
		if(image != null){
			String pattern = Pattern.quote("..");
			String img = image.replaceFirst(pattern, scenarioFolderName);
			sbTc.append(COMMA)
			   .append("gblContext+")
			   .append(DOUBLE_QUOTE)			   
			   .append(img)
			   .append(DOUBLE_QUOTE);			
		}
		testCaseDetails.append(sbTc.toString());
		testCaseDetails.append("]");
		if(status.equalsIgnoreCase("FAIL")){
			addFailedScenarioDetail(sbTc.toString());
		}
	}

	public void logTestCaseIterationDetails(String testCaseFileName,
			int currentIteration, int startIteration) {
		
		if(currentIteration != startIteration){
			testCaseDetails.append("\n];\n");
		}
		
		testCaseDetails.append(getTestCaseIterationsString("Iterations"))		
		   			   .append(".push(")
		   			   .append(currentIteration)
					   .append(");\n");
							   
		newIteration = true;		
		
		testCaseDetails.append("var ")
			   .append(getTestCaseIterationsString(String.valueOf(currentIteration)))
			   .append(" = [\n");
		
	}

	public void logTestCaseSummary(String testCaseFileName, int totalSteps,
			int passed, int failed, String executionTime) {
		
		testCaseDetails.append("\n];\n\n");
		
		testCaseDetails.append("var ")
		   	.append(getTestCaseIterationsString("Totals"))
		   	.append(" = [")
			.append(DOUBLE_QUOTE)
			.append(totalSteps)
			.append(DOUBLE_QUOTE)
			.append(COMMA)
			.append(DOUBLE_QUOTE)
			.append(passed)
			.append(DOUBLE_QUOTE)
			.append(COMMA)
			.append(DOUBLE_QUOTE)
			.append(failed)
			.append(DOUBLE_QUOTE)
			.append(COMMA)
			.append(DOUBLE_QUOTE)
			.append(executionTime)
			.append(DOUBLE_QUOTE)					
		   .append("];");
		
		writeTestCaseDetails();
	}

	
	public void createRegressionSummaryReport(String regSumFileName,
			List<List<String>> regressionScenarios, String executionTime) {
		// write regression summary file		
		regSumDetails.append("var regSummaryDetails = [\n")
		.append(getRegSummaryDetails(regressionScenarios,executionTime));
		
		prepareArtifactsMap(regressionScenarios);
		
		verifyAndAssignRegSumFolder(regSumFileName);		
		// write all imports file
		writeAllImports();
	}
	
	public void prepareArtifactsMap(List<List<String>> regressionScenarios){
		StringBuilder sb = new StringBuilder(300);
		sb.append("\n\nvar artifactsMap = {\n");
		boolean addComma = false;
		for(List<String> regScenario: regressionScenarios){
			if(addComma){
				sb.append(",\n");
			}
			addComma = true;
			sb.append(regScenario.get(0));
			sb.append(":");
			sb.append(DOUBLE_QUOTE);
			sb.append(regScenario.get(6));
			sb.append(DOUBLE_QUOTE);	
		}
		sb.append("\n};\n\n");		
		regSumDetails.append(sb.toString());
	}
	
	public void createScenarioAssitanceMap(RegressionScenario[] regScenarios, String regFolder){
		StringBuilder sb = new StringBuilder(300);
		sb.append("\n\nvar ScenarioAssistanceTypeMap = {\n");
		boolean addComma = false;
		for(RegressionScenario scenario : regScenarios){
			if(addComma){
				sb.append(",\n");
			}
			addComma = true;
			sb.append(scenario.getName());
			sb.append(": ");
			sb.append(DOUBLE_QUOTE);
			sb.append(scenario.getDescription());
			sb.append(DOUBLE_QUOTE);
		}
		sb.append("\n};\n\n");		
		regSumDetails.append(sb.toString());
		
		createScenarioInformationMap(regScenarios);
	}
	
	public void createScenarioInformationMap(RegressionScenario[] regScenarios){
		
		Map<String, HashMap> indexMap = KeywordUtilities.getScenarioInformation(KeywordUtilities.getScenarioInformationPath());
		Map<String, String> infoMap=indexMap.get("info");
		Map<String, String> coaMap=indexMap.get("toa");
		StringBuilder sb = new StringBuilder();
		StringBuilder sbcoa = new StringBuilder();
		sb.append("\n\nvar descMap = {\n");
		sbcoa.append("\n\nvar coaMap = {\n");
		boolean addComma = false;
		String info=null;
		for(RegressionScenario scenario : regScenarios){
			if(addComma){
				sb.append(",\n");
				sbcoa.append(",\n");
			}
			addComma = true;
			
			sb.append(scenario.getName());
			sb.append(": ");
			sb.append(DOUBLE_QUOTE);
			info=infoMap.get(scenario.getName());
			if(info!=null){
				sb.append(info);
			}else{
				sb.append("Information Not Available");
			}
			
			sb.append(DOUBLE_QUOTE);
			
			
			sbcoa.append(scenario.getName());
			sbcoa.append(": ");
			sbcoa.append(DOUBLE_QUOTE);
			info=coaMap.get(scenario.getName());
			if(info!=null){
				sbcoa.append(info);
			}else{
				sbcoa.append("Information Not Available");
			}
			
			sbcoa.append(DOUBLE_QUOTE);
		}
		sb.append("\n};\n\n");
		sbcoa.append("\n};\n\n");
		regSumDetails.append(sb.toString());
		regSumDetails.append(sbcoa.toString());
	}
	
	public void updateBuildInfo(String buildDate, String beginTime, String endTime, String totalDuration, String environment){
		
		String projectInfo = getProjectInfo(environment);
		String buildInfo = getBuildInfo(buildDate, beginTime,endTime,totalDuration);
		StringBuilder sb = new StringBuilder(1000);
		sb.append("var buildInfo = {")
		.append(projectInfo)
		.append(COMMA)
		.append(buildInfo)
		.append("}\n");
		
		regSumDetails.append(sb.toString());				
		
		writeDetails("RegressionSummary.js",regSumFolder,regSumDetails.toString());
		
		regSumDetails = new StringBuilder(2500);
		
		if(environment != null && environment.equalsIgnoreCase("LOCAL")){
			createContext();
			try {
				createSummaryPlaceHolder();
			} catch (IOException e) {				
				logger.log(Level.INFO,"Error while creating summary place holder", e);
			}			
		}
		
		ReportData.SnapShotMap.put("projectInfo", projectInfo.replaceAll(",", "<br>"));
		ReportData.SnapShotMap.put("buildDate", buildDate);
		ReportData.SnapShotMap.put("beginTime", beginTime);
		ReportData.SnapShotMap.put("endTime", endTime);
		ReportData.SnapShotMap.put("totalDuration", totalDuration);
	}
	
	private String getFailedScenarioDetails(){
		if(failedScenarioDetails.toString().isEmpty()){
			return "";
		}else{
			StringBuilder sb = new StringBuilder(1200);
			sb.append("\nvar "+currentScenarioName+"Failed = [");
			sb.append(failedScenarioDetails.toString().replaceFirst(",", ""));
			sb.append("\n];\n\n");
			return sb.toString();
		}
	}
	
	private void addFailedScenarioDetail(String tcDetails){
		failedScenarioDetails.append(",\n[")
							 .append(DOUBLE_QUOTE)
							 .append(currentScenarioName)
							 .append(DOUBLE_QUOTE)
							 .append(",")
							 .append(DOUBLE_QUOTE)
							 .append(testCaseNameWithSequence.split("-TC")[0])
							 .append(DOUBLE_QUOTE)
							 .append(",")
							 .append(tcDetails)
							 .append("]");
	}
	
	private void createContext(){
		//get the current context
		String pattern = Pattern.quote(String.valueOf(File.separatorChar));
		String[] parts = regSumFolder.split(pattern);
		String context = parts[parts.length - 1];
		
		//get results folder
		pattern = Pattern.quote(String.valueOf(File.separatorChar) + context);
		String resultsFolder = regSumFolder.replaceFirst(pattern, "");
		
		//create content
		StringBuffer sb = new StringBuffer(150);
		sb.append("var lclRpt = ")
		.append(DOUBLE_QUOTE)
		.append(context)
		.append(DOUBLE_QUOTE)
		.append(";");
		
		//Create the file
		String namePath = resultsFolder + File.separatorChar+"context.js";
		writeToFile(namePath,sb.toString(),false);
	}
	
	private void createSummaryPlaceHolder() throws IOException{
		String root = KeywordUtilities.getRootPath()+ PrjConstants.appendPath;
		String summaryPath = "file:///"+root + File.separatorChar + "Dashboard"+ File.separatorChar+ "Dashboard.htm";
		String content = getPlaceHolderContent(summaryPath);
		writeDetails("RegressionSummary.htm",regSumFolder,content);
	}
	
	private String getPlaceHolderContent(String summaryPath){
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head>\n")
		.append("<meta http-equiv=\"refresh\" content=\"0; url=")
		.append(summaryPath)
		.append("\" />")
		.append("\n</head></html>");
		return sb.toString();
	}
	
	private String getProjectInfo(String environment){
		StringBuilder sb = new StringBuilder(300);
		sb.append("Project:")
		.append(DOUBLE_QUOTE)
		.append(getProject())
		.append(DOUBLE_QUOTE)
		.append(COMMA)
		.append("Release:")
		.append(DOUBLE_QUOTE)
		.append(getRelease())
		.append(DOUBLE_QUOTE)
		.append(COMMA)	
		.append("Environment:")
		.append(DOUBLE_QUOTE)
		.append(environment)
		.append(DOUBLE_QUOTE);	
		return sb.toString();
	}
	
	private String getBuildInfo(String buildDate, String beginTime, String endTime, String totalDuration){
		StringBuilder sb = new StringBuilder(350);
		sb.append("builddate:")
		.append(DOUBLE_QUOTE)
		.append(buildDate)
		.append(DOUBLE_QUOTE)
		.append(COMMA)		
		.append("starttime:")
		.append(DOUBLE_QUOTE)
		.append(beginTime)
		.append(DOUBLE_QUOTE)
		.append(COMMA)		
		.append("endtime:")
		.append(DOUBLE_QUOTE)
		.append(endTime)
		.append(DOUBLE_QUOTE)	
		.append(COMMA)		
		.append("duration:")
		.append(DOUBLE_QUOTE)
		.append(totalDuration)
		.append(DOUBLE_QUOTE);
		return sb.toString();
	}
	
	private String getRegSummaryDetails(List<List<String>> regressionScenarios, String executionTime){
		StringBuilder regSumDetails = new StringBuilder(1000);
		allImports = new StringBuilder(1000);
		boolean addComma = false;
		int failedScenariosCount = 0;
		
		addSummaryAssitanceMapToImports();
		
		for(List<String> regScenario: regressionScenarios){
			StringBuilder sb = new StringBuilder();
			if(addComma){
				sb.append(",\n");
			}
			addComma = true;
			sb.append("[")
			.append(DOUBLE_QUOTE)
			.append(regScenario.get(0))
			.append(DOUBLE_QUOTE)
			.append(COMMA)
			.append(DOUBLE_QUOTE)
			.append(regScenario.get(2))
			.append(DOUBLE_QUOTE)
			.append(COMMA)
			.append(DOUBLE_QUOTE)
			.append(regScenario.get(3))
			.append(DOUBLE_QUOTE)
			.append(COMMA)
			.append(DOUBLE_QUOTE)
			.append(regScenario.get(4))
			.append(DOUBLE_QUOTE)
			.append(COMMA)
			.append(DOUBLE_QUOTE)
			.append(regScenario.get(5))
			.append(DOUBLE_QUOTE)
			.append("]");
			
			if(!regScenario.get(4).equalsIgnoreCase("0")){
				failedScenariosCount++;
				allImports.append("?1")
				.append(regScenario.get(0))
				.append("-Failed.js")
				.append("?2")
				.append("\n");				
			}
			
			
			regSumDetails.append(sb.toString());
		}
		regSumDetails.append("\n");
		regSumDetails.append("];\n\n");
		
		String totals = getRegSummaryTotals(regressionScenarios.size(), failedScenariosCount, executionTime);
		
		ReportData.SnapShotMap.put("totalScn", regressionScenarios.size());
		ReportData.SnapShotMap.put("passedScn", regressionScenarios.size() - failedScenariosCount);
		ReportData.SnapShotMap.put("failScn", failedScenariosCount);
		regSumDetails.append(totals);
		
		return regSumDetails.toString();
	}
	
	private void addSummaryAssitanceMapToImports(){
		allImports.append("?1")
		.append("RegressionSummary.js")
		.append("?2")
		.append("\n");
		
		//This is not required for now
		/*
		allImports.append("?1")
		.append("ScenarioAssistanceTypeMap.js")
		.append("?2")
		.append("\n");	
		*/
	}
	
	private String getRegSummaryTotals(int totalScenarios, int totalFailed, String executionTime){
		StringBuilder sb = new StringBuilder();
		sb.append("var regSummaryTotals =[")
		.append(DOUBLE_QUOTE)
		.append(totalScenarios)
		.append(DOUBLE_QUOTE)
		.append(COMMA)
		.append(DOUBLE_QUOTE)
		.append(totalScenarios-totalFailed)
		.append(DOUBLE_QUOTE)
		.append(COMMA)
		.append(DOUBLE_QUOTE)
		.append(totalFailed)
		.append(DOUBLE_QUOTE)
		.append(COMMA)	
		.append(DOUBLE_QUOTE)
		.append(executionTime)
		.append(DOUBLE_QUOTE)		
		.append("];");
		
		return sb.toString();
	}
	
	private void verifyAndAssignRegSumFolder(String regSumFileName){
		if(regSumFolder == null){
			String pattern = Pattern.quote(String.valueOf(File.separatorChar)+"RegressionSummary.htm");
			regSumFolder = regSumFileName.replaceFirst(pattern, "");			
		}
	}
	
	private void assignRegSumFolder(){
		if(regSumFolder == null){
			String pattern = Pattern.quote(String.valueOf(File.separatorChar)+scenarioFolderName);
			regSumFolder = scenarioFolder.replaceFirst(pattern, "");
		}
	}
	
	private String getTestCaseIterationsString(String itString){
		StringBuffer sb = new StringBuffer();
		sb.append(currentScenarioName)	
		   .append(UNDERSCORE)	
		   .append(testCaseNameWithSequenceUnderScore)	
		   .append(UNDERSCORE)	
		   .append(itString);
		return sb.toString();
	}
	
	private void writeScenarioDetails(){
		writeDetails(currentScenarioName+".js",scenarioFolder,scenarioDetails.toString());
	}
	
	private void writeScenarioTestCaseImports(){
		String content = getScenarioTestCaseImports();
		writeDetails(currentScenarioName+"-Imports.js",regSumFolder,content);
	}
	
	private void writeFailedScenarioDetails(){
		String content = getFailedScenarioDetails();
		if(content.length() > 0){
			writeDetails(currentScenarioName+"-Failed.js",regSumFolder,content);
		}
	}	
	
	private String getScenarioTestCaseImports(){
		StringBuilder sb = new StringBuilder();
		String imports = getCovertedImports();
		//sb.append(imports);
		
		sb.append("document.write(\n")
		.append(imports)
		.append(DOUBLE_QUOTE)
		.append(DOUBLE_QUOTE)
		.append("\n);");
		
		return sb.toString();
	}
	
	private String getCovertedImports(){
		String imports = scenarioImports.toString();
		String pattern = Pattern.quote("?1");
		imports = imports.replaceAll(pattern, "\"<script src='\"+gblContext+\"");
		//imports = imports.replaceAll(pattern, "loadJScriptFile(gblContext+\"");
		pattern = Pattern.quote("?2");
		imports = imports.replaceAll(pattern, "'></script>\"+");
		//imports = imports.replaceAll(pattern, "\");");
		return imports;
	}
	
	private void writeTestCaseDetails(){
		String tcName = testCaseNameWithSequence+".js";
		logScenarioTestCaseImports(tcName);
		writeDetails(tcName,scenarioFolder,testCaseDetails.toString());		
	}
	
	private void logScenarioTestCaseImports(String testCaseName){
		scenarioImports.append("?1")
		.append(scenarioFolderName)
		.append("/")
		.append(testCaseName)
		.append("?2")
		.append("\n");
	}
	
	private void writeAllImports(){
		StringBuilder content = new StringBuilder(1000);
		content.append("document.write(\n");
		content.append(getAllImportsDetails());
		content.append(DOUBLE_QUOTE);
		content.append(DOUBLE_QUOTE);
		content.append("\n);");
		writeDetails("All-Imports.js",regSumFolder, content.toString());
	}
	
	private String getAllImportsDetails(){
		String imports = allImports.toString();
		String pattern = Pattern.quote("?1");
		imports = imports.replaceAll(pattern, "\"<script src='\"+gblContext+\"");
		pattern = Pattern.quote("?2");
		imports = imports.replaceAll(pattern, "'></script>\"+");
		return imports;
	}
	
	private void writeDetails(String fileName, String folder, String content){
		String namePath = folder + File.separatorChar+fileName;
		writeToFile(namePath, content,true);
	}
	
	private void writeToFile(String fileNamePath, String content, boolean append){
		try {
			FileWriter bw = new FileWriter(fileNamePath,append);
			bw.write(content);
			bw.flush();
			bw.close();
		} catch (IOException e) {			
			logger.log(Level.WARNING, "Error while creating file-->"+fileNamePath, e);
		}		
	}


}
