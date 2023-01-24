package reports.jsonReports;

import java.io.File;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import Model.UIPerfModel;


public class JSONUtility {

	public void DeSerializeTCDataWriteToFile( ArrayList<UIPerfModel> UIPerfModels,String FilePath) throws Exception
	{
		File file = new File(FilePath);
		ObjectMapper mapper = new ObjectMapper();

		try {
			
			mapper.writeValue(file, UIPerfModels);
			String JSONData=mapper.writeValueAsString(UIPerfModels);
			System.out.println(JSONData);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
			throw e;
		}
			
	}
	
	
	
}
