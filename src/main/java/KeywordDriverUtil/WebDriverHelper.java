package KeywordDriverUtil;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverHelper {	//The static WebDriver object
//	public static WebDriver driver = null;
	//public static WebDriverBackedSelenium selenium = null;
	public static long timeout = 30;
	//public static String mainWindowHandle = null;
	
	
	/**
	 * Method Name: SelectDriver
	 * Description: This method is to select the Type of Driver to be used for executing the script depending on the Browser given in Properties.INI file
	 * @return 
	 * @throws IOException 
	 */
	public WebDriver SelectDriver(String Browser) throws IOException{
		
		 WebDriver driver = null;
		 //String driverExePath ="";
		String driverExePath = new File (".").getCanonicalPath() + "\\src\\main\\resources\\BrowserDrivers";
		try {
			if(Browser.equalsIgnoreCase("Chrome")){

				
				
				
				
				WebDriverManager.chromedriver().setup();
		    	ChromeOptions options= new ChromeOptions();    	
		    	options.setAcceptInsecureCerts(true);
		    	options.addArguments("start-maximized"); // open Browser in maximized mode
		    	options.addArguments("disable-infobars"); // disabling infobars
		    	options.addArguments("--disable-extensions"); // disabling extensions
		    	options.addArguments("--disable-gpu"); // applicable to windows os only
		    	options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
		    	options.addArguments("--no-sandbox"); // Bypass OS security model  
		    	driver= new ChromeDriver(options);
		    	driver.manage().deleteAllCookies();
				
				
			}
			else if(Browser.equalsIgnoreCase("Chrome_Android")) 
			{
				DesiredCapabilities capabilities = new DesiredCapabilities();
				
				capabilities.setCapability("chromedriverExecutable",driverExePath+"\\chromedriver.exe");

				capabilities.setCapability("deviceName", "a52q");

				capabilities.setCapability("platformName", "Android");

				capabilities.setCapability("platformVersion", "12");

				capabilities.setCapability("browserName", "chrome");
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--user-agent=Chrome/99.0.4844.73");
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				String url = "http://127.0.0.1:4723/wd/hub";
				driver = new RemoteWebDriver(new java.net.URL("http://127.0.0.1:4723/wd/hub"),capabilities);
				
			}
			
			else if(Browser.equalsIgnoreCase("Edge")){
			    // DesiredCapabilities caps = DesiredCapabilities.edge();
				
				//caps.setCapability("resolution", "1296x696");
				System.setProperty("webdriver.edge.driver", driverExePath+"\\msedgedriver.exe");
				driver = new EdgeDriver();
			}
			else if(Browser.equalsIgnoreCase("IE")){

		    	DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();

		    	capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		    	// this line of code is to resolve protected mode issue 
		    	capabilities.setCapability("NATIVE_EVENTS", false);
		    	capabilities.setCapability("IGNORE_ZOOM_SETTING", true);
		    	capabilities.setCapability("ACCEPT_SSL_CERTS", true); 
		    	capabilities.setCapability("unexpectedAlertBehaviour", "accept");
		    	capabilities.setCapability("ignoreProtectedModeSettings", true);
		    	capabilities.setCapability("disable-popup-blocking", true);
		    	capabilities.setCapability("enablePersistentHover", true); 
		    	capabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "https://stl3-wp-upg.one.or.gov/");
		    	
		    	//caps.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
		    	System.setProperty("webdriver.ie.driver", driverExePath+"\\IEDriverServer.exe");
				driver = new InternetExplorerDriver(capabilities);
			}else if(Browser.equalsIgnoreCase("Firefox")){
				
				   System.setProperty("webdriver.gecko.driver",driverExePath+"\\geckodriver.exe");
				  // System.setProperty("webdriver.firefox.bin","C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
				    
				    FirefoxProfile profile = new FirefoxProfile();
				    //Set Location to store files after downloading.
					profile.setPreference("browser.download.dir", "C:\\AUT Framework\\PDFDownloads");
					profile.setPreference("browser.download.folderList", 2);
					profile.setPreference( "browser.download.manager.showWhenStarting", false );
				//	C:\Users\schenthamarakshan\AppData\Local\Temp
					profile.setPreference("browser.helperApps.neverAsk.saveToDisk","application/pdf");
					profile.setPreference( "pdfjs.disabled", true );
					profile.setPreference("browser.helperApps.alwaysAsk.force", false);
					profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
					profile.setPreference("browser.download.manager.focusWhenStarting", false);
					profile.setPreference("browser.download.manager.useWindow", false);
					profile.setPreference("browser.download.manager.showAlertOnComplete", false);
					profile.setPreference("browser.download.manager.closeWhenDone", false);
					profile.setPreference("plugin.scan.Acrobat", "99.0");
					profile.setPreference("plugin.scan.plid.all", false);
					

				// driver = new FirefoxDriver(profile);
				//driver = new FirefoxDriver();
				
			}else if(Browser.equalsIgnoreCase("Safari")){
				driver = new SafariDriver();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return driver;
	}
	
	
	public WebDriver InitializeAndroidDriver() throws IOException
	{
		DesiredCapabilities capabilities = new DesiredCapabilities();
		
		capabilities.setCapability("chromedriverExecutable","C:\\SeleniumDrivers\\chromedriver.exe");

		capabilities.setCapability("deviceName", "a52q");

		capabilities.setCapability("platformName", "Android");

		capabilities.setCapability("platformVersion", "12");

		capabilities.setCapability("browserName", "chrome");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--user-agent=Chrome/99.0.4844.73");
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		String url = "http://127.0.0.1:4723/wd/hub";
		 WebDriver driver = new RemoteWebDriver(new java.net.URL("http://127.0.0.1:4723/wd/hub"),capabilities);
		 //WebDriver driver = new AndroidDriver<>(new URL(url), capabilities);
    	
    	return driver;
	}
	
	
//	public static void waitForXPath(String objectXpathID){
//		try {
//			int count=1;
//			while(driver.findElements(By.xpath(objectXpathID)).size()==0){
//				count++;
//				Thread.sleep(3000);
//				if(count==5)
//					break;
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	public static void waitForLinkText(String linkText){
//		try {
//			int count=1;
//			while(driver.findElements(By.linkText(linkText)).size()==0){
//				count++;
//				Thread.sleep(2000);
//				if(count==2)
//					break;
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//

}
