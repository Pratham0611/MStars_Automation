package Constants;

import java.util.ArrayList;
import java.util.HashMap;

import Model.UIPerfModel;

public class UIPerfConstants {

	public static HashMap<String,ArrayList<UIPerfModel>> UIPerfData= new HashMap<String,ArrayList<UIPerfModel>>();

	public static long ExpectedResponseTimeinMilliSecond=3000;
	
	public void addUIPerfData(String SourceScreen,String DestinationScreen,UIPerfModel uiPerfModel)
	{
		ArrayList<UIPerfModel> uiPerfModels= new ArrayList<UIPerfModel>();
		uiPerfModels.add(uiPerfModel);
		UIPerfData.put(SourceScreen +"_"+ DestinationScreen, uiPerfModels);
	}
	
	public void addUpdatePerfData(String SourceScreen,String DestinationScreen,UIPerfModel uiPerfModel)
	{
		
		if(UIPerfData.containsKey(SourceScreen +"_"+ DestinationScreen))
		{
			ArrayList<UIPerfModel> uiPerfModels=UIPerfData.get(SourceScreen +"_"+ DestinationScreen);
			uiPerfModels.add(uiPerfModel);
			UIPerfData.put(SourceScreen +"_"+ DestinationScreen, uiPerfModels);
		}
		else
		{
			ArrayList<UIPerfModel> uiPerfModels= new ArrayList<UIPerfModel>();
			uiPerfModels.add(uiPerfModel);
			UIPerfData.put(SourceScreen +"_"+ DestinationScreen, uiPerfModels);	
		}
	}
	
	public ArrayList<UIPerfModel> getPerfData(String SourceScreen,String DestinationScreen)
	{
		if(UIPerfData.containsKey(SourceScreen +"_"+ DestinationScreen))
		{
			return UIPerfData.get(SourceScreen +"_"+ DestinationScreen);
		}
		else
		{
			return null;	
		}
	}
}