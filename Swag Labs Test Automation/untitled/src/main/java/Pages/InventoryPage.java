package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class InventoryPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private final By pageTitleLoc = By.className("app_logo");
    private final By cardIconLoc = By.className("shopping_cart_link");
    private final By productsLoc = By.className("inventory_item");
    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait=new WebDriverWait(driver , Duration.ofSeconds(10));
    }

    public String open(){
        wait.until(ExpectedConditions.urlContains("inventory.html"));
        driver.get(driver.getCurrentUrl());
        return driver.getCurrentUrl();
    }
    public boolean clickOnCardIcon(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(cardIconLoc));

        driver.findElement(cardIconLoc).isDisplayed();
        return driver.findElement(cardIconLoc).isDisplayed();
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

}
