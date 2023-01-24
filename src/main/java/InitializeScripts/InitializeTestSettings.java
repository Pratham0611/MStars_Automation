package InitializeScripts;

import java.io.File;
import java.util.Properties;

import org.apache.log4j.Logger;

import Constants.PrjConstants;
import Constants.ReportContants;
import TestSettings.TestRunSettings;
import Utilities.Util;

public class InitializeTestSettings
{
	
    private static final Logger logger =Logger.getLogger(InitializeTestSettings.class.getName());


    Properties prop=new Properties();

	Util util = new Util();
	
	public void LoadConfigData(String PrjPath) throws Exception
	{
		try
		{
			
		 prop=util.loadProperties(PrjPath+PrjConstants.ConfigFile);
			TestRunSettings.HomePath = PrjPath;
			

			TestRunSettings.URL = prop.getProperty("URL");
			TestRunSettings.ProjectName = prop.getProperty("ProjectName");
			TestRunSettings.Release = prop.getProperty("Release");
			TestRunSettings.Environment = prop.getProperty("Environment").toUpperCase().trim();
			TestRunSettings.TestRunName = prop.getProperty("TestRunName");
			TestRunSettings.ExecutedBy = prop.getProperty("ExecutedBy");
			TestRunSettings.Browser = prop.getProperty("Browser");
			if (prop.getProperty("CustomBrowserLocation").equalsIgnoreCase("Yes"))
			{
				TestRunSettings.BrowserLocation = prop.getProperty("BrowserLocation");
			}
			else
			{
				TestRunSettings.BrowserLocation = TestRunSettings.HomePath + "\\Drivers";

			}

			if (prop.getProperty("MaximizedMode").equalsIgnoreCase("Yes"))
			{
				TestRunSettings.MaximizedMode = true;
			}
			if (prop.getProperty("SnapshotForAllPass").equalsIgnoreCase("Yes"))
			{
				TestRunSettings.isSnapshotForAllPass = true;
			}
			else
			{
				TestRunSettings.isSnapshotForAllPass = false;
			}

			if (prop.getProperty("FullPageScreenshot").equalsIgnoreCase("Yes"))
			{
				TestRunSettings.isFullPageScreenshot = true;
			}
			else
			{
				TestRunSettings.isFullPageScreenshot = false;
			}

			TestRunSettings.PageLoadTimeout = Integer.parseInt(prop.getProperty("PageLoadTimeout").toString());
			TestRunSettings.ElementTimeout = Integer.parseInt(prop.getProperty("ElementTimeout").toString());
			TestRunSettings.ElementTimeoutLongWait = Integer.parseInt(prop.getProperty("ElementTimeoutLongWait").toString());



			//Artifacts Path
			TestRunSettings.ArtifactsPath = setArtifactsPath(prop.getProperty("SetCustomArtifactsPath"), prop.getProperty("ArtifactsPath"));
			TestRunSettings.TestDataPath = TestRunSettings.ArtifactsPath + prop.getProperty("TestDataMappingLocation");
			TestRunSettings.TestDataMappingFileName = TestRunSettings.TestDataPath + prop.getProperty("TestDataMappingFileName");
			TestRunSettings.TestDataMappingSheetName_WP = prop.getProperty("TestDataMappingSheetName_WP");
			TestRunSettings.TestDataMappingSheetName_SSP = prop.getProperty("TestDataMappingSheetName_SSP");
			TestRunSettings.ApplicationCredentialsFileName = TestRunSettings.TestDataPath + prop.getProperty("ApplicationCredentialsFileName");
			TestRunSettings.ApplicationCredentialsSheetName = prop.getProperty("ApplicationCredentialsSheetName");

			//Config Path
			TestRunSettings.ConfigLocation =TestRunSettings.HomePath + prop.getProperty("ConfigLocation");

			
			//Set Results Location
			TestRunSettings.ResultsPath = getResultsPath();


			//Parallel Execution Setup
			if (prop.getProperty("ParallelExecution").equalsIgnoreCase("Yes"))
			{
				TestRunSettings.isParallelExecution = true;
				TestRunSettings.ParallelNodesCount = Integer.parseInt(prop.getProperty("ParallelNodes").toString());
				TestRunSettings.ParallelExecutionConfig = prop.getProperty("ParallelExecutionConfig");
				TestRunSettings.ParallelNodeSheetAssociation = prop.getProperty("ParallelNodeSheetAssociation");

			}



			//Setup Driver Config
			TestRunSettings.LoadDriverOptions = loadDriverDetails();
			TestRunSettings.ChromeConfig = prop.getProperty("ChromeConfig");
			TestRunSettings.FireFoxConfig = prop.getProperty("FireFoxConfig");
			TestRunSettings.IEConfig = prop.getProperty("IEConfig");
			TestRunSettings.EdgeConfig = prop.getProperty("EdgeConfig");
			TestRunSettings.OperaConfig = prop.getProperty("OperaConfig");
			TestRunSettings.CloudConfig = prop.getProperty("CloudConfig");
			TestRunSettings.AndroidConfig = prop.getProperty("AndroidConfig");
			TestRunSettings.IOSConfig = prop.getProperty("IOSConfig");



			//Browser Location in OS
			TestRunSettings.FireFoxLocation = prop.getProperty("FireFoxLocation");
			TestRunSettings.IEDriverLocation = TestRunSettings.HomePath + prop.getProperty("IEDriverLocation");
			TestRunSettings.OperaLocation = TestRunSettings.HomePath + prop.getProperty("IEDriverLocation");



			//Initialize API Settings

			TestRunSettings.InterfaceTestCaseSheet = TestRunSettings.ArtifactsPath + prop.getProperty("InterfaceTestCaseSheet");
			TestRunSettings.UrlRepositorySheet = TestRunSettings.ArtifactsPath + prop.getProperty("URLRepositorySheet");
			TestRunSettings.MockRepositorySheet = TestRunSettings.ArtifactsPath + prop.getProperty("MockRepositorySheet");
			TestRunSettings.HeaderRepository = TestRunSettings.ArtifactsPath + prop.getProperty("HeaderRepository");
			TestRunSettings.ResponseSheetPath = TestRunSettings.ArtifactsPath + prop.getProperty("ResponseSheetPath");

			TestRunSettings.CertificateLocation = TestRunSettings.ArtifactsPath + prop.getProperty("CertificateLocation");
			TestRunSettings.RequestLocation = TestRunSettings.ArtifactsPath + prop.getProperty("RequestLocation");
			TestRunSettings.ResponseLocation = TestRunSettings.ArtifactsPath + prop.getProperty("ResponseLocation");
			TestRunSettings.TestDataLocation_WP = TestRunSettings.ArtifactsPath + prop.getProperty("TestDataLocation_WP");
			TestRunSettings.TestDataLocation_SSP = TestRunSettings.ArtifactsPath + prop.getProperty("TestDataLocation_SSP");
			TestRunSettings.InterfaceSheetDetails = prop.getProperty("InterfaceSheetDetails");
//			TestRunSettings.DBConnectionString = prop.getProperty("DBConnectionString");

			TestRunSettings.ExcelSheetExtension = prop.getProperty("ExcelSheetExtension");
			TestRunSettings.XMLExtension = prop.getProperty("XMLExtension");
			TestRunSettings.JSONExtension = prop.getProperty("JSONExtension");
			TestRunSettings.CommonMockSheetName = prop.getProperty("CommonMockSheetName");
			TestRunSettings.UseCommonMockSheet = prop.getProperty("UseCommonMockSheet");
			TestRunSettings.MockTemplateLocation = TestRunSettings.ArtifactsPath + prop.getProperty("MockTemplateLocation");

			TestRunSettings.HeaderRepositorySheetName = prop.getProperty("HeaderRepositorySheetName");
			TestRunSettings.MockSheetName = GetMockSheetName(TestRunSettings.UseCommonMockSheet, TestRunSettings.CommonMockSheetName, TestRunSettings.Environment);
			TestRunSettings.AddReportToDB = AddReportToDB(prop.getProperty("AddReportToDataBase"));
			TestRunSettings.DefaultServiceTimeout = Integer.parseInt(prop.getProperty("DefaultServiceTimeout"));

			

			TestRunSettings.RequestFolderPath = prop.getProperty("RequestFolderName");
			TestRunSettings.ResponseFolderPath = prop.getProperty("ResponseFolderName");

			//Excel Reports
			TestRunSettings.GenerateExcelReport = GenerateExcelFile(prop.getProperty("GenerateExcelReports"));
			TestRunSettings.ExcelFileName = prop.getProperty("ExcelReportFileName");
			TestRunSettings.ExcelFilepath=TestRunSettings.ResultsFolderPath+"\\"+ TestRunSettings.ExcelFileName + TestRunSettings.ExcelSheetExtension;
			
			//DB Validations
			
			TestRunSettings.DBValidationFileName = TestRunSettings.ArtifactsPath+ prop.getProperty("DBValidationFileName");
			TestRunSettings.DBValidationSheetName =prop.getProperty("DBValidationSheetName");
			TestRunSettings.DBConnectionStrings =TestRunSettings.ConfigLocation+prop.getProperty("DBConnectionStrings");
			TestRunSettings.StaticDataFileName = TestRunSettings.ArtifactsPath+ prop.getProperty("StaticDataFileName");
			TestRunSettings.StaticDataSheetName=prop.getProperty("StaticDataSheetName");

			//API Settings
			
			TestRunSettings.APIConfigFileName =prop.getProperty("APIConfig");
		//	InitializeAPISettings initializeAPITestSettings= new InitializeAPISettings();
			String APIConfigLocation=TestRunSettings.ConfigLocation +TestRunSettings.APIConfigFileName;
		//	APITestSettings.apiTestSettings = initializeAPITestSettings.InitializeInterfaceSettings(TestRunSettings.HomePath,TestRunSettings.ArtifactsPath,APIConfigLocation,TestRunSettings.Environment);

			
			//ExcelDriven
			TestRunSettings.TestScenarioFilePath=TestRunSettings.ArtifactsPath+ prop.getProperty("TestScenarioFilePath");
			TestRunSettings.TestScenarioFileName=TestRunSettings.TestScenarioFilePath+prop.getProperty("TestScenarioFileName");
			TestRunSettings.TestScenarioSheetName=prop.getProperty("TestScenarioSheetName");
			TestRunSettings.TestCasesFilePath=TestRunSettings.ArtifactsPath+ prop.getProperty("TestCasesFilePath");
			TestRunSettings.ParallelThreadCount=Integer.parseInt(prop.getProperty("ParallelThreadCount"));
			
			
			ReportContants reportConstants = new ReportContants(TestRunSettings.ResultsPath,TestRunSettings.isSnapshotForAllPass,TestRunSettings.isFullPageScreenshot); 
			 

			
		}
		catch (Exception e)
		{
			logger.info("Failed to Initialize the Test Run Settings");
			System.out.println("Failed to Initialize the Test Run Settings");
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
			logger.info(e.getMessage());
			logger.info(e.getStackTrace());
			throw new Exception("Failed to Initialize the Test Run Settings");

		}

	}




	/// <summary>
	/// This method will return a booleanean value if the database config is set to YES
	/// Author: Jigesh Shah
	/// </summary>
	/// <param name="AddReportToDB">AddReportToDB Flag</param>
	/// <returns>true if  database config is set to YES</returns>
	boolean AddReportToDB(String AddReportToDB)
	{

		if (AddReportToDB.toUpperCase().trim().equals("YES"))
		{
			return true;
		}
		else
			return false;
	}

	/// <summary>
	/// This method will return true if the Excel Report Generation is set to 'YES' in config file
	///Author: Jigesh Shah
	/// </summary>
	/// <param name="strGenerateExcelReport">Yes/No based on the Excel Report Generation</param>
	/// <returns>true if Value is set to 'YES'</returns>
	boolean GenerateExcelFile(String strGenerateExcelReport)
	{

		if (strGenerateExcelReport.toUpperCase().trim().equals("YES"))
		{
			return true;
		}
		else
			return false;
	}

	/// <summary>
	/// This method will return true if the Excel Report Generation is set to 'YES' in config file
	/// Author : Jigesh Shah
	/// </summary>
	/// <param name="strEmailGeneration">YES/NO</param>
	/// <returns>true if Value is set to 'YES'</returns>
	boolean SendEmail(String strEmailGeneration)
	{

		if (strEmailGeneration.toUpperCase().trim().equals("YES"))
		{
			return true;
		}
		else
			return false;
	}


	/// <summary>
	/// This method will return true if the Attached Files for Passed Test Cases is set to 'YES' in config file
	/// Author: Jigesh Shah
	/// </summary>
	/// <param name="AttachFilePassedCases">Yes/No</param>
	/// <returns>true if Value is set to 'YES'</returns>
	boolean AttachFileForPassedTestCases(String AttachFilePassedCases)
	{

		if (AttachFilePassedCases.toUpperCase().trim().equals("YES"))
		{
			return true;
		}
		else
			return false;
	}

	/// <summary>
	/// This method will return true if External Mock Files needs to be placed on Mock Servers
	/// Author: Jigesh Shah
	/// </summary>
	/// <param name="UseCommonMockSheet">Yes/No</param>
	/// <param name="CommonMockSheetName">Mock Sheet Name</param>
	/// <param name="Environment">Environment Name</param>
	/// <returns></returns>
	String GetMockSheetName(String UseCommonMockSheet, String CommonMockSheetName, String Environment)
	{

		if (UseCommonMockSheet.toUpperCase().trim() == "YES")
		{
			return CommonMockSheetName;
		}
		else
		{
			return Environment;
		}
	}

	/// <summary>
	/// This method will set the Directory path for the Artifacts Folder
	/// Author: Jigesh Shah
	/// </summary>
	/// <param name="SetCustomPath">Yes/No</param>
	/// <param name="CustomPath">Set Custom Path if the value is set to 'Yes' in the SetCustomPath Argument </param>
	/// <returns>Sets and returns the Directory path for the Artifacts Folder </returns>
	public String setArtifactsPath(String SetCustomPath, String CustomPath) throws Exception
	{
		try
		{
			if (SetCustomPath.toUpperCase().trim() == "YES")
			{
				return prop.getProperty("ArtifactsPath");
			}
			else
			{
				return TestRunSettings.HomePath + prop.getProperty("DefaultArtifactLocation");
			}

		}
		catch (Exception e)
		{
			logger.info("Failed to Initialize the Artifacts Path");
			System.out.println("Failed to Initialize the Artifacts Path");
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
			logger.info(e.getMessage());
			logger.info(e.getStackTrace());
			throw new Exception("Failed to Initialize the Artifacts Path");

		}
	}

	/// <summary>
	/// This method will return true/false if Driver Config needs to be loaded
	/// </summary>
	/// <returns></returns>
	public boolean loadDriverDetails() throws Exception
	{
		try
		{
			if (prop.getProperty("LoadDriverOptions").toUpperCase().trim() == "YES")
			{
				return true;
			}
			else
			{
				return false;
			}

		}
		catch (Exception e)
		{
			logger.info("Failed to Initialize the Driver Load Details");
			System.out.println("Failed to Initialize the  Driver Load Details");
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
			logger.info(e.getMessage());
			logger.info(e.getStackTrace());
			throw new Exception("Failed to Initialize the  Driver Load Details");

		}
	}

	/// <summary>
	/// This mmethod will return the Directory Path where the results files will be stored
	/// Author: Jigesh Shah
	/// </summary>
	/// <returns>Results Directory Path</returns>
	public String getResultsPath() throws Exception
	{
		try
		{
			if (prop.getProperty("SetCustomResultLocation").toUpperCase().trim() == "YES")
			{
				File dir = new File(prop.getProperty("CustomResultLocation"));
	            if (!dir.exists()) dir.mkdirs();
				return prop.getProperty("CustomResultLocation");
			}
			else
			{
				String ResultFolder= TestRunSettings.HomePath + "\\" + prop.getProperty("DefaultResultLocation") + "\\"+  Util.getCurrentDate() + "\\Run_" +  Util.getCurrentTime();
				File dir = new File(ResultFolder);
	            if (!dir.exists()) dir.mkdirs();

				return ResultFolder;
			}

		}
		catch (Exception e)
		{
			logger.info("Failed to Initialize the Results Path");
			System.out.println("Failed to Initialize the Results Path");
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
			logger.info(e.getMessage());
			logger.info(e.getStackTrace());
			throw new Exception("Failed to Initialize the Results Path");

		}
	}


	public void setHomePath(String currHomePath)
	{
		TestRunSettings.HomePath = currHomePath;
	}
	public void setResultFolderPath(String strResultsFolderPath)
	{
		TestRunSettings.ResultsFolderPath = strResultsFolderPath;
	}



}
