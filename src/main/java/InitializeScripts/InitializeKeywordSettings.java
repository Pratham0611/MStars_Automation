package InitializeScripts;

import java.io.File;
import java.util.Properties;

import org.apache.log4j.Logger;

import Constants.PrjConstants;
import Constants.ReportContants;
import TestSettings.KeywordTestSettings;
import TestSettings.TestRunSettings;
import Utilities.Util;

public class InitializeKeywordSettings
{
	
    private static final Logger logger =Logger.getLogger(InitializeKeywordSettings.class.getName());


    Properties prop=new Properties();
    Util util = new Util();
	
	public void LoadConfigData(String PrjPath) throws Exception
	{
		try
		{
			
		 prop=util.loadProperties(PrjPath+PrjConstants.KeywordConfigPath);
		 KeywordTestSettings.homePath = PrjPath;
			

			KeywordTestSettings.TestSuiteFilePath= PrjPath + File.separatorChar + PrjConstants.TEST_SUITE
					+ File.separatorChar+prop.getProperty("TestSuiteFileName");
			KeywordTestSettings.ENV= prop.getProperty("Env");
			KeywordTestSettings.branch= prop.getProperty("branch");
			KeywordTestSettings.TestSuiteSheetName= prop.getProperty("TestSuiteSheetName");
			KeywordTestSettings.Parallel_Execution= prop.getProperty("Parallel_Execution");
			KeywordTestSettings.ThreadCount=Integer.parseInt(prop.getProperty("ThreadCount"));
			KeywordTestSettings.Browser= prop.getProperty("Browser");
			KeywordTestSettings.ApplicationCredentialsFileName = PrjPath + prop.getProperty("DefaultArtifactLocation")+prop.getProperty("ApplicationCredentialsFileName");
			KeywordTestSettings.ApplicationCredentialsSheetName = prop.getProperty("ApplicationCredentialsSheetName");			
			
			if(prop.getProperty("UseBrowserfromScenarioSheet").equalsIgnoreCase("Yes")) {
				KeywordTestSettings.UseBrowserfromScenarioSheet=true;
			}
			else {
				KeywordTestSettings.UseBrowserfromScenarioSheet=false;
			}
		}
		catch (Exception e)
		{
			logger.info("Failed to Initialize the Test Run Settings");
			System.out.println("Failed to Initialize the Test Run Settings");
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
			logger.info(e.getMessage());
			logger.info(e.getStackTrace());
			throw new Exception("Failed to Initialize the Keyword Test Settings");

		}

	}




}
