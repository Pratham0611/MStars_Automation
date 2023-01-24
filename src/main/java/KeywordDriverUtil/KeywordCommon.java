package KeywordDriverUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

import com.deloitte.framework.objects.RegressionScenario;
import com.deloitte.framework.objects.ReportSerialization;

import KeywordDriverUtil.Reports.ReportGeneratorFactory;
import Model.ExtentModel.TestCaseDetails;
import Model.ExtentModel.TestRunDetails;

public class KeywordCommon {

	private static final Logger logger = Logger.getLogger(KeywordCommon.class.getName());
	
	public  void createExtentReports(String regsumFolder) throws IOException
	{
//		RegressionScenario[] regScenarios = getAllScenarios(Util.getRootPath() + KeywordDriver.appendPath,"Yes", "RegressionScenarios");

		ArrayList<HashMap<UUID, TestCaseDetails>> TestCaseRepository = new ArrayList<HashMap<UUID, TestCaseDetails>>();
//        
//        for( RegressionScenario regScenario : regScenarios)
//        {
//               try
//               {
//               ReportSerialization RS = new ReportSerialization();
//               String FileName = regsumFolder+"\\"+ regScenario.getName() + ".json";
//               System.out.println("Read Json file: "+FileName);
//               UUID uuid = UUID.randomUUID();
//               HashMap<UUID, TestCaseDetails> TCRep= new HashMap<UUID, TestCaseDetails>();
//         TestCaseDetails TCD= RS.SerializeJSONDatafromFile(FileName);
//               TCRep.put(uuid, TCD);
//               TestCaseRepository.add(TCRep);
//               }
//               catch(Exception e)
//               {
//                     
//               }
//        }
//

   //Extent Report
   try
   {
    ExtentReport.ExtentReport extentReport = new ExtentReport.ExtentReport();
extentReport.CreateExtentReport_Model(regsumFolder,TestRunDetails.getTestCaseRepository(),"Test","Test","Test");
}
    catch(Exception e)
    {
           System.out.println("Failed to generate the extent test results");
           System.out.println(e.getMessage());
           System.out.println(e.getStackTrace());
           
    }            

   //ExtentReport By Category
   
   try
   {

    ExtentReport.ExtentReport extentReport = new ExtentReport.ExtentReport();
    
    extentReport.CreateExtentReport_Category(regsumFolder,TestRunDetails.getTestCaseRepository(),"Test","Test","Test");
   }
    catch(Exception e)
    {
           System.out.println("Failed to generate the extent test results");
           System.out.println(e.getMessage());
           System.out.println(e.getStackTrace());
           
    }            


	}

	
	private static void CreateScenarioFolder(String ResultPath,String testCase){
		if(!ReportGeneratorFactory.getInstance().isJqGridReport()){
			File f = new File(ResultPath+"\\"+testCase);
			logger.fine("Scenario folder-->"+ResultPath+"\\"+testCase);
			if(!f.exists()){
				f.mkdir();
			}
		}
	}

}
