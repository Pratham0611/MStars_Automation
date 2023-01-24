package KeywordDriverUtil.Reports;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.deloitte.framework.objects.RegressionScenario;

public class HtmlReportGenerator extends AbstractReportGenerator{

	private static final Logger logger = Logger.getLogger(HtmlReportGenerator.class.getName());
	/**
	 * 
	 */
	public HtmlReportGenerator() {
		super();
	}
	
	// Report 3 - Test Steps Report - M1
	// Instantiated By  hariom sinha 557173
	
	
	public void createTestCaseHeader(String testCaseFileName,String testCase, String date){

			String text = "";
			text += "<!DOCTYPE html PUBLIC "+"\""+"-//W3C//DTD XHTML 1.0 Transitional//EN"+"\" \""+ "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"+"\">\n";
			text += "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n";
			text += "<html><head><title>Test Execution Summary</title>\n";
			text += "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\""+ "/>\n";
			text += "<style type=\"text/css\" media=\"screen\""+">\n";
			text += "@import \"../../../SupportLibraries/filtergrid.css\""+";\n";
			text += "</style>\n";
			text += "<script language=\"javascript\" type=\"text/javascript\" src=\"../../../SupportLibraries/tablefilter.js\"></script>\n";
			
			text += "</head><body><center><font size=4><b>Test Case Execution Results</b><br>" +
					"<br><center><table border=0 width=1000><tr></table>"+
					"<table border=1 width=1000><tr><td bgcolor=cyan align = left width = 500><font color = black size=3><b>"+"Project:</b> "+getProject()+"</td>" +
					"<td bgcolor=cyan align = right width = 500><font color = black size=3><b>Release:</b> "+getRelease()+"</td></tr></table>" +
					"<table border=1 width=1000><tr bgcolor=white><font size=4 color=black><b>"+
					"<table border=1 width=1000><tr><td bgcolor=cyan align = left width = 800><font color = black size=4><b>"+"Test Case: "+testCase+"</b></td>" +
					"<td bgcolor=cyan align = right width = 200><font color = black size=4><b>"+date+"</b></td></tr></table>" +
					"<br><table border=1 width=1000><tr bgcolor=white><font size=4 face=candara color=black><b>";
       
			text += "<tr><td bgcolor=#8B4513 width=80><b><font size =2 color=white face = verdana><center>Step No</td>" +
					"<td bgcolor=#8B4513 width=800><b><font size =2 color=white face = verdana><center>Step Name</td>" +
       		"<td bgcolor=#8B4513 width=580><b><font size =2 color=white face = verdana><center>Description</td>" +
       		"<td bgcolor=#8B4513 width=60><b><font size =2 color=white face = verdana><center>Step Status</td>" +
       		"<td bgcolor=#8B4513 width=120><b><font size =2 color=white face = verdana><center>Time of Execution</td><tr>";
                         	
			writeDetails(testCaseFileName, text, true);
	}
	
	// Report 3 - Test Steps Report - M2
	// Instantiated By  hariom sinha 557173
	
	public void logTestStepDetails(int stepNum,String stepName, String stepDesc, String status, String testCaseFileName, String executionTime, String image){
		
		String text = "<tr><td bgcolor=#F8F8FF width=100><font size =1 color=black face = verdana><center>"+stepNum+"</td>" + 
				"<td word-wrap: break-word; bgcolor=#F8F8FF width=300><font size =1 color=black face = verdana><center>"+stepName+"</td>"+
				"<td bgcolor=#F8F8FF width=500><font size =2 color=black face = verdana><center>"+stepDesc+"</td>";
		String color = "blue";
		if(status.equalsIgnoreCase("PASS")){
			color="blue";
		}else if(status.equalsIgnoreCase("FAIL")){
			color="red"; 
		}
		if(image == null){			
			text = text+"<td bgcolor=#F8F8FF width=100><font size =2 color="+color+" face = verdana><b><center>"+status+"</td>";
		}else{
			text = text+"<td bgcolor=#F8F8FF width=100><b><center><a href='"+image+"'><font size =2 color="+color+" face = verdana>"+status+"</a></td>";
		}
		
		text = text+"<td bgcolor=#F8F8FF width=200><font size =1 color=black face = verdana><center>"+executionTime+"</td>";
		
		writeDetails(testCaseFileName, text);
		
	}
	
	private void writeDetails(String testCaseFileName, String txtDetails, boolean checkFile){
		if(checkFile){
			File f = new File(testCaseFileName);			
			if(!f.exists()){
				try {
					f.createNewFile();
				} catch (Exception e) {				
					e.printStackTrace();
				}
			}			
		}
		writeDetails(testCaseFileName,txtDetails);
	}
	
	private void writeDetails(String testCaseFileName, String txtDetails){
	
		logger.fine("testCaseFileName-->"+testCaseFileName);		
		try {
			FileWriter bw = new FileWriter(testCaseFileName,true);
			bw.write(txtDetails);
			bw.flush();
			bw.close();
		} catch (IOException e) {			
			e.printStackTrace();
		}		
		
	}

	// Report 2 - Test Scenario Report - M1
	//Instantiated By hariom sinha 557173
	
	public void createScenarioSummaryHeader(String scenarioName,
			String summaryFileName) {
		
		System.out.println("Test Scenario Report - M1"); 
		
		String text = "<html><head><title>Execution Summary</title></head><body><center><font size=5><b>Test Scenario Execution Summary</b><br>" +
				"<font size=4><div style=\"float:right;margin-right:165px;\"><b>Execution Performed By:</b> AUT</div><br><br>" +
				"<table border=1 width=1000><tr><td bgcolor=cyan align = left width = 500><font color = black size=3><b>"+"Project:</b> "+getProject()+"</td>" +
				"<td bgcolor=cyan align = right width = 500><font color = black size=3><b>Release: </b>"+getRelease()+"</td></tr></table>" +
				"<table border=1 width=1000><tr bgcolor=white><font size=4 color=black><b>"+
				"<table border=1 width=1000><tr><td bgcolor=cyan align = left width = 800><font color = black size=4><b>"+"Scenario: "+scenarioName+"</b></td>" +
				"<td bgcolor=cyan align = right width = 200><font color = black size=4><b></b></td></tr></table>" +					
		 		"<table border=0 width=1000><tr></table><br><table border=1 width=1000><tr bgcolor=black><td colspan=5><font size=5 face=candara color=black><b>";
   
		text += "<tr><td bgcolor=#8B4513 width=350><b><font size =2 color=white face = verdana><center>Business Module</td>" +
   		"<td bgcolor=#8B4513 width=500><b><font color=white size =2 face = verdana><center>Total # of Steps Executed</td>" +
   		"<td bgcolor=#8B4513 width=500><b><font color=white size =2 face = verdana><center># of Steps Passed</td>" +
   		"<td bgcolor=#8B4513 width=500><b><font color=white size =2 face = verdana><center># of Steps Failed</td>" +
   		"<td bgcolor=#8B4513 width=200><b><font color=white size =2 face = verdana><center>Execution Time</td><tr>";
  		
		writeDetails(summaryFileName, text, true);
	}

	//Report 2 - Test Scenario Report - M2
	//Instantiated By hariom sinha 557173
	
	public void addRowToScenarioSummary(String summaryFileName,
			String testCase, int testCasesExecuted, int passed, int failed,
			String execTime, String shref) {
		
		String text = "<tr><td bgcolor=#F8F8FF width=350><font size =2 color=black face = verdana><center>"+"<a target=blank href='"+shref+"'>"+testCase+"</a></td>" +
				"<td bgcolor=#F8F8FF width=500><font size =2 color=black face = verdana><center>"+testCasesExecuted+"</td>" +
				"<td bgcolor=#F8F8FF width=500><font size =2 color=black face = verdana><center>"+passed+"</td>" +
				"<td bgcolor=#F8F8FF width=500><font size =2 color=black face = verdana><center>"+failed+"</td>" +
				"<td bgcolor=#F8F8FF width=200><font size =2 color=black face = verdana><center>"+execTime+"</td>";
		
		writeDetails(summaryFileName, text);
	}

	// Report 2 - Test Scenario Report - M3
	//Instantiated By hariom sinha 557173
	
	public void closeScenarioSummary(String summaryFileName,int totalExecuted,
			int totalTCPassed, int totalTCFailed, String execTime) {
		
		String text = "</table><br><table border=1 width=1000><tr bgcolor=black><td colspan=4><font size=5 face=candara color=black><b><tr><td bgcolor=#F8F8FF width=350><font size =2 color=black face = verdana><center>Total Test Cases Executed: <b>"+totalExecuted+"</b></td>" +
				"<td bgcolor=#F8F8FF width=500><font size =2 face = verdana font color=green><center>Total Passed: <b>"+totalTCPassed+"</b></td>" +
				"<td bgcolor=#F8F8FF width=500><font size =2 face = verdana font color=red><center>Total Failed: <b>"+totalTCFailed+"</b></td>" +
				"<td bgcolor=#F8F8FF width=200><font size =2 color=black face = verdana><center><b>"+execTime+"</b></td>";
		
		writeDetails(summaryFileName, text);
		
	}
	
	//Report -3  Test Step Report M3
	//Instantiated By hariom sinha 557173
	
	public void logTestCaseIterationDetails(String testCaseFileName,
			int currentIteration, int startIteration) {
		
		String brk = (currentIteration != startIteration)?"<br>":"";
		
		String text = "</table>"+brk+"<center><table border=1 width=1000><tr bgcolor=white><font size=4 face=verdana color=black><b>" +
				"<tr><td bgcolor=#F8F8FF width=1000><font size=3 face = verdana font color=black><center>Iteration: <b>"+currentIteration+"</b></td></tr></table>" +
				"<center><table id=Iteration"+currentIteration+" border=1 width=1000><tr bgcolor=white><font size=4 face=verdana color=black>";
		
		writeDetails(testCaseFileName, text);
	}
	
	//Report -3  Test Step Report M4
	//Instantiated By hariom sinha 557173

	public void logTestCaseSummary(String testCaseFileName, int totalSteps,
			int passed, int failed, String executionTime) {
		
		String text = "</table><br><table border=1 width=1000><tr bgcolor=white><font size=4 face=verdana color=black><b>" +
				"<tr><td bgcolor=cyan width=300><font size=2 face = verdana font color=black><center>Total Steps: <b>"+totalSteps+"</b></td>" +
				"<td bgcolor=cyan width=300><font size=2 face = verdana font color=black><center>Total Steps Passed: <b>"+passed+"</b></td>"+
				"<td bgcolor=red width=300><font size=2 face = verdana font color=black><center>Total Steps Failed: <b>"+failed+"</b></td>"+
				"<td bgcolor=cyan width=300><font size=2 face = verdana font color=black><center>Execution Time: <b>"+executionTime+"</b></td></tr></table>";
		
		writeDetails(testCaseFileName, text);
	}


	public void createRegressionSummaryReport(String regSumFileName,
			List<List<String>> regressionScenarios, String executionTime) {		

		createRegressionSummaryHeader(regSumFileName);
		int failedScenariosCount = 0;
		for(List<String> regScenario : regressionScenarios){
			addRowToRegressionSummary(regSumFileName,regScenario);
			
			if(!regScenario.get(4).equalsIgnoreCase("0")){
				failedScenariosCount++;
			}
		}
		
		closeRegressionSummary(regSumFileName, regressionScenarios.size(), failedScenariosCount, executionTime);		
		
	}
	
	// Report 1 - Overall Summary Report  -  M1
	// Instantiated By hariom sinha  557173
	
	private  void createRegressionSummaryHeader(String regSumFileName){
		
		System.out.println("Overall Report  - 1");
		String text = "<html><head><title>Regression Summary</title></head><body><center><font size=5><b>Overall Exection Summary Report</b><br>" + 
				"<font size=4><div style=\"float:right;margin-right:165px;\"><b>Execution Performed By:</b> AUT</div><br><br>" +
				"<table border=1 width=1000><tr><td bgcolor=cyan align = left width = 500><font color = black size=3><b>"+"Project:</b> "+getProject()+"</td>" +
				"<td bgcolor=cyan align = right width = 500><font color = black size=3><b>Release: </b>"+getRelease()+"</td></tr></table>" +
				"<table border=1 width=1000><tr bgcolor=white><font size=4 color=black><b>"+
		 		"<table border=0 width=1000><tr></table><br><table border=1 width=1000><tr bgcolor=black><td colspan=5><font size=5 face=candara color=black><b>";
   
		text += "<tr><td bgcolor=#8B4513 width=350><b><font size =2 color=white face = verdana><center>Scenario</td>" +
   		"<td bgcolor=#8B4513 width=500><b><font color=white size =2 face = verdana><center>Test Cases Executed</td>" +
   		"<td bgcolor=#8B4513 width=500><b><font color=white size =2 face = verdana><center>Test Cases Passed</td>" +
   		"<td bgcolor=#8B4513 width=500><b><font color=white size =2 face = verdana><center>Test Cases Failed</td>" +
   		"<td bgcolor=#8B4513 width=200><b><font color=white size =2 face = verdana><center>Execution Time</td><tr>";
		
		writeDetails(regSumFileName, text,true);
	}
	
	
	// Report 1 - Overall Summary Report  -  M2
	// Instantiated By hariom sinha 557173
	
	private void addRowToRegressionSummary(String regSumFileName, List<String> regScenario){
		
		System.out.println("Overall Report  - 2");
		
		String pattern = Pattern.quote(String.valueOf(File.separatorChar));
		String[] href = regScenario.get(1).split(pattern);
		String url = "."+File.separatorChar +href[href.length-2]+File.separatorChar +href[href.length-1];		
		
		String text = "<tr><td bgcolor=#F8F8FF width=350><font size =2 color=black face = verdana><center>"+"<a target=blank href='"+url+"'>"+regScenario.get(0)+"</a></td>" +
				"<td bgcolor=#F8F8FF width=500><font size =2 color=black face = verdana><center>"+regScenario.get(2)+"</td>" +
				"<td bgcolor=#F8F8FF width=500><font size =2 color=black face = verdana><center>"+regScenario.get(3)+"</td>" +
				"<td bgcolor=#F8F8FF width=500><font size =2 color=black face = verdana><center>"+regScenario.get(4)+"</td>" +
				"<td bgcolor=#F8F8FF width=200><font size =2 color=black face = verdana><center>"+regScenario.get(5)+"</td>";
		
		writeDetails(regSumFileName, text);
	}
	
	// Report 1 - Overall Summary Report  -  M3
	// Instantiated By hariom sinha 557173
	
	private void closeRegressionSummary(String regSumFileName, int totalScenarios, int totalFailed, String executionTime){
		
		System.out.println("Overall Summary Report - 3 ");
		String text = "</table><br><table border=1 width=1000><tr bgcolor=black><td colspan=4><font size=5 face=candara color=black><b><tr><td bgcolor=cyan width=350><font size =2 color=black face = verdana><center>Total # of scenarios Executed: <b>"+totalScenarios+"</b></td>" +
				"<td bgcolor=cyan width=500><font size =2 face = verdana font color=black><center>Total Scenario Passed: <b>"+(totalScenarios - totalFailed)+"</b></td>" +
				"<td bgcolor=red width=500><font size =2 face = verdana font color=black><center>Total Scenario Failed: <b>"+totalFailed+"</b></td>" +
				"<td bgcolor=cyan width=200><font size =2 color=black face = verdana><center><b>"+executionTime+"</b></td>";
		
		text += "</table><br>";
		writeDetails(regSumFileName, text);
	}


	public void createScenarioAssitanceMap(RegressionScenario[] regScenarios,
			String regSumFolder) {		
		//No implementation is required here for now
	}


	public void updateBuildInfo(String buildDate, String beginTime, String endTime, String totalDuration, String environment) {
		//No implementation is required here for now		
	}


}
