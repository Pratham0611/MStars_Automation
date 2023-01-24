package reports.extentReports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.model.ExceptionInfo;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import Constants.UIPerfConstants;
import Model.UIPerfModel;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class UIPerfExtentReport
{

	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;

	public static HashMap<Integer, ExtentTest> DictExtentTestScenario = new HashMap<Integer, ExtentTest>();
	public static HashMap<Integer, String> DictExtentTestCase = new HashMap<Integer, String>();





	public void CreateExtentReport_Category(String ReportPath, String TestName, String BrowserName,String Environment, String ExecutedBy) throws Exception
	{
		
		try
		{

			ReportPath = ReportPath + "\\UIPerformance.html"; 
			

		ExtentHtmlReporter htmlReporter_lcl = new ExtentHtmlReporter(ReportPath);
		ExtentReports extent_lcl = new ExtentReports();
		try
		{
			extent_lcl.attachReporter(htmlReporter_lcl);
		
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
			throw e;	
		}
		extent_lcl.setSystemInfo("Host Name", TestName);
		extent_lcl.setSystemInfo("Environment", Environment);
		extent_lcl.setSystemInfo("Username", ExecutedBy);

		
		if (UIPerfConstants.UIPerfData != null)
		{

			
			for (String  ScreenName : UIPerfConstants.UIPerfData.keySet())
			{

				ExtentTest test = extent_lcl.createTest(ScreenName);

				test.assignCategory(BrowserName);

				ArrayList<UIPerfModel> uiPerfModels=UIPerfConstants.UIPerfData.get(ScreenName);
				
				
				for(UIPerfModel uiPerfModel:uiPerfModels)
				{
					ExtentTest MultiTCNode=test.createNode("Test Case : "+ uiPerfModel.TestCaseName + "  :: ModuleName : " + uiPerfModel.ModuleName + " :: BrowserName : " + uiPerfModel.BrowserName);	
					
					if(uiPerfModel.ResponseTimeMillisecond<=UIPerfConstants.ExpectedResponseTimeinMilliSecond)
					{
						MultiTCNode.pass(MarkupHelper.createLabel( " SourceScreen : " + uiPerfModel.SourceScreen+ "<br>  "
								+ " DestinationScreen : " + uiPerfModel.DestinationScreen+ "<br>  "
								+ " Source_DestinationScreen : " + uiPerfModel.Source_DestinationScreen+ "<br>  "
								+ " ResponseTime : " + uiPerfModel.ResponseTime+ "<br>  "
								+ " ResponseTimeMillisecond : " + uiPerfModel.ResponseTimeMillisecond+ "<br>  "
								+ " TestCaseName : " + uiPerfModel.TestCaseName+ "<br>  "
								+ " ModuleName : " + uiPerfModel.ModuleName+ "<br>  "
								+ " BrowserName : " + uiPerfModel.BrowserName+ "<br>  "
								+ " StartTime : " + uiPerfModel.StartTime+ "<br>  "
								+ " EndTime : " + uiPerfModel.EndTime+ "<br>  "
								+ "NavigationTimeDetails : " + uiPerfModel.NavigationTimeDetails +"<br>"
								, ExtentColor.GREEN));
					}
					else
					{
						MultiTCNode.fail(MarkupHelper.createLabel( " SourceScreen : " + uiPerfModel.SourceScreen+ "<br>  "
										+ " DestinationScreen : " + uiPerfModel.DestinationScreen+ "<br>  "
										+ " Source_DestinationScreen : " + uiPerfModel.Source_DestinationScreen+ "<br>  "
										+ " ResponseTime : " + uiPerfModel.ResponseTime+ "<br>  "
										+ " ResponseTimeMillisecond : " + uiPerfModel.ResponseTimeMillisecond+ "<br>  "
										+ " TestCaseName : " + uiPerfModel.TestCaseName+ "<br>  "
										+ " ModuleName : " + uiPerfModel.ModuleName+ "<br>  "
										+ " BrowserName : " + uiPerfModel.BrowserName+ "<br>  "
										+ " StartTime : " + uiPerfModel.StartTime+ "<br>  "
										+ " EndTime : " + uiPerfModel.EndTime+ "<br>  "
										+ "NavigationTimeDetails : " + uiPerfModel.NavigationTimeDetails +"<br>"
								, ExtentColor.RED));
					}

				}
				
			}
			
		}

		extent_lcl.flush();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
			throw e;	
		}
	}

	



}
