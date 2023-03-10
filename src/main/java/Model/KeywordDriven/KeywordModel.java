package Model.KeywordDriven;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.WebElement;

import KeywordDriverUtil.KeywordLibrary;

public class KeywordModel {
	public final Logger logger = Logger.getLogger(KeywordLibrary.class.getName());
	public	String methodName = "";
	public	String keyword = "";
	public	String objectName = "";
	public	String objectID = "";
	public	String dataValue = "";
	public	String passScreenshot = "";
	public	String onPassLog = "";
	public	String onFailLog = "";
	public	String jumpIndex = "";
	public	String checkBoolean = "";
	public	String stepIndex = "";
	public	String stepStatus = "";
	public	String resultsPath = "";
	public	String dateNTime = "";
	public	String ScenarioNameTostore = "" ;
	public	String SKIP_STEP = "n/a";
	public	String formName = "";
	public	String APpageTitle = "";
	public	String PrevScreenName = "HomePage";
	public	WebElement dynaElement = null;
	public	List<WebElement> dynamicObjects = null;
	public	ArrayList<String> ScenarioList = null;
	public	int totalScenarioCount = 0;
	public	int resultSetCounter = 0; 			
	public	int dynaElementIndex = 0;
	public	int ddlIndex = 0;
	public	boolean nextData = false;
	public boolean error=false;
	public String inputXPath;
	public String optionXPath;	
	public String testCase="";
	public String scenario="";	
	public String testCaseFileName="";
	public int startIteration=1;
	public int endIteration=1;
	public int currentIteration=1;
	public boolean displayError;
	public String ScreenName;
	public String dataPath;
	public String testCaseActual;
	public String homePath;
	public String testCaseDesc;
	public String scenarioDesc;
	public String path;
	public String browser;
	public String caseNumber;
	public String individualID;
	public String applicationNumber;
	public String TTDate;
	public boolean elementPresence = false;
	public int resultSize; 
	public String[] edmResult ;
	public String sessionid = "";
	public Connection con;
	public Statement st;
	public String Query = "";
	public String DBTableLabel[];
	public String DBTableData[];
	public int DBExcelTableSize=0;
	public int numberOfRows=0;
	public String parentWindow = "";
	public String mainWindowHandle = null;
	public String textByValue=null;
	public int resultCounter = 1000;
	public int jcount=0;
	public String[] indNameList;
	public String Pageloadstatus;
}
