package Model;

import java.time.LocalDateTime;

import utilities.UIPerfUtilities;

public class UIPerfModel {

	public String SourceScreen="";
	public String DestinationScreen="";
	public String Source_DestinationScreen="";
	public int ScreenCount=0;
	public String ResponseTime="";
	public long ResponseTimeMillisecond=0;
	public String TestCaseName="";
	public String ModuleName="";
	public String BrowserName="";
	public String StartTime="";
	public String EndTime="";
	public String NavigationTimeDetails="";
			
	
	public UIPerfModel()
	{
		
	}
	
	
	public UIPerfModel AddUIPerfModel(String SourceScreen, String DestinationScreen, String TestCaseName, String ModuleName, String BrowserName,
			long StartTime,String NavigationTimeDetails)
	{
		long endTime= System.currentTimeMillis();
		UIPerfUtilities uiPerfUtilities = new UIPerfUtilities();
		UIPerfModel uiPerfModel= new UIPerfModel();
		uiPerfModel.SourceScreen=SourceScreen;
		uiPerfModel.DestinationScreen=DestinationScreen;
		uiPerfModel.Source_DestinationScreen=SourceScreen+ "_" + DestinationScreen;
		uiPerfModel.ScreenCount=ScreenCount;
		uiPerfModel.TestCaseName=TestCaseName;
		uiPerfModel.ModuleName=ModuleName;
		uiPerfModel.BrowserName=BrowserName;
		uiPerfModel.StartTime=uiPerfUtilities.LocalDateTimeToString(StartTime);
		uiPerfModel.EndTime=uiPerfUtilities.LocalDateTimeToString(endTime);
		uiPerfModel.NavigationTimeDetails=NavigationTimeDetails;
		uiPerfModel.ResponseTimeMillisecond=uiPerfUtilities.getDurationinMillisecond(StartTime, endTime);
		uiPerfModel.ResponseTime=uiPerfUtilities.getDuration(StartTime, endTime);
		return uiPerfModel;
	}
	
}
