package project.pageObject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import project.AbstractComponent.AbstractComponent;

public class MyCartPage extends AbstractComponent {
	
	WebDriver driver;
	public MyCartPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath="//div[@class='cartSection']/h3")
	List<WebElement> cartProducts;
	
	@FindBy(css=".totalRow button")
	WebElement checkoutButton;
	
	By products=By.xpath("//div[@class='cartSection']/h3");
	
	public boolean checkoutItem(String productName) {
		elementToBeAppeared(products);
		Boolean match=cartProducts.stream().anyMatch(product->product.getText().equalsIgnoreCase(productName));
		return match;
	}
	
	public PaymentPage clickCheckOutButton() {
		checkoutButton.click();
		PaymentPage payment=new PaymentPage(driver);
		return payment;
	}
}
