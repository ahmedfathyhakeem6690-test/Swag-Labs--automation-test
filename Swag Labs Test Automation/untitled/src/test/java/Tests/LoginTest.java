package Tests;

import Pages.LoginPage;
import base.BaseTest;
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
    public void loginTest(Map<String, String> data)  {
        SoftAssert softAssert = new SoftAssert();
        String username = data.get("username");
        String password = data.get("password");
        String expected = data.get("expected");

        loginPage.validateLogin(username, password);


        switch (expected) {
            case "success" -> softAssert.assertTrue(loginPage.isInventory(), username + " " + password);
            case "locked" -> {
                String msg = loginPage.enterpasswordMsg();
                softAssert.assertEquals(msg, "Epic sadface: Sorry, this user has been locked out.");
            }
            case "failed" -> {
                String msg = loginPage.enterpasswordMsg();
                softAssert.assertEquals(msg, "Epic sadface: Username and password do not match any user in this service");
                softAssert.assertFalse(loginPage.erroricon().isEmpty());
            }
            case "invalid" -> {
                String msg = loginPage.enterpasswordMsg();
                softAssert.assertEquals(msg, "Epic sadface: Password is required");
            }
        }
        softAssert.assertAll();


    }

}
