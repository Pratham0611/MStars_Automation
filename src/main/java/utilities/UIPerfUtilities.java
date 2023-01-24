package utilities;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class UIPerfUtilities {

	   public static String getCurrentTime(){
	        try 
	        { 
	        	 Date date = Calendar.getInstance().getTime();
			   DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss.sss");
				   String result = dateFormat.format(date);
				   result = result.replace(":", "_");
	            result = result.replace(" ", "_");
	            result = result.replace(".", "_");
	            return result;

	        }
	        catch(Exception e)
	        {
	            System.out.println(e.getMessage());   
	            
	            throw e;
	        }

	}
	   
	public String getDuration(long StartTime, long EndTime)
	{
		long MilliSecs = EndTime - StartTime;
		
		
		long seconds = (long) (MilliSecs / 1000) % 60 ;
		long minutes = (long) ((MilliSecs / (1000*60)) % 60);
		long hours   = (long) ((MilliSecs / (1000*60*60)) % 24);
		
		String strhours = String.valueOf(hours);
		String strminutes = String.valueOf(minutes);
		String strseconds = String.valueOf(seconds);
		String strMilliSeconds=String.valueOf(MilliSecs);

		if(strhours.length()==1)
		{
			strhours="0"+strhours;
		}

		if(strminutes.length()==1)
		{
			strminutes="0"+strminutes;
		}

		if(strseconds.length()==1)
		{
			strseconds="0"+strseconds;
		}
		
		if(strMilliSeconds.length()==1)
		{
			strMilliSeconds="0"+strMilliSeconds;
		}

		return strhours +":" + strminutes+ ":" + strseconds+":"+strMilliSeconds;

	}

	
	public long getDurationinMillisecond(long StartTime, long EndTime)
	{

		long totalSecs = EndTime - StartTime;
		return totalSecs;
	}
	
	public String LocalDateTimeToString( long datetime)
	{ 
		Date date = new Date(datetime);
		Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss.SSS");
		return format.format(date);
    
	}

}
