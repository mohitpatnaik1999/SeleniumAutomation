package project.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import project.pageObject.LandingPage;

//This class is created for holding the methods which are used commonly across all test classes same as that of AbstractComponent which is being created for page object files.
public class BaseTest {
	
	public WebDriver driver;
	public LandingPage landingPage;
	
	public WebDriver initializeDriver() throws IOException {
		
		//Using the Properties we are able to fetch the key value pairs from the GlobalData.properties file.
		//GlobalData.properties contains the key value pairs which can be used globally across the project.
		Properties prop=new Properties();
		
		//This FileInputStream converts the file path into FileInputStream object.
		FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\project\\resources\\GlobalData.properties");
		
		//This load the FileInputStream object and then we can fetch the key value pairs from the GlobalData.properties.
		prop.load(fis);
		
		//After that,we are fetching the browser which contains the browser name where test cases need to be executed.
		//Whenever we pass in which browser we need to execute using maven commands from terminal,then we fetch the browser value from terminal by System.getProperty("browser") and if its not null,then we execute what we have set in GlobalData.properties file.
		String browserName= System.getProperty("browser")!=null ? System.getProperty("browser") : prop.getProperty("browser");
		
		if(browserName.contains("chrome")) {
			//When we try to run the browser in headless mode,then we need to use ChromeOptions class.
			ChromeOptions option=new ChromeOptions();
			if(browserName.contains("headless")) {
				option.addArguments("headless");
			}
			driver=new ChromeDriver(option);
			//When the browser is in headless mode,then the browser won't get maximized so some element click might fail which will lead to test case failure.
			driver.manage().window().setSize(new Dimension(1440,900));
		}
		
		else if(browserName.equalsIgnoreCase("firefox"))
		{
			//firefox
			driver=new FirefoxDriver();
		}
		else if(browserName.equalsIgnoreCase("edge"))
		{
			//edge
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		return driver;
		
	}
	
	public List<HashMap<String,String>> jsonDataToMap(String filepath) throws IOException {
		
		//In the below step,we are converting the json file to a string object with name jsonContent with the help of FileUtils.
		String jsonContent=FileUtils.readFileToString(new File(filepath),StandardCharsets.UTF_8);
		
		//By the help of Jackson Databind,we are converting the string to Hashmap which we have added in pom.xml.
		//Now from the above step,we got the string.Now the string is being converted to Hashmap by the help of readValue and stores it in list. 
		ObjectMapper mapper=new ObjectMapper();
		List<HashMap<String,String>> data=mapper.readValue(jsonContent, new TypeReference<List<HashMap<String,String>>>(){});
		
		return data;
	}
	
	public String getScreenshot(String testCaseName,WebDriver driver) throws IOException {
		//We are getting the life of the driver from the Listeners.java class.
		TakesScreenshot ts= (TakesScreenshot)driver;
		File source=ts.getScreenshotAs(OutputType.FILE);
		String filePath=System.getProperty("user.dir")+"\\reports\\"+ testCaseName +".png";
		File dest=new File(filePath);
		FileUtils.copyFile(source, dest);
		return filePath;
	}
	
	//By the use of groups annotation in the test class,Testng assumes to be a part of the group so it fails to execute if we don't include alwaysRun=true.
	@BeforeMethod(alwaysRun=true)
	public LandingPage launchApplication() throws IOException {
		driver=initializeDriver();
		landingPage=new LandingPage(driver);
		landingPage.goTo();
		return landingPage;
	}
	
	@AfterMethod(alwaysRun=true)
	public void tearDown() {
		driver.close();
	}
}
