package project.AbstractComponent;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import project.pageObject.MyCartPage;
import project.pageObject.OrderPage;

public class AbstractComponent {
	
	WebDriver driver;
	public WebDriverWait wait;
	public AbstractComponent(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
		wait=new WebDriverWait(driver,Duration.ofSeconds(5));
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//button[contains(@routerlink,'cart')]")
	WebElement cartButton;
	
	@FindBy(xpath="//button[@routerlink='/dashboard/myorders']")
	WebElement orderButton;
	
	public void elementToBeAppeared(By locator) {
		//driver.findElement(By.cssSelector("#toast-container"))
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public void webElementToBeAppeared(WebElement ele) {
		wait.until(ExpectedConditions.visibilityOf(ele));
	}
	
	public void elementToBeDisappeared(WebElement ele) throws InterruptedException {
		Thread.sleep(2000);
		//wait.until(ExpectedConditions.invisibilityOf(ele));
	}
	
	public MyCartPage goToCart() {
		cartButton.click();
		MyCartPage myCart=new MyCartPage(driver);
		return myCart;
	}
	
	public OrderPage goToOrderPage() {
		orderButton.click();
		OrderPage orderPage=new OrderPage(driver);
		return orderPage;
	}

}
