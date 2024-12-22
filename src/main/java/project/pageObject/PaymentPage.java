package project.pageObject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import project.AbstractComponent.AbstractComponent;

public class PaymentPage extends AbstractComponent {
	WebDriver driver;
	JavascriptExecutor js;
	public PaymentPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		js=(JavascriptExecutor)driver;
	}

	By country=By.xpath("//section/button");
	
	@FindBy(css=".form-group input")
	WebElement countryBox;
	
	@FindBy(css="section button[class*='ta-item']")
	List<WebElement> countryNames;
	
//	driver.findElement(By.cssSelector("a[class*='btnn action__submit']")).click();
	@FindBy(css="a[class*='btnn action__submit']")
	WebElement placeOrderClick;
	
	public void shippingInformation(String countryName) {
		countryBox.sendKeys(countryName);
		js.executeScript("window,scrollBy(0,200)");
		elementToBeAppeared(country);
		for(WebElement choice:countryNames) {
			if(choice.getText().equals(countryName)) {
				choice.click();
				break;
			}
		}
	}
	
	public ConfirmationPage placeOrder() {
		js.executeScript("window.scrollBy(0,300)");
		By placeOrderButton=By.cssSelector("a[class*='btnn action__submit']");
		elementToBeAppeared(placeOrderButton);
		placeOrderClick.click();
		ConfirmationPage confirm=new ConfirmationPage(driver);
		return confirm;
	}
}
