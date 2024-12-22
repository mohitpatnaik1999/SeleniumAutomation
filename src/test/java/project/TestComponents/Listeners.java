package project.TestComponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import project.resources.ExtentReport;

public class Listeners extends BaseTest implements ITestListener {

	ExtentTest tests;
	ExtentReports extent=ExtentReport.config();
	
	/*
	 * When running tests in parallel mode, the tests object is updated before the current method completes, 
	 * resulting in inaccurate information being displayed in the report. 
	 * This causes discrepancies in the reported pass or failure status.
	 */
	
	//So to avoid such kind of problems,we use ThreadLocal which is provided by java to avoid concurrency problem.
	
	ThreadLocal<ExtentTest> extentTest=new ThreadLocal<ExtentTest>();
	
	@Override
	public void onTestStart(ITestResult result) {
		ITestListener.super.onTestStart(result);
		tests=extent.createTest(result.getName());
		
		/*.set is a method which will set the tests object into ThreadLocal.
		After doing this,it will create a unique thread id and will map with the tests object.*/
		extentTest.set(tests);
	}
	
	@Override
	public void onTestSuccess(ITestResult result) {
		ITestListener.super.onTestSuccess(result);
		extentTest.get().log(Status.PASS, "Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		ITestListener.super.onTestFailure(result);
		
		//getThrowable() is used to print the error message for the failed test case in the report.
		//To avoid concurrency problem,we are going to use extentTest.get() instead of tests.
		extentTest.get().fail(result.getThrowable());
		String filePath = null;
		try {
			//We are getting the life of the driver from the result object as it stores the details of the testcase so from the testcase indirectly we are getting the driver reference.
			driver=(WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			filePath = getScreenshot(result.getName(),driver);
		} catch (IOException e) {
			e.printStackTrace();
		}
		extentTest.get().addScreenCaptureFromPath(filePath, result.getName());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestSkipped(result);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedWithTimeout(result);
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		ITestListener.super.onStart(context);
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		ITestListener.super.onFinish(context);
		
		//If we don't write extent.flush(),then the ultimate report won't be generated.
		extent.flush();
	}

}
