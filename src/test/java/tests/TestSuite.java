package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.CheckoutPages;
import pages.ItemsPage;
import pages.LoginPage;

public class TestSuite {

		public ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
	
	public WebDriver getDriver() {
			return this.driver.get();
		}

		public void setDriver(WebDriver driver) {
			this.driver.set(driver);
		}


	@BeforeMethod
	public void setup(){
		WebDriverManager.chromedriver().setup();
		setDriver( new ChromeDriver());
		getDriver().get("https://www.saucedemo.com");
    }
	
	
	@Test (priority = 1)
	public void successfulFlow() throws InterruptedException {
		System.out.println("1st TestCase  " + Thread.currentThread().getId() + "\n");
		
		LoginPage login = new LoginPage(getDriver());
		ItemsPage itemsPage = new ItemsPage(getDriver());
		CheckoutPages checkoutPages = new CheckoutPages(getDriver());
	
		login.login("standard_user", "secret_sauce");
		login.checkSuccess();
		itemsPage.checkUserIsReadyToCheckout();
		checkoutPages.completeCheckout("Mahmoud", "Saleh", "32511");
		
	}
	
	@Test (priority = 4)
	public void lockedUser() {
		System.out.println("2nd TestCase  " + Thread.currentThread().getId() + "\n");
		
		LoginPage login = new LoginPage(getDriver());
		
		login.login("locked_out_user", "secret_sauce");
		login.checkLockedOutUserCantLogin();
		
	}
	
	@Test (priority = 3)
	public void virtualUser() throws InterruptedException {
		System.out.println("3rd TestCase  " + Thread.currentThread().getId() + "\n");
		
		LoginPage login = new LoginPage(getDriver());
		ItemsPage itemsPage = new ItemsPage(getDriver());
		CheckoutPages checkoutPages = new CheckoutPages(getDriver());
		
		login.login("visual_user", "secret_sauce");
		login.checkSuccess();
		itemsPage.checkUserIsReadyToCheckout();
		checkoutPages.checkVisualUserScenario();
		
	}
	
	@Test (priority = 2)
	public void pwIsMandatoryCheck() {
		System.out.println("4th TestCase  " + Thread.currentThread().getId() + "\n");
		
		LoginPage login = new LoginPage(getDriver());
		login.loginWithoutUserName("secret_sauce");
	}
	
	@Test (priority = 5)
	public void glitchUser() {
		System.out.println("5th TestCase  " + Thread.currentThread().getId() + "\n");
		
		LoginPage login = new LoginPage(getDriver());
		login.loginWithGlitchUser("performance_glitch_user", "secret_sauce");
		
	}
	
	
	@AfterMethod
	public void quit(){

		getDriver().quit();
    }


}
