package utilities;

import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class NavigationTimeHelper {


	    Map<String, Object> timings = null; 
	    private final String JavaScriptForPerformance = "var performance = window.performance || window.webkitPerformance || window.mozPerformance || window.msPerformance || {};var timings = performance.timing || {};return timings;"; 
	    
	    public String getAllTiming(WebDriver driver){
	        JavascriptExecutor jsrunner = (JavascriptExecutor) driver;
	        var timings = jsrunner.executeScript(JavaScriptForPerformance); 
	        return timings.toString();
	    }
	    public Long getAnTime(String name){       
	        return (Long)timings.get((Object)name);     
	    }
	    public Long getnavigationStart(){        
	        return getAnTime("navigationStart");    
	    }
	    public Long getunloadEventStart(){        
	        return getAnTime("unloadEventStart");    
	    }
	    public Long getunloadEventEnd(){        
	        return getAnTime("unloadEventEnd");    
	    }
	    public Long getredirectStart(){        
	        return getAnTime("redirectStart");    
	    }
	    public Long getredirectEnd(){        
	        return getAnTime("redirectEnd");    
	    }
	    public Long getfetchStart(){       
	        return getAnTime("fetchStart");    
	    }
	    public Long getdomainLookupStart(){        
	        return getAnTime("domainLookupStart");    
	    }
	    public Long getdomainLookupEnd(){        
	        return getAnTime("domainLookupEnd");    
	    }
	    public Long getconnectStart(){        
	        return getAnTime("connectStart");    
	    }
	    public Long getconnectEnd(){        
	        return getAnTime("connectEnd");    
	    }
	    public Long getsecureConnectionStart(){        
	        return getAnTime("secureConnectionStart");    
	    }
	    public Long getrequestStart(){        
	        return getAnTime("requestStart");    
	    }
	    public Long getresponseStart(){        
	        return getAnTime("responseStart");    
	    }
	    public Long getresponseEnd(){        
	        return getAnTime("responseEnd");    
	    }
	    public Long getdomLoading(){        
	        return getAnTime("domLoading");    
	    }
	    public Long getdomInteractive(){        
	        return getAnTime("domInteractive");    
	    }
	    public Long getdomContentLoadedEventStart(){        
	        return getAnTime("domContentLoadedEventStart");    
	    }
	    public Long getdomContentLoadedEventEnd(){        
	        return getAnTime("domContentLoadedEventEnd");    
	    }
	    public Long getdomComplete(){        
	        return getAnTime("domComplete");    }
	    public Long getloadEventStart(){        
	        return getAnTime("loadEventStart");    
	    }
	    public Long getloadEventEnd(){        
	        return getAnTime("loadEventEnd");    
	    }
}
