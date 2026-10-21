package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class InventoryPage extends BasePage{

    private final By pageTitleLoc = By.className("app_logo");
    private final By cardIconLoc = By.className("shopping_cart_link");
    private final By productsLoc = By.className("inventory_item");
    private final By xPageloc = By.cssSelector("a[data-test=\"social-x\"]");
    private final By FacebookPageloc = By.cssSelector("a[data-test=\"social-facebook\"]");
    private final By LinkedInPageloc = By.cssSelector("a[data-test=\"social-linkedin\"]");
    private final By productNameLoc = By.className("inventory_item_name");
    private final By productPriceLoc = By.className("inventory_item_price");
    private final By SideMenuLoc = By.id("react-burger-menu-btn");
    private final By LogoutLoc = By.xpath("//a[text()=\"Logout\"]");


    public  InventoryPage(WebDriver driver) {
        super(driver);
    }

    public String open(){
        wait.until(ExpectedConditions.urlContains("inventory.html"));
        driver.get(driver.getCurrentUrl());
        return driver.getCurrentUrl();
    }
    public boolean CardIconExist(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(cardIconLoc));

        boolean cardIconExistboolean = driver.findElement(cardIconLoc).isDisplayed();
        return cardIconExistboolean;
    }
    public void clickOnCardIcon(){
        wait.until(ExpectedConditions.elementToBeClickable(cardIconLoc)).click();
        driver.findElement(cardIconLoc).click();
    }

    public String TitleisExist(){
        wait.until(ExpectedConditions.urlContains("inventory.html"));
//        driver.switchTo().alert();
//        driver.switchTo().alert().accept();
        driver.findElement(pageTitleLoc).getText();
        return driver.findElement(pageTitleLoc).getText();
    }
    public List<WebElement> getProducts(){
        wait.until(ExpectedConditions.urlContains("inventory.html"));
        List<WebElement> products = new ArrayList<>();
        for (WebElement e : driver.findElements(productsLoc)){
            products.add(e);
        }
        return products;
    }
    public List<String> SocialLinks() {
        String originalWindow = driver.getWindowHandle();
        driver.findElement(xPageloc).click();
        wait.until(ExpectedConditions.elementToBeClickable(FacebookPageloc));
        driver.findElement(FacebookPageloc).click();
        wait.until(ExpectedConditions.elementToBeClickable(LinkedInPageloc));
        driver.findElement(LinkedInPageloc).click();


        wait.until(ExpectedConditions.numberOfWindowsToBe(4));
        List<String> tabsIds = new ArrayList<>(driver.getWindowHandles());
        List<String> links = new ArrayList<>();

        for (String tab : tabsIds) {
            if (tab.equals(originalWindow)) continue;
            driver.switchTo().window(tab);
            wait.until(d -> !d.getCurrentUrl().isEmpty()
                    && !d.getCurrentUrl().equals("about:blank"));
            links.add(driver.getCurrentUrl());
            driver.close();
        }

        driver.switchTo().window(originalWindow);
        return links;
    }
    public InventoryPage addProductToCart(String productName) {
        String id = "add-to-cart-" + productName.toLowerCase().replace(" ", "-");
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id))).click();
        } catch (TimeoutException e) {
            System.out.println("URL: " + driver.getCurrentUrl());
            for (WebElement b : driver.findElements(By.cssSelector("button[id]"))) {
                System.out.println("button id: " + b.getAttribute("id"));
            }
            throw e;
        }
        return this;
    }

    public String getbuttontexeproduct(String productName) {
        By btnLoc = By.xpath("//div[contains(@class,'inventory_item_name') and normalize-space()='"
                + productName + "']/ancestor::div[@class='inventory_item']//button");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(btnLoc)).getText();
    }

    public double getProductPrice(String productName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(productsLoc));
        for (WebElement item : driver.findElements(productsLoc)) {
            String name = item.findElement(productNameLoc).getText();
            if (name.equals(productName)) {
                return parsePrice(item.findElement(productPriceLoc).getText());
            }
        }
        throw new NoSuchElementException("Product not found on inventory page: " + productName);
    }
    public List<Double> getProductsPrices(List<String> productNames) {
        List<Double> prices = new ArrayList<>();
        for (String productName : productNames) {
            prices.add(getProductPrice(productName));
        }
        return prices;
    }

    public double getTotalPrice(List<String> productNames) {
        double total = 0;
        for (double price : getProductsPrices(productNames)) {
            total += price;
        }
        return total;
    }

    public void logout() {
        wait.until(ExpectedConditions.elementToBeClickable(SideMenuLoc));
        driver.findElement(SideMenuLoc).click();
        wait.until(ExpectedConditions.elementToBeClickable(LogoutLoc));
        driver.findElement(LogoutLoc).click();
        wait.until(ExpectedConditions.urlToBe("https://www.saucedemo.com/"));
    }


    public CartPage cartPage() {
        return new CartPage(driver);
    }



}
