package Model.ExcelDriven;

public class ExcelTestStepModel {

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


public ExcelTestStepModel AddExcelTestCaseModelData(
		String BusinessComponentName,String TransactionName,String BusinessModule,String TestStepDescription,String ExpectedResults,	
		String Parameter1,String Parameter2,String Parameter3,String Parameter4,String Parameter5)
{
	
	ExcelTestStepModel excelTestStepModel= new ExcelTestStepModel();
	excelTestStepModel.BusinessComponentName=BusinessComponentName;
	excelTestStepModel.TransactionName=TransactionName;
	excelTestStepModel.BusinessModule=BusinessModule;
	excelTestStepModel.TestStepDescription= TestStepDescription;	
	excelTestStepModel.ExpectedResults=ExpectedResults;
	excelTestStepModel.Parameter1=Parameter1;
	excelTestStepModel.Parameter2=Parameter2;
	excelTestStepModel.Parameter3=Parameter3;
	excelTestStepModel.Parameter4=Parameter4;
	excelTestStepModel.Parameter5=Parameter5;
	
	return excelTestStepModel;
}
}
