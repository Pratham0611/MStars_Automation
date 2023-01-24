package KeywordDriverUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import Common.ReportCommon;
import ExcelReport.ExcelReportGenerator;
import InitializeScripts.InitializeTestSettings;
import Model.ExtentModel.TestRunDetails;
import TestSettings.TestRunSettings;


public class KeywordTestNG {

	static long beginTime =0;	
	static String rootPath ="";	

	
	@BeforeSuite
    public static void TestRunSetUp() throws Exception
    {
		KeywordDriver keywordDriver= new KeywordDriver();	
		keywordDriver.PreMethod();
	}

	@Parameters({ "ScenarioName1" })
	@Test
	public void ExecuteTest1(String ScenarioName) throws IOException
	{
		KeywordDriver keywordDriver= new KeywordDriver();
		keywordDriver.KeyWordScenario(ScenarioName);
	}
	
	@Parameters({ "ScenarioName2" })
	@Test
	public void ExecuteTest2(String ScenarioName) throws IOException
	{
		KeywordDriver keywordDriver= new KeywordDriver();
		keywordDriver.KeyWordScenario(ScenarioName);
		
	}
	
	@Parameters({ "ScenarioName3" })
	@Test
	public void ExecuteTest3(String ScenarioName) throws IOException
	{
		KeywordDriver keywordDriver= new KeywordDriver();
		keywordDriver.KeyWordScenario(ScenarioName);
		
	}

	@AfterSuite
    public static void TestRunTearDown() throws Exception
    {
		KeywordDriver keywordDriver= new KeywordDriver();
		
		keywordDriver.postMethod();
	
    }

}
