package project.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import project.AbstractComponent.AbstractComponent;

public class ConfirmationPage extends AbstractComponent {
	
	WebDriver driver;
	public ConfirmationPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
	}

	@FindBy(xpath="//h1[@class='hero-primary']")
	WebElement confirmMessage;
	
	By confirm=By.xpath("//h1[@class='hero-primary']");
	
	public boolean confirmation() {
		elementToBeAppeared(confirm);
		String message=confirmMessage.getText();
		return message.equalsIgnoreCase("Thankyou for the order.");
	}
}
