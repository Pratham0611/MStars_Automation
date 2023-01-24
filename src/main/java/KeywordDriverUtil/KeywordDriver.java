package KeywordDriverUtil;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import TestSettings.KeywordTestSettings;
import TestSettings.TestRunSettings;
import reports.extentReports.UIPerfExtentReport;

import com.deloitte.aut.util.AUTUtility;
import com.deloitte.exception.AUTException;
import com.deloitte.framework.objects.RegressionScenario;
import com.deloitte.framework.objects.ReportSerialization;

import Common.POI_ReadExcel;
import Common.ReportCommon;
import Constants.PrjConstants;
import Constants.ReportContants;
import InitializeScripts.InitializeKeywordSettings;
import KeywordDriverUtil.Reports.ReportUtilities;
import Model.TestCaseParam;
import Model.ExcelDriven.ExcelTestCaseModel;
import Model.ExtentModel.ExtentUtilities;
import Model.ExtentModel.TestCaseDetails;
import Model.ExtentModel.TestRunDetails;
import Model.KeywordDriven.KeywordTestScenarioModel;



public class KeywordDriver extends WebDriverHelper{
	private static final Logger logger = Logger.getLogger(KeywordDriver.class.getName());
	static long beginTime =0;	
	static String rootPath ="";	
	KeywordCommon keywordCommon= new KeywordCommon();

	public static void main(String[] args) throws IOException{	
		
		KeywordDriver keywordDriver= new KeywordDriver();
		keywordDriver.PreMethod();
		keywordDriver.KeyWordScenario();
		keywordDriver.postMethod();
	
	}
	

	public void KeyWordScenario() throws IOException
	{

		ArrayList<KeywordTestScenarioModel> KeywordTestScenarios = new ArrayList<KeywordTestScenarioModel>();
		
		KeywordDriver keywordDriver= new KeywordDriver();

		KeywordTestScenarios=keywordDriver.GetAllScenarios(rootPath);

		
		
		if(KeywordTestScenarios.isEmpty()==false)
		{
			if(KeywordTestScenarios.size()>0)
			{
				for(int i=0;i<KeywordTestScenarios.size();i++)
				{
					
					
					ScenarioUtil scenarioUtil= new ScenarioUtil();
					scenarioUtil.executeScenario(KeywordTestScenarios.get(i),rootPath
				, KeywordTestSettings.regSumFolder);
			
				
				
					ReportUtilities.saveRegSummaryReport(KeywordTestScenarios.get(i).TestScenarioType,
					KeywordTestScenarios.get(i).TestScenarioName,
					KeywordTestSettings.regSumFolder);
				}
			}
		}
		
		
		if(KeywordTestScenarios.size() > 0){
		//	ReportUtilities.createRegressionSummaryReport();
		//	Report.createScenarioAssitanceMap(regScenarios,KeywordTestSettings. regSumFolder);
			ReportUtilities.updateBuildInfo(beginTime, "LOCAL");
		}		


	}
	

	public void KeyWordScenario(String ScenarioName) throws IOException
	{

		ArrayList<KeywordTestScenarioModel> KeywordTestScenarios = new ArrayList<KeywordTestScenarioModel>();
		
		KeywordDriver keywordDriver= new KeywordDriver();

		KeywordTestScenarios=keywordDriver.GetScenarioByName(rootPath,ScenarioName);

		
		
		if(KeywordTestScenarios.isEmpty()==false)
		{
			if(KeywordTestScenarios.size()>0)
			{
				for(int i=0;i<KeywordTestScenarios.size();i++)
				{
					
					
					ScenarioUtil scenarioUtil= new ScenarioUtil();
					scenarioUtil.executeScenario(KeywordTestScenarios.get(i),rootPath
				, KeywordTestSettings.regSumFolder);
			
				
				
					ReportUtilities.saveRegSummaryReport(KeywordTestScenarios.get(i).TestScenarioType,
					KeywordTestScenarios.get(i).TestScenarioName,
					KeywordTestSettings.regSumFolder);
				}
			}
		}
		
		
		if(KeywordTestScenarios.size() > 0){
		//	ReportUtilities.createRegressionSummaryReport();
		//	Report.createScenarioAssitanceMap(regScenarios,KeywordTestSettings. regSumFolder);
			ReportUtilities.updateBuildInfo(beginTime, "LOCAL");
		}		


	}

	public static long captureStartTimings()
	{
		long beginTime = System.currentTimeMillis();
		return beginTime;
		
	}

	public String PreMethod() throws IOException
	{
		beginTime=captureStartTimings();
		 rootPath = KeywordUtilities.getRootPath() + PrjConstants.appendPath;
		InitializeKeywordSettings initializeKeywordSettings= new InitializeKeywordSettings();
		try {
			initializeKeywordSettings.LoadConfigData(rootPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		KeywordTestSettings.regSumFolder = ReportUtilities.createRegressionSummaryFolder(rootPath);
		 
		return rootPath;
	}

	public void postMethod() throws IOException
	{
		
		KeywordCommon keywordCommon= new KeywordCommon();
		keywordCommon.createExtentReports(KeywordTestSettings.regSumFolder);
		
		try {
			//ExtentReport.ExtentReport extentReport = new ExtentReport.ExtentReport();
			//extentReport.CreateExtentReport_Model(KeywordTestSettings.regSumFolder,TestRunDetails.getTestCaseRepository(),"Test","Test","Test");
			

	    	UIPerfExtentReport perfextentReport =  new UIPerfExtentReport();
	    	
	    	perfextentReport.CreateExtentReport_Category(KeywordTestSettings.regSumFolder,"Test", KeywordTestSettings.Browser, "Test", "Test"); 
	    	
		}catch (Exception e){
			System.out.println("Unable to generate performance report ");
		}
		System.exit(1);

	}
	
	public ArrayList<KeywordTestScenarioModel> GetAllScenarios(String Rootpath)
	{
		
		ArrayList<KeywordTestScenarioModel> KeywordTestScenarios = new ArrayList<KeywordTestScenarioModel>();
		POI_ReadExcel poiObject = new POI_ReadExcel();
		ArrayList<String> whereClause = new ArrayList<String>();
		whereClause.add("Execute::Yes");
		

		String TestScenarioFileName=Rootpath+File.separatorChar+PrjConstants.TestSuiteFolder+ File.separatorChar+ KeywordTestSettings.TestSuiteFilePath;
		HashMap<String, ArrayList<String>> result = poiObject.fetchWithCondition(KeywordTestSettings.TestSuiteFilePath, KeywordTestSettings.TestSuiteSheetName, whereClause);
		
		if(result.isEmpty()==false)
		{
			if(result.get("Scenario Type").size()>0)
			{
				
				for(int i=0;i<result.get("Scenario Type").size();i++)
				{
					String ScenarioType=result.get("Scenario Type").get(i);
					String ScenarioName=result.get("Scenario Name").get(i);
					String Description=result.get("Description").get(i);
					String Browser="";
					int StartIndexofIteration=1;
					int NoOfIterations=1;
					try
					{
						Browser=result.get("Browser").get(i);
						
					}
					catch(Exception e)
					{
						Browser=KeywordTestSettings.Browser;
					}

					try
					{
					StartIndexofIteration=Integer.parseInt(result.get("StartIndexofIteration").get(i));
					}
					catch(Exception e)
					{
						
					}
					
					try
					{
					NoOfIterations=Integer.parseInt(result.get("NoOfIterations").get(i));
					}
					catch(Exception e)
					{
					
					}
					
					KeywordTestScenarioModel keywordTestScenarioModel= new KeywordTestScenarioModel();
					keywordTestScenarioModel=keywordTestScenarioModel.
							AddKeywordTestScenarioModelData(ScenarioType, ScenarioName, Description, Browser, 
									StartIndexofIteration, NoOfIterations);
					
					KeywordTestScenarios.add(keywordTestScenarioModel);
				}
			}
		
		}
		
		return KeywordTestScenarios;

	}

	
	public ArrayList<KeywordTestScenarioModel> GetScenarioByName(String Rootpath,String Scenario)
	{
		
		ArrayList<KeywordTestScenarioModel> KeywordTestScenarios = new ArrayList<KeywordTestScenarioModel>();
		POI_ReadExcel poiObject = new POI_ReadExcel();
		ArrayList<String> whereClause = new ArrayList<String>();
		whereClause.add("Scenario Name::"+Scenario);
		whereClause.add("Execute::Yes");
		

		String TestScenarioFileName=KeywordTestSettings.TestSuiteFilePath;
		HashMap<String, ArrayList<String>> result = poiObject.fetchWithCondition(KeywordTestSettings.TestSuiteFilePath, KeywordTestSettings.TestSuiteSheetName, whereClause);
		
		if(result.isEmpty()==false)
		{
			if(result.get("Scenario Type").size()>0)
			{
				
				for(int i=0;i<result.get("Scenario Type").size();i++)
				{
					String ScenarioType=result.get("Scenario Type").get(i);
					String ScenarioName=result.get("Scenario Name").get(i);
					String Description=result.get("Description").get(i);
					String Browser="";
					int StartIndexofIteration=1;
					int NoOfIterations=1;
					try
					{
						Browser=result.get("Browser").get(i);
						
					}
					catch(Exception e)
					{
						Browser=KeywordTestSettings.Browser;
					}

					try
					{
					StartIndexofIteration=Integer.parseInt(result.get("StartIndexofIteration").get(i));
					}
					catch(Exception e)
					{
						
					}
					
					try
					{
					NoOfIterations=Integer.parseInt(result.get("NoOfIterations").get(i));
					}
					catch(Exception e)
					{
					
					}
					
					KeywordTestScenarioModel keywordTestScenarioModel= new KeywordTestScenarioModel();
					keywordTestScenarioModel=keywordTestScenarioModel.
							AddKeywordTestScenarioModelData(ScenarioType, ScenarioName, Description, Browser, 
									StartIndexofIteration, NoOfIterations);
					
					KeywordTestScenarios.add(keywordTestScenarioModel);
				}
			}
		
		}
		
		return KeywordTestScenarios;

	}
	

	///class org.openqa.selenium.NoSuchElementException
	



}