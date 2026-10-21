package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutPage extends BasePage{
    public CheckoutPage(WebDriver driver){
        super(driver);
    }

    private final By FirstNameLoc = By.id("first-name");
    private final By LastNameLoc = By.id("last-name");
    private final By PostalCodeLoc = By.id("postal-code");
    private final By ContinueLoc = By.id("continue");
    private final By TotalPriceLoc = By.cssSelector(".summary_subtotal_label");
    private final By ErrorMsgLoc = By.cssSelector(".error-message-container");


    public void validCheckoutPageContinue(){
        driver.findElement(FirstNameLoc).sendKeys("Ahmed");
        driver.findElement(LastNameLoc).sendKeys("Fathy");
        driver.findElement(PostalCodeLoc).sendKeys("12223");
        driver.findElement(ContinueLoc).click();
    }

    public double getTotalPrice() {
        wait.until(ExpectedConditions.urlContains("checkout-step-two"));
        String text = wait.until(
                ExpectedConditions.visibilityOfElementLocated(TotalPriceLoc)).getText();
        return parsePrice(text);
    }
    public boolean errorMsgloaded(){
        driver.findElement(ContinueLoc).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(ErrorMsgLoc));
        return driver.findElement(ErrorMsgLoc).isDisplayed();
    }

}
