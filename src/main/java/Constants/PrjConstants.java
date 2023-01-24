package Constants;

import java.util.ArrayList;
import java.util.Arrays;

public class PrjConstants {

	public static final String ConfigFile="\\config.properties";
	public static final String Prereq = "_Prereq";
	
	public static final ArrayList<String> ProgramCodes = new ArrayList<String>(Arrays.asList("KT", "SN", "MA","QP","KC","CC"));
	
	public  enum ProgramType{MA,KTAP,SNAP,CCAP,SS};
	
	public static String InitializeMethod="InitializePage";
	
	public static String appendPath="\\src\\main\\java";
	public static String separatorChar="\\";
	
	//Config
	public static final String KeywordConfigPath = "\\Config\\Config.properties";
	public static final String KeywordErrorsConfigPath = "\\\\Config\\\\Errors.properties";
	public static final String KeywordAPErrorsConfigPath = "\\\\Config\\\\APErrors.properties";
	public static final String KeywordIterationConfigPath = "\\Config\\IterationConfig.properties";
	public static final String KeywordDBQueriesConfigPath = "\\\\Config\\\\DBQueries.properties";
	public static final String KeywordDBRefConfigPath = "\\\\Config\\\\DBRef.properties";
	
	public static final String TEST_CASE_ALIAS = "-TC";
	public static final String  TestSuiteFolder="TestSuite";
	public static final String SCENARIOS = "Scenarios";
	public static final String TEST_SUITE = "TestSuite";
	public static final String TEST_DATA = "TestDataSheet";
	

	
	
}
