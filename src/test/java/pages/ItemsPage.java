package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class ItemsPage {

	private WebDriver driver;

	// Element Locators

	@FindBy(xpath = "//button[@class = 'btn btn_primary btn_small btn_inventory '][1]") // Add to cart button on the 1st
																						// Product on the list
	WebElement product;
	@FindBy(xpath = "//a [@class = 'shopping_cart_link']")
	WebElement shoppingCart;
	@FindBy(xpath = "//span [@class = 'shopping_cart_badge']")
	WebElement shoppingCartBadge;

	// Constructor & PageFactory Initialization
	public ItemsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	//Actions (Methods are self-explanatory)
	
	public void addItemToCart() {
		product.click();
	}

	public void checkItemAddedToCart() {
		Boolean badge = shoppingCartBadge.isDisplayed();
		Assert.assertEquals(badge, true, "User Successfully added an item into cart");
	}

	public void goToCart() {
		shoppingCart.click();
	}

	public void checkUserWentToCart() {
		String actualURL = driver.getCurrentUrl();
		String expectedURL = "https://www.saucedemo.com/cart.html";
		Assert.assertEquals(actualURL, expectedURL, "User is successfully in Cart Page");

	}

	public void checkUserIsReadyToCheckout() {
		this.addItemToCart();
		this.checkItemAddedToCart();
		this.goToCart();
		this.checkUserWentToCart();
	}

}
