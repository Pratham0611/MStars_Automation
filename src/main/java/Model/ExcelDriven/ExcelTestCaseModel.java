package Model.ExcelDriven;

public class ExcelTestCaseModel {

	public String TestCaseID="";
	public String TestCaseName="";
	public String Module="";
	public String Browser="";
	public String TestSheetName="";
	public String TestCaseDescription="";
	public int StartIndexofIteration=1;
	public int NoOfIterations=1;

	
	public ExcelTestCaseModel AddExcelTestScenarioModelData(String TestCaseID,String TestCaseName,
	String Module,	String Browser,	String TestCaseDescription,String TestSheetName,	int StartIndexofIteration,	int NoOfIterations ) {
	
		ExcelTestCaseModel excelTestCaseModel= new  ExcelTestCaseModel();
		
		excelTestCaseModel.TestCaseID=TestCaseID;
		excelTestCaseModel.TestCaseName=TestCaseName;
		excelTestCaseModel.Module=Module;
		excelTestCaseModel.Browser=Browser;
		excelTestCaseModel.TestCaseDescription=TestCaseDescription;
		excelTestCaseModel.TestSheetName=TestSheetName;
		excelTestCaseModel.StartIndexofIteration=StartIndexofIteration;
		excelTestCaseModel.NoOfIterations=NoOfIterations;
	
		return excelTestCaseModel;
	}
	
}
