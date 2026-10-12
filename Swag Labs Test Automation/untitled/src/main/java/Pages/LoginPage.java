package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private final By usernameinputLoc = By.id("user-name");
    private final By passwordinputLoc = By.id("password");
    private final By loginbuttonLoc = By.id("login-button");
    private final By errormessageLoc = By.className("error-message-container");
    private final By erroriconeLoc = By.className("error_icon") ;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait=new WebDriverWait(driver , Duration.ofSeconds(10));

    }

    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameinputLoc));
        driver.findElement(usernameinputLoc).clear();
        driver.findElement(usernameinputLoc).sendKeys(username);
    }
    public void enterPassword(String password) {
        driver.findElement(passwordinputLoc).clear();
        driver.findElement(passwordinputLoc).sendKeys(password);
    }
    public void clickLoginButton() {
        driver.findElement(loginbuttonLoc).click();
    }
    public void validateLogin(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }
    public List <WebElement> erroricon(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(erroriconeLoc));

        List<WebElement> erroricon = new ArrayList<>();

        for (WebElement e : driver.findElements(erroriconeLoc)) {
            erroricon.add(e);
        }

        return erroricon;
    }
    public void enterusernameAndLeavePassword(String username){
        driver.findElement(usernameinputLoc).clear();
        driver.findElement(usernameinputLoc).sendKeys(username);
        driver.findElement(passwordinputLoc).clear();
        driver.findElement(loginbuttonLoc).click();
    }

    public String enterpasswordMsg(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(errormessageLoc));

        return driver.findElement(errormessageLoc).getText();

    }
    public boolean isInventory () {
        return wait.until(ExpectedConditions.urlContains("/inventory.html"));
    }

}
