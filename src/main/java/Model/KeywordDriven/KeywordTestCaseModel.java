package Model.KeywordDriven;

public class KeywordTestCaseModel {

public String BusinessComponentName="";
public String TransactionName="";
public String BusinessModule="";
public String TestStepDescription="";	
public String ExpectedResults="";	
public String Parameter1="";
public String Parameter2="";
public String Parameter3="";
public String Parameter4="";
public String Parameter5="";


public KeywordTestCaseModel AddKeywordTestCaseModelData(
		String BusinessComponentName,String TransactionName,String BusinessModule,String TestStepDescription,String ExpectedResults,	
		String Parameter1,String Parameter2,String Parameter3,String Parameter4,String Parameter5)
{
	
	KeywordTestCaseModel keywordTestCaseModel= new KeywordTestCaseModel();
	keywordTestCaseModel.BusinessComponentName=BusinessComponentName;
	keywordTestCaseModel.TransactionName=TransactionName;
	keywordTestCaseModel.BusinessModule=BusinessModule;
	keywordTestCaseModel.TestStepDescription= TestStepDescription;	
	keywordTestCaseModel.ExpectedResults=ExpectedResults;
	keywordTestCaseModel.Parameter1=Parameter1;
	keywordTestCaseModel.Parameter2=Parameter2;
	keywordTestCaseModel.Parameter3=Parameter3;
	keywordTestCaseModel.Parameter4=Parameter4;
	keywordTestCaseModel.Parameter5=Parameter5;
	
	return keywordTestCaseModel;
}
}
