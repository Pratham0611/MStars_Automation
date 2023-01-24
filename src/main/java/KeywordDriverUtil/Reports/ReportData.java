package KeywordDriverUtil.Reports;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class ReportData {

	public static HashMap<String, Integer> TestCasesExecuted = new HashMap<String, Integer>();
	public static String dateNTime="";
	public static long totalDuration=0;
	public static long overallDuration=0;
	public static int totalExecutedCount=1;
	public static String onPassLog="";
	public static String onFailLog="";
	public static String stepStatus="";
	public static String passScreenshot="";
	public static int currentIteration=1;
	public static int startIteration=1;
	public static long duration;
	public static HashMap<String, Object> SnapShotMap = new HashMap<String, Object>();
	public static String currentScenarioName="";
	public static enum Status  {PASS,FAIL,DONE,pass};
	public static String homePath = "";
	public static String summaryHTML = "";
	public static String testCaseHTML = "";
	public static String scenarioHTML = "";
	public static String scenario = "";
	public static String scenarioDesc = "";
	public static String testCaseDesc = "";
	public static String testcase = "";
	public static String screenshotPassPath = "";
	public static String screenshotFailPath = "";
	public static Date beginTime = null;
	public static List<List<String>> regressionScenarios = new ArrayList<List<String>>();
	public static int testCaseStepNo = 1;
	public static int pass=0;
	public static int fail=0;
	public static int scenarioPass=0;
	public static int scenarioFail=0;
	public static int passInc=1;
	public static int failInc=1;
	public static int totalpassTC = 0;
	public static int totalfailTC = 0;
	public static long regSummaryTotalDuration = 0;
	public static HashMap<String, Integer> TestCasesPassed = new HashMap<String, Integer>();
	public static HashMap<String, Integer> TestCasesFailed = new HashMap<String, Integer>();
	public static LinkedHashSet<String> scenarios= new LinkedHashSet<String>();
	public static Map<String,Map<String,String>> testCaseData= new HashMap<String,Map<String,String>>();
	public static String scenarioName ="";
	public static Map<String,String> testCase;
	public static String matrixSummaryHtml="";
	public static List<String> scenarioDefectList = new ArrayList<String>();
	public static Map<Integer, List<Map<String, String>>> testcaseDefectList = new HashMap<Integer, List<Map<String, String>>>();
	public static String defectLogFolder="";
	public static String path="";
	public static String dbPath="";	
	public static String resultsPath="";

}
