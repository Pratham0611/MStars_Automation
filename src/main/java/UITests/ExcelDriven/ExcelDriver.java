package UITests.ExcelDriven;

import java.util.ArrayList;
import java.util.HashMap;

import Common.POI_ReadExcel;
import Model.ExcelDriven.ExcelTestCaseModel;
import TestSettings.TestRunSettings;

public class ExcelDriver {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//PreLoad Data
//		ExcelPrePost excelPrePost= new ExcelPrePost();
//		excelPrePost.ExecutePreSuiteFlows();
//		
//		ArrayList<ExcelTestCaseModel> ExcelTestCases = new ArrayList<ExcelTestCaseModel>();
//		ExcelDriver excelDriver= new ExcelDriver();
//		ExcelTestCases=excelDriver.GetAllScenarios();
//
//		
//		if(ExcelTestCases.isEmpty()==false)
//		{
//			if(ExcelTestCases.size()>0)
//			{
//				for(int i=0;i<ExcelTestCases.size();i++)
//				{
//					ExcelTCExecution excelTCExecution= new ExcelTCExecution();
//					excelTCExecution.ExecuteTestCase(ExcelTestCases.get(i));
//				}
//			}
//		}
		
		
		
	}

	
	public ArrayList<ExcelTestCaseModel> GetAllScenarios()
	{
		
		ArrayList<ExcelTestCaseModel> ExcelTestCases = new ArrayList<ExcelTestCaseModel>();
		POI_ReadExcel poiObject = new POI_ReadExcel();
		ArrayList<String> whereClause = new ArrayList<String>();
		whereClause.add("Execute::Yes");

		HashMap<String, ArrayList<String>> result = poiObject.fetchWithCondition(TestRunSettings.TestScenarioFileName, TestRunSettings.TestScenarioSheetName, whereClause);
		
		if(result.isEmpty()==false)
		{
			if(result.get("TestCaseName").size()>0)
			{
				
				for(int i=0;i<result.get("TestCaseName").size();i++)
				{
					String TestCaseID=result.get("TestCaseID").get(i);
					String TestCaseName=result.get("TestCaseName").get(i);
					String Module=result.get("Module").get(i);
					String Browser=result.get("Browser").get(i);
					String TestCaseDescription=result.get("TestCaseDescription").get(i);
					String TestSheetName=result.get("TestSheetName").get(i);
					int StartIndexofIteration=Integer.parseInt(result.get("StartIndexofIteration").get(i));
					int NoOfIterations=Integer.parseInt(result.get("NoOfIterations").get(i));
					
					
					ExcelTestCaseModel excelTestCaseModel= new ExcelTestCaseModel();
					excelTestCaseModel=excelTestCaseModel.
							AddExcelTestScenarioModelData(TestCaseID, TestCaseName, Module, Browser, 
									TestCaseDescription, TestSheetName,StartIndexofIteration, NoOfIterations);
					
					ExcelTestCases.add(excelTestCaseModel);
				}
			}
		
		}
		
		return ExcelTestCases;

	}
}
