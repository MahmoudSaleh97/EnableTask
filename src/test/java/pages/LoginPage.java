package pages;

//import org.openqa.selenium.By;	
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.ItemsPage;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class LoginPage {

	// Declaration
	private WebDriver driver;
	ItemsPage itemsPage;

	// Element Locators

	@FindBy(id = "user-name")
	WebElement userName;
	@FindBy(id = "password")
	WebElement pw;
	@FindBy(id = "login-button")
	WebElement login_btn;
	// Error Locator to be customized by text later
	@FindBy(className = "error-message-container")
	WebElement errorMSG;

	// Constructor & PageFactory Initialization
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	// Actions

	// Input UserName
	public void enterUserName(String usrName) {
		userName.sendKeys(usrName);
	}

	// Input Password
	public void enterPassword(String password) {
		pw.sendKeys(password);
	}

	// Click on login button
	public void clickLogin() {
		login_btn.click();
	}

	// Login Method
	public void login(String usrName, String password) {

		this.enterUserName(usrName);
		this.enterPassword(password);

		// Long start = System.currentTimeMillis();
		this.clickLogin();
		// Long end = System.currentTimeMillis();
		// Long actualNormalDuration = (end - start) / 1000;
		// System.out.println("Standard User has logged in the system in " +
		// actualNormalDuration + " Seconds"); 
		//The Above commented lines of code were used
		// to determine the normal duration for login to assert on the performance
		// glitch user case

	}

	// Login without UserName Method
	public void loginWithoutUserName(String password) {

		this.enterPassword(password);
		this.clickLogin();
		String pwIsRequiredErrorTxt = errorMSG.getText();
		Assert.assertEquals(pwIsRequiredErrorTxt, "Epic sadface: Username is required");
	}

	// Login with Locked User Method
	public void checkLockedOutUserCantLogin() {

		String lockedUserErrorTxt = errorMSG.getText();
		Assert.assertEquals(lockedUserErrorTxt, "Epic sadface: Sorry, this user has been locked out.");
	}

	// Login with Glitch User Method
	public void loginWithGlitchUser(String usrName, String password) {

		ItemsPage itemsPage = new ItemsPage(driver);

		this.enterUserName(usrName);
		this.enterPassword(password);

		Long start = System.currentTimeMillis();
		this.clickLogin();
		Assert.assertTrue(itemsPage.shoppingCart.isDisplayed()); //Check that user is finally logged in
		Long end = System.currentTimeMillis();

		Long actualGlitchDuration = (end - start) / 1000; //Calculate the time for Performance Glitch User took to log in
		System.out.println("Performance Glitch User has logged in 'cause it took " + actualGlitchDuration
				+ " Seconds to login in instead of the normal duration which is approx. 2.1 Seconds");
	}

	// Verify Successful Login Method
	public void checkSuccess() {
		String actualURL = driver.getCurrentUrl();
		String expectedURL = "https://www.saucedemo.com/inventory.html";
		Assert.assertEquals(actualURL, expectedURL, "User Logged in successfully");
	}
}
