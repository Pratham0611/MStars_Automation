package com.deloitte.framework.objects;

import java.io.File;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import Model.ExtentModel.TestCaseDetails;

	
	public class ReportSerialization {

		public String DeSerializeJSONData(TestCaseDetails TCD) throws Exception
		{
			String JSONData="";
			ObjectMapper mapper = new ObjectMapper();
			try {

				JSONData=mapper.writeValueAsString(TCD);
				System.out.println(JSONData);
				
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
				System.out.println(e.getStackTrace());
				throw e;
			}
			
			return JSONData;
			
		}
		public void DeSerializeJSONDataWriteToFile(TestCaseDetails TCD,String FilePath) throws Exception
		{
			File file = new File(FilePath);
			ObjectMapper mapper = new ObjectMapper();

			try {

				mapper.registerModule(new JavaTimeModule());
				String JSONData=mapper.writeValueAsString(TCD);
				mapper.writeValue(file, TCD);

				System.out.println(JSONData);
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
				System.out.println(e.getStackTrace());
				throw e;
			}
				
		}
		
		
		public TestCaseDetails SerializeJSONDatafromString(String TestCaseData) throws Exception
		{

			TestCaseDetails TCD = new TestCaseDetails();
			ObjectMapper mapper = new ObjectMapper();
			try {
				
				TCD= mapper.readValue(TestCaseData, TestCaseDetails.class);
			
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
				System.out.println(e.getStackTrace());
				throw e;
			}
			
			return TCD;
			
		}
		
		public TestCaseDetails SerializeJSONDatafromFile(String FilePath) throws Exception
		{

			TestCaseDetails TCD = new TestCaseDetails();
			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());
			  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			try {
				
				TCD= mapper.readValue(new File(FilePath), TestCaseDetails.class);
			
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
				System.out.println(e.getStackTrace());
				throw e;
			}
			
			return TCD;
			
		}
		


}