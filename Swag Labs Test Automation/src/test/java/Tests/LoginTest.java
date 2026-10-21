package Tests;

import Pages.LoginPage;
import base.BaseTest;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.DataDriven;

import java.io.IOException;
import java.util.Map;

public class LoginTest extends BaseTest {

    private LoginPage loginPage;
    @BeforeMethod
    public void beforeMethod() {
        loginPage = new LoginPage(driver);
    }
    @DataProvider(name = "LoginData")
    public Object[][] getLoginData() throws IOException {
        return DataDriven.jsonReader();
    }
    @Test (dataProvider = "LoginData")
    @Feature("Login Feature")
    @Epic("Authentication Epic")
    @Description ("Test that login with valid credintial")
    @Severity(SeverityLevel.CRITICAL)
    public void loginTest(Map<String, String> data)  {
        SoftAssert softAssert = new SoftAssert();
        String username = data.get("username");
        String password = data.get("password");
        String expected = data.get("expected");

        loginPage.validateLogin(username, password);
        switch (expected) {
            case "success" -> {
                Allure.step("Verify user could login with valid credintials");
                softAssert.assertTrue(loginPage.isInventory(), username + " " + password);
                //softAssert.assertFalse(loginPage.isInventory(), username + " " + password);
            }
            case "locked" -> {
                Allure.step("Verify user is locked");
                String msg = loginPage.enterpasswordMsg();
                softAssert.assertEquals(msg, "Epic sadface: Sorry, this user has been locked out.");
                //softAssert.assertEquals(msg, "Epic sadface:  this user has been locked out.");

            }
            case "failed" -> {
                Allure.step("Verify user is failed");
                String msg = loginPage.enterpasswordMsg();
               softAssert.assertEquals(msg, "Epic sadface: Username and password do not match any user in this service");
               // softAssert.assertEquals(msg, "Epic sadface: Username and password do not match any user in");
                softAssert.assertFalse(loginPage.erroricon().isEmpty());
            }
            case "invalid" -> {
                Allure.step("Verify user is invalid");
                String msg = loginPage.enterpasswordMsg();
                softAssert.assertEquals(msg, "Epic sadface: Password is required");
                //softAssert.assertEquals(msg, "Epic : Password is required");

            }
        }
        softAssert.assertAll();


    }

}
