package KeywordDriverUtil;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;


public class DriverUtil {

	public void InvokeDriver(WebDriver driver, String scenarioType,String ENV, String SEQ_EXECUTE)
	
	{
		if((scenarioType.equalsIgnoreCase("WorkerPortal")&&ENV.equalsIgnoreCase("STL3")&&SEQ_EXECUTE.equalsIgnoreCase("NO"))) {
			driver.get(KeywordUtilities.getValueFromConfigProperties("WP_STL3_URL"));
			driver.manage().window().maximize();
		}else if(scenarioType.equalsIgnoreCase("ApplicantPortal")&&ENV.equalsIgnoreCase("STL3")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
			driver.get(KeywordUtilities.getValueFromConfigProperties("AP_STL3_URL"));
			driver.manage().window().maximize();}
			
		else if(scenarioType.equalsIgnoreCase("WorkerPortal")&&ENV.equalsIgnoreCase("STS3")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
			driver.get(KeywordUtilities.getValueFromConfigProperties("WP_STS3_URL"));
			driver.manage().window().maximize();}
		
		else if(scenarioType.equalsIgnoreCase("ApplicantPortal")&&ENV.equalsIgnoreCase("STS3")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
			driver.get(KeywordUtilities.getValueFromConfigProperties("AP_STS3_URL"));
			driver.manage().window().maximize();}
					
		else if(scenarioType.equalsIgnoreCase("WorkerPortal")&&ENV.equalsIgnoreCase("STL2")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
			driver.get(KeywordUtilities.getValueFromConfigProperties("WP_STL2_URL"));
			driver.manage().window().maximize();}
				
		else if(scenarioType.equalsIgnoreCase("ApplicantPortal")&&ENV.equalsIgnoreCase("STL2")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
			driver.get(KeywordUtilities.getValueFromConfigProperties("AP_STL2_URL"));
			driver.manage().window().maximize();}
		
		else if(scenarioType.equalsIgnoreCase("WorkerPortal")&&ENV.equalsIgnoreCase("DEV2")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
			driver.get(KeywordUtilities.getValueFromConfigProperties("WP_DEV2_URL"));
			driver.manage().window().maximize();}
		
		else if(scenarioType.equalsIgnoreCase("ApplicantPortal")&&ENV.equalsIgnoreCase("DEV2")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
			driver.get(KeywordUtilities.getValueFromConfigProperties("AP_DEV2_URL"));
			driver.manage().window().maximize();}
			
		else if(scenarioType.equalsIgnoreCase("WorkerPortal")&&ENV.equalsIgnoreCase("INS1")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
			driver.get(KeywordUtilities.getValueFromConfigProperties("WP_INS1_URL"));
			driver.manage().window().maximize();}	
			
		else if(scenarioType.equalsIgnoreCase("ApplicantPortal")&&ENV.equalsIgnoreCase("INS1")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
			driver.get(KeywordUtilities.getValueFromConfigProperties("AP_INS1_URL"));
			driver.manage().window().maximize();}
					
		else if(scenarioType.equalsIgnoreCase("WorkerPortal")&&ENV.equalsIgnoreCase("UAT1")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
			driver.get(KeywordUtilities.getValueFromConfigProperties("WP_UAT1_URL"));
			driver.manage().window().maximize();}	
					
		else if(scenarioType.equalsIgnoreCase("ApplicantPortal")&&ENV.equalsIgnoreCase("UAT1")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
			driver.get(KeywordUtilities.getValueFromConfigProperties("AP_UAT1_URL"));
			driver.manage().window().maximize();}
							
		else if(scenarioType.equalsIgnoreCase("WorkerPortal")&&ENV.equalsIgnoreCase("UAT2")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
			driver.get(KeywordUtilities.getValueFromConfigProperties("WP_UAT2_URL"));
			driver.manage().window().maximize();}	
							
		else if(scenarioType.equalsIgnoreCase("ApplicantPortal")&&ENV.equalsIgnoreCase("UAT2")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
			driver.get(KeywordUtilities.getValueFromConfigProperties("AP_UAT2_URL"));
			driver.manage().window().maximize();}
		
		else if(scenarioType.equalsIgnoreCase("WorkerPortal")&&ENV.equalsIgnoreCase("UAT3")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
			driver.get(KeywordUtilities.getValueFromConfigProperties("WP_UAT3_URL"));
			driver.manage().window().maximize();}
		
		else if(scenarioType.equalsIgnoreCase("ApplicantPortal")&&ENV.equalsIgnoreCase("UAT3")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
			driver.get(KeywordUtilities.getValueFromConfigProperties("AP_UAT3_URL"));
			driver.manage().window().maximize();}
			
		else if(scenarioType.equalsIgnoreCase("WorkerPortal")&&ENV.equalsIgnoreCase("INT2")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
			driver.get(KeywordUtilities.getValueFromConfigProperties("WP_INT2_URL"));
			driver.manage().window().maximize();}
			
		else if(scenarioType.equalsIgnoreCase("ApplicantPortal")&&ENV.equalsIgnoreCase("INT2")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
			driver.get(KeywordUtilities.getValueFromConfigProperties("AP_INT2_URL"));
			driver.manage().window().maximize();}
				
		else if(scenarioType.equalsIgnoreCase("WorkerPortal")&&ENV.equalsIgnoreCase("STS2")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
			driver.get(KeywordUtilities.getValueFromConfigProperties("WP_STS2_URL"));
			driver.manage().window().maximize();}
				
		else if(scenarioType.equalsIgnoreCase("ApplicantPortal")&&ENV.equalsIgnoreCase("STS2")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
			driver.get(KeywordUtilities.getValueFromConfigProperties("AP_STS2_URL"));
			driver.manage().window().maximize();}
		
		else if(scenarioType.equalsIgnoreCase("WorkerPortal")&&ENV.equalsIgnoreCase("SAT")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
			driver.get(KeywordUtilities.getValueFromConfigProperties("WP_SAT_URL"));
			driver.manage().window().maximize();}
		
		else if(scenarioType.equalsIgnoreCase("ApplicantPortal")&&ENV.equalsIgnoreCase("SAT")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
			driver.get(KeywordUtilities.getValueFromConfigProperties("AP_SAT_URL"));
			driver.manage().window().maximize();}
			
		else if(scenarioType.equalsIgnoreCase("WorkerPortal")&&ENV.equalsIgnoreCase("PROD")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
				driver.get(KeywordUtilities.getValueFromConfigProperties("WP_PROD_URL"));
				driver.manage().window().maximize();}
			
		else if(scenarioType.equalsIgnoreCase("ApplicantPortal")&&ENV.equalsIgnoreCase("PROD")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
				driver.get(KeywordUtilities.getValueFromConfigProperties("AP_PROD_URL"));
				driver.manage().window().maximize();}
				
		else if(scenarioType.equalsIgnoreCase("WorkerPortal")&&ENV.equalsIgnoreCase("STS1")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
				driver.get(KeywordUtilities.getValueFromConfigProperties("WP_STS1_URL"));
	//			driver.manage().window().maximize();
				driver.manage().window().setSize(new Dimension(1920,1080));
				System.out.println(driver.manage().window().getSize());}
				
		else if(scenarioType.equalsIgnoreCase("ApplicantPortal")&&ENV.equalsIgnoreCase("STS1")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
				driver.get(KeywordUtilities.getValueFromConfigProperties("AP_STS1_URL"));
//				driver.manage().window().maximize();}
//				driver.manage().window().setSize(new Dimension(1024,760));
				System.out.println(driver.manage().window().getSize());}
				
		else if(scenarioType.equalsIgnoreCase("WorkerPortal")&&ENV.equalsIgnoreCase("STL1")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
					driver.get(KeywordUtilities.getValueFromConfigProperties("WP_STL1_URL"));
					driver.manage().window().maximize();}
				
		else if(scenarioType.equalsIgnoreCase("ApplicantPortal")&&ENV.equalsIgnoreCase("STL1")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
					driver.get(KeywordUtilities.getValueFromConfigProperties("AP_STL1_URL"));
					driver.manage().window().maximize();}		
			
		else if(scenarioType.equalsIgnoreCase("MDOT")&&ENV.equalsIgnoreCase("MDOT")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
			driver.get(KeywordUtilities.getValueFromConfigProperties("MDOT_URL"));
			driver.manage().window().maximize();
		
		
//				(scenarioType.equalsIgnoreCase("WorkerPortal")&&Environment.equalsIgnoreCase("STL3"))||((scenarioType.equalsIgnoreCase("BenefitManagement")&&Environment.equalsIgnoreCase("STL3")))) {
//			driver.get(KeywordUtilities.getValueFromConfigProperties("WP_STL3_URL"));
//			driver.manage().window().maximize();
//		}else if ((scenarioType.equalsIgnoreCase("ApplicantPortal")&&Environment.equalsIgnoreCase("STS3"))||((scenarioType.equalsIgnoreCase("BenefitManagement")&&Environment.equalsIgnoreCase("STS3")))) {
//			driver.get(KeywordUtilities.getValueFromConfigProperties("AP_STL3_URL"));
//			driver.manage().window().maximize();
//		}else if
			
	}
	
		else{
			if((scenarioType.equalsIgnoreCase("WorkerPortal")&&ENV.equalsIgnoreCase("STL3")&&SEQ_EXECUTE.equalsIgnoreCase("Yes"))) {
				driver.get(KeywordUtilities.getValueFromConfigProperties("WP_STL3_URL"));
				driver.manage().window().maximize();}
			else if(scenarioType.equalsIgnoreCase("ApplicantPortal")&&ENV.equalsIgnoreCase("STL3")&&SEQ_EXECUTE.equalsIgnoreCase("Yes")) {
				driver.get(KeywordUtilities.getValueFromConfigProperties("AP_STL3_URL"));
				driver.manage().window().maximize();}
				
			else if(scenarioType.equalsIgnoreCase("WorkerPortal")&&ENV.equalsIgnoreCase("STS3")&&SEQ_EXECUTE.equalsIgnoreCase("Yes")) {
				driver.get(KeywordUtilities.getValueFromConfigProperties("WP_STS3_URL"));
				driver.manage().window().maximize();}
			
			else if(scenarioType.equalsIgnoreCase("ApplicantPortal")&&ENV.equalsIgnoreCase("STS3")&&SEQ_EXECUTE.equalsIgnoreCase("Yes")) {
				driver.get(KeywordUtilities.getValueFromConfigProperties("AP_STS3_URL"));
				driver.manage().window().maximize();}
			else if(scenarioType.equalsIgnoreCase("WorkerPortal")&&ENV.equalsIgnoreCase("STL2")&&SEQ_EXECUTE.equalsIgnoreCase("Yes")) {
				driver.get(KeywordUtilities.getValueFromConfigProperties("WP_STL2_URL"));
				driver.manage().window().maximize();}
		
			else if(scenarioType.equalsIgnoreCase("ApplicantPortal")&&ENV.equalsIgnoreCase("STL2")&&SEQ_EXECUTE.equalsIgnoreCase("Yes")) {
				driver.get(KeywordUtilities.getValueFromConfigProperties("AP_STL2_URL"));
				driver.manage().window().maximize();}
			
			else if(scenarioType.equalsIgnoreCase("WorkerPortal")&&ENV.equalsIgnoreCase("DEV2")&&SEQ_EXECUTE.equalsIgnoreCase("Yes")) {
				driver.get(KeywordUtilities.getValueFromConfigProperties("WP_DEV2_URL"));
				driver.manage().window().maximize();}
			
			else if(scenarioType.equalsIgnoreCase("ApplicantPortal")&&ENV.equalsIgnoreCase("DEV2")&&SEQ_EXECUTE.equalsIgnoreCase("Yes")) {
				driver.get(KeywordUtilities.getValueFromConfigProperties("AP_DEV2_URL"));
				driver.manage().window().maximize();}
			
			else if(scenarioType.equalsIgnoreCase("WorkerPortal")&&ENV.equalsIgnoreCase("UAT1")&&SEQ_EXECUTE.equalsIgnoreCase("Yes")) {
				driver.get(KeywordUtilities.getValueFromConfigProperties("WP_UAT1_URL"));
				driver.manage().window().maximize();}	
			
			else if(scenarioType.equalsIgnoreCase("ApplicantPortal")&&ENV.equalsIgnoreCase("UAT1")&&SEQ_EXECUTE.equalsIgnoreCase("Yes")) {
				driver.get(KeywordUtilities.getValueFromConfigProperties("AP_UAT1_URL"));
				driver.manage().window().maximize();}
					
			else if(scenarioType.equalsIgnoreCase("WorkerPortal")&&ENV.equalsIgnoreCase("UAT2")&&SEQ_EXECUTE.equalsIgnoreCase("Yes")) {
				driver.get(KeywordUtilities.getValueFromConfigProperties("WP_UAT2_URL"));
				driver.manage().window().maximize();}	
					
			else if(scenarioType.equalsIgnoreCase("ApplicantPortal")&&ENV.equalsIgnoreCase("UAT2")&&SEQ_EXECUTE.equalsIgnoreCase("Yes")) {
				driver.get(KeywordUtilities.getValueFromConfigProperties("AP_UAT2_URL"));
				driver.manage().window().maximize();}

			else if(scenarioType.equalsIgnoreCase("WorkerPortal")&&ENV.equalsIgnoreCase("UAT3")&&SEQ_EXECUTE.equalsIgnoreCase("Yes")) {
				driver.get(KeywordUtilities.getValueFromConfigProperties("WP_UAT3_URL"));
				driver.manage().window().maximize();}

			else if(scenarioType.equalsIgnoreCase("ApplicantPortal")&&ENV.equalsIgnoreCase("UAT3")&&SEQ_EXECUTE.equalsIgnoreCase("Yes")) {
				driver.get(KeywordUtilities.getValueFromConfigProperties("AP_UAT3_URL"));
				driver.manage().window().maximize();}
			
			else if(scenarioType.equalsIgnoreCase("WorkerPortal")&&ENV.equalsIgnoreCase("INT2")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
				driver.get(KeywordUtilities.getValueFromConfigProperties("WP_INT2_URL"));
				driver.manage().window().maximize();}
			
			else if(scenarioType.equalsIgnoreCase("ApplicantPortal")&&ENV.equalsIgnoreCase("INT2")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
				driver.get(KeywordUtilities.getValueFromConfigProperties("AP_INT2_URL"));
				driver.manage().window().maximize();}
			
			else if(scenarioType.equalsIgnoreCase("WorkerPortal")&&ENV.equalsIgnoreCase("STS2")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
				driver.get(KeywordUtilities.getValueFromConfigProperties("WP_STS2_URL"));
				driver.manage().window().maximize();}
			
			else if(scenarioType.equalsIgnoreCase("ApplicantPortal")&&ENV.equalsIgnoreCase("STS2")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
				driver.get(KeywordUtilities.getValueFromConfigProperties("AP_STS2_URL"));
				driver.manage().window().maximize();}
			
			else if(scenarioType.equalsIgnoreCase("WorkerPortal")&&ENV.equalsIgnoreCase("SAT")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
				driver.get(KeywordUtilities.getValueFromConfigProperties("WP_SAT_URL"));
				driver.manage().window().maximize();}
			
			else if(scenarioType.equalsIgnoreCase("ApplicantPortal")&&ENV.equalsIgnoreCase("SAT")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
				driver.get(KeywordUtilities.getValueFromConfigProperties("AP_SAT_URL"));
				driver.manage().window().maximize();}
			
			else if(scenarioType.equalsIgnoreCase("WorkerPortal")&&ENV.equalsIgnoreCase("STS1")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
				driver.get(KeywordUtilities.getValueFromConfigProperties("WP_STS1_URL"));
	//			driver.manage().window().maximize();
	//			driver.manage().window().setSize(new Dimension(1024,760));
				System.out.println(driver.manage().window().getSize());}
			
			else if(scenarioType.equalsIgnoreCase("ApplicantPortal")&&ENV.equalsIgnoreCase("STS1")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
				driver.get(KeywordUtilities.getValueFromConfigProperties("AP_STS1_URL"));
	//			driver.manage().window().maximize();
	//			driver.manage().window().setSize(new Dimension(1024,760));
				System.out.println(driver.manage().window().getSize());}
			
			else if(scenarioType.equalsIgnoreCase("WorkerPortal")&&ENV.equalsIgnoreCase("STL1")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
				driver.get(KeywordUtilities.getValueFromConfigProperties("WP_STL1_URL"));
				driver.manage().window().maximize();}
			
			else if(scenarioType.equalsIgnoreCase("ApplicantPortal")&&ENV.equalsIgnoreCase("STL1")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
				driver.get(KeywordUtilities.getValueFromConfigProperties("AP_STL1_URL"));
				driver.manage().window().maximize();}
			
			else if(scenarioType.equalsIgnoreCase("JJIS")&&ENV.equalsIgnoreCase("JJIS")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
				driver.get(KeywordUtilities.getValueFromConfigProperties("JJIS_URL"));
				driver.manage().window().maximize();}
			
			else if(scenarioType.equalsIgnoreCase("MobileAutomation")&&ENV.equalsIgnoreCase("MobileAutomation")&&SEQ_EXECUTE.equalsIgnoreCase("NO")) {
				driver.get(KeywordUtilities.getValueFromConfigProperties("MobileAutomation_URL"));
				driver.manage().window().maximize();}

	}
	
}
}
