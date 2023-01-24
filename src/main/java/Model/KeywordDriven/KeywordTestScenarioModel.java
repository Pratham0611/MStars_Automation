package Model.KeywordDriven;

import java.io.File;

import Constants.PrjConstants;
import TestSettings.KeywordTestSettings;

public class KeywordTestScenarioModel {


	public String TestScenarioName="";
	public String TestScenarioType="";
	public String TestScenarioDescription="";
	public String Browser="";
	public int StartIndexofIteration=1;
	public int NoOfIterations=1;
	public String TestScenarioPath="";

	
	public KeywordTestScenarioModel AddKeywordTestScenarioModelData(String TestScenarioType,String TestScenarioName,
	String TestScenarioDescription,	String Browser,	int StartIndexofIteration,	int NoOfIterations ) {
	
		KeywordTestScenarioModel keywordTestScenarioModel= new  KeywordTestScenarioModel();
	
		keywordTestScenarioModel.TestScenarioName=TestScenarioName;
		keywordTestScenarioModel.TestScenarioType=TestScenarioType;
		keywordTestScenarioModel.TestScenarioDescription=TestScenarioDescription;
		keywordTestScenarioModel.Browser=Browser;
		keywordTestScenarioModel.StartIndexofIteration=StartIndexofIteration;
		keywordTestScenarioModel.NoOfIterations=NoOfIterations;
		keywordTestScenarioModel.TestScenarioPath=getScenarioPath(TestScenarioType, TestScenarioName);
		return keywordTestScenarioModel;
	}
	
	public  String getScenarioNamePath(String scenarioType, String scenarioName){
		StringBuilder namePath = new StringBuilder();
		namePath.append(KeywordTestSettings.ENV);
		namePath.append(File.separatorChar);
		namePath.append(PrjConstants.SCENARIOS);
		namePath.append(File.separatorChar);
		namePath.append(scenarioType);
		namePath.append(File.separatorChar);		
		namePath.append(scenarioName);		

		return namePath.toString();
	}	
	
	public  String getScenarioPath(String scenarioType, String scenarioName){
		StringBuilder namePath = new StringBuilder();
		namePath.append(KeywordTestSettings.branch);
		namePath.append(File.separatorChar);
		namePath.append(PrjConstants.SCENARIOS);
		namePath.append(File.separatorChar);
		namePath.append(scenarioType);
		namePath.append(File.separatorChar);		
		namePath.append(scenarioName);		

		return namePath.toString();
	}	
		

}
