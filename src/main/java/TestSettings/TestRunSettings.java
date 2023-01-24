package TestSettings;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class TestRunSettings
{

    private static final Logger logger =Logger.getLogger(TestRunSettings.class.getName());

	
    public static String HomePath = "";

    //TestInitialization
    public static boolean isTestInitialized = false;
    public static boolean isParallelExecution = false;
    public static Integer ParallelNodesCount = 1;

    //Environment Settings
    public static String URL = "";
    public static String ProjectName = "";
    public static String Release = "";
    public static String Environment = "";
    public static String TestRunName = "";
    public static String ExecutedBy = "";
    public static String Browser = "";
    public static String BrowserLocation = "";
    public static boolean MaximizedMode = false;
    public static boolean isSnapshotForAllPass = false;
    public static boolean isFullPageScreenshot = false;
    public static Integer PageLoadTimeout = 60;
    public static Integer ElementTimeout = 60;
    public static Integer ElementTimeoutLongWait = 120;
    public static String appDate = "";
    public static boolean ClearCache = false;


    //Artifacts Location Setup
    public static String ArtifactsPath = "";
    public static String TestDataPath = "";
    public static String TestDataMappingFileName = "";
    public static String TestDataMappingSheetName_SSP = "";
    public static String TestDataMappingSheetName_WP = "";
    public static String ApplicationCredentialsFileName = "";
    public static String ApplicationCredentialsSheetName = "";
    public static String TestDataLocation_WP = "\\WP";
    public static String TestDataLocation_SSP = "\\SSP";

    //Result  Location Setup
    public static String ResultsFolderPath = "";

    //ParallelConfig
    public static String ParallelConfigPath = "";
    public static String ParallelExecutionConfig = "";
    public static String ParallelNodeSheetAssociation = "";

   
    //Driver Config
    public static boolean LoadDriverOptions = false;
    public static String ChromeConfig = "";
    public static String FireFoxConfig = "";
    public static String IEConfig = "";
    public static String EdgeConfig = "";
    public static String OperaConfig = "";
    public static String CloudConfig = "";
    public static String AndroidConfig = "";
    public static String IOSConfig = "";


    //Location for Browser in OS
    public static String FireFoxLocation = "";
    public static String IEDriverLocation = "";
    public static String OperaLocation = "";



    //InterfaceTestRunSettings

    public static String DefaultTestDataFormat = ".xlsx";
    public static String InterfaceTestCaseSheet = "";
    public static String UrlRepositorySheet = "";
    public static String RequestLocation = "";
    public static String ResponseLocation = "";
    public static String CertificateLocation = "";

   // public static String DBConnectionString = "";
    public static boolean AddReportToDB = false;

    public static long TestRunId = 1;
    public static String MockRepositorySheet;
    public static String InterfaceSheetDetails;
    public static String ExcelSheetExtension;
    public static String XMLExtension;
    public static String JSONExtension;
    public static String CommonMockSheetName;
    public static String UseCommonMockSheet;
    public static String MockSheetName;
    public static String HeaderRepositorySheetName;
    public static String HeaderRepository;
    public static String ResponseSheetPath;
    public static Integer DefaultServiceTimeout;
    public static String ResultsPath;
    public static String RequestFolderPath;
    public static String ResponseFolderPath;
    public static String ExcelFileName;
    public static boolean GenerateExcelReport;
    public static String MockTemplateLocation;
    public static String DomainName;


    //Capabilities Config
    public static HashMap<String, HashMap<String, String>> DictCapabilities = new HashMap<String, HashMap<String, String>>();
    public static boolean isDriverOptionsEnabled = false;


    //WebDrivers
    public static WebDriver driver;
    public static HashMap<Integer, WebDriver> DictWebDriver = new HashMap<Integer, WebDriver>();

    //AndroidDriver
    public static AndroidDriver<AndroidElement> ADriver;
    public static HashMap<Integer, AndroidDriver<AndroidElement>> DictADriver = new HashMap<Integer, AndroidDriver<AndroidElement>>();


    //IOSDriver
    public static IOSDriver<IOSElement> IDriver;
    public static HashMap<Integer, IOSDriver<IOSElement>> DictIDriver = new HashMap<Integer, IOSDriver<IOSElement>>();


    public static HashMap<String, HashMap<String,String>> MasterInterfaceSheet = new HashMap<String, HashMap<String, String>>();


	public static String ExcelFilepath;


	//DB Validations
	public static String DBValidationFileName;
	public static String DBValidationSheetName;
	public static String DBConnectionStrings;
	public static  String StaticDataFileName = "";

	public static String StaticDataSheetName="";

	//ConfigLocation
	public static String ConfigLocation;


	//API Config
	public static String APIConfigFileName;

//	ExcelDriven TestCases
	public static String TestScenarioFilePath;
	public static String TestScenarioFileName;
	public static String TestScenarioSheetName;
	public static String TestCasesFilePath;
	public static int ParallelThreadCount;







}
