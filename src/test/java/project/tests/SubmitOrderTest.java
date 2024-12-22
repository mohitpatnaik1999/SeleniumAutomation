package project.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import project.TestComponents.BaseTest;
import project.pageObject.ConfirmationPage;
import project.pageObject.MyCartPage;
import project.pageObject.OrderPage;
import project.pageObject.PaymentPage;
import project.pageObject.ProductCatalogue;

public class SubmitOrderTest extends BaseTest {

	String productName="ADIDAS ORIGINAL";
	//Converted the code from public static void main() to TestNG code.
	@Test(dataProvider="getData",groups= {"purchase"})
	public void submitOrder(HashMap<String,String> input) throws IOException, InterruptedException {
		ProductCatalogue productCatalogue= landingPage.loginApplication(input.get("email"), input.get("password"));

		List<WebElement> products=productCatalogue.productList();
		productCatalogue.addProductToCart(input.get("product"));
		MyCartPage myCart= productCatalogue.goToCart();
		
		Boolean match=myCart.checkoutItem(input.get("product"));
		Assert.assertTrue(match);
		PaymentPage payment=myCart.clickCheckOutButton();
		
		payment.shippingInformation("India");
		ConfirmationPage confirm=payment.placeOrder();
		Assert.assertTrue(confirm.confirmation());
	}

	//Suppose we are going to check if the product name which we have ordered is present in the order history or not,so there is a dependency
	@Test(dependsOnMethods= {"submitOrder"})
	public void orderHistoryTest() {
		ProductCatalogue productCatalogue= landingPage.loginApplication("mohit@email.com", "Mohit@123");
		OrderPage orderPage=productCatalogue.goToOrderPage();
		Assert.assertTrue(orderPage.verifyOrderItem(productName));
	}
	
//	@DataProvider
//	public Object[][] getData() {
//		return new Object[][]{{"mohit@email.com","Mohit@123","ADIDAS ORIGINAL"},{"mohit@abc.com","Mohit@1999","IPHONE 13 PRO"}};
//	}
	
/*//Here we are trying to use data provider to inject the different set of data to our submitOrder test case.
	@DataProvider
	public Object[][] getData() {
		
	//Suppose there are many key value pairs we need to inject to the submitOrder test case,so its inconvenient to create like mentioned in the above step.So to avoid this,we will use Hashmap.
	HashMap<String,String> map=new HashMap<String,String>();
	map.put("email", "mohit@email.com");
	map.put("password","Mohit@123");
	map.put("product", "ADIDAS ORIGINAL");

	HashMap<String,String> map1=new HashMap<String,String>();
	map1.put("email", "mohit@abc.com");
	map1.put("password","Mohit@1999");
	map1.put("product", "IPHONE 13 PRO");
		
	return new Object[][] {{map},{map1}};
		
		
	}*/
	
	@DataProvider
	public Object[][] getData() throws IOException {
		List<HashMap<String,String>> data=jsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\project\\Data\\PurchaseOrder.json");
		return new Object[][] { {data.get(0)} , {data.get(1)} };
	}
	
	

}
