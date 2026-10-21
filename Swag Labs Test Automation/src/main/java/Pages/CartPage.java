package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends BasePage {
    private final By ContinueShoppingLoc = By.id("continue-shopping");
    private final By CheckoutLoc = By.id("checkout");
    private final By CartListLoc = By.xpath("//div[@data-test=\"cart-list\"]/div");
    private final By cartItemNamesLoc = By.cssSelector(".cart_item .inventory_item_name");
    public CartPage(WebDriver driver) {
        super(driver);
    }

    public List<String> cartlist() {
        List<WebElement> emptyCart;
        emptyCart = driver.findElements(CartListLoc);
        List<String> cart = new ArrayList<>();
        for (WebElement webElement : emptyCart) {
            cart.add(webElement.getText());
        }
        return cart;
    }
    public void ProductRemove(String productName) {
        By productRemove = By.id("remove-" + productName.toLowerCase().replace(" ", "-"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(productRemove)).click();
    }

    public List<String> getCartItemNames() {
        wait.until(ExpectedConditions.urlContains("cart.html"));
        List<String> names = new ArrayList<>();
        for (WebElement e : driver.findElements(cartItemNamesLoc)) {
            names.add(e.getText());
        }
        return names;
    }
    public void CheckoutBtn() {
            wait.until(ExpectedConditions.urlContains("cart.html"));
            wait.until(ExpectedConditions.elementToBeClickable(CheckoutLoc)).click();

    }
    public void continueShopping() {
        wait.until(ExpectedConditions.elementToBeClickable(ContinueShoppingLoc)).click();
        wait.until(ExpectedConditions.urlContains("inventory.html"));
    }
    public CheckoutPage checkout() {

        return new CheckoutPage(driver);
    }
}


