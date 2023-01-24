package KeywordDriverUtil.Reports;

import java.util.List;

import com.deloitte.framework.objects.RegressionScenario;

public interface ReportGenerator {

	//CreateSummaryFile
	public void createScenarioSummaryHeader(String scenarioName,String summaryFileName);
	
	//AddRowToSummary
	public  void addRowToScenarioSummary(String summaryFileName,String testCase,int testCasesExecuted,int passed, int failed, String execTime,String shref);
	
	//CloseSummary
	public  void closeScenarioSummary(String summaryFileName,int totalExecuted, int totalTCPassed, int totalTCFailed,String execTime);
	
	
	
	//CreateTestCaseHTML
	public void createTestCaseHeader(String testCaseFileName,String testCase, String date);
	
	//log
	public void logTestStepDetails(int stepNum,String stepName, String stepDesc, String status, String testCaseFileName, String executionTime, String image);
	
	//WriteIterationToHTML
	public void logTestCaseIterationDetails(String testCaseFileName,int currentIteration, int startIteration);
	
	//closeTestCaseHTML
	public void logTestCaseSummary(String testCaseFileName,int totalSteps,int passed,int failed,String executionTime);
	
	//createRegressionSummaryReport
	public  void createRegressionSummaryReport(String regSumFileName,List<List<String>> regressionScenarios, String executionTime);
	
	public  void createScenarioAssitanceMap(RegressionScenario[] regScenarios, String regSumFolder);
	
	public void updateBuildInfo(String buildDate, String beginTime, String endTime, String totalDuration, String environment);

}
