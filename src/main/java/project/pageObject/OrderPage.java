package project.pageObject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import project.AbstractComponent.AbstractComponent;

public class OrderPage extends AbstractComponent {
	
	WebDriver driver;
	public OrderPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath="//tr/td[2]")
	List<WebElement> orderHistoryProducts;
	
	@FindBy(css=".totalRow button")
	WebElement checkoutButton;
	
	By orderProducts=By.xpath("//tr/td[2]");
	
	public boolean verifyOrderItem(String productName) {
		elementToBeAppeared(orderProducts);
		Boolean match=orderHistoryProducts.stream().anyMatch(product->product.getText().equalsIgnoreCase(productName));
		return match;
	}
}
