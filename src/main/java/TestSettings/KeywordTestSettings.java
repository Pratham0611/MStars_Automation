package TestSettings;

import java.util.ArrayList;
import java.util.HashMap;

public class KeywordTestSettings {

    public static boolean UseBrowserfromScenarioSheet = false;
	public static String URL = "";
    public static String ProjectName = "";
    public static String Release = "";
    public static String ENV = "";
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
	public static String homePath;
	public static String regSumFolder;
	public static String TestSuiteFilePath;
	public static String TestSuiteSheetName;
	public static String Parallel_Execution="";
	public static int ThreadCount=1;
	public static String ApplicationCredentialsFileName = "";
    public static String ApplicationCredentialsSheetName = "";
    public static String ApplicationExcelResult = "";
    public static HashMap<String, ArrayList<String>> LoginResult ;
    public static String branch="";  
}
