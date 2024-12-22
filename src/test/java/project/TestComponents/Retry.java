package project.TestComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {

	//Whenever the test fails,it comes here as well to check whether to rerun the test due to flaky failed test.
	//We cannot add the Retry class in the testng.xml file like listeners because it is not allowed.So we have to identify the method where the test is flaky,there we need to add.
	int count=0;
	int maxTry=2;
	@Override
	public boolean retry(ITestResult result) {
		// TODO Auto-generated method stub
		if(count<maxTry) {
			count++;
			return true;
		}
		return false;
	}

}
