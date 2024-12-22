package project.tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import project.pageObject.LandingPage;

import org.openqa.selenium.JavascriptExecutor;

public class StandAloneTest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		WebDriver driver=new ChromeDriver();
		driver.get("https://rahulshettyacademy.com/client");
		
		//With the help of landingPage object,we are sending the driver from StandAloneTest class to LandingPage class.
		LandingPage landingPage=new LandingPage(driver);
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(5));
		JavascriptExecutor js=(JavascriptExecutor)driver;
		
		String productName="ADIDAS ORIGINAL";
		driver.findElement(By.id("userEmail")).sendKeys("mohit@email.com");
		driver.findElement(By.id("userPassword")).sendKeys("Mohit@123");
		driver.findElement(By.id("login")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("mb-3")));
		List<WebElement> products=driver.findElements(By.className("mb-3"));
		//findFirst() will list the first product with the product name and orElse will return if it does not find any product.
		//If we want the complete webelement,then we use filter method to do more actions.
		WebElement prod=products.stream().filter(product->product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);		
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		
		/*List<WebElement> product_name=driver.findElements(By.cssSelector(".card-body b"));
		for(int i=0;i<products.size();i++) {
			String names=product_name.get(i).getText();
			
			if(names.equals("ADIDAS ORIGINAL")) {
				driver.findElements(By.cssSelector(".card-body button:last-of-type")).get(i).click();
			}
		}*/
		
		//Implementation of explicit wait for the toast message to appear after clicking to "Add to Cart".
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		driver.findElement(By.xpath("//button[contains(@routerlink,'cart')]")).click();
		
		List<WebElement> cartProducts=driver.findElements(By.xpath("//div[@class='cartSection']/h3"));
		
		//If we want to compare and return the result either true or false,then we can use anyMatch().
		Boolean match=cartProducts.stream().anyMatch(product->product.getText().equalsIgnoreCase(productName));
		Assert.assertTrue(match);
		
		driver.findElement(By.cssSelector(".totalRow button")).click();
		
		driver.findElement(By.cssSelector(".form-group input")).sendKeys("ind");
		js.executeScript("window,scrollBy(0,200)");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//section/button")));
		
//		driver.findElement(By.cssSelector("section button[class*='ta-item']:nth-child(3)")).click();
		
		
		List<WebElement> product=driver.findElements(By.cssSelector("section button[class*='ta-item']"));
		
		for(WebElement choice:product) {
			if(choice.getText().equals("India")) {
				choice.click();
				break;
			}
		}
		
//		WebElement submit=driver.findElement(By.cssSelector("a[class*='btnn action__submit']"));
//		js.executeScript("arguments[0].click();", submit);
		
		js.executeScript("window.scrollBy(0,300)");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[class*='btnn action__submit']")));
		driver.findElement(By.cssSelector("a[class*='btnn action__submit']")).click();
		
		String actual=driver.findElement(By.xpath("//h1[@class='hero-primary']")).getText();
		Assert.assertTrue(actual.equalsIgnoreCase("Thankyou for the order."));
		
	}

}
