package project.tests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.Test;

import com.sun.net.httpserver.Authenticator.Retry;

import project.TestComponents.BaseTest;
import project.pageObject.MyCartPage;
import project.pageObject.ProductCatalogue;

public class ErrorValidationTest extends BaseTest {
	//Here we are suspecting that the loginValidation() method will fail so we are passing retryAnalyzer.
	@Test(groups= {"errorHandling"},retryAnalyzer=project.TestComponents.Retry.class)
	public void loginValidation() throws IOException, InterruptedException {
		landingPage.loginApplication("mohit@email.com", "Mohit@12");
		Assert.assertEquals(landingPage.displayErrorMessage(), "Incorrect email password.");

	}
	
	@Test
	public void productValidation() throws IOException, InterruptedException {
		String productName="ADIDAS ORIGINAL";
		ProductCatalogue productCatalogue= landingPage.loginApplication("mohit@abc.com", "Mohit@1999");

		List<WebElement> products=productCatalogue.productList();
		productCatalogue.addProductToCart(productName);
		MyCartPage myCart= productCatalogue.goToCart();
		Boolean match=myCart.checkoutItem("ADIDAS ORIGINAL123");
		Assert.assertFalse(match);

	}
}
