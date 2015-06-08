package executionEngine;
 
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Properties;

import org.apache.log4j.xml.DOMConfigurator;

import config.ActionKeywords;
import config.Constants;
import utility.ExcelUtils;
import utility.Log;
 
public class DriverScript {
 
	public static Properties OR;
	public static ActionKeywords actionKeywords;
	public static String sActionKeyword;
	public static String sObject;
	public static Method method[];
 	public static int iTestStep;
	public static int iTestLastStep;
	public static String sTestCaseID;
	public static String sRunMode;
	public static String sData;
	public static boolean bResult;
	
	 
	public DriverScript() throws NoSuchMethodException, SecurityException{
		actionKeywords = new ActionKeywords();
		method = actionKeywords.getClass().getMethods();		
	}
 
    public static void main(String[] args) throws Exception {
    	ExcelUtils.setExcelFile(Constants.Path_TestData);
    	
    	//This is to start the Log4j logging in the test case
    	DOMConfigurator.configure("log4j.xml");
    	
    	//Connect to DB for Test Case data
    	//mysqli_connect("localhost", "root","") or die(mysql_error());
    	
    	
    	
    	//Get the OR
    	FileInputStream fs = new FileInputStream(Constants.Path_OR);
		OR= new Properties(System.getProperties());
		OR.load(fs);
 
		DriverScript startEngine = new DriverScript();
		startEngine.execute_TestCase();
    }
 
    private void execute_TestCase() throws Exception {
    	//TODO Abstract all this out into a DATABASE. THe user will write a test script using with a UI interfacing with the data base and set up execution using the same UI.
    	   	
    	
		//This will return the total number of test cases mentioned in the Test cases sheet
    	int iTotalTestCases = (ExcelUtils.getRowCount(Constants.Sheet_TestCases));
		//This loop will execute number of times equal to Total number of test cases
		for(int iTestcase=1;iTestcase<=iTotalTestCases;iTestcase++){
			bResult = true;
			//This is to get the Test case name from the Test Cases sheet
			sTestCaseID = ExcelUtils.getCellData(iTestcase, Constants.Col_TestCaseID, Constants.Sheet_TestCases); 
			//This is to get the value of the Run Mode column for the current test case
			sRunMode = ExcelUtils.getCellData(iTestcase, Constants.Col_RunMode,Constants.Sheet_TestCases);
			//This is the condition statement on RunMode value
			if (sRunMode.equals("Yes")){
				//Only if the value of Run Mode is 'Yes', this part of code will execute
				iTestStep = ExcelUtils.getRowContains(sTestCaseID, Constants.Col_TestCaseID, Constants.Sheet_TestSteps);
				iTestLastStep = ExcelUtils.getTestStepsCount(Constants.Sheet_TestSteps, sTestCaseID, iTestStep);		
				Log.startTestCase(sTestCaseID);
				bResult = true;
				//This loop will execute number of times equal to Total number of test steps
				for (;iTestStep<=iTestLastStep;iTestStep++){
		    		sActionKeyword = ExcelUtils.getCellData(iTestStep, Constants.Col_ActionKeyword,Constants.Sheet_TestSteps);
		    		sObject = ExcelUtils.getCellData(iTestStep, Constants.Col_Object, Constants.Sheet_TestSteps);
		    		sData = ExcelUtils.getCellData(iTestStep, Constants.Col_Data, Constants.Sheet_TestSteps);
		    		execute_Actions();
		    		//This is the result code, this will execute after each test step
		    		//The execution flow will only execute if the value of bResult is 'false'
		    		if(bResult == false){
		    			//If 'false' then store the test case results as Fail
		    			ExcelUtils.setCellData(Constants.KEYWORD_FAIL, iTestcase, Constants.Col_TestCaseResult, Constants.Sheet_TestCases);
		    			Log.endTestCase(sTestCaseID);
		    			break;
		    		}
		    		
		    	}
				//This will only Execute after the last step of the test case
				if(bResult == true){
					//Storing the result as Pass in the excel sheet
					ExcelUtils.setCellData(Constants.KEYWORD_PASS,iTestcase,Constants.Col_TestCaseResult,Constants.Sheet_TestCases);
					Log.endTestCase(sTestCaseID);
				}
				
			}
    	}
    }
 
     private static void execute_Actions() throws Exception {
 
		for(int i=0;i<method.length;i++){			
			if(method[i].getName().equals(sActionKeyword)){
				method[i].invoke(actionKeywords,sObject, sData);
				//This code block will execute after every test step
				if(bResult == true){
					//If the executed test step value is true, Pass the test step in Excel Sheet
					ExcelUtils.setCellData(Constants.KEYWORD_PASS, iTestStep, Constants.Col_StepResult, Constants.Sheet_TestSteps);
					break;
				}else{
					ExcelUtils.setCellData(Constants.KEYWORD_FAIL, iTestStep, Constants.Col_StepResult, Constants.Sheet_TestSteps);
					//ActionKeywords.closeBrowser("");
					break;
				}
				
			}
		}
	}
 }