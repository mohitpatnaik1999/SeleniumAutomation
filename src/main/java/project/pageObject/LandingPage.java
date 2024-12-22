package project.pageObject;

//import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import project.AbstractComponent.AbstractComponent;

public class LandingPage extends AbstractComponent {
	WebDriver driver;
	
	//In the parameter of constructor,we are catching the driver object which we are getting from StandAloneTest class.
	public LandingPage(WebDriver driver) {
		//Initialization happens here
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	//As the driver does not have any life before the initialization of constructor
	//WebElement userEmail=driver.findElement(By.id("userEmail"));
	//WebElement password=driver.findElement(By.id("userPassword"));
	
	//Another way to declare userEmail in the above step is to use PageFactory.
	//In the runtime,it initializes the driver.findElement and then assigns to userEmail.
	//The construction of FindBy annotation is triggered when we call initElement method
	//It takes the driver from initElement method and then initializes it with.

	@FindBy(id="userEmail")
	WebElement userEmail;
	
	@FindBy(id="userPassword")
	WebElement password;
	
	@FindBy(id="login")
	WebElement login;
	
	@FindBy(css="[class*='flyInOut']")
	WebElement errorMessage;
	
	public void goTo() {
		driver.get("https://rahulshettyacademy.com/client");
	}
	
	public ProductCatalogue loginApplication(String user,String pass) {
		userEmail.sendKeys(user);
		password.sendKeys(pass);
		login.click();
		ProductCatalogue productCatalogue=new ProductCatalogue(driver);
		return productCatalogue;
	}
	
	public String displayErrorMessage() {
		webElementToBeAppeared(errorMessage);
		String message=errorMessage.getText();
		return message;
	}
	
}
