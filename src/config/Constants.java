package config;

public class Constants {
	//This is the list of System Variables
    //public static final String URL = "http://www.store.demoqa.com";
	public static final String Path_TestData = "C://Users//Matt//workspace//advanceddata_framework//src//executionEngine//DataEngine.xlsx";
	public static final String Path_OR = "C://Users//Matt//workspace//advanceddata_framework//src//config//OR.txt";
	public static final String File_TestData = "DataEngine.xlsx";
 
	//List of Test Case Data Sheet Column Numbers
		//Test Case ID is the same for both sheets 'Test Cases' and 'Test Steps'
	public static final int Col_TestCaseID = 0;
	public static final int Col_Description = 1;
	public static final int Col_RunMode = 2;
	public static final int Col_TestCaseResult = 3;
	
	//List of Test Steps Data Sheet Column Numbers
	public static final int Col_TestScenarioID =1 ;
	public static final int Col_StepDescription = 2;
	public static final int Col_Page =3;
	public static final int Col_Object = 4;
	public static final int Col_ActionKeyword = 5;
	public static final int Col_Data = 6;
	public static final int Col_StepResult = 7;
	
	//Pass/Fail key words
	public static final String KEYWORD_FAIL = "FAIL";
	public static final String KEYWORD_PASS = "PASS";
	
 
	//List of Test Cases Sheet
	public static final String Sheet_TestCases = "Test Cases";
	//List of Data Engine Excel sheets
	public static final String Sheet_TestSteps = "Test Steps";
 
}
