package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class CheckoutPages {

	private WebDriver driver;

	// Element Locators

	@FindBy(id = "checkout")
	WebElement checkout_btn; // Checkout Button in Cart Page

	// Fields in "Checkout: Your Information" Page
	@FindBy(id = "first-name")
	WebElement firstName;
	@FindBy(id = "last-name")
	WebElement lastName;
	@FindBy(id = "postal-code")
	WebElement postalCode;
	@FindBy(id = "continue")
	WebElement continue_btn;
	@FindBy(name = "finish")
	WebElement finish_btn;

	@FindBy(className = "complete-text")
	WebElement confirmationText;

	// Constructor & PageFactory Initialization
	public CheckoutPages(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// Actions (Methods are self-explanatory)

	public void goToCheckoutPage() {
		checkout_btn.click();
	}

	public void checkUserIsOnStepOne() {
		String actualURL = driver.getCurrentUrl();
		String expectedURL = "https://www.saucedemo.com/checkout-step-one.html";
		Assert.assertEquals(actualURL, expectedURL, "User is successfully in Checkout Step One");
	}

	public void fillCheckoutForm(String frstName, String lstName, String zipCode) {
		firstName.sendKeys(frstName);
		lastName.sendKeys(lstName);
		postalCode.sendKeys(zipCode);
		continue_btn.click();
	}

	public void checkUserIsOnStepTwo() {
		String actualURL = driver.getCurrentUrl();
		String expectedURL = "https://www.saucedemo.com/checkout-step-two.html";
		Assert.assertEquals(actualURL, expectedURL, "User is successfully in Checkout Step Two");
	}

	public void finish() {
		finish_btn.click();
	}

	public void checkSuccessfulFlow() {
		Boolean confirmation = confirmationText.isDisplayed();
		Assert.assertEquals(confirmation, true, "UserSuccessfullyPurchasedAnItem");
	}

	public void completeCheckout(String frstName, String lstName, String zipCode) {
		this.goToCheckoutPage();
		this.checkUserIsOnStepOne();
		this.fillCheckoutForm(frstName, lstName, zipCode);
		this.checkUserIsOnStepTwo();
		this.finish();
		this.checkSuccessfulFlow();

	}

	public void checkVisualUserScenario() {

		Actions actions = new Actions(driver);
		int getX = checkout_btn.getLocation().getX();
		int getY = checkout_btn.getLocation().getY();
		actions.moveByOffset(getX, getY).click();
		actions.build().perform();
		String actualURL = driver.getCurrentUrl();
		String expectedURL = "https://www.saucedemo.com/checkout-step-one.html";
		Assert.assertNotEquals(actualURL, expectedURL,
				"This is a Virtual User as Checkout Button is NOT displayed in its original location");
	}

}
