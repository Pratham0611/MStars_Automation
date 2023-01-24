//package KeywordDriverUtil;
//
//import java.awt.Point;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.Statement;
//import java.util.concurrent.TimeUnit;
//
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.Keys;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.interactions.Actions;
//import org.openqa.selenium.support.ui.Select;
//
//import KeywordDriverUtil.Reports.ReportUtilities;
//import KeywordDriverUtil.Reports.ReportData.Status;
//import Model.KeywordDriven.KeywordModel;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Properties;
//import java.util.Set;
//
//
//public class ReusableFunctions extends WebDriverHelper{
//	String[] indNameList;
//	KeywordLibrary keywordLib = new KeywordLibrary();
//	
//	/**
//	 * Method Name: Login
//	 * Description: This method is to Login to the Application1
//	 * Parameters: Nothing
//	 * Return Type: Nothing
//	 * @throws InterruptedException 
//	 * @throws Exception 
//	 */	
//	public void LoginApp1(HashMap<String, ArrayList<String>> LoginResult ,WebDriver driver, KeywordModel keywordModel ) throws InterruptedException {
//	 
//		driver.get(LoginResult.get("URL").get(0));
//		driver.findElement(By.xpath("//input[@title='Search']")).sendKeys(LoginResult.get("CaseWorkerUserName").get(0));
//		Thread.sleep(3000);
//		driver.findElement(By.xpath("//input[@value='Google Search']")).click();
//		
//		
//	}
//	
//	
//	public void LoginApp2(HashMap<String, ArrayList<String>> LoginResult ,WebDriver driver, KeywordModel keywordModel ) throws InterruptedException {
//		 
//		driver.get(LoginResult.get("URL").get(0));
//		driver.findElement(By.xpath("//input[@title='Search']")).sendKeys(LoginResult.get("CaseWorkerUserName").get(0));
//		Thread.sleep(3000);
//		driver.findElement(By.xpath("//input[@value='Google Search']")).click();
//		
//	}
//	
//	public void StoreAPIndNames(WebDriver driver, KeywordModel keywordModel,int indSize) {
//		try
//        {
//            
//			indNameList = new String[indSize];
//			for (int i = 1; i <= indSize; i++)
//			{
//				indNameList[i - 1] = driver.findElement(By.xpath("(//table[@id='tableUsersProgram']//div[@class='caption'])[" + i + "]")).getText();
//			}
//
//		}
//		catch (Exception e)
//		{
//			System.out.println("Storing AP individual names has failed");
//
//		}
//		
//	}
//	
//	public void verifyAPMedicalRFI(WebDriver driver, KeywordModel keywordModel,String[] indivEligibilityDetails) throws InterruptedException {
//		 int num = 100;
//		 String indivName = "";
//         String programName = "";
//         String RFI = "";
//         String DueDate = "";
//         String[] indivDetail = null;
//		try {
//			int count = driver.findElements(By.xpath("//*[@id='0~ProofListToSubmitGrid~']//tbody/tr")).size();
//
//
//
//			int[] temp = new int[count];
//			for (int l = 0; l < count; l++)
//			{
//				temp[l] = num;
//				num = num + 1;
//			}
//
//			int x = 0;
//			int j = 0;
//
//			for (j = 1; j <= count; j++)
//			{
//
//				indivName = driver.findElement(By.xpath("(//*[contains(@id,'ProofListToSubmit~HouseHoldMemeberName~td')])[" + j + "]")).getText();
//
//
//				for (int k = 1; k <= indNameList.length; k++)
//				{
//					String ind_name = indNameList[k - 1];
//
//					if (ind_name.contains(indivName) /*&& indivDetail[0].Equals("Indiv"+k+"")*/)
//					{
//						x = j;
//						for (int i = 0, z = 0; i < count && z < temp.length; i++, z++)
//						{
//							indivDetail = indivEligibilityDetails[i].split("\\|");
//
//							if (indivDetail[0].equals("Indiv" + k + "") && temp[z] != i)  //3
//							{
//								temp[z] = i;
//
//								break;
//							}
//						}
//						break;
//					}
//
//
//				}
//
//
//				programName = driver.findElement(By.xpath("(//*[contains(@id,'ProofListToSubmit~Program~td')])[" + x + "]")).getText();
//
//				RFI = driver.findElement(By.xpath("(//*[contains(@id,'ProofListToSubmit~ProofType~td')])[" + x + "]")).getText();
//
//				DueDate = driver.findElement(By.xpath("(//*[contains(@id,'ProofListToSubmit~VerificationChecklistDueDateNeeded~td')])[" + x + "]")).getText();
//
//				//   RFIDate = verifyAPRFIDate(indivDetail[3]);
//
//                   if (programName.equals(indivDetail[1]))
//                   {
//                       
//                       System.out.println(" Program Name for record " + j + " are as expected");
//                       ReportUtilities.Log(driver,"Verifying Program Name for record "+ j,"The Program Names are  as expected for record  " + j + " having text "  + programName, Status.PASS , keywordModel);
//                   }
//                   else
//                   {
//                       
//                       System.out.println(" Program Name for record " + j + " has failed");
//                       ReportUtilities.Log(driver,"Verifying Program Name for record " + j, "The Program Name are not as expected for record  " + j + " having text " + programName, Status.FAIL , keywordModel);
//                       driver.wait();
//                   }
//                   if (RFI.equals(indivDetail[2]))
//                   {
//                      
//                       System.out.println("RFI, for the record " + j + " are as expected");
//                       ReportUtilities.Log(driver,"Verifying RFI for record " + j, "The RFI are as expected for record  " + j + " having text " + RFI, Status.PASS , keywordModel);
//                       
//                   }
//                   else
//                   {
//                      
//                       System.out.println("RFI, for the record " + j + " has failed");
//                       ReportUtilities.Log(driver,"Verifying RFI for record " + j,"The eligibility TOA are not as expected for record  [" + j + "]having text " + RFI, Status.FAIL , keywordModel);
//                       driver.wait();
//                   }
//                   if (DueDate.equals(indivDetail[3]))
//                   {
//                      
//                       System.out.println("Due Date for the record " + j + " are as expected");
//                       ReportUtilities.Log(driver,"Verifying Due Date for record " + j,"The Begin Date are as expected for record  " + j + " having text " + DueDate, Status.PASS , keywordModel);
//                   }
//                   else
//                   {
//                      
//                       System.out.println("Due Date for the record " + j + " has failed");
//                       ReportUtilities.Log(driver,"Verifying Due Date for record " + j, "The Begin Date are not as expected for record  " + j + " having text " + DueDate, Status.FAIL , keywordModel);
//                       driver.wait();
//                   }
//                  
//
//               }
//               
//
//           }
//           catch (Exception e)
//           {
//                ReportUtilities.Log(driver,"Verifying the Medical RFI records " + keywordModel.objectName , "The values are not displayed as expected", Status.FAIL , keywordModel);
//                driver.wait();
//           }
//	}
//	
//	public void verifyAPMedicalRFIDyna(WebDriver driver, KeywordModel keywordModel,String[] indivEligibilityDetails) throws InterruptedException
//	{
//        String indivName = "";
//        String programName = "";
//        String RFI = "";
//        String DueDate = "";
//        String RFIDate="";
//        int num = 100;
//       
//        String[] indivDetail = null;
//        
//    
//        
//        try
//        {
//
//           
//            int count = driver.findElements(By.xpath("//*[@id='0~ProofListToSubmitGrid~']//tbody/tr")).size();
//           
//
//
//            int[] temp = new int[count];
//            for (int l = 0; l < count; l++)
//            {
//                temp[l] = num;
//                num = num + 1;
//            }
//
//            int x = 0;
//            int j = 0;
//          
//            for (j = 1; j <= count; j++)
//            {
//
//                indivName = driver.findElement(By.xpath("(//*[contains(@id,'ProofListToSubmit~HouseHoldMemeberName~td')])[" + j + "]")).getText();
//
//
//                for (int k = 1; k <= indNameList.length; k++)
//                {
//                    String ind_name = indNameList[k - 1];
//
//                    if (ind_name.contains(indivName) /*&& indivDetail[0].Equals("Indiv"+k+"")*/)
//                    {
//                        x = j;
//                        for (int i = 0, z = 0; i < count && z < temp.length; i++, z++)
//                        {
//                            indivDetail = indivEligibilityDetails[i].split("\\|");
//
//                            if (indivDetail[0].equals("Indiv" + k + "") && temp[z] != i)  //3
//                            {
//                                temp[z] = i;
//                             
//                                break;
//                            }
//                        }
//                        break;
//                    }
//                  
//
//                }
//              
//
//                programName = driver.findElement(By.xpath("(//*[contains(@id,'ProofListToSubmit~Program~td')])[" + x + "]")).getText();
//            
//                RFI = driver.findElement(By.xpath("(//*[contains(@id,'ProofListToSubmit~ProofType~td')])[" + x + "]")).getText();
//              
//                DueDate = driver.findElement(By.xpath("(//*[contains(@id,'ProofListToSubmit~VerificationChecklistDueDateNeeded~td')])[" + x + "]")).getText();
//              
//                RFIDate = keywordLib.verifyAPRFIDate(indivDetail[3], driver, keywordModel);
//
//                if (programName.equals(indivDetail[1]))
//                {
//                    
//                    System.out.println(" Program Name for record " + j + " are as expected");
//                    ReportUtilities.Log(driver,"Verifying Program Name for record "+ j,"The Program Names are  as expected for record  " + j + " having text "  + programName, Status.PASS , keywordModel);
//                }
//                else
//                {
//                    
//                    System.out.println(" Program Name for record " + j + " has failed");
//                    ReportUtilities.Log(driver,"Verifying Program Name for record " + j, "The Program Name are not as expected for record  " + j + " having text " + programName, Status.FAIL , keywordModel);
//                    keywordLib.storeEDMResult(driver, keywordModel);
//                    // driver.wait();
//                }
//                if (RFI.equals(indivDetail[2]))
//                {
//                   
//                    System.out.println("RFI, for the record " + j + " are as expected");
//                    ReportUtilities.Log(driver,"Verifying RFI for record " + j, "The RFI are as expected for record  " + j + " having text " + RFI, Status.PASS , keywordModel);
//                    
//                }
//                else
//                {
//                   
//                    System.out.println("RFI, for the record " + j + " has failed");
//                    ReportUtilities.Log(driver,"Verifying RFI for record " + j,"The eligibility TOA are not as expected for record  [" + j + "]having text " + RFI, Status.FAIL , keywordModel);
//                    keywordLib.storeEDMResult(driver, keywordModel);
//                   // driver.wait();
//                }
//                if (DueDate.equals(RFIDate))
//                {
//                   
//                    System.out.println("Due Date for the record " + j + " are as expected");
//                    ReportUtilities.Log(driver,"Verifying Due Date for record " + j,"The Begin Date are as expected for record  " + j + " having text " + DueDate, Status.PASS , keywordModel);
//                }
//                else
//                {
//                   
//                    System.out.println("Due Date for the record " + j + " has failed");
//                    ReportUtilities.Log(driver,"Verifying Due Date for record " + j, "The Begin Date are not as expected for record  " + j + " having text " + DueDate, Status.FAIL , keywordModel);
//                    keywordLib.storeEDMResult(driver, keywordModel);
//                 //  driver.wait();
//                }
//               
//
//            }
//            
//
//        }
//        catch (Exception e)
//        {
//             ReportUtilities.Log(driver,"Verifying the Medical RFI records " + keywordModel.objectName , "The values are not displayed as expected", Status.FAIL , keywordModel);
//             driver.wait();
//        }
//
//    }
//	
//	public void clickAnnualIncomeEditIcon(WebDriver driver, KeywordModel keywordModel, int icount) {
//		String indCompleteName = driver.findElement(By.xpath("(//div[contains(@class,'rpIndividualName')])["+icount+"]")).getText();
//		String[] arrayName = indCompleteName.split(" ");
//		String indName = arrayName[0]+" "+arrayName[1];
//		System.out.println("Clicking 100% Annual income edit icon for ind "+indName);
//		String newXpathString ="(//td[contains(text(),'"+indName+"')]/following-sibling::td[4]//img[@class='editRecord'])["+keywordModel.jcount+"]";
//		driver.findElement(By.xpath(newXpathString)).click();
//		ReportUtilities.Log(driver,"clicking on 100% annual income edit icon " + keywordModel.dataValue,
//				"clicked on 100% annual income edit icon " + keywordModel.dataValue, Status.PASS , keywordModel);
//	}
//	
//	public void clickMedicalNoticeReasonlink(WebDriver driver, KeywordModel keywordModel, int icount) {
//		String indCompleteName = driver.findElement(By.xpath("(//div[contains(@class,'rpIndividualName')])["+icount+"]")).getText();
//		String[] arrayName = indCompleteName.split(" ");
//		String indName = arrayName[0]+" "+arrayName[1];
//		System.out.println("Clicking medical notice reason link for ind "+indName);
//		String newXpathString ="(//div[contains(@class,'MedicalGrid')]/div[contains(text(),'"+indName+"')]/following-sibling::div[5]/a[1])["+keywordModel.jcount+"]" ;
//		driver.findElement(By.xpath(newXpathString)).click();
//		ReportUtilities.Log(driver,"clicking on medical notice reason link " + keywordModel.dataValue,
//				"clicked on medical notice reason link " + keywordModel.dataValue, Status.PASS , keywordModel);
//	}
//	
//	public void clickMedicalRFIlink(WebDriver driver, KeywordModel keywordModel,int icount) {
//		
//		String indCompleteName = driver.findElement(By.xpath("(//div[contains(@class,'rpIndividualName')])["+icount+"]")).getText();
//		String[] arrayName = indCompleteName.split(" ");
//		String indName = arrayName[0]+" "+arrayName[1];
//		System.out.println("Clicking on medical rfi link for ind "+indName);
//		String newXpathString ="(//div[contains(@class,'MedicalGrid')]/div[contains(text(),'"+indName+"')]/following-sibling::div[5]/a[2])["+keywordModel.jcount+"]" ;
//		driver.findElement(By.xpath(newXpathString)).click();
//		ReportUtilities.Log(driver,"clicking on medical rfi link " + keywordModel.dataValue,
//				"clicked on medical rfi link " + keywordModel.dataValue, Status.PASS , keywordModel);
//	}
//	
//	 public void verifyAuthSummaryMedicalEDMDyna(WebDriver driver, KeywordModel keywordModel,String[] indivEligibilityDetails) throws InterruptedException 
//     {
//      String indivName = "";
//      String status = "";
//      String toa = "";
//      String beginDt = "";
//      String endDt = "";
//      int num = 100;
//     
//      String[] indivDetail = null;
//      String calBeginDate="";
//      String calEndDate="";
//     
//      
//      try
//      {
//
//         
//          int indSize = driver.findElements(By.xpath("//div[contains(@class,'rpIndividualName')]")).size();
//          String[] indNameList = new String[indSize];
//
//          driver.findElement(By.xpath("//a[@id = 'viewAuthorizationHistory']")).click();
//          Thread.sleep(3000);
//          driver.findElement(By.xpath("//input[@id='btnSearch']")).click();
//          Thread.sleep(3000);
//          int count = driver.findElements(By.xpath("//div[contains(@class,'MedicalGrid')]")).size();
//          System.out.println("Total Medical EDMs on Auth History is : " + count);
//
//
//          for (int i = 1; i <= indSize; i++)
//          {
//              indNameList[i - 1] = driver.findElement(By.xpath("(//div[contains(@class,'rpIndividualName')])[" + i + "]")).getText();
//
//          }
//
//          int[] temp = new int[count];
//          for (int l = 0; l < count; l++)
//          {
//              temp[l] = num;
//              num = num + 1;
//          }
//
//          int x = 0;
//          int j = 0;
//        
//          for (j = 1; j <= count; j++)
//          {
//
//              indivName = driver.findElement(By.xpath("(//div[contains(@class,'MedicalGrid')]//div[4])[" + j + "]")).getText();
//
//
//              for (int k = 1; k <= indSize; k++)
//              {
//             	 String ind_name = indNameList[k - 1];		
//
//                  if (ind_name.contains(indivName) /*&& indivDetail[0].Equals("Indiv"+k+"")*/)
//                  {
//                      x = j;
//                      for (int i = 0, z = 0; i < count && z < temp.length; i++, z++)
//                      {
//                          indivDetail = indivEligibilityDetails[i].split("\\|");
//
//                          if (indivDetail[0].equals("Indiv" + k + "") && temp[z] != i)
//                          {
//                              temp[z] = i;
//
//                              break;
//                          }
//                      }
//                      break;
//                  }
//               
//
//              }
//
//              calBeginDate = keywordLib.calMABeginDate(indivDetail[3], driver, keywordModel);
//              calEndDate = keywordLib.calMAEndDate(indivDetail[4], driver, keywordModel);
//
//              toa = driver.findElement(By.xpath("(//div[contains(@class,'MedicalGrid')]//div[3])[" + x + "]")).getText();
//             
//              beginDt = driver.findElement(By.xpath("(//div[contains(@class,'MedicalGrid')]//div[5])[" + x + "]")).getText();
//             
//              endDt = driver.findElement(By.xpath("(//div[contains(@class,'MedicalGrid')]//div[6])[" + x + "]")).getText();
//              
//              status = driver.findElement(By.xpath("(//div[contains(@class,'MedicalGrid')]//div[8])[" + x + "]")).getText();
//             
//              
//              if (status.contains(indivDetail[1]))
//              {
//                  
//                  System.out.println(" Eligibility Status for record " + j + " are as expected");
//                  ReportUtilities.Log(driver,"Verifying Status for record "+ j,"The eligibility Status are  as expected for record  " + j + " having text "  + status, Status.PASS , keywordModel);
//              }
//              else
//              {
//                  
//                  System.out.println(" Eligibility Status for record " + j + " has failed");
//                  ReportUtilities.Log(driver,"Verifying Status for record " + j, "The eligibility Status are not as expected for record  " + j + " having text " + status, Status.FAIL , keywordModel);
//                  keywordLib.storeEDMResult(driver, keywordModel);
//                 // driver.wait();
//              }
//              if (toa.contains(indivDetail[2]))
//              {
//                 
//                  System.out.println("TOA, for the record " + j + " are as expected");
//                  ReportUtilities.Log(driver,"Verifying TOA for record " + j, "The eligibility TOA are as expected for record  " + j + " having text " + toa, Status.PASS , keywordModel);
//              }
//              else
//              {
//                 
//                  System.out.println("TOA, for the record " + j + " has failed");
//                  ReportUtilities.Log(driver,"Verifying TOA for record " + j,"The eligibility TOA are not as expected for record  [" + j + "]having text " + toa, Status.FAIL , keywordModel);
//                  keywordLib.storeEDMResult(driver, keywordModel);
//                  //driver.wait();
//              }
//              if (beginDt.contains(calBeginDate))
//              {
//                 
//                  System.out.println("Begin Date for the record " + j + " are as expected");
//                  ReportUtilities.Log(driver,"Verifying Begin Date for record " + j,"The Begin Date are as expected for record  " + j + " having text " + beginDt, Status.PASS , keywordModel);
//              }
//              else
//              {
//                 
//                  System.out.println("Begin Date for the record " + j + " has failed");
//                  ReportUtilities.Log(driver,"Verifying Begin Date for record " + j, "The Begin Date are not as expected for record  " + j + " having text " + beginDt, Status.FAIL , keywordModel);
//                  keywordLib.storeEDMResult(driver, keywordModel);
//                  // driver.wait();
//              }
//              if (endDt.contains(calEndDate))
//              {
//                  
//                  System.out.println("End Date for the record " + j + " are as expected");
//                  ReportUtilities.Log(driver,"Verifying End Date for record " + j, "The End Date are as expected for record  " + j + " having text " + endDt, Status.PASS , keywordModel);
//              }
//              else
//              {
//                  
//                  System.out.println(" End Date for the record " + j + " has failed");
//                  ReportUtilities.Log(driver,"Verifying End Date for record " + j, "The End Date are not as expected for record  " + j + " having text " + endDt, Status.FAIL , keywordModel);
//                  keywordLib.storeEDMResult(driver, keywordModel);
//                  //driver.wait();
//              }
//
//            
//
//          }
//          
//
//      }
//      catch (Exception e)
//      {
//           ReportUtilities.Log(driver,"Verifying the medical EDM records " + keywordModel.objectName , "The values are not displayed as expected", Status.FAIL , keywordModel);
//           driver.wait();
//      }
//
//  }
//	 
//	 public void verifyMedicalEDMDyna(WebDriver driver, KeywordModel keywordModel,String[] indivEligibilityDetails,int count) throws InterruptedException 
//     {
//      String indivName = "";
//      
//      String status = "";
//      String toa = "";
//      String beginDt = "";
//      String endDt = "";
//      int num = 100;
//     
//      String[] indivDetail = null;
//      
//      String calBeginDate = "";
//      String calEndDate = "";
//      
//      try
//      {
//         
//          int indSize = driver.findElements(By.xpath("//div[contains(@class,'rpIndividualName')]")).size();
//
//
//          int[] temp = new int[count];
//          for (int l = 0; l < count; l++)
//          {
//              temp[l] = num;
//              num = num + 1;
//          }
//
//          int x = 0;
//          int j = 0;
//        
//          for (j = 1; j <= count; j++)
//          {
//
//              indivName = driver.findElement(By.xpath("(//div[contains(@class,'MedicalGrid')]//div[4])[" + j + "]")).getText();
//
//
//              for (int k = 1; k <= indSize; k++)
//              {
//                  String ind_name = driver.findElement(By.xpath("(//div[contains(@class,'rpIndividualName')])[" + k + "]")).getText();
//
//                  if (ind_name.contains(indivName) /*&& indivDetail[0].Equals("Indiv"+k+"")*/)
//                  {
//                      x = j;
//                      for (int i = 0, z = 0; i < count && z < temp.length; i++, z++)
//                      {
//                          indivDetail = indivEligibilityDetails[i].split("\\|");
//
//                          if (indivDetail[0].equals("Indiv" + k + "") && temp[z] != i)
//                          {
//                              temp[z] = i;
//
//                              break;
//                          }
//                      }
//                      break;
//                  }
//               
//
//              }
//
//              
//
//              toa = driver.findElement(By.xpath("(//div[contains(@class,'MedicalGrid')]//div[3])[" + x + "]")).getText();
//             
//              beginDt = driver.findElement(By.xpath("(//div[contains(@class,'MedicalGrid')]//div[5])[" + x + "]")).getText();
//             
//              endDt = driver.findElement(By.xpath("(//div[contains(@class,'MedicalGrid')]//div[6])[" + x + "]")).getText();
//              
//              status = driver.findElement(By.xpath("(//div[contains(@class,'MedicalGrid')]//div[8])[" + x + "]")).getText();
//             
//             calBeginDate = keywordLib.calMABeginDate(indivDetail[3], driver, keywordModel);
//             calEndDate = keywordLib.calMAEndDate(indivDetail[4], driver, keywordModel);
//              
//              if (status.equals(indivDetail[1]))
//              {
//                  
//                  System.out.println("Eligibility Status is as expected, Eligibility Status for record " + j +"is "+status);
//                  ReportUtilities.Log(driver,"Verifying Status for record "+ j,"The eligibility Status are  as expected for record  " + j + " having text "  + status, Status.PASS , keywordModel);
//              }
//              else
//              {
//                  
//                  System.out.println("Eligibility Status is not as expected, Eligibility Status for record " + j + " on UI is "+status+"While expected Eligibility Status is"+indivDetail[1]);
//                  ReportUtilities.Log(driver,"Verifying Status for record " + j, "The eligibility Status is not as expected for record  " + j + " having text on UI as " + status+"while expected status is"+ indivDetail[1], Status.FAIL , keywordModel);
//                  keywordLib.storeEDMResult(driver, keywordModel);
//                  //  driver.wait();
//              }
//              if (toa.equals(indivDetail[2]))
//              {
//                  System.out.println("TOA, for the record " + j + " are as expected");
//                  ReportUtilities.Log(driver,"Verifying TOA for record " + j, "The eligibility TOA are as expected for record  " + j + " having text " + toa, Status.PASS , keywordModel);
//              }
//              else
//              {
//                 
//                  System.out.println("TOA, for the record " + j + " has failed");
//                  ReportUtilities.Log(driver,"Verifying TOA for record " + j,"The eligibility TOA are not as expected for record  [" + j + "]having text " + toa, Status.FAIL , keywordModel);
//                  keywordLib.storeEDMResult(driver, keywordModel);
//                 // driver.wait();
//              }
//              if (beginDt.equals(calBeginDate))
//              {
//                 
//                  System.out.println("Begin Date for the record " + j + " are as expected");
//                  ReportUtilities.Log(driver,"Verifying Begin Date for record " + j,"The Begin Date are as expected for record  " + j + " having text " + beginDt, Status.PASS , keywordModel);
//                 
//              }
//              else
//              {
//                 
//                  System.out.println("Begin Date for the record " + j + " has failed");
//                  ReportUtilities.Log(driver,"Verifying Begin Date for record " + j, "The Begin Date are not as expected for record  " + j + " having text " + beginDt, Status.FAIL , keywordModel);
//                  keywordLib.storeEDMResult(driver, keywordModel);
//                  //driver.wait();
//              }
//              if (endDt.equals(calEndDate))
//              {
//                  
//                  System.out.println("End Date for the record " + j + " are as expected");
//                  ReportUtilities.Log(driver,"Verifying End Date for record " + j, "The End Date are as expected for record  " + j + " having text " + endDt, Status.PASS , keywordModel);
//              }
//              else
//              {
//                  
//                  System.out.println(" End Date for the record " + j + " has failed");
//                  ReportUtilities.Log(driver,"Verifying End Date for record " + j, "The End Date are not as expected for record  " + j + " having text " + endDt, Status.FAIL , keywordModel);
//                  keywordLib.storeEDMResult(driver, keywordModel);
//                 // driver.wait();
//              }
//
//            
//
//          }
//          
//
//      }
//      catch (Exception e)
//      {
//           ReportUtilities.Log(driver,"Verifying the medical EDM records " + keywordModel.objectName , "The values are not displayed as expected", Status.FAIL , keywordModel);
//           driver.wait();
//      }
//     }  
//
//      /**
//       * Method Name: initialiseEDMResult Return Type: Nothing Description: This method
//       * calculates total no of EDM fields validated for case and stored as EDM size
//       * @throws InterruptedException 
//       */
//
//      	public void initialiseEDMResult(WebDriver driver, KeywordModel keywordModel)
//      	{
//      	
//      		
//      		int count = driver.findElements(By.xpath("(//div[contains(@class,'33percent')])")).size();
//      		String[] prog = new String[count-1];
//      		for (int j=0,i=2;i<=count;i++,j++)
//      		{
//      			prog[j] =  driver.findElement(By.xpath("(//div[contains(@class,'33percent')])["+i+"]")).getText();
//      		}		//edmResult = new String[];
//
//      		for (int i=0;i<count-1;i++)
//      		{
//      			if(prog[i].equalsIgnoreCase("Medical"))
//      			{
//      				int med_count = driver.findElements(By.xpath("//div[contains(@class,'MedicalGrid')]")).size();
//      				keywordModel.resultSize = keywordModel.resultSize + (med_count * 4);
//      			}
//      			else if (prog[i].equalsIgnoreCase("SNAP"))	
//      			{
//      				int snap_count = driver.findElements(By.xpath("//*[@id='tblSNAPEligibilitySummary']//tbody/tr[@role='row']")).size();
//      				keywordModel.resultSize = keywordModel.resultSize + (snap_count * 6);	
//      			}
//      			
//      			else if (prog[i].equalsIgnoreCase("CASH"))	
//      			{
//      				int cash_count = driver.findElements(By.xpath("//*[@id='tblCASHEligibilitySummary_wrapper']//tbody/tr[@role='row']")).size();
//      				keywordModel.resultSize = keywordModel.resultSize + (cash_count * 6);	
//      			}
//      			
//      			else if (prog[i].equalsIgnoreCase("TA-DVS"))
//      			{
//      				int tadvs_count = driver.findElements(By.xpath("//*[@id='tblTADVSEligibilitySummary']//tbody/tr[@role='row']")).size();
//      				keywordModel.resultSize = keywordModel.resultSize + (tadvs_count * 5);	
//      			}
//      			
//      			else if (prog[i].equalsIgnoreCase("ERDC"))	
//      			{
//      				int erdc_count = driver.findElements(By.xpath("(//table[@id='tblERDCEligibilitySummary'])//tbody/tr[@role='row']")).size();
//      				keywordModel.resultSize = keywordModel.resultSize + (erdc_count * 7);	
//      			}
//      			
//      			else if (prog[i].equalsIgnoreCase("DSNAP"))	
//      			{
//      				int dsnap_count = driver.findElements(By.xpath("//*[@id='tblDSNAPEligibilitySummary']//tbody/tr[@role='row']")).size();
//      				keywordModel.resultSize = keywordModel.resultSize + (dsnap_count * 7);	
//      			}
//      			
//      			else if (prog[i].equalsIgnoreCase("Presumptive Eligiblity"))	
//      			{
//      				int hpe_count = driver.findElements(By.xpath("//table[@id='tblPEEligibilitySummary']")).size();
//      				keywordModel.resultSize = keywordModel.resultSize + (hpe_count * 4);	
//      			}
//
//      		}
//      		
//      		System.out.println(keywordModel.resultSize);
//      		keywordModel.edmResult = new String[keywordModel.resultSize];
//      		
//      		for(int i=0;i<keywordModel.resultSize;i++)
//      		{
//      			keywordModel.edmResult[i]="P";
//      				
//      			}
//
//      	
//      	}
//
//	
// }