package config;

import java.util.concurrent.TimeUnit;

import static executionEngine.DriverScript.OR;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import executionEngine.DriverScript;
import utility.Log;

public class ActionKeywords {

		public static WebDriver driver;
	
	public static void openBrowser(String object, String data){
		try {
			
			//Open different browsers based on Keyword
			if(data.equals("Mozilla")){
				driver=new FirefoxDriver();
				Log.info("Firefox Browser Started");	
			}else if(data.equals("IE")){
				driver = new InternetExplorerDriver();
				Log.info("IE Browser Started");
			}else if(data.equals("Chrome")){
				driver = new ChromeDriver();
				Log.info("Chrome Browser Started");
			}
			
			int implicitWaitTime=(10);
			driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
			
		}catch(Exception e){
			Log.error("Not able to open " + data + " Browser --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	public static void navigate(String object, String data){
		try{
			Log.info("Navigating to" + data + "");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get(data);
		}catch(Exception e){
			Log.error("Not able to navigate --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	public static void click(String object, String data){
		try { 
			Log.info("Clicking on Webelement " + object);
			driver.findElement(By.xpath(OR.getProperty(object))).click();
		}catch(Exception e){
			Log.error("Not able to click ---" + e.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	public static void input(String object, String data){
		try {
			Log.info("Entering the text in " + object);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);	
		}catch(Exception e){
			Log.error("Not able to input text in " + object + ": " + e.getMessage());
			DriverScript.bResult = false;
		}
		
	}
	
	public static void waitFor(String object, String data) throws Exception{
		try {
			Log.info("Wait for 5 seconds");
			Thread.sleep(5000);
		}catch(Exception e){
			Log.error("Not able to Wait ---" + e.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	public static void closeBrowser(String object, String data){
		try {
			Log.error("Closing the brower");
			driver.quit();
		}catch(Exception e){
			Log.info("Not able to close browser ---" + e.getMessage());
			DriverScript.bResult = false;
		}
	}
}
